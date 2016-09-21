package org.adempiere.ad.expression.api.impl;

import java.util.Objects;
import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.ad.expression.json.JsonStringExpressionSerializer;
import org.adempiere.util.Check;
import org.compiere.util.CtxName;
import org.compiere.util.Evaluatee;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

/**
 * Specialized {@link IStringExpression} which wraps one {@link CtxName} parameter.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@JsonSerialize(using = JsonStringExpressionSerializer.class)
/* package */final class SingleParameterStringExpression implements IStringExpression
{
	private final String expressionStr;
	private final CtxName parameter;

	private final Set<String> parameters;

	/* package */ SingleParameterStringExpression(final String expressionStr, final CtxName parameter)
	{
		super();

		Check.assumeNotNull(expressionStr, "Parameter expressionStr is not null");
		this.expressionStr = expressionStr;

		Check.assumeNotNull(parameter, "Parameter parameter is not null");
		this.parameter = parameter;

		parameters = ImmutableSet.of(parameter.getName()); // NOTE: we need only the parameter name (and not all modifiers)
	}

	@Override
	public String toString()
	{
		return expressionStr;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(parameter);
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
		final SingleParameterStringExpression other = (SingleParameterStringExpression)obj;
		return Objects.equals(parameter, other.parameter);
	}

	@Override
	public String getExpressionString()
	{
		return expressionStr;
	}

	@Override
	public String getFormatedExpressionString()
	{
		return parameter.toStringWithMarkers();
	}

	@Override
	public Set<String> getParameters()
	{
		return parameters;
	}

	@Override
	public String evaluate(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException
	{
		try
		{
			return StringExpressionsHelper.evaluateParam(parameter, ctx, onVariableNotFound);
		}
		catch (final Exception e)
		{
			throw ExpressionEvaluationException.wrapIfNeeded(e)
					.addExpression(this);
		}
	}

	@Override
	public IStringExpression resolvePartial(final Evaluatee ctx) throws ExpressionEvaluationException
	{
		try
		{
			final String value = StringExpressionsHelper.evaluateParam(parameter, ctx, OnVariableNotFound.ReturnNoResult);
			if (value == null || value == EMPTY_RESULT)
			{
				return this;
			}

			return ConstantStringExpression.of(value);
		}
		catch (final Exception e)
		{
			throw ExpressionEvaluationException.wrapIfNeeded(e)
					.addExpression(this);
		}
	}
}
