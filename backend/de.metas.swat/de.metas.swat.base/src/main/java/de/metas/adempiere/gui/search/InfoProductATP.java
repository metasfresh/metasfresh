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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import org.slf4j.Logger;

import de.metas.i18n.Msg;
import de.metas.logging.LogManager;

import de.metas.logging.LogManager;

import org.compiere.minigrid.IMiniTable;
import org.compiere.model.X_C_DocType;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class InfoProductATP implements IInfoProductDetail
{
	private final Logger log = LogManager.getLogger(getClass());

	private IMiniTable m_tableAtp = null;

	public InfoProductATP()
	{
		init();
	}

	private void init()
	{
		m_tableAtp = createMiniTable();
		m_tableAtp.setMultiSelection(false);
	}

	
	private void refresh(int m_M_Product_ID, int m_M_Warehouse_ID, int m_M_AttributeSetInstance_ID)
	{
		// Header
		Vector<String> columnNames = new Vector<String>();
		columnNames.add(Msg.translate(Env.getCtx(), "Date"));
		columnNames.add(Msg.translate(Env.getCtx(), "QtyOnHand"));
		columnNames.add(Msg.translate(Env.getCtx(), "C_BPartner_ID"));
		columnNames.add(Msg.translate(Env.getCtx(), "QtyOrdered"));
		columnNames.add(Msg.translate(Env.getCtx(), "QtyReserved"));
		columnNames.add(Msg.translate(Env.getCtx(), "M_Locator_ID"));
		columnNames.add(Msg.translate(Env.getCtx(), "M_AttributeSetInstance_ID"));
		columnNames.add(Msg.translate(Env.getCtx(), "DocumentNo"));
		columnNames.add(Msg.translate(Env.getCtx(), "M_Warehouse_ID"));

		// Fill Storage Data
		boolean showDetail = LogManager.isLevelFine();
		String sql = "SELECT s.QtyOnHand, s.QtyReserved, s.QtyOrdered,"
				+ " productAttribute(s.M_AttributeSetInstance_ID), s.M_AttributeSetInstance_ID,";
		if (!showDetail)
			sql = "SELECT SUM(s.QtyOnHand), SUM(s.QtyReserved), SUM(s.QtyOrdered),"
					+ " productAttribute(s.M_AttributeSetInstance_ID), 0,";
		sql += " w.Name, l.Value "
				+ "FROM M_Storage s"
				+ " INNER JOIN M_Locator l ON (s.M_Locator_ID=l.M_Locator_ID)"
				+ " INNER JOIN M_Warehouse w ON (l.M_Warehouse_ID=w.M_Warehouse_ID) "
				+ "WHERE M_Product_ID=?";
		if (m_M_Warehouse_ID != 0)
			sql += " AND l.M_Warehouse_ID=?";
		if (m_M_AttributeSetInstance_ID > 0)
			sql += " AND s.M_AttributeSetInstance_ID=?";
		sql += " AND (s.QtyOnHand<>0 OR s.QtyReserved<>0 OR s.QtyOrdered<>0)";
		if (!showDetail)
			sql += " GROUP BY productAttribute(s.M_AttributeSetInstance_ID), w.Name, l.Value";
		sql += " ORDER BY l.Value";

		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		double qty = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_M_Product_ID);
			if (m_M_Warehouse_ID != 0)
				pstmt.setInt(2, m_M_Warehouse_ID);
			if (m_M_AttributeSetInstance_ID > 0)
				pstmt.setInt(3, m_M_AttributeSetInstance_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>(9);
				line.add(null); // Date
				double qtyOnHand = rs.getDouble(1);
				qty += qtyOnHand;
				line.add(new Double(qtyOnHand)); // Qty
				line.add(null); // BPartner
				line.add(new Double(rs.getDouble(3))); // QtyOrdered
				line.add(new Double(rs.getDouble(2))); // QtyReserved
				line.add(rs.getString(7)); // Locator
				String asi = rs.getString(4);
				if (showDetail && (asi == null || asi.length() == 0))
					asi = "{" + rs.getInt(5) + "}";
				line.add(asi); // ASI
				line.add(null); // DocumentNo
				line.add(rs.getString(6)); // Warehouse
				data.add(line);
			}
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		// Orders
		sql = "SELECT o.DatePromised, ol.QtyReserved,"
				+ " productAttribute(ol.M_AttributeSetInstance_ID), ol.M_AttributeSetInstance_ID,"
				+ " dt.DocBaseType, bp.Name,"
				+ " dt.PrintName || ' ' || o.DocumentNo As DocumentNo, w.Name "
				+ "FROM C_Order o"
				+ " INNER JOIN C_OrderLine ol ON (o.C_Order_ID=ol.C_Order_ID)"
				+ " INNER JOIN C_DocType dt ON (o.C_DocType_ID=dt.C_DocType_ID)"
				+ " INNER JOIN M_Warehouse w ON (ol.M_Warehouse_ID=w.M_Warehouse_ID)"
				+ " INNER JOIN C_BPartner bp  ON (o.C_BPartner_ID=bp.C_BPartner_ID) "
				+ "WHERE ol.QtyReserved<>0"
				+ " AND ol.M_Product_ID=?";
		if (m_M_Warehouse_ID != 0)
			sql += " AND ol.M_Warehouse_ID=?";
		if (m_M_AttributeSetInstance_ID > 0)
			sql += " AND ol.M_AttributeSetInstance_ID=?";
		sql += " ORDER BY o.DatePromised";
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_M_Product_ID);
			if (m_M_Warehouse_ID != 0)
				pstmt.setInt(2, m_M_Warehouse_ID);
			if (m_M_AttributeSetInstance_ID > 0)
				pstmt.setInt(3, m_M_AttributeSetInstance_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>(9);
				line.add(rs.getTimestamp(1)); // Date
				double oq = rs.getDouble(2);
				String DocBaseType = rs.getString(5);
				Double qtyReserved = null;
				Double qtyOrdered = null;
				if (X_C_DocType.DOCBASETYPE_PurchaseOrder.equals(DocBaseType))
				{
					qtyOrdered = new Double(oq);
					qty += oq;
				}
				else
				{
					qtyReserved = new Double(oq);
					qty -= oq;
				}
				line.add(new Double(qty)); // Qty
				line.add(rs.getString(6)); // BPartner
				line.add(qtyOrdered); // QtyOrdered
				line.add(qtyReserved); // QtyReserved
				line.add(null); // Locator
				String asi = rs.getString(3);
				if (showDetail && (asi == null || asi.length() == 0))
					asi = "{" + rs.getInt(4) + "}";
				line.add(asi); // ASI
				line.add(rs.getString(7)); // DocumentNo
				line.add(rs.getString(8)); // Warehouse
				data.add(line);
			}
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		// Table
		setModel(columnNames, data);
		m_tableAtp.setColumnClass(0, Timestamp.class, true); // Date
		m_tableAtp.setColumnClass(1, Double.class, true); // Quantity
		m_tableAtp.setColumnClass(2, String.class, true); // Partner
		m_tableAtp.setColumnClass(3, Double.class, true); // Quantity
		m_tableAtp.setColumnClass(4, Double.class, true); // Quantity
		m_tableAtp.setColumnClass(5, String.class, true); // Locator
		m_tableAtp.setColumnClass(6, String.class, true); // ASI
		m_tableAtp.setColumnClass(7, String.class, true); // DocNo
		m_tableAtp.setColumnClass(8, String.class, true); // Warehouse
		//
		m_tableAtp.autoSize();

	}

	public java.awt.Component getComponent()
	{
		return (java.awt.Component)m_tableAtp;
	}

	protected IMiniTable createMiniTable()
	{
		org.compiere.minigrid.MiniTable table = new org.compiere.minigrid.MiniTable();
		table.setRowSelectionAllowed(false);
		return table;
	}

	protected void setModel(Vector<String> columnNames, Vector<Vector<Object>> data)
	{
		org.compiere.minigrid.MiniTable table = (org.compiere.minigrid.MiniTable)m_tableAtp;
		javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(data, columnNames);
		table.setModel(model);
	}

	@Override
	public void refresh(int M_Product_ID, int M_Warehouse_ID, int M_AttributeSetInstance_ID, int M_PriceList_Version_ID)
	{
		refresh(M_Product_ID, M_Warehouse_ID, M_AttributeSetInstance_ID);
		
	}

}
