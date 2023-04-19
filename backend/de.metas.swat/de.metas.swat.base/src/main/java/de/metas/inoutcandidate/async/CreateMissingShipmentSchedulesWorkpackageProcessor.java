/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate.async;

import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IEnqueueResult;
import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;

/**
 * Workpackage used to create missing shipment schedules.
 *
 * @author tsa
 */
public class CreateMissingShipmentSchedulesWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	private static final Logger logger = LogManager.getLogger(CreateMissingShipmentSchedulesWorkpackageProcessor.class);

	private static final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private static final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	private static final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private static final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);

	public static void scheduleIfNotPostponed(final IContextAware ctxAware)
	{
		final AsyncBatchId asyncBatchId = null;
		_scheduleIfNotPostponed(ctxAware, asyncBatchId);
	}

	public static IEnqueueResult scheduleIfNotPostponed(@NonNull final Object model)
	{
		final AsyncBatchId asyncBatchId = asyncBatchBL
				.getAsyncBatchId(model)
				.orElse(null);

		final boolean scheduled = _scheduleIfNotPostponed(InterfaceWrapperHelper.getContextAware(model), asyncBatchId);
		final int workpackageCount = scheduled ? 1 : 0;

		return () -> workpackageCount;
	}

	/**
	 * Schedules a new "create missing shipment schedules" run, <b>unless</b> the processor is disabled or all scheds would be created later.<br>
	 * See {@link IShipmentScheduleBL#allMissingSchedsWillBeCreatedLater()}.
	 *
	 * @param ctxAware if it has a not-null trxName, then the workpackage will be marked as ready for processing when given transaction is committed.
	 */
	private static boolean _scheduleIfNotPostponed(final IContextAware ctxAware, @Nullable final AsyncBatchId asyncBatchId)
	{
		if (shipmentScheduleBL.allMissingSchedsWillBeCreatedLater())
		{
			logger.debug("Not scheduling WP because IShipmentScheduleBL.allMissingSchedsWillBeCreatedLater() returned true: {}", CreateMissingShipmentSchedulesWorkpackageProcessor.class.getSimpleName());
			return false;
		}

		// don't try to enqueue it if is not active
		if (!queueDAO.isWorkpackageProcessorEnabled(CreateMissingShipmentSchedulesWorkpackageProcessor.class))
		{
			logger.debug("Not scheduling WP because this workpackage processor is disabled: {}", CreateMissingShipmentSchedulesWorkpackageProcessor.class.getSimpleName());
			return false;
		}

		final Properties ctx = ctxAware.getCtx();

		workPackageQueueFactory.getQueueForEnqueuing(ctx, CreateMissingShipmentSchedulesWorkpackageProcessor.class)
				.newWorkPackage()
				.setC_Async_Batch_ID(asyncBatchId)
				.bindToTrxName(ctxAware.getTrxName())
				.buildAndEnqueue();
		return true;
	}

	// services
	private final transient IShipmentScheduleHandlerBL inOutCandHandlerBL = Services.get(IShipmentScheduleHandlerBL.class);

	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workpackage, final String localTrxName_NOTUSED)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(workpackage);

		final Set<ShipmentScheduleId> shipmentScheduleIds = inOutCandHandlerBL.createMissingCandidates(ctx);

		// After shipment schedules where created, invalidate them because we want to make sure they are up2date.
		final IShipmentScheduleInvalidateBL invalidSchedulesService = Services.get(IShipmentScheduleInvalidateBL.class);
		final IShipmentSchedulePA shipmentScheduleDAO = Services.get(IShipmentSchedulePA.class);

		final Collection<I_M_ShipmentSchedule> scheduleRecords = shipmentScheduleDAO.getByIds(shipmentScheduleIds).values();
		for (final I_M_ShipmentSchedule scheduleRecord : scheduleRecords)
		{
			invalidSchedulesService.notifySegmentChangedForShipmentScheduleInclSched(scheduleRecord);
		}

		Loggables.addLog("Created " + shipmentScheduleIds.size() + " candidates");
		return Result.SUCCESS;
	}

}
