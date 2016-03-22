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

import org.compiere.model.MTree;
import org.compiere.model.MTreeNode;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

/**
 * 
 * @author Low Heng Sin
 *
 */
public class ADTreeOnDropListener implements EventListener {
	
	private SimpleTreeModel treeModel;
	private MTree mTree;
	private int windowNo;
	private Tree tree;
	
	private static final Logger log = LogManager.getLogger(ADTreeOnDropListener.class);

	/**
	 * 
	 * @param tree
	 * @param model
	 * @param mTree
	 * @param windowNo
	 */
	public ADTreeOnDropListener(Tree tree, SimpleTreeModel model, MTree mTree, int windowNo) {
		this.tree = tree;
		this.treeModel = model;
		this.mTree = mTree;
		this.windowNo = windowNo;
	}

	/**
	 * @param event
	 */
	public void onEvent(Event event) throws Exception {
		if (event instanceof DropEvent) {
			DropEvent de = (DropEvent) event;
			log.debug("Source=" + de.getDragged() + " Target=" + de.getTarget());
			if (de.getDragged() != de.getTarget()) {
				Treeitem src = (Treeitem) ((Treerow) de.getDragged()).getParent();
				Treeitem target = (Treeitem) ((Treerow) de.getTarget()).getParent();
				moveNode((MTreeNode)src.getValue(), (MTreeNode)target.getValue());
			}
		} 
	}
	
	/**
	 *	Move TreeNode
	 *	@param	movingNode	The node to be moved
	 *	@param	toNode		The target node
	 */
	private void moveNode(MTreeNode movingNode, MTreeNode toNode)
	{
		log.info(movingNode.toString() + " to " + toNode.toString());

		if (movingNode == toNode)
			return;
		
		if (!toNode.isSummary())	//	drop on a child node
		{
			moveNode(movingNode, toNode, false);
		}
		else						//	drop on a summary node
		{
			//prompt user to select insert after or drop into the summary node
			int path[] = treeModel.getPath(treeModel.getRoot(), toNode);
			Treeitem toItem = tree.renderItemByPath(path);
			
			tree.setSelectedItem(toItem);
			Events.sendEvent(tree, new Event(Events.ON_SELECT, tree));
			
			MenuListener listener = new MenuListener(movingNode, toNode);
			
			//TODO: translation
			Menupopup popup = new Menupopup();
			Menuitem menuItem = new Menuitem("Insert After");
			menuItem.setValue("InsertAfter");
			menuItem.setParent(popup);
			menuItem.addEventListener(Events.ON_CLICK, listener);
			
			menuItem = new Menuitem("Move Into");
			menuItem.setValue("MoveInto");
			menuItem.setParent(popup);
			menuItem.addEventListener(Events.ON_CLICK, listener);
			
			popup.setPage(tree.getPage());
			popup.open(toItem.getTreerow());
		}
		
	}	//	moveNode
	
	private void moveNode(MTreeNode movingNode, MTreeNode toNode, boolean moveInto)
	{
		MTreeNode newParent;
		int index;		
		
		//  remove
		MTreeNode oldParent = treeModel.getParent(movingNode);
		treeModel.removeNode(movingNode);
		
		//get new index
		if (!moveInto)
		{
			newParent = treeModel.getParent(toNode);
			index = newParent.getIndex(toNode) + 1;	//	the next node
		}
		else									//	drop on a summary node
		{
			newParent = toNode;
			index = 0;                   			//	the first node
		}
		
		//  insert
		treeModel.addNode(newParent, movingNode, index);

		//	***	Save changes to disk
		if (oldParent != newParent)
			mTree.updateNodeChildren(oldParent, oldParent.getChildrenList());
		mTree.updateNodeChildren(newParent, newParent.getChildrenList());
//		FDialog.error(windowNo, tree, "TreeUpdateError", e.getLocalizedMessage());
		
		int path[] = treeModel.getPath(treeModel.getRoot(), movingNode);
		Treeitem movingItem = tree.renderItemByPath(path);		
		tree.setSelectedItem(movingItem);
		Events.sendEvent(tree, new Event(Events.ON_SELECT, tree));
	}
	
	class MenuListener implements EventListener {
		private MTreeNode movingNode;
		private MTreeNode toNode;
		MenuListener(MTreeNode movingNode, MTreeNode toNode) {
			this.movingNode = movingNode;
			this.toNode = toNode;
		}
		public void onEvent(Event event) throws Exception {
			if (Events.ON_CLICK.equals(event.getName()) && event.getTarget() instanceof Menuitem) {
				Menuitem menuItem = (Menuitem) event.getTarget();
				if ("InsertAfter".equals(menuItem.getValue())) {
					moveNode(movingNode, toNode, false);
				} else if ("MoveInto".equals(menuItem.getValue())) {
					moveNode(movingNode, toNode, true);
				}
			}
		}
		
	}
}
