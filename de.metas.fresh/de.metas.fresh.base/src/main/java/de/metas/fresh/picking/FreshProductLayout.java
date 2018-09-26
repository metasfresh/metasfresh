/**
 * 
 */
package de.metas.fresh.picking;

import java.awt.Color;

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
import java.util.List;
import java.util.Objects;

import de.metas.adempiere.form.terminal.IKeyLayoutSelectionModel;
import de.metas.adempiere.form.terminal.IKeyLayoutSelectionModelAware;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.KeyLayout;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.fresh.picking.form.FreshSwingPackageTerminalPanel;
import de.metas.fresh.picking.form.swing.FreshSwingPackageItems;
import de.metas.picking.service.IPackingItem;
import de.metas.picking.service.PackingItemGroupingKey;
import de.metas.picking.service.PackingItemsMap;
import de.metas.picking.service.PackingSlot;
import de.metas.picking.terminal.Utils.PackingStates;

/**
 * @author cg
 * 
 */
public class FreshProductLayout extends KeyLayout implements IKeyLayoutSelectionModelAware
{
	private static final Color DEFAULT_Color = Color.GREEN;

	public FreshProductLayout(final ITerminalContext tc)
	{
		super(tc);
		setColumns(3);
		setDefaultColor(DEFAULT_Color);

		//
		// Configure the selection model:
		final IKeyLayoutSelectionModel selectionModel = getKeyLayoutSelectionModel();
		selectionModel.setAllowKeySelection(true);
		selectionModel.setAllowMultipleSelection(false);
		selectionModel.setAutoSelectIfOnlyOne(false); // let the user select it
		selectionModel.setToggleableSelection(false);
	}

	@Override
	public String getId()
	{
		return "ProductLayout#" + 100;
	}

	@Override
	public boolean isNumeric()
	{
		return false;
	}

	@Override
	public boolean isText()
	{
		return false;
	}

	private FreshSwingPackageTerminalPanel getPackageTerminalPanel()
	{
		return FreshSwingPackageTerminalPanel.cast(getBasePanel());
	}

	private PackingItemsMap getPackingItems()
	{
		return getPackageTerminalPanel().getPackingItems();
	}

	private FreshSwingPackageItems getFreshSwingPackageItems()
	{
		return getPackageTerminalPanel().getProductKeysPanel();
	}

	private PickingSlotKey getSelectedPickingSlotKey()
	{
		FreshSwingPackageItems packageItems = getFreshSwingPackageItems();
		if (packageItems == null)
		{
			return null;
		}
		return packageItems.getSelectedPickingSlotKey();
	}

	@Override
	public List<ITerminalKey> createKeys()
	{
		final PackingItemsMap packingItems = getPackingItems();

		final List<ITerminalKey> productKeys = new ArrayList<>();

		final PickingSlotKey selectedPickingSlot = getSelectedPickingSlotKey();

		// do not show all unpacked if a slot is selected
		if (selectedPickingSlot == null)
		{
			// add to layout unpacked items
			for (final IPackingItem packingItem : packingItems.getBySlot(PackingSlot.UNPACKED))
			{
				final FreshProductKey productKey = createProductKey(packingItem, selectedPickingSlot);
				final IPackingItem allocatedItem = packingItem.copy();
				productKey.setUnAllocatedPackingItem(allocatedItem);
				productKey.resetPackingItem(); // nothing packed yet in the key
				if (!productKey.isActive())
				{
					continue;
				}
				productKey.setStatus(computePackingState(packingItem, PackingSlot.UNPACKED));
				productKeys.add(productKey);
			}
		}
		else
		{
			// put to layout items from selected box
			final PackingSlot key = PackingSlot.ofPickingSlotId(selectedPickingSlot.getPickingSlotId());
			for (final IPackingItem packingItem : packingItems.getBySlot(key))
			{
				final FreshProductKey productKey = createProductKey(packingItem, selectedPickingSlot);
				if (productKey == null || !productKey.isActive())
				{
					continue;
				}
				productKey.setStatus(computePackingState(packingItem, key));
				productKeys.add(productKey);
			}

			// now show unpacked for that specific partner
			final List<IPackingItem> unpacked = packingItems.getBySlot(PackingSlot.UNPACKED);
			final List<IPackingItem> items = getFreshSwingPackageItems().createUnpackedForBpAndBPLoc(unpacked, selectedPickingSlot);
			for (final IPackingItem packingItem : items)
			{
				// if the item exist in the list, do not create the key again
				final FreshProductKey productKeyExisting = getExistentProductKey(productKeys, packingItem);
				if (productKeyExisting != null)
				{
					// first set unallocated
					productKeyExisting.setUnAllocatedPackingItem(packingItem);
					productKeyExisting.setStatus(computePackingState(packingItem, PackingSlot.UNPACKED));
					continue;
				}

				final FreshProductKey productKey = createProductKey(packingItem, selectedPickingSlot);
				if (productKey == null || !productKey.isActive())
				{
					continue;
				}

				productKey.resetPackingItem();
				productKey.setUnAllocatedPackingItem(packingItem);
				productKey.setStatus(computePackingState(packingItem, PackingSlot.UNPACKED));
				productKeys.add(productKey);
			}

		}
		return Collections.unmodifiableList(productKeys);
	}

	private FreshProductKey createProductKey(
			final IPackingItem packingItem,
			final PickingSlotKey selectedPickingSlot)
	{
		final FreshProductKey productKey = new FreshProductKey(getTerminalContext(), packingItem);

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
			if (productKey.getPackingItem() != null
					&& PackingItemGroupingKey.equals(productKey.getPackingItem().getGroupingKey(), apck.getGroupingKey()))
			{
				return productKey;
			}
		}
		return null;
	}

	private PackingStates computePackingState(final IPackingItem packingItem, final PackingSlot packingSlot)
	{
		final PackingItemsMap packItems = getPackingItems();
		boolean unpacked = packingSlot.isUnpacked();
		boolean packed = packItems.streamPackedItems()
				.anyMatch(item -> isMatching(item, packingItem));
		//
		PackingStates state = PackingStates.unpacked;
		if (packed && unpacked)
		{
			state = PackingStates.partiallypacked;
		}
		else if (!packed && unpacked)
		{
			state = PackingStates.unpacked;
		}
		else if (packed && !unpacked)
		{
			state = PackingStates.packed;
		}

		return state;
	}

	private static final boolean isMatching(final IPackingItem item1, final IPackingItem item2)
	{
		return Objects.equals(item1.getProductId(), item2.getProductId())
				&& isSameBPartner(item1, item2)
				&& isSameBPLocation(item1, item2);

	}

	private static boolean isSameBPartner(final IPackingItem item1, final IPackingItem item2)
	{
		final IPackingItem freshItem1 = item1;
		final IPackingItem freshItem2 = item2;
		return Objects.equals(freshItem1.getBPartnerId(), freshItem2.getBPartnerId());
	}

	private static boolean isSameBPLocation(final IPackingItem item1, final IPackingItem item2)
	{
		final IPackingItem freshItem1 = item1;
		final IPackingItem freshItem2 = item2;
		return Objects.equals(freshItem1.getBPartnerLocationId(), freshItem2.getBPartnerLocationId());
	}

}
