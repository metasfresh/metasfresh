package de.metas.async.processor;

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

import de.metas.async.processor.descriptor.model.QueueProcessorDescriptor;

/**
 * Responsible for managing registered {@link IQueueProcessor}.
 * 
 * Implementations of this class will be instantiated and configured by {@link IQueueProcessorExecutorService}.
 * 
 * @author tsa
 * 
 */
public interface IQueueProcessorsExecutor
{
	/**
	 * Instantiates and configures the right {@link IQueueProcessor} for given definition.
	 * Starts the underlying {@link de.metas.async.processor.impl.planner.QueueProcessorPlanner} if not started already.
	 * 
	 * @param processorDef
	 */
	void addQueueProcessor(QueueProcessorDescriptor processorDef);

	/**
	 * Unregisters the {@link IQueueProcessor} for given C_Queue_Processor_ID.
	 * 
	 * @param queueProcessorId
	 */
	void removeQueueProcessor(final QueueProcessorId queueProcessorId);

	/**
	 * Stops all {@link de.metas.async.processor.impl.planner.QueueProcessorPlanner} and {@link IQueueProcessor} threads
	 */
	void shutdown();

	/**
	 * Gets {@link IQueueProcessor} for given C_Queue_Processor_ID.
	 * 
	 * @param queueProcessorId
	 * @return
	 */
	IQueueProcessor getQueueProcessor(QueueProcessorId queueProcessorId);
}
