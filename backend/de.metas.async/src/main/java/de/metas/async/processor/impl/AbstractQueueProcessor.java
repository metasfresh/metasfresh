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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.api.IWorkpackageLogsRepository;
import de.metas.async.api.WorkPackageLockHelper;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IMutableQueueProcessorStatistics;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IQueueProcessorEventDispatcher;
import de.metas.async.processor.IQueueProcessorFactory;
import de.metas.async.processor.IQueueProcessorStatistics;
import de.metas.async.processor.IWorkpackageProcessorFactory;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.async.processor.QueueProcessorId;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.slf4j.Logger;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractQueueProcessor implements IQueueProcessor
{
	private static final Logger logger = LogManager.getLogger(AbstractQueueProcessor.class);

	private final ReentrantLock mainLock = new ReentrantLock();
	private final IQueueProcessorFactory queueProcessorFactory = Services.get(IQueueProcessorFactory.class);
	private IWorkpackageProcessorFactory workpackageProcessorFactory = null;

	private final IWorkPackageQueue queue;
	private final QueueProcessorStatistics statistics;
	private final IWorkpackageLogsRepository logsRepository;


	public AbstractQueueProcessor(
			@NonNull final IWorkPackageQueue queue,
			@NonNull final IWorkpackageLogsRepository logsRepository)
	{
		this.queue = queue;
		this.logsRepository = logsRepository;
		this.statistics = new QueueProcessorStatistics();
	}

	protected abstract void executeTask(WorkpackageProcessorTask task);

	@Override
	public IWorkPackageQueue getQueue()
	{
		return queue;
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

	@Override
	public boolean processLockedWorkPackage(@NonNull final I_C_Queue_WorkPackage workPackage)
	{
		boolean success = false;
		try
		{
			//dev-note: we acquire a lock to make sure the `isAvailableToWork` check is accurate
			mainLock.lock();

			if (!isAvailableToWork())
			{
				return false;
			}

			success = processWorkPackage(workPackage);
			return success;
		}
		catch (final Throwable t)
		{
			logger.error("*** processLockedWorkPackage failed! Moving forward...", t);
			success = false;
			return false;
		}
		finally
		{
			if (!success)
			{
				WorkPackageLockHelper.unlockNoFail(workPackage);
			}

			mainLock.unlock();
		}
	}

	@NonNull
	public Set<QueuePackageProcessorId> getAssignedPackageProcessorIds() {
		return getQueue().getQueuePackageProcessorIds();
	}

	@Override
	public QueueProcessorId getQueueProcessorId()
	{
		return getQueue().getQueueProcessorId();
	}

	private boolean processWorkPackage(@NonNull final I_C_Queue_WorkPackage workPackage)
	{
		boolean success = false;
		try
		{
			final IWorkpackageProcessor workPackageProcessor = getWorkpackageProcessor(workPackage);
			final WorkpackageProcessorTask task = new WorkpackageProcessorTask(this, workPackageProcessor, workPackage, logsRepository);
			executeTask(task);
			success = true;
			return true;
		}
		finally
		{
			if (!success)
			{
				logger.info("Submitting for processing next workPackage failed. Trying to unlock {}.", workPackage);
				queue.unlockNoFail(workPackage);

				getEventDispatcher().unregisterListeners(workPackage.getC_Queue_WorkPackage_ID());
			}
		}
	}

	private IWorkpackageProcessor getWorkpackageProcessor(final I_C_Queue_WorkPackage workPackage)
	{
		final IWorkpackageProcessorFactory factory = getActualWorkpackageProcessorFactory();

		final Properties ctx = InterfaceWrapperHelper.getCtx(workPackage);
		final int packageProcessorId = workPackage.getC_Queue_PackageProcessor_ID();

		return factory.getWorkpackageProcessor(ctx, packageProcessorId);
	}

	private IQueueProcessorEventDispatcher getEventDispatcher()
	{
		return queueProcessorFactory.getQueueProcessorEventDispatcher();
	}
}
