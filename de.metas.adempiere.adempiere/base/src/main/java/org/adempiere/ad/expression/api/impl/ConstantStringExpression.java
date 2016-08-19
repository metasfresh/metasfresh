package org.adempiere.ad.expression.api.impl;

import java.util.List;

import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.json.JsonStringExpressionSerializer;
import org.adempiere.util.Check;
import org.compiere.util.Evaluatee;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableList;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

@JsonSerialize(using = JsonStringExpressionSerializer.class)
class ConstantStringExpression implements IStringExpression
{
	private final String expressionStr;
	private final List<Object> expressionChunks;

	/* package */ ConstantStringExpression(final String expressionStr)
	{
		super();
		Check.assumeNotNull(expressionStr, "Parameter expressionStr is not null");
		this.expressionStr = expressionStr;
		this.expressionChunks = ImmutableList.of((Object)expressionStr);
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
		result = prime * result + (expressionStr == null ? 0 : expressionStr.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final ConstantStringExpression other = (ConstantStringExpression)obj;
		if (expressionStr == null)
		{
			if (other.expressionStr != null)
			{
				return false;
			}
		}
		else if (!expressionStr.equals(other.expressionStr))
		{
			return false;
		}
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
		return expressionStr;
	}

	@Override
	public List<String> getParameters()
	{
		return ImmutableList.of();
	}

	@Override
	public List<Object> getExpressionChunks()
	{
		return expressionChunks;
	}

	@Override
	public String evaluate(final Evaluatee ctx, final boolean ignoreUnparsable)
	{
		return expressionStr;
	}

	@Override
	public String evaluate(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound)
	{
		return expressionStr;
	}

	@Override
	public final IExpressionEvaluator<IStringExpression, String> getEvaluator()
	{
		return StringExpressionEvaluator.instance;
	}
}
