package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Product_Category_Acct
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Product_Category_Acct 
{

	String Table_Name = "M_Product_Category_Acct";

//	/** AD_Table_ID=401 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_AcctSchema_ID();

	org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new ModelColumn<>(I_M_Product_Category_Acct.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
	String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Costing Level.
	 * The lowest level to accumulate Costing Information
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCostingLevel (@Nullable java.lang.String CostingLevel);

	/**
	 * Get Costing Level.
	 * The lowest level to accumulate Costing Information
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCostingLevel();

	ModelColumn<I_M_Product_Category_Acct, Object> COLUMN_CostingLevel = new ModelColumn<>(I_M_Product_Category_Acct.class, "CostingLevel", null);
	String COLUMNNAME_CostingLevel = "CostingLevel";

	/**
	 * Set Costing Method.
	 * Indicates how Costs will be calculated
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCostingMethod (@Nullable java.lang.String CostingMethod);

	/**
	 * Get Costing Method.
	 * Indicates how Costs will be calculated
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCostingMethod();

	ModelColumn<I_M_Product_Category_Acct, Object> COLUMN_CostingMethod = new ModelColumn<>(I_M_Product_Category_Acct.class, "CostingMethod", null);
	String COLUMNNAME_CostingMethod = "CostingMethod";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Product_Category_Acct, Object> COLUMN_Created = new ModelColumn<>(I_M_Product_Category_Acct.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_M_Product_Category_Acct, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Product_Category_Acct.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Product Category Acct.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_Category_Acct_ID (int M_Product_Category_Acct_ID);

	/**
	 * Get Product Category Acct.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_Category_Acct_ID();

	ModelColumn<I_M_Product_Category_Acct, Object> COLUMN_M_Product_Category_Acct_ID = new ModelColumn<>(I_M_Product_Category_Acct.class, "M_Product_Category_Acct_ID", null);
	String COLUMNNAME_M_Product_Category_Acct_ID = "M_Product_Category_Acct_ID";

	/**
	 * Set Product Category.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Product Category.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_Category_ID();

	String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/**
	 * Set Product Asset.
	 * Account for Product Asset (Inventory)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_Asset_Acct (int P_Asset_Acct);

	/**
	 * Get Product Asset.
	 * Account for Product Asset (Inventory)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_Asset_Acct();

	org.compiere.model.I_C_ValidCombination getP_Asset_A();

	void setP_Asset_A(org.compiere.model.I_C_ValidCombination P_Asset_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_Asset_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_Asset_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_Asset_Acct = "P_Asset_Acct";

	/**
	 * Set Burden.
	 * The Burden account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_Burden_Acct (int P_Burden_Acct);

	/**
	 * Get Burden.
	 * The Burden account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_Burden_Acct();

	org.compiere.model.I_C_ValidCombination getP_Burden_A();

	void setP_Burden_A(org.compiere.model.I_C_ValidCombination P_Burden_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_Burden_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_Burden_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_Burden_Acct = "P_Burden_Acct";

	/**
	 * Set Product COGS.
	 * Account for Cost of Goods Sold
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_COGS_Acct (int P_COGS_Acct);

	/**
	 * Get Product COGS.
	 * Account for Cost of Goods Sold
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_COGS_Acct();

	org.compiere.model.I_C_ValidCombination getP_COGS_A();

	void setP_COGS_A(org.compiere.model.I_C_ValidCombination P_COGS_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_COGS_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_COGS_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_COGS_Acct = "P_COGS_Acct";

	/**
	 * Set Cost Adjustment.
	 * Product Cost Adjustment Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_CostAdjustment_Acct (int P_CostAdjustment_Acct);

	/**
	 * Get Cost Adjustment.
	 * Product Cost Adjustment Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_CostAdjustment_Acct();

	org.compiere.model.I_C_ValidCombination getP_CostAdjustment_A();

	void setP_CostAdjustment_A(org.compiere.model.I_C_ValidCombination P_CostAdjustment_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_CostAdjustment_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_CostAdjustment_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_CostAdjustment_Acct = "P_CostAdjustment_Acct";

	/**
	 * Set Cost Of Production.
	 * The Cost Of Production account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_CostOfProduction_Acct (int P_CostOfProduction_Acct);

	/**
	 * Get Cost Of Production.
	 * The Cost Of Production account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_CostOfProduction_Acct();

	org.compiere.model.I_C_ValidCombination getP_CostOfProduction_A();

	void setP_CostOfProduction_A(org.compiere.model.I_C_ValidCombination P_CostOfProduction_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_CostOfProduction_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_CostOfProduction_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_CostOfProduction_Acct = "P_CostOfProduction_Acct";

	/**
	 * Set Product Expense.
	 * Account for Product Expense
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_Expense_Acct (int P_Expense_Acct);

	/**
	 * Get Product Expense.
	 * Account for Product Expense
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_Expense_Acct();

	org.compiere.model.I_C_ValidCombination getP_Expense_A();

	void setP_Expense_A(org.compiere.model.I_C_ValidCombination P_Expense_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_Expense_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_Expense_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_Expense_Acct = "P_Expense_Acct";

	/**
	 * Set Externally Owned Stock.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_ExternallyOwnedStock_Acct (int P_ExternallyOwnedStock_Acct);

	/**
	 * Get Externally Owned Stock.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_ExternallyOwnedStock_Acct();

	org.compiere.model.I_C_ValidCombination getP_ExternallyOwnedStock_A();

	void setP_ExternallyOwnedStock_A(org.compiere.model.I_C_ValidCombination P_ExternallyOwnedStock_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_ExternallyOwnedStock_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_ExternallyOwnedStock_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_ExternallyOwnedStock_Acct = "P_ExternallyOwnedStock_Acct";

	/**
	 * Set Floor Stock.
	 * The Floor Stock account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_FloorStock_Acct (int P_FloorStock_Acct);

	/**
	 * Get Floor Stock.
	 * The Floor Stock account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_FloorStock_Acct();

	org.compiere.model.I_C_ValidCombination getP_FloorStock_A();

	void setP_FloorStock_A(org.compiere.model.I_C_ValidCombination P_FloorStock_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_FloorStock_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_FloorStock_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_FloorStock_Acct = "P_FloorStock_Acct";

	/**
	 * Set Inventory Clearing.
	 * Product Inventory Clearing Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_InventoryClearing_Acct (int P_InventoryClearing_Acct);

	/**
	 * Get Inventory Clearing.
	 * Product Inventory Clearing Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_InventoryClearing_Acct();

	org.compiere.model.I_C_ValidCombination getP_InventoryClearing_A();

	void setP_InventoryClearing_A(org.compiere.model.I_C_ValidCombination P_InventoryClearing_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_InventoryClearing_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_InventoryClearing_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_InventoryClearing_Acct = "P_InventoryClearing_Acct";

	/**
	 * Set Invoice Price Variance.
	 * Difference between Costs and Invoice Price (IPV)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_InvoicePriceVariance_Acct (int P_InvoicePriceVariance_Acct);

	/**
	 * Get Invoice Price Variance.
	 * Difference between Costs and Invoice Price (IPV)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_InvoicePriceVariance_Acct();

	org.compiere.model.I_C_ValidCombination getP_InvoicePriceVariance_A();

	void setP_InvoicePriceVariance_A(org.compiere.model.I_C_ValidCombination P_InvoicePriceVariance_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_InvoicePriceVariance_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_InvoicePriceVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_InvoicePriceVariance_Acct = "P_InvoicePriceVariance_Acct";

	/**
	 * Set Labor.
	 * The Labor account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_Labor_Acct (int P_Labor_Acct);

	/**
	 * Get Labor.
	 * The Labor account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_Labor_Acct();

	org.compiere.model.I_C_ValidCombination getP_Labor_A();

	void setP_Labor_A(org.compiere.model.I_C_ValidCombination P_Labor_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_Labor_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_Labor_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_Labor_Acct = "P_Labor_Acct";

	/**
	 * Set Method Change Variance.
	 * The Method Change Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_MethodChangeVariance_Acct (int P_MethodChangeVariance_Acct);

	/**
	 * Get Method Change Variance.
	 * The Method Change Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_MethodChangeVariance_Acct();

	org.compiere.model.I_C_ValidCombination getP_MethodChangeVariance_A();

	void setP_MethodChangeVariance_A(org.compiere.model.I_C_ValidCombination P_MethodChangeVariance_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_MethodChangeVariance_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_MethodChangeVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_MethodChangeVariance_Acct = "P_MethodChangeVariance_Acct";

	/**
	 * Set Mix Variance.
	 * The Mix Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_MixVariance_Acct (int P_MixVariance_Acct);

	/**
	 * Get Mix Variance.
	 * The Mix Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_MixVariance_Acct();

	org.compiere.model.I_C_ValidCombination getP_MixVariance_A();

	void setP_MixVariance_A(org.compiere.model.I_C_ValidCombination P_MixVariance_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_MixVariance_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_MixVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_MixVariance_Acct = "P_MixVariance_Acct";

	/**
	 * Set Outside Processing.
	 * The Outside Processing Account is the account used in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_OutsideProcessing_Acct (int P_OutsideProcessing_Acct);

	/**
	 * Get Outside Processing.
	 * The Outside Processing Account is the account used in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_OutsideProcessing_Acct();

	org.compiere.model.I_C_ValidCombination getP_OutsideProcessing_A();

	void setP_OutsideProcessing_A(org.compiere.model.I_C_ValidCombination P_OutsideProcessing_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_OutsideProcessing_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_OutsideProcessing_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_OutsideProcessing_Acct = "P_OutsideProcessing_Acct";

	/**
	 * Set Overhead.
	 * The Overhead account is the account used  in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_Overhead_Acct (int P_Overhead_Acct);

	/**
	 * Get Overhead.
	 * The Overhead account is the account used  in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_Overhead_Acct();

	org.compiere.model.I_C_ValidCombination getP_Overhead_A();

	void setP_Overhead_A(org.compiere.model.I_C_ValidCombination P_Overhead_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_Overhead_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_Overhead_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_Overhead_Acct = "P_Overhead_Acct";

	/**
	 * Set Purchase Price Variance.
	 * Difference between Standard Cost and Purchase Price (PPV)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_PurchasePriceVariance_Acct (int P_PurchasePriceVariance_Acct);

	/**
	 * Get Purchase Price Variance.
	 * Difference between Standard Cost and Purchase Price (PPV)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_PurchasePriceVariance_Acct();

	org.compiere.model.I_C_ValidCombination getP_PurchasePriceVariance_A();

	void setP_PurchasePriceVariance_A(org.compiere.model.I_C_ValidCombination P_PurchasePriceVariance_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_PurchasePriceVariance_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_PurchasePriceVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_PurchasePriceVariance_Acct = "P_PurchasePriceVariance_Acct";

	/**
	 * Set Rate Variance.
	 * The Rate Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_RateVariance_Acct (int P_RateVariance_Acct);

	/**
	 * Get Rate Variance.
	 * The Rate Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_RateVariance_Acct();

	org.compiere.model.I_C_ValidCombination getP_RateVariance_A();

	void setP_RateVariance_A(org.compiere.model.I_C_ValidCombination P_RateVariance_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_RateVariance_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_RateVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_RateVariance_Acct = "P_RateVariance_Acct";

	/**
	 * Set Product Revenue.
	 * Account for Product Revenue (Sales Account)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_Revenue_Acct (int P_Revenue_Acct);

	/**
	 * Get Product Revenue.
	 * Account for Product Revenue (Sales Account)
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_Revenue_Acct();

	org.compiere.model.I_C_ValidCombination getP_Revenue_A();

	void setP_Revenue_A(org.compiere.model.I_C_ValidCombination P_Revenue_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_Revenue_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_Revenue_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_Revenue_Acct = "P_Revenue_Acct";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_M_Product_Category_Acct, Object> COLUMN_Processing = new ModelColumn<>(I_M_Product_Category_Acct.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Scrap.
	 * The Scrap account is the account used  in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_Scrap_Acct (int P_Scrap_Acct);

	/**
	 * Get Scrap.
	 * The Scrap account is the account used  in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_Scrap_Acct();

	org.compiere.model.I_C_ValidCombination getP_Scrap_A();

	void setP_Scrap_A(org.compiere.model.I_C_ValidCombination P_Scrap_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_Scrap_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_Scrap_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_Scrap_Acct = "P_Scrap_Acct";

	/**
	 * Set Trade Discount Granted.
	 * Trade Discount Granted Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_TradeDiscountGrant_Acct (int P_TradeDiscountGrant_Acct);

	/**
	 * Get Trade Discount Granted.
	 * Trade Discount Granted Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_TradeDiscountGrant_Acct();

	org.compiere.model.I_C_ValidCombination getP_TradeDiscountGrant_A();

	void setP_TradeDiscountGrant_A(org.compiere.model.I_C_ValidCombination P_TradeDiscountGrant_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_TradeDiscountGrant_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_TradeDiscountGrant_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_TradeDiscountGrant_Acct = "P_TradeDiscountGrant_Acct";

	/**
	 * Set Trade Discount Received.
	 * Trade Discount Receivable Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_TradeDiscountRec_Acct (int P_TradeDiscountRec_Acct);

	/**
	 * Get Trade Discount Received.
	 * Trade Discount Receivable Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_TradeDiscountRec_Acct();

	org.compiere.model.I_C_ValidCombination getP_TradeDiscountRec_A();

	void setP_TradeDiscountRec_A(org.compiere.model.I_C_ValidCombination P_TradeDiscountRec_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_TradeDiscountRec_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_TradeDiscountRec_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_TradeDiscountRec_Acct = "P_TradeDiscountRec_Acct";

	/**
	 * Set Usage Variance.
	 * The Usage Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_UsageVariance_Acct (int P_UsageVariance_Acct);

	/**
	 * Get Usage Variance.
	 * The Usage Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_UsageVariance_Acct();

	org.compiere.model.I_C_ValidCombination getP_UsageVariance_A();

	void setP_UsageVariance_A(org.compiere.model.I_C_ValidCombination P_UsageVariance_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_UsageVariance_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_UsageVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_UsageVariance_Acct = "P_UsageVariance_Acct";

	/**
	 * Set Work In Process.
	 * The Work in Process account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setP_WIP_Acct (int P_WIP_Acct);

	/**
	 * Get Work In Process.
	 * The Work in Process account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getP_WIP_Acct();

	org.compiere.model.I_C_ValidCombination getP_WIP_A();

	void setP_WIP_A(org.compiere.model.I_C_ValidCombination P_WIP_A);

	ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_WIP_Acct = new ModelColumn<>(I_M_Product_Category_Acct.class, "P_WIP_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_P_WIP_Acct = "P_WIP_Acct";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Product_Category_Acct, Object> COLUMN_Updated = new ModelColumn<>(I_M_Product_Category_Acct.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
