package org.adempiere.ad.expression.api.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.compiere.util.CtxName;
import org.compiere.util.Evaluatee;

import com.google.common.collect.ImmutableList;

/**
 * Standard implementation of {@link IStringExpression}
 * 
 * @author tsa
 * 
 */
/* package */final class StringExpression implements IStringExpression
{
	private final String expressionStr;
	private final List<Object> expressionChunks;

	private final List<String> stringParams;

	/* package */StringExpression(final String expressionStr, final List<Object> expressionChunks)
	{
		super();
		this.expressionStr = expressionStr;
		this.expressionChunks = ImmutableList.copyOf(expressionChunks);

		//
		// Initialize stringParams list
		final Set<String> stringParams = new LinkedHashSet<>(); // NOTE: preserve parameters order because at least some tests are relying on this
		for (final Object chunk : expressionChunks)
		{
			if (chunk instanceof CtxName)
			{
				// NOTE: we need only the parameter name (and not all modifiers)
				final String parameterName = ((CtxName)chunk).getName();
				stringParams.add(parameterName);
			}
		}
		this.stringParams = ImmutableList.copyOf(stringParams);
	}

	@Override
	public String toString()
	{
		return expressionStr;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expressionStr == null) ? 0 : expressionStr.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StringExpression other = (StringExpression)obj;
		if (expressionStr == null)
		{
			if (other.expressionStr != null)
				return false;
		}
		else if (!expressionStr.equals(other.expressionStr))
			return false;
		return true;
	}

	@Override
	public String getExpressionString()
	{
		return expressionStr;
	}

	@Override
	public String getFormatedExpressionString()
	{
		StringBuilder sb = new StringBuilder();
		for (final Object chunk : expressionChunks)
		{
			if (chunk == null)
			{
				continue;
			}

			if (chunk instanceof CtxName)
			{
				final CtxName name = (CtxName)chunk;
				sb.append(name.toString(true));
			}
			else
			{
				sb.append(chunk.toString());
			}
		}
		return sb.toString();
	}

	@Override
	public List<String> getParameters()
	{
		return stringParams;
	}

	@Override
	public List<Object> getExpressionChunks()
	{
		return expressionChunks;
	}

	@Override
	public String evaluate(Evaluatee ctx, boolean ignoreUnparsable)
	{
		// backward compatibility
		final OnVariableNotFound onVariableNotFound = ignoreUnparsable ? OnVariableNotFound.Empty : OnVariableNotFound.ReturnNoResult;
		return evaluate(ctx, onVariableNotFound);
	}

	@Override
	public String evaluate(Evaluatee ctx, final OnVariableNotFound onVariableNotFound)
	{
		final IExpressionEvaluator<IStringExpression, String> evaluator = getEvaluator();
		final String value = evaluator.evaluate(ctx, this, onVariableNotFound);
		return value;
	}

	@Override
	public final IExpressionEvaluator<IStringExpression, String> getEvaluator()
	{
		return StringExpressionEvaluator.instance;
	}
}
