package de.metas.picking.service;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;

import lombok.NonNull;
import lombok.ToString;

/**
 * This map helps to keep track about which item is packed into which place..it's sort of legacy..
 * <p>
 * As far as i see, it needs to be initialized using {@link #addUnpackedItem(IPackingItem)} or {@link #addUnpackedItems(Collection)}, before the fun can start.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ToString
public final class PackingItemsMap
{
	public static PackingItemsMap ofUnpackedItems(final Collection<? extends IPackingItem> unpackedItems)
	{
		final PackingItemsMap map = new PackingItemsMap();
		map.addUnpackedItems(unpackedItems);
		return map;
	}

	public static PackingItemsMap ofUnpackedItem(@NonNull final IPackingItem unpackedItem)
	{
		final PackingItemsMap map = new PackingItemsMap();
		map.addUnpackedItem(unpackedItem);
		return map;
	}

	private final ListMultimap<PackingSlot, IPackingItem> items;

	public PackingItemsMap()
	{
		items = MultimapBuilder.hashKeys()
				.arrayListValues()
				.build();
	}

	/** Copy constructor */
	private PackingItemsMap(final PackingItemsMap copyFrom)
	{
		items = MultimapBuilder.hashKeys()
				.arrayListValues()
				.build(copyFrom.items);
	}

	public ImmutableList<IPackingItem> getBySlot(final PackingSlot slot)
	{
		return ImmutableList.copyOf(items.get(slot));
	}

	public final ImmutableList<IPackingItem> getUnpackedItems()
	{
		return getBySlot(PackingSlot.UNPACKED);
	}

	public final ImmutableList<IPackingItem> getUnpackedItemsMatching(@NonNull Predicate<IPackingItem> predicate)
	{
		return items.get(PackingSlot.UNPACKED)
				.stream()
				.filter(predicate)
				.collect(ImmutableList.toImmutableList());
	}

	public final IPackingItem getUnpackedItemByGroupingKey(@NonNull final PackingItemGroupingKey groupingKey)
	{
		final ImmutableList<IPackingItem> items = getUnpackedItems()
				.stream()
				.filter(item -> PackingItemGroupingKey.equals(item.getGroupingKey(), groupingKey))
				.collect(ImmutableList.toImmutableList());

		if (items.isEmpty())
		{
			return null;
		}
		else if (items.size() == 1)
		{
			return items.get(0);
		}
		else
		{
			throw new AdempiereException("More than one item found for " + groupingKey + " in " + this);
		}
	}

	public void addUnpackedItem(@NonNull final IPackingItem item)
	{
		addItem(PackingSlot.UNPACKED, item);
	}

	public void addUnpackedItems(final Collection<? extends IPackingItem> items)
	{
		addItems(PackingSlot.UNPACKED, items);
	}

	public void addItem(@NonNull final PackingSlot slot, @NonNull final IPackingItem item)
	{
		items.put(slot, item);
	}

	public void addItems(@NonNull final PackingSlot slot, @NonNull final Collection<? extends IPackingItem> items)
	{
		this.items.putAll(slot, items);
	}

	public PackingItemsMap copy()
	{
		return new PackingItemsMap(this);
	}

	public List<IPackingItem> removeBySlot(@NonNull final PackingSlot slot)
	{
		return items.removeAll(slot);
	}

	public void removeUnpackedItem(@NonNull final IPackingItem itemToRemove)
	{
		final boolean removed = items.remove(PackingSlot.UNPACKED, itemToRemove);
		if (!removed)
		{
			throw new AdempiereException("Unpacked item " + itemToRemove + " was not found in: " + items.get(PackingSlot.UNPACKED));
		}
	}

	/**
	 * Append given <code>itemPacked</code> to existing packed items
	 *
	 * @param slot
	 * @param packedItem
	 */
	public void appendPackedItem(@NonNull final PackingSlot slot, @NonNull final IPackingItem packedItem)
	{
		for (final IPackingItem item : items.get(slot))
		{
			// add new item into the list only if is a real new item
			// NOTE: should be only one item with same grouping key
			if (PackingItemGroupingKey.equals(item.getGroupingKey(), packedItem.getGroupingKey()))
			{
				item.addParts(packedItem);
				return;
			}
		}

		//
		// No matching existing packed item where our item could be added was found
		// => add it here as a new item
		addItem(slot, packedItem);
	}

	/**
	 *
	 * @return true if there exists at least one packed item
	 */
	public boolean hasPackedItems()
	{
		return streamPackedItems().findAny().isPresent();
	}

	public boolean hasPackedItemsMatching(@NonNull final Predicate<IPackingItem> predicate)
	{
		return streamPackedItems().anyMatch(predicate);
	}

	public Stream<IPackingItem> streamPackedItems()
	{
		return items
				.entries()
				.stream()
				.filter(e -> !e.getKey().isUnpacked())
				.map(e -> e.getValue());
	}

	/**
	 *
	 * @return true if there exists at least one unpacked item
	 */
	public boolean hasUnpackedItems()
	{
		return !items.get(PackingSlot.UNPACKED).isEmpty();
	}
}
