/*
 * Created on May 20, 2003
 *
 * Developed by Intelligent ChoicePoint Inc. 2003
 */

package org.openl.types.java;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.openl.base.INamedThing;
import org.openl.classloader.OpenLBundleClassLoader;
import org.openl.types.IAggregateInfo;
import org.openl.types.IMemberMetaInfo;
import org.openl.types.IOpenClass;
import org.openl.types.IOpenField;
import org.openl.types.IOpenMethod;
import org.openl.types.impl.AOpenClass;
import org.openl.types.impl.ArrayIndex;
import org.openl.types.impl.ArrayLengthOpenField;
import org.openl.types.impl.MethodKey;
import org.openl.util.RuntimeExceptionWrapper;
import org.openl.util.StringTool;
import org.openl.vm.IRuntimeEnv;

/**
 * @author snshor
 */
public class JavaOpenClass extends AOpenClass {

    public static final JavaOpenClass INT = new JavaPrimitiveClass(int.class, Integer.class, Integer.valueOf(0));
    public static final JavaOpenClass LONG = new JavaPrimitiveClass(long.class, Long.class, Long.valueOf(0));
    public static final JavaOpenClass DOUBLE = new JavaPrimitiveClass(double.class, Double.class, Double.valueOf(0));
    public static final JavaOpenClass FLOAT = new JavaPrimitiveClass(float.class, Float.class, Float.valueOf(0));
    public static final JavaOpenClass SHORT = new JavaPrimitiveClass(short.class,
        Short.class,
        Short.valueOf((short) 0));
    public static final JavaOpenClass CHAR = new JavaPrimitiveClass(char.class,
        Character.class,
        Character.valueOf('\0'));
    public static final JavaOpenClass BYTE = new JavaPrimitiveClass(byte.class, Byte.class, Byte.valueOf((byte) 0));
    public static final JavaOpenClass BOOLEAN = new JavaPrimitiveClass(boolean.class, Boolean.class, Boolean.FALSE);
    public static final JavaOpenClass VOID = new JavaPrimitiveClass(void.class, Void.class, null);
    public static final JavaOpenClass STRING = new JavaOpenClass(String.class, true);
    public static final JavaOpenClass OBJECT = new JavaOpenClass(Object.class, false);
    public static final JavaOpenClass CLASS = new JavaOpenClass(Class.class, true);

    protected Class<?> instanceClass;

    private final boolean simple;

    private IAggregateInfo aggregateInfo;

    protected volatile Map<String, IOpenField> fields;

    protected JavaOpenClass(Class<?> instanceClass) {
        this(instanceClass, false);
    }

    protected JavaOpenClass(Class<?> instanceClass, boolean simple) {
        this.instanceClass = instanceClass;
        this.simple = simple;
    }

    public static JavaOpenClass getOpenClass(Class<?> c) {
        JavaOpenClass res = JavaOpenClassCache.getInstance().get(c);
        if (res == null) {
            if (c.isInterface()) {
                res = new JavaOpenInterface(c);
            } else if (c.isEnum()) {
                res = new JavaOpenEnum(c);
            } else {
                CustomJavaOpenClass annotation = c.getAnnotation(CustomJavaOpenClass.class);
                if (annotation != null) {
                    res = createOpenClass(c, annotation);
                } else {
                    res = new JavaOpenClass(c);
                }
            }
            JavaOpenClassCache.getInstance().put(c, res);
        }

        return res;
    }

    public static JavaOpenClass createNewOpenClass(Class<?> c) {
        if (c.isInterface()) {
            return new JavaOpenInterface(c);
        } else if (c.isEnum()) {
            return new JavaOpenEnum(c);
        } else {
            return new JavaOpenClass(c);
        }
    }

    private static JavaOpenClass createOpenClass(Class<?> c, CustomJavaOpenClass annotation) {
        Class<? extends JavaOpenClass> type = annotation.type();
        try {
            return type.getConstructor(Class.class).newInstance(c);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(String.format(
                "Cannot find constructor with signature 'public MyCustomJavaOpenClass(Class<?> c)' in type %s",
                type.getCanonicalName()), e);
        } catch (InstantiationException e) {
            throw new IllegalStateException(
                String.format("Error while creating a custom JavaOpenClass of type '%s'", type.getCanonicalName()),
                e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(
                String.format("Constructor of a custom JavaOpenClass of type '%s' is inaccessible",
                    type.getCanonicalName()),
                e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(
                String.format("Constructor of a class '%s' threw and exception", type.getCanonicalName()),
                e);
        }
    }

    public static IOpenClass[] getOpenClasses(Class<?>[] cc) {
        if (cc.length == 0) {
            return IOpenClass.EMPTY;
        }

        IOpenClass[] ary = new IOpenClass[cc.length];

        for (int i = 0; i < cc.length; i++) {
            ary[i] = getOpenClass(cc[i]);
        }

        return ary;

    }

    public static Class<?> makeArrayClass(Class<?> c) {
        return Array.newInstance(c, 0).getClass();
    }

    public static ArrayIndex makeArrayIndex(IOpenClass arrayType) {
        return new ArrayIndex(getOpenClass(arrayType.getInstanceClass().getComponentType()));
    }

    public static synchronized void resetClassloader(ClassLoader cl) {
        List<Class<?>> toRemove = new ArrayList<Class<?>>();
        
        Collection<Class<?>> nonJavaClasses = JavaOpenClassCache.getInstance().getNonJavaClasses();
        
        for (Class<?> c : nonJavaClasses) {
            ClassLoader classLoader = c.getClassLoader();
            if (classLoader == cl) {
                toRemove.add(c);
            }
            if (cl instanceof OpenLBundleClassLoader) {
                if (((OpenLBundleClassLoader) cl).containsClassLoader(classLoader)) {
                    toRemove.add(c);
                }
            }
        }

        for (Class<?> c : toRemove) {
            JavaOpenClassCache.getInstance().remove(c);
        }
    }

    public static boolean isVoid(IOpenClass clazz) {
        return JavaOpenClass.VOID.equals(clazz);
    }

    protected void collectBeanFields() {
        BeanOpenField.collectFields(fields, instanceClass, null, null);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JavaOpenClass)) {
            return false;
        }
        return instanceClass == ((JavaOpenClass) obj).instanceClass;
    }

    @Override
    protected Map<String, IOpenField> fieldMap() {
        if (fields == null) {
            synchronized (this) {
                if (fields == null) {
                    fields = new HashMap<String, IOpenField>();
                    Field[] ff = instanceClass.getDeclaredFields();

                    if (isPublic(instanceClass)) {
                        for (int i = 0; i < ff.length; i++) {
                            if (isPublic(ff[i])) {
                                fields.put(ff[i].getName(), new JavaOpenField(ff[i]));
                            }
                        }
                    }
                    if (instanceClass.isArray()) {
                        fields.put("length", new JavaArrayLengthField());
                    }

                    fields.put("class", new JavaClassClassField(instanceClass));
                    collectBeanFields();
                }
            }
        }
        return fields;
    }

    public synchronized IAggregateInfo getAggregateInfo() {
        if (aggregateInfo != null)
            return aggregateInfo;
        
        if (List.class.isAssignableFrom(getInstanceClass())) {
            aggregateInfo = JavaListAggregateInfo.LIST_AGGREGATE;
        }
        else aggregateInfo = JavaArrayAggregateInfo.ARRAY_AGGREGATE;
        return aggregateInfo;
    }

    public String getDisplayName(int mode) {
        String name = getName();
        switch (mode) {
            case INamedThing.SHORT:
            case INamedThing.REGULAR:
            default:
                return StringTool.lastToken(name, ".");
            case INamedThing.LONG:
                return name;
        }
    }

    public Class<?> getInstanceClass() {
        return instanceClass;
    }

    String name;

    public String getName() {
        if (name == null)
            name = instanceClass.getCanonicalName();
        return name;
    }

    @Override
    public String getJavaName() {
        return instanceClass.getName();
    }

    public String getSimpleName() {
        return getDisplayName(INamedThing.SHORT);
    }

    @Override
    public int hashCode() {
        return instanceClass.hashCode();
    }

    @Override
    public boolean isAbstract() {
        return Modifier.isAbstract(instanceClass.getModifiers());
    }

    public boolean isAssignableFrom(Class<?> c) {
        return instanceClass.isAssignableFrom(c);
    }

    public boolean isAssignableFrom(IOpenClass ioc) {
        return instanceClass.isAssignableFrom(ioc.getInstanceClass());
    }

    public boolean isInstance(Object instance) {
        return instanceClass.isInstance(instance);
    }

    protected boolean isPublic(Class<?> declaringClass) {
        return Modifier.isPublic(declaringClass.getModifiers());
    }

    protected boolean isPublic(Member member) {
        return Modifier.isPublic(member.getModifiers());
    }

    @Override
    public boolean isSimple() {
        return simple;
    }

    @Override
    protected Map<MethodKey, IOpenMethod> initMethodMap() {
        HashMap<MethodKey, IOpenMethod> methods;

        methods = new HashMap<MethodKey, IOpenMethod>();
        Method[] mm = instanceClass.getDeclaredMethods();
        if (isPublic(instanceClass)) {
            for (int i = 0; i < mm.length; i++) {
                if (isPublic(mm[i])) {
                    JavaOpenMethod om = new JavaOpenMethod(mm[i]);
                    methods.put(new MethodKey(om), om);
                }
            }
        }

        Constructor<?>[] cc = instanceClass.getDeclaredConstructors();
        for (int i = 0; i < cc.length; i++) {
            if (isPublic(cc[i])) {
                IOpenMethod om = new JavaOpenConstructor(cc[i]);
                // Log.debug("Adding method " + mm[i].getName() + " code = "
                // + new MethodKey(om).hashCode());
                methods.put(new MethodKey(om), om);
            }
        }
        if (methods.isEmpty()) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(methods);
    }

    public Object newInstance(IRuntimeEnv env) {

        try {
            return getInstanceClass().newInstance();
        } catch (Exception e) {
            throw RuntimeExceptionWrapper.wrap(e);
        }
    }

    @Override
    public Object nullObject() {
        return null;
    }

    @Override
    public IOpenClass getComponentClass() {
        if (isArray() || OpenClassHelper.isCollection(this)) {
            return getAggregateInfo().getComponentType(this);
        } 
        return null;
    }

    List<IOpenClass> superClasses;

    public synchronized Iterable<IOpenClass> superClasses() {
        if (superClasses == null) {
            Class<?>[] interfaces = instanceClass.getInterfaces();
            Class<?> superClass = instanceClass.getSuperclass();
            List<IOpenClass> superClasses = new ArrayList<IOpenClass>(interfaces.length + 1);
            if (superClass != null) {
                superClasses.add(getOpenClass(superClass));
            }
            for (Class<?> interf : interfaces) {
                superClasses.add(getOpenClass(interf));
            }
            this.superClasses = superClasses;
        }

        return superClasses;
    }

    private static class JavaArrayLengthField extends ArrayLengthOpenField {
        @Override
        public int getLength(Object target) {
            if (target == null) {
                return 0;
            }
            return Array.getLength(target);
        }
    }

    private static class JavaClassClassField implements IOpenField {
        private Class<?> instanceClass;

        public JavaClassClassField(Class<?> instanceClass) {
            this.instanceClass = instanceClass;
        }

        public Object get(Object target, IRuntimeEnv env) {
            return instanceClass;
        }

        public IOpenClass getDeclaringClass() {
            return null;
        }

        public String getDisplayName(int mode) {
            return "class";
        }

        public IMemberMetaInfo getInfo() {
            return null;
        }

        public String getName() {
            return "class";
        }

        public IOpenClass getType() {
            return JavaOpenClass.CLASS;
        }

        public boolean isConst() {
            return true;
        }

        public boolean isReadable() {
            return true;
        }

        public boolean isStatic() {
            return true;
        }

        public boolean isWritable() {
            return false;
        }

        public void set(Object target, Object value, IRuntimeEnv env) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return getName();
        }
    }

    static private class JavaPrimitiveClass extends JavaOpenClass {
        private Class<?> wrapperClass;

        private Object nullObject;

        public JavaPrimitiveClass(Class<?> instanceClass, Class<?> wrapperClass, Object nullObject) {
            super(instanceClass, true);
            this.wrapperClass = wrapperClass;
            this.nullObject = nullObject;
        }

        @Override
        public Object newInstance(IRuntimeEnv env) {
            return nullObject;
        }

        @Override
        public Object nullObject() {
            return nullObject;
        }
    }

    private static class JavaOpenInterface extends JavaOpenClass {

        private static Method toString;
        private static Method equals;
        private static Method hashCode;

        private Map<Method, BeanOpenField> getters;
        private Map<Method, BeanOpenField> setters;

        @SuppressWarnings("unused")
        private Class<?> proxyClass;

        private InvocationHandler beanInterfaceHandler;

        static {
            try {
                toString = Object.class.getMethod("toString");
                equals = Object.class.getMethod("equals", Object.class);
                hashCode = Object.class.getMethod("hashCode");
            } catch (NoSuchMethodException nsme) {
                throw RuntimeExceptionWrapper.wrap(nsme);
            }
        }

        protected JavaOpenInterface(Class<?> instanceClass) {
            super(instanceClass);
            proxyClass = Proxy.getProxyClass(instanceClass.getClassLoader(), new Class[] { instanceClass });

        }

        @Override
        protected void collectBeanFields() {
            getters = new HashMap<Method, BeanOpenField>();
            setters = new HashMap<Method, BeanOpenField>();
            BeanOpenField.collectFields(fields, instanceClass, getters, setters);
        }

        private synchronized InvocationHandler getInvocationHandler(Class<?> instClass) {

            if (List.class.isAssignableFrom(instClass)) {
                return new JavaInstanceBasedInvocationhandler(new ArrayList<Object>());
            }

            if (Set.class.isAssignableFrom(instClass)) {
                return new JavaInstanceBasedInvocationhandler(new HashSet<Object>());
            }

            if (SortedMap.class.isAssignableFrom(instClass)) {
                return new JavaInstanceBasedInvocationhandler(new TreeMap<Object, Object>());
            }

            if (Map.class.isAssignableFrom(instClass)) {
                return new JavaInstanceBasedInvocationhandler(new HashMap<Object, Object>());
            }

            if (Collection.class.isAssignableFrom(instClass)) {
                return new JavaInstanceBasedInvocationhandler(new ArrayList<Object>());
            }

            if (beanInterfaceHandler == null) {
                beanInterfaceHandler = new BeanInterfaceInvocationHandler();
            }

            return beanInterfaceHandler;
        }

        @Override
        public Object newInstance(IRuntimeEnv env) {
            try {
                return Proxy.newProxyInstance(instanceClass.getClassLoader(),
                    new Class[] { instanceClass },
                    getInvocationHandler(instanceClass));
            } catch (Exception e) {
                throw RuntimeExceptionWrapper.wrap(e);
            }

        }

        private class JavaInstanceBasedInvocationhandler implements InvocationHandler {

            Object instance;

            public JavaInstanceBasedInvocationhandler(Object instance) {
                this.instance = instance;
            }

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(instance, args);
            }
        }

        private class BeanInterfaceInvocationHandler implements InvocationHandler {

            private IdentityHashMap<Object, HashMap<BeanOpenField, Object>> map = new IdentityHashMap<Object, HashMap<BeanOpenField, Object>>();

            public synchronized Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                HashMap<BeanOpenField, Object> values = map.get(proxy);
                if (values == null) {
                    values = new HashMap<BeanOpenField, Object>();
                    map.put(proxy, values);
                }

                BeanOpenField bf = null;
                if (getters != null) {
                    bf = getters.get(method);
                }

                if (bf != null) {
                    Object res = values.get(bf);
                    return res != null ? res : bf.getType().nullObject();
                }

                if (setters != null) {
                    bf = setters.get(method);
                }

                if (bf != null) {
                    values.put(bf, args[0]);
                    return null;
                }

                if (method.getName().equals(toString.getName())) {
                    return proxy.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(proxy));
                }

                if (method.getName().equals(hashCode.getName())) {
                    return System.identityHashCode(proxy);
                }

                if (method.getName().equals(equals.getName())) {
                    return proxy == args[0];
                }

                throw new RuntimeException(
                    "Default Interface Proxy Implementation does not support method " + method.getDeclaringClass()
                        .getName() + "::" + method.getName() + ". Only bean access is supported");
            }

        }

    }

}