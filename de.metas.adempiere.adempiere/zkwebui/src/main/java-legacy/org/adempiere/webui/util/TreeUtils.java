/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
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
package org.adempiere.webui.util;

import java.util.List;

import org.compiere.model.MTreeNode;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

/**
 * 
 * @author hengsin
 *
 */
public class TreeUtils {

	/**
	 * Collapse all nodes
	 * @param tree
	 */
	public static void collapseAll(Tree tree) {
		traverse(tree, new TreeItemAction() {
			public void run(Treeitem treeItem) {
				treeItem.setOpen(false);
			}
			
		});
	}
	
	/**
	 * Expand all nodes
	 * @param tree
	 */
	public static void expandAll(Tree tree) {
		traverse(tree, new TreeItemAction() {
			public void run(Treeitem treeItem) {
				treeItem.setOpen(true);
			}			
		});
	}
	
	/**
	 * Traverse tree and execution action on Treeitem
	 * @param tree
	 * @param action
	 */
	public static void traverse(Tree tree, TreeItemAction action)
	{
		Treechildren treechildren = tree.getTreechildren();
		traverse(treechildren, action);
	}
	
	/**
	 * Traverse treechildren and execution action on Treeitem
	 * @param treechildren
	 * @param action
	 */
	public static void traverse(Treechildren treechildren, TreeItemAction action)
	{
		List<?> list = treechildren.getChildren();
		for(int index = 0; index < list.size(); index++)
		{
			Object o = list.get(index);
			if(o instanceof Treechildren)
			{
				Treechildren treechild = (Treechildren) o;
				traverse(treechild, action);
			}
			else if(o instanceof Treeitem)
			{
				Treeitem treeitem = (Treeitem) o;
				action.run(treeitem);
	
				List<?> treeitemChildren = treeitem.getChildren();
				for(int childIndex = 0; childIndex < treeitemChildren.size(); childIndex++)
				{
					Object child = treeitemChildren.get(childIndex);
					if(child instanceof Treechildren)
					{
						Treechildren treechild = (Treechildren) child;
						traverse(treechild, action);
					}
				}
			}
		}
	}

	/**
	 * Traverse tree model and execution action on tree node
	 * @param model
	 * @param action
	 */
	public static void traverse(TreeModel model, TreeNodeAction action) {
		traverse(model, model.getRoot(), action);
		
	}

	/**
	 * Traverse tree model from parent and execution action on tree node
	 * @param model
	 * @param parent
	 * @param action
	 */
	public static void traverse(TreeModel model, Object parent,
			TreeNodeAction action) {
		int count = model.getChildCount(parent);
		for(int i = 0; i < count; i++) {
			Object child = model.getChild(parent, i);
			if (child instanceof MTreeNode) {
				action.run((MTreeNode) child);
			}
			traverse(model, child, action);
		}
	}
}
