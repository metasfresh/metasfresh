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

import com.google.common.collect.ImmutableSet;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IMutableQueueProcessorStatistics;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IQueueProcessorStatistics;
import de.metas.async.processor.IWorkpackageProcessorFactory;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.async.processor.QueueProcessorId;
import de.metas.async.spi.IWorkpackageProcessor;
import org.junit.Ignore;

import java.util.Set;

/**
 * An {@link IQueueProcessor} which actually does nothing. To be used in decoupled unit tests.
 * 
 * @author tsa
 * 
 */
@Ignore
public class MockedQueueProcessor implements IQueueProcessor
{

	private IWorkpackageProcessorFactory workpackageProcessorFactory;
	private IWorkPackageQueue queue;
	private final IMutableQueueProcessorStatistics statistics = new QueueProcessorStatistics();

	public MockedQueueProcessor()
	{
	}

	@Override
	public String getName()
	{
		return getClass().getName();
	}

	@Override
	public void setWorkpackageProcessorFactory(final IWorkpackageProcessorFactory workpackageProcessorFactory)
	{
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
		return getWorkpackageProcessorFactory();
	}

	@Override
	public IQueueProcessorStatistics getStatisticsSnapshot()
	{
		return statistics;
	}

	@Override
	public IWorkPackageQueue getQueue()
	{
		return queue;
	}

	public void setQueue(final IWorkPackageQueue queue)
	{
		this.queue = queue;
	}

	@Override
	public void shutdownExecutor()
	{
		// nothing
	}

	@Override
	public void notifyWorkpackageProcessed(final I_C_Queue_WorkPackage workPackage, final IWorkpackageProcessor workPackageProcessor)
	{
	}

	@Override
	public boolean isAvailableToWork()
	{
		return false;
	}

	@Override
	public Set<QueuePackageProcessorId> getAssignedPackageProcessorIds()
	{
		return ImmutableSet.of();
	}

	@Override
	public boolean processLockedWorkPackage(final I_C_Queue_WorkPackage workPackage) { return true; }

	@Override
	public QueueProcessorId getQueueProcessorId()
	{
		return null;
	}
}
