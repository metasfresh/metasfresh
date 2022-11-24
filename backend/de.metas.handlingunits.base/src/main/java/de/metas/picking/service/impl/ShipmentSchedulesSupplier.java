/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.picking.service.impl;

import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;

import java.util.HashMap;

public class ShipmentSchedulesSupplier
{
	private final IShipmentScheduleBL shipmentScheduleBL;

	private final HashMap<ShipmentScheduleId, I_M_ShipmentSchedule> shipmentSchedulesById = new HashMap<>();
	private final HashMap<ShipmentScheduleId, Quantity> projectedQtyToDeliverById =  new HashMap<>();

	@Builder
	private ShipmentSchedulesSupplier(@NonNull final IShipmentScheduleBL shipmentScheduleBL)
	{
		this.shipmentScheduleBL = shipmentScheduleBL;
	}

	public I_M_ShipmentSchedule getShipmentScheduleById(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return shipmentSchedulesById.computeIfAbsent(shipmentScheduleId, shipmentScheduleBL::getById);
	}

	public Quantity getProjectedQtyToDeliver(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return projectedQtyToDeliverById.computeIfAbsent(shipmentScheduleId, this::retrieveQtyToDeliver);
	}

	private Quantity retrieveQtyToDeliver(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final I_M_ShipmentSchedule sched = getShipmentScheduleById(shipmentScheduleId);
		return shipmentScheduleBL.getQtyToDeliver(sched);
	}

	public void addQtyDelivered(@NonNull final ShipmentScheduleId shipmentScheduleId, @NonNull final Quantity qtyDelivered)
	{
		Quantity projectedQtyToDeliver = getProjectedQtyToDeliver(shipmentScheduleId)
				.subtract(qtyDelivered);

		projectedQtyToDeliverById.put(shipmentScheduleId, projectedQtyToDeliver);
	}

}
