package org.adempiere.ad.callout.spi.impl;

import java.util.Map.Entry;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Supplier;

import org.adempiere.ad.callout.api.IADColumnCalloutDAO;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.api.TableCalloutsMap;
import org.adempiere.ad.callout.api.impl.MethodNameCalloutInstance;
import org.adempiere.ad.callout.api.impl.RuleCalloutInstance;
import org.adempiere.ad.callout.spi.IDefaultCalloutProvider;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_ColumnCallout;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;

import de.metas.adempiere.util.CacheCtx;
import de.metas.logging.LogManager;
import de.metas.script.ScriptEngineFactory;

/**
 * Provides {@link ICalloutInstance}s from {@link I_AD_ColumnCallout}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class DefaultCalloutProvider implements IDefaultCalloutProvider
{
	private static final transient Logger logger = LogManager.getLogger(DefaultCalloutProvider.class);

	@Override
	public TableCalloutsMap getCallouts(final Properties ctx, final String tableName)
	{
		final TableCalloutsMap.Builder tableCalloutsBuilder = TableCalloutsMap.builder();
		for (final Entry<String, Supplier<ICalloutInstance>> entry : supplyCallouts(ctx, tableName).entries())
		{
			final Supplier<ICalloutInstance> columnCalloutSupplier = entry.getValue();
			try
			{
				final ICalloutInstance callout = columnCalloutSupplier.get();
				if (callout == null)
				{
					continue;
				}

				final String columnName = entry.getKey();
				tableCalloutsBuilder.put(columnName, callout);
			}
			catch (final Exception ex)
			{
				logger.warn("Failed creating callout instance for {}. Skipped.", columnCalloutSupplier, ex);
			}
		}

		return tableCalloutsBuilder.build();
	}

	@Cached
	public ImmutableListMultimap<String, Supplier<ICalloutInstance>> supplyCallouts(@CacheCtx final Properties ctx, final String tableName)
	{
		final ListMultimap<String, I_AD_ColumnCallout> calloutsDef = Services.get(IADColumnCalloutDAO.class).retrieveAvailableCalloutsToRun(ctx, tableName);
		if (calloutsDef == null || calloutsDef.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		final ImmutableListMultimap.Builder<String, Supplier<ICalloutInstance>> callouts = ImmutableListMultimap.builder();

		for (final Entry<String, I_AD_ColumnCallout> entry : calloutsDef.entries())
		{
			final I_AD_ColumnCallout calloutDef = entry.getValue();
			final Supplier<ICalloutInstance> calloutSupplier = supplyCalloutInstanceOrNull(calloutDef);
			if (calloutSupplier == null)
			{
				continue;
			}

			final String columnName = entry.getKey();
			callouts.put(columnName, calloutSupplier);
		}

		return callouts.build();
	}

	/**
	 * @return supplier or <code>null</code> but never throws exception
	 */
	private Supplier<ICalloutInstance> supplyCalloutInstanceOrNull(final I_AD_ColumnCallout calloutDef)
	{
		if (calloutDef == null)
		{
			return null;
		}
		if (!calloutDef.isActive())
		{
			return null;
		}

		try
		{
			final String classname = calloutDef.getClassname();
			Check.assumeNotEmpty(classname, "classname is not empty");

			final Optional<String> ruleValue = ScriptEngineFactory.extractRuleValueFromClassname(classname);
			if(ruleValue.isPresent())
			{
				return RuleCalloutInstance.supplier(ruleValue.get());
			}
			else
			{
				return MethodNameCalloutInstance.supplier(classname);
			}
		}
		catch (final Exception e)
		{
			// We are just logging and discarding the error because there is nothing that we can do about it
			// More, we want to load the other callouts and not just fail
			logger.error("Failed creating callout instance for " + calloutDef, e);
			return null;
		}
	}

}
