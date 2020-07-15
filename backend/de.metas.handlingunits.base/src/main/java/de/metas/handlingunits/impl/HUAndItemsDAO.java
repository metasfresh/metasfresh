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

import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TrxRunnable;

import com.google.common.annotations.VisibleForTesting;

import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.exceptions.HUPIInvalidConfigurationException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public final class HUAndItemsDAO extends AbstractHUAndItemsDAO
{
	public static final transient HUAndItemsDAO instance = new HUAndItemsDAO();

	private HUAndItemsDAO()
	{
	}

	@Override
	public void saveHU(final I_M_HU hu)
	{
		final boolean isNew = hu.getM_HU_ID() <= 0;

		if (isNew)
		{
			HUItemsLocalCache.getCreate(hu)
					.setEmptyNotStaled();
		}

		// task 07600
		final String huTrxName = InterfaceWrapperHelper.getTrxName(hu);
		Services.get(ITrxManager.class).run(huTrxName, new TrxRunnable()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				InterfaceWrapperHelper.save(hu, localTrxName);
			}
		});

		//
		// If our HU was deactivated/destroyed => removed it from Parent's cache
		if (!hu.isActive())
		{
			final I_M_HU_Item parentHUItem = retrieveParentItem(hu);
			if (parentHUItem != null)
			{
				IncludedHUsLocalCache.getCreate(parentHUItem).removeItem(hu);
			}
		}
	}

	@Override
	public void delete(final I_M_HU hu)
	{
		final I_M_HU_Item parentHUItem = retrieveParentItem(hu);

		InterfaceWrapperHelper.delete(hu);

		if (parentHUItem != null)
		{
			IncludedHUsLocalCache.getCreate(parentHUItem).removeItem(hu);
		}
	}

	@Override
	public I_M_HU retrieveParent(@NonNull final I_M_HU hu)
	{
		final I_M_HU_Item itemParent = hu.getM_HU_Item_Parent();
		if (itemParent == null)
		{
			return null;
		}
		return itemParent.getM_HU();
	}

	@Override
	public int retrieveParentId(final I_M_HU hu)
	{
		final I_M_HU_Item itemParent = hu.getM_HU_Item_Parent();
		if (itemParent == null)
		{
			return -1;
		}
		return itemParent.getM_HU_ID();
	}

	@Override
	public I_M_HU_Item retrieveParentItem(final I_M_HU hu)
	{
		final I_M_HU_Item parentHUItem = hu.getM_HU_Item_Parent();
		if (parentHUItem == null || parentHUItem.getM_HU_Item_ID() <= 0)
		{
			return null;
		}

		return parentHUItem;
	}

	@Override
	public void setParentItem(final I_M_HU hu, final I_M_HU_Item parentHUItem)
	{
		final I_M_HU_Item parentHUItemOld = retrieveParentItem(hu);

		hu.setM_HU_Item_Parent(parentHUItem);
		saveHU(hu);

		//
		// Caches update
		{
			if (parentHUItemOld != null)
			{
				IncludedHUsLocalCache.getCreate(parentHUItemOld).removeItem(hu);
			}
			if (parentHUItem != null)
			{
				IncludedHUsLocalCache.getCreate(parentHUItem).addItem(hu);
			}
		}

	}

	@Override
	public List<I_M_HU> retrieveIncludedHUs(final I_M_HU_Item item)
	{
		final IncludedHUsLocalCache includedHUsCache = IncludedHUsLocalCache.getCreate(item);
		return includedHUsCache.getItems();
	}

	@Override
	public List<I_M_HU_Item> retrieveItems(final I_M_HU hu)
	{
		final HUItemsLocalCache huItemsCache = HUItemsLocalCache.getCreate(hu);
		return huItemsCache.getItems();
	}

	@VisibleForTesting
	public static I_M_HU_Item createHUItemNoSave(
			@NonNull final I_M_HU hu,
			@NonNull final I_M_HU_PI_Item piItem)
	{
		final boolean huIsAggregateHU = Services.get(IHandlingUnitsBL.class).isAggregateHU(hu);

		//
		// make different sanity checks, based on the item type
		HuPackingInstructionsVersionId huPIVersionId = HuPackingInstructionsVersionId.ofRepoId(hu.getM_HU_PI_Version_ID());
		if (huIsAggregateHU)
		{
			Check.errorUnless(huPIVersionId.isVirtual(), "The M_HU_PI_Version_ID of the compressed hu={} has to be the one of the virtual M_HU_PI_Version");
		}
		else
		{
			// if the given HU is *not* an aggregate HU, then its M_HU_PI_Version_ID must match the item's M_HU_PI_Version_ID
			final boolean huAndItemHaveSamePI = huPIVersionId.getRepoId() == piItem.getM_HU_PI_Version_ID();
			Check.errorUnless(huAndItemHaveSamePI,
					"The given hu has M_HU_PI_Version_ID={} while the given piItem has M_HU_PI_Version_ID={}; hu={}; piItem={}",
					huPIVersionId, piItem.getM_HU_PI_Version_ID(), hu, piItem);
		}

		final I_M_HU_Item item = InterfaceWrapperHelper.newInstance(I_M_HU_Item.class, hu);
		item.setAD_Org_ID(hu.getAD_Org_ID());
		item.setM_HU(hu);
		item.setM_HU_PI_Item(piItem);

		final String itemType = piItem.getItemType();
		item.setItemType(itemType); // gh #460: we kindof denormalized the item type, so set it here.

		if (X_M_HU_Item.ITEMTYPE_PackingMaterial.equals(itemType))
		{
			final int huPackingMaterialId = piItem.getM_HU_PackingMaterial_ID();
			if (huPackingMaterialId <= 0)
			{
				throw new HUPIInvalidConfigurationException("No packing material defined", piItem);
			}
			item.setM_HU_PackingMaterial_ID(huPackingMaterialId);
		}
		return item;
	}

	@Override
	public I_M_HU_Item createHUItem(final I_M_HU hu, final I_M_HU_PI_Item piItem)
	{
		final I_M_HU_Item item = createHUItemNoSave(hu, piItem);
		return finalizeAndStoreItem(hu, item);
	}

	@Override
	public I_M_HU_Item createChildHUItem(@NonNull final I_M_HU hu)
	{
		return createHuItemWithoutPI(hu, X_M_HU_Item.ITEMTYPE_Material);
	}

	@Override
	public I_M_HU_Item createAggregateHUItem(@NonNull final I_M_HU hu)
	{
		return createHuItemWithoutPI(hu, X_M_HU_Item.ITEMTYPE_HUAggregate);
	}

	private I_M_HU_Item createHuItemWithoutPI(@NonNull final I_M_HU hu, @NonNull final String itemType)
	{
		final I_M_HU_Item item = InterfaceWrapperHelper.newInstance(I_M_HU_Item.class, hu);
		item.setAD_Org_ID(hu.getAD_Org_ID());
		item.setM_HU(hu);
		item.setItemType(itemType);

		return finalizeAndStoreItem(hu, item);
	}

	private I_M_HU_Item finalizeAndStoreItem(final I_M_HU hu, final I_M_HU_Item item)
	{
		InterfaceWrapperHelper.save(item);

		// Update HU Items cache
		HUItemsLocalCache
				.getCreate(hu)
				.addItem(item);

		return item;
	}
}
