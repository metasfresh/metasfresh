package de.metas.ui.web.window.model;

import java.math.BigDecimal;
import java.util.Objects;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.adempiere.util.NumberUtils;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.controller.Execution;
import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.exceptions.DocumentFieldNotLookupException;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.lookup.LookupDataSource;

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

/*package*/class DocumentField implements IDocumentField
{
	private static final Logger logger = LogManager.getLogger(DocumentField.class);

	private final DocumentFieldDescriptor descriptor;
	private final Document _document;
	private final LookupDataSource _lookupDataSource;
	private boolean lookupValuesStaled = true;

	private transient ICalloutField _calloutField; // lazy

	//
	// State
	private Object _initialValue;
	private Object _valueOnCheckout;
	private Object _value;

	private static final LogicExpressionResult MANDATORY_InitialValue = LogicExpressionResult.namedConstant("mandatory-initial", false);
	private LogicExpressionResult _mandatory = MANDATORY_InitialValue;
	private static final LogicExpressionResult READONLY_InitialValue = LogicExpressionResult.namedConstant("readonly-initial", false);
	private LogicExpressionResult _readonly = READONLY_InitialValue;
	private static final LogicExpressionResult DISPLAYED_InitialValue = LogicExpressionResult.namedConstant("displayed-initial", false);
	private LogicExpressionResult _displayed = DISPLAYED_InitialValue;

	private DocumentValidStatus _validStatus = DocumentValidStatus.inititalInvalid();

	/* package */ DocumentField(final DocumentFieldDescriptor descriptor, final Document document)
	{
		super();
		this.descriptor = descriptor;
		_document = document;

		_lookupDataSource = descriptor.createLookupDataSource(LookupDescriptorProvider.LookupScope.DocumentField);

		_validStatus = DocumentValidStatus.inititalInvalid();
	}

	/** copy constructor */
	protected DocumentField(final DocumentField from, final Document document, final CopyMode copyMode)
	{
		super();
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
		
		return MoreObjects.toStringHelper(this)
				.add("fieldName", getFieldName())
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
	
	private LookupDataSource getLookupDataSource()
	{
		return _lookupDataSource;
	}

	@Override
	public Document getDocument()
	{
		return _document;
	}

	@Override
	public Object getInitialValue()
	{
		return _initialValue;
	}

	/**
	 * @param initialValue
	 */
	@Override
	public void setInitialValue(final Object initialValue, final FieldInitializationMode mode)
	{
		final Object initialValueConv = convertToValueClassAndCorrect(initialValue);
		if (logger.isTraceEnabled())
		{
			logger.trace("setInitialValue: {} = {} ({})", getFieldName(), initialValueConv, mode);
		}
		_initialValue = initialValueConv;

		//
		// Update old value too
		_valueOnCheckout = initialValueConv;

		//
		// Update the current value too, if needed
		final Object valueOld = _value;
		if (DataTypes.equals(initialValueConv, valueOld))
		{
			return;
		}
		_value = initialValueConv;

		//
		// Set valid state to Staled
		final DocumentValidStatus validOld = _validStatus;
		final DocumentValidStatus validNew = DocumentValidStatus.staled();
		_validStatus = validNew;
		if (logger.isDebugEnabled() && !Objects.equals(validOld, validNew))
		{
			logger.debug("setInitialValue: {}: {} <- {}", getFieldName(), validNew, validOld);
		}
	}

	@Override
	public void setValue(final Object value)
	{
		final Object valueNew = convertToValueClassAndCorrect(value);
		final Object valueOld = _value;

		if (DataTypes.equals(valueNew, valueOld))
		{
			return;
		}

		_value = valueNew;
		if (logger.isTraceEnabled())
		{
			logger.trace("setValue: {} = {} <- {}", getFieldName(), valueNew, valueOld);
		}

		updateValid();
	}

	@Override
	public Object getValue()
	{
		return _value;
	}

	@Override
	public Object getValueAsJsonObject()
	{
		return Values.valueToJsonObject(_value);
	}

	@Override
	public int getValueAsInt(final int defaultValueWhenNull)
	{
		final Integer valueInt = convertToValueClass(_value, Integer.class);
		return valueInt == null ? defaultValueWhenNull : valueInt;
	}

	@Override
	public boolean getValueAsBoolean()
	{
		final Boolean valueBoolean = convertToValueClass(_value, Boolean.class);
		return valueBoolean != null && valueBoolean.booleanValue();
	}

	@Override
	public <T> T getValueAs(final Class<T> returnType)
	{
		Preconditions.checkNotNull(returnType, "returnType shall not be null");
		final T value = convertToValueClass(_value, returnType);
		return value;
	}

	@Override
	public Object getOldValue()
	{
		return _valueOnCheckout;
	}

	private Object convertToValueClass(final Object value)
	{
		final Class<?> targetType = descriptor.getValueClass();
		return convertToValueClass(value, targetType);
	}

	/**
	 * Converts given value to field's type and after that applies various corrections like precision in case of numbers with precision.
	 * 
	 * @param value
	 * @return value converted and corrected
	 */
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
				final BigDecimal valueBDCorrected = NumberUtils.setMinimumScale((BigDecimal)valueConv, precision);
				return valueBDCorrected;
			}
			else
			{
				final BigDecimal valueBDCorrected = NumberUtils.stripTrailingDecimalZeros((BigDecimal)valueConv);
				return valueBDCorrected;
			}
		}

		return valueConv;
	}

	private final <T> T convertToValueClass(final Object value, final Class<T> targetType)
	{
		return descriptor.convertToValueClass(value, targetType, getLookupDataSource());
	}

	@Override
	public LogicExpressionResult getMandatory()
	{
		return _mandatory;
	}

	@Override
	public void setMandatory(final LogicExpressionResult mandatory)
	{
		if (mandatory == null)
		{
			throw new NullPointerException("mandatory");
		}

		final LogicExpressionResult mandatoryOld = _mandatory;
		_mandatory = mandatory;

		if (!mandatoryOld.equalsByValue(mandatory))
		{
			updateValid();
		}
	}

	@Override
	public LogicExpressionResult getReadonly()
	{
		return _readonly;
	}

	@Override
	public void setReadonly(final LogicExpressionResult readonly)
	{
		if (readonly == null)
		{
			throw new NullPointerException("readonly");
		}
		_readonly = readonly;
	}

	@Override
	public LogicExpressionResult getDisplayed()
	{
		return _displayed;
	}

	@Override
	public void setDisplayed(final LogicExpressionResult displayed)
	{
		if (displayed == null)
		{
			throw new NullPointerException("displayed");
		}
		_displayed = displayed;
	}

	@Override
	public boolean isLookupValuesStale()
	{
		final LookupDataSource lookupDataSource = getLookupDataSource();
		if (lookupDataSource == null)
		{
			return false;
		}
		return lookupValuesStaled;
	}

	@Override
	public boolean setLookupValuesStaled(final String triggeringFieldName)
	{
		final LookupDataSource lookupDataSource = getLookupDataSource();
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
		if (lookupDataSource == null)
		{
			throw new DocumentFieldNotLookupException(getFieldName());
		}

		final DocumentEvaluatee ctx = getDocument().asEvaluatee();
		final LookupValuesList values = lookupDataSource.findEntities(ctx);
		lookupValuesStaled = false;
		return values == null ? LookupValuesList.EMPTY : values;
	}

	@Override
	public LookupValuesList getLookupValuesForQuery(final String query)
	{
		final LookupDataSource lookupDataSource = getLookupDataSource();
		if (lookupDataSource == null)
		{
			throw new DocumentFieldNotLookupException(getFieldName());
		}
		final DocumentEvaluatee ctx = getDocument().asEvaluatee();
		final LookupValuesList values = lookupDataSource.findEntities(ctx, query);
		lookupValuesStaled = false;
		return values;
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

	@Override
	public void updateValid()
	{
		final DocumentValidStatus validStatusOld = _validStatus;
		final DocumentValidStatus validStatusNew = checkValid();
		_validStatus = validStatusNew;

		// Collect validStatus changed event
		if (!Objects.equals(validStatusOld, validStatusNew))
		{
			//logger.debug("updateValid: {}: {} <- {}", getFieldName(), validNew, validOld);
			Execution.getCurrentDocumentChangesCollectorOrNull().collectValidStatus(this);
		}
	}

	@Override
	public void updateValidIfStaled()
	{
		if (!_validStatus.isStaled())
		{
			return;
		}

		updateValid();
	}

	private final DocumentValidStatus checkValid()
	{
		if (isMandatory() && getValue() == null)
		{
			return DocumentValidStatus.invalidMandatoryFieldNotFilled(getFieldName());
		}

		return DocumentValidStatus.valid();
	}

	/**
	 * @return field's valid state; never return null
	 */
	@Override
	public DocumentValidStatus getValidStatus()
	{
		return _validStatus;
	}

	@Override
	public boolean hasChangesToSave()
	{
		if(isVirtualField())
		{
			return false;
		}
		
		return !DataTypes.equals(_value, _initialValue);
	}
}
