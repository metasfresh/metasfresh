package de.metas.ui.web.window.shared.datatype;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.json.JsonHelper;
import de.metas.ui.web.window.PropertyName;

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

/**
 * {@link PropertyName} and its value DTO.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
@JsonSerialize(using = PropertyValuesDTO_JSONSerializer.class)
@JsonDeserialize(using = PropertyValuesDTO_JSONDeserializer.class)
public final class PropertyValuesDTO implements Serializable
{
	public static final PropertyValuesDTO of(final Map<PropertyName, Object> values)
	{
		if (values == null || values.isEmpty())
		{
			return EMPTY;
		}

		return new PropertyValuesDTO(values);
	}

	public static final PropertyValuesDTO of()
	{
		return EMPTY;
	}

	public static final PropertyValuesDTO copyOf(final PropertyValuesDTO values)
	{
		// NOTE: we assume the PropertyValuesDTO are immutable
		return values;
	}

	public static final Builder builder()
	{
		return new Builder();
	}

	private static final transient PropertyValuesDTO EMPTY = new PropertyValuesDTO();
	private final ImmutableMap<PropertyName, Object> valuesMap;

	/** empty constructor */
	private PropertyValuesDTO()
	{
		super();
		valuesMap = ImmutableMap.of();
	}

	/** Builder constructor */
	private PropertyValuesDTO(@JsonProperty("values") final Map<PropertyName, Object> values)
	{
		super();
		valuesMap = values == null ? ImmutableMap.of() : ImmutableMap.copyOf(values);
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
		return valuesMap.hashCode();
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

		if (!(obj instanceof PropertyValuesDTO))
		{
			return false;
		}

		final PropertyValuesDTO other = (PropertyValuesDTO)obj;
		return Objects.equals(valuesMap, other.valuesMap);
	}

	public boolean containsKey(final PropertyName propertyName)
	{
		return valuesMap.containsKey(propertyName);
	}

	public Object get(final PropertyName propertyName)
	{
		return valuesMap.get(propertyName);
	}

	public Set<Map.Entry<PropertyName, Object>> entrySet()
	{
		return valuesMap.entrySet();
	}

	public static final class Builder
	{
		private final ImmutableMap.Builder<PropertyName, Object> valuesBuilder = ImmutableMap.builder();

		private Builder()
		{
			super();
		}

		public PropertyValuesDTO build()
		{
			final ImmutableMap<PropertyName, Object> values = valuesBuilder.build();
			if (values.isEmpty())
			{
				return EMPTY;
			}
			return new PropertyValuesDTO(values);
		}

		public Builder put(final PropertyName propertyName, final Object value)
		{
			JsonHelper.validateValueType(propertyName, value);

			final Object valueNotNull = NullValue.makeNotNull(value);
			valuesBuilder.put(propertyName, valueNotNull);
			return this;
		}
	}

}
