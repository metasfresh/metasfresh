package de.metas.ui.web.window.model;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.json.DateTimeConverters;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import lombok.NonNull;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.CtxName;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee2;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

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

/* package */final class DocumentEvaluatee implements IDocumentEvaluatee, IDocumentAware
{
	private static final Logger logger = LogManager.getLogger(DocumentEvaluatee.class);

	private final Document _document;
	private final String _fieldNameInScope;
	private final ImmutableSet<String> _fieldNamesToExclude;


	/* package */ DocumentEvaluatee(@NotNull final Document document)
	{
		_document = document; // note: we assume it's not null
		_fieldNameInScope = null;
		_fieldNamesToExclude = ImmutableSet.of();
	}

	private DocumentEvaluatee(@NotNull final Document document, @Nullable final String fieldNameInScope, @NonNull final ImmutableSet<String> fieldNamesToExclude)
	{
		super();
		_document = document; // note: we assume it's not null
		_fieldNameInScope = fieldNameInScope; // null is also ok
		_fieldNamesToExclude = fieldNamesToExclude;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("fieldInScope", _fieldNameInScope)
				.addValue(_document)
				.toString();
	}

	@Override
	public IDocumentEvaluatee fieldInScope(final String fieldNameInScope)
	{
		if (isFieldInScope(fieldNameInScope))
		{
			return this;
		}

		return new DocumentEvaluatee(_document, fieldNameInScope, ImmutableSet.of());
	}

	private boolean isFieldInScope(final String fieldName)
	{
		return Objects.equals(_fieldNameInScope, fieldName);
	}
	
	@Override
	public IDocumentEvaluatee excludingFields(final String ... fieldNamesToExclude)
	{
		return new DocumentEvaluatee(_document, _fieldNameInScope, ImmutableSet.copyOf(fieldNamesToExclude));
	}
	
	private boolean isExcludedField(final String fieldName)
	{
		return _fieldNamesToExclude.contains(fieldName);
	}


	private Properties getCtx()
	{
		return _document.getCtx();
	}

	private IDocumentEvaluatee getParentEvaluateeOrNull()
	{
		return _document.getParentDocumentEvaluateeOrNull();
	}

	@Override
	public Document getDocument()
	{
		return _document;
	}

	private IDocumentFieldView getDocumentFieldOrNull(final String name)
	{
		return _document.getFieldViewOrNull(name);
	}

	@Override
	public String get_ValueAsString(final String variableName)
	{
		Object valueObj = get_ValueIfExists(variableName, String.class).orElse(null);
		if (valueObj == null)
		{
			valueObj = getDefaultValue(variableName).orElse(null);
		}

		return valueObj == null ? null : convertToString(variableName, valueObj);
	}

	@Override
	public Integer get_ValueAsInt(final String variableName, final Integer defaultValue)
	{
		Object valueObj = get_ValueIfExists(variableName, Integer.class).orElse(null);
		if (valueObj == null && defaultValue == null)
		{
			valueObj = getDefaultValue(variableName).orElse(null);
		}

		return valueObj == null ? defaultValue : convertToInteger(variableName, valueObj);
	}

	@Override
	public Boolean get_ValueAsBoolean(final String variableName, final Boolean defaultValue)
	{
		Object valueObj = get_ValueIfExists(variableName, Boolean.class).orElse(null);
		if (valueObj == null && defaultValue == null)
		{
			valueObj = getDefaultValue(variableName).orElse(null);
		}

		return DisplayType.toBoolean(valueObj, defaultValue);
	}

	@Override
	public java.util.Date get_ValueAsDate(final String variableName, final java.util.Date defaultValue)
	{
		Object valueObj = get_ValueIfExists(variableName, java.util.Date.class).orElse(null);
		if (valueObj == null && defaultValue == null)
		{
			valueObj = getDefaultValue(variableName).orElse(null);
		}

		return valueObj == null ? defaultValue : convertToDate(variableName, valueObj);
	}

	private Date convertToDate(final String variableName, final Object valueObj)
	{
		if (valueObj == null)
		{
			return null;
		}
		else if (valueObj instanceof Date)
		{
			return (Date)valueObj;
		}
		else
		{
			return TimeUtil.asDate(DateTimeConverters.fromObject(valueObj, DocumentFieldWidgetType.ZonedDateTime));
		}
	}

	@Override
	public BigDecimal get_ValueAsBigDecimal(final String variableName, final BigDecimal defaultValue)
	{
		Object valueObj = get_ValueIfExists(variableName, BigDecimal.class).orElse(null);
		if (valueObj == null && defaultValue == null)
		{
			valueObj = getDefaultValue(variableName).orElse(null);
		}

		return valueObj == null ? defaultValue : convertToBigDecimal(variableName, valueObj);
	}

	@Override
	public Optional<Object> get_ValueIfExists(final @NonNull String variableName, final @NonNull Class<?> targetType)
	{
		if (variableName == null)
		{
			return Optional.empty();
		}
		
		if(isExcludedField(variableName))
		{
			return Optional.empty();
		}

		if (WindowConstants.CONTEXTVAR_NextLineNo.equals(variableName))
		{
			final Document parentDocument = _document.getParentDocument();
			if (parentDocument != null)
			{
				final DetailId detailId = _document.getEntityDescriptor().getDetailId();
				final int nextLineNo = parentDocument.getIncludedDocumentsCollection(detailId).getNextLineNo();
				return Optional.of(nextLineNo);
			}
		}

		if (IValidationContext.PARAMETER_ContextTableName.equals(variableName))
		{
			return Optional.of(_document.getEntityDescriptor().getTableName());
		}

		//
		// Check global context variable if this is an explicit global variable
		boolean globalContextChecked = false;
		if (CtxName.isExplicitGlobal(variableName))
		{
			final Object value = getGlobalContext(variableName, targetType);
			globalContextChecked = true;
			if (value != null)
			{
				return Optional.of(value);
			}

		}

		//
		// Document field
		final IDocumentFieldView documentField = getDocumentFieldOrNull(variableName);
		boolean inScopeField = false;
		if (documentField != null)
		{
			inScopeField = isFieldInScope(documentField.getFieldName());
			if (inScopeField)
			{
				logger.trace("Skip evaluation of {} because it's actually the field for whom we do the whole evaluation", documentField);
			}
			else
			{
				final Object documentFieldValue = documentField.getValue();
				if (documentFieldValue != null)
				{
					return Optional.of(documentFieldValue);
				}
			}
		}

		//
		// Document's dynamic attribute
		if (_document.hasDynAttribute(variableName))
		{
			final Object value = _document.getDynAttribute(variableName);
			if (value != null)
			{
				return Optional.of(value);
			}
		}

		//
		// Check parent
		final IDocumentEvaluatee parentEvaluatee = getParentEvaluateeOrNull();
		final boolean hasParentEvaluatee = parentEvaluatee != null;
		if (hasParentEvaluatee)
		{
			final Optional<Object> value = parentEvaluatee.get_ValueIfExists(variableName, targetType);
			if (value.isPresent())
			{
				return value;
			}
		}

		//
		// If it's about the field in scope, try checking the global context
		if (inScopeField)
		{
			if (!globalContextChecked)
			{
				final Object value = getGlobalContext(variableName, targetType);
				globalContextChecked = true;
				if (value != null)
				{
					return Optional.of(value);
				}
			}
		}

		//
		// Fallback: Check again the documentField and assume some defaults
		if (!hasParentEvaluatee)
		{
			if (documentField != null)
			{
				final Class<?> valueClass = documentField.getValueClass();
				if (StringLookupValue.class.equals(valueClass))
				{
					// corner case: e.g. Field: C_Order.IncotermLocation's DisplayLogic=@Incoterm@!''
					return Optional.of("");
				}
			}

			final Optional<Object> defaultValue = getDefaultValue(variableName);
			if (defaultValue.isPresent())
			{
				return defaultValue;
			}
		}

		//
		// Value not found
		if (logger.isTraceEnabled())
		{
			logger.trace("Variable '{}' not found.", variableName);
			logger.trace("Existing properties are: {}", _document.getFieldNames());
			logger.trace("Existing dyn attributes are: {}", _document.getAvailableDynAttributes());
			logger.trace("Was field in scope: {} (fieldInScope={})", inScopeField, _fieldNameInScope);
		}
		return Optional.empty();
	}

	private Optional<Object> getDefaultValue(final String variableName)
	{
		// FIXME: hardcoded default to avoid a lot of warnings
		if (variableName.endsWith("_ID"))
		{
			return Optional.of(InterfaceWrapperHelper.getFirstValidIdByColumnName(variableName) - 1);
		}

		// TODO: find some defaults?
		return Optional.empty();
	}

	/**
	 * Gets variable's value from global context.
	 *
	 * @param variableName
	 * @param targetType
	 * @return value or <code>null</code> if does not apply
	 */
	private Object getGlobalContext(final String variableName, final Class<?> targetType)
	{
		if (Integer.class.equals(targetType))
		{
			return Env.getContextAsInt(getCtx(), variableName);
		}
		else if (java.util.Date.class.equals(targetType)
				|| Timestamp.class.equals(targetType))
		{
			return Env.getContextAsDate(getCtx(), variableName);
		}
		else if (Integer.class.equals(targetType)
				|| int.class.equals(targetType))
		{
			return Env.getContextAsInt(getCtx(), variableName);
		}
		else if (Boolean.class.equals(targetType))
		{
			final String valueStr = Env.getContext(getCtx(), variableName);
			return DisplayType.toBoolean(valueStr, null);
		}

		final String valueStr = Env.getContext(getCtx(), variableName);

		//
		// Use some default value
		if (Check.isEmpty(valueStr))
		{
			// FIXME hardcoded. when we will do a proper login, this won't be necessary
			if (variableName.startsWith("$Element_"))
			{
				return Boolean.FALSE;
			}
		}

		return valueStr;
	}

	/** Converts field value to {@link Evaluatee2} friendly string */
	private static String convertToString(final String variableName, final Object value)
	{
		if (value == null)
		{
			return null;
		}
		else if (value instanceof Boolean)
		{
			return DisplayType.toBooleanString((Boolean)value);
		}
		else if (value instanceof String)
		{
			return value.toString();
		}
		else if (value instanceof LookupValue)
		{
			return ((LookupValue)value).getIdAsString();
		}
		else if (value instanceof java.util.Date)
		{
			final java.util.Date valueDate = (java.util.Date)value;
			return Env.toString(valueDate);
		}
		else
		{
			return value.toString();
		}
	}

	private static Integer convertToInteger(final String variableName, final Object valueObj)
	{
		if (valueObj == null)
		{
			return null;
		}
		else if (valueObj instanceof Integer)
		{
			return (Integer)valueObj;
		}
		else if (valueObj instanceof Number)
		{
			return ((Number)valueObj).intValue();
		}
		else if (valueObj instanceof IntegerLookupValue)
		{
			return ((IntegerLookupValue)valueObj).getIdAsInt();
		}
		else
		{
			final String valueStr = valueObj.toString().trim();
			if (valueStr.isEmpty())
			{
				return null;
			}
			return Integer.parseInt(valueStr);
		}
	}

	private static BigDecimal convertToBigDecimal(final String variableName, final Object valueObj)
	{
		if (valueObj == null)
		{
			return null;
		}
		else if (valueObj instanceof BigDecimal)
		{
			return (BigDecimal)valueObj;
		}
		else
		{
			final String valueStr = valueObj.toString().trim();
			if (valueStr.isEmpty())
			{
				return null;
			}
			return new BigDecimal(valueStr);
		}
	}

}
