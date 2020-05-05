/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                        *
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
package org.compiere.grid.tree;

import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.TreePath;

import org.compiere.model.MTreeNode;

/**
 *  VTreeTransferHandler provides the TransferHandler for dragging and dropping
 *  within a tree.  See VTreePanel.
 *  
 *  
 *  @author 	phib  2008/07/30
 *  FR [ 2032092 ] Java 6 improvements to tree drag and drop
 */
class VTreeTransferHandler extends TransferHandler
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static class MoveTo
	{
		public MTreeNode to;
		public int indexTo;
	}

	@Override
	public int getSourceActions(JComponent c)
	{
		return TransferHandler.MOVE;
	}

	@Override
	protected Transferable createTransferable(JComponent c)
	{
		JTree tree = (JTree) c;
		MTreeNode node = (MTreeNode) tree.getSelectionPath().getLastPathComponent();
		return new TransferableTreeNode(node);
	}
	
	private final MTreeNode getNode(Transferable t)
	{
		MTreeNode node = null;
		try
		{
			node = (MTreeNode)t.getTransferData(TransferableTreeNode.TREE_NODE_FLAVOR);
		} 
		catch (Exception e)
		{
			return null;
		}
		return node;
	}

	@Override
	protected void exportDone(JComponent c, Transferable t, int action)
	{
		if (action == MOVE)
		{
			MTreeNode node = getNode(t);
			if (node == null)
				return;

			JTree tree = (JTree) c;
			AdempiereTreeModel model = (AdempiereTreeModel)tree.getModel();
			if (node.getParent() != null)
				model.removeNodeFromParent(node);
		}
	}

	@Override
	public boolean canImport(TransferSupport info)
	{
		// Check for flavor
		if (!info.isDataFlavorSupported(TransferableTreeNode.TREE_NODE_FLAVOR))
		{
			return false;
		}
//		return getNodeMove(info) != null;
		return true;
	}
	
	@Override
	public boolean importData(TransferHandler.TransferSupport info)
	{
		if (!canImport(info))
			return false;
		
		
		MTreeNode nodeNew = getNode(info.getTransferable());
		if (nodeNew == null)
		{
			return false;
		}

		final MoveTo move = getMoveTo(info);
		if (equals(nodeNew, move.to))
			return false;
		
		JTree tree = (JTree) info.getComponent();
		AdempiereTreeModel model = (AdempiereTreeModel) tree.getModel();
		
		
		// Update database
		// we need to do this in 2 steps (db and swing) because in case database updates fail
		// we should not update swing model
		List<MTreeNode> children = new ArrayList<MTreeNode>();
		Enumeration<?> en = move.to.children();
		while (en.hasMoreElements())
		{
			MTreeNode nd = (MTreeNode)en.nextElement();
			if (!equals(nd, nodeNew))
				children.add(nd);
		}
		children.add(move.indexTo, nodeNew);
		if (!model.saveChildren(move.to, children))
		{
			return false;
		}
		// Update Swing tree
		MTreeNode nodeOld = move.to.getChildById(nodeNew.getNode_ID());
		if (nodeOld != null)
		{
			model.removeNodeFromParent(nodeOld);
		}
		model.insertNodeInto(nodeNew, move.to, move.indexTo);
		tree.scrollPathToVisible(new TreePath(nodeNew.getPath()));   // display from's new location
		
		return true;
	}

	private final MoveTo getMoveTo(TransferSupport info)
	{
		MoveTo move = new MoveTo();
		if (info.isDrop())
		{
			JTree.DropLocation dl = (JTree.DropLocation)info.getDropLocation();
			TreePath path = dl.getPath();
			if (path == null)
				return null;
			move.to = (MTreeNode)path.getLastPathComponent();
			if (move.to == null)
			{
				return null;
			}

			move.indexTo = dl.getChildIndex();
			if (move.indexTo == -1)
				move.indexTo = 0; // insert as first child

		}
		else
		{ // it's a paste
			JTree tree = (JTree)info.getComponent();
			TreePath path = tree.getSelectionPath();
			if (path == null)
				return null;
			MTreeNode selected = (MTreeNode)path.getLastPathComponent();
			if (selected.isLeaf())
			{
				move.to = (MTreeNode)selected.getParent();
				move.indexTo = move.to.getIndex(selected) + 1; // insert after selected
			}
			else
			{
				move.to = selected;
				move.indexTo = 0;
			}
		}
		
		return move;
	}
	
	private boolean equals(MTreeNode node1, MTreeNode node2)
	{
		if(node1 == null || node2 == null)
			return false;
		if(node1 == node2)
			return true;
		return node1.getAD_Tree_ID() == node2.getAD_Tree_ID()
				&& node1.getAD_Table_ID() == node2.getAD_Table_ID()
				&& node1.getNode_ID() == node2.getNode_ID();
	}

}
