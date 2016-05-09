package de.metas.ui.web.vaadin.window.prototype.order.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimap;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;

import de.metas.ui.web.vaadin.window.prototype.order.PropertyName;

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

public class PropertyNameDependenciesMap
{
	public enum DependencyType
	{
		Value, ReadonlyLogic, DisplayLogic, MandatoryLogic,
	};

	public static Builder builder()
	{
		return new Builder();
	}

	public static final PropertyNameDependenciesMap EMPTY = new PropertyNameDependenciesMap();

	private final Map<DependencyType, Multimap<PropertyName, PropertyName>> type2name2dependencies;

	/** empty constructor */
	private PropertyNameDependenciesMap()
	{
		super();
		type2name2dependencies = ImmutableMap.of();
	}

	private PropertyNameDependenciesMap(final Builder builder)
	{
		super();

		type2name2dependencies = builder.getType2Name2DependenciesMap();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(type2name2dependencies)
				.toString();
	}

	public String toStringX()
	{
		final StringBuilder sb = new StringBuilder();

		for (final Map.Entry<DependencyType, Multimap<PropertyName, PropertyName>> l1 : type2name2dependencies.entrySet())
		{
			final DependencyType dependencyType = l1.getKey();
			final Multimap<PropertyName, PropertyName> name2dependencies = l1.getValue();
			for (final Map.Entry<PropertyName, PropertyName> l2 : name2dependencies.entries())
			{
				final PropertyName dependsOn = l2.getKey();
				final PropertyName propertyName = l2.getValue();

				if (sb.length() > 0)
				{
					sb.append("\n");
				}
				sb.append(dependencyType).append(": ").append(dependsOn).append(" -> ").append(propertyName);
			}
		}

		return sb.toString();
	}

	public Collection<PropertyName> getPropertyNamesWhichDependOn(final PropertyName propertyName, final DependencyType dependencyType)
	{
		final Multimap<PropertyName, PropertyName> name2dependencies = type2name2dependencies.get(dependencyType);
		if (name2dependencies == null)
		{
			return ImmutableSet.of();
		}
		return name2dependencies.get(propertyName);
	}

	public void consume(final PropertyName propertyName, final BiConsumer<PropertyName, Set<DependencyType>> consumer)
	{
		for (final DependencyType dependencyType : DependencyType.values())
		{
			final Multimap<PropertyName, PropertyName> name2dependencies = type2name2dependencies.get(dependencyType);
			if (name2dependencies == null)
			{
				continue;
			}
			for (final PropertyName dependentPropertyName : name2dependencies.get(propertyName))
			{
				consumer.accept(dependentPropertyName, ImmutableSet.of(dependencyType));
			}
		}
	}

	public static final class Builder
	{
		private final Map<DependencyType, ImmutableSetMultimap.Builder<PropertyName, PropertyName>> type2name2dependencies = new HashMap<>();

		private Builder()
		{
			super();
		}

		public PropertyNameDependenciesMap build()
		{
			if (type2name2dependencies.isEmpty())
			{
				return EMPTY;
			}

			return new PropertyNameDependenciesMap(this);
		}

		private Map<DependencyType, Multimap<PropertyName, PropertyName>> getType2Name2DependenciesMap()
		{
			final ImmutableMap.Builder<DependencyType, Multimap<PropertyName, PropertyName>> builder = ImmutableMap.builder();
			for (final Entry<DependencyType, ImmutableSetMultimap.Builder<PropertyName, PropertyName>> e : type2name2dependencies.entrySet())
			{
				final DependencyType dependencyType = e.getKey();
				final Multimap<PropertyName, PropertyName> name2dependencies = e.getValue().build();
				if (name2dependencies.isEmpty())
				{
					continue;
				}

				builder.put(dependencyType, name2dependencies);
			}

			return builder.build();
		}

		public Builder add(final PropertyName propertyName, final PropertyName dependsOnPropertyName, final DependencyType dependencyType)
		{
			ImmutableSetMultimap.Builder<PropertyName, PropertyName> name2dependencies = type2name2dependencies.get(dependencyType);
			if (name2dependencies == null)
			{
				name2dependencies = ImmutableSetMultimap.builder();
				type2name2dependencies.put(dependencyType, name2dependencies);
			}

			name2dependencies.put(dependsOnPropertyName, propertyName);

			return this;
		}

		public Builder add(final PropertyName propertyName, final Set<PropertyName> dependsOnPropertyNames, final DependencyType dependencyType)
		{
			if (dependsOnPropertyNames == null || dependsOnPropertyNames.isEmpty())
			{
				return this;
			}

			ImmutableSetMultimap.Builder<PropertyName, PropertyName> name2dependencies = type2name2dependencies.get(dependencyType);
			if (name2dependencies == null)
			{
				name2dependencies = ImmutableSetMultimap.builder();
				type2name2dependencies.put(dependencyType, name2dependencies);
			}

			for (final PropertyName dependsOnPropertyName : dependsOnPropertyNames)
			{
				name2dependencies.put(dependsOnPropertyName, propertyName);
			}

			return this;
		}

		public Builder add(final PropertyNameDependenciesMap dependencies)
		{
			if (dependencies == null)
			{
				return this;
			}

			for (final Map.Entry<DependencyType, Multimap<PropertyName, PropertyName>> l1 : dependencies.type2name2dependencies.entrySet())
			{
				final DependencyType dependencyType = l1.getKey();

				ImmutableSetMultimap.Builder<PropertyName, PropertyName> name2dependencies = type2name2dependencies.get(dependencyType);
				if (name2dependencies == null)
				{
					name2dependencies = ImmutableSetMultimap.builder();
					type2name2dependencies.put(dependencyType, name2dependencies);
				}

				name2dependencies.putAll(l1.getValue());
			}

			return this;
		}
	}
}
