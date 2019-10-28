package org.openl.binding.impl;

import org.openl.binding.IBindingContext;
import org.openl.binding.IBoundNode;
import org.openl.binding.ILocalVar;
import org.openl.syntax.ISyntaxNode;
import org.openl.types.IOpenClass;
import org.openl.types.NullOpenClass;

/**
 * Binds conditional index for arrays like: - arrayOfDrivers[@ age < 20]; - arrayOfDrivers[select all having gender ==
 * "Male"]
 *
 * @author PUdalau
 */
public class OrderByIndexNodeBinder extends BaseAggregateIndexNodeBinder {

    @Override
    protected IBoundNode createBoundNode(ISyntaxNode node,
            IBoundNode targetNode,
            IBoundNode expressionNode,
            ILocalVar localVar,
            IBindingContext bindingContext) {

        IOpenClass type = expressionNode.getType();
        if (type instanceof NullOpenClass) {
            String message = "Order By expression requires typed parameter";
            return makeErrorNode(message, expressionNode.getSyntaxNode(), bindingContext);
        }
        Class<?> instanceClass = type.getInstanceClass();
        if (!Comparable.class
            .isAssignableFrom(instanceClass) && (!instanceClass.isPrimitive() || instanceClass == void.class)) {
            return makeErrorNode("Order By expression must be Comparable",
                expressionNode.getSyntaxNode(),
                bindingContext);
        }
        boolean isDecreasing = node.getType().contains("decreasing");
        return new OrderByIndexNode(node, targetNode, expressionNode, localVar, isDecreasing);
    }

}
