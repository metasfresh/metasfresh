package de.metas.inoutcandidate.spi;

import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Service;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
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

@Service
public class ShipmentScheduleOrderDocFactory
{
	public ShipmentScheduleOrderDoc createFor(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return ShipmentScheduleOrderDoc.builder()
				.preparationDate(getOrderPreparationDate(shipmentSchedule))
				.deliveryDate(getOrderLineDeliveryDate(shipmentSchedule))
				.billPartner(shipmentSchedule.getC_OrderLine().getC_Order().getBill_BPartner())
				.build();
	}

	/**
	 * Fetch it from order header if possible.
	 * 
	 * @param sched
	 * @return
	 */
	private static Timestamp getOrderPreparationDate(@NonNull final I_M_ShipmentSchedule sched)
	{
		if (sched.getC_Order_ID() <= 0)
		{
			return null;
		}

		final I_C_Order order = sched.getC_Order();

		final Timestamp preparationDate = order.getPreparationDate();
		if (preparationDate != null)
		{
			return preparationDate;
		}

		return null;
	}

	private static Timestamp getOrderLineDeliveryDate(@NonNull final I_M_ShipmentSchedule sched)
	{
		// Fetch it from order line if possible
		if (sched.getC_OrderLine_ID() > 0)
		{
			final I_C_OrderLine orderLine = sched.getC_OrderLine();
			final Timestamp datePromised = orderLine.getDatePromised();
			if (datePromised != null)
			{
				return datePromised;
			}
		}

		// Fetch it from order header if possible
		if (sched.getC_Order_ID() > 0)
		{
			final I_C_Order order = sched.getC_Order();
			final Timestamp datePromised = order.getDatePromised();
			if (datePromised != null)
			{
				return datePromised;
			}
		}

		// Fail miserably...
		throw new AdempiereException("@NotFound@ @DeliveryDate@"
				+ "\n @M_ShipmentSchedule_ID@: " + sched
				+ "\n @C_OrderLine_ID@: " + sched.getC_OrderLine()
				+ "\n @C_Order_ID@: " + sched.getC_Order());
	}
}
