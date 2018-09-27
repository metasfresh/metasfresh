package de.metas.fresh.picking;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.fresh.picking.form.PackingStates;
import de.metas.picking.service.IPackingItem;
import de.metas.picking.service.PackingItemGroupingKey;
import de.metas.picking.service.PackingItemsMap;
import de.metas.picking.service.PackingSlot;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Builder
final class ProductKeyFactory
{
	@NonNull
	private final ITerminalContext terminalContext;
	@NonNull
	private final PackingItemsMap packingItems;
	@Nullable
	private final PickingSlotKey selectedPickingSlot;

	public List<ProductKey> create()
	{
		final List<ProductKey> productKeys = new ArrayList<>();

		// do not show all unpacked if a slot is selected
		if (selectedPickingSlot == null)
		{
			// add to layout unpacked items
			for (final IPackingItem packingItem : packingItems.getUnpackedItems())
			{
				final ProductKey productKey = createProductKeyOrNull(packingItem, selectedPickingSlot);
				if (productKey == null)
				{
					continue;
				}

				final IPackingItem allocatedItem = packingItem.copy();
				productKey.setUnAllocatedPackingItem(allocatedItem);
				productKey.resetPackingItem(); // nothing packed yet in the key
				productKey.setStatus(computePackingState(packingItems, packingItem, PackingSlot.UNPACKED));
				productKeys.add(productKey);
			}
		}
		else
		{
			// put to layout items from selected slot
			final PackingSlot packingSlot = selectedPickingSlot.getPackingSlot();
			for (final IPackingItem packingItem : packingItems.getBySlot(packingSlot))
			{
				final ProductKey productKey = createProductKeyOrNull(packingItem, selectedPickingSlot);
				if (productKey == null)
				{
					continue;
				}

				productKey.setStatus(computePackingState(packingItems, packingItem, packingSlot));
				productKeys.add(productKey);
			}

			// now show unpacked for that specific partner
			for (final IPackingItem packingItem : packingItems.getUnpackedItemsMatching(selectedPickingSlot::isCompatible))
			{
				// if the item exist in the list, do not create the key again
				final ProductKey productKeyExisting = findByGroupingKey(productKeys, packingItem.getGroupingKey());
				if (productKeyExisting != null)
				{
					// first set unallocated
					productKeyExisting.setUnAllocatedPackingItem(packingItem);
					productKeyExisting.setStatus(computePackingState(packingItems, packingItem, PackingSlot.UNPACKED));
				}
				else
				{
					final ProductKey productKey = createProductKeyOrNull(packingItem, selectedPickingSlot);
					if (productKey == null)
					{
						continue;
					}

					productKey.resetPackingItem();
					productKey.setUnAllocatedPackingItem(packingItem);
					productKey.setStatus(computePackingState(packingItems, packingItem, PackingSlot.UNPACKED));
					productKeys.add(productKey);
				}
			}
		}
		return ImmutableList.copyOf(productKeys);
	}

	private ProductKey createProductKeyOrNull(
			final IPackingItem packingItem,
			final PickingSlotKey selectedPickingSlot)
	{
		final ProductKey productKey = new ProductKey(terminalContext, packingItem);

		// Make sure the created product key is compatible with given picking slot (if any)
		if (selectedPickingSlot != null && !selectedPickingSlot.isCompatible(productKey))
		{
			return null;
		}

		return productKey;
	}

	private static ProductKey findByGroupingKey(final List<ProductKey> productKeys, @NonNull final PackingItemGroupingKey groupingKey)
	{
		return productKeys.stream()
				.filter(productKey -> PackingItemGroupingKey.equals(productKey.getGroupingKey(), groupingKey))
				.findFirst()
				.orElse(null);
	}

	private static PackingStates computePackingState(final PackingItemsMap packingItems, final IPackingItem packingItem, final PackingSlot packingSlot)
	{
		final boolean hasUnpacked = packingSlot.isUnpacked();
		final boolean hasPacked = packingItems.hasPackedItemsMatching(item -> isMatching(item, packingItem));

		if (hasPacked)
		{
			return hasUnpacked ? PackingStates.partiallypacked : PackingStates.packed;
		}
		else
		{
			return PackingStates.unpacked;
		}
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
