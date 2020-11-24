package de.metas.async.processor.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;

/*
 * #%L
 * de.metas.async
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

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import org.adempiere.util.concurrent.BlockingExecutorWrapper;
import org.adempiere.util.concurrent.CustomizableThreadFactory;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.api.IWorkpackageLogsRepository;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.logging.LogManager;
import lombok.NonNull;

class ThreadPoolQueueProcessor extends AbstractQueueProcessor
{
	private static final Logger logger = LogManager.getLogger(ThreadPoolQueueProcessor.class);

	/** we don't have LogManager in the executor's package, so we create the logger here and inject it when creating the executor. */
	private static final Logger loggerForExecutor = LogManager.getLogger(BlockingExecutorWrapper.class);

	private String name;
	private final ExecutorService executor;
	private final AtomicBoolean running;

	public ThreadPoolQueueProcessor(
			final I_C_Queue_Processor config,
			final IWorkPackageQueue queue,
			final IWorkpackageLogsRepository logsRepository)
	{
		super(queue, logsRepository);

		this.name = config.getName();

		//
		// Create the tasks executor
		{
			final CustomizableThreadFactory threadFactory = CustomizableThreadFactory.builder()
					.setThreadNamePrefix("async-Worker-" + name)
					.setDaemon(true)
					.build();

			// About threadPoolQueue: we must be able to hold max 1 runnable for each thread of the pool,
			// because within BlockingExecutorWrapper the semaphore is released by the runnable before it's done.
			// That means that the next runnable can be submitted before the runnable "really" made place within the thread pool.
			// That means we need to be able to enqueue the next runnable.
			final ArrayBlockingQueue threadPoolQueue = new ArrayBlockingQueue<>(config.getPoolSize());
			final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(config.getPoolSize()/*corePoolSize*/,
					config.getPoolSize(),
					config.getKeepAliveTimeMillis(),
					TimeUnit.MILLISECONDS,
					threadPoolQueue,
					threadFactory);
			// If we have a KeepAliveTimeMillis in processor definition, then we apply the timeout for core threads too
			 threadPoolExecutor.allowCoreThreadTimeOut(config.getKeepAliveTimeMillis() > 0);

			this.executor = BlockingExecutorWrapper.builder()
					.delegate(threadPoolExecutor)
					.loggerToUse(loggerForExecutor)
					.poolSize(config.getPoolSize())
					.build();
		}

		this.running = new AtomicBoolean(true);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("name", name)
				.add("executor", executor)
				.toString();
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	protected boolean isRunning()
	{
		if (!running.get())
		{
			return false;
		}
		if (executor.isTerminated())
		{
			return false;
		}
		if (executor.isShutdown())
		{
			return false;
		}

		return true;
	}

	@Override
	protected void executeTask(@NonNull final WorkpackageProcessorTask task)
	{
		logger.debug("Going to submit task={} to executor={}", task, executor);
		executor.execute(task);
		logger.debug("Done submitting task");
	}

	private final ReentrantLock shutdownLock = new ReentrantLock();

	@Override
	public void shutdown()
	{
		shutdownLock.lock();
		try
		{
			shutdown0();
		}
		finally
		{
			shutdownLock.unlock();
		}
	}

	private final void shutdown0()
	{
		logger.info("shutdown - Shutdown started for executor={}", executor);
		executor.shutdown();

		int retryCount = 5;
		boolean terminated = false;
		while (terminated && retryCount > 0)
		{
			logger.info("shutdown - waiting for executor to be terminated; retries left={}; executor={}", retryCount, executor);
			try
			{
				terminated = executor.awaitTermination(5 * 1000, TimeUnit.MICROSECONDS);
			}
			catch (InterruptedException e)
			{
				logger.warn("Failed shutting down executor for " + name + ". Retry " + retryCount + " more times.");
				terminated = false;
			}
			retryCount--;
		}

		executor.shutdownNow();
		logger.info("Shutdown finished");

		running.set(false);
	}
}
