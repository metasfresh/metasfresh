package de.metas.handlingunits.trace.model.interceptor;

import java.time.Instant;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.Adempiere;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.trace.HUTraceEvent;
import de.metas.handlingunits.trace.HUTraceEvent.HUTraceEventBuilder;
import de.metas.handlingunits.trace.HUTraceRepository;
import de.metas.handlingunits.trace.HUTraceType;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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
@Interceptor(I_M_ShipmentSchedule_QtyPicked.class)
public class M_ShipmentSchedule_QtyPicked
{

	@ModelChange(timings =
		{
				ModelValidator.TYPE_AFTER_CHANGE,
				ModelValidator.TYPE_BEFORE_DELETE,
				ModelValidator.TYPE_AFTER_NEW
		})
	public void addTraceEvent(@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked)
	{

		// get the top-level HU's ID for our event
		final int huId;
		if (shipmentScheduleQtyPicked.getM_LU_HU_ID() > 0)
		{
			huId = shipmentScheduleQtyPicked.getM_LU_HU_ID();
		}
		else if (shipmentScheduleQtyPicked.getM_TU_HU_ID() > 0)
		{
			huId = shipmentScheduleQtyPicked.getM_TU_HU_ID();
		}
		else if (shipmentScheduleQtyPicked.getVHU_ID() > 0)
		{
			huId = shipmentScheduleQtyPicked.getVHU_ID();
		}
		else
		{
			huId = -1;
		}

		if (huId < 0)
		{
			return;
		}

		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.eventTime(Instant.now())
				.shipmentScheduleId(shipmentScheduleQtyPicked.getM_ShipmentSchedule_ID())
				.type(HUTraceType.MATERIAL_PICKING)
				.huId(huId);

		final HUTraceRepository huTraceRepository = Adempiere.getBean(HUTraceRepository.class);
		huTraceRepository.addEvent(builder.build());
	}
}
