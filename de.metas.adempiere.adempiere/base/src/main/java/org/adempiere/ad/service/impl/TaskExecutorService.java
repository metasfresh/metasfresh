package org.adempiere.ad.service.impl;

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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.service.ITaskExecutorService;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.concurrent.CustomizableThreadFactory;
import org.compiere.util.Ini;

public class TaskExecutorService implements ITaskExecutorService
{
	private static final transient Logger logger = LogManager.getLogger(TaskExecutorService.class);

	private static final String CONFIG_Server_MaxThreadPoolSize = "org.adempiere.ad.service.ITaskExecutorService.Server.MaxThreadPoolSize";
	private static final String CONFIG_Client_MaxThreadPoolSize = "org.adempiere.ad.service.ITaskExecutorService.Client.MaxThreadPoolSize";

	private Map<String, ScheduledThreadPoolExecutor> threadNamePrefix2ThreadPoolExecutor = new HashMap<>();

	private ScheduledThreadPoolExecutor getOrInit(final String threadNamePrefix)
	{
		final String threadNamePrefixToUse;
		if (Check.isEmpty(threadNamePrefix, true))
		{
			threadNamePrefixToUse = TaskExecutorService.class.getName(); // using the full class name, because it is our responsibility to have a fallback that is reasonably unique.
			logger.info("Param threadNamePrefix={} is empty; falling back to {}", threadNamePrefix, threadNamePrefixToUse);
		}
		else
		{
			threadNamePrefixToUse = threadNamePrefix;
		}

		if (threadNamePrefix2ThreadPoolExecutor.containsKey(threadNamePrefixToUse))
		{
			return threadNamePrefix2ThreadPoolExecutor.get(threadNamePrefixToUse);
		}

		final int corePoolSizeDefault = Runtime.getRuntime().availableProcessors();
		final int corePoolSize = Services.get(ISysConfigBL.class).getIntValue(Ini.isClient() ? CONFIG_Client_MaxThreadPoolSize : CONFIG_Server_MaxThreadPoolSize, corePoolSizeDefault);

		final CustomizableThreadFactory threadFactory = CustomizableThreadFactory.builder()
				.setThreadNamePrefix(threadNamePrefixToUse)
				.setDaemon(true)
				.build();

		// note-ts: i would prefer the BlockingThreadPoolExecutor, because those threads usually do SQL and with the ScheduledThreadPoolExecutor, the number of parallel threads is not bound
		// However, we don't have the time to properly QA the effects of the UI if MLookup loader threads are still waiting in the queue while others are already done
		// ..that's why we still use ScheduledThreadPoolExecutor

		// final int keepAliveTime = 0;
		// threadPoolExecutor = new BlockingThreadPoolExecutor(corePoolSize, keepAliveTime, TimeUnit.MILLISECONDS, threadFactory);
		final ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
		threadNamePrefix2ThreadPoolExecutor.put(threadNamePrefixToUse, threadPoolExecutor);
		return threadPoolExecutor;
	}

	@Override
	public void destroy()
	{
		for (final ScheduledThreadPoolExecutor threadPoolExecutor : threadNamePrefix2ThreadPoolExecutor.values())
		{
			threadPoolExecutor.shutdown();
		}
		threadNamePrefix2ThreadPoolExecutor.clear();
	}

	@Override
	public <T> Future<T> submit(final Callable<T> task, final String theadNamePrefix)
	{
		final ScheduledThreadPoolExecutor threadPoolExecutor = getOrInit(theadNamePrefix);
		return threadPoolExecutor.submit(task);
	}

	@Override
	public <T> ScheduledFuture<T> schedule(final Callable<T> task,
			final int time,
			final TimeUnit timeUnit,
			final String theadNamePrefix)
	{
		final ScheduledThreadPoolExecutor threadPoolExecutor = getOrInit(theadNamePrefix);
		return threadPoolExecutor.schedule(task, time, timeUnit);
	}

	@Override
	public Future<?> submit(final Runnable task, final String theadNamePrefix)
	{
		final ScheduledThreadPoolExecutor threadPoolExecutor = getOrInit(theadNamePrefix);
		return threadPoolExecutor.submit(task);
	}

}
