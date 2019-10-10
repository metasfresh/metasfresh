package de.metas.shipper.gateway.dhl.model;


/** Generated Interface for DHL_Shipper_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DHL_Shipper_Config 
{

    /** TableName=DHL_Shipper_Config */
    public static final String Table_Name = "DHL_Shipper_Config";

    /** AD_Table_ID=541411 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_DHL_Shipper_Config, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DHL_Shipper_Config, org.compiere.model.I_AD_Client>(I_DHL_Shipper_Config.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_DHL_Shipper_Config, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DHL_Shipper_Config, org.compiere.model.I_AD_Org>(I_DHL_Shipper_Config.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_DHL_Shipper_Config, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DHL_Shipper_Config, Object>(I_DHL_Shipper_Config.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_DHL_Shipper_Config, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DHL_Shipper_Config, org.compiere.model.I_AD_User>(I_DHL_Shipper_Config.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set DHL API URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setdhl_api_url (java.lang.String dhl_api_url);

	/**
	 * Get DHL API URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getdhl_api_url();

    /** Column definition for dhl_api_url */
    public static final org.adempiere.model.ModelColumn<I_DHL_Shipper_Config, Object> COLUMN_dhl_api_url = new org.adempiere.model.ModelColumn<I_DHL_Shipper_Config, Object>(I_DHL_Shipper_Config.class, "dhl_api_url", null);
    /** Column name dhl_api_url */
    public static final String COLUMNNAME_dhl_api_url = "dhl_api_url";

	/**
	 * Set DHL Shipper Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDHL_Shipper_Config_ID (int DHL_Shipper_Config_ID);

	/**
	 * Get DHL Shipper Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDHL_Shipper_Config_ID();

    /** Column definition for DHL_Shipper_Config_ID */
    public static final org.adempiere.model.ModelColumn<I_DHL_Shipper_Config, Object> COLUMN_DHL_Shipper_Config_ID = new org.adempiere.model.ModelColumn<I_DHL_Shipper_Config, Object>(I_DHL_Shipper_Config.class, "DHL_Shipper_Config_ID", null);
    /** Column name DHL_Shipper_Config_ID */
    public static final String COLUMNNAME_DHL_Shipper_Config_ID = "DHL_Shipper_Config_ID";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_DHL_Shipper_Config, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DHL_Shipper_Config, Object>(I_DHL_Shipper_Config.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_DHL_Shipper_Config, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DHL_Shipper_Config, Object>(I_DHL_Shipper_Config.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_DHL_Shipper_Config, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DHL_Shipper_Config, org.compiere.model.I_AD_User>(I_DHL_Shipper_Config.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
