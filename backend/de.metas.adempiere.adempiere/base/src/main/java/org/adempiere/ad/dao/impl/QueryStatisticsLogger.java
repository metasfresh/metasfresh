package org.adempiere.ad.dao.impl;

import com.google.common.base.Stopwatch;
import de.metas.dao.sql.SqlParamsInliner;
import de.metas.logging.LogManager;
import de.metas.monitoring.adapter.NoopPerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryStatisticsCollector;
import org.adempiere.ad.dao.IQueryStatisticsLogger;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.sql.impl.StatementsFactory;
import org.compiere.SpringContextHolder;
import org.compiere.util.CStatementVO;
import org.compiere.util.Trace;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class QueryStatisticsLogger implements IQueryStatisticsLogger, IQueryStatisticsCollector
{
	private static final Logger logger = LogManager.getLogger(QueryStatisticsLogger.class);

	private static final TimeUnit TIMEUNIT_Internal = TimeUnit.NANOSECONDS;
	private static final TimeUnit TIMEUNIT_Display = TimeUnit.MILLISECONDS;
	private static final String nl = "\n";
	private static final SqlParamsInliner SQL_PARAMS_INLINER = SqlParamsInliner.builder().failOnError(false).build();

	private final ConcurrentHashMap<String, QueryStatistics> sql2statistics = new ConcurrentHashMap<>();
	private String filterBy = null;

	private boolean traceSqlQueries = false;
	private static final AtomicInteger traceSqlQueries_Count = new AtomicInteger(0);

	private static final boolean logToSystemError = Boolean.getBoolean(SYSTEM_PROPERTY_LOG_TO_SYSTEM_ERROR);

	private PerformanceMonitoringService _performanceMonitoringService;
	private boolean recordWithMicrometerEnabled = false;
	private static final PerformanceMonitoringService.Metadata PM_METADATA_COLLECT =
			PerformanceMonitoringService.Metadata
					.builder()
					.className("QueryStatisticLogger")
					.type(PerformanceMonitoringService.Type.DB)
					.functionName("recordExecutedSQLsWithMicrometer")
					.build();

	public QueryStatisticsLogger()
	{
	}

	private boolean isDisabled() {return !traceSqlQueries && !recordWithMicrometerEnabled;}

	private void logMessage(final String message)
	{
		if (logToSystemError)
		{
			System.err.println(message);
		}
		else
		{
			logger.warn(message);
		}
	}

	@Override
	public void collect(final CStatementVO vo, final Stopwatch duration)
	{
		if (isDisabled())
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
		if (isDisabled())
		{
			return;
		}

		collect(sql, null, "?", duration);
	}

	private void collect(final String sql, final Map<Integer, Object> sqlParams, final String trxName, final Stopwatch durationStopwatch)
	{
		if (isDisabled())
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

		if (traceSqlQueries)
		{
			final QueryStatistics queryStatistics = sql2statistics.computeIfAbsent(sql, QueryStatistics::new);
			final CountAndDuration duration = queryStatistics.incrementAndGet(durationValue);
			traceSqlQuery(sql, sqlParams, trxName, duration);
		}

		if (recordWithMicrometerEnabled)
		{
			performanceMonitoringService().recordElapsedTime(durationValue, TIMEUNIT_Internal, PM_METADATA_COLLECT);
		}
	}

	private PerformanceMonitoringService performanceMonitoringService()
	{
		PerformanceMonitoringService performanceMonitoringService = this._performanceMonitoringService;
		if (performanceMonitoringService == null)
		{
			performanceMonitoringService = SpringContextHolder.instance.getBeanOr(PerformanceMonitoringService.class, null);
			if (performanceMonitoringService != null)
			{
				this._performanceMonitoringService = performanceMonitoringService;
			}
			else
			{
				performanceMonitoringService = NoopPerformanceMonitoringService.INSTANCE;
				// don't set this._performanceMonitoringService
			}
		}
		return performanceMonitoringService;
	}

	@Override
	public void enableSqlTracing()
	{
		reset();
		enableStatementTracingIfNeeded();

		final boolean traceSqlQueriesOld = traceSqlQueries;
		traceSqlQueries = true;

		final int countPrev = traceSqlQueries_Count.get();
		traceSqlQueries_Count.set(0);

		logMessage("\n\n"
				+ "\n*********************************************************************************************"
				+ "\n*** Enabled SQL Tracing in " + getClass()
				+ "\n"
				+ "\nBefore calling this method it was " + (traceSqlQueriesOld ? "enabled" : "disabled")
				+ "\nPrevious SQLs counter was " + countPrev + " and now was reset to ZERO."
				+ "\n*********************************************************************************************"
				+ "\n\n");
	}

	@Override
	public void disableSqlTracing()
	{
		traceSqlQueries = false;
		disableStatementTracingIfNeeded();
	}

	private void enableStatementTracingIfNeeded()
	{
		if (!isDisabled())
		{
			StatementsFactory.instance.enableSqlQueriesTracing(this);
		}
	}

	private void disableStatementTracingIfNeeded()
	{
		if (isDisabled())
		{
			StatementsFactory.instance.disableSqlQueriesTracing();
		}
	}

	private void reset()
	{
		sql2statistics.clear();
	}

	@Override
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
	public void clearFilterBy()
	{
		setFilterBy(null);
	}

	private boolean isSqlAccepted(final String sql)
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

	private void traceSqlQuery(final String sql, final Map<Integer, Object> sqlParams, final String trxName, final CountAndDuration duration)
	{
		final Thread thread = Thread.currentThread();
		final String threadName = thread.getName();
		final StackTraceElement[] stacktrace = thread.getStackTrace();
		final String durationStr = duration.getLastDurationAsString(TIMEUNIT_Display) + " (Avg. " + duration.getAverageDurationAsString(TIMEUNIT_Display) + ")";
		final String trxNameInfo = extractTrxNameInfo(trxName);

		final int count = traceSqlQueries_Count.incrementAndGet();
		final String prefix = "-- SQL[" + count + "]-" + threadName + "-";
		final String prefixSQL = "                 ";

		final StringBuilder message = new StringBuilder();

		//
		// Duration
		message.append("-- Duration: ").append(durationStr);

		//
		// Dump the stacktrace as short as possible
		final String stackTraceStr = Trace.toOneLineStackTraceString(stacktrace);
		message.append(nl + "-- Stacktrace: ").append(stackTraceStr);

		//
		// TrxName
		message.append(nl + "-- TrxName: ").append(trxNameInfo);

		//
		// SQL query
		if (sql == null)
		{
			message.append(nl + "<NO SQL QUERY>");
		}
		else
		{
			String sqlNorm = normalizeAndInlineSqlParams(sql, sqlParams);
			message.append(nl).append(sqlNorm);
		}

		//
		// Print the message
		logMessage("\n"
				+ "\n" + prefix + "-begin---------------------------------------------------------------------------"
				+ "\n" + prefixSQL + message.toString().replaceAll("\n", "\n" + prefixSQL)
				+ "\n" + prefix + "-end-----------------------------------------------------------------------------"
				+ "\n");

	}

	private static String normalizeAndInlineSqlParams(@NonNull final String sql, @Nullable final Map<Integer, Object> sqlParams)
	{
		String sqlNorm = sql
				.trim()
				.replace("\r\n", "\n").replace("\n", nl); // make sure we always have `nl`

		if (sqlParams != null && !sqlParams.isEmpty())
		{
			sqlNorm = SQL_PARAMS_INLINER.inline(sqlNorm, sqlParams.values().toArray());
		}
		return sqlNorm;
	}

	private String extractTrxNameInfo(final String trxName)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		if (ITrx.TRXNAME_ThreadInherited.equals(trxName))
		{
			final ITrx trx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
			final String trxNameEffective = trxManager.isNull(trx) ? "out-of-transaction" : trx.getTrxName();
			return trxName + " (resolved as: " + trxNameEffective + ")";
		}
		else if (trxName == null || trxManager.isNull(trxName))
		{
			return "out-of-transaction";
		}
		else
		{
			return trxName;
		}
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
				return "μs";
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

	private static double convert(final double duration, final TimeUnit fromUnit, final TimeUnit unit)
	{
		return duration / fromUnit.convert(1, unit);
	}

	@SuppressWarnings("SameParameterValue")
	private static String format(final double duration, final TimeUnit fromUnit, final TimeUnit toUnit)
	{
		final double durationConv = convert(duration, fromUnit, toUnit);
		return String.format("%.4g %s", durationConv, abbreviate(toUnit));
	}

	@Override
	public String[] getTopTotalDurationQueriesAsString()
	{
		return getTopQueriesAsString(Comparator.comparing(QueryStatistics::getTotalDuration));
	}

	@Override
	public String[] getTopCountQueriesAsString()
	{
		return getTopQueriesAsString(Comparator.comparing(QueryStatistics::getCount));
	}

	@Override
	public String[] getTopAverageDurationQueriesAsString()
	{
		return getTopQueriesAsString(Comparator.comparing(QueryStatistics::getAverageDuration));
	}

	@Override
	public void enableRecordWithMicrometer()
	{
		recordWithMicrometerEnabled = true;
		enableStatementTracingIfNeeded();
	}

	@Override
	public void disableRecordWithMicrometer()
	{
		recordWithMicrometerEnabled = false;
		disableStatementTracingIfNeeded();
	}

	private String[] getTopQueriesAsString(final Comparator<QueryStatistics> comparing)
	{
		return sql2statistics.values()
				.stream()
				.sorted(comparing.reversed())
				.map(QueryStatistics::toString)
				.toArray(String[]::new);
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

			return (double)durationTotal / count;
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
