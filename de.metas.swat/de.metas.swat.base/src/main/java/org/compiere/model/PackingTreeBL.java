/**
 * 
 */
package org.compiere.model;

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


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.picking.terminal.Utils;
import de.metas.shipping.interfaces.I_M_Package;

/**
 * @author cg
 * 
 */
public class PackingTreeBL
{
	public static I_M_PackagingTree getPackingTree(final int bp_id, final int warehouseDestID, final BigDecimal qty)
	{
		if (Utils.disableSavePickingTree) // cg : task 05659 Picking Terminal: Disable Persistency
		{
			return null;
		}
		
		final Properties ctx = Env.getCtx();
		final String trxName = null;
		
		String whereClause = I_M_PackagingTree.COLUMNNAME_C_BPartner_ID + " = ?  AND " +  I_M_PackagingTree.COLUMNNAME_Processed + " = ? ";
		final List<Object> params = new ArrayList<>();
		params.add(bp_id);
		params.add(false);
		if (warehouseDestID > 0)
		{
			whereClause += " AND " + I_M_PackagingTree.COLUMNNAME_M_Warehouse_Dest_ID + " = ?";
			params.add(warehouseDestID);
		}
		whereClause = whereClause
				+ " AND ? = (select sum(pi.qty) from m_packagingtreeitem pi "
				+ " where pi.M_PackagingTree_ID = M_PackagingTree.M_PackagingTree_ID "
				// only UnPacked Items and Packed Items
				+ " and pi.Type in ("
				+ "		" + DB.TO_STRING(X_M_PackagingTreeItem.TYPE_UnPackedItem)
				+ "		," + DB.TO_STRING(X_M_PackagingTreeItem.TYPE_PackedItem)
				+ "	)"
				+ ") ";
		params.add(qty);
		
		final I_M_PackagingTree tree = new Query(ctx, I_M_PackagingTree.Table_Name, whereClause, trxName)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setParameters(params)
				.setOrderBy(I_M_PackagingTree.COLUMNNAME_Updated + " DESC")
				.first(I_M_PackagingTree.class);
		return tree;
	}

	public static List<PackagingTreeItemComparable> getItems(int tree_id, String type)
	{
		if (Utils.disableSavePickingTree) // cg : task 05659 Picking Terminal: Disable Persistency
		{
			return Collections.emptyList();
		}
		
		String whereClause = X_M_PackagingTreeItem.COLUMNNAME_M_PackagingTree_ID + " = ?  AND "
				+ X_M_PackagingTreeItem.COLUMNNAME_Type + " = ?";
		
		List<X_M_PackagingTreeItem> list = new Query(Env.getCtx(), X_M_PackagingTreeItem.Table_Name, whereClause, null)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setParameters(tree_id, type)
				.list(X_M_PackagingTreeItem.class);
		
		final List<PackagingTreeItemComparable> result = new ArrayList<>();
		
		for (final X_M_PackagingTreeItem pi : list)
		{
			final PackagingTreeItemComparable pic = new PackagingTreeItemComparable(pi.getCtx(), pi.getM_PackagingTreeItem_ID(), pi.get_TrxName());
			result.add(pic);
		}
		
		return result;
	}

	public static List<X_M_PackagingTreeItemSched> getNonItems(int tree_id)
	{
		if (Utils.disableSavePickingTree) // cg : task 05659 Picking Terminal: Disable Persistency
		{
			return Collections.emptyList();
		}
		
		String whereClause = X_M_PackagingTreeItem.COLUMNNAME_M_PackagingTree_ID + " = ?  AND "
				+ X_M_PackagingTreeItem.COLUMNNAME_Type + " = ?";

		int id = new Query(Env.getCtx(), X_M_PackagingTreeItem.Table_Name, whereClause, null)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setParameters(tree_id, X_M_PackagingTreeItem.TYPE_NonItem)
				.firstIdOnly();

		whereClause = X_M_PackagingTreeItemSched.COLUMNNAME_M_PackagingTreeItem_ID + " = ? ";

		return new Query(Env.getCtx(), X_M_PackagingTreeItemSched.Table_Name, whereClause, null)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setParameters(id)
				.list(X_M_PackagingTreeItemSched.class);

	}
	
	/**
	 * check if the product is in the package
	 * @param product_id
	 * @param package_id
	 * @param qty
	 * @return
	 */
	public static boolean isPacked(final int product_id, final int package_id, final BigDecimal qty)
	{
		String whereClause = X_M_PackageLine.COLUMNNAME_M_Package_ID + " = ? AND "
				           + X_M_PackageLine.COLUMNNAME_Qty + " = ?";

		List<X_M_PackageLine> list = new Query(Env.getCtx(), X_M_PackageLine.Table_Name, whereClause, null)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setParameters(package_id, qty)
				.list(X_M_PackageLine.class);

		for (X_M_PackageLine pl : list)
		{
			if (pl.getM_InOutLine_ID() > 0)
			{
				if (pl.getM_InOutLine().getM_Product_ID() >0 )
				{
					if (pl.getM_InOutLine().getM_Product_ID() == product_id )
						return true;
				}
			}
		}
		
		return false;

	}


	public static List<X_M_PackagingTreeItemSched> getSchedforItem(int item_id)
	{
		if (Utils.disableSavePickingTree) // cg : task 05659 Picking Terminal: Disable Persistency
		{
			return Collections.emptyList();
		}
		
		String whereClause = X_M_PackagingTreeItemSched.COLUMNNAME_M_PackagingTreeItem_ID + " = ?";
		return new Query(Env.getCtx(), X_M_PackagingTreeItemSched.Table_Name, whereClause, null)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setParameters(item_id)
				.list(X_M_PackagingTreeItemSched.class);
	}
	
	/**
	 * fetch open boxes by partener's location
	 * @param C_BPartner_Location_ID
	 * @return
	 */
	public static List<PackagingTreeItemComparable> getOpenBoxes(int C_BPartner_Location_ID, int M_Warehouse_Dest_ID, boolean isGrouppingByWarehouse)
	{
		if (Utils.disableSavePickingTree) // cg : task 05659 Picking Terminal: Disable Persistency
		{
			return Collections.emptyList();
		}
		
		String whereClause = X_M_PackagingTreeItem.COLUMNNAME_M_PackageTree_ID + " IN (SELECT "
				+ X_M_PackagingTreeItem.COLUMNNAME_M_PackageTree_ID + " FROM " + X_M_PackageTree.Table_Name + " WHERE " ;
		
		List<Object> params = new ArrayList<>();
		
		if (isGrouppingByWarehouse)
		{
			whereClause  += X_M_PackageTree.COLUMNNAME_M_Package_ID + " IN (SELECT " + I_M_Package.COLUMNNAME_M_Package_ID 
						 + " FROM " + I_M_Package.Table_Name + " WHERE ";
			if (M_Warehouse_Dest_ID == 0)
			{
				whereClause  += I_M_Package.COLUMNNAME_M_Warehouse_Dest_ID + " is null ";
			}
			else
			{
				whereClause  += I_M_Package.COLUMNNAME_M_Warehouse_Dest_ID + " = ? ";
				params.add(M_Warehouse_Dest_ID);
			}
			whereClause  += " AND " +I_M_Package.COLUMNNAME_Processed + " = ?) AND ";
			params.add(false);
		}

		// the location matters anyway
		{
			whereClause  +=  X_M_PackageTree.COLUMNNAME_C_BPartner_Location_ID + " = ? AND ";
			params.add(C_BPartner_Location_ID);
		}
		
		whereClause  += X_M_PackageTree.COLUMNNAME_IsClosed + " = ?)"
				+ " AND " + X_M_PackagingTreeItem.COLUMNNAME_Status + " != ? "
				+ " AND " + X_M_PackagingTreeItem.COLUMNNAME_Type + " = ?";
		params.add(false);
		params.add(X_M_PackagingTreeItem.STATUS_UnPacked);
		params.add(X_M_PackagingTreeItem.TYPE_Box);
		List<X_M_PackagingTreeItem> list = new Query(Env.getCtx(), X_M_PackagingTreeItem.Table_Name, whereClause, null)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setParameters(params)
				.list(X_M_PackagingTreeItem.class);
		List<PackagingTreeItemComparable> result = new ArrayList<>();
		for (X_M_PackagingTreeItem pi : list)
		{
			PackagingTreeItemComparable pic = new PackagingTreeItemComparable(pi.getCtx(), pi.getM_PackagingTreeItem_ID(), pi.get_TrxName());
			if (!result.contains(pic))
			{
				pic.setStatus(X_M_PackagingTreeItem.STATUS_Open);
				result.add(pic);
			}
		}

		return result;
	}
	
	/**
	 * fetch virtual package
	 * @param M_Package_ID
	 * @return
	 */
	public static X_M_PackageTree retrieveVirtualPackage(final Properties ctx, final int M_Package_ID,  final String trxName)
	{
		if (Utils.disableSavePickingTree) // cg : task 05659 Picking Terminal: Disable Persistency
		{
			return null;
		}
		
		String whereClause =  X_M_PackageTree.COLUMNNAME_IsClosed + " = ? AND "
						   + X_M_PackageTree.COLUMNNAME_M_Package_ID + " = ?";
		return new Query(ctx, X_M_PackageTree.Table_Name, whereClause, trxName)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setParameters(false,  M_Package_ID)
				.firstOnly();
	}
}
