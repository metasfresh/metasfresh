package org.adempiere.ad.callout.api;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.adempiere.util.Check;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * Convenient immutable AD_Column_ID to {@link ICalloutInstance} list.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Immutable
public final class TableCalloutsMap
{
	public static final TableCalloutsMap of(final Map<String, List<ICalloutInstance>> calloutsByColumn)
	{
		if (calloutsByColumn == null || calloutsByColumn.isEmpty())
		{
			return EMPTY;
		}

		return builder()
				.putAll(calloutsByColumn)
				.build();
	}

	public static final TableCalloutsMap of(final String columnName, final ICalloutInstance callout)
	{
		Check.assumeNotNull(callout, "Parameter callout is not null");
		final ImmutableListMultimap<String, ICalloutInstance> map = ImmutableListMultimap.of(columnName, callout);
		final ImmutableSet<ArrayKey> calloutKeys = ImmutableSet.of(mkCalloutKey(columnName, callout));
		return new TableCalloutsMap(map, calloutKeys);
	}

	/**
	 * Composes given <code>tableCalloutMap</code> with given columnName and callout.
	 * 
	 * If the <code>tableCalloutMap</code> is null then a new {@link TableCalloutsMap} which contains the columnName and callout only will be returned.
	 * 
	 * @param tableCalloutMap
	 * @param columnName
	 * @param callout
	 */
	public static final TableCalloutsMap compose(@Nullable TableCalloutsMap tableCalloutMap, final String columnName, final ICalloutInstance callout)
	{
		if (tableCalloutMap == null)
		{
			return of(columnName, callout);
		}
		else
		{
			return tableCalloutMap.compose(columnName, callout);
		}
	}

	public static final Builder builder()
	{
		return new Builder();
	}

	public static final TableCalloutsMap EMPTY = new TableCalloutsMap();

	private static final Logger logger = LogManager.getLogger(TableCalloutsMap.class);

	private final ImmutableSet<ArrayKey> calloutKeys;
	private final ImmutableListMultimap<String, ICalloutInstance> calloutsByColumn;

	private TableCalloutsMap(final ImmutableListMultimap<String, ICalloutInstance> calloutsByColumnId, final ImmutableSet<ArrayKey> calloutKeys)
	{
		super();
		this.calloutsByColumn = calloutsByColumnId;
		this.calloutKeys = calloutKeys;
	}

	private TableCalloutsMap()
	{
		super();
		calloutsByColumn = ImmutableListMultimap.of();
		calloutKeys = ImmutableSet.of();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.addValue(calloutsByColumn.isEmpty() ? "EMPTY" : calloutsByColumn)
				.toString();
	}

	public boolean isEmpty()
	{
		return calloutsByColumn.isEmpty();
	}

	public List<ICalloutInstance> getColumnCallouts(final String columnName)
	{
		return calloutsByColumn.get(columnName);
	}

	public Set<Entry<String, Collection<ICalloutInstance>>> getColumnNamesAndCalloutsEntries()
	{
		return calloutsByColumn.asMap().entrySet();
	}

	public boolean hasColumnCallouts(final String columnName)
	{
		return calloutsByColumn.containsKey(columnName);
	}

	public boolean hasColumnCallouts(final String columnName, final Set<String> calloutIdsBlacklist)
	{
		if (calloutIdsBlacklist.isEmpty())
		{
			return calloutsByColumn.containsKey(columnName);
		}

		final ImmutableList<ICalloutInstance> callouts = calloutsByColumn.get(columnName);
		if (callouts.isEmpty())
		{
			return false;
		}

		final long count = callouts.stream()
				.filter(callout -> !calloutIdsBlacklist.contains(callout.getId()))
				.count();
		return count > 0;
	}

	public TableCalloutsMap compose(final String columnName, final ICalloutInstance callout)
	{
		Check.assumeNotNull(callout, "Parameter callout is not null");
		final ArrayKey calloutKey = mkCalloutKey(columnName, callout);
		if (calloutKeys.contains(calloutKey))
		{
			logger.warn("Skip composing {} to {} because the callout key {} is already present in the map", callout, this, calloutKey);
			return this;
		}

		final ImmutableListMultimap<String, ICalloutInstance> calloutsByColumnIdNew = ImmutableListMultimap.<String, ICalloutInstance> builder()
				.putAll(calloutsByColumn)
				.put(columnName, callout)
				.build();
		final ImmutableSet<ArrayKey> calloutKeysNew = ImmutableSet.<ArrayKey> builder()
				.addAll(calloutKeys)
				.add(calloutKey)
				.build();

		return new TableCalloutsMap(calloutsByColumnIdNew, calloutKeysNew);
	}

	private static final ArrayKey mkCalloutKey(final String columnName, final ICalloutInstance callout)
	{
		return ArrayKey.of(columnName, callout.getId());
	}

	public static final class Builder
	{
		private final ListMultimap<String, ICalloutInstance> calloutsByColumn = ArrayListMultimap.create();
		private final Set<ArrayKey> seenCalloutKeys = new HashSet<>();

		private Builder()
		{
			super();
		}

		public TableCalloutsMap build()
		{
			if (calloutsByColumn.isEmpty())
			{
				return EMPTY;
			}

			final ImmutableListMultimap<String, ICalloutInstance> map = ImmutableListMultimap.copyOf(calloutsByColumn);
			final ImmutableSet<ArrayKey> calloutsKeys = ImmutableSet.copyOf(seenCalloutKeys);
			return new TableCalloutsMap(map, calloutsKeys);
		}

		public Builder put(final String columnName, final ICalloutInstance callout)
		{
			if (callout == null)
			{
				logger.warn("Skip adding callout for ColumnName={} to map because it's null", columnName);
				return this;
			}

			final ArrayKey calloutKey = mkCalloutKey(columnName, callout);
			if (!seenCalloutKeys.add(calloutKey))
			{
				logger.warn("Skip adding callout {} with key '{}' to map because was already added", callout, calloutKey);
				return this;
			}

			calloutsByColumn.put(columnName, callout);
			return this;
		}

		public Builder putAll(final ListMultimap<String, ICalloutInstance> calloutsByColumn)
		{
			if (calloutsByColumn == null || calloutsByColumn.isEmpty())
			{
				return this;
			}

			for (final Entry<String, ICalloutInstance> entry : calloutsByColumn.entries())
			{
				put(entry.getKey(), entry.getValue());
			}
			return this;
		}

		public Builder putAll(final Map<String, List<ICalloutInstance>> calloutsByColumn)
		{
			if (calloutsByColumn == null || calloutsByColumn.isEmpty())
			{
				return this;
			}

			for (final Entry<String, List<ICalloutInstance>> entry : calloutsByColumn.entrySet())
			{
				final List<ICalloutInstance> callouts = entry.getValue();
				if (callouts == null || callouts.isEmpty())
				{
					continue;
				}

				final String columnName = entry.getKey();
				for (final ICalloutInstance callout : callouts)
				{
					put(columnName, callout);
				}
			}

			return this;
		}

		public Builder putAll(final TableCalloutsMap callouts)
		{
			if (callouts == null || callouts.isEmpty())
			{
				return this;
			}

			putAll(callouts.calloutsByColumn);
			return this;
		}
	}
}
