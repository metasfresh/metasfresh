package de.metas.inoutcandidate.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOutLine;

import de.metas.document.engine.IDocumentBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.quantity.Quantity;
import lombok.NonNull;

public class ShipmentScheduleAllocBL implements IShipmentScheduleAllocBL
{
	@Override
	public BigDecimal getQtyPicked(final I_M_ShipmentSchedule sched)
	{
		Check.assumeNotNull(sched, "sched not null");
		return Services.get(IShipmentScheduleAllocDAO.class).retrievePickedNotDeliveredQty(sched);
	}

	@Override
	public void setQtyPicked(final I_M_ShipmentSchedule sched, final BigDecimal qtyPicked)
	{
		final boolean justAdd = false;
		final I_C_UOM uom = Services.get(IShipmentScheduleBL.class).getUomOfProduct(sched);
		setQtyPicked(sched, Quantity.of(qtyPicked, uom), justAdd);
	}

	@Override
	public I_M_ShipmentSchedule_QtyPicked addQtyPicked(
			final I_M_ShipmentSchedule sched,
			final Quantity qtyPickedDiff)
	{
		final boolean justAdd = true;
		return setQtyPicked(sched, qtyPickedDiff, justAdd);
	}

	/**
	 * Adds or sets QtyPicked
	 *
	 * @param justAdd if true, then if will create a {@link I_M_ShipmentSchedule_QtyPicked} only for difference between given <code>qtyPicked</code> and current qty picked.
	 *
	 * @return {@link I_M_ShipmentSchedule_QtyPicked} created record
	 */
	private I_M_ShipmentSchedule_QtyPicked setQtyPicked(
			@NonNull final I_M_ShipmentSchedule sched,
			@NonNull final Quantity qtyPicked,
			final boolean justAdd)
	{
		// Convert QtyPicked to shipment schedule's UOM
		final org.compiere.model.I_M_Product product = sched.getM_Product();
		final I_C_UOM schedUOM = Services.get(IShipmentScheduleBL.class).getUomOfProduct(sched);
		final BigDecimal qtyPickedConv = Services.get(IUOMConversionBL.class).convertQty(product,
				qtyPicked.getQty(),
				qtyPicked.getUOM(), // from UOM
				schedUOM // to UOM
				);

		final BigDecimal qtyPickedToAdd;
		if (justAdd)
		{
			qtyPickedToAdd = qtyPickedConv;
		}
		else
		{
			final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
			final BigDecimal qtyPickedOld = shipmentScheduleAllocDAO.retrievePickedNotDeliveredQty(sched);
			qtyPickedToAdd = qtyPickedConv.subtract(qtyPickedOld);
		}

		final I_M_ShipmentSchedule_QtyPicked schedQtyPicked = newInstance(I_M_ShipmentSchedule_QtyPicked.class, sched);
		schedQtyPicked.setAD_Org_ID(sched.getAD_Org_ID());
		schedQtyPicked.setM_ShipmentSchedule(sched);
		schedQtyPicked.setIsActive(true);
		schedQtyPicked.setQtyPicked(qtyPickedToAdd);
		save(schedQtyPicked);

		return schedQtyPicked;
	}

	@Override
	public boolean isDelivered(final I_M_ShipmentSchedule_QtyPicked alloc)
	{
		// task 08959
		// Only the allocations made on inout lines that belong to a completed inout are considered Delivered.
		final I_M_InOutLine line = alloc.getM_InOutLine();
		if(line == null)
		{
			return false;
		}

		final org.compiere.model.I_M_InOut io = line.getM_InOut();

		return Services.get(IDocumentBL.class).isDocumentCompletedOrClosed(io);
	}
}
