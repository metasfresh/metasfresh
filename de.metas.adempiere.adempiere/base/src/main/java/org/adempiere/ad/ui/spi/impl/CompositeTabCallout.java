package org.adempiere.ad.ui.spi.impl;

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


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.ad.ui.spi.ITabCallout;
import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.GridTab;
import org.compiere.util.CLogger;

public class CompositeTabCallout implements ITabCallout
{
	// services
	private final transient CLogger logger = CLogger.getCLogger(getClass());

	private final List<ITabCallout> tabCalloutsAll = new ArrayList<>();
	/** Initialized tab callouts */
	private final List<ITabCallout> tabCallouts = new ArrayList<>();
	private boolean _initialized = false;

	public void addTabCallout(final ITabCallout tabCallout)
	{
		assertNotInitialized();
		Check.assumeNotNull(tabCallout, "tabCallout not null");

		if (tabCalloutsAll.contains(tabCallout))
		{
			return;
		}
		tabCalloutsAll.add(tabCallout);
	}

	private final void assertNotInitialized()
	{
		Check.assume(!_initialized, "not already initialized");
	}

	private final void markAsInitialized()
	{
		assertNotInitialized();
		_initialized = true;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public void onInit(final GridTab gridTab)
	{
		markAsInitialized();

		for (final ITabCallout tabCallout : tabCalloutsAll)
		{
			try
			{
				tabCallout.onInit(gridTab);
				tabCallouts.add(tabCallout);
			}
			catch (Exception e)
			{
				logger.log(Level.SEVERE, "Failed to initialize: " + tabCallout, e);
			}
		}
	}

	@Override
	public void onIgnore(final GridTab gridTab)
	{
		for (final ITabCallout tabCallout : tabCallouts)
		{
			tabCallout.onIgnore(gridTab);
		}
	}

	@Override
	public void onNew(final GridTab gridTab)
	{
		for (final ITabCallout tabCallout : tabCallouts)
		{
			tabCallout.onNew(gridTab);
		}
	}

	@Override
	public void onSave(final GridTab gridTab)
	{
		for (final ITabCallout tabCallout : tabCallouts)
		{
			tabCallout.onSave(gridTab);
		}
	}

	@Override
	public void onDelete(final GridTab gridTab)
	{
		for (final ITabCallout tabCallout : tabCallouts)
		{
			tabCallout.onDelete(gridTab);
		}
	}

	@Override
	public void onRefresh(final GridTab gridTab)
	{
		for (final ITabCallout tabCallout : tabCallouts)
		{
			tabCallout.onRefresh(gridTab);
		}
	}

	@Override
	public void onRefreshAll(final GridTab gridTab)
	{
		for (final ITabCallout tabCallout : tabCallouts)
		{
			tabCallout.onRefreshAll(gridTab);
		}
	}

	@Override
	public void onAfterQuery(final GridTab gridTab)
	{
		for (final ITabCallout tabCallout : tabCallouts)
		{
			tabCallout.onAfterQuery(gridTab);
		}
	}

}
