package org.adempiere.ad.dao.impl;

import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.adempiere.ad.dao.IQueryStatisticsCollector;
import org.adempiere.ad.dao.IQueryStatisticsLogger;
import org.adempiere.sql.impl.StatementsFactory;
import org.adempiere.util.Check;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.CStatementVO;
import org.compiere.util.Trace;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import com.google.common.base.Stopwatch;

@Service
@ManagedResource(objectName = "org.adempiere.ad.dao.impl.QueryStatisticsLogger:type=Statistics", description = "SQL query statistics and tracing")
public class QueryStatisticsLogger implements IQueryStatisticsLogger, IQueryStatisticsCollector
{
	private static final TimeUnit TIMEUNIT_Internal = TimeUnit.NANOSECONDS;
	private static final TimeUnit TIMEUNIT_Display = TimeUnit.MILLISECONDS;

	private boolean enabled = false;
	private final ConcurrentHashMap<String, QueryStatistics> sql2statistics = new ConcurrentHashMap<>();
	private Date validFrom = null;
	private String filterBy = null;

	private boolean traceSqlQueries = false;
	private static final AtomicInteger traceSqlQueries_Count = new AtomicInteger(0);

	public QueryStatisticsLogger()
	{
		super();
	}

	@Override
	public void collect(final CStatementVO vo, final Stopwatch duration)
	{
		if (!enabled)
		{
			return;
		}

		final String sql = vo == null ? null : vo.getSql();
		final Map<Integer, Object> sqlParams = vo == null ? null : vo.getDebugSqlParams();
		final String trxName = vo == null ? "?" : vo.getTrxName();
		collect(sql, sqlParams, trxName, duration);
	}

	@Override
	public void collect(final String sql, final Stopwatch duration)
	{
		if (!enabled)
		{
			return;
		}

		final Map<Integer, Object> sqlParams = null;
		final String trxName = "?";
		collect(sql, sqlParams, trxName, duration);
	}

	private void collect(final String sql, final Map<Integer, Object> sqlParams, final String trxName, final Stopwatch durationStopwatch)
	{
		if (!enabled)
		{
			return;
		}

		// Snapshot the duration as soon as possible
		final long durationValue = durationStopwatch.elapsed(TIMEUNIT_Internal);

		//
		// Do not log if we're filtering
		if (!isSqlAccepted(sql))
		{
			return;
		}

		final QueryStatistics queryStatistics = sql2statistics.computeIfAbsent(sql, (sqlKey) -> new QueryStatistics(sqlKey));
		final CountAndDuration duration = queryStatistics.incrementAndGet(durationValue);

		if (traceSqlQueries)
		{
			traceSqlQuery(sql, sqlParams, trxName, duration);
		}
	}

	@Override
	@ManagedOperation(description = "Enables statistics collector")
	public void enable()
	{
		enabled = false;

		reset();
		enabled = true;
		StatementsFactory.instance.enableSqlQueriesTracing(this);
	}

	@Override
	@ManagedOperation(description = "Enables statistics collector and console tracing of executed SQLs")
	public void enableWithSqlTracing()
	{
		enable();

		final boolean traceSqlQueriesOld = traceSqlQueries;
		traceSqlQueries = enabled;

		final int countPrev = traceSqlQueries_Count.get();
		traceSqlQueries_Count.set(0);

		System.err.println("\n");
		System.err.println("*********************************************************************************************");
		System.err.println("*** Enabled SQL Tracing in " + getClass());
		System.err.println("");
		System.err.println("Before calling this method it was " + (traceSqlQueriesOld ? "enabled" : "disabled"));
		System.err.println("Previous SQLs counter was " + countPrev + " and now was reset to ZERO.");
		System.err.println("*********************************************************************************************");
		System.err.println("\n");
	}

	@Override
	@ManagedOperation(description = "Disables statistics collector (and tracing)")
	public void disable()
	{
		enabled = false;

		traceSqlQueries = false;
		StatementsFactory.instance.disableSqlQueriesTracing();
	}

	@Override
	@ManagedOperation(description = "Resets currently collected statistics and counters")
	public void reset()
	{
		sql2statistics.clear();
		validFrom = SystemTime.asDate();
	}

	@Override
	@ManagedOperation(description = "Sets a filter for SQLs which are collected for statistics. NOTE: this is not affecting the SQL tracing.")
	public void setFilterBy(final String filterBy)
	{
		if (Objects.equals(this.filterBy, filterBy))
		{
			//
			// Nothing changed
			return;
		}

		reset();

		this.filterBy = Check.isEmpty(filterBy, true) ? null : filterBy;
	}

	@Override
	@ManagedOperation(description = "Gets the SQL query filter")
	public String getFilterBy()
	{
		return filterBy;
	}

	@Override
	@ManagedOperation(description = "Clears the current SQL query filter, if any")
	public void clearFilterBy()
	{
		setFilterBy(null);
	}

	private final boolean isSqlAccepted(final String sql)
	{
		if (sql == null)
		{
			// shall not happen
			return false;
		}

		final String filterBy = this.filterBy;
		if (filterBy == null)
		{
			return true;
		}

		// TODO filter by RegEx?
		return sql.contains(filterBy);
	}

	@Override
	@ManagedOperation(description = "Gets the timestamp from when we started to collect the statistics")
	public Date getValidFrom()
	{
		return validFrom;
	}

	private final void traceSqlQuery(final String sql, final Map<Integer, Object> sqlParams, final String trxName, final CountAndDuration duration)
	{
		final Thread thread = Thread.currentThread();
		final String threadName = thread.getName();
		final StackTraceElement[] stacktrace = thread.getStackTrace();
		final String durationStr = duration.getLastDurationAsString(TIMEUNIT_Display) + " (Avg. " + duration.getAverageDurationAsString(TIMEUNIT_Display) + ")";

		final int count = traceSqlQueries_Count.incrementAndGet();
		final String prefix = "-- SQL[" + count + "]-" + threadName + "-";
		final String prefixSQL = "                 ";
		final String nl = "\r\n";

		final StringBuilder message = new StringBuilder();

		//
		// Duration
		message.append("-- Duration: " + durationStr);

		//
		// Dump the stacktrace as short as possible
		final String stackTraceStr = Trace.toOneLineStackTraceString(stacktrace);
		message.append(nl + "-- Stacktrace: ").append(stackTraceStr);
		
		//
		// TrxName
		message.append(nl + "-- TrxName: ").append(trxName);

		//
		// SQL query
		if (sql == null)
		{
			message.append(nl + "<NO SQL QUERY>");
		}
		else
		{
			final String sqlNorm = sql
					.trim()
					.replace(nl, "\n").replace("\n", nl); // make sure we always have \r\n
			message.append(nl + sqlNorm);
		}

		//
		// SQL query parameters
		if (sqlParams != null && !sqlParams.isEmpty())
		{
			message.append(nl + "-- Parameters[" + sqlParams.size() + "]: " + sqlParams);
		}

		//
		// Print the message
		System.err.println("");
		System.err.println(prefix + "-begin---------------------------------------------------------------------------");
		System.err.println(prefixSQL + message.toString().replaceAll("\n", prefixSQL));
		System.err.println(prefix + "-end-----------------------------------------------------------------------------");
		System.err.println("");
	}

	/**
	 * @author com.google.common.base.Stopwatch.abbreviate(TimeUnit)
	 */
	private static String abbreviate(final TimeUnit unit)
	{
		switch (unit)
		{
			case NANOSECONDS:
				return "ns";
			case MICROSECONDS:
				return "\u03bcs"; // Î¼s
			case MILLISECONDS:
				return "ms";
			case SECONDS:
				return "s";
			case MINUTES:
				return "min";
			case HOURS:
				return "h";
			case DAYS:
				return "d";
			default:
				throw new AssertionError();
		}
	}

	private static final double convert(final double duration, final TimeUnit fromUnit, final TimeUnit unit)
	{
		final double durationConv = duration / fromUnit.convert(1, unit);
		return durationConv;
	}

	private static final String format(final double duration, final TimeUnit fromUnit, final TimeUnit toUnit)
	{
		final double durationConv = convert(duration, fromUnit, toUnit);
		return String.format("%.4g %s", durationConv, abbreviate(toUnit));
	}

	@Override
	@ManagedOperation(description = "Gets top SQL queries ordered by their total summed executon time (descending)")
	public String[] getTopTotalDurationQueriesAsString()
	{
		return getTopQueriesAsString(Comparator.comparing(QueryStatistics::getTotalDuration));
	}

	@Override
	@ManagedOperation(description = "Gets top SQL queries ordered by their execution count (descending)")
	public String[] getTopCountQueriesAsString()
	{
		return getTopQueriesAsString(Comparator.comparing(QueryStatistics::getCount));
	}

	@Override
	@ManagedOperation(description = "Gets top SQL queries ordered by their average execution time (descending)")
	public String[] getTopAverageDurationQueriesAsString()
	{
		return getTopQueriesAsString(Comparator.comparing(QueryStatistics::getAverageDuration));
	}

	private String[] getTopQueriesAsString(final Comparator<QueryStatistics> comparing)
	{
		return sql2statistics.values()
				.stream()
				.sorted(comparing.reversed())
				.map(stat -> stat.toString())
				.toArray(size -> new String[size]);
	}


	private static final class CountAndDuration
	{
		public static final CountAndDuration ZERO = new CountAndDuration(0, 0, 0);

		private final long count;
		private final long durationTotal;
		private final long durationLast;

		private CountAndDuration(final long count, final long durationTotal, final long durationLast)
		{
			this.count = count;
			this.durationTotal = durationTotal;
			this.durationLast = durationLast;
		}

		@Override
		public String toString()
		{
			return getAverageDurationAsString(TIMEUNIT_Display)
					+ ", Total " + getTotalDurationAsString(TIMEUNIT_Display);
		}

		public String getAverageDurationAsString(final TimeUnit timeUnit)
		{
			return format(getAverageDuration(), TIMEUNIT_Internal, timeUnit) + " / " + count + " executions";
		}

		public String getLastDurationAsString(final TimeUnit timeUnit)
		{
			return format(durationLast, TIMEUNIT_Internal, timeUnit);
		}

		public String getTotalDurationAsString(final TimeUnit timeUnit)
		{
			return format(durationTotal, TIMEUNIT_Internal, timeUnit);
		}

		public CountAndDuration newIncrement(final long durationToAdd)
		{
			if (durationToAdd == 0)
			{
				return this;
			}

			return new CountAndDuration(count + 1, durationTotal + durationToAdd, durationToAdd);
		}

		public long getCount()
		{
			return count;
		}

		private double getAverageDuration()
		{
			if (count == 0)
			{
				return count;
			}

			return durationTotal / count;
		}

		private long getTotalDuration()
		{
			return durationTotal;
		}
	}

	private static final class QueryStatistics
	{
		private final String sql;
		private final AtomicReference<CountAndDuration> countAndDurationRef;

		public QueryStatistics(final String sql)
		{
			super();
			this.sql = sql;
			this.countAndDurationRef = new AtomicReference<>(CountAndDuration.ZERO);
		}

		@Override
		public String toString()
		{
			return "SQL: " + sql
					+ "\n-- " + countAndDurationRef.get();
		}

		public CountAndDuration incrementAndGet(final long duration)
		{
			return countAndDurationRef.updateAndGet(countAndDuration -> countAndDuration.newIncrement(duration));
		}

		@SuppressWarnings("unused")
		public String getSql()
		{
			return sql;
		}

		public long getCount()
		{
			return countAndDurationRef.get().getCount();
		}

		public long getTotalDuration()
		{
			return countAndDurationRef.get().getTotalDuration();
		}

		private double getAverageDuration()
		{
			return countAndDurationRef.get().getAverageDuration();
		}
	}
}
