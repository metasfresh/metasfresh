/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2007 Adempiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *
 * Copyright (C) 2007 Low Heng Sin hengsin@avantz.com
 * _____________________________________________
 *****************************************************************************/
package org.compiere.print;

import javax.swing.JFrame;

import org.compiere.apps.AMenu;
import org.compiere.util.Env;

/**
 * 
 * @author Low Heng Sin
 *
 */
public class SwingViewerProvider implements ReportViewerProvider
{
	public static final transient SwingViewerProvider instance = new SwingViewerProvider();

	private SwingViewerProvider()
	{
		super();
	}

	@Override
	public void openViewer(ReportEngine re)
	{
		final Viewer viewer = new Viewer(re);
		final JFrame top = Env.getWindow(Env.WINDOW_MAIN);
		if (top instanceof AMenu)
		{
			((AMenu)top).getWindowManager().add(viewer);
		}
	}

}
