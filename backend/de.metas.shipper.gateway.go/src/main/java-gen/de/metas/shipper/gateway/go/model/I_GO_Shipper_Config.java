package de.metas.shipper.gateway.go.model;


/** Generated Interface for GO_Shipper_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_GO_Shipper_Config 
{

    /** TableName=GO_Shipper_Config */
    public static final String Table_Name = "GO_Shipper_Config";

    /** AD_Table_ID=540895 */
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

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_GO_Shipper_Config, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_GO_Shipper_Config, org.compiere.model.I_AD_Client>(I_GO_Shipper_Config.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_GO_Shipper_Config, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_GO_Shipper_Config, org.compiere.model.I_AD_Org>(I_GO_Shipper_Config.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_GO_Shipper_Config, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_GO_Shipper_Config, Object>(I_GO_Shipper_Config.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_GO_Shipper_Config, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_GO_Shipper_Config, org.compiere.model.I_AD_User>(I_GO_Shipper_Config.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Auth Password.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_AuthPassword (java.lang.String GO_AuthPassword);

	/**
	 * Get Auth Password.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGO_AuthPassword();

    /** Column definition for GO_AuthPassword */
    public static final org.adempiere.model.ModelColumn<I_GO_Shipper_Config, Object> COLUMN_GO_AuthPassword = new org.adempiere.model.ModelColumn<I_GO_Shipper_Config, Object>(I_GO_Shipper_Config.class, "GO_AuthPassword", null);
    /** Column name GO_AuthPassword */
    public static final String COLUMNNAME_GO_AuthPassword = "GO_AuthPassword";

	/**
	 * Set Auth Username.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_AuthUsername (java.lang.String GO_AuthUsername);

	/**
	 * Get Auth Username.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGO_AuthUsername();

    /** Column definition for GO_AuthUsername */
    public static final org.adempiere.model.ModelColumn<I_GO_Shipper_Config, Object> COLUMN_GO_AuthUsername = new org.adempiere.model.ModelColumn<I_GO_Shipper_Config, Object>(I_GO_Shipper_Config.class, "GO_AuthUsername", null);
    /** Column name GO_AuthUsername */
    public static final String COLUMNNAME_GO_AuthUsername = "GO_AuthUsername";

	/**
	 * Set Request Sender ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_RequestSenderId (java.lang.String GO_RequestSenderId);

	/**
	 * Get Request Sender ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGO_RequestSenderId();

    /** Column definition for GO_RequestSenderId */
    public static final org.adempiere.model.ModelColumn<I_GO_Shipper_Config, Object> COLUMN_GO_RequestSenderId = new org.adempiere.model.ModelColumn<I_GO_Shipper_Config, Object>(I_GO_Shipper_Config.class, "GO_RequestSenderId", null);
    /** Column name GO_RequestSenderId */
    public static final String COLUMNNAME_GO_RequestSenderId = "GO_RequestSenderId";

	/**
	 * Set Request Username.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_RequestUsername (java.lang.String GO_RequestUsername);

	/**
	 * Get Request Username.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGO_RequestUsername();

    /** Column definition for GO_RequestUsername */
    public static final org.adempiere.model.ModelColumn<I_GO_Shipper_Config, Object> COLUMN_GO_RequestUsername = new org.adempiere.model.ModelColumn<I_GO_Shipper_Config, Object>(I_GO_Shipper_Config.class, "GO_RequestUsername", null);
    /** Column name GO_RequestUsername */
    public static final String COLUMNNAME_GO_RequestUsername = "GO_RequestUsername";

	/**
	 * Set GO Shipper Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_Shipper_Config_ID (int GO_Shipper_Config_ID);

	/**
	 * Get GO Shipper Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getGO_Shipper_Config_ID();

    /** Column definition for GO_Shipper_Config_ID */
    public static final org.adempiere.model.ModelColumn<I_GO_Shipper_Config, Object> COLUMN_GO_Shipper_Config_ID = new org.adempiere.model.ModelColumn<I_GO_Shipper_Config, Object>(I_GO_Shipper_Config.class, "GO_Shipper_Config_ID", null);
    /** Column name GO_Shipper_Config_ID */
    public static final String COLUMNNAME_GO_Shipper_Config_ID = "GO_Shipper_Config_ID";

	/**
	 * Set GO URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_URL (java.lang.String GO_URL);

	/**
	 * Get GO URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGO_URL();

    /** Column definition for GO_URL */
    public static final org.adempiere.model.ModelColumn<I_GO_Shipper_Config, Object> COLUMN_GO_URL = new org.adempiere.model.ModelColumn<I_GO_Shipper_Config, Object>(I_GO_Shipper_Config.class, "GO_URL", null);
    /** Column name GO_URL */
    public static final String COLUMNNAME_GO_URL = "GO_URL";

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
    public static final org.adempiere.model.ModelColumn<I_GO_Shipper_Config, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_GO_Shipper_Config, Object>(I_GO_Shipper_Config.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Lieferweg.
	 * Methode oder Art der Warenlieferung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Lieferweg.
	 * Methode oder Art der Warenlieferung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Shipper_ID();

	public org.compiere.model.I_M_Shipper getM_Shipper();

	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

    /** Column definition for M_Shipper_ID */
    public static final org.adempiere.model.ModelColumn<I_GO_Shipper_Config, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new org.adempiere.model.ModelColumn<I_GO_Shipper_Config, org.compiere.model.I_M_Shipper>(I_GO_Shipper_Config.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
    /** Column name M_Shipper_ID */
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

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
    public static final org.adempiere.model.ModelColumn<I_GO_Shipper_Config, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_GO_Shipper_Config, Object>(I_GO_Shipper_Config.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_GO_Shipper_Config, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_GO_Shipper_Config, org.compiere.model.I_AD_User>(I_GO_Shipper_Config.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
