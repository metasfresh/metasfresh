package org.adempiere.util.concurrent;

/*
 * #%L
 * de.metas.util
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


import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import de.metas.util.Check;

/**
 * Helper class used to delayed start a given {@link Runnable}.
 * 
 * @author tsa
 *
 */
public final class DelayedRunnableExecutor
{
	private final Runnable runnable;

	private final String threadNamePrefix;
	private final CustomizableThreadFactory threadFactory;
	private ScheduledThreadPoolExecutor _executor;
	private ScheduledFuture<?> future;

	private AtomicBoolean done = new AtomicBoolean(false);

	/**
	 * Task which will be submited to {@link #_executor} to execute the {@link #runnable}.
	 */
	private class RunnableTask implements Runnable
	{
		/**
		 * Store a local reference of the "done" flag because when we are canceling and reseting this helper, a new instance of "done" will be created.
		 * 
		 * @see DelayedRunnableExecutor#cancelAndReset()
		 */
		private AtomicBoolean done = DelayedRunnableExecutor.this.done;

		@Override
		public void run()
		{
			Thread.currentThread().setName(threadNamePrefix + "-RUNNING");

			try
			{
				// Mark as done.
				// Do nothing if already executed.
				if (done.getAndSet(true))
				{
					return;
				}

				runnable.run();
			}
			finally
			{
				Thread.currentThread().setName(threadNamePrefix + "-DONE");
			}
		}

	};

	/**
	 * 
	 * @param runnable the runnable that will be delayed started when {@link #run(long)} is invoked.
	 */
	public DelayedRunnableExecutor(final Runnable runnable)
	{
		super();
		Check.assumeNotNull(runnable, "runnable not null");
		this.runnable = runnable;

		this.threadNamePrefix = getClass().getSimpleName() + "-" + runnable.toString();
		this.threadFactory = CustomizableThreadFactory.builder()
				.setThreadNamePrefix(threadNamePrefix)
				.setDaemon(true)
				.build();
	}

	/**
	 * Execute the underlying runnable after <code>delayMs</code> milliseconds.
	 * 
	 * If the runnable was already executed, this method will do nothing.
	 * 
	 * @param delayMillis
	 */
	public synchronized void run(final long delayMillis)
	{
		// Do nothig if already done
		if (done.get())
		{
			return;
		}

		// If we already have an execution scheduled, and it was not performed yet then cancel it.
		if (future != null && !future.isDone())
		{
			final boolean mayInterruptIfRunning = false; // don't interrupt if is already running
			future.cancel(mayInterruptIfRunning);
		}

		//
		// Schedule our run
		final ScheduledExecutorService executor = getExecutor();
		future = executor.schedule(new RunnableTask(), delayMillis, TimeUnit.MILLISECONDS);
	}

	private final ScheduledExecutorService getExecutor()
	{
		// NOTE: we are lazy initalizate the executor because we want to consume as few resources as possible in case
		// an instance of this executor is created but nobody asked for something to be executed.
		if (_executor == null)
		{
			final int corePoolSize = 1; // we go with only one thread
			_executor = new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);

			// Allow the core thread to be terminated if not needed.
			// In this way, after our runnable is executed we would not keep any running threads.
			_executor.setKeepAliveTime(10, TimeUnit.SECONDS);
			_executor.allowCoreThreadTimeOut(true);
		}
		return _executor;
	}

	/**
	 * @return true if the underlying runnable was already executed.
	 */
	public synchronized boolean isDone()
	{
		return done.get();
	}

	/**
	 * Try to stop any scheduled run (triggered by invoking {@link #run(long)}) and reset this helper to initial state,
	 * which means that next time when you will call {@link #run(long)} it will be like first time.
	 */
	public synchronized final void cancelAndReset()
	{
		// Eagerly mark as done.
		final boolean alreadyDone = done.getAndSet(true);

		//
		// If not already done, try stop the current running future (if any)
		if (!alreadyDone)
		{
			final ScheduledFuture<?> future = this.future;
			if (future != null && !future.isDone())
			{
				// Try to cancel current execution.
				// If this was not possible, then wait until it finishes.
				if (!future.cancel(false))
				{
					try
					{
						future.get();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}

		// Create a new instance of "done" flag and set it to false.
		// We do this because existing enqueued tasks will work with the old "done" reference which was set to "true".
		done = new AtomicBoolean(false);
	}
}
