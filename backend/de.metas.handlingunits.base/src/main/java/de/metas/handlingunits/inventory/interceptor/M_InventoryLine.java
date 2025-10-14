package de.metas.handlingunits.inventory.interceptor;

import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.inventory.InventoryLineId;
import de.metas.product.IProductBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

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

@Interceptor(I_M_InventoryLine.class)
@Callout(I_M_InventoryLine.class)
@Component
@RequiredArgsConstructor
public class M_InventoryLine
{
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final InventoryService inventoryService;

	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = org.compiere.model.I_M_InventoryLine.COLUMNNAME_M_Product_ID)
	public void setUOM(@NonNull final I_M_InventoryLine inventoryLine)
	{
		final UomId stockUOMId = inventoryLine.getM_Product_ID() > 0 ? productBL.getStockUOMId(inventoryLine.getM_Product_ID()) : null;
		inventoryLine.setC_UOM_ID(UomId.toRepoId(stockUOMId));
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_M_InventoryLine.COLUMNNAME_QtyCount)
	public void distributeQuantityToHUs(@NonNull final I_M_InventoryLine inventoryLineRecord)
	{
		inventoryService.distributeQuantityToHUs(inventoryLineRecord);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteInventoryLineHURecords(@NonNull final I_M_InventoryLine inventoryLineRecord)
	{
		final InventoryLineId inventoryLineId = InventoryLineId.ofRepoId(inventoryLineRecord.getM_InventoryLine_ID());
		inventoryService.deleteInventoryLineHUs(inventoryLineId);
	}

	@CalloutMethod(columnNames = { I_M_InventoryLine.COLUMNNAME_M_HU_ID, I_M_InventoryLine.COLUMNNAME_M_Product_ID, I_M_InventoryLine.COLUMNNAME_C_UOM_ID })
	public void setQtyBookedFromHU(@NonNull final I_M_InventoryLine inventoryLineRecord)
	{
		inventoryService.updateQtyBookedFromHUStorage(inventoryLineRecord);
	}
}
