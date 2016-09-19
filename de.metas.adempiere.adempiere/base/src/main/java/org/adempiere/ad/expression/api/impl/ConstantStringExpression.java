package org.adempiere.ad.expression.api.impl;

import java.util.Objects;
import java.util.Set;

import org.adempiere.ad.expression.api.ICachedStringExpression;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.json.JsonStringExpressionSerializer;
import org.adempiere.util.Check;
import org.compiere.util.Evaluatee;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

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
public final class ConstantStringExpression implements IStringExpression, ICachedStringExpression
{
	public static final ConstantStringExpression of(final String expressionStr)
	{
		final ConstantStringExpression cached = CACHE.get(expressionStr);
		if (cached != null)
		{
			return cached;
		}

		return new ConstantStringExpression(expressionStr);
	}

	private static final ImmutableMap<String, ConstantStringExpression> CACHE = ImmutableMap.<String, ConstantStringExpression> builder()
			.put("", new ConstantStringExpression("")) // this case is totally discouraged, but if it happens, lets not create a lot of instances...
			.put(" ", new ConstantStringExpression(" ")) // one space
			.put(", ", new ConstantStringExpression(", ")) // one space comma
			.put("\n, ", new ConstantStringExpression("\n, "))
			.put("\n", new ConstantStringExpression("\n"))
			.put("\r\n", new ConstantStringExpression("\r\n"))
			.build();

	private final String expressionStr;

	private ConstantStringExpression(final String expressionStr)
	{
		super();
		Check.assumeNotNull(expressionStr, "Parameter expressionStr is not null");
		this.expressionStr = expressionStr;
	}

	@Override
	public String toString()
	{
		return expressionStr;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(expressionStr);
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
		return Objects.equals(expressionStr, other.expressionStr);
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
	public Set<String> getParameters()
	{
		return ImmutableSet.of();
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

	public String getConstantValue()
	{
		return expressionStr;
	}

	@Override
	public final IStringExpression resolvePartial(final Evaluatee ctx)
	{
		return this;
	}
}
