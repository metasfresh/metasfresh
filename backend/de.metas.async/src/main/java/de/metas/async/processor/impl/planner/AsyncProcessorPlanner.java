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

import com.google.common.collect.ImmutableSet;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.impl.AbstractQueueProcessor;
import de.metas.async.processor.impl.ThreadPoolQueueProcessor;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.util.concurrent.CustomizableThreadFactory;
import org.slf4j.Logger;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AsyncProcessorPlanner extends QueueProcessorPlanner
{
	private static final Logger logger = LogManager.getLogger(QueueProcessorPlanner.class);

	private ExecutorService plannerExecutorService = null;
	private ExecutorService queueProcessorExecutorService = null;

	protected void startPlanner()
	{
		restartExecutorsIfTerminated();

		isRunning.set(true);

		this.plannerExecutorService.submit(this);
	}

	protected void shutdownPlanner()
	{
		if (!isRunning.get())
		{
			return;
		}

		shutdownExecutor(queueProcessorExecutorService);

		isRunning.set(false);

		shutdownExecutor(plannerExecutorService);
	}

	protected boolean handleWorkPackageProcessing(@NonNull final IQueueProcessor queueProcessor, @NonNull final I_C_Queue_WorkPackage workPackage)
	{
		queueProcessorExecutorService.submit(() -> queueProcessor.processLockedWorkPackage(workPackage));

		return true;
	}

	@Override
	protected Set<Class<? extends AbstractQueueProcessor>> getSupportedQueueProcessors()
	{
		return ImmutableSet.of(ThreadPoolQueueProcessor.class);
	}

	@Override
	protected boolean isStopOnFailedRun()
	{
		return false;
	}

	private void restartExecutorsIfTerminated()
	{
		if (plannerExecutorService == null || plannerExecutorService.isTerminated())
		{
			plannerExecutorService = createPlannerThreadExecutor();
		}

		if (queueProcessorExecutorService == null || queueProcessorExecutorService.isTerminated())
		{
			queueProcessorExecutorService = createQueueProcessorThreadExecutor();
		}
	}

	private static void shutdownExecutor(@NonNull final ExecutorService executor)
	{
		logger.info("shutdown - Shutdown started for executor={}", executor);
		executor.shutdown();

		int retryCount = 5;
		boolean terminated = false;
		while (!terminated && retryCount > 0)
		{
			logger.info("shutdown - waiting for executor to be terminated; retries left={}; executor={}", retryCount, executor);
			try
			{
				terminated = executor.awaitTermination(5 * 1000, TimeUnit.MICROSECONDS);
			}
			catch (final InterruptedException e)
			{
				logger.warn("Failed shutting down executor for " + executor + ". Retry " + retryCount + " more times.");
				terminated = false;
			}
			retryCount--;
		}

		executor.shutdownNow();
		logger.info("Shutdown finished for executor: {}", executor);
	}

	@NonNull
	private static ThreadPoolExecutor createPlannerThreadExecutor()
	{
		final CustomizableThreadFactory threadFactory = CustomizableThreadFactory.builder()
				.setThreadNamePrefix("QueueProcessorPlanner")
				.setDaemon(true)
				.build();

		return new ThreadPoolExecutor(
				1, // corePoolSize
				1, // maximumPoolSize
				1000, // keepAliveTime
				TimeUnit.MILLISECONDS, // timeUnit
				new SynchronousQueue<>(), // workQueue
				threadFactory, // threadFactory
				new ThreadPoolExecutor.AbortPolicy());
	}

	@NonNull
	private static ThreadPoolExecutor createQueueProcessorThreadExecutor()
	{
		final ThreadFactory threadFactory = CustomizableThreadFactory.builder()
				.setThreadNamePrefix("QueueProcessor")
				.setDaemon(true)
				.build();

		return new ThreadPoolExecutor(
				1, // corePoolSize
				100, // maximumPoolSize
				1000, // keepAliveTime
				TimeUnit.MILLISECONDS, // timeUnit

				// SynchronousQueue has *no* capacity. Therefore, each new submitted task will directly cause a new thread to be started,
				// which is exactly what we want here.
				// Thank you, http://stackoverflow.com/questions/10186397/threadpoolexecutor-without-a-queue !!!
				new SynchronousQueue<>(), // workQueue

				threadFactory, // threadFactory
				new ThreadPoolExecutor.AbortPolicy() // RejectedExecutionHandler
		);
	}
}
