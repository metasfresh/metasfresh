package de.metas.adempiere.form;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

public class PackingItemsMap
{
	public static final int KEY_UnpackedItems = 0;
	private final Map<Integer, List<IPackingItem>> itemsMap = new HashMap<>();

	public PackingItemsMap()
	{
		super();

		final List<IPackingItem> unpackedItems = new ArrayList<>();
		itemsMap.put(KEY_UnpackedItems, unpackedItems);
	}

	/** Copy constructor */
	private PackingItemsMap(final PackingItemsMap copyFrom)
	{
		super();

		//
		// Do a deep-copy of "copyFrom"'s map
		for (final Entry<Integer, List<IPackingItem>> key2itemsList : copyFrom.itemsMap.entrySet())
		{
			final List<IPackingItem> items = key2itemsList.getValue();
			
			// skip null items (shall not happen)
			if (items == null)
			{
				continue;
			}
			
			// NOTE: to avoid NPEs we are also copying empty lists
			
			final List<IPackingItem> itemsCopy = new ArrayList<>(items);

			final Integer key = key2itemsList.getKey();

			itemsMap.put(key, itemsCopy);
		}
	}

	public List<IPackingItem> get(final int key)
	{
		return itemsMap.get(key);
	}

	public void addUnpackedItems(final Collection<? extends IPackingItem> unpackedItemsToAdd)
	{
		if (unpackedItemsToAdd == null || unpackedItemsToAdd.isEmpty())
		{
			return;
		}

		final List<IPackingItem> unpackedItems = getUnpackedItems();
		unpackedItems.addAll(unpackedItemsToAdd);
	}

	private final List<IPackingItem> getUnpackedItems()
	{
		List<IPackingItem> unpackedItems = itemsMap.get(KEY_UnpackedItems);
		if (unpackedItems == null)
		{
			unpackedItems = new ArrayList<>();
			itemsMap.put(KEY_UnpackedItems, unpackedItems);
		}

		return unpackedItems;
	}

	public void addUnpackedItem(final IPackingItem unpackedItemToAdd)
	{
		Check.assumeNotNull(unpackedItemToAdd, "unpackedItemToAdd not null");
		final List<IPackingItem> unpackedItems = getUnpackedItems();
		unpackedItems.add(unpackedItemToAdd);
	}

	public void put(int key, List<IPackingItem> items)
	{
		itemsMap.put(key, items);
	}

	public Set<Entry<Integer, List<IPackingItem>>> entrySet()
	{
		return itemsMap.entrySet();
	}

	public PackingItemsMap copy()
	{
		return new PackingItemsMap(this);
	}

	/**
	 * @param key
	 * @return see {@link Map#remove(Object)}
	 */
	public List<IPackingItem> remove(int key)
	{
		return itemsMap.remove(key);
	}

	public void removeUnpackedItem(final IPackingItem itemToRemove)
	{
		Check.assumeNotNull(itemToRemove, "itemToRemove not null");
		final List<IPackingItem> unpackedItems = getUnpackedItems();
		for (final Iterator<IPackingItem> it = unpackedItems.iterator(); it.hasNext();)
		{
			final IPackingItem item = it.next();
			if (item.isSameAs(itemToRemove))
			{
				it.remove();
				return;
			}
		}

		throw new AdempiereException("Unpacked item " + itemToRemove + " was not found in: " + unpackedItems);
	}

	/**
	 * Append given <code>itemPacked</code> to existing packed items
	 * 
	 * @param key
	 * @param itemPacked
	 */
	public void appendPackedItem(final int key, final IPackingItem itemPacked)
	{
		List<IPackingItem> existingPackedItems = get(key);
		if (existingPackedItems == null)
		{
			existingPackedItems = new ArrayList<>();
			put(key, existingPackedItems);
		}
		else
		{
			for (final IPackingItem item : existingPackedItems)
			{
				// add new item into the list only if is a real new item
				// NOTE: should be only one item with same grouping key
				if (item.getGroupingKey() == itemPacked.getGroupingKey())
				{
					item.addSchedules(itemPacked);
					return;
				}
			}
		}

		//
		// No matching existing packed item where our item could be added was found
		// => add it here as a new item
		existingPackedItems.add(itemPacked);
	}
	
	/**
	 * 
	 * @return true if there exists at least one packed item
	 */
	public boolean hasPackedItems()
	{
		for (final Entry<Integer, List<IPackingItem>> key2itemsList : itemsMap.entrySet())
		{
			final Integer key = key2itemsList.getKey();
			
			if (key == KEY_UnpackedItems)
			{
				continue;
			}
			
			final List<IPackingItem> items = key2itemsList.getValue();
			if (items == null || items.isEmpty())
			{
				continue;
			}
			
			// if we reach this point it means that we just found a list with packed items
			return true;
		}

		return false;
	}
	
	/**
	 * 
	 * @return true if there exists at least one unpacked item
	 */
	public boolean hasUnpackedItems()
	{
		final List<IPackingItem> unpackedItems = getUnpackedItems();
		if (unpackedItems == null || unpackedItems.isEmpty())
		{
			return false;
		}
		
		return true;
	}

	@Override
	public String toString()
	{
		return String.format("PackingItemsMap [itemsMap=%s, hasPackedItems()=%s, hasUnpackedItems()=%s]", itemsMap, hasPackedItems(), hasUnpackedItems());
	}
	
	
}
