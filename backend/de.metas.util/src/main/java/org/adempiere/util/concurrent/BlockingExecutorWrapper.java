package org.adempiere.util.concurrent;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Delegate;
import org.slf4j.Logger;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2020 metas GmbH
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

/**
 * Thx to https://stackoverflow.com/questions/3446011/threadpoolexecutor-block-when-queue-is-full
 */
@ToString
public class BlockingExecutorWrapper implements ExecutorService
{
	private final Semaphore semaphore;

	@Delegate(excludes = Executor.class) // don't delegate to Executor.execute(); of that, we have our own
	private final ThreadPoolExecutor delegate;

	private @NonNull Logger logger;

	@Builder
	private BlockingExecutorWrapper(
			final int poolSize,
			@NonNull final ThreadPoolExecutor delegate,
			@NonNull final Logger loggerToUse)
	{
		this.semaphore = new Semaphore(poolSize);
		this.delegate = delegate;
		this.logger = loggerToUse;
	}

	@Override
	public void execute(@NonNull final Runnable command)
	{
		try
		{
			logger.debug("Going to acquire semaphore{}", semaphore.toString());
			semaphore.acquire();
			logger.debug("Done acquiring semaphore={}", semaphore.toString());
		}
		catch (final InterruptedException e)
		{
			logger.warn("execute - InterruptedException while acquiring semaphore=" + semaphore + " for command=" + command + ";-> return", e);
			return;
		}

		// wrap 'command' into another runnable, so we can in the end release the semaphore.
		final Runnable r = new Runnable()
		{
			public String toString()
			{
				return "runnable-wrapper-for[" + command + "]";
			}

			public void run()
			{
				try
				{
					logger.debug("execute - Going to invoke command.run() within delegate thread-pool");
					command.run();
					logger.debug("execute - Done invoking command.run() within delegate thread-pool");
				}
				catch (final Throwable t)
				{
					logger.error("execute - Caught throwable while running command=" + command + "; -> rethrow", t);
					throw t;
				}
				finally
				{
					semaphore.release();
					logger.debug("Released semaphore={}", semaphore);
				}
			}
		};
		try
		{
			delegate.execute(r); // don't expect RejectedExecutionException because delegate has a small queue that can hold runnables after semaphore-release and before they can actually start
		}
		catch (final RejectedExecutionException e)
		{
			semaphore.release();
			logger.error("execute - Caught RejectedExecutionException while trying to submit task=" + r + " to delegate thread-pool=" + delegate + "; -> released semaphore=" + semaphore + " and rethrow", e);
			throw e;
		}
	}

	public boolean hasAvailablePermits()
	{
		return semaphore.availablePermits() > 0;
	}
}
