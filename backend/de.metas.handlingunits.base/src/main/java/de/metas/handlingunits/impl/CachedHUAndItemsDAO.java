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

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuItemId;
import de.metas.handlingunits.IHUAndItemsDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.util.Services;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * This service wraps a {@link HUAndItemsDAO} and caches its results.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@SuppressWarnings("CommentedOutCode")
public class CachedHUAndItemsDAO extends AbstractHUAndItemsDAO
{
	private final HashMap<HuId, ArrayList<I_M_HU_Item>> huKey2huItems = new HashMap<>();
	private final HashMap<HuItemId, ArrayList<I_M_HU>> huItemKey2includedHUs = new HashMap<>();

	private final IHUAndItemsDAO db = HUAndItemsDAO.instance;

	/* package */ CachedHUAndItemsDAO()
	{
	}

	private HuId mkHUKey(final I_M_HU hu) {return HuId.ofRepoId(hu.getM_HU_ID());}

	private HuId mkHUKey(final I_M_HU_Item huItem) {return HuId.ofRepoId(huItem.getM_HU_ID());}

	private HuItemId mkHUItemKey(final I_M_HU_Item huItem) {return HuItemId.ofRepoId(huItem.getM_HU_Item_ID());}

	@Override
	public void saveHU(@NonNull final I_M_HU hu)
	{
		final boolean isNew = hu.getM_HU_ID() <= 0;

		db.saveHU(hu);

		final HuId huKey = mkHUKey(hu);

		if (isNew)
		{
			huKey2huItems.put(huKey, new ArrayList<>());
		}

		// if (!hu.isActive())
		// {
		// 	final int parentHUItemId = hu.getM_HU_Item_Parent_ID();
		// 	if (parentHUItemId > 0)
		// 	{
		// 		// NOTE: don't check this because it's not always valid
		// 		// e.g. destroying an LU on Receipt Schedule, because that planning LU is not usable anymore
		// 		// throw new HUException("Inactivating an included HU not allowed"
		// 		// + "\n HU: " + hu
		// 		// + "\n Parent Item: " + db.retrieveParent(hu));
		//
		// 		// NOTE: this is also not ok when we mark as destroyed a planning LU/TU, but we can live with that for now
		// 		// setParentItem(hu, null); // we are just updating the cache
		// 	}
		// }
	}

	@Override
	public void saveHUItem(@NonNull final I_M_HU_Item huItem)
	{
		db.saveHUItem(huItem);

		//
		// Cache reset:
		huKey2huItems.remove(mkHUKey(huItem));
		huItemKey2includedHUs.remove(mkHUItemKey(huItem));
	}

	@Override
	public void delete(@NonNull final I_M_HU hu)
	{
		final HuId huKey = mkHUKey(hu);

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
		final HuId huKey = mkHUKey(hu);
		final ArrayList<I_M_HU_Item> huItems = huKey2huItems.get(huKey);
		if (huItems != null)
		{
			huItems.add(huItem);

			// sort to make sure that the order we expect is still preserved
			huItems.sort(IHandlingUnitsDAO.HU_ITEMS_COMPARATOR);
		}

		//
		// Init Included HUs list
		final HuItemId huItemKey = mkHUItemKey(huItem);
		huItemKey2includedHUs.put(huItemKey, new ArrayList<>());

		return huItem;
	}

	@Override
	public List<I_M_HU_Item> retrieveItems(@NonNull final I_M_HU hu)
	{
		final HuId huKey = mkHUKey(hu);
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
	public HuId retrieveParentId(final @NonNull I_M_HU hu)
	{
		return db.retrieveParentId(hu);
	}

	@Override
	public I_M_HU_Item retrieveParentItem(final I_M_HU hu)
	{
		return db.retrieveParentItem(hu);
	}

	@Override
	public ArrayList<I_M_HU> retrieveIncludedHUs(@NonNull final I_M_HU_Item huItem)
	{
		final HuItemId huItemKey = mkHUItemKey(huItem);
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

		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());
		final HuItemId parentHUItemIdOld = HuItemId.ofRepoIdOrNull(hu.getM_HU_Item_Parent_ID());

		//
		// Perform database change
		db.setParentItem(hu, parentItem);

		//
		// Remove HU from included HUs list of old parent (if any)
		if (parentHUItemIdOld != null)
		{
			final ArrayList<I_M_HU> includedHUs = huItemKey2includedHUs.get(parentHUItemIdOld);
			if (includedHUs != null)
			{
				boolean removed = false;
				for (final Iterator<I_M_HU> it = includedHUs.iterator(); it.hasNext(); )
				{
					final I_M_HU includedHU = it.next();
					final HuId includedHUId = HuId.ofRepoId(includedHU.getM_HU_ID());
					if (HuId.equals(includedHUId, huId))
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
		// Add HU to include HUs list of new parent (if any)
		final HuItemId parentHUItemIdNew = HuItemId.ofRepoIdOrNull(hu.getM_HU_Item_Parent_ID());
		if (parentHUItemIdNew != null)
		{
			final ArrayList<I_M_HU> includedHUs = huItemKey2includedHUs.get(parentHUItemIdNew);
			if (includedHUs != null)
			{
				boolean added = false;
				for (final ListIterator<I_M_HU> it = includedHUs.listIterator(); it.hasNext(); )
				{
					final I_M_HU includedHU = it.next();
					final HuId includedHUId = HuId.ofRepoId(includedHU.getM_HU_ID());
					if (HuId.equals(includedHUId, huId))
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
