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

import javax.annotation.Nullable;

/**
 *	Product Category Model
 *	
 *  @author Jorg Janke
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
	@Nullable
	public static MProductCategory get (final Properties ctx, final int M_Product_Category_ID)
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

	
	@SuppressWarnings("unused")
	public MProductCategory (final Properties ctx, final int M_Product_Category_ID, final String trxName)
	{
		super(ctx, M_Product_Category_ID, trxName);
		if (is_new())
		{
			setMMPolicy (MMPOLICY_FiFo);	// F
			setPlannedMargin (BigDecimal.ZERO);
			setIsDefault (false);
			setIsSelfService (true);	// Y
		}
	}	//	MProductCategory

	@SuppressWarnings("unused")
	public MProductCategory(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MProductCategory

	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (final boolean newRecord)
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
	protected boolean afterSave (final boolean newRecord, final boolean success)
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
	 */
	private static boolean hasLoopInTree (final I_M_Product_Category productCategory)
	{
		final int productCategoryId = productCategory.getM_Product_Category_ID();
		final int newParentCategoryId = productCategory.getM_Product_Category_Parent_ID();
		//	get values
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		final String sql = " SELECT M_Product_Category_ID, M_Product_Category_Parent_ID FROM M_Product_Category";
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
		} catch (final SQLException e) {
			log.error(sql, e);
			return true;
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		return false;
	}	//	hasLoopInTree
	

	/**
	 * Recursive search for parent nodes - climbs the to the root.
	 * If there is a circle there is no root but it comes back to the start node.
	 */
	private static boolean hasLoop(final int parentCategoryId, final Vector<SimpleTreeNode> categories, final int loopIndicatorId) {
		final Iterator<SimpleTreeNode> iter = categories.iterator();
		boolean ret = false;
		while (iter.hasNext()) {
			final SimpleTreeNode node = iter.next();
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
		private final int nodeId;
		/** id of the nodes parent */
		private final int parentId;

		public SimpleTreeNode(final int nodeId, final int parentId) {
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
