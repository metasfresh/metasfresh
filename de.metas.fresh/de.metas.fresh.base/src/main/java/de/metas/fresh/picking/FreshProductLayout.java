/**
 * 
 */
package de.metas.fresh.picking;

/*
 * #%L
 * de.metas.fresh.base
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
import java.util.Collections;
import java.util.List;

import org.adempiere.util.Check;

import de.metas.adempiere.form.IPackingItem;
import de.metas.adempiere.form.PackingItemsMap;
import de.metas.adempiere.form.terminal.IKeyLayoutSelectionModel;
import de.metas.adempiere.form.terminal.IKeyLayoutSelectionModelAware;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.fresh.picking.form.FreshPackingItemHelper;
import de.metas.fresh.picking.form.IFreshPackingItem;
import de.metas.fresh.picking.form.swing.FreshSwingPackageItems;
import de.metas.fresh.picking.terminal.FreshProductKey;
import de.metas.picking.terminal.ProductLayout;

/**
 * @author cg
 * 
 */
public class FreshProductLayout extends ProductLayout implements IKeyLayoutSelectionModelAware
{

	public FreshProductLayout(ITerminalContext tc)
	{
		super(tc);
		
		//
		// Configure the selection model:
		final IKeyLayoutSelectionModel selectionModel = getKeyLayoutSelectionModel();
		selectionModel.setAllowKeySelection(true);
		selectionModel.setAllowMultipleSelection(false);
		selectionModel.setAutoSelectIfOnlyOne(false); // let the user select it
		selectionModel.setToggleableSelection(false);
	}

	private FreshSwingPackageItems getPackageItems()
	{
		return (FreshSwingPackageItems)getPackageTerminalPanel().getProductKeysPanel();
	}
	
	private PickingSlotKey getSelectedPickingSlotKey()
	{
		FreshSwingPackageItems packageItems = getPackageItems();
		if (packageItems == null)
		{
			return null;
		}
		return packageItems.getSelectedPickingSlotKey(); 
	}

	@Override
	public List<ITerminalKey> createKeys()
	{
		final PackingItemsMap map = getPackageTerminalPanel().getPackItems();

		final List<ITerminalKey> productKeys = new ArrayList<ITerminalKey>();

		final PickingSlotKey selectedPickingSlot = getSelectedPickingSlotKey();

		// do not show all unpacked if a slot is selected
		if (selectedPickingSlot == null)
		{
			// get objects from box 0: means unpacked items
			final int key = PackingItemsMap.KEY_UnpackedItems;
			final List<IPackingItem> unpacked = map.get(key);
			// add to layout unpacked items
			{
				if (!(unpacked == null || unpacked.isEmpty()))
				{
					for (final IPackingItem apck : unpacked)
					{
						final IFreshPackingItem pck = FreshPackingItemHelper.cast(apck);
						final FreshProductKey productKey = createProductKey(pck, key, selectedPickingSlot); // selectedPickingSlot=null
						final IFreshPackingItem allocatedItem = FreshPackingItemHelper.copy(pck);
						productKey.setUnAllocatedPackingItem(allocatedItem);
						productKey.setPackingItem(null); // nothing packed yet in the key
						if (!productKey.isActive())
						{
							continue;
						}
						productKey.setStatus(getProductState(pck, key));
						productKeys.add(productKey);
					}
				}
			}
		}
		else
		{
			// put to layout items from selected box
			final int key = selectedPickingSlot.getM_PickingSlot().getM_PickingSlot_ID();
			final List<IPackingItem> selected = map.get(key);
			if (selected != null && !selected.isEmpty())
			{
				for (final IPackingItem apck : selected)
				{
					final IFreshPackingItem pck = FreshPackingItemHelper.cast(apck);
					final FreshProductKey productKey = createProductKey(pck, key, selectedPickingSlot);
					if (productKey == null || !productKey.isActive())
					{
						continue;
					}
					productKey.setStatus(getProductState(pck, key));
					productKeys.add(productKey);
				}
			}

			// now show unpacked for that specific partner
			final List<IPackingItem> unpacked = map.get(PackingItemsMap.KEY_UnpackedItems);
			final List<IPackingItem> items = getPackageItems().createUnpackedForBpAndBPLoc(unpacked, selectedPickingSlot);
			for (final IPackingItem apck : items)
			{
				final IFreshPackingItem packingItem = FreshPackingItemHelper.cast(apck);
				
				// if the item exist in the list, do not create the key again
				final FreshProductKey productKeyExisting = getExistentProductKey(productKeys, packingItem);
				if (productKeyExisting != null)
				{
					// first set unallocated
					productKeyExisting.setUnAllocatedPackingItem(packingItem);
					productKeyExisting.setStatus(getProductState(packingItem, PackingItemsMap.KEY_UnpackedItems));
					continue;
				}

				final FreshProductKey productKey = createProductKey(packingItem, key, selectedPickingSlot);
				if (productKey == null || !productKey.isActive())
				{
					continue;
				}
				
				productKey.setPackingItem(null);
				productKey.setUnAllocatedPackingItem(packingItem);
				productKey.setStatus(getProductState(packingItem, PackingItemsMap.KEY_UnpackedItems));
				productKeys.add(productKey);
			}

		}
		return Collections.unmodifiableList(productKeys);
	}

	private FreshProductKey createProductKey(final IFreshPackingItem pck,
			final int key,
			final PickingSlotKey selectedPickingSlot)
	{
		final FreshProductKey productKey = new FreshProductKey(getTerminalContext(), pck, key);

		// Make sure the created product key is compatible with given picking slot (if any)
		if (selectedPickingSlot != null && !selectedPickingSlot.isCompatible(productKey))
		{
			return null;
		}
		
		return productKey;
	}

	private static FreshProductKey getExistentProductKey(final List<ITerminalKey> productKeys, final IPackingItem apck)
	{
		for (final ITerminalKey keyItem : productKeys)
		{
			final FreshProductKey productKey = (FreshProductKey)keyItem;
			if (productKey.getPackingItem() != null && Check.equals(productKey.getPackingItem().getGroupingKey(), apck.getGroupingKey()))
			{
				return productKey;
			}
		}
		return null;
	}

	@Override
	public boolean isSameBPartner(final IPackingItem item1, final IPackingItem item2)
	{
		final IFreshPackingItem freshItem1 = (IFreshPackingItem)item1;
		final IFreshPackingItem freshItem2 = (IFreshPackingItem)item2;
		return freshItem1.getC_BPartner_ID() == freshItem2.getC_BPartner_ID();
	}

	@Override
	public boolean isSameBPLocation(final IPackingItem item1, final IPackingItem item2)
	{
		final IFreshPackingItem freshItem1 = (IFreshPackingItem)item1;
		final IFreshPackingItem freshItem2 = (IFreshPackingItem)item2;
		return freshItem1.getC_BPartner_Location_ID() == freshItem2.getC_BPartner_Location_ID();
	}

}
