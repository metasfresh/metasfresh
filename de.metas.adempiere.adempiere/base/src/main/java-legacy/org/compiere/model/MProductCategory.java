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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 *	Product Category Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MProductCategory.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MProductCategory extends X_M_Product_Category
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1290361229726779892L;


	/**
	 * 	Get from Cache
	 *	@param ctx context
	 *	@param M_Product_Category_ID id
	 *	@return category or null
	 */
	public static MProductCategory get (Properties ctx, int M_Product_Category_ID)
	{
		if (M_Product_Category_ID <= 0)
		{
			return null;
		}
		final I_M_Product_Category pc = InterfaceWrapperHelper.create(ctx, M_Product_Category_ID, I_M_Product_Category.class, ITrx.TRXNAME_None);
		return LegacyAdapters.convertToPO(pc);
	}	//	get
	
	/**	Static Logger	*/
	private static final Logger log = LogManager.getLogger(MProductCategory.class);

	
	/**************************************************************************
	 * 	Default Constructor
	 *	@param ctx context
	 *	@param M_Product_Category_ID id
	 *	@param trxName transaction
	 */
	public MProductCategory (Properties ctx, int M_Product_Category_ID, String trxName)
	{
		super(ctx, M_Product_Category_ID, trxName);
		if (M_Product_Category_ID == 0)
		{
		//	setName (null);
		//	setValue (null);
			setMMPolicy (MMPOLICY_FiFo);	// F
			setPlannedMargin (BigDecimal.ZERO);
			setIsDefault (false);
			setIsSelfService (true);	// Y
		}
	}	//	MProductCategory

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MProductCategory(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MProductCategory

	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		assertNoLoopInTree(this);
		
		return true;
	}	//	beforeSave

	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return success
	 */
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (newRecord && success)
			insert_Accounting("M_Product_Category_Acct", "C_AcctSchema_Default", null);

		return success;
	}	//	afterSave

	/**
	 * 	Before Delete
	 *	@return true
	 */
	@Override
	protected boolean beforeDelete ()
	{
		return delete_Accounting("M_Product_Category_Acct"); 
	}	//	beforeDelete
	
	/**
	 * 	FiFo Material Movement Policy
	 *	@return true if FiFo
	 */
	public boolean isFiFo()
	{
		return MMPOLICY_FiFo.equals(getMMPolicy());
	}	//	isFiFo
	
	static void assertNoLoopInTree(final I_M_Product_Category productCategory)
	{
		if (hasLoopInTree(productCategory))
		{
			throw new AdempiereException("@ProductCategoryLoopDetected@");
		}
	}
	
	/**
	 *	Loop detection of product category tree.
	 * @param productCategoryId 
	 * @param newParentCategoryId 
	 *
	 *  @param newParentCategoryId New Parent Category
	 *  @return "" or error message
	 */
	private static boolean hasLoopInTree (final I_M_Product_Category productCategory)
	{
		int productCategoryId = productCategory.getM_Product_Category_ID();
		int newParentCategoryId = productCategory.getM_Product_Category_Parent_ID();
		//	get values
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql = " SELECT M_Product_Category_ID, M_Product_Category_Parent_ID FROM M_Product_Category";
		final Vector<SimpleTreeNode> categories = new Vector<>(100);
		try {
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) == productCategoryId)
					categories.add(new SimpleTreeNode(rs.getInt(1), newParentCategoryId));
				categories.add(new SimpleTreeNode(rs.getInt(1), rs.getInt(2)));
			}
 			if (hasLoop(newParentCategoryId, categories, productCategoryId))
				return true;
		} catch (SQLException e) {
			log.error(sql, e);
			return true;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return false;
	}	//	hasLoopInTree
	

	/**
	 * Recursive search for parent nodes - climbs the to the root.
	 * If there is a circle there is no root but it comes back to the start node.
	 * @param parentCategoryId
	 * @param categories
	 * @param loopIndicatorId
	 * @return
	 */
	private static boolean hasLoop(int parentCategoryId, Vector<SimpleTreeNode> categories, int loopIndicatorId) {
		final Iterator<SimpleTreeNode> iter = categories.iterator();
		boolean ret = false;
		while (iter.hasNext()) {
			SimpleTreeNode node = iter.next();
			if(node.getNodeId()==parentCategoryId){
				if (node.getParentId()==0) {
					//root node, all fine
					return false;
				}
				if(node.getNodeId()==loopIndicatorId){
					//loop found
					return true;
				}
				ret = hasLoop(node.getParentId(), categories, loopIndicatorId);
			}
		}
		return ret;
	}	//hasLoop

	/**
	 * Simple class for tree nodes.
	 * @author Karsten Thiemann, kthiemann@adempiere.org
	 *
	 */
	private static class SimpleTreeNode {
		/** id of the node */
		private int nodeId;
		/** id of the nodes parent */
		private int parentId;

		/**
		 * Constructor.
		 * @param nodeId
		 * @param parentId
		 */
		public SimpleTreeNode(int nodeId, int parentId) {
			this.nodeId = nodeId;
			this.parentId = parentId;
		}

		public int getNodeId() {
			return nodeId;
		}

		public int getParentId() {
			return parentId;
		}
	}
		
}	//	MProductCategory
