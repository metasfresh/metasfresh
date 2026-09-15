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
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.api.ShipmentScheduleUpdateInvalidRequest;
import de.metas.inoutcandidate.api.ShipmentScheduleUpdateInvalidResult;
import de.metas.inoutcandidate.api.ShipmentSchedulesMDC;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IContextAware;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import java.util.Properties;

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
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

		final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
		final IWorkPackageQueue queueForEnqueuing = workPackageQueueFactory.getQueueForEnqueuing(Env.getCtx(), UpdateInvalidShipmentSchedulesWorkpackageProcessor.class);
		final int alreadyEnqueuedWPs = queueForEnqueuing.size();
		if (alreadyEnqueuedWPs > 1)
		{
			loggable.addLog("Not scheduling WP because there are {} processable workpackages, and we just need one to revalidate all flagged schedules: {}",
					alreadyEnqueuedWPs,
					UpdateInvalidShipmentSchedulesWorkpackageProcessor.class.getSimpleName());
			return;
		}

		SCHEDULER.schedule(request);
	}

	private static final UpdateInvalidShipmentSchedulesScheduler //
			SCHEDULER = new UpdateInvalidShipmentSchedulesScheduler(true /*createOneWorkpackagePerAsyncBatch*/);

	private static final String SYSCONFIG_MaxToProcess = "de.metas.inoutcandidate.async.UpdateInvalidShipmentSchedulesWorkpackageProcessor.MaxToProcess";
	private static final int DEFAULT_MaxToProcess = 500;

	// services
	private final transient IShipmentScheduleUpdater shipmentScheduleUpdater = Services.get(IShipmentScheduleUpdater.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final transient IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

	@Override
	public final boolean isRunInTransaction()
	{
		return false; // run out of transaction; we bound our own batch to an explicit, short-lived trx below instead
	}

	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workpackage, final String localTrxName_NOTUSED)
	{
		trxManager.assertThreadInheritedTrxNotExists();

		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

		final PInstanceId selectionId = Services.get(IADPInstanceDAO.class).createSelectionId();
		loggable.addLog("Using revalidation ID: {}", selectionId);

		try (final MDCCloseable ignored = ShipmentSchedulesMDC.putRevalidationId(selectionId))
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(workpackage);

			final ShipmentScheduleUpdateInvalidRequest request = ShipmentScheduleUpdateInvalidRequest.builder()
					.ctx(ctx)
					.selectionId(selectionId)
					.createMissingShipmentSchedules(false) // don't create missing schedules; for that we have CreateMissingShipmentSchedulesWorkpackageProcessor
					.maxToProcess(getMaxToProcess())
					.build();
			loggable.addLog("Starting revalidation for {}", request);

			// Recompute one bounded batch in its own transaction.
			// Why callInThreadInheritedTrx here: this processor runs out-of-transaction (isRunInTransaction()==false), so
			// there is NO ambient trx; callInThreadInheritedTrx then starts a new trx (and commits/closes it) -- giving us
			// ONE short, bounded transaction per batch instead of a single unbounded transaction for the whole backlog
			// (which OOMs on a large backlog). Mirrors CreateMissingShipmentSchedulesWorkpackageProcessor.
			final ShipmentScheduleUpdateInvalidResult result = trxManager.callInThreadInheritedTrx(() -> shipmentScheduleUpdater.updateShipmentSchedules(request));

			loggable.addLog("Updated {} shipment schedule entries for {}", result.getUpdatedCount(), request);

			if (result.isLimitReached())
			{
				enqueueFollowUpWorkpackage(ctx, workpackage);
			}

			return Result.SUCCESS;
		}
	}

	private QueryLimit getMaxToProcess()
	{
		return QueryLimit.ofInt(sysConfigBL.getIntValue(SYSCONFIG_MaxToProcess, DEFAULT_MaxToProcess));
	}

	/**
	 * Enqueues a fresh workpackage (carrying over the current one's async batch) to recompute the shipment
	 * schedules that remained after this run's bounded batch. Enqueues it directly instead of going through
	 * {@link #schedule(ShipmentSchedulesUpdateSchedulerRequest)}, because that method's dedup guard (skip if
	 * &gt;1 processable workpackage already queued) does not apply here: we KNOW there is more work left for
	 * this exact run and must not skip it.
	 */
	private void enqueueFollowUpWorkpackage(@NonNull final Properties ctx, @NonNull final I_C_Queue_WorkPackage workpackage)
	{
		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoIdOrNull(workpackage.getC_Async_Batch_ID());

		// Deliberately NOT bound to any trx (unlike _schedule, which does bindToTrxName via ShipmentSchedulesUpdateSchedulerRequest):
		// this run's batch already committed inside the callInThreadInheritedTrx above, so there is nothing left to
		// defer the follow-up's readiness to -- it must become ready-for-processing immediately.
		workPackageQueueFactory
				.getQueueForEnqueuing(ctx, UpdateInvalidShipmentSchedulesWorkpackageProcessor.class)
				.newWorkPackage()
				.setAsyncBatchId(asyncBatchId)
				.buildAndEnqueue();

		Loggables.addLog("Limit reached; enqueued a follow-up workpackage to revalidate the remaining flagged shipment schedules");
	}
}
