package org.adempiere.ad.dao.cache.jmx;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.adempiere.ad.dao.cache.ITableCacheStatisticsCollector;

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
