package de.metas.picking.legacy.form;

/*
 * #%L
 * de.metas.swat.base
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


import org.compiere.apps.AppsAction;

import de.metas.adempiere.form.terminal.IPropertiesPanelModel;

/**
 * Task 04966: implementations can be used to attach additional ways of data input (camera, scales) to generic UI components.
 * <p>
 * Currently it's used to add additional buttons properties/attribute editors
 * 
 * @author ts
 * 
 * @param <T> type of the {@link #invoke()} method's return value.
 * @see IPropertiesPanelModel#getAdditionalInputMethods(int)
 */
public interface IInputMethod<T>
{
	/**
	 * Returns the action that shall be added to the UI.
	 * 
	 * @return
	 */
	AppsAction getAppsAction();

	/**
	 * Does whatever is required to get the data. This can be a simple API call, but can also open an additional UI (e.g. to display a camera's video stream).
	 * 
	 * @return
	 */
	T invoke();
}
