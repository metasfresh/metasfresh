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
package org.compiere.apps.form;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import org.slf4j.Logger;

import de.metas.i18n.Msg;
import de.metas.logging.LogManager;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.adempiere.images.Images;
import org.compiere.grid.tree.VTreePanel;
import org.compiere.model.MTree;
import org.compiere.model.MTreeNode;
import org.compiere.swing.CButton;
import org.compiere.swing.CCheckBox;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/**
 *	Tree Maintenance
 *	
 *  @author Jorg Janke
 *  @version $Id: VTreeMaintenance.java,v 1.3 2006/07/30 00:51:28 jjanke Exp $
 */
public class VTreeMaintenance extends TreeMaintenance
	implements FormPanel, ActionListener, ListSelectionListener, PropertyChangeListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3679450924195670486L;
	
	private CPanel panel = new CPanel();
	
	/**	FormFrame				*/
	private FormFrame 		m_frame;	
	
	private BorderLayout	mainLayout	= new BorderLayout ();
	private CPanel 			northPanel	= new CPanel ();
	private FlowLayout		northLayout	= new FlowLayout ();
	private CLabel			treeLabel	= new CLabel ();
	private CComboBox		treeField;
	private CButton bAddAll = new CButton(Images.getImageIcon2("FastBack24"));
	private CButton bAdd = new CButton(Images.getImageIcon2("StepBack24"));
	private CButton bDelete = new CButton(Images.getImageIcon2("StepForward24"));
	private CButton bDeleteAll = new CButton(Images.getImageIcon2("FastForward24"));
	private CCheckBox		cbAllNodes	= new CCheckBox ();
	private CLabel			treeInfo	= new CLabel ();
	//
	private JSplitPane		splitPane	= new JSplitPane ();
	private VTreePanel		centerTree;
	private JList			centerList	= new JList ();

	
	/**
	 *	Initialize Panel
	 *  @param WindowNo window
	 *  @param frame frame
	 */
	@Override
	public void init (int WindowNo, FormFrame frame)
	{
		m_WindowNo = WindowNo;
		m_frame = frame;
		log.info( "VMerge.init - WinNo=" + m_WindowNo);
		try
		{
			preInit();
			jbInit ();
			frame.getContentPane().add(panel, BorderLayout.CENTER);
		//	frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
			action_loadTree();
		}
		catch (Exception ex)
		{
			log.error("VTreeMaintenance.init", ex);
		}
	}	//	init
	
	/**
	 * 	Fill Tree Combo
	 */
	private void preInit()
	{
		treeField = new CComboBox(getTreeData());
		treeField.addActionListener(this);
		//
		centerTree = new VTreePanel (m_WindowNo, false, true);
		centerTree.addPropertyChangeListener(VTreePanel.PROPERTY_ExecuteNode, this);
	}	//	preInit
	
	/**
	 * 	Static init
	 *	@throws Exception
	 */
	private void jbInit () throws Exception
	{
		panel.setLayout (mainLayout);
		treeLabel.setText (Msg.translate(Env.getCtx(), "AD_Tree_ID"));
		cbAllNodes.setEnabled (false);
		cbAllNodes.setText (Msg.translate(Env.getCtx(), "IsAllNodes"));
		treeInfo.setText (" ");
		bAdd.setToolTipText("Add to Tree");
		bAddAll.setToolTipText("Add ALL to Tree");
		bDelete.setToolTipText("Delete from Tree");
		bDeleteAll.setToolTipText("Delete ALL from Tree");
		bAdd.addActionListener(this);
		bAddAll.addActionListener(this);
		bDelete.addActionListener(this);
		bDeleteAll.addActionListener(this);
		northPanel.setLayout (northLayout);
		northLayout.setAlignment (FlowLayout.LEFT);
		//
		panel.add (northPanel, BorderLayout.NORTH);
		northPanel.add (treeLabel, null);
		northPanel.add (treeField, null);
		northPanel.add (cbAllNodes, null);
		northPanel.add (treeInfo, null);
		northPanel.add (bAddAll, null);
		northPanel.add (bAdd, null);
		northPanel.add (bDelete, null);
		northPanel.add (bDeleteAll, null);
		//
		panel.add (splitPane, BorderLayout.CENTER);
		splitPane.add (centerTree, JSplitPane.LEFT);
		splitPane.add (new JScrollPane(centerList), JSplitPane.RIGHT);
		centerList.setSelectionMode (ListSelectionModel.SINGLE_SELECTION);
		centerList.addListSelectionListener(this);
	}	//	jbInit

	/**
	 * 	Dispose
	 */
	@Override
	public void dispose()
	{
		if (m_frame != null)
			m_frame.dispose();
		m_frame = null;
	}	//	dispose

	/**
	 * 	Action Listener
	 *	@param e event
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		if (e.getSource() == treeField)
			action_loadTree();
		else if (e.getSource() == bAddAll)
			action_treeAddAll();
		else if (e.getSource() == bAdd)
			action_treeAdd((ListItem)centerList.getSelectedValue());
		else if (e.getSource() == bDelete)
			action_treeDelete((ListItem)centerList.getSelectedValue());
		else if (e.getSource() == bDeleteAll)
			action_treeDeleteAll();
	}	//	actionPerformed

	
	/**
	 * 	Action: Fill Tree with all nodes
	 */
	private void action_loadTree()
	{
		KeyNamePair tree = (KeyNamePair)treeField.getSelectedItem();
		log.info("Tree=" + tree);
		if (tree.getKey() <= 0)
		{
			centerList.setModel(new DefaultListModel());
			return;
		}
		//	Tree
		m_tree = new MTree (Env.getCtx(), tree.getKey(), null);
		cbAllNodes.setSelected(m_tree.isAllNodes());
		bAddAll.setEnabled(!m_tree.isAllNodes());
		bAdd.setEnabled(!m_tree.isAllNodes());
		bDelete.setEnabled(!m_tree.isAllNodes());
		bDeleteAll.setEnabled(!m_tree.isAllNodes());
		//
		
		//	List
		DefaultListModel model = new DefaultListModel();
		ArrayList<ListItem> items = getTreeItemData();
		for(ListItem item : items)
			model.addElement(item);
		
		//	List
		log.info("#" + model.getSize());
		centerList.setModel(model);
		//	Tree
		centerTree.initTree(m_tree.getAD_Tree_ID());
	}	//	action_fillTree
	
	/**
	 * 	List Selection Listener
	 *	@param e event
	 */
	@Override
	public void valueChanged (ListSelectionEvent e)
	{
		if (e.getValueIsAdjusting())
			return;
		ListItem selected = null;
		try
		{	//	throws a ArrayIndexOutOfBoundsException if root is selected
			selected = (ListItem)centerList.getSelectedValue();
		}
		catch (Exception ex)
		{
		}
		log.info("Selected=" + selected);
		if (selected != null)	//	allow add if not in tree
			bAdd.setEnabled(!centerTree.setTreeSelectionPath(selected.id));
	}	//	valueChanged
	
	/**
	 * 	VTreePanel Changed
	 *	@param e event
	 */
	@Override
	public void propertyChange (PropertyChangeEvent e)
	{
		MTreeNode tn = (MTreeNode)e.getNewValue();
		log.info(tn.toString());
		if (tn == null)
			return;
		ListModel model = centerList.getModel();
		int size = model.getSize();
		int index = -1;
		for (index = 0; index < size; index++)
		{
			ListItem item = (ListItem)model.getElementAt(index);
			if (item.id == tn.getNode_ID())
				break;
		}
		centerList.setSelectedIndex(index);
	}	//	propertyChange

	/**
	 * 	Action: Add Node to Tree
	 * 	@param item item
	 */
	private void action_treeAdd(ListItem item)
	{
		log.info("Item=" + item);
		if (item != null)
		{
			MTreeNode info = new MTreeNode(item.id, 0, item.name, item.description, -1,
					item.isSummary, item.imageIndicator, false, null);
			centerTree.nodeChanged(true, info);
			
			//	May cause Error if in tree
			addNode(item);
		}
	}	//	action_treeAdd
	
	/**
	 * 	Action: Delete Node from Tree
	 * 	@param item item
	 */
	private void action_treeDelete(ListItem item)
	{
		log.info("Item=" + item);
		if (item != null)
		{
			MTreeNode info = new MTreeNode(item.id, 0, item.name, item.description, -1,
					item.isSummary, item.imageIndicator, false, null);
			centerTree.nodeChanged(false, info);
			
			deleteNode(item);
		}
	}	//	action_treeDelete

	
	/**
	 * 	Action: Add All Nodes to Tree
	 */
	private void action_treeAddAll()
	{
		log.info("");
		ListModel model = centerList.getModel();
		int size = model.getSize();
		int index = -1;
		for (index = 0; index < size; index++)
		{
			ListItem item = (ListItem)model.getElementAt(index);
			action_treeAdd(item);
		}
	}	//	action_treeAddAll
	
	/**
	 * 	Action: Delete All Nodes from Tree
	 */
	private void action_treeDeleteAll()
	{
		log.info("");
		ListModel model = centerList.getModel();
		int size = model.getSize();
		int index = -1;
		for (index = 0; index < size; index++)
		{
			ListItem item = (ListItem)model.getElementAt(index);
			action_treeDelete(item);
		}
	}	//	action_treeDeleteAll	

}	//	VTreeMaintenance
