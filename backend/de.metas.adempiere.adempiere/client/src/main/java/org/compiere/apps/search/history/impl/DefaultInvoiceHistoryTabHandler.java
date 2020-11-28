package org.compiere.apps.search.history.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.util.Map;
import java.util.TreeMap;

import org.compiere.apps.search.history.IInvoiceHistoryTabHandler;

/**
 * Default tab handler
 *
 * @author al
 */
public class DefaultInvoiceHistoryTabHandler implements IInvoiceHistoryTabHandler
{
	/**
	 * Map is sorted
	 */
	private final Map<Integer, Boolean> tabEnabledMap = new TreeMap<>();

	public DefaultInvoiceHistoryTabHandler()
	{
		super();
	}

	@Override
	public void setTabEnabled(final int tabIndex, final boolean enabled)
	{
		tabEnabledMap.put(tabIndex, enabled);
	}

	@Override
	public boolean isTabEnabled(final int tabIndex)
	{
		final Boolean enabled = tabEnabledMap.get(tabIndex);
		return enabled == Boolean.TRUE || enabled == null; // null is also true
	}

	@Override
	public int getTabIndexEffective(final int tabIndex)
	{
		int tabIndexToUse = tabIndex;
		for (final Integer tabIndexEnabled : tabEnabledMap.keySet())
		{
			if (tabIndexEnabled > tabIndex) // note that we must go one additional step (i.e first tab)
			{
				return tabIndexToUse; // we shall not go over the tab we're searching for
			}
			if (!isTabEnabled(tabIndexEnabled)) // if tab is not enabled before, then assume an earlier tab (one position before))
			{
				tabIndexToUse--;
			}
		}
		return tabIndexToUse;
	}

	@Override
	public void initializeTab(final Runnable initializeTabRunnable, final int tabIndex)
	{
		final Boolean enabled = isTabEnabled(tabIndex);
		if (enabled)
		{
			initializeTabRunnable.run();
		}
	}
}
