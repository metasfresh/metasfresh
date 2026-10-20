/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.async.service;

import com.google.common.collect.ImmutableList;
import de.metas.async.AsyncBatchId;
import de.metas.async.Async_Constants;
import de.metas.async.eventbus.AsyncBatchEventBusService;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.event.IEventBusFactory;
import de.metas.event.log.EventLogUserService;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

class AsyncBatchServiceTest
{
	private AsyncBatchId asyncBatchId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final I_C_Async_Batch asyncBatchRecord = InterfaceWrapperHelper.newInstance(I_C_Async_Batch.class);
		InterfaceWrapperHelper.saveRecord(asyncBatchRecord);
		asyncBatchId = AsyncBatchId.ofRepoId(asyncBatchRecord.getC_Async_Batch_ID());
	}

	/**
	 * The async batch receives a foreign workpackage (e.g. the shipment schedule recompute) whose processing finishes
	 * while {@code executeBatch}'s own enqueue trx is not yet committed. Its notification reports "1 of 1 processed".
	 * Once our enqueue is done, that stale snapshot must not complete the batch while the DB already knows about our own,
	 * still unprocessed workpackage: {@code executeBatch} may return only after that workpackage was processed.
	 */
	@Test
	void executeBatch_staleAllProcessedNotificationBeforeOwnEnqueue_waitsForOwnWorkPackage() throws Exception
	{
		Services.get(ISysConfigBL.class).setValue(Async_Constants.SYS_Config_WaitTimeOutMS, 30_000, ClientId.SYSTEM, OrgId.ANY);

		final AsyncBatchObserver observer = new AsyncBatchObserver();
		// dev-note: the plain event enqueuer dispatches through the registered IEventBusFactory service, so subscribe there
		final AsyncBatchEventBusService eventBusService = new AsyncBatchEventBusService(Services.get(IEventBusFactory.class), new EventLogUserService());
		eventBusService.registerHandlers(Optional.of(ImmutableList.of(observer)));
		final AsyncBatchService asyncBatchService = new AsyncBatchService(observer, eventBusService);

		final AtomicReference<I_C_Queue_WorkPackage> ownWorkPackage = new AtomicReference<>();
		final AtomicReference<Thread> processorThread = new AtomicReference<>();
		final AtomicReference<Throwable> processorFailure = new AtomicReference<>();

		asyncBatchService.executeBatch(
				() -> {
					// the foreign workpackage of the same async batch is processed, and reports 1 of 1 processed
					createWorkPackage(true);
					asyncBatchService.checkProcessed(asyncBatchId, ITrx.TRXNAME_None);

					// now our own workpackage is enqueued
					ownWorkPackage.set(createWorkPackage(false));

					// a queue processor picks it up a bit later and reports the batch's progress, like C_Queue_WorkPackage.processBatchFromWP
					final Thread thread = new Thread(() -> {
						try
						{
							Thread.sleep(1000);
							ownWorkPackage.get().setProcessed(true);
							InterfaceWrapperHelper.saveRecord(ownWorkPackage.get());
							asyncBatchService.checkProcessed(asyncBatchId, ITrx.TRXNAME_None);
						}
						catch (final Throwable t)
						{
							processorFailure.set(t);
						}
					}, "queueProcessor");
					thread.setDaemon(true);
					processorThread.set(thread);
					thread.start();

					return () -> 1;
				},
				asyncBatchId);

		final boolean ownWorkPackageProcessedWhenExecuteBatchReturned = InterfaceWrapperHelper.load(ownWorkPackage.get().getC_Queue_WorkPackage_ID(), I_C_Queue_WorkPackage.class).isProcessed();

		processorThread.get().join(Duration.ofSeconds(30).toMillis());
		assertThat(processorFailure.get()).as("queue processor failure").isNull();

		assertThat(ownWorkPackageProcessedWhenExecuteBatchReturned)
				.as("executeBatch returned only after its own workpackage was processed (a stale 1-of-1 notification must not complete the batch)")
				.isTrue();
	}

	@NonNull
	private I_C_Queue_WorkPackage createWorkPackage(final boolean processed)
	{
		final I_C_Queue_WorkPackage workPackage = InterfaceWrapperHelper.newInstance(I_C_Queue_WorkPackage.class);
		workPackage.setC_Async_Batch_ID(asyncBatchId.getRepoId());
		workPackage.setIsReadyForProcessing(true);
		workPackage.setProcessed(processed);
		workPackage.setIsError(false);
		InterfaceWrapperHelper.saveRecord(workPackage);
		return workPackage;
	}
}
