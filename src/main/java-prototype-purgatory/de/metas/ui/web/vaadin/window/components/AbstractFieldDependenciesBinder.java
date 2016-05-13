package de.metas.ui.web.vaadin.window.components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.util.collections.IdentityHashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.vaadin.data.Property;
import com.vaadin.data.util.TransactionalPropertyWrapper;
import com.vaadin.ui.Field;

import de.metas.ui.web.vaadin.window.descriptor.DataFieldPropertyDescriptor;
import de.metas.ui.web.vaadin.window.model.DataFieldProperty;

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

public abstract class AbstractFieldDependenciesBinder implements IFieldDependenciesBinder
{
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private IFieldGroup fieldGroup = null;
	private IFieldGroupContext fieldGroupContext = null;

	/** Multimap of (depends on PropertyId) to (dependent field) */
	private final ListMultimap<Object, Property<?>> dependsOnPropertyId2dependentProperty = ArrayListMultimap.create();

	private final Map<Property<?>, Object> dependsOnProperty2propertyId = new HashMap<>();

	@SuppressWarnings("serial")
	private final Property.ValueChangeListener propertyListener = new Property.ValueChangeListener()
	{
		@Override
		public void valueChange(final Property.ValueChangeEvent event)
		{
			final Property<?> dependsOnProperty = event.getProperty();
			final Object dependsOnPropertyId = dependsOnProperty2propertyId.get(dependsOnProperty);
			if (dependsOnPropertyId == null)
			{
				// shall not happen
				return;
			}

			for (final Property<?> dependant : dependsOnPropertyId2dependentProperty.get(dependsOnPropertyId))
			{
				updateProperty(dependant);
			}
		}
	};
	
	public final IFieldGroup getFieldGroup()
	{
		return fieldGroup;
	}

	public final IFieldGroupContext getFieldGroupContext()
	{
		return fieldGroupContext;
	}

	@Override
	public final void bind(final IFieldGroup fieldGroup)
	{
		unbind();

		final Set<Property<?>> dependentProperties = new IdentityHashSet<>();
		for (final Object propertyId : fieldGroup.getPropertyIds())
		{
			final Property<?> dependentProperty = fieldGroup.getProperty(propertyId);
			final DataFieldPropertyDescriptor dependentDescriptor = fieldGroup.getFieldDescriptor(propertyId);
			if (dependentDescriptor == null)
			{
				continue;
			}

			dependentProperties.add(dependentProperty);

			for (final Object dependsOnPropertyId : getDependsOnPropertyIds(dependentProperty, propertyId))
			{
				final Property<?> dependsOnProperty = fieldGroup.getProperty(dependsOnPropertyId);
				if (dependsOnProperty == null)
				{
					continue;
				}

				dependsOnProperty2propertyId.put(dependsOnProperty, dependsOnPropertyId);
				dependsOnPropertyId2dependentProperty.put(dependsOnPropertyId, dependentProperty);
				getValueChangeNotifier(dependsOnProperty).addValueChangeListener(propertyListener);

				// logger.debug("Added readonly dependency listener: {} -> {}", dependsOnPropertyId, propertyId);
				System.out.println("" + getClass().getSimpleName() + ": added dependency: " + dependsOnPropertyId + " -> " + propertyId);
			}
		}

		this.fieldGroup = fieldGroup;
		this.fieldGroupContext = fieldGroup.getFieldGroupContext();

		//
		// Update field statuses
		for (final Property<?> dependentField : dependentProperties)
		{
			updateProperty(dependentField);
		}

		//
		// Call after bind
		afterBind();
	}

	protected void afterBind()
	{
		// nothing
	}

	@Override
	public final void unbind()
	{
		for (final Property<?> field : dependsOnProperty2propertyId.keySet())
		{
			getValueChangeNotifier(field).removeValueChangeListener(propertyListener);
		}

		dependsOnProperty2propertyId.clear();
		dependsOnPropertyId2dependentProperty.clear();

		fieldGroupContext = null;

		afterUnbind();
	}

	protected void afterUnbind()
	{
		// nothing at this level
	}

	protected static final DataFieldPropertyDescriptor getFieldDescriptor(final Property<?> field)
	{
		Property<?> propertyActual;
		if (field instanceof Field)
		{
			propertyActual = ((Field<?>)field).getPropertyDataSource();
		}
		else
		{
			propertyActual = field;
		}
		if (propertyActual instanceof TransactionalPropertyWrapper)
		{
			propertyActual = ((TransactionalPropertyWrapper<?>)propertyActual).getWrappedProperty();
		}
		return DataFieldProperty.cast(propertyActual).getDescriptor();
	}

	protected static final Property.ValueChangeNotifier getValueChangeNotifier(final Property<?> property)
	{
		return (Property.ValueChangeNotifier)property;
	}

	protected static final Field<?> getField(final Property<?> property)
	{
		if (property instanceof Field)
		{
			return (Field<?>)property;
		}
		return null;
	}

	protected abstract void updateProperty(final Property<?> property);

	protected abstract List<? extends Object> getDependsOnPropertyIds(final Property<?> field, final Object propertyId);
}
