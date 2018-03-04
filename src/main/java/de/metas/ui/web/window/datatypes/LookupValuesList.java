package de.metas.ui.web.window.datatypes;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.adempiere.util.GuavaCollectors;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * A list of {@link LookupValue}s with some more debug properties attached.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Immutable
public final class LookupValuesList implements Iterable<LookupValue>
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
		final Supplier<ImmutableListMultimap.Builder<Object, LookupValue>> supplier = ImmutableListMultimap.Builder::new;
		final BiConsumer<ImmutableListMultimap.Builder<Object, LookupValue>, LookupValue> accumulator = (builder, item) -> builder.put(item.getId(), item);
		final BinaryOperator<ImmutableListMultimap.Builder<Object, LookupValue>> combiner = (builder1, builder2) -> builder1.putAll(builder2.build());
		final Function<ImmutableListMultimap.Builder<Object, LookupValue>, LookupValuesList> finisher = (builder) -> build(builder, debugProperties);
		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	public static final LookupValuesList fromNullable(final LookupValue lookupValue)
	{
		if (lookupValue == null)
		{
			return EMPTY;
		}

		final ImmutableListMultimap<Object, LookupValue> valuesById = ImmutableListMultimap.of(lookupValue.getId(), lookupValue);
		final Map<String, String> debugProperties = ImmutableMap.of();
		return new LookupValuesList(valuesById, debugProperties);
	}

	public static final LookupValuesList fromCollection(final Collection<? extends LookupValue> lookupValues)
	{
		if (lookupValues.isEmpty())
		{
			return EMPTY;
		}

		final ImmutableListMultimap<Object, LookupValue> valuesById = lookupValues.stream().collect(GuavaCollectors.toImmutableListMultimap(LookupValue::getId));
		final Map<String, String> debugProperties = ImmutableMap.of();
		return new LookupValuesList(valuesById, debugProperties);
	}

	private static final LookupValuesList build(final ImmutableListMultimap.Builder<Object, LookupValue> valuesByIdBuilder, final Map<String, String> debugProperties)
	{
		final ImmutableListMultimap<Object, LookupValue> valuesById = valuesByIdBuilder.build();
		if (valuesById.isEmpty() && (debugProperties == null || debugProperties.isEmpty()))
		{
			return EMPTY;
		}

		final LookupValuesList result = new LookupValuesList(valuesById, debugProperties);
		return result;
	}

	public static final LookupValuesList EMPTY = new LookupValuesList();

	private final ImmutableListMultimap<Object, LookupValue> valuesById;
	private final transient ImmutableMap<String, String> debugProperties;

	private LookupValuesList(
			@NonNull final ImmutableListMultimap<Object, LookupValue> valuesById,
			@Nullable final Map<String, String> debugProperties)
	{
		this.valuesById = valuesById;
		this.debugProperties = debugProperties == null || debugProperties.isEmpty() ? ImmutableMap.of() : ImmutableMap.copyOf(debugProperties);
	}

	/** Empty constructor */
	private LookupValuesList()
	{
		valuesById = ImmutableListMultimap.of();
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

	public Set<Object> getKeys()
	{
		return valuesById.keySet();
	}

	public Set<Integer> getKeysAsInt()
	{
		return valuesById.values().stream()
				.map(LookupValue::getIdAsInt)
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * @return lookup values, respecting the order in which they were initially created.
	 */
	public Collection<LookupValue> getValues()
	{
		return valuesById.values();
	}

	public Stream<LookupValue> stream()
	{
		return getValues().stream();
	}
	
	@Override
	public Iterator<LookupValue> iterator()
	{
		return getValues().iterator();
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
	 * @return first lookup value found for <code>id</code> or null
	 */
	public LookupValue getById(final Object id)
	{
		final ImmutableList<LookupValue> values = valuesById.get(id);
		return values.isEmpty() ? null : values.get(0);
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

		return stream()
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

		return stream()
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

		return stream()
				.filter(filter)
				.skip(offsetEffective)
				.limit(maxSizeEffective)
				.collect(collect(debugProperties));
	}

	public LookupValuesList addIfAbsent(@NonNull final LookupValue lookupValue)
	{
		if (valuesById.containsKey(lookupValue.getId()))
		{
			return this;
		}
		else
		{
			final ImmutableListMultimap<Object, LookupValue> valuesByIdNew = ImmutableListMultimap.<Object, LookupValue> builder()
					.putAll(valuesById)
					.put(lookupValue.getId(), lookupValue)
					.build();
			return new LookupValuesList(valuesByIdNew, debugProperties);
		}
	}

	/**
	 * Creates and returns a new {@link LookupValuesList} which contains the lookup values from this list but without the lookup values from <code>valuesToRemove</code>.
	 * The values will be matched by ID only. The display name will be ignored.
	 */
	public LookupValuesList removeAll(final LookupValuesList valuesToRemove)
	{
		// If nothing to remove, we can return this
		if (valuesToRemove.isEmpty())
		{
			return this;
		}

		// If this list is empty, we can return it
		if (valuesById.isEmpty())
		{
			return this;
		}

		// Create a new values map which does not contain the the values to be removed
		final ImmutableListMultimap<Object, LookupValue> valuesByIdNew = valuesById.entries().stream()
				.filter(entry -> !valuesToRemove.containsId(entry.getKey()))
				.collect(GuavaCollectors.toImmutableListMultimap());

		// If nothing was filtered out, we can return this
		if (valuesById.size() == valuesByIdNew.size())
		{
			return this;
		}

		//
		return new LookupValuesList(valuesByIdNew, debugProperties);
	}
}
