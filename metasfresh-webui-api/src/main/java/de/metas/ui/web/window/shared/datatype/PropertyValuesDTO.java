package de.metas.ui.web.window.shared.datatype;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.shared.command.LookupDataSourceQueryCommand;

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

	@JsonProperty("values") 
	private final ImmutableMap<PropertyName, Object> values;

	/** empty constructor */
	private PropertyValuesDTO()
	{
		super();
		values = ImmutableMap.of();
	}

	private PropertyValuesDTO(@JsonProperty("values") final Map<PropertyName, Object> values)
	{
		super();
		this.values = values == null ? ImmutableMap.of() : ImmutableMap.copyOf(values);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(values)
				.toString();
	}

	public boolean containsKey(final PropertyName propertyName)
	{
		return values.containsKey(propertyName);
	}

	public Object get(final PropertyName propertyName)
	{
		return values.get(propertyName);
	}

	public Set<Entry<PropertyName, Object>> entrySet()
	{
		return values.entrySet();
	}

	public static final class Builder
	{
		private static final ImmutableList<Class<?>> allowedValueTypes = ImmutableList.of( //
				NullValue.class //
				, GridRowId.class //
				, String.class //
				, Integer.class, BigDecimal.class //
				, java.util.Date.class, Timestamp.class // FIXME: i think we shall avoid Timestamp, but since Timestamp extends Date, i think it's fine for now
				, Boolean.class //
				, LookupDataSourceQueryCommand.class //
				, PropertyValuesListDTO.class //
				, LazyPropertyValuesListDTO.class // NOTE: this one is used only in model
				, LookupValue.class //
				, ComposedValue.class //
		);

		private static final ImmutableList<Class<?>> allowedBaseValueTypes = ImmutableList.of( //
				LookupDataSourceQueryCommand.class // FIXME: see de.metas.ui.web.vaadin.window.model.LookupPropertyValue.LookupDataSourceServiceDTOImpl 
		);

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
			validateValueType(propertyName, value);

			final Object valueNotNull = NullValue.valueOrNull(value);
			valuesBuilder.put(propertyName, valueNotNull);
			return this;
		}

		private final void validateValueType(final PropertyName propertyName, final Object value)
		{
			// NOTE: the aim is to have primitive or well known values types because, later, we want to serialize to JSON
			
			//
			if (value == null)
			{
				return; // OK
			}

			//
			final Class<? extends Object> valueClass = value.getClass();
			if (allowedValueTypes.contains(valueClass))
			{
				return; // OK
			}
			
			//
			for (final Class<?> baseType : allowedBaseValueTypes)
			{
				if (baseType.isAssignableFrom(valueClass))
				{
					return; // OK
				}
			}

			//
			throw new IllegalArgumentException("Invalid " + propertyName + "=" + value + " (" + valueClass + ")"
					+ "\n Allowed types are: " + allowedValueTypes);
		}
	}

}
