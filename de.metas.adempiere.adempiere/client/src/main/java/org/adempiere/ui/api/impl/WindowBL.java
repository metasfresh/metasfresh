package org.adempiere.ui.api.impl;

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


import javax.swing.SwingUtilities;

import org.adempiere.ui.api.IWindowBL;
import org.compiere.apps.AEnv;
import org.compiere.apps.AWindow;
import org.compiere.apps.WindowManager;
import org.compiere.util.Env;
import org.compiere.util.Ini;

public class WindowBL implements IWindowBL
{
	@Override
	public boolean openWindow(final int AD_Window_ID)
	{
		return openWindow(null, // windowManager
				0, // AD_Workbench_ID
				AD_Window_ID,
				null); // invokeLaterForStatusBar
	}

	@Override
	public boolean openWindow(final WindowManager windowManager, final int AD_Workbench_ID, final int AD_Window_ID, final Runnable invokeLaterForStatusBar)
	{
		AWindow frame = (AWindow)Env.showWindow(AD_Window_ID);
		if (frame != null)
		{
			addFrame(windowManager, frame);
			return true;
		}

		// metas: begin: US831
		// metas: code changed:
		frame = findFrame(windowManager, AD_Window_ID);
		if (frame != null)
		{
			final boolean isOneInstanceOnly = frame.getGridWindow().getVO().isOneInstanceOnly();
			if (Ini.isPropertyBool(Ini.P_SINGLE_INSTANCE_PER_WINDOW) || isOneInstanceOnly)
			{
				AEnv.showWindow(frame);
				return true;
			}
		}
		// metas: end: US831

		invokeLaterIfNotNull(invokeLaterForStatusBar);			// 1
		frame = new AWindow();

		final boolean OK;
		if (AD_Workbench_ID > 0)
		{
			OK = frame.initWorkbench(AD_Workbench_ID);
		}
		else
		{
			OK = frame.initWindow(AD_Window_ID, null);	// No Query Value
		}
		if (!OK)
		{
			return false;
		}
		invokeLaterIfNotNull(invokeLaterForStatusBar);			// 2
		if (Ini.isPropertyBool(Ini.P_OPEN_WINDOW_MAXIMIZED))
		{
			AEnv.showMaximized(frame);
		}

		// Center the window
		invokeLaterIfNotNull(invokeLaterForStatusBar);			// 3
		if (!Ini.isPropertyBool(Ini.P_OPEN_WINDOW_MAXIMIZED))
		{
			// frame.validate(); // metas: tsa: is this still necessary?
			AEnv.showCenterScreen(frame);
		}

		addFrame(windowManager, frame);
		frame.getAPanel().requestFocusInWindow(); // metas-2009_0021_AP1_CR064: set Cursor to the first search field in the search panel

		return true;
	}

	private void invokeLaterIfNotNull(final Runnable invokeLaterForStatusBar)
	{
		if (invokeLaterForStatusBar == null)
		{
			return;
		}
		SwingUtilities.invokeLater(invokeLaterForStatusBar);
	}

	private void addFrame(final WindowManager windowManager, final AWindow frame)
	{
		if (windowManager != null)
		{
			windowManager.add(frame);
			return;
		}
		AEnv.addToWindowManager(frame);
	}

	private AWindow findFrame(final WindowManager windowManager, final int AD_Window_ID)
	{
		if (windowManager != null)
		{
			return windowManager.find(AD_Window_ID);
		}
		return AEnv.findInWindowManager(AD_Window_ID);
	}
}
