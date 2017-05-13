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
import org.slf4j.Logger;

import de.metas.i18n.Msg;
import de.metas.logging.LogManager;

import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IMiniTable;
import org.compiere.minigrid.MiniTable;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class InfoProductSubstitute implements IInfoProductDetail
{
	private final Logger log = LogManager.getLogger(getClass());

	private IMiniTable substituteTbl = null;
	private String m_sqlSubstitute;

	public InfoProductSubstitute()
	{
		initUI();
		init();
	}

	private void initUI()
	{
		MiniTable table = new MiniTable();
		table.setRowSelectionAllowed(false);
		substituteTbl = table;
	}

	private void init()
	{
		ColumnInfo[] s_layoutSubstitute = new ColumnInfo[] {
				new ColumnInfo(Msg.translate(Env.getCtx(), "Warehouse"), "orgname", String.class),
				new ColumnInfo(
						Msg.translate(Env.getCtx(), "Value"),
						"(Select Value from M_Product p where p.M_Product_ID=M_PRODUCT_SUBSTITUTERELATED_V.Substitute_ID)",
						String.class),
				new ColumnInfo(Msg.translate(Env.getCtx(), "Name"), "Name", String.class),
				new ColumnInfo(Msg.translate(Env.getCtx(), "QtyAvailable"), "QtyAvailable", Double.class),
					new ColumnInfo(Msg.translate(Env.getCtx(), "QtyOnHand"), "QtyOnHand", Double.class),
				new ColumnInfo(Msg.translate(Env.getCtx(), "QtyReserved"), "QtyReserved", Double.class),
					new ColumnInfo(Msg.translate(Env.getCtx(), "PriceStd"), "PriceStd", Double.class) };
		String s_sqlFrom = "M_PRODUCT_SUBSTITUTERELATED_V";
		String s_sqlWhere = "M_Product_ID = ? AND M_PriceList_Version_ID = ? and RowType = 'S'";
		m_sqlSubstitute = substituteTbl.prepareTable(s_layoutSubstitute, s_sqlFrom, s_sqlWhere, false, "M_PRODUCT_SUBSTITUTERELATED_V");
		substituteTbl.setMultiSelection(false);
		// substituteTbl.addMouseListener(this);
		// substituteTbl.getSelectionModel().addListSelectionListener(this);
		substituteTbl.autoSize();

	}

	private void refresh(int M_Product_ID, int M_PriceList_Version_ID)
	{
		String sql = m_sqlSubstitute;
		log.trace(sql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, M_Product_ID);
			pstmt.setInt(2, M_PriceList_Version_ID);
			rs = pstmt.executeQuery();
			substituteTbl.loadTable(rs);
			rs.close();
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

	public java.awt.Component getComponent()
	{
		return (java.awt.Component)substituteTbl;
	}

	@Override
	public void refresh(int M_Product_ID, int M_Warehouse_ID, int M_AttributeSetInstance_ID, int M_PriceList_Version_ID)
	{
		refresh(M_Product_ID, M_PriceList_Version_ID);
		
	}
}
