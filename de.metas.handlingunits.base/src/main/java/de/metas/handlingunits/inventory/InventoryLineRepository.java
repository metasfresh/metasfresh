package de.metas.handlingunits.inventory;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOrNew;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.compiere.model.I_M_Inventory;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.inventory.InventoryId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Repository
public class InventoryLineRepository
{
	public ImmutableList<InventoryLine> getByInventoryId(@NonNull final InventoryId inventoryId)
	{
		return null; // TODO
	}

	public void save(@NonNull final InventoryLine inventoryLine)
	{
		final I_M_Inventory inventoryRecord = load(inventoryLine.getInventoryId(), I_M_Inventory.class);

		final I_M_InventoryLine inventoryLineRecord = loadOrNew(inventoryLine.getId(), I_M_InventoryLine.class, inventoryRecord);
		inventoryLineRecord.setAD_Org_ID(inventoryRecord.getAD_Org_ID());
		inventoryLineRecord.setM_Inventory_ID(inventoryLine.getInventoryId().getRepoId());

		final AttributeSetInstanceId asiId = AttributesKeys.createAttributeSetInstanceFromAttributesKey(inventoryLine.getStorageAttributesKey());
		inventoryLineRecord.setM_AttributeSetInstance_ID(asiId.getRepoId());

		// inventoryLine.setM_HU_PI_Item_Product(null); // TODO
		// inventoryLine.setQtyTU(BigDecimal.ZERO); // TODO

		inventoryLineRecord.setM_Locator_ID(inventoryLine.getLocatorId().getRepoId());
		inventoryLineRecord.setM_Product_ID(inventoryLine.getProductId().getRepoId());

		if (inventoryLine.isSingleHUAggregation())
		{
			final InventoryLineHU hu = CollectionUtils.singleElement(inventoryLine.getInventoryLineHUs());
			inventoryLineRecord.setM_HU_ID(hu.getHuId().getRepoId());

			final Quantity countQty = hu.getCountQty();
			inventoryLineRecord.setQtyCount(countQty.getAsBigDecimal());

			final Quantity bookQty = hu.getBookQty();
			inventoryLineRecord.setQtyBook(bookQty.getAsBigDecimal());

			final UomId uomId = Quantity.getCommonUomIdOfAll(countQty, bookQty);
			inventoryLineRecord.setC_UOM_ID(uomId.getRepoId());
		}
		else
		{
			// TODO sync I_M_InventoryLine_HU records

		}

		saveRecord(inventoryLineRecord);
	}
}
