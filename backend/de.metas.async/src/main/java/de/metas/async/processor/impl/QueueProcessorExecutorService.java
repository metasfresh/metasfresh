package de.metas.async.processor.impl;

import org.adempiere.util.concurrent.DelayedRunnableExecutor;
import org.compiere.util.Ini;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.processor.IQueueProcessorExecutorService;
import de.metas.async.processor.IQueueProcessorsExecutor;
import de.metas.util.Services;

class QueueProcessorExecutorService implements IQueueProcessorExecutorService
{
	private final IQueueProcessorsExecutor executor;

	/**
	 * Used to delayed invoke {@link #initNow()}.
	 */
	private final DelayedRunnableExecutor delayedInit = new DelayedRunnableExecutor(new Runnable()
	{
		@Override
		public void run()
		{
			initNow();
		}

		@Override
		public String toString()
		{
			// nice toString representation to be displayed part of the thread name
			return QueueProcessorExecutorService.class.getName() + "-delayedInit";
		};
	});

	public QueueProcessorExecutorService()
	{
		if (Ini.isSwingClient())
		{
			// NOTE: later, here we can implement and add a ProxyQueueProcessorExecutorService which will contact the server
			executor = NullQueueProcessorsExecutor.instance;
		}
		else
		{
			// default
			executor = new QueueProcessorsExecutor();
		}
	}

	@Override
	public void init(final long delayMillis)
	{
		// If it's NULL then there is no point to schedule a timer
		if (executor instanceof NullQueueProcessorsExecutor)
		{
			return;
		}

		// Do nothing if already initialized
		if (delayedInit.isDone())
		{
			return;
		}

		delayedInit.run(delayMillis);
	}

	/**
	 * Initialize executors now.
	 *
	 * NOTE: never ever call this method directly. It's supposed to be called from {@link #delayedInit}.
	 */
	private void initNow()
	{
		// NOTE: don't check if it's already initialized because at the time when this method is called the "initialized" flag was already set,
		// to prevent future executions.

		// If it's NULL then there is no point to load all processors
		if (executor instanceof NullQueueProcessorsExecutor)
		{
			return;
		}

		// Remove all queue processors. It shall be none, but just to make sure
		executor.shutdown();

		for (final I_C_Queue_Processor processorDef : Services.get(IQueueDAO.class).retrieveAllProcessors())
		{
			executor.addQueueProcessor(processorDef);
		}
	}

	@Override
	public void removeAllQueueProcessors()
	{
		delayedInit.cancelAndReset();
		executor.shutdown();
	}

	@Override
	public IQueueProcessorsExecutor getExecutor()
	{
		// Make sure executor was initialized.
		// Else it makes no sense to return it because could lead to data corruption.
		if (!isInitialized())
		{
			throw new IllegalStateException("Service not initialized");
		}
		return executor;
	}

	@Override
	public boolean isInitialized()
	{
		return delayedInit.isDone();
	}
}
