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


import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.async.Async_Constants;
import de.metas.async.processor.IMutableQueueProcessorStatistics;
import de.metas.monitoring.api.IMeter;
import de.metas.monitoring.api.IMonitoringBL;

/**
 * Workpackage Processor statistics which are directly bound to monitor {@link IMeter}s.
 * 
 * @author tsa
 * 
 */
public class MonitorableQueueProcessorStatistics implements IMutableQueueProcessorStatistics
{
	private static final String METERNAME_QueueSize = "QueueSize";
	private static final String METERNAME_All = "All";
	private static final String METERNAME_Processed = "Processed";
	private static final String METERNAME_Error = "Error";
	private static final String METERNAME_Skipped = "Skipped";

	private final String workpackageProcessorName;

	public MonitorableQueueProcessorStatistics(final String workpackageProcessorName)
	{
		super();
		Check.assumeNotNull(workpackageProcessorName, "workpackageProcessorName not null");
		this.workpackageProcessorName = workpackageProcessorName;
	}

	private final IMeter getMeter(final String meterName)
	{
		final String moduleName = Async_Constants.ENTITY_TYPE;
		final String meterNameFQ = workpackageProcessorName + "_" + meterName;
		return Services.get(IMonitoringBL.class).createOrGet(moduleName, meterNameFQ);
	}

	/**
	 * NOTE: there is nothing to clone, since this is just an accessor for {@link IMeter}s
	 * 
	 * @return this
	 */
	@Override
	public MonitorableQueueProcessorStatistics clone()
	{
		return this;
	}

	@Override
	public long getCountAll()
	{
		return getMeter(METERNAME_All).getGauge();
	}

	@Override
	public void incrementCountAll()
	{
		getMeter(METERNAME_All).plusOne();
	}

	@Override
	public long getCountProcessed()
	{
		return getMeter(METERNAME_Processed).getGauge();
	}

	@Override
	public void incrementCountProcessed()
	{
		getMeter(METERNAME_Processed).plusOne();
	}

	@Override
	public long getCountErrors()
	{
		return getMeter(METERNAME_Error).getGauge();
	}

	@Override
	public void incrementCountErrors()
	{
		getMeter(METERNAME_Error).plusOne();
	}

	@Override
	public long getQueueSize()
	{
		return getMeter(METERNAME_QueueSize).getGauge();
	}

	@Override
	public void incrementQueueSize()
	{
		getMeter(METERNAME_QueueSize).plusOne();
	}

	@Override
	public void decrementQueueSize()
	{
		getMeter(METERNAME_QueueSize).minusOne();
	}

	@Override
	public long getCountSkipped()
	{
		return getMeter(METERNAME_Skipped).getGauge();
	}

	@Override
	public void incrementCountSkipped()
	{
		getMeter(METERNAME_Skipped).plusOne();
	}
}
