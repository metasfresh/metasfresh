package de.metas.cache.model.jmx;

import de.metas.cache.model.ITableCacheStatisticsCollector;
import de.metas.util.Check;

public class JMXCacheStatisticsManager implements JMXCacheStatisticsManagerMBean
{
	private final ITableCacheStatisticsCollector statisticsCollector;

	public JMXCacheStatisticsManager(final ITableCacheStatisticsCollector statisticsCollector)
	{
		super();

		Check.assumeNotNull(statisticsCollector, "statisticsCollector not null");
		this.statisticsCollector = statisticsCollector;
	}

	@Override
	public void resetStatistics()
	{
		statisticsCollector.reset();
	}

	@Override
	public boolean isStatisticsEnabled()
	{
		return statisticsCollector.isEnabled();
	}

	@Override
	public void enableStatistics()
	{
		statisticsCollector.setEnabled(true);
	}

	@Override
	public void disableStatistics()
	{
		statisticsCollector.setEnabled(false);
	}

	@Override
	public boolean isTableLevelStatistics()
	{
		return statisticsCollector.isTableStatisticsEnabled();
	}

	@Override
	public void enableTableLevelStatistics()
	{
		enableStatistics();
		statisticsCollector.setTableStatisticsEnabled(true);
	}

	@Override
	public void disableTableLevelStatistics()
	{
		statisticsCollector.setTableStatisticsEnabled(false);
	}
}
