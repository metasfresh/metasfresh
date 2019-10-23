package org.adempiere.ad.expression.api;

import de.metas.util.ISingletonService;

/**
 * Expressions factory. Used for compiling various types of expressions.
 * 
 * @author tsa
 * 
 */
public interface IExpressionFactory extends ISingletonService
{
	<V, ET extends IExpression<V>> void registerCompiler(final Class<ET> expressionType, final IExpressionCompiler<V, ET> compiler);

	<V, ET extends IExpression<V>, CT extends IExpressionCompiler<V, ET>> CT getCompiler(final Class<ET> expressionType);

	/**
	 * Compiles given string expression
	 * 
	 * @param expressionStr The expression to be compiled
	 * @return compiled string expression; never returns null
	 */
	<V, ET extends IExpression<V>> ET compile(String expressionStr, final Class<ET> expressionType);

	/**
	 * Compiles given string expression
	 * 
	 * If the expression cannot be evaluated, returns the given default expression.
	 * 
	 * This method does not throw any exception, but in case of error that error will be logged.
	 * 
	 * @param expressionStr The expression to be compiled
	 * @return compiled string expression; never returns null
	 */
	<V, ET extends IExpression<V>> ET compileOrDefault(String expressionStr, ET defaultExpr, final Class<ET> expressionType);

	<V, ET extends IExpression<V>> ET compile(String expressionStr, Class<ET> expressionType, ExpressionContext context);
}
