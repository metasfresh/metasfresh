package de.metas.ui.web.window;

import java.io.Serializable;
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
public final class PropertyNameSet implements Serializable
{
	@JsonCreator
	public static final PropertyNameSet of(@JsonProperty("n") final Set<PropertyName> propertyNames)
	{
		if (propertyNames == null || propertyNames.isEmpty())
		{
			return EMPTY;
		}

		return new PropertyNameSet(propertyNames);
	}

	public static final PropertyNameSet of(final PropertyName propertyName)
	{
		return new PropertyNameSet(ImmutableSet.of(propertyName));
	}

	public static final transient PropertyNameSet EMPTY = new PropertyNameSet(ImmutableSet.of());

	@JsonProperty("n")
	private final ImmutableSet<PropertyName> propertyNames;

	private PropertyNameSet(final Set<PropertyName> propertyNames)
	{
		super();
		this.propertyNames = ImmutableSet.copyOf(propertyNames);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(propertyNames)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return propertyNames.hashCode();
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

		if (!(obj instanceof PropertyNameSet))
		{
			return false;
		}

		final PropertyNameSet other = (PropertyNameSet)obj;

		return Objects.equal(propertyNames, other.propertyNames);
	}

	@JsonIgnore
	public boolean isEmpty()
	{
		return propertyNames.isEmpty();
	}

	@JsonIgnore
	public Set<PropertyName> asSet()
	{
		return propertyNames;
	}

	public boolean contains(final PropertyName propertyName)
	{
		return propertyNames.contains(propertyName);
	}
}
