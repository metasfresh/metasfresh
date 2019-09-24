/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.apps;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.apps.form.FormFrame;

/**
 * Managed a list of window.
 * 
 * @author Low Heng Sin
 * @version 2006/11/20
 */
public class WindowManager
{
	public WindowManager()
	{
	}

	private List<Window> windows = new ArrayList<>();
	private WindowEventListener eventListener = new WindowEventListener(this);

	/**
	 * Add window.
	 * If given window was already added, it won't be added twice.
	 * 
	 * @param window
	 */
	public void add(final Window window)
	{
		// guard against null (shall not happen)
		if (window == null)
		{
			return;
		}
		
		// Make sure it was not already added
		if(windows.contains(window))
		{
			return;
		}
		
		windows.add(window);
		window.addComponentListener(eventListener);
		window.addWindowListener(eventListener);
	}

	/**
	 * Close all windows managed by this window manager.
	 */
	public void close()
	{
		for (Window w : windows)
		{
			w.removeComponentListener(eventListener);
			w.removeWindowListener(eventListener);
			w.dispose();
		}
		windows = new ArrayList<>();
	}

	/**
	 * Close all except one window.
	 * 
	 * @param window
	 */
	public void closeOthers(Window window)
	{
		for (Window w : windows)
		{
			w.removeComponentListener(eventListener);
			w.removeWindowListener(eventListener);
			if (!w.equals(window))
			{
				w.dispose();
			}
		}
		windows = new ArrayList<>();
		add(window);
	}

	/**
	 * Remove window
	 * 
	 * @param window
	 */
	public void remove(Window window)
	{
		if (windows.remove(window))
		{
			window.removeComponentListener(eventListener);
			window.removeWindowListener(eventListener);
		}
	}

	/**
	 * Get list of windows managed by this window manager
	 * 
	 * @return Array of windows
	 */
	public Window[] getWindows()
	{
		Window[] a = new Window[windows.size()];
		return windows.toArray(a);
	}

	/**
	 * @return Number of windows managed by this window manager
	 */
	public int getWindowCount()
	{
		return windows.size();
	}

	/**
	 * Find window by ID
	 * 
	 * @param AD_Window_ID
	 * @return AWindow reference, null if not found
	 */
	public AWindow find(final AdWindowId adWindowId)
	{
		for (Window w : windows)
		{
			if (w instanceof AWindow)
			{
				AWindow a = (AWindow)w;
				if (AdWindowId.equals(a.getAdWindowId(), adWindowId))
				{
					return a;
				}
			}
		}
		return null;
	}

	public FormFrame findForm(int AD_FORM_ID)
	{
		for (Window w : windows)
		{
			if (w instanceof FormFrame)
			{
				FormFrame ff = (FormFrame)w;
				if (ff.getAD_Form_ID() == AD_FORM_ID)
				{
					return ff;
				}
			}
		}
		return null;
	}
}

class WindowEventListener implements ComponentListener, WindowListener
{
	WindowManager windowManager;

	protected WindowEventListener(WindowManager windowManager)
	{
		this.windowManager = windowManager;
	}

	@Override
	public void componentHidden(ComponentEvent e)
	{
		Component c = e.getComponent();
		if (c instanceof Window)
		{
			c.removeComponentListener(this);
			((Window)c).removeWindowListener(this);
			windowManager.remove((Window)c);
		}
	}

	@Override
	public void componentMoved(ComponentEvent e)
	{
	}

	@Override
	public void componentResized(ComponentEvent e)
	{
	}

	@Override
	public void componentShown(ComponentEvent e)
	{
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		Window w = e.getWindow();
		if (w instanceof Window)
		{
			w.removeComponentListener(this);
			w.removeWindowListener(this);
			windowManager.remove(w);
		}
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
	}
}