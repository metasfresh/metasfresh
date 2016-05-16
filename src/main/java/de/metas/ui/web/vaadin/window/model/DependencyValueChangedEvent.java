package de.metas.ui.web.vaadin.window.model;

import java.util.Set;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.vaadin.window.PropertyName;
import de.metas.ui.web.vaadin.window.model.PropertyNameDependenciesMap.DependencyType;

/*
 * #%L
 * metasfresh-webui
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

public class DependencyValueChangedEvent
{
	public static final DependencyValueChangedEvent of(final PropertyValueCollection values, final PropertyName changedPropertyName, final Set<DependencyType> dependencyTypes)
	{
		return new DependencyValueChangedEvent(values, changedPropertyName, dependencyTypes);
	}

	public static final DependencyValueChangedEvent of(final PropertyValueCollection values, final PropertyName changedPropertyName, final DependencyType dependencyType)
	{
		return new DependencyValueChangedEvent(values, changedPropertyName, ImmutableSet.of(dependencyType));
	}
	
	public static final DependencyValueChangedEvent any(final PropertyValueCollection values)
	{
		final PropertyName changedPropertyName = null;
		return new DependencyValueChangedEvent(values, changedPropertyName, PropertyNameDependenciesMap.DEPENDENCYTYPE_ALL);
	}

	private final PropertyValueCollection values;
	private final PropertyName changedPropertyName;
	private final Set<DependencyType> dependencyTypes;

	private DependencyValueChangedEvent(final PropertyValueCollection values, final PropertyName changedPropertyName, final Set<DependencyType> dependencyTypes)
	{
		super();
		this.values = values;
		this.changedPropertyName = changedPropertyName;
		this.dependencyTypes = ImmutableSet.copyOf(dependencyTypes);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("changedPropertyName", changedPropertyName)
				.add("dependencyTypes", dependencyTypes)
				.toString();
	}
	
	public PropertyValueCollection getValues()
	{
		return values;
	}

	public PropertyName getChangedPropertyName()
	{
		return changedPropertyName;
	}

	public boolean isDependencyType(final DependencyType dependencyType)
	{
		return dependencyTypes.contains(dependencyType);
	}
}
