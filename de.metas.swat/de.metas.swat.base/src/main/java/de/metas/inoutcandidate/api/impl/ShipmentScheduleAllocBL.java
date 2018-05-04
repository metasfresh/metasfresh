package de.metas.inoutcandidate.api.impl;

import static java.math.BigDecimal.ZERO;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
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
	private enum Mode
	{
		/** Just take the given {@code qtyPicked} (converted to sched's UOM ) and set it as the new {@code schedQtyPicked}'s {@code QtyPicked value}. */
		JUST_SET_QTY,

		/** Retrieve the sched's qty that is picked, but not yet shipped, and subtract that quantity from the given {@code qtyPicked}. */
		SUBTRACT_FROM_ALREADY_PICKED_QTY
	}

	@Override
	public void setQtyPicked(final I_M_ShipmentSchedule sched, final BigDecimal qtyPicked)
	{
		final I_C_UOM uom = Services.get(IShipmentScheduleBL.class).getUomOfProduct(sched);
		setQtyPicked(sched, Quantity.of(qtyPicked, uom), Mode.SUBTRACT_FROM_ALREADY_PICKED_QTY);
	}

	@Override
	public I_M_ShipmentSchedule_QtyPicked addQtyPicked(
			final I_M_ShipmentSchedule sched,
			final Quantity qtyPickedDiff)
	{
		return setQtyPicked(sched, qtyPickedDiff, Mode.JUST_SET_QTY);
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
			@NonNull final Mode mode)
	{
		// Convert QtyPicked to shipment schedule's UOM
		final I_C_UOM schedUOM = Services.get(IShipmentScheduleBL.class).getUomOfProduct(sched);
		final BigDecimal qtyPickedConv = Services.get(IUOMConversionBL.class).convertQty(sched.getM_Product_ID(),
				qtyPicked.getQty(),
				qtyPicked.getUOM(), // from UOM
				schedUOM // to UOM
		);

		final BigDecimal qtyPickedToAdd;
		switch (mode)
		{
			case JUST_SET_QTY:
				qtyPickedToAdd = qtyPickedConv;
				break;
			case SUBTRACT_FROM_ALREADY_PICKED_QTY:
				final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
				final BigDecimal qtyPickedOld = shipmentScheduleAllocDAO.retrieveNotOnShipmentLineQty(sched);
				qtyPickedToAdd = qtyPickedConv.subtract(qtyPickedOld);
				break;
			default:
				Check.errorIf(true, "Unexpected mode={}; qtyPicked={}; sched={}", mode, qtyPicked, sched);
				qtyPickedToAdd = ZERO; // won't be reached
				break;
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
		if (line == null)
		{
			return false;
		}

		final org.compiere.model.I_M_InOut io = line.getM_InOut();

		return Services.get(IDocumentBL.class).isDocumentCompletedOrClosed(io);
	}
}
