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


import java.util.concurrent.TimeoutException;

import de.metas.util.Check;

/**
 * Misc {@link Thread} helpers
 *
 * @author tsa
 *
 */
public final class Threads
{
	private Threads()
	{
	}

	/**
	 * Attempt to stop given thread.
	 *
	 * @param thread thread to be stopped.
	 * @param timeoutMillis how many millis (maximum) to try stopping the thread; if ZERO it will try to stop infinitelly
	 * @throws TimeoutException if thread could not be stopped in given time frame.
	 */
	public static final void stop(final Thread thread, final long timeoutMillis) throws TimeoutException
	{
		Check.assumeNotNull(thread, "thread not null");
		Check.assume(timeoutMillis >= 0, "timeoutMillis >= 0");

		long timeoutRemaining = timeoutMillis <= 0 ? Long.MAX_VALUE : timeoutMillis;
		final long periodMillis = Math.min(100, timeoutRemaining);

		//
		// As long as the thread is not dead and we did not exceed our timeout, hit the tread unil it dies.
		while (thread.isAlive() && timeoutRemaining > 0)
		{
			thread.interrupt();

			// Wait for the thread to die
			try
			{
				thread.join(periodMillis);
			}
			catch (InterruptedException e)
			{
				// Thread was interrupted.
				// We don't fucking care, we continue to hit it until it's dead or until the timeout is exceeded.
			}

			timeoutRemaining -= periodMillis;
		}

		//
		// If the thread is still alive we throw an timeout exception
		if (thread.isAlive())
		{
			throw new TimeoutException("Failed to kill thread " + thread.getName() + " in " + timeoutMillis + "ms");
		}
	}

	/**
	 * Convenient method to set a thread name and also get the previous name.
	 *
	 * @param thread
	 * @param name
	 * @return previous thread name
	 */
	public static String setThreadName(final Thread thread, final String name)
	{
		final String nameOld = thread.getName();
		thread.setName(name);
		return nameOld;
	}

	/**
	 * Convenient method to set a current thread's name and also get the previous name.
	 *
	 * @param thread
	 * @param name
	 * @return previous thread name
	 */
	public static String setThreadName(final String name)
	{
		return setThreadName(Thread.currentThread(), name);
	}
}
