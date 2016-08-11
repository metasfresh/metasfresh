package de.metas.ui.web.window.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import com.google.common.base.MoreObjects;

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
	private final DocumentFieldDescriptor descriptor;
	private final Document _document;

	private Object initialValue;
	private Object value;

	private boolean mandatory = false;
	private boolean readonly = false;
	private boolean displayed = false;

	private final LookupDataSource lookupDataSource;
	
	private ICalloutField _calloutField; // lazy

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
				.add("value", value)
				.add("initalValue", initialValue)
				.add("mandatory", mandatory)
				.add("readonly", readonly)
				.add("displayed", displayed)
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

	public String getName()
	{
		return descriptor.getName();
	}

	public Object getInitialValue()
	{
		return initialValue;
	}

	public void setInitialValue(final Object initialValue)
	{
		this.initialValue = initialValue;
	}

	public void setValue(final Object value)
	{
		final Object valueConv = convertToValueClass(value);
		this.value = valueConv;
	}

	public Object getValue()
	{
		return value;
	}

	public Object getValueAsJsonObject()
	{
		return JSONConverters.valueToJsonObject(value);
	}

	public int getValueAsInt(final int defaultValue)
	{
		final Integer valueInt = convertToValueClass(value, Integer.class);
		return valueInt == null ? defaultValue : valueInt;
	}

	public Object getOldValue()
	{
		// TODO Auto-generated method stub
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
			if(targetType.isAssignableFrom(fromType))
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
						return valueConv;
					}
				}
			}
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Cannot convert " + getName() + "'s value '" + value + "' (" + fromType + ") to " + targetType, e);
		}

		throw new AdempiereException("Cannot convert " + getName() + "'s value '" + value + "' (" + fromType + ") to " + targetType);
	}

	public boolean isMandatory()
	{
		return mandatory;
	}

	public void setMandatory(final boolean mandatory)
	{
		this.mandatory = mandatory;
	}

	public boolean isReadonly()
	{
		return readonly;
	}

	public void setReadonly(final boolean readonly)
	{
		this.readonly = readonly;
	}

	public boolean isDisplayed()
	{
		return displayed;
	}

	public void setDisplayed(final boolean displayed)
	{
		this.displayed = displayed;
	}

	public boolean isLookupValuesStale()
	{
		// TODO: implement
		return false;
	}

	public List<LookupValue> getLookupValues(final Document document)
	{
		return lookupDataSource.findEntities(document, LookupDataSource.DEFAULT_PageLength);
	}

	public List<LookupValue> getLookupValuesForQuery(final Document document, final String query)
	{
		return lookupDataSource.findEntities(document, query, LookupDataSource.FIRST_ROW, LookupDataSource.DEFAULT_PageLength);
	}

	public ICalloutField asCalloutField()
	{
		if(_calloutField == null)
		{
			_calloutField = new AsCalloutField();
		}
		return _calloutField;
	}

	private final class AsCalloutField implements ICalloutField
	{

		@Override
		public boolean isTriggerCalloutAllowed()
		{
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public Properties getCtx()
		{
			// TODO Auto-generated method stub
			return Env.getCtx();
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
			return getName();
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
			return InterfaceWrapperHelper.create(getDocument(), modelClass);
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
			throw new UnsupportedOperationException();
		}

	}
}
