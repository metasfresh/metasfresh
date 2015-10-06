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
package org.adempiere.webui.window;

import org.adempiere.webui.component.Window;
import org.adempiere.webui.session.SessionManager;
import org.compiere.print.ReportEngine;
import org.compiere.print.ReportViewerProvider;

/**
 * 
 * @author Low Heng Sin
 *
 */
public class ZkReportViewerProvider implements ReportViewerProvider {

	@Override
	public void openViewer(ReportEngine report)
	{
		Window viewer = new ZkReportViewer(report, report.getName());
		
		viewer.setAttribute(Window.MODE_KEY, Window.MODE_EMBEDDED);
		viewer.setAttribute(Window.INSERT_POSITION_KEY, Window.INSERT_NEXT);
		SessionManager.getAppDesktop().showWindow(viewer);
	}
}