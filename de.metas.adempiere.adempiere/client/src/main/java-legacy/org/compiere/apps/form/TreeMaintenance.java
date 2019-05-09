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
package org.compiere.apps.form;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.tree.IPOTreeSupportFactory;
import org.adempiere.model.tree.spi.IPOTreeSupport;
import org.compiere.model.MTree;
import org.compiere.model.MTreeNode;
import org.compiere.model.MTree_Node;
import org.compiere.model.MTree_NodeBP;
import org.compiere.model.MTree_NodeMM;
import org.compiere.model.MTree_NodePR;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;
import de.metas.security.permissions.Access;
import de.metas.util.Services;


public class TreeMaintenance {

	/**	Window No				*/
	public int         	m_WindowNo = 0;
	/**	Active Tree				*/
	public MTree		 	m_tree;
	/**	Logger			*/
	public static Logger log = LogManager.getLogger(TreeMaintenance.class);
	
	public KeyNamePair[] getTreeData()
	{
		return DB.getKeyNamePairs(Env.getUserRolePermissions().addAccessSQL(
				"SELECT AD_Tree_ID, Name FROM AD_Tree WHERE TreeType NOT IN ('BB') ORDER BY 2", 
				"AD_Tree", IUserRolePermissions.SQL_NOTQUALIFIED, Access.WRITE), false);
	}
	
	public ArrayList<ListItem> getTreeItemData()
	{
		//
		// services
		final IPOTreeSupportFactory treeSupportFactory = Services.get(IPOTreeSupportFactory.class);
		
		final ArrayList<ListItem> data = new ArrayList<ListItem>();

		final String sourceTableName = m_tree.getSourceTableName();
		final IPOTreeSupport poTreeSupport = treeSupportFactory.get(m_tree.getSourceTableName());
		
		// If we are running on system level, don't enforce any role access because we want to see all items (task 09562).
		// E.g. if we are looking at AD_Menu entries we want to see all of them, not only those which system can access. 
		if (Env.getAD_Client_ID(Env.getCtx()) == Env.CTXVALUE_AD_Client_ID_System)
		{
			poTreeSupport.disableRoleAccessCheckWhileLoading();
		}
		
		final List<Object> sqlParams = new ArrayList<>();
		String sql = poTreeSupport.getNodeInfoSelectSQL(m_tree, sqlParams);
		sql = Env.getUserRolePermissions().addAccessSQL(sql, sourceTableName, IUserRolePermissions.SQL_FULLYQUALIFIED, Access.READ);
		sql += " ORDER BY 2";
		log.info(sql);
		//	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				MTreeNode info = poTreeSupport.loadNodeInfo(m_tree, rs);
				// metas 01386: Unable to obtain info about this item (probably we don't have the appropriate rights)
				if (info == null)
				{
					continue;
				}
				// metas end
				ListItem item = new ListItem(
						info.getNode_ID(),
						info.getName(), info.getDescription(),
					info.getAllowsChildren(), info.getImageIndiactor());
				data.add(item);
			}
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return data;
	}
	
	/**
	 * 	Action: Add Node to Tree
	 * 	@param item item
	 */
	public void addNode(ListItem item)
	{
		if (item != null)
		{
			//	May cause Error if in tree
			if (m_tree.isProduct())
			{
				MTree_NodePR node = new MTree_NodePR (m_tree, item.id);
				node.save();
			}
			else if (m_tree.isBPartner())
			{
				MTree_NodeBP node = new MTree_NodeBP (m_tree, item.id);
				node.save();
			}
			else if (m_tree.isMenu())
			{
				MTree_NodeMM node = new MTree_NodeMM (m_tree, item.id);
				node.save();
			}
			else
			{
				MTree_Node node = new MTree_Node (m_tree, item.id);
				node.save();
			}
		}
	}	//	action_treeAdd
	
	/**
	 * 	Action: Delete Node from Tree
	 * 	@param item item
	 */
	public void deleteNode(ListItem item)
	{
		if (item != null)
		{
			if (m_tree.isProduct())
			{
				MTree_NodePR node = MTree_NodePR.get (m_tree, item.id);
				if (node != null)
					node.delete(true);
			}
			else if (m_tree.isBPartner())
			{
				MTree_NodeBP node = MTree_NodeBP.get (m_tree, item.id);
				if (node != null)
					node.delete(true);
			}
			else if (m_tree.isMenu())
			{
				MTree_NodeMM node = MTree_NodeMM.get (m_tree, item.id);
				if (node != null)
					node.delete(true);
			}
			else
			{
				MTree_Node node = MTree_Node.get (m_tree, item.id);
				if (node != null)
					node.delete(true);
			}
		}
	}	//	action_treeDelete
	
	/**************************************************************************
	 * 	Tree Maintenance List Item
	 */
	public class ListItem
	{
		/**
		 * 	ListItem
		 *	@param ID
		 *	@param Name
		 *	@param Description
		 *	@param summary
		 *	@param ImageIndicator
		 */
		public ListItem (int ID, String Name, String Description, 
			boolean summary, String ImageIndicator)
		{
			id = ID;
			name = Name;
			description = Description;
			isSummary = summary;
			imageIndicator = ImageIndicator;
		}	//	ListItem
		
		/**	ID			*/
		public int id;
		/** Name		*/
		public String name;
		/** Description	*/
		public String description;
		/** Summary		*/
		public boolean isSummary;
		/** Indicator	*/
		public String imageIndicator;  //  Menu - Action
		
		/**
		 * 	To String
		 *	@return	String Representation
		 */
		@Override
		public String toString ()
		{
			String retValue = name;
			if (description != null && description.length() > 0)
				retValue += " (" + description + ")";
			return retValue;
		}	//	toString
		
	}	//	ListItem
}
