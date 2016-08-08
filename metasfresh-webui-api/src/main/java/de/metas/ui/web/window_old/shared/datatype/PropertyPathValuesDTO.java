package de.metas.ui.web.window_old.shared.datatype;

import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.json.JsonHelper;

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
public final class PropertyPathValuesDTO implements Serializable
{
	@JsonCreator
	public static final PropertyPathValuesDTO of(@JsonProperty("values") final List<PropertyPathValue> valuesList)
	{
		if (valuesList == null || valuesList.isEmpty())
		{
			return EMPTY;
		}

		return new PropertyPathValuesDTO(ImmutableList.copyOf(valuesList));
	}

	public static final PropertyPathValuesDTO of()
	{
		return EMPTY;
	}

	public static final Builder builder()
	{
		return new Builder();
	}

	private static final transient PropertyPathValuesDTO EMPTY = new PropertyPathValuesDTO();

	@JsonProperty("values")
	private final List<PropertyPathValue> valuesList;

	@JsonIgnore
	private final ImmutableMap<PropertyPath, Object> valuesMap;

	/** empty constructor */
	private PropertyPathValuesDTO()
	{
		super();
		valuesList = ImmutableList.of();
		valuesMap = ImmutableMap.of();
	}

	/** JSON constructor */
	private PropertyPathValuesDTO(final List<PropertyPathValue> list)
	{
		super();
		valuesList = list == null ? ImmutableList.of() : ImmutableList.copyOf(list);

		//
		// Build values map
		final ImmutableMap.Builder<PropertyPath, Object> mapBuilder = ImmutableMap.builder();
		for (final PropertyPathValue propertyPathValue : valuesList)
		{
			final PropertyPath propertyPath = propertyPathValue.getPropertyPath();
			final Object value = NullValue.makeNotNull(propertyPathValue.getValue());
			mapBuilder.put(propertyPath, value);
		}
		valuesMap = mapBuilder.build();
	}

	/** Builder constructor */
	private PropertyPathValuesDTO(final ImmutableList<PropertyPathValue> list, final ImmutableMap<PropertyPath, Object> map)
	{
		super();
		valuesList = list;
		valuesMap = map;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(valuesMap)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return valuesList.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}

		if (!(obj instanceof PropertyPathValuesDTO))
		{
			return false;
		}

		final PropertyPathValuesDTO other = (PropertyPathValuesDTO)obj;
		return Objects.equals(this.valuesList, other.valuesList);
	}

	public boolean containsKey(final PropertyPath propertyPath)
	{
		return valuesMap.containsKey(propertyPath);
	}

	public Object get(final PropertyPath propertyPath)
	{
		return valuesMap.get(propertyPath);
	}

	public Set<Entry<PropertyPath, Object>> entrySet()
	{
		return valuesMap.entrySet();
	}

	public static final class Builder
	{
		private final ImmutableList.Builder<PropertyPathValue> listBuilder = ImmutableList.builder();
		private final ImmutableMap.Builder<PropertyPath, Object> mapBuilder = ImmutableMap.builder();

		private Builder()
		{
			super();
		}

		public PropertyPathValuesDTO build()
		{
			final ImmutableList<PropertyPathValue> list = listBuilder.build();
			if (list.isEmpty())
			{
				return EMPTY;
			}

			final ImmutableMap<PropertyPath, Object> map = mapBuilder.build();

			return new PropertyPathValuesDTO(list, map);
		}

		public Builder put(final PropertyPath propertyPath, final Object value)
		{
			JsonHelper.validateValueType(propertyPath, value);

			final Object valueNotNull = NullValue.makeNotNull(value);
			mapBuilder.put(propertyPath, valueNotNull);

			final PropertyPathValue propertyPathValue = PropertyPathValue.of(propertyPath, valueNotNull);
			listBuilder.add(propertyPathValue);

			return this;
		}
	}
}
