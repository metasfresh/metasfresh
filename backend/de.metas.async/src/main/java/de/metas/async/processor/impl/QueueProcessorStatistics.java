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

import de.metas.async.processor.IMutableQueueProcessorStatistics;
import lombok.ToString;

@ToString
final class QueueProcessorStatistics implements IMutableQueueProcessorStatistics
{
	private long countAll;
	private long countProcessed;
	private long countErrors;
	private long countSkipped;
	private long queueSize;

	public QueueProcessorStatistics()
	{
		countAll = 0;
		countProcessed = 0;
		countErrors = 0;
		countSkipped = 0;
		queueSize = 0;
	}

	private QueueProcessorStatistics(final QueueProcessorStatistics from)
	{
		countAll = from.countAll;
		countErrors = from.countErrors;
		countProcessed = from.countProcessed;
		countSkipped = from.countSkipped;
		queueSize = from.queueSize;
	}

	@Override
	public QueueProcessorStatistics clone()
	{
		return new QueueProcessorStatistics(this);
	}

	@Override
	public long getCountAll()
	{
		return countAll;
	}

	@Override
	public void incrementCountAll()
	{
		countAll++;
	}

	@Override
	public long getCountProcessed()
	{
		return countProcessed;
	}

	@Override
	public void incrementCountProcessed()
	{
		countProcessed++;
	}

	@Override
	public long getCountErrors()
	{
		return countErrors;
	}

	@Override
	public void incrementCountErrors()
	{
		countErrors++;
	}

	@Override
	public long getQueueSize()
	{
		return queueSize;
	}

	@Override
	public void incrementQueueSize()
	{
		queueSize++;
	}

	@Override
	public void decrementQueueSize()
	{
		queueSize--;
	}

	@Override
	public long getCountSkipped()
	{
		return countSkipped;
	}

	@Override
	public void incrementCountSkipped()
	{
		countSkipped++;
	}

}
