package de.metas.async.processor.impl;

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

import de.metas.async.QueueProcessorTestBase;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.api.NOPWorkpackageLogsRepository;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.common.util.time.SystemTime;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The planner locks a workpackage ({@code LockedAt = now}) and then dispatches it to a queue processor on
 * another thread. If the processor has lost its last permit in between, {@code processLockedWorkPackage}
 * bails out early — and must release the lock, or the workpackage becomes invisible to the poller.
 * <p>
 * Concrete failure this prevents (measured 2026-08-05, cucumber
 * {@code automateOrderCandToOrderAndShipmentAndInvoice.feature}, 5 of 7 scenarios): the bail-out left
 * {@code LockedAt} set. {@code QueueDAO}'s polling query only reconsiders a locked workpackage after
 * {@code LockedAt < now() - interval '10 minutes'}, while a caller waiting on the workpackage's async batch
 * gives up after {@code de.metas.async.AsyncBatchObserver.WaitTimeOutMS} = 5 minutes. So the workpackage
 * could never be re-polled in time: it stayed
 * {@code processed=false error=false readyForProcessing=true skippedAt=null} and the REST call
 * {@code PUT api/v2/orders/sales/candidates/process} returned 400 after a ~300 s stall.
 */
public class AbstractQueueProcessor_UnlockOnNotProcessed_Test extends QueueProcessorTestBase
{
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

	@Test
	public void givenProcessorHasNoPermitsLeft_whenProcessLockedWorkPackage_thenLockedAtIsCleared()
	{
		// given
		final I_C_Queue_Processor processorDef = helper.createQueueProcessor("test", 1, 1000);
		helper.assignPackageProcessor(processorDef, StaticMockedWorkpackageProcessor.class);

		final IWorkPackageQueue queue = workPackageQueueFactory
				.getQueueForEnqueuing(ctx, StaticMockedWorkpackageProcessor.class);

		// dev-note: isAvailableToWork() is overridden rather than the semaphore being drained for real.
		// Draining it would need a second task parked on the pool's only thread, i.e. a timing-dependent
		// (flaky) setup. What is under test here is the *bail-out path's* failure to unlock, not the
		// permit accounting itself - so pinning the precondition deterministically is the point.
		final ThreadPoolQueueProcessor queueProcessor = new ThreadPoolQueueProcessor(processorDef, queue, NOPWorkpackageLogsRepository.instance)
		{
			@Override
			public boolean isAvailableToWork()
			{
				return false;
			}
		};

		final I_C_Queue_WorkPackage workPackage = helper.createAndEnqueueWorkpackages(queue, 1, false).get(0);
		workPackage.setLockedAt(SystemTime.asTimestamp());
		InterfaceWrapperHelper.save(workPackage);
		assertThat(workPackage.getLockedAt()).as("guard: the workpackage must start out locked").isNotNull();

		// when
		final boolean processed = queueProcessor.processLockedWorkPackage(workPackage);

		// then
		assertThat(processed).as("a processor with no permits left cannot process the workpackage").isFalse();

		InterfaceWrapperHelper.refresh(workPackage);
		assertThat(workPackage.getLockedAt())
				.as("LockedAt must be cleared so the poller can pick the workpackage up again")
				.isNull();
		assertThat(workPackage.isProcessed()).as("the workpackage was never processed").isFalse();
		assertThat(workPackage.isError()).as("bailing out is not an error of the workpackage").isFalse();
	}
}
