package org.adempiere.ad.expression.api;

import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.json.JsonStringExpressionSerializer;
import org.compiere.util.Evaluatee;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableSet;

/**
 * NULL {@link IStringExpression}
 *
 * @author tsa
 *
 */
@JsonSerialize(using = JsonStringExpressionSerializer.class)
public final class NullStringExpression implements ICachedStringExpression
{
	public static final NullStringExpression instance = new NullStringExpression();

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
	public Set<String> getParameters()
	{
		return ImmutableSet.of();
	}

	@Override
	public String evaluate(final Evaluatee ctx, final boolean ignoreUnparsable)
	{
		return EMPTY_RESULT;
	}

	@Override
	public String evaluate(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound)
	{
		return EMPTY_RESULT;
	}

	@Override
	public final IStringExpression resolvePartial(final Evaluatee ctx)
	{
		return this;
	}

	@Override
	public boolean isNullExpression()
	{
		return true;
	}

	@Override
	public Class<String> getValueClass()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
