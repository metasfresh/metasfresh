package de.metas.ui.web.vaadin.window.prototype.order.model;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimap;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableSet;

import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptorType;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyName;
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

public class PropertyValueCollection
{
	public static final PropertyValueCollection.Builder builder()
	{
		return new Builder();
	}

	public static final PropertyValueCollection EMPTY = new PropertyValueCollection();

	private final Map<PropertyName, PropertyValue> name2value;
	private final Multimap<PropertyName, PropertyName> name2dependencies;

	private PropertyValueCollection(final PropertyValueCollection.Builder builder)
	{
		super();
		name2value = builder.name2value.build();
		name2dependencies = builder.name2dependencies.build();

		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("Build PropertyValuesCollection" + TraceHelper.toStringRecursivelly(name2value.values()));
		System.out.println("--------------------------------------------------------------------------------");
	}

	/** empty builder */
	private PropertyValueCollection()
	{
		super();
		name2value = ImmutableMap.of();
		name2dependencies = ImmutableMultimap.of();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("name2value", name2value)
				.toString();
	}

	public PropertyValue getPropertyValue(final String propertyNameStr)
	{
		final PropertyName propertyName = PropertyName.of(propertyNameStr);
		return getPropertyValue(propertyName);
	}

	public PropertyValue getPropertyValue(final PropertyName propertyName)
	{
		final PropertyValue propertyValue = getPropertyValueOrNull(propertyName);
		if (propertyValue == null)
		{
			throw new RuntimeException("Property " + propertyName + " not found");
		}
		return propertyValue;
	}

	public PropertyValue getPropertyValueOrNull(final PropertyName propertyName)
	{
		return name2value.get(propertyName);
	}

	public Collection<PropertyName> getPropertyNamesWhichDependOn(final PropertyName propertyName)
	{
		return name2dependencies.get(propertyName);
	}

	public Collection<PropertyName> getAllDependentPropertyNames()
	{
		return ImmutableSet.copyOf(name2dependencies.values());
	}

	public Set<PropertyName> getPropertyNames()
	{
		return name2value.keySet();
	}

	public Collection<PropertyValue> getPropertyValues()
	{
		return name2value.values();
	}

	public Map<PropertyName, Object> getValuesAsMap()
	{
		return getValuesAsMap(name2value.keySet());
	}

	public Map<PropertyName, Object> getValuesAsMap(final Set<PropertyName> selectedPropertyNames)
	{
		final ImmutableMap.Builder<PropertyName, Object> propertiesMap = ImmutableMap.builder();
		for (final PropertyName propertyName : selectedPropertyNames)
		{
			final PropertyValue propertyValue = name2value.get(propertyName);
			if (propertyValue == null)
			{
				continue;
			}
			final Object value = NullValue.valueOrNull(propertyValue.getValue());
			propertiesMap.put(propertyName, value);
		}

		return propertiesMap.build();
	}

	public boolean hasChanges()
	{
		for (final PropertyValue propertyValue : getPropertyValues())
		{
			if (propertyValue.isChanged())
			{
				return true;
			}
		}

		return false;
	}

	public Iterable<Object> getAvailableValues(final PropertyName propertyName)
	{
		// TODO: implement
		return ImmutableList.of();
	}

	public static final class Builder
	{
		private final ImmutableMap.Builder<PropertyName, PropertyValue> name2value = ImmutableMap.builder();
		private final ImmutableSetMultimap.Builder<PropertyName, PropertyName> name2dependencies = ImmutableSetMultimap.builder();

		private Builder()
		{
			super();
		}

		public PropertyValueCollection build()
		{
			return new PropertyValueCollection(this);
		}

		public PropertyValueCollection.Builder addProperty(final PropertyValue propertyValue)
		{
			final PropertyName propertyName = propertyValue.getName();

			name2value.put(propertyName, propertyValue);

			//
			// Collect dependencies
			final Set<PropertyName> dependsOnPropertyNames = propertyValue.getDependsOnPropertyNames();
			if (dependsOnPropertyNames != null && !dependsOnPropertyNames.isEmpty())
			{
				for (final PropertyName dependsOnPropertyName : dependsOnPropertyNames)
				{
					name2dependencies.put(dependsOnPropertyName, propertyName);
				}
			}

			return this;
		}

		public PropertyValueCollection.Builder addPropertyRecursivelly(final PropertyDescriptor propertyDescriptor)
		{
			final PropertyValueBuilder parentBuilder = null;
			final Object initialValue = null;
			addPropertyRecursivelly(parentBuilder, propertyDescriptor, initialValue);
			return this;
		}

		public PropertyValueCollection.Builder addPropertyRecursivelly(final PropertyDescriptor propertyDescriptor, final Object initialValue)
		{
			final PropertyValueBuilder parentBuilder = null;
			addPropertyRecursivelly(parentBuilder, propertyDescriptor, initialValue);
			return this;
		}

		private void addPropertyRecursivelly(final PropertyValueBuilder parentBuilder, final PropertyDescriptor propertyDescriptor, final Object initialValue)
		{
			final PropertyValueBuilder builder;
			final boolean addingChildPropertiesAllowed;
			if (propertyDescriptor.isValueProperty()
					|| propertyDescriptor.getType() == PropertyDescriptorType.Tabular)
			{
				builder = PropertyValueBuilder.newBuilder()
						.setPropertyDescriptor(propertyDescriptor);

				if (initialValue != null)
				{
					builder.setInitialValue(initialValue);
				}

				if (propertyDescriptor.isValueProperty() && propertyDescriptor.getSqlLookupDescriptor() != null)
				{
					final PropertyValue lookupPropertyValue = new LookupPropertyValue(propertyDescriptor);
					builder.addChildPropertyValue(lookupPropertyValue);
					addProperty(lookupPropertyValue);
				}

				addingChildPropertiesAllowed = builder.isAddingChildPropertiesAllowed();
			}
			else
			{
				builder = null;
				addingChildPropertiesAllowed = true;
			}

			//
			// Create and add child properties, if allowed
			if (addingChildPropertiesAllowed)
			{
				for (final PropertyDescriptor childPropertyDescriptor : propertyDescriptor.getChildPropertyDescriptors())
				{
					final PropertyValueBuilder parentBuilderActual = builder == null ? parentBuilder : builder;
					final Object childInitialValue = null;
					addPropertyRecursivelly(parentBuilderActual, childPropertyDescriptor, childInitialValue);
				}
			}

			//
			// Build the property value
			if (builder != null)
			{
				final ObjectPropertyValue propertyValue = builder.build();
				addProperty(propertyValue);

				// Add the property value to parent, if any
				if (parentBuilder != null)
				{
					parentBuilder.addChildPropertyValue(propertyValue);
				}
			}
		}
	}
}