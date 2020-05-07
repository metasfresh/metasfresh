package de.metas.async.processor.impl;

/*
 * #%L
 * de.metas.async
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


import org.adempiere.util.Services;
import org.adempiere.util.concurrent.DelayedRunnableExecutor;
import org.compiere.db.CConnection;
import org.compiere.util.Ini;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.processor.IQueueProcessorExecutorService;
import de.metas.async.processor.IQueueProcessorsExecutor;

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

		// if (Services.get(IDeveloperModeBL.class).isEnabled())
		if (CConnection.isServerEmbedded())
		{
			// If we are in DeveloperMode we always want to start the actual Queue Processor Executor because the developers needs to check/test his/her enqueings.
			// => I think using "ServerEmbedded" for the decision to start an actual executor is better..being in developer mode does not always mean that there is not actual app-server to communicate
			// with.
			executor = new QueueProcessorsExecutor();
		}
		else if (Ini.isClient())
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
		executor.removeAllQueueProcessor();

		for (final I_C_Queue_Processor processorDef : Services.get(IQueueDAO.class).retrieveAllProcessors())
		{
			executor.addQueueProcessor(processorDef);
		}
	}

	@Override
	public void removeAllQueueProcessors()
	{
		delayedInit.cancelAndReset();
		executor.removeAllQueueProcessor();
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
