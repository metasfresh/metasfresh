package de.metas.ui.web.vaadin.window.prototype.order.model;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;
import de.metas.ui.web.vaadin.window.prototype.order.GridRowId;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyName;
import de.metas.ui.web.vaadin.window.prototype.order.WindowConstants;
import de.metas.ui.web.vaadin.window.prototype.order.editor.NullValue;

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

public final class GridRow
{
	/* package */static final GridRow of(final Collection<PropertyDescriptor> columnDescriptors, final PropertyValuesDTO initialValues)
	{
		return new GridRow(columnDescriptors, initialValues);
	}

	private static final Logger logger = LogManager.getLogger(GridRow.class);

	public static final GridRow NULL = new GridRow();

	private final GridRowId rowId;

	private final PropertyValueCollection propertyValues;

	/** empty constructor */
	private GridRow()
	{
		super();
		rowId = GridRowId.newRowId(); // TODO: have a custom one for this case
		propertyValues = PropertyValueCollection.EMPTY;
	}

	private GridRow(final Collection<PropertyDescriptor> columnDescriptors, final PropertyValuesDTO initialValues)
	{
		super();
		final PropertyValueCollection.Builder valuesBuilder = PropertyValueCollection.builder();

		// Row Id
		{
			rowId = GridRowId.newRowId();
			final PropertyValue rowIdPropertyValue = ConstantPropertyValue.of(WindowConstants.PROPERTYNAME_GridRowId, rowId);
			valuesBuilder.addProperty(rowIdPropertyValue);
		}

		for (final PropertyDescriptor columnDesc : columnDescriptors)
		{
			final PropertyName propertyName = columnDesc.getPropertyName();

			final Object initialValue = initialValues.get(propertyName);
			valuesBuilder.addPropertyRecursivelly(columnDesc, initialValue);
		}

		propertyValues = valuesBuilder.build();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("rowId", rowId)
				.add("propertyValues", propertyValues)
				.toString();
	}

	public GridRowId getRowId()
	{
		return rowId;
	}

	public PropertyValuesDTO getValuesAsMap()
	{
		return propertyValues.getValuesAsMap();
	}

	public void setValuesFromMap(final PropertyValuesDTO values)
	{
		for (final Map.Entry<PropertyName, Object> e : values.entrySet())
		{
			final PropertyName propertyName = e.getKey();
			final Object value = e.getValue();
			setValue(propertyName, value);
		}
	}

	public Object setValue(final PropertyName propertyName, Object value)
	{
		final PropertyValue propertyValue = propertyValues.getPropertyValue(propertyName);
		if (propertyValue == null)
		{
			logger.warn("Property {} not found in {}. Skip setting it.", propertyName, this);
			return null;
		}

		if (NullValue.isNull(value))
		{
			value = null;
		}

		final Object valueOld = propertyValue.getValue();
		propertyValue.setValue(value);
		return valueOld;
	}

	public Object getValue(final PropertyName propertyName)
	{
		final PropertyValue propertyValue = propertyValues.getPropertyValueOrNull(propertyName);
		if (propertyValue == null)
		{
			logger.warn("Property {} not found in {}. Skip setting it.", propertyName, this);
			return null;
		}

		final Object value = propertyValue.getValue();
		return value;
	}
}