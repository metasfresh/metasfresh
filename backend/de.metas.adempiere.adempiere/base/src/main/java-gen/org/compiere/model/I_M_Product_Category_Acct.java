package org.compiere.model;


/** Generated Interface for M_Product_Category_Acct
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Product_Category_Acct 
{

    /** TableName=M_Product_Category_Acct */
    public static final String Table_Name = "M_Product_Category_Acct";

    /** AD_Table_ID=401 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_AD_Client>(I_M_Product_Category_Acct.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_AD_Org>(I_M_Product_Category_Acct.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Buchführungs-Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Buchführungs-Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_AcctSchema_ID();

	public org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

    /** Column definition for C_AcctSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_AcctSchema>(I_M_Product_Category_Acct.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Kostenrechnungsstufe.
	 * The lowest level to accumulate Costing Information
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCostingLevel (java.lang.String CostingLevel);

	/**
	 * Get Kostenrechnungsstufe.
	 * The lowest level to accumulate Costing Information
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCostingLevel();

    /** Column definition for CostingLevel */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, Object> COLUMN_CostingLevel = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, Object>(I_M_Product_Category_Acct.class, "CostingLevel", null);
    /** Column name CostingLevel */
    public static final String COLUMNNAME_CostingLevel = "CostingLevel";

	/**
	 * Set Kostenrechnungsmethode.
	 * Indicates how Costs will be calculated
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCostingMethod (java.lang.String CostingMethod);

	/**
	 * Get Kostenrechnungsmethode.
	 * Indicates how Costs will be calculated
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCostingMethod();

    /** Column definition for CostingMethod */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, Object> COLUMN_CostingMethod = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, Object>(I_M_Product_Category_Acct.class, "CostingMethod", null);
    /** Column name CostingMethod */
    public static final String COLUMNNAME_CostingMethod = "CostingMethod";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, Object>(I_M_Product_Category_Acct.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_AD_User>(I_M_Product_Category_Acct.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, Object>(I_M_Product_Category_Acct.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Product Category Acct.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_Category_Acct_ID (int M_Product_Category_Acct_ID);

	/**
	 * Get Product Category Acct.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_Category_Acct_ID();

    /** Column definition for M_Product_Category_Acct_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, Object> COLUMN_M_Product_Category_Acct_ID = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, Object>(I_M_Product_Category_Acct.class, "M_Product_Category_Acct_ID", null);
    /** Column name M_Product_Category_Acct_ID */
    public static final String COLUMNNAME_M_Product_Category_Acct_ID = "M_Product_Category_Acct_ID";

	/**
	 * Set Produkt-Kategorie.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Produkt-Kategorie.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_Category_ID();

	public org.compiere.model.I_M_Product_Category getM_Product_Category();

	public void setM_Product_Category(org.compiere.model.I_M_Product_Category M_Product_Category);

    /** Column definition for M_Product_Category_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_M_Product_Category> COLUMN_M_Product_Category_ID = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_M_Product_Category>(I_M_Product_Category_Acct.class, "M_Product_Category_ID", org.compiere.model.I_M_Product_Category.class);
    /** Column name M_Product_Category_ID */
    public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/**
	 * Set Warenbestand.
	 * Konto für Warenbestand
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_Asset_Acct (int P_Asset_Acct);

	/**
	 * Get Warenbestand.
	 * Konto für Warenbestand
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_Asset_Acct();

	public org.compiere.model.I_C_ValidCombination getP_Asset_A();

	public void setP_Asset_A(org.compiere.model.I_C_ValidCombination P_Asset_A);

    /** Column definition for P_Asset_Acct */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_Asset_Acct = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination>(I_M_Product_Category_Acct.class, "P_Asset_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_Asset_Acct */
    public static final String COLUMNNAME_P_Asset_Acct = "P_Asset_Acct";

	/**
	 * Set Burden.
	 * The Burden account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_Burden_Acct (int P_Burden_Acct);

	/**
	 * Get Burden.
	 * The Burden account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_Burden_Acct();

	public org.compiere.model.I_C_ValidCombination getP_Burden_A();

	public void setP_Burden_A(org.compiere.model.I_C_ValidCombination P_Burden_A);

    /** Column definition for P_Burden_Acct */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_Burden_Acct = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination>(I_M_Product_Category_Acct.class, "P_Burden_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_Burden_Acct */
    public static final String COLUMNNAME_P_Burden_Acct = "P_Burden_Acct";

	/**
	 * Set Produkt Vertriebsausgaben.
	 * Konto für Produkt Vertriebsausgaben
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_COGS_Acct (int P_COGS_Acct);

	/**
	 * Get Produkt Vertriebsausgaben.
	 * Konto für Produkt Vertriebsausgaben
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_COGS_Acct();

	public org.compiere.model.I_C_ValidCombination getP_COGS_A();

	public void setP_COGS_A(org.compiere.model.I_C_ValidCombination P_COGS_A);

    /** Column definition for P_COGS_Acct */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_COGS_Acct = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination>(I_M_Product_Category_Acct.class, "P_COGS_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_COGS_Acct */
    public static final String COLUMNNAME_P_COGS_Acct = "P_COGS_Acct";

	/**
	 * Set Bezugsnebenkosten.
	 * Konto für Bezugsnebenkosten
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_CostAdjustment_Acct (int P_CostAdjustment_Acct);

	/**
	 * Get Bezugsnebenkosten.
	 * Konto für Bezugsnebenkosten
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_CostAdjustment_Acct();

	public org.compiere.model.I_C_ValidCombination getP_CostAdjustment_A();

	public void setP_CostAdjustment_A(org.compiere.model.I_C_ValidCombination P_CostAdjustment_A);

    /** Column definition for P_CostAdjustment_Acct */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_CostAdjustment_Acct = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination>(I_M_Product_Category_Acct.class, "P_CostAdjustment_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_CostAdjustment_Acct */
    public static final String COLUMNNAME_P_CostAdjustment_Acct = "P_CostAdjustment_Acct";

	/**
	 * Set Cost Of Production.
	 * The Cost Of Production account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_CostOfProduction_Acct (int P_CostOfProduction_Acct);

	/**
	 * Get Cost Of Production.
	 * The Cost Of Production account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_CostOfProduction_Acct();

	public org.compiere.model.I_C_ValidCombination getP_CostOfProduction_A();

	public void setP_CostOfProduction_A(org.compiere.model.I_C_ValidCombination P_CostOfProduction_A);

    /** Column definition for P_CostOfProduction_Acct */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_CostOfProduction_Acct = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination>(I_M_Product_Category_Acct.class, "P_CostOfProduction_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_CostOfProduction_Acct */
    public static final String COLUMNNAME_P_CostOfProduction_Acct = "P_CostOfProduction_Acct";

	/**
	 * Set Produkt Aufwand.
	 * Konto für Produkt Aufwand
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_Expense_Acct (int P_Expense_Acct);

	/**
	 * Get Produkt Aufwand.
	 * Konto für Produkt Aufwand
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_Expense_Acct();

	public org.compiere.model.I_C_ValidCombination getP_Expense_A();

	public void setP_Expense_A(org.compiere.model.I_C_ValidCombination P_Expense_A);

    /** Column definition for P_Expense_Acct */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_Expense_Acct = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination>(I_M_Product_Category_Acct.class, "P_Expense_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_Expense_Acct */
    public static final String COLUMNNAME_P_Expense_Acct = "P_Expense_Acct";

	/**
	 * Set Floor Stock.
	 * The Floor Stock account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_FloorStock_Acct (int P_FloorStock_Acct);

	/**
	 * Get Floor Stock.
	 * The Floor Stock account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_FloorStock_Acct();

	public org.compiere.model.I_C_ValidCombination getP_FloorStock_A();

	public void setP_FloorStock_A(org.compiere.model.I_C_ValidCombination P_FloorStock_A);

    /** Column definition for P_FloorStock_Acct */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_FloorStock_Acct = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination>(I_M_Product_Category_Acct.class, "P_FloorStock_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_FloorStock_Acct */
    public static final String COLUMNNAME_P_FloorStock_Acct = "P_FloorStock_Acct";

	/**
	 * Set Inventory Clearing.
	 * Product Inventory Clearing Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_InventoryClearing_Acct (int P_InventoryClearing_Acct);

	/**
	 * Get Inventory Clearing.
	 * Product Inventory Clearing Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_InventoryClearing_Acct();

	public org.compiere.model.I_C_ValidCombination getP_InventoryClearing_A();

	public void setP_InventoryClearing_A(org.compiere.model.I_C_ValidCombination P_InventoryClearing_A);

    /** Column definition for P_InventoryClearing_Acct */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_InventoryClearing_Acct = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination>(I_M_Product_Category_Acct.class, "P_InventoryClearing_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_InventoryClearing_Acct */
    public static final String COLUMNNAME_P_InventoryClearing_Acct = "P_InventoryClearing_Acct";

	/**
	 * Set Preisdifferenz Einkauf Rechnung.
	 * Konto für Preisdifferenz Einkauf Rechnung
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_InvoicePriceVariance_Acct (int P_InvoicePriceVariance_Acct);

	/**
	 * Get Preisdifferenz Einkauf Rechnung.
	 * Konto für Preisdifferenz Einkauf Rechnung
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_InvoicePriceVariance_Acct();

	public org.compiere.model.I_C_ValidCombination getP_InvoicePriceVariance_A();

	public void setP_InvoicePriceVariance_A(org.compiere.model.I_C_ValidCombination P_InvoicePriceVariance_A);

    /** Column definition for P_InvoicePriceVariance_Acct */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_InvoicePriceVariance_Acct = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination>(I_M_Product_Category_Acct.class, "P_InvoicePriceVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_InvoicePriceVariance_Acct */
    public static final String COLUMNNAME_P_InvoicePriceVariance_Acct = "P_InvoicePriceVariance_Acct";

	/**
	 * Set Labor.
	 * The Labor account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_Labor_Acct (int P_Labor_Acct);

	/**
	 * Get Labor.
	 * The Labor account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_Labor_Acct();

	public org.compiere.model.I_C_ValidCombination getP_Labor_A();

	public void setP_Labor_A(org.compiere.model.I_C_ValidCombination P_Labor_A);

    /** Column definition for P_Labor_Acct */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_Labor_Acct = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination>(I_M_Product_Category_Acct.class, "P_Labor_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_Labor_Acct */
    public static final String COLUMNNAME_P_Labor_Acct = "P_Labor_Acct";

	/**
	 * Set Method Change Variance.
	 * The Method Change Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_MethodChangeVariance_Acct (int P_MethodChangeVariance_Acct);

	/**
	 * Get Method Change Variance.
	 * The Method Change Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_MethodChangeVariance_Acct();

	public org.compiere.model.I_C_ValidCombination getP_MethodChangeVariance_A();

	public void setP_MethodChangeVariance_A(org.compiere.model.I_C_ValidCombination P_MethodChangeVariance_A);

    /** Column definition for P_MethodChangeVariance_Acct */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_MethodChangeVariance_Acct = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination>(I_M_Product_Category_Acct.class, "P_MethodChangeVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_MethodChangeVariance_Acct */
    public static final String COLUMNNAME_P_MethodChangeVariance_Acct = "P_MethodChangeVariance_Acct";

	/**
	 * Set Mix Variance.
	 * The Mix Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_MixVariance_Acct (int P_MixVariance_Acct);

	/**
	 * Get Mix Variance.
	 * The Mix Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_MixVariance_Acct();

	public org.compiere.model.I_C_ValidCombination getP_MixVariance_A();

	public void setP_MixVariance_A(org.compiere.model.I_C_ValidCombination P_MixVariance_A);

    /** Column definition for P_MixVariance_Acct */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_MixVariance_Acct = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination>(I_M_Product_Category_Acct.class, "P_MixVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_MixVariance_Acct */
    public static final String COLUMNNAME_P_MixVariance_Acct = "P_MixVariance_Acct";

	/**
	 * Set Outside Processing.
	 * The Outside Processing Account is the account used in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_OutsideProcessing_Acct (int P_OutsideProcessing_Acct);

	/**
	 * Get Outside Processing.
	 * The Outside Processing Account is the account used in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_OutsideProcessing_Acct();

	public org.compiere.model.I_C_ValidCombination getP_OutsideProcessing_A();

	public void setP_OutsideProcessing_A(org.compiere.model.I_C_ValidCombination P_OutsideProcessing_A);

    /** Column definition for P_OutsideProcessing_Acct */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_OutsideProcessing_Acct = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination>(I_M_Product_Category_Acct.class, "P_OutsideProcessing_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_OutsideProcessing_Acct */
    public static final String COLUMNNAME_P_OutsideProcessing_Acct = "P_OutsideProcessing_Acct";

	/**
	 * Set Overhead.
	 * The Overhead account is the account used  in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_Overhead_Acct (int P_Overhead_Acct);

	/**
	 * Get Overhead.
	 * The Overhead account is the account used  in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_Overhead_Acct();

	public org.compiere.model.I_C_ValidCombination getP_Overhead_A();

	public void setP_Overhead_A(org.compiere.model.I_C_ValidCombination P_Overhead_A);

    /** Column definition for P_Overhead_Acct */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_Overhead_Acct = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination>(I_M_Product_Category_Acct.class, "P_Overhead_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_Overhead_Acct */
    public static final String COLUMNNAME_P_Overhead_Acct = "P_Overhead_Acct";

	/**
	 * Set Preisdifferenz Bestellung.
	 * Konto für Differenz zwischen Standard Kosten und Preis in Bestellung.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_PurchasePriceVariance_Acct (int P_PurchasePriceVariance_Acct);

	/**
	 * Get Preisdifferenz Bestellung.
	 * Konto für Differenz zwischen Standard Kosten und Preis in Bestellung.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_PurchasePriceVariance_Acct();

	public org.compiere.model.I_C_ValidCombination getP_PurchasePriceVariance_A();

	public void setP_PurchasePriceVariance_A(org.compiere.model.I_C_ValidCombination P_PurchasePriceVariance_A);

    /** Column definition for P_PurchasePriceVariance_Acct */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_PurchasePriceVariance_Acct = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination>(I_M_Product_Category_Acct.class, "P_PurchasePriceVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_PurchasePriceVariance_Acct */
    public static final String COLUMNNAME_P_PurchasePriceVariance_Acct = "P_PurchasePriceVariance_Acct";

	/**
	 * Set Rate Variance.
	 * The Rate Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_RateVariance_Acct (int P_RateVariance_Acct);

	/**
	 * Get Rate Variance.
	 * The Rate Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_RateVariance_Acct();

	public org.compiere.model.I_C_ValidCombination getP_RateVariance_A();

	public void setP_RateVariance_A(org.compiere.model.I_C_ValidCombination P_RateVariance_A);

    /** Column definition for P_RateVariance_Acct */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_RateVariance_Acct = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination>(I_M_Product_Category_Acct.class, "P_RateVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_RateVariance_Acct */
    public static final String COLUMNNAME_P_RateVariance_Acct = "P_RateVariance_Acct";

	/**
	 * Set Produkt Ertrag.
	 * Konto für Produkt Ertrag
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_Revenue_Acct (int P_Revenue_Acct);

	/**
	 * Get Produkt Ertrag.
	 * Konto für Produkt Ertrag
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_Revenue_Acct();

	public org.compiere.model.I_C_ValidCombination getP_Revenue_A();

	public void setP_Revenue_A(org.compiere.model.I_C_ValidCombination P_Revenue_A);

    /** Column definition for P_Revenue_Acct */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_Revenue_Acct = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination>(I_M_Product_Category_Acct.class, "P_Revenue_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_Revenue_Acct */
    public static final String COLUMNNAME_P_Revenue_Acct = "P_Revenue_Acct";

	/**
	 * Set Scrap.
	 * The Scrap account is the account used  in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_Scrap_Acct (int P_Scrap_Acct);

	/**
	 * Get Scrap.
	 * The Scrap account is the account used  in Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_Scrap_Acct();

	public org.compiere.model.I_C_ValidCombination getP_Scrap_A();

	public void setP_Scrap_A(org.compiere.model.I_C_ValidCombination P_Scrap_A);

    /** Column definition for P_Scrap_Acct */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_Scrap_Acct = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination>(I_M_Product_Category_Acct.class, "P_Scrap_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_Scrap_Acct */
    public static final String COLUMNNAME_P_Scrap_Acct = "P_Scrap_Acct";

	/**
	 * Set Gewährte Rabatte.
	 * Konto für gewährte Rabatte
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_TradeDiscountGrant_Acct (int P_TradeDiscountGrant_Acct);

	/**
	 * Get Gewährte Rabatte.
	 * Konto für gewährte Rabatte
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_TradeDiscountGrant_Acct();

	public org.compiere.model.I_C_ValidCombination getP_TradeDiscountGrant_A();

	public void setP_TradeDiscountGrant_A(org.compiere.model.I_C_ValidCombination P_TradeDiscountGrant_A);

    /** Column definition for P_TradeDiscountGrant_Acct */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_TradeDiscountGrant_Acct = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination>(I_M_Product_Category_Acct.class, "P_TradeDiscountGrant_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_TradeDiscountGrant_Acct */
    public static final String COLUMNNAME_P_TradeDiscountGrant_Acct = "P_TradeDiscountGrant_Acct";

	/**
	 * Set Erhaltene Rabatte.
	 * Konto für erhaltene Rabatte
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_TradeDiscountRec_Acct (int P_TradeDiscountRec_Acct);

	/**
	 * Get Erhaltene Rabatte.
	 * Konto für erhaltene Rabatte
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_TradeDiscountRec_Acct();

	public org.compiere.model.I_C_ValidCombination getP_TradeDiscountRec_A();

	public void setP_TradeDiscountRec_A(org.compiere.model.I_C_ValidCombination P_TradeDiscountRec_A);

    /** Column definition for P_TradeDiscountRec_Acct */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_TradeDiscountRec_Acct = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination>(I_M_Product_Category_Acct.class, "P_TradeDiscountRec_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_TradeDiscountRec_Acct */
    public static final String COLUMNNAME_P_TradeDiscountRec_Acct = "P_TradeDiscountRec_Acct";

	/**
	 * Set Usage Variance.
	 * The Usage Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_UsageVariance_Acct (int P_UsageVariance_Acct);

	/**
	 * Get Usage Variance.
	 * The Usage Variance account is the account used Manufacturing Order
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_UsageVariance_Acct();

	public org.compiere.model.I_C_ValidCombination getP_UsageVariance_A();

	public void setP_UsageVariance_A(org.compiere.model.I_C_ValidCombination P_UsageVariance_A);

    /** Column definition for P_UsageVariance_Acct */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_UsageVariance_Acct = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination>(I_M_Product_Category_Acct.class, "P_UsageVariance_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_UsageVariance_Acct */
    public static final String COLUMNNAME_P_UsageVariance_Acct = "P_UsageVariance_Acct";

	/**
	 * Set Unfertige Leistungen.
	 * Das Konto Unfertige Leistungen wird im Produktionaauftrag verwendet
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setP_WIP_Acct (int P_WIP_Acct);

	/**
	 * Get Unfertige Leistungen.
	 * Das Konto Unfertige Leistungen wird im Produktionaauftrag verwendet
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getP_WIP_Acct();

	public org.compiere.model.I_C_ValidCombination getP_WIP_A();

	public void setP_WIP_A(org.compiere.model.I_C_ValidCombination P_WIP_A);

    /** Column definition for P_WIP_Acct */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_P_WIP_Acct = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_C_ValidCombination>(I_M_Product_Category_Acct.class, "P_WIP_Acct", org.compiere.model.I_C_ValidCombination.class);
    /** Column name P_WIP_Acct */
    public static final String COLUMNNAME_P_WIP_Acct = "P_WIP_Acct";

	/**
	 * Set Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, Object>(I_M_Product_Category_Acct.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, Object>(I_M_Product_Category_Acct.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Product_Category_Acct, org.compiere.model.I_AD_User>(I_M_Product_Category_Acct.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
