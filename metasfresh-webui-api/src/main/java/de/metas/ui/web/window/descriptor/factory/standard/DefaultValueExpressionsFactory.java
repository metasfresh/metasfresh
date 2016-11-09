package de.metas.ui.web.window.descriptor.factory.standard;

import java.math.BigDecimal;
import java.util.Optional;

import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.BigDecimalStringExpressionSupport.BigDecimalStringExpression;
import org.adempiere.ad.expression.api.impl.BooleanStringExpressionSupport.BooleanStringExpression;
import org.adempiere.ad.expression.api.impl.DateStringExpressionSupport.DateStringExpression;
import org.adempiere.ad.expression.api.impl.IntegerStringExpressionSupport.IntegerStringExpression;
import org.adempiere.ad.expression.api.impl.SysDateDateExpression;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.util.Services;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.SqlDefaultValueExpression;

/*
 * #%L
 * metasfresh-webui-api
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

public class DefaultValueExpressionsFactory
{
	// services
	private static final Logger logger = LogManager.getLogger(DefaultValueExpressionsFactory.class);
	private final transient IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);

	private static final String HARDCODED_DEFAUL_EXPRESSION_STRING_NULL = "@NULL@";
	private static final Optional<IExpression<?>> DEFAULT_VALUE_EXPRESSION_Yes;
	private static final Optional<IExpression<?>> DEFAULT_VALUE_EXPRESSION_No;
	private static final Optional<IExpression<?>> DEFAULT_VALUE_EXPRESSION_Zero_BigDecimal;
	private static final Optional<IExpression<?>> DEFAULT_VALUE_EXPRESSION_Zero_Integer;
	private static final Optional<IExpression<?>> DEFAULT_VALUE_EXPRESSION_M_AttributeSetInstance_ID;
	private static final Optional<IExpression<?>> DEFAULT_VALUE_EXPRESSION_NextLineNo;

	static
	{
		final IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);

		DEFAULT_VALUE_EXPRESSION_Yes = Optional.of(expressionFactory.compile(DisplayType.toBooleanString(true), BooleanStringExpression.class));
		DEFAULT_VALUE_EXPRESSION_No = Optional.of(expressionFactory.compile(DisplayType.toBooleanString(false), BooleanStringExpression.class));
		DEFAULT_VALUE_EXPRESSION_Zero_BigDecimal = Optional.of(expressionFactory.compile("0", BigDecimalStringExpression.class));
		DEFAULT_VALUE_EXPRESSION_Zero_Integer = Optional.of(expressionFactory.compile("0", IntegerStringExpression.class));
		DEFAULT_VALUE_EXPRESSION_M_AttributeSetInstance_ID = Optional.of(expressionFactory.compile(String.valueOf(IAttributeDAO.M_AttributeSetInstance_ID_None), IntegerStringExpression.class));
		DEFAULT_VALUE_EXPRESSION_NextLineNo = Optional.of(expressionFactory.compile("@" + WindowConstants.CONTEXTVAR_NextLineNo + "@", IntegerStringExpression.class));
	}

	//
	// Parameters
	private final boolean _isDetailTab;
	
	public DefaultValueExpressionsFactory(final boolean isDetailTab)
	{
		super();
		this._isDetailTab = isDetailTab;
	}
	
	private boolean isDetailTab()
	{
		return _isDetailTab;
	}

	public Optional<IExpression<?>> extractDefaultValueExpression(
			final String defaultValueStr //
			, final String columnName //
			, final DocumentFieldWidgetType widgetType //
			, final Class<?> fieldValueClass //
			, final boolean isMandatory //
			)
	{
		final boolean isDetailTab = isDetailTab();
		
		//
		// Case: "Line" field in included tabs
		if (WindowConstants.FIELDNAME_Line.equals(columnName)
				&& isDetailTab // only on included tabs
		)
		{
			return DEFAULT_VALUE_EXPRESSION_NextLineNo;
		}

		//
		// If there is no default value expression, use some defaults
		if (defaultValueStr == null || defaultValueStr.isEmpty())
		{
			if (Boolean.class.equals(fieldValueClass))
			{
				if (WindowConstants.FIELDNAME_IsActive.equals(columnName))
				{
					return DEFAULT_VALUE_EXPRESSION_Yes;
				}
				else
				{
					return DEFAULT_VALUE_EXPRESSION_No;
				}
			}
			else if (Integer.class.equals(fieldValueClass))
			{
				if (isMandatory)
				{
					return DEFAULT_VALUE_EXPRESSION_Zero_Integer;
				}
			}
			else if (BigDecimal.class.equals(fieldValueClass))
			{
				// e.g. C_OrderLine.QtyReserved
				return DEFAULT_VALUE_EXPRESSION_Zero_BigDecimal;
			}
			else if (widgetType == DocumentFieldWidgetType.ProductAttributes)
			{
				return DEFAULT_VALUE_EXPRESSION_M_AttributeSetInstance_ID;
			}

			return Optional.empty();
		}
		// Explicit NULL
		else if ("null".equalsIgnoreCase(defaultValueStr.trim()))
		{
			return Optional.of(IStringExpression.NULL);
		}
		// If it's a SQL expression => compile it as SQL expression
		else if (defaultValueStr.startsWith("@SQL="))
		{
			final String sqlTemplate = defaultValueStr.substring(5).trim();
			final IStringExpression sqlTemplateStringExpression = expressionFactory.compile(sqlTemplate, IStringExpression.class);
			return Optional.of(SqlDefaultValueExpression.of(sqlTemplateStringExpression, fieldValueClass));
		}
		// Regular default value expression
		else
		{
			return buildExpression(expressionFactory, defaultValueStr, fieldValueClass);
		}
	}

	private static Optional<IExpression<?>> buildExpression(final IExpressionFactory expressionFactory, final String expressionStr, final Class<?> fieldValueClass)
	{
		// Hardcoded: NULL
		if (HARDCODED_DEFAUL_EXPRESSION_STRING_NULL.equalsIgnoreCase(expressionStr))
		{
			return Optional.empty();
		}

		// Hardcoded: SysDate
		if (SysDateDateExpression.EXPRESSION_STRING.equals(expressionStr))
		{
			return SysDateDateExpression.optionalInstance;
		}

		final IExpression<?> expression;
		if (Integer.class.equals(fieldValueClass))
		{
			expression = expressionFactory.compile(expressionStr, IntegerStringExpression.class);
		}
		else if (BigDecimal.class.equals(fieldValueClass))
		{
			expression = expressionFactory.compile(expressionStr, BigDecimalStringExpression.class);
		}
		else if (IntegerLookupValue.class.equals(fieldValueClass))
		{
			expression = expressionFactory.compile(expressionStr, IntegerStringExpression.class);
		}
		else if (StringLookupValue.class.equals(fieldValueClass))
		{
			final String expressionStrNorm = stripDefaultValueQuotes(expressionStr);
			expression = expressionFactory.compile(expressionStrNorm, IStringExpression.class);
		}
		else if (java.util.Date.class.equals(fieldValueClass))
		{
			expression = expressionFactory.compile(expressionStr, DateStringExpression.class);
		}
		else if (Boolean.class.equals(fieldValueClass))
		{
			final String expressionStrNorm = stripDefaultValueQuotes(expressionStr);
			expression = expressionFactory.compile(expressionStrNorm, BooleanStringExpression.class);
		}
		//
		// Fallback
		else
		{
			expression = expressionFactory.compile(expressionStr, IStringExpression.class);
		}

		if (expression.isNullExpression())
		{
			return Optional.empty();
		}
		return Optional.of(expression);
	}

	/**
	 * Strips default value expressions which are quoted strings.
	 * e.g.
	 * <ul>
	 * <li>we have some cases where a YesNo default value is 'N' or 'Y'
	 * <li>we have some cases where a List default value is something like 'P'
	 * <li>we have some cases where a Table's reference default value is something like 'de.metas.swat'
	 * </ul>
	 * 
	 * @param expressionStr
	 * @return fixed expression or same expression if does not apply
	 */
	private static final String stripDefaultValueQuotes(final String expressionStr)
	{
		if (expressionStr == null || expressionStr.isEmpty())
		{
			return expressionStr;
		}

		if (expressionStr.startsWith("'") && expressionStr.endsWith("'"))
		{
			final String expressionStrNorm = expressionStr.substring(1, expressionStr.length() - 1);
			logger.warn("Normalized string expression: [{}] -> [{}]", expressionStr, expressionStrNorm);
		}

		return expressionStr;
	}
}
