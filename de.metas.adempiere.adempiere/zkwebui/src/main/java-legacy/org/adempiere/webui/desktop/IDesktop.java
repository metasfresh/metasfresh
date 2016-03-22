/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin                                            *
 * Copyright (C) 2008 Idalica Corporation                                     *
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
package org.adempiere.webui.desktop;

import org.adempiere.webui.ClientInfo;
import org.adempiere.webui.apps.ProcessDialog;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.part.UIPart;
import org.adempiere.webui.util.ServerPushTemplate;
import org.adempiere.webui.window.ADWindow;
import org.compiere.model.MQuery;
import org.compiere.util.WebDoc;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zul.api.Window;

/**
 * Desktop interface
 * @author hengsin
 *
 */
public interface IDesktop extends UIPart {

	public static final String WINDOWNO_ATTRIBUTE = "desktop.windowno";
	public static final String CLASS_NAME_KEY = "ZK_DESKTOP_CLASS";

	/**
	 *
	 * @return ClientInfo
	 */
	public ClientInfo getClientInfo();

	/**
	 *
	 * @param nodeId
	 */
	public void onMenuSelected(int nodeId);

	/**
	 *
	 * @param window
	 * @return windowNo
	 */
	public int registerWindow(Object window);

	/**
	 *
	 * @param WindowNo
	 * @return Object
	 */
	public Object findWindow(int WindowNo);

	/**
	 * close active window
	 * @return boolean
	 */
	public boolean closeActiveWindow();

	/**
	 *
	 * @param windowNo
	 * @return boolean
	 */
	public boolean closeWindow(int windowNo);

	/**
	 *
	 * @param url
	 * @param closeable
	 */
	public void showURL(String url, boolean closeable);

	/**
	 *
	 * @param doc
	 * @param string
	 * @param closeable
	 */
	public void showURL(WebDoc doc, String string, boolean closeable);

	/**
	 *
	 * @param win
	 */
	public void showWindow(Window win);

	/**
	 *
	 * @param win
	 * @param position
	 */
	public void showWindow(Window win, String position);

	/**
	 *
	 * @param window_ID
	 * @param query
	 */
	public ADWindow showZoomWindow(int window_ID, MQuery query);

	/**
	 *
	 * @param window_ID
	 * @param query
	 * @deprecated
	 */
	public void showWindow(int window_ID, MQuery query);

	/**
	 *
	 * @param windowNo
	 */
	public void unregisterWindow(int windowNo);

	/**
     *
     * @param processId
     * @param soTrx
     * @return ProcessDialog
     */
	public ProcessDialog openProcessDialog(int processId, boolean soTrx);

	/**
     *
     * @param formId
     * @return ADWindow
     */
	public ADForm openForm(int formId);

	/**
	 *
	 * @param windowId
	 * @return ADWindow
	 */
	public ADWindow openWindow(int windowId);

	/**
	 *
	 * @param windowId
	 * @param query
	 * @return ADWindow
	 */
	public ADWindow openWindow(int windowId, MQuery query);

	/**
	 * Open operating system task window
	 * @param task_ID
	 */
	public void openTask(int task_ID);

	/**
	 *
	 * @param workflow_ID
	 */
	public void openWorkflow(int workflow_ID);

	/**
	 * Get the root component of the desktop
	 * @return Component
	 */
	public Component getComponent();

	/**
	 * Attached to page
	 * @param page
	 */
	public void setPage(Page page);

	/**
	 * @param clientInfo
	 */
	public void setClientInfo(ClientInfo clientInfo);

	/**
	 * User logout from desktop, do clean up
	 */
	public void logout();

	/**
	 * Invoke by the server push thread. If the desktop argument is not null, must activate desktop
	 * before making update to UI. For performance reason, keep the activate of desktop as short
	 * as possible.
	 * @param template
	 */
	public void onServerPush(ServerPushTemplate template);
}
