package de.metas.async.processor.impl;

import de.metas.async.Async_Constants;
import de.metas.async.processor.IMutableQueueProcessorStatistics;
import de.metas.monitoring.api.IMeter;
import de.metas.monitoring.api.IMonitoringBL;
import de.metas.util.Services;
import lombok.NonNull;

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

	public MonitorableQueueProcessorStatistics(@NonNull final String workpackageProcessorName)
	{
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
