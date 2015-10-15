package org.adempiere.ad.expression.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.compiere.util.CtxName;
import org.compiere.util.Evaluatee;

/**
 * Standard implementation of {@link IStringExpression}
 * 
 * @author tsa
 * 
 */
/* package */class StringExpression implements IStringExpression
{
	private final String expressionStr;
	private final List<Object> expressionChunks;

	private final transient List<String> stringParams = new ArrayList<String>();
	private final transient List<String> stringParamsRO = Collections.unmodifiableList(stringParams);

	/* package */StringExpression(final String expressionStr, final List<Object> expressionChunks)
	{
		super();
		this.expressionStr = expressionStr;
		this.expressionChunks = expressionChunks;

		//
		// Initialize stringParams list
		for (final Object chunk : expressionChunks)
		{
			if (chunk instanceof CtxName)
			{
				// NOTE: we need only the parameter name (and not all modifiers)
				final String parameterName = ((CtxName)chunk).getName();
				stringParams.add(parameterName);
			}
		}
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
		return stringParamsRO;
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
