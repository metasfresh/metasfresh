/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2023 metas GmbH
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

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.api.IEnqueueResult;
import de.metas.async.eventbus.AsyncBatchEventBusService;
import de.metas.async.eventbus.AsyncBatchNotifyRequest;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.validator.C_Queue_WorkPackage;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class AsyncBatchService
{
	private static final Logger logger = LogManager.getLogger(AsyncBatchService.class);

	private final IAsyncBatchDAO asyncBatchDAO = Services.get(IAsyncBatchDAO.class);
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final AsyncBatchObserver asyncBatchObserver;
	private final AsyncBatchEventBusService asyncBatchEventBusService;

	public AsyncBatchService(
			@NonNull final AsyncBatchObserver asyncBatchObserver,
			@NonNull final AsyncBatchEventBusService asyncBatchEventBusService)
	{
		this.asyncBatchObserver = asyncBatchObserver;
		this.asyncBatchEventBusService = asyncBatchEventBusService;
	}

	public void checkProcessed(@NonNull final AsyncBatchId asyncBatchId, @Nullable final String trxName)
	{
		final I_C_Async_Batch asyncBatch = asyncBatchBL.getAsyncBatchById(asyncBatchId);

		final List<I_C_Queue_WorkPackage> workPackages = getWorkPackagesFromCurrentRun(asyncBatch, trxName);

		if (workPackages.isEmpty())
		{
			return;
		}

		final int workPackagesProcessedCount = (int)workPackages.stream()
				.filter(I_C_Queue_WorkPackage::isProcessed)
				.count();

		final int workPackagesWithErrorCount = (int)workPackages.stream()
				.filter(I_C_Queue_WorkPackage::isError)
				.count();

		Loggables.withLogger(logger, Level.INFO).addLog("*** processAsyncBatch for: asyncBatchID: " + asyncBatch.getC_Async_Batch_ID() +
																" allWPSize: " + workPackages.size() +
																" processedWPSize: " + workPackagesProcessedCount +
																" erroredWPSize: " + workPackagesWithErrorCount);

		final AsyncBatchNotifyRequest request = AsyncBatchNotifyRequest.builder()
				.clientId(Env.getClientId())
				.asyncBatchId(AsyncBatchId.toRepoId(asyncBatchId))
				.noOfProcessedWPs(workPackagesProcessedCount)
				.noOfEnqueuedWPs(workPackages.size())
				.noOfErrorWPs(workPackagesWithErrorCount)
				.build();

		asyncBatchEventBusService.postRequest(request);
	}

	/**
	 * Enqueues and waits for the workpackages to finish, successfully or exceptionally.
	 * It's mandatory for the given Supplier<> to enqueue workpackages previously assigned to the given async batch.
	 *
	 * @param supplier     Supplier<>
	 * @param asyncBatchId C_Async_Batch_ID
	 * @param <T>          model type
	 * @return model type of supplier
	 * @see C_Queue_WorkPackage#processBatchFromWP(de.metas.async.model.I_C_Queue_WorkPackage)
	 */
	public <T extends IEnqueueResult> T executeBatch(@NonNull final Supplier<T> supplier, @NonNull final AsyncBatchId asyncBatchId)
	{
		final T result;
		try
		{
			asyncBatchObserver.observeOn(asyncBatchId);

			result = trxManager.callInNewTrx(supplier::get); // let the supplier enqueue its workpackages

			if (result.getWorkpackageEnqueuedCount() > 0)
			{
			asyncBatchObserver.waitToBeProcessed(asyncBatchId);
			}
			else
			{
				Loggables.withLogger(logger, Level.INFO).addLog("*** executeBatch: C_Async_Batch_ID: {} no workpackages were enqeued; Not waiting for asyncBatchObserver!", asyncBatchId.getRepoId());
			}
		}
		finally
		{
			asyncBatchObserver.removeObserver(asyncBatchId);
		}

		return result;
	}

	@NonNull
	private List<I_C_Queue_WorkPackage> getWorkPackagesFromCurrentRun(@NonNull final I_C_Async_Batch asyncBatch, @Nullable final String trxName)
	{
		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoId(asyncBatch.getC_Async_Batch_ID());

		final Optional<Instant> startMonitoringFrom = asyncBatchObserver.getStartMonitoringTimestamp(asyncBatchId);

		if (!startMonitoringFrom.isPresent())
		{
			Loggables.withLogger(logger, Level.WARN).addLog("*** getWorkPackagesFromCurrentRun: C_Async_Batch_ID: {} not monitored! Return empty list!", asyncBatchId.getRepoId());
			return ImmutableList.of();
		}

		final List<I_C_Queue_WorkPackage> workPackages = asyncBatchDAO.retrieveWorkPackages(asyncBatch, trxName);

		Loggables.withLogger(logger, Level.INFO).addLog("*** getWorkPackagesFromCurrentRun: asyncBatchId: {}, startMonitoringFrom: {}, WPs BEFORE filter: {}!",
														asyncBatchId, startMonitoringFrom.get(), workPackages.size());

		final List<I_C_Queue_WorkPackage> filteredWPs = workPackages.stream()
				.filter(workPackage -> qualifiesForBatchProcessingStatus(workPackage, startMonitoringFrom.get()))
				.collect(ImmutableList.toImmutableList());

		Loggables.withLogger(logger, Level.INFO).addLog("*** getWorkPackagesFromCurrentRun: asyncBatchId: {}, startMonitoringFrom: {}, WPs AFTER filter: {}!",
														asyncBatchId, startMonitoringFrom.get(), filteredWPs.size());

		return filteredWPs;
	}

	/**
	 * {@code wasCreatedAfterMonitorStarted} = true, if the {@link I_C_Queue_WorkPackage} was created after the monitoring of its async batch has started.
	 * <br/>
	 * This is important as we want to avoid old "with-error" work packages failing a new async batch run.
	 * <br/>
	 * <br/>
	 * {@code wasProcessedAfterMonitorStarted} = true, if the {@link I_C_Queue_WorkPackage} was processed for the first time after the monitoring of its async batch has started.
	 * <br/>
	 * This is important as we want to consider work packages that were created in the past but only run now.
	 * <br/>
	 * <br/>
	 * {@code isPendingProcessingNoSkipping} = true, if the {@link I_C_Queue_WorkPackage} was never processed before and now it's ready for processing.
	 * <br/>
	 *
	 * @return true, if {@code wasCreatedAfterMonitorStarted || wasProcessedAfterMonitorStarted || isPendingProcessingNoSkipping}
	 */
	private boolean qualifiesForBatchProcessingStatus(@NonNull final I_C_Queue_WorkPackage workPackage, @NonNull final Instant startMonitoringFrom)
	{
		final boolean wasCreatedAfterMonitorStarted = workPackage.getCreated().toInstant().getEpochSecond() >= startMonitoringFrom.getEpochSecond();

		final boolean wasProcessedAfterMonitorStarted = workPackage.getUpdated().toInstant().getEpochSecond() >= startMonitoringFrom.getEpochSecond()
				&& (workPackage.isProcessed() || workPackage.isError())
				&& workPackage.getSkippedAt() == null;

		final boolean isPendingProcessingNoSkipping = !workPackage.isError()
				&& !workPackage.isProcessed()
				&& workPackage.isReadyForProcessing()
				&& workPackage.getSkippedAt() == null;

		return wasCreatedAfterMonitorStarted || wasProcessedAfterMonitorStarted || isPendingProcessingNoSkipping;
	}
}
