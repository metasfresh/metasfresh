package org.adempiere.ad.expression.api.impl;

import java.util.List;

import org.adempiere.ad.expression.api.ExpressionContext;
import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.impl.BooleanStringExpressionSupport.BooleanStringExpression;
import org.compiere.util.CtxName;
import org.compiere.util.DisplayType;
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

public class BooleanStringExpressionSupport extends StringExpressionSupportTemplate<Boolean, BooleanStringExpression>
{
	public static interface BooleanStringExpression extends IExpression<Boolean>
	{
	}

	public static final transient BooleanStringExpressionSupport instance = new BooleanStringExpressionSupport();

	private BooleanStringExpressionSupport()
	{
		super(new Compiler<Boolean, BooleanStringExpression>(Boolean.class, BooleanValueConverter.instance)
		{
			private final NullExpression nullExpression = new NullExpression(this);

			private final BooleanStringExpression TRUE = new ConstantExpression(this, "Y", Boolean.TRUE);
			private final BooleanStringExpression FALSE = new ConstantExpression(this, "N", Boolean.FALSE);

			@Override
			protected BooleanStringExpression getNullExpression()
			{
				return nullExpression;
			}

			@Override
			protected BooleanStringExpression createConstant(final ExpressionContext context, final String expressionStr, final Boolean constantValue)
			{
				if (constantValue == null)
				{
					// shall not happen
					return nullExpression;
				}

				return constantValue ? TRUE : FALSE;
			}

			@Override
			protected BooleanStringExpression createSingleParamaterExpression(final ExpressionContext context, final String expressionStr, final CtxName parameter)
			{
				return new SingleParameterExpression(context, this, expressionStr, parameter);
			}

			@Override
			protected BooleanStringExpression createGeneralExpression(final ExpressionContext context, final String expressionStr, final List<Object> expressionChunks)
			{
				return new GeneralExpression(context, this, expressionStr, expressionChunks);
			}
		});
	}

	private static final class BooleanValueConverter implements ValueConverter<Boolean, BooleanStringExpression>
	{
		public static final transient BooleanStringExpressionSupport.BooleanValueConverter instance = new BooleanStringExpressionSupport.BooleanValueConverter();

		private BooleanValueConverter()
		{
			super();
		}

		@Override
		public Boolean convertFrom(final Object valueObj, final ExpressionContext options)
		{
			if (valueObj == null)
			{
				return null;
			}
			final Boolean defaultValue = null;
			return DisplayType.toBoolean(valueObj, defaultValue);
		}
	}

	private static final class NullExpression extends NullExpressionTemplate<Boolean, BooleanStringExpression>implements BooleanStringExpression
	{
		public NullExpression(final Compiler<Boolean, BooleanStringExpression> compiler)
		{
			super(compiler);
		}
	}

	private static final class ConstantExpression extends ConstantExpressionTemplate<Boolean, BooleanStringExpression>implements BooleanStringExpression
	{
		private ConstantExpression(final Compiler<Boolean, BooleanStringExpression> compiler, final String expressionStr, final Boolean constantValue)
		{
			super(ExpressionContext.EMPTY, compiler, expressionStr, constantValue);
		}

	}

	private static final class SingleParameterExpression extends SingleParameterExpressionTemplate<Boolean, BooleanStringExpression>implements BooleanStringExpression
	{
		private SingleParameterExpression(final ExpressionContext context, final Compiler<Boolean, BooleanStringExpression> compiler, final String expressionStr, final CtxName parameter)
		{
			super(context, compiler, expressionStr, parameter);
		}

		@Override
		protected Boolean extractParameterValue(Evaluatee ctx)
		{
			return parameter.getValueAsBoolean(ctx);
		}
	}

	private static final class GeneralExpression extends GeneralExpressionTemplate<Boolean, BooleanStringExpression>implements BooleanStringExpression
	{

		private GeneralExpression(final ExpressionContext context, final Compiler<Boolean, BooleanStringExpression> compiler, final String expressionStr, final List<Object> expressionChunks)
		{
			super(context, compiler, expressionStr, expressionChunks);
		}

	}
}
