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

import java.util.ArrayList;
import java.util.List;

import org.adempiere.webui.ClientInfo;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.exception.ApplicationException;
import org.adempiere.webui.part.AbstractUIPart;
import org.compiere.model.MMenu;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.zkoss.zk.ui.event.Events;

/**
 * Base class for desktop implementation
 * @author hengsin
 *
 */
public abstract class AbstractDesktop extends AbstractUIPart implements IDesktop {

	private transient ClientInfo clientInfo;

	private List<Object> windows = null;

	@SuppressWarnings("unused")
	private static final CLogger logger = CLogger.getCLogger(AbstractDesktop.class);

	public AbstractDesktop() {
		windows = new ArrayList<Object>();
	}
	
	/**
     * Event listener for menu item selection.
     * Identifies the action associated with the selected
     * menu item and acts accordingly.
     * 
     * @param	menuId	Identifier for the selected menu item
     * 
     * @throws	ApplicationException	If the selected menu action has yet 
     * 									to be implemented
     */
    public void onMenuSelected(int menuId)
    {
        MMenu menu = new MMenu(Env.getCtx(), menuId, null);
        if(menu == null)
        {
            return;
        }

        if(menu.getAction().equals(MMenu.ACTION_Window))
        {
        	openWindow(menu.getAD_Window_ID());
        }
        else if(menu.getAction().equals(MMenu.ACTION_Process) ||
        		menu.getAction().equals(MMenu.ACTION_Report))
        {
        	openProcessDialog(menu.getAD_Process_ID(), menu.isSOTrx());
        }
        else if(menu.getAction().equals(MMenu.ACTION_Form))
        {
        	openForm(menu.getAD_Form_ID());        	
        }
        else if(menu.getAction().equals(MMenu.ACTION_WorkFlow))
        {
        	openWorkflow(menu.getAD_Workflow_ID());
        }
        else if(menu.getAction().equals(MMenu.ACTION_Task))
        {
        	openTask(menu.getAD_Task_ID());
        }
        else
        {
            throw new ApplicationException("Menu Action not yet implemented: " + menu.getAction());
        }
    }
    
    /**
	 * @return clientInfo
	 */
	public ClientInfo getClientInfo() {
		return clientInfo;
	}

	/**
	 * 
	 * @param clientInfo
	 */
	public void setClientInfo(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}
	
	/**
	 * @param win
	 */
	public int registerWindow(Object win) {
		int retValue = windows.size();
		windows.add(win);
		return retValue;
	}
	
	/**
	 * @param WindowNo
	 */
	public void unregisterWindow(int WindowNo) {
		if (WindowNo < windows.size())
			windows.set(WindowNo, null);
		Env.clearWinContext(WindowNo);
	}
   	
    /**
     * 
     * @param WindowNo
     * @return Object
     */
	public Object findWindow(int WindowNo) {
		if (WindowNo < windows.size())
			return windows.get(WindowNo);
		else
			return null;
	}
	
    /**
     * @param win
     */
    public void showWindow(org.zkoss.zul.api.Window win) 
    {
    	String pos = win.getPosition();
    	this.showWindow(win, pos);
    }
    
    /**
     * @param win
     * @param pos
     */
   	public void showWindow(org.zkoss.zul.api.Window win, String pos)
	{
   		win.setPage(page);		
		Object objMode = win.getAttribute(Window.MODE_KEY);

		String mode = Window.MODE_MODAL;
		
		if (objMode != null)
		{
			mode = objMode.toString();
		}
		
		if (Window.MODE_MODAL.equals(mode))
		{
			showModal(win);
		}
		else if (Window.MODE_POPUP.equals(mode))
		{
			showPopup(win, pos);
		}
		else if (Window.MODE_OVERLAPPED.equals(mode))
		{
			showOverlapped(win, pos);
		}
		else if (Window.MODE_EMBEDDED.equals(mode))
		{
			showEmbedded(win);
		}
		else if (Window.MODE_HIGHLIGHTED.equals(mode))
		{
			showHighlighted(win, pos);
		}		
	}
   	
   	protected abstract void showEmbedded(org.zkoss.zul.api.Window win);

	/**
   	 * 
   	 * @param win
   	 */
   	protected void showModal(org.zkoss.zul.api.Window win)
   	{
   		//fall back to highlighted if can't execute doModal
   		if (Events.inEventListener())
   		{
			try
			{
				win.doModal();
			}
			catch(InterruptedException e)
			{
				
			}
   		}
   		else
   		{
   			showHighlighted(win, null);
   		}
			
	}
   	
   	/**
   	 * 
   	 * @param win
   	 * @param position
   	 */
   	protected void showPopup(org.zkoss.zul.api.Window win, String position)
   	{
   		if (position == null)
   			win.setPosition("center");
   		else
   			win.setPosition(position);
   		
   		win.doPopup();
   	}
   	
   	/**
   	 * 
   	 * @param win
   	 * @param position
   	 */
   	protected void showOverlapped(org.zkoss.zul.api.Window win, String position)
   	{
		if (position == null)
			win.setPosition("center");
		else
			win.setPosition(position);
		
   		win.doOverlapped();
   	}
	
	/**
	 * 
	 * @param win
	 * @param position
	 */
   	protected void showHighlighted(org.zkoss.zul.api.Window win, String position)
   	{
		if (position == null)
			win.setPosition("center");
		else
			win.setPosition(position);
		
   		win.doHighlighted();
   	}

   	protected String stripHtml(String htmlString, boolean all) {
		htmlString = htmlString
		.replace("<html>", "")
		.replace("</html>", "")
		.replace("<body>", "")
		.replace("</body>", "")
		.replace("<head>", "")
		.replace("</head>", "");
		
		if (all)
			htmlString = htmlString
			.replace(">", "&gt;")
			.replace("<", "&lt;");
		return htmlString;
	}
}
