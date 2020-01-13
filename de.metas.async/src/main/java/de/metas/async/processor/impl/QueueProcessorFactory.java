package de.metas.async.processor.impl;

import org.compiere.SpringContextHolder;

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
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IQueueProcessorEventDispatcher;
import de.metas.async.processor.IQueueProcessorFactory;

public class QueueProcessorFactory implements IQueueProcessorFactory
{
	private IWorkpackageLogsRepository getLogsRepository()
	{
		return SpringContextHolder.instance.getBean(IWorkpackageLogsRepository.class);
	}

	@Override
	public IQueueProcessor createSynchronousQueueProcessor(final IWorkPackageQueue queue)
	{
		final IWorkpackageLogsRepository logsRepository = getLogsRepository();
		return new SynchronousQueueProcessor(queue, logsRepository);
	}

	@Override
	public IQueueProcessor createAsynchronousQueueProcessor(final I_C_Queue_Processor config, final IWorkPackageQueue queue)
	{
		final IWorkpackageLogsRepository logsRepository = getLogsRepository();
		return new ThreadPoolQueueProcessor(config, queue, logsRepository);
	}

	private IQueueProcessorEventDispatcher queueProcessorEventDispatcher = new DefaultQueueProcessorEventDispatcher();

	@Override
	public IQueueProcessorEventDispatcher getQueueProcessorEventDispatcher()
	{
		return queueProcessorEventDispatcher;
	}
}
