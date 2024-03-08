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

import com.google.common.base.MoreObjects;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;
import de.metas.security.permissions.Access;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.tree.IPOTreeSupportFactory;
import org.adempiere.model.tree.spi.IPOTreeSupport;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

public class MTree extends MTree_Base
{
	private static final Logger log = LogManager.getLogger(MTree.class);

	public static Builder builder()
	{
		return new Builder();
	}

	/**
	 * Default Constructor.
	 * Need to call retrieveRootWithChildren explicitly
	 */
	public MTree(final Properties ctx, final int AD_Tree_ID, final String trxName)
	{
		super(ctx, AD_Tree_ID, trxName);
		m_editable = false;
		m_allNodes = true;
		m_clientTree = true;
		_adLanguage = null;
		_userRolePermissions = null;
	}   // MTree

	private MTree(final Builder builder)
	{
		super(builder.getCtx(), builder.getAD_Tree_ID(), builder.getTrxName());
		m_editable = builder.isEditable();
		m_allNodes = builder.isAllNodes();
		m_clientTree = builder.isClientTree();
		_adLanguage = builder.getAD_Language();
		_userRolePermissions = builder.getUserRolePermissions();
		reload();
	}   // MTree

	/**
	 * Is Tree editable
	 */
	private final boolean m_editable;
	private final boolean m_allNodes;
	private final boolean m_clientTree;
	private final String _adLanguage;
	private final IUserRolePermissions _userRolePermissions;

	private MTreeNode _rootNode = null;

	/**************************************************************************
	 * Get default (oldest) complete AD_Tree_ID for KeyColumn.
	 * Called from GridController
	 *
	 * @param keyColumnName key column name, eg. C_Project_ID
	 * @param AD_Client_ID client
	 * @return AD_Tree_ID
	 */
	public static int getDefaultAD_Tree_ID(final int AD_Client_ID, final String keyColumnName)
	{
		log.trace("Getting default AD_Tree_ID for AD_Client_ID={}, keyColumnName={}", AD_Client_ID, keyColumnName);
		if (keyColumnName == null || keyColumnName.length() == 0)
		{
			return 0;
		}

		final String TreeType = Services.get(IPOTreeSupportFactory.class).get(keyColumnName.substring(0, keyColumnName.length() - 3)).getTreeType();

		if (TreeType == null)
		{
			final String tableName = MQuery.getZoomTableName(keyColumnName);
			final int AD_Tree_ID = getDefaultByTableName(AD_Client_ID, tableName);
			if (AD_Tree_ID >= 0)
			{
				return AD_Tree_ID;
			}
		}
		//
		if (TreeType == null)
		{
			log.error("Could not map {}", keyColumnName);
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
		catch (final SQLException e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		return AD_Tree_ID;
	}   // getDefaultAD_Tree_ID

	/**
	 * Get or create default tree for given table
	 *
	 * @return AD_Tree_ID
	 */
	private static int getDefaultByTableName(final int AD_Client_ID, final String tableName)
	{
		log.trace("TableName={}", tableName);
		if (tableName == null)
		{
			return 0;
		}
		final String sql = "SELECT tr.AD_Tree_ID"
				+ " FROM AD_Tree tr INNER JOIN AD_Table tb ON (tr.AD_Table_ID=tb.AD_Table_ID) "
				+ " WHERE tr.AD_Client_ID=? AND tb.TableName=? AND tr.IsActive='Y' AND tr.IsAllNodes='Y' "
				+ " ORDER BY tr.IsDefault DESC, tr.AD_Tree_ID";
		int AD_Tree_ID = DB.getSQLValue(ITrx.TRXNAME_None, sql, AD_Client_ID, tableName);
		if (AD_Tree_ID <= 0)
		{
			final MClient client = MClient.get(Env.getCtx(), AD_Client_ID);
			final String name = client.getName() + "-" + tableName;
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

	private MTreeNode retrieveRootWithChildren(@Nullable final UserId userId)
	{
		final MTreeNode root = new MTreeNode(0, 0, getName(), getDescription(), 0, true, null, false, null);
		final HashMap<Integer, MTreeNode> nodesById = new HashMap<>(retrieveNodesById()); // all nodes indexed by Node_ID
		final ArrayList<MTreeNode> buffer = new ArrayList<>(); // buffer of nodes for whom the parent was not yet loaded

		// SQL to retrieve nodes tree structure
		final ArrayList<Object> sqlParams = new ArrayList<>();
		final StringBuilder sql = new StringBuilder("SELECT tn.Node_ID,tn.Parent_ID,tn.SeqNo,tb.IsActive FROM ").append(getNodeTableName()).append(" tn");

		sql.append(" LEFT OUTER JOIN " + I_AD_TreeBar.Table_Name + " tb ON (tn.Node_ID=tb.Node_ID ");
		if (userId != null)
		{
			sql.append(" AND tb.AD_User_ID=? ");
			sqlParams.add(userId);
		}
		sql.append(") ");

		sql.append(" WHERE tn.AD_Tree_ID=?");
		sqlParams.add(getAD_Tree_ID());
		if (!m_editable)
		{
			sql.append(" AND tn.IsActive='Y'");
		}

		sql.append(" ORDER BY COALESCE(tn.Parent_ID, -1), tn.SeqNo");
		log.trace("sql: {}", sql);

		// The Node Loop
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				final int node_ID = rs.getInt(1);
				final int parent_ID = rs.getInt(2);
				final int seqNo = rs.getInt(3);
				final boolean onBar = rs.getString(4) != null;
				//
				if (node_ID == 0 && parent_ID == 0)
				{
					// skip root node
				}
				else
				{
					final MTreeNode node = nodesById.remove(node_ID);
					if (node != null)
					{
						node.setSeqNo(seqNo);
						node.setParent_ID(parent_ID);
						node.setOnBar(onBar);

						addToTree(root, node, buffer);
					}
				}
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		// Done with loading - add remainder from buffer
		if (!buffer.isEmpty())
		{
			log.trace("clearing buffer - Adding to: {}", root);
			for (int i = 0; i < buffer.size(); i++)
			{
				final MTreeNode node = buffer.get(i);
				final MTreeNode parent = root.findNode(node.getParent_ID());
				if (parent != null && parent.getAllowsChildren())
				{
					parent.add(node);
					final int sizeBeforeCheckBuffer = buffer.size();
					checkBuffer(buffer, node);
					if (sizeBeforeCheckBuffer == buffer.size())
					{
						buffer.remove(i);
					}
					i = -1;        // start again with i=0
				}
			}
		}

		// Nodes w/o parent
		if (!buffer.isEmpty())
		{
			log.error("Nodes w/o parent - adding to root - {}", buffer);
			for (int i = 0; i < buffer.size(); i++)
			{
				final MTreeNode node = buffer.get(i);
				root.add(node);
				final int sizeBeforeCheckBuffer = buffer.size();
				checkBuffer(buffer, node);
				if (sizeBeforeCheckBuffer == buffer.size())
				{
					buffer.remove(i);
				}
				i = -1;
			}
			if (!buffer.isEmpty())
			{
				log.error("Still nodes in Buffer - {}", buffer);
			}
		}        // nodes w/o parents

		// clean up
		if (!m_editable && root.getChildCount() > 0)
		{
			trimTree();
		}
		if (log.isDebugEnabled() || root.getChildCount() == 0)
		{
			log.debug("ChildCount={}", root.getChildCount());
		}

		//
		return root;
	}

	private static void addToTree(@NonNull final MTreeNode root, @NonNull final MTreeNode nodeToAdd, @NonNull final ArrayList<MTreeNode> buffer)
	{
		final MTreeNode parent = root.findNode(nodeToAdd.getParent_ID());
		if (parent != null && parent.getAllowsChildren())
		{
			parent.add(nodeToAdd);
			// see if we can add nodes from buffer
			if (buffer.size() > 0)
			{
				checkBuffer(buffer, nodeToAdd);
			}
		}
		else
		{
			buffer.add(nodeToAdd);
		}
	}

	/**
	 * Check the buffer for nodes which have newNode as Parents
	 *
	 * @param newNode new node
	 */
	private static void checkBuffer(@NonNull final ArrayList<MTreeNode> m_buffer, @NonNull final MTreeNode newNode)
	{
		// Ability to add nodes
		if (!newNode.isSummary() || !newNode.getAllowsChildren())
		{
			return;
		}

		for (int i = 0; i < m_buffer.size(); i++)
		{
			final MTreeNode node = m_buffer.get(i);
			if (node.getParent_ID() == newNode.getNode_ID())
			{
				try
				{
					newNode.add(node);
				}
				catch (final Exception ex)
				{
					log.error("Failed adding {} to {}", node.getName(), newNode.getName(), ex);
				}
				m_buffer.remove(i);
				i--;
			}
		}
	}   // checkBuffer

	private ImmutableMap<Integer, MTreeNode> retrieveNodesById()
	{
		final IPOTreeSupportFactory treeSupportFactory = Services.get(IPOTreeSupportFactory.class);

		final String sourceTable = getSourceTableName();
		final IPOTreeSupport poTreeSupport = treeSupportFactory.get(sourceTable);

		final List<Object> sqlParams = new ArrayList<>();
		String sql = poTreeSupport.getNodeInfoSelectSQL(this, sqlParams);
		if (!m_editable)        // editable = menu/etc. window
		{
			sql = getUserRolePermissions().addAccessSQL(sql, sourceTable, IUserRolePermissions.SQL_FULLYQUALIFIED, Access.READ);
		}
		log.debug("SQL={} -- {}", sql, sqlParams);

		final ImmutableList<MTreeNode> nodes = DB.retrieveRows(
				sql,
				sqlParams,
				rs -> poTreeSupport.loadNodeInfo(this, rs));

		return Maps.uniqueIndex(nodes, MTreeNode::getNode_ID);
	}

	// /**
	//  * Get Menu Node Details.
	//  * As SQL contains security access, not all nodes will be found
	//  *
	//  * @param node_ID   Key of the record
	//  * @param parent_ID Parent ID of the record
	//  * @param seqNo     Sort index
	//  * @param onBar     Node also on Shortcut bar
	//  * @return Node
	//  */
	// private MTreeNode getNodeDetail(final int node_ID, final int parent_ID, final int seqNo, final boolean onBar)
	// {
	// 	final IPOTreeSupportFactory treeSupportFactory = Services.get(IPOTreeSupportFactory.class);
	//
	// 	final IPOTreeSupport poTreeNodeSupport = treeSupportFactory.get(getSourceTableName());
	// 	MTreeNode treeNode = null;
	// 	try
	// 	{
	// 		final List<Integer> nodeList = m_nodeIdMap.get(node_ID);
	// 		final int size = nodeList != null ? nodeList.size() : 0;
	// 		int i = 0;
	// 		while (i < size)
	// 		{
	// 			final int pos = nodeList.get(i);
	// 			i++;
	// 			m_nodeRowSet.absolute(pos);
	//
	// 			treeNode = poTreeNodeSupport.loadNodeInfo(this, m_nodeRowSet);
	// 			if (treeNode == null)
	// 			{
	// 				return null;
	// 			}
	// 			if (node_ID != treeNode.getNode_ID())
	// 			{
	// 				continue;    // search for correct one
	// 			}
	//
	// 			treeNode.setSeqNo(seqNo);
	// 			treeNode.setParent_ID(parent_ID);
	// 			treeNode.setOnBar(onBar);
	// 		}
	// 	}
	// 	catch (final Exception e)
	// 	{
	// 		log.error("", e);
	// 	}
	//
	// 	return treeNode;
	// }   // retrieveNodesById

	/**************************************************************************
	 * Trim tree of empty summary nodes
	 */
	public void trimTree()
	{
		boolean needsTrim = _rootNode != null;
		while (needsTrim)
		{
			needsTrim = false;
			final Enumeration<?> en = _rootNode.preorderEnumeration();
			while (_rootNode.getChildCount() > 0 && en.hasMoreElements())
			{
				final MTreeNode nd = (MTreeNode)en.nextElement();
				if (nd.isSummary() && nd.getChildCount() == 0)
				{
					nd.removeFromParent();
					needsTrim = true;
				}
			}
		}
	}   // trimTree

	public MTreeNode getRoot()
	{
		return _rootNode;
	}

	public boolean isMenu()
	{
		return TREETYPE_Menu.equals(getTreeType());
	}

	public boolean isProduct()
	{
		return TREETYPE_Product.equals(getTreeType());
	}

	public boolean isBPartner()
	{
		return TREETYPE_BPartner.equals(getTreeType());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("AD_Tree_ID", getAD_Tree_ID())
				.add("Name", getName())
				.toString();
	}

	private static final Supplier<ImmutableSet<Integer>> s_TableIDsSupplier = Suppliers.memoize(() -> {
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		final ImmutableSet.Builder<Integer> tableIds = ImmutableSet.builder();
		final String sql = "SELECT DISTINCT TreeType, AD_Table_ID FROM AD_Tree";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final String treeType = rs.getString(1);
				int AD_Table_ID = rs.getInt(2);
				if (AD_Table_ID <= 0)
				{
					final String tableName = getSourceTableName(treeType, -1);
					if (tableName != null)
					{
						AD_Table_ID = adTableDAO.retrieveTableId(tableName);
					}
				}
				if (AD_Table_ID <= 0)
				{
					continue;
				}

				tableIds.add(AD_Table_ID);
			}

			return tableIds.build();
		}
		catch (final Exception e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	});

	static boolean hasTree(final int AD_Table_ID)
	{
		return s_TableIDsSupplier.get().contains(AD_Table_ID);
	}

	/**
	 * Get Node TableName
	 *
	 * @param AD_Table_ID table
	 * @return node table name, e.g. AD_TreeNode
	 */
	@Nullable
	static String getNodeTableName(final int AD_Table_ID)
	{
		String nodeTableName = "AD_TreeNode";
		if (getTableId(I_AD_Menu.class) == AD_Table_ID)
		{
			nodeTableName += "MM";
		}
		else if (Services.get(IADTableDAO.class).retrieveTableId(I_C_BPartner.Table_Name) == AD_Table_ID)
		{
			nodeTableName += "BP";
		}
		else if (AD_Table_ID == InterfaceWrapperHelper.getTableId(I_M_Product.class))
		{
			nodeTableName += "PR";
		}
		else if (I_CM_Container.Table_ID == AD_Table_ID)
		{
			nodeTableName += "CMC";
		}
		else if (I_CM_CStage.Table_ID == AD_Table_ID)
		{
			nodeTableName += "CMS";
		}
		else if (I_CM_Media.Table_ID == AD_Table_ID)
		{
			nodeTableName += "CMM";
		}
		else if (I_CM_Template.Table_ID == AD_Table_ID)
		{
			nodeTableName += "CMT";
			//
		}
		else
		{
			if (!hasTree(AD_Table_ID))
			{
				return null;
			}
		}
		return nodeTableName;
	}    // getNodeTableName

	public boolean isEditable()
	{
		return m_editable;
	}

	public boolean isClientTree()
	{
		return m_clientTree;
	}

	public final String getAD_Language()
	{
		if (_adLanguage == null)
		{
			return Env.getADLanguageOrBaseLanguage(getCtx());
		}
		return _adLanguage;
	}

	private void reload()
	{
		final UserId userId;
		if (m_allNodes)
		{
			userId = null;
		}
		else
		{
			userId = getUserRolePermissions().getUserId();
		}
		log.trace("Reloaded tree for AD_User_ID={}", userId);
		this._rootNode = retrieveRootWithChildren(userId);
	}

	public final IUserRolePermissions getUserRolePermissions()
	{
		if (_userRolePermissions == null)
		{
			return Env.getUserRolePermissions(getCtx());
		}
		return _userRolePermissions;
	}

	//
	//
	//
	//
	//

	public static final class Builder
	{
		private Properties ctx;
		private int AD_Tree_ID;
		private boolean editable;
		private boolean clientTree;
		private boolean allNodes = false;
		@Nullable private String trxName = ITrx.TRXNAME_None;
		private String adLanguage = null;
		private IUserRolePermissions userRolePermissions;

		private Builder()
		{
			super();
		}

		/**
		 * Construct & Load Tree
		 */
		public MTree build()
		{
			return new MTree(this);
		}

		public Builder setCtx(final Properties ctx)
		{
			this.ctx = ctx;
			return this;
		}

		public Properties getCtx()
		{
			Check.assumeNotNull(ctx, "Parameter ctx is not null");
			return ctx;
		}

		public Builder setUserRolePermissions(final IUserRolePermissions userRolePermissions)
		{
			this.userRolePermissions = userRolePermissions;
			return this;
		}

		public IUserRolePermissions getUserRolePermissions()
		{
			if (userRolePermissions == null)
			{
				return Env.getUserRolePermissions(getCtx());
			}
			return userRolePermissions;
		}

		public Builder setAD_Tree_ID(final int AD_Tree_ID)
		{
			this.AD_Tree_ID = AD_Tree_ID;
			return this;
		}

		public int getAD_Tree_ID()
		{
			if (AD_Tree_ID <= 0)
			{
				throw new IllegalArgumentException("Param 'AD_Tree_ID' may not be null");
			}
			return AD_Tree_ID;
		}

		public Builder setTrxName(@Nullable final String trxName)
		{
			this.trxName = trxName;
			return this;
		}

		@Nullable
		public String getTrxName()
		{
			return trxName;
		}

		/**
		 * @param editable True, if tree can be modified
		 *                 - includes inactive and empty summary nodes
		 */
		public Builder setEditable(final boolean editable)
		{
			this.editable = editable;
			return this;
		}

		public boolean isEditable()
		{
			return editable;
		}

		/**
		 * @param clientTree the tree is displayed on the java client (not on web)
		 */
		public Builder setClientTree(final boolean clientTree)
		{
			this.clientTree = clientTree;
			return this;
		}

		public boolean isClientTree()
		{
			return clientTree;
		}

		public Builder setAllNodes(final boolean allNodes)
		{
			this.allNodes = allNodes;
			return this;
		}

		public boolean isAllNodes()
		{
			return allNodes;
		}

		public Builder setLanguage(final String adLanguage)
		{
			this.adLanguage = adLanguage;
			return this;
		}

		private String getAD_Language()
		{
			if (adLanguage == null)
			{
				return Env.getADLanguageOrBaseLanguage(getCtx());
			}
			return adLanguage;
		}
	}
}   // MTree
