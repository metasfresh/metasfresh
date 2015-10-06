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
package org.adempiere.webui.component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.model.tree.IADTreeBL;
import org.adempiere.util.Colors;
import org.adempiere.util.Services;
import org.compiere.model.MTree;
import org.compiere.model.MTreeNode;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.zkoss.lang.Objects;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treecols;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.event.TreeDataEvent;

/**
 * 
 * @author Low Heng Sin
 *
 */
public class SimpleTreeModel extends org.zkoss.zul.AbstractTreeModel implements TreeitemRenderer, EventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4649471521757131755L;

	private static final CLogger logger = CLogger.getCLogger(SimpleTreeModel.class);
	
	private boolean itemDraggable;
	private List<EventListener> onDropListners = new ArrayList<EventListener>();

	public SimpleTreeModel(MTreeNode root) {
		super(root);
	}
	
	/**
	 * @param tree
	 * @param AD_Tree_ID
	 * @param windowNo
	 * @return SimpleTreeModel
	 */
	public static SimpleTreeModel initADTree(Tree tree, int AD_Tree_ID, int windowNo) {
		return initADTree(tree, AD_Tree_ID, windowNo, true, null);
	}
	
	/**
	 * @param tree
	 * @param AD_Tree_ID
	 * @param windowNo
	 * @param editable
	 * @param trxName
	 * @return SimpleTreeModel
	 */
	public static SimpleTreeModel initADTree(Tree tree, int AD_Tree_ID, int windowNo, boolean editable, String trxName) { 
		MTree vTree = new MTree (Env.getCtx(), AD_Tree_ID, editable, true, trxName);
		MTreeNode root = vTree.getRoot();
		SimpleTreeModel treeModel = SimpleTreeModel.createFrom(root);
		treeModel.m_MTree = vTree; // metas
		treeModel.setItemDraggable(true);
		treeModel.addOnDropEventListener(new ADTreeOnDropListener(tree, treeModel, vTree, windowNo));
		
		Treecols treeCols = new Treecols();
		tree.appendChild(treeCols);
		Treecol treeCol = new Treecol();
		treeCols.appendChild(treeCol);
		tree.setPageSize(-1);
		try {
			tree.setTreeitemRenderer(treeModel);
			tree.setModel(treeModel);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to setup tree");
		}
		
		return treeModel;
	}
	
	/**
	 * 
	 * @param root
	 * @return SimpleTreeModel
	 */
	public static SimpleTreeModel createFrom(MTreeNode root) {
		SimpleTreeModel model = new SimpleTreeModel(root);
		return model;
	}

	/**
	 * @param ti
	 * @param node
	 */
	public void render(Treeitem ti, Object node) {
		Treecell tc = new Treecell(Objects.toString(node));
		StringBuffer style = new StringBuffer(); // metas;
		style.append("white-space: nowrap; "); // metas
		tc.setTooltiptext(Objects.toString(node)); //metas
		Treerow tr = null;
		if(ti.getTreerow()==null){
			tr = new Treerow();
			tr.setParent(ti);
			if (isItemDraggable()) {
				tr.setDraggable("true");
			}
			if (!onDropListners.isEmpty()) {
				tr.setDroppable("true");
				tr.addEventListener(Events.ON_DROP, this);
			}
		}else{
			tr = ti.getTreerow(); 
			tr.getChildren().clear();
		}				
		tc.setParent(tr);
		
		ti.setValue(node);
		
		// metas: begin
		if (node instanceof MTreeNode)
		{
			MTreeNode tn = (MTreeNode)node;
			if (tn.getColor() != null)
				style.append("color: "+Colors.toHtmlColor(tn.getColor())+"; ");
			if (tn.getBackgroundColor() != null)
				style.append("background-color: "+Colors.toHtmlColor(tn.getBackgroundColor())+"; ");
		}
		tc.setStyle(style.toString());
		// metas: end
	}

	/**
	 * Add to root
	 * @param newNode
	 */
	public void addNode(MTreeNode newNode) {
		MTreeNode root = (MTreeNode) getRoot();
		root.add(newNode);
		fireEvent(root, root.getChildCount() - 1, root.getChildCount() - 1, TreeDataEvent.INTERVAL_ADDED);
	}

	@Override
	public MTreeNode getRoot() {
		return (MTreeNode) super.getRoot();
	}

	@Override
	public MTreeNode getChild(Object parent, int index) {
		MTreeNode node = (MTreeNode)parent;
		return (MTreeNode)node.getChildAt(index);
	}

	/**
	 * @param treeNode
	 */
	public void removeNode(MTreeNode treeNode) {
		int path[] = this.getPath(getRoot(), treeNode);
		
		if (path != null && path.length > 0) {
			MTreeNode parentNode = getRoot();
			int index = path.length - 1;
			for (int i = 0; i < index; i++) {
				parentNode = getChild(parentNode, path[i]);
			}
			

			parentNode.remove(treeNode);
			fireEvent(parentNode, path[index], path[index], TreeDataEvent.INTERVAL_REMOVED);
		}
	}
	
	/**
	 * @param b
	 */
	public void setItemDraggable(boolean b) {
		itemDraggable = b;
	}
	
	/**
	 * @return boolean
	 */
	public boolean isItemDraggable() {
		return itemDraggable;
	}
	
	/**
	 * @param listener
	 */
	public void addOnDropEventListener(EventListener listener) {
		onDropListners.add(listener);
	}

	/**
	 * @param event
	 * @see EventListener#onEvent(Event)
	 */
	public void onEvent(Event event) throws Exception {
		if (Events.ON_DROP.equals(event.getName())) {
			for (EventListener listener : onDropListners) {
				listener.onEvent(event);
			}
		}
	}

	/**
	 * @param treeNode
	 * @return MTreeNode
	 */
	public MTreeNode getParent(MTreeNode treeNode) {
		return (MTreeNode)treeNode.getParent();
	}

	/**
	 * @param newParent
	 * @param newNode
	 * @param index
	 */
	public void addNode(MTreeNode newParent, MTreeNode newNode, int index) {
		newParent.insert(newNode, index);
		fireEvent(newParent, index, index, TreeDataEvent.INTERVAL_ADDED);
	}
	
	/**
	 * @param fromNode
	 * @param recordId
	 * @return MTreeNode
	 */
	public MTreeNode find(MTreeNode fromNode, int recordId) {
		MTreeNode node = fromNode;
		if (node == null)
			node = getRoot();
		return node.findNode(recordId);
	}
	
	/**
	 * @param node
	 */
	public void nodeUpdated(MTreeNode node) {
		MTreeNode parent = getParent(node);
		if (parent != null)
		{
			int i = parent.getIndex(node);
			fireEvent(parent, i, i, TreeDataEvent.CONTENTS_CHANGED);
		}
		else
		{
			// root node changed
			fireEvent(node, 0, 0, TreeDataEvent.CONTENTS_CHANGED);
		}
	}
	// metas: begin
	private MTree m_MTree;
	public void filterIds(List<Integer> ids)
	{
		Services.get(IADTreeBL.class).filterIds(getRoot(), ids);
		nodeUpdated(getRoot());
	}

	@Override
	public int getChildCount(Object parent)
	{
		MTreeNode node = (MTreeNode)parent;
		return node.getChildCount();
	}

	@Override
	public boolean isLeaf(Object node)
	{
		MTreeNode tn = (MTreeNode)node;
		return !tn.isSummary();
	}
	public MTree getAD_Tree()
	{
		return m_MTree;
	}
	// metas: end
}
