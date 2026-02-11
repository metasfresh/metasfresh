/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuItemId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Item_Storage;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CopyHUsCommand
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUAttributesDAO huAttributesDAO = Services.get(IHUAttributesDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	private final ImmutableSet<HuId> huIdsToCopy;
	private final String huStatus;
	private final LocatorId targetLocatorId;
	@Nullable private final Quantity qtyCU;
	@Nullable private final ProductId cuProductId;

	/**
	 * Map containing the exact quantity to take from each Source HU.
	 * If an HU is not in this map, it (and its storage) should not be copied.
	 * Calculated before copying starts to ensure deterministic distribution.
	 */
	private @Nullable Map<HuId, Quantity> mapSourceHuId2AllocatedQty;

	// Helper to lookup Parent HU for an Item during storage copy
	private final Map<HuItemId, HuId> mapSourceItemId2SourceHuId = new HashMap<>();

	private final HashMap<HuId, HuId> old2new_M_HU_ID = new HashMap<>();
	private final HashMap<HuItemId, HuItemId> old2new_M_HU_Item_ID = new HashMap<>();
	private final LinkedHashMap<HuId, I_M_HU> old2new_HU = new LinkedHashMap<>();

	@Builder
	private CopyHUsCommand(
			@NonNull @Singular("huIdToCopy") final ImmutableSet<HuId> huIdsToCopy,
			@Nullable final WarehouseId targetWarehouseId,
			@Nullable final Quantity qtyCU,
			@Nullable final ProductId limitProductId)
	{
		this.huIdsToCopy = huIdsToCopy;
		this.huStatus = X_M_HU.HUSTATUS_Planning;
		this.qtyCU = qtyCU;
		this.cuProductId = limitProductId;

		if (targetWarehouseId != null)
		{
			final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
			this.targetLocatorId = warehouseBL.getOrCreateDefaultLocatorId(targetWarehouseId);
		}
		else
		{
			this.targetLocatorId = null;
		}
	}

	public static class CopyHUsCommandBuilder
	{
		public CopyHUsResponse execute() {return build().execute();}
	}

	public CopyHUsResponse execute()
	{
		trxManager.assertThreadInheritedTrxExists();

		// Pre-calculate which source HUs are needed to satisfy the quantity
		if (qtyCU != null)
		{
			calculateSourceHUsToCopy();
		}
		else
		{
			mapSourceHuId2AllocatedQty = null; // Copy everything
		}

		copyHUs(huIdsToCopy);

		return buildResponse();
	}

	private void calculateSourceHUsToCopy()
	{
		mapSourceHuId2AllocatedQty = new HashMap<>();
		Quantity remaining = Check.assumeNotNull(qtyCU, "qtyCU is not null");

		for (final HuId rootHuId : huIdsToCopy)
		{
			if (remaining.signum() <= 0)
			{
				break;
			}
			final I_M_HU rootHU = handlingUnitsDAO.getById(rootHuId);
			remaining = collectHUsRecursive(rootHU, remaining);
		}
	}

	private Quantity collectHUsRecursive(final I_M_HU hu, final Quantity needed)
	{
		if (needed.signum() <= 0)
		{
			return needed;
		}

		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());
		Quantity availableInThisHU = Quantitys.zero(needed.getUomId());

		final List<I_M_HU_Item> items = handlingUnitsDAO.retrieveItems(hu);

		// 1. Recurse children FIRST (Bottom-Up strategy)
		// This ensures we pick up CUs before consuming quantity at the TU level (if any exists there)
		final List<I_M_HU> children = handlingUnitsDAO.retrieveIncludedHUs(hu);

		Quantity remainingNeeded = needed;
		boolean keepThisHU = false;

		for (final I_M_HU child : children)
		{
			final Quantity newRemaining = collectHUsRecursive(child, remainingNeeded);
			if (newRemaining.compareTo(remainingNeeded) < 0)
			{
				keepThisHU = true;
				remainingNeeded = newRemaining;
			}

			if (remainingNeeded.signum() <= 0)
			{
				break;
			}
		}

		// 2. If we still need quantity, check if this HU has storage directly
		if (remainingNeeded.signum() > 0)
		{
			final Set<HuItemId> itemIds = items.stream()
					.map(item -> HuItemId.ofRepoId(item.getM_HU_Item_ID()))
					.collect(Collectors.toSet());

			if (!itemIds.isEmpty())
			{
				final List<I_M_HU_Item_Storage> itemStorages = handlingUnitsDAO.retrieveAllItemStoragesNoCache(itemIds);
				for (final I_M_HU_Item_Storage storage : itemStorages)
				{
					if (cuProductId == null || ProductId.equals(ProductId.ofRepoId(storage.getM_Product_ID()), cuProductId))
					{
						final UomId productUomId = productBL.getStockUOMId(cuProductId != null ? cuProductId : ProductId.ofRepoId(storage.getM_Product_ID()));
						final Quantity storageQty = Quantitys.of(storage.getQty(), productUomId);
						final Quantity storageQtyConverted = uomConversionBL.convertQuantityTo(storageQty, cuProductId, needed.getUomId());

						availableInThisHU = availableInThisHU.add(storageQtyConverted);
					}
				}
			}

			if (availableInThisHU.signum() > 0)
			{
				final Quantity toTake = availableInThisHU.min(remainingNeeded);

				mapSourceHuId2AllocatedQty.put(huId, toTake);

				remainingNeeded = remainingNeeded.subtract(toTake);
				keepThisHU = true;
			}
		}

		// Mark container HU as needed (with 0 quantity of its own, but present in map)
		if (keepThisHU && !mapSourceHuId2AllocatedQty.containsKey(huId))
		{
			mapSourceHuId2AllocatedQty.put(huId, Quantitys.zero(needed.getUomId()));
		}

		return remainingNeeded;
	}

	private CopyHUsResponse buildResponse()
	{
		final CopyHUsResponse.CopyHUsResponseBuilder response = CopyHUsResponse.builder();
		for (final HuId oldHUId : huIdsToCopy)
		{
			if (old2new_HU.containsKey(oldHUId))
			{
				response.item(CopyHUsResponse.CopyHUsResponseItem.builder()
						.oldHUId(oldHUId)
						.newHU(old2new_HU.get(oldHUId))
						.build());
			}
		}
		return response.build();
	}

	private void copyHUs(final Set<HuId> rootOldHUIds)
	{
		if (rootOldHUIds.isEmpty())
		{
			return;
		}

		final HashSet<HuId> allOldHUIds = new HashSet<>();
		final HashSet<HuItemId> allOldHUItemIds = new HashSet<>();

		List<I_M_HU> oldHUs = handlingUnitsDAO.getByIds(rootOldHUIds);

		// Filter root HUs
		if (mapSourceHuId2AllocatedQty != null)
		{
			oldHUs = oldHUs.stream()
					.filter(hu -> mapSourceHuId2AllocatedQty.containsKey(HuId.ofRepoId(hu.getM_HU_ID())))
					.collect(Collectors.toList());
		}

		boolean isCopyAsTopLevel = true;
		while (!oldHUs.isEmpty())
		{
			for (final I_M_HU oldHU : oldHUs)
			{
				copyHU(oldHU, isCopyAsTopLevel);
			}

			final ImmutableSet<HuId> oldHUIds = extractHUIds(oldHUs);
			final List<I_M_HU_Item> oldHUItems = handlingUnitsDAO.retrieveAllItemsNoCache(oldHUIds);
			for (final I_M_HU_Item oldHUItem : oldHUItems)
			{
				mapSourceItemId2SourceHuId.put(HuItemId.ofRepoId(oldHUItem.getM_HU_Item_ID()), HuId.ofRepoId(oldHUItem.getM_HU_ID()));
				copyHUItem(oldHUItem);
			}

			final ImmutableSet<HuItemId> oldHUItemIds = extractHUItemIds(oldHUItems);
			List<I_M_HU> childrenHUs = handlingUnitsDAO.retrieveAllIncludedHUsNoCache(oldHUItemIds);

			// Filter children HUs
			if (mapSourceHuId2AllocatedQty != null)
			{
				childrenHUs = childrenHUs.stream()
						.filter(hu -> mapSourceHuId2AllocatedQty.containsKey(HuId.ofRepoId(hu.getM_HU_ID())))
						.collect(Collectors.toList());
			}

			oldHUs = childrenHUs;
			isCopyAsTopLevel = false;

			allOldHUIds.addAll(oldHUIds);
			allOldHUItemIds.addAll(oldHUItemIds);
		}

		//
		// Copy all M_HU_Item_Storage(s) 
		for (final I_M_HU_Item_Storage oldHUItemStorage : handlingUnitsDAO.retrieveAllItemStoragesNoCache(allOldHUItemIds))
		{
			copyHUItemStorage(oldHUItemStorage);
		}

		//
		// Copy all M_HU_Storage(s)
		for (final I_M_HU_Storage oldHUStorage : handlingUnitsDAO.retrieveAllStoragesNoCache(allOldHUIds))
		{
			copyHUStorage(oldHUStorage);
		}

		//
		// Copy all M_HU_Attribute(s)
		for (final I_M_HU_Attribute oldHUAttribute : huAttributesDAO.retrieveAllAttributesNoCache(allOldHUIds))
		{
			copyHUAttribute(oldHUAttribute);
		}
	}

	private ImmutableSet<HuItemId> extractHUItemIds(final List<I_M_HU_Item> oldHUItems)
	{
		return oldHUItems.stream().map(huItem -> HuItemId.ofRepoId(huItem.getM_HU_Item_ID())).collect(ImmutableSet.toImmutableSet());
	}

	private ImmutableSet<HuId> extractHUIds(final List<I_M_HU> oldHUs)
	{
		return oldHUs.stream().map(hu -> HuId.ofRepoId(hu.getM_HU_ID())).collect(ImmutableSet.toImmutableSet());
	}

	private void copyHU(@NonNull final I_M_HU oldHU, final boolean copyAsTopLevel)
	{
		final HuItemId newParentItemId;
		if (copyAsTopLevel)
		{
			newParentItemId = null;
		}
		else
		{
			final HuItemId oldParentItemId = HuItemId.ofRepoIdOrNull(oldHU.getM_HU_Item_Parent_ID());
			if (oldParentItemId != null)
			{
				newParentItemId = old2new_M_HU_Item_ID.get(oldParentItemId);
				if (newParentItemId == null)
				{
					throw new AdempiereException("Parent item was not already cloned for " + oldParentItemId);
				}
			}
			else
			{
				newParentItemId = null;
			}
		}

		final I_M_HU newHU = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		InterfaceWrapperHelper.copyValues(oldHU, newHU);
		newHU.setM_HU_Item_Parent_ID(HuItemId.toRepoId(newParentItemId));
		newHU.setHUStatus(huStatus);
		newHU.setIsActive(true);
		newHU.setClonedFrom_HU_ID(oldHU.getM_HU_ID());

		if (targetLocatorId != null)
		{
			newHU.setM_Locator_ID(targetLocatorId.getRepoId());
		}

		InterfaceWrapperHelper.saveRecord(newHU);

		final HuId oldHUId = HuId.ofRepoId(oldHU.getM_HU_ID());
		final HuId newHUId = HuId.ofRepoId(newHU.getM_HU_ID());
		old2new_M_HU_ID.put(oldHUId, newHUId);
		old2new_HU.put(oldHUId, newHU);
	}

	private void copyHUItem(@NonNull final I_M_HU_Item oldItem)
	{
		final HuId oldHUId = HuId.ofRepoId(oldItem.getM_HU_ID());
		final HuId newHUId = old2new_M_HU_ID.get(oldHUId);
		if (newHUId == null)
		{
			return;
		}

		final I_M_HU_Item newItem = InterfaceWrapperHelper.newInstance(I_M_HU_Item.class);
		InterfaceWrapperHelper.copyValues(oldItem, newItem);
		newItem.setM_HU_ID(newHUId.getRepoId());
		InterfaceWrapperHelper.saveRecord(newItem);

		final HuItemId oldItemId = HuItemId.ofRepoId(oldItem.getM_HU_Item_ID());
		final HuItemId newItemId = HuItemId.ofRepoId(newItem.getM_HU_Item_ID());
		old2new_M_HU_Item_ID.put(oldItemId, newItemId);
	}

	private void copyHUAttribute(final I_M_HU_Attribute oldHUAttribute)
	{
		final HuId oldHUId = HuId.ofRepoId(oldHUAttribute.getM_HU_ID());
		final HuId newHUId = old2new_M_HU_ID.get(oldHUId);
		if (newHUId == null)
		{
			return;
		}

		//FIXME doesn't include AttributeSplitterStrategy for split cases
		final I_M_HU_Attribute newHUAttribute = InterfaceWrapperHelper.newInstance(I_M_HU_Attribute.class);
		InterfaceWrapperHelper.copyValues(oldHUAttribute, newHUAttribute);
		newHUAttribute.setM_HU_ID(newHUId.getRepoId());
		newHUAttribute.setValue(oldHUAttribute.getValue()); // programmatically setting this because the framework handles it as a search key and avoids copying it
		InterfaceWrapperHelper.save(newHUAttribute);
	}

	private void copyHUStorage(final I_M_HU_Storage oldHUStorage)
	{
		final HuId oldHUId = HuId.ofRepoId(oldHUStorage.getM_HU_ID());
		final HuId newHUId = old2new_M_HU_ID.get(oldHUId);
		if (newHUId == null)
		{
			return;
		}

		final I_M_HU_Storage newHUStorage = InterfaceWrapperHelper.newInstance(I_M_HU_Storage.class);
		InterfaceWrapperHelper.copyValues(oldHUStorage, newHUStorage);
		newHUStorage.setM_HU_ID(newHUId.getRepoId());
		InterfaceWrapperHelper.save(newHUStorage);
	}

	private void copyHUItemStorage(final I_M_HU_Item_Storage oldHUItemStorage)
	{
		final HuItemId oldHUItemId = HuItemId.ofRepoId(oldHUItemStorage.getM_HU_Item_ID());
		final HuItemId newHUItemId = old2new_M_HU_Item_ID.get(oldHUItemId);
		if (newHUItemId == null)
		{
			return;
		}

		final I_M_HU_Item_Storage newHUItemStorage = InterfaceWrapperHelper.newInstance(I_M_HU_Item_Storage.class);
		InterfaceWrapperHelper.copyValues(oldHUItemStorage, newHUItemStorage);
		newHUItemStorage.setM_HU_Item_ID(newHUItemId.getRepoId());

		if (mapSourceHuId2AllocatedQty != null)
		{
			final boolean isMatchingProduct = cuProductId == null || cuProductId.getRepoId() == oldHUItemStorage.getM_Product_ID();
			if (isMatchingProduct)
			{
				final HuId sourceHuId = mapSourceItemId2SourceHuId.get(oldHUItemId);
				final Quantity allocatedQty = mapSourceHuId2AllocatedQty.get(sourceHuId);

				if (allocatedQty != null && allocatedQty.signum() > 0)
				{
					final UomId productUomId = productBL.getStockUOMId(cuProductId != null ? cuProductId : ProductId.ofRepoId(oldHUItemStorage.getM_Product_ID()));
					final Quantity currentStorageQty = Quantitys.of(newHUItemStorage.getQty(), productUomId);
					final Quantity allocatedQtyInStorageUOM = uomConversionBL.convertQuantityTo(allocatedQty, cuProductId, productUomId);

					final Quantity finalQtyToTake = currentStorageQty.min(allocatedQtyInStorageUOM);
					newHUItemStorage.setQty(finalQtyToTake.toBigDecimal());

					final Quantity takenInRequestUOM = uomConversionBL.convertQuantityTo(finalQtyToTake, cuProductId, allocatedQty.getUomId());
					mapSourceHuId2AllocatedQty.put(sourceHuId, allocatedQty.subtract(takenInRequestUOM));
				}
				else
				{
					newHUItemStorage.setQty(BigDecimal.ZERO);
				}
			}
			else
			{
				newHUItemStorage.setQty(BigDecimal.ZERO);
			}
		}

		InterfaceWrapperHelper.save(newHUItemStorage);
	}
}