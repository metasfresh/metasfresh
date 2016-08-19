package org.adempiere.ad.expression.api;

import java.util.List;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.impl.StringExpressionEvaluator;
import org.adempiere.ad.expression.json.JsonStringExpressionSerializer;
import org.compiere.util.Evaluatee;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableList;

/**
 * NULL {@link IStringExpression}
 * 
 * @author tsa
 * 
 */
@JsonSerialize(using = JsonStringExpressionSerializer.class)
public final class NullStringExpression implements IStringExpression
{
	public static final NullStringExpression instance = new NullStringExpression();
	
	public static final boolean isNull(final IStringExpression expression)
	{
		return expression == null || expression == NullStringExpression.instance;
	}

	private NullStringExpression()
	{
		super();
	}

	@Override
	public String getExpressionString()
	{
		return "";
	}

	@Override
	public String getFormatedExpressionString()
	{
		return "";
	}

	@Override
	public List<String> getParameters()
	{
		return ImmutableList.of();
	}

	@Override
	public List<Object> getExpressionChunks()
	{
		return ImmutableList.of();
	}

	@Override
	public String evaluate(Evaluatee ctx, boolean ignoreUnparsable)
	{
		return StringExpressionEvaluator.EMPTY_RESULT;
	}

	@Override
	public String evaluate(Evaluatee ctx, OnVariableNotFound onVariableNotFound)
	{
		return StringExpressionEvaluator.EMPTY_RESULT;
	}

	@Override
	public final IExpressionEvaluator<IStringExpression, String> getEvaluator()
	{
		return StringExpressionEvaluator.instance;
	}
}
