package org.adempiere.ad.dao.impl;

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


import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.adempiere.ad.dao.IQueryStatisticsLogger;
import org.adempiere.ad.dao.jmx.JMXQueryStatisticsLogger;
import org.adempiere.ad.dao.jmx.JMXQueryStatisticsLoggerMBean;
import org.adempiere.util.Check;
import org.adempiere.util.time.SystemTime;

public class QueryStatisticsLogger implements IQueryStatisticsLogger
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	private boolean enabled = false;
	private final Map<String, QueryStatistics> sql2statistics = new HashMap<String, QueryStatistics>();
	private final ReentrantLock lock = new ReentrantLock();
	private Date validFrom = null;
	private String filterBy = null;

	public QueryStatisticsLogger()
	{
		super();

		registerJMX();
	}

	private void registerJMX()
	{
		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		final JMXQueryStatisticsLoggerMBean jmxBean = new JMXQueryStatisticsLogger();
		final String jmxName = getClass().getName() + ":type=Statistics";

		final ObjectName name;
		try
		{
			name = new ObjectName(jmxName);
		}
		catch (final MalformedObjectNameException e)
		{
			logger.warn("Unable to create ObjectName: " + jmxName, e);
			return;
		}

		try
		{
			if (!mbs.isRegistered(name))
			{
				mbs.registerMBean(jmxBean, name);
			}
		}
		catch (final InstanceAlreadyExistsException e)
		{
			logger.warn("Unable to register JMX Bean: " + jmxBean, e);
			return;
		}
		catch (final MBeanRegistrationException e)
		{
			logger.warn("Unable to register JMX Bean: " + jmxBean, e);
			return;
		}
		catch (final NotCompliantMBeanException e)
		{
			logger.warn("Unable to register JMX Bean: " + jmxBean, e);
			return;
		}
	}

	@Override
	public void collect(final String sql)
	{
		if (!enabled)
		{
			return;
		}

		lock.lock();
		try
		{
			//
			// Do not log if we're filtering
			if (isFilterBy() && !sql.contains(filterBy)) // TODO filter by RegEx?
			{
				return;
			}

			QueryStatistics queryStatistics = sql2statistics.get(sql);
			if (queryStatistics == null)
			{
				queryStatistics = new QueryStatistics(sql);
				sql2statistics.put(sql, queryStatistics);
			}

			queryStatistics.incrementCounter();
		}
		finally
		{
			lock.unlock();
		}
	}

	@Override
	public void enable()
	{
		if (enabled)
		{
			return;
		}

		reset();
		enabled = true;
	}

	@Override
	public void disable()
	{
		if (!enabled)
		{
			return;
		}

		enabled = false;
	}

	@Override
	public void reset()
	{
		sql2statistics.clear();
		validFrom = SystemTime.asDate();
	}

	@Override
	public void setFilterBy(final String filterBy)
	{
		if (Check.equals(this.filterBy, filterBy))
		{
			//
			// Nothing changed
			return;
		}

		reset();
		this.filterBy = filterBy;
	}

	@Override
	public String getFilterBy()
	{
		return filterBy;
	}

	@Override
	public void clearFilterBy()
	{
		setFilterBy(null);
	}

	private boolean isFilterBy()
	{
		return !Check.isEmpty(filterBy);
	}

	@Override
	public Date getValidFrom()
	{
		return validFrom;
	}

	private List<QueryStatistics> getQueryStatistics()
	{
		lock.lock();
		try
		{
			return new ArrayList<QueryStatistics>(sql2statistics.values());
		}
		finally
		{
			lock.unlock();
		}
	}

	@Override
	public String[] getTopQueriesAsString()
	{
		final List<QueryStatistics> queryStatisticsList = getQueryStatistics();
		Collections.sort(queryStatisticsList, new Comparator<QueryStatistics>()
		{

			@Override
			public int compare(final QueryStatistics o1, final QueryStatistics o2)
			{
				return (o1.getCount() - o2.getCount()) * -1;
			}
		});

		final String[] result = new String[queryStatisticsList.size()];
		for (int i = 0; i < queryStatisticsList.size(); i++)
		{
			final QueryStatistics queryStatistics = queryStatisticsList.get(i);
			final String queryStatisticsStr = queryStatistics.toString();
			result[i] = queryStatisticsStr;
		}

		return result;
	}

	private static class QueryStatistics
	{
		private final String sql;
		private int count = 0;

		public QueryStatistics(final String sql)
		{
			super();
			this.sql = sql;
		}

		@Override
		public String toString()
		{
			return "SQL: " + sql
					+ "\nCount: " + count;
		}

		public void incrementCounter()
		{
			count++;
		}

		public int getCount()
		{
			return count;
		}
	}
}
