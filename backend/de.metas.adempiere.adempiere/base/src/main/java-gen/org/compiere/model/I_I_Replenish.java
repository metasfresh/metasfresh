package org.compiere.model;


/** Generated Interface for I_Replenish
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_I_Replenish 
{

    /** TableName=I_Replenish */
    public static final String Table_Name = "I_Replenish";

    /** AD_Table_ID=541362 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Issue_ID();

	public org.compiere.model.I_AD_Issue getAD_Issue();

	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue);

    /** Column definition for AD_Issue_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_AD_Issue> COLUMN_AD_Issue_ID = new org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_AD_Issue>(I_I_Replenish.class, "AD_Issue_ID", org.compiere.model.I_AD_Issue.class);
    /** Column name AD_Issue_ID */
    public static final String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Calendar.
	 * Accounting Calendar Name
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Calendar_ID (int C_Calendar_ID);

	/**
	 * Get Calendar.
	 * Accounting Calendar Name
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Calendar_ID();

	public org.compiere.model.I_C_Calendar getC_Calendar();

	public void setC_Calendar(org.compiere.model.I_C_Calendar C_Calendar);

    /** Column definition for C_Calendar_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_C_Calendar> COLUMN_C_Calendar_ID = new org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_C_Calendar>(I_I_Replenish.class, "C_Calendar_ID", org.compiere.model.I_C_Calendar.class);
    /** Column name C_Calendar_ID */
    public static final String COLUMNNAME_C_Calendar_ID = "C_Calendar_ID";

	/**
	 * Set Data import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DataImport_ID (int C_DataImport_ID);

	/**
	 * Get Data import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DataImport_ID();

	public org.compiere.model.I_C_DataImport getC_DataImport();

	public void setC_DataImport(org.compiere.model.I_C_DataImport C_DataImport);

    /** Column definition for C_DataImport_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_C_DataImport> COLUMN_C_DataImport_ID = new org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_C_DataImport>(I_I_Replenish.class, "C_DataImport_ID", org.compiere.model.I_C_DataImport.class);
    /** Column name C_DataImport_ID */
    public static final String COLUMNNAME_C_DataImport_ID = "C_DataImport_ID";

	/**
	 * Set Data Import Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DataImport_Run_ID (int C_DataImport_Run_ID);

	/**
	 * Get Data Import Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DataImport_Run_ID();

	public org.compiere.model.I_C_DataImport_Run getC_DataImport_Run();

	public void setC_DataImport_Run(org.compiere.model.I_C_DataImport_Run C_DataImport_Run);

    /** Column definition for C_DataImport_Run_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_C_DataImport_Run> COLUMN_C_DataImport_Run_ID = new org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_C_DataImport_Run>(I_I_Replenish.class, "C_DataImport_Run_ID", org.compiere.model.I_C_DataImport_Run.class);
    /** Column name C_DataImport_Run_ID */
    public static final String COLUMNNAME_C_DataImport_Run_ID = "C_DataImport_Run_ID";

	/**
	 * Set Period.
	 * Period of the Calendar
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Period_ID (int C_Period_ID);

	/**
	 * Get Period.
	 * Period of the Calendar
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Period_ID();

	public org.compiere.model.I_C_Period getC_Period();

	public void setC_Period(org.compiere.model.I_C_Period C_Period);

    /** Column definition for C_Period_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_C_Period> COLUMN_C_Period_ID = new org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_C_Period>(I_I_Replenish.class, "C_Period_ID", org.compiere.model.I_C_Period.class);
    /** Column name C_Period_ID */
    public static final String COLUMNNAME_C_Period_ID = "C_Period_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Datum.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateGeneral (java.lang.String DateGeneral);

	/**
	 * Get Datum.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDateGeneral();

    /** Column definition for DateGeneral */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_DateGeneral = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "DateGeneral", null);
    /** Column name DateGeneral */
    public static final String COLUMNNAME_DateGeneral = "DateGeneral";

	/**
	 * Set Import Error Message.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg);

	/**
	 * Get Import Error Message.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_ErrorMsg();

    /** Column definition for I_ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_I_ErrorMsg = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "I_ErrorMsg", null);
    /** Column name I_ErrorMsg */
    public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Imported.
	 * Has this import been processed
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_IsImported (java.lang.String I_IsImported);

	/**
	 * Get Imported.
	 * Has this import been processed
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_IsImported();

    /** Column definition for I_IsImported */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_I_IsImported = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "I_IsImported", null);
    /** Column name I_IsImported */
    public static final String COLUMNNAME_I_IsImported = "I_IsImported";

	/**
	 * Set Import Line Content.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setI_LineContent (java.lang.String I_LineContent);

	/**
	 * Get Import Line Content.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_LineContent();

    /** Column definition for I_LineContent */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_I_LineContent = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "I_LineContent", null);
    /** Column name I_LineContent */
    public static final String COLUMNNAME_I_LineContent = "I_LineContent";

	/**
	 * Set Import Line No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setI_LineNo (int I_LineNo);

	/**
	 * Get Import Line No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getI_LineNo();

    /** Column definition for I_LineNo */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_I_LineNo = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "I_LineNo", null);
    /** Column name I_LineNo */
    public static final String COLUMNNAME_I_LineNo = "I_LineNo";

	/**
	 * Set Import Replenishment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_Replenish_ID (int I_Replenish_ID);

	/**
	 * Get Import Replenishment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getI_Replenish_ID();

    /** Column definition for I_Replenish_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_I_Replenish_ID = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "I_Replenish_ID", null);
    /** Column name I_Replenish_ID */
    public static final String COLUMNNAME_I_Replenish_ID = "I_Replenish_ID";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Maximum Level.
	 * Maximum Inventory level for this product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLevel_Max (java.math.BigDecimal Level_Max);

	/**
	 * Get Maximum Level.
	 * Maximum Inventory level for this product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLevel_Max();

    /** Column definition for Level_Max */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_Level_Max = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "Level_Max", null);
    /** Column name Level_Max */
    public static final String COLUMNNAME_Level_Max = "Level_Max";

	/**
	 * Set Minimum Level.
	 * Minimum Inventory level for this product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLevel_Min (java.math.BigDecimal Level_Min);

	/**
	 * Get Minimum Level.
	 * Minimum Inventory level for this product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLevel_Min();

    /** Column definition for Level_Min */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_Level_Min = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "Level_Min", null);
    /** Column name Level_Min */
    public static final String COLUMNNAME_Level_Min = "Level_Min";

	/**
	 * Set Locator Key.
	 * Key of the Warehouse Locator
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLocatorValue (java.lang.String LocatorValue);

	/**
	 * Get Locator Key.
	 * Key of the Warehouse Locator
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLocatorValue();

    /** Column definition for LocatorValue */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_LocatorValue = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "LocatorValue", null);
    /** Column name LocatorValue */
    public static final String COLUMNNAME_LocatorValue = "LocatorValue";

	/**
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Locator_ID();

    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set M_Replenish.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Replenish_ID (int M_Replenish_ID);

	/**
	 * Get M_Replenish.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Replenish_ID();

	public org.compiere.model.I_M_Replenish getM_Replenish();

	public void setM_Replenish(org.compiere.model.I_M_Replenish M_Replenish);

    /** Column definition for M_Replenish_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_M_Replenish> COLUMN_M_Replenish_ID = new org.adempiere.model.ModelColumn<I_I_Replenish, org.compiere.model.I_M_Replenish>(I_I_Replenish.class, "M_Replenish_ID", org.compiere.model.I_M_Replenish.class);
    /** Column name M_Replenish_ID */
    public static final String COLUMNNAME_M_Replenish_ID = "M_Replenish_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Organisation Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrgValue (java.lang.String OrgValue);

	/**
	 * Get Organisation Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getOrgValue();

    /** Column definition for OrgValue */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_OrgValue = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "OrgValue", null);
    /** Column name OrgValue */
    public static final String COLUMNNAME_OrgValue = "OrgValue";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Product Value.
	 * Product identifier;
 "val-<search key>", "ext-<external id>" or internal M_Product_ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductValue (java.lang.String ProductValue);

	/**
	 * Get Product Value.
	 * Product identifier;
 "val-<search key>", "ext-<external id>" or internal M_Product_ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductValue();

    /** Column definition for ProductValue */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_ProductValue = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "ProductValue", null);
    /** Column name ProductValue */
    public static final String COLUMNNAME_ProductValue = "ProductValue";

	/**
	 * Set Replenish Type.
	 * Method for re-ordering a product
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReplenishType (java.lang.String ReplenishType);

	/**
	 * Get Replenish Type.
	 * Method for re-ordering a product
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReplenishType();

    /** Column definition for ReplenishType */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_ReplenishType = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "ReplenishType", null);
    /** Column name ReplenishType */
    public static final String COLUMNNAME_ReplenishType = "ReplenishType";

	/**
	 * Set Time to Market.
	 * Time to Market (in Wochen)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTimeToMarket (int TimeToMarket);

	/**
	 * Get Time to Market.
	 * Time to Market (in Wochen)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getTimeToMarket();

    /** Column definition for TimeToMarket */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_TimeToMarket = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "TimeToMarket", null);
    /** Column name TimeToMarket */
    public static final String COLUMNNAME_TimeToMarket = "TimeToMarket";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Warehouse Key.
	 * Key of the Warehouse
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWarehouseValue (java.lang.String WarehouseValue);

	/**
	 * Get Warehouse Key.
	 * Key of the Warehouse
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWarehouseValue();

    /** Column definition for WarehouseValue */
    public static final org.adempiere.model.ModelColumn<I_I_Replenish, Object> COLUMN_WarehouseValue = new org.adempiere.model.ModelColumn<I_I_Replenish, Object>(I_I_Replenish.class, "WarehouseValue", null);
    /** Column name WarehouseValue */
    public static final String COLUMNNAME_WarehouseValue = "WarehouseValue";
}
