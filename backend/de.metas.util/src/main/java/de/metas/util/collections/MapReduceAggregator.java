package de.metas.util.collections;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.apache.commons.collections4.map.LRUMap;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
	private Function<ItemType, Object> _itemAggregationKeyBuilder;

	//
	// Status
	private Map<Object, GroupType> _itemHashKey2group = null;
	private GroupType _lastGroupUsed;
	/** How many groups were created */
	private int _countGroups = 0;
	/** How many items were added */
	private int _countItems = 0;

	private List<GroupType> closedGroupsCollector = null;

	public MapReduceAggregator()
	{
		this._configurable = true;

		setGroupsBufferSize(DEFAULT_BufferSize);
	}

	/**
	 * Create a new EMPTY group instance.
	 *
	 * @return created group
	 */
	protected abstract GroupType createGroup(Object itemHashKey, ItemType item);

	/**
	 * Close given group. This method is called by API when it removes given group from its buffer.
	 */
	protected abstract void closeGroup(GroupType group);

	/**
	 * Adds given <code>item</code> to <code>group</code>.
	 */
	protected abstract void addItemToGroup(GroupType group, ItemType item);

	/** Asserts this aggregator is still in "configurable" state */
	protected final void assertConfigurable()
	{
		Check.assume(_configurable, "Aggregator {} is in configurable state", this);
	}

	/** Make this aggregator not configurable. Mainly this method is called when processing is started. */
	private void setNotConfigurable()
	{
		this._configurable = false;
	}

	/**
	 * Configure which is the hash key builder used to group the items.
	 * <p>
	 * <b>IMPORTANT TO KNOW:</b> only the key builder's {@link IAggregationKeyBuilder#buildKey(Object) buildKey(Object)} method is invoked.<br>
	 * Not its {@link IAggregationKeyBuilder#isSame(Object, Object) isSame(Object, Object} method
	 */
	public final void setItemAggregationKeyBuilder(@NonNull final IAggregationKeyBuilder<ItemType> itemAggregationKeyBuilder)
	{
		setItemAggregationKeyBuilder(itemAggregationKeyBuilder::buildKey);
	}

	public final void setItemAggregationKeyBuilder(@NonNull final Function<ItemType, Object> itemAggregationKeyBuilder)
	{
		assertConfigurable();
		this._itemAggregationKeyBuilder = itemAggregationKeyBuilder;
	}

	private Function<ItemType, Object> getItemAggregationKeyBuilder()
	{
		return this._itemAggregationKeyBuilder;
	}

	public void collectClosedGroups()
	{
		assertConfigurable();
		if (closedGroupsCollector == null)
		{
			this.closedGroupsCollector = new ArrayList<>();
		}
	}

	public List<GroupType> getClosedGroups()
	{
		if (closedGroupsCollector == null)
		{
			throw new IllegalStateException("Not collecting groups");
		}
		return ImmutableList.copyOf(closedGroupsCollector);
	}

	/**
	 * Sets how many groups you want to keep in memory. If the value is zero then all groups will be kept in memory.
	 *
	 * Default value is {@value #DEFAULT_BufferSize}.
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
					if (group != null)
					{
						closeGroupAndCollect(group);
					}
					return true; // accept to remove it
				}

			};
		}
	}

	/**
	 * Adds item to be aggregated.
	 */
	public final void add(@NonNull final ItemType item)
	{
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
			group = createAndAddGroupToBuffer(itemHashKey, item);
		}

		//
		// Add item to group
		addItemToGroup(group, item);

		//
		// Update status
		_lastGroupUsed = group;
		_countItems++;
	}

	@VisibleForTesting
	public MapReduceAggregator<GroupType, ItemType> addAll(final Iterator<ItemType> items)
	{
		Check.assumeNotNull(items, "items not null");
		while (items.hasNext())
		{
			final ItemType item = items.next();
			add(item);
		}

		return this;
	}

	/**
	 * Invokes this instance's {@link IAggregationKeyBuilder}'s (see {@link #setItemAggregationKeyBuilder(IAggregationKeyBuilder)}) <code>buildKey</code> method.
	 *
	 * @return hash/aggregation key for given <code>item</code>
	 */
	private Object createItemHashKey(@NonNull final ItemType item)
	{
		final Function<ItemType, Object> itemAggregationKeyBuilder = getItemAggregationKeyBuilder();
		final Object itemHashKey = itemAggregationKeyBuilder.apply(item);
		return itemHashKey;
	}

	/**
	 * Creates a new group based on given item and adds it to groups buffer.
	 *
	 * If the groups buffer capacity is reached the last used group will be removed from buffer (this is done indirectly by {@link LRUMap}).
	 *
	 * @param item item to be used for creating the new group
	 */
	private GroupType createAndAddGroupToBuffer(@NonNull final Object itemHashKey, @NonNull final ItemType item)
	{
		// We put a null value to map (before actually creating the group),
		// to make sure the previous groups are closed before the new group is actually created
		_itemHashKey2group.put(itemHashKey, null);

		final GroupType group = createGroup(itemHashKey, item);

		_itemHashKey2group.put(itemHashKey, group);
		_countGroups++;

		return group;
	}

	private void closeGroupAndCollect(final GroupType group)
	{
		closeGroup(group);
		if (closedGroupsCollector != null)
		{
			closedGroupsCollector.add(group);
		}
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
	private void closeAllGroupsExcept(@Nullable final GroupType exceptGroup)
	{
		final Iterator<GroupType> groups = _itemHashKey2group.values().iterator();
		while (groups.hasNext())
		{
			final GroupType group = groups.next();
			if (group == null)
			{
				continue;
			}

			// Skip the excepted group
			if (group == exceptGroup)
			{
				continue;
			}

			closeGroupAndCollect(group);
			groups.remove();
		}
	}

	/**
	 * @return how many groups were created
	 */
	public final int getGroupsCount()
	{
		return _countGroups;
	}

	/**
	 * @return how many items were added
	 */
	public final int getItemsCount()
	{
		return _countItems;
	}
}
