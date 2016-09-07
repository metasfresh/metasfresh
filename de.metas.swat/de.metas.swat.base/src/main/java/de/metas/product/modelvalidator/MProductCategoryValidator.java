package de.metas.product.modelvalidator;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.tree.IPOTreeSupportFactory;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_TreeNode;
import org.compiere.model.MClient;
import org.compiere.model.MProductCategory;
import org.compiere.model.MTree_Base;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_AD_Tree;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_M_Product_Category;
import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshLastError;
import de.metas.product.tree.spi.impl.MProductCategoryTreeSupport;

public final class MProductCategoryValidator implements ModelValidator
{
	private static final Logger logger = LogManager.getLogger(MProductCategoryValidator.class);

	public final static String TABLENAME = MTree_Base
			.getNodeTableName(X_AD_Tree.TREETYPE_ProductCategory);

	public final static String SQL_INSERT_NODE = //
	"INSERT INTO "
			+ TABLENAME //
			+ "   (" //
			+ "     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, "
			+ "     AD_Tree_ID, Node_ID, Parent_ID, SeqNo" //
			+ "   )" //
			+ "   SELECT " //
			+ "     t.AD_Client_ID, 0, 'Y', now(), 0, now(), 0,"
			+ "     t.AD_Tree_ID, ?, ?, 999" //
			+ "   FROM AD_Tree t WHERE t.AD_Client_ID=? AND t.IsActive='Y'";

	public final static String SQL_UPDATE_PARENTS = //
	" UPDATE " + TABLENAME //
			+ " SET " + I_AD_TreeNode.COLUMNNAME_Parent_ID + "=?" //
			+ " WHERE " + I_AD_TreeNode.COLUMNNAME_Parent_ID + "=?";

	public final static String SQL_UPDATE_PARENT = //
	" UPDATE " + TABLENAME //
			+ " SET " + I_AD_TreeNode.COLUMNNAME_Parent_ID + "=?" //
			+ " WHERE " + I_AD_TreeNode.COLUMNNAME_Node_ID + "=?";

	public final static String SQL_UPDATE_SEQNO = //
	" UPDATE " + TABLENAME //
			+ " SET " + I_AD_TreeNode.COLUMNNAME_SeqNo + "=?" //
			+ " WHERE " + I_AD_TreeNode.COLUMNNAME_Node_ID + "=?";

	public final static String SQL_DELETE_NODE = //
	" DELETE FROM " + TABLENAME
			+ " n" //
			+ " WHERE Node_ID=?" //
			+ "   AND EXISTS (" //
			+ "     SELECT * FROM AD_Tree t" //
			+ "     WHERE t.AD_Tree_ID=n.AD_Tree_ID " //
			+ "       AND t.TreeType='" + X_AD_Tree.TREETYPE_ProductCategory
			+ "'" + "   )"; //

	public final static String SQL_COUNT_SUBCATEGORIES = //
	" SELECT count(1) " //
			+ "FROM "
			+ I_M_Product_Category.Table_Name //
			+ " WHERE "
			+ I_M_Product_Category.COLUMNNAME_M_Product_Category_Parent_ID
			+ "    =?";

	public final static String SQL_SELECT_PARENT_ID = //
	" SELECT " + I_M_Product_Category.COLUMNNAME_M_Product_Category_Parent_ID //
			+ " FROM " + I_M_Product_Category.Table_Name //
			+ " WHERE " //
			+ I_M_Product_Category.COLUMNNAME_M_Product_Category_ID + "=?";

	public final static String SQL_SELECT_SIBLING_IDS = //
	" SELECT " + I_M_Product_Category.COLUMNNAME_M_Product_Category_ID
			+ " FROM "
			+ I_M_Product_Category.Table_Name //
			+ " WHERE " //
			+ I_M_Product_Category.COLUMNNAME_M_Product_Category_Parent_ID
			+ "=?" //
			+ " ORDER BY " + I_M_Product_Category.COLUMNNAME_Name;

	public final static String SQL_SELECT_PARENTSIBLING_IDS = //
	" SELECT "
			+ I_M_Product_Category.COLUMNNAME_M_Product_Category_ID
			+ " FROM "
			+ I_M_Product_Category.Table_Name //
			+ " WHERE " //
			+ "   ("
			+ I_M_Product_Category.COLUMNNAME_M_Product_Category_Parent_ID
			+ " is null OR " //
			+ I_M_Product_Category.COLUMNNAME_M_Product_Category_Parent_ID
			+ "=0" //
			+ "   ) AND AD_Client_ID=?"//
			+ " ORDER BY " + I_M_Product_Category.COLUMNNAME_Name;

	public final static String SQL_UPDATE_ISSUMMARY = //
	" UPDATE " + I_M_Product_Category.Table_Name //
			+ " SET IsSummary=?"//
			+ " WHERE " //
			+ I_M_Product_Category.COLUMNNAME_M_Product_Category_ID + "=?";

	private int ad_Client_ID = -1;

	@Override
	public String docValidate(final PO po, final int timing)
	{
		return null;
	}

	@Override
	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	@Override
	public final void initialize(final ModelValidationEngine engine, final MClient client)
	{
		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
		}
		
		Services.get(IPOTreeSupportFactory.class).register(I_M_Product_Category.Table_Name, MProductCategoryTreeSupport.class);
		
		engine.addModelChange(I_M_Product_Category.Table_Name, this);
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		return null;
	}

	private void resetSeqNos(final MProductCategory productCategory)
	{

		int seqNo = 10;
		final int parentId = productCategory.getM_Product_Category_Parent_ID();
		List<Integer> siblings;
		if (parentId == 0)
		{

			siblings = selectIntegers(SQL_SELECT_PARENTSIBLING_IDS,
					productCategory.getAD_Client_ID(), productCategory
							.get_TrxName());
		}
		else
		{
			siblings = selectIntegers(SQL_SELECT_SIBLING_IDS, parentId,
					productCategory.get_TrxName());
		}
		for (int siblingId : siblings)
		{

			modifyDB(SQL_UPDATE_SEQNO, new Integer[] { seqNo, siblingId },
					productCategory.get_TrxName());
			seqNo += 10;
		}
	}

	@Override
	public String modelChange(PO po, int type) throws Exception
	{
		if ((ModelValidator.TYPE_BEFORE_NEW == type || ModelValidator.TYPE_BEFORE_CHANGE == type)
				&& po.is_ValueChanged(I_M_Product_Category.COLUMNNAME_M_Product_Category_Parent_ID))
		{
			I_M_Product_Category pc = InterfaceWrapperHelper.create(po, I_M_Product_Category.class);
			if (pc.getM_Product_Category_Parent_ID() > 0)
			{
				I_M_Product_Category parent = pc.getM_Product_Category_Parent();
				if (!parent.isSummary())
				{
					parent.setIsSummary(true);
					InterfaceWrapperHelper.save(parent);
				}
			}
		}
		if (ModelValidator.TYPE_AFTER_CHANGE == type && po.is_ValueChanged(I_M_Product_Category.COLUMNNAME_M_Product_Category_Parent_ID))
		{
			
		}
		return null;
	}

	public String modelChange0(PO po, int type) throws Exception
	{

		//
		// Why we react on before_delete instead of after_delet:
		// If a category is deleted via GridTable.delete, the po's id may
		// already be 0, so we don't know which tree node we should delete.

		if (type != ModelValidator.TYPE_BEFORE_CHANGE
				&& type != ModelValidator.TYPE_BEFORE_DELETE
				&& type != ModelValidator.TYPE_AFTER_CHANGE
				&& type != ModelValidator.TYPE_AFTER_NEW)
		{
			return null;
		}

		final MProductCategory productCategory = (MProductCategory)po;
		final int parentCategoryId = productCategory
				.getM_Product_Category_Parent_ID();

		if (type == ModelValidator.TYPE_BEFORE_CHANGE)
		{

			// get the category's old parent category which is still stored in
			// the DB
			int oldParentcategory = selectInteger(SQL_SELECT_PARENT_ID, po
					.get_ID(), po.get_TrxName());
			if (oldParentcategory == 0 || oldParentcategory == parentCategoryId)
			{
				return null;
			}
			int subCategories = selectInteger(SQL_COUNT_SUBCATEGORIES,
					oldParentcategory, po.get_TrxName());
			if (subCategories == 1)
			{
				// the old parent category only has one sub category (i.e. the
				// one that is about to be changed)
				updateIsSummary(oldParentcategory, 'N', po.get_TrxName());
			}
			return null;
		}

		int accessResult = -1;
		char parentIsSummary = '?';

		if (type == ModelValidator.TYPE_BEFORE_DELETE)
		{

			// update the nodes that have this category as parent. (make
			// their grandparent their new parent)
			Integer[] params = new Integer[] { parentCategoryId,
					productCategory.get_ID() };
			accessResult = modifyDB(SQL_UPDATE_PARENTS, params, po
					.get_TrxName());

			// now we can remove the node
			params = new Integer[] { productCategory.get_ID() };
			accessResult = modifyDB(SQL_DELETE_NODE, params, po.get_TrxName());

		}
		else if (type == ModelValidator.TYPE_AFTER_NEW)
		{

			Integer parentId = productCategory
					.getM_Product_Category_Parent_ID();
			if (parentId == 0)
			{
				// a SQL query in MTree.loadNodes assumes that nodes with no
				// parent have NULL as their Parent_ID.
				parentId = null;
			}

			final Integer[] params = new Integer[] { productCategory.get_ID(),
					parentId, Env.getAD_Client_ID(Env.getCtx()) };
			accessResult = modifyDB(SQL_INSERT_NODE, params, po.get_TrxName());

			resetSeqNos(productCategory);

		}
		else if (type == ModelValidator.TYPE_AFTER_CHANGE)
		{

			Integer paramParentId = productCategory
					.getM_Product_Category_Parent_ID();
			if (paramParentId == 0)
			{
				paramParentId = null;
			}
			Integer[] params = new Integer[] { paramParentId,
					productCategory.get_ID() };
			accessResult = modifyDB(SQL_UPDATE_PARENT, params, po.get_TrxName());

			resetSeqNos(productCategory);
		}

		if (parentCategoryId != 0)
		{

			int subCategories = selectInteger(SQL_COUNT_SUBCATEGORIES,
					parentCategoryId, po.get_TrxName());

			if (subCategories > 0)
			{

				// we need to figure out if the parent already had any
				// children, because if this is its first child, we need to
				// change
				// its 'IsSummary' value from 'N' to 'Y'.
				parentIsSummary = 'Y';

			}
			else if (subCategories == 0)
			{

				// we need to figure out if the deleted node's parent has any
				// children left, because we need to set its 'IsSummary' value
				// accordingly.
				parentIsSummary = 'N';
			}

			if (parentIsSummary != '?')
			{
				updateIsSummary(parentCategoryId, parentIsSummary, po
						.get_TrxName());
			}
		}
		if (accessResult < 1)
		{
			return "Database access failed for update of node table";
		}
		return null;
	}

	private int modifyDB(final String preparedStatement,
			final Integer[] params, final String trxName)
	{

		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try
		{
			pstmt = DB.prepareStatement(preparedStatement, trxName);

			if (preparedStatement == SQL_INSERT_NODE)
			{

				setIntOrNull(pstmt, 1, params[0]);
				setIntOrNull(pstmt, 2, params[1]);
				setIntOrNull(pstmt, 3, params[2]);

			}
			else if (preparedStatement == SQL_UPDATE_PARENTS
					|| preparedStatement == SQL_UPDATE_PARENT
					|| preparedStatement == SQL_UPDATE_SEQNO)
			{

				setIntOrNull(pstmt, 1, params[0]);
				setIntOrNull(pstmt, 2, params[1]);

			}
			else if (preparedStatement == SQL_DELETE_NODE)
			{

				pstmt.setInt(1, params[0]);
			}
			return pstmt.executeUpdate();

		}
		catch (SQLException e)
		{
			MetasfreshLastError.saveError(logger, "Unable to modify table ad_treenodepc. SQL statement: " + preparedStatement, e);
			return 0;
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private int selectInteger(final String preparedStatement,
			final int parameter, final String trxName)
	{

		return selectIntegers(preparedStatement, parameter, trxName).get(0);
	}

	private List<Integer> selectIntegers(final String preparedStatement,
			final int parameter, final String trxName)
	{

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		List<Integer> result = new ArrayList<Integer>();
		try
		{
			pstmt = DB.prepareStatement(preparedStatement, trxName);
			pstmt.setInt(1, parameter);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				result.add(rs.getInt(1));
			}
			return result;
		}
		catch (SQLException e)
		{
			MetasfreshLastError.saveError(logger, "Unable to select an integer. SQL statement: "
					+ preparedStatement + "; parameter: " + parameter, e);
			throw new RuntimeException(e);

		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private void updateIsSummary(final int categoryId, final char isSummary,
			final String trxName)
	{

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(SQL_UPDATE_ISSUMMARY, trxName);
			pstmt.setString(1, Character.toString(isSummary));
			pstmt.setInt(2, categoryId);
			pstmt.executeUpdate();

		}
		catch (SQLException e)
		{
			MetasfreshLastError.saveError(logger, "Unable to update IsSummary", e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private void setIntOrNull(final PreparedStatement pstmt, final int idx,
			final Integer param) throws SQLException
	{

		if (param == null)
		{
			pstmt.setNull(idx, Types.INTEGER);
		}
		else
		{
			pstmt.setInt(idx, param);
		}
	}
}
