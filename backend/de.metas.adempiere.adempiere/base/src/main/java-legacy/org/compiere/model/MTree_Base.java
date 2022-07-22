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

import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.migration.logger.IMigrationLogger;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.tree.AdTreeId;
import org.adempiere.model.tree.IPOTreeSupportFactory;
import org.adempiere.model.tree.ITreeListener;
import org.adempiere.model.tree.TreeListenerSupport;
import org.adempiere.model.tree.spi.IPOTreeSupport;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Base Tree Model. (see also MTree in project base)
 *
 * @author Jorg Janke
 * @version $Id: MTree_Base.java,v 1.2 2006/07/30 00:58:37 jjanke Exp $
 */
public class MTree_Base extends X_AD_Tree
{
	private static final Logger log = LogManager.getLogger(MTree_Base.class);

	private static final long serialVersionUID = -7657958239525901547L;

	public static final int ROOT_Node_ID = 0;

	/**
	 * Add Node to correct tree
	 *
	 * @param ctx       cpntext
	 * @param treeType  tree type
	 * @param Record_ID id
	 * @param trxName   transaction
	 * @return true if node added
	 */
	public static boolean addNode(Properties ctx, String treeType, int Record_ID, String trxName)
	{
		// TODO: Check & refactor this
		// Get Tree
		int AD_Tree_ID = 0;
		MClient client = MClient.get(ctx);
		I_AD_ClientInfo ci = client.getInfo();

		if (TREETYPE_Activity.equals(treeType))
			AD_Tree_ID = ci.getAD_Tree_Activity_ID();
		else if (TREETYPE_BoM.equals(treeType))
			throw new IllegalArgumentException("BoM Trees not supported");
		else if (TREETYPE_BPartner.equals(treeType))
			AD_Tree_ID = ci.getAD_Tree_BPartner_ID();
		else if (TREETYPE_Campaign.equals(treeType))
			AD_Tree_ID = ci.getAD_Tree_Campaign_ID();
		else if (TREETYPE_ElementValue.equals(treeType))
			throw new IllegalArgumentException("ElementValue cannot use this API");
		else if (TREETYPE_Menu.equals(treeType))
			AD_Tree_ID = ci.getAD_Tree_Menu_ID();
		else if (TREETYPE_Organization.equals(treeType))
			AD_Tree_ID = ci.getAD_Tree_Org_ID();
		else if (TREETYPE_Product.equals(treeType))
			AD_Tree_ID = ci.getAD_Tree_Product_ID();
		else if (TREETYPE_ProductCategory.equals(treeType))
			throw new IllegalArgumentException("Product Category Trees not supported");
		else if (TREETYPE_Project.equals(treeType))
			AD_Tree_ID = ci.getAD_Tree_Project_ID();
		else if (TREETYPE_SalesRegion.equals(treeType))
			AD_Tree_ID = ci.getAD_Tree_SalesRegion_ID();

		if (AD_Tree_ID == 0)
			throw new IllegalArgumentException("No Tree found");
		MTree_Base tree = MTree_Base.get(ctx, AD_Tree_ID, trxName);
		if (tree.get_ID() != AD_Tree_ID)
			throw new IllegalArgumentException("Tree found AD_Tree_ID=" + AD_Tree_ID);

		// Insert Tree in correct tree
		boolean saved = false;
		if (TREETYPE_Menu.equals(treeType))
		{
			MTree_NodeMM node = new MTree_NodeMM(tree, Record_ID);
			saved = node.save();
		}
		else if (TREETYPE_BPartner.equals(treeType))
		{
			MTree_NodeBP node = new MTree_NodeBP(tree, Record_ID);
			saved = node.save();
		}
		else if (TREETYPE_Product.equals(treeType))
		{
			MTree_NodePR node = new MTree_NodePR(tree, Record_ID);
			saved = node.save();
		}
		else
		{
			MTree_Node node = new MTree_Node(tree, Record_ID);
			saved = node.save();
		}
		return saved;
	}    // addNode

	/**************************************************************************
	 * Get Node TableName
	 *
	 * @param treeType tree type
	 * @return node table name, e.g. AD_TreeNode
	 */
	public static String getNodeTableName(String treeType)
	{
		String nodeTableName = "AD_TreeNode";
		if (TREETYPE_Menu.equals(treeType))
			nodeTableName += "MM";
		else if (TREETYPE_BPartner.equals(treeType))
			nodeTableName += "BP";
		else if (TREETYPE_Product.equals(treeType))
			nodeTableName += "PR";
			// else if (TREETYPE_ProductCategory.equals(treeType))
			// nodeTableName += TREETYPE_ProductCategory;

		else if (TREETYPE_CMContainer.equals(treeType))
			nodeTableName += "CMC";
		else if (TREETYPE_CMContainerStage.equals(treeType))
			nodeTableName += "CMS";
		else if (TREETYPE_CMMedia.equals(treeType))
			nodeTableName += "CMM";
		else if (TREETYPE_CMTemplate.equals(treeType))
			nodeTableName += "CMT";
			//
		else if (TREETYPE_User1.equals(treeType))
			nodeTableName += "U1";
		else if (TREETYPE_User2.equals(treeType))
			nodeTableName += "U2";
		else if (TREETYPE_User3.equals(treeType))
			nodeTableName += "U3";
		else if (TREETYPE_User4.equals(treeType))
			nodeTableName += "U4";
		return nodeTableName;
	}    // getNodeTableName

	/**
	 * Get Source TableName
	 *
	 * @param treeType tree type
	 * @return source table name, e.g. AD_Org or null
	 */
	public static String getDefaultTableNameByTreeType(String treeType)
	{
		return getSourceTableName(treeType, -1);
	}

	/**
	 * Get Source TableName
	 *
	 * @param treeType    tree type
	 * @param AD_Table_ID
	 * @return source table name, e.g. AD_Org or null
	 */
	public static String getSourceTableName(String treeType, int AD_Table_ID)
	{
		if (treeType == null)
			return null;
		String sourceTable = null;
		if (treeType.equals(TREETYPE_Menu))
			sourceTable = "AD_Menu";
		else if (treeType.equals(TREETYPE_Organization))
			sourceTable = "AD_Org";
		else if (treeType.equals(TREETYPE_Product))
			sourceTable = "M_Product";
		else if (treeType.equals(TREETYPE_ProductCategory))
			sourceTable = "M_Product_Category";
		else if (treeType.equals(TREETYPE_BoM))
			sourceTable = "M_BOM";
		else if (treeType.equals(TREETYPE_ElementValue))
			sourceTable = "C_ElementValue";
		else if (treeType.equals(TREETYPE_BPartner))
			sourceTable = "C_BPartner";
		else if (treeType.equals(TREETYPE_Campaign))
			sourceTable = "C_Campaign";
		else if (treeType.equals(TREETYPE_Project))
			sourceTable = "C_Project";
		else if (treeType.equals(TREETYPE_Activity))
			sourceTable = "C_Activity";
		else if (treeType.equals(TREETYPE_SalesRegion))
			sourceTable = "C_SalesRegion";
			//
		else if (treeType.equals(TREETYPE_CMContainer))
			sourceTable = "CM_Container";
		else if (treeType.equals(TREETYPE_CMContainerStage))
			sourceTable = "CM_CStage";
		else if (treeType.equals(TREETYPE_CMMedia))
			sourceTable = "CM_Media";
		else if (treeType.equals(TREETYPE_CMTemplate))
			sourceTable = "CM_Template";
			// User Trees
			// afalcone [Bugs #1837219]
		else if (treeType.equals(TREETYPE_User1) ||
				treeType.equals(TREETYPE_User2) ||
				treeType.equals(TREETYPE_User3) ||
				treeType.equals(TREETYPE_User4))
			sourceTable = "C_ElementValue";
			//
			// General Table
		else if (treeType.equals(TREETYPE_Other) && AD_Table_ID > 0)
		{
			sourceTable = Services.get(IADTableDAO.class).retrieveTableName(AD_Table_ID);
		}

		// else if (treeType.equals(TREETYPE_User1))
		// sourceTable = "??";
		// end afalcone

		return sourceTable;
	}    // getSourceTableName

	/**
	 * Get MTree_Base from Cache
	 *
	 * @param ctx        context
	 * @param AD_Tree_ID id
	 * @param trxName    transaction
	 * @return MTree_Base
	 */
	public static MTree_Base get(Properties ctx, int AD_Tree_ID, String trxName)
	{
		Integer key = new Integer(AD_Tree_ID);
		MTree_Base retValue = s_cache.get(key);
		if (retValue != null)
			return retValue;
		retValue = new MTree_Base(ctx, AD_Tree_ID, trxName);
		if (retValue.get_ID() != 0)
			s_cache.put(key, retValue);
		return retValue;
	}    // get

	public static MTree_Base getById(@NonNull final AdTreeId adTreeId)
	{
		return get(Env.getCtx(), adTreeId.getRepoId(), ITrx.TRXNAME_None);
	}

	/**
	 * Cache
	 */
	private static CCache<Integer, MTree_Base> s_cache = new CCache<Integer, MTree_Base>("AD_Tree", 10);

	/**************************************************************************
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param AD_Tree_ID id
	 * @param trxName transaction
	 */
	public MTree_Base(Properties ctx, int AD_Tree_ID, String trxName)
	{
		super(ctx, AD_Tree_ID, trxName);
		if (AD_Tree_ID == 0)
		{
			// setName (null);
			// setTreeType (null);
			setIsAllNodes(true);    // complete tree
			setIsDefault(false);
		}
	}    // MTree_Base

	/**
	 * Load Constructor
	 *
	 * @param ctx     context
	 * @param rs      result set
	 * @param trxName transaction
	 */
	public MTree_Base(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}    // MTree_Base

	/**
	 * Parent Constructor
	 *
	 * @param client   client
	 * @param name     name
	 * @param treeType
	 */
	public MTree_Base(MClient client, String name, String treeType)
	{
		this(client.getCtx(), 0, client.get_TrxName());
		setClientOrg(client);
		setName(name);
		setTreeType(treeType);
	}    // MTree_Base

	/**
	 * Full Constructor
	 *
	 * @param ctx      context
	 * @param Name     name
	 * @param TreeType tree type
	 * @param trxName  transaction
	 */
	public MTree_Base(Properties ctx, String Name, String TreeType,
					  String trxName)
	{
		super(ctx, 0, trxName);
		setName(Name);
		setTreeType(TreeType);
		setIsAllNodes(true);    // complete tree
		setIsDefault(false);
	}    // MTree_Base

	/**
	 * Get Node TableName
	 *
	 * @return node table name, e.g. AD_TreeNode
	 */
	public String getNodeTableName()
	{
		return getNodeTableName(getTreeType());
	}    // getNodeTableName

	/**
	 * Get Source TableName (i.e. where to get the name and color)
	 *
	 * @return source table name, e.g. AD_Org or null
	 */
	public String getSourceTableName()
	{
		return getSourceTableName(true);
	}

	@Deprecated
	public String getSourceTableName(boolean tableNameOnly)
	{
		String tableName = getSourceTableName(getTreeType(), getAD_Table_ID());
		if (tableNameOnly)
			return tableName;
		if ("M_Product".equals(tableName))
			return "M_Product t INNER JOIN M_Product_Category x ON (t.M_Product_Category_ID=x.M_Product_Category_ID)";
		if ("C_BPartner".equals(tableName))
			return "C_BPartner t INNER JOIN C_BP_Group x ON (t.C_BP_Group_ID=x.C_BP_Group_ID)";
		if ("AD_Org".equals(tableName))
			return "AD_Org t INNER JOIN AD_OrgInfo i ON (t.AD_Org_ID=i.AD_Org_ID) "
					+ "LEFT OUTER JOIN AD_OrgType x ON (i.AD_OrgType_ID=x.AD_OrgType_ID)";
		if ("C_Campaign".equals(tableName))
			return "C_Campaign t LEFT OUTER JOIN C_Channel x ON (t.C_Channel_ID=x.C_Channel_ID)";
		if (tableName != null)
			tableName += " t";
		return tableName;
	}    // getSourceTableName

	public String getSourceKeyColumnName()
	{
		String sourceTableName = getSourceTableName();
		return getSourceKeyColumnName(sourceTableName);
	}

	public static String getSourceKeyColumnName(String sourceTableName)
	{
		String sourceTableKey = sourceTableName + "_ID"; // TODO: hardcoded
		return sourceTableKey;
	}

	/**
	 * Get fully qualified Name of Action/Color Column
	 *
	 * @return NULL or Action or Color
	 */
	public String getActionColorName()
	{
		String tableName = getSourceTableName(getTreeType(), getAD_Table_ID());
		if ("AD_Menu".equals(tableName))
			return "t.Action";
		if ("M_Product".equals(tableName) || "C_BPartner".equals(tableName)
				|| "AD_Org".equals(tableName) || "C_Campaign".equals(tableName))
			return "x.AD_PrintColor_ID";
		return "NULL";
	}    // getSourceTableName

	/**
	 * Before Save
	 *
	 * @param newRecord new
	 * @return true
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (!isActive() || !isAllNodes())
			setIsDefault(false);

		if (getAD_Table_ID() <= 0 || is_ValueChanged(COLUMNNAME_TreeType) || newRecord)
		{
			if (TREETYPE_Other.equals(getTreeType()))
			{
				if (getAD_Table_ID() <= 0)
					throw new FillMandatoryException(COLUMNNAME_AD_Table_ID);
			}
			else
			{
				final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

				final String tableName = getDefaultTableNameByTreeType(getTreeType());
				final int AD_Table_ID = adTableDAO.retrieveTableId(tableName);
				if (AD_Table_ID <= 0)
				{
					throw new FillMandatoryException(COLUMNNAME_AD_Table_ID);
				}
				setAD_Table_ID(AD_Table_ID);
			}
		}
		return true;
	}    // beforeSabe

	/**
	 * After Save
	 *
	 * @param newRecord new
	 * @param success   success
	 * @return success
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (newRecord)    // Base Node
		{
			verifyTree();
		}

		return success;
	}    // afterSave

	/**
	 * Move node from parent to another
	 *
	 * @throws AdempiereException if an exception occurs
	 */
	public void updateNodeChildren(final MTreeNode parent, final List<MTreeNode> children)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.runInNewTrx(() -> updateNodeChildrenInTrx(parent, children));
	}

	private void updateNodeChildrenInTrx(final MTreeNode parent, final List<MTreeNode> children)
	{
		MigrationScriptFileLoggerHolder.logComment("Reordering children of `" + parent.getName() + "`");

		int seqNo = 0;
		for (final MTreeNode nd : children)
		{
			if (nd.isPlaceholder())
			{
				for (final MTreeNode nd2 : nd.getPlaceholderNodes())
				{
					updateNode(nd2, parent.getNode_ID(), seqNo++);
				}
			}
			else
			{
				updateNode(nd, parent.getNode_ID(), seqNo++);
			}
		}
	}

	private void updateNode(MTreeNode node, int parentId, int seqNo)
	{
		//
		// services
		final IPOTreeSupportFactory treeSupportFactory = Services.get(IPOTreeSupportFactory.class);

		final int AD_Tree_ID = this.getAD_Tree_ID();
		if (node.getAD_Tree_ID() < 0) // TODO: workaround, we should instantiate the MTreeNode by giving the tree
		{
			node.setAD_Tree_ID(this.getAD_Tree_ID());
			node.setAD_Table_ID(this.getAD_Table_ID());
		}

		if (node.getAD_Tree_ID() != AD_Tree_ID)
			throw new IllegalArgumentException("AD_Tree_ID<>" + AD_Tree_ID + " (node:" + node + ", AD_Tree_ID=" + node.getAD_Tree_ID() + ")");

		final int nodeId = node.getNode_ID();
		final int oldParentId = node.getParent_ID();

		MigrationScriptFileLoggerHolder.logComment("Node name: `" + node.getName() + "`");
		updateNode(null, this.getAD_Tree_ID(), this.getTreeType(),
				this.getAD_Table_ID(),
				nodeId,
				parentId, oldParentId,
				seqNo,
				ITrx.TRXNAME_ThreadInherited);

		node.setParent_ID(parentId);
		node.setSeqNo(seqNo);

		treeSupportFactory.get(getSourceTableName()).setParent_ID(this, node.getNode_ID(), parentId, ITrx.TRXNAME_ThreadInherited);
		listeners.onParentChanged(node.getAD_Table_ID(), nodeId, parentId, oldParentId, ITrx.TRXNAME_ThreadInherited);
	}

	private static void updateNode(PO contextPO, int AD_Tree_ID, String treeType, int AD_Table_ID, int nodeId, int parentId, int oldParentId, int seqNo, String trxName)
	{
		final String nodeTableName = getNodeTableName(treeType);

		StringBuilder sql = new StringBuilder("UPDATE ").append(nodeTableName)
				.append(" SET ").append("Parent_ID").append("=").append(parentId);
		if (seqNo >= 0)
		{
			sql.append(", SeqNo=").append(seqNo);
		}
		else
		{
			sql.append(", SeqNo=(SELECT COALESCE(MAX(SeqNo),0)+1 FROM ").append(nodeTableName).append(" n2 WHERE n2.Parent_ID=").append(parentId).append(")");
		}
		sql.append(", Updated=now()")
				.append(", UpdatedBy=").append(Env.getAD_User_ID(Env.getCtx()))
				.append(" WHERE ")
				.append(" Node_ID=").append(nodeId)
				.append(" AND AD_Tree_ID=").append(AD_Tree_ID);
		int no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no == 0)
		{
			// TODO: Insert
			return;
		}
		else if (no == 1)
		{
			// metas: log SQL migration only if contextPO is null,
			// because else, the method is called directly from PO
			if (contextPO == null)
			{
				Services.get(IMigrationLogger.class).logMigrationSQL(contextPO, sql.toString());
			}
			return;
		}
		else
		// no > 1
		{
			throw new AdempiereException("Moved node updated more then one record (" + sql + ")"); // Internal Error
		}
	}

	/**************************************************************************
	 * Insert id data into Tree
	 *
	 * @return true if inserted
	 */
	protected static boolean insertTreeNode(PO po)
	{
		//
		// services 
		final IPOTreeSupportFactory treeSupportFactory = Services.get(IPOTreeSupportFactory.class);

		// TODO: check self contained tree
		final int AD_Table_ID = po.get_Table_ID();
		if (!MTree.hasTree(AD_Table_ID))
		{
			return false;
		}
		final int id = po.get_ID();
		final int AD_Client_ID = po.getAD_Client_ID();
		final String treeTableName = MTree.getNodeTableName(AD_Table_ID);
		final String trxName = po.get_TrxName();

		final IPOTreeSupport treeSupport = treeSupportFactory.get(po.get_TableName());
		final int AD_Tree_ID = treeSupport.getAD_Tree_ID(po);
		int parentId = treeSupport.getParent_ID(po);
		if (parentId < 0 || parentId == IPOTreeSupport.UNKNOWN_ParentID)
		{
			parentId = ROOT_Node_ID;
		}
		//
		// Insert
		final StringBuilder sb = new StringBuilder("INSERT INTO ")
				.append(treeTableName)
				.append(" (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, ")
				.append("AD_Tree_ID, Node_ID, Parent_ID, SeqNo) ")
				//
				.append("SELECT t.AD_Client_ID,0, 'Y', now(), " + po.getUpdatedBy() + ", now(), " + po.getUpdatedBy() + ",")
				.append("t.AD_Tree_ID, ").append(id).append(", ").append(parentId).append(", 999 ")
				.append("FROM AD_Tree t ")
				.append("WHERE t.AD_Client_ID=").append(AD_Client_ID).append(" AND t.IsActive='Y'");
		if (AD_Tree_ID > 0)
		{
			sb.append(" AND t.AD_Tree_ID=").append(AD_Tree_ID);
		}
		else
		// std trees
		{
			sb.append(" AND t.IsAllNodes='Y' AND t.AD_Table_ID=").append(AD_Table_ID);
		}
		// Duplicate Check
		sb.append(" AND NOT EXISTS (SELECT * FROM ").append(treeTableName).append(" e ")
				.append("WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=").append(id).append(")");
		//
		final int no = DB.executeUpdateEx(sb.toString(), trxName);
		log.debug("Inserted into {} {} rows (AD_Table_ID={}, id={})", treeTableName, no, AD_Table_ID, id);
		// MigrationLogger.instance.logMigrationSQL(po, sb.toString()); // metas: not needed because it's called directly from PO

		listeners.onNodeInserted(po);

		return true;
	}    // insert_Tree

	/**
	 * Delete ID Tree Nodes
	 *
	 * @return true if actually deleted (could be non existing)
	 */
	protected static boolean deleteTreeNode(PO po)
	{
		// TODO: check self contained tree
		int id = po.get_ID();
		if (id <= 0)
			id = po.get_IDOld();
		final int AD_Table_ID = po.get_Table_ID();
		if (!MTree.hasTree(AD_Table_ID))
			return false;
		final String treeTableName = MTree.getNodeTableName(AD_Table_ID);
		if (treeTableName == null)
			return false;
		final String trxName = po.get_TrxName();

		//
		// Check children:
		List<MTreeNode> children = fetchNodes(AD_Table_ID, "Parent_ID=?", new Object[] { id }, trxName);
		if (children.size() > 0)
		{
			throw new AdempiereException("TreeNodeHasChildren"); // TODO: translate
		}

		//
		final StringBuffer sb = new StringBuffer("DELETE FROM ")
				.append(treeTableName)
				.append(" n WHERE Node_ID=").append(id)
				.append(" AND EXISTS (SELECT * FROM AD_Tree t ")
				.append("WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=")
				.append(AD_Table_ID).append(")");
		//
		final int no = DB.executeUpdateEx(sb.toString(), trxName);
		log.debug("Deleted from {} {} rows (AD_Table_ID={}, id={})", treeTableName, no, AD_Table_ID, id);
		// MigrationLogger.instance.logMigrationSQL(po, sb.toString()); // metas: not needed because it's called directly from PO
		listeners.onNodeDeleted(po);

		//
		// TODO Update children

		return true;
	}    // delete_Tree

	public static void updateTreeNode(PO po)
	{
		int nodeId = po.get_ID();
		if (nodeId < 0)
		{
			return; // nothing to do, because our PO has no ID to match against a tree node
		}
		final int AD_Table_ID = po.get_Table_ID();
		if (!MTree.hasTree(AD_Table_ID))
		{
			return;
		}
		final String nodeTableName = MTree.getNodeTableName(AD_Table_ID);
		if (nodeTableName == null)
		{
			return;
		}

		//
		// services 
		final IPOTreeSupportFactory treeSupportFactory = Services.get(IPOTreeSupportFactory.class);

		final String trxName = po.get_TrxName();
		final IPOTreeSupport treeSupport = treeSupportFactory.get(po.get_TableName());

		if (po.is_ValueChanged("IsSummary") && !po.get_ValueAsBoolean("IsSummary"))
		{
			// Move all its children to parent
			final List<MTreeNode> children = fetchNodes(AD_Table_ID, "Parent_ID=?", new Object[] { nodeId }, trxName);
			if (children.size() > 0)
			{
				throw new AdempiereException("TreeNodeHasChildren"); // TODO: translate
			}
		}

		if (treeSupport.isParentChanged(po))
		{
			int parentId = treeSupport.getParent_ID(po);
			int oldParentId = treeSupport.getOldParent_ID(po);
			int seqNo = -1; // compute

			final String sql = "SELECT AD_Tree_ID, TreeType FROM AD_Tree WHERE AD_Table_ID=? AND AD_Client_ID=?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, trxName);
				DB.setParameters(pstmt, new Object[] { AD_Table_ID, po.getAD_Client_ID() });
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					int AD_Tree_ID = rs.getInt(COLUMNNAME_AD_Tree_ID);
					String treeType = rs.getString(COLUMNNAME_TreeType);
					updateNode(po, AD_Tree_ID, treeType, AD_Table_ID, nodeId, parentId, oldParentId, seqNo, trxName);
				}
			}
			catch (SQLException e)
			{
				throw new DBException(e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		}
	}

	private static List<MTreeNode> fetchNodes(int AD_Table_ID, String whereClause, Object[] params, String trxName)
	{
		List<MTreeNode> list = new ArrayList<MTreeNode>();
		final String nodeTableName = MTree.getNodeTableName(AD_Table_ID);
		if (nodeTableName == null)
			return list;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("SELECT Node_ID, Parent_ID, SeqNo, AD_Tree_ID ")
				.append(" FROM ").append(nodeTableName)
				.append(" WHERE ")
				.append(" EXISTS (SELECT * FROM AD_Tree t ")
				.append("WHERE t.AD_Tree_ID=" + nodeTableName + ".AD_Tree_ID AND t.AD_Table_ID=").append(AD_Table_ID).append(")")
				.append(" AND (").append(whereClause).append(")")
				.append(" ORDER BY AD_Tree_ID, Parent_ID, Node_ID");
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), trxName);
			DB.setParameters(pstmt, params);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				int node_ID = rs.getInt("Node_ID");
				int parent_ID = rs.getInt("Node_ID");
				int seqNo = rs.getInt("SeqNo");
				int AD_Tree_ID = rs.getInt("AD_Tree_ID");
				MTreeNode node = new MTreeNode(node_ID, seqNo, null, null, parent_ID, false, null, false, null);
				node.setAD_Tree_ID(AD_Tree_ID);
				node.setAD_Table_ID(AD_Table_ID);
				list.add(node);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return list;
	}

	public void verifyTree()
	{
		final IPOTreeSupportFactory treeSupportFactory = Services.get(IPOTreeSupportFactory.class);

		final String nodeTableName = getNodeTableName();
		final String sourceTableName = getSourceTableName();
		final String sourceTableKey = getSourceKeyColumnName();
		final int AD_Client_ID = getAD_Client_ID();

		final IPOTreeSupport treeSupport = treeSupportFactory.get(getSourceTableName());
		final String sourceTableWhereClause = treeSupport.getWhereClause(this);

		// Delete unused
		final StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM ").append(nodeTableName)
				.append(" WHERE AD_Tree_ID=").append(getAD_Tree_ID())
				.append(" AND Node_ID<>0") // don't delete root node
				.append(" AND Node_ID NOT IN (SELECT ").append(sourceTableKey)
				.append(" FROM ").append(sourceTableName)
				.append(" WHERE AD_Client_ID=").append(AD_Client_ID);
		if (!Check.isEmpty(sourceTableWhereClause, true))
		{
			sql.append(" AND (").append(sourceTableWhereClause).append(")");
		}
		sql.append(")");
		log.trace("SQL: {}", sql);
		//
		final int deletes = DB.executeUpdateEx(sql.toString(), get_TrxName());
		log.debug("{}: Deleted {} rows", getName(), deletes);

		// Check and create root node
		{
			final Query query = new Query(getCtx(), nodeTableName, "AD_Tree_ID=? AND Node_ID=?", get_TrxName())
					.setParameters(getAD_Tree_ID(), ROOT_Node_ID);
			if (!query.anyMatch())
			{
				createNode(ROOT_Node_ID, ROOT_Node_ID);
				log.debug("{} Root Node Created", getName());
			}
		}

		if (!isAllNodes())
		{
			return;
		}
		String sqlParentId = treeSupport.getParentIdSQL();
		boolean updateParents = true;
		if (Check.isEmpty(sqlParentId, true))
		{
			sqlParentId = "" + ROOT_Node_ID;
			updateParents = true;
		}

		// Insert new
		sql.setLength(0);
		sql.append("SELECT ")
				.append(sourceTableKey) // 1
				.append(",").append(sqlParentId) // 2
				.append(" FROM ").append(sourceTableName)
				.append(" WHERE AD_Client_ID=").append(AD_Client_ID);
		if (!Check.isEmpty(sourceTableWhereClause, true))
		{
			sql.append(" AND (").append(sourceTableWhereClause).append(")");
		}
		sql.append(" AND ").append(sourceTableKey)
				.append("  NOT IN (SELECT Node_ID FROM ").append(nodeTableName)
				.append(" WHERE AD_Tree_ID=").append(getAD_Tree_ID()).append(")");
		//
		int inserted = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				int nodeId = rs.getInt(1);
				int parentId = rs.getInt(2);
				if (nodeId == ROOT_Node_ID)
					throw new AdempiereException("Tree is not supported for this table because it contains a record that has the same ID as current hardcoded root id");
				createNode(nodeId, parentId);
				inserted++;
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql.toString());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		//
		// Update parents from PO table:
		if (updateParents)
		{
			// TODO: implement update parents logic
		}
		log.debug("{} Inserted {} rows", getName(), inserted);
	}

	private PO createNode(int nodeId, int parentId)
	{
		final String nodeTableName = getNodeTableName();
		PO node = null;
		if (nodeTableName.equals(MTree_Node.Table_Name))
		{
			MTree_Node n = new MTree_Node(this, nodeId);
			if (nodeId != ROOT_Node_ID)
				n.setParent_ID(parentId);
			node = n;
		}
		else if (nodeTableName.equals(MTree_NodeBP.Table_Name))
		{
			MTree_NodeBP n = new MTree_NodeBP(this, nodeId);
			if (nodeId != ROOT_Node_ID)
				n.setParent_ID(parentId);
			node = n;
		}
		else if (nodeTableName.equals(MTree_NodePR.Table_Name))
		{
			MTree_NodePR n = new MTree_NodePR(this, nodeId);
			if (nodeId != ROOT_Node_ID)
				n.setParent_ID(parentId);
			node = n;
		}
		else if (nodeTableName.equals(MTree_NodeMM.Table_Name))
		{
			MTree_NodeMM n = new MTree_NodeMM(this, nodeId);
			if (nodeId != ROOT_Node_ID)
				n.setParent_ID(parentId);
			node = n;
		}
		else
		{
			throw new AdempiereException("No Table Model for " + nodeTableName);
		}

		node.saveEx();
		return node;
	}

	private final static TreeListenerSupport listeners = new TreeListenerSupport();

	public static void registerTreeListener(ITreeListener listener, boolean isWeak)
	{
		listeners.addTreeListener(listener, isWeak);
	}

	public static void unregisterTreeListener(ITreeListener listener)
	{
		listeners.removeTreeListener(listener);
	}

}    // MTree_Base
