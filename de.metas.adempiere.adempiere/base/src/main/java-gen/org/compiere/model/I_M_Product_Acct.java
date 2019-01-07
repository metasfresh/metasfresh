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
package org.compiere.model;


/** Generated Interface for M_Product_Acct
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Product_Acct 
{

    /** TableName=M_Product_Acct */
    public static final String Table_Name = "M_Product_Acct";

    /** AD_Table_ID=273 */
    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org() throws RuntimeException;

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/** Set Buchführungs-Schema.
	  * Rules for accounting
	  */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/** Get Buchführungs-Schema.
	  * Rules for accounting
	  */
	public int getC_AcctSchema_ID();

//	public org.compiere.model.I_C_AcctSchema getC_AcctSchema() throws RuntimeException;
//
//	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/** Set Activity.
	  * Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID);

	/** Get Activity.
	  * Business Activity
	  */
	public int getC_Activity_ID();

	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException;

	public void setC_Activity(org.compiere.model.I_C_Activity C_Activity);

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt.
	  * Date this record was created
	  */
	public java.sql.Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Erstellt durch.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Aktiv.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Aktiv.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Produkt.
	  * Produkt, Leistung, Artikel
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Produkt.
	  * Produkt, Leistung, Artikel
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column name P_Asset_Acct */
    public static final String COLUMNNAME_P_Asset_Acct = "P_Asset_Acct";

	/** Set Produkt-Asset.
	  * Account for Product Asset (Inventory)
	  */
	public void setP_Asset_Acct (int P_Asset_Acct);

	/** Get Produkt-Asset.
	  * Account for Product Asset (Inventory)
	  */
	public int getP_Asset_Acct();

	public org.compiere.model.I_C_ValidCombination getP_Asset_A() throws RuntimeException;

	public void setP_Asset_A(org.compiere.model.I_C_ValidCombination P_Asset_A);

    /** Column name P_Burden_Acct */
    public static final String COLUMNNAME_P_Burden_Acct = "P_Burden_Acct";

	/** Set Burden.
	  * The Burden account is the account used Manufacturing Order
	  */
	public void setP_Burden_Acct (int P_Burden_Acct);

	/** Get Burden.
	  * The Burden account is the account used Manufacturing Order
	  */
	public int getP_Burden_Acct();

	public org.compiere.model.I_C_ValidCombination getP_Burden_A() throws RuntimeException;

	public void setP_Burden_A(org.compiere.model.I_C_ValidCombination P_Burden_A);

    /** Column name P_COGS_Acct */
    public static final String COLUMNNAME_P_COGS_Acct = "P_COGS_Acct";

	/** Set Product COGS.
	  * Account for Cost of Goods Sold
	  */
	public void setP_COGS_Acct (int P_COGS_Acct);

	/** Get Product COGS.
	  * Account for Cost of Goods Sold
	  */
	public int getP_COGS_Acct();

	public org.compiere.model.I_C_ValidCombination getP_COGS_A() throws RuntimeException;

	public void setP_COGS_A(org.compiere.model.I_C_ValidCombination P_COGS_A);

    /** Column name P_CostAdjustment_Acct */
    public static final String COLUMNNAME_P_CostAdjustment_Acct = "P_CostAdjustment_Acct";

	/** Set Cost Adjustment.
	  * Product Cost Adjustment Account
	  */
	public void setP_CostAdjustment_Acct (int P_CostAdjustment_Acct);

	/** Get Cost Adjustment.
	  * Product Cost Adjustment Account
	  */
	public int getP_CostAdjustment_Acct();

	public org.compiere.model.I_C_ValidCombination getP_CostAdjustment_A() throws RuntimeException;

	public void setP_CostAdjustment_A(org.compiere.model.I_C_ValidCombination P_CostAdjustment_A);

    /** Column name P_CostOfProduction_Acct */
    public static final String COLUMNNAME_P_CostOfProduction_Acct = "P_CostOfProduction_Acct";

	/** Set Cost Of Production.
	  * The Cost Of Production account is the account used Manufacturing Order
	  */
	public void setP_CostOfProduction_Acct (int P_CostOfProduction_Acct);

	/** Get Cost Of Production.
	  * The Cost Of Production account is the account used Manufacturing Order
	  */
	public int getP_CostOfProduction_Acct();

	public org.compiere.model.I_C_ValidCombination getP_CostOfProduction_A() throws RuntimeException;

	public void setP_CostOfProduction_A(org.compiere.model.I_C_ValidCombination P_CostOfProduction_A);

    /** Column name P_Expense_Acct */
    public static final String COLUMNNAME_P_Expense_Acct = "P_Expense_Acct";

	/** Set Product Expense.
	  * Account for Product Expense
	  */
	public void setP_Expense_Acct (int P_Expense_Acct);

	/** Get Product Expense.
	  * Account for Product Expense
	  */
	public int getP_Expense_Acct();

	public org.compiere.model.I_C_ValidCombination getP_Expense_A() throws RuntimeException;

	public void setP_Expense_A(org.compiere.model.I_C_ValidCombination P_Expense_A);

    /** Column name P_FloorStock_Acct */
    public static final String COLUMNNAME_P_FloorStock_Acct = "P_FloorStock_Acct";

	/** Set Floor Stock.
	  * The Floor Stock account is the account used Manufacturing Order
	  */
	public void setP_FloorStock_Acct (int P_FloorStock_Acct);

	/** Get Floor Stock.
	  * The Floor Stock account is the account used Manufacturing Order
	  */
	public int getP_FloorStock_Acct();

	public org.compiere.model.I_C_ValidCombination getP_FloorStock_A() throws RuntimeException;

	public void setP_FloorStock_A(org.compiere.model.I_C_ValidCombination P_FloorStock_A);

    /** Column name P_InventoryClearing_Acct */
    public static final String COLUMNNAME_P_InventoryClearing_Acct = "P_InventoryClearing_Acct";

	/** Set Inventory Clearing.
	  * Product Inventory Clearing Account
	  */
	public void setP_InventoryClearing_Acct (int P_InventoryClearing_Acct);

	/** Get Inventory Clearing.
	  * Product Inventory Clearing Account
	  */
	public int getP_InventoryClearing_Acct();

	public org.compiere.model.I_C_ValidCombination getP_InventoryClearing_A() throws RuntimeException;

	public void setP_InventoryClearing_A(org.compiere.model.I_C_ValidCombination P_InventoryClearing_A);

    /** Column name P_InvoicePriceVariance_Acct */
    public static final String COLUMNNAME_P_InvoicePriceVariance_Acct = "P_InvoicePriceVariance_Acct";

	/** Set Invoice Price Variance.
	  * Difference between Costs and Invoice Price (IPV)
	  */
	public void setP_InvoicePriceVariance_Acct (int P_InvoicePriceVariance_Acct);

	/** Get Invoice Price Variance.
	  * Difference between Costs and Invoice Price (IPV)
	  */
	public int getP_InvoicePriceVariance_Acct();

	public org.compiere.model.I_C_ValidCombination getP_InvoicePriceVariance_A() throws RuntimeException;

	public void setP_InvoicePriceVariance_A(org.compiere.model.I_C_ValidCombination P_InvoicePriceVariance_A);

    /** Column name P_Labor_Acct */
    public static final String COLUMNNAME_P_Labor_Acct = "P_Labor_Acct";

	/** Set Labor.
	  * The Labor account is the account used Manufacturing Order
	  */
	public void setP_Labor_Acct (int P_Labor_Acct);

	/** Get Labor.
	  * The Labor account is the account used Manufacturing Order
	  */
	public int getP_Labor_Acct();

	public org.compiere.model.I_C_ValidCombination getP_Labor_A() throws RuntimeException;

	public void setP_Labor_A(org.compiere.model.I_C_ValidCombination P_Labor_A);

    /** Column name P_MethodChangeVariance_Acct */
    public static final String COLUMNNAME_P_MethodChangeVariance_Acct = "P_MethodChangeVariance_Acct";

	/** Set Method Change Variance.
	  * The Method Change Variance account is the account used Manufacturing Order
	  */
	public void setP_MethodChangeVariance_Acct (int P_MethodChangeVariance_Acct);

	/** Get Method Change Variance.
	  * The Method Change Variance account is the account used Manufacturing Order
	  */
	public int getP_MethodChangeVariance_Acct();

	public org.compiere.model.I_C_ValidCombination getP_MethodChangeVariance_A() throws RuntimeException;

	public void setP_MethodChangeVariance_A(org.compiere.model.I_C_ValidCombination P_MethodChangeVariance_A);

    /** Column name P_MixVariance_Acct */
    public static final String COLUMNNAME_P_MixVariance_Acct = "P_MixVariance_Acct";

	/** Set Mix Variance.
	  * The Mix Variance account is the account used Manufacturing Order
	  */
	public void setP_MixVariance_Acct (int P_MixVariance_Acct);

	/** Get Mix Variance.
	  * The Mix Variance account is the account used Manufacturing Order
	  */
	public int getP_MixVariance_Acct();

	public org.compiere.model.I_C_ValidCombination getP_MixVariance_A() throws RuntimeException;

	public void setP_MixVariance_A(org.compiere.model.I_C_ValidCombination P_MixVariance_A);

    /** Column name P_OutsideProcessing_Acct */
    public static final String COLUMNNAME_P_OutsideProcessing_Acct = "P_OutsideProcessing_Acct";

	/** Set Outside Processing.
	  * The Outside Processing Account is the account used in Manufacturing Order
	  */
	public void setP_OutsideProcessing_Acct (int P_OutsideProcessing_Acct);

	/** Get Outside Processing.
	  * The Outside Processing Account is the account used in Manufacturing Order
	  */
	public int getP_OutsideProcessing_Acct();

	public org.compiere.model.I_C_ValidCombination getP_OutsideProcessing_A() throws RuntimeException;

	public void setP_OutsideProcessing_A(org.compiere.model.I_C_ValidCombination P_OutsideProcessing_A);

    /** Column name P_Overhead_Acct */
    public static final String COLUMNNAME_P_Overhead_Acct = "P_Overhead_Acct";

	/** Set Overhead.
	  * The Overhead account is the account used  in Manufacturing Order 
	  */
	public void setP_Overhead_Acct (int P_Overhead_Acct);

	/** Get Overhead.
	  * The Overhead account is the account used  in Manufacturing Order 
	  */
	public int getP_Overhead_Acct();

	public org.compiere.model.I_C_ValidCombination getP_Overhead_A() throws RuntimeException;

	public void setP_Overhead_A(org.compiere.model.I_C_ValidCombination P_Overhead_A);

    /** Column name P_PurchasePriceVariance_Acct */
    public static final String COLUMNNAME_P_PurchasePriceVariance_Acct = "P_PurchasePriceVariance_Acct";

	/** Set Purchase Price Variance.
	  * Difference between Standard Cost and Purchase Price (PPV)
	  */
	public void setP_PurchasePriceVariance_Acct (int P_PurchasePriceVariance_Acct);

	/** Get Purchase Price Variance.
	  * Difference between Standard Cost and Purchase Price (PPV)
	  */
	public int getP_PurchasePriceVariance_Acct();

	public org.compiere.model.I_C_ValidCombination getP_PurchasePriceVariance_A() throws RuntimeException;

	public void setP_PurchasePriceVariance_A(org.compiere.model.I_C_ValidCombination P_PurchasePriceVariance_A);

    /** Column name P_RateVariance_Acct */
    public static final String COLUMNNAME_P_RateVariance_Acct = "P_RateVariance_Acct";

	/** Set Rate Variance.
	  * The Rate Variance account is the account used Manufacturing Order
	  */
	public void setP_RateVariance_Acct (int P_RateVariance_Acct);

	/** Get Rate Variance.
	  * The Rate Variance account is the account used Manufacturing Order
	  */
	public int getP_RateVariance_Acct();

	public org.compiere.model.I_C_ValidCombination getP_RateVariance_A() throws RuntimeException;

	public void setP_RateVariance_A(org.compiere.model.I_C_ValidCombination P_RateVariance_A);

    /** Column name P_Revenue_Acct */
    public static final String COLUMNNAME_P_Revenue_Acct = "P_Revenue_Acct";

	/** Set Product Revenue.
	  * Account for Product Revenue (Sales Account)
	  */
	public void setP_Revenue_Acct (int P_Revenue_Acct);

	/** Get Product Revenue.
	  * Account for Product Revenue (Sales Account)
	  */
	public int getP_Revenue_Acct();

	public org.compiere.model.I_C_ValidCombination getP_Revenue_A() throws RuntimeException;

	public void setP_Revenue_A(org.compiere.model.I_C_ValidCombination P_Revenue_A);

    /** Column name P_Scrap_Acct */
    public static final String COLUMNNAME_P_Scrap_Acct = "P_Scrap_Acct";

	/** Set Scrap.
	  * The Scrap account is the account used  in Manufacturing Order 
	  */
	public void setP_Scrap_Acct (int P_Scrap_Acct);

	/** Get Scrap.
	  * The Scrap account is the account used  in Manufacturing Order 
	  */
	public int getP_Scrap_Acct();

	public org.compiere.model.I_C_ValidCombination getP_Scrap_A() throws RuntimeException;

	public void setP_Scrap_A(org.compiere.model.I_C_ValidCombination P_Scrap_A);

    /** Column name P_TradeDiscountGrant_Acct */
    public static final String COLUMNNAME_P_TradeDiscountGrant_Acct = "P_TradeDiscountGrant_Acct";

	/** Set Trade Discount Granted.
	  * Trade Discount Granted Account
	  */
	public void setP_TradeDiscountGrant_Acct (int P_TradeDiscountGrant_Acct);

	/** Get Trade Discount Granted.
	  * Trade Discount Granted Account
	  */
	public int getP_TradeDiscountGrant_Acct();

	public org.compiere.model.I_C_ValidCombination getP_TradeDiscountGrant_A() throws RuntimeException;

	public void setP_TradeDiscountGrant_A(org.compiere.model.I_C_ValidCombination P_TradeDiscountGrant_A);

    /** Column name P_TradeDiscountRec_Acct */
    public static final String COLUMNNAME_P_TradeDiscountRec_Acct = "P_TradeDiscountRec_Acct";

	/** Set Trade Discount Received.
	  * Trade Discount Receivable Account
	  */
	public void setP_TradeDiscountRec_Acct (int P_TradeDiscountRec_Acct);

	/** Get Trade Discount Received.
	  * Trade Discount Receivable Account
	  */
	public int getP_TradeDiscountRec_Acct();

	public org.compiere.model.I_C_ValidCombination getP_TradeDiscountRec_A() throws RuntimeException;

	public void setP_TradeDiscountRec_A(org.compiere.model.I_C_ValidCombination P_TradeDiscountRec_A);

    /** Column name P_UsageVariance_Acct */
    public static final String COLUMNNAME_P_UsageVariance_Acct = "P_UsageVariance_Acct";

	/** Set Usage Variance.
	  * The Usage Variance account is the account used Manufacturing Order
	  */
	public void setP_UsageVariance_Acct (int P_UsageVariance_Acct);

	/** Get Usage Variance.
	  * The Usage Variance account is the account used Manufacturing Order
	  */
	public int getP_UsageVariance_Acct();

	public org.compiere.model.I_C_ValidCombination getP_UsageVariance_A() throws RuntimeException;

	public void setP_UsageVariance_A(org.compiere.model.I_C_ValidCombination P_UsageVariance_A);

    /** Column name P_WIP_Acct */
    public static final String COLUMNNAME_P_WIP_Acct = "P_WIP_Acct";

	/** Set Work In Process.
	  * The Work in Process account is the account used Manufacturing Order
	  */
	public void setP_WIP_Acct (int P_WIP_Acct);

	/** Get Work In Process.
	  * The Work in Process account is the account used Manufacturing Order
	  */
	public int getP_WIP_Acct();

	public org.compiere.model.I_C_ValidCombination getP_WIP_A() throws RuntimeException;

	public void setP_WIP_A(org.compiere.model.I_C_ValidCombination P_WIP_A);

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert.
	  * Date this record was updated
	  */
	public java.sql.Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
