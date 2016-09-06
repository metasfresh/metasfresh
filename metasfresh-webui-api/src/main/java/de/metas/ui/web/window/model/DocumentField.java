package de.metas.ui.web.window.model;

import java.util.List;
import java.util.Objects;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.exceptions.DocumentFieldNotLookupException;

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

/*package*/class DocumentField implements IDocumentFieldView
{
	private static final Logger logger = LogManager.getLogger(DocumentField.class);

	private final DocumentFieldDescriptor descriptor;
	private final Document _document;
	private final LookupDataSource lookupDataSource;

	private transient ICalloutField _calloutField; // lazy

	//
	// State
	private Object _initialValue;
	private Object _value;
	private LogicExpressionResult _mandatory = LogicExpressionResult.FALSE;
	private LogicExpressionResult _readonly = LogicExpressionResult.FALSE;
	private LogicExpressionResult _displayed = LogicExpressionResult.FALSE;
	private DocumentValidStatus _valid = DocumentValidStatus.inititalInvalid();

	/* package */ DocumentField(final DocumentFieldDescriptor descriptor, final Document document)
	{
		super();
		this.descriptor = descriptor;
		_document = document;
		lookupDataSource = descriptor.getDataBinding().createLookupDataSource();
		_valid = DocumentValidStatus.inititalInvalid();
	}

	/** copy constructor */
	private DocumentField(final DocumentField from, final Document document)
	{
		super();
		descriptor = from.descriptor;
		_document = document;
		lookupDataSource = from.lookupDataSource == null ? null : from.lookupDataSource.copy();
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
	public DocumentFieldDescriptor getDescriptor()
	{
		return descriptor;
	}

	/* package */Document getDocument()
	{
		return _document;
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		return _document.getDocumentPath();
	}

	@Override
	public String getFieldName()
	{
		return descriptor.getFieldName();
	}

	@Override
	public boolean isKey()
	{
		return descriptor.isKey();
	}

	@Override
	public boolean isVirtualField()
	{
		return descriptor.isVirtualField();
	}

	@Override
	public boolean isCalculated()
	{
		return descriptor.isCalculated();
	}

	@Override
	public Object getInitialValue()
	{
		return _initialValue;
	}

	/**
	 * @param value
	 */
	/* package */ void setInitialValue(final Object value)
	{
		final Object valueConv = convertToValueClass(value);
		_initialValue = valueConv;
		_value = valueConv;
		if (logger.isTraceEnabled())
		{
			logger.trace("Set {}'s initial value: {}", getFieldName(), valueConv);
		}

		updateValid();
	}

	/* package */void setValue(final Object value)
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
			logger.trace("Changed {}'s value: {} -> {}", getFieldName(), valueOld, valueNew);
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
	public boolean isMandatory()
	{
		return _mandatory.booleanValue();
	}

	/* package */ LogicExpressionResult getMandatory()
	{
		return _mandatory;
	}

	/* package */ void setMandatory(final LogicExpressionResult mandatory)
	{
		if (_mandatory.valueEquals(mandatory))
		{
			return;
		}

		_mandatory = mandatory;
		updateValid();
	}

	@Override
	public boolean isReadonly()
	{
		return _readonly.booleanValue();
	}

	/* package */ LogicExpressionResult getReadonly()
	{
		return _readonly;
	}

	/* package */void setReadonly(final LogicExpressionResult readonly)
	{
		_readonly = readonly;
	}

	@Override
	public boolean isDisplayed()
	{
		return _displayed.booleanValue();
	}

	/* package */LogicExpressionResult getDisplayed()
	{
		return _displayed;
	}

	/* package */void setDisplayed(final LogicExpressionResult displayed)
	{
		_displayed = displayed;
	}

	@Override
	public boolean isPublicField()
	{
		return descriptor.isPublicField();
	}

	@Override
	public boolean isAdvancedField()
	{
		return descriptor.isAdvancedField();
	}

	@Override
	public boolean isLookupValuesStale()
	{
		return lookupDataSource != null && lookupDataSource.isStaled();
	}

	/* package */ boolean setLookupValuesStaled(final String triggeringFieldName)
	{
		if (lookupDataSource == null)
		{
			return false;
		}
		return lookupDataSource.setStaled(triggeringFieldName);
	}

	public boolean isLookupWithNumericKey()
	{
		return lookupDataSource != null && lookupDataSource.isNumericKey();
	}

	/* package */List<LookupValue> getLookupValues(final Document document)
	{
		if (lookupDataSource == null)
		{
			throw new DocumentFieldNotLookupException(getFieldName());
		}
		return lookupDataSource.findEntities(document, LookupDataSource.DEFAULT_PageLength);
	}

	/* package */List<LookupValue> getLookupValuesForQuery(final Document document, final String query)
	{
		if (lookupDataSource == null)
		{
			throw new DocumentFieldNotLookupException(getFieldName());
		}
		return lookupDataSource.findEntities(document, query, LookupDataSource.FIRST_ROW, LookupDataSource.DEFAULT_PageLength);
	}

	public ICalloutField asCalloutField()
	{
		if (_calloutField == null)
		{
			_calloutField = new DocumentFieldAsCalloutField(this);
		}
		return _calloutField;
	}

	final void updateValid()
	{
		final DocumentValidStatus validOld = _valid;
		final DocumentValidStatus validNew = checkValid();
		if (Objects.equals(validOld, validNew))
		{
			return;
		}

		_valid = validNew;
		logger.debug("Changed valid state {}->{} for {}", validOld, validNew, this);
	}

	private final DocumentValidStatus checkValid()
	{
		if (isMandatory() && getValue() == null)
		{
			logger.debug("Not valid because mandatory field is not filled: {}", this);
			return DocumentValidStatus.invalidMandatoryFieldNotFilled(getFieldName());
		}

		return DocumentValidStatus.valid();
	}

	/**
	 * @return field's valid state; never return null
	 */
	/* package */DocumentValidStatus getValid()
	{
		return _valid;
	}

	@Override
	public boolean hasChanges()
	{
		return !DataTypes.equals(_value, _initialValue);
	}

	/* package */DocumentField copy(final Document document)
	{
		return new DocumentField(this, document);
	}
}
