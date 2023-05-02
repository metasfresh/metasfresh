package de.metas.handlingunits.trace.interceptor;

import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.trace.HUTraceEventsService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

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
class M_ShipmentSchedule_QtyPicked
{
	private final HUTraceEventsService huTraceEventsService;
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

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
		deferredProcessor().schedule(shipmentScheduleQtyPicked);
	}

	private DeferredProcessor deferredProcessor()
	{
		return trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.Fail) // at this point we always run in trx
				.getPropertyAndProcessAfterCommit(
						DeferredProcessor.class.getName(),
						() -> new DeferredProcessor(huTraceEventsService),
						DeferredProcessor::processNow);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void addTraceEventForDelete(@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked)
	{
		huTraceEventsService.createAndAddFor(shipmentScheduleQtyPicked);
	}

	private static class DeferredProcessor
	{
		private final HUTraceEventsService huTraceEventsService;

		private final AtomicBoolean processed = new AtomicBoolean(false);
		private final LinkedHashMap<Integer, I_M_ShipmentSchedule_QtyPicked> records = new LinkedHashMap<>();

		public DeferredProcessor(@NonNull final HUTraceEventsService huTraceEventsService)
		{
			this.huTraceEventsService = huTraceEventsService;
		}

		public void schedule(@NonNull final I_M_ShipmentSchedule_QtyPicked record)
		{
			if (processed.get())
			{
				throw new AdempiereException("already processed: " + this);
			}

			this.records.put(record.getM_ShipmentSchedule_QtyPicked_ID(), record);
		}

		public void processNow()
		{
			final boolean alreadyProcessed = processed.getAndSet(true);
			if (alreadyProcessed)
			{
				throw new AdempiereException("already processed: " + this);
			}

			records.values().forEach(huTraceEventsService::createAndAddFor);
		}
	}
}
