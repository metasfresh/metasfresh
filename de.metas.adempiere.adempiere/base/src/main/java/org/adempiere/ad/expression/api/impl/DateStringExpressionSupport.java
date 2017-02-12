package org.adempiere.ad.expression.api.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.adempiere.ad.expression.api.ExpressionContext;
import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.impl.DateStringExpressionSupport.DateStringExpression;
import org.compiere.util.CtxName;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

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

public class DateStringExpressionSupport extends StringExpressionSupportTemplate<java.util.Date, DateStringExpression>
{
	public static interface DateStringExpression extends IExpression<Date>
	{
		String PARAM_DateFormat = DateFormat.class.getName();
	}

	public static final transient DateStringExpressionSupport instance = new DateStringExpressionSupport();

	private DateStringExpressionSupport()
	{
		super(new Compiler<Date, DateStringExpression>(Date.class, new DateValueConverter())
		{
			private final NullExpression nullExpression = new NullExpression(this);

			@Override
			protected DateStringExpression getNullExpression()
			{
				return nullExpression;
			}

			@Override
			protected DateStringExpression createConstant(final ExpressionContext context, final String expressionStr, final Date constantValue)
			{
				return new ConstantExpression(context, this, expressionStr, constantValue);
			}

			@Override
			protected DateStringExpression createSingleParamaterExpression(final ExpressionContext context, final String expressionStr, final CtxName parameter)
			{
				return new SingleParameterExpression(context, this, expressionStr, parameter);
			}

			@Override
			protected DateStringExpression createGeneralExpression(final ExpressionContext context, final String expressionStr, final List<Object> expressionChunks)
			{
				return new GeneralExpression(context, this, expressionStr, expressionChunks);
			}
		});
	}

	private static final class DateValueConverter implements ValueConverter<java.util.Date, DateStringExpression>
	{
		private static final Logger logger = LogManager.getLogger(DateValueConverter.class);

		@Override
		public Date convertFrom(final Object valueObj, final ExpressionContext options)
		{
			if (valueObj == null)
			{
				return null;
			}
			else if (valueObj instanceof Date)
			{
				return (Date)valueObj;
			}

			String valueStr = valueObj.toString();
			// JSONVa
			if (valueStr == null)
			{
				return null; // shall not happen
			}

			valueStr = valueStr.trim();

			//
			// Use the given date format
			try
			{
				final DateFormat dateFormat = (DateFormat)options.get(DateStringExpression.PARAM_DateFormat);
				if (dateFormat != null)
				{
					return dateFormat.parse(valueStr);
				}
			}
			catch (final ParseException ex1)
			{
				// second try
				// FIXME: this is not optimum. We shall unify how we store Dates (as String)
				logger.warn("Using Env.parseTimestamp to convert '{}' to Date", valueStr, ex1);
			}

			//
			// Fallback
			try
			{
				final Timestamp ts = Env.parseTimestamp(valueStr);
				if (ts == null)
				{
					return null;
				}

				return new java.util.Date(ts.getTime());
			}
			catch (final Exception ex2)
			{
				final IllegalArgumentException exFinal = new IllegalArgumentException("Failed converting '" + valueStr + "' to date", ex2);
				throw exFinal;
			}

		}
	}

	private static final class NullExpression extends NullExpressionTemplate<Date, DateStringExpression>implements DateStringExpression
	{
		public NullExpression(final Compiler<Date, DateStringExpression> compiler)
		{
			super(compiler);
		}
	}

	private static final class ConstantExpression extends ConstantExpressionTemplate<Date, DateStringExpression>implements DateStringExpression
	{
		public ConstantExpression(final ExpressionContext context, final Compiler<Date, DateStringExpression> compiler, final String expressionStr, final Date constantValue)
		{
			super(context, compiler, expressionStr, constantValue);
		}

	}

	private static final class SingleParameterExpression extends SingleParameterExpressionTemplate<Date, DateStringExpression>implements DateStringExpression
	{
		public SingleParameterExpression(final ExpressionContext context, final Compiler<Date, DateStringExpression> compiler, final String expressionStr, final CtxName parameter)
		{
			super(context, compiler, expressionStr, parameter);
		}

		@Override
		protected Object extractParameterValue(final Evaluatee ctx)
		{
			return parameter.getValueAsDate(ctx);
		}
	}

	private static final class GeneralExpression extends GeneralExpressionTemplate<Date, DateStringExpression>implements DateStringExpression
	{

		public GeneralExpression(final ExpressionContext context, final Compiler<Date, DateStringExpression> compiler, final String expressionStr, final List<Object> expressionChunks)
		{
			super(context, compiler, expressionStr, expressionChunks);
		}

	}
}
