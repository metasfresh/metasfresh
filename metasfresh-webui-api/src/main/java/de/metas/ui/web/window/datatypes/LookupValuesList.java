package de.metas.ui.web.window.datatypes;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.ui.web.window.datatypes.json.JSONLookupValue;

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

/**
 * A list of {@link JSONLookupValue} with some more debug properties attached.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class LookupValuesList
{
	public static final LookupValuesList of(final List<LookupValue> values)
	{
		final Map<String, String> debugProperties = null;
		return of(values, debugProperties);
	}

	public static final LookupValuesList of(final List<LookupValue> values, final Map<String, String> debugProperties)
	{
		if ((values == null || values.isEmpty())
				&& (debugProperties == null || debugProperties.isEmpty()))
		{
			return EMPTY;
		}

		return new LookupValuesList(values, debugProperties);
	}
	
	public static final Collector<LookupValue, ?, LookupValuesList> collect()
	{
		final Supplier<ImmutableMap.Builder<Object, LookupValue>> supplier = ImmutableMap.Builder::new;
		final BiConsumer<ImmutableMap.Builder<Object, LookupValue>, LookupValue> accumulator = (builder, item) -> {
			final Object key = item.getId();
			builder.put(key, item);
		};
		final BinaryOperator<ImmutableMap.Builder<Object, LookupValue>> combiner = (builder1, builder2) -> builder1.putAll(builder2.build());
		final Function<ImmutableMap.Builder<Object, LookupValue>, LookupValuesList> finisher = (builder) -> new LookupValuesList(builder.build());
		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	public static final LookupValuesList EMPTY = new LookupValuesList(ImmutableList.of(), ImmutableMap.of());

	private final Map<Object, LookupValue> values;
	private final transient ImmutableMap<String, String> debugProperties;

	private LookupValuesList(final List<LookupValue> values, final Map<String, String> debugProperties)
	{
		super();
		this.values = values == null || values.isEmpty() ? ImmutableMap.of() : Maps.uniqueIndex(values, value -> value.getId());
		this.debugProperties = debugProperties == null || debugProperties.isEmpty() ? ImmutableMap.of() : ImmutableMap.copyOf(debugProperties);
	}
	
	private LookupValuesList(final Map<Object, LookupValue> valuesById)
	{
		super();
		this.values = ImmutableMap.copyOf(valuesById);
		this.debugProperties = ImmutableMap.of();
	}


	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("values", values)
				.add("debug", debugProperties.isEmpty() ? null : debugProperties)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return values.hashCode();
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

		if (!getClass().equals(obj.getClass()))
		{
			return false;
		}

		final LookupValuesList other = (LookupValuesList)obj;
		return values.equals(other.values);
	}

	public boolean isEmpty()
	{
		return values.isEmpty() && debugProperties.isEmpty();
	}

	public Collection<LookupValue> getValues()
	{
		return values.values();
	}

	public Map<String, String> getDebugProperties()
	{
		return debugProperties;
	}

	public boolean containsId(final Object id)
	{
		return values.containsKey(id);
	}

	public LookupValue getById(final Object id)
	{
		return values.get(id);
	}

	public final <T> T transform(final Function<LookupValuesList, T> transformation)
	{
		return transformation.apply(this);
	}
}
