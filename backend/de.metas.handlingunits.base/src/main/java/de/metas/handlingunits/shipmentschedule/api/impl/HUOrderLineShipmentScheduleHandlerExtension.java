package de.metas.handlingunits.shipmentschedule.api.impl;

import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Component;

import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.TableRecordMDC;
import de.metas.order.inoutcandidate.OrderLineShipmentScheduleHandlerExtension;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Component
public class HUOrderLineShipmentScheduleHandlerExtension implements OrderLineShipmentScheduleHandlerExtension
{
	private final IHUShipmentScheduleBL shipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);

	@Override
	public void updateShipmentScheduleFromOrderLine(final I_M_ShipmentSchedule shipmentSchedule, final I_C_OrderLine orderLine)
	{
		try (final MDCCloseable shipmentScheduleMDC = TableRecordMDC.putTableRecordReference(shipmentSchedule);
				final MDCCloseable orderLineMDC = TableRecordMDC.putTableRecordReference(orderLine);)
		{
			shipmentScheduleBL.updateHURelatedValuesFromOrderLine(shipmentSchedule);
		}
	}

}
