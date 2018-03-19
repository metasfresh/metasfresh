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


import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.exceptions.ConfigurationException;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IMutableQueueProcessorStatistics;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IQueueProcessorEventDispatcher;
import de.metas.async.processor.IQueueProcessorFactory;
import de.metas.async.processor.IQueueProcessorStatistics;
import de.metas.async.processor.IWorkpackageProcessorFactory;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.logging.LogManager;
import lombok.NonNull;

public abstract class AbstractQueueProcessor implements IQueueProcessor
{
	protected final transient Logger logger = LogManager.getLogger(getClass());

	private final IWorkPackageQueue queue;
	private long queuePollingTimeout = IWorkPackageQueue.TIMEOUT_Infinite;

	private IWorkpackageProcessorFactory workpackageProcessorFactory = null;
	private final IMutableQueueProcessorStatistics statistics;

	public AbstractQueueProcessor(final IWorkPackageQueue queue)
	{
		super();

		Check.assumeNotNull(queue != null, "queue not null");
		this.queue = queue;
		this.statistics = newMutableQueueProcessorStatistics();
	}

	protected abstract boolean isRunning();

	protected abstract void executeTask(WorkpackageProcessorTask task);

	protected IMutableQueueProcessorStatistics newMutableQueueProcessorStatistics()
	{
		return new QueueProcessorStatistics();
	}

	@Override
	public IWorkPackageQueue getQueue()
	{
		return queue;
	}

	/**
	 * @return the queuePollingTimeout
	 */
	public long getQueuePollingTimeout()
	{
		return queuePollingTimeout;
	}

	/**
	 * @param queuePollingTimeout the queuePollingTimeout to set
	 */
	protected void setQueuePollingTimeout(long queuePollingTimeout)
	{
		this.queuePollingTimeout = queuePollingTimeout;
	}

	@Override
	public void run()
	{
		while (true)
		{
			if (Thread.interrupted())
			{
				logger.info("Thread interrupted. Shutdown executor and quit");
				shutdown();
				break;
			}
			if (!isRunning())
			{
				logger.info("Processor not running anymore. Shutdown executor and quit");
				shutdown();
				break;
			}

			boolean success = false;
			boolean skip = false;
			Exception error = null;
			try
			{
				success = pollAndSubmitNextWorkPackageTask();
			}
			catch (final ConfigurationException e)
			{
				// We have a configuration issue (e.g. one processor class is missing)
				error = e;
				success = false;
				skip = true;
			}
			catch (final Exception e)
			{
				error = e;
				success = false;
			}

			if (!success)
			{
				// Shall we skip this package?
				if (skip)
				{
					continue;
				}

				if (getQueuePollingTimeout() == IWorkPackageQueue.TIMEOUT_OneTimeOnly)
				{
					if (error != null)
					{
						throw new AdempiereException("Polling and submit failed: " + error.getLocalizedMessage(), error);
					}
					else
					{
						logger.info("Last polling wasn't successful and QueuePollingTimeout is OneTimeOnly. Stopping here");
						break;
					}
				}
				else
				{
					if (error != null)
					{
						logger.warn(error.getLocalizedMessage(), error);
					}
					logger.info("Previous pollAndSubmit was not successfull. Sleeping 1000ms");
					try
					{
						Thread.sleep(1000);
					}
					catch (final InterruptedException e)
					{
						// thread was interrupted. Quit.
						logger.debug("Thread interrupted while sleeping. Quit.", e);
						break;
					}
				}
			}
		}
	}

	private boolean pollAndSubmitNextWorkPackageTask()
	{
		final IWorkPackageQueue queue = getQueue();

		final I_C_Queue_WorkPackage workPackage = queue.pollAndLock(queuePollingTimeout);
		if (workPackage == null)
		{
			return false;
		}

		boolean success = false;
		try
		{
			final IWorkpackageProcessor workPackageProcessor = getWorkpackageProcessor(workPackage);
			final WorkpackageProcessorTask task = new WorkpackageProcessorTask(this, workPackageProcessor, workPackage);
			executeTask(task);
			success = true;
		}
		finally
		{
			if (!success)
			{
				logger.info("Submiting for processing next workpackage failed. Trying to unlock {}.", workPackage);
				queue.unlockNoFail(workPackage);

				getEventDispatcher().unregisterListeners(workPackage.getC_Queue_WorkPackage_ID());
			}
		}
		return success;
	}

	@Override
	public IQueueProcessorStatistics getStatisticsSnapshot()
	{
		synchronized (statistics)
		{
			return statistics.clone();
		}
	}

	@Override
	public void notifyWorkpackageProcessed(
			@NonNull final I_C_Queue_WorkPackage workPackage,
			@NonNull final IWorkpackageProcessor workPackageProcessor)
	{
		// Statistics
		synchronized (statistics)
		{
			final IMutableQueueProcessorStatistics workpackageProcessorStatistics = //
					getActualWorkpackageProcessorFactory().getWorkpackageProcessorStatistics(workPackageProcessor);

			statistics.incrementCountAll();
			workpackageProcessorStatistics.incrementCountAll();

			if (workPackage.isProcessed())
			{
				statistics.incrementCountProcessed();
				workpackageProcessorStatistics.incrementCountProcessed();

				// statistics.decrementQueueSize(); // N/A at Queue Processor level because there is no way to increment it
				workpackageProcessorStatistics.decrementQueueSize();

			}
			else if (workPackage.isError())
			{
				statistics.incrementCountErrors();
				workpackageProcessorStatistics.incrementCountErrors();

				// statistics.decrementQueueSize(); // N/A at Queue Processor level because there is no way to increment it
				workpackageProcessorStatistics.decrementQueueSize();
			}
			// Skipped
			// TODO: it would be better to introduce a flag for Skipped or better C_Queue_Workpackage.Status(Processed, Error, Skipped, New)
			else
			{
				statistics.incrementCountSkipped();
				workpackageProcessorStatistics.incrementCountSkipped();

				// NOTE: we shall not decrement queue size because the workpackage was just skipped
				// workpackageProcessorStatistics.decrementQueueSize();
			}
		}

		// Notify event listeners
		getEventDispatcher().fireWorkpackageProcessed(workPackage, workPackageProcessor);
	}

	@Override
	public void setWorkpackageProcessorFactory(final IWorkpackageProcessorFactory workpackageProcessorFactory)
	{
		Check.assumeNotNull(workpackageProcessorFactory, "workpackageProcessorFactory not null");
		this.workpackageProcessorFactory = workpackageProcessorFactory;
	}

	@Override
	public IWorkpackageProcessorFactory getWorkpackageProcessorFactory()
	{
		return workpackageProcessorFactory;
	}

	@Override
	public IWorkpackageProcessorFactory getActualWorkpackageProcessorFactory()
	{
		final IWorkpackageProcessorFactory factory = workpackageProcessorFactory == null ? Services.get(IWorkpackageProcessorFactory.class) : workpackageProcessorFactory;
		return factory;
	}

	protected IWorkpackageProcessor getWorkpackageProcessor(final I_C_Queue_WorkPackage workPackage)
	{
		final IWorkpackageProcessorFactory factory = getActualWorkpackageProcessorFactory();

		final Properties ctx = InterfaceWrapperHelper.getCtx(workPackage);
		final int packageProcessorId = workPackage.getC_Queue_Block().getC_Queue_PackageProcessor_ID();

		return factory.getWorkpackageProcessor(ctx, packageProcessorId);
	}

	protected IQueueProcessorEventDispatcher getEventDispatcher()
	{
		return Services.get(IQueueProcessorFactory.class).getQueueProcessorEventDispatcher();
	}
}
