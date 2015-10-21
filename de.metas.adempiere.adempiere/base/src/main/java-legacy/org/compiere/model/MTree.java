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
package org.compiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import javax.sql.RowSet;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.tree.IPOTreeSupportFactory;
import org.adempiere.model.tree.spi.IPOTreeSupport;
import org.adempiere.util.Services;
import org.compiere.util.CLogMgt;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;


/**
 *  Builds Tree.
 *  Creates tree structure - maintained in VTreePanel
 *
 *  @author     Jorg Janke
 *  @version    $Id: MTree.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class MTree extends MTree_Base
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6412057411585787707L;


	/**
	 *  Default Constructor.
	 * 	Need to call loadNodes explicitly
	 * 	@param ctx context for security
	 *  @param AD_Tree_ID   The tree to build
	 *  @param trxName transaction
	 */
	public MTree (Properties ctx, int AD_Tree_ID, String trxName)
	{
		super (ctx, AD_Tree_ID, trxName);
	}   //  MTree

	/**
	 *  Construct & Load Tree
	 *  @param AD_Tree_ID   The tree to build
	 *  @param editable     True, if tree can be modified
	 *  - includes inactive and empty summary nodes
	 * 	@param ctx context for security
	 *	@param clientTree the tree is displayed on the java client (not on web)
	 *  @param trxName transaction
	 */
	public MTree (Properties ctx, int AD_Tree_ID, 
		boolean editable, boolean clientTree, String trxName)
	{
		this (ctx, AD_Tree_ID, editable, clientTree, false, trxName);
	}   //  MTree

	public MTree (Properties ctx, int AD_Tree_ID, 
			boolean editable, boolean clientTree, boolean allNodes, String trxName)
	{
		this (ctx, AD_Tree_ID, trxName);
		m_editable = editable;
		m_allNodes = allNodes;
		m_clientTree = clientTree;
		log.info("AD_Tree_ID=" + AD_Tree_ID
				+ ", Editable=" + editable
				+ ", OnClient=" + clientTree);
		//
		if (AD_Tree_ID <= 0) {
			throw new IllegalArgumentException(
					"Param 'AD_Tree_ID' may not be null");
		}
		reload();
	}   //  MTree

	/** Is Tree editable    	*/
	private boolean     		m_editable = false;
	private boolean				m_allNodes = true;
	/** Root Node                   */
	private MTreeNode           m_root = null;
	/** Buffer while loading tree   */
	private ArrayList<MTreeNode> m_buffer = new ArrayList<MTreeNode>();
	/** Prepared Statement for Node Details */
	private RowSet			   	m_nodeRowSet;
	/** The tree is displayed on the Java Client (i.e. not web)	*/
	private boolean				m_clientTree = true;
	
	private HashMap<Integer, ArrayList<Integer>> m_nodeIdMap;

	/**	Logger			*/
	private static CLogger s_log = CLogger.getCLogger(MTree.class);
	
	
	/**************************************************************************
	 *  Get default (oldest) complete AD_Tree_ID for KeyColumn.
	 *  Called from GridController
	 *  @param keyColumnName key column name, eg. C_Project_ID
	 *  @param AD_Client_ID client
	 *  @return AD_Tree_ID
	 */
	public static int getDefaultAD_Tree_ID (int AD_Client_ID, String keyColumnName)
	{
		s_log.config(keyColumnName);
		if (keyColumnName == null || keyColumnName.length() == 0)
		{
			return 0;
		}
		
		final String TreeType = Services.get(IPOTreeSupportFactory.class).get(keyColumnName.substring(0, keyColumnName.length() - 3)).getTreeType();
		
		if (TreeType == null)
		{
			String tableName = MQuery.getZoomTableName(keyColumnName);
			int AD_Tree_ID = getDefaultByTableName(AD_Client_ID, tableName);
			if (AD_Tree_ID >= 0)
			{
				return AD_Tree_ID;
			}
		}
		//
		if (TreeType == null)
		{
			s_log.log(Level.SEVERE, "Could not map " + keyColumnName);
			return 0;
		}

		int AD_Tree_ID = 0;
		final String sql = "SELECT AD_Tree_ID, Name FROM AD_Tree "
			+ "WHERE AD_Client_ID IN (0,?) AND TreeType=? AND IsActive='Y' AND IsAllNodes='Y' "
			+ "ORDER BY AD_Client_ID DESC, IsDefault DESC, AD_Tree_ID";
		
		final PreparedStatement pstmt = DB.prepareStatement(sql, null);
		ResultSet rs = null;
		try
		{			
			pstmt.setInt(1, AD_Client_ID);
			pstmt.setString(2, TreeType);
			 rs = pstmt.executeQuery();
			if (rs.next())
			{
				AD_Tree_ID = rs.getInt(1);
			}
		}
		catch (SQLException e)
		{
			s_log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		return AD_Tree_ID;
	}   //  getDefaultAD_Tree_ID
	
	/**
	 * Get or create default tree for given table
	 * @param AD_Client_ID
	 * @param tableName
	 * @return AD_Tree_ID
	 */
	public static int getDefaultByTableName (int AD_Client_ID, String tableName)
	{
		s_log.finer("TableName=" + tableName);
		if (tableName == null)
		{
			return 0;
		}
		final String sql = "SELECT tr.AD_Tree_ID"
			+ " FROM AD_Tree tr INNER JOIN AD_Table tb ON (tr.AD_Table_ID=tb.AD_Table_ID) "
			+ " WHERE tr.AD_Client_ID=? AND tb.TableName=? AND tr.IsActive='Y' AND tr.IsAllNodes='Y' "
			+ " ORDER BY tr.IsDefault DESC, tr.AD_Tree_ID";
		int AD_Tree_ID = DB.getSQLValue(null, sql, AD_Client_ID, tableName);
		if (AD_Tree_ID <= 0)
		{
			final MClient client = MClient.get(Env.getCtx(), AD_Client_ID);
			final String name = client.getName()+"-"+tableName;
			final int AD_Table_ID = Services.get(IADTableDAO.class).retrieveTableId(tableName);
			final MTree_Base tree = new MTree_Base(client, name, TREETYPE_Other);
			tree.setAD_Table_ID(AD_Table_ID);
			tree.setIsAllNodes(true);
			tree.setIsDefault(true);
			tree.setIsActive(true);
			tree.saveEx();
			AD_Tree_ID = tree.getAD_Tree_ID();
		}

		return AD_Tree_ID;
	}

	/*************************************************************************
	 *  Load Nodes and Bar
	 * 	@param AD_User_ID user for tree bar
	 */
	private void loadNodes (int AD_User_ID)
	{
		//  SQL for TreeNodes
		StringBuilder sql = new StringBuilder("SELECT "
			+ "tn.Node_ID,tn.Parent_ID,tn.SeqNo,tb.IsActive "
			+ "FROM ").append(getNodeTableName()).append(" tn"
			+ " LEFT OUTER JOIN AD_TreeBar tb ON (tn.AD_Tree_ID=tb.AD_Tree_ID"
			+ " AND tn.Node_ID=tb.Node_ID "
			+ (AD_User_ID != -1 ? " AND tb.AD_User_ID=? ": "") 	//	#1 (conditional)
			+ ") "
			+ "WHERE tn.AD_Tree_ID=?");								//	#2
		
		if (!m_editable)
		{
			sql.append(" AND tn.IsActive='Y'");
		}
		sql.append(" ORDER BY COALESCE(tn.Parent_ID, -1), tn.SeqNo");
		log.finest(sql.toString());

		//  The Node Loop
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			// load Node details - addToTree -> getNodeDetail
			getNodeDetails(); 
			//
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			int idx = 1;
			if (AD_User_ID != -1)
			{
				pstmt.setInt(idx++, AD_User_ID);
			}
			pstmt.setInt(idx++, getAD_Tree_ID());
			//	Get Tree & Bar
			rs = pstmt.executeQuery();

			final boolean isSummary = true;
			m_root = new MTreeNode (0, 0, getName(), getDescription(), 0, isSummary, null, false, null);
			while (rs.next())
			{
				int node_ID = rs.getInt(1);
				int parent_ID = rs.getInt(2);
				int seqNo = rs.getInt(3);
				boolean onBar = (rs.getString(4) != null);
				//
				if (node_ID == 0 && parent_ID == 0)
					;
				else
					addToTree (node_ID, parent_ID, seqNo, onBar);	//	calls getNodeDetail
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
			m_nodeRowSet = null;
			m_nodeIdMap = null;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
			//
			//closing the rowset will also close connection for oracle rowset implementation
			//m_nodeRowSet.close();
			m_nodeRowSet = null;
			m_nodeIdMap = null;
		}
			
		//  Done with loading - add remainder from buffer
		if (!m_buffer.isEmpty())
		{
			log.finest("clearing buffer - Adding to: " + m_root);
			for (int i = 0; i < m_buffer.size(); i++)
			{
				MTreeNode node = m_buffer.get(i);
				MTreeNode parent = m_root.findNode(node.getParent_ID());
				if (parent != null && parent.getAllowsChildren())
				{
					parent.add(node);
					int sizeBeforeCheckBuffer = m_buffer.size();
					checkBuffer(node);
					if (sizeBeforeCheckBuffer == m_buffer.size())
						m_buffer.remove(i);
					i = -1;		//	start again with i=0
				}
			}
		}

		//	Nodes w/o parent
		if (!m_buffer.isEmpty())
		{
			log.severe ("Nodes w/o parent - adding to root - " + m_buffer);
			for (int i = 0; i < m_buffer.size(); i++)
			{
				MTreeNode node = m_buffer.get(i);
				m_root.add(node);
				int sizeBeforeCheckBuffer = m_buffer.size();
				checkBuffer(node);
				if (sizeBeforeCheckBuffer == m_buffer.size())
					m_buffer.remove(i);
				i = -1;
			}
			if (!m_buffer.isEmpty())
				log.severe ("Still nodes in Buffer - " + m_buffer);
		}	//	nodes w/o parents

		//  clean up
		if (!m_editable && m_root.getChildCount() > 0)
			trimTree();
//		diagPrintTree();
		if (CLogMgt.isLevelFinest() || m_root.getChildCount() == 0)
			log.fine("ChildCount=" + m_root.getChildCount());
	}   //  loadNodes

	/**
	 *  Add Node to Tree.
	 *  If not found add to buffer
	 *  @param node_ID Node_ID
	 *  @param parent_ID Parent_ID
	 *  @param seqNo SeqNo
	 *  @param onBar on bar
	 */
	private void addToTree (int node_ID, int parent_ID, int seqNo, boolean onBar)
	{
		//  Create new Node
		MTreeNode child = getNodeDetail (node_ID, parent_ID, seqNo, onBar);
		if (child == null)
			return;

		//  Add to Tree
		MTreeNode parent = null;
		if (m_root != null)
			parent = m_root.findNode (parent_ID);
		//  Parent found
		if (parent != null && parent.getAllowsChildren())
		{
			parent.add(child);
			//  see if we can add nodes from buffer
			if (m_buffer.size() > 0)
				checkBuffer(child);
		}
		else
			m_buffer.add(child);
	}   //  addToTree

	/**
	 *  Check the buffer for nodes which have newNode as Parents
	 *  @param newNode new node
	 */
	private void checkBuffer (MTreeNode newNode)
	{
		//	Ability to add nodes
		if (!newNode.isSummary() || !newNode.getAllowsChildren())
			return;
		//
		for (int i = 0; i < m_buffer.size(); i++)
		{
			MTreeNode node = m_buffer.get(i);
			if (node.getParent_ID() == newNode.getNode_ID())
			{
				try
				{
					newNode.add(node);
				}
				catch (Exception e)
				{
					log.severe("Adding " + node.getName() 
						+ " to " + newNode.getName() + ": " + e.getMessage());
				}
				m_buffer.remove(i);
				i--;
			}
		}
	}   //  checkBuffer

	
	
	/**************************************************************************
	 *  Get Node Detail.
	 * 	Loads data into RowSet m_nodeRowSet
	 *  Columns:
	 * 	- ID
	 *  - Name
	 *  - Description
	 *  - IsSummary
	 *  - ImageIndicator
	 * 	- additional for Menu
	 *  Parameter:
	 *  - Node_ID
	 *  The SQL contains security/access control
	 */
	private void getNodeDetails ()
	{
		final IPOTreeSupportFactory treeSupportFactory = Services.get(IPOTreeSupportFactory.class);
		
		final String sourceTable = getSourceTableName();
		final IPOTreeSupport poTreeSupport = treeSupportFactory.get(sourceTable);
		
		final List<Object> sqlParams = new ArrayList<>();
		String sql = poTreeSupport.getNodeInfoSelectSQL(this, sqlParams);
		if (!m_editable)	//	editable = menu/etc. window
		{
			sql = Env.getUserRolePermissions(getCtx()).addAccessSQL(sql, sourceTable, IUserRolePermissions.SQL_FULLYQUALIFIED, m_editable);
		}
		log.fine(sql);
		
		m_nodeRowSet = DB.getRowSet (sql, sqlParams);
		m_nodeIdMap = new HashMap<Integer, ArrayList<Integer>>(50);
		try 
		{
			m_nodeRowSet.beforeFirst();
			int i = 0;
			while (m_nodeRowSet.next())
			{
				i++;
				int node = m_nodeRowSet.getInt(1);
				Integer nodeId = Integer.valueOf(node);
				ArrayList<Integer> list = m_nodeIdMap.get(nodeId);
				if (list == null)
				{
					list = new ArrayList<Integer>(5);
					m_nodeIdMap.put(nodeId, list);
				}
				list.add(Integer.valueOf(i));
			}
		} catch (SQLException e) 
		{
			log.log(Level.SEVERE, "", e);
		}
	}   //  getNodeDetails

	/**
	 *  Get Menu Node Details.
	 *  As SQL contains security access, not all nodes will be found
	 *  @param  node_ID     Key of the record
	 *  @param  parent_ID   Parent ID of the record
	 *  @param  seqNo       Sort index
	 *  @param  onBar       Node also on Shortcut bar
	 *  @return Node
	 */
	private MTreeNode getNodeDetail (int node_ID, int parent_ID, int seqNo, boolean onBar)
	{
		final IPOTreeSupportFactory treeSupportFactory = Services.get(IPOTreeSupportFactory.class);
		
		final IPOTreeSupport poTreeNodeSupport = treeSupportFactory.get(getSourceTableName());
		MTreeNode retValue = null;
		try
		{
			//m_nodeRowSet.beforeFirst();
			ArrayList<Integer> nodeList = m_nodeIdMap.get(Integer.valueOf(node_ID));
			int size = nodeList != null ? nodeList.size() : 0;
			int i = 0;
			//while (m_nodeRowSet.next())
			while (i < size)
			{
				int pos = nodeList.get(i);
				i++;
				m_nodeRowSet.absolute(pos);
				
				retValue = poTreeNodeSupport.loadNodeInfo(this, m_nodeRowSet);
				if (retValue == null)
					return null;
				if (node_ID != retValue.getNode_ID())
					continue;	//	search for correct one
					
				retValue.setSeqNo(seqNo);
				retValue.setParent_ID(parent_ID);
				retValue.setOnBar(onBar);
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "", e);
		}
		
		return retValue;
	}   //  getNodeDetails

	
	/**************************************************************************
	 *  Trim tree of empty summary nodes
	 */
	public void trimTree()
	{
		boolean needsTrim = m_root != null;
		while (needsTrim)
		{
			needsTrim = false;
			Enumeration<?> en = m_root.preorderEnumeration();
			while (m_root.getChildCount() > 0 && en.hasMoreElements())
			{
				MTreeNode nd = (MTreeNode)en.nextElement();
				if (nd.isSummary() && nd.getChildCount() == 0)
				{
					nd.removeFromParent();
					needsTrim = true;
				}
			}
		}
	}   //  trimTree

	/**
	 *  Get Root node
	 *  @return root
	 */
	public MTreeNode getRoot()
	{
		return m_root;
	}   //  getRoot

	/**
	 * 	Is Menu Tree
	 *	@return true if menu
	 */
	public boolean isMenu()
	{
		return TREETYPE_Menu.equals(getTreeType());
	}	//	isMenu

	/**
	 * 	Is Product Tree
	 *	@return true if product
	 */
	public boolean isProduct()
	{
		return TREETYPE_Product.equals(getTreeType());
	}	//	isProduct
	
	/**
	 * 	Is Business Partner Tree
	 *	@return true if partner
	 */
	public boolean isBPartner()
	{
		return TREETYPE_BPartner.equals(getTreeType());
	}	//	isBPartner
	
	/**
	 *  String representation
	 *  @return info
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("MTree[");
		sb.append("AD_Tree_ID=").append(getAD_Tree_ID())
			.append(", Name=").append(getName());
		sb.append("]");
		return sb.toString();
	}
	
	private static List<Integer> s_TableIDs = null;
	private static synchronized void loadUserTables ()
	{
		// TODO: i think we dont need to use caching here
		s_TableIDs = new ArrayList<Integer>();
		final String sql = "SELECT DISTINCT TreeType, AD_Table_ID FROM AD_Tree";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				final String treeType = rs.getString(1);
				int AD_Table_ID = rs.getInt(2);
				if (AD_Table_ID <= 0)
				{
					final String tableName = getSourceTableName(treeType, -1);
					if (tableName != null)
					{
						AD_Table_ID = Services.get(IADTableDAO.class).retrieveTableId(tableName);
					}
				}
				if (AD_Table_ID <= 0)
					continue;
				s_TableIDs.add(AD_Table_ID);
			}
		}
		catch (Exception e)
		{
			s_log.log (Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}

	public static boolean hasTree(int AD_Table_ID)
	{
		if (s_TableIDs == null)
			loadUserTables();
		return s_TableIDs.contains(AD_Table_ID);
	}
	
	/**
	 * 	Get Node TableName
	 *	@param AD_Table_ID table
	 *	@return node table name, e.g. AD_TreeNode
	 */
	static public String getNodeTableName (int AD_Table_ID)
	{
		String	nodeTableName = "AD_TreeNode";
		if (X_AD_Menu.Table_ID == AD_Table_ID)
			nodeTableName += "MM";
		else if  (X_C_BPartner.Table_ID == AD_Table_ID)
			nodeTableName += "BP";
		else if  (X_M_Product.Table_ID == AD_Table_ID)
			nodeTableName += "PR";
		//
		else if  (X_CM_Container.Table_ID == AD_Table_ID)
			nodeTableName += "CMC";
		else if  (X_CM_CStage.Table_ID == AD_Table_ID)
			nodeTableName += "CMS";
		else if  (X_CM_Media.Table_ID == AD_Table_ID)
			nodeTableName += "CMM";
		else if  (X_CM_Template.Table_ID == AD_Table_ID)
			nodeTableName += "CMT";
		//
		else
		{
			if (!hasTree(AD_Table_ID))
				return null;
		}
		return nodeTableName;
	}	//	getNodeTableName
	
	public boolean isEditable()
	{
		return m_editable;
	}
	
	public boolean isClientTree()
	{
		return m_clientTree;
	}
	public void reload()
	{
		int AD_User_ID;
		if (m_allNodes)
			AD_User_ID = -1;
		else
			AD_User_ID = Env.getAD_User_ID(getCtx());
		log.info("AD_User_ID=" + AD_User_ID); 
		loadNodes(AD_User_ID);
	}
}   //  MTree
