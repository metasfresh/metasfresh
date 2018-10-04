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


import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import de.metas.util.Check;

/**
 * Thread factory that allows to decide if a thread is a daemon or user thread.
 * 
 * The code is based on <code>java.util.concurrent.Executors.DefaultThreadFactory</code> and it brings following functionalities (more):
 * <ul>
 * <li>ability to specify which will be the name prefix for newly created threads
 * <li>ability to specify if we want to create Daemon threads or user threads
 * </ul>
 * 
 * @author tsa
 * 
 */
public final class CustomizableThreadFactory implements ThreadFactory
{
	public static final Builder builder()
	{
		return new Builder();
	}

	/**
	 * Counts the instances of CustomizableThreadFactory and includes the number in each instance's thread name prefix.
	 */
	private static final AtomicInteger poolNumber = new AtomicInteger(1);

	/**
	 * Thread group used to create to new threads
	 */
	private final ThreadGroup group;

	/**
	 * Current thread number in this factory.
	 */
	private final AtomicInteger threadNumber = new AtomicInteger(1);

	/**
	 * Name prefix to be used when creating new threads
	 */
	private final String namePrefix;

	/**
	 * Shall we create Daemon threads?
	 */
	private final boolean daemon;

	private CustomizableThreadFactory(final Builder builder)
	{
		super();
		
		final SecurityManager s = System.getSecurityManager();
		this.group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		this.namePrefix = builder.getThreadNamePrefix() + "-pool-" + poolNumber.getAndIncrement() + "-thread-";

		this.daemon = builder.isDaemon();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This implementation creates a new thread with priority {@link Thread#NORM_PRIORITY}, using {@link #isDaemon()} to decide if the thread shall be a daemon thread or not.
	 */
	@Override
	public Thread newThread(Runnable r)
	{
		String threadName = namePrefix + threadNumber.getAndIncrement();
		final Thread t = new Thread(group, r, threadName, 0); // stackSize=0

		if (t.isDaemon() != daemon)
		{
			t.setDaemon(daemon);
		}
		if (t.getPriority() != Thread.NORM_PRIORITY)
		{
			t.setPriority(Thread.NORM_PRIORITY);
		}
		return t;
	}

	/**
	 * @return the daemon
	 */
	public boolean isDaemon()
	{
		return daemon;
	}

	public static final class Builder
	{
		private String threadNamePrefix;
		private boolean daemon = false;

		private Builder()
		{
			super();
		}

		public CustomizableThreadFactory build()
		{
			return new CustomizableThreadFactory(this);
		}

		public Builder setThreadNamePrefix(String threadNamePrefix)
		{
			this.threadNamePrefix = threadNamePrefix;
			return this;
		}

		private final String getThreadNamePrefix()
		{
			Check.assumeNotEmpty(threadNamePrefix, "threadNamePrefix not empty");
			return threadNamePrefix;
		}

		/**
		 * Decides if the threads shall be daemon threads or user threads.
		 * 
		 * @param daemon the daemon to set
		 */
		public Builder setDaemon(boolean daemon)
		{
			this.daemon = daemon;
			return this;
		}

		private final boolean isDaemon()
		{
			return daemon;
		}
	}
}
