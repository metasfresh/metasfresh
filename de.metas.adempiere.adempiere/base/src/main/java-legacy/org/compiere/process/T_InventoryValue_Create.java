/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 * Portions created by Carlos Ruiz are Copyright (C) 2005 QSS Ltda.
 * Contributor(s): Carlos Ruiz (globalqss)
 *****************************************************************************/
package org.compiere.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.ValueNamePair;

import de.metas.logging.MetasfreshLastError;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

/**
 * Title:	Inventory Valuation Temporary Table
 *	
 *  @author Carlos Ruiz (globalqss)
 *  @version $Id: T_InventoryValue_Create.java,v 1.0 2005/09/21 20:29:00 globalqss Exp $
 */
public class T_InventoryValue_Create extends SvrProcess
{

	/** The Parameters		*/
	private int p_M_PriceList_Version_ID;
	private Timestamp p_DateValue;
	private int p_M_Warehouse_ID;
	private int p_C_Currency_ID;
	/** The Record						*/
	private int		p_Record_ID = 0;
	/** The Instance					*/
	private int     p_PInstance_ID;
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;			
			else if (name.equals("M_PriceList_Version_ID"))
				p_M_PriceList_Version_ID = para[i].getParameterAsInt();
			else if (name.equals("DateValue"))
				p_DateValue = (Timestamp)para[i].getParameter();
			else if (name.equals("M_Warehouse_ID"))
				p_M_Warehouse_ID = para[i].getParameterAsInt();
			else if (name.equals("C_Currency_ID"))
				p_C_Currency_ID = para[i].getParameterAsInt();
			else
				log.error("Unknown Parameter: " + name);
		}
		p_Record_ID = getRecord_ID();
		p_PInstance_ID = getAD_PInstance_ID();
	}	//	prepare

	/**
	 * 	Process
	 *	@return message
	 *	@throws Exception
	 */
	@Override
	protected String doIt() throws Exception
	{
		String sqlupd;
		String sqlins;
		int cntu = 0;
		int cnti = 0;

		log.info("Inventory Valuation Temporary Table");

		// Clear
		//	v_ResultStr := 'ClearTable';
		//	DELETE T_InventoryValue WHERE M_Warehouse_ID=p_M_Warehouse_ID;
		//	COMMIT;		
		
		// Insert Products
		sqlins = "INSERT INTO T_InventoryValue "
		       + "(AD_Client_ID,AD_Org_ID, AD_PInstance_ID, M_Warehouse_ID,M_Product_ID) "
		       + "SELECT AD_Client_ID,AD_Org_ID," + p_PInstance_ID + "," + p_M_Warehouse_ID + ",M_Product_ID "
		       + "FROM M_Product "
		       + "WHERE IsStocked='Y'";
		cnti = DB.executeUpdate(sqlins, get_TrxName());
		if (cnti == 0) {
			return "@Created@ = 0";
		}
		if (cnti < 0) {
			raiseError("InsertStockedProducts:ERROR", sqlins);
		}
		
		// Update Constants
        // En Oracle SET DateValue = TRUNC(?) + 0.9993, equivale a sumar 23:59 a la fecha
		p_DateValue.setHours(23);
		p_DateValue.setMinutes(59);
		p_DateValue.setSeconds(0);
		sqlupd = "UPDATE T_InventoryValue "
		       + "SET DateValue = ?, "
		       +     "M_PriceList_Version_ID = ? , "
		       +     "C_Currency_ID = ? "
		       + "WHERE M_Warehouse_ID = ?";
		PreparedStatement pstmt = DB.prepareStatement(sqlupd, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE, get_TrxName());
		pstmt.setTimestamp(1, p_DateValue);
		pstmt.setInt(2, p_M_PriceList_Version_ID);
		pstmt.setInt(3, p_C_Currency_ID);
		pstmt.setInt(4, p_M_Warehouse_ID);
		cntu = pstmt.executeUpdate();
		if (cntu < 0) {
			raiseError("UpdateConstants:ERROR", sqlupd);
		}
		
		// Get current QtyOnHand
		sqlupd = "UPDATE T_InventoryValue "
	           + "SET QtyOnHand = (SELECT SUM(QtyOnHand) FROM M_Storage s, M_Locator l "
	           + "WHERE T_InventoryValue.M_Product_ID=s.M_Product_ID "
	           + "AND l.M_Locator_ID=s.M_Locator_ID "
	           + "AND l.M_Warehouse_ID=T_InventoryValue.M_Warehouse_ID) "
	           + "WHERE T_InventoryValue.M_Warehouse_ID = " + p_M_Warehouse_ID;		
		cntu = DB.executeUpdate(sqlupd, get_TrxName());
		if (cntu < 0) {
			raiseError("GetQtyOnHand:ERROR", sqlupd);
		}
		
		// Adjust for Valuation Date
		sqlupd = "UPDATE T_InventoryValue " 
	           + "SET QtyOnHand = "
	           + "(SELECT T_InventoryValue.QtyOnHand - COALESCE(SUM(t.MovementQty), 0) " 
	           + "FROM M_Transaction t, M_Locator l "
	           + "WHERE t.M_Product_ID=T_InventoryValue.M_Product_ID " 
	           // + "AND t.M_AttributeSetInstance_ID=T_InventoryValue.M_AttributeSetInstance_ID "
	           + "AND t.MovementDate > T_InventoryValue.DateValue "
	           + "AND t.M_Locator_ID=l.M_Locator_ID "
	           + "AND l.M_Warehouse_ID=T_InventoryValue.M_Warehouse_ID) "
	           + "WHERE	T_InventoryValue.M_Warehouse_ID = " + p_M_Warehouse_ID;		
		cntu = DB.executeUpdate(sqlupd, get_TrxName());
		if (cntu < 0) {
			raiseError("AdjustQtyOnHand:ERROR", sqlupd);
		}
		
		// Delete Records w/o OnHand Qty
		sqlupd = "DELETE FROM T_InventoryValue " 
	           + "WHERE QtyOnHand=0 "
	           + "OR QtyOnHand IS NULL";		
		cntu = DB.executeUpdate(sqlupd, get_TrxName());
		if (cntu < 0) {
			raiseError("DeleteZeroQtyOnHand:ERROR", sqlupd);
		}
		
		// Update Prices
		sqlupd = "UPDATE T_InventoryValue "
	           + "SET PricePO = "
	           + "(SELECT currencyConvert (po.PriceList,po.C_Currency_ID,T_InventoryValue.C_Currency_ID,T_InventoryValue.DateValue, null, T_InventoryValue.AD_Client_ID, T_InventoryValue.AD_Org_ID) "
	           + "FROM M_Product_PO po WHERE po.M_Product_ID=T_InventoryValue.M_Product_ID "
	           + "AND po.IsCurrentVendor='Y' AND po.C_BPartner_ID = (SELECT MIN(po1.C_BPartner_ID) FROM M_Product_PO po1 WHERE po1.M_Product_ID=T_InventoryValue.M_Product_ID AND po1.IsCurrentVendor='Y')), "
	           + "PriceList = " 
	           + "(SELECT currencyConvert(pp.PriceList,pl.C_Currency_ID,T_InventoryValue.C_Currency_ID,T_InventoryValue.DateValue, null, T_InventoryValue.AD_Client_ID, T_InventoryValue.AD_Org_ID) "
	           + "FROM M_PriceList pl, M_PriceList_Version plv, M_ProductPrice pp "
	           + "WHERE pp.M_Product_ID=T_InventoryValue.M_Product_ID AND pp.M_PriceList_Version_ID=T_InventoryValue.M_PriceList_Version_ID "
	           + "AND pp.M_PriceList_Version_ID=plv.M_PriceList_Version_ID "
	           + "AND plv.M_PriceList_ID=pl.M_PriceList_ID), "
	           + "PriceStd = " 
	           + "(SELECT currencyConvert(pp.PriceStd,pl.C_Currency_ID,T_InventoryValue.C_Currency_ID,T_InventoryValue.DateValue, null, T_InventoryValue.AD_Client_ID, T_InventoryValue.AD_Org_ID) "
	           + "FROM M_PriceList pl, M_PriceList_Version plv, M_ProductPrice pp "
	           + "WHERE pp.M_Product_ID=T_InventoryValue.M_Product_ID AND pp.M_PriceList_Version_ID=T_InventoryValue.M_PriceList_Version_ID "
	           + "AND pp.M_PriceList_Version_ID=plv.M_PriceList_Version_ID "
	           + "AND plv.M_PriceList_ID=pl.M_PriceList_ID), " 
	           + "PriceLimit = " 
	           + "(SELECT currencyConvert(pp.PriceLimit,pl.C_Currency_ID,T_InventoryValue.C_Currency_ID,T_InventoryValue.DateValue, null, T_InventoryValue.AD_Client_ID, T_InventoryValue.AD_Org_ID) "
	           + "FROM M_PriceList pl, M_PriceList_Version plv, M_ProductPrice pp "
	           + "WHERE pp.M_Product_ID=T_InventoryValue.M_Product_ID AND pp.M_PriceList_Version_ID=T_InventoryValue.M_PriceList_Version_ID "
	           + "AND pp.M_PriceList_Version_ID=plv.M_PriceList_Version_ID "
	           + "AND plv.M_PriceList_ID=pl.M_PriceList_ID), "
	           + "CostStandard = " 
	           + "(SELECT currencyConvert(pc.CurrentCostPrice,acs.C_Currency_ID,T_InventoryValue.C_Currency_ID,T_InventoryValue.DateValue, null, T_InventoryValue.AD_Client_ID, T_InventoryValue.AD_Org_ID) "
	           + "FROM AD_ClientInfo ci, C_AcctSchema acs, M_Product_Costing pc "
	           + "WHERE T_InventoryValue.AD_Client_ID=ci.AD_Client_ID AND ci.C_AcctSchema1_ID=acs.C_AcctSchema_ID "
	           + "AND acs.C_AcctSchema_ID=pc.C_AcctSchema_ID "
	           + "AND T_InventoryValue.M_Product_ID=pc.M_Product_ID) "
	           + "WHERE	T_InventoryValue.M_Warehouse_ID = " + p_M_Warehouse_ID;		
		cntu = DB.executeUpdate(sqlupd, get_TrxName());
		if (cntu < 0) {
			raiseError("GetPrices:ERROR", sqlupd);
		}
		
		// Update Values
		sqlupd = "UPDATE T_InventoryValue " 
	           + "SET PricePOAmt = QtyOnHand * PricePO, " 
	           + "PriceListAmt = QtyOnHand * PriceList, " 
	           + "PriceStdAmt = QtyOnHand * PriceStd, " 
	           + "PriceLimitAmt = QtyOnHand * PriceLimit, " 
	           + "CostStandardAmt = QtyOnHand * CostStandard "
	           + "WHERE	M_Warehouse_ID = " + p_M_Warehouse_ID;
		cntu = DB.executeUpdate(sqlupd, get_TrxName());
		if (cntu < 0) {
			raiseError("UpdateValue:ERROR", sqlupd);
		}
		
		DB.commit(true, get_TrxName());
		return "@Created@ = " + cntu;		
	}	//	doIt
	
	private void raiseError(String string, String sql) throws Exception {
		DB.rollback(false, get_TrxName());
		String msg = string;
		ValueNamePair pp = MetasfreshLastError.retrieveError();
		if (pp != null)
			msg = pp.getName() + " - ";
		msg += sql;
		throw new AdempiereUserError (msg);
	}
	
}	//	T_InventoryValue_Create
