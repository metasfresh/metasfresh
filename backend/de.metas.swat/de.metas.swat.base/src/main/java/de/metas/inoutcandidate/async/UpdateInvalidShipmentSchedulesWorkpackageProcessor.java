package de.metas.inoutcandidate.async;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import ch.qos.logback.classic.Level;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.api.ShipmentScheduleUpdateInvalidRequest;
import de.metas.inoutcandidate.api.ShipmentSchedulesMDC;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

/**
 * Workpackage used to update all invalid {@link I_M_ShipmentSchedule}s.
 *
 * @author tsa
 */
public class UpdateInvalidShipmentSchedulesWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	private static final Logger logger = LogManager.getLogger(UpdateInvalidShipmentSchedulesWorkpackageProcessor.class);

	public static void schedule()
	{
		final IContextAware contextAwareWithThreadInherit = PlainContextAware.newWithThreadInheritedTrx();

		final ShipmentSchedulesUpdateSchedulerRequest request = ShipmentSchedulesUpdateSchedulerRequest.builder()
				.ctx(contextAwareWithThreadInherit.getCtx())
				.trxName(contextAwareWithThreadInherit.getTrxName())
				.build();

		_schedule(request);
	}

	public static void schedule(@NonNull final ShipmentSchedulesUpdateSchedulerRequest request)
	{
		_schedule(request);
	}

	private static void _schedule(@NonNull final ShipmentSchedulesUpdateSchedulerRequest request)
	{
		SCHEDULER.schedule(request);
	}

	private static final UpdateInvalidShipmentSchedulesScheduler //
			SCHEDULER = new UpdateInvalidShipmentSchedulesScheduler(true /*createOneWorkpackagePerAsyncBatch*/);

	// services
	private final transient IShipmentScheduleUpdater shipmentScheduleUpdater = Services.get(IShipmentScheduleUpdater.class);

	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workpackage, final String localTrxName_NOTUSED)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

		final PInstanceId selectionId = Services.get(IADPInstanceDAO.class).createSelectionId();
		loggable.addLog("Using revalidation ID: {}", selectionId);

		try (final MDCCloseable ignored = ShipmentSchedulesMDC.putRevalidationId(selectionId))
		{
			final ShipmentScheduleUpdateInvalidRequest request = ShipmentScheduleUpdateInvalidRequest.builder()
					.ctx(InterfaceWrapperHelper.getCtx(workpackage))
					.selectionId(selectionId)
					.createMissingShipmentSchedules(false) // don't create missing schedules; for that we have CreateMissingShipmentSchedulesWorkpackageProcessor
					.build();
			loggable.addLog("Starting revalidation for {}", request);

			final int updatedCount = shipmentScheduleUpdater.updateShipmentSchedules(request);

			loggable.addLog("Updated {} shipment schedule entries for {}", updatedCount, request);

			return Result.SUCCESS;
		}
	}
}
