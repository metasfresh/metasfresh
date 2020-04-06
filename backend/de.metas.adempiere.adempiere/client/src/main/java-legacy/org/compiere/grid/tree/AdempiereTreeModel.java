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

import java.util.List;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;

import javax.swing.tree.DefaultTreeModel;

import org.adempiere.model.tree.IADTreeBL;
import org.compiere.apps.ADialog;
import org.compiere.model.MTree;
import org.compiere.model.MTreeNode;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

/**
 *  AdempiereTreeModel provides a persistable tree model based on an MTree.
 *
 *  @author 	phib  2008/07/30
 *  FR [ 2032092 ] Java 6 improvements to tree drag and drop
 */
class AdempiereTreeModel extends DefaultTreeModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8503954687681402088L;

	/**	Logger			*/
	private static Logger log = LogManager.getLogger(AdempiereTreeModel.class);
	
	private MTree m_MTree;
	
	public AdempiereTreeModel()
	{
		this(null); // root=null
	}
	
	public AdempiereTreeModel(final MTreeNode root)
	{
		super(root, true); // asksAllowsChildren=true
	}

	public void setMTree(MTree tree)
	{
		m_MTree = tree;
	}
	
	@Override
	public MTreeNode getRoot()
	{
		return (MTreeNode)super.getRoot();
	}
	
	public boolean saveChildren(MTreeNode parent)
	{
		return saveChildren(parent, parent.getChildrenList());
	}
	public boolean saveChildren(MTreeNode parent, List<MTreeNode> children)
	{
		try
		{
			m_MTree.updateNodeChildren(parent, children);
		}
		catch (Exception e)
		{
			ADialog.error(0, null, "Error", e.getLocalizedMessage());
			log.error(e.getLocalizedMessage(), e);
			return false;
		}
		return true;
	}
	
	// metas: begin
	public void filterIds(final List<Integer> ids)
	{
		Check.assumeNotNull(ids, "Param 'ids' is not null");
		
		Services.get(IADTreeBL.class).filterIds(getRoot(), ids);
		reload();
	}
	// metas: end
}
