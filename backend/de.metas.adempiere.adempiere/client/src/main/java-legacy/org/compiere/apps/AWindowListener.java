/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.apps;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

/**
 *	Convenience Class to tunnel Events.
 *
 *  Forwards Window Events like windowClosed to the WindowState listener.
 *  Usually, a WindowStateListener gets only state events (minimized,..)
 *  This allows implementing a single method (windowStateChanged)
 *  to receive potentially all window events.
 *  <p>
 *  Implemented:
 * 	e.getID() == WindowEvent.WINDOW_CLOSED
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: AWindowListener.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class AWindowListener extends WindowAdapter
{
	/**
	 *	Constructor
	 *
	 * 	@param win Window
	 * 	@param l Listener
	 */
	public AWindowListener (Window win, WindowStateListener l)
	{
		m_window = win;
		m_listener = l;
		win.addWindowListener(this);
	}	//	AWindowListener

	/**	The Window					*/
	private Window 				m_window;
	/** The Listener				*/
	private WindowStateListener m_listener;

	/**
	 * 	Invoked when a window has been closed.
	 *  Forwarded.
	 *  @param e event to be forwarded
	 */
	public void windowClosed(WindowEvent e)
	{
		m_listener.windowStateChanged(e);
	}	//	windowClosed

}	//	AWindowListenr
