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


import de.metas.async.processor.IMutableQueueProcessorStatistics;

public class QueueProcessorStatistics implements IMutableQueueProcessorStatistics
{
	private long countAll = 0;
	private long countProcessed = 0;
	private long countErrors = 0;
	private long countSkipped = 0;
	private long queueSize = 0;

	@Override
	public QueueProcessorStatistics clone()
	{
		final QueueProcessorStatistics statisticsNew = new QueueProcessorStatistics();
		statisticsNew.countAll = countAll;
		statisticsNew.countErrors = countErrors;
		statisticsNew.countProcessed = countProcessed;
		statisticsNew.countSkipped = countSkipped;
		statisticsNew.queueSize = queueSize;
		return statisticsNew;
	}

	@Override
	public String toString()
	{
		return "QueueProcessorStatistics ["
				+ "countAll=" + countAll
				+ ", countProcessed=" + countProcessed
				+ ", countErrors=" + countErrors
				+ ", countSkipped=" + countSkipped
				+ ", queueSize=" + queueSize
				+ "]";
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
