package de.metas.ui.web.vaadin.window.prototype.order.editor;

import java.util.Collection;
import java.util.Map;

import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;

import de.metas.ui.web.vaadin.window.prototype.order.GridRowId;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyName;
import de.metas.ui.web.vaadin.window.prototype.order.model.PropertyValuesDTO;

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


@SuppressWarnings("serial")
final class GridRowItem implements Item, Property.ValueChangeNotifier
{
	private final GridRowId rowId;
	private final Map<PropertyName, GridCellProperty> cells;

	GridRowItem(final GridRowId rowId, final Map<PropertyName, PropertyDescriptor> cellDescriptors)
	{
		super();
		this.rowId = rowId;
		
		final ImmutableMap.Builder<PropertyName, GridCellProperty> cellsBuilder = ImmutableMap.<PropertyName, GridCellProperty>builder();
		for (final Map.Entry<PropertyName, PropertyDescriptor> e : cellDescriptors.entrySet())
		{
			final PropertyName cellName = e.getKey();
			final PropertyDescriptor cellDescriptor = e.getValue();
			final GridCellProperty cell = new GridCellProperty(rowId, cellDescriptor);
			cellsBuilder.put(cellName, cell);
		}
		
		this.cells = cellsBuilder.build();
	}
	
	public GridRowId getRowId()
	{
		return rowId;
	}

	@Override
	public GridCellProperty getItemProperty(Object id)
	{
		return cells.get(id);
	}

	@Override
	public Collection<PropertyName> getItemPropertyIds()
	{
		return cells.keySet();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean addItemProperty(Object id, Property property) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeItemProperty(Object id) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}
	
	public void setValues(final PropertyValuesDTO values)
	{
		for (final Map.Entry<PropertyName, Object> e : values.entrySet())
		{
			final PropertyName propertyName = e.getKey();
			final Object value = e.getValue();
			setValue(propertyName, value);
		}
	}
	
	public void setValue(final PropertyName propertyName, Object value)
	{
		final GridCellProperty item = getItemProperty(propertyName);
		if(item == null)
		{
			// TODO
			return;
		}
		
		if(NullValue.isNull(value))
		{
			value = null;
		}
		
		item.setValue(value);
	}

	@Override
	public void addValueChangeListener(ValueChangeListener listener)
	{
		for (final GridCellProperty cell : cells.values())
		{
			cell.addValueChangeListener(listener);
		}
	}

	@Override
	@Deprecated
	public void addListener(ValueChangeListener listener)
	{
		addValueChangeListener(listener);
	}

	@Override
	public void removeValueChangeListener(ValueChangeListener listener)
	{
		for (final GridCellProperty cell : cells.values())
		{
			cell.removeValueChangeListener(listener);
		}
	}

	@Override
	@Deprecated
	public void removeListener(ValueChangeListener listener)
	{
		removeValueChangeListener(listener);
	}
}