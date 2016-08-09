package de.metas.ui.web.window.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DisplayType;

import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
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

	private Object initialValue;
	private Object value;

	private boolean mandatory = false;
	private boolean readonly = false;
	private boolean displayed = false;

	private final LookupDataSource lookupDataSource;

	public DocumentField(final DocumentFieldDescriptor descriptor)
	{
		super();
		this.descriptor = descriptor;
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

	private Object convertToValueClass(final Object value)
	{
		if (NullValue.isNull(value))
		{
			return NullValue.instance;
		}

		final Class<?> fromType = value.getClass();
		final Class<?> targetType = descriptor.getValueClass();

		if (fromType.equals(targetType))
		{
			return value;
		}

		if (String.class == targetType)
		{
			return value.toString();
		}
		else if (java.util.Date.class == targetType)
		{
			if (value instanceof String)
			{
				return JSONConverters.dateFromString((String)value);
			}
		}
		else if (Integer.class == targetType)
		{
			if (value instanceof String)
			{
				return Integer.parseInt((String)value);
			}
			else if (value instanceof Number)
			{
				return ((Number)value).intValue();
			}
		}
		else if (BigDecimal.class == targetType)
		{
			if (String.class == fromType)
			{
				return new BigDecimal((String)value);
			}
		}
		else if (Boolean.class == targetType)
		{
			return DisplayType.toBoolean(value);
		}
		else if (LookupValue.class == targetType)
		{
			if (Map.class.isAssignableFrom(fromType))
			{
				@SuppressWarnings("unchecked")
				final Map<String, String> map = (Map<String, String>)value;
				return JSONConverters.lookupValueFromJsonMap(map);
			}
		}

		throw new AdempiereException("Cannot convert value '" + value + "' from " + fromType + " to " + targetType);
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
}
