package org.adempiere.ui.api;

import java.util.Properties;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.util.ISingletonService;
import org.compiere.apps.WindowManager;
import org.compiere.model.I_AD_Window;

public interface IWindowBL extends ISingletonService
{
	/**
	 * @param AD_Window_ID the ID of the window to be opened
	 *
	 * @return <code>true</code> if the window could be successfully opened
	 */
	boolean openWindow(int AD_Window_ID);

	/**
	 * @param windowManager maybe <code>null</code>. If <code>null</code>, then {@link org.compiere.apps.AEnv} is used to access the window manager
	 * @param AD_Workbench_ID maybe 0
	 * @param AD_Window_ID the ID of the window to be opened
	 *
	 * @return <code>true</code> if the window could be successfully opened
	 */
	boolean openWindow(WindowManager windowManager, int AD_Workbench_ID, int AD_Window_ID);

	/**
	 * Get the window from the menu if this menu points to a window
	 * 
	 * @param ctx
	 * @param menuID
	 * @return the window if found, null otherwise
	 */
	I_AD_Window getWindowFromMenu(Properties ctx, int menuID);
}
