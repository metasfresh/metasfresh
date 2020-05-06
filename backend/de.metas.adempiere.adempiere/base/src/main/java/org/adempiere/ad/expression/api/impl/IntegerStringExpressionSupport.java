package org.adempiere.ad.expression.api.impl;

import java.util.List;

import org.adempiere.ad.expression.api.ExpressionContext;
import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.impl.IntegerStringExpressionSupport.IntegerStringExpression;
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

public final class IntegerStringExpressionSupport extends StringExpressionSupportTemplate<Integer, IntegerStringExpression>
{
	public static interface IntegerStringExpression extends IExpression<Integer>
	{
	}

	public static final transient IntegerStringExpressionSupport instance = new IntegerStringExpressionSupport();

	private IntegerStringExpressionSupport()
	{
		super(new Compiler<Integer, IntegerStringExpression>(Integer.class, new IntegerValueConverter())
		{
			private final NullExpression nullExpression = new NullExpression(this);
			private final ConstantExpression CONSTANT_ZERO = new ConstantExpression(this, "0", 0);
			private final ConstantExpression CONSTANT_MINUS_ONE = new ConstantExpression(this, "-1", -1);

			@Override
			protected IntegerStringExpression getNullExpression()
			{
				return nullExpression;
			}

			@Override
			protected IntegerStringExpression createConstant(final ExpressionContext context, final String expressionStr, final Integer constantValue)
			{
				if (constantValue == null)
				{
					// shall not happen
					return nullExpression;
				}
				else if (constantValue == 0)
				{
					return CONSTANT_ZERO;
				}
				else if (constantValue == -1)
				{
					return CONSTANT_MINUS_ONE;
				}
				
				return new ConstantExpression(this, expressionStr, constantValue);
			}

			@Override
			protected IntegerStringExpression createSingleParamaterExpression(final ExpressionContext context, final String expressionStr, final CtxName parameter)
			{
				return new SingleParameterExpression(context, this, expressionStr, parameter);
			}

			@Override
			protected IntegerStringExpression createGeneralExpression(final ExpressionContext context, final String expressionStr, final List<Object> expressionChunks)
			{
				return new GeneralExpression(context, this, expressionStr, expressionChunks);
			}
		});
	}

	private static final class IntegerValueConverter implements ValueConverter<Integer, IntegerStringExpression>
	{

		@Override
		public Integer convertFrom(final Object valueObj, final ExpressionContext context)
		{
			if (valueObj == null)
			{
				return null;
			}
			else if (valueObj instanceof Integer)
			{
				return (Integer)valueObj;
			}
			else
			{
				String valueStr = valueObj.toString();
				if (valueStr == null)
				{
					return null; // shall not happen
				}

				valueStr = valueStr.trim();
				return new Integer(valueStr);
			}
		}
	}

	private static final class NullExpression extends NullExpressionTemplate<Integer, IntegerStringExpression>implements IntegerStringExpression
	{
		public NullExpression(final Compiler<Integer, IntegerStringExpression> compiler)
		{
			super(compiler);
		}
	}

	private static final class ConstantExpression extends ConstantExpressionTemplate<Integer, IntegerStringExpression>implements IntegerStringExpression
	{
		public ConstantExpression(final Compiler<Integer, IntegerStringExpression> compiler, final String expressionStr, final Integer constantValue)
		{
			super(ExpressionContext.EMPTY, compiler, expressionStr, constantValue);
		}

	}

	private static final class SingleParameterExpression extends SingleParameterExpressionTemplate<Integer, IntegerStringExpression>implements IntegerStringExpression
	{
		public SingleParameterExpression(final ExpressionContext context, final Compiler<Integer, IntegerStringExpression> compiler, final String expressionStr, final CtxName parameter)
		{
			super(context, compiler, expressionStr, parameter);
		}

		@Override
		protected Integer extractParameterValue(final Evaluatee ctx)
		{
			return parameter.getValueAsInteger(ctx);
		}
	}

	private static final class GeneralExpression extends GeneralExpressionTemplate<Integer, IntegerStringExpression>implements IntegerStringExpression
	{

		public GeneralExpression(final ExpressionContext context, final Compiler<Integer, IntegerStringExpression> compiler, final String expressionStr, final List<Object> expressionChunks)
		{
			super(context, compiler, expressionStr, expressionChunks);
		}

	}
}
