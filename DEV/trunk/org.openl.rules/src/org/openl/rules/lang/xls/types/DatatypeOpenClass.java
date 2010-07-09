/*
 * Created on Jul 25, 2003
 *
 * Developed by Intelligent ChoicePoint Inc. 2003
 */

package org.openl.rules.lang.xls.types;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openl.types.IAggregateInfo;
import org.openl.types.IMemberMetaInfo;
import org.openl.types.IMethodSignature;
import org.openl.types.IOpenClass;
import org.openl.types.IOpenField;
import org.openl.types.IOpenMethod;
import org.openl.types.IOpenSchema;
import org.openl.types.impl.ADynamicClass;
import org.openl.types.impl.DynamicArrayAggregateInfo;
import org.openl.types.impl.MethodSignature;
import org.openl.types.java.JavaOpenClass;
import org.openl.vm.IRuntimeEnv;

/**
 * Open class for types represented as datatype table components in openl.
 * 
 * @author snshor
 *
 */
public class DatatypeOpenClass extends ADynamicClass {
    
    private static final Log LOG = LogFactory.getLog(DatatypeOpenClass.class);
    
    public DatatypeOpenClass(IOpenSchema schema, String name) {
        super(schema, name, null);
        addMethod(new EqualsMethod(this));
        addMethod(new HashCodeMethod(this));
        addMethod(new ToStringMethod(this));
    }

    @Override
    public IAggregateInfo getAggregateInfo() {
        return DynamicArrayAggregateInfo.aggregateInfo;
    }

    public Object newInstance(IRuntimeEnv env) {
        Object instance = null;
        try {
            instance = getInstanceClass().newInstance();
        } catch (InstantiationException e) {            
            LOG.error(this, e);
        } catch (IllegalAccessException e) {            
            LOG.error(this, e);
        }
        return instance;
    }


    /**
     * Constructor with all parameters initialization.
     * 
     * @author PUdalau
     */
    public static class OpenFieldsConstructor implements IOpenMethod {

        IOpenClass openClass;

        public OpenFieldsConstructor(IOpenClass openClass) {
            this.openClass = openClass;
        }

        public IOpenClass getDeclaringClass() {
            return openClass;
        }

        public String getDisplayName(int mode) {
            return openClass.getDisplayName(mode);
        }

        public IMemberMetaInfo getInfo() {
            return null;
        }

        public IOpenMethod getMethod() {
            return this;
        }

        public String getName() {
            return openClass.getName();
        }

        public IMethodSignature getSignature() {
            Collection<IOpenField> fields = openClass.getFields().values();
            IOpenClass[] params = new IOpenClass[fields.size()];
            int i = 0;
            for(IOpenField field : fields){
                params[i] = field.getType();
                i++;
            }
            return new MethodSignature(params);
        }

        public IOpenClass getType() {
            return openClass;
        }

        public Object invoke(Object target, Object[] params, IRuntimeEnv env) {
            Object result = openClass.newInstance(env);
            int i = 0;
            for(IOpenField field : openClass.getFields().values()){
                field.set(result, params[i], env);
                i++;
            }
            return result;
        }

        public boolean isStatic() {
            return true;
        }

        @Override
        public String toString() {
            return openClass.getName();
        }

    };

    /**
     * <code>toString()</code> method.
     * 
     * @author PUdalau
     *
     */
    public static class ToStringMethod implements IOpenMethod{
        private IOpenClass openClass;
        
        public ToStringMethod(IOpenClass forClass) {
            this.openClass = forClass;
        }

        public IMethodSignature getSignature() {
            return IMethodSignature.VOID;
        }

        public IOpenClass getDeclaringClass() {
            return openClass;
        }

        public IMemberMetaInfo getInfo() {
            return null;
        }

        public IOpenClass getType() {
            return JavaOpenClass.STRING;
        }

        public boolean isStatic() {
            return false;
        }

        public String getDisplayName(int mode) {
            return getName();
        }

        public String getName() {
            return "toString";
        }

        public IOpenMethod getMethod() {
            return this;
        }

        public Object invoke(Object target, Object[] params, IRuntimeEnv env) {
            StringBuilder builder = new StringBuilder(openClass.getDisplayName(0) + "{ ");
            Map<String, IOpenField> fields = openClass.getFields();
            for (Entry<String, IOpenField> field : fields.entrySet()) {
                builder.append(field.getKey() + "=" + field.getValue().get(target, env) + " ");
            }
            builder.append('}');
            return builder.toString();
        }
    }
    
    /**
     * Method that compares two objects by fields defined in some {@link IOpenClass}
     * 
     * @author PUdalau
     */
    public static class EqualsMethod implements IOpenMethod{
        private IOpenClass openClass;
        
        public EqualsMethod(IOpenClass forClass) {
            this.openClass = forClass;
        }

        public IMethodSignature getSignature() {
            return new MethodSignature(new IOpenClass[]{JavaOpenClass.OBJECT});
        }

        public IOpenClass getDeclaringClass() {
            return openClass;
        }

        public IMemberMetaInfo getInfo() {
            return null;
        }

        public IOpenClass getType() {
            return JavaOpenClass.BOOLEAN;
        }

        public boolean isStatic() {
            return false;
        }

        public String getDisplayName(int mode) {
            return getName();
        }

        public String getName() {
            return "equals";
        }

        public IOpenMethod getMethod() {
            return this;
        }

        public Object invoke(Object target, Object[] params, IRuntimeEnv env) {
            EqualsBuilder builder = new EqualsBuilder();
            Map<String, IOpenField> fields = openClass.getFields();
            for (IOpenField field : fields.values()) {
                builder.append(field.get(target, env), field.get(params[0], env));
            }
            return builder.isEquals();
        }
    }

    /**
     * Methods that returns hash code calculated using fields.
     * @author PUdalau
     *
     */
    public static class HashCodeMethod implements IOpenMethod{
        private IOpenClass openClass;
        
        public HashCodeMethod(IOpenClass forClass) {
            this.openClass = forClass;
        }

        public IMethodSignature getSignature() {
            return IMethodSignature.VOID;
        }

        public IOpenClass getDeclaringClass() {
            return openClass;
        }

        public IMemberMetaInfo getInfo() {
            return null;
        }

        public IOpenClass getType() {
            return JavaOpenClass.INT;
        }

        public boolean isStatic() {
            return false;
        }

        public String getDisplayName(int mode) {
            return getName();
        }

        public String getName() {
            return "hashCode";
        }

        public IOpenMethod getMethod() {
            return this;
        }

        public Object invoke(Object target, Object[] params, IRuntimeEnv env) {
            HashCodeBuilder builder = new HashCodeBuilder();
            Map<String, IOpenField> fields = openClass.getFields();
            for (IOpenField field : fields.values()) {
                builder.append(field.get(target, env));
            }
            return builder.toHashCode();
        }
    }
}
