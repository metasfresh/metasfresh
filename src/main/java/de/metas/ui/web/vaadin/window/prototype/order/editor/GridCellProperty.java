package de.metas.ui.web.vaadin.window.prototype.order.editor;

import com.google.common.base.Objects;
import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractProperty;

import de.metas.ui.web.vaadin.window.prototype.order.GridRowId;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyName;

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
class GridCellProperty extends AbstractProperty<Object>
{
	private final PropertyDescriptor descriptor;
	private final GridRowId rowId;
	private Object value;

	public GridCellProperty(GridRowId rowId, final PropertyDescriptor descriptor)
	{
		super();
		this.rowId = rowId;
		this.descriptor = descriptor;
	}
	
	public GridRowId getRowId()
	{
		return rowId;
	}
	
	public PropertyName getPropertyName()
	{
		return descriptor.getPropertyName();
	}

	@Override
	public Object getValue()
	{
		return value;
	}

	@Override
	public void setValue(final Object newValue) throws com.vaadin.data.Property.ReadOnlyException
	{
		// Checks the mode
		if (isReadOnly())
		{
			throw new Property.ReadOnlyException();
		}

		if (Objects.equal(this.value, newValue))
		{
			return;
		}
		
		this.value = newValue;
		fireValueChange();
	}

	@Override
	public Class<? extends Object> getType()
	{
		return descriptor.getValueType();
	}
}