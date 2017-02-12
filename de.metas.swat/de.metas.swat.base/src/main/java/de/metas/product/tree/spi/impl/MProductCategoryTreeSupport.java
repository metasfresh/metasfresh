/**
 * 
 */
package de.metas.product.tree.spi.impl;

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


import java.sql.ResultSet;
import java.sql.SQLException;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.tree.spi.impl.DefaultPOTreeSupport;
import org.compiere.model.GridTab;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.MTable;
import org.compiere.model.MTree;
import org.compiere.model.MTreeNode;
import org.compiere.model.MTree_Base;
import org.compiere.model.PO;


/**
 * @author tsa
 * 
 */
public class MProductCategoryTreeSupport extends DefaultPOTreeSupport
{
	@Override
	public int getAD_Tree_ID(PO po)
	{
		return UNKNOWN_TreeID;
	}

	@Override
	public int getParent_ID(PO po)
	{
		if (I_M_Product_Category.Table_Name.equals(po.get_TableName()))
		{
			I_M_Product_Category pc = InterfaceWrapperHelper.create(po, I_M_Product_Category.class);
			return pc.getM_Product_Category_Parent_ID();
		}
		return UNKNOWN_ParentID;
	}

	@Override
	public String getWhereClause(MTree_Base tree)
	{
		return null;
	}

	@Override
	public void setParent_ID(MTree_Base tree, int nodeId, int parentId, String trxName)
	{
		final MTable table = MTable.get(tree.getCtx(), tree.getAD_Table_ID());
		
		if (I_M_Product_Category.Table_Name.equals(table.getTableName()))
		{
			final I_M_Product_Category pc = InterfaceWrapperHelper.create(table.getPO(nodeId, trxName), I_M_Product_Category.class);
			pc.setM_Product_Category_Parent_ID(parentId);
			InterfaceWrapperHelper.save(pc);
		}
	}

	@Override
	public boolean isParentChanged(PO po)
	{
		return po.is_ValueChanged(I_M_Product_Category.COLUMNNAME_M_Product_Category_Parent_ID);
	}

	@Override
	public int getOldParent_ID(PO po)
	{
		return po.get_ValueOldAsInt(I_M_Product_Category.COLUMNNAME_M_Product_Category_Parent_ID);
	}

	@Override
	public String getParentIdSQL()
	{
		return I_M_Product_Category.COLUMNNAME_M_Product_Category_Parent_ID;
	}

	@Override
	public MTreeNode getNodeInfo(GridTab gridTab)
	{
		MTreeNode info = super.getNodeInfo(gridTab);
		info.setAllowsChildren(true); // we always allow children because IsSummary field will be automatically
		// maintained
		return info;
	}

	@Override
	public MTreeNode loadNodeInfo(MTree tree, ResultSet rs) throws SQLException
	{
		MTreeNode info = super.loadNodeInfo(tree, rs);
		info.setAllowsChildren(true); // we always allow children because IsSummary field will be automatically
		info.setImageIndicator(I_M_Product_Category.Table_Name);
		return info;
	}
}
