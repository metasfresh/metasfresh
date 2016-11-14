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
import java.sql.SQLException;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.ValueNamePair;

import de.metas.logging.MetasfreshLastError;

/**
 * Title:	Create the (new) costing information
 * Description:
 *			- (optionally) update FutureCostPrice according to Parameter
 *			- (optionally) set CostStandard to FutureCostPrice
 *			- set CurrentCostPrice to cost depending on primary AcctSchema
 *	
 *  @author Carlos Ruiz (globalqss)
 *  @version $Id: M_Product_CostingUpdate.java,v 1.0 2005/09/26 22:28:00 globalqss Exp $
 */
public class M_Product_CostingUpdate extends SvrProcess
{

	/** The Record						*/
	private int		p_Record_ID = 0;
	private int p_AD_Client_ID = -1;
	private int p_M_Product_Category_ID = -1;
	private String p_SetFutureCostTo;
	private int p_M_PriceList_Version_ID = -1;
	private String p_SetStandardCost;
	private String v_CostingMethod;
	
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
			else if (name.equals("AD_Client_ID"))
				p_AD_Client_ID = para[i].getParameterAsInt();
			else if (name.equals("M_Product_Category_ID"))
				p_M_Product_Category_ID = para[i].getParameterAsInt();
			else if (name.equals("SetFutureCostTo"))
				p_SetFutureCostTo = (String) para[i].getParameter();
			else if (name.equals("M_PriceList_Version_ID"))
				p_M_PriceList_Version_ID = para[i].getParameterAsInt();
			else if (name.equals("SetStandardCost"))
				p_SetStandardCost = (String) para[i].getParameter();
			else
				log.error("Unknown Parameter: " + name);
		}
		p_Record_ID = getRecord_ID();
	}	//	prepare

	/**
	 * 	Process
	 *	@return message
	 *	@throws Exception
	 */
	@Override
	protected String doIt() throws Exception
	{
        StringBuffer sql = null;
		int no = 0;
		int no1 = 0;
		int no2 = 0;

		log.info("Create the (new) costing information");

		// ==========	(1) Set Future Cost To	==========
		
		if (p_SetFutureCostTo.equals("S")) {
			//	S 	- Standard Cost
			log.info("Set to Standard Cost");
	        sql = new StringBuffer(
	        		"UPDATE M_Product_Costing " +
	        		"SET FutureCostPrice = CostStandard " +
	        		"WHERE AD_Client_ID=" + p_AD_Client_ID + " AND " +
	        		"("+ p_M_Product_Category_ID + " = -1 OR " +
	        		"EXISTS (SELECT * FROM M_Product p " +
	        		"WHERE p.M_Product_Category_ID= " + p_M_Product_Category_ID + " " +
	        		"AND p.M_Product_ID=M_Product_Costing.M_Product_ID))");
	        no = DB.executeUpdate(sql.toString(), get_TrxName());
   			if (no == -1) raiseError("Set to Standard Cost:ERROR", sql.toString());
		
		} else if (p_SetFutureCostTo.equals("DP")) {
			// DP	- Difference PO			
			log.info("Set to Difference PO");
	        sql = new StringBuffer(
	        		"UPDATE M_Product_Costing " +
	        		"SET FutureCostPrice = CostStandard + (CostStandardPOAmt/CostStandardPOQty) " +
	        		"WHERE CostStandardPOQty <> 0 AND " +
	        		"CostStandardPOAmt <> 0 AND " +
	        		"AD_Client_ID="+p_AD_Client_ID+" AND " +
	        		"("+ p_M_Product_Category_ID + " = -1 OR " +
	        		"EXISTS (SELECT * FROM M_Product p " +
	        		"WHERE p.M_Product_Category_ID="+p_M_Product_Category_ID+" AND " +
	        		"p.M_Product_ID=M_Product_Costing.M_Product_ID))");
	        no = DB.executeUpdate(sql.toString(), get_TrxName());
   			if (no == -1) raiseError("Set to Difference PO:ERROR", sql.toString());
		
		} else if (p_SetFutureCostTo.equals("DI")) {
			// DI	- Difference Invoice
			log.info("Set to Difference Inv");
	        sql = new StringBuffer(
	        		"UPDATE M_Product_Costing " +
	        		"SET FutureCostPrice = CostStandard + (CostStandardCumAmt/CostStandardCumQty) " +
	        		"WHERE CostStandardCumQty <> 0 AND " +
	        		"CostStandardCumAmt <> 0 AND " +
	        		"AD_Client_ID="+p_AD_Client_ID+" AND " +
	        		"("+ p_M_Product_Category_ID + " = -1 OR " +
	        		"EXISTS (SELECT * FROM M_Product p " +
	        		"WHERE p.M_Product_Category_ID="+p_M_Product_Category_ID+" AND " +
	        		"p.M_Product_ID=M_Product_Costing.M_Product_ID))");
	        no = DB.executeUpdate(sql.toString(), get_TrxName());
   			if (no == -1) raiseError("Set to Difference Inv:ERROR", sql.toString());
		
		} else if (p_SetFutureCostTo.equals("P")) {
			// P	- Last PO Price
			log.info("Set to PO Price");
	        sql = new StringBuffer(
	        		"UPDATE M_Product_Costing " +
	        		"SET FutureCostPrice = PriceLastPO " +
	        		"WHERE PriceLastPO <> 0 AND " +
	        		"AD_Client_ID="+p_AD_Client_ID+" AND " +
	        		"("+ p_M_Product_Category_ID + " = -1 OR " +
	        		"EXISTS (SELECT * FROM M_Product p " +
	        		"WHERE p.M_Product_Category_ID="+p_M_Product_Category_ID+" AND " +
	        		"p.M_Product_ID=M_Product_Costing.M_Product_ID))");
	        no = DB.executeUpdate(sql.toString(), get_TrxName());
   			if (no == -1) raiseError("Set to PO Price:ERROR", sql.toString());
		
		} else if (p_SetFutureCostTo.equals("I")) {
			// L	- Last Inv Price
			log.info("Set to Inv Price");
	        sql = new StringBuffer(
	        		"UPDATE M_Product_Costing " +
	        		"SET FutureCostPrice = PriceLastInv " +
	        		"WHERE PriceLastInv <> 0 AND " +
	        		"AD_Client_ID="+p_AD_Client_ID+" AND " +
	        		"("+ p_M_Product_Category_ID + " = -1 OR " +
	        		"EXISTS (SELECT * FROM M_Product p " +
	        		"WHERE p.M_Product_Category_ID="+p_M_Product_Category_ID+" AND " +
	        		"p.M_Product_ID=M_Product_Costing.M_Product_ID))");
	        no = DB.executeUpdate(sql.toString(), get_TrxName());
   			if (no == -1) raiseError("Set to Inv Price:ERROR", sql.toString());
		
		} else if (p_SetFutureCostTo.equals("A")) {
			// A	- Average Cost
			log.info("Set to Average Cost");
	        sql = new StringBuffer(
	        		"UPDATE M_Product_Costing " +
	        		"SET FutureCostPrice = CostAverage " +
	        		"WHERE CostAverage <> 0 AND " +
	        		"AD_Client_ID="+p_AD_Client_ID+" AND " +
	        		"("+ p_M_Product_Category_ID + " = -1 OR " +
	        		"EXISTS (SELECT * FROM M_Product p " +
	        		"WHERE p.M_Product_Category_ID="+p_M_Product_Category_ID+" AND " +
	        		"p.M_Product_ID=M_Product_Costing.M_Product_ID))");
	        no = DB.executeUpdate(sql.toString(), get_TrxName());
   			if (no == -1) raiseError("Set to Average Cost:ERROR", sql.toString());
		
		} else if (p_SetFutureCostTo.equals("LL") && p_M_PriceList_Version_ID > 0) {
			// A	- Average Cost
			log.info("Set to PriceList " + p_M_PriceList_Version_ID);
	        sql = new StringBuffer(
	        		"UPDATE M_Product_Costing " +
	        		"SET FutureCostPrice = " +
	        		"(SELECT pp.PriceLimit " +
	        		"FROM M_ProductPrice pp " +
	        		"WHERE pp.M_PriceList_Version_ID="+p_M_PriceList_Version_ID+" AND " +
	        		"pp.M_Product_ID=M_Product_Costing.M_Product_ID)" + 
/**		  SET 	FutureCostPrice = C_Currency_Convert (
		  	--	Amount
			(SELECT pp.PriceLimit FROM M_ProductPrice pp 
			WHERE pp.M_PriceList_Version_ID=11
			  AND pp.M_Product_ID=M_Product_Costing.M_Product_ID),
			--	Cur From
			(SELECT C_Currency_ID FROM M_PriceList pl, M_PriceList_Version pv 
			WHERE pv.M_PriceList_ID=pl.M_PriceList_ID 
			  AND pv.M_PriceList_Version_ID=11),
			--	Cur To 
			(SELECT a.C_Currency_ID FROM C_AcctSchema a WHERE a.C_AcctSchema_ID=M_Product_Costing.C_AcctSchema_ID))
**/
	        		"WHERE	AD_Client_ID="+p_AD_Client_ID+ " " + 
					// we have a price
	        		"AND EXISTS (SELECT * FROM M_ProductPrice pp " +
	        		"WHERE pp.M_PriceList_Version_ID="+p_M_PriceList_Version_ID+" " +
	        		"AND pp.M_Product_ID=M_Product_Costing.M_Product_ID) " +
	        		// and the same currency
	        		"AND EXISTS (SELECT * FROM C_AcctSchema a, M_PriceList pl, M_PriceList_Version pv " +
	        		"WHERE a.C_AcctSchema_ID=M_Product_CostingUpdate.C_AcctSchema_ID " +
	        		"AND pv.M_PriceList_Version_ID="+p_M_PriceList_Version_ID+" " +
	        		"AND pv.M_PriceList_ID=pl.M_PriceList_ID " +
	        		"AND pl.C_Currency_ID=a.C_Currency_ID) " +
	        		"AND ("+p_M_Product_Category_ID+" = -1 OR " +
	        		"EXISTS (SELECT * FROM M_Product p " +
	        		"WHERE p.M_Product_Category_ID="+p_M_Product_Category_ID+" " +
	        		"AND p.M_Product_ID=M_Product_Costing.M_Product_ID))");
	        no = DB.executeUpdate(sql.toString(), get_TrxName());
   			if (no == -1) raiseError("Set to Average Cost:ERROR", sql.toString());
		
		} else {
			log.info("SetFutureCostTo=" + p_SetFutureCostTo + " ?");
			
		}
		log.info(" - Updated: " + no);

		// ==========	(2) SetStandardCost	==========
		if (p_SetStandardCost.equals("Y")) {
			// A	- Average Cost
			log.info("Set Standard Cost");
	        sql = new StringBuffer(
	        		"UPDATE M_Product_Costing " +
	        		"SET CostStandard = FutureCostPrice " +
	        		"WHERE AD_Client_ID="+ p_AD_Client_ID + " AND " +
	        		"(" + p_M_Product_Category_ID + " = -1 OR " +
	        		"EXISTS (SELECT * FROM M_Product p " +
	        		"WHERE p.M_Product_Category_ID="+p_M_Product_Category_ID+ " AND " +
	        		"p.M_Product_ID=M_Product_Costing.M_Product_ID))");
	        no1 = DB.executeUpdate(sql.toString(), get_TrxName());
   			if (no1 == -1) raiseError("Set Standard Cost", sql.toString());
			
		}
		
		// ==========	(3) Update CurrentCostPrice depending on Costing Method ==========
		try
		{
			PreparedStatement pstmt = DB.prepareStatement
				("SELECT a.CostingMethod " +
						"FROM C_AcctSchema a, AD_ClientInfo ci " +
						"WHERE a.C_AcctSchema_ID=ci.C_AcctSchema1_ID AND " +
						"ci.AD_Client_ID="+p_AD_Client_ID, get_TrxName());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				v_CostingMethod = rs.getString(1);
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			throw new Exception ("select CostingMethod", e);
		}
		// (A)verage (S)tandard
		log.info("Update Current Cost " + v_CostingMethod);
		log.info("Set Standard Cost");
        sql = new StringBuffer(
        		"UPDATE M_Product_Costing " +
        		"SET CurrentCostPrice = " +
        		"DECODE ('"+ v_CostingMethod + "', 'A', CostAverage, CostStandard) " +
        		"WHERE AD_Client_ID="+p_AD_Client_ID);
        no2 = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no2 == -1) raiseError("Set Standard Cost", sql.toString());
		log.info(" - Updated: " + no2);		
		
		return "@Updated@: " + no + "/" + no1;
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
	
}	//	M_Product_CostingUpdate
