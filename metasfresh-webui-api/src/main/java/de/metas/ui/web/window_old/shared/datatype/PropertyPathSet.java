package de.metas.ui.web.window_old.shared.datatype;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;

/*
 * #%L
 * metasfresh-webui-api
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
public class PropertyPathSet implements Serializable, Iterable<PropertyPath>
{
	@JsonCreator
	public static final PropertyPathSet of(@JsonProperty("n") final Set<PropertyPath> propertyPaths)
	{
		if (propertyPaths == null || propertyPaths.isEmpty())
		{
			return EMPTY;
		}

		return new PropertyPathSet(propertyPaths);
	}

	public static final PropertyPathSet of(final PropertyPath PropertyPath)
	{
		return new PropertyPathSet(ImmutableSet.of(PropertyPath));
	}

	public static final transient PropertyPathSet EMPTY = new PropertyPathSet(ImmutableSet.of());

	@JsonProperty("n")
	private final ImmutableSet<PropertyPath> propertyPaths;

	private PropertyPathSet(final Set<PropertyPath> PropertyPaths)
	{
		super();
		this.propertyPaths = ImmutableSet.copyOf(PropertyPaths);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(propertyPaths)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return propertyPaths.hashCode();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}

		if (!(obj instanceof PropertyPathSet))
		{
			return false;
		}

		final PropertyPathSet other = (PropertyPathSet)obj;

		return Objects.equal(propertyPaths, other.propertyPaths);
	}

	@JsonIgnore
	public boolean isEmpty()
	{
		return propertyPaths.isEmpty();
	}

	@JsonIgnore
	public Set<PropertyPath> asSet()
	{
		return propertyPaths;
	}

	public boolean contains(final PropertyPath propertyPath)
	{
		return propertyPaths.contains(propertyPath);
	}

	@Override
	@JsonIgnore
	public Iterator<PropertyPath> iterator()
	{
		return propertyPaths.iterator();
	}

}
