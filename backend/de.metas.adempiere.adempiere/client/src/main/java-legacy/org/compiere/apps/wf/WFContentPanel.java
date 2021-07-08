/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.apps.wf;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.workflow.WFNodeId;
import de.metas.workflow.service.IADWorkflowDAO;
import de.metas.workflow.service.WFNodeCreateRequest;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.apps.AMenuStartItem;
import org.compiere.model.I_AD_WF_NodeNext;
import org.compiere.swing.CMenuItem;
import org.compiere.swing.CPanel;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.TextLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Workflow Content Panel.
 *
 * @author Jorg Janke
 * @version $Id: WFContentPanel.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
class WFContentPanel extends CPanel
		implements MouseListener, MouseMotionListener, ActionListener
{
	private static final long serialVersionUID = 4868946962536126669L;

	private IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IADWorkflowDAO workflowDAO = Services.get(IADWorkflowDAO.class);

	/**
	 * WFContentPanel
	 */
	public WFContentPanel(WFPanel parent)
	{
		super(new WFLayoutManager());
		m_parent = parent;
		//
		m_NewPopupMenu.add(m_NewMenuNode);
		m_NewMenuNode.addActionListener(this);
	}    //	WFContentPanel

	private WFPanel m_parent = null;
	/**
	 * Logger
	 */
	private static Logger log = LogManager.getLogger(WFContentPanel.class);
	/**
	 * Node List
	 */
	private ArrayList<WFNodeComponent> m_nodes = new ArrayList<WFNodeComponent>();
	/**
	 * Line List
	 */
	private ArrayList<WFLine> m_lines = new ArrayList<WFLine>();
	/**
	 * Last Pressed Point
	 */
	private Point m_draggedStart = null;
	/**
	 * Last Dragged Node
	 */
	private WFNodeComponent m_draggedNode = null;
	/**
	 * Dragged
	 */
	private boolean m_dragged = false;

	/**
	 * ReadWrite
	 */
	private boolean m_readWrite = false;
	/**
	 * The Workflow
	 */
	private WorkflowModel m_wf = null;

	private JPopupMenu m_NewPopupMenu = new JPopupMenu();
	private CMenuItem m_NewMenuNode = new CMenuItem(msgBL.getMsg(Env.getCtx(), "CreateNewNode"));

	private JPopupMenu m_LinePopupMenu = null;

	/**
	 * Set Read/Write
	 *
	 * @param readWrite read/write
	 */
	public void setReadWrite(boolean readWrite)
	{
		m_readWrite = readWrite;
		if (m_readWrite)
			addMouseListener(this);
		else
			removeMouseListener(this);
	}    //	setReadWrite

	/**
	 * Set Workflow
	 *
	 * @param wf workflow
	 */
	public void setWorkflow(WorkflowModel wf)
	{
		m_wf = wf;
	}    //	setWorkflow

	/**
	 * Remove All and their listeners
	 */
	@Override
	public void removeAll()
	{
		m_nodes.clear();
		m_lines.clear();
		Component[] components = getComponents();
		for (int i = 0; i < components.length; i++)
		{
			Component component = components[i];
			component.removeMouseListener(this);
			component.removeMouseMotionListener(this);
		}
		super.removeAll();
	}    //	removeAll

	/**
	 * Add Component and add Mouse Listener
	 *
	 * @param comp component
	 * @param rw   read/write
	 * @return component
	 */
	public Component add(Component comp, boolean rw)
	{
		//	Line
		if (comp instanceof WFLine)
		{
			m_lines.add((WFLine)comp);
			return comp;
		}
		//	Node
		if (comp instanceof WFNodeComponent)
		{
			m_nodes.add((WFNodeComponent)comp);
			comp.addMouseListener(this);
			if (m_readWrite && rw)    //	can be moved
				comp.addMouseMotionListener(this);
		}
		return super.add(comp);
	}    //	add

	/**
	 * Create Lines.
	 * Called by WF Layout Manager
	 */
	protected void createLines()
	{
		log.debug("Lines #" + m_lines.size());
		for (int i = 0; i < m_lines.size(); i++)
		{
			WFLine line = m_lines.get(i);
			Rectangle from = findBounds(line.getFromNodeId());
			Rectangle to = findBounds(line.getToNodeId());
			line.setFromTo(from, to);
			//	same bounds as parent
			//	line.setBounds(0,0, width, height);
		}    //	for all lines
	}

	/**
	 * Get Bounds of WF Node Icon
	 *
	 * @return bounds of node with ID or null
	 */
	private Rectangle findBounds(final WFNodeId nodeId)
	{
		for (final WFNodeComponent node : m_nodes)
		{
			if (WFNodeId.equals(node.getNodeId(), nodeId))
			{
				return node.getBounds();
			}
		}
		return null;
	}    //	findBounds

	/**
	 * Get Component At point
	 *
	 * @param p point
	 * @return Node (ignore lines)
	 */
	@Override
	public Component getComponentAt(Point p)
	{
		return getComponentAt(p.x, p.y);
	}    //	getComponentAt

	/**
	 * Get Node at x/y
	 *
	 * @param x x
	 * @param y y
	 * @return Node (ignore lines)
	 */
	@Override
	public Component getComponentAt(int x, int y)
	{
		Component comp = super.getComponentAt(x, y);
		if (comp instanceof WFNodeComponent)
			return comp;
		for (int i = 0; i < m_nodes.size(); i++)
		{
			WFNodeComponent node = m_nodes.get(i);
			int xx = x - node.getX();
			int yy = y - node.getY();
			if (node.contains(xx, yy))
				return node;
		}
		return comp;
	}    //	getComponentAt

	/**************************************************************************
	 * 	Mouse Clicked.
	 * 	Pressed - Released - Clicked.
	 *    @param e event
	 */
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (m_readWrite && SwingUtilities.isRightMouseButton(e))
		{
			ClientId clientId = Env.getClientId();
			if (e.getSource() == this && m_wf != null)
			{
				m_NewPopupMenu.show(this, e.getX(), e.getY());
			}
			else if (e.getSource() instanceof WFNodeComponent)
			{
				WorkflowNodeModel node = ((WFNodeComponent)e.getSource()).getModel();
				m_LinePopupMenu = new JPopupMenu(node.getName().getDefaultValue());
				if (ClientId.equals(node.getClientId(), clientId))
				{
					String title = msgBL.getMsg(Env.getCtx(), "DeleteNode") + ": " + node.getName();
					addMenuItem(m_LinePopupMenu, title, node, null);
					m_LinePopupMenu.addSeparator();
				}
				List<WorkflowNodeModel> nodes = m_wf.getNodesInOrder(clientId);
				ImmutableList<WorkflowNodeTransitionModel> lines = node.getTransitions(clientId);
				//	Add New Line
				for (int n = 0; n < nodes.size(); n++)
				{
					final WorkflowNodeModel nn = nodes.get(n);
					if (nn.getId().equals(node.getId()))
					{
						continue;    //	same
					}

					boolean found = false;
					for (int i = 0; i < lines.size(); i++)
					{
						WorkflowNodeTransitionModel line = lines.get(i);
						if (nn.getId().equals(line.getNextNodeId()))
						{
							found = true;
							break;
						}
					}
					if (!found)
					{
						String title = msgBL.getMsg(Env.getCtx(), "AddLine")
								+ ": " + node.getName() + " -> " + nn.getName();
						addMenuItem(m_LinePopupMenu, title, node, nn.getId());
					}
				}
				m_LinePopupMenu.addSeparator();
				//	Delete Lines
				for (int i = 0; i < lines.size(); i++)
				{
					final WorkflowNodeTransitionModel line = lines.get(i);
					if (!line.getClientId().equals(clientId))
					{
						continue;
					}

					final WorkflowNodeModel next = line.getNextNode();
					String title = msgBL.getMsg(Env.getCtx(), "DeleteLine")
							+ ": " + node.getName() + " -> " + next.getName();
					addMenuItem(m_LinePopupMenu, title, line);
				}
				m_LinePopupMenu.show(this, e.getX(), e.getY());
			}
		}

		//	Selection
		else if (e.getSource() instanceof WFNodeComponent)
		{
			final WFNodeComponent selected = (WFNodeComponent)e.getSource();
			log.debug(selected.toString());
			for (int i = 0; i < m_nodes.size(); i++)
			{
				WFNodeComponent node = m_nodes.get(i);
				if (WFNodeId.equals(selected.getNodeId(), node.getNodeId()))
				{
					node.setSelected(true);
				}
				else
				{
					node.setSelected(false);
				}
			}

			// handle double click
			if (e.getClickCount() >= 2)
			{
				final WorkflowNodeModel model = selected.getModel();
				AMenuStartItem.startWFNode(
						model.getWorkflowId(),
						model.unbox(),
						m_parent.getAMenu()); // async load
			}
		}
		m_dragged = false;
	}    //	mouseClicked

	/**
	 * Mouse Entered
	 *
	 * @param e event
	 */
	@Override
	public void mouseEntered(MouseEvent e)
	{
	}    //	mouseEntered

	/**
	 * Mouse Exited
	 *
	 * @param e event
	 */
	@Override
	public void mouseExited(MouseEvent e)
	{
	}    //	mouseExited

	/**
	 * Mouse Pressed.
	 * Initial drag
	 *
	 * @param e event
	 */
	@Override
	public void mousePressed(MouseEvent e)
	{
		if (e.getSource() instanceof WFNodeComponent)
		{
			WFNodeComponent node = (WFNodeComponent)e.getSource();
			if (node.isEditable())
			{
				m_draggedNode = node;
				m_draggedStart = SwingUtilities.convertPoint(m_draggedNode, e.getX(), e.getY(), this);
			}
			else
			{
				m_dragged = false;
				m_draggedNode = null;
				m_draggedStart = null;
			}
		}
	}    //	mousePressed

	/**********************************
	 * 	Mouse Dragged
	 *    @param e event
	 */
	@Override
	public void mouseDragged(MouseEvent e)
	{
		//	Nothing selected
		if (m_draggedNode == null || e.getSource() != m_draggedNode)
		{
			if (e.getSource() instanceof WFNodeComponent)
			{
				WFNodeComponent node = (WFNodeComponent)e.getSource();
				if (node.isEditable())
					m_draggedNode = node;
				m_draggedStart = null;
			}
		}
		//	Move Node
		if (m_draggedNode != null)
		{
			m_dragged = true;
			if (m_draggedStart == null)
				m_draggedStart = SwingUtilities.convertPoint(m_draggedNode, e.getX(), e.getY(), this);
			//	If not converted to coordinate system of parent, it gets jumpy
			Point mousePosition = SwingUtilities.convertPoint(m_draggedNode, e.getX(), e.getY(), this);
			int xDelta = mousePosition.x - m_draggedStart.x;
			int yDelta = mousePosition.y - m_draggedStart.y;
			Point newLocation = m_draggedNode.getLocation();
			newLocation.x += xDelta;
			if (newLocation.x < 0)
				newLocation.x = 0;
			newLocation.y += yDelta;
			if (newLocation.y < 0)
				newLocation.y = 0;
			m_draggedNode.setLocation(newLocation.x, newLocation.y);
			//	log.debug("mouseDragged - " + m_draggedNode + " - " + e);
			//	log.debug("mouseDragged - Delta=" + xDelta + "/" + yDelta);
			m_draggedStart = mousePosition;
			invalidate();
			validate();
			repaint();
		}
	}    //	mouseDragged

	/**
	 * Mouse Released.
	 * Finals dragging
	 *
	 * @param e event
	 */
	@Override
	public void mouseReleased(MouseEvent e)
	{
		//	log.debug("mouseReleased - " + m_draggedNode);
		m_dragged = false;
		m_draggedNode = null;
		m_draggedStart = null;
		repaint();
	}    //	mouseReleased

	/**
	 * Mouse Moved
	 *
	 * @param e event
	 */
	@Override
	public void mouseMoved(MouseEvent e)
	{
	}    //	mouseMoved

	/**************************************************************************
	 * 	Paint Component.
	 * 	Paint Lines directly as not added.
	 *    @param g graphics
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//	Paint Lines
		for (int i = 0; i < m_lines.size(); i++)
		{
			WFLine line = m_lines.get(i);
			line.paint(g);
		}
		//	Paint Position = right next to the box
		if (m_dragged && m_draggedNode != null)
		{
			Point loc = m_draggedNode.getLocation();
			String text = "(" + loc.x + "," + loc.y + ")";
			Graphics2D g2D = (Graphics2D)g;
			Font font = new Font("Dialog", Font.PLAIN, 10);
			g2D.setColor(Color.magenta);
			TextLayout layout = new TextLayout(text, font, g2D.getFontRenderContext());
			loc.x += m_draggedNode.getWidth();
			loc.y += layout.getAscent();
			layout.draw(g2D, loc.x, loc.y);
		}
	}    //	paintComponents

	/**
	 * Add Menu Item to - add new line to node
	 *
	 * @param menu  base menu
	 * @param title title
	 */
	private void addMenuItem(JPopupMenu menu, String title, WorkflowNodeModel node, WFNodeId nodeToId)
	{
		WFPopupItem item = new WFPopupItem(title, node, nodeToId);
		menu.add(item);
		item.addActionListener(this);
	}    //	addMenuItem

	/**
	 * Add Menu Item to - delete line
	 *
	 * @param menu  base menu
	 * @param title title
	 */
	private void addMenuItem(JPopupMenu menu, String title, WorkflowNodeTransitionModel line)
	{
		WFPopupItem item = new WFPopupItem(title, line);
		menu.add(item);
		item.addActionListener(this);
	}    //	addMenuItem

	/**
	 * Action Listener
	 *
	 * @param e event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		log.info(e.toString());

		//	Add new Node
		if (e.getSource() == m_NewMenuNode)
		{
			log.info("Create New Node");
			String nameLabel = Util.cleanAmp(msgBL.getMsg(Env.getCtx(), "Name"));
			String name = JOptionPane.showInputDialog(this,
					nameLabel,            //	message
					msgBL.getMsg(Env.getCtx(), "CreateNewNode"),    //	title
					JOptionPane.QUESTION_MESSAGE);
			if (name != null && name.length() > 0)
			{
				workflowDAO.createWFNode(WFNodeCreateRequest.builder()
						.workflowId(m_wf.getId())
						.value(name)
						.name(name)
						.build());
				m_parent.load(m_wf.getId(), true);
			}
		}
		//	Add/Delete Line
		else if (e.getSource() instanceof WFPopupItem)
		{
			WFPopupItem item = (WFPopupItem)e.getSource();
			item.execute();
		}

	}    //	actionPerformed

	class WFPopupItem extends JMenuItem
	{
		private static final long serialVersionUID = 4634863991042969718L;

		public WFPopupItem(String title, WorkflowNodeModel node, WFNodeId nodeToId)
		{
			super(title);
			m_node = node;
			m_line = null;
			this.nodeToId = nodeToId;
		}    //	WFPopupItem

		/**
		 * Delete Line Item
		 *
		 * @param title title
		 * @param line  line to be deleted
		 */
		public WFPopupItem(String title, WorkflowNodeTransitionModel line)
		{
			super(title);
			m_node = null;
			m_line = line;
			nodeToId = null;
		}    //	WFPopupItem

		/**
		 * The Node
		 */
		private final WorkflowNodeModel m_node;
		/**
		 * The Line
		 */
		private final WorkflowNodeTransitionModel m_line;
		/**
		 * The Next Node ID
		 */
		private final WFNodeId nodeToId;

		/**
		 * Execute
		 */
		public void execute()
		{
			//	Add Line
			if (m_node != null && nodeToId != null)
			{
				final I_AD_WF_NodeNext newLine = InterfaceWrapperHelper.newInstance(I_AD_WF_NodeNext.class);
				newLine.setAD_Org_ID(OrgId.ANY.getRepoId());
				newLine.setAD_WF_Node_ID(m_node.getId().getRepoId());
				newLine.setAD_WF_Next_ID(nodeToId.getRepoId());
				InterfaceWrapperHelper.save(newLine);

				log.info("Add Line to " + m_node + " -> " + newLine);
				m_parent.load(m_wf.getId(), true);
			}
			//	Delete Node
			else if (m_node != null && nodeToId == null)
			{
				log.info("Delete Node: " + m_node);
				Services.get(IADWorkflowDAO.class).deleteNodeById(m_node.getId());
				m_parent.load(m_wf.getId(), true);
			}
			//	Delete Line
			else if (m_line != null)
			{
				log.info("Delete Line: " + m_line);
				Services.get(IADWorkflowDAO.class).deleteNodeTransitionById(m_line.getId());
				m_parent.load(m_wf.getId(), true);
			}
			else
				log.error("No Action??");
		}    //	execute

	}    //	WFPopupItem

}    //	WFContentPanel
