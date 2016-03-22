/******************************************************************************
 * Product: Posterita Ajax UI 												  *
 * Copyright (C) 2007 Posterita Ltd.  All Rights Reserved.                    *
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
 * Posterita Ltd., 3, Draper Avenue, Quatre Bornes, Mauritius                 *
 * or via info@posterita.org or http://www.posterita.org/                     *
 *****************************************************************************/

package org.adempiere.webui.desktop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.webui.apps.ProcessDialog;
import org.adempiere.webui.apps.graph.WGraph;
import org.adempiere.webui.apps.graph.WPerformanceDetail;
import org.adempiere.webui.component.Tabpanel;
import org.adempiere.webui.component.ToolBarButton;
import org.adempiere.webui.dashboard.DPActivities;
import org.adempiere.webui.dashboard.DashboardPanel;
import org.adempiere.webui.dashboard.DashboardRunnable;
import org.adempiere.webui.event.MenuListener;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.HeaderPanel;
import org.adempiere.webui.panel.SidePanel;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.util.IServerPushCallback;
import org.adempiere.webui.util.ServerPushTemplate;
import org.adempiere.webui.window.ADWindow;
import org.compiere.model.MGoal;
import org.compiere.model.MMenu;
import org.compiere.model.MSysConfig;
import org.compiere.model.X_AD_Menu;
import org.compiere.model.X_PA_DashboardContent;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import org.zkoss.zk.au.out.AuScript;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.West;
import org.zkoss.zkmax.zul.Portalchildren;
import org.zkoss.zkmax.zul.Portallayout;
import org.zkoss.zul.Html;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Toolbarbutton;

import de.metas.ui.web.base.session.UserPreference;

/**
 *
 * Default desktop implementation.
 * @author <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @author <a href="mailto:hengsin@gmail.com">Low Heng Sin</a>
 * @date Mar 2, 2007
 * @version $Revision: 0.10 $
 */
public class DefaultDesktop extends TabbedDesktop implements MenuListener, Serializable, EventListener, IServerPushCallback
{
	/**
	 * generated serial version ID 
	 */
	private static final long serialVersionUID = -8203958978173990301L;

	private static final Logger logger = LogManager.getLogger(DefaultDesktop.class);

    private Center windowArea;

	private Borderlayout layout;

	private Thread dashboardThread;

	private DashboardRunnable dashboardRunnable;

	private int noOfNotice;

	private int noOfRequest;

	private int noOfWorkflow;

    public DefaultDesktop()
    {
    	super();
    }

    @Override
	protected Component doCreatePart(Component parent)
    {
    	SidePanel pnlSide = new SidePanel();
    	HeaderPanel pnlHead = new HeaderPanel();
    	this.pnlHead = pnlHead; // metas

        pnlSide.getMenuPanel().addMenuListener(this);

        layout = new Borderlayout();
        if (parent != null)
        {
        	layout.setParent(parent);
        	layout.setWidth("100%");
        	layout.setHeight("100%");
        	layout.setStyle("position: absolute");
        }
        else
        	layout.setPage(page);

        dashboardRunnable = new DashboardRunnable(layout.getDesktop(), this);

        North n = new North();
        layout.appendChild(n);
        n.setCollapsible(false);
        pnlHead.setParent(n);

        West w = new West();
        layout.appendChild(w);
        w.setWidth("300px");
        w.setCollapsible(true);
        w.setSplittable(true);
        w.setTitle("Menu");
        w.setFlex(true);
        w.addEventListener(Events.ON_OPEN, new EventListener() {			
			@Override
			public void onEvent(Event event) throws Exception {
				OpenEvent oe = (OpenEvent) event;
				UserPreference pref = SessionManager.getSessionApplication().getUserPreference();
				pref.setProperty(UserPreference.P_MENU_COLLAPSED, !oe.isOpen());
				pref.savePreference();
			}
		});
        UserPreference pref = SessionManager.getSessionApplication().getUserPreference();
        // metas: teo_sarca: begin: US074: Make menu collapse by default
        if (MSysConfig.getBooleanValue("ZK_MENU_AUTOCOLLAPSE", false, Env.getAD_Client_ID(Env.getCtx())))
        	pref.setProperty(UserPreference.P_MENU_COLLAPSED, true); 
        // metas: teo_sarca: end: US074: Make menu collapse by default
        boolean menuCollapsed= pref.isPropertyBool(UserPreference.P_MENU_COLLAPSED);
        w.setOpen(!menuCollapsed);
        pnlSide.setParent(w);
        w.setVisible(pnlSide.getMenuPanel().isMenuAvailable()); // metas: 03019

        windowArea = new Center();
        windowArea.setParent(layout);
        windowArea.setFlex(true);

        windowContainer.createPart(windowArea);

        createHomeTab();

        return layout;
    }

	private void createHomeTab()
	{
        Tabpanel homeTab = new Tabpanel();
        windowContainer.addWindow(homeTab, Msg.getMsg(Env.getCtx(), "Home").replaceAll("&", ""), false);

        Portallayout portalLayout = new Portallayout();
        portalLayout.setWidth("100%");
        portalLayout.setHeight("100%");
        portalLayout.setStyle("position: absolute; overflow: auto");
        homeTab.appendChild(portalLayout);

        // Dashboard content
        Portalchildren portalchildren = null;
        int currentColumnNo = 0;

        String sql = "SELECT COUNT(DISTINCT COLUMNNO) "
        	+ "FROM PA_DASHBOARDCONTENT "
        	+ "WHERE (AD_CLIENT_ID=0 OR AD_CLIENT_ID=?) AND ISACTIVE='Y'";

        int noOfCols = DB.getSQLValue(null, sql,
        		Env.getAD_Client_ID(Env.getCtx()));

        int width = noOfCols <= 0 ? 100 : 100/noOfCols;

		sql =  "SELECT x.*, m.AD_MENU_ID "
			+ "FROM PA_DASHBOARDCONTENT x "
			+ "LEFT OUTER JOIN AD_MENU m ON x.AD_WINDOW_ID=m.AD_WINDOW_ID "
			+ "WHERE (x.AD_CLIENT_ID=0 OR x.AD_CLIENT_ID=?) AND x.ISACTIVE='Y' "
			+ "ORDER BY x.COLUMNNO, x.AD_CLIENT_ID, x.LINE ";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, Env.getAD_Client_ID(Env.getCtx()));
			rs = pstmt.executeQuery();

			while (rs.next())
			{
	        	int columnNo = rs.getInt(X_PA_DashboardContent.COLUMNNAME_ColumnNo);
	        	if(portalchildren == null || currentColumnNo != columnNo)
	        	{
	        		portalchildren = new Portalchildren();
	                portalLayout.appendChild(portalchildren);
	                portalchildren.setWidth(width + "%");
	                portalchildren.setStyle("padding: 5px");

	                currentColumnNo = columnNo;
	        	}

	        	Panel panel = new Panel();
	        	panel.setStyle("margin-bottom:10px");
	        	panel.setTitle(rs.getString(X_PA_DashboardContent.COLUMNNAME_Name));

	        	String description = rs.getString(X_PA_DashboardContent.COLUMNNAME_Description);
            	if(description != null)
            		panel.setTooltiptext(description);

            	String collapsible = rs.getString(X_PA_DashboardContent.COLUMNNAME_IsCollapsible);
            	panel.setCollapsible(collapsible.equals("Y"));

	        	panel.setBorder("normal");
	        	portalchildren.appendChild(panel);
	            Panelchildren content = new Panelchildren();
	            panel.appendChild(content);

	            boolean panelEmpty = true;

	            // HTML content
	            String htmlContent = rs.getString(X_PA_DashboardContent.COLUMNNAME_HTML);
	            if(htmlContent != null)
	            {
		            StringBuffer result = new StringBuffer("<html><head>");

		    		URL url = getClass().getClassLoader().
					getResource("org/compiere/images/PAPanel.css");
					InputStreamReader ins;
					try {
						ins = new InputStreamReader(url.openStream());
						BufferedReader bufferedReader = new BufferedReader( ins );
						String cssLine;
						while ((cssLine = bufferedReader.readLine()) != null)
							result.append(cssLine + "\n");
					} catch (IOException e1) {
						logger.error(e1.getLocalizedMessage(), e1);
					}

					result.append("</head><body><div class=\"content\">\n");

//	            	if(description != null)
//	            		result.append("<h2>" + description + "</h2>\n");
	            	result.append(stripHtml(htmlContent, false) + "<br>\n");
	            	result.append("</div>\n</body>\n</html>\n</html>");

		            Html html = new Html();
		            html.setContent(result.toString());
		            content.appendChild(html);
		            panelEmpty = false;
	            }

	        	// Window
	        	int AD_Window_ID = rs.getInt(X_PA_DashboardContent.COLUMNNAME_AD_Window_ID);
	        	if(AD_Window_ID > 0)
	        	{
		        	int AD_Menu_ID = rs.getInt(X_AD_Menu.COLUMNNAME_AD_Menu_ID);
					ToolBarButton btn = new ToolBarButton(String.valueOf(AD_Menu_ID));
					MMenu menu = new MMenu(Env.getCtx(), AD_Menu_ID, null);
					btn.setLabel(menu.getName());
					btn.addEventListener(Events.ON_CLICK, this);
					content.appendChild(btn);
					panelEmpty = false;
	        	}

	        	// Goal
	        	int PA_Goal_ID = rs.getInt(X_PA_DashboardContent.COLUMNNAME_PA_Goal_ID);
	        	if(PA_Goal_ID > 0)
	        	{
	        		//link to open performance detail
	        		Toolbarbutton link = new Toolbarbutton();
		            link.setImage("/images/Zoom16.png");
		            link.setAttribute("PA_Goal_ID", PA_Goal_ID);
		            link.addEventListener(Events.ON_CLICK, new EventListener() {

						@Override
						public void onEvent(Event event) throws Exception {
							int PA_Goal_ID = (Integer)event.getTarget().getAttribute("PA_Goal_ID");
							MGoal goal = new MGoal(Env.getCtx(), PA_Goal_ID, null);
							new WPerformanceDetail(goal);
						}

		            });
		            content.appendChild(link);

		            String goalDisplay = rs.getString(X_PA_DashboardContent.COLUMNNAME_GoalDisplay);
		            MGoal goal = new MGoal(Env.getCtx(), PA_Goal_ID, null);
		            WGraph graph = new WGraph(goal, 55, false, true, 
		            		!(X_PA_DashboardContent.GOALDISPLAY_Chart.equals(goalDisplay)),
		            		X_PA_DashboardContent.GOALDISPLAY_Chart.equals(goalDisplay));
		            content.appendChild(graph);
		            panelEmpty = false;
	        	}

	            // ZUL file url
	        	String url = rs.getString(X_PA_DashboardContent.COLUMNNAME_ZulFilePath);
	        	if(url != null)
	        	{
		        	try {
		                Component component = Executions.createComponents(url, content, null);
		                if(component != null)
		                {
		                	if (component instanceof DashboardPanel)
		                	{
			                	DashboardPanel dashboardPanel = (DashboardPanel) component;
			                	if (!dashboardPanel.getChildren().isEmpty()) {
			                		content.appendChild(dashboardPanel);
			                		dashboardRunnable.add(dashboardPanel);
			                		panelEmpty = false;
			                	}
		                	}
		                	else
		                	{
		                		content.appendChild(component);
		                		panelEmpty = false;
		                	}
		                }
					} catch (Exception e) {
						logger.warn("Failed to create components. zul="+url, e);
					}
	        	}

	        	if (panelEmpty)
	        		panel.detach();
	        }
		} catch(Exception e) {
			logger.warn("Failed to create dashboard content", e);
		} finally {
			DB.close(rs, pstmt);
		}
        //

        //register as 0
        registerWindow(homeTab);

        if (!portalLayout.getDesktop().isServerPushEnabled())
        	portalLayout.getDesktop().enableServerPush(true);

        dashboardRunnable.refreshDashboard();

        dashboardThread = new Thread(dashboardRunnable, dashboardRunnable.getThreadName()); // metas: use threadName instead of  "UpdateInfo"
        dashboardThread.setDaemon(true);
        dashboardThread.start();
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

            	if(menuId > 0) onMenuSelected(menuId);
            }
        }
    }

    @Override
	public void onServerPush(ServerPushTemplate template)
	{
    	noOfNotice = DPActivities.getNoticeCount();
    	noOfRequest = DPActivities.getRequestCount();
    	noOfWorkflow = DPActivities.getWorkflowCount();

    	template.execute(this);
	}

	/**
	 *
	 * @param page
	 */
	@Override
	public void setPage(Page page) {
		if (this.page != page) {
			layout.setPage(page);
			this.page = page;
			if (dashboardThread != null && dashboardThread.isAlive()) {
				dashboardRunnable.stop();
				dashboardThread.interrupt();

				DashboardRunnable tmp = dashboardRunnable;
				dashboardRunnable = new DashboardRunnable(tmp, layout.getDesktop(), this);
				dashboardThread = new Thread(dashboardRunnable, dashboardRunnable.getThreadName()); // metas: use threadName instead of  "UpdateInfo"
		        dashboardThread.setDaemon(true);
		        dashboardThread.start();
			}
		}
	}

	/**
	 * Get the root component
	 * @return Component
	 */
	@Override
	public Component getComponent() {
		return layout;
	}

	@Override
	public void logout() {
		if (dashboardThread != null && dashboardThread.isAlive()) {
			dashboardRunnable.stop();
			dashboardThread.interrupt();
		}
	}

	@Override
	public void updateUI() {
		int total = noOfNotice + noOfRequest + noOfWorkflow;
		windowContainer.setTabTitle(0, Msg.getMsg(Env.getCtx(), "Home").replaceAll("&", "")
				+ " (" + total + ")",
				Msg.translate(Env.getCtx(), "AD_Note_ID") + " : " + noOfNotice
				//+ ", " + Msg.translate(Env.getCtx(), "R_Request_ID") + " : " + noOfRequest // metas: not supported anymore
				+ ", " + Msg.getMsg (Env.getCtx(), "WorkflowActivities") + " : " + noOfWorkflow);
	}
	
	private void autoHideMenu() {
		if (layout.getWest().isCollapsible() && !layout.getWest().isOpen())
		{
			//using undocumented js api, need to be retested after every version upgrade
			String id = layout.getWest().getUuid() + "!real";
			String btn = layout.getWest().getUuid() + "!btn";
			String script = "zk.show('" + id + "', false);";
			script += "$e('"+id+"')._isSlide = false;";
			script += "$e('"+id+"')._lastSize = null;";
			script += "$e('"+btn+"').style.display = '';";
			AuScript aus = new AuScript(layout.getWest(), script);
			Clients.response("autoHideWest", aus);
		}
	}

	@Override
	protected void preOpenNewTab() 
	{
		autoHideMenu();
	}	

// metas: begin
	@Override
	public ADWindow openWindow(int windowId) {
		autoHideMenu();
		return super.openWindow(windowId);
	}

	@Override
	public ADForm openForm(int formId) {
		autoHideMenu();
		return super.openForm(formId);
	}

	@Override
	public ProcessDialog openProcessDialog(int processId, boolean soTrx) {
		autoHideMenu();
		return super.openProcessDialog(processId, soTrx);
	}

	@Override
	public void openTask(int taskId) {
		autoHideMenu();
		super.openTask(taskId);
	}

	@Override
	public void openWorkflow(int workflowID) {
		autoHideMenu();
		super.openWorkflow(workflowID);
	}
	
	private HeaderPanel pnlHead;
	public HeaderPanel getHeaderPanel()
	{
		return this.pnlHead;
	}
// metas: end
}
