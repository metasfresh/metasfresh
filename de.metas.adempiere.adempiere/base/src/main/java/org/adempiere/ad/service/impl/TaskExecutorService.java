package org.adempiere.ad.service.impl;

/*
 * #%L
 * ADempiere ERP - Base
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
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import org.adempiere.ad.service.ITaskExecutorService;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.adempiere.util.concurrent.CustomizableThreadFactory;
import org.compiere.util.Ini;

public class TaskExecutorService implements ITaskExecutorService
{
	private static final String CONFIG_Server_MaxThreadPoolSize = "org.adempiere.ad.service.ITaskExecutorService.Server.MaxThreadPoolSize";
	private static final String CONFIG_Client_MaxThreadPoolSize = "org.adempiere.ad.service.ITaskExecutorService.Client.MaxThreadPoolSize";

	private ThreadPoolExecutor threadPoolExecutor;

	public TaskExecutorService()
	{
		init();
	}

	private final void init()
	{
		if (threadPoolExecutor != null)
		{
			return;
		}

		final int corePoolSizeDefault = Runtime.getRuntime().availableProcessors();
		final int corePoolSize = Services.get(ISysConfigBL.class).getIntValue(Ini.isClient() ? CONFIG_Client_MaxThreadPoolSize : CONFIG_Server_MaxThreadPoolSize, corePoolSizeDefault);

		final CustomizableThreadFactory threadFactory = CustomizableThreadFactory.builder()
				.setThreadNamePrefix("adempiere-task-executor")
				.setDaemon(true)
				.build();

		// note-ts: i would prefer the BlockingThreadPoolExecutor, because those threads usually do SQL and with the ScheduledThreadPoolExecutor, the number of parallel threads is not bound
		// However, we don't have the time to properly QA the effects of the UI if MLookup loader threads are still waiting in the queue while others are already done
		// ..that's why we still use ScheduledThreadPoolExecutor
		
		//final int keepAliveTime = 0;
		//threadPoolExecutor = new BlockingThreadPoolExecutor(corePoolSize, keepAliveTime, TimeUnit.MILLISECONDS, threadFactory);
		threadPoolExecutor = new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
	}

	@Override
	public void destroy()
	{
		if (threadPoolExecutor == null)
		{
			return;
		}

		threadPoolExecutor.shutdown();
		threadPoolExecutor = null;
	}

	@Override
	public <T> Future<T> submit(Callable<T> task)
	{
		init();
		return threadPoolExecutor.submit(task);
	}

	@Override
	public Future<?> submit(Runnable task)
	{
		init();
		return threadPoolExecutor.submit(task);
	}
}
