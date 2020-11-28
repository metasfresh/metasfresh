package org.adempiere.ui.api;

import java.util.Properties;

import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.apps.WindowManager;
import org.compiere.model.I_AD_Window;

import de.metas.util.ISingletonService;

public interface IWindowBL extends ISingletonService
{
	/**
	 * @param adWindowId the ID of the window to be opened
	 *
	 * @return <code>true</code> if the window could be successfully opened
	 */
	boolean openWindow(AdWindowId adWindowId);

	/**
	 * @param windowManager maybe <code>null</code>. If <code>null</code>, then {@link org.compiere.apps.AEnv} is used to access the window manager
	 * @param AD_Workbench_ID maybe 0
	 * @param adWindowId the ID of the window to be opened
	 *
	 * @return <code>true</code> if the window could be successfully opened
	 */
	boolean openWindow(WindowManager windowManager, int AD_Workbench_ID, AdWindowId adWindowId);

	/**
	 * Get the window from the menu if this menu points to a window
	 *
	 * @param ctx
	 * @param menuID
	 * @return the window if found, null otherwise
	 */
	I_AD_Window getWindowFromMenu(Properties ctx, int menuID);
}
