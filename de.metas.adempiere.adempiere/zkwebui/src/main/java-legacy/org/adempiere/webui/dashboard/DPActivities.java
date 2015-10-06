/******************************************************************************
 * Copyright (C) 2008 Elaine Tan                                              *
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
package org.adempiere.webui.dashboard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.util.ServerPushTemplate;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Box;
import org.zkoss.zul.Vbox;

/**
 * Dashboard item: Workflow activities, notices and requests
 * 
 * @author Elaine
 * @date November 20, 2008
 */
public class DPActivities extends DashboardPanel implements EventListener
{

	/**
	 * 
	 */
	// Constants for button visibility
	
	private static final String SYSCONFIG_ShowNotices = "org.adempiere.webui.dashboard.DPActivities.ShowNotices";
	
	private static final String SYSCONFIG_ShowWFActivities = "org.adempiere.webui.dashboard.DPActivities.ShowWFActivities";
	
	private static final long serialVersionUID = 8123912981765687655L;

	private static final CLogger logger = CLogger.getCLogger(DPActivities.class);

	private Vbox activityItemsPanel;
	private Button btnNotice, btnWorkflow;

	private int noOfNotice;

	private int noOfWorkflow;
	
	final boolean showNotices = Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_ShowNotices, true);
	final boolean showWFActivities = Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_ShowWFActivities, true);

	public DPActivities()
	{
		super();
		this.appendChild(createActivitiesPanel());
	}

	private Box createActivitiesPanel()
	{
		int AD_Menu_ID;
		
		this.activityItemsPanel = new Vbox();

		if (showNotices)
		{
			btnNotice = new Button();
			activityItemsPanel.appendChild(btnNotice);
			btnNotice.setLabel(Msg.translate(Env.getCtx(), "AD_Note_ID") + " : 0");
			btnNotice.setTooltiptext(Msg.translate(Env.getCtx(), "AD_Note_ID"));
			btnNotice.setImage("/images/GetMail16.png");
			AD_Menu_ID = DB.getSQLValue(null, "SELECT AD_Menu_ID FROM AD_Menu WHERE Name = 'Notice' AND IsSummary = 'N'");
			btnNotice.setName(String.valueOf(AD_Menu_ID));
			btnNotice.addEventListener(Events.ON_CLICK, this);
		}

		if (showWFActivities)
		{
			btnWorkflow = new Button();
			activityItemsPanel.appendChild(btnWorkflow);
			btnWorkflow.setLabel(Msg.getMsg(Env.getCtx(), "WorkflowActivities") + " : 0");
			btnWorkflow.setTooltiptext(Msg.getMsg(Env.getCtx(), "WorkflowActivities"));
			btnWorkflow.setImage("/images/Assignment16.png");
			AD_Menu_ID = DB.getSQLValue(null, "SELECT AD_Menu_ID FROM AD_Menu WHERE Name = 'Workflow Activities' AND IsSummary = 'N'");
			btnWorkflow.setName(String.valueOf(AD_Menu_ID));
			btnWorkflow.addEventListener(Events.ON_CLICK, this);
		}
		
		createActivityItemComponents(); // metas: 03798
		
		return activityItemsPanel;
	}

	/**
	 * Get notice count
	 * 
	 * @return number of notice
	 */
	public static int getNoticeCount()
	{
		String sql = "SELECT COUNT(1) FROM AD_Note "
				+ "WHERE AD_Client_ID=? AND AD_User_ID IN (0,?)"
				+ " AND Processed='N'";

		int retValue = DB.getSQLValue(null, sql, Env.getAD_Client_ID(Env.getCtx()), Env.getAD_User_ID(Env.getCtx()));
		return retValue;
	}

	/**
	 * Get request count
	 * 
	 * @return number of request
	 */
	public static int getRequestCount()
	{
		return 0; // TODO
	}

	/**
	 * Get workflow activity count
	 * 
	 * @return number of workflow activity
	 */
	public static int getWorkflowCount()
	{
		int count = 0;

		String sql = "SELECT count(*) FROM AD_WF_Activity a "
				+ "WHERE a.Processed='N' AND a.WFState='OS' AND ("
				// Owner of Activity
				+ " a.AD_User_ID=?"	// #1
				// Invoker (if no invoker = all)
				+ " OR EXISTS (SELECT * FROM AD_WF_Responsible r WHERE a.AD_WF_Responsible_ID=r.AD_WF_Responsible_ID"
				+ " AND COALESCE(r.AD_User_ID,0)=0 AND COALESCE(r.AD_Role_ID,0)=0 AND (a.AD_User_ID=? OR a.AD_User_ID IS NULL))"	// #2
				// Responsible User
				+ " OR EXISTS (SELECT * FROM AD_WF_Responsible r WHERE a.AD_WF_Responsible_ID=r.AD_WF_Responsible_ID"
				+ " AND r.AD_User_ID=?)"		// #3
				// Responsible Role
				+ " OR EXISTS (SELECT * FROM AD_WF_Responsible r INNER JOIN AD_User_Roles ur ON (r.AD_Role_ID=ur.AD_Role_ID)"
				+ " WHERE a.AD_WF_Responsible_ID=r.AD_WF_Responsible_ID AND ur.AD_User_ID=?))";	// #4
		//
		// + ") ORDER BY a.Priority DESC, Created";
		int AD_User_ID = Env.getAD_User_ID(Env.getCtx());
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_User_ID);
			pstmt.setInt(2, AD_User_ID);
			pstmt.setInt(3, AD_User_ID);
			pstmt.setInt(4, AD_User_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				count = rs.getInt(1);
			}
		}
		catch (Exception e)
		{
			logger.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return count;
	}

	@Override
	public void refresh(ServerPushTemplate template)
	{
		noOfNotice = getNoticeCount();
		noOfWorkflow = getWorkflowCount();
		
		refreshActivityItems(); // metas: 03798

		// metas-tsa: 03903: If template is null, execute the updateUI directly
		if (template == null)
		{
			updateUI();
		}
		else
		{
			template.execute(this);
		}
	}

	@Override
	public void updateUI()
	{
		// don't update if not visible
		Component c = this.getParent();
		while (c != null)
		{
			if (!c.isVisible())
				return;
			c = c.getParent();
		}
		if (showNotices)
		{
			btnNotice.setLabel(Msg.translate(Env.getCtx(), "AD_Note_ID") + " : " + noOfNotice);
		}
		if (showWFActivities)
		{
			btnWorkflow.setLabel(Msg.getMsg(Env.getCtx(), "WorkflowActivities") + " : " + noOfWorkflow);
		}
		
		updateActivityItemsUI(); // metas: 03798
	}

	public void onEvent(Event event)
    {
        Component comp = event.getTarget();
        String eventName = event.getName();

        if(eventName.equals(Events.ON_CLICK))
        {
            if(comp instanceof Button)
            {
            	Button btn = (Button) comp;
            	
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
	}
	
	// metas
	private final static List<IDPActivityItem> activityItemList = Collections.synchronizedList(new ArrayList<IDPActivityItem>());
	static
	{
		registerItem(new DPActivityItemRequests());
	}
	public static void registerItem(IDPActivityItem item)
	{
		if (!activityItemList.contains(item))
		{
			activityItemList.add(item);
		}
	}

	private final List<DPActivityItemComponent> activityItemComponentList = new ArrayList<DPActivityItemComponent>();
	private void createActivityItemComponents()
	{
		// We are copying the activityItemList, just to not keep busy the synchronized activityItemList
		final List<IDPActivityItem> items = new ArrayList<IDPActivityItem>(activityItemList);
		
		for (final IDPActivityItem item : items)
		{
			final DPActivityItemComponent itemComp = new DPActivityItemComponent(item);
			activityItemsPanel.appendChild(itemComp.getComponent());
			activityItemComponentList.add(itemComp);
		}
	}
	
	private void refreshActivityItems()
	{
		for (DPActivityItemComponent itemComp : activityItemComponentList)
		{
			itemComp.refresh();
		}
	}
	
	private void updateActivityItemsUI()
	{
		for (DPActivityItemComponent itemComp : activityItemComponentList)
		{
			itemComp.updateUI();
		}
	}
}
