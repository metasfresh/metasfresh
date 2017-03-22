package org.adempiere.ad.callout.spi.impl;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.ad.callout.annotations.api.impl.AnnotatedCalloutInstance;
import org.adempiere.ad.callout.annotations.api.impl.AnnotatedCalloutInstanceFactory;
import org.adempiere.ad.callout.api.ICalloutFactory;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.api.TableCalloutsMap;
import org.adempiere.ad.callout.spi.ICalloutProvider;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

public class ProgramaticCalloutProvider implements ICalloutProvider, IProgramaticCalloutProvider
{
	private static final transient Logger logger = LogManager.getLogger(ProgramaticCalloutProvider.class);

	private final Map<String, TableCalloutsMap> registeredCalloutsByTableId = new ConcurrentHashMap<>();

	@Override
	public TableCalloutsMap getCallouts(final Properties ctx, final String tableName)
	{
		final TableCalloutsMap callouts = registeredCalloutsByTableId.get(tableName.toLowerCase());
		return callouts == null ? TableCalloutsMap.EMPTY : callouts;
	}

	@Override
	public boolean registerCallout(final String tableName, final String columnName, final ICalloutInstance callout)
	{
		Check.assumeNotNull(tableName, "TableName not null");
		Check.assumeNotNull(columnName, "ColumnName not null");
		Check.assumeNotNull(callout, "callout not null");

		String tableNameToUse = tableName.toLowerCase();

		//
		// Add the new callout to our internal map
		final AtomicBoolean registered = new AtomicBoolean(false);
		registeredCalloutsByTableId.compute(tableNameToUse, (tableNameKey, currentTabCalloutsMap) -> {
			if (currentTabCalloutsMap == null)
			{
				registered.set(true);
				return TableCalloutsMap.of(columnName, callout);
			}
			else
			{
				final TableCalloutsMap newTabCalloutsMap = currentTabCalloutsMap.compose(columnName, callout);
				registered.set(newTabCalloutsMap != currentTabCalloutsMap);
				return newTabCalloutsMap;
			}
		});

		// Stop here if it was not registered
		if (!registered.get())
		{
			return false;
		}

		logger.debug("Registered callout for {}.{}: {}", tableNameToUse, columnName, callout);

		// Make sure this provider is registered to ICalloutFactory.
		// We assume the factory won't register it twice.
		Services.get(ICalloutFactory.class).registerCalloutProvider(this);

		return true;
	}

	@Override
	public boolean registerAnnotatedCallout(final Object annotatedCalloutObj)
	{
		final List<AnnotatedCalloutInstance> calloutInstances = new AnnotatedCalloutInstanceFactory()
				.setAnnotatedCalloutObject(annotatedCalloutObj)
				.create();

		if (calloutInstances.isEmpty())
		{
			throw new AdempiereException("No binding columns found for " + annotatedCalloutObj + " (class=" + annotatedCalloutObj.getClass() + ")");
		}

		boolean registered = false;
		for (final AnnotatedCalloutInstance calloutInstance : calloutInstances)
		{
			final String tableName = calloutInstance.getTableName();
			for (final String columnName : calloutInstance.getColumnNames())
			{
				final boolean columNameRegistered = registerCallout(tableName, columnName, calloutInstance);
				if (columNameRegistered)
				{
					registered = true;
				}
			}
		}

		return registered;
	}
}
