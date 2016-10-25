package org.adempiere.ad.expression.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

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
	 * Compiles given expression.
	 * 
	 * @param expressionStr
	 * @return compiled expression
	 */
	public static IStringExpression compile(final String expressionStr)
	{
		return StringExpressionCompiler.instance.compile(expressionStr);
	}
	
	/**
	 * Compiles given string expression
	 * 
	 * If the expression cannot be evaluated, returns the given default expression.
	 * 
	 * This method does not throw any exception, but in case of error that error will be logged.
	 * 
	 * @param expressionStr The expression to be compiled
	 * @return compiled expression or <code>defaultExpression</code>
	 */
	public static IStringExpression compileOrDefault(final String expressionStr, IStringExpression defaultExpression)
	{
		return StringExpressionCompiler.instance.compileOrDefault(expressionStr, defaultExpression);
	}


	/**
	 * Gets a new composite string expression builder.
	 * 
	 * @return composer
	 */
	public static CompositeStringExpression.Builder composer()
	{
		return CompositeStringExpression.builder();
	}

	/**
	 * Returns a {@code Collector} that concatenates the input string expressions, separated by the specified delimiter, in encounter order.
	 */
	public static Collector<IStringExpression, ?, IStringExpression> collectJoining(final String delimiter)
	{
		final Supplier<List<IStringExpression>> supplier = ArrayList::new;
		final BiConsumer<List<IStringExpression>, IStringExpression> accumulator = (list, item) -> list.add(item);
		final BinaryOperator<List<IStringExpression>> combiner = (list1, list2) -> {
			list1.addAll(list2);
			return list1;
		};
		final Function<List<IStringExpression>, IStringExpression> finisher = (list) -> composer()
				.appendAllJoining(delimiter, list)
				.build();
		return Collector.of(supplier, accumulator, combiner, finisher);
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
