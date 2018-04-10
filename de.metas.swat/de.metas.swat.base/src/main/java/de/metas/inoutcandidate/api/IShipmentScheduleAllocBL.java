package de.metas.inoutcandidate.api;

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

import org.adempiere.util.ISingletonService;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.quantity.Quantity;

public interface IShipmentScheduleAllocBL extends ISingletonService
{

	/**
	 * Add QtyPickedDiff to current QtyPicked of given shipment schedule
	 *
	 * @param sched
	 * @param qtyPickedDiff
	 * @return qtyPicked record for this addition
	 */
	I_M_ShipmentSchedule_QtyPicked addQtyPicked(I_M_ShipmentSchedule sched, Quantity qtyPickedDiff);

	/**
	 * Set QtyPicked for given shipment schedule
	 *
	 * @param sched
	 * @param qtyPicked qty picked in shipment schedule's UOM
	 */
	void setQtyPicked(I_M_ShipmentSchedule sched, BigDecimal qtyPicked);

	/**
	 *
	 * @param alloc
	 * @return true if given alloc was already delivered (i.e. {@link I_M_ShipmentSchedule_QtyPicked#getM_InOutLine_ID()} is set).
	 *         Note: task 08959
	 *         Only the allocations made on inout lines that belong to a completed inout are considered Delivered.
	 */
	boolean isDelivered(I_M_ShipmentSchedule_QtyPicked alloc);

}
