package de.metas.adempiere.gui.search;

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

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.IMiniTable;
import org.compiere.minigrid.MiniTable;
import org.compiere.swing.CTextArea;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

public class InfoProductStock implements IInfoProductDetail
{
	private final Logger log = LogManager.getLogger(getClass());

	public static final int PANELTYPE_Stock = 1;
	public static final int PANELTYPE_Description = 2;

	private IMiniTable warehouseTbl = null;
	private String m_sqlWarehouse;

	// UI:
	private CTextArea fieldDescription = null;

	public InfoProductStock()
	{
		initUI();
		init();
	}

	private void initUI()
	{
		MiniTable table = new MiniTable();
		table.setRowSelectionAllowed(true);
		table.setShowTotals(true);
		warehouseTbl = table;

		fieldDescription = new CTextArea();
		fieldDescription.setBackground(AdempierePLAF.getInfoBackground());
		fieldDescription.setEditable(false);
		fieldDescription.setPreferredSize(null); // just to make sure it has no predefined size
	}

	private void init()
	{
		ColumnInfo[] s_layoutWarehouse = new ColumnInfo[] {
				new ColumnInfo(" ", "M_Warehouse_ID", IDColumn.class),
				new ColumnInfo(Msg.translate(Env.getCtx(), "Warehouse"), "Warehouse", String.class),
				new ColumnInfo(Msg.translate(Env.getCtx(), "QtyOnHand"), "sum(QtyOnHand)", Double.class),
		};
		/** From Clause */
		String s_sqlFrom = " M_PRODUCT_STOCK_V ";
		/** Where Clause */
		String s_sqlWhere = "M_Product_ID = ?";
		m_sqlWarehouse = warehouseTbl.prepareTable(s_layoutWarehouse, s_sqlFrom, s_sqlWhere, false, "M_PRODUCT_STOCK_V");
		m_sqlWarehouse += " GROUP BY M_Warehouse_ID, Warehouse, documentnote ";
		warehouseTbl.setMultiSelection(false);
		// warehouseTbl.addMouseListener(this);
		// warehouseTbl.getSelectionModel().addListSelectionListener(this);

		warehouseTbl.autoSize();
	}

	private void refresh(int M_Product_ID)
	{
		// int M_Product_ID = 0;
		String sql = m_sqlWarehouse;
		// Add description to the query
		sql = sql.replace(" FROM", ", DocumentNote FROM");
		log.trace(sql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, M_Product_ID);
			rs = pstmt.executeQuery();
			setDescription("");
			warehouseTbl.loadTable(rs);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				if (rs.getString("DocumentNote") != null)
					setDescription(rs.getString("DocumentNote"));
			}
		}
		catch (Exception e)
		{
			log.warn(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}

	private void setDescription(String description)
	{
		fieldDescription.setText(description);
	}

	public java.awt.Component getComponent(int type)
	{
		if (type == PANELTYPE_Stock)
			return (java.awt.Component)warehouseTbl;
		else if (type == PANELTYPE_Description)
			return fieldDescription;
		else
			throw new IllegalArgumentException("Unknown panel type " + type);
	}

	public int getM_Warehouse_ID()
	{
		if (warehouseTbl.getRowCount() <= 0)
			return -1;
		Integer id = warehouseTbl.getSelectedRowKey();
		return id == null ? -1 : id;
	}

	@Override
	public void refresh(final int M_Product_ID, final int M_Warehouse_ID, final int M_AttributeSetInstance_ID, final int M_PriceList_Version_ID)
	{
		refresh(M_Product_ID);
		
	}
}
