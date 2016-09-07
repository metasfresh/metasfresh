package org.adempiere.ad.expression.api;

import java.util.Map;

import org.compiere.util.CtxName;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

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
 * The result of a {@link ILogicExpression} evaluation.
 *
 * Mainly it consists of:
 * <ul>
 * <li>evaluation result: {@link #booleanValue()}
 * <li>which parameters were used while evaluating and which was their value: {@link #getUsedParameters()}.
 * <li>{@link #toString()}: evaluation result, used parameters, expression that was evaluated
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class LogicExpressionResult
{
	public static final LogicExpressionResult of(final Boolean value, final ILogicExpression expression, final Map<CtxName, String> parameters)
	{
		return new LogicExpressionResult(value, expression, parameters);
	}

	public static final LogicExpressionResult FALSE = new LogicExpressionResult(false, ConstantLogicExpression.FALSE, null);
	public static final LogicExpressionResult TRUE = new LogicExpressionResult(true, ConstantLogicExpression.TRUE, null);

	private final boolean value;
	private final ILogicExpression expression;
	private final Map<CtxName, String> usedParameters;

	private transient String _toString = null; // lazy

	private LogicExpressionResult(final Boolean value, final ILogicExpression expression, final Map<CtxName, String> usedParameters)
	{
		super();
		this.value = value == null ? false : value;
		this.expression = expression;
		this.usedParameters = usedParameters;
	}

	@Override
	public String toString()
	{
		if (_toString == null)
		{
			_toString = MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("value", value)
					.add("expression", expression)
					.add("usedParameters", usedParameters)
					.toString();
		}
		return _toString;
	}

	/**
	 * @param obj
	 * @return true if the value of this result equals with the value of given result
	 * @see #booleanValue()
	 */
	public boolean valueEquals(final LogicExpressionResult obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		return value == obj.value;
	}

	public boolean booleanValue()
	{
		return value;
	}

	/**
	 * @return which parameters were used while evaluating and which was their value
	 */
	public Map<CtxName, String> getUsedParameters()
	{
		return usedParameters == null ? ImmutableMap.of() : usedParameters;
	}
}
