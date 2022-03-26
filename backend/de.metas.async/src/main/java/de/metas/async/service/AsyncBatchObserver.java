/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2021 metas GmbH
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
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.eventbus.AsyncBatchNotifyRequest;
import de.metas.async.eventbus.AsyncBatchNotifyRequestHandler;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import static de.metas.async.Async_Constants.SYS_Config_WaitTimeOutMS;
import static de.metas.async.Async_Constants.SYS_Config_WaitTimeOutMS_DEFAULT_VALUE;

@Service
public class AsyncBatchObserver implements AsyncBatchNotifyRequestHandler
{
	private static final Logger logger = LogManager.getLogger(AsyncBatchObserver.class);
	private final IAsyncBatchDAO asyncBatchDAO = Services.get(IAsyncBatchDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final ILockManager lockManager = Services.get(ILockManager.class);

	private final Map<AsyncBatchId, BatchProgress> asyncBatch2Completion = new ConcurrentHashMap<>();

	@Override
	public void handleRequest(@NonNull final AsyncBatchNotifyRequest request)
	{
		Loggables.withLogger(logger, Level.INFO).addLog("Batch notified as finished; AsyncBatchId: {}", request.getAsyncBatchId());

		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoId(request.getAsyncBatchId());

		this.notifyBatchProcessedFor(asyncBatchId, request.getSuccess());
	}

	public void observeOn(@NonNull final AsyncBatchId id)
	{
		Loggables.withLogger(logger, Level.INFO).addLog("Observer registered for asyncBatchId: " + id.getRepoId());

		//dev-note: make sure to wait for any work already enqueued with this async batch to finalize
		Optional.ofNullable(asyncBatch2Completion.get(id))
				.ifPresent(batchProgress -> {
					try
					{
						batchProgress.getCompletableFuture().get();
					}
					catch (final Throwable t)
					{
						throw AdempiereException.wrapIfNeeded(t)
								.appendParametersToMessage()
								.setParameter("AsyncBatchId", id);
					}
				});

		final int timeoutMS = sysConfigBL.getIntValue(SYS_Config_WaitTimeOutMS, SYS_Config_WaitTimeOutMS_DEFAULT_VALUE);

		//dev-note:acquire an owner related lock to make sure there is just one AsyncBatchObserver that's registering a certain async batch at a time
		final ILock lock = lockBatch(id, Duration.ofMillis(timeoutMS));

		asyncBatch2Completion.put(id, new BatchProgress(Instant.now(), lock));
	}

	/**
	 * Waits max. the timeout specified in {@code AD_SysConfig de.metas.async.AsyncBatchObserver.WaitTimeOutMS}
	 * for the given AsyncBatchId to be completed.
	 */
	public void waitToBeProcessed(@NonNull final AsyncBatchId id)
	{
		if (asyncBatch2Completion.get(id) == null)
		{
			Loggables.withLogger(logger, Level.INFO).addLog("No observer registered to be processed for asyncBatchId: {}", id.getRepoId());
			return;
		}

		try
		{
			final CompletableFuture<Void> completableFuture = asyncBatch2Completion.get(id).getCompletableFuture();

			final int timeoutMS = sysConfigBL.getIntValue(SYS_Config_WaitTimeOutMS, SYS_Config_WaitTimeOutMS_DEFAULT_VALUE);

			completableFuture.get(timeoutMS, TimeUnit.MILLISECONDS);
		}
		catch (final TimeoutException timeoutException)
		{
			final I_C_Async_Batch asyncBatch = asyncBatchDAO.retrieveAsyncBatchRecordOutOfTrx(id);

			final List<I_C_Queue_WorkPackage> workPackages = asyncBatchDAO.retrieveWorkPackages(asyncBatch, ITrx.TRXNAME_None);

			if (workPackages.isEmpty())
			{
				Loggables.withLogger(logger, Level.INFO).addLog("No workpackages retrieved for asyncBatchId: {}", id.getRepoId());
				return;
			}

			throw AdempiereException.wrapIfNeeded(timeoutException);
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("AsyncBatchId", id);
		}
		finally
		{
			removeObserver(id);
		}
	}

	public boolean isAsyncBatchObserved(@NonNull final AsyncBatchId id)
	{
		return asyncBatch2Completion.get(id) != null;
	}

	@NonNull
	public Optional<Instant> getStartMonitoringTimestamp(@NonNull final AsyncBatchId asyncBatchId)
	{
		return Optional.ofNullable(asyncBatch2Completion.get(asyncBatchId))
				.map(BatchProgress::getEnqueuedAt);
	}

	private void removeObserver(@NonNull final AsyncBatchId id)
	{
		if (asyncBatch2Completion.get(id) == null)
		{
			Loggables.withLogger(logger, Level.WARN).addLog("No observer registered that can be removed for asyncBatchId: {}", id.getRepoId());
			return;
		}

		final BatchProgress batchProgress = asyncBatch2Completion.remove(id);

		batchProgress.getLock().unlockAll();

		Loggables.withLogger(logger, Level.INFO).addLog("Observer removed for asyncBatchId: {}", id.getRepoId());
	}

	private void notifyBatchProcessedFor(@NonNull final AsyncBatchId id, final boolean successful)
	{
		if (asyncBatch2Completion.get(id) == null)
		{
			Loggables.withLogger(logger, Level.INFO).addLog("No observer registered to notify for asyncBatchId: {}" , id.getRepoId());
			return;
		}

		if (successful)
		{
			Loggables.withLogger(logger, Level.INFO).addLog("AsyncBatchId={} completed successfully. " , id.getRepoId());
			asyncBatch2Completion.get(id).getCompletableFuture().complete(null);
		}
		else
		{
			asyncBatch2Completion.get(id).getCompletableFuture().completeExceptionally(new AdempiereException("A Workpackage completed with an exception")
																		.appendParametersToMessage()
																		.setParameter("AsyncBatchId" , id.getRepoId()));
		}
	}

	@NonNull
	public ILock lockBatch(@NonNull final AsyncBatchId asyncBatchId, @NonNull final Duration timeout)
	{
		final Instant startTime = Instant.now();

		final I_C_Async_Batch asyncBatch = asyncBatchDAO.retrieveAsyncBatchRecordOutOfTrx(asyncBatchId);

		final String ownerName = AsyncBatchObserver.class.getName() + "_" + I_C_Async_Batch.COLUMNNAME_C_Async_Batch_ID + "=" + asyncBatch.getC_Async_Batch_ID();
		
		final Supplier<Boolean> timeoutReached = () -> startTime.plusMillis(timeout.toMillis()).isBefore(Instant.now());
		
		while (!timeoutReached.get())
		{
			final ILock lock = lockManager.lock()
					.setOwner(LockOwner.forOwnerName(ownerName))
					.setAllowAdditionalLocks(ILockCommand.AllowAdditionalLocks.FOR_DIFFERENT_OWNERS)
					.setFailIfAlreadyLocked(false)
					.addRecordByModel(asyncBatch)
					.acquire();

			if (lock.getCountLocked() > 0)
			{
				return lock;
			}

			try
			{
				//dev-note: sleep one sec and retry
				Thread.sleep(1000);
			}
			catch (final InterruptedException e)
			{
				throw AdempiereException.wrapIfNeeded(e);
			}
		}

		throw new AdempiereException("Couldn't acquire lock for C_Async_Batch!")
				.appendParametersToMessage()
				.setParameter("C_Async_Batch_ID", asyncBatchId);
	}

	@Value
	private static class BatchProgress
	{
		public BatchProgress(@NonNull final Instant enqueuedAt, @NonNull final ILock lock)
		{
			this.completableFuture = new CompletableFuture<>();
			this.enqueuedAt = enqueuedAt;
			this.lock = lock;
		}

		@NonNull
		CompletableFuture<Void> completableFuture;

		@NonNull
		Instant enqueuedAt;

		@NonNull
		ILock lock;
	}
}
