package org.adempiere.ad.callout.api.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
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

import de.metas.logging.LogManager;

public final class CalloutExecutor implements ICalloutExecutor
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private static final Logger logger = LogManager.getLogger(CalloutExecutor.class);

	private final String tableName;
	private final TableCalloutsMap tableCalloutsMap;
	private final Set<String> executionBlackListIds;

	//
	private final Set<ICalloutInstance> activeCalloutInstances = new HashSet<>();

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
		for (final Map.Entry<String, Collection<ICalloutInstance>> columnNameAndCallouts : tableCalloutsMap.getColumnNamesAndCalloutsEntries())
		{
			//
			// Get the callout field from supplier
			final String columnName = columnNameAndCallouts.getKey();
			final ICalloutField field = calloutFieldsSupplier.apply(columnName);
			if (field == null)
			{
				logger.trace("Skip executing all callouts for {} because the supplied field was null", columnName);
			}
			if (!isEligibleForExecuting(field))
			{
				logger.trace("Skip executing callouts for {} because it's not eligible", field);
				return;
			}

			//
			// Execute all callouts for current callout field
			final Collection<ICalloutInstance> callouts = columnNameAndCallouts.getValue();
			for (final ICalloutInstance callout : callouts)
			{
				execute(callout, field);
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

		return field.isTriggerCalloutAllowed();
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
}
