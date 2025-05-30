package de.metas.handlingunits.shipmentschedule.api.impl;

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

import java.math.BigDecimal;

import de.metas.inout.ShipmentScheduleId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.storage.impl.AbstractProductStorage;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Product storage oriented on {@link I_M_ShipmentSchedule}'s QtyPicked.
 *
 * @author tsa
 *
 */
public class ShipmentScheduleQtyPickedProductStorage extends AbstractProductStorage
{
	public static ShipmentScheduleQtyPickedProductStorage of(final I_M_ShipmentSchedule shipmentSchedule)
	{
		return new ShipmentScheduleQtyPickedProductStorage(shipmentSchedule);
	}
	
	private final transient IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

	private final I_M_ShipmentSchedule shipmentSchedule;
	private boolean staled = false;

	public ShipmentScheduleQtyPickedProductStorage(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		setConsiderForceQtyAllocationFromRequest(false); // TODO: consider changing it to "true" (default)

		Check.assumeNotNull(shipmentSchedule, "shipmentSchedule not null");
		this.shipmentSchedule = shipmentSchedule;
	}

	@Override
	protected BigDecimal retrieveQtyInitial()
	{
		checkStaled();

		//
		// Calculate the initial Qty (i.e. Qty To Pick) as target Qty minus how much was picked and not delivered, minus how much was delivered
		// NOTE: we cannot rely on QtyToDeliver because that one is about what it needs to be delivered *and* we are able to deliver it.
		// But in our case we need to have how much is Picked but not delivered because we want to let the API/user to "un-pick" that quantity if they want.

		final Capacity capacityTotal = getTotalCapacity();
		final BigDecimal qtyTarget = capacityTotal.toBigDecimal();
		BigDecimal qtyToPick = qtyTarget;

		// NOTE: we shall consider QtyDelivered and QtyPickedNotDelivered only if the QtyToDeliver_Override is not set,
		// because if it's set this is what we actually want to deliver, no matter what!
		final boolean hasQtyToDeliverOverride = !InterfaceWrapperHelper.isNull(shipmentSchedule, I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver_Override);
		if (!hasQtyToDeliverOverride)
		{
			final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);

			final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
			final BigDecimal qtyPickedNotDelivered = shipmentScheduleAllocDAO.retrieveNotOnShipmentLineQty(shipmentScheduleId);
			final BigDecimal qtyDelivered = shipmentSchedule.getQtyDelivered();

			qtyToPick = qtyToPick.subtract(qtyPickedNotDelivered).subtract(qtyDelivered);
		}

		// NOTE: Qty to Pick is the actual storage qty
		return qtyToPick;
	}

	@Override
	protected Capacity retrieveTotalCapacity()
	{
		checkStaled();

		//
		// Get shipment schedule's target quantity (i.e. how much we need to deliver in total)
		// * in case we have QtyToDeliver_Override we consider this right away
		// * else we consider the QtyOrdered
		final BigDecimal qtyTarget;
		if (!InterfaceWrapperHelper.isNull(shipmentSchedule, I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver_Override))
		{
			qtyTarget = shipmentSchedule.getQtyToDeliver_Override();
		}
		else
		{
			// task 09005: make sure the correct qtyOrdered is taken from the shipmentSchedule
			final BigDecimal qtyOrdered = Services.get(IShipmentScheduleEffectiveBL.class).computeQtyOrdered(shipmentSchedule);
			qtyTarget = qtyOrdered;
		}

		//
		// Create the total capacity based on qtyTarget
		final ProductId productId = ProductId.ofRepoId(shipmentSchedule.getM_Product_ID());
		final I_C_UOM uom = shipmentScheduleBL.getUomOfProduct(shipmentSchedule);
		return Capacity.createCapacity(
				qtyTarget, // qty
				productId, // product
				uom, // uom
				false // allowNegativeCapacity
		);
	}

	@Override
	protected void beforeMarkingStalled()
	{
		staled = true;
	}

	private final void checkStaled()
	{
		if (!staled)
		{
			return;
		}

		InterfaceWrapperHelper.refresh(shipmentSchedule);
		staled = false;
	}
}
