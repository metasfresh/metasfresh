/*
 * #%L
 * de.metas.async
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

package de.metas.async.processor.impl.planner;

import com.google.common.collect.ImmutableList;
import de.metas.async.api.WorkPackageLockHelper;
import de.metas.async.api.impl.WorkPackageQueue;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.async.processor.QueueProcessorId;
import de.metas.async.processor.impl.AbstractQueueProcessor;
import de.metas.lock.api.ILockManager;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.IQuery;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public abstract class QueueProcessorPlanner implements Runnable
{
	private static final Logger logger = LogManager.getLogger(QueueProcessorPlanner.class);

	private final static String SYSCONFIG_POLLINTERVAL_MILLIS = "de.metas.async.PollIntervallMillis";
	private final static int SYSCONFIG_POLLINTERVAL_DEFAULT_MS = 1000;

	private final ILockManager lockManager = Services.get(ILockManager.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private final ReentrantLock mainLock = new ReentrantLock();
	protected final ConcurrentHashMap<QueueProcessorId, IQueueProcessor> queueProcessors;
	protected final AtomicBoolean isRunning;

	public QueueProcessorPlanner()
	{
		this.queueProcessors = new ConcurrentHashMap<>();
		this.isRunning = new AtomicBoolean(false);
	}

	@Override
	public void run()
	{
		if (!isRunning.get())
		{
			throw new AdempiereException("Planner is not running! Hint: QueueProcessorPlanner should be started via start() method!");
		}

		while (isRunning.get())
		{
			try
			{
				final boolean successfulRun = runOnce();

				if (!successfulRun && isStopOnFailedRun())
				{
					logger.warn("*** QueueProcessorPlanner.run() -> last run was not successful & planner = {} has stopOnFailedRun policy; Shutting down...", this.getClass().getName());
					shutdown();
				}
				else
				{
					// note: we always get the new value, because things might have changed since this method started
					final int pollIntervalMs = getPollIntervalMillis();

					Thread.sleep(pollIntervalMs);
				}
			}
			catch (final InterruptedException e)
			{
				logger.error("Got interrupt request.", e);
				return;
			}
			catch (final Throwable throwable)
			{
				logger.warn("*** QueueProcessorPlanner.run() caught an unexpected error!", throwable);

				if (isStopOnFailedRun())
				{
					logger.error("*** QueueProcessorPlanner.run() caught an unexpected error!"
										 + " Planner = " + this.getClass().getName() + " has stopOnFailedRun policy; Shutting down...", throwable);
					shutdown();

					throw AdempiereException.wrapIfNeeded(throwable);
				}
			}
		}
	}

	@NonNull
	public List<IQueueProcessor> getRegisteredQueueProcessors()
	{
		return ImmutableList.copyOf(queueProcessors.values());
	}

	public void addQueueProcessor(@NonNull final IQueueProcessor queueProcessor)
	{
		validateQueueProcessorAssignment(queueProcessor);

		queueProcessors.put(queueProcessor.getQueueProcessorId(), queueProcessor);
	}

	public void removeQueueProcessor(@NonNull final QueueProcessorId queueProcessorId)
	{
		getQueueProcessor(queueProcessorId)
				.ifPresent(queueProcessor -> {
					queueProcessor.shutdownExecutor();
					queueProcessors.remove(queueProcessor.getQueueProcessorId());
				});
	}

	@NonNull
	public Optional<IQueueProcessor> getQueueProcessor(@NonNull final QueueProcessorId queueProcessorId)
	{
		return Optional.ofNullable(queueProcessors.get(queueProcessorId));
	}

	public void start()
	{
		mainLock.lock();
		try
		{
			if (isRunning.get())
			{
				logger.debug("QueueProcessorPlanner.start() - already running! return...");
				return;
			}

			this.startPlanner();
		}
		finally
		{
			mainLock.unlock();
		}
	}

	public void shutdown()
	{
		mainLock.lock();
		try
		{
			if (!queueProcessors.isEmpty())
			{
				queueProcessors
						.values()
						.stream()
						.map(IQueueProcessor::getQueueProcessorId)
						.forEach(this::removeQueueProcessor);
			}

			this.shutdownPlanner();
		}
		finally
		{
			mainLock.unlock();
		}
	}

	private boolean runOnce()
	{
		final List<IQueueProcessor> availableProcessors = getQueueProcessorsAvailableToWork();

		if (availableProcessors.isEmpty())
		{
			logger.debug("run0 - all processors are busy! Skipping...");
			return false;
		}

		final Properties workPackageCtx = Env.newTemporaryCtx();

		final List<I_C_Queue_WorkPackage> workPackages = pollAndLockWorkPackages(workPackageCtx, availableProcessors);
		if (workPackages.isEmpty())
		{
			logger.debug("pollAndLockWorkpackages - returned no workPackage to be processed for availableProcessorIds={}", availableProcessors);
			return false;
		}

		return handleWorkPackages(workPackageCtx, workPackages);
	}

	private boolean handleWorkPackages(@NonNull final Properties ctx, @NonNull final List<I_C_Queue_WorkPackage> workPackages)
	{
		if (workPackages.isEmpty())
		{
			return false;
		}

		boolean handledAllWorkPackages = true;

		for (final I_C_Queue_WorkPackage workPackage : workPackages)
		{
			if (!isValid(workPackage))
			{
				WorkPackageLockHelper.unlockNoFail(workPackage);
				handledAllWorkPackages = false;
				continue;
			}

			final QueuePackageProcessorId packageProcessorId = QueuePackageProcessorId.ofRepoId(workPackage.getC_Queue_PackageProcessor_ID());
			final IQueueProcessor queueProcessor = getAvailableQueueProcessorForWorkPackage(packageProcessorId).orElse(null);

			if (queueProcessor == null)
			{
				logger.warn("*** processWorkPackages: No available QueueProcessor found for workPackageId: {}, c_queue_workpackage.c_queue_packageprocessor_id: {}",
							workPackage.getC_Queue_WorkPackage_ID(), workPackage.getC_Queue_PackageProcessor_ID());

				WorkPackageLockHelper.unlockNoFail(workPackage);
				handledAllWorkPackages = false;
				continue;
			}

			setupNewCtxForWorkPackage(workPackage, ctx);

			final boolean successfullyHandled = handleWorkPackageProcessing(queueProcessor, workPackage);

			handledAllWorkPackages = handledAllWorkPackages && successfullyHandled;
		}

		return handledAllWorkPackages;
	}

	@NonNull
	private List<I_C_Queue_WorkPackage> pollAndLockWorkPackages(@NonNull final Properties ctx, @NonNull final List<IQueueProcessor> queueProcessors)
	{
		final IQuery<I_C_Queue_WorkPackage> queueProcessorWPQueriesAggregator = queryBL.createQueryBuilder(I_C_Queue_WorkPackage.class)
				//dev-note: workaround to be able to union multiple queries without applying 'limit' and 'order by' to the end result
				.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_WorkPackage_ID, -1)
				.create();

		final QueryLimit numberOfWorkPackagesPerProcessor = QueryLimit.ONE;

		final List<IQuery<I_C_Queue_WorkPackage>> queueProcessorSpecificQueries = queueProcessors
				.stream()
				.map(IQueueProcessor::getQueue)
				.map(queue -> queue.createQuery(ctx, numberOfWorkPackagesPerProcessor))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(lockManager::addNotLockedClause)
				.collect(ImmutableList.toImmutableList());

		if (queueProcessorSpecificQueries.isEmpty())
		{
			return ImmutableList.of();
		}

		queueProcessorWPQueriesAggregator.addUnions(queueProcessorSpecificQueries, true);

		return lockManager.retrieveAndLockMultipleRecords(queueProcessorWPQueriesAggregator, I_C_Queue_WorkPackage.class);
	}

	@NonNull
	private Optional<IQueueProcessor> getAvailableQueueProcessorForWorkPackage(@NonNull final QueuePackageProcessorId packageProcessorId)
	{
		return queueProcessors
				.values()
				.stream()
				.filter(processor -> processor.getAssignedPackageProcessorIds().contains(packageProcessorId))
				.filter(IQueueProcessor::isAvailableToWork)
				.findFirst();
	}

	private void validateQueueProcessorAssignment(@NonNull final IQueueProcessor queueProcessor)
	{
		if (!getSupportedQueueProcessors().contains(queueProcessor.getClass()))
		{
			final String supportedClassNames = getSupportedQueueProcessors().stream().map(Class::getName).collect(Collectors.joining(","));

			throw new AdempiereException("QueueProcessorPlanner can only take: [" + supportedClassNames + "]! but received: " + queueProcessor.getClass().getName());
		}
	}

	@NonNull
	private Integer getPollIntervalMillis()
	{
		return sysConfigBL.getIntValue(SYSCONFIG_POLLINTERVAL_MILLIS, SYSCONFIG_POLLINTERVAL_DEFAULT_MS);
	}

	@NonNull
	private List<IQueueProcessor> getQueueProcessorsAvailableToWork()
	{
		return queueProcessors
				.values()
				.stream()
				.filter(IQueueProcessor::isAvailableToWork)
				.collect(ImmutableList.toImmutableList());
	}

	private static boolean isValid(final I_C_Queue_WorkPackage workPackage)
	{
		if (workPackage == null)
		{
			return false;
		}
		if (workPackage.isProcessed())
		{
			return false;
		}
		if (workPackage.isError())
		{
			return false;
		}

		return true;
	}

	private static void setupNewCtxForWorkPackage(@NonNull final I_C_Queue_WorkPackage workPackage, @NonNull final Properties commonCtx)
	{
		final Properties newCtx = Env.copyCtx(commonCtx);

		InterfaceWrapperHelper.setCtx(workPackage, newCtx);

		WorkPackageQueue.setupWorkPackageContext(newCtx, workPackage);
	}

	protected abstract boolean handleWorkPackageProcessing(@NonNull final IQueueProcessor queueProcessor, @NonNull final I_C_Queue_WorkPackage workPackage);

	protected abstract void startPlanner();

	protected abstract void shutdownPlanner();

	protected abstract Set<Class<? extends AbstractQueueProcessor>> getSupportedQueueProcessors();

	protected abstract boolean isStopOnFailedRun();
}
