package de.metas.handlingunits.inventory.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.inventory.InventoryLineRepository;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.inventory.InventoryConstants;
import de.metas.inventory.InventoryLineId;
import de.metas.quantity.Quantity;
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

@Interceptor(I_M_InventoryLine.class)
@Component("de.metas.handlingunits.inventory.interceptor.M_InventoryLine")
public class M_InventoryLine
{
	private final InventoryLineRepository inventoryLineRepository;

	public M_InventoryLine(@NonNull final InventoryLineRepository inventoryLineRepository)
	{
		this.inventoryLineRepository = inventoryLineRepository;
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_M_InventoryLine.COLUMNNAME_QtyCount)
	public void distributeQuantityToHUs(@NonNull final I_M_InventoryLine inventoryLineRecord)
	{
		final boolean isSingleHU = InventoryConstants.HUAggregationType_SINGLE_HU.equals(inventoryLineRecord.getHUAggregationType());
		if(isSingleHU)
		{
			return; // nothing to do
		}


		final InventoryLineId inventoryLineId = InventoryLineId.ofRepoId(inventoryLineRecord.getM_InventoryLine_ID());
		final InventoryLine inventoryLine = inventoryLineRepository.getById(inventoryLineId);

		final Quantity qtyCount = Quantity.of(inventoryLineRecord.getQtyCount(), inventoryLineRecord.getC_UOM());
		inventoryLine.withCountQty(qtyCount);

		inventoryLineRepository.save(inventoryLine);
	}
}
