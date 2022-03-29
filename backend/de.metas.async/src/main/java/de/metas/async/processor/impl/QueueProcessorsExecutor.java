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

import com.google.common.annotations.VisibleForTesting;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.jmx.JMXQueueProcessor;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IQueueProcessorFactory;
import de.metas.async.processor.IQueueProcessorsExecutor;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.processor.QueueProcessorId;
import de.metas.async.processor.impl.planner.AsyncProcessorPlanner;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Default implementation of queue processor executor
 *
 * @author tsa
 */
@VisibleForTesting
public class QueueProcessorsExecutor implements IQueueProcessorsExecutor
{
	private static final transient Logger logger = LogManager.getLogger(QueueProcessorsExecutor.class);

	private final ReentrantLock mainLock = new ReentrantLock();
	private final AsyncProcessorPlanner asyncProcessorPlanner = new AsyncProcessorPlanner();

	public QueueProcessorsExecutor()
	{
	}

	@Override
	public void addQueueProcessor(@NonNull final I_C_Queue_Processor processorDef)
	{
		mainLock.lock();
		try
		{
			final int queueProcessorId = processorDef.getC_Queue_Processor_ID();
			removeQueueProcessor0(queueProcessorId);

			final IQueueProcessor processor = createProcessor(processorDef);
			Check.assumeNotNull(processor, "processor not null"); // shall not happen

			//
			// Register MBean
			registerMBean(processor);

			asyncProcessorPlanner.addQueueProcessor(processor);

			asyncProcessorPlanner.start();
		}
		finally
		{
			mainLock.unlock();
		}
	}

	@Override
	public void removeQueueProcessor(final int queueProcessorId)
	{
		mainLock.lock();
		try
		{
			removeQueueProcessor0(queueProcessorId);
		}
		finally
		{
			mainLock.unlock();
		}
	}

	private void removeQueueProcessor0(final int queueProcessorId)
	{
		final Optional<IQueueProcessor> queueProcessor = asyncProcessorPlanner.getQueueProcessor(QueueProcessorId.ofRepoId(queueProcessorId));
		if (!queueProcessor.isPresent())
		{
			return;
		}

		unregisterMBean(queueProcessor.get());

		this.asyncProcessorPlanner.removeQueueProcessor(queueProcessor.get().getQueueProcessorId());
	}

	@Override
	@Nullable
	public IQueueProcessor getQueueProcessor(final int queueProcessorId)
	{
		mainLock.lock();
		try
		{
			return asyncProcessorPlanner
					.getQueueProcessor(QueueProcessorId.ofRepoId(queueProcessorId))
					.orElse(null);
		}
		finally
		{
			mainLock.unlock();
		}
	}

	@Override
	public void shutdown()
	{
		mainLock.lock();
		try
		{
			logger.info("Shutdown started");

			asyncProcessorPlanner.getRegisteredQueueProcessors()
					.stream()
					.map(IQueueProcessor::getQueueProcessorId)
					.map(QueueProcessorId::getRepoId)
					.forEach(this::removeQueueProcessor0);

			this.asyncProcessorPlanner.shutdown();

			logger.info("Shutdown finished");
		}
		finally
		{
			mainLock.unlock();
		}
	}

	private void registerMBean(final IQueueProcessor queueProcessor)
	{
		unregisterMBean(queueProcessor);

		final JMXQueueProcessor processorMBean = new JMXQueueProcessor(queueProcessor, queueProcessor.getQueueProcessorId().getRepoId());

		final String jmxName = createJMXName(queueProcessor);

		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		try
		{
			final ObjectName name = new ObjectName(jmxName);
			mbs.registerMBean(processorMBean, name);
		}
		catch (final MalformedObjectNameException | InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException | NullPointerException e)
		{
			e.printStackTrace();
		}
	}

	private void unregisterMBean(@NonNull final IQueueProcessor queueProcessor)
	{
		final String jmxName = createJMXName(queueProcessor);

		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		try
		{
			final ObjectName name = new ObjectName(jmxName);
			mbs.unregisterMBean(name);
		}
		catch (final InstanceNotFoundException e)
		{
			// nothing
			// e.printStackTrace();
		}
		catch (final MalformedObjectNameException | NullPointerException | MBeanRegistrationException e)
		{
			e.printStackTrace();
		}
	}

	private IQueueProcessor createProcessor(@NonNull final I_C_Queue_Processor processorDef)
	{
		final IWorkPackageQueue queue = Services.get(IWorkPackageQueueFactory.class).getQueueForPackageProcessing(processorDef);
		return Services.get(IQueueProcessorFactory.class).createAsynchronousQueueProcessor(processorDef, queue);
	}

	private static String createJMXName(@NonNull final IQueueProcessor queueProcessor)
	{
		return QueueProcessorsExecutor.class.getName()
				+ ":type=" + queueProcessor.getName();
	}
}
