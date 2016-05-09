package de.metas.ui.web.vaadin.window.prototype.order.model;

import java.util.Map;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

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

public final class ConstantPropertyValue implements PropertyValue
{
	public static final ConstantPropertyValue of(final PropertyName name, final Object value)
	{
		return new ConstantPropertyValue(name, value);
	}

	public static boolean isConstant(final PropertyValue propertyValue)
	{
		return propertyValue instanceof ConstantPropertyValue;
	}

	private final PropertyName propertyName;
	private final Object constantValue;

	private ConstantPropertyValue(final PropertyName name, final Object value)
	{
		super();
		propertyName = name;
		constantValue = value;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add(propertyName.toString(), constantValue)
				.toString();
	}

	@Override
	public String getComposedValuePartName()
	{
		return null;
	}

	@Override
	public PropertyNameDependenciesMap getDependencies()
	{
		return PropertyNameDependenciesMap.EMPTY;
	}

	@Override
	public void onDependentPropertyValueChanged(final DependencyValueChangedEvent event)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<PropertyName, PropertyValue> getChildPropertyValues()
	{
		return ImmutableMap.of();
	}

	@Override
	public void setValue(final Object value)
	{
		throw new UnsupportedOperationException("Cannot set value '" + value + "' to " + this + " because it's constant.");
	}

	@Override
	public Optional<String> getValueAsString()
	{
		if (constantValue == null)
		{
			return Optional.absent();
		}
		return Optional.of(constantValue.toString());
	}

	@Override
	public Object getValue()
	{
		return constantValue;
	}

	@Override
	public PropertyName getName()
	{
		return propertyName;
	}

	@Override
	public boolean isChanged()
	{
		return false;
	}
	
	@Override
	public boolean isReadOnlyForUser()
	{
		return true;
	}
}