package de.metas.ui.web.vaadin.window.model;

import org.compiere.util.ArrayKeyBuilder;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.vaadin.data.Property;
import com.vaadin.data.util.PropertysetItem;

import de.metas.logging.LogManager;
/*
 * #%L
 * de.metas.ui.web.vaadin
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
import de.metas.ui.web.vaadin.window.descriptor.DataFieldPropertyDescriptor;
import de.metas.ui.web.vaadin.window.descriptor.DataRowDescriptor;

@SuppressWarnings("serial")
public class DataRowItem extends PropertysetItem
{
	private static final Logger logger = LogManager.getLogger(DataRowItem.class);

	private final DataRowDescriptor descriptor;

	public DataRowItem(final DataRowDescriptor descriptor)
	{
		super();
		this.descriptor = descriptor;

		for (final DataFieldPropertyDescriptor fieldDescriptor : descriptor.getFieldDescriptors())
		{
			final DataFieldProperty fieldProperty = new DataFieldProperty(fieldDescriptor);

			super.addItemProperty(fieldDescriptor.getColumnName(), fieldProperty);
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean addItemProperty(final Object id, final Property property)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeItemProperty(final Object id)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public DataFieldProperty getItemProperty(final Object columnName)
	{
		return (DataFieldProperty)super.getItemProperty(columnName);
	}

	public boolean hasPropertyId(final Object columnName)
	{
		return getItemProperty(columnName) != null;
	}

	public void setPropertyValue(final Object columnName, final Object value)
	{
		final DataFieldProperty property = getItemProperty(columnName);
		if (property == null)
		{
			logger.warn("No property found for {}. Skip setting the value.", columnName);
			return;
		}

		property.setValue(value);
	}

	public Object getPropertyValue(final Object columnName)
	{
		final DataFieldProperty property = getItemProperty(columnName);
		if (property == null)
		{
			logger.warn("No property found for {}. Returning null.", columnName);
			return null;
		}

		return property.getValue();
	}

	public String getPropertyValueAsString(final Object columnName)
	{
		final DataFieldProperty property = getItemProperty(columnName);
		if (property == null)
		{
			return null;
		}

		final Object value = property.getValue();
		return value == null ? null : value.toString();
	}

	final Object getKey()
	{
		// TODO: cache the key, update the key when the fields on which is depending were changed. also fire an "key changed event".
		final ArrayKeyBuilder keyBuilder = Util.mkKey();
		for (final DataFieldPropertyDescriptor idDescriptor : descriptor.getIdFieldsDescriptors())
		{
			final String columnName = idDescriptor.getColumnName();
			final Object value = getPropertyValue(columnName);
			keyBuilder.append(value);
		}

		return keyBuilder.build();
	}

	public void setFrom(final DataRowItem row)
	{
		for (final Object columnName : getItemPropertyIds())
		{
			final Object value = row == null ? null : row.getPropertyValue(columnName);
			setPropertyValue(columnName, value);
		}
		// TODO Auto-generated method stub

	}
}
