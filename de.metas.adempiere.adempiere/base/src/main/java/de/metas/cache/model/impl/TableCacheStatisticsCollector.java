package de.metas.cache.model.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.slf4j.Logger;

import de.metas.cache.CacheMgt;
import de.metas.cache.model.ITableCacheConfig;
import de.metas.cache.model.ITableCacheStatistics;
import de.metas.cache.model.ITableCacheStatisticsCollector;
import de.metas.cache.model.jmx.JMXCacheStatisticsManager;
import de.metas.cache.model.jmx.JMXTableCacheStatistics;
import de.metas.logging.LogManager;
import de.metas.util.Check;

/* package */class TableCacheStatisticsCollector implements ITableCacheStatisticsCollector
{
	private static final transient Logger logger = LogManager.getLogger(TableCacheStatisticsCollector.class);

	private final String collectorName;

	private boolean enabled = false;
	private boolean tableStatisticsEnabled = false;

	/**
	 * Global statistics
	 */
	private ITableCacheStatistics statistics = null;
	private Map<String, ITableCacheStatistics> tableName2statistics = null;
	private final ReentrantLock statisticsLock = new ReentrantLock();

	public TableCacheStatisticsCollector(final String collectorName)
	{
		super();

		Check.assumeNotEmpty(collectorName, "collectorName not empty");
		this.collectorName = collectorName;

		registerMBean("StatisticsManager", new JMXCacheStatisticsManager(this));

		setEnabled(false);
		setTableStatisticsEnabled(false);
	}

	@Override
	public void record(final ITableCacheConfig cacheConfig, final boolean hit, final boolean inTransaction)
	{
		if (!enabled)
		{
			return;
		}

		statisticsLock.lock();
		try
		{
			record(statistics, hit, inTransaction);

			//
			// Record table statistics
			if (tableStatisticsEnabled)
			{
				final ITableCacheStatistics tableStatistics = getTableStatistics(cacheConfig);
				record(tableStatistics, hit, inTransaction);
			}
		}
		finally
		{
			statisticsLock.unlock();
		}
	}

	private final void record(ITableCacheStatistics statistics, final boolean hit, final boolean inTransaction)
	{
		if (hit)
		{
			if (inTransaction)
			{
				statistics.incrementHitInTrxCount();

			}
			else
			{
				statistics.incrementHitCount();
			}
		}
		else
		{
			if (inTransaction)
			{
				statistics.incrementMissInTrxCount();
			}
			else
			{
				statistics.incrementMissCount();
			}
		}

	}

	private final ITableCacheStatistics getTableStatistics(final ITableCacheConfig cacheConfig)
	{
		final String tableName = cacheConfig.getTableName();
		ITableCacheStatistics tableStatistics = tableName2statistics.get(tableName);
		if (tableStatistics == null)
		{
			tableStatistics = createTableStatistics(tableName, cacheConfig);
			tableName2statistics.put(tableName, tableStatistics);
		}

		return tableStatistics;
	}

	private ITableCacheStatistics createTableStatistics(final String tableName, final ITableCacheConfig cacheConfig)
	{
		final ITableCacheStatistics tableStatistics = new TableCacheStatistics(tableName, cacheConfig);

		//
		// Register JMX bean
		registerMBean(tableStatistics);

		return tableStatistics;
	}

	@Override
	public void reset()
	{
		statisticsLock.lock();
		try
		{
			if (!enabled)
			{
				return;
			}

			statistics.reset();

			for (ITableCacheStatistics tableStatistics : tableName2statistics.values())
			{
				tableStatistics.reset();
			}
		}
		finally
		{
			statisticsLock.unlock();
		}
	}

	@Override
	public void setEnabled(final boolean enabled)
	{
		if (this.enabled == enabled)
		{
			return;
		}

		statisticsLock.lock();
		try
		{
			this.enabled = enabled;

			if (enabled)
			{
				final ITableCacheConfig cacheConfig = null; // cache config is not available on Overall statistics
				this.statistics = createTableStatistics("Overall", cacheConfig);
			}
			else
			{
				unregisterMBean(statistics);
				this.statistics = null;

				setTableStatisticsEnabled(false);
			}
		}
		finally
		{
			statisticsLock.unlock();
		}
	}

	@Override
	public boolean isEnabled()
	{
		return this.enabled;
	}

	@Override
	public void setTableStatisticsEnabled(final boolean tableStatisticsEnabled)
	{
		if (this.tableStatisticsEnabled == tableStatisticsEnabled)
		{
			return;
		}

		statisticsLock.lock();
		try
		{
			this.tableStatisticsEnabled = tableStatisticsEnabled;

			if (tableStatisticsEnabled)
			{
				tableName2statistics = new HashMap<String, ITableCacheStatistics>();
			}
			else
			{
				for (final ITableCacheStatistics tableStatistics : tableName2statistics.values())
				{
					unregisterMBean(tableStatistics);
				}
				tableName2statistics.clear();
				tableName2statistics = null;
			}
		}
		finally
		{
			statisticsLock.unlock();
		}
	}

	@Override
	public boolean isTableStatisticsEnabled()
	{
		return tableStatisticsEnabled;
	}

	private final String createJMXName(final String jmxType)
	{
		final String jmxName = CacheMgt.JMX_BASE_NAME + "." + collectorName + ":type=" + jmxType;
		return jmxName;
	}

	private final String createJMXType(final ITableCacheStatistics tableStatistics)
	{
		final String jmxType = "Statistics-" + tableStatistics.getTableName();
		return jmxType;
	}

	private final void registerMBean(final ITableCacheStatistics tableStatistics)
	{
		final JMXTableCacheStatistics jmxTableStatistics = new JMXTableCacheStatistics(tableStatistics);
		final String jmxType = createJMXType(tableStatistics);
		registerMBean(jmxType, jmxTableStatistics);
	}

	private final void unregisterMBean(final ITableCacheStatistics tableStatistics)
	{
		final String jmxType = createJMXType(tableStatistics);
		unregisterMBean(jmxType);
	}

	private final void registerMBean(final String jmxType, final Object jmxBean)
	{
		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		final String jmxName = createJMXName(jmxType);
		final ObjectName name;
		try
		{
			name = new ObjectName(jmxName);
		}
		catch (MalformedObjectNameException e)
		{
			logger.warn("Cannot create JMX name: " + jmxName, e);
			return;
		}

		try
		{
			if (!mbs.isRegistered(name))
			{
				mbs.registerMBean(jmxBean, name);
			}
		}
		catch (Exception e)
		{
			logger.warn("Cannot register MBean for " + jmxName, e);
			return;
		}
	}

	private final void unregisterMBean(final String jmxType)
	{
		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		final String jmxName = createJMXName(jmxType);
		final ObjectName name;
		try
		{
			name = new ObjectName(jmxName);
		}
		catch (MalformedObjectNameException e)
		{
			logger.warn("Cannot create JMX name: " + jmxName, e);
			return;
		}

		try
		{
			mbs.unregisterMBean(name);
		}
		catch (Exception e)
		{
			logger.warn("Cannot unregister MBean for " + jmxName, e);
			return;
		}

	}
}
