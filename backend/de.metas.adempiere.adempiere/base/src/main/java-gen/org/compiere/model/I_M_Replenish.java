package org.compiere.model;


/** Generated Interface for M_Replenish
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Replenish 
{

    /** TableName=M_Replenish */
    public static final String Table_Name = "M_Replenish";

    /** AD_Table_ID=249 */
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
    public static final org.adempiere.model.ModelColumn<I_M_Replenish, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Replenish, org.compiere.model.I_AD_Client>(I_M_Replenish.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Replenish, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Replenish, org.compiere.model.I_AD_Org>(I_M_Replenish.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Kalender.
	 * Bezeichnung des Buchf�hrungs-Kalenders
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Calendar_ID (int C_Calendar_ID);

	/**
	 * Get Kalender.
	 * Bezeichnung des Buchf�hrungs-Kalenders
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
	 * Set Periode.
	 * Periode des Kalenders
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Period_ID (int C_Period_ID);

	/**
	 * Get Periode.
	 * Periode des Kalenders
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
	 * Get Erstellt.
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
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_Replenish, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Replenish, org.compiere.model.I_AD_User>(I_M_Replenish.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Replenish, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Replenish, Object>(I_M_Replenish.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Maximalmenge.
	 * Maximum Inventory level for this product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLevel_Max (java.math.BigDecimal Level_Max);

	/**
	 * Get Maximalmenge.
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
	 * Set Mindestmenge.
	 * Minimum Inventory level for this product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLevel_Min (java.math.BigDecimal Level_Min);

	/**
	 * Get Mindestmenge.
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
	 * Set Lagerort.
	 * Warehouse Locator
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Lagerort.
	 * Warehouse Locator
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Locator_ID();

	public org.compiere.model.I_M_Locator getM_Locator();

	public void setM_Locator(org.compiere.model.I_M_Locator M_Locator);

    /** Column definition for M_Locator_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Replenish, org.compiere.model.I_M_Locator> COLUMN_M_Locator_ID = new org.adempiere.model.ModelColumn<I_M_Replenish, org.compiere.model.I_M_Locator>(I_M_Replenish.class, "M_Locator_ID", org.compiere.model.I_M_Locator.class);
    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Replenish, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_Replenish, org.compiere.model.I_M_Product>(I_M_Replenish.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
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
	 * Set Lager.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Lager.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse();

	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse);

    /** Column definition for M_Warehouse_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Replenish, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_M_Replenish, org.compiere.model.I_M_Warehouse>(I_M_Replenish.class, "M_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Source Warehouse.
	 * Optional Warehouse to replenish from
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_WarehouseSource_ID (int M_WarehouseSource_ID);

	/**
	 * Get Source Warehouse.
	 * Optional Warehouse to replenish from
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_WarehouseSource_ID();

	public org.compiere.model.I_M_Warehouse getM_WarehouseSource();

	public void setM_WarehouseSource(org.compiere.model.I_M_Warehouse M_WarehouseSource);

    /** Column definition for M_WarehouseSource_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Replenish, org.compiere.model.I_M_Warehouse> COLUMN_M_WarehouseSource_ID = new org.adempiere.model.ModelColumn<I_M_Replenish, org.compiere.model.I_M_Warehouse>(I_M_Replenish.class, "M_WarehouseSource_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_WarehouseSource_ID */
    public static final String COLUMNNAME_M_WarehouseSource_ID = "M_WarehouseSource_ID";

	/**
	 * Set Art der Wiederauffüllung.
	 * Method for re-ordering a product
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setReplenishType (java.lang.String ReplenishType);

	/**
	 * Get Art der Wiederauffüllung.
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
	 * Set Markteinführungszeit.
	 * Time to Market (in Wochen)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTimeToMarket (int TimeToMarket);

	/**
	 * Get Markteinführungszeit.
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
	 * Get Aktualisiert.
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
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_Replenish, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Replenish, org.compiere.model.I_AD_User>(I_M_Replenish.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
