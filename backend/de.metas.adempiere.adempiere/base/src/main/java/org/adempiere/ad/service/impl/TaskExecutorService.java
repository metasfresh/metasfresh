package org.adempiere.ad.service.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.adempiere.ad.service.ITaskExecutorService;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.concurrent.CustomizableThreadFactory;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import de.metas.logging.LogManager;

public class TaskExecutorService implements ITaskExecutorService
{
	private static final transient Logger logger = LogManager.getLogger(TaskExecutorService.class);

	private static final String SYSCONFIG_Server_MaxThreadPoolSize = "org.adempiere.ad.service.ITaskExecutorService.Server.MaxThreadPoolSize";
	private static final String SYSCONFIG_Client_MaxThreadPoolSize = "org.adempiere.ad.service.ITaskExecutorService.Client.MaxThreadPoolSize";

	private static final String THREADNAMEPREFIX_ROOT = "TES-";
	private static final String THREADNAMEPREFIX_DEFAULT = THREADNAMEPREFIX_ROOT + TaskExecutorService.class.getName(); // using the full class name, because it is our responsibility to have a fallback that is reasonably unique.

	private final LoadingCache<String, ScheduledThreadPoolExecutor> _threadNamePrefix2ThreadPoolExecutor = CacheBuilder.newBuilder()
			.expireAfterAccess(20, TimeUnit.MINUTES)
			.removalListener(new RemovalListener<String, ScheduledThreadPoolExecutor>()
			{

				@Override
				public void onRemoval(final RemovalNotification<String, ScheduledThreadPoolExecutor> notification)
				{
					destroyExecutor(notification.getValue());
				}
			})
			.build(new CacheLoader<String, ScheduledThreadPoolExecutor>()
			{
				@Override
				public ScheduledThreadPoolExecutor load(final String threadNamePrefix) throws Exception
				{
					return createExecutor(threadNamePrefix);
				}
			});

	private ScheduledThreadPoolExecutor getCreateExecutor(final String threadNamePrefix)
	{
		final String threadNamePrefixToUse;
		if (Check.isEmpty(threadNamePrefix, true))
		{
			threadNamePrefixToUse = THREADNAMEPREFIX_DEFAULT;
			logger.debug("Param threadNamePrefix={} is empty; falling back to {}", threadNamePrefix, threadNamePrefixToUse);
		}
		else
		{
			threadNamePrefixToUse = THREADNAMEPREFIX_ROOT + threadNamePrefix.trim();
		}

		try
		{
			return _threadNamePrefix2ThreadPoolExecutor.get(threadNamePrefixToUse);
		}
		catch (final ExecutionException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	private final ScheduledThreadPoolExecutor createExecutor(final String threadNamePrefix)
	{
		final int corePoolSizeDefault = Runtime.getRuntime().availableProcessors();
		final int corePoolSize = Services.get(ISysConfigBL.class).getIntValue(Ini.isClient() ? SYSCONFIG_Client_MaxThreadPoolSize : SYSCONFIG_Server_MaxThreadPoolSize, corePoolSizeDefault);

		final CustomizableThreadFactory threadFactory = CustomizableThreadFactory.builder()
				.setThreadNamePrefix(threadNamePrefix)
				.setDaemon(true)
				.build();

		// note-ts: i would prefer the BlockingThreadPoolExecutor, because those threads usually do SQL and with the ScheduledThreadPoolExecutor, the number of parallel threads is not bound
		// However, we don't have the time to properly QA the effects of the UI if MLookup loader threads are still waiting in the queue while others are already done
		// ..that's why we still use ScheduledThreadPoolExecutor

		// final int keepAliveTime = 0;
		// threadPoolExecutor = new BlockingThreadPoolExecutor(corePoolSize, keepAliveTime, TimeUnit.MILLISECONDS, threadFactory);
		final ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
		logger.debug("Created {} with corePoolSize={}, threadNamePrefix={}", threadPoolExecutor, corePoolSize, threadNamePrefix);
		return threadPoolExecutor;
	}

	private final void destroyExecutor(final ScheduledThreadPoolExecutor threadPoolExecutor)
	{
		threadPoolExecutor.shutdown();
		logger.debug("Shutdown {}", threadPoolExecutor);
	}

	@Override
	public void destroy()
	{
		_threadNamePrefix2ThreadPoolExecutor.invalidateAll();
		_threadNamePrefix2ThreadPoolExecutor.cleanUp();
	}

	@Override
	public <T> Future<T> submit(final Callable<T> task, final String theadNamePrefix)
	{
		final ScheduledThreadPoolExecutor threadPoolExecutor = getCreateExecutor(theadNamePrefix);
		return threadPoolExecutor.submit(task);
	}

	@Override
	public <T> ScheduledFuture<T> schedule(final Callable<T> task,
			final int time,
			final TimeUnit timeUnit,
			final String theadNamePrefix)
	{
		final ScheduledThreadPoolExecutor threadPoolExecutor = getCreateExecutor(theadNamePrefix);
		return threadPoolExecutor.schedule(task, time, timeUnit);
	}

	@Override
	public Future<?> submit(final Runnable task, final String theadNamePrefix)
	{
		final ScheduledThreadPoolExecutor threadPoolExecutor = getCreateExecutor(theadNamePrefix);
		return threadPoolExecutor.submit(task);
	}

}
