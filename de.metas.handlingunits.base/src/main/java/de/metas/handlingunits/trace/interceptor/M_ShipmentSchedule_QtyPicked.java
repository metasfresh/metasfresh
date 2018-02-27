package de.metas.handlingunits.trace.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.Adempiere;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.trace.HUTraceEventsService;
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
/* package */ class M_ShipmentSchedule_QtyPicked
{

	@ModelChange(timings =
		{
				ModelValidator.TYPE_AFTER_CHANGE,
				ModelValidator.TYPE_BEFORE_DELETE,
				ModelValidator.TYPE_AFTER_NEW
		})
	public void addTraceEvent(@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked)
	{
		final HUTraceEventsService huTraceEventsService = Adempiere.getBean(HUTraceEventsService.class);
		huTraceEventsService.createAndAddFor(shipmentScheduleQtyPicked);
	}
}
