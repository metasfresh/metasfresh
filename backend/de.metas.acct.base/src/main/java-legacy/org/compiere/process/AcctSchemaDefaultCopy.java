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
 *****************************************************************************/
package org.compiere.process;

import java.math.BigDecimal;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MAcctSchemaDefault;
import org.compiere.util.AdempiereSystemError;
import org.compiere.util.DB;

/**
 * 	Add or Copy Acct Schema Default Accounts
 *	
 *  @author Jorg Janke
 *  @version $Id: AcctSchemaDefaultCopy.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class AcctSchemaDefaultCopy extends JavaProcess
{
	/**	Acct Schema					*/
	private int			p_C_AcctSchema_ID = 0;
	/** Copy & Overwrite			*/
	private boolean 	p_CopyOverwriteAcct = false;
	
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare ()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_AcctSchema_ID"))
				p_C_AcctSchema_ID = para[i].getParameterAsInt();
			else if (name.equals("CopyOverwriteAcct"))
				p_CopyOverwriteAcct = "Y".equals(para[i].getParameter());
			else
				log.error("Unknown Parameter: " + name);
		}
	}	//	prepare
		
	/**
	 * 	Process
	 *	@return message
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{
		log.info("C_AcctSchema_ID=" + p_C_AcctSchema_ID
			+ ", CopyOverwriteAcct=" + p_CopyOverwriteAcct);
		if (p_C_AcctSchema_ID == 0)
			throw new AdempiereSystemError("C_AcctSchema_ID=0");
		MAcctSchema as = MAcctSchema.get(getCtx(), p_C_AcctSchema_ID);
		if (as.get_ID() == 0)
			throw new AdempiereSystemError("Not Found - C_AcctSchema_ID=" + p_C_AcctSchema_ID);
		MAcctSchemaDefault acct = MAcctSchemaDefault.get (getCtx(), p_C_AcctSchema_ID);
		if (acct == null || acct.get_ID() == 0)
			throw new AdempiereSystemError("Default Not Found - C_AcctSchema_ID=" + p_C_AcctSchema_ID);
		
		String sql = null;
		int updated = 0;
		int created = 0;
		int updatedTotal = 0;
		int createdTotal = 0;
		
		//	Update existing Product Category
		if (p_CopyOverwriteAcct)
		{
			sql = "UPDATE M_Product_Category_Acct pa "
				+ "SET P_Revenue_Acct=" + acct.getP_Revenue_Acct()
				+ ", P_Expense_Acct=" + acct.getP_Expense_Acct()
				+ ", P_CostAdjustment_Acct=" + acct.getP_CostAdjustment_Acct()
				+ ", P_InventoryClearing_Acct=" + acct.getP_InventoryClearing_Acct()
				+ ", P_Asset_Acct=" + acct.getP_Asset_Acct()
				+ ", P_COGS_Acct=" + acct.getP_COGS_Acct()
				+ ", P_PurchasePriceVariance_Acct=" + acct.getP_PurchasePriceVariance_Acct()
				+ ", P_InvoicePriceVariance_Acct=" + acct.getP_InvoicePriceVariance_Acct()
				+ ", P_TradeDiscountRec_Acct=" + acct.getP_TradeDiscountRec_Acct()
				+ ", P_TradeDiscountGrant_Acct=" + acct.getP_TradeDiscountGrant_Acct()
				+ ", P_WIP_Acct=" + acct.getP_WIP_Acct()
				+ ", P_FloorStock_Acct=" + acct.getP_FloorStock_Acct()
				+ ", P_MethodChangeVariance_Acct=" + acct.getP_MethodChangeVariance_Acct()
				+ ", P_UsageVariance_Acct=" + acct.getP_UsageVariance_Acct()
				+ ", P_RateVariance_Acct=" + acct.getP_RateVariance_Acct()
				+ ", P_MixVariance_Acct=" + acct.getP_MixVariance_Acct()
				+ ", P_Labor_Acct=" + acct.getP_Labor_Acct()
				+ ", P_Burden_Acct=" + acct.getP_Burden_Acct()
				+ ", P_CostOfProduction_Acct=" + acct.getP_CostOfProduction_Acct()
				+ ", P_OutsideProcessing_Acct=" + acct.getP_OutsideProcessing_Acct()			
				+ ", P_Overhead_Acct=" + acct.getP_Overhead_Acct()	
				+ ", P_Scrap_Acct=" + acct.getP_Scrap_Acct()	
				+ ", Updated=now(), UpdatedBy=0 "
				+ "WHERE pa.C_AcctSchema_ID=" + p_C_AcctSchema_ID
				+ " AND EXISTS (SELECT * FROM M_Product_Category p "
					+ "WHERE p.M_Product_Category_ID=pa.M_Product_Category_ID)";
			updated = DB.executeUpdate(sql, get_TrxName());
			addLog(0, null, new BigDecimal(updated), "@Updated@ @M_Product_Category_ID@");
			updatedTotal += updated;
		}
		//	Insert new Product Category
		sql = "INSERT INTO M_Product_Category_Acct "
			+ "(M_Product_Category_ID, C_AcctSchema_ID,"
			+ " AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,"
			+ " P_Revenue_Acct, P_Expense_Acct, P_CostAdjustment_Acct, P_InventoryClearing_Acct, P_Asset_Acct, P_CoGs_Acct,"
			+ " P_PurchasePriceVariance_Acct, P_InvoicePriceVariance_Acct,"
			+ " P_TradeDiscountRec_Acct, P_TradeDiscountGrant_Acct," 
			+ " P_WIP_Acct,P_FloorStock_Acct,P_MethodChangeVariance_Acct,P_UsageVariance_Acct,P_RateVariance_Acct," 
			+ " P_MixVariance_Acct,P_Labor_Acct,P_Burden_Acct,P_CostOfProduction_Acct,P_OutsideProcessing_Acct,P_Overhead_Acct,P_Scrap_Acct) "
			+ " SELECT p.M_Product_Category_ID, acct.C_AcctSchema_ID,"
			+ " p.AD_Client_ID, p.AD_Org_ID, 'Y', now(), 0, now(), 0,"
			+ " acct.P_Revenue_Acct, acct.P_Expense_Acct, acct.P_CostAdjustment_Acct, acct.P_InventoryClearing_Acct, acct.P_Asset_Acct, acct.P_CoGs_Acct,"
			+ " acct.P_PurchasePriceVariance_Acct, acct.P_InvoicePriceVariance_Acct,"
			+ " acct.P_TradeDiscountRec_Acct, acct.P_TradeDiscountGrant_Acct,"
			+ " acct.P_WIP_Acct,acct.P_FloorStock_Acct,acct.P_MethodChangeVariance_Acct,acct.P_UsageVariance_Acct,acct.P_RateVariance_Acct," 
			+ " acct.P_MixVariance_Acct,acct.P_Labor_Acct,acct.P_Burden_Acct,acct.P_CostOfProduction_Acct,acct.P_OutsideProcessing_Acct,P_Overhead_Acct,P_Scrap_Acct "
			+ "FROM M_Product_Category p"
			+ " INNER JOIN C_AcctSchema_Default acct ON (p.AD_Client_ID=acct.AD_Client_ID) "
			+ "WHERE acct.C_AcctSchema_ID=" + p_C_AcctSchema_ID
			+ " AND NOT EXISTS (SELECT * FROM M_Product_Category_Acct pa "
				+ "WHERE pa.M_Product_Category_ID=p.M_Product_Category_ID"
				+ " AND pa.C_AcctSchema_ID=acct.C_AcctSchema_ID)";
		created = DB.executeUpdate(sql, get_TrxName());
		addLog(0, null, new BigDecimal(created), "@Created@ @M_Product_Category_ID@");
		createdTotal += created;
		if (!p_CopyOverwriteAcct)	//	Insert new Products
		{
			sql = "INSERT INTO M_Product_Acct "
				+ "(M_Product_ID, C_AcctSchema_ID,"
				+ " AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,"
				+ " P_Revenue_Acct, P_Expense_Acct, P_CostAdjustment_Acct, P_InventoryClearing_Acct, P_Asset_Acct, P_CoGs_Acct,"
				+ " P_PurchasePriceVariance_Acct, P_InvoicePriceVariance_Acct,"
				+ " P_TradeDiscountRec_Acct, P_TradeDiscountGrant_Acct, "
				+ " P_WIP_Acct,P_FloorStock_Acct,P_MethodChangeVariance_Acct,P_UsageVariance_Acct,P_RateVariance_Acct," 
				+ " P_MixVariance_Acct,P_Labor_Acct,P_Burden_Acct,P_CostOfProduction_Acct,P_OutsideProcessing_Acct,P_Overhead_Acct,P_Scrap_Acct) "
				+ "SELECT p.M_Product_ID, acct.C_AcctSchema_ID,"
				+ " p.AD_Client_ID, p.AD_Org_ID, 'Y', now(), 0, now(), 0,"
				+ " acct.P_Revenue_Acct, acct.P_Expense_Acct, acct.P_CostAdjustment_Acct, acct.P_InventoryClearing_Acct, acct.P_Asset_Acct, acct.P_CoGs_Acct,"
				+ " acct.P_PurchasePriceVariance_Acct, acct.P_InvoicePriceVariance_Acct,"
				+ " acct.P_TradeDiscountRec_Acct, acct.P_TradeDiscountGrant_Acct,"
				+ " acct.P_WIP_Acct,acct.P_FloorStock_Acct,acct.P_MethodChangeVariance_Acct,acct.P_UsageVariance_Acct,acct.P_RateVariance_Acct," 
				+ " acct.P_MixVariance_Acct,acct.P_Labor_Acct,acct.P_Burden_Acct,acct.P_CostOfProduction_Acct,acct.P_OutsideProcessing_Acct,acct.P_Overhead_Acct,acct.P_Scrap_Acct "
				+ "FROM M_Product p"
				+ " INNER JOIN M_Product_Category_Acct acct ON (acct.M_Product_Category_ID=p.M_Product_Category_ID)"
				+ "WHERE acct.C_AcctSchema_ID=" + p_C_AcctSchema_ID
				+ " AND p.M_Product_Category_ID=acct.M_Product_Category_ID"
				+ " AND NOT EXISTS (SELECT * FROM M_Product_Acct pa "
					+ "WHERE pa.M_Product_ID=p.M_Product_ID"
					+ " AND pa.C_AcctSchema_ID=acct.C_AcctSchema_ID)";
			created = DB.executeUpdate(sql, get_TrxName());
			addLog(0, null, new BigDecimal(created), "@Created@ @M_Product_ID@");
			createdTotal += created;
		}
		
		
		//	Update Business Partner Group
		if (p_CopyOverwriteAcct)
		{
			sql = "UPDATE C_BP_Group_Acct a "
				+ "SET C_Receivable_Acct=" + acct.getC_Receivable_Acct()
				+ ", C_Receivable_Services_Acct=" + acct.getC_Receivable_Services_Acct()
				+ ", C_Prepayment_Acct=" + acct.getC_Prepayment_Acct()
				+ ", V_Liability_Acct=" + acct.getV_Liability_Acct()
				+ ", V_Liability_Services_Acct=" + acct.getV_Liability_Services_Acct()
				+ ", V_Prepayment_Acct=" + acct.getV_Prepayment_Acct()
				+ ", PayDiscount_Exp_Acct=" + acct.getPayDiscount_Exp_Acct()
				+ ", PayDiscount_Rev_Acct=" + acct.getPayDiscount_Rev_Acct()
				+ ", WriteOff_Acct=" + acct.getWriteOff_Acct()
				+ ", NotInvoicedReceipts_Acct=" + acct.getNotInvoicedReceipts_Acct()
				+ ", UnEarnedRevenue_Acct=" + acct.getUnEarnedRevenue_Acct()
				+ ", NotInvoicedRevenue_Acct=" + acct.getNotInvoicedRevenue_Acct()
				+ ", NotInvoicedReceivables_Acct=" + acct.getNotInvoicedReceivables_Acct()
				+ ", Updated=now(), UpdatedBy=0 "
				+ "WHERE a.C_AcctSchema_ID=" + p_C_AcctSchema_ID
				+ " AND EXISTS (SELECT * FROM C_BP_Group_Acct x "
					+ "WHERE x.C_BP_Group_ID=a.C_BP_Group_ID)";
			updated = DB.executeUpdate(sql, get_TrxName());
			addLog(0, null, new BigDecimal(updated), "@Updated@ @C_BP_Group_ID@");
			updatedTotal += updated;
		}
		// Insert Business Partner Group
		sql = "INSERT INTO C_BP_Group_Acct "
			+ "(C_BP_Group_ID, C_AcctSchema_ID,"
			+ " AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,"
			+ " C_Receivable_Acct, C_Receivable_Services_Acct, C_PrePayment_Acct,"
			+ " V_Liability_Acct, V_Liability_Services_Acct, V_PrePayment_Acct,"
			+ " PayDiscount_Exp_Acct, PayDiscount_Rev_Acct, WriteOff_Acct,"
			+ " NotInvoicedReceipts_Acct, UnEarnedRevenue_Acct,"
			+ " NotInvoicedRevenue_Acct, NotInvoicedReceivables_Acct) "
			+ "SELECT x.C_BP_Group_ID, acct.C_AcctSchema_ID,"
			+ " x.AD_Client_ID, x.AD_Org_ID, 'Y', now(), 0, now(), 0,"
			+ " acct.C_Receivable_Acct, acct.C_Receivable_Services_Acct, acct.C_PrePayment_Acct,"
			+ " acct.V_Liability_Acct, acct.V_Liability_Services_Acct, acct.V_PrePayment_Acct,"
			+ " acct.PayDiscount_Exp_Acct, acct.PayDiscount_Rev_Acct, acct.WriteOff_Acct,"
			+ " acct.NotInvoicedReceipts_Acct, acct.UnEarnedRevenue_Acct,"
			+ " acct.NotInvoicedRevenue_Acct, acct.NotInvoicedReceivables_Acct "
			+ "FROM C_BP_Group x"
			+ " INNER JOIN C_AcctSchema_Default acct ON (x.AD_Client_ID=acct.AD_Client_ID) "
			+ "WHERE acct.C_AcctSchema_ID=" + p_C_AcctSchema_ID
			+ " AND NOT EXISTS (SELECT * FROM C_BP_Group_Acct a "
				+ "WHERE a.C_BP_Group_ID=x.C_BP_Group_ID"
				+ " AND a.C_AcctSchema_ID=acct.C_AcctSchema_ID)";
		created = DB.executeUpdate(sql, get_TrxName());
		addLog(0, null, new BigDecimal(created), "@Created@ @C_BP_Group_ID@");
		createdTotal += created;

		
		//	Update Business Partner - Employee
		if (p_CopyOverwriteAcct)
		{
			sql = "UPDATE C_BP_Employee_Acct a "
				+ "SET E_Expense_Acct=" + acct.getE_Expense_Acct()
				+ ", E_Prepayment_Acct=" + acct.getE_Prepayment_Acct()
				+ ", Updated=now(), UpdatedBy=0 "
				+ "WHERE a.C_AcctSchema_ID=" + p_C_AcctSchema_ID
				+ " AND EXISTS (SELECT * FROM C_BP_Employee_Acct x "
					+ "WHERE x.C_BPartner_ID=a.C_BPartner_ID)";
			updated = DB.executeUpdate(sql, get_TrxName());
			addLog(0, null, new BigDecimal(updated), "@Updated@ @C_BPartner_ID@ @IsEmployee@");
			updatedTotal += updated;
		}
		//	Insert new Business Partner - Employee
		sql = "INSERT INTO C_BP_Employee_Acct "
			+ "(C_BPartner_ID, C_AcctSchema_ID,"
			+ " AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,"
			+ " E_Expense_Acct, E_Prepayment_Acct) "
			+ "SELECT x.C_BPartner_ID, acct.C_AcctSchema_ID,"
			+ " x.AD_Client_ID, x.AD_Org_ID, 'Y', now(), 0, now(), 0,"
			+ " acct.E_Expense_Acct, acct.E_Prepayment_Acct "
			+ "FROM C_BPartner x"
			+ " INNER JOIN C_AcctSchema_Default acct ON (x.AD_Client_ID=acct.AD_Client_ID) "
			+ "WHERE acct.C_AcctSchema_ID=" + p_C_AcctSchema_ID
			+ " AND NOT EXISTS (SELECT * FROM C_BP_Employee_Acct a "
				+ "WHERE a.C_BPartner_ID=x.C_BPartner_ID"
				+ " AND a.C_AcctSchema_ID=acct.C_AcctSchema_ID)";
		created = DB.executeUpdate(sql, get_TrxName());
		addLog(0, null, new BigDecimal(created), "@Created@ @C_BPartner_ID@ @IsEmployee@");
		createdTotal += created;
		//
		if (!p_CopyOverwriteAcct)
		{
			sql = "INSERT INTO C_BP_Customer_Acct "
				+ "(C_BPartner_ID, C_AcctSchema_ID,"
				+ " AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,"
				+ " C_Receivable_Acct, C_Receivable_Services_Acct, C_PrePayment_Acct) "
				+ "SELECT p.C_BPartner_ID, acct.C_AcctSchema_ID,"
				+ " p.AD_Client_ID, p.AD_Org_ID, 'Y', now(), 0, now(), 0,"
				+ " acct.C_Receivable_Acct, acct.C_Receivable_Services_Acct, acct.C_PrePayment_Acct "
				+ "FROM C_BPartner p"
				+ " INNER JOIN C_BP_Group_Acct acct ON (acct.C_BP_Group_ID=p.C_BP_Group_ID)"
				+ "WHERE acct.C_AcctSchema_ID=" + p_C_AcctSchema_ID			//	#
				+ " AND p.C_BP_Group_ID=acct.C_BP_Group_ID"
				+ " AND NOT EXISTS (SELECT * FROM C_BP_Customer_Acct ca "
					+ "WHERE ca.C_BPartner_ID=p.C_BPartner_ID"
					+ " AND ca.C_AcctSchema_ID=acct.C_AcctSchema_ID)";
			created = DB.executeUpdate(sql, get_TrxName());
			addLog(0, null, new BigDecimal(created), "@Created@ @C_BPartner_ID@ @IsCustomer@");
			createdTotal += created;
			//
			sql = "INSERT INTO C_BP_Vendor_Acct "
				+ "(C_BPartner_ID, C_AcctSchema_ID,"
				+ " AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,"
				+ " V_Liability_Acct, V_Liability_Services_Acct, V_PrePayment_Acct) "
				+ "SELECT p.C_BPartner_ID, acct.C_AcctSchema_ID,"
				+ " p.AD_Client_ID, p.AD_Org_ID, 'Y', now(), 0, now(), 0,"
				+ " acct.V_Liability_Acct, acct.V_Liability_Services_Acct, acct.V_PrePayment_Acct "
				+ "FROM C_BPartner p"
				+ " INNER JOIN C_BP_Group_Acct acct ON (acct.C_BP_Group_ID=p.C_BP_Group_ID)"
				+ "WHERE acct.C_AcctSchema_ID=" + p_C_AcctSchema_ID			//	#
				+ " AND p.C_BP_Group_ID=acct.C_BP_Group_ID"
				+ " AND NOT EXISTS (SELECT * FROM C_BP_Vendor_Acct va "
					+ "WHERE va.C_BPartner_ID=p.C_BPartner_ID AND va.C_AcctSchema_ID=acct.C_AcctSchema_ID)";
			created = DB.executeUpdate(sql, get_TrxName());
			addLog(0, null, new BigDecimal(created), "@Created@ @C_BPartner_ID@ @IsVendor@");
			createdTotal += created;
		}

		//	Update Warehouse
		if (p_CopyOverwriteAcct)
		{
			sql = "UPDATE M_Warehouse_Acct a "
				+ "SET W_Inventory_Acct=" + acct.getW_Inventory_Acct()
				+ ", W_Differences_Acct=" + acct.getW_Differences_Acct()
				+ ", W_Revaluation_Acct=" + acct.getW_Revaluation_Acct()
				+ ", W_InvActualAdjust_Acct=" + acct.getW_InvActualAdjust_Acct()
				+ ", Updated=now(), UpdatedBy=0 "
				+ "WHERE a.C_AcctSchema_ID=" + p_C_AcctSchema_ID
				+ " AND EXISTS (SELECT * FROM M_Warehouse_Acct x "
					+ "WHERE x.M_Warehouse_ID=a.M_Warehouse_ID)";
			updated = DB.executeUpdate(sql, get_TrxName());
			addLog(0, null, new BigDecimal(updated), "@Updated@ @M_Warehouse_ID@");
			updatedTotal += updated;
		}
		//	Insert new Warehouse
		sql = "INSERT INTO M_Warehouse_Acct "
			+ "(M_Warehouse_ID, C_AcctSchema_ID,"
			+ " AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,"
			+ " W_Inventory_Acct, W_Differences_Acct, W_Revaluation_Acct, W_InvActualAdjust_Acct) "
			+ "SELECT x.M_Warehouse_ID, acct.C_AcctSchema_ID,"
			+ " x.AD_Client_ID, x.AD_Org_ID, 'Y', now(), 0, now(), 0,"
			+ " acct.W_Inventory_Acct, acct.W_Differences_Acct, acct.W_Revaluation_Acct, acct.W_InvActualAdjust_Acct "
			+ "FROM M_Warehouse x"
			+ " INNER JOIN C_AcctSchema_Default acct ON (x.AD_Client_ID=acct.AD_Client_ID) "
			+ "WHERE acct.C_AcctSchema_ID=" + p_C_AcctSchema_ID
			+ " AND NOT EXISTS (SELECT * FROM M_Warehouse_Acct a "
				+ "WHERE a.M_Warehouse_ID=x.M_Warehouse_ID"
				+ " AND a.C_AcctSchema_ID=acct.C_AcctSchema_ID)";
		created = DB.executeUpdate(sql, get_TrxName());
		addLog(0, null, new BigDecimal(created), "@Created@ @M_Warehouse_ID@");
		createdTotal += created;


		//	Update Project
		if (p_CopyOverwriteAcct)
		{
			sql = "UPDATE C_Project_Acct a "
				+ "SET PJ_Asset_Acct=" + acct.getPJ_Asset_Acct()
				+ ", PJ_WIP_Acct=" + acct.getPJ_Asset_Acct()
				+ ", Updated=now(), UpdatedBy=0 "
				+ "WHERE a.C_AcctSchema_ID=" + p_C_AcctSchema_ID
				+ " AND EXISTS (SELECT * FROM C_Project_Acct x "
					+ "WHERE x.C_Project_ID=a.C_Project_ID)";
			updated = DB.executeUpdate(sql, get_TrxName());
			addLog(0, null, new BigDecimal(updated), "@Updated@ @C_Project_ID@");
			updatedTotal += updated;
		}
		//	Insert new Projects
		sql = "INSERT INTO C_Project_Acct "
			+ "(C_Project_ID, C_AcctSchema_ID,"
			+ " AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,"
			+ " PJ_Asset_Acct, PJ_WIP_Acct) "
			+ "SELECT x.C_Project_ID, acct.C_AcctSchema_ID,"
			+ " x.AD_Client_ID, x.AD_Org_ID, 'Y', now(), 0, now(), 0,"
			+ " acct.PJ_Asset_Acct, acct.PJ_WIP_Acct "
			+ "FROM C_Project x"
			+ " INNER JOIN C_AcctSchema_Default acct ON (x.AD_Client_ID=acct.AD_Client_ID) "
			+ "WHERE acct.C_AcctSchema_ID=" + p_C_AcctSchema_ID
			+ " AND NOT EXISTS (SELECT * FROM C_Project_Acct a "
				+ "WHERE a.C_Project_ID=x.C_Project_ID"
				+ " AND a.C_AcctSchema_ID=acct.C_AcctSchema_ID)";
		created = DB.executeUpdate(sql, get_TrxName());
		addLog(0, null, new BigDecimal(created), "@Created@ @C_Project_ID@");
		createdTotal += created;


		//	Update Tax
		if (p_CopyOverwriteAcct)
		{
			sql = "UPDATE C_Tax_Acct a "
				+ "SET T_Due_Acct=" + acct.getT_Due_Acct()
				+ ", T_Liability_Acct=" + acct.getT_Liability_Acct()
				+ ", T_Credit_Acct=" + acct.getT_Credit_Acct()
				+ ", T_Receivables_Acct=" + acct.getT_Receivables_Acct()
				+ ", T_Expense_Acct=" + acct.getT_Expense_Acct()
				+ ", Updated=now(), UpdatedBy=0 "
				+ "WHERE a.C_AcctSchema_ID=" + p_C_AcctSchema_ID
				+ " AND EXISTS (SELECT * FROM C_Tax_Acct x "
					+ "WHERE x.C_Tax_ID=a.C_Tax_ID)";
			updated = DB.executeUpdate(sql, get_TrxName());
			addLog(0, null, new BigDecimal(updated), "@Updated@ @C_Tax_ID@");
			updatedTotal += updated;
		}
		//	Insert new Tax
		sql = "INSERT INTO C_Tax_Acct "
			+ "(C_Tax_ID, C_AcctSchema_ID,"
			+ " AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,"
			+ " T_Due_Acct, T_Liability_Acct, T_Credit_Acct, T_Receivables_Acct, T_Expense_Acct) "
			+ "SELECT x.C_Tax_ID, acct.C_AcctSchema_ID,"
			+ " x.AD_Client_ID, x.AD_Org_ID, 'Y', now(), 0, now(), 0,"
			+ " acct.T_Due_Acct, acct.T_Liability_Acct, acct.T_Credit_Acct, acct.T_Receivables_Acct, acct.T_Expense_Acct "
			+ "FROM C_Tax x"
			+ " INNER JOIN C_AcctSchema_Default acct ON (x.AD_Client_ID=acct.AD_Client_ID) "
			+ "WHERE acct.C_AcctSchema_ID=" + p_C_AcctSchema_ID
			+ " AND NOT EXISTS (SELECT * FROM C_Tax_Acct a "
				+ "WHERE a.C_Tax_ID=x.C_Tax_ID"
				+ " AND a.C_AcctSchema_ID=acct.C_AcctSchema_ID)";
		created = DB.executeUpdate(sql, get_TrxName());
		addLog(0, null, new BigDecimal(created), "@Created@ @C_Tax_ID@");
		createdTotal += created;


		//	Update BankAccount
		if (p_CopyOverwriteAcct)
		{
			sql = "UPDATE C_BP_BankAccount_Acct a "
				+ "SET B_InTransit_Acct=" + acct.getB_InTransit_Acct()
				+ ", B_Asset_Acct=" + acct.getB_Asset_Acct()
				+ ", B_Expense_Acct=" + acct.getB_Expense_Acct()
				+ ", B_InterestRev_Acct=" + acct.getB_InterestRev_Acct()
				+ ", B_InterestExp_Acct=" + acct.getB_InterestExp_Acct()
				+ ", B_Unidentified_Acct=" + acct.getB_Unidentified_Acct()
				+ ", B_UnallocatedCash_Acct=" + acct.getB_UnallocatedCash_Acct()
				+ ", B_PaymentSelect_Acct=" + acct.getB_PaymentSelect_Acct()
				+ ", B_SettlementGain_Acct=" + acct.getB_SettlementGain_Acct()
				+ ", B_SettlementLoss_Acct=" + acct.getB_SettlementLoss_Acct()
				+ ", B_RevaluationGain_Acct=" + acct.getB_RevaluationGain_Acct()
				+ ", B_RevaluationLoss_Acct=" + acct.getB_RevaluationLoss_Acct()
				+ ", Updated=now(), UpdatedBy=0 "
				+ "WHERE a.C_AcctSchema_ID=" + p_C_AcctSchema_ID
				+ " AND EXISTS (SELECT * FROM C_BP_BankAccount_Acct x "
					+ "WHERE x.C_BP_BankAccount_ID=a.C_BP_BankAccount_ID)";
			updated = DB.executeUpdate(sql, get_TrxName());
			addLog(0, null, new BigDecimal(updated), "@Updated@ @C_BP_BankAccount_ID@");
			updatedTotal += updated;
		}
		//	Insert new BankAccount
		sql = "INSERT INTO C_BP_BankAccount_Acct "
			+ "(C_BP_BankAccount_ID, C_AcctSchema_ID,"
			+ " AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,"
			+ " B_InTransit_Acct, B_Asset_Acct, B_Expense_Acct, B_InterestRev_Acct, B_InterestExp_Acct,"
			+ " B_Unidentified_Acct, B_UnallocatedCash_Acct, B_PaymentSelect_Acct,"
			+ " B_SettlementGain_Acct, B_SettlementLoss_Acct,"
			+ " B_RevaluationGain_Acct, B_RevaluationLoss_Acct) "
			+ "SELECT x.C_BP_BankAccount_ID, acct.C_AcctSchema_ID,"
			+ " x.AD_Client_ID, x.AD_Org_ID, 'Y', now(), 0, now(), 0,"
			+ " acct.B_InTransit_Acct, acct.B_Asset_Acct, acct.B_Expense_Acct, acct.B_InterestRev_Acct, acct.B_InterestExp_Acct,"
			+ " acct.B_Unidentified_Acct, acct.B_UnallocatedCash_Acct, acct.B_PaymentSelect_Acct,"
			+ " acct.B_SettlementGain_Acct, acct.B_SettlementLoss_Acct,"
			+ " acct.B_RevaluationGain_Acct, acct.B_RevaluationLoss_Acct "
			+ "FROM C_BP_BankAccount x"
			+ " INNER JOIN C_AcctSchema_Default acct ON (x.AD_Client_ID=acct.AD_Client_ID) "
			+ "WHERE acct.C_AcctSchema_ID=" + p_C_AcctSchema_ID
			+ " AND NOT EXISTS (SELECT * FROM C_BP_BankAccount_Acct a "
				+ "WHERE a.C_BP_BankAccount_ID=x.C_BP_BankAccount_ID"
				+ " AND a.C_AcctSchema_ID=acct.C_AcctSchema_ID)";
		created = DB.executeUpdate(sql, get_TrxName());
		addLog(0, null, new BigDecimal(created), "@Created@ @C_BP_BankAccount_ID@");
		createdTotal += created;


		//	Update Withholding
		if (p_CopyOverwriteAcct)
		{
			sql = "UPDATE C_Withholding_Acct a "
				+ "SET Withholding_Acct=" + acct.getWithholding_Acct()
				+ ", Updated=now(), UpdatedBy=0 "
				+ "WHERE a.C_AcctSchema_ID=" + p_C_AcctSchema_ID 
				+ " AND EXISTS (SELECT * FROM C_Withholding_Acct x "
					+ "WHERE x.C_Withholding_ID=a.C_Withholding_ID)";
			updated = DB.executeUpdate(sql, get_TrxName());
			addLog(0, null, new BigDecimal(updated), "@Updated@ @C_Withholding_ID@");
			updatedTotal += updated;
		}
		//	Insert new Withholding
		sql = "INSERT INTO C_Withholding_Acct "
			+ "(C_Withholding_ID, C_AcctSchema_ID,"
			+ " AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,"
			+ "	Withholding_Acct) "
			+ "SELECT x.C_Withholding_ID, acct.C_AcctSchema_ID,"
			+ " x.AD_Client_ID, x.AD_Org_ID, 'Y', now(), 0, now(), 0,"
			+ " acct.Withholding_Acct "
			+ "FROM C_Withholding x"
			+ " INNER JOIN C_AcctSchema_Default acct ON (x.AD_Client_ID=acct.AD_Client_ID) "
			+ "WHERE acct.C_AcctSchema_ID=" + p_C_AcctSchema_ID
			+ " AND NOT EXISTS (SELECT * FROM C_Withholding_Acct a "
				+ "WHERE a.C_Withholding_ID=x.C_Withholding_ID"
				+ " AND a.C_AcctSchema_ID=acct.C_AcctSchema_ID)";
		created = DB.executeUpdate(sql, get_TrxName());
		addLog(0, null, new BigDecimal(created), "@Created@ @C_Withholding_ID@");
		createdTotal += created;

		
		//	Update Charge
		if (p_CopyOverwriteAcct)
		{
			sql = "UPDATE C_Charge_Acct a "
				+ "SET Ch_Expense_Acct=" + acct.getCh_Expense_Acct()
				+ ", Ch_Revenue_Acct=" + acct.getCh_Revenue_Acct()
				+ ", Updated=now(), UpdatedBy=0 "
				+ "WHERE a.C_AcctSchema_ID=" + p_C_AcctSchema_ID
				+ " AND EXISTS (SELECT * FROM C_Charge_Acct x "
					+ "WHERE x.C_Charge_ID=a.C_Charge_ID)";
			updated = DB.executeUpdate(sql, get_TrxName());
			addLog(0, null, new BigDecimal(updated), "@Updated@ @C_Charge_ID@");
			updatedTotal += updated;
		}
		//	Insert new Charge
		sql = "INSERT INTO C_Charge_Acct "
			+ "(C_Charge_ID, C_AcctSchema_ID,"
			+ " AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,"
			+ " Ch_Expense_Acct, Ch_Revenue_Acct) "
			+ "SELECT x.C_Charge_ID, acct.C_AcctSchema_ID,"
			+ " x.AD_Client_ID, x.AD_Org_ID, 'Y', now(), 0, now(), 0,"
			+ " acct.Ch_Expense_Acct, acct.Ch_Revenue_Acct "
			+ "FROM C_Charge x"
			+ " INNER JOIN C_AcctSchema_Default acct ON (x.AD_Client_ID=acct.AD_Client_ID) "
			+ "WHERE acct.C_AcctSchema_ID=" + p_C_AcctSchema_ID
			+ " AND NOT EXISTS (SELECT * FROM C_Charge_Acct a "
				+ "WHERE a.C_Charge_ID=x.C_Charge_ID"
				+ " AND a.C_AcctSchema_ID=acct.C_AcctSchema_ID)";
		created = DB.executeUpdate(sql, get_TrxName());
		addLog(0, null, new BigDecimal(created), "@Created@ @C_Charge_ID@");
		createdTotal += created;


		//	Update Cashbook
		if (p_CopyOverwriteAcct)
		{
			sql = "UPDATE C_Cashbook_Acct a "
				+ "SET CB_Asset_Acct=" + acct.getCB_Asset_Acct()
				+ ", CB_Differences_Acct=" + acct.getCB_Differences_Acct()
				+ ", CB_CashTransfer_Acct=" + acct.getCB_CashTransfer_Acct()
				+ ", CB_Expense_Acct=" + acct.getCB_Expense_Acct()
				+ ", CB_Receipt_Acct=" + acct.getCB_Receipt_Acct()
				+ ", Updated=now(), UpdatedBy=0 "
				+ "WHERE a.C_AcctSchema_ID=" + p_C_AcctSchema_ID
				+ " AND EXISTS (SELECT * FROM C_Cashbook_Acct x "
					+ "WHERE x.C_Cashbook_ID=a.C_Cashbook_ID)";
			updated = DB.executeUpdate(sql, get_TrxName());
			addLog(0, null, new BigDecimal(updated), "@Updated@ @C_Cashbook_ID@");
			updatedTotal += updated;
		}
		//	Insert new Cashbook
		sql = "INSERT INTO C_Cashbook_Acct "
			+ "(C_Cashbook_ID, C_AcctSchema_ID,"
			+ " AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,"
			+ " CB_Asset_Acct, CB_Differences_Acct, CB_CashTransfer_Acct,"
			+ " CB_Expense_Acct, CB_Receipt_Acct) "
			+ "SELECT x.C_Cashbook_ID, acct.C_AcctSchema_ID,"
			+ " x.AD_Client_ID, x.AD_Org_ID, 'Y', now(), 0, now(), 0,"
			+ " acct.CB_Asset_Acct, acct.CB_Differences_Acct, acct.CB_CashTransfer_Acct,"
			+ " acct.CB_Expense_Acct, acct.CB_Receipt_Acct "
			+ "FROM C_Cashbook x"
			+ " INNER JOIN C_AcctSchema_Default acct ON (x.AD_Client_ID=acct.AD_Client_ID) "
			+ "WHERE acct.C_AcctSchema_ID=" + p_C_AcctSchema_ID
			+ " AND NOT EXISTS (SELECT * FROM C_Cashbook_Acct a "
				+ "WHERE a.C_Cashbook_ID=x.C_Cashbook_ID"
				+ " AND a.C_AcctSchema_ID=acct.C_AcctSchema_ID)";
		created = DB.executeUpdate(sql, get_TrxName());
		addLog(0, null, new BigDecimal(created), "@Created@ @C_Cashbook_ID@");
		createdTotal += created;
		
		return "@Created@=" + createdTotal + ", @Updated@=" + updatedTotal;
	}	//	doIt
	
}	//	AcctSchemaDefaultCopy
