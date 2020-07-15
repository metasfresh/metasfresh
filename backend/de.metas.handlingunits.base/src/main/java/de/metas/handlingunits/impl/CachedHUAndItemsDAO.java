package de.metas.handlingunits.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import de.metas.handlingunits.IHUAndItemsDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * This service wraps a {@link HUAndItemsDAO} and caches its results.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class CachedHUAndItemsDAO extends AbstractHUAndItemsDAO
{
	private final Map<Object, ArrayList<I_M_HU_Item>> huKey2huItems = new HashMap<>();
	private final Map<Object, ArrayList<I_M_HU>> huItemKey2includedHUs = new HashMap<>();

	private final IHUAndItemsDAO db = HUAndItemsDAO.instance;

	/* package */ CachedHUAndItemsDAO()
	{
	}

	private final Object mkHUKey(final I_M_HU hu)
	{
		final int huId = hu.getM_HU_ID();
		return mkHUKey(huId);
	}

	private final Object mkHUKey(final int huId)
	{
		return huId;
	}

	private final Object mkHUItemKey(final I_M_HU_Item huItem)
	{
		final int huItemId = huItem.getM_HU_Item_ID();
		return mkHUItemKey(huItemId);
	}

	private final Object mkHUItemKey(final int huItemId)
	{
		return huItemId;
	}

	@Override
	public void saveHU(final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "hu not null");

		final boolean isNew = hu.getM_HU_ID() <= 0;

		db.saveHU(hu);

		final Object huKey = mkHUKey(hu);

		if (isNew)
		{
			huKey2huItems.put(huKey, new ArrayList<I_M_HU_Item>());
		}

		if (!hu.isActive())
		{
			final int parentHUItemId = hu.getM_HU_Item_Parent_ID();
			if (parentHUItemId > 0)
			{
				// NOTE: don't check this because it's not always valid
				// e.g. destroying an LU on Receipt Schedule, because that planning LU is not usable anymore
				// throw new HUException("Inactivating an included HU not allowed"
				// + "\n HU: " + hu
				// + "\n Parent Item: " + db.retrieveParent(hu));

				// NOTE: this is also not ok when we mark as destroyed a planning LU/TU, but we can live with that for now
				// setParentItem(hu, null); // we are just updating the cache
			}
		}
	}

	@Override
	public void delete(final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "hu not null");

		final Object huKey = mkHUKey(hu);

		db.delete(hu);

		huKey2huItems.remove(huKey);
	}

	@Override
	public I_M_HU_Item createHUItem(final I_M_HU hu, final I_M_HU_PI_Item piItem)
	{
		final I_M_HU_Item huItem = db.createHUItem(hu, piItem);
		return finalizeAndAddToCache(hu, huItem);
	}

	@Override
	public I_M_HU_Item createAggregateHUItem(@NonNull final I_M_HU hu)
	{
		final I_M_HU_Item huItem = db.createAggregateHUItem(hu);
		return finalizeAndAddToCache(hu, huItem);
	}

	@Override
	public I_M_HU_Item createChildHUItem(@NonNull final I_M_HU hu)
	{
		final I_M_HU_Item huItem = db.createChildHUItem(hu);
		return finalizeAndAddToCache(hu, huItem);
	}

	private I_M_HU_Item finalizeAndAddToCache(final I_M_HU hu, final I_M_HU_Item huItem)
	{
		huItem.setM_HU(hu);

		//
		// Add Item to HU Items list
		final Object huKey = mkHUKey(hu);
		final ArrayList<I_M_HU_Item> huItems = huKey2huItems.get(huKey);
		if (huItems != null)
		{
			huItems.add(huItem);

			// sort to make sure that the order we expect is still preserved
			Collections.sort(huItems, IHandlingUnitsDAO.HU_ITEMS_COMPARATOR);
		}

		//
		// Init Included HUs list
		final Object huItemKey = mkHUItemKey(huItem);
		huItemKey2includedHUs.put(huItemKey, new ArrayList<I_M_HU>());

		return huItem;
	}

	@Override
	public List<I_M_HU_Item> retrieveItems(@NonNull final I_M_HU hu)
	{
		final Object huKey = mkHUKey(hu);
		ArrayList<I_M_HU_Item> huItems = huKey2huItems.get(huKey);
		if (huItems == null)
		{
			huItems = new ArrayList<>(db.retrieveItems(hu));

			for (final I_M_HU_Item huItem : huItems)
			{
				huItem.setM_HU(hu);
			}

			huKey2huItems.put(huKey, huItems);
		}

		return new ArrayList<>(huItems);
	}

	@Override
	public I_M_HU retrieveParent(final I_M_HU hu)
	{
		return db.retrieveParent(hu);
	}

	@Override
	public int retrieveParentId(final I_M_HU hu)
	{
		return db.retrieveParentId(hu);
	}

	@Override
	public I_M_HU_Item retrieveParentItem(final I_M_HU hu)
	{
		final I_M_HU_Item parentHUItem = db.retrieveParentItem(hu);

		return parentHUItem;
	}

	@Override
	public ArrayList<I_M_HU> retrieveIncludedHUs(@NonNull final I_M_HU_Item huItem)
	{
		final Object huItemKey = mkHUItemKey(huItem);
		ArrayList<I_M_HU> includedHUs = huItemKey2includedHUs.get(huItemKey);
		if (includedHUs == null)
		{
			includedHUs = new ArrayList<>(db.retrieveIncludedHUs(huItem));

			for (final I_M_HU includedHU : includedHUs)
			{
				includedHU.setM_HU_Item_Parent(huItem);
			}

			huItemKey2includedHUs.put(huItemKey, includedHUs);
		}

		return new ArrayList<>(includedHUs);
	}

	@Override
	public void setParentItem(final I_M_HU hu, final I_M_HU_Item parentItem)
	{
		// TODO: Skip if no actual change; shall not happen because the caller it's already checking this

		final int huId = hu.getM_HU_ID();
		final int parentHUItemIdOld = hu.getM_HU_Item_Parent_ID();

		//
		// Perform database change
		db.setParentItem(hu, parentItem);

		//
		// Remove HU from included HUs list of old parent (if any)
		if (parentHUItemIdOld > 0)
		{
			final Object parentHUItemKeyOld = mkHUItemKey(parentHUItemIdOld);
			final ArrayList<I_M_HU> includedHUs = huItemKey2includedHUs.get(parentHUItemKeyOld);
			if (includedHUs != null)
			{
				boolean removed = false;
				for (final Iterator<I_M_HU> it = includedHUs.iterator(); it.hasNext();)
				{
					final I_M_HU includedHU = it.next();
					if (includedHU.getM_HU_ID() == huId)
					{
						it.remove();
						removed = true;
						break;
					}
				}
				if (!removed)
				{
					throw new HUException("Included HU not found in cached included HUs list of old parent"
							+ "\n HU: " + Services.get(IHandlingUnitsBL.class).getDisplayName(hu)
							+ "\n Included HUs (cached): " + includedHUs);
				}
			}
		}

		//
		// Add HU to included HUs list of new parent (if any)
		final int parentHUItemIdNew = hu.getM_HU_Item_Parent_ID();
		if (parentHUItemIdNew > 0)
		{
			final Object parentHUItemKeyNew = mkHUItemKey(parentHUItemIdNew);
			final ArrayList<I_M_HU> includedHUs = huItemKey2includedHUs.get(parentHUItemKeyNew);
			if (includedHUs != null)
			{
				boolean added = false;
				for (final ListIterator<I_M_HU> it = includedHUs.listIterator(); it.hasNext();)
				{
					final I_M_HU includedHU = it.next();
					if (includedHU.getM_HU_ID() == huId)
					{
						it.set(hu);
						added = true;
						break;
					}
				}
				if (!added)
				{
					includedHUs.add(hu);
				}
			}
		}
	}
}
