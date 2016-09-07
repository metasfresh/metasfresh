package org.adempiere.ad.ui.api.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.api.ITabCalloutDAO;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.ad.ui.spi.ITabCallout;
import org.adempiere.ad.ui.spi.impl.CompositeTabCallout;
import org.adempiere.model.I_AD_Tab_Callout;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;

import de.metas.logging.LogManager;

public class TabCalloutFactory implements ITabCalloutFactory
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	private final SetMultimap<String, Class<? extends ITabCallout>> tableName2tabCalloutClasses = LinkedHashMultimap.create();

	@Override
	public ITabCallout createAndInitialize(final ICalloutRecord calloutRecord)
	{
		try
		{
			return createAndInitialize0(calloutRecord);
		}
		catch(Exception e)
		{
			logger.error("Failed creating & initializing tab callouts for {}. Returning null.", calloutRecord, e);
			return ITabCallout.NULL;
		}
	}
	
	private ITabCallout createAndInitialize0(final ICalloutRecord calloutRecord)
	{
		final int adTabId = calloutRecord.getAD_Tab_ID();
		final String tableName = calloutRecord.getTableName();
		
		
		
		final CompositeTabCallout.Builder tabCalloutsBuilder = CompositeTabCallout.builder()
				.setCalloutRecord(calloutRecord);

		final Set<String> classnamesConsidered = new HashSet<>();

		//
		// Retrieve and instantiate tab callouts registered in application dictionary
		final Properties ctx = Env.getCtx();
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
				final ITabCallout tabCallout = Util.getInstance(ITabCallout.class, classname);
				tabCalloutsBuilder.addTabCallout(tabCallout);
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
					final ITabCallout tabCallout = tabCalloutClass.newInstance();
					tabCalloutsBuilder.addTabCallout(tabCallout);
				}
				catch (final Exception e)
				{
					logger.warn("Cannot load " + classname + " for AD_Tab_ID=" + adTabId, e);
				}
			}
		}
		
		//
		// Build & initialize
		return tabCalloutsBuilder.buildAndInitialize();
	}

	@Override
	public void registerTabCalloutForTable(final String tableName, final Class<? extends ITabCallout> tabCalloutClass)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		Check.assumeNotNull(tabCalloutClass, "tabCalloutClass not null");
		tableName2tabCalloutClasses.put(tableName, tabCalloutClass);
	}
}
