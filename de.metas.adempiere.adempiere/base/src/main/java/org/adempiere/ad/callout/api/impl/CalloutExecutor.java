package org.adempiere.ad.callout.api.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutFactory;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.api.TableCalloutsMap;
import org.adempiere.ad.callout.exceptions.CalloutException;
import org.adempiere.ad.callout.exceptions.CalloutExecutionException;
import org.adempiere.ad.callout.exceptions.CalloutInitException;
import org.adempiere.ad.callout.spi.ICalloutProvider;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;

import de.metas.logging.LogManager;

public final class CalloutExecutor implements ICalloutExecutor
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private static final Logger logger = LogManager.getLogger(CalloutExecutor.class);

	//
	// Configuration (sharable data)
	private final String tableName;
	private final TableCalloutsMap tableCalloutsMap;
	/** Set of callout IDs which are banned from execution because they failed on initialization */
	private final Set<String> executionBlackListIds;

	//
	// Current state data (don't share between CalloutExecutor instances)
	/** A set of callouts which are currently running */
	private final Set<ICalloutInstance> activeCalloutInstances = new HashSet<>();
	/** When enabled, contains a set of already executed callouts */
	private volatile Set<ICalloutInstance> executedCalloutsTrace = null;

	private CalloutExecutor(
			final String tableName,
			final TableCalloutsMap tableCalloutsMap,
			final Set<String> executionBlackListIds)
	{
		super();

		Check.assumeNotEmpty(tableName, "tableName is not empty");
		this.tableName = tableName;

		Check.assumeNotNull(tableCalloutsMap, "Parameter tableCalloutsMap is not null");
		this.tableCalloutsMap = tableCalloutsMap;

		if (executionBlackListIds == null)
		{
			this.executionBlackListIds = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
		}
		else
		{
			this.executionBlackListIds = executionBlackListIds;
		}
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("tableName", tableName)
				.add("activeCalloutInstances", activeCalloutInstances.isEmpty() ? null : activeCalloutInstances)
				.add("backList", executionBlackListIds.isEmpty() ? null : executionBlackListIds)
				.toString();
	}

	@Override
	public void execute(final ICalloutField field)
	{
		if (!isEligibleForExecuting(field))
		{
			logger.trace("Skip executing callouts for {} because it's not eligible", field);
			return;
		}

		final List<ICalloutInstance> callouts = tableCalloutsMap.getColumnCallouts(field.getColumnName());
		if (callouts.isEmpty())
		{
			logger.trace("Skip executing callouts for {} because there are none", field);
			return;
		}

		for (final ICalloutInstance callout : callouts)
		{
			execute(callout, field);
		}
	}

	@Override
	public void executeAll(final Function<String, ICalloutField> calloutFieldsSupplier)
	{
		Check.assumeNotNull(calloutFieldsSupplier, "Parameter calloutFieldsSupplier is not null");

		logger.trace("executeAll: Begin executing all callouts for {} using fields supplier: {}", this, calloutFieldsSupplier);

		//
		// Enabled executed callouts tracing.
		// We will track all indirectly executed callouts and we won't execute them again here.
		final Set<ICalloutInstance> executedCalloutsTrace = new HashSet<>();
		if (this.executedCalloutsTrace != null)
		{
			throw new IllegalStateException("executedCalloutsTrace is not null. Is there another executeAll currently running?"); // shall not happen
		}
		this.executedCalloutsTrace = executedCalloutsTrace;

		//
		// Statistics
		StatisticsCollector statisticsCollector = logger.isTraceEnabled() ? new StatisticsCollector() : null;

		try
		{
			for (final Map.Entry<String, Collection<ICalloutInstance>> columnNameAndCallouts : tableCalloutsMap.getColumnNamesAndCalloutsEntries())
			{
				//
				// Get the callout field from supplier
				final String columnName = columnNameAndCallouts.getKey();
				final ICalloutField field = calloutFieldsSupplier.apply(columnName);
				if (field == null)
				{
					statisticsCollector = statisticsCollector == null ? null : statisticsCollector.collectFieldSkipped(columnName, "supplied field was null");
					continue;
				}
				if (!isEligibleForExecuting(field))
				{
					statisticsCollector = statisticsCollector == null ? null : statisticsCollector.collectFieldSkipped(field, "not eligible");
					continue;
				}

				//
				// Execute all callouts for current callout field
				final Collection<ICalloutInstance> callouts = columnNameAndCallouts.getValue();
				for (final ICalloutInstance callout : callouts)
				{
					// Optimization: don't fire this callout if it was indirectly already fired.
					if (executedCalloutsTrace.contains(callout))
					{
						statisticsCollector = statisticsCollector == null ? null : statisticsCollector.collectCalloutSkipped(columnName, callout, "it was already executed");
						continue;
					}

					final Stopwatch calloutDuration = statisticsCollector == null ? null : Stopwatch.createStarted();

					execute(callout, field);

					statisticsCollector = statisticsCollector == null ? null : statisticsCollector.collectCalloutExecuted(columnName, callout, calloutDuration);
				}
			}
		}
		finally
		{
			// Disable executed callouts tracing
			this.executedCalloutsTrace = null;

			logger.trace("executeAll: Finished executing all callouts for {} using fields supplier: {}", this, calloutFieldsSupplier);
			if (statisticsCollector != null)
			{
				statisticsCollector.stop();
				logger.trace("{}", statisticsCollector.getSummary());
			}
		}
	}

	private boolean isEligibleForExecuting(final ICalloutField field)
	{
		if (field == null)
		{
			// shall not happen
			logger.warn("field is null", new Exception("TRACE"));
			return false;
		}

		if (!tableName.equals(field.getTableName()))
		{
			logger.warn("Field {} is not handled by {} because it's TableName does not match", field, this, new Exception("TRACE"));
			return false;
		}

		if (!field.isTriggerCalloutAllowed())
		{
			logger.trace("Field {} does not allow callouts triggering", field);
			return false;
		}

		return true;
	}

	private void execute(final ICalloutInstance callout, final ICalloutField field)
	{
		final String calloutId = callout.getId();

		if (executionBlackListIds.contains(calloutId))
		{
			logger.trace("Skip executing callout {} because it's on backlist", callout);
			return;
		}

		try
		{
			final boolean added = activeCalloutInstances.add(callout);
			// detect infinite loop
			if (!added)
			{
				logger.debug("Skip callout {} because is already active", callout);
				return;
			}

			if (logger.isDebugEnabled())
			{
				logger.debug("Executing callout {}: {}={} - old={}", callout, field, field.getValue(), field.getOldValue());
			}

			callout.execute(this, field);

			// Trace executed callout if required
			final Set<ICalloutInstance> executedCalloutsTrace = this.executedCalloutsTrace;
			if (executedCalloutsTrace != null)
			{
				executedCalloutsTrace.add(callout);
			}
		}
		catch (final CalloutInitException e)
		{
			logger.error("Callout {} failed with init error on execution. Discarding the callout from next calls and propagating the exception", callout, e);
			executionBlackListIds.add(calloutId);
			throw e.setCalloutExecutorIfAbsent(this)
					.setFieldIfAbsent(field)
					.setCalloutInstanceIfAbsent(callout);
		}
		catch (final CalloutException e)
		{
			throw e.setCalloutExecutorIfAbsent(this)
					.setFieldIfAbsent(field)
					.setCalloutInstanceIfAbsent(callout);
		}
		catch (final Exception e)
		{
			throw CalloutExecutionException.of(e)
					.setCalloutExecutor(this)
					.setField(field)
					.setCalloutInstance(callout);
		}
		finally
		{
			activeCalloutInstances.remove(callout);
		}
	}

	@Override
	public int getActiveCalloutInstancesCount()
	{
		return activeCalloutInstances.size();
	}

	@Override
	public boolean hasCallouts(final ICalloutField field)
	{
		if (field == null)
		{
			return false;
		}

		return tableCalloutsMap.hasColumnCallouts(field.getColumnName(), executionBlackListIds);
	}

	public ICalloutExecutor newInstanceSharingMasterData()
	{
		return new CalloutExecutor(tableName, tableCalloutsMap, executionBlackListIds);
	}

	public static final class Builder
	{
		private String tableName;
		private ICalloutProvider _calloutProvider;

		private Builder()
		{
			super();
		}

		public CalloutExecutor build()
		{
			final ICalloutProvider calloutFactory = getCalloutProvider();
			final Properties ctx = Env.getCtx(); // FIXME: get rid of ctx!
			final TableCalloutsMap tableCalloutsMap = calloutFactory.getCallouts(ctx, tableName);
			if (tableCalloutsMap == null)
			{
				throw new NullPointerException("Got null table callouts map from " + calloutFactory + " for AD_Table_ID=" + tableName);
			}
			final Set<String> executionBlackListIds = null;
			return new CalloutExecutor(tableName, tableCalloutsMap, executionBlackListIds);
		}

		public Builder setTableName(final String tableName)
		{
			this.tableName = tableName;
			return this;
		}

		public Builder setCalloutProvider(final ICalloutProvider calloutProvider)
		{
			_calloutProvider = calloutProvider;
			return this;
		}

		private ICalloutProvider getCalloutProvider()
		{
			if (_calloutProvider == null)
			{
				return getDefaultCalloutProvider();
			}
			else
			{
				return _calloutProvider;
			}
		}

		public ICalloutProvider getDefaultCalloutProvider()
		{
			return Services.get(ICalloutFactory.class).getProvider();
		}
	}

	/**
	 * Internal object for collecting statistics when we run {@link CalloutExecutor#executeAll(Function)}.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	private static final class StatisticsCollector
	{
		private final Stopwatch durationTotal = Stopwatch.createStarted();
		private int countFieldsSkipped = 0;
		private int countCalloutsExecuted = 0;
		private int countCalloutsSkipped = 0;
		private final LinkedHashMap<ICalloutInstance, CalloutStatisticsEntry> calloutStatistics = new LinkedHashMap<>();

		private StatisticsCollector()
		{
			super();
		}

		public String getSummary()
		{
			final StringBuilder sb = new StringBuilder();
			sb.append("\n Executed " + countCalloutsExecuted + " callouts in " + durationTotal);
			if (countCalloutsSkipped > 0)
			{
				sb.append("\n Skipped " + countCalloutsSkipped + " callouts which were called indirectly");
			}
			if (countFieldsSkipped > 0)
			{
				sb.append("\n Skipped " + countFieldsSkipped + " fields because they are no eligible");
			}

			final Collection<CalloutStatisticsEntry> calloutStatsEntries = calloutStatistics.values();
			if (!calloutStatsEntries.isEmpty())
			{
				sb.append("\n Following callouts were executed: ");
				calloutStatsEntries
						.stream()
						.sorted((s1, s2) -> (int)(s2.getDurationNanos() - s1.getDurationNanos())) // descending by duration
						.forEach((s) -> sb.append("\n * " + s));
			}

			return sb.toString();
		}

		public StatisticsCollector stop()
		{
			durationTotal.stop();
			return this;
		}

		public StatisticsCollector collectFieldSkipped(final String columnName, final String reason)
		{
			logger.trace("Skip executing all callouts for {} because {}", columnName, reason);
			countFieldsSkipped++;
			return this;
		}

		public StatisticsCollector collectFieldSkipped(final ICalloutField calloutField, final String reason)
		{
			logger.trace("Skip executing all callouts for {} because {}", calloutField, reason);
			countFieldsSkipped++;
			return this;
		}

		public StatisticsCollector collectCalloutSkipped(final String columnName, final ICalloutInstance callout, final String reason)
		{
			logger.trace("Skip executing callout {}: {} because {}", columnName, callout, reason);
			countCalloutsSkipped++;
			calloutStatistics.put(callout, CalloutStatisticsEntry.skipped(columnName, callout, reason));
			return this;
		}

		public StatisticsCollector collectCalloutExecuted(final String columnName, final ICalloutInstance callout, final Stopwatch calloutDuration)
		{
			countCalloutsExecuted++;
			calloutStatistics.put(callout, CalloutStatisticsEntry.executed(columnName, callout, calloutDuration));
			return this;
		}

		/**
		 * Per {@link ICalloutInstance} statistics.
		 *
		 * @author metas-dev <dev@metasfresh.com>
		 *
		 */
		private static final class CalloutStatisticsEntry
		{
			public static final CalloutStatisticsEntry executed(final String columnName, final ICalloutInstance callout, final Stopwatch calloutDuration)
			{
				final long durationNanos;
				final String durationStr;
				if (calloutDuration != null)
				{
					calloutDuration.stop();
					durationStr = calloutDuration.toString();
					durationNanos = calloutDuration.elapsed(TimeUnit.NANOSECONDS);
				}
				else
				{
					durationStr = "???";
					durationNanos = Long.MAX_VALUE; // just to jump in developer's eye!
				}
				final String summary = "EXECUTED " + columnName + " - " + durationStr + " - " + callout;
				return new CalloutStatisticsEntry(summary, durationNanos);
			}

			public static final CalloutStatisticsEntry skipped(final String columnName, final ICalloutInstance callout, final String skipReason)
			{
				final String summary = "SKIPPED  " + columnName + " - " + skipReason + " - " + callout;
				return new CalloutStatisticsEntry(summary, 0);
			}

			private final String summary;
			private final long durationNanos;

			private CalloutStatisticsEntry(final String summary, final long durationNanos)
			{
				super();
				this.summary = summary;
				this.durationNanos = durationNanos;
			}

			@Override
			public String toString()
			{
				return summary;
			}

			public long getDurationNanos()
			{
				return durationNanos;
			}
		}
	}
}
