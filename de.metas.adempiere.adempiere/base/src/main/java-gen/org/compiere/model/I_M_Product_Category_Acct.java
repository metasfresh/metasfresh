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

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for M_Product_Category_Acct
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_M_Product_Category_Acct 
{

    /** TableName=M_Product_Category_Acct */
    public static final String Table_Name = "M_Product_Category_Acct";

    /** AD_Table_ID=401 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/** Set Accounting Schema.
	  * Rules for accounting
	  */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/** Get Accounting Schema.
	  * Rules for accounting
	  */
	public int getC_AcctSchema_ID();

	public I_C_AcctSchema getC_AcctSchema() throws RuntimeException;

    /** Column name CostingLevel */
    public static final String COLUMNNAME_CostingLevel = "CostingLevel";

	/** Set Costing Level.
	  * The lowest level to accumulate Costing Information
	  */
	public void setCostingLevel (String CostingLevel);

	/** Get Costing Level.
	  * The lowest level to accumulate Costing Information
	  */
	public String getCostingLevel();

    /** Column name CostingMethod */
    public static final String COLUMNNAME_CostingMethod = "CostingMethod";

	/** Set Costing Method.
	  * Indicates how Costs will be calculated
	  */
	public void setCostingMethod (String CostingMethod);

	/** Get Costing Method.
	  * Indicates how Costs will be calculated
	  */
	public String getCostingMethod();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name M_Product_Category_ID */
    public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/** Set Product Category.
	  * Category of a Product
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/** Get Product Category.
	  * Category of a Product
	  */
	public int getM_Product_Category_ID();

	public I_M_Product_Category getM_Product_Category() throws RuntimeException;

    /** Column name P_Asset_Acct */
    public static final String COLUMNNAME_P_Asset_Acct = "P_Asset_Acct";

	/** Set Product Asset.
	  * Account for Product Asset (Inventory)
	  */
	public void setP_Asset_Acct (int P_Asset_Acct);

	/** Get Product Asset.
	  * Account for Product Asset (Inventory)
	  */
	public int getP_Asset_Acct();

	public I_C_ValidCombination getP_Asset_A() throws RuntimeException;

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

	public I_C_ValidCombination getP_Burden_A() throws RuntimeException;

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

	public I_C_ValidCombination getP_COGS_A() throws RuntimeException;

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

	public I_C_ValidCombination getP_CostAdjustment_A() throws RuntimeException;

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

	public I_C_ValidCombination getP_CostOfProduction_A() throws RuntimeException;

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

	public I_C_ValidCombination getP_Expense_A() throws RuntimeException;

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

	public I_C_ValidCombination getP_FloorStock_A() throws RuntimeException;

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

	public I_C_ValidCombination getP_InventoryClearing_A() throws RuntimeException;

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

	public I_C_ValidCombination getP_InvoicePriceVariance_A() throws RuntimeException;

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

	public I_C_ValidCombination getP_Labor_A() throws RuntimeException;

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

	public I_C_ValidCombination getP_MethodChangeVariance_A() throws RuntimeException;

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

	public I_C_ValidCombination getP_MixVariance_A() throws RuntimeException;

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

	public I_C_ValidCombination getP_OutsideProcessing_A() throws RuntimeException;

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

	public I_C_ValidCombination getP_Overhead_A() throws RuntimeException;

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

	public I_C_ValidCombination getP_PurchasePriceVariance_A() throws RuntimeException;

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

	public I_C_ValidCombination getP_RateVariance_A() throws RuntimeException;

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

	public I_C_ValidCombination getP_Revenue_A() throws RuntimeException;

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

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

	public I_C_ValidCombination getP_Scrap_A() throws RuntimeException;

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

	public I_C_ValidCombination getP_TradeDiscountGrant_A() throws RuntimeException;

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

	public I_C_ValidCombination getP_TradeDiscountRec_A() throws RuntimeException;

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

	public I_C_ValidCombination getP_UsageVariance_A() throws RuntimeException;

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

	public I_C_ValidCombination getP_WIP_A() throws RuntimeException;

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
