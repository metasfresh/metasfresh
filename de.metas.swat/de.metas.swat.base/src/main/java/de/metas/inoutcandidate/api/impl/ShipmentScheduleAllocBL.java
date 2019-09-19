package de.metas.inoutcandidate.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.swat.base
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

import java.math.BigDecimal;
import java.util.Optional;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOutLine;

import de.metas.document.engine.DocStatus;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.Services;
import lombok.NonNull;

public class ShipmentScheduleAllocBL implements IShipmentScheduleAllocBL
{

	@Override
	public I_M_ShipmentSchedule_QtyPicked createNewQtyPickedRecord(
			@NonNull final I_M_ShipmentSchedule sched,
			@NonNull final StockQtyAndUOMQty stockQtyAndCatchQty)
	{
		final I_M_ShipmentSchedule_QtyPicked schedQtyPicked = newInstance(I_M_ShipmentSchedule_QtyPicked.class, sched);

		schedQtyPicked.setAD_Org_ID(sched.getAD_Org_ID());
		schedQtyPicked.setM_ShipmentSchedule(sched);
		schedQtyPicked.setIsActive(true);
		schedQtyPicked.setQtyPicked(stockQtyAndCatchQty.getStockQty().toBigDecimal());

		final Optional<Quantity> uomQty = stockQtyAndCatchQty.getUOMQtyOpt();
		if (uomQty.isPresent())
		{
			schedQtyPicked.setCatch_UOM_ID(uomQty.get().getUomId().getRepoId());
			schedQtyPicked.setQtyDeliveredCatch(uomQty.get().toBigDecimal());
		}

		saveRecord(schedQtyPicked);

		return schedQtyPicked;
	}

	@Override
	public boolean isDelivered(final I_M_ShipmentSchedule_QtyPicked alloc)
	{
		// task 08959
		// Only the allocations made on inout lines that belong to a completed inout are considered Delivered.
		final I_M_InOutLine line = alloc.getM_InOutLine();
		if (line == null)
		{
			return false;
		}

		final org.compiere.model.I_M_InOut io = line.getM_InOut();

		return DocStatus.ofCode(io.getDocStatus()).isCompletedOrClosed();
	}

	@Override
	public Quantity retrieveQtyPickedAndUnconfirmed(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final IProductBL productBL = Services.get(IProductBL.class);
		final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);

		final BigDecimal qtyPicked = shipmentScheduleAllocDAO.retrieveQtyPickedAndUnconfirmed(shipmentSchedule);
		final I_C_UOM stockUOMRecord = productBL.getStockUOM(ProductId.ofRepoId(shipmentSchedule.getM_Product_ID()));

		return Quantity.of(qtyPicked, stockUOMRecord);
	}

}
