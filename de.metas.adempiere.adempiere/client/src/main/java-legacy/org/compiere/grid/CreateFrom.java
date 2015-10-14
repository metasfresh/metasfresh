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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;

import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MOrder;
import org.compiere.model.MRMA;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

public abstract class CreateFrom implements ICreateFrom
{
	/**	Logger			*/
	protected CLogger log = CLogger.getCLogger(getClass());

	/** Loaded Order            */
	protected MOrder p_order = null;

	/**  Loaded RMA             */
	protected MRMA m_rma = null;

	private GridTab gridTab;

	private String title;

	private boolean initOK = false;

	public CreateFrom(GridTab gridTab) {
		this.gridTab = gridTab;
	}

	public abstract boolean dynInit() throws Exception;

	public abstract void info();

	public abstract boolean save(IMiniTable miniTable, String trxName);

	/**
	 *	Init OK to be able to make changes?
	 *  @return on if initialized
	 */
	public boolean isInitOK()
	{
		return initOK;
	}

	public void setInitOK(boolean initOK)
	{
		this.initOK = initOK;
	}

	/**
	 *  Load PBartner dependent Order/Invoice/Shipment Field.
	 *  @param C_BPartner_ID BPartner
	 *  @param forInvoice for invoice
	 */
	protected ArrayList<KeyNamePair> loadOrderData (int C_BPartner_ID, boolean forInvoice, boolean sameWarehouseOnly)
	{
		ArrayList<KeyNamePair> list = new ArrayList<KeyNamePair>();

		//	Display
		StringBuffer display = new StringBuffer("o.DocumentNo||' - ' ||")
			.append(DB.TO_CHAR("o.DateOrdered", DisplayType.Date, Env.getAD_Language(Env.getCtx())))
			.append("||' - '||")
			.append(DB.TO_CHAR("o.GrandTotal", DisplayType.Amount, Env.getAD_Language(Env.getCtx())));
		//
		String column = "ol.QtyDelivered";
		if (forInvoice)
			column = "ol.QtyInvoiced";
		StringBuffer sql = new StringBuffer("SELECT o.C_Order_ID,").append(display)
			.append(" FROM C_Order o "
			+ "WHERE o.C_BPartner_ID=? AND o.IsSOTrx='N' AND o.DocStatus IN ('CL','CO')"
			+ " AND o.C_Order_ID IN "
				  + "(SELECT ol.C_Order_ID FROM C_OrderLine ol"
				  + " WHERE ol.QtyOrdered - ").append(column).append(" != 0) ");
		if(sameWarehouseOnly)
		{
			sql = sql.append(" AND o.M_Warehouse_ID=? ");
		}
		sql = sql.append("ORDER BY o.DateOrdered");
		//
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, C_BPartner_ID);
			if(sameWarehouseOnly)
			{
				//only active for material receipts
				pstmt.setInt(2, getM_Warehouse_ID());
			}
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				list.add(new KeyNamePair(rs.getInt(1), rs.getString(2)));
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		return list;
	}   //  initBPartnerOIS

	/**
	 *  Load Data - Order
	 *  @param C_Order_ID Order
	 *  @param forInvoice true if for invoice vs. delivery qty
	 */
	protected Vector<Vector<Object>> getOrderData (int C_Order_ID, boolean forInvoice)
	{
		/**
		 *  Selected        - 0
		 *  Qty             - 1
		 *  C_UOM_ID        - 2
		 *  M_Product_ID    - 3
		 *  VendorProductNo - 4
		 *  OrderLine       - 5
		 *  ShipmentLine    - 6
		 *  InvoiceLine     - 7
		 */
		log.config("C_Order_ID=" + C_Order_ID);
		p_order = new MOrder (Env.getCtx(), C_Order_ID, null);

		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		StringBuffer sql = new StringBuffer("SELECT "
			+ "l.QtyOrdered-SUM(COALESCE(m.Qty,0)),"					//	1
			+ "CASE WHEN l.QtyOrdered=0 THEN 0 ELSE l.QtyEntered/l.QtyOrdered END,"	//	2
			+ " l.C_UOM_ID,COALESCE(uom.UOMSymbol,uom.Name),"			//	3..4
			+ " COALESCE(l.M_Product_ID,0),COALESCE(p.Name,c.Name),po.VendorProductNo,"	//	5..7
			+ " l.C_OrderLine_ID,l.Line "								//	8..9
			+ "FROM C_OrderLine l"
			+ " LEFT OUTER JOIN M_Product_PO po ON (l.M_Product_ID = po.M_Product_ID AND l.C_BPartner_ID = po.C_BPartner_ID) "
			+ " LEFT OUTER JOIN M_MatchPO m ON (l.C_OrderLine_ID=m.C_OrderLine_ID AND ");
		sql.append(forInvoice ? "m.C_InvoiceLine_ID" : "m.M_InOutLine_ID");
		sql.append(" IS NOT NULL)")
			.append(" LEFT OUTER JOIN M_Product p ON (l.M_Product_ID=p.M_Product_ID)"
			+ " LEFT OUTER JOIN C_Charge c ON (l.C_Charge_ID=c.C_Charge_ID)");
		if (Env.isBaseLanguage(Env.getCtx(), "C_UOM"))
			sql.append(" LEFT OUTER JOIN C_UOM uom ON (l.C_UOM_ID=uom.C_UOM_ID)");
		else
			sql.append(" LEFT OUTER JOIN C_UOM_Trl uom ON (l.C_UOM_ID=uom.C_UOM_ID AND uom.AD_Language='")
				.append(Env.getAD_Language(Env.getCtx())).append("')");
		//
		sql.append(" WHERE l.C_Order_ID=? "			//	#1
			+ "GROUP BY l.QtyOrdered,CASE WHEN l.QtyOrdered=0 THEN 0 ELSE l.QtyEntered/l.QtyOrdered END, "
			+ "l.C_UOM_ID,COALESCE(uom.UOMSymbol,uom.Name),po.VendorProductNo, "
				+ "l.M_Product_ID,COALESCE(p.Name,c.Name), l.Line,l.C_OrderLine_ID "
			+ "ORDER BY l.Line");
		//
		log.finer(sql.toString());
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, C_Order_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>();
				line.add(new Boolean(false));           //  0-Selection
				BigDecimal qtyOrdered = rs.getBigDecimal(1);
				BigDecimal multiplier = rs.getBigDecimal(2);
				BigDecimal qtyEntered = qtyOrdered.multiply(multiplier);
				line.add(qtyEntered);                   //  1-Qty
				KeyNamePair pp = new KeyNamePair(rs.getInt(3), rs.getString(4).trim());
				line.add(pp);                           //  2-UOM
				pp = new KeyNamePair(rs.getInt(5), rs.getString(6));
				line.add(pp);                           //  3-Product
				line.add(rs.getString(7));				// 4-VendorProductNo
				pp = new KeyNamePair(rs.getInt(8), rs.getString(9));
				line.add(pp);                           //  5-OrderLine
				line.add(null);                         //  6-Ship
				line.add(null);                         //  7-Invoice
				data.add(line);
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		return data;
	}   //  LoadOrder

	public void showWindow()
	{

	}

	public void closeWindow()
	{

	}

	public GridTab getGridTab()
	{
		return gridTab;
	}

	/**
	 * Get Warehouse from window's context
	 * @return warehouse id
	 */
	public int getM_Warehouse_ID()
	{
		return Env.getContextAsInt(Env.getCtx(), gridTab.getWindowNo(), "M_Warehouse_ID");
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
