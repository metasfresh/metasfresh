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

import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IQueueProcessorsExecutor;
import de.metas.async.processor.QueueProcessorId;
import de.metas.async.processor.descriptor.model.QueueProcessorDescriptor;

/**
 * Null implementation of {@link IQueueProcessorsExecutor}. Mainly, it does nothing.
 * 
 * @author tsa
 * 
 */
public final class NullQueueProcessorsExecutor implements IQueueProcessorsExecutor
{
	public static final NullQueueProcessorsExecutor instance = new NullQueueProcessorsExecutor();

	private NullQueueProcessorsExecutor()
	{
		super();
	}

	@Override
	public void removeQueueProcessor(final QueueProcessorId queueProcessorId)
	{
	}

	@Override
	public void addQueueProcessor(final QueueProcessorDescriptor processorDef)
	{
		// nothing
	}

	@Override
	public void shutdown()
	{
		// nothing
	}

	@Override
	public IQueueProcessor getQueueProcessor(final QueueProcessorId queueProcessorId)
	{
		// nothing
		return null;
	}
}
