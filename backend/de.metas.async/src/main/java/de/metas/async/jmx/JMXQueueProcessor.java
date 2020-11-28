package de.metas.async.jmx;

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

public class JMXQueueProcessor implements JMXQueueProcessorMBean
{
	private final IQueueProcessor processor;
	private final int queueProcessorId;

	public JMXQueueProcessor(final IQueueProcessor processor, final int queueProcessorId)
	{
		this.processor = processor;
		this.queueProcessorId = queueProcessorId;
	}

	@Override
	public String getName()
	{
		return processor.getName();
	}

	@Override
	public int getQueueProcessorId()
	{
		return queueProcessorId;
	}

	@Override
	public long getCountAll()
	{
		return processor.getStatisticsSnapshot().getCountAll();
	}

	@Override
	public long getCountProcessed()
	{
		return processor.getStatisticsSnapshot().getCountProcessed();
	}

	@Override
	public long getCountErrors()
	{
		return processor.getStatisticsSnapshot().getCountErrors();
	}

	@Override
	public long getCountSkipped()
	{
		return processor.getStatisticsSnapshot().getCountSkipped();
	}

	@Override
	public String getQueueInfo()
	{
		return processor.getQueue().toString();
	}
}
