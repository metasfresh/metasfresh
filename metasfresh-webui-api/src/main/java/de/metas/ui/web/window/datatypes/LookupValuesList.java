package de.metas.ui.web.window.datatypes;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

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
 * A list of {@link LookupValue}s with some more debug properties attached.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class LookupValuesList
{
	/**
	 * Collects {@link LookupValue}s and builds a {@link LookupValuesList} with those values.
	 *
	 * @param debugProperties optional debug properties, <code>null</code> is also OK.
	 */
	public static final Collector<LookupValue, ?, LookupValuesList> collect()
	{
		final Map<String, String> debugProperties = null;
		return collect(debugProperties);
	}

	/**
	 * Collects {@link LookupValue}s and builds a {@link LookupValuesList} with those values, having given <code>debugProperties</code>
	 *
	 * @param debugProperties optional debug properties, <code>null</code> is also OK.
	 */
	public static final Collector<LookupValue, ?, LookupValuesList> collect(final Map<String, String> debugProperties)
	{
		final Supplier<ImmutableMap.Builder<Object, LookupValue>> supplier = ImmutableMap.Builder::new;
		final BiConsumer<ImmutableMap.Builder<Object, LookupValue>, LookupValue> accumulator = (builder, item) -> builder.put(item.getId(), item);
		final BinaryOperator<ImmutableMap.Builder<Object, LookupValue>> combiner = (builder1, builder2) -> builder1.putAll(builder2.build());
		final Function<ImmutableMap.Builder<Object, LookupValue>, LookupValuesList> finisher = (builder) -> build(builder, debugProperties);
		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	private static final LookupValuesList build(final ImmutableMap.Builder<Object, LookupValue> valuesByIdBuilder, final Map<String, String> debugProperties)
	{
		final ImmutableMap<Object, LookupValue> valuesById = valuesByIdBuilder.build();
		if (valuesById.isEmpty() && (debugProperties == null || debugProperties.isEmpty()))
		{
			return EMPTY;
		}

		final LookupValuesList result = new LookupValuesList(valuesById, debugProperties);
		return result;
	}

	public static final LookupValuesList EMPTY = new LookupValuesList();

	private final Map<Object, LookupValue> valuesById;
	private final transient ImmutableMap<String, String> debugProperties;

	private LookupValuesList(final ImmutableMap<Object, LookupValue> valuesById, final Map<String, String> debugProperties)
	{
		super();
		this.valuesById = valuesById;
		this.debugProperties = debugProperties == null || debugProperties.isEmpty() ? ImmutableMap.of() : ImmutableMap.copyOf(debugProperties);
	}

	/** Empty constructor */
	private LookupValuesList()
	{
		super();
		valuesById = ImmutableMap.of();
		debugProperties = ImmutableMap.of();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("values", valuesById.values())
				.add("debug", debugProperties.isEmpty() ? null : debugProperties)
				.toString();
	}

	/**
	 * NOTE: {@link #getDebugProperties()} is ignored
	 */
	@Override
	public int hashCode()
	{
		return valuesById.hashCode();
	}

	/**
	 * NOTE: {@link #getDebugProperties()} is ignored when comparing
	 */
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
		return valuesById.equals(other.valuesById);
	}

	/**
	 * @return true if this list is empty (considering it's values and also it's debug properties)
	 */
	public boolean isEmpty()
	{
		return valuesById.isEmpty() && debugProperties.isEmpty();
	}

	/**
	 * @return lookup values, respecting the order in which they were initially created.
	 */
	public Collection<LookupValue> getValues()
	{
		return valuesById.values();
	}

	/**
	 * @return debug properties or empty map; never returns null
	 */
	public Map<String, String> getDebugProperties()
	{
		return debugProperties;
	}

	/**
	 * @param id
	 * @return true if this list contains an {@link LookupValue} with given <code>id</code>
	 * @see LookupValue#getId()
	 */
	public boolean containsId(final Object id)
	{
		return valuesById.containsKey(id);
	}

	/**
	 * @param id
	 * @return lookup value or null
	 */
	public LookupValue getById(final Object id)
	{
		return valuesById.get(id);
	}

	public final <T> T transform(final Function<LookupValuesList, T> transformation)
	{
		return transformation.apply(this);
	}

	/**
	 * @param maxSize
	 * @return a {@link LookupValuesList} which has the given maximum size
	 */
	public LookupValuesList limit(final int maxSize)
	{
		final long maxSizeEffective = maxSize <= 0 ? Long.MAX_VALUE : maxSize;
		if (valuesById.size() <= maxSizeEffective)
		{
			return this;
		}

		return valuesById.values()
				.stream()
				.limit(maxSize)
				.collect(collect(debugProperties));
	}

	public LookupValuesList offsetAndLimit(final int offset, final int maxSize)
	{
		final int offsetEffective = offset <= 0 ? 0 : offset;
		final long maxSizeEffective = maxSize <= 0 ? Long.MAX_VALUE : maxSize;

		if (offsetEffective <= 0 && valuesById.size() <= maxSizeEffective)
		{
			return this;
		}

		return valuesById.values()
				.stream()
				.skip(offsetEffective)
				.limit(maxSizeEffective)
				.collect(collect(debugProperties));
	}

	/**
	 * Filters, skips <code>offset</code> items and limits the result to <code>maxSize</code>.
	 *
	 * NOTE: please mind the operations order, i.e. first we filter and then we skip and limit.
	 *
	 * @param filter
	 * @param offset
	 * @param maxSize
	 * @return
	 */
	public LookupValuesList filter(final Predicate<LookupValue> filter, final int offset, final int maxSize)
	{
		final int offsetEffective = offset <= 0 ? 0 : offset;
		final long maxSizeEffective = maxSize <= 0 ? Long.MAX_VALUE : maxSize;

		return valuesById.values()
				.stream()
				.filter(filter)
				.skip(offsetEffective)
				.limit(maxSizeEffective)
				.collect(collect(debugProperties));
	}
}
