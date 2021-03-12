/**
 * 
 */
package org.adempiere.apps.graph;

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


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;

import de.metas.project.ProjectType;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.images.Images;
import org.compiere.apps.AEnv;
import org.compiere.apps.AWindow;
import org.compiere.model.MAchievement;
import org.compiere.model.MGoal;
import org.compiere.model.MMeasure;
import org.compiere.model.MMeasureCalc;
import org.compiere.model.MQuery;
import org.compiere.model.MRequestType;
import org.compiere.swing.CMenuItem;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import de.metas.i18n.Msg;
import de.metas.logging.LogManager;

/**
 * @author fcsku
 *
 */
public class HtmlDashboard extends JPanel implements MouseListener,
		ActionListener, HyperlinkListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8125801717324723271L;
	private static Dimension paneldimensionMin = new Dimension(80, 80);
	private	JEditorPane	html;
	private enum PAGE_TYPE {PAGE_HOME, PAGE_PERFORMANCE, PAGE_LOGO}
	private static Logger log = LogManager.getLogger(HtmlDashboard.class);
	MGoal[] m_goals = null;
	JPopupMenu 					popupMenu = new JPopupMenu();
	private CMenuItem mRefresh = new CMenuItem(Msg.getMsg(Env.getCtx(), "Refresh"), Images.getImageIcon2("Refresh16"));
	URL	lastUrl  = null;


	/**
	 * 	Constructor
	 */
	public HtmlDashboard(String url, MGoal[] m_goals, boolean scrolling)
	{	
		super();
		//+param VTreePanel treePanel,
		//this.treePanel = treePanel;
		setName("test title");
		this.setLayout( new BorderLayout() );
		this.m_goals = m_goals;
	    // Create an HTML viewer
		JEditorPane.registerEditorKitForContentType("text/html", "org.adempiere.apps.graph.FCHtmlEditorKit");
	    html = new JEditorPane();
	    html.setContentType("text/html");
	    html.setEditable( false );
	    htmlUpdate(url);
	    JScrollPane scrollPane = null;
	    if (scrolling)
		{
			scrollPane = new JScrollPane();
		}
		else
		{
			scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		}
	    scrollPane.getViewport().add( html, BorderLayout.CENTER );
	    this.add( scrollPane, BorderLayout.CENTER );
		this.setMinimumSize(paneldimensionMin);
		addMouseListener(this);
		html.addHyperlinkListener( this );
		mRefresh.addActionListener(this);
		popupMenu.add(mRefresh);
		html.addMouseListener(this);
		html.setBackground(getBackground());
	}
	
	public HtmlDashboard(String url)
	{	
		 new HtmlDashboard(url, null, true);
	}
	
	private String createHTML(PAGE_TYPE requestPage){
		
		String result = "<html><head>";
		
		// READ CSS
		URL url = getClass().getClassLoader().
			getResource("org/compiere/images/PAPanel.css");
		InputStreamReader ins;
		try {
			ins = new InputStreamReader(url.openStream());
			BufferedReader bufferedReader = new BufferedReader( ins );
			String cssLine;
			while ((cssLine = bufferedReader.readLine()) != null)
			{
				result += cssLine + "\n";
			}
		} catch (IOException e1) {
			log.error(e1.getLocalizedMessage(), e1);
		}
		//System.out.println(result);
		switch (requestPage) {
		    case PAGE_LOGO:
		    	result += "</head><body class=\"header\">"
		    			 + "<table width=\"100%\"><tr><td>"
		    			 + "<img src=\"res:org/compiere/images/logo_ad.png\">"
		    			 + "</td><td></td><td width=\"290\">"
		    			 //+ "<img src=\"res:at/freecom/apps/images/logo_fc.png\">"
		    			 + "</td></tr></table>"
		    			 + "</body></html>";
		    	break;
			case PAGE_HOME: //**************************************************************
				 result += // "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///c:/standard.css\"/>"
						 "</head><body><div class=\"content\">\n";
				queryZoom = null;
				queryZoom = new ArrayList<>();
				String appendToHome = null;
				String sql =  " SELECT x.AD_CLIENT_ID, x.NAME, x.DESCRIPTION, x.AD_WINDOW_ID, x.PA_GOAL_ID, x.LINE, x.HTML, m.AD_MENU_ID"
							+ " FROM PA_DASHBOARDCONTENT x"
							+ " LEFT OUTER JOIN AD_MENU m ON x.ad_window_id=m.ad_window_id" 
							+ " WHERE (x.AD_Client_ID=0 OR x.AD_Client_ID=?) AND x.IsActive='Y'"
							+ " AND x.ZulFilePath IS NULL" // Elaine 2008/11/19 - available in WebUI only at the moment
							+ " ORDER BY LINE";
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try
				{
					pstmt = DB.prepareStatement(sql, null);
					pstmt.setInt(1, Env.getAD_Client_ID(Env.getCtx()));
					rs = pstmt.executeQuery();
					while (rs.next()) {				
						appendToHome = rs.getString("HTML");
						if (appendToHome != null) {
							if (rs.getString("DESCRIPTION") != null)
							{
								result += "<H2>" + rs.getString("DESCRIPTION") + "</H2>\n";
							}
							result += stripHtml(appendToHome, false) + "<br>\n";
						}
						
						if (rs.getInt("AD_MENU_ID") > 0) {
							result += "<a class=\"hrefNode\" href=\"http:///window/node#" 
								   + String.valueOf( rs.getInt("AD_WINDOW_ID")// "AD_MENU_ID") fcsku 3.7.07
								   + "\">" 	
								   + rs.getString("DESCRIPTION")
								   + "</a><br>\n");
						}
						result += "<br>\n";
						//result += "table id: " + rs.getInt("AD_TABLE_ID");
						if (rs.getInt("PA_GOAL_ID") > 0)
						 {
							result += goalsDetail(rs.getInt("PA_GOAL_ID"));
							//result += goalsDetail(rs.getInt("AD_TABLE_ID"));
						}
					}
				}
				catch (SQLException e)
				{
					log.error(sql, e);
				}
				finally
				{
					DB.close(rs, pstmt);
					rs = null; pstmt = null;
				}
				result += "<br><br><br>\n"
				+ "</div>\n</body>\n</html>\n";
				break;
			default: //************************************************************** 
				log.warn("Unknown option - "+requestPage);
		}
		return result;
	}

	 
	ArrayList<MQuery> queryZoom = null; //new ArrayList<MQuery>();
	
	private String goalsDetail(int AD_Table_ID) { //TODO link to goals
		String output = "";
		if (m_goals==null)
		{
			return output;
		}
		for (MGoal m_goal : m_goals)
		{
		  MMeasureCalc mc = MMeasureCalc.get(Env.getCtx(), m_goal.getMeasure().getPA_MeasureCalc_ID());
		  if (AD_Table_ID == m_goal.getPA_Goal_ID()){// mc.getAD_Table_ID()) {
			output += "<table class=\"dataGrid\"><tr>\n<th colspan=\"3\" class=\"label\"><b>" + m_goal.getName() + "</b></th></tr>\n";
			output += "<tr><td class=\"label\">Target</td><td colspan=\"2\" class=\"tdcontent\">" + m_goal.getMeasureTarget() + "</td></tr>\n";
			output += "<tr><td class=\"label\">Actual</td><td colspan=\"2\" class=\"tdcontent\">" + m_goal.getMeasureActual() + "</td></tr>\n";
			//if (mc.getTableName()!=null) output += "table: " + mc.getAD_Table_ID() + "<br>\n";
			Graph barPanel = new Graph(m_goal);
			GraphColumn[] bList = barPanel.getGraphColumnList();
			MQuery query = null;
			output += "<tr><td rowspan=\"" + bList.length + "\" class=\"label\" valign=\"top\">" + m_goal.getXAxisText() + "</td>\n";
			for (int k=0; k<bList.length; k++) {
				GraphColumn bgc = bList[k];
				if (k>0)
				{
					output += "<tr>";
				}
				if (bgc.getAchievement() != null)	//	Single Achievement
				{
					MAchievement a = bgc.getAchievement();
					query = MQuery.getEqualQuery("PA_Measure_ID", a.getPA_Measure_ID());
				}
				else if (bgc.getGoal() != null)		//	Multiple Achievements 
				{
					MGoal goal = bgc.getGoal();
					query = MQuery.getEqualQuery("PA_Measure_ID", goal.getPA_Measure_ID());
				}
				else if (bgc.getMeasureCalc() != null)	//	Document
				{
					mc = bgc.getMeasureCalc();
					query = mc.getQuery(m_goal.getRestrictions(false), 
						bgc.getMeasureDisplay(), bgc.getDate(), 
								Env.getUserRolePermissions());	// logged in role
				}
				else if (bgc.getProjectType() != null)	//	Document
				{
					ProjectType pt = bgc.getProjectType();
					query = MMeasure.getQuery(pt, m_goal.getRestrictions(false),
						bgc.getMeasureDisplay(), bgc.getDate(), bgc.getID(), 
								Env.getUserRolePermissions());	// logged in role
				}
				else if (bgc.getRequestType() != null)	//	Document
				{
					MRequestType rt = bgc.getRequestType();
					query = rt.getQuery(m_goal.getRestrictions(false), 
						bgc.getMeasureDisplay(), bgc.getDate(), bgc.getID(),
								Env.getUserRolePermissions());	// logged in role
				}
				output += "<td class=\"tdcontent\">"+ bgc.getLabel() + "</td><td  class=\"tdcontent\">";
				if (query != null) {
					output += "<a class=\"hrefZoom\" href=\"http:///window/zoom#"  
						   + queryZoom.size()
						   + "\">"
						   + bgc.getValue()
						   + "</a><br>\n";
					queryZoom.add(query);
				}
				else {
					log.info("Nothing to zoom to - " + bgc);
					output += bgc.getValue();
				}
				output += "</td></tr>";
			}
			output +=  "</tr>"
				   + "<tr><td colspan=\"3\">" 
				   + m_goal.getDescription()
				   + "<br>"
				   + stripHtml(m_goal.getColorSchema().getDescription(), true)
				   + "</td></tr>" 
				   + "</table>\n";
			bList = null;
			barPanel = null;
		}
		}
		return output;
	}
	
	private String stripHtml(String htmlString, boolean all) {
		htmlString = htmlString
		.replace("<html>", "")
		.replace("</html>", "")
		.replace("<body>", "")
		.replace("</body>", "")
		.replace("<head>", "")
		.replace("</head>", "");
		
		if (all)
		{
			htmlString = htmlString
			.replace(">", "&gt;")
			.replace("<", "&lt;");
		}
		return htmlString;
	}
	private void htmlUpdate(String url) {
		try {
			htmlUpdate( new URL( url ) );
		} catch( MalformedURLException e )	{
		    log.warn("Malformed URL: " + e );
		}
	}
	
	private void htmlUpdate(URL url) {
		if ((url==null) || (url.getPath().equals("/local/home"))){
			html.setText(createHTML(PAGE_TYPE.PAGE_HOME));
			html.setCaretPosition(0);
			lastUrl = url;
		}
		else if (url.getPath().equals("/local/logo")){
			html.setText(createHTML(PAGE_TYPE.PAGE_LOGO));
			html.setCaretPosition(0);
			lastUrl = url;
		}
		else if (url.getPath().equals("/local/performance")){
			html.setText(createHTML(PAGE_TYPE.PAGE_PERFORMANCE));
		}
		else if (url.getPath().equals("/window/node")){
			/* fcsku 3.7.07
			CButton button = new CButton("");		//	Create the button
			button.setActionCommand(String.valueOf(url.getRef())); //getNode_ID()
			button.addActionListener(treePanel); //VTreePanel
			//AEnv.zoom(130, 0);
			html.setCursor(Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ));
			button.doClick();
			html.setCursor(Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ));
			*/
			AdWindowId AD_Window_ID=AdWindowId.ofRepoId(Integer.parseInt(url.getRef()));
			AWindow frame = new AWindow();
			if (!frame.initWindow(AD_Window_ID, null))
			{
				return;
			}
			AEnv.addToWindowManager(frame);
			if (Ini.isPropertyBool(Ini.P_OPEN_WINDOW_MAXIMIZED))
			{
				AEnv.showMaximized(frame);
			}
			else
			{
				AEnv.showCenterScreen(frame);
			}
			frame = null;
			html.setCursor(Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ));
		}
		else if (url.getPath().equals("/window/zoom")){ 
			int index = Integer.parseInt(String.valueOf(url.getRef()));
			if ((index >= 0) && (index < queryZoom.size())) {
				html.setCursor(Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ));
				AEnv.zoom(queryZoom.get(index));
				html.setCursor(Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ));
			}
		}
		else if (url != null){
			// Load some cursors
			Cursor cursor = html.getCursor();
			html.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
			SwingUtilities.invokeLater( new PageLoader( html, url, cursor ) );
			lastUrl = url;
		}
	}
	
	
    @Override
	public void hyperlinkUpdate( HyperlinkEvent event )
    {
		if( event.getEventType() == HyperlinkEvent.EventType.ACTIVATED )
		{
			//System.out.println("parsed url: " + event.getURL());// +" from: " +event.getDescription());
			htmlUpdate(event.getURL());
		}
    }
    
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e))
		{
			popupMenu.show((Component)e.getSource(), e.getX(), e.getY());
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mRefresh)
		{
			if (m_goals != null)
			{
				for (MGoal m_goal : m_goals)
				{
					m_goal.updateGoal(true);
				}
			}
			htmlUpdate(lastUrl);
			Container parent = getParent();
			if (parent != null)
			{
				parent.invalidate();
			}
			invalidate();
			if (parent != null)
			{
				parent.repaint();
			}
			else
			{
				repaint();
			}
		}
	}
	
	class PageLoader implements Runnable
	{
	    private JEditorPane html;
	    private URL         url;
	    private Cursor      cursor;

	    PageLoader( JEditorPane html, URL url, Cursor cursor ) 
	    {
	        this.html = html;
	        this.url = url;
	        this.cursor = cursor;
	    }

	    @Override
		public void run() 
	    {
		    if( url == null ) 
		    {
	    		// restore the original cursor
		    	html.setCursor( cursor );

	    		// PENDING(prinz) remove this hack when
	    		// automatic validation is activated.
	    		Container parent = html.getParent();
	    		parent.repaint();
	        }
	        else 
	        {
	    		Document doc = html.getDocument();
		    	try {
			        html.setPage( url );
	    		}
	    		catch( IOException ioe ) 
	    		{
	    		    html.setDocument( doc );
	    		}
	    		finally
	    		{
	    		    // schedule the cursor to revert after
		    	    // the paint has happended.
			        url = null;
	    		    SwingUtilities.invokeLater( this );
		    	}
		    }
		}
	}
}
