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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.IntSupplier;

import javax.annotation.Nullable;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutFactory;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.api.TableCalloutsMap;
import org.adempiere.ad.callout.exceptions.CalloutException;
import org.adempiere.ad.callout.exceptions.CalloutExecutionException;
import org.adempiere.ad.callout.exceptions.CalloutInitException;
import org.adempiere.ad.callout.spi.CompositeCalloutProvider;
import org.adempiere.ad.callout.spi.ICalloutProvider;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;

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
	private volatile Set<ArrayKey> executedCalloutsTrace = null;

	private CalloutStatisticsEntry statisticsCollector;

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
		final Set<ArrayKey> executedCalloutsTrace = new HashSet<>();
		if (this.executedCalloutsTrace != null)
		{
			throw new IllegalStateException("executedCalloutsTrace is not null. Is there another executeAll currently running?"); // shall not happen
		}
		this.executedCalloutsTrace = executedCalloutsTrace;

		//
		// Statistics
		CalloutStatisticsEntry statisticsCollector = logger.isTraceEnabled() ? CalloutStatisticsEntry.newRoot() : null;
		this.statisticsCollector = statisticsCollector;

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
					statisticsCollector = statisticsCollector == null ? null : statisticsCollector.childSkipped(columnName, "supplied field was null");
					continue;
				}
				if (!isEligibleForExecuting(field))
				{
					statisticsCollector = statisticsCollector == null ? null : statisticsCollector.childSkipped(field, "not eligible");
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
						statisticsCollector = statisticsCollector == null ? null : statisticsCollector.childSkipped(field, callout, "it was already executed");
						continue;
					}

					execute(callout, field);
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
				statisticsCollector.setStatusExecuted();
				this.statisticsCollector = null;
				logger.trace("Execution statistics: {}", statisticsCollector.getSummary());
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

		if (tableName != ICalloutProvider.ANY_TABLE && !tableName.equals(field.getTableName()))
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

		//
		// Statistics
		final boolean tracing = logger.isTraceEnabled();
		final CalloutStatisticsEntry statisticsCollectorPrevious;
		CalloutStatisticsEntry statisticsCollectorCurrent;
		if (tracing)
		{
			statisticsCollectorPrevious = this.statisticsCollector;
			this.statisticsCollector = statisticsCollectorCurrent = CalloutStatisticsEntry.child(statisticsCollectorPrevious, field, callout);
		}
		else
		{
			statisticsCollectorPrevious = null;
			statisticsCollectorCurrent = null;
		}

		boolean calloutAddedActivesList = false;
		try
		{
			if (executionBlackListIds.contains(calloutId))
			{
				statisticsCollectorCurrent = statisticsCollectorCurrent == null ? null : statisticsCollectorCurrent.setStatusSkipped("blacklist");
				return;
			}

			//
			// Add the callout to active running callouts list
			calloutAddedActivesList = activeCalloutInstances.add(callout);
			if (!calloutAddedActivesList)
			{
				// callout is already running => don't run it again because it will introduce a loop
				statisticsCollectorCurrent = statisticsCollectorCurrent == null ? null : statisticsCollectorCurrent.setStatusSkipped("already active", "active callouts: " + activeCalloutInstances);
				return;
			}

			statisticsCollectorCurrent = statisticsCollectorCurrent == null ? null : statisticsCollectorCurrent.setStatusStarted();

			//
			// Actually execute the callout
			callout.execute(this, field);

			statisticsCollectorCurrent = statisticsCollectorCurrent == null ? null : statisticsCollectorCurrent.setStatusExecuted();

			//
			// Trace executed callout if required
			final Set<ArrayKey> executedCalloutsTrace = this.executedCalloutsTrace;
			if (executedCalloutsTrace != null)
			{
				executedCalloutsTrace.add(Util.mkKey(field.getColumnName(), callout));
			}
		}
		catch (final CalloutInitException e)
		{
			logger.error("Callout {} failed with init error on execution. Discarding the callout from next calls and propagating the exception", callout, e);
			executionBlackListIds.add(calloutId);
			
			statisticsCollectorCurrent = statisticsCollectorCurrent == null ? null : statisticsCollectorCurrent.setStatusFailed(e);
			
			throw e.setCalloutExecutorIfAbsent(this)
					.setFieldIfAbsent(field)
					.setCalloutInstanceIfAbsent(callout);
		}
		catch (final CalloutException e)
		{
			statisticsCollectorCurrent = statisticsCollectorCurrent == null ? null : statisticsCollectorCurrent.setStatusFailed(e);
			
			throw e.setCalloutExecutorIfAbsent(this)
					.setFieldIfAbsent(field)
					.setCalloutInstanceIfAbsent(callout);
		}
		catch (final Exception e)
		{
			statisticsCollectorCurrent = statisticsCollectorCurrent == null ? null : statisticsCollectorCurrent.setStatusFailed(e);
			
			throw CalloutExecutionException.of(e)
					.setCalloutExecutor(this)
					.setField(field)
					.setCalloutInstance(callout);
		}
		finally
		{
			//
			// Statistics
			this.statisticsCollector = statisticsCollectorPrevious;
			if(statisticsCollectorCurrent != null && statisticsCollectorPrevious == null)
			{
				logger.trace("Executed callout {} for {}", callout, field);
				logger.trace("Execution statistics: {}", statisticsCollectorCurrent.getSummary());
			}

			//
			// Remove callout from currently running callouts list
			if(calloutAddedActivesList)
			{
				activeCalloutInstances.remove(callout);
			}
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

	@Override
	public ICalloutExecutor newInstanceSharingMasterData()
	{
		return new CalloutExecutor(tableName, tableCalloutsMap, executionBlackListIds);
	}

	public static final class Builder
	{
		private String tableName;
		private ICalloutProvider _calloutProvider = null;

		private Builder()
		{
			super();
		}

		public ICalloutExecutor build()
		{
			final ICalloutProvider calloutFactory = getCalloutProviderEffective();
			final Properties ctx = Env.getCtx(); // FIXME: get rid of ctx or user server's ctx
			final TableCalloutsMap tableCalloutsMap = calloutFactory.getCallouts(ctx, tableName);
			if (tableCalloutsMap == null)
			{
				throw new NullPointerException("Got null table callouts map from " + calloutFactory + " for AD_Table_ID=" + tableName);
			}
			if(tableCalloutsMap.isEmpty())
			{
				return NullCalloutExecutor.instance;
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
		
		public Builder addCalloutProvider(final ICalloutProvider calloutProviderToAdd)
		{
			_calloutProvider = CompositeCalloutProvider.compose(_calloutProvider, calloutProviderToAdd);
			return this;
		}
		
		public Builder addDefaultCalloutProvider()
		{
			addCalloutProvider(getDefaultCalloutProvider());
			return this;
		}

		private ICalloutProvider getCalloutProviderEffective()
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

		private ICalloutProvider getDefaultCalloutProvider()
		{
			return Services.get(ICalloutFactory.class).getProvider();
		}
	}

	/**
	 * Internal object for collecting statistics when we run callouts.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	private static final class CalloutStatisticsEntry
	{
		public static final CalloutStatisticsEntry newRoot()
		{
			return new CalloutStatisticsEntry()
					.setStatusStarted();
		}

		public static final CalloutStatisticsEntry child(@Nullable final CalloutStatisticsEntry parent, final ICalloutField field, final ICalloutInstance callout)
		{
			if (parent == null)
			{
				final IntSupplier indexSupplier = null; // N/A
				return new CalloutStatisticsEntry(indexSupplier, field, callout);
			}
			else
			{
				return parent.getCreateChild(field, callout);
			}
		}

		private final int index;
		private final IntSupplier indexSupplier;
		private final String columnName;
		private final ICalloutField calloutField;
		private final ICalloutInstance callout;

		//
		// Node level info
		private static enum Status
		{
			Unknown, Started, Executed, Skipped, Failed
		};

		private Status status = Status.Unknown;
		private String skipReasonSummary;
		private String skipReasonDetails;
		private static final Object FIELD_VALUE_UNKNOWN = "<UNKNOWN>";
		private Object fieldValueOnStart = FIELD_VALUE_UNKNOWN;
		private Object fieldValueOldOnStart = FIELD_VALUE_UNKNOWN;
		private Throwable exception;
		private String _nodeSummary; // lazy
		//
		private Stopwatch duration = null;

		//
		// Children info
		private final LinkedHashMap<ArrayKey, CalloutStatisticsEntry> children = new LinkedHashMap<>();
		private int countChildFieldsSkipped = 0;

		private CalloutStatisticsEntry(final IntSupplier indexSupplier, final ICalloutField field, final ICalloutInstance callout)
		{
			super();
			
			this.indexSupplier = indexSupplier != null ? indexSupplier : new AtomicInteger(1)::getAndIncrement;
			index = this.indexSupplier.getAsInt();
			
			this.calloutField = field;
			this.columnName = calloutField == null ? null : calloutField.getColumnName();
			
			this.callout = callout;
		}

		/** Root constructor */
		private CalloutStatisticsEntry()
		{
			this(
					(IntSupplier)null // indexSupplier
					, (ICalloutField)null // field
					, null // callout
			);
		}

		@Override
		public String toString()
		{
			return getNodeSummary();
		}

		public String getSummary()
		{
			final String linePrefix = "\n";
			final boolean showOverallSummary = true;
			return getSummary(linePrefix, showOverallSummary);
		}

		private String getSummary(final String linePrefix, final boolean showOverallSummary)
		{
			final StringBuilder sb = new StringBuilder();

			if (showOverallSummary)
			{
				if (countChildFieldsSkipped > 0)
				{
					sb.append(linePrefix + "Skipped " + countChildFieldsSkipped + " fields because they are no eligible");
				}
			}

			//
			// Node summary entry
			sb.append(linePrefix + " * " + getNodeSummary());

			//
			// Child summary
			final Collection<CalloutStatisticsEntry> childrenList = children.values();
			if (!childrenList.isEmpty())
			{
				final boolean childShowOverallSummary = false;
				final String childLinePrefix = linePrefix + "  ";
				childrenList
						.stream()
						.forEach((s) -> sb.append(s.getSummary(childLinePrefix, childShowOverallSummary)));
			}

			return sb.toString();
		}

		public String getNodeSummary()
		{
			if (_nodeSummary == null)
			{
				_nodeSummary = buildSummary();
			}
			return _nodeSummary;
		}

		private String buildSummary()
		{
			final StringBuilder sb = new StringBuilder();

			// Index
			sb.append("(").append(String.format("%3d", index)).append(")");

			// Status
			sb.append(" ").append(Strings.padStart(status.toString(), 8, ' '));
			
			// ColumnName
			sb.append(" ").append(columnName != null ? columnName : "(ROOT)");

			// Duration
			final Stopwatch duration = this.duration;
			if (duration != null)
			{
				sb.append(" - ").append(duration);
				if (duration.isRunning())
				{
					sb.append("(!)");
				}
			}

			// Skip reason summary
			if (status == Status.Skipped && !Check.isEmpty(skipReasonSummary, true))
			{
				sb.append(" - ").append(skipReasonSummary);
			}

			// Callout
			if (callout != null)
			{
				sb.append(" - ").append(callout);
			}

			// Skip reason details
			if (status == Status.Skipped && !Check.isEmpty(skipReasonDetails, true))
			{
				sb.append(" - ").append(skipReasonDetails);
			}
			
			if(status == Status.Failed && exception != null)
			{
				sb.append(" - Exception: ").append(AdempiereException.extractMessage(exception));
			}

			//
			return sb.toString();
		}

		public CalloutStatisticsEntry setStatusStarted()
		{
			status = Status.Started;
			fieldValueOnStart = calloutField == null ? FIELD_VALUE_UNKNOWN : calloutField.getValue();
			fieldValueOldOnStart = calloutField == null ? FIELD_VALUE_UNKNOWN : calloutField.getValue();
			duration = Stopwatch.createStarted();
			_nodeSummary = null; // reset
			
			logger.debug("Executing callout {}: {}={} - old={}", callout, calloutField, fieldValueOnStart, fieldValueOldOnStart);

			return this;
		}

		public CalloutStatisticsEntry setStatusExecuted()
		{
			duration = duration == null ? null : duration.stop();

			if (status != Status.Started)
			{
				logger.warn("Callout statistics internal bug: changing status from {} to {} for {}", status, Status.Executed, this);
			}

			status = Status.Executed;
			_nodeSummary = null; // reset

			return this;
		}

		public CalloutStatisticsEntry setStatusSkipped(final String skipReasonSummary)
		{
			final String skipReasonDetails = null;
			return setStatusSkipped(skipReasonSummary, skipReasonDetails);
		}

		public CalloutStatisticsEntry setStatusSkipped(final String skipReasonSummary, final String skipReasonDetails)
		{
			if (status != Status.Unknown)
			{
				logger.warn("Callout statistics internal bug: changing status from {} to {} for {}", status, Status.Skipped, this);
			}

			status = Status.Skipped;
			this.skipReasonSummary = skipReasonSummary;
			this.skipReasonDetails = skipReasonDetails;
			_nodeSummary = null; // reset

			return this;
		}

		public CalloutStatisticsEntry setStatusFailed(final Throwable exception)
		{
			duration = duration == null ? null : duration.stop();
			this.exception = exception;

			status = Status.Failed;
			_nodeSummary = null; // reset

			return this;
		}

		public CalloutStatisticsEntry getCreateChild(final ICalloutField field, final ICalloutInstance callout)
		{
			final ArrayKey key = Util.mkKey(field.getColumnName(), callout);
			return children.computeIfAbsent(key, (theKey) -> new CalloutStatisticsEntry(indexSupplier, field, callout));
		}

		public CalloutStatisticsEntry childSkipped(final String columnName, final String reason)
		{
			logger.trace("Skip executing all callouts for {} because {}", columnName, reason);
			countChildFieldsSkipped++;
			return this;
		}

		public CalloutStatisticsEntry childSkipped(final ICalloutField field, final String reason)
		{
			logger.trace("Skip executing all callouts for {} because {}", field, reason);
			countChildFieldsSkipped++;
			return this;
		}

		public CalloutStatisticsEntry childSkipped(final ICalloutField field, final ICalloutInstance callout, final String reasonSummary)
		{
			final String reasonDetails = null;
			return childSkipped(field, callout, reasonSummary, reasonDetails);
		}

		public CalloutStatisticsEntry childSkipped(final ICalloutField field, final ICalloutInstance callout, final String reasonSummary, final String reasonDetails)
		{
			logger.trace("Skip executing callout {} for field {} because {} ({})", callout, field, reasonSummary, reasonDetails);

			getCreateChild(field, callout).setStatusSkipped(reasonSummary, reasonDetails);
			return this;
		}
	}

}
