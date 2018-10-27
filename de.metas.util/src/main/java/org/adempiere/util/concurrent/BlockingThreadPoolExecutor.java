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


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import de.metas.util.Check;

/**
 * An {@link ThreadPoolExecutor} which blocks on submitting new tasks, if the maximum pool size was reached. This implementation is taken from <a
 * href="https://today.java.net/pub/a/today/2008/10/23/creating-a-notifying-blocking-thread-pool-executor.html"
 * >https://today.java.net/pub/a/today/2008/10/23/creating-a-notifying-blocking-thread-pool-executor.html</a>.
 * 
 * It is supposed to to a similar thing like {@link BlockingThreadPoolExecutor}, but it drops the requirement of having a core and maximum pool size, and it doesnt use semaphores to achieve the
 * blocking, but in stead implemented a rejection policy that boils down to block and wait for a place in the pool.
 * 
 * @author ts
 * 
 */
public class BlockingThreadPoolExecutor extends ThreadPoolExecutor
{
	/**
	 * 
	 * @param the number of threads in this pool.This value shall be greater than zero.
	 * @param threadFactory
	 * @see ThreadPoolExecutor#ThreadPoolExecutor(int, int, long, TimeUnit, java.util.concurrent.BlockingQueue, ThreadFactory, RejectedExecutionHandler)
	 */
	public BlockingThreadPoolExecutor(
			final int poolSize,
			final ThreadFactory threadFactory)
	{
		super(
				poolSize,
				poolSize,
				1,
				TimeUnit.MILLISECONDS,
				new SynchronousQueue<Runnable>(),
				threadFactory,

				new BlockThenRunPolicy(500, TimeUnit.MILLISECONDS, // check every 500 ms if a place in the pool became free
						new Callable<Boolean>() // this callback never wants to stop waiting, so it always returns true
						{
							@Override
							public Boolean call() throws Exception
							{
								return true;
							}
						}));
		Check.assume(poolSize > 0, "poolSize={} > 0", poolSize);
	}

	@Override
	public void setRejectedExecutionHandler(RejectedExecutionHandler h)
	{
		throw new UnsupportedOperationException("setRejectedExecutionHandler is not allowed on this class.");
	}

	// --------------------------------------------------
	// Inner private class of BlockingThreadPoolExecutor
	// A reject policy that waits on the queue
	// --------------------------------------------------
	private static class BlockThenRunPolicy implements RejectedExecutionHandler
	{

		private final long blockTimeout;
		private final TimeUnit blocTimeoutUnit;
		private final Callable<Boolean> blockTimeoutCallback;

		public BlockThenRunPolicy(long blockTimeout, TimeUnit blocTimeoutUnit, Callable<Boolean> blockTimeoutCallback)
		{
			super();
			this.blockTimeout = blockTimeout;
			this.blocTimeoutUnit = blocTimeoutUnit;
			this.blockTimeoutCallback = blockTimeoutCallback;
		}

		// --------------------------------------------------

		@Override
		public void rejectedExecution(
				Runnable task,
				ThreadPoolExecutor executor)
		{

			BlockingQueue<Runnable> queue = executor.getQueue();
			boolean taskSent = false;

			while (!taskSent)
			{
				if (executor.isShutdown())
				{
					throw new RejectedExecutionException("ThreadPoolExecutor has shutdown while attempting to offer a new task.");
				}

				try
				{
					// offer the task to the queue, for a blocking-timeout
					if (queue.offer(task, blockTimeout, blocTimeoutUnit))
					{
						taskSent = true;
					}
					else
					{
						// task was not accepted - call the user's Callback
						Boolean result = null;
						try
						{
							result = blockTimeoutCallback.call();
						}
						catch (Exception e)
						{
							// wrap the Callback exception and re-throw
							throw new RejectedExecutionException(e);
						}
						// check the Callback result
						if (result == false)
						{
							throw new RejectedExecutionException("User decided to stop waiting for task insertion");
						}
						else
						{
							// user decided to keep waiting (may log it)
							continue;
						}
					}
				}
				catch (InterruptedException e)
				{
					// we need to go back to the offer call...
				}

			} // end of while for InterruptedException

		} // end of method rejectExecution

		// --------------------------------------------------

	} // end of inner private class BlockThenRunPolicy

	@Override
	public String toString()
	{
		return String.format("BlockingThreadPoolExecutor [getPoolSize()=%s]", getPoolSize());
	}
}
