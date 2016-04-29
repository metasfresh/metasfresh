package de.metas.ui.web.vaadin.window.prototype.order.model;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.vaadin.window.prototype.order.HARDCODED_Order;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptorType;
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


public final class PropertyValueBuilder
{
	public static final PropertyValueBuilder newBuilder()
	{
		return new PropertyValueBuilder();
	}
	
	private PropertyName propertyName;
	private String composedValuePartName;
	private final ImmutableMap.Builder<PropertyName, PropertyValue> _childPropertyValuesBuilder = ImmutableMap.builder();
	private ImmutableMap<PropertyName, PropertyValue> _childPropertyValues;
	private PropertyDescriptor propertyDescriptor;
	private Object _initialValue;
	private boolean _initialValueSet;

	private PropertyValueBuilder()
	{
		super();
	}

	public ObjectPropertyValue build()
	{
		final PropertyDescriptorType type = getType();
		if (type == PropertyDescriptorType.Tabular)
		{
			return new GridPropertyValue(this);
		}
		
		// FIXME: hardcoded
		if (HARDCODED_Order.ORDER_BPartnerAndAddress.equals(propertyName)
				|| HARDCODED_Order.ORDER_BillBPartnerAndAddress.equals(propertyName))
		{
			return new BPartnerAndAddressPropertyValue(this);
		}

		return new ObjectPropertyValue(this);
	}

	public PropertyValueBuilder setPropertyDescriptor(final PropertyDescriptor descriptor)
	{
		this.propertyDescriptor = descriptor;
		setPropertyName(descriptor.getPropertyName());
		this.composedValuePartName = descriptor.getComposedValuePartName();
		return this;
	}
	
	PropertyDescriptor getPropertyDescriptor()
	{
		return propertyDescriptor;
	}
	
	public PropertyValueBuilder setPropertyName(final PropertyName name)
	{
		this.propertyName = name;
		return this;
	}
	
	PropertyName getPropertyName()
	{
		return propertyName;
	}
	
	String getComposedValuePartName()
	{
		return composedValuePartName;
	}
	
	private PropertyDescriptorType getType()
	{
		if (propertyDescriptor != null)
		{
			return propertyDescriptor.getType();
		}
		
		return PropertyDescriptorType.Value;
	}

	public PropertyValueBuilder addChildPropertyValue(final PropertyValue propertyValue)
	{
		_childPropertyValuesBuilder.put(propertyValue.getName(), propertyValue);
		_childPropertyValues = null;
		return this;
	}
	
	Map<PropertyName, PropertyValue> getChildPropertyValues()
	{
		if(_childPropertyValues == null)
		{
			_childPropertyValues = _childPropertyValuesBuilder.build();
		}
		return _childPropertyValues;
	}
	
	public boolean isAddingChildPropertiesAllowed()
	{
		if (getType() == PropertyDescriptorType.Tabular)
		{
			return false;
		}
		
		return true;
	}
	
	public PropertyValueBuilder setInitialValue(final Object initialValue)
	{
		this._initialValue = initialValue;
		this._initialValueSet = true;
		return this;
	}
	
	boolean isInitialValueSet()
	{
		return _initialValueSet;
	}
	
	Object getInitialValue()
	{
		return _initialValue;
	}
}