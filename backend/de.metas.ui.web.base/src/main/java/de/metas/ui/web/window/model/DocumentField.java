package de.metas.ui.web.window.model;

import com.google.common.base.MoreObjects;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.exceptions.DocumentFieldNotLookupException;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.lookup.DocumentZoomIntoInfo;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.util.NumberUtils;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

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

/* package */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
class DocumentField implements IDocumentField
{
	private static final Logger logger = LogManager.getLogger(DocumentField.class);

	private final DocumentFieldDescriptor descriptor;
	private final Document _document;
	private final Optional<LookupDataSource> _lookupDataSource;
	private boolean lookupValuesStaled = true;

	@Nullable
	private transient ICalloutField _calloutField; // lazy

	//
	// State
	@Nullable
	private Object _initialValue;
	@Nullable
	private Object _valueOnCheckout;
	@Nullable
	private Object _value;

	private static final LogicExpressionResult MANDATORY_InitialValue = LogicExpressionResult.namedConstant("mandatory-initial", false);
	private LogicExpressionResult _mandatory = MANDATORY_InitialValue;
	private static final LogicExpressionResult READONLY_InitialValue = LogicExpressionResult.namedConstant("readonly-initial", false);
	private LogicExpressionResult _readonly = READONLY_InitialValue;
	private static final LogicExpressionResult DISPLAYED_InitialValue = LogicExpressionResult.namedConstant("displayed-initial", false);
	private LogicExpressionResult _displayed = DISPLAYED_InitialValue;

	@NonNull private DocumentValidStatus _validStatus;

	/* package */ DocumentField(final DocumentFieldDescriptor descriptor, final Document document)
	{
		this.descriptor = descriptor;
		_document = document;

		_lookupDataSource = descriptor.createLookupDataSource();

		_validStatus = DocumentValidStatus.fieldInitiallyInvalid();
	}

	/** copy constructor */
	protected DocumentField(final DocumentField from, final Document document, final CopyMode copyMode)
	{
		descriptor = from.descriptor;
		_document = document;
		_lookupDataSource = from._lookupDataSource;
		lookupValuesStaled = from.lookupValuesStaled;
		_calloutField = null; // don't copy it
		_initialValue = from._initialValue;
		_value = from._value;
		_mandatory = from._mandatory;
		_readonly = from._readonly;
		_displayed = from._displayed;
		_validStatus = from._validStatus;

		switch (copyMode)
		{
			case CheckInReadonly:
				_valueOnCheckout = _value;
				break;
			case CheckOutWritable:
				_valueOnCheckout = _initialValue;
				break;
			default:
				break;
		}
	}

	@Override
	public String toString()
	{
		// NOTE: try keeping this string short...

		final String tableName = getDocument().getEntityDescriptor().getTableNameOrNull();
		final String fieldName = getFieldName();
		final String fieldNameFQ = tableName == null ? fieldName : tableName + "." + fieldName;
		return MoreObjects.toStringHelper(this)
				.add("fieldName", fieldNameFQ)
				// .add("documentPath", getDocumentPath())
				.add("value", _value)
				.add("initalValue", _initialValue)
				// .add("mandatory", _mandatory)
				// .add("readonly", _readonly)
				// .add("displayed", _displayed)
				.toString();
	}

	@Override
	public DocumentField copy(final Document document, final CopyMode copyMode)
	{
		return new DocumentField(this, document, copyMode);
	}

	@Override
	public DocumentFieldDescriptor getDescriptor()
	{
		return descriptor;
	}

	@Nullable private LookupDataSource getLookupDataSourceOrNull()
	{
		return _lookupDataSource.orElse(null);
	}

	private LookupDataSource getLookupDataSource()
	{
		return _lookupDataSource
				.orElseThrow(() -> new DocumentFieldNotLookupException(getFieldName()));
	}

	@Override
	public Document getDocument()
	{
		return _document;
	}

	@Override
	@Nullable
	public Object getInitialValue()
	{
		return _initialValue;
	}

	@Override
	public void setInitialValue(final Object initialValue, final IDocumentChangesCollector changesCollector)
	{
		final Object initialValueConv = convertToValueClassAndCorrect(initialValue);
		if (logger.isTraceEnabled())
		{
			logger.trace("setInitialValue: {} = {}", getFieldName(), initialValueConv);
		}
		_initialValue = initialValueConv;

		//
		// Update checkout value too because we consider initialization as first checkout
		_valueOnCheckout = initialValueConv;

		//
		// Update the current value too
		_value = initialValueConv;

		// Update valid status
		// NOTE: usually this method is called on initialization
		updateValid(changesCollector);
	}

	@Override
	public void setValue(final Object value, final IDocumentChangesCollector changesCollector)
	{
		final Object valueNew = convertToValueClassAndCorrect(value);
		final Object valueOld = _value;
		_value = valueNew;

		if (logger.isTraceEnabled() && !DataTypes.equals(valueNew, valueOld))
		{
			logger.trace("setValue: {} = {} <- {}", getFieldName(), valueNew, valueOld);
		}

		updateValid(changesCollector);
	}

	@Override
	@Nullable
	public Object getValue()
	{
		return _value;
	}

	@Override
	@Nullable
	public Object getValueAsJsonObject(@NonNull final JSONOptions jsonOpts)
	{
		Object value = getValue();
		if (value == null)
		{
			return null;
		}

		//
		// If we are dealing with a lookup value, make, sure it's translated (see https://github.com/metasfresh/metasfresh-webui-api/issues/311 )
		final LookupDataSource lookupDataSource = getLookupDataSourceOrNull();
		if (lookupDataSource != null && value instanceof LookupValue)
		{
			final LookupValue lookupValue = (LookupValue)value;
			final ITranslatableString displayNameTrl = lookupValue.getDisplayNameTrl();
			if (!displayNameTrl.isTranslatedTo(jsonOpts.getAdLanguage()))
			{
				value = lookupDataSource.findById(lookupValue.getId());
			}
		}

		return Values.valueToJsonObject(value, jsonOpts);
	}

	@Override
	public int getValueAsInt(final int defaultValueWhenNull)
	{
		final Integer valueInt = convertToValueClass(_value, DocumentFieldWidgetType.Integer, Integer.class);
		return valueInt == null ? defaultValueWhenNull : valueInt;
	}

	@Override
	public boolean getValueAsBoolean()
	{
		final Boolean valueBoolean = convertToValueClass(_value, DocumentFieldWidgetType.YesNo, Boolean.class);
		return valueBoolean != null && valueBoolean;
	}

	@Override
	@Nullable
	public <T> T getValueAs(@NonNull final Class<T> returnType)
	{
		return convertToValueClass(_value, null, returnType);
	}

	@Override
	public DocumentZoomIntoInfo getZoomIntoInfo()
	{
		final IntegerLookupValue lookupValue = getValueAs(IntegerLookupValue.class);
		return getLookupDataSource()
				.getDocumentZoomInto(lookupValue != null ? lookupValue.getIdAsInt() : -1)
				.overrideWindowIdIfPossible(getZoomIntoWindowId());
	}

	@Override
	@Nullable
	public Object getOldValue()
	{
		return _valueOnCheckout;
	}

	private Object convertToValueClass(final Object value)
	{
		final DocumentFieldWidgetType widgetType = getWidgetType();
		final Class<?> targetType = descriptor.getValueClass();
		return convertToValueClass(value, widgetType, targetType);
	}

	/**
	 * Converts given value to field's type and after that applies various corrections like precision in case of numbers with precision.
	 *
	 * @return value converted and corrected
	 */
	@Nullable
	private Object convertToValueClassAndCorrect(final Object value)
	{
		final Object valueConv = convertToValueClass(value);

		//
		// Apply corrections if needed
		//

		// Apply number precision
		if (valueConv instanceof BigDecimal)
		{
			final Integer precision = getWidgetType().getStandardNumberPrecision();
			if (precision != null)
			{
				return NumberUtils.setMinimumScale((BigDecimal)valueConv, precision);
			}
			else
			{
				return NumberUtils.stripTrailingDecimalZeros((BigDecimal)valueConv);
			}
		}
		else if (valueConv instanceof String)
		{
			if (((String)valueConv).isEmpty())
			{
				return null;
			}
		}

		return valueConv;
	}

	@Nullable
	private <T> T convertToValueClass(
			@Nullable final Object value,
			@Nullable final DocumentFieldWidgetType widgetType,
			@NonNull final Class<T> targetType)
	{
		return descriptor.convertToValueClass(value, widgetType, targetType, getLookupDataSourceOrNull());
	}

	@Override
	public LogicExpressionResult getMandatory()
	{
		return _mandatory;
	}

	@Override
	public void setMandatory(@NonNull final LogicExpressionResult mandatory, final IDocumentChangesCollector changesCollector)
	{
		final LogicExpressionResult mandatoryOld = _mandatory;
		_mandatory = mandatory;

		//
		// Update validStatus if mandatory flag changed
		if (!mandatoryOld.equalsByValue(mandatory))
		{
			updateValid(changesCollector);
		}
	}

	@Override
	public LogicExpressionResult getReadonly()
	{
		return _readonly;
	}

	@Override
	public void setReadonly(@NonNull final LogicExpressionResult readonly)
	{
		_readonly = readonly;
	}

	@Override
	public LogicExpressionResult getDisplayed()
	{
		return _displayed;
	}

	@Override
	public void setDisplayed(@NonNull final LogicExpressionResult displayed)
	{
		_displayed = displayed;
	}

	@Override
	public boolean isLookupValuesStale()
	{
		final LookupDataSource lookupDataSource = getLookupDataSourceOrNull();
		if (lookupDataSource == null)
		{
			return false;
		}
		return lookupValuesStaled;
	}

	@Override
	public boolean setLookupValuesStaled(final String triggeringFieldName)
	{
		final LookupDataSource lookupDataSource = getLookupDataSourceOrNull();
		if (lookupDataSource == null)
		{
			return false;
		}

		lookupValuesStaled = true;
		logger.trace("Marked lookup values as staled (->{}): {}", triggeringFieldName, this);

		return true;
	}

	@Override
	public LookupValuesList getLookupValues()
	{
		final LookupDataSource lookupDataSource = getLookupDataSource();
		final Evaluatee ctx = getDocument().asEvaluatee();
		final LookupValuesList values = lookupDataSource.findEntities(ctx).getValues();
		lookupValuesStaled = false;
		return values;
	}

	@Override
	public LookupValuesPage getLookupValuesForQuery(final String query)
	{
		final LookupDataSource lookupDataSource = getLookupDataSource();
		final Evaluatee ctx = getDocument().asEvaluatee();
		final LookupValuesPage page = lookupDataSource.findEntities(ctx, query);
		lookupValuesStaled = false;
		return page;
	}

	@Override
	public Optional<LookupValue> getLookupValueById(@NonNull final RepoIdAware id)
	{
		final LookupDataSource lookupDataSource = getLookupDataSource();
		final LookupValue value = lookupDataSource.findById(id);
		lookupValuesStaled = false;
		return Optional.ofNullable(value);
	}


	@Override
	public ICalloutField asCalloutField()
	{
		if (_calloutField == null)
		{
			_calloutField = new DocumentFieldAsCalloutField(this);
		}
		return _calloutField;
	}

	private void updateValid(final IDocumentChangesCollector changesCollector)
	{
		final DocumentValidStatus validStatusOld = _validStatus;
		final DocumentValidStatus validStatusNew = computeValidStatus();
		_validStatus = validStatusNew;

		// Collect validStatus changed event
		if (!NullDocumentChangesCollector.isNull(changesCollector) && !Objects.equals(validStatusOld, validStatusNew))
		{
			// logger.debug("updateValid: {}: {} <- {}", getFieldName(), validNew, validOld);
			changesCollector.collectValidStatus(this);
		}
	}

	/**
	 * Computes field's validStatus.
	 * IMPORTANT: this method is not updating the status, it's only computing it.
	 */
	private DocumentValidStatus computeValidStatus()
	{
		// Consider virtual fields as valid because there is nothing we can do about them
		if (isReadonlyVirtualField())
		{
			return DocumentValidStatus.validField(getFieldName(), isInitialValue());
		}

		// Check mandatory constraint
		if (isMandatory() && getValue() == null)
		{
			return DocumentValidStatus.invalidFieldMandatoryNotFilled(getFieldName(), isInitialValue());
		}

		return DocumentValidStatus.validField(getFieldName(), isInitialValue());
	}

	@Override
	public DocumentValidStatus getValidStatus()
	{
		return _validStatus;
	}

	@Override
	public DocumentValidStatus updateStatusIfInitialInvalidAndGet(final IDocumentChangesCollector changesCollector)
	{
		if (_validStatus.isInitialInvalid())
		{
			updateValid(changesCollector);
		}
		return _validStatus;
	}

	@Override
	public boolean hasChangesToSave()
	{
		if (isReadonlyVirtualField())
		{
			return false;
		}

		return !isInitialValue();
	}

	private boolean isInitialValue()
	{
		return DataTypes.equals(_value, _initialValue);
	}

	@Override
	public Optional<WindowId> getZoomIntoWindowId()
	{
		final LookupDataSource lookupDataSource = getLookupDataSourceOrNull();
		if (lookupDataSource == null)
		{
			return Optional.empty();
		}

		return lookupDataSource.getZoomIntoWindowId();
	}
}
