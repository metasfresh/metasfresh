/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin                                            *
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
package org.adempiere.webui.apps.wf;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Properties;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import org.adempiere.webui.exception.ApplicationException;
import org.adempiere.webui.session.SessionManager;
import org.compiere.apps.wf.WFLine;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.wf.MWFNode;
import org.compiere.wf.MWFNodeNext;
import org.compiere.wf.MWorkflow;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Area;
import org.zkoss.zul.Div;
import org.zkoss.zul.Html;
import org.zkoss.zul.Imagemap;

/**
 *	WorkFlow Panel
 *
 * 	@author Low Heng Sin
 */
public class WFPanel extends Borderlayout implements EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8777798080154603970L;


	/**
	 * 	Create Workflow Panel
	 */
	public WFPanel ()
	{
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "WFPanel", e);
		}
		m_WindowNo = SessionManager.getAppDesktop().registerWindow(this);
	}	//	WFPanel

	/**	Window No			*/
	private int         m_WindowNo = 0;


	/**	Workflow Model				*/
	private MWorkflow	m_wf = null;
	/**	Context						*/
	private Properties	m_ctx = Env.getCtx();

	/**	Logger			*/
	private static CLogger	log = CLogger.getCLogger(WFPanel.class);
	
	//	IO
	private WFNodeContainer nodeContainer = new WFNodeContainer();
	
	private Html infoTextPane = new Html();
	private Div contentPanel = new Div();
	//
	
	
	/**
	 * 	Static Init
	 *  <pre>
	 * 		centerScrollPane
	 * 			centerPanel
	 * 		south Panel
	 * 			infoScrollPane
	 * 			buttonPanel
	 * 	</pre>
	 * 	@throws Exception
	 */
	private void jbInit() throws Exception
	{
		this.setStyle("height: 100%; width: 100%; position: absolute");
		Center center = new Center();
		this.appendChild(center);
		center.appendChild(contentPanel);
		contentPanel.setStyle("width: 100%; heigh: 100%;");
		center.setAutoscroll(true);
		center.setFlex(true);
		
		South south = new South();
		this.appendChild(south);
		south.appendChild(infoTextPane);
		south.setHeight("15%");
		south.setSplittable(true);
		south.setCollapsible(true);
		south.setAutoscroll(true);
		south.setFlex(true);		
	}	//	jbInit

	/**
	 * 	Dispose
	 * @see org.compiere.apps.form.FormPanel#dispose()
	 */
	public void dispose()
	{
		SessionManager.getAppDesktop().closeActiveWindow();
	}	//	dispose

	
	/**
	 * 	Load Workflow & Nodes
	 * 	@param AD_Workflow_ID ID
	 */
	public void load (int AD_Workflow_ID)
	{
		log.fine("AD_Workflow_ID=" + AD_Workflow_ID);
		if (AD_Workflow_ID == 0)
			return;
		int AD_Client_ID = Env.getAD_Client_ID(Env.getCtx());
		//	Get Workflow
		m_wf = new MWorkflow (Env.getCtx(), AD_Workflow_ID, null);
		nodeContainer.removeAll();
		nodeContainer.setWorkflow(m_wf);
		
		//	Add Nodes for Paint
		MWFNode[] nodes = m_wf.getNodes(true, AD_Client_ID);
		for (int i = 0; i < nodes.length; i++)
		{
			WFNode wfn = new WFNode (nodes[i]);
			nodeContainer.add (wfn);
			//	Add Lines
			MWFNodeNext[] nexts = nodes[i].getTransitions(AD_Client_ID);
			for (int j = 0; j < nexts.length; j++)
				nodeContainer.add (new WFLine (nexts[j]));
		}
		Dimension dimension = nodeContainer.getDimension();
		BufferedImage bi = new BufferedImage (dimension.width + 2, dimension.height + 2, BufferedImage.TYPE_INT_ARGB);
		nodeContainer.paint(bi.createGraphics());
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(bi, "png", os);
			AImage imageContent = new AImage("workflow.png", os.toByteArray());
			Imagemap image = new Imagemap();		
			image.setWidth(dimension.width + "px");
			image.setHeight(dimension.height + "px");
			image.setContent(imageContent);
			contentPanel.appendChild(image);
			
			image.addEventListener(Events.ON_CLICK, this);
			for(WFNode node : nodeContainer.getNodes()) {
				Area area = new Area();
				Rectangle rect = node.getBounds();
				area.setCoords(rect.x + "," + rect.y + "," + (rect.x+rect.width) + ","
						+ (rect.y+rect.height));
				image.appendChild(area);
				area.setId("WFN_"+node.getAD_WF_Node_ID());
				StringBuffer tooltip = new StringBuffer();
				String s = node.getNode().getDescription(true);
				if (s != null && s.trim().length() > 0)
					tooltip.append(s);
				String h = node.getNode().getHelp(true);
				if (h != null && h.trim().length() > 0) {
					if (tooltip.length() > 0)
						tooltip.append(". ");
					tooltip.append(h);
				}				
				area.setTooltiptext(tooltip.toString());
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
		
		//	Info Text
		StringBuffer msg = new StringBuffer("");
		msg.append("<H2>").append(m_wf.getName(true)).append("</H2>");
		String s = m_wf.getDescription(true);
		if (s != null && s.length() > 0)
			msg.append("<B>").append(s).append("</B>");
		s = m_wf.getHelp(true);
		if (s != null && s.length() > 0)
			msg.append("<BR>").append(s);
		infoTextPane.setContent(msg.toString());

	}	//	load

	/**
	 * 	String Representation
	 * 	@return info
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer("WorkflowPanel[");
		if (m_wf != null)
			sb.append(m_wf.getAD_Workflow_ID());
		sb.append("]");
		return sb.toString();
	}	//	toString
	
	public MWorkflow getWorkflow() 
	{
		return m_wf;
	}

	public void onEvent(Event event) throws Exception {
		if (Events.ON_CLICK.equals(event.getName()) && event instanceof MouseEvent) {
			MouseEvent me = (MouseEvent) event;
			String areaId = me.getArea();
			if (areaId != null && areaId.startsWith("WFN_")) {
				int id = Integer.valueOf(areaId.substring(4));
				for(WFNode node : nodeContainer.getNodes()) {
					if (node.getAD_WF_Node_ID() == id) {
						start(node);
						break;
					}
				}
			}
		}
	}

	private void start(WFNode node) {
		MWFNode wfn = node.getNode();
		if (wfn.getAD_Window_ID() > 0) {
			SessionManager.getAppDesktop().openWindow(wfn.getAD_Window_ID());
		} else if (wfn.getAD_Form_ID() > 0) {
			SessionManager.getAppDesktop().openForm(wfn.getAD_Form_ID());
		} else if (wfn.getAD_Process_ID() > 0) {
			SessionManager.getAppDesktop().openProcessDialog(wfn.getAD_Process_ID(), false);
		} else if (wfn.getAD_Task_ID() > 0) {
			SessionManager.getAppDesktop().openTask(wfn.getAD_Task_ID());
		} else if (wfn.getWorkflow_ID() > 0) {
			SessionManager.getAppDesktop().openWorkflow(wfn.getWorkflow_ID());
		} else {
            throw new ApplicationException("Action not yet implemented: " + wfn.getAction());
        }		
	}

}	//	WFPanel
