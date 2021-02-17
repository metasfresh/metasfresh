package de.metas.ui.web.window.datatypes;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.util.GuavaCollectors;
import de.metas.util.lang.RepoIdAware;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

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
 */
@Immutable
@EqualsAndHashCode(exclude = "debugProperties")
public final class LookupValuesList implements Iterable<LookupValue>
{
	/**
	 * Collects {@link LookupValue}s and builds a {@link LookupValuesList} with those values.
	 *
	 * @param debugProperties optional debug properties, <code>null</code> is also OK.
	 */
	public static Collector<LookupValue, ?, LookupValuesList> collect()
	{
		return collect(DebugProperties.EMPTY);
	}

	/**
	 * Collects {@link LookupValue}s and builds a {@link LookupValuesList} with those values, having given <code>debugProperties</code>
	 *
	 * @param debugProperties optional debug properties, <code>null</code> is also OK.
	 */
	public static Collector<LookupValue, ?, LookupValuesList> collect(@Nullable final DebugProperties debugProperties)
	{
		final Supplier<ImmutableListMultimap.Builder<Object, LookupValue>> supplier = ImmutableListMultimap.Builder::new;
		final BiConsumer<ImmutableListMultimap.Builder<Object, LookupValue>, LookupValue> accumulator = (builder, item) -> builder.put(item.getId(), item);
		final BinaryOperator<ImmutableListMultimap.Builder<Object, LookupValue>> combiner = (builder1, builder2) -> builder1.putAll(builder2.build());
		final Function<ImmutableListMultimap.Builder<Object, LookupValue>, LookupValuesList> finisher = (builder) -> build(builder, debugProperties);
		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	public static LookupValuesList fromNullable(final LookupValue lookupValue)
	{
		if (lookupValue == null)
		{
			return EMPTY;
		}

		final ImmutableListMultimap<Object, LookupValue> valuesById = ImmutableListMultimap.of(lookupValue.getId(), lookupValue);
		final boolean ordered = true;
		return new LookupValuesList(valuesById, ordered, DebugProperties.EMPTY);
	}

	public static LookupValuesList fromCollection(final Collection<? extends LookupValue> lookupValues)
	{
		if (lookupValues.isEmpty())
		{
			return EMPTY;
		}

		final ImmutableListMultimap<Object, LookupValue> valuesById = lookupValues.stream().collect(GuavaCollectors.toImmutableListMultimap(LookupValue::getId));
		final boolean ordered = true;
		return new LookupValuesList(valuesById, ordered, DebugProperties.EMPTY);
	}

	private static LookupValuesList build(
			@NonNull final ImmutableListMultimap.Builder<Object, LookupValue> valuesByIdBuilder,
			@Nullable final DebugProperties debugProperties)
	{
		final ImmutableListMultimap<Object, LookupValue> valuesById = valuesByIdBuilder.build();
		if (valuesById.isEmpty() && (debugProperties == null || debugProperties.isEmpty()))
		{
			return EMPTY;
		}

		final boolean ordered = true;
		final LookupValuesList result = new LookupValuesList(valuesById, ordered, debugProperties);
		return result;
	}

	public static final LookupValuesList EMPTY = new LookupValuesList();

	private final ImmutableListMultimap<Object, LookupValue> valuesById;
	private final boolean ordered;
	@Getter
	private final DebugProperties debugProperties;

	private LookupValuesList(
			@NonNull final ImmutableListMultimap<Object, LookupValue> valuesById,
			final boolean ordered,
			@Nullable final DebugProperties debugProperties)
	{
		this.valuesById = valuesById;
		this.ordered = ordered;
		this.debugProperties = debugProperties != null ? debugProperties : DebugProperties.EMPTY;
	}

	/**
	 * Empty constructor
	 */
	private LookupValuesList()
	{
		valuesById = ImmutableListMultimap.of();
		ordered = true;
		debugProperties = DebugProperties.EMPTY;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("values", valuesById.values())
				.add("ordered", ordered)
				.add("debug", debugProperties.isEmpty() ? null : debugProperties)
				.toString();
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

	public Set<String> getKeysAsString()
	{
		return valuesById.values().stream()
				.map(LookupValue::getIdAsString)
				.collect(ImmutableSet.toImmutableSet());
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
	 * @param id
	 * @return true if this list contains an {@link LookupValue} with given <code>id</code>
	 * @see LookupValue#getId()
	 */
	public boolean containsId(final Object id)
	{
		return valuesById.containsKey(normalizeId(id));
	}

	/**
	 * @param id
	 * @return first lookup value found for <code>id</code> or null
	 */
	public LookupValue getById(final Object id)
	{
		final ImmutableList<LookupValue> values = valuesById.get(normalizeId(id));
		return values.isEmpty() ? null : values.get(0);
	}

	private static Object normalizeId(final Object id)
	{
		if (id == null)
		{
			return null;
		}
		else if (id instanceof RepoIdAware)
		{
			return ((RepoIdAware)id).getRepoId();
		}
		else
		{
			return id;
		}
	}

	public <T> T transform(final Function<LookupValuesList, T> transformation)
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
	 * <p>
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
			final ImmutableListMultimap<Object, LookupValue> valuesByIdNew = ImmutableListMultimap.<Object, LookupValue>builder()
					.putAll(valuesById)
					.put(lookupValue.getId(), lookupValue)
					.build();
			return new LookupValuesList(valuesByIdNew, ordered, debugProperties);
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
		return new LookupValuesList(valuesByIdNew, ordered, debugProperties);
	}

	/**
	 * @return true if the lookup values are in a precise order (no matter which one is that);
	 * When converting to JSON (so when the AD_Language will be also known),
	 * a not ordered list will be automatically sorted alphabetically by caption.
	 */
	public boolean isOrdered()
	{
		return ordered;
	}

	/**
	 * @see #isOrdered()
	 */
	public LookupValuesList ordered(final boolean ordered)
	{
		return this.ordered != ordered
				? new LookupValuesList(valuesById, ordered, debugProperties)
				: this;
	}

	/**
	 * @see #isOrdered()
	 */
	public LookupValuesList notOrdered()
	{
		return ordered(false);
	}
}
