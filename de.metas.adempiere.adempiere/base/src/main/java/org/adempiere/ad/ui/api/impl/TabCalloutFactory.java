package org.adempiere.ad.ui.api.impl;

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


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

import org.adempiere.ad.ui.api.ITabCalloutDAO;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.ad.ui.spi.ITabCallout;
import org.adempiere.ad.ui.spi.impl.CompositeTabCallout;
import org.adempiere.model.I_AD_Tab_Callout;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.GridTab;
import org.compiere.model.StateChangeEvent;
import org.compiere.model.StateChangeListener;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Util;

public class TabCalloutFactory implements ITabCalloutFactory
{
	private final transient CLogger logger = CLogger.getCLogger(getClass());

	private final Map<String, LinkedHashSet<Class<? extends ITabCallout>>> tableName2tabCalloutClasses = new HashMap<>();

	/**
	 * Retrieve NEW instances of all {@link ITabCallout}s registered for given {@link GridTab}.
	 *
	 * @param gridTab
	 * @return
	 */
	public ITabCallout retrieveCallouts(final GridTab gridTab)
	{
		final Properties ctx = Env.getCtx();
		final int adTabId = gridTab.getAD_Tab_ID();

		final CompositeTabCallout tabCallouts = new CompositeTabCallout();

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
				logger.log(Level.WARNING, "No classname set on {0}", def);
				continue;
			}

			//
			// Trim the classname and collect it (to make sure we are not instantiating it more then once)
			classname = classname.trim();
			if (!classnamesConsidered.add(classname))
			{
				logger.log(Level.FINE, "Skip {0} because it was already considered", classname);
				continue;
			}

			// Skip those which are not active
			if (!def.isActive())
			{
				logger.log(Level.FINE, "Skip {0} because is not active", classname);
				continue;
			}

			//
			// Instantiate the tab callout class
			try
			{
				final ITabCallout tabCallout = Util.getInstance(ITabCallout.class, classname);
				tabCallouts.addTabCallout(tabCallout);
			}
			catch (final Exception e)
			{
				logger.log(Level.WARNING, "Cannot load " + classname + " for AD_Tab_ID=" + adTabId, e);
			}
		}

		//
		// Retrieve and instantiate callouts which were programatically registered
		final String tableName = gridTab.getTableName();
		final Set<Class<? extends ITabCallout>> tableNameCalloutClasses = tableName2tabCalloutClasses.get(tableName);
		if (!Check.isEmpty(tableNameCalloutClasses))
		{
			for (final Class<? extends ITabCallout> tabCalloutClass : tableNameCalloutClasses)
			{
				final String classname = tabCalloutClass.getName();
				if (!classnamesConsidered.add(classname))
				{
					logger.log(Level.FINE, "Skip {0} because it was already considered", classname);
				}

				//
				// Instantiate the tab callout class
				try
				{
					final ITabCallout tabCallout = tabCalloutClass.newInstance();
					tabCallouts.addTabCallout(tabCallout);
				}
				catch (final Exception e)
				{
					logger.log(Level.WARNING, "Cannot load " + classname + " for AD_Tab_ID=" + adTabId, e);
				}
			}
		}

		return tabCallouts;
	}

	@Override
	public ITabCallout createAndInitialize(final GridTab gridTab)
	{
		try
		{
			final ITabCallout tabCallouts = retrieveCallouts(gridTab);

			// Call callouts initializer
			tabCallouts.onInit(gridTab);

			// Bind StateChangeEvent to tab callouts
			// It will cover almost all the tab callouts methods.
			final TabCalloutStateChangeListener listener = new TabCalloutStateChangeListener(gridTab, tabCallouts);
			gridTab.addStateChangeListener(listener);

			return tabCallouts;
		}
		catch (final Exception e)
		{
			logger.log(Level.WARNING, "Error loading callouts for " + gridTab, e);
			return ITabCallout.NULL;
		}
	}

	/**
	 * Listen on {@link GridTab}'s {@link StateChangeEvent}s and call the proper {@link ITabCallout} methods.
	 */
	private static class TabCalloutStateChangeListener implements StateChangeListener
	{
		private final GridTab gridTab;
		private final ITabCallout callouts;

		public TabCalloutStateChangeListener(final GridTab gridTab, final ITabCallout callouts)
		{
			Check.assume(gridTab != null, "gridTab not null");
			this.gridTab = gridTab;

			Check.assumeNotNull(callouts, "callouts not null");
			this.callouts = callouts;
		}

		@Override
		public void stateChange(final StateChangeEvent event)
		{
			final int eventType = event.getEventType();
			switch (eventType)
			{
				case StateChangeEvent.DATA_REFRESH_ALL:
					callouts.onRefreshAll(gridTab);
					break;
				case StateChangeEvent.DATA_REFRESH:
					callouts.onRefresh(gridTab);
					break;
				case StateChangeEvent.DATA_NEW:
					callouts.onNew(gridTab);
					break;
				case StateChangeEvent.DATA_DELETE:
					callouts.onDelete(gridTab);
					break;
				case StateChangeEvent.DATA_SAVE:
					callouts.onSave(gridTab);
					break;
				case StateChangeEvent.DATA_IGNORE:
					callouts.onIgnore(gridTab);
					break;
				default:
					// tolerate all other events, event if they are meaningless for us
					// throw new AdempiereException("EventType " + eventType + " is not supported");
					break;
			}
		}
	}

	@Override
	public void registerTabCalloutForTable(final String tableName, final Class<? extends ITabCallout> tabCalloutClass)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		Check.assumeNotNull(tabCalloutClass, "tabCalloutClass not null");

		LinkedHashSet<Class<? extends ITabCallout>> tabCalloutClasses = tableName2tabCalloutClasses.get(tableName);
		if (tabCalloutClasses == null)
		{
			tabCalloutClasses = new LinkedHashSet<>();
			tableName2tabCalloutClasses.put(tableName, tabCalloutClasses);
		}

		tabCalloutClasses.add(tabCalloutClass);
	}
}
