package de.metas.ui.web.vaadin.window.model;

import org.adempiere.ad.expression.api.ILogicExpression;

import com.google.common.base.Objects;
import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractProperty;

import de.metas.ui.web.vaadin.window.descriptor.DataFieldPropertyDescriptor;

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
public class DataFieldProperty extends AbstractProperty<Object>
{
	public static final DataFieldProperty cast(Property<?> property)
	{
		return (DataFieldProperty)property;
	}
	
	private final DataFieldPropertyDescriptor descriptor;

	private Object value = null;

	public DataFieldProperty(final DataFieldPropertyDescriptor descriptor)
	{
		super();
		this.descriptor = descriptor;
	}

	public DataFieldPropertyDescriptor getDescriptor()
	{
		return descriptor;
	}
	
	public String getPropertyId()
	{
		return descriptor.getColumnName();
	}

	@Override
	public Object getValue()
	{
		return value;
	}

	@Override
	public void setValue(Object newValue) throws com.vaadin.data.Property.ReadOnlyException
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
		return descriptor.getValueClass();
	}

	public String getCaption()
	{
		return descriptor.getCaption();
	}

	public boolean isDisplayed()
	{
		return descriptor.isDisplayable();
	}
	
	@Override
	public boolean isReadOnly()
	{
		return super.isReadOnly();
	}
	
	public ILogicExpression getReadOnlyLogic()
	{
		return descriptor.getReadOnlyLogic();
	}

	public void updateReadOnly(final DataRowItemEditingContext editingContext)
	{
		// TODO Auto-generated method stub
		
	}
}
