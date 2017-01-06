package de.metas.handlingunits.impl;

import java.math.BigDecimal;

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
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.TrxRunnable;

import com.google.common.annotations.VisibleForTesting;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.exceptions.HUPIInvalidConfigurationException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_Item;

public final class HUAndItemsDAO extends AbstractHUAndItemsDAO
{
	public static final transient HUAndItemsDAO instance = new HUAndItemsDAO();

	private HUAndItemsDAO()
	{
		super();
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
	public I_M_HU retrieveParent(final I_M_HU hu)
	{
		final I_M_HU_Item itemParent = hu.getM_HU_Item_Parent();
		if (itemParent == null)
		{
			return null;
		}
		return itemParent.getM_HU();
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
	public static I_M_HU_Item createHUItemNoSave(final I_M_HU hu, final I_M_HU_PI_Item piItem)
	{
		Check.assumeNotNull(hu, "hu not null");
		Check.assumeNotNull(piItem, "piItem not null");

		//
		// make different sanity checks, based on the item type
		if (hu.getM_HU_Item_Parent() != null && X_M_HU_Item.ITEMTYPE_HUAggregate.equals(hu.getM_HU_Item_Parent().getItemType()))
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(hu);
			final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
			Check.errorUnless(hu.getM_HU_PI_Version_ID() == handlingUnitsDAO.retrieveVirtualPIItem(ctx).getM_HU_PI_Version_ID(),
					"The M_HU_PI_Version_ID of the compressed hu={} has to be the one of the virtual M_HU_PI_Version");
		}
		else
		{
			// if the given HU is *not* an aggregate HU, then its M_HU_PI_Version_ID must match the item's M_HU_PI_Version_ID
			Check.errorUnless(hu.getM_HU_PI_Version_ID() == piItem.getM_HU_PI_Version_ID(),
					"Incompatible HU's PI Version (hu={}) and Item PI Version (={})",
					hu, piItem);
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
			final BigDecimal qty = piItem.getQty();
			if (qty.signum() <= 0)
			{
				throw new HUPIInvalidConfigurationException("Invalid packing material quantity defined", piItem);
			}
			item.setM_HU_PackingMaterial_ID(huPackingMaterialId);
			item.setQty(qty);
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
	public I_M_HU_Item createAggregateHUItem(final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "hu not null");
		final I_M_HU_Item item = InterfaceWrapperHelper.newInstance(I_M_HU_Item.class, hu);
		item.setAD_Org_ID(hu.getAD_Org_ID());
		item.setM_HU(hu);
		item.setItemType(X_M_HU_Item.ITEMTYPE_HUAggregate);

		return finalizeAndStoreItem(hu, item);
	}

	private I_M_HU_Item finalizeAndStoreItem(final I_M_HU hu, final I_M_HU_Item item)
	{
		InterfaceWrapperHelper.save(item);

		// FIXME: this is making de.metas.customer.picking.service.impl.PackingServiceTest to fail
		// IncludedHUsLocalCache
		// .getCreate(item)
		// .setEmptyNotStaled();

		//
		// Update HU Items cache
		HUItemsLocalCache
				.getCreate(hu)
				.addItem(item);

		return item;
	}
}
