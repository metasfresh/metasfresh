/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.shipmentschedule;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_Processor_Assign;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IQueueProcessorExecutorService;
import de.metas.async.processor.IQueueProcessorsExecutor;
import de.metas.async.processor.QueueProcessorId;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor;
import de.metas.inoutcandidate.async.UpdateInvalidShipmentSchedulesWorkpackageProcessor;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Runs the shipment schedule recompute pass deterministically, also while another business transaction is still open.
 * <p>
 * In production, the recompute pass ({@link UpdateInvalidShipmentSchedulesWorkpackageProcessor}) and the creation of missing
 * shipment schedules ({@link CreateMissingShipmentSchedulesWorkpackageProcessor}) are run by the always-on async queue
 * processors, whenever the queue planner picks up their workpackages. A race between such a pass and a concurrent, not yet
 * committed change depends on that timing, so a scenario driving the real async path would be flaky. Instead, this harness
 * pauses the queue processors that run the recompute pass, runs the creation and the recompute pass itself in the step thread
 * (like the async processor does), and can hold a change's transaction open until the pass is done.
 * <p>
 * Pausing removes the queue processors from the running executor; their workpackages stay queued and are processed once
 * the processors are resumed. Removal shuts down the processor's thread pool, which waits for a running workpackage
 * (up to 25 s) before interrupting it; to never get there, pausing first waits until the processors are idle.
 */
public class ShipmentScheduleRecomputeRaceHarness
{
	private static final String RECOMPUTE_PACKAGE_PROCESSOR_CLASSNAME_SUFFIX = "." + UpdateInvalidShipmentSchedulesWorkpackageProcessor.class.getSimpleName();
	private static final int TIMEOUT_SECONDS = 60;
	private static final long POLL_MILLIS = 100;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IQueueProcessorExecutorService queueProcessorExecutorService = Services.get(IQueueProcessorExecutorService.class);

	private final List<I_C_Queue_Processor> pausedQueueProcessors = new ArrayList<>();

	/**
	 * Removes every running queue processor that is assigned to the recompute pass's package processor from the async executor,
	 * after waiting until none of them is processing a workpackage. Their workpackages stay queued until {@link #resumeRecomputeQueueProcessors()}.
	 */
	public void pauseRecomputeQueueProcessors() throws InterruptedException
	{
		assertThat(pausedQueueProcessors).as("Recompute queue processors are already paused").isEmpty();

		final IQueueProcessorsExecutor executor = queueProcessorExecutorService.getExecutor();
		final ImmutableList<IQueueProcessor> runningProcessors = retrieveRecomputeQueueProcessorIds()
				.stream()
				.map(queueProcessorId -> executor.getQueueProcessor(QueueProcessorId.ofRepoId(queueProcessorId)))
				.filter(Objects::nonNull) // e.g. an inactive C_Queue_Processor, which the executor never started
				.collect(ImmutableList.toImmutableList());
		assertThat(runningProcessors)
				.as("Running queue processors assigned to %s", UpdateInvalidShipmentSchedulesWorkpackageProcessor.class.getSimpleName())
				.isNotEmpty();

		StepDefUtil.tryAndWait(TIMEOUT_SECONDS, POLL_MILLIS, () -> runningProcessors.stream().allMatch(IQueueProcessor::isAvailableToWork));

		for (final IQueueProcessor processor : runningProcessors)
		{
			final int queueProcessorId = processor.getQueueProcessorId().getRepoId();
			pausedQueueProcessors.add(InterfaceWrapperHelper.loadOutOfTrx(queueProcessorId, I_C_Queue_Processor.class));
			executor.removeQueueProcessor(queueProcessorId);
		}
	}

	/**
	 * Adds the queue processors removed by {@link #pauseRecomputeQueueProcessors()} back to the async executor; does nothing if none are paused.
	 */
	public void resumeRecomputeQueueProcessors()
	{
		if (pausedQueueProcessors.isEmpty())
		{
			return;
		}

		final IQueueProcessorsExecutor executor = queueProcessorExecutorService.getExecutor();
		pausedQueueProcessors.forEach(executor::addQueueProcessor);
		pausedQueueProcessors.clear();
	}

	/**
	 * Creates the missing shipment schedules, like a {@link CreateMissingShipmentSchedulesWorkpackageProcessor} workpackage run does.
	 */
	public void processPendingShipmentScheduleCreation()
	{
		final I_C_Queue_WorkPackage workPackage = InterfaceWrapperHelper.newInstanceOutOfTrx(I_C_Queue_WorkPackage.class);
		final IWorkpackageProcessor.Result result = new CreateMissingShipmentSchedulesWorkpackageProcessor().processWorkPackage(workPackage, ITrx.TRXNAME_None);
		assertThat(result).as("Outcome of creating the missing shipment schedules").isEqualTo(IWorkpackageProcessor.Result.SUCCESS);
	}

	/**
	 * Runs one recompute pass in the calling thread, like a {@link UpdateInvalidShipmentSchedulesWorkpackageProcessor} workpackage run does.
	 *
	 * @return the exception the pass ended with, or {@code null} if it succeeded
	 */
	@Nullable
	public RuntimeException runPendingRecomputePass()
	{
		assertRecomputeQueueProcessorsPaused();
		trxManager.assertThreadInheritedTrxNotExists();

		final I_C_Queue_WorkPackage workPackage = InterfaceWrapperHelper.newInstanceOutOfTrx(I_C_Queue_WorkPackage.class);
		try
		{
			final IWorkpackageProcessor.Result result = new UpdateInvalidShipmentSchedulesWorkpackageProcessor().processWorkPackage(workPackage, ITrx.TRXNAME_None);
			assertThat(result).as("Outcome of the recompute pass").isEqualTo(IWorkpackageProcessor.Result.SUCCESS);
			return null;
		}
		catch (final RuntimeException e)
		{
			return e;
		}
	}

	/**
	 * Runs {@code change} in its own transaction in another thread and keeps that transaction open while this thread runs
	 * {@code assertChangeInvisibleOutside} and then a recompute pass; commits the change afterwards.
	 * Fails if the change, the recompute pass or the assertion fails.
	 *
	 * @return the result of {@code change}
	 */
	public <T> T runInTransactionCommittedAfterRecompute(
			@NonNull final Supplier<T> change,
			@NonNull final Runnable assertChangeInvisibleOutside) throws InterruptedException
	{
		assertRecomputeQueueProcessorsPaused();

		final CountDownLatch changed = new CountDownLatch(1);
		final CountDownLatch release = new CountDownLatch(1);
		final AtomicReference<T> changeResult = new AtomicReference<>();
		final AtomicReference<Throwable> changeFailure = new AtomicReference<>();

		final Thread changeThread = new Thread(
				() -> {
					try
					{
						trxManager.callInThreadInheritedTrx(() -> {
							changeResult.set(change.get());
							changed.countDown();

							if (!release.await(TIMEOUT_SECONDS, TimeUnit.SECONDS))
							{
								// e.g. the recompute pass waits for a lock that this transaction holds
								throw new AdempiereException("The change's transaction was not released within " + TIMEOUT_SECONDS + "s");
							}
							return null;
						});
					}
					catch (final Throwable t)
					{
						changeFailure.set(t);
					}
					finally
					{
						changed.countDown();
					}
				},
				"uncommitted-change-during-shipment-schedule-recompute");
		changeThread.setDaemon(true);
		changeThread.start();

		final RuntimeException recomputeFailure;
		try
		{
			assertThat(changed.await(TIMEOUT_SECONDS, TimeUnit.SECONDS)).as("The change reached its end (uncommitted)").isTrue();
			rethrowIfFailed(changeFailure);

			assertChangeInvisibleOutside.run();
			recomputeFailure = runPendingRecomputePass();
		}
		finally
		{
			release.countDown();
		}

		changeThread.join(TimeUnit.SECONDS.toMillis(TIMEOUT_SECONDS));
		assertThat(changeThread.isAlive()).as("The change's thread is still running").isFalse();
		rethrowIfFailed(changeFailure);
		if (recomputeFailure != null)
		{
			throw recomputeFailure;
		}

		return changeResult.get();
	}

	private void assertRecomputeQueueProcessorsPaused()
	{
		assertThat(pausedQueueProcessors)
				.as("Paused recompute queue processors; without the pause, the async recompute pass runs concurrently")
				.isNotEmpty();
	}

	private static void rethrowIfFailed(@NonNull final AtomicReference<Throwable> failure)
	{
		final Throwable throwable = failure.get();
		if (throwable != null)
		{
			throw AdempiereException.wrapIfNeeded(throwable);
		}
	}

	private ImmutableSet<Integer> retrieveRecomputeQueueProcessorIds()
	{
		final ImmutableSet<Integer> recomputePackageProcessorIds = queryBL.createQueryBuilderOutOfTrx(I_C_Queue_PackageProcessor.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.filter(packageProcessor -> packageProcessor.getClassname() != null && packageProcessor.getClassname().endsWith(RECOMPUTE_PACKAGE_PROCESSOR_CLASSNAME_SUFFIX))
				.map(I_C_Queue_PackageProcessor::getC_Queue_PackageProcessor_ID)
				.collect(ImmutableSet.toImmutableSet());
		if (recomputePackageProcessorIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return queryBL.createQueryBuilderOutOfTrx(I_C_Queue_Processor_Assign.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Queue_Processor_Assign.COLUMNNAME_C_Queue_PackageProcessor_ID, recomputePackageProcessorIds)
				.create()
				.stream()
				.map(I_C_Queue_Processor_Assign::getC_Queue_Processor_ID)
				.collect(ImmutableSet.toImmutableSet());
	}
}
