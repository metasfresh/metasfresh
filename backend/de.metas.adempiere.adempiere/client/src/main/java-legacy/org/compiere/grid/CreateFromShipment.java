/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin *
 * Copyright (C) 2009 Idalica Corporation *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 *****************************************************************************/

package org.compiere.grid;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.i18n.Msg;
import de.metas.inout.location.adapter.InOutDocumentLocationAdapterFactory;
import de.metas.order.location.adapter.OrderDocumentLocationAdapterFactory;
import de.metas.util.Services;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.I_M_Locator;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MRMA;
import org.compiere.model.MRMALine;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Create Invoice Transactions from PO Orders or Receipt
 *
 * @author Jorg Janke
 * @version $Id: VCreateFromShipment.java,v 1.4 2006/07/30 00:51:28 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <li>BF [ 1896947 ] Generate invoice from Order error
 *         <li>BF [ 2007837 ] VCreateFrom.save() should run in trx
 */
public class CreateFromShipment extends CreateFrom
{
	/** Loaded Invoice */
	private MInvoice m_invoice = null;
	/** Loaded RMA */
	private MRMA m_rma = null;
	private int defaultLocator_ID = 0;

	/**
	 * Protected Constructor
	 * 
	 * @param mTab MTab
	 */
	public CreateFromShipment(GridTab mTab)
	{
		super(mTab);
		log.info(mTab.toString());
	}   // VCreateFromShipment

	/**
	 * Dynamic Init
	 * 
	 * @return true if initialized
	 */
	@Override
	public boolean dynInit() throws Exception
	{
		log.info("");
		setTitle(Msg.getElement(Env.getCtx(), "M_InOut_ID", false) + " .. " + Msg.translate(Env.getCtx(), "CreateFrom"));

		return true;
	}   // dynInit

	/**
	 * Load PBartner dependent Order/Invoice/Shipment Field.
	 * 
	 * @param C_BPartner_ID BPartner
	 */
	protected ArrayList<KeyNamePair> loadRMAData(int C_BPartner_ID)
	{
		ArrayList<KeyNamePair> list = new ArrayList<>();

		String sqlStmt = "SELECT r.M_RMA_ID, r.DocumentNo || '-' || r.Amt from M_RMA r "
				// metas: Lieferanten- und Kundenruecklieferungen laufen nun hierueber
				// + "WHERE ISSOTRX='Y' AND r.DocStatus in ('CO', 'CL') "
				+ "WHERE r.DocStatus in ('CO', 'CL') "
				// metas ende
				+ "AND r.C_BPartner_ID=? "
				+ "AND r.M_RMA_ID in (SELECT rl.M_RMA_ID FROM M_RMALine rl "
				+ "WHERE rl.M_RMA_ID=r.M_RMA_ID AND rl.QtyDelivered < rl.Qty "
				+ "AND rl.M_InOutLine_ID IS NOT NULL)";

		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sqlStmt, null);
			pstmt.setInt(1, C_BPartner_ID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				list.add(new KeyNamePair(rs.getInt(1), rs.getString(2)));
			}
			rs.close();
		}
		catch (SQLException e)
		{
			log.error(sqlStmt.toString(), e);
		}
		finally
		{
			if (pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch (Exception ex)
				{
					log.error("Could not close prepared statement");
				}
			}
		}

		return list;
	}

	/**
	 * Load PBartner dependent Order/Invoice/Shipment Field.
	 * 
	 * @param C_BPartner_ID
	 */
	protected ArrayList<KeyNamePair> loadInvoiceData(int C_BPartner_ID)
	{
		ArrayList<KeyNamePair> list = new ArrayList<>();

		StringBuffer display = new StringBuffer("i.DocumentNo||' - '||")
				.append(DB.TO_CHAR("DateInvoiced", DisplayType.Date, Env.getAD_Language(Env.getCtx())))
				.append("|| ' - ' ||")
				.append(DB.TO_CHAR("GrandTotal", DisplayType.Amount, Env.getAD_Language(Env.getCtx())));
		//
		StringBuffer sql = new StringBuffer("SELECT i.C_Invoice_ID,").append(display)
				.append(" FROM C_Invoice i "
						+ "WHERE i.C_BPartner_ID=? AND i.IsSOTrx='N' AND i.DocStatus IN ('CL','CO')"
						+ " AND i.C_Invoice_ID IN "
						+ "(SELECT il.C_Invoice_ID FROM C_InvoiceLine il"
						+ " LEFT OUTER JOIN M_MatchInv mi ON (il.C_InvoiceLine_ID=mi.C_InvoiceLine_ID) "
						+ " JOIN C_Invoice i2 ON (il.C_Invoice_ID = i2.C_Invoice_ID) "
						+ " WHERE i2.C_BPartner_ID=? AND i2.IsSOTrx='N' AND i2.DocStatus IN ('CL','CO') "
						+ "GROUP BY il.C_Invoice_ID,mi.C_InvoiceLine_ID,il.QtyInvoiced "
						+ "HAVING (il.QtyInvoiced<>SUM(mi.Qty) AND mi.C_InvoiceLine_ID IS NOT NULL)"
						+ " OR mi.C_InvoiceLine_ID IS NULL) "
						+ "ORDER BY i.DateInvoiced");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, C_BPartner_ID);
			pstmt.setInt(2, C_BPartner_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				list.add(new KeyNamePair(rs.getInt(1), rs.getString(2)));
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql.toString(), e);
		}
		return list;
	}

	/**
	 * Load Data - Order
	 * 
	 * @param C_Order_ID Order
	 * @param forInvoice true if for invoice vs. delivery qty
	 */
	@Override
	protected Vector<Vector<Object>> getOrderData(int C_Order_ID, boolean forInvoice)
	{
		/**
		 * Selected - 0
		 * Qty - 1
		 * C_UOM_ID - 2
		 * M_Locator_ID - 3
		 * M_Product_ID - 4
		 * VendorProductNo - 5
		 * OrderLine - 6
		 * ShipmentLine - 7
		 * InvoiceLine - 8
		 */
		log.info("C_Order_ID=" + C_Order_ID);
		p_order = new MOrder(Env.getCtx(), C_Order_ID, null);      // save

		Vector<Vector<Object>> data = new Vector<>();
		StringBuffer sql = new StringBuffer("SELECT "
				+ "l.QtyOrdered-SUM(COALESCE(m.Qty,0)),"					// 1
				+ "CASE WHEN l.QtyOrdered=0 THEN 0 ELSE l.QtyEntered/l.QtyOrdered END,"	// 2
				+ " l.C_UOM_ID,COALESCE(uom.UOMSymbol,uom.Name),"			// 3..4
				+ " p.M_Locator_ID, loc.Value, " // 5..6
				+ " COALESCE(l.M_Product_ID,0),COALESCE(p.Name,c.Name), " // 7..8
				+ " po.VendorProductNo, " // 9
				+ " l.C_OrderLine_ID,l.Line "	// 10..11
				+ "FROM C_OrderLine l"
				+ " LEFT OUTER JOIN M_Product_PO po ON (l.M_Product_ID = po.M_Product_ID AND l.C_BPartner_ID = po.C_BPartner_ID) "
				+ " LEFT OUTER JOIN M_MatchPO m ON (l.C_OrderLine_ID=m.C_OrderLine_ID AND ");
		sql.append(forInvoice ? "m.C_InvoiceLine_ID" : "m.M_InOutLine_ID");
		sql.append(" IS NOT NULL)")
				.append(" LEFT OUTER JOIN M_Product p ON (l.M_Product_ID=p.M_Product_ID)"
						+ " LEFT OUTER JOIN M_Locator loc on (p.M_Locator_ID=loc.M_Locator_ID)"
						+ " LEFT OUTER JOIN C_Charge c ON (l.C_Charge_ID=c.C_Charge_ID)");
		if (Env.isBaseLanguage(Env.getCtx(), "C_UOM"))
			sql.append(" LEFT OUTER JOIN C_UOM uom ON (l.C_UOM_ID=uom.C_UOM_ID)");
		else
			sql.append(" LEFT OUTER JOIN C_UOM_Trl uom ON (l.C_UOM_ID=uom.C_UOM_ID AND uom.AD_Language='")
					.append(Env.getAD_Language(Env.getCtx())).append("')");
		//
		sql.append(" WHERE l.C_Order_ID=? "			// #1
				+ "GROUP BY l.QtyOrdered,CASE WHEN l.QtyOrdered=0 THEN 0 ELSE l.QtyEntered/l.QtyOrdered END, "
				+ "l.C_UOM_ID,COALESCE(uom.UOMSymbol,uom.Name), p.M_Locator_ID, loc.Value, po.VendorProductNo, "
				+ "l.M_Product_ID,COALESCE(p.Name,c.Name), l.Line,l.C_OrderLine_ID "
				+ "ORDER BY l.Line");
		//
		log.trace(sql.toString());
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, C_Order_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<>();
				line.add(new Boolean(false));           // 0-Selection
				BigDecimal qtyOrdered = rs.getBigDecimal(1);
				BigDecimal multiplier = rs.getBigDecimal(2);
				BigDecimal qtyEntered = qtyOrdered.multiply(multiplier);
				line.add(qtyEntered);  // 1-Qty
				KeyNamePair pp = new KeyNamePair(rs.getInt(3), rs.getString(4).trim());
				line.add(pp);                           // 2-UOM
				// Add locator
				line.add(getLocatorKeyNamePair(rs.getInt(5)));// 3-Locator
				// Add product
				pp = new KeyNamePair(rs.getInt(7), rs.getString(8));
				line.add(pp);                           // 4-Product
				line.add(rs.getString(9));				// 5-VendorProductNo
				pp = new KeyNamePair(rs.getInt(10), rs.getString(11));
				line.add(pp);                           // 6-OrderLine
				line.add(null);                         // 7-Ship
				line.add(null);                         // 8-Invoice
				data.add(line);
			}
		}
		catch (SQLException e)
		{
			log.error(sql.toString(), e);
			// throw new DBException(e, sql.toString());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return data;
	}   // LoadOrder

	/**
	 * Load RMA details
	 * 
	 * @param M_RMA_ID RMA
	 */
	protected Vector<Vector<Object>> getRMAData(int M_RMA_ID)
	{
		m_invoice = null;
		p_order = null;
		m_rma = new MRMA(Env.getCtx(), M_RMA_ID, null);

		Vector<Vector<Object>> data = new Vector<>();
		StringBuffer sqlStmt = new StringBuffer();
		sqlStmt.append("SELECT rl.M_RMALine_ID, rl.line, rl.Qty - rl.QtyDelivered, iol.M_Product_ID, p.Name, uom.C_UOM_ID, COALESCE(uom.UOMSymbol,uom.Name) ");
		sqlStmt.append("FROM M_RMALine rl INNER JOIN M_InOutLine iol ON rl.M_InOutLine_ID=iol.M_InOutLine_ID ");

		if (Env.isBaseLanguage(Env.getCtx(), "C_UOM"))
		{
			sqlStmt.append("LEFT OUTER JOIN C_UOM uom ON (uom.C_UOM_ID=iol.C_UOM_ID) ");
		}
		else
		{
			sqlStmt.append("LEFT OUTER JOIN C_UOM_Trl uom ON (uom.C_UOM_ID=iol.C_UOM_ID AND uom.AD_Language='");
			sqlStmt.append(Env.getAD_Language(Env.getCtx())).append("') ");
		}
		sqlStmt.append("LEFT OUTER JOIN M_Product p ON p.M_Product_ID=iol.M_Product_ID ");
		sqlStmt.append("WHERE rl.M_RMA_ID=? ");
		sqlStmt.append("AND rl.M_INOUTLINE_ID IS NOT NULL");

		sqlStmt.append(" UNION ");

		sqlStmt.append("SELECT rl.M_RMALine_ID, rl.line, rl.Qty - rl.QtyDelivered, 0, c.Name, uom.C_UOM_ID, COALESCE(uom.UOMSymbol,uom.Name) ");
		sqlStmt.append("FROM M_RMALine rl INNER JOIN C_Charge c ON c.C_Charge_ID = rl.C_Charge_ID ");
		if (Env.isBaseLanguage(Env.getCtx(), "C_UOM"))
		{
			sqlStmt.append("LEFT OUTER JOIN C_UOM uom ON (uom.C_UOM_ID=100) ");
		}
		else
		{
			sqlStmt.append("LEFT OUTER JOIN C_UOM_Trl uom ON (uom.C_UOM_ID=100 AND uom.AD_Language='");
			sqlStmt.append(Env.getAD_Language(Env.getCtx())).append("') ");
		}
		sqlStmt.append("WHERE rl.M_RMA_ID=? ");
		sqlStmt.append("AND rl.C_Charge_ID IS NOT NULL");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sqlStmt.toString(), null);
			pstmt.setInt(1, M_RMA_ID);
			pstmt.setInt(2, M_RMA_ID);
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				Vector<Object> line = new Vector<>(7);
				line.add(new Boolean(false));   // 0-Selection
				line.add(rs.getBigDecimal(3));  // 1-Qty
				KeyNamePair pp = new KeyNamePair(rs.getInt(6), rs.getString(7));
				line.add(pp); // 2-UOM
				line.add(getLocatorKeyNamePair(0));
				pp = new KeyNamePair(rs.getInt(4), rs.getString(5));
				line.add(pp); // 4-Product
				line.add(null); // 5-Vendor Product No
				line.add(null); // 6-Order
				pp = new KeyNamePair(rs.getInt(1), rs.getString(2));
				line.add(pp);   // 7-RMA
				line.add(null); // 8-invoice
				data.add(line);
			}
		}
		catch (Exception ex)
		{
			log.error(sqlStmt.toString(), ex);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return data;
	}

	/**
	 * Load Invoice details
	 * 
	 * @param C_Invoice_ID Invoice
	 */
	protected Vector<Vector<Object>> getInvoiceData(int C_Invoice_ID)
	{
		m_invoice = new MInvoice(Env.getCtx(), C_Invoice_ID, null); // save
		p_order = null;
		m_rma = null;

		Vector<Vector<Object>> data = new Vector<>();
		StringBuffer sql = new StringBuffer("SELECT " // Entered UOM
				+ "l.QtyInvoiced-SUM(COALESCE(mi.Qty,0)),l.QtyEntered/l.QtyInvoiced,"
				+ " l.C_UOM_ID,COALESCE(uom.UOMSymbol,uom.Name)," // 3..4
				+ " p.M_Locator_ID, loc.Value, " // 5..6
				+ " l.M_Product_ID,p.Name, po.VendorProductNo, l.C_InvoiceLine_ID,l.Line," // 7..11
				+ " l.C_OrderLine_ID " // 12
				+ " FROM C_InvoiceLine l ");
		if (Env.isBaseLanguage(Env.getCtx(), "C_UOM"))
			sql.append(" LEFT OUTER JOIN C_UOM uom ON (l.C_UOM_ID=uom.C_UOM_ID)");
		else
			sql.append(" LEFT OUTER JOIN C_UOM_Trl uom ON (l.C_UOM_ID=uom.C_UOM_ID AND uom.AD_Language='")
					.append(Env.getAD_Language(Env.getCtx())).append("')");

		sql.append(" LEFT OUTER JOIN M_Product p ON (l.M_Product_ID=p.M_Product_ID)")
				.append(" LEFT OUTER JOIN M_Locator loc on (p.M_Locator_ID=loc.M_Locator_ID)")
				.append(" INNER JOIN C_Invoice inv ON (l.C_Invoice_ID=inv.C_Invoice_ID)")
				.append(" LEFT OUTER JOIN M_Product_PO po ON (l.M_Product_ID = po.M_Product_ID AND inv.C_BPartner_ID = po.C_BPartner_ID)")
				.append(" LEFT OUTER JOIN M_MatchInv mi ON (l.C_InvoiceLine_ID=mi.C_InvoiceLine_ID)")

				.append(" WHERE l.C_Invoice_ID=? AND l.QtyInvoiced<>0 ")
				.append("GROUP BY l.QtyInvoiced,l.QtyEntered/l.QtyInvoiced,"
						+ "l.C_UOM_ID,COALESCE(uom.UOMSymbol,uom.Name),"
						+ "p.M_Locator_ID, loc.Value, "
						+ "l.M_Product_ID,p.Name, po.VendorProductNo, l.C_InvoiceLine_ID,l.Line,l.C_OrderLine_ID ")
				.append("ORDER BY l.Line");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, C_Invoice_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<>(7);
				line.add(new Boolean(false)); // 0-Selection
				BigDecimal qtyInvoiced = rs.getBigDecimal(1);
				BigDecimal multiplier = rs.getBigDecimal(2);
				BigDecimal qtyEntered = qtyInvoiced.multiply(multiplier);
				line.add(qtyEntered); // 1-Qty
				KeyNamePair pp = new KeyNamePair(rs.getInt(3), rs.getString(4).trim());
				line.add(pp); // 2-UOM
				// Add locator
				line.add(getLocatorKeyNamePair(rs.getInt(5))); // 3-Locator
				pp = new KeyNamePair(rs.getInt(7), rs.getString(8));
				line.add(pp); // 4-Product
				line.add(rs.getString(9));				// 5-VendorProductNo
				int C_OrderLine_ID = rs.getInt(12);
				if (rs.wasNull())
					line.add(null); // 6-Order
				else
					line.add(new KeyNamePair(C_OrderLine_ID, "."));
				line.add(null); // 7-Ship
				pp = new KeyNamePair(rs.getInt(10), rs.getString(11));
				line.add(pp); // 8-Invoice
				data.add(line);
			}
		}
		catch (SQLException e)
		{
			log.error(sql.toString(), e);
			// throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return data;
	}

	/**
	 * Get KeyNamePair for Locator.
	 * If no locator specified or the specified locator is not valid (e.g. warehouse not match),
	 * a default one will be used.
	 * 
	 * @param M_Locator_ID
	 * @return KeyNamePair
	 */
	protected KeyNamePair getLocatorKeyNamePair(final int M_Locator_ID)
	{
		I_M_Locator locator = null;

		// Load desired Locator
		if (M_Locator_ID > 0)
		{
			locator = Services.get(IWarehouseDAO.class).getLocatorByRepoId(M_Locator_ID);
			// Validate warehouse
			if (locator != null && locator.getM_Warehouse_ID() != getM_Warehouse_ID())
			{
				locator = null;
			}
		}

		// Try to use default locator from Order Warehouse
		if (locator == null && p_order != null && p_order.getM_Warehouse_ID() == getM_Warehouse_ID())
		{
			locator = Services.get(IWarehouseBL.class).getDefaultLocator(WarehouseId.ofRepoId(p_order.getM_Warehouse_ID()));
		}
		// Try to get from locator field
		if (locator == null)
		{
			if (defaultLocator_ID > 0)
			{
				locator = Services.get(IWarehouseDAO.class).getLocatorByRepoId(defaultLocator_ID);
			}
		}
		// Validate Warehouse
		if (locator == null || locator.getM_Warehouse_ID() != getM_Warehouse_ID())
		{
			locator = Services.get(IWarehouseBL.class).getDefaultLocator(WarehouseId.ofRepoId(getM_Warehouse_ID()));
		}

		KeyNamePair pp = null;
		if (locator != null)
		{
			pp = new KeyNamePair(locator.getM_Locator_ID(), locator.getValue());
		}
		return pp;
	}

	/**
	 * List number of rows selected
	 */
	@Override
	public void info()
	{

	}   // infoInvoice

	protected void configureMiniTable(IMiniTable miniTable)
	{
		miniTable.setColumnClass(0, Boolean.class, false);     // Selection
		miniTable.setColumnClass(1, BigDecimal.class, false);      // Qty
		miniTable.setColumnClass(2, String.class, true);          // UOM
		miniTable.setColumnClass(3, String.class, false);  // Locator
		miniTable.setColumnClass(4, String.class, true);   // Product
		miniTable.setColumnClass(5, String.class, true); // VendorProductNo
		miniTable.setColumnClass(6, String.class, true);     // Order
		miniTable.setColumnClass(7, String.class, true);     // Ship
		miniTable.setColumnClass(8, String.class, true);   // Invoice

		// Table UI
		miniTable.autoSize();

	}

	/**
	 * Save - Create Invoice Lines
	 * 
	 * @return true if saved
	 */
	@Override
	public boolean save(IMiniTable miniTable, String trxName)
	{
		/*
		 * dataTable.stopEditor(true);
		 * log.info("");
		 * TableModel model = dataTable.getModel();
		 * int rows = model.getRowCount();
		 * if (rows == 0)
		 * return false;
		 * //
		 * Integer defaultLoc = (Integer) locatorField.getValue();
		 * if (defaultLoc == null || defaultLoc.intValue() == 0) {
		 * locatorField.setBackground(AdempierePLAF.getFieldBackground_Error());
		 * return false;
		 * }
		 */
		int M_Locator_ID = defaultLocator_ID;
		if (M_Locator_ID == 0)
		{
			return false;
		}
		// Get Shipment
		int M_InOut_ID = ((Integer)getGridTab().getValue("M_InOut_ID")).intValue();
		MInOut inout = new MInOut(Env.getCtx(), M_InOut_ID, trxName);
		log.info(inout + ", C_Locator_ID=" + M_Locator_ID);

		// Lines
		for (int i = 0; i < miniTable.getRowCount(); i++)
		{
			if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue())
			{
				// variable values
				BigDecimal QtyEntered = (BigDecimal)miniTable.getValueAt(i, 1); // Qty
				KeyNamePair pp = (KeyNamePair)miniTable.getValueAt(i, 2); // UOM
				int C_UOM_ID = pp.getKey();
				pp = (KeyNamePair)miniTable.getValueAt(i, 3); // Locator
				// If a locator is specified on the product, choose that otherwise default locator
				M_Locator_ID = pp != null && pp.getKey() != 0 ? pp.getKey() : defaultLocator_ID;

				pp = (KeyNamePair)miniTable.getValueAt(i, 4); // Product
				int M_Product_ID = pp.getKey();
				int C_OrderLine_ID = 0;
				pp = (KeyNamePair)miniTable.getValueAt(i, 6); // OrderLine
				if (pp != null)
					C_OrderLine_ID = pp.getKey();
				int M_RMALine_ID = 0;
				pp = (KeyNamePair)miniTable.getValueAt(i, 7); // RMA
				// If we have RMA
				if (pp != null)
					M_RMALine_ID = pp.getKey();
				int C_InvoiceLine_ID = 0;
				MInvoiceLine il = null;
				pp = (KeyNamePair)miniTable.getValueAt(i, 8); // InvoiceLine
				if (pp != null)
					C_InvoiceLine_ID = pp.getKey();
				if (C_InvoiceLine_ID != 0)
					il = new MInvoiceLine(Env.getCtx(), C_InvoiceLine_ID, trxName);
				// boolean isInvoiced = (C_InvoiceLine_ID != 0);
				// Precision of Qty UOM
				int precision = 2;
				if (M_Product_ID != 0)
				{
					MProduct product = MProduct.get(Env.getCtx(), M_Product_ID);
					precision = product.getUOMPrecision();
				}
				QtyEntered = QtyEntered.setScale(precision, BigDecimal.ROUND_HALF_DOWN);
				//
				log.debug("Line QtyEntered=" + QtyEntered
						+ ", Product=" + M_Product_ID
						+ ", OrderLine=" + C_OrderLine_ID + ", InvoiceLine=" + C_InvoiceLine_ID);

				// Credit Memo - negative Qty
				if (m_invoice != null && m_invoice.isCreditMemo())
					QtyEntered = QtyEntered.negate();

				// Create new InOut Line
				MInOutLine iol = new MInOutLine(inout);
				iol.setM_Product_ID(M_Product_ID, C_UOM_ID);	// Line UOM
				iol.setQty(QtyEntered);							// Movement/Entered
				//
				MOrderLine ol = null;
				MRMALine rmal = null;
				if (C_OrderLine_ID != 0)
				{
					iol.setC_OrderLine_ID(C_OrderLine_ID);
					ol = new MOrderLine(Env.getCtx(), C_OrderLine_ID, trxName);
					if (ol.getQtyEntered().compareTo(ol.getQtyOrdered()) != 0)
					{
						iol.setMovementQty(QtyEntered
								.multiply(ol.getQtyOrdered())
								.divide(ol.getQtyEntered(), 12, BigDecimal.ROUND_HALF_UP));
						iol.setC_UOM_ID(ol.getC_UOM_ID());
					}
					iol.setM_AttributeSetInstance_ID(ol.getM_AttributeSetInstance_ID());
					iol.setDescription(ol.getDescription());
					//
					iol.setC_Project_ID(ol.getC_Project_ID());
					iol.setC_ProjectPhase_ID(ol.getC_ProjectPhase_ID());
					iol.setC_ProjectTask_ID(ol.getC_ProjectTask_ID());
					iol.setC_Activity_ID(ol.getC_Activity_ID());
					iol.setC_Campaign_ID(ol.getC_Campaign_ID());
					iol.setAD_OrgTrx_ID(ol.getAD_OrgTrx_ID());
					iol.setUser1_ID(ol.getUser1_ID());
					iol.setUser2_ID(ol.getUser2_ID());
				}
				else if (il != null)
				{
					if (il.getQtyEntered().compareTo(il.getQtyInvoiced()) != 0)
					{
						iol.setQtyEntered(QtyEntered
								.multiply(il.getQtyInvoiced())
								.divide(il.getQtyEntered(), 12, BigDecimal.ROUND_HALF_UP));
						iol.setC_UOM_ID(il.getC_UOM_ID());
					}
					iol.setDescription(il.getDescription());
					iol.setC_Project_ID(il.getC_Project_ID());
					iol.setC_ProjectPhase_ID(il.getC_ProjectPhase_ID());
					iol.setC_ProjectTask_ID(il.getC_ProjectTask_ID());
					iol.setC_Activity_ID(il.getC_Activity_ID());
					iol.setC_Campaign_ID(il.getC_Campaign_ID());
					iol.setAD_OrgTrx_ID(il.getAD_OrgTrx_ID());
					iol.setUser1_ID(il.getUser1_ID());
					iol.setUser2_ID(il.getUser2_ID());
				}
				else if (M_RMALine_ID != 0)
				{
					rmal = new MRMALine(Env.getCtx(), M_RMALine_ID, trxName);
					iol.setM_RMALine_ID(M_RMALine_ID);
					iol.setQtyEntered(QtyEntered);
					iol.setDescription(rmal.getDescription());
					iol.setM_AttributeSetInstance_ID(rmal.getM_AttributeSetInstance_ID());
					iol.setC_Project_ID(rmal.getC_Project_ID());
					iol.setC_ProjectPhase_ID(rmal.getC_ProjectPhase_ID());
					iol.setC_ProjectTask_ID(rmal.getC_ProjectTask_ID());
					iol.setC_Activity_ID(rmal.getC_Activity_ID());
					iol.setAD_OrgTrx_ID(rmal.getAD_OrgTrx_ID());
					iol.setUser1_ID(rmal.getUser1_ID());
					iol.setUser2_ID(rmal.getUser2_ID());
				}

				// Charge
				if (M_Product_ID == 0)
				{
					if (ol != null && ol.getC_Charge_ID() != 0)			// from order
						iol.setC_Charge_ID(ol.getC_Charge_ID());
					else if (il != null && il.getC_Charge_ID() != 0)	// from invoice
						iol.setC_Charge_ID(il.getC_Charge_ID());
					else if (rmal != null && rmal.getC_Charge_ID() != 0) // from rma
						iol.setC_Charge_ID(rmal.getC_Charge_ID());
				}
				// Set locator
				iol.setM_Locator_ID(M_Locator_ID);
				if (!iol.save())
					log.error("Line NOT created #" + i);
				// Create Invoice Line Link
				else if (il != null)
				{
					il.setM_InOutLine_ID(iol.getM_InOutLine_ID());
					il.saveEx();
				}
			}   // if selected
		}   // for all rows

		/**
		 * Update Header
		 * - if linked to another order/invoice/rma - remove link
		 * - if no link set it
		 */
		if (p_order != null && p_order.getC_Order_ID() != 0)
		{
			inout.setC_Order_ID(p_order.getC_Order_ID());
			inout.setAD_OrgTrx_ID(p_order.getAD_OrgTrx_ID());
			inout.setC_Project_ID(p_order.getC_Project_ID());
			inout.setC_Campaign_ID(p_order.getC_Campaign_ID());
			inout.setC_Activity_ID(p_order.getC_Activity_ID());
			inout.setUser1_ID(p_order.getUser1_ID());
			inout.setUser2_ID(p_order.getUser2_ID());
			inout.setC_Incoterms_ID(p_order.getC_Incoterms_ID());
			inout.setAD_InputDataSource_ID(p_order.getAD_InputDataSource_ID());

			if (p_order.isDropShip())
			{
				inout.setM_Warehouse_ID(p_order.getM_Warehouse_ID());
				inout.setIsDropShip(p_order.isDropShip());
				InOutDocumentLocationAdapterFactory
						.deliveryLocationAdapter(inout)
						.setFrom(OrderDocumentLocationAdapterFactory.deliveryLocationAdapter(p_order).toDocumentLocation());
			}
		}
		if (m_invoice != null && m_invoice.getC_Invoice_ID() != 0)
		{
			if (inout.getC_Order_ID() == 0)
				inout.setC_Order_ID(m_invoice.getC_Order_ID());
			inout.setC_Invoice_ID(m_invoice.getC_Invoice_ID());
			inout.setAD_OrgTrx_ID(m_invoice.getAD_OrgTrx_ID());
			inout.setC_Project_ID(m_invoice.getC_Project_ID());
			inout.setC_Campaign_ID(m_invoice.getC_Campaign_ID());
			inout.setC_Activity_ID(m_invoice.getC_Activity_ID());
			inout.setUser1_ID(m_invoice.getUser1_ID());
			inout.setUser2_ID(m_invoice.getUser2_ID());
			inout.setAD_InputDataSource_ID(m_invoice.getAD_InputDataSource_ID());
		}
		if (m_rma != null && m_rma.getM_RMA_ID() != 0)
		{
			MInOut originalIO = m_rma.getShipment();
			inout.setIsSOTrx(m_rma.isSOTrx());
			inout.setC_Order_ID(0);
			inout.setC_Invoice_ID(0);
			inout.setM_RMA_ID(m_rma.getM_RMA_ID());
			inout.setAD_OrgTrx_ID(originalIO.getAD_OrgTrx_ID());
			inout.setC_Project_ID(originalIO.getC_Project_ID());
			inout.setC_Campaign_ID(originalIO.getC_Campaign_ID());
			inout.setC_Activity_ID(originalIO.getC_Activity_ID());
			inout.setUser1_ID(originalIO.getUser1_ID());
			inout.setUser2_ID(originalIO.getUser2_ID());
			inout.setAD_InputDataSource_ID(originalIO.getAD_InputDataSource_ID());
		}
		inout.saveEx();
		return true;

	}   // saveInvoice

	protected Vector<String> getOISColumnNames()
	{
		// Header Info
		Vector<String> columnNames = new Vector<>(7);
		columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
		columnNames.add(Msg.translate(Env.getCtx(), "Quantity"));
		columnNames.add(Msg.translate(Env.getCtx(), "C_UOM_ID"));
		columnNames.add(Msg.translate(Env.getCtx(), "M_Locator_ID"));
		columnNames.add(Msg.translate(Env.getCtx(), "M_Product_ID"));
		columnNames.add(Msg.getElement(Env.getCtx(), "VendorProductNo", false));
		columnNames.add(Msg.getElement(Env.getCtx(), "C_Order_ID", false));
		columnNames.add(Msg.getElement(Env.getCtx(), "M_RMA_ID", false));
		columnNames.add(Msg.getElement(Env.getCtx(), "C_Invoice_ID", false));

		return columnNames;
	}

	protected Vector<Vector<Object>> getOrderData(int C_Order_ID, boolean forInvoice, int M_Locator_ID)
	{
		defaultLocator_ID = M_Locator_ID;
		return getOrderData(C_Order_ID, forInvoice);
	}

	protected Vector<Vector<Object>> getRMAData(int M_RMA_ID, int M_Locator_ID)
	{
		defaultLocator_ID = M_Locator_ID;
		return getRMAData(M_RMA_ID);
	}

	protected Vector<Vector<Object>> getInvoiceData(int C_Invoice_ID, int M_Locator_ID)
	{
		defaultLocator_ID = M_Locator_ID;
		return getInvoiceData(C_Invoice_ID);
	}

}
