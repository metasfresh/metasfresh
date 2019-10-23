package org.adempiere.ad.service;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import de.metas.util.ISingletonService;

/**
 * Service responsible for executing asynchronous tasks. Maintain a map of thread pool executors, one for each thread name prefix.
 *
 * @author tsa
 *
 */
public interface ITaskExecutorService extends ISingletonService
{
	/**
	 * Shut down and destroy all the internal thread pool executors. Successive invocations of a <code>submit</code> or <code>schedule</code> method shall recreate them.
	 */
	void destroy();

	/**
	 * Submit a task to be executed asynchronously, "as soon as possible".
	 *
	 * @param task
	 * @param theadNamePrefix if there is no thread pool executor for the given theadNamePrefix, create one and store it for future use.
	 * @return
	 */
	<T> Future<T> submit(Callable<T> task, String theadNamePrefix);

	/**
	 * Submit a task to be executed asynchronously, "as soon as possible".
	 *
	 * @param task
	 * @param theadNamePrefix if there is no thread pool executor for the given theadNamePrefix, create one and store it for future use.
	 * @return
	 */
	Future<?> submit(Runnable task, String theadNamePrefix);

	/**
	 * Submit a task to be executed asynchronously, after the given time interval has passed.
	 *
	 * @param task
	 * @param time
	 * @param timeUnit
	 * @param theadNamePrefix if there is no thread pool executor for the given theadNamePrefix, create one and store it for future use.
	 * @return
	 */
	<T> ScheduledFuture<T> schedule(Callable<T> task, int time, TimeUnit timeUnit, String theadNamePrefix);
}
