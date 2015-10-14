package org.adempiere.ad.ui.spi;

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


import org.compiere.model.GridTab;

/**
 * Implementing classes are called by the system if they have been registered in the <code>AD_Tab_Callout</code> table.
 * Other than common (column-)callouts implementers are not called if a specific field is changed, but rather on general
 * user events such as "new record", "delete" etc.
 * <p>
 * 
 * Note:
 * <ul>
 * <li>instead of implementing this interface, please extend {@link TabCalloutAdapter}.
 * <li>that there is a method for each type defined in {@link org.compiere.model.StateChangeEvent}.
 * <li>Each new {@link GridTab} will get a new instance of {@link ITabCallout} so it's safe to have fields in your implementations.
 * </ul>
 * 
 * 
 */
public interface ITabCallout
{
	ITabCallout NULL = NullTabCallout.instance;

	/**
	 * Called after {@link GridTab} was initialized.
	 * 
	 * @param gridTab
	 */
	void onInit(GridTab gridTab);

	void onIgnore(GridTab gridTab);

	void onNew(GridTab gridTab);

	void onSave(GridTab gridTab);

	void onDelete(GridTab gridTab);

	void onRefresh(GridTab gridTab);

	void onRefreshAll(GridTab gridTab);

	/**
	 * Called after {@link GridTab} was queried.
	 * 
	 * @param gridTab
	 */
	void onAfterQuery(GridTab gridTab);
}
