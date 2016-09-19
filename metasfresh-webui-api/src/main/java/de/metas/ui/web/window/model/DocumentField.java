package de.metas.ui.web.window.model;

import java.util.Objects;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.exceptions.DocumentFieldNotLookupException;
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
	private final LookupDataSource lookupDataSource;
	private boolean lookupValuesStaled = true;

	private transient ICalloutField _calloutField; // lazy

	//
	// State
	private Object _initialValue;
	private Object _value;

	private static final LogicExpressionResult MANDATORY_InitialValue = LogicExpressionResult.namedConstant("mandatory-initial", false);
	private LogicExpressionResult _mandatory = MANDATORY_InitialValue;
	private static final LogicExpressionResult READONLY_InitialValue = LogicExpressionResult.namedConstant("readonly-initial", false);
	private LogicExpressionResult _readonly = READONLY_InitialValue;
	private static final LogicExpressionResult DISPLAYED_InitialValue = LogicExpressionResult.namedConstant("displayed-initial", false);
	private LogicExpressionResult _displayed = DISPLAYED_InitialValue;

	private DocumentValidStatus _valid = DocumentValidStatus.inititalInvalid();

	/* package */ DocumentField(final DocumentFieldDescriptor descriptor, final Document document)
	{
		super();
		this.descriptor = descriptor;
		_document = document;
		
		final DocumentFieldDataBindingDescriptor dataBinding = descriptor.getDataBinding().orElse(null);
		lookupDataSource = dataBinding == null ? null : dataBinding.createLookupDataSource();
		
		_valid = DocumentValidStatus.inititalInvalid();
	}

	/** copy constructor */
	protected DocumentField(final DocumentField from, final Document document)
	{
		super();
		descriptor = from.descriptor;
		_document = document;
		lookupDataSource = from.lookupDataSource;
		_calloutField = null; // don't copy it
		_initialValue = from._initialValue;
		_value = from._value;
		_mandatory = from._mandatory;
		_readonly = from._readonly;
		_displayed = from._displayed;
		_valid = from._valid;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("fieldName", getFieldName())
				.add("documentPath", getDocumentPath())
				.add("value", _value)
				.add("initalValue", _initialValue)
				.add("mandatory", _mandatory)
				.add("readonly", _readonly)
				.add("displayed", _displayed)
				.toString();
	}

	@Override
	public DocumentField copy(final Document document)
	{
		return new DocumentField(this, document);
	}

	@Override
	public DocumentFieldDescriptor getDescriptor()
	{
		return descriptor;
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
	 * @param value
	 */
	@Override
	public void setInitialValue(final Object value)
	{
		final Object valueNew = convertToValueClass(value);
		if (logger.isTraceEnabled())
		{
			logger.trace("setInitialValue: {} = {}", getFieldName(), valueNew);
		}
		_initialValue = valueNew;

		//
		// Update the current value too, if needed
		final Object valueOld = _value;
		if (DataTypes.equals(valueNew, valueOld))
		{
			return;
		}
		_value = valueNew;

		//
		// Set valid state to Staled
		final DocumentValidStatus validOld = _valid;
		final DocumentValidStatus validNew = DocumentValidStatus.staled();
		_valid = validNew;
		if (logger.isDebugEnabled() && !Objects.equals(validOld, validNew))
		{
			logger.debug("setInitialValue: {}: {} <- {}", getFieldName(), validNew, validOld);
		}
	}

	@Override
	public void setValue(final Object value)
	{
		final Object valueNew = convertToValueClass(value);
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
	public int getValueAsInt(final int defaultValue)
	{
		final Integer valueInt = convertToValueClass(_value, Integer.class);
		return valueInt == null ? defaultValue : valueInt;
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
		// TODO to implement. "getOldValue" is mainly needed for ICalloutField and DocumentInterfaceWrapper
		return getValue();
	}

	private Object convertToValueClass(final Object value)
	{
		final Class<?> targetType = descriptor.getValueClass();
		return convertToValueClass(value, targetType);
	}

	private final <T> T convertToValueClass(final Object value, final Class<T> targetType)
	{
		return descriptor.convertToValueClass(value, targetType, lookupDataSource);
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
		if(lookupDataSource == null)
		{
			return false;
		}
		return lookupValuesStaled;
	}

	@Override
	public boolean setLookupValuesStaled(final String triggeringFieldName)
	{
		if (lookupDataSource == null)
		{
			return false;
		}
		
		lookupValuesStaled = true;
		logger.trace("Marked {} as staled (triggeringFieldName={})", this, triggeringFieldName);
		
		return true;
	}

	@Override
	public LookupValuesList getLookupValues()
	{
		if (lookupDataSource == null)
		{
			throw new DocumentFieldNotLookupException(getFieldName());
		}
		
		final DocumentEvaluatee ctx = getDocument().asEvaluatee();
		final LookupValuesList values = lookupDataSource.findEntities(ctx, LookupDataSource.DEFAULT_PageLength);
		lookupValuesStaled = false;
		return values;
	}

	@Override
	public LookupValuesList getLookupValuesForQuery(final String query)
	{
		if (lookupDataSource == null)
		{
			throw new DocumentFieldNotLookupException(getFieldName());
		}
		final DocumentEvaluatee ctx = getDocument().asEvaluatee();
		final LookupValuesList values = lookupDataSource.findEntities(ctx, query, LookupDataSource.FIRST_ROW, LookupDataSource.DEFAULT_PageLength);
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
		final DocumentValidStatus validOld = _valid;
		final DocumentValidStatus validNew = checkValid();
		_valid = validNew;

		if (logger.isDebugEnabled() && !Objects.equals(validOld, validNew))
		{
			logger.debug("updateValid: {}: {} <- {}", getFieldName(), validNew, validOld);
		}
	}

	@Override
	public void updateValidIfStaled()
	{
		if (!_valid.isStaled())
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
	public DocumentValidStatus getValid()
	{
		return _valid;
	}

	@Override
	public boolean hasChanges()
	{
		return !DataTypes.equals(_value, _initialValue);
	}
}
