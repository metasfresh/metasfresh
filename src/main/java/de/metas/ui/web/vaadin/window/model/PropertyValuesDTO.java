package de.metas.ui.web.vaadin.window.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.base.MoreObjects;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;

import de.metas.ui.web.vaadin.window.GridRowId;
import de.metas.ui.web.vaadin.window.PropertyName;
import de.metas.ui.web.vaadin.window.editor.LookupValue;
import de.metas.ui.web.vaadin.window.editor.NullValue;

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

	private final ImmutableMap<PropertyName, Object> values;

	/** empty constructor */
	private PropertyValuesDTO()
	{
		super();
		values = ImmutableMap.of();
	}

	private PropertyValuesDTO(final Map<PropertyName, Object> values)
	{
		super();
		this.values = ImmutableMap.copyOf(values);
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
				, Date.class, Timestamp.class // FIXME: i think we shall avoid Timestamp
				, Boolean.class //
				, SqlLazyLookupDataSource.class // FIXME: tolerate it for now
				, ArrayList.class // FIXME: tolerate it for now => introduce a new datatype for List<PropertyValuesDTO>
				, LookupValue.class //
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
			//
			// Validate the value type
			// NOTE: the aim is to have primitive or well known values types because, later, we want to serialize to JSON
			if (value != null)
			{
				final Class<? extends Object> valueClass = value.getClass();
				if (allowedValueTypes.contains(valueClass))
				{
					// ok
				}
				else if (Supplier.class.isAssignableFrom(valueClass))
				{
					// tolerate it for now

					// e.g.
					// Suppliers.memoize(de.metas.ui.web.vaadin.window.datasource.sql.SqlModelDataSource$$Lambda$34/1396214905@5e071179)
					// see de.metas.ui.web.vaadin.window.datasource.sql.SqlModelDataSource.retrieveRecordsSupplier(ModelDataSourceQuery)
				}
				else
				{
					throw new IllegalArgumentException("Invalid value class: " + valueClass
							+ "\n Allowed types are: " + allowedValueTypes);
				}
			}

			final Object valueNotNull = NullValue.valueOrNull(value);
			valuesBuilder.put(propertyName, valueNotNull);
			return this;
		}
	}

}
