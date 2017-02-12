package org.adempiere.ad.ui.api;

import org.adempiere.ad.callout.api.ICalloutRecord;

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


import org.adempiere.ad.ui.spi.ITabCallout;
import org.adempiere.util.ISingletonService;
import org.compiere.model.StateChangeEvent;

/**
 * Creates and registers {@link ICalloutRecord}'s {@link ITabCallout}s.
 * 
 * @author tsa
 *
 */
public interface ITabCalloutFactory extends ISingletonService
{
	/**
	 * Creates new {@link ITabCallout} instances from registered callouts of given tab.
	 * 
	 * This method will make sure to intercept {@link ICalloutRecord}'s {@link StateChangeEvent}s and call the right callout methods.
	 * 
	 * @return instantiated tab callouts.
	 */
	ITabCallout createAndInitialize(ICalloutRecord calloutRecord);

	/**
	 * Programmatically registers a {@link ITabCallout} to all {@link ICalloutRecord}s which are about given <code>tableName</code>.
	 * 
	 * @param tableName
	 * @param tabCalloutClass
	 */
	void registerTabCalloutForTable(final String tableName, final Class<? extends ITabCallout> tabCalloutClass);
}
