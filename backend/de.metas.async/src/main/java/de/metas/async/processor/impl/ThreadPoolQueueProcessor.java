package de.metas.async.processor.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import org.adempiere.util.concurrent.BlockingThreadPoolExecutor;
import org.adempiere.util.concurrent.CustomizableThreadFactory;

import com.google.common.base.MoreObjects;

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.api.IWorkpackageLogsRepository;
import de.metas.async.model.I_C_Queue_Processor;

class ThreadPoolQueueProcessor extends AbstractQueueProcessor
{
	private String name;
	private final ThreadPoolExecutor executor;
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
					.setThreadNamePrefix("de.metas.async.processor.impl.ThreadPoolQueueProcessor-" + name)
					.setDaemon(true)
					.build();

			executor = new BlockingThreadPoolExecutor(
					config.getPoolSize(),
					threadFactory
			);
			// If we have a KeepAliveTimeMillis in processor definition, then we apply the timeout for core threads too
			executor.allowCoreThreadTimeOut(config.getKeepAliveTimeMillis() > 0);
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
		if (executor.isTerminating())
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
	protected void executeTask(WorkpackageProcessorTask task)
	{
		executor.submit(task);
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
		logger.info("Shutdown started");
		executor.shutdown();

		int retryCount = 5;
		boolean terminated = false;
		while (terminated && retryCount > 0)
		{
			try
			{
				terminated = executor.awaitTermination(5 * 1000, TimeUnit.MICROSECONDS);
			}
			catch (InterruptedException e)
			{
				logger.warn("Failed shutdowning executor for " + name + ". Retry " + retryCount + " more times.");
				terminated = false;
			}
			retryCount--;
		}

		executor.shutdownNow();
		logger.info("Shutdown finished");

		running.set(false);
	}
}
