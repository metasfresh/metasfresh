/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Product_Category_Acct
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_M_Product_Category_Acct extends PO implements I_M_Product_Category_Acct, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_M_Product_Category_Acct (Properties ctx, int M_Product_Category_Acct_ID, String trxName)
    {
      super (ctx, M_Product_Category_Acct_ID, trxName);
      /** if (M_Product_Category_Acct_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setM_Product_Category_ID (0);
			setP_Asset_Acct (0);
			setP_Burden_Acct (0);
			setP_COGS_Acct (0);
			setP_CostAdjustment_Acct (0);
			setP_CostOfProduction_Acct (0);
			setP_Expense_Acct (0);
			setP_FloorStock_Acct (0);
			setP_InventoryClearing_Acct (0);
			setP_InvoicePriceVariance_Acct (0);
			setP_Labor_Acct (0);
			setP_MethodChangeVariance_Acct (0);
			setP_MixVariance_Acct (0);
			setP_OutsideProcessing_Acct (0);
			setP_Overhead_Acct (0);
			setP_PurchasePriceVariance_Acct (0);
			setP_RateVariance_Acct (0);
			setP_Revenue_Acct (0);
			setP_Scrap_Acct (0);
			setP_TradeDiscountGrant_Acct (0);
			setP_TradeDiscountRec_Acct (0);
			setP_UsageVariance_Acct (0);
			setP_WIP_Acct (0);
        } */
    }

    /** Load Constructor */
    public X_M_Product_Category_Acct (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_M_Product_Category_Acct[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_AcctSchema getC_AcctSchema() throws RuntimeException
    {
		return (I_C_AcctSchema)MTable.get(getCtx(), I_C_AcctSchema.Table_Name)
			.getPO(getC_AcctSchema_ID(), get_TrxName());	}

	/** Set Accounting Schema.
		@param C_AcctSchema_ID 
		Rules for accounting
	  */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	/** Get Accounting Schema.
		@return Rules for accounting
	  */
	public int getC_AcctSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** CostingLevel AD_Reference_ID=355 */
	public static final int COSTINGLEVEL_AD_Reference_ID=355;
	/** Client = C */
	public static final String COSTINGLEVEL_Client = "C";
	/** Organization = O */
	public static final String COSTINGLEVEL_Organization = "O";
	/** Batch/Lot = B */
	public static final String COSTINGLEVEL_BatchLot = "B";
	/** Set Costing Level.
		@param CostingLevel 
		The lowest level to accumulate Costing Information
	  */
	public void setCostingLevel (String CostingLevel)
	{

		set_Value (COLUMNNAME_CostingLevel, CostingLevel);
	}

	/** Get Costing Level.
		@return The lowest level to accumulate Costing Information
	  */
	public String getCostingLevel () 
	{
		return (String)get_Value(COLUMNNAME_CostingLevel);
	}

	/** CostingMethod AD_Reference_ID=122 */
	public static final int COSTINGMETHOD_AD_Reference_ID=122;
	/** Standard Costing = S */
	public static final String COSTINGMETHOD_StandardCosting = "S";
	/** Average PO = A */
	public static final String COSTINGMETHOD_AveragePO = "A";
	/** Lifo = L */
	public static final String COSTINGMETHOD_Lifo = "L";
	/** Fifo = F */
	public static final String COSTINGMETHOD_Fifo = "F";
	/** Last PO Price = p */
	public static final String COSTINGMETHOD_LastPOPrice = "p";
	/** Average Invoice = I */
	public static final String COSTINGMETHOD_AverageInvoice = "I";
	/** Last Invoice = i */
	public static final String COSTINGMETHOD_LastInvoice = "i";
	/** User Defined = U */
	public static final String COSTINGMETHOD_UserDefined = "U";
	/** _ = x */
	public static final String COSTINGMETHOD__ = "x";
	/** Set Costing Method.
		@param CostingMethod 
		Indicates how Costs will be calculated
	  */
	public void setCostingMethod (String CostingMethod)
	{

		set_Value (COLUMNNAME_CostingMethod, CostingMethod);
	}

	/** Get Costing Method.
		@return Indicates how Costs will be calculated
	  */
	public String getCostingMethod () 
	{
		return (String)get_Value(COLUMNNAME_CostingMethod);
	}

	public I_M_Product_Category getM_Product_Category() throws RuntimeException
    {
		return (I_M_Product_Category)MTable.get(getCtx(), I_M_Product_Category.Table_Name)
			.getPO(getM_Product_Category_ID(), get_TrxName());	}

	/** Set Product Category.
		@param M_Product_Category_ID 
		Category of a Product
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Product Category.
		@return Category of a Product
	  */
	public int getM_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_Asset_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_Asset_Acct(), get_TrxName());	}

	/** Set Product Asset.
		@param P_Asset_Acct 
		Account for Product Asset (Inventory)
	  */
	public void setP_Asset_Acct (int P_Asset_Acct)
	{
		set_Value (COLUMNNAME_P_Asset_Acct, Integer.valueOf(P_Asset_Acct));
	}

	/** Get Product Asset.
		@return Account for Product Asset (Inventory)
	  */
	public int getP_Asset_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Asset_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_Burden_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_Burden_Acct(), get_TrxName());	}

	/** Set Burden.
		@param P_Burden_Acct 
		The Burden account is the account used Manufacturing Order
	  */
	public void setP_Burden_Acct (int P_Burden_Acct)
	{
		set_Value (COLUMNNAME_P_Burden_Acct, Integer.valueOf(P_Burden_Acct));
	}

	/** Get Burden.
		@return The Burden account is the account used Manufacturing Order
	  */
	public int getP_Burden_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Burden_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_COGS_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_COGS_Acct(), get_TrxName());	}

	/** Set Product COGS.
		@param P_COGS_Acct 
		Account for Cost of Goods Sold
	  */
	public void setP_COGS_Acct (int P_COGS_Acct)
	{
		set_Value (COLUMNNAME_P_COGS_Acct, Integer.valueOf(P_COGS_Acct));
	}

	/** Get Product COGS.
		@return Account for Cost of Goods Sold
	  */
	public int getP_COGS_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_COGS_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_CostAdjustment_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_CostAdjustment_Acct(), get_TrxName());	}

	/** Set Cost Adjustment.
		@param P_CostAdjustment_Acct 
		Product Cost Adjustment Account
	  */
	public void setP_CostAdjustment_Acct (int P_CostAdjustment_Acct)
	{
		set_Value (COLUMNNAME_P_CostAdjustment_Acct, Integer.valueOf(P_CostAdjustment_Acct));
	}

	/** Get Cost Adjustment.
		@return Product Cost Adjustment Account
	  */
	public int getP_CostAdjustment_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_CostAdjustment_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_CostOfProduction_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_CostOfProduction_Acct(), get_TrxName());	}

	/** Set Cost Of Production.
		@param P_CostOfProduction_Acct 
		The Cost Of Production account is the account used Manufacturing Order
	  */
	public void setP_CostOfProduction_Acct (int P_CostOfProduction_Acct)
	{
		set_Value (COLUMNNAME_P_CostOfProduction_Acct, Integer.valueOf(P_CostOfProduction_Acct));
	}

	/** Get Cost Of Production.
		@return The Cost Of Production account is the account used Manufacturing Order
	  */
	public int getP_CostOfProduction_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_CostOfProduction_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_Expense_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_Expense_Acct(), get_TrxName());	}

	/** Set Product Expense.
		@param P_Expense_Acct 
		Account for Product Expense
	  */
	public void setP_Expense_Acct (int P_Expense_Acct)
	{
		set_Value (COLUMNNAME_P_Expense_Acct, Integer.valueOf(P_Expense_Acct));
	}

	/** Get Product Expense.
		@return Account for Product Expense
	  */
	public int getP_Expense_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Expense_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_FloorStock_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_FloorStock_Acct(), get_TrxName());	}

	/** Set Floor Stock.
		@param P_FloorStock_Acct 
		The Floor Stock account is the account used Manufacturing Order
	  */
	public void setP_FloorStock_Acct (int P_FloorStock_Acct)
	{
		set_Value (COLUMNNAME_P_FloorStock_Acct, Integer.valueOf(P_FloorStock_Acct));
	}

	/** Get Floor Stock.
		@return The Floor Stock account is the account used Manufacturing Order
	  */
	public int getP_FloorStock_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_FloorStock_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_InventoryClearing_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_InventoryClearing_Acct(), get_TrxName());	}

	/** Set Inventory Clearing.
		@param P_InventoryClearing_Acct 
		Product Inventory Clearing Account
	  */
	public void setP_InventoryClearing_Acct (int P_InventoryClearing_Acct)
	{
		set_Value (COLUMNNAME_P_InventoryClearing_Acct, Integer.valueOf(P_InventoryClearing_Acct));
	}

	/** Get Inventory Clearing.
		@return Product Inventory Clearing Account
	  */
	public int getP_InventoryClearing_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_InventoryClearing_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_InvoicePriceVariance_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_InvoicePriceVariance_Acct(), get_TrxName());	}

	/** Set Invoice Price Variance.
		@param P_InvoicePriceVariance_Acct 
		Difference between Costs and Invoice Price (IPV)
	  */
	public void setP_InvoicePriceVariance_Acct (int P_InvoicePriceVariance_Acct)
	{
		set_Value (COLUMNNAME_P_InvoicePriceVariance_Acct, Integer.valueOf(P_InvoicePriceVariance_Acct));
	}

	/** Get Invoice Price Variance.
		@return Difference between Costs and Invoice Price (IPV)
	  */
	public int getP_InvoicePriceVariance_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_InvoicePriceVariance_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_Labor_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_Labor_Acct(), get_TrxName());	}

	/** Set Labor.
		@param P_Labor_Acct 
		The Labor account is the account used Manufacturing Order
	  */
	public void setP_Labor_Acct (int P_Labor_Acct)
	{
		set_Value (COLUMNNAME_P_Labor_Acct, Integer.valueOf(P_Labor_Acct));
	}

	/** Get Labor.
		@return The Labor account is the account used Manufacturing Order
	  */
	public int getP_Labor_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Labor_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_MethodChangeVariance_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_MethodChangeVariance_Acct(), get_TrxName());	}

	/** Set Method Change Variance.
		@param P_MethodChangeVariance_Acct 
		The Method Change Variance account is the account used Manufacturing Order
	  */
	public void setP_MethodChangeVariance_Acct (int P_MethodChangeVariance_Acct)
	{
		set_Value (COLUMNNAME_P_MethodChangeVariance_Acct, Integer.valueOf(P_MethodChangeVariance_Acct));
	}

	/** Get Method Change Variance.
		@return The Method Change Variance account is the account used Manufacturing Order
	  */
	public int getP_MethodChangeVariance_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_MethodChangeVariance_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_MixVariance_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_MixVariance_Acct(), get_TrxName());	}

	/** Set Mix Variance.
		@param P_MixVariance_Acct 
		The Mix Variance account is the account used Manufacturing Order
	  */
	public void setP_MixVariance_Acct (int P_MixVariance_Acct)
	{
		set_Value (COLUMNNAME_P_MixVariance_Acct, Integer.valueOf(P_MixVariance_Acct));
	}

	/** Get Mix Variance.
		@return The Mix Variance account is the account used Manufacturing Order
	  */
	public int getP_MixVariance_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_MixVariance_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_OutsideProcessing_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_OutsideProcessing_Acct(), get_TrxName());	}

	/** Set Outside Processing.
		@param P_OutsideProcessing_Acct 
		The Outside Processing Account is the account used in Manufacturing Order
	  */
	public void setP_OutsideProcessing_Acct (int P_OutsideProcessing_Acct)
	{
		set_Value (COLUMNNAME_P_OutsideProcessing_Acct, Integer.valueOf(P_OutsideProcessing_Acct));
	}

	/** Get Outside Processing.
		@return The Outside Processing Account is the account used in Manufacturing Order
	  */
	public int getP_OutsideProcessing_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_OutsideProcessing_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_Overhead_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_Overhead_Acct(), get_TrxName());	}

	/** Set Overhead.
		@param P_Overhead_Acct 
		The Overhead account is the account used  in Manufacturing Order 
	  */
	public void setP_Overhead_Acct (int P_Overhead_Acct)
	{
		set_Value (COLUMNNAME_P_Overhead_Acct, Integer.valueOf(P_Overhead_Acct));
	}

	/** Get Overhead.
		@return The Overhead account is the account used  in Manufacturing Order 
	  */
	public int getP_Overhead_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Overhead_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_PurchasePriceVariance_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_PurchasePriceVariance_Acct(), get_TrxName());	}

	/** Set Purchase Price Variance.
		@param P_PurchasePriceVariance_Acct 
		Difference between Standard Cost and Purchase Price (PPV)
	  */
	public void setP_PurchasePriceVariance_Acct (int P_PurchasePriceVariance_Acct)
	{
		set_Value (COLUMNNAME_P_PurchasePriceVariance_Acct, Integer.valueOf(P_PurchasePriceVariance_Acct));
	}

	/** Get Purchase Price Variance.
		@return Difference between Standard Cost and Purchase Price (PPV)
	  */
	public int getP_PurchasePriceVariance_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_PurchasePriceVariance_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_RateVariance_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_RateVariance_Acct(), get_TrxName());	}

	/** Set Rate Variance.
		@param P_RateVariance_Acct 
		The Rate Variance account is the account used Manufacturing Order
	  */
	public void setP_RateVariance_Acct (int P_RateVariance_Acct)
	{
		set_Value (COLUMNNAME_P_RateVariance_Acct, Integer.valueOf(P_RateVariance_Acct));
	}

	/** Get Rate Variance.
		@return The Rate Variance account is the account used Manufacturing Order
	  */
	public int getP_RateVariance_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_RateVariance_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_Revenue_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_Revenue_Acct(), get_TrxName());	}

	/** Set Product Revenue.
		@param P_Revenue_Acct 
		Account for Product Revenue (Sales Account)
	  */
	public void setP_Revenue_Acct (int P_Revenue_Acct)
	{
		set_Value (COLUMNNAME_P_Revenue_Acct, Integer.valueOf(P_Revenue_Acct));
	}

	/** Get Product Revenue.
		@return Account for Product Revenue (Sales Account)
	  */
	public int getP_Revenue_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Revenue_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_C_ValidCombination getP_Scrap_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_Scrap_Acct(), get_TrxName());	}

	/** Set Scrap.
		@param P_Scrap_Acct 
		The Scrap account is the account used  in Manufacturing Order 
	  */
	public void setP_Scrap_Acct (int P_Scrap_Acct)
	{
		set_Value (COLUMNNAME_P_Scrap_Acct, Integer.valueOf(P_Scrap_Acct));
	}

	/** Get Scrap.
		@return The Scrap account is the account used  in Manufacturing Order 
	  */
	public int getP_Scrap_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Scrap_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_TradeDiscountGrant_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_TradeDiscountGrant_Acct(), get_TrxName());	}

	/** Set Trade Discount Granted.
		@param P_TradeDiscountGrant_Acct 
		Trade Discount Granted Account
	  */
	public void setP_TradeDiscountGrant_Acct (int P_TradeDiscountGrant_Acct)
	{
		set_Value (COLUMNNAME_P_TradeDiscountGrant_Acct, Integer.valueOf(P_TradeDiscountGrant_Acct));
	}

	/** Get Trade Discount Granted.
		@return Trade Discount Granted Account
	  */
	public int getP_TradeDiscountGrant_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_TradeDiscountGrant_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_TradeDiscountRec_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_TradeDiscountRec_Acct(), get_TrxName());	}

	/** Set Trade Discount Received.
		@param P_TradeDiscountRec_Acct 
		Trade Discount Receivable Account
	  */
	public void setP_TradeDiscountRec_Acct (int P_TradeDiscountRec_Acct)
	{
		set_Value (COLUMNNAME_P_TradeDiscountRec_Acct, Integer.valueOf(P_TradeDiscountRec_Acct));
	}

	/** Get Trade Discount Received.
		@return Trade Discount Receivable Account
	  */
	public int getP_TradeDiscountRec_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_TradeDiscountRec_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_UsageVariance_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_UsageVariance_Acct(), get_TrxName());	}

	/** Set Usage Variance.
		@param P_UsageVariance_Acct 
		The Usage Variance account is the account used Manufacturing Order
	  */
	public void setP_UsageVariance_Acct (int P_UsageVariance_Acct)
	{
		set_Value (COLUMNNAME_P_UsageVariance_Acct, Integer.valueOf(P_UsageVariance_Acct));
	}

	/** Get Usage Variance.
		@return The Usage Variance account is the account used Manufacturing Order
	  */
	public int getP_UsageVariance_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_UsageVariance_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_WIP_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_WIP_Acct(), get_TrxName());	}

	/** Set Work In Process.
		@param P_WIP_Acct 
		The Work in Process account is the account used Manufacturing Order
	  */
	public void setP_WIP_Acct (int P_WIP_Acct)
	{
		set_Value (COLUMNNAME_P_WIP_Acct, Integer.valueOf(P_WIP_Acct));
	}

	/** Get Work In Process.
		@return The Work in Process account is the account used Manufacturing Order
	  */
	public int getP_WIP_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_WIP_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}