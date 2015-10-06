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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.compiere.apps.wf.WFLine;
import org.compiere.util.CLogger;
import org.compiere.wf.MWorkflow;

/**
 * 
 * @author Low Heng Sin
 *
 */
public class WFNodeContainer 	
{
	/**
	 * 	WFContentPanel
	 */
	public WFNodeContainer ()
	{
	}	//	WFContentPanel
	
	/**	Logger			*/
	private static CLogger	log = CLogger.getCLogger(WFNodeContainer.class);
	/** Node List		*/
	private ArrayList<WFNode>	m_nodes = new ArrayList<WFNode>();
	/** Line List		*/
	private ArrayList<WFLine>	m_lines = new ArrayList<WFLine>();
	
	/** The Workflow		*/
	private MWorkflow	m_wf = null;
	
	/**
	 * 	Set Workflow
	 *	@param wf workflow
	 */
	public void setWorkflow (MWorkflow wf)
	{
		m_wf = wf;
	}	//	setWorkflow
	
	
	/**
	 * 	Remove All and their listeners
	 */
	public void removeAll ()
	{
		m_nodes.clear();
		m_lines.clear();
	}	//	removeAll
	
	
	/**
	 * 	Add Component and add Mouse Listener
	 *	@param comp component
	 *	@return component
	 */
	public void add (WFNode node)
	{
		m_nodes.add(node);
	}	//	add
	
	/**
	 * 
	 * @param line
	 */
	public void add(WFLine line)
	{
		m_lines.add(line);
	}

	/**
	 * 	Create Lines.
	 * 	Called by WF Layout Manager
	 */
	protected void createLines()
	{
		log.fine("Lines #" + m_lines.size());
		for (int i = 0; i < m_lines.size(); i++)
		{
			WFLine line = (WFLine)m_lines.get(i);
			Rectangle from = findBounds (line.getAD_WF_Node_ID());
			Rectangle to = findBounds (line.getAD_WF_Next_ID());
			line.setFromTo(from, to);
			//	same bounds as parent
		//	line.setBounds(0,0, width, height);
		}	//	for all lines
	}
	
	/**
	 * 	Get Bounds of WF Node Icon
	 * 	@param AD_WF_Node_ID node id
	 * 	@return bounds of node with ID or null
	 */
	private Rectangle findBounds (int AD_WF_Node_ID)
	{
		for (int i = 0; i < m_nodes.size(); i++)
		{
			WFNode node = (WFNode)m_nodes.get(i);
			if (node.getAD_WF_Node_ID() == AD_WF_Node_ID)
				return node.getBounds();
		}
		return null;
	}	//	findBounds

	public Dimension getDimension() 
	{
		if (needLayout())
			updateLayout();
		
		int width = 0;
		int height = 0;
		//	Go through all node
		for (int i = 0; i < m_nodes.size(); i++)
		{
			WFNode node = m_nodes.get(i);
			Rectangle rect = node.getBounds();
			if (rect.x + rect.width > width)
				width = rect.x + rect.width;
			if (rect.y + rect.height > height)
				height = rect.y + rect.height;
		}	//	for all components
		
		return new Dimension(width, height);
	}
	
	/**************************************************************************
	 * 	Paint Component.
	 * 	Paint Lines directly as not added.
	 *	@param g graphics
	 */
	public void paint(Graphics2D g)
	{
		
		for (int i = 0; i < m_nodes.size(); i++)
		{
			WFNode node = m_nodes.get(i);
			Rectangle rect = node.getBounds();
			g.setColor(Color.BLACK);
			g.drawRect(rect.x, rect.y, rect.width, rect.height);
			Graphics2D t = (Graphics2D) g.create(rect.x, rect.y, rect.width, rect.height); 
			node.paint(t);
			t.dispose();
		}	//	for all components
		
		createLines();
		
		//	Paint Lines
		for (int i = 0; i < m_lines.size(); i++)
		{
			WFLine line = (WFLine)m_lines.get(i);
			line.paint(g);
		}
	}	//	paint


	private void updateLayout() {
		int x = 5;
		int y = 5;for (int i = 0; i < m_nodes.size(); i++)
		{
			WFNode node = m_nodes.get(i);
			Rectangle rect = node.getBounds();
			node.setBounds(x, y, rect.width, rect.height);
			//	next pos
			if (x == 5)
				x = 230;
			else
			{
				x = 5;
				y += 100;
			}
		}
	}
	
	/**
	 * 	Need Layout
	 * 	@param parent parent
	 * 	@return true if we need to layout
	 */
	private boolean needLayout ()
	{
		//	Go through all components
		for (int i = 0; i < m_nodes.size(); i++)
		{
			WFNode node = m_nodes.get(i);
			Rectangle rect = node.getBounds();
			if (rect.x == 0 && rect.y == 0)
			{
				return true;
			}
		}
		return false;
	}	//	needLayout

	public ArrayList<WFNode> getNodes() {
		return m_nodes;
	}
	
}	//	WFContentPanel
