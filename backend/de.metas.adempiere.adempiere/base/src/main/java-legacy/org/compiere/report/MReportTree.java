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
package org.compiere.report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.MHierarchy;
import org.compiere.model.MTree;
import org.compiere.model.MTreeNode;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.util.Objects;
import com.google.common.collect.ImmutableSet;

import de.metas.acct.api.AcctSchemaElementType;
import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import lombok.NonNull;

/**
 *	Report Tree Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MReportTree.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MReportTree
{
	/**
	 * 	Get Report Tree (cached)
	 *	@param ctx context
	 *	@param PA_Hierarchy_ID optional hierarchy
	 *	@param elementType Account Schema Element Type
	 *	@return tree
	 */
	private static MReportTree get (Properties ctx, int PA_Hierarchy_ID, final AcctSchemaElementType elementType)
	{
		final String key = PA_Hierarchy_ID + elementType.getCode();
		MReportTree tree = s_trees.get(key);
		if (tree == null)
		{
			tree = new MReportTree (ctx, PA_Hierarchy_ID, elementType);
			s_trees.put(key, tree);
		}
		return tree;	
	}	//	get

	/**
	 * 	Get Where Clause
	 *	@param ctx context
	 *	@param PA_Hierarchy_ID optional hierarchy
	 *	@param elementType Account Schema Element Type
	 *	@param id leaf element id
	 *	@return where clause
	 */
	public static String getWhereClause (Properties ctx, int PA_Hierarchy_ID, AcctSchemaElementType elementType, int id)
	{
		MReportTree tree = get (ctx, PA_Hierarchy_ID, elementType);
		return tree.getWhereClause(id);	
	}	//	getWhereClause

	/**
	 * 	Get Child IDs
	 *	@param ctx context
	 *	@param PA_Hierarchy_ID optional hierarchie
	 *	@param ElementType Account Schema Element Type
	 *	@param ID id
	 *	@return array of IDs
	 */
	public static Integer[] getChildIDs (Properties ctx, int PA_Hierarchy_ID, AcctSchemaElementType elementType, int ID)
	{
		MReportTree tree = get (ctx, PA_Hierarchy_ID, elementType);
		return tree.getChildIDs(ID);	
	}	//	getChildIDs
	
	public static Set<OrgId> getChildOrgIds(@NonNull final OrgId orgId)
	{
		final Integer[] childOrgIds = getChildIDs(
				Env.getCtx(),
				0, // PA_Hierarchy_ID
				AcctSchemaElementType.Organization,
				orgId.getRepoId());

		return Stream.of(childOrgIds)
				.filter(Objects::nonNull)
				.map(OrgId::ofRepoIdOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	/**	Map with Tree				*/
	private static CCache<String,MReportTree> s_trees = new CCache<String,MReportTree>("MReportTree", 20);


	/**************************************************************************
	 * 	Report Tree
	 *	@param ctx context
	 *	@param PA_Hierarchy_ID optional hierarchy
	 *	@param ElementType Account Schema Element Type
	 */
	public MReportTree (Properties ctx, int PA_Hierarchy_ID, AcctSchemaElementType elementType)
	{
		this.elementType = elementType;
		
		if (AcctSchemaElementType.Account.equals(elementType)
			|| AcctSchemaElementType.UserList1.equals(elementType)
			|| AcctSchemaElementType.UserList2.equals(elementType) )
		{
			m_TreeType = MTree.TREETYPE_ElementValue;
		}
		else if (AcctSchemaElementType.OrgTrx.equals(elementType))
		{
			m_TreeType = MTree.TREETYPE_Organization;
		}
		else
		{
			m_TreeType = elementType.getCode();
		}
		
		m_PA_Hierarchy_ID = PA_Hierarchy_ID;
		m_ctx = ctx;
		//
		int AD_Tree_ID = getAD_Tree_ID();
		//	Not found
		if (AD_Tree_ID == 0)
			throw new IllegalArgumentException("No AD_Tree_ID for TreeType=" + m_TreeType 
				+ ", PA_Hierarchy_ID=" + PA_Hierarchy_ID);
		//
		boolean clientTree = true;
		m_tree = MTree.builder()
				.setCtx(ctx)
				.setTrxName(ITrx.TRXNAME_None)
				.setAD_Tree_ID(AD_Tree_ID)
				.setEditable(true)
				.setClientTree(clientTree)
				.build();
		// remove summary nodes without children
		m_tree.trimTree();
	}	//	MReportTree

	/** Optional Hierarchy		*/
	private int			m_PA_Hierarchy_ID = 0;
	/**	Element Type			*/
	private final AcctSchemaElementType elementType;
	/** Context					*/
	private Properties	m_ctx = null;
	/** Tree Type				*/
	private final String m_TreeType;
	/**	The Tree				*/
	private MTree 		m_tree = null;
	/**	Logger					*/
	private static final Logger log = LogManager.getLogger(MReportTree.class);

	
	/**
	 * 	Get AD_Tree_ID 
	 *	@return tree
	 */
	protected int getAD_Tree_ID ()
	{
		if (m_PA_Hierarchy_ID == 0)
			return getDefaultAD_Tree_ID();

		MHierarchy hierarchy = MHierarchy.get(m_ctx, m_PA_Hierarchy_ID);
		int AD_Tree_ID = hierarchy.getAD_Tree_ID (m_TreeType);

		if (AD_Tree_ID == 0)
			return getDefaultAD_Tree_ID();
		
		return AD_Tree_ID;
	}	//	getAD_Tree_ID
	
	/**
	 * 	Get Default AD_Tree_ID 
	 * 	see MTree.getDefaultAD_Tree_ID
	 *	@return tree
	 */
	protected int getDefaultAD_Tree_ID()
	{
		int AD_Tree_ID = 0;
		int AD_Client_ID = Env.getAD_Client_ID(m_ctx);
		
		String sql = "SELECT AD_Tree_ID, Name FROM AD_Tree "
			+ "WHERE AD_Client_ID=? AND TreeType=? AND IsActive='Y' AND IsAllNodes='Y' "
			+ "ORDER BY IsDefault DESC, AD_Tree_ID";	//	assumes first is primary tree
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_Client_ID);
			pstmt.setString(2, m_TreeType);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				AD_Tree_ID = rs.getInt(1);
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}

		return AD_Tree_ID;
	}	//	getDefaultAD_Tree_ID

	/**
	 * 	Get Account Schema Element Type
	 *	@return element Type
	 */
	public AcctSchemaElementType getElementType()
	{
		return elementType;
	}	//	getElementType
	
	/**
	 * 	Get Tree Type
	 *	@return tree type
	 */
	public String getTreeType()
	{
		return m_TreeType;
	}	//	getTreeType
	
	/**
	 * 	Get Tree
	 *	@return tree
	 */
	public MTree getTree()
	{
		return m_tree;
	}	//	getTreeType

	/**
	 * 	Get Where Clause
	 *	@param ID start node
	 *	@return ColumnName = 1 or ( ColumnName = 1 OR ColumnName = 2 OR ColumnName = 3)
	 */	
	public String getWhereClause (int ID)
	{
		String ColumnName = getElementType().getColumnName();
		//
		MTreeNode node = m_tree.getRoot().findNode(ID);
		log.trace("Root=" + node);
		//
		StringBuilder result = null;
		if (node != null && node.isSummary ())
		{
			
			Enumeration<MTreeNode> en = node.preorderEnumeration ();
			StringBuilder sb = new StringBuilder ();
			while (en.hasMoreElements ())
			{
				MTreeNode nn = en.nextElement ();
				if (!nn.isSummary ())
				{
					if (sb.length () > 0)
					{
						sb.append (" OR ");
					}
					sb.append (ColumnName);
					sb.append ('=');
					sb.append (nn.getNode_ID ());
					log.trace("- " + nn);
				}
				else
					log.trace("- skipped parent (" + nn + ")");
			}
			result = new StringBuilder (" ( ");
			result.append (sb);
			result.append (" ) ");
		}
		else	//	not found or not summary 
			result = new StringBuilder (ColumnName).append("=").append(ID);
		//
		log.trace(result.toString());
		return result.toString();
	}	//	getWhereClause

	/**
	 * 	Get Child IDs
	 *	@param ID start node
	 *	@return array if IDs
	 */	
	public Integer[] getChildIDs (int ID)
	{
		ArrayList<Integer> list = new ArrayList<Integer>(); 
		//
		MTreeNode node = m_tree.getRoot().findNode(ID);
		log.trace("Root=" + node);
		//
		if (node != null && node.isSummary())
		{
			Enumeration en = node.preorderEnumeration();
			while (en.hasMoreElements())
			{
				MTreeNode nn = (MTreeNode)en.nextElement();
				if (!nn.isSummary())
				{
					list.add(new Integer(nn.getNode_ID()));
					log.trace("- " + nn);
				}
				else
					log.trace("- skipped parent (" + nn + ")");
			}
		}
		else	//	not found or not summary 
			list.add(new Integer(ID));
		//
		Integer[] retValue = new Integer[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	getWhereClause

	
	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("MReportTree[ElementType=");
		sb.append(elementType).append(",TreeType=").append(m_TreeType)
			.append(",").append(m_tree)
			.append("]");
		return sb.toString();
	}	//	toString

}	//	MReportTree
