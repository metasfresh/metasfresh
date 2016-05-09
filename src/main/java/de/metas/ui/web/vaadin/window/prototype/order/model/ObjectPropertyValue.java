package de.metas.ui.web.vaadin.window.prototype.order.model;

import java.util.Map;

import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.util.Check;

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

public class ObjectPropertyValue implements PropertyValue
{
	private final PropertyName propertyName;
	private final String composedValuePartName;
	private final PropertyNameDependenciesMap dependencies;

	private final IStringExpression defaultValueExpression;
	private final Object initialValue;
	private Object value;

	private final ImmutableMap<PropertyName, PropertyValue> _childPropertyValues;
	private final boolean readOnlyForUser;

	ObjectPropertyValue(final PropertyValueBuilder builder)
	{
		super();
		propertyName = builder.getPropertyName();
		composedValuePartName = builder.getComposedValuePartName();
		_childPropertyValues = ImmutableMap.copyOf(builder.getChildPropertyValues());
		
		defaultValueExpression = builder.getDefaultValueExpression();
		initialValue = builder.getInitialValue();
		value = initialValue;
		
		readOnlyForUser = builder.isReadOnlyForUser();
		
//		final ILogicExpression readonlyLogic = builder.getReadonlyLogic();
//		final ILogicExpression mandatoryLogic = builder.getMandatoryLogic();
//		final ILogicExpression displayLogic = builder.getDisplayLogic();
		dependencies = PropertyNameDependenciesMap.builder()
//				.add(propertyName, PropertyName.toSet(readonlyLogic.getParameters()), DependencyType.ReadonlyLogic)
//				.add(propertyName, PropertyName.toSet(mandatoryLogic.getParameters()), DependencyType.MandatoryLogic)
//				.add(propertyName, PropertyName.toSet(displayLogic.getParameters()), DependencyType.DisplayLogic)
				.build();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add(propertyName.toString(), value)
				.add("partName", composedValuePartName)
				.toString();
	}

	@Override
	public PropertyName getName()
	{
		return propertyName;
	}

	@Override
	public PropertyNameDependenciesMap getDependencies()
	{
		return dependencies;
	}

	@Override
	public void onDependentPropertyValueChanged(final DependencyValueChangedEvent event)
	{
		// nothing on this level
	}

	public IStringExpression getDefaultValueExpression()
	{
		return defaultValueExpression;
	}

	@Override
	public Object getValue()
	{
		return value;
	}

	@Override
	public Optional<String> getValueAsString()
	{
		final Object value = this.value;
		return Optional.fromNullable(value == null ? null : value.toString());
	}

	@Override
	public void setValue(final Object value)
	{
		this.value = value;
	}

	public Object getInitialValue()
	{
		return initialValue;
	}

	@Override
	public Map<PropertyName, PropertyValue> getChildPropertyValues()
	{
		return _childPropertyValues;
	}

	@Override
	public String getComposedValuePartName()
	{
		return composedValuePartName;
	}

	@Override
	public boolean isChanged()
	{
		return !Check.equals(value, initialValue);
	}
	
	@Override
	public boolean isReadOnlyForUser()
	{
		return readOnlyForUser;
	}
}