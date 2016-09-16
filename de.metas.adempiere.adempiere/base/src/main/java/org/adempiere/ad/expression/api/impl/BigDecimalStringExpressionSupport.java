package org.adempiere.ad.expression.api.impl;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.expression.api.ExpressionContext;
import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.impl.BigDecimalStringExpressionSupport.BigDecimalStringExpression;
import org.compiere.util.CtxName;
import org.compiere.util.Evaluatee;

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

public final class BigDecimalStringExpressionSupport extends StringExpressionSupportTemplate<BigDecimal, BigDecimalStringExpression>
{
	public static interface BigDecimalStringExpression extends IExpression<BigDecimal>
	{
	}

	public static final transient BigDecimalStringExpressionSupport instance = new BigDecimalStringExpressionSupport();

	private BigDecimalStringExpressionSupport()
	{
		super(new Compiler<BigDecimal, BigDecimalStringExpression>(BigDecimal.class, new BigDecimalValueConverter())
		{
			private final NullExpression nullExpression = new NullExpression(this);

			private final ConstantExpression CONSTANT_ZERO = new ConstantExpression(this, "0", BigDecimal.ZERO);
			private final ConstantExpression CONSTANT_ONE = new ConstantExpression(this, "1", BigDecimal.ONE);

			@Override
			protected BigDecimalStringExpression getNullExpression()
			{
				return nullExpression;
			}

			@Override
			protected BigDecimalStringExpression createConstant(final ExpressionContext context, final String expressionStr, final BigDecimal constantValue)
			{
				if (constantValue == null)
				{
					return nullExpression; // shall not happen
				}
				else if (constantValue.signum() == 0)
				{
					return CONSTANT_ZERO;
				}
				else if (constantValue.compareTo(BigDecimal.ONE) == 0)
				{
					return CONSTANT_ONE;
				}
				return new ConstantExpression(this, expressionStr, constantValue);
			}

			@Override
			protected BigDecimalStringExpression createSingleParamaterExpression(final ExpressionContext context, final String expressionStr, final CtxName parameter)
			{
				return new SingleParameterExpression(context, this, expressionStr, parameter);
			}

			@Override
			protected BigDecimalStringExpression createGeneralExpression(final ExpressionContext context, final String expressionStr, final List<Object> expressionChunks)
			{
				return new GeneralExpression(context, this, expressionStr, expressionChunks);
			}
		});
	}

	private static final class BigDecimalValueConverter implements ValueConverter<BigDecimal, BigDecimalStringExpression>
	{

		@Override
		public BigDecimal convertFrom(final Object valueObj, final ExpressionContext options)
		{
			if (valueObj == null)
			{
				return null;
			}
			else if (valueObj instanceof BigDecimal)
			{
				return (BigDecimal)valueObj;
			}
			else if (valueObj instanceof Integer)
			{
				return BigDecimal.valueOf((Integer)valueObj);
			}
			else
			{
				String valueStr = valueObj.toString();
				if (valueStr == null)
				{
					return null; // shall not happen
				}

				valueStr = valueStr.trim();
				return new BigDecimal(valueStr);
			}
		}
	}

	private static final class NullExpression extends NullExpressionTemplate<BigDecimal, BigDecimalStringExpression>implements BigDecimalStringExpression
	{
		public NullExpression(final Compiler<BigDecimal, BigDecimalStringExpression> compiler)
		{
			super(compiler);
		}
	}

	private static final class ConstantExpression extends ConstantExpressionTemplate<BigDecimal, BigDecimalStringExpression>implements BigDecimalStringExpression
	{
		public ConstantExpression(final Compiler<BigDecimal, BigDecimalStringExpression> compiler, final String expressionStr, final BigDecimal constantValue)
		{
			super(ExpressionContext.EMPTY, compiler, expressionStr, constantValue);
		}

	}

	private static final class SingleParameterExpression extends SingleParameterExpressionTemplate<BigDecimal, BigDecimalStringExpression>implements BigDecimalStringExpression
	{
		public SingleParameterExpression(final ExpressionContext context, final Compiler<BigDecimal, BigDecimalStringExpression> compiler, final String expressionStr, final CtxName parameter)
		{
			super(context, compiler, expressionStr, parameter);
		}

		@Override
		protected Object extractParameterValue(final Evaluatee ctx)
		{
			return parameter.getValueAsBigDecimal(ctx);
		}
	}

	private static final class GeneralExpression extends GeneralExpressionTemplate<BigDecimal, BigDecimalStringExpression>implements BigDecimalStringExpression
	{

		public GeneralExpression(final ExpressionContext context, final Compiler<BigDecimal, BigDecimalStringExpression> compiler, final String expressionStr, final List<Object> expressionChunks)
		{
			super(context, compiler, expressionStr, expressionChunks);
		}

	}
}
