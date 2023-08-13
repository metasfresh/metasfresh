package de.metas.async.processor.impl;

import com.google.common.base.MoreObjects;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.api.IWorkpackageLogsRepository;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.util.concurrent.BlockingExecutorWrapper;
import org.adempiere.util.concurrent.CustomizableThreadFactory;
import org.slf4j.Logger;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolQueueProcessor extends AbstractQueueProcessor
{
	private static final Logger logger = LogManager.getLogger(ThreadPoolQueueProcessor.class);

	/** we don't have LogManager in the executor's package, so we create the logger here and inject it when creating the executor. */
	private static final Logger loggerForExecutor = LogManager.getLogger(BlockingExecutorWrapper.class);

	private final String name;
	private final BlockingExecutorWrapper executor;
	private final ReentrantLock shutdownLock = new ReentrantLock();

	public ThreadPoolQueueProcessor(
			final I_C_Queue_Processor config,
			final IWorkPackageQueue queue,
			final IWorkpackageLogsRepository logsRepository)
	{
		super(queue, logsRepository);

		this.name = config.getName();

		// Create the tasks executor
		{
			final CustomizableThreadFactory threadFactory = CustomizableThreadFactory.builder()
					.setThreadNamePrefix("async-Worker-" + name)
					.setDaemon(true)
					.build();

			// About threadPoolQueue: we must be able to hold max 1 runnable for each thread of the pool,
			// because within BlockingExecutorWrapper the semaphore is released by the runnable before it's done.
			// That means that the next runnable can be submitted before the runnable "really" made place within the thread pool.
			// That means we need to be able to enqueue the next runnable.
			final ArrayBlockingQueue<Runnable> threadPoolQueue = new ArrayBlockingQueue<>(config.getPoolSize());
			final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(config.getPoolSize()/*corePoolSize*/,
					config.getPoolSize(),
					config.getKeepAliveTimeMillis(),
					TimeUnit.MILLISECONDS,
					threadPoolQueue,
					threadFactory);

			// If we have a KeepAliveTimeMillis in processor definition, then we apply the timeout for core threads too
			 threadPoolExecutor.allowCoreThreadTimeOut(config.getKeepAliveTimeMillis() > 0);

			this.executor = BlockingExecutorWrapper.builder()
					.delegate(threadPoolExecutor)
					.loggerToUse(loggerForExecutor)
					.poolSize(config.getPoolSize())
					.build();
		}
	}

	@Override
	public String getName()
	{
		return name;
	}

	public boolean isAvailableToWork()
	{
		return this.executor.hasAvailablePermits();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("name", name)
				.add("executor", executor)
				.toString();
	}

	@Override
	public void shutdownExecutor()
	{
		shutdownLock.lock();
		try
		{
			shutdown0();
		}
		finally
		{
			shutdownLock.unlock();
		}
	}

	@Override
	protected void executeTask(@NonNull final WorkpackageProcessorTask task)
	{
		logger.debug("Going to submit task={} to executor={}", task, executor);
		executor.execute(task);
		logger.debug("Done submitting task");
	}

	private void shutdown0()
	{
		logger.info("shutdown - Shutdown started for executor={}", executor);
		executor.shutdown();

		int retryCount = 5;
		boolean terminated = false;
		while (!terminated && retryCount > 0)
		{
			logger.info("shutdown - waiting for executor to be terminated; retries left={}; executor={}", retryCount, executor);
			try
			{
				terminated = executor.awaitTermination(5 * 1000, TimeUnit.MICROSECONDS);
			}
			catch (final InterruptedException e)
			{
				logger.warn("Failed shutting down executor for " + name + ". Retry " + retryCount + " more times.");
				terminated = false;
			}
			retryCount--;
		}

		executor.shutdownNow();
		logger.info("Shutdown finished");
	}
}
