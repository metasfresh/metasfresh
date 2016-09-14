package org.adempiere.ad.ui.api.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.api.ITabCalloutDAO;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.ad.ui.spi.IStatefulTabCallout;
import org.adempiere.ad.ui.spi.ITabCallout;
import org.adempiere.ad.ui.spi.impl.CompositeTabCallout;
import org.adempiere.model.I_AD_Tab_Callout;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;

import de.metas.adempiere.util.CacheCtx;
import de.metas.logging.LogManager;

public class TabCalloutFactory implements ITabCalloutFactory
{
	private static final transient Logger logger = LogManager.getLogger(TabCalloutFactory.class);

	private final SetMultimap<String, Class<? extends ITabCallout>> tableName2tabCalloutClasses = LinkedHashMultimap.create();

	@Override
	public ITabCallout createAndInitialize(final ICalloutRecord calloutRecord)
	{
		try
		{
			final Properties ctx = Env.getCtx();
			final int adTabId = calloutRecord.getAD_Tab_ID();
			final String tableName = calloutRecord.getTableName();
			final Function<ICalloutRecord, ITabCallout> factory = getTabCalloutsFactory(ctx, adTabId, tableName);
			return factory.apply(calloutRecord);
		}
		catch (final Exception e)
		{
			logger.error("Failed creating & initializing tab callouts for {}. Returning null.", calloutRecord, e);
			return ITabCallout.NULL;
		}
	}

	@Cached
	Function<ICalloutRecord, ITabCallout> getTabCalloutsFactory(@CacheCtx final Properties ctx, final int adTabId, final String tableName)
	{
		final LinkedHashSet<Object> tabCalloutsList = new LinkedHashSet<>();
		boolean haveStatefulCallouts = false;

		final Set<String> classnamesConsidered = new HashSet<>();

		//
		// Retrieve and instantiate tab callouts registered in application dictionary
		final List<I_AD_Tab_Callout> calloutsDefinition = Services.get(ITabCalloutDAO.class).retrieveAllCalloutsDefinition(ctx, adTabId);
		for (final I_AD_Tab_Callout def : calloutsDefinition)
		{
			String classname = def.getClassname();

			// No classname was set? wtf, but continue
			if (Check.isEmpty(classname, true))
			{
				logger.warn("No classname set on {}", def);
				continue;
			}

			//
			// Trim the classname and collect it (to make sure we are not instantiating it more then once)
			classname = classname.trim();
			if (!classnamesConsidered.add(classname))
			{
				logger.debug("Skip {} because it was already considered", classname);
				continue;
			}

			// Skip those which are not active
			if (!def.isActive())
			{
				logger.debug("Skip {} because is not active", classname);
				continue;
			}

			//
			// Instantiate the tab callout class
			try
			{
				final Class<? extends ITabCallout> tabCalloutClass = Util.loadClass(ITabCallout.class, classname);
				if (IStatefulTabCallout.class.isAssignableFrom(tabCalloutClass))
				{
					tabCalloutsList.add(tabCalloutClass);
					haveStatefulCallouts = true;
				}
				else
				{
					final ITabCallout tabCalloutInstance = Util.newInstance(ITabCallout.class, tabCalloutClass);
					tabCalloutsList.add(tabCalloutInstance);
				}
			}
			catch (final Exception e)
			{
				logger.warn("Cannot load " + classname + " for AD_Tab_ID=" + adTabId, e);
			}
		}

		//
		// Retrieve and instantiate callouts which were programatically registered
		final Set<Class<? extends ITabCallout>> tableNameCalloutClasses = tableName2tabCalloutClasses.get(tableName);
		if (!Check.isEmpty(tableNameCalloutClasses))
		{
			for (final Class<? extends ITabCallout> tabCalloutClass : tableNameCalloutClasses)
			{
				final String classname = tabCalloutClass.getName();
				if (!classnamesConsidered.add(classname))
				{
					logger.debug("Skip {} because it was already considered", classname);
				}

				//
				// Instantiate the tab callout class
				try
				{
					if (IStatefulTabCallout.class.isAssignableFrom(tabCalloutClass))
					{
						tabCalloutsList.add(tabCalloutClass);
						haveStatefulCallouts = true;
					}
					else
					{
						final ITabCallout tabCalloutInstance = Util.newInstance(ITabCallout.class, tabCalloutClass);
						tabCalloutsList.add(tabCalloutInstance);
					}
				}
				catch (final Exception e)
				{
					logger.warn("Cannot load " + classname + " for AD_Tab_ID=" + adTabId, e);
				}
			}
		}

		if (haveStatefulCallouts)
		{
			final Set<Object> tabCalloutsListCopy = ImmutableSet.copyOf(tabCalloutsList);
			return (calloutRecord) -> copyAndInitialize(tabCalloutsListCopy, calloutRecord);
		}
		else
		{
			final ITabCallout tabCallouts = copyAndInitialize(tabCalloutsList, (ICalloutRecord)null);
			return (calloutRecord) -> tabCallouts;
		}
	}

	private static ITabCallout copyAndInitialize(final Collection<Object> tabCalloutsList, @Nullable final ICalloutRecord calloutRecord)
	{
		final CompositeTabCallout.Builder tabCalloutsBuilder = CompositeTabCallout.builder();
		for (final Object tabCalloutObj : tabCalloutsList)
		{
			if (tabCalloutObj instanceof Class)
			{
				try
				{
					final Class<?> tabCalloutClass = (Class<?>)tabCalloutObj;
					final ITabCallout tabCalloutInstance = Util.newInstance(ITabCallout.class, tabCalloutClass);
					
					if(tabCalloutInstance instanceof IStatefulTabCallout)
					{
						((IStatefulTabCallout)tabCalloutInstance).onInit(calloutRecord);
					}

					tabCalloutsBuilder.addTabCallout(tabCalloutInstance);
				}
				catch (final Exception e)
				{
					logger.warn("Failed to initialize {}. Ignored.", tabCalloutObj, e);
				}
			}
			else if (tabCalloutObj instanceof ITabCallout)
			{
				final ITabCallout tabCallout = (ITabCallout)tabCalloutObj;
				tabCalloutsBuilder.addTabCallout(tabCallout);
			}
			else
			{
				logger.warn("Unknown callout type {}. Ignored.", tabCalloutObj);
			}
		}

		return tabCalloutsBuilder.build();
	}

	@Override
	public void registerTabCalloutForTable(final String tableName, final Class<? extends ITabCallout> tabCalloutClass)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		Check.assumeNotNull(tabCalloutClass, "tabCalloutClass not null");
		tableName2tabCalloutClasses.put(tableName, tabCalloutClass);
	}
}
