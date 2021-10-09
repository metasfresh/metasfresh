package de.metas.ui.web.window.descriptor.factory.standard;

import java.math.BigDecimal;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.BigDecimalStringExpressionSupport.BigDecimalStringExpression;
import org.adempiere.ad.expression.api.impl.BooleanStringExpressionSupport.BooleanStringExpression;
import org.adempiere.ad.expression.api.impl.DateStringExpressionSupport.DateStringExpression;
import org.adempiere.ad.expression.api.impl.IntegerStringExpressionSupport.IntegerStringExpression;
import org.adempiere.ad.expression.api.impl.SysDateDateExpression;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.AutoSequenceDefaultValueExpression;
import de.metas.ui.web.window.descriptor.sql.SqlDefaultValueExpression;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class DefaultValueExpressionsFactory
{
	/** @return new default instance */
	public static final DefaultValueExpressionsFactory newInstance()
	{
		final String tableName = null; // N/A
		final boolean isDetailTab = false;
		return new DefaultValueExpressionsFactory(tableName, isDetailTab);
	}

	public static final DefaultValueExpressionsFactory newInstance(@NonNull final String tableName, final boolean isDetailTab)
	{
		return new DefaultValueExpressionsFactory(tableName, isDetailTab);
	}

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
	private static final Optional<IExpression<?>> DEFAULT_VALUE_AD_Client_ID;
	private static final Optional<IExpression<?>> DEFAULT_VALUE_AD_Org_ID;
	private static final Optional<IExpression<?>> DEFAULT_VALUE_ContextDate;
	private static final Optional<IExpression<?>> DEFAULT_VALUE_ContextUser_ID;

	static
	{
		final IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);

		DEFAULT_VALUE_EXPRESSION_Yes = Optional.of(expressionFactory.compile(DisplayType.toBooleanString(true), BooleanStringExpression.class));
		DEFAULT_VALUE_EXPRESSION_No = Optional.of(expressionFactory.compile(DisplayType.toBooleanString(false), BooleanStringExpression.class));
		DEFAULT_VALUE_EXPRESSION_Zero_BigDecimal = Optional.of(expressionFactory.compile("0", BigDecimalStringExpression.class));
		DEFAULT_VALUE_EXPRESSION_Zero_Integer = Optional.of(expressionFactory.compile("0", IntegerStringExpression.class));
		DEFAULT_VALUE_EXPRESSION_M_AttributeSetInstance_ID = Optional.of(expressionFactory.compile(String.valueOf(AttributeConstants.M_AttributeSetInstance_ID_None), IntegerStringExpression.class));
		DEFAULT_VALUE_EXPRESSION_NextLineNo = Optional.of(expressionFactory.compile("@" + WindowConstants.CONTEXTVAR_NextLineNo + "@", IntegerStringExpression.class));

		DEFAULT_VALUE_AD_Client_ID = Optional.of(expressionFactory.compile("@" + WindowConstants.FIELDNAME_AD_Client_ID + "@", IntegerStringExpression.class));
		DEFAULT_VALUE_AD_Org_ID = Optional.of(expressionFactory.compile("@" + WindowConstants.FIELDNAME_AD_Org_ID + "@", IntegerStringExpression.class));
		DEFAULT_VALUE_ContextUser_ID = Optional.of(expressionFactory.compile("@#AD_User_ID@", IntegerStringExpression.class));
		DEFAULT_VALUE_ContextDate = Optional.of(expressionFactory.compile("@#Date@", DateStringExpression.class));
	}

	//
	// Parameters
	@Nullable
	private final String _tableName;
	private final boolean _isDetailTab;

	private DefaultValueExpressionsFactory(final String tableName, final boolean isDetailTab)
	{
		_tableName = tableName;
		_isDetailTab = isDetailTab;
	}

	@Nullable
	private String getTableName()
	{
		return _tableName;
	}

	private boolean isDetailTab()
	{
		return _isDetailTab;
	}

	public Optional<IExpression<?>> extractDefaultValueExpression(
			final String defaultValueStr,
			final String columnName,
			final DocumentFieldWidgetType widgetType,
			final Class<?> fieldValueClass,
			final boolean isMandatory,
			final boolean allowUsingAutoSequence)
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
		if (Check.isEmpty(defaultValueStr))
		{
			if (WindowConstants.FIELDNAME_AD_Client_ID.equals(columnName))
			{
				return DEFAULT_VALUE_AD_Client_ID;
			}
			else if (WindowConstants.FIELDNAME_AD_Org_ID.equals(columnName))
			{
				return DEFAULT_VALUE_AD_Org_ID;
			}
			else if (WindowConstants.FIELDNAME_Created.equals(columnName)
					|| WindowConstants.FIELDNAME_Updated.equals(columnName))
			{
				return DEFAULT_VALUE_ContextDate;
			}
			else if (WindowConstants.FIELDNAME_CreatedBy.equals(columnName)
					|| WindowConstants.FIELDNAME_UpdatedBy.equals(columnName))
			{
				return DEFAULT_VALUE_ContextUser_ID;
			}
			//
			else if (WindowConstants.FIELDNAME_Value.equals(columnName)
					&& getTableName() != null
					&& allowUsingAutoSequence)
			{
				return Optional.of(AutoSequenceDefaultValueExpression.of(getTableName()));
			}
			else if (Boolean.class.equals(fieldValueClass))
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
				if (isMandatory)
				{
					// e.g. C_OrderLine.QtyReserved
					return DEFAULT_VALUE_EXPRESSION_Zero_BigDecimal;
				}
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
		else if (TimeUtil.isDateOrTimeClass(fieldValueClass))
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
