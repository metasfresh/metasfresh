package org.adempiere.ad.expression.api.impl;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.Evaluatee;

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
 * Expression which evaluates to {@link SystemTime#asDate()}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class SysDateDateExpression implements IExpression<java.util.Date>
{
	public static final transient SysDateDateExpression instance = new SysDateDateExpression();
	public static final transient Optional<IExpression<?>> optionalInstance = Optional.of(instance);

	public static final String EXPRESSION_STRING = "@SysDate@";

	private SysDateDateExpression()
	{
		super();
	}

	@Override
	public Class<Date> getValueClass()
	{
		return java.util.Date.class;
	}

	@Override
	public String getExpressionString()
	{
		return EXPRESSION_STRING;
	}

	@Override
	public String getFormatedExpressionString()
	{
		return EXPRESSION_STRING;
	}

	@Override
	public Set<String> getParameters()
	{
		return ImmutableSet.of();
	}

	@Override
	public Date evaluate(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException
	{
		return SystemTime.asDate();
	}

	@Override
	public boolean isNoResult(final Object result)
	{
		return result == null;
	}

	@Override
	public boolean isNullExpression()
	{
		return false;
	}
}
