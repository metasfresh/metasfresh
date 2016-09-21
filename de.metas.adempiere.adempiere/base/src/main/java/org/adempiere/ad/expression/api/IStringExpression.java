package org.adempiere.ad.expression.api;

import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.impl.CachedStringExpression;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.adempiere.ad.expression.api.impl.StringExpressionCompiler;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.ad.expression.json.JsonStringExpressionDeserializer;
import org.compiere.util.Evaluatee;
import org.compiere.util.Util;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * String Expression
 * 
 * NOTE to developers: If you are going to implement this interface please make sure your new implementation's evaluator is depending only on the context which is given as parameter and no other
 * external variables. Breaking this rule will make the expressions behaving weird.
 *
 * @author tsa
 *
 */
@JsonDeserialize(using = JsonStringExpressionDeserializer.class)
public interface IStringExpression extends IExpression<String>
{
	/**
	 * Gets a new composite string expression builder.
	 * 
	 * @return composer
	 */
	public static CompositeStringExpression.Builder composer()
	{
		return CompositeStringExpression.builder();
	}

	String EMPTY_RESULT = new String(""); // NOTE: we get a new string instance because we will compare with it by identity

	/**
	 * Null String Expression Object
	 */
	IStringExpression NULL = NullStringExpression.instance;

	@Override
	default Class<String> getValueClass()
	{
		return String.class;
	}

	@Override
	Set<String> getParameters();

	@Override
	default boolean isNoResult(final Object result)
	{
		return result == null
				|| Util.same(result, EMPTY_RESULT);
	}

	@Override
	default boolean isNullExpression()
	{
		return false;
	}

	/**
	 * Resolves all variables which available and returns a new string expression.
	 *
	 * @param ctx
	 * @return string expression with all available variables resolved.
	 * @throws ExpressionEvaluationException
	 */
	default IStringExpression resolvePartial(final Evaluatee ctx) throws ExpressionEvaluationException
	{
		final String expressionStr = evaluate(ctx, OnVariableNotFound.Preserve);
		return StringExpressionCompiler.instance.compile(expressionStr);
	}

	/**
	 * Turns this expression in an expression which caches it's evaluation results.
	 * If this expression implements {@link ICachedStringExpression} it will be returned directly without any wrapping.
	 * 
	 * @return cached expression
	 */
	default ICachedStringExpression caching()
	{
		return CachedStringExpression.wrapIfPossible(this);
	}

	/**
	 * @return same as {@link #toString()} but the whole string representation will be on one line (new lines are stripped)
	 */
	default String toOneLineString()
	{
		return toString()
				.replace("\r", "")
				.replace("\n", "");
	}
}
