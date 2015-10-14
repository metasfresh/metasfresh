package org.adempiere.util.collections;

/*
 * #%L
 * org.adempiere.util
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.adempiere.util.Check;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.apache.commons.collections4.map.LRUMap;

/**
 * An aggregator which takes items as input and group them.
 * 
 * You can use it whenever how have a huge stream of items and you want to distribute them to groups (aggregation).
 * 
 * Items are put in the same group if they have the same hash key (see {@link #setItemAggregationKeyBuilder(IAggregationKeyBuilder)}).
 * 
 * @author tsa
 *
 * @param <GroupType> items group type
 * @param <ItemType> input items type
 */
public abstract class MapReduceAggregator<GroupType, ItemType>
{
	public static final int DEFAULT_BufferSize = 1;

	//
	// Configuration
	private boolean _configurable = true;
	private IAggregationKeyBuilder<ItemType> _itemAggregationKeyBuilder;

	//
	// Status
	private Map<Object, GroupType> _itemHashKey2group = null;
	private GroupType _lastGroupUsed;
	/** How many groups were created */
	private int _countGroups = 0;
	/** How many items were added */
	private int _countItems = 0;

	public MapReduceAggregator()
	{
		super();
		this._configurable = true;

		setGroupsBufferSize(DEFAULT_BufferSize);
	}

	/**
	 * Create a new EMPTY group instance.
	 * 
	 * @param itemHashKey
	 * @param item
	 * @return created group
	 */
	protected abstract GroupType createGroup(Object itemHashKey, ItemType item);

	/**
	 * Close given group. This method is called by API when it removes given group from its buffer.
	 * 
	 * @param group
	 */
	protected abstract void closeGroup(GroupType group);

	/**
	 * Adds given <code>item</code> to <code>group</code>.
	 * 
	 * @param group
	 * @param item
	 */
	protected abstract void addItemToGroup(GroupType group, ItemType item);

	/** Asserts this aggregator is still in "configurable" state */
	protected final void assertConfigurable()
	{
		Check.assume(_configurable, "Aggregator {0} is in configurable state", this);
	}

	/** Make this aggregator not configurable. Mainly this method is called when processing is started. */
	private final void setNotConfigurable()
	{
		this._configurable = false;
	}

	/**
	 * Configure which is the hash key builder used to group the items.
	 * 
	 * @param itemAggregationKeyBuilder
	 */
	public final void setItemAggregationKeyBuilder(final IAggregationKeyBuilder<ItemType> itemAggregationKeyBuilder)
	{
		assertConfigurable();
		this._itemAggregationKeyBuilder = itemAggregationKeyBuilder;
	}

	protected final IAggregationKeyBuilder<ItemType> getItemAggregationKeyBuilder()
	{
		return this._itemAggregationKeyBuilder;
	}

	/**
	 * Sets how many groups you want to keep in memory. If the value is zero then all groups will be kept in memory.
	 * 
	 * Default value is {@value #DEFAULT_BufferSize}.
	 * 
	 * @param bufferSize
	 */
	public void setGroupsBufferSize(final int bufferSize)
	{
		assertConfigurable();

		//
		// Case: infinite memory
		if (bufferSize <= 0)
		{
			this._itemHashKey2group = new HashMap<>();
		}
		//
		// Case: finite buffer size
		else
		{
			this._itemHashKey2group = new LRUMap<Object, GroupType>(bufferSize)
			{
				private static final long serialVersionUID = -1342388742697440633L;

				/**
				 * When a group is removed from LRU map, because it does not fit it buffer anymore, we need to close that group.
				 */
				@Override
				protected boolean removeLRU(final org.apache.commons.collections4.map.AbstractLinkedMap.LinkEntry<Object, GroupType> entry)
				{
					final GroupType group = entry.getValue();
					closeGroup(group);
					return true; // accept to remove it
				}

			};
		}
	}

	/**
	 * Adds item to be aggregated.
	 * 
	 * @param item
	 */
	public final void add(final ItemType item)
	{
		Check.assumeNotNull(item, "item not null");

		setNotConfigurable(); // not configurable anymore

		//
		// Create aggregation key
		final Object itemHashKey = createItemHashKey(item);

		//
		// Find matching group for our aggregation key
		GroupType group = _itemHashKey2group.get(itemHashKey);

		//
		// If no matching group found for our item, create a new group.
		if (group == null)
		{
			group = createGroup(itemHashKey, item);
			addGroupToBuffer(itemHashKey, group);
		}

		//
		// Add item to group
		addItemToGroup(group, item);

		//
		// Update status
		_lastGroupUsed = group;
		_countItems++;
	}

	/** @return hash/aggregation key for given <code>item</code> */
	private final Object createItemHashKey(final ItemType item)
	{
		final IAggregationKeyBuilder<ItemType> itemAggregationKeyBuilder = getItemAggregationKeyBuilder();
		final String itemHashKey = itemAggregationKeyBuilder.buildKey(item);
		return itemHashKey;
	}

	/**
	 * Adds given group to groups buffer.
	 * 
	 * If the groups buffer capacity is reached the last used group will be removed from buffer (this is done indirectly by {@link LRUMap}).
	 * 
	 * @param itemHashKey
	 * @param group
	 */
	private final void addGroupToBuffer(final Object itemHashKey, final GroupType group)
	{
		_itemHashKey2group.put(itemHashKey, group);
		_countGroups++;
	}

	/**
	 * Close all groups from buffer. As a result, after this call the groups buffer will be empty.
	 */
	public final void closeAllGroups()
	{
		final GroupType exceptGroup = null; // no exception
		closeAllGroupsExcept(exceptGroup);
	}

	/**
	 * Close all groups, but not the last used one. Last used one is considered that group where we added last item.
	 */
	public final void closeAllGroupExceptLastUsed()
	{
		closeAllGroupsExcept(_lastGroupUsed);
	}

	/**
	 * Close all groups.
	 * 
	 * @param exceptGroup optional group to except from closing
	 */
	private final void closeAllGroupsExcept(final GroupType exceptGroup)
	{
		final Iterator<GroupType> groups = _itemHashKey2group.values().iterator();
		while (groups.hasNext())
		{
			final GroupType group = groups.next();

			// Skip the excepted group
			if (group == exceptGroup)
			{
				continue;
			}

			closeGroup(group);
			groups.remove();
		}
	}

	/** @return how many groups were created */
	public final int getGroupsCount()
	{
		return _countGroups;
	}

	/** @return how many items were added */
	public final int getItemsCount()
	{
		return _countItems;
	}
}
