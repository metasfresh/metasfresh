package org.adempiere.inout.util;

import java.math.BigDecimal;

import org.adempiere.inout.util.IShipmentCandidates.CompleteStatus;
import org.compiere.model.I_M_Product;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Data
@EqualsAndHashCode(exclude = { "shipmentSchedule", "qtyToDeliver", "discarded", "completeStatus" })
public class DeliveryLineCandidate
{
	/**
	 * A more generic replacement for order..needed at least for deliveryRule complete-order
	 */
	@NonNull
	private final DeliveryGroupCandidate group;

	@NonNull
	private final I_M_ShipmentSchedule shipmentSchedule;

	private final int shipmentScheduleId;

	@NonNull
	private BigDecimal qtyToDeliver = BigDecimal.ZERO;

	private boolean discarded = false;

	@NonNull
	private CompleteStatus completeStatus;

	public DeliveryLineCandidate(
			@NonNull final DeliveryGroupCandidate group, 
			@NonNull final I_M_ShipmentSchedule shipmentSchedule, 
			@NonNull final CompleteStatus completeStatus)
	{
		this.group = group;
		this.shipmentSchedule = shipmentSchedule;
		this.shipmentScheduleId = shipmentSchedule.getM_ShipmentSchedule_ID();
		this.completeStatus = completeStatus;
	}

	public I_M_Product getM_Product()
	{
		return shipmentSchedule.getM_Product();
	}
}
