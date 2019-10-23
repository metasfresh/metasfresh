package org.adempiere.ui.api;

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


import org.adempiere.exceptions.AdempiereException;
import org.adempiere.ui.spi.IGridTabSummaryInfoProvider;
import org.compiere.model.GridTab;

import de.metas.util.ISingletonService;

public interface IGridTabSummaryInfoFactory extends ISingletonService
{
	/**
	 * Get the Summary Info provider that fits your grid tab (window).
	 *
	 * For the normal tabs the default provider is used. It displays what is specified in the AT_Tab.AD_Message.
	 *
	 * For the tabs that have particular providers defined, these are the ones that are taken and the default is not used anymroe
	 *
	 * @param gridTab
	 * @return summary provider; never returns <code>null</code>
	 */
	IGridTabSummaryInfoProvider getSummaryInfoProvider(GridTab gridTab);

	/**
	 * Registers a new {@link IGridTabSummaryInfoFactory} which will be used when {@link GridTab}'s table name is <code>tableName</code>.
	 *
	 * @param tableName
	 * @param summaryInfoProvider
	 *
	 * @throws AdempiereException if provider is already registered for given <code>tableName</code>
	 */
	void register(String tableName, IGridTabSummaryInfoProvider summaryInfoProvider);

	/**
	 * Registers a new {@link IGridTabSummaryInfoFactory} which will be used when {@link GridTab}'s table name is <code>tableName</code>.
	 *
	 * @param tableName
	 * @param summaryInfoProvider
	 * @param forceOverride if true then this invocation may override and replace an already registered provider
	 *
	 * @throws AdempiereException if provider is already registered for given <code>tableName</code> and <code>forceOverride</code> is false.
	 */
	void register(String tableName, IGridTabSummaryInfoProvider summaryInfoProvider, boolean forceOverride);
}
