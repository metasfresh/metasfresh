/******************************************************************************
 * Copyright (C) 2008 Elaine Tan                                              *
 * Copyright (C) 2008 Idalica Corporation
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
package org.adempiere.webui.dashboard;

import java.util.Enumeration;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.webui.component.ToolBarButton;
import org.adempiere.webui.session.SessionManager;
import org.compiere.model.MTree;
import org.compiere.model.MTreeNode;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Box;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Vbox;

/**
 * Dashboard item: User favourites
 * @author Elaine
 * @date November 20, 2008
 */
public class DPFavourites extends DashboardPanel implements EventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -481859785800845108L;

	public static final String FAVOURITE_DROPPABLE = "favourite";

	public static final String DELETE_FAV_DROPPABLE = "deleteFav";

	private Box bxFav;
	
	private Label lblMsg;
	
	private int m_AD_Tree_ID;
		
	public DPFavourites()
	{
		super();
		
		Panel panel = new Panel();
		this.appendChild(panel);
		
		Panelchildren favContent = new Panelchildren();
		panel.appendChild(favContent);
		favContent.appendChild(createFavouritesPanel());
		
		Toolbar favToolbar = new Toolbar();
		this.appendChild(favToolbar);
		
		// Elaine 2008/07/24
		Image img = new Image("/images/Delete24.png");
		favToolbar.appendChild(img);
		img.setAlign("right");
		img.setDroppable(DELETE_FAV_DROPPABLE);
		img.addEventListener(Events.ON_DROP, this);
		//
        
        favContent.setDroppable(FAVOURITE_DROPPABLE); 
        favContent.addEventListener(Events.ON_DROP, this);
        
        // metas: begin: 03048
        if (!isReadWrite())
        {
        	img.setVisible(false);
        	img.detach();
        	img.removeEventListener(Events.ON_DROP, this);
        	favContent.setDroppable("false");
        	favContent.removeEventListener(Events.ON_DROP, this);
        }
        // metas: end
	}
	
	private Box createFavouritesPanel()
	{
		bxFav = new Vbox();
		
		int AD_Role_ID = Env.getAD_Role_ID(Env.getCtx());
		int AD_Tree_ID = DB.getSQLValue(null,
			"SELECT COALESCE(r.AD_Tree_Menu_ID, ci.AD_Tree_Menu_ID)" 
			+ "FROM AD_ClientInfo ci" 
			+ " INNER JOIN AD_Role r ON (ci.AD_Client_ID=r.AD_Client_ID) "
			+ "WHERE AD_Role_ID=?", AD_Role_ID);
		if (AD_Tree_ID <= 0)
			AD_Tree_ID = 10;	//	Menu
		
		m_AD_Tree_ID = AD_Tree_ID;
		
		MTree vTree = new MTree(Env.getCtx(), AD_Tree_ID, false, true, null);
		MTreeNode m_root = vTree.getRoot();
		Enumeration<?> enTop = m_root.children();
		while(enTop.hasMoreElements())
		{
			MTreeNode ndTop = (MTreeNode)enTop.nextElement();
			Enumeration<?> en = ndTop.preorderEnumeration();
			while (en.hasMoreElements())
			{
				MTreeNode nd = (MTreeNode)en.nextElement();
				if (nd.isOnBar()) {
					String label = nd.toString().trim();
					ToolBarButton btnFavItem = new ToolBarButton(String.valueOf(nd.getNode_ID()));
					btnFavItem.setLabel(label);
					btnFavItem.setImage(getIconFile(nd));
					btnFavItem.setDraggable(DELETE_FAV_DROPPABLE);
					btnFavItem.addEventListener(Events.ON_CLICK, this);
					btnFavItem.addEventListener(Events.ON_DROP, this);
					bxFav.appendChild(btnFavItem);
			        // metas: begin: 03048
			        if (!isReadWrite())
			        {
			        	btnFavItem.setDraggable("false");
			        	btnFavItem.removeEventListener(Events.ON_DROP, this);
			        }
			        // metas: end
				}
			}
		}
		
		lblMsg = new Label("(Drag and drop menu item here)"); 
		if(bxFav.getChildren().isEmpty()) bxFav.appendChild(lblMsg);
				
		return bxFav;
	}
	
    /**
	 *	Make Bar add/remove persistent
	 *  @param add true if add - otherwise remove
	 *  @param Node_ID Node ID
	 *  @return true if updated
	 */
    private boolean barDBupdate(boolean add, int Node_ID)
	{
		int AD_Client_ID = Env.getAD_Client_ID(Env.getCtx());
		int AD_Org_ID = Env.getContextAsInt(Env.getCtx(), "#AD_Org_ID");
		int AD_User_ID = Env.getContextAsInt(Env.getCtx(), "#AD_User_ID");
		StringBuffer sql = new StringBuffer();
		if (add)
			sql.append("INSERT INTO AD_TreeBar "
				+ "(AD_Tree_ID,AD_User_ID,Node_ID, "
				+ "AD_Client_ID,AD_Org_ID, "
				+ "IsActive,Created,CreatedBy,Updated,UpdatedBy)VALUES (")
				.append(m_AD_Tree_ID).append(",").append(AD_User_ID).append(",").append(Node_ID).append(",")
				.append(AD_Client_ID).append(",").append(AD_Org_ID).append(",")
				.append("'Y',now(),").append(AD_User_ID).append(",now(),").append(AD_User_ID).append(")");
			//	if already exist, will result in ORA-00001: unique constraint (ADEMPIERE.AD_TREEBAR_KEY)
		else
			sql.append("DELETE FROM AD_TreeBar WHERE AD_Tree_ID=").append(m_AD_Tree_ID)
				.append(" AND AD_User_ID=").append(AD_User_ID)
				.append(" AND Node_ID=").append(Node_ID);
		int no = DB.executeUpdate(sql.toString(), false, null);
		return no == 1;
	}
        
    @Override
	public void onEvent(Event event)
    {
        Component comp = event.getTarget();
        String eventName = event.getName();
        
        if(eventName.equals(Events.ON_CLICK))
        {
            if(comp instanceof ToolBarButton)
            {
            	ToolBarButton btn = (ToolBarButton) comp;
            	
            	int menuId = 0;
            	try
            	{
            		menuId = Integer.valueOf(btn.getName());            		
            	}
            	catch (Exception e) {
					
				}
            	
            	if(menuId > 0) SessionManager.getAppDesktop().onMenuSelected(menuId);
            }
        }
        // Elaine 2008/07/24
        else if(eventName.equals(Events.ON_DROP))
        {
        	// metas: 03048: begin
        	if (!isReadWrite())
        	{
        		return;
        	}
        	// metas: end
        	DropEvent de = (DropEvent) event;
    		Component dragged = de.getDragged();
        	
        	if(comp instanceof Panelchildren)
        	{
        		if(dragged instanceof Treerow)
        		{
        			Treerow treerow = (Treerow) dragged;
        			Treeitem treeitem = (Treeitem) treerow.getParent();
        			
        			addItem(treeitem);
        		}
        	}
        	else if(comp instanceof Image)
        	{
        		if(dragged instanceof ToolBarButton)
        		{
        			ToolBarButton btn = (ToolBarButton) dragged;
        			removeLink(btn);
        		}
        	}
        }
        //
	}

	private void removeLink(ToolBarButton btn) {
		String value = btn.getName();
		
		if(value != null)
		{
			int Node_ID = Integer.valueOf(value.toString());
			if(barDBupdate(false, Node_ID))
			{
				bxFav.removeChild(btn);
				
				if(bxFav.getChildren().isEmpty())
					bxFav.appendChild(lblMsg);
				
				bxFav.invalidate();
			}
		}
	}

    /**
     * Add menu treeitem into the user favorite panel
     * @param treeitem
     */
	public void addItem(Treeitem treeitem) {
		Object value = treeitem.getValue();
		if(value != null)
		{
			int Node_ID = Integer.valueOf(value.toString());
			if(barDBupdate(true, Node_ID))
			{
				String label = treeitem.getLabel().trim();
				ToolBarButton btnFavItem = new ToolBarButton(String.valueOf(Node_ID));
				btnFavItem.setLabel(label);
				btnFavItem.setImage(treeitem.getImage());
				btnFavItem.setDraggable(DELETE_FAV_DROPPABLE);
				btnFavItem.addEventListener(Events.ON_CLICK, this);
				btnFavItem.addEventListener(Events.ON_DROP, this);
				bxFav.appendChild(btnFavItem);
				bxFav.removeChild(lblMsg);        					
				bxFav.invalidate();
		        // metas: begin: 03048
		        if (!isReadWrite())
		        {
		        	btnFavItem.setDraggable("false");
		        	btnFavItem.removeEventListener(Events.ON_DROP, this);
		        }
		        // metas: end
			}
		}
	}
	
	private String getIconFile(MTreeNode mt) {
		if (mt.isWindow())
			return "images/mWindow.png";
		if (mt.isReport())
			return "images/mReport.png";
		if (mt.isProcess())
			return "images/mProcess.png";
		if (mt.isWorkFlow())
			return "images/mWorkFlow.png";
		return "images/mWindow.png";
	}
	
	// metas: begin
	/**
	 * 
	 * @return true if the DPFavorites is read-write (i.e. you can add or delete items from it)
	 * @task 03048
	 */
	public boolean isReadWrite()
	{
		return Env.getUserRolePermissions().hasPermission(IUserRolePermissions.PERMISSION_MenuAvailable);
	}
	// metas: end
}
