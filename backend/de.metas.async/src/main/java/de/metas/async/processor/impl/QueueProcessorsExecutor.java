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


import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.adempiere.util.concurrent.CustomizableThreadFactory;
import org.adempiere.util.concurrent.Threads;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.jmx.JMXQueueProcessor;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IQueueProcessorFactory;
import de.metas.async.processor.IQueueProcessorsExecutor;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Default implementation of queue processor executor
 *
 * @author tsa
 *
 */
@VisibleForTesting
public class QueueProcessorsExecutor implements IQueueProcessorsExecutor
{
	private static final transient Logger logger = LogManager.getLogger(QueueProcessorsExecutor.class);

	private final String threadNamePrefix = "async-Dispatcher";
	private final ThreadPoolExecutor threadExecutor;
	private final Map<Integer, QueueProcessorDescriptor> queueProcessorDescriptors = new ConcurrentHashMap<>();
	private final ReentrantLock mainLock = new ReentrantLock();

	public QueueProcessorsExecutor()
	{
		final CustomizableThreadFactory threadFactory = CustomizableThreadFactory.builder()
				.setThreadNamePrefix(threadNamePrefix)
				.setDaemon(true)
				.build();

		this.threadExecutor = new ThreadPoolExecutor(
				1, // corePoolSize
				100, // maximumPoolSize
				1000, // keepAliveTime
				TimeUnit.MILLISECONDS, // timeUnit

				// SynchronousQueue has *no* capacity. Therefore, each new submitted task will directly cause a new thread to be started,
				// which is exactly what we want here.
				// Thank you, http://stackoverflow.com/questions/10186397/threadpoolexecutor-without-a-queue !!!
				new SynchronousQueue<Runnable>(), // workQueue

				threadFactory, // threadFactory
				new ThreadPoolExecutor.AbortPolicy() // RejectedExecutionHandler
		);
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
			// Run the processor in one of this executors free slots
			final Future<?> future = threadExecutor.submit(() -> {
				// Set thread name to easily debug and check which processor (poller) in which thread.
				final String threadNameOld = Threads.setThreadName(threadNamePrefix + "-" + processor.getName());

				try
				{
					processor.run();
				}
				finally
				{
					// restore thread's name
					Threads.setThreadName(threadNameOld);
				}
			});

			final QueueProcessorDescriptor descriptor = new QueueProcessorDescriptor(queueProcessorId, processor, future);
			queueProcessorDescriptors.put(queueProcessorId, descriptor);

			logger.info("Registered {}", descriptor);

			//
			// Register MBean
			registerMBean(descriptor);
		}
		finally
		{
			mainLock.unlock();
		}
	}

	private IQueueProcessor createProcessor(I_C_Queue_Processor processorDef)
	{
		final IWorkPackageQueue queue = Services.get(IWorkPackageQueueFactory.class).getQueueForPackageProcessing(processorDef);
		return Services.get(IQueueProcessorFactory.class).createAsynchronousQueueProcessor(processorDef, queue);
	}

	@Override
	public boolean removeAllQueueProcessor()
	{
		boolean removed = true;

		mainLock.lock();
		try
		{
			final List<QueueProcessorDescriptor> descriptors = new ArrayList<>(queueProcessorDescriptors.values());
			for (final QueueProcessorDescriptor descriptor : descriptors)
			{
				final int queueProcessorId = descriptor.getQueueProcessorId();
				if (!removeQueueProcessor0(queueProcessorId))
				{
					removed = false;
				}
			}
		}
		finally
		{
			mainLock.unlock();
		}

		return removed;
	}

	@Override
	public boolean removeQueueProcessor(final int queueProcessorId)
	{
		mainLock.lock();
		try
		{
			return removeQueueProcessor0(queueProcessorId);
		}
		finally
		{
			mainLock.unlock();
		}
	}

	private boolean removeQueueProcessor0(final int queueProcessorId)
	{
		final QueueProcessorDescriptor descriptor = queueProcessorDescriptors.remove(queueProcessorId);
		if (descriptor == null)
		{
			return true;
		}

		unregisterMBean(descriptor);

		descriptor.getQueueProcessor().shutdown();

		final Future<?> future = descriptor.getFuture();
		if (future.isDone())
		{
			logger.info("Unregistered {} (already done)", descriptor);
			return true;
		}
		if (future.isCancelled())
		{
			logger.info("Unregistered {} (already canceled)", descriptor);
			return true;
		}

		if (future.cancel(true))
		{
			logger.info("Unregistered {} (canceled now)", descriptor);
			return true;
		}

		logger.warn("Could not unregister {} (canceling failed)", descriptor);
		return false;
	}

	@Override
	public IQueueProcessor getQueueProcessor(final int queueProcessorId)
	{
		mainLock.lock();
		try
		{
			final QueueProcessorDescriptor desc = queueProcessorDescriptors.get(queueProcessorId);
			if (desc == null)
			{
				return null;
			}

			return desc.getQueueProcessor();
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
			threadExecutor.shutdown();

			final List<QueueProcessorDescriptor> descriptors = new ArrayList<>(queueProcessorDescriptors.values());
			for (final QueueProcessorDescriptor desc : descriptors)
			{
				removeQueueProcessor0(desc.getQueueProcessorId());
			}

			threadExecutor.shutdownNow();
			logger.info("Shutdown finished");
		}
		finally
		{
			mainLock.unlock();
		}
	}

	private String createJMXName(final QueueProcessorDescriptor desc)
	{
		final String jmxName = de.metas.async.processor.impl.QueueProcessorsExecutor.class.getName()
				+ ":type=" + desc.getQueueProcessor().getName();
		return jmxName;
	}

	private void registerMBean(final QueueProcessorDescriptor desc)
	{
		unregisterMBean(desc);

		final JMXQueueProcessor processorMBean = new JMXQueueProcessor(desc.getQueueProcessor(), desc.getQueueProcessorId());
		final String jmxName = createJMXName(desc);

		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		try
		{
			final ObjectName name = new ObjectName(jmxName);
			mbs.registerMBean(processorMBean, name);
		}
		catch (MalformedObjectNameException e)
		{
			e.printStackTrace();
		}
		catch (InstanceAlreadyExistsException e)
		{
			e.printStackTrace();
		}
		catch (MBeanRegistrationException e)
		{
			e.printStackTrace();
		}
		catch (NotCompliantMBeanException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
	}

	private void unregisterMBean(final QueueProcessorDescriptor desc)
	{
		final String jmxName = createJMXName(desc);

		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		try
		{
			final ObjectName name = new ObjectName(jmxName);
			mbs.unregisterMBean(name);
		}
		catch (InstanceNotFoundException e)
		{
			// nothing
			// e.printStackTrace();
		}
		catch (MalformedObjectNameException e)
		{
			e.printStackTrace();
		}
		catch (MBeanRegistrationException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}

	}

	private static final class QueueProcessorDescriptor
	{
		private final int queueProcessorId;
		private final IQueueProcessor queueProcessor;
		private final Future<?> future;

		public QueueProcessorDescriptor(final int queueProcessorId, final IQueueProcessor queueProcessor, final Future<?> future)
		{
			super();
			this.queueProcessorId = queueProcessorId;
			this.queueProcessor = queueProcessor;
			this.future = future;
		}

		@Override
		public String toString()
		{
			return "QueueProcessorDescriptor [queueProcessorId=" + queueProcessorId + ", queueProcessor=" + queueProcessor + ", future=" + future + "]";
		}

		public int getQueueProcessorId()
		{
			return queueProcessorId;
		}

		public IQueueProcessor getQueueProcessor()
		{
			return queueProcessor;
		}

		public Future<?> getFuture()
		{
			return future;
		}
	}
}
