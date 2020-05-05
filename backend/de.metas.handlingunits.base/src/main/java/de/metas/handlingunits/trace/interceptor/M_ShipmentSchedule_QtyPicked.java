package de.metas.handlingunits.trace.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.trace.HUTraceEventsService;
import de.metas.util.Services;
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
@Component
/* package */ class M_ShipmentSchedule_QtyPicked
{

	private HUTraceEventsService huTraceEventsService;

	public M_ShipmentSchedule_QtyPicked(@NonNull final HUTraceEventsService huTraceEventsService)
	{
		this.huTraceEventsService = huTraceEventsService;

	}

	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_CHANGE,
			ModelValidator.TYPE_AFTER_NEW
	})
	public void addTraceEventForNewAndChange(@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked)
	{
		Services.get(ITrxManager.class)
				.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(trx -> huTraceEventsService.createAndAddFor(shipmentScheduleQtyPicked));
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void addTraceEventForDelete(@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked)
	{
		huTraceEventsService.createAndAddFor(shipmentScheduleQtyPicked);
	}
}
