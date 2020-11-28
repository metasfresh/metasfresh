/**
 *
 */
package org.adempiere.model.tree.spi.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.model.tree.spi.IPOTreeSupport;
import org.compiere.model.GridTab;
import org.compiere.model.MTree;
import org.compiere.model.MTreeNode;
import org.compiere.model.MTree_Base;
import org.compiere.model.PO;
import org.compiere.model.X_AD_Tree;
import org.compiere.print.MPrintColor;

/**
 * @author tsa
 *
 */
public class DefaultPOTreeSupport implements IPOTreeSupport
{
	public static final String COLUMNNAME_PrintColor = "PrintColor";

	private String tableName;

	private boolean checkRoleAccessWhileLoading = true;

	@Override
	public int getAD_Tree_ID(final PO po)
	{
		return UNKNOWN_TreeID;
	}

	@Override
	public String getWhereClause(final MTree_Base tree)
	{
		return null;
	}

	@Override
	public int getParent_ID(final PO po)
	{
		return UNKNOWN_ParentID;
	}

	@Override
	public void setParent_ID(final MTree_Base tree, final int nodeId, final int parentId, final String trxName)
	{
	}

	@Override
	public int getOldParent_ID(final PO po)
	{
		return UNKNOWN_ParentID;
	}

	@Override
	public boolean isParentChanged(final PO po)
	{
		return false;
	}

	@Override
	public String getParentIdSQL()
	{
		return null;
	}

	@Override
	public MTreeNode getNodeInfo(final GridTab gridTab)
	{
		final int nodeId = gridTab.getRecord_ID();
		final String name = String.valueOf(gridTab.getValue("Name"));
		final String description = String.valueOf(gridTab.getValue("Description"));
		final Boolean isSummaryObj = (Boolean)gridTab.getValue("IsSummary");
		final boolean isSummary = isSummaryObj != null && isSummaryObj.booleanValue();
		final String imageIndicator = String.valueOf(gridTab.getValue("Action"));
		final int seqNo = 0;
		final int parent_ID = 0;
		final boolean onBar = false;
		final Color color = null;
		return new MTreeNode(nodeId, seqNo, name, description, parent_ID, isSummary, imageIndicator, onBar, color);
	}

	protected String getPrintColorSQL(final String tableAlias)
	{
		return "NULL";
	}

	protected Color loadPrintColor(final MTree tree, final ResultSet rs) throws SQLException
	{
		Color color = null;
		final int AD_PrintColor_ID = rs.getInt(COLUMNNAME_PrintColor);
		if (AD_PrintColor_ID > 0)
		{
			final MPrintColor printColor = MPrintColor.get(tree.getCtx(), AD_PrintColor_ID);
			if (printColor != null)
			{
				color = printColor.getColor();
			}
		}
		return color;
	}

	protected String getNodeInfoFromSQL(final MTree tree, final String tableAlias)
	{
		String tableName = tree.getSourceTableName();
		tableName += " " + tableAlias;
		return tableName;
	}

	@Override
	public String getNodeInfoSelectSQL(final MTree tree, final List<Object> sqlParams)
	{
		final String tableAlias = tree.getSourceTableName();
		final String columnNameX = tree.getSourceKeyColumnName();
		if (columnNameX == null)
		{
			throw new IllegalArgumentException("Unknown TreeType=" + tree.getTreeType());
		}
		final String fromClause = getNodeInfoFromSQL(tree, tableAlias);
		final StringBuffer sqlNode = new StringBuffer();
		sqlNode.append("SELECT ")
		.append(tableAlias).append(".").append(columnNameX).append(" AS Node_ID")
		.append(",").append(tableAlias).append(".Name")
		.append(",").append(tableAlias).append(".Description")
		.append(",").append(tableAlias).append(".IsSummary")
		.append(",").append(getPrintColorSQL(tableAlias)).append(" AS ").append(COLUMNNAME_PrintColor)
		.append(" FROM ").append(fromClause);
		if (!tree.isEditable())
		{
			sqlNode.append(" WHERE ").append(tableAlias).append(".IsActive='Y'");
		}

		return sqlNode.toString();
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	public MTreeNode loadNodeInfo(final MTree tree, final ResultSet rs) throws SQLException
	{
		final int nodeId = rs.getInt("Node_ID");
		final String name = rs.getString("Name");
		final String description = rs.getString("Description");
		final boolean isAllowChildren = "Y".equals(rs.getString("IsSummary"));
		final Color color = loadPrintColor(tree, rs);

		final int seqNo = 0;
		final String imageIndicator = null;
		final boolean onBar = false;
		final int parent_ID = 0;
		
		final MTreeNode node = new MTreeNode(
				nodeId, 
				seqNo, 
				name, 
				description,
				parent_ID, 
				isAllowChildren, 
				imageIndicator, 
				onBar, 
				color);
		
		node.setAD_Tree_ID(tree.getAD_Tree_ID());
		node.setAD_Table_ID(tree.getAD_Table_ID());
		return node;
	}

	/**
	 * Code cut&pasted (and cleaned up) from {@link org.compiere.model.MTree#getDefaultAD_Tree_ID(int, String)}
	 */
	@Override
	public String getTreeType()
	{
		final String keyColumnName = tableName + "_ID";

		final String treeType;
		if (keyColumnName.equals("AD_Menu_ID"))
		{
			treeType = X_AD_Tree.TREETYPE_Menu;
		}
		else if (keyColumnName.equals("C_ElementValue_ID"))
		{
			treeType = X_AD_Tree.TREETYPE_ElementValue;
		}
		else if (keyColumnName.equals("M_Product_ID"))
		{
			treeType = X_AD_Tree.TREETYPE_Product;
		}
		else if (keyColumnName.equals("C_BPartner_ID"))
		{
			treeType = X_AD_Tree.TREETYPE_BPartner;
		}
		else if (keyColumnName.equals("AD_Org_ID"))
		{
			treeType = X_AD_Tree.TREETYPE_Organization;
		}
		else if (keyColumnName.equals("C_Project_ID"))
		{
			treeType = X_AD_Tree.TREETYPE_Project;
		}
		else if (keyColumnName.equals("M_ProductCategory_ID"))
		{
			treeType = X_AD_Tree.TREETYPE_ProductCategory;
		}
		else if (keyColumnName.equals("M_BOM_ID"))
		{
			treeType = X_AD_Tree.TREETYPE_BoM;
		}
		else if (keyColumnName.equals("C_SalesRegion_ID"))
		{
			treeType = X_AD_Tree.TREETYPE_SalesRegion;
		}
		else if (keyColumnName.equals("C_Campaign_ID"))
		{
			treeType = X_AD_Tree.TREETYPE_Campaign;
		}
		else if (keyColumnName.equals("C_Activity_ID"))
		{
			treeType = X_AD_Tree.TREETYPE_Activity;
		}
		else if (keyColumnName.equals("CM_CStage_ID"))
		{
			treeType = X_AD_Tree.TREETYPE_CMContainerStage;
		}
		else if (keyColumnName.equals("CM_Container_ID"))
		{
			treeType = X_AD_Tree.TREETYPE_CMContainer;
		}
		else if (keyColumnName.equals("CM_Media_ID"))
		{
			treeType = X_AD_Tree.TREETYPE_CMMedia;
		}
		else if (keyColumnName.equals("CM_Template_ID"))
		{
			treeType = X_AD_Tree.TREETYPE_CMTemplate;
		}
		else
		{
			treeType = null;
		}
		return treeType;
	}

	@Override
	public void setTableName(final String tableName)
	{
		this.tableName = tableName;
	}
	
	protected String getTableName()
	{
		return tableName;
	}
	
	@Override
	public final void disableRoleAccessCheckWhileLoading()
	{
		this.checkRoleAccessWhileLoading = false;
	}
	
	protected final boolean isCheckRoleAccessWhileLoading()
	{
		return checkRoleAccessWhileLoading;
	}
}
