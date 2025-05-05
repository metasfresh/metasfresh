package org.adempiere.util.concurrent;

import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.util.lang.IAutoCloseable;
import org.slf4j.Logger;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

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
 * Thx to <a href="https://stackoverflow.com/questions/3446011/threadpoolexecutor-block-when-queue-is-full">stackoverflow</a>
 */
@ToString
public class BlockingExecutorWrapper
{
	@NonNull private final ThreadPoolExecutor delegate;
	@NonNull private final Logger logger;
	@NonNull private final Semaphore semaphore;

	@Builder
	private BlockingExecutorWrapper(
			final int poolSize,
			@NonNull final ThreadPoolExecutor delegate,
			@NonNull final Logger loggerToUse)
	{
		this.delegate = delegate;
		this.logger = loggerToUse;
		this.semaphore = new Semaphore(poolSize);
	}

	public void execute(@NonNull final Runnable command)
	{
		final IAutoCloseable permit = acquirePermit();

		try
		{
			delegate.execute(() -> {
				//noinspection TryFinallyCanBeTryWithResources
				try
				{
					command.run();
				}
				finally
				{
					permit.close(); // release the semaphore permit on actual task is done
				}
			});
		}
		catch (final Exception enqueueException)
		{
			permit.close(); // release the semaphore permit just in case enqueueing fails

			throw enqueueException;
		}
	}

	private IAutoCloseable acquirePermit()
	{
		try
		{
			logger.debug("Going to acquire semaphore{}", semaphore);
			semaphore.acquire();
			logger.debug("Done acquiring semaphore={}", semaphore);
		}
		catch (final InterruptedException e)
		{
			throw Check.mkEx("Got interrupted while acquiring permit from " + semaphore, e);
		}

		final AtomicBoolean closed = new AtomicBoolean();
		return () -> {
			final boolean alreadyClosed = closed.getAndSet(true);
			if (!alreadyClosed)
			{
				semaphore.release();
				logger.debug("Released semaphore={}", semaphore);
			}
		};
	}

	public boolean hasAvailablePermits()
	{
		return semaphore.availablePermits() > 0;
	}

	public void shutdown() {delegate.shutdown();}

	public void shutdownNow() {delegate.shutdownNow();}

	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException
	{
		return delegate.awaitTermination(timeout, unit);
	}
}
