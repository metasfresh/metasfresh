package de.metas.ui.web.window.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DisplayType;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.util.JSONConverters;
import de.metas.ui.web.window_old.shared.datatype.LookupValue;
import de.metas.ui.web.window_old.shared.datatype.NullValue;

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

public class DocumentField
{
	private static final Logger logger = LogManager.getLogger(DocumentField.class);

	private final DocumentFieldDescriptor descriptor;
	private final Document _document;
	private final LookupDataSource lookupDataSource;

	private ICalloutField _calloutField; // lazy

	//
	// State
	private Object _initialValue;
	private Object _value;
	private boolean _mandatory = false;
	private boolean _readonly = false;
	private boolean _displayed = false;

	/* package */ DocumentField(final DocumentFieldDescriptor descriptor, final Document document)
	{
		super();
		this.descriptor = descriptor;
		_document = document;
		lookupDataSource = descriptor.getDataBinding().createLookupDataSource();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("name", descriptor.getName())
				.add("value", _value)
				.add("initalValue", _initialValue)
				.add("mandatory", _mandatory)
				.add("readonly", _readonly)
				.add("displayed", _displayed)
				.toString();
	}

	public DocumentFieldDescriptor getDescriptor()
	{
		return descriptor;
	}

	private Document getDocument()
	{
		return _document;
	}

	public String getFieldName()
	{
		return descriptor.getName();
	}

	public boolean isKey()
	{
		return descriptor.isKey();
	}
	
	public boolean isVirtualField()
	{
		return descriptor.isVirtualField();
	}

	public boolean isCalculated()
	{
		return descriptor.isCalculated();
	}


	public Object getInitialValue()
	{
		return _initialValue;
	}

	/**
	 * FIXME: make it private or package level
	 *
	 * @param value
	 */
	public void setInitialValue(final Object value)
	{
		final Object valueConv = convertToValueClass(value);
		_initialValue = valueConv;
		_value = valueConv;

		if (logger.isTraceEnabled())
		{
			logger.trace("Set {}'s initial value: {}", getFieldName(), valueConv);
		}
	}

	/* package */void setValue(final Object value)
	{
		final Object valueConv = convertToValueClass(value);
		final Object valueOld = _value;
		_value = valueConv;

		if (logger.isTraceEnabled())
		{
			logger.trace("Changed {}'s value: {} -> {}", getFieldName(), valueOld, value);
		}
	}

	public Object getValue()
	{
		return _value;
	}

	public Object getValueAsJsonObject()
	{
		return JSONConverters.valueToJsonObject(_value);
	}

	public int getValueAsInt(final int defaultValue)
	{
		final Integer valueInt = convertToValueClass(_value, Integer.class);
		return valueInt == null ? defaultValue : valueInt;
	}

	public boolean getValueAsBoolean()
	{
		final Boolean valueBoolean = convertToValueClass(_value, Boolean.class);
		return valueBoolean != null && valueBoolean.booleanValue();
	}

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

	private <T> T convertToValueClass(final Object value, final Class<T> targetType)
	{
		if (NullValue.isNull(value))
		{
			return null;
		}

		final Class<?> fromType = value.getClass();

		try
		{
			if (targetType.isAssignableFrom(fromType))
			{
				@SuppressWarnings("unchecked")
				final T valueConv = (T)value;
				return valueConv;
			}

			if (String.class == targetType)
			{
				@SuppressWarnings("unchecked")
				final T valueConv = (T)value.toString();
				return valueConv;
			}
			else if (java.util.Date.class == targetType)
			{
				if (value instanceof String)
				{
					@SuppressWarnings("unchecked")
					final T valueConv = (T)JSONConverters.dateFromString((String)value);
					return valueConv;
				}
			}
			else if (Integer.class == targetType)
			{
				if (value instanceof String)
				{
					@SuppressWarnings("unchecked")
					final T valueConv = (T)(Integer)Integer.parseInt((String)value);
					return valueConv;
				}
				else if (value instanceof Number)
				{
					@SuppressWarnings("unchecked")
					final T valueConv = (T)(Integer)((Number)value).intValue();
					return valueConv;
				}
			}
			else if (BigDecimal.class == targetType)
			{
				if (String.class == fromType)
				{
					@SuppressWarnings("unchecked")
					final T valueConv = (T)new BigDecimal((String)value);
					return valueConv;
				}
			}
			else if (Boolean.class == targetType)
			{
				@SuppressWarnings("unchecked")
				final T valueConv = (T)DisplayType.toBoolean(value, Boolean.FALSE);
				return valueConv;
			}
			else if (LookupValue.class == targetType)
			{
				if (Map.class.isAssignableFrom(fromType))
				{
					@SuppressWarnings("unchecked")
					final Map<String, String> map = (Map<String, String>)value;
					@SuppressWarnings("unchecked")
					final T valueConv = (T)JSONConverters.lookupValueFromJsonMap(map);
					return valueConv;
				}
				else if (Integer.class.isAssignableFrom(fromType))
				{
					final Integer valueInt = (Integer)value;
					if (lookupDataSource != null)
					{
						@SuppressWarnings("unchecked")
						final T valueConv = (T)lookupDataSource.findById(valueInt);
						// TODO: what if valueConv was not found?
						return valueConv;
					}
				}
				else if (String.class == fromType)
				{
					final String valueStr = (String)value;
					if (valueStr.isEmpty())
					{
						return null;
					}

					if (lookupDataSource != null)
					{
						@SuppressWarnings("unchecked")
						final T valueConv = (T)lookupDataSource.findById(valueStr);
						// TODO: what if valueConv was not found?
						return valueConv;
					}
				}
			}
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Cannot convert " + getFieldName() + "'s value '" + value + "' (" + fromType + ") to " + targetType, e);
		}

		throw new AdempiereException("Cannot convert " + getFieldName() + "'s value '" + value + "' (" + fromType + ") to " + targetType);
	}

	public boolean isMandatory()
	{
		return _mandatory;
	}

	/* package */ void setMandatory(final boolean mandatory)
	{
		_mandatory = mandatory;
	}

	public boolean isReadonly()
	{
		return _readonly;
	}

	/* package */void setReadonly(final boolean readonly)
	{
		_readonly = readonly;
	}

	public boolean isDisplayed()
	{
		return _displayed;
	}

	/* package */void setDisplayed(final boolean displayed)
	{
		_displayed = displayed;
	}

	public boolean isLookupValuesStale()
	{
		// TODO: implement
		return false;
	}
	
	public boolean isLookupWithNumericKey()
	{
		return lookupDataSource != null && lookupDataSource.isNumericKey();
	}

	/* package */List<LookupValue> getLookupValues(final Document document)
	{
		return lookupDataSource.findEntities(document, LookupDataSource.DEFAULT_PageLength);
	}

	/* package */List<LookupValue> getLookupValuesForQuery(final Document document, final String query)
	{
		return lookupDataSource.findEntities(document, query, LookupDataSource.FIRST_ROW, LookupDataSource.DEFAULT_PageLength);
	}

	public ICalloutField asCalloutField()
	{
		if (_calloutField == null)
		{
			_calloutField = new AsCalloutField();
		}
		return _calloutField;
	}

	private final class AsCalloutField implements ICalloutField
	{
		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.addValue(DocumentField.this)
					.toString();
		}

		@Override
		public boolean isTriggerCalloutAllowed()
		{
			if (!getDescriptor().isAlwaysUpdateable() && getDocument().isProcessed())
			{
				return false;
			}
			return true;
		}

		@Override
		public Properties getCtx()
		{
			return getDocument().getCtx();
		}

		@Override
		public String getTableName()
		{
			return SqlDocumentEntityDataBindingDescriptor.getTableName(getDocument());
		}

		@Override
		public int getAD_Table_ID()
		{
			return SqlDocumentEntityDataBindingDescriptor.getAD_Table_ID(getDocument());
		}

		@Override
		public int getAD_Column_ID()
		{
			return SqlDocumentFieldDataBindingDescriptor.getAD_Column_ID(DocumentField.this);
		}

		@Override
		public String getColumnName()
		{
			return getFieldName();
		}

		@Override
		public Object getValue()
		{
			return DocumentField.this.getValue();
		}

		@Override
		public Object getOldValue()
		{
			return DocumentField.this.getOldValue();
		}

		@Override
		public <T> T getModel(final Class<T> modelClass)
		{
			return getCalloutRecord().getModel(modelClass);
		}

		@Override
		public int getWindowNo()
		{
			return getDocument().getWindowNo();
		}

		@Override
		public int getTabNo()
		{
			return getDocument().getEntityDescriptor().getTabNo();
		}

		@Override
		public boolean isRecordCopyingMode()
		{
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isRecordCopyingModeIncludingDetails()
		{
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public ICalloutExecutor getCurrentCalloutExecutor()
		{
			return getDocument().getCalloutExecutor();
		}

		@Override
		public void fireDataStatusEEvent(final String AD_Message, final String info, final boolean isError)
		{
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("fireDataStatusEEvent: AD_Message=" + AD_Message + ", info=" + info + ", isError=" + isError);
		}

		@Override
		public void fireDataStatusEEvent(final ValueNamePair errorLog)
		{
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("fireDataStatusEEvent: errorLog=" + errorLog);
		}

		@Override
		public void putContext(final String name, final boolean value)
		{
			getDocument().setDynAttribute(name, value);
		}

		@Override
		public void putContext(final String name, final java.util.Date value)
		{
			getDocument().setDynAttribute(name, value);
		}

		@Override
		public void putContext(final String name, final int value)
		{
			getDocument().setDynAttribute(name, value);
		}

		@Override
		public boolean getContextAsBoolean(final String name)
		{
			final Object valueObj = getDocument().getDynAttribute(name);
			return DisplayType.toBoolean(valueObj);
		}

		@Override
		public ICalloutRecord getCalloutRecord()
		{
			return getDocument().asCalloutRecord();
		}

	}
}
