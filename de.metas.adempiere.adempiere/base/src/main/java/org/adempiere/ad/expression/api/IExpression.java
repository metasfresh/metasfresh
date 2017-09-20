package org.adempiere.ad.expression.api;

import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;
import org.compiere.util.Evaluatee;

/**
 * Root interface for all kind of expressions
 * 
 * @author tsa
 * 
 * @param <V> expression evaluation result type
 */
public interface IExpression<V>
{
	/**
	 * @return the type of evaluation result
	 */
	Class<V> getValueClass();

	/**
	 * Gets string representation of underlying expression. Usually this shall be EXACTLY the same as the string from where the expression was compiled
	 * 
	 * @return string representation of underlying expression
	 */
	String getExpressionString();

	/**
	 * Similar with {@link #getExpressionString()} but in this case the string representation is build from compiled expression respecting all formating
	 * 
	 * @return
	 */
	default String getFormatedExpressionString()
	{
		return getExpressionString();
	}

	/**
	 * Gets the list of parameter names.<br>
	 * In most implementations this can be the result of a {@link #getParameters()} that was "dumbed down" via {@link CtxNames#toNames(java.util.Collection)}.<br>
	 * However, if your implementation wraps around and delegates to an internal {@link IExpression} instance, then I recommend to explicitly delegate also this method.
	 * 
	 * @return list of parameter names or empty list; never return {@code null}
	 */
	default Set<String> getParameterNames()
	{
		return CtxNames.toNames(getParameters());
	}

	/**
	 * Return the parameters as {@link CtxName}s.<br>
	 * If you really, really have only strings in your implementation, you can use {@link CtxNames#parseAll(java.util.Collection)} to implement the method.
	 * 
	 * @return
	 */
	Set<CtxName> getParameters();

	/**
	 * Consider using {@link #evaluate(Evaluatee, OnVariableNotFound)}. This method will be deprecated in future.
	 * 
	 * @param ctx
	 * @param ignoreUnparsable
	 * @return
	 */
	default V evaluate(final Evaluatee ctx, final boolean ignoreUnparsable)
	{
		// backward compatibility
		final OnVariableNotFound onVariableNotFound = ignoreUnparsable ? OnVariableNotFound.Empty : OnVariableNotFound.ReturnNoResult;
		return evaluate(ctx, onVariableNotFound);
	}

	/**
	 * Evaluates expression in given context.
	 * 
	 * @param ctx
	 * @param onVariableNotFound
	 * @return evaluation result
	 * @throws ExpressionEvaluationException if evaluation fails and we were advised to throw exception
	 */
	V evaluate(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException;

	/**
	 * @param result
	 * @return true if the given <code>result</code> shall be considered as "NO RESULT"
	 */
	default boolean isNoResult(Object result)
	{
		return result == null;
	}

	/**
	 * @return true if this expression is constant and always evaluated "NO RESULT"
	 * @see #isNoResult(Object)
	 */
	default boolean isNullExpression()
	{
		return false;
	}
}
