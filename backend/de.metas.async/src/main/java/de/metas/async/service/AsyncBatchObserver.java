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
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.lock.spi.ExistingLockInfo;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
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

	private final ConcurrentHashMap<AsyncBatchId, BatchProgress> asyncBatch2Completion = new ConcurrentHashMap<>();

	@NonNull
	private static LockOwner toLockOwner(@NonNull final AsyncBatchId asyncBatchId)
	{
		final String lockOwnerName = AsyncBatchObserver.class.getName() + "_" + I_C_Async_Batch.COLUMNNAME_C_Async_Batch_ID + "=" + asyncBatchId.getRepoId();
		return LockOwner.forOwnerName(lockOwnerName);
	}

	@Override
	public void handleRequest(@NonNull final AsyncBatchNotifyRequest notification)
	{
		final AsyncBatchId asyncBatchId = notification.getAsyncBatchId();
		Loggables.withLogger(logger, Level.DEBUG).addLog("AsyncBatch identified by: {} notified with status: {}", asyncBatchId, notification);

		final BatchProgress asyncBatchProgress = asyncBatch2Completion.get(asyncBatchId);
		if (asyncBatchProgress == null)
		{
			Loggables.withLogger(logger, Level.INFO).addLog("notifyBatchFor - No observer registered to notify for asyncBatchId: {}", asyncBatchId.getRepoId());
			return;
		}

		asyncBatchProgress.updateWorkPackagesProgress(notification);
	}

	public IAutoCloseable observeOn(@NonNull final AsyncBatchId id)
	{
		Loggables.withLogger(logger, Level.INFO).addLog("Observer registered for asyncBatchId: " + id.getRepoId());

		final Duration timeout = getWaitTimeOut();

		//dev-note: make sure to wait for any work already enqueued with this async batch to finalize
		final BatchProgress existingBatchProgress = asyncBatch2Completion.get(id);
		if (existingBatchProgress != null)
		{
			existingBatchProgress.waitToComplete(timeout);
		}

		//dev-note: acquire an owner related lock to make sure there is just one AsyncBatchObserver that's registering a certain async batch at a time
		final ILock lock = lockBatch(id, timeout);
		asyncBatch2Completion.put(id, new BatchProgress(asyncBatchDAO, lock, id));

		return () -> removeObserver(id);
	}

	/**
	 * Waits max. the timeout specified in {@code AD_SysConfig de.metas.async.AsyncBatchObserver.WaitTimeOutMS}
	 * for the given AsyncBatchId to be completed.
	 */
	public void markEnqueueingIsDoneAndWaitToComplete(@NonNull final AsyncBatchId id)
	{
		final BatchProgress asyncBatchProgress = asyncBatch2Completion.get(id);
		if (asyncBatchProgress == null)
		{
			Loggables.withLogger(logger, Level.INFO).addLog("No observer registered to be processed for asyncBatchId: {}", id);
			return;
		}

		try
		{
			asyncBatchProgress.markEnqueueingIsDone();
			asyncBatchProgress.waitToComplete(getWaitTimeOut());
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("AsyncBatchId", id);
		}
	}

	public void waitToComplete(@NonNull final AsyncBatchId id)
	{
		final BatchProgress asyncBatchProgress = asyncBatch2Completion.get(id);
		if (asyncBatchProgress != null)
		{
			asyncBatchProgress.waitToComplete(getWaitTimeOut());
		}
	}

	private Duration getWaitTimeOut()
	{
		final int durationMillis = sysConfigBL.getIntValue(SYS_Config_WaitTimeOutMS, SYS_Config_WaitTimeOutMS_DEFAULT_VALUE);
		return Duration.ofMillis(durationMillis > 0 ? durationMillis : SYS_Config_WaitTimeOutMS_DEFAULT_VALUE);
	}

	public boolean isAsyncBatchObserved(@NonNull final AsyncBatchId id)
	{
		return asyncBatch2Completion.get(id) != null;
	}

	@NonNull
	public Optional<Instant> getStartMonitoringTimestamp(@NonNull final AsyncBatchId asyncBatchId)
	{
		final ExistingLockInfo existingLockInfo = lockManager.getLockInfo(toTableRecordReference(asyncBatchId), toLockOwner(asyncBatchId));
		return Optional.ofNullable(existingLockInfo).map(ExistingLockInfo::getCreated);
	}

	@NonNull
	private static TableRecordReference toTableRecordReference(final @NonNull AsyncBatchId asyncBatchId) {return TableRecordReference.of(I_C_Async_Batch.Table_Name, asyncBatchId);}

	private void removeObserver(@NonNull final AsyncBatchId id)
	{
		final BatchProgress batchProgress = asyncBatch2Completion.remove(id);
		if (batchProgress == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No observer registered that can be removed for asyncBatchId: {}", id);
			return;
		}

		batchProgress.unlock();
		batchProgress.forceCompletionIfNotAlreadyCompleted();

		Loggables.withLogger(logger, Level.INFO).addLog("Observer removed for asyncBatchId: {}", id);
	}

	@NonNull
	private ILock lockBatch(@NonNull final AsyncBatchId asyncBatchId, @NonNull final Duration timeout)
	{
		final Instant startTime = Instant.now();
		final LockOwner lockOwner = toLockOwner(asyncBatchId);
		final TableRecordReference asyncBatchRef = toTableRecordReference(asyncBatchId);
		final Supplier<Boolean> timeoutReached = () -> startTime.plusMillis(timeout.toMillis()).isBefore(Instant.now());

		while (!timeoutReached.get())
		{
			final ILock lock = lockManager.lock()
					.setOwner(lockOwner)
					.setAllowAdditionalLocks(ILockCommand.AllowAdditionalLocks.FOR_DIFFERENT_OWNERS)
					.setFailIfAlreadyLocked(false)
					.addRecord(asyncBatchRef)
					.acquire();

			if (lock.getCountLocked() > 0)
			{
				return lock;
			}

			try
			{
				//dev-note: sleep one sec and retry
				//noinspection BusyWait
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

	private static class BatchProgress
	{
		private static final Logger logger = LogManager.getLogger(BatchProgress.class);
		private final IAsyncBatchDAO asyncBatchDAO;

		@NonNull private final ILock lock;
		@NonNull private final AsyncBatchId batchId;

		@NonNull private final CompletableFuture<Void> completableFuture;
		private volatile boolean isEnqueueingDone;
		private volatile WorkPackagesProgress wpProgress = null;

		private BatchProgress(
				@NonNull final IAsyncBatchDAO asyncBatchDAO,
				@NonNull final ILock lock,
				@NonNull final AsyncBatchId batchId)
		{
			this.asyncBatchDAO = asyncBatchDAO;
			this.lock = lock;
			this.batchId = batchId;

			this.completableFuture = new CompletableFuture<>();
		}

		public void waitToComplete(@NonNull final Duration timeout)
		{
			try
			{
				completableFuture.get(timeout.toNanos(), TimeUnit.NANOSECONDS);
			}
			catch (final TimeoutException timeoutException)
			{
				if (!asyncBatchDAO.hasWorkPackages(batchId))
				{
					Loggables.withLogger(logger, Level.INFO).addLog("No workpackages retrieved for asyncBatchId: {}", batchId);
					return;
				}

				throw AdempiereException.wrapIfNeeded(timeoutException);
			}
			catch (final Exception e)
			{
				throw AdempiereException.wrapIfNeeded(e)
						.appendParametersToMessage()
						.setParameter("AsyncBatchId", batchId);
			}
		}

		private void markEnqueueingIsDone()
		{
			isEnqueueingDone = true;
			checkIfBatchIsDone();
		}

		public void updateWorkPackagesProgress(@NonNull final AsyncBatchNotifyRequest notifyRequest)
		{
			this.wpProgress = WorkPackagesProgress.of(notifyRequest);
			checkIfBatchIsDone();
		}

		public void unlock()
		{
			lock.unlockAll();
		}

		private void forceCompletionIfNotAlreadyCompleted()
		{
			if (checkIfBatchIsDone())
			{
				return;
			}

			this.completableFuture.completeExceptionally(new AdempiereException("Forced exceptionally complete!")
					.appendParametersToMessage()
					.setParameter("AsyncBatchId", batchId.getRepoId()));
		}

		private synchronized boolean checkIfBatchIsDone()
		{
			final WorkPackagesProgress wpProgress = this.wpProgress;
			final boolean isEnqueueingDone = this.isEnqueueingDone;

			if (wpProgress == null || !isEnqueueingDone)
			{
				return false;
			}

			if (wpProgress.isProcessedSuccessfully())
			{
				Loggables.withLogger(logger, Level.INFO).addLog("AsyncBatchId={} completed successfully. ", batchId.getRepoId());
				this.completableFuture.complete(null);
				return true;
			}
			else if (wpProgress.isProcessedWithError())
			{
				this.completableFuture.completeExceptionally(new AdempiereException("WorkPackage completed with an exception")
						.appendParametersToMessage()
						.setParameter("AsyncBatchId", batchId.getRepoId()));
				return true;
			}

			return false;
		}

		private static class WorkPackagesProgress
		{
			@NonNull Integer noOfEnqueuedWPs;
			@NonNull Integer noOfProcessedWPs;
			@NonNull Integer noOfErrorWPs;

			@Builder
			private WorkPackagesProgress(
					@NonNull final Integer noOfEnqueuedWPs,
					@Nullable final Integer noOfProcessedWPs,
					@Nullable final Integer noOfErrorWPs)
			{
				this.noOfEnqueuedWPs = noOfEnqueuedWPs;
				this.noOfProcessedWPs = CoalesceUtil.coalesceNotNull(noOfProcessedWPs, 0);
				this.noOfErrorWPs = CoalesceUtil.coalesceNotNull(noOfErrorWPs, 0);

				Check.assumeGreaterThanZero(noOfEnqueuedWPs, "noOfEnqueuedWPs");
				Check.assumeGreaterOrEqualToZero(this.noOfProcessedWPs, this.noOfErrorWPs);
			}

			@NonNull
			private static WorkPackagesProgress of(@NonNull final AsyncBatchNotifyRequest request)
			{
				return builder()
						.noOfProcessedWPs(request.getNoOfProcessedWPs())
						.noOfEnqueuedWPs(request.getNoOfEnqueuedWPs())
						.noOfErrorWPs(request.getNoOfErrorWPs())
						.build();
			}

			public boolean isProcessedSuccessfully()
			{
				return noOfErrorWPs == 0 && noOfProcessedWPs >= noOfEnqueuedWPs;
			}

			public boolean isProcessedWithError()
			{
				return noOfErrorWPs > 0 && (noOfProcessedWPs + noOfErrorWPs >= noOfEnqueuedWPs);
			}
		}
	}
}

