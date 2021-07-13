package org.compiere.model;


/** Generated Interface for M_Replenish
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Replenish 
{

    /** TableName=M_Replenish */
    public static final String Table_Name = "M_Replenish";

    /** AD_Table_ID=249 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
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
    public static final org.adempiere.model.ModelColumn<I_M_Replenish, org.compiere.model.I_C_Calendar> COLUMN_C_Calendar_ID = new org.adempiere.model.ModelColumn<I_M_Replenish, org.compiere.model.I_C_Calendar>(I_M_Replenish.class, "C_Calendar_ID", org.compiere.model.I_C_Calendar.class);
    /** Column name C_Calendar_ID */
    public static final String COLUMNNAME_C_Calendar_ID = "C_Calendar_ID";

	/**
	 * Set Period.
	 * Period of the Calendar
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Period_ID (int C_Period_ID);

	/**
	 * Get Period.
	 * Period of the Calendar
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Period_ID();

	public org.compiere.model.I_C_Period getC_Period();

	public void setC_Period(org.compiere.model.I_C_Period C_Period);

    /** Column definition for C_Period_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Replenish, org.compiere.model.I_C_Period> COLUMN_C_Period_ID = new org.adempiere.model.ModelColumn<I_M_Replenish, org.compiere.model.I_C_Period>(I_M_Replenish.class, "C_Period_ID", org.compiere.model.I_C_Period.class);
    /** Column name C_Period_ID */
    public static final String COLUMNNAME_C_Period_ID = "C_Period_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_Replenish, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Replenish, Object>(I_M_Replenish.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_M_Replenish, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Replenish, Object>(I_M_Replenish.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Maximum Level.
	 * Maximum Inventory level for this product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLevel_Max (java.math.BigDecimal Level_Max);

	/**
	 * Get Maximum Level.
	 * Maximum Inventory level for this product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLevel_Max();

    /** Column definition for Level_Max */
    public static final org.adempiere.model.ModelColumn<I_M_Replenish, Object> COLUMN_Level_Max = new org.adempiere.model.ModelColumn<I_M_Replenish, Object>(I_M_Replenish.class, "Level_Max", null);
    /** Column name Level_Max */
    public static final String COLUMNNAME_Level_Max = "Level_Max";

	/**
	 * Set Minimum Level.
	 * Minimum Inventory level for this product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLevel_Min (java.math.BigDecimal Level_Min);

	/**
	 * Get Minimum Level.
	 * Minimum Inventory level for this product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLevel_Min();

    /** Column definition for Level_Min */
    public static final org.adempiere.model.ModelColumn<I_M_Replenish, Object> COLUMN_Level_Min = new org.adempiere.model.ModelColumn<I_M_Replenish, Object>(I_M_Replenish.class, "Level_Min", null);
    /** Column name Level_Min */
    public static final String COLUMNNAME_Level_Min = "Level_Min";

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
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set M_Replenish.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Replenish_ID (int M_Replenish_ID);

	/**
	 * Get M_Replenish.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Replenish_ID();

    /** Column definition for M_Replenish_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Replenish, Object> COLUMN_M_Replenish_ID = new org.adempiere.model.ModelColumn<I_M_Replenish, Object>(I_M_Replenish.class, "M_Replenish_ID", null);
    /** Column name M_Replenish_ID */
    public static final String COLUMNNAME_M_Replenish_ID = "M_Replenish_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Replenish Type.
	 * Method for re-ordering a product
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setReplenishType (java.lang.String ReplenishType);

	/**
	 * Get Replenish Type.
	 * Method for re-ordering a product
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReplenishType();

    /** Column definition for ReplenishType */
    public static final org.adempiere.model.ModelColumn<I_M_Replenish, Object> COLUMN_ReplenishType = new org.adempiere.model.ModelColumn<I_M_Replenish, Object>(I_M_Replenish.class, "ReplenishType", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Replenish, Object> COLUMN_TimeToMarket = new org.adempiere.model.ModelColumn<I_M_Replenish, Object>(I_M_Replenish.class, "TimeToMarket", null);
    /** Column name TimeToMarket */
    public static final String COLUMNNAME_TimeToMarket = "TimeToMarket";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_M_Replenish, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Replenish, Object>(I_M_Replenish.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
