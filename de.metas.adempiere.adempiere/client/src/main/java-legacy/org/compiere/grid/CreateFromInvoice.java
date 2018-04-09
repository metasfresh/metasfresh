/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.adempiere.util.Check;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MRMA;
import org.compiere.model.MRMALine;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

import de.metas.i18n.Msg;

/**
 *  Create Invoice Transactions from PO Orders or Receipt
 *
 *  @author Jorg Janke
 *  @version  $Id: VCreateFromInvoice.java,v 1.4 2006/07/30 00:51:28 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			<li>BF [ 1896947 ] Generate invoice from Order error
 * 			<li>BF [ 2007837 ] VCreateFrom.save() should run in trx
 */
public class CreateFromInvoice extends CreateFrom
{
	/**
	 *  Protected Constructor
	 *  @param mTab MTab
	 */
	public CreateFromInvoice(GridTab mTab)
	{
		super(mTab);
		log.info(mTab.toString());
	}   //  VCreateFromInvoice

	/**
	 *  Dynamic Init
	 *  @return true if initialized
	 */
	@Override
	public boolean dynInit() throws Exception
	{
		log.info("");
		setTitle(Msg.getElement(Env.getCtx(), "C_Invoice_ID", false) + " .. " + Msg.translate(Env.getCtx(), "CreateFrom"));

		return true;
	}   //  dynInit

	/**
	 * Load PBartner dependent Order/Invoice/Shipment Field.
	 * @param C_BPartner_ID
	 */
	protected ArrayList<KeyNamePair> loadShipmentData (int C_BPartner_ID)
	{
		ArrayList<KeyNamePair> list = new ArrayList<>();

		//	Display
		StringBuffer display = new StringBuffer("s.DocumentNo||' - '||")
			.append(DB.TO_CHAR("s.MovementDate", DisplayType.Date, Env.getAD_Language(Env.getCtx())));
		//
		StringBuffer sql = new StringBuffer("SELECT s.M_InOut_ID,").append(display)
			.append(" FROM M_InOut s "
			+ "WHERE s.C_BPartner_ID=? AND s.IsSOTrx='N' AND s.DocStatus IN ('CL','CO')"
			+ " AND s.M_InOut_ID IN "
				+ "(SELECT sl.M_InOut_ID FROM M_InOutLine sl"
				+ " LEFT OUTER JOIN M_MatchInv mi ON (sl.M_InOutLine_ID=mi.M_InOutLine_ID) "
				+ " JOIN M_InOut s2 ON (sl.M_InOut_ID=s2.M_InOut_ID) "
				+ " WHERE s2.C_BPartner_ID=? AND s2.IsSOTrx='N' AND s2.DocStatus IN ('CL','CO') "
				+ "GROUP BY sl.M_InOut_ID,mi.M_InOutLine_ID,sl.MovementQty "
				+ "HAVING (sl.MovementQty<>SUM(mi.Qty) AND mi.M_InOutLine_ID IS NOT NULL)"
				+ " OR mi.M_InOutLine_ID IS NULL) "
			+ "ORDER BY s.MovementDate");
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, C_BPartner_ID);
			pstmt.setInt(2, C_BPartner_ID);
			ResultSet rs = pstmt.executeQuery();
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
	 *  Load PBartner dependent Order/Invoice/Shipment Field.
	 *  @param C_BPartner_ID BPartner
	 */
	protected ArrayList<KeyNamePair> loadRMAData(int C_BPartner_ID) {
		ArrayList<KeyNamePair> list = new ArrayList<>();

		String sqlStmt = "SELECT r.M_RMA_ID, r.DocumentNo || '-' || r.Amt from M_RMA r "
				+ "WHERE ISSOTRX='N' AND r.DocStatus in ('CO', 'CL') "
				+ "AND r.C_BPartner_ID=? "
				+ "AND NOT EXISTS (SELECT * FROM C_Invoice inv "
				+ "WHERE inv.M_RMA_ID=r.M_RMA_ID AND inv.DocStatus IN ('CO', 'CL'))";

		PreparedStatement pstmt = null;
		try {
			pstmt = DB.prepareStatement(sqlStmt, null);
			pstmt.setInt(1, C_BPartner_ID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(new KeyNamePair(rs.getInt(1), rs.getString(2)));
			}
			rs.close();
		} catch (SQLException e) {
			log.error(sqlStmt.toString(), e);
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception ex) {
					log.error("Could not close prepared statement");
				}
			}
		}

		return list;
	}

	/**
	 *  Load Data - Shipment not invoiced
	 *  @param M_InOut_ID InOut
	 */
	protected Vector<Vector<Object>> getShipmentData(int M_InOut_ID)
	{
		log.info("M_InOut_ID=" + M_InOut_ID);
		MInOut inout = new MInOut(Env.getCtx(), M_InOut_ID, null);
		p_order = null;
		if (inout.getC_Order_ID() != 0)
			p_order = new MOrder (Env.getCtx(), inout.getC_Order_ID(), null);

		m_rma = null;
		if (inout.getM_RMA_ID() != 0)
			m_rma = new MRMA (Env.getCtx(), inout.getM_RMA_ID(), null);

		//
		Vector<Vector<Object>> data = new Vector<>();
		StringBuffer sql = new StringBuffer("SELECT "	//	QtyEntered
			+ "l.MovementQty-SUM(COALESCE(mi.Qty, 0)), l.QtyEntered/l.MovementQty,"
			+ " l.C_UOM_ID, COALESCE(uom.UOMSymbol, uom.Name),"			//  3..4
			+ " l.M_Product_ID, p.Name, po.VendorProductNo, l.M_InOutLine_ID, l.Line,"        //  5..9
			+ " l.C_OrderLine_ID " //  10
			+ " FROM M_InOutLine l "
			);
		if (Env.isBaseLanguage(Env.getCtx(), "C_UOM"))
			sql.append(" LEFT OUTER JOIN C_UOM uom ON (l.C_UOM_ID=uom.C_UOM_ID)");
		else
			sql.append(" LEFT OUTER JOIN C_UOM_Trl uom ON (l.C_UOM_ID=uom.C_UOM_ID AND uom.AD_Language='")
				.append(Env.getAD_Language(Env.getCtx())).append("')");

		sql.append(" LEFT OUTER JOIN M_Product p ON (l.M_Product_ID=p.M_Product_ID)")
			.append(" INNER JOIN M_InOut io ON (l.M_InOut_ID=io.M_InOut_ID)")
			.append(" LEFT OUTER JOIN M_Product_PO po ON (l.M_Product_ID = po.M_Product_ID AND io.C_BPartner_ID = po.C_BPartner_ID)")
			.append(" LEFT OUTER JOIN M_MatchInv mi ON (l.M_InOutLine_ID=mi.M_InOutLine_ID)")

			.append(" WHERE l.M_InOut_ID=? AND l.MovementQty<>0 ")
			.append("GROUP BY l.MovementQty, l.QtyEntered/l.MovementQty, "
				+ "l.C_UOM_ID, COALESCE(uom.UOMSymbol, uom.Name), "
				+ "l.M_Product_ID, p.Name, po.VendorProductNo, l.M_InOutLine_ID, l.Line, l.C_OrderLine_ID ")
			.append("ORDER BY l.Line");

		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, M_InOut_ID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<>(7);
				line.add(new Boolean(false));           //  0-Selection
				BigDecimal qtyMovement = rs.getBigDecimal(1);
				BigDecimal multiplier = rs.getBigDecimal(2);
				BigDecimal qtyEntered = qtyMovement.multiply(multiplier);
				line.add(qtyEntered);  //  1-Qty
				KeyNamePair pp = new KeyNamePair(rs.getInt(3), rs.getString(4).trim());
				line.add(pp);                           //  2-UOM
				pp = new KeyNamePair(rs.getInt(5), rs.getString(6));
				line.add(pp);                           //  3-Product
				line.add(rs.getString(7));				// 4-VendorProductNo
				int C_OrderLine_ID = rs.getInt(10);
				if (rs.wasNull())
					line.add(null);                     //  5-Order
				else
					line.add(new KeyNamePair(C_OrderLine_ID,"."));
				pp = new KeyNamePair(rs.getInt(8), rs.getString(9));
				line.add(pp);                           //  6-Ship
				line.add(null);                     	//  7-RMA
				data.add(line);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql.toString(), e);
		}

		return data;
	}   //  loadShipment

	/**
	 * Load RMA details
	 * @param M_RMA_ID RMA
	 */
	protected Vector<Vector<Object>> getRMAData(int M_RMA_ID)
	{
	    p_order = null;

//	    MRMA m_rma = new MRMA(Env.getCtx(), M_RMA_ID, null);

	    Vector<Vector<Object>> data = new Vector<>();
	    StringBuffer sqlStmt = new StringBuffer();
	    sqlStmt.append("SELECT rl.M_RMALine_ID, rl.line, rl.Qty - COALESCE(rl.QtyInvoiced, 0), iol.M_Product_ID, p.Name, uom.C_UOM_ID, COALESCE(uom.UOMSymbol,uom.Name) ");
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
	            pp = new KeyNamePair(rs.getInt(4), rs.getString(5));
	            line.add(pp); // 3-Product
	            line.add(null); //4-Vendor Product No
	            line.add(null); //5-Order
	            pp = new KeyNamePair(rs.getInt(1), rs.getString(2));
	            line.add(null);   //6-Ship
	            line.add(pp);   //7-RMA
	            data.add(line);
            }
	        rs.close();
	    }
	    catch (Exception ex)
	    {
	        log.error(sqlStmt.toString(), ex);
	    }
	    finally
	    {
	    	DB.close(rs, pstmt);
	    	rs = null; pstmt = null;
	    }

	    return data;
	}

	/**
	 *  List number of rows selected
	 */
	@Override
	public void info()
	{

	}   //  infoInvoice

	protected void configureMiniTable (IMiniTable miniTable)
	{
		miniTable.setColumnClass(0, Boolean.class, false);      //  0-Selection
		miniTable.setColumnClass(1, BigDecimal.class, true);        //  1-Qty
		miniTable.setColumnClass(2, String.class, true);        //  2-UOM
		miniTable.setColumnClass(3, String.class, true);        //  3-Product
		miniTable.setColumnClass(4, String.class, true);        //  4-VendorProductNo
		miniTable.setColumnClass(5, String.class, true);        //  5-Order
		miniTable.setColumnClass(6, String.class, true);        //  6-Ship
		miniTable.setColumnClass(7, String.class, true);        //  7-Invoice
		//  Table UI
		miniTable.autoSize();
	}

	/**
	 *  Save - Create Invoice Lines
	 *  @return true if saved
	 */
	@Override
	public boolean save(IMiniTable miniTable, String trxName)
	{
		//  Invoice
		int C_Invoice_ID = ((Integer)getGridTab().getValue("C_Invoice_ID")).intValue();
		MInvoice invoice = new MInvoice (Env.getCtx(), C_Invoice_ID, trxName);
		log.info(invoice.toString());

		if (p_order != null)
		{
			invoice.setOrder(p_order);	//	overwrite header values
			invoice.saveEx();
		}

		if (m_rma != null)
		{
			invoice.setM_RMA_ID(m_rma.getM_RMA_ID());
			invoice.saveEx();
		}

		MInOut inout = null;
//		if (m_M_InOut_ID > 0)
//		{
//			inout = new MInOut(Env.getCtx(), m_M_InOut_ID, trxName);
//		}
		if (inout != null && inout.getM_InOut_ID() != 0
			&& inout.getC_Invoice_ID() == 0)	//	only first time
		{
			inout.setC_Invoice_ID(C_Invoice_ID);
			inout.saveEx();
		}

		//  Lines
		for (int i = 0; i < miniTable.getRowCount(); i++)
		{
			if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue())
			{
				//  variable values
				BigDecimal QtyEntered = (BigDecimal)miniTable.getValueAt(i, 1);              //  1-Qty

				KeyNamePair pp = (KeyNamePair)miniTable.getValueAt(i, 2);   //  2-UOM
				int C_UOM_ID = pp.getKey();
				//
				pp = (KeyNamePair)miniTable.getValueAt(i, 3);               //  3-Product
				int M_Product_ID = 0;
				if (pp != null)
					M_Product_ID = pp.getKey();
				//
				int C_OrderLine_ID = 0;
				pp = (KeyNamePair)miniTable.getValueAt(i, 5);               //  5-OrderLine
				if (pp != null)
					C_OrderLine_ID = pp.getKey();
				int M_InOutLine_ID = 0;
				pp = (KeyNamePair)miniTable.getValueAt(i, 6);               //  6-Shipment
				if (pp != null)
					M_InOutLine_ID = pp.getKey();
				//
				int M_RMALine_ID = 0;
				pp = (KeyNamePair)miniTable.getValueAt(i, 7);               //  7-RMALine
				if (pp != null)
					M_RMALine_ID = pp.getKey();

				//	Precision of Qty UOM
				int precision = 2;
				if (M_Product_ID != 0)
				{
					MProduct product = MProduct.get(Env.getCtx(), M_Product_ID);
					precision = product.getUOMPrecision();
				}
				QtyEntered = QtyEntered.setScale(precision, BigDecimal.ROUND_HALF_DOWN);
				//
				log.debug("Line QtyEntered=" + QtyEntered
					+ ", Product_ID=" + M_Product_ID
					+ ", OrderLine_ID=" + C_OrderLine_ID + ", InOutLine_ID=" + M_InOutLine_ID);

				//	Create new Invoice Line
				MInvoiceLine invoiceLine = new MInvoiceLine (invoice);
				invoiceLine.setM_Product_ID(M_Product_ID, C_UOM_ID);	//	Line UOM
				invoiceLine.setQty(QtyEntered);							//	Invoiced/Entered

				//  Info
				MOrderLine orderLine = null;
				if (C_OrderLine_ID != 0)
					orderLine = new MOrderLine (Env.getCtx(), C_OrderLine_ID, trxName);
				//
				MRMALine rmaLine = null;
				if (M_RMALine_ID > 0)
					rmaLine = new MRMALine (Env.getCtx(), M_RMALine_ID, null);
				//
				MInOutLine inoutLine = null;
				if (M_InOutLine_ID != 0)
				{
					inoutLine = new MInOutLine (Env.getCtx(), M_InOutLine_ID, trxName);
					if (orderLine == null && inoutLine.getC_OrderLine_ID() != 0)
					{
						C_OrderLine_ID = inoutLine.getC_OrderLine_ID();
						orderLine = new MOrderLine (Env.getCtx(), C_OrderLine_ID, trxName);
					}
				}
				else if (C_OrderLine_ID > 0)
				{
					String whereClause = "EXISTS (SELECT 1 FROM M_InOut io WHERE io.M_InOut_ID=M_InOutLine.M_InOut_ID AND io.DocStatus IN ('CO','CL'))";
					MInOutLine[] lines = getInOutLinesByOrderLineId(Env.getCtx(), C_OrderLine_ID, whereClause, trxName);
					log.debug("Receipt Lines with OrderLine = #" + lines.length);
					if (lines.length > 0)
					{
						for (int j = 0; j < lines.length; j++)
						{
							MInOutLine line = lines[j];
							if (line.getQtyEntered().compareTo(QtyEntered) == 0)
							{
								inoutLine = line;
								M_InOutLine_ID = inoutLine.getM_InOutLine_ID();
								break;
							}
						}
						if (inoutLine == null)
						{
							inoutLine = lines[0];	//	first as default
							M_InOutLine_ID = inoutLine.getM_InOutLine_ID();
						}
					}
				}
				else if (M_RMALine_ID != 0)
				{
					String whereClause = "EXISTS (SELECT 1 FROM M_InOut io WHERE io.M_InOut_ID=M_InOutLine.M_InOut_ID AND io.DocStatus IN ('CO','CL'))";
					MInOutLine[] lines = getInOutLinesByRMALineId(Env.getCtx(), M_RMALine_ID, whereClause, null);
					log.debug("Receipt Lines with RMALine = #" + lines.length);
					if (lines.length > 0)
					{
						for (int j = 0; j < lines.length; j++)
						{
							MInOutLine line = lines[j];
							if (rmaLine.getQty().compareTo(QtyEntered) == 0)
							{
								inoutLine = line;
								M_InOutLine_ID = inoutLine.getM_InOutLine_ID();
								break;
							}
						}
						if (rmaLine == null)
						{
							inoutLine = lines[0];	//	first as default
							M_InOutLine_ID = inoutLine.getM_InOutLine_ID();
						}
					}

				}
				//	get Ship info

				//	Shipment Info
				if (inoutLine != null)
				{
					invoiceLine.setShipLine(inoutLine);		//	overwrites
					if (inoutLine.sameOrderLineUOM())
						invoiceLine.setQtyInvoiced(QtyEntered);
					else
						invoiceLine.setQtyInvoiced(inoutLine.getMovementQty());
				}
				else {
					log.debug("No Receipt Line");
					//	Order Info
					if (orderLine != null)
					{
						invoiceLine.setOrderLine(orderLine);	//	overwrites
						if (orderLine.getQtyEntered().compareTo(orderLine.getQtyOrdered()) != 0)
							invoiceLine.setQtyInvoiced(QtyEntered
								.multiply(orderLine.getQtyOrdered())
								.divide(orderLine.getQtyEntered(), 12, BigDecimal.ROUND_HALF_UP));
					}
					else
					{
						log.debug("No Order Line");
						invoiceLine.setPrice();
						invoiceLine.setTax();
					}

					//RMA Info
					if (rmaLine != null)
					{
						invoiceLine.setRMALine(rmaLine);		//	overwrites
						invoiceLine.setQty(QtyEntered);
					}
					else
						log.debug("No RMA Line");
				}
				invoiceLine.saveEx();
			}   //   if selected
		}   //  for all rows

		return true;
	}   //  saveInvoice
	
	/**
	 * Get Ship lines Of Order Line
	 *
	 * @param ctx context
	 * @param C_OrderLine_ID line
	 * @param where optional addition where clause
	 * @param trxName transaction
	 * @return array of receipt lines
	 */
	private static MInOutLine[] getInOutLinesByOrderLineId(Properties ctx, int C_OrderLine_ID, String where, String trxName)
	{
		String whereClause = "C_OrderLine_ID=?" + (!Check.isEmpty(where, true) ? " AND " + where : "");
		List<MInOutLine> list = new Query(ctx, MInOutLine.Table_Name, whereClause, trxName)
				.setParameters(new Object[] { C_OrderLine_ID })
				.list(MInOutLine.class);
		return list.toArray(new MInOutLine[list.size()]);
	}

	/**
	 * Get Ship lines Of RMA Line
	 *
	 * @param ctx context
	 * @param M_RMALine_ID line
	 * @param where optional addition where clause
	 * @param trxName transaction
	 * @return array of receipt lines
	 */
	public static MInOutLine[] getInOutLinesByRMALineId(Properties ctx, int M_RMALine_ID, String where, String trxName)
	{
		String whereClause = "M_RMALine_ID=? " + (!Check.isEmpty(where, true) ? " AND " + where : "");
		List<MInOutLine> list = new Query(ctx, MInOutLine.Table_Name, whereClause, trxName)
				.setParameters(new Object[] { M_RMALine_ID })
				.list(MInOutLine.class);
		return list.toArray(new MInOutLine[list.size()]);
	}	// getOfRMALine


	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
	    Vector<String> columnNames = new Vector<>(7);
	    columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
	    columnNames.add(Msg.translate(Env.getCtx(), "Quantity"));
	    columnNames.add(Msg.translate(Env.getCtx(), "C_UOM_ID"));
	    columnNames.add(Msg.translate(Env.getCtx(), "M_Product_ID"));
	    columnNames.add(Msg.getElement(Env.getCtx(), "VendorProductNo", false));
	    columnNames.add(Msg.getElement(Env.getCtx(), "C_Order_ID", false));
	    columnNames.add(Msg.getElement(Env.getCtx(), "M_InOut_ID", false));
	    columnNames.add(Msg.getElement(Env.getCtx(), "M_RMA_ID", false));

	    return columnNames;
	}

}
