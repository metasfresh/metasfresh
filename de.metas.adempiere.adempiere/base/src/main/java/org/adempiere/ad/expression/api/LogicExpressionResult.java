package org.adempiere.ad.expression.api;

import java.util.Map;
import java.util.Objects;

import javax.annotation.concurrent.Immutable;

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
@Immutable
public class LogicExpressionResult
{
	public static final LogicExpressionResult of(final Boolean value, final ILogicExpression expression, final Map<CtxName, String> parameters)
	{
		final String name = null;
		return new LogicExpressionResult(name, value, expression, parameters);
	}

	public static final LogicExpressionResult ofConstantExpression(final ILogicExpression constantExpression)
	{
		final boolean value = constantExpression.constantValue();
		return value ? TRUE : FALSE;
	}

	public static final LogicExpressionResult namedConstant(final String name, final boolean value)
	{
		return new NamedConstant(name, value);
	}

	public static final LogicExpressionResult FALSE = new Constant(false);
	public static final LogicExpressionResult TRUE = new Constant(true);

	protected final String name;
	protected final boolean value;
	private final ILogicExpression expression;
	private final Map<CtxName, String> usedParameters;

	private transient String _toString = null; // lazy

	private LogicExpressionResult(final String name, final Boolean value, final ILogicExpression expression, final Map<CtxName, String> usedParameters)
	{
		super();
		this.name = name;
		this.value = value == null ? false : value;
		this.expression = expression;
		this.usedParameters = usedParameters == null || usedParameters.isEmpty() ? null : usedParameters;
	}

	@Override
	public String toString()
	{
		if (_toString == null)
		{
			_toString = MoreObjects.toStringHelper(value ? "TRUE" : "FALSE")
					.omitNullValues()
					.add("name", name)
					.add("expression", expression)
					.add("usedParameters", usedParameters)
					.toString();
		}
		return _toString;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(name, value, expression, usedParameters);
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

		if (!LogicExpressionResult.class.isAssignableFrom(obj.getClass()))
		{
			return false;
		}

		final LogicExpressionResult other = (LogicExpressionResult)obj;
		return Objects.equals(name, other.name)
				&& Objects.equals(value, other.value)
				&& Objects.equals(expression, other.expression)
				&& Objects.equals(usedParameters, other.usedParameters);
	}

	/**
	 * @param obj
	 * @return true if the value of this result equals with the value of given result
	 * @see #booleanValue()
	 */
	public boolean equalsByValue(final LogicExpressionResult obj)
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

	public boolean equalsByNameAndValue(final LogicExpressionResult obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		return Objects.equals(name, obj.name)
				&& value == obj.value;
	}

	public boolean booleanValue()
	{
		return value;
	}
	
	public boolean isTrue()
	{
		return value;
	}

	public boolean isFalse()
	{
		return !value;
	}

	/**
	 * @return which parameters were used while evaluating and which was their value
	 */
	public Map<CtxName, String> getUsedParameters()
	{
		return usedParameters == null ? ImmutableMap.of() : usedParameters;
	}

	private static final class Constant extends LogicExpressionResult
	{
		private Constant(final boolean value)
		{
			super(null, value, value ? ConstantLogicExpression.TRUE : ConstantLogicExpression.FALSE, null);
		}

		@Override
		public String toString()
		{
			return value ? "TRUE" : "FALSE";
		}
	}

	private static final class NamedConstant extends LogicExpressionResult
	{
		private transient String _toString = null; // lazy

		private NamedConstant(final String name, final boolean value)
		{
			super(name, value, value ? ConstantLogicExpression.TRUE : ConstantLogicExpression.FALSE, null);
		}

		@Override
		public String toString()
		{
			if (_toString == null)
			{
				_toString = MoreObjects.toStringHelper(value ? "TRUE" : "FALSE")
						.omitNullValues()
						.addValue(name)
						.toString();
			}
			return _toString;
		}
	}

}
