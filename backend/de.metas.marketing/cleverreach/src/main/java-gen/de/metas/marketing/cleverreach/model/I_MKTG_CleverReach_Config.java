package de.metas.marketing.cleverreach.model;


/** Generated Interface for MKTG_CleverReach_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MKTG_CleverReach_Config 
{

    /** TableName=MKTG_CleverReach_Config */
    public static final String Table_Name = "MKTG_CleverReach_Config";

    /** AD_Table_ID=540977 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

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
    public static final org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, org.compiere.model.I_AD_Client>(I_MKTG_CleverReach_Config.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, org.compiere.model.I_AD_Org>(I_MKTG_CleverReach_Config.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, Object>(I_MKTG_CleverReach_Config.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, org.compiere.model.I_AD_User>(I_MKTG_CleverReach_Config.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Kundennummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCustomerNo (java.lang.String CustomerNo);

	/**
	 * Get Kundennummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCustomerNo();

    /** Column definition for CustomerNo */
    public static final org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, Object> COLUMN_CustomerNo = new org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, Object>(I_MKTG_CleverReach_Config.class, "CustomerNo", null);
    /** Column name CustomerNo */
    public static final String COLUMNNAME_CustomerNo = "CustomerNo";

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
    public static final org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, Object>(I_MKTG_CleverReach_Config.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set MKTG_CleverReach_Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMKTG_CleverReach_Config_ID (int MKTG_CleverReach_Config_ID);

	/**
	 * Get MKTG_CleverReach_Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMKTG_CleverReach_Config_ID();

    /** Column definition for MKTG_CleverReach_Config_ID */
    public static final org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, Object> COLUMN_MKTG_CleverReach_Config_ID = new org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, Object>(I_MKTG_CleverReach_Config.class, "MKTG_CleverReach_Config_ID", null);
    /** Column name MKTG_CleverReach_Config_ID */
    public static final String COLUMNNAME_MKTG_CleverReach_Config_ID = "MKTG_CleverReach_Config_ID";

	/**
	 * Set MKTG_Platform.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMKTG_Platform_ID (int MKTG_Platform_ID);

	/**
	 * Get MKTG_Platform.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMKTG_Platform_ID();

    /** Column definition for MKTG_Platform_ID */
    public static final org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, Object> COLUMN_MKTG_Platform_ID = new org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, Object>(I_MKTG_CleverReach_Config.class, "MKTG_Platform_ID", null);
    /** Column name MKTG_Platform_ID */
    public static final String COLUMNNAME_MKTG_Platform_ID = "MKTG_Platform_ID";

	/**
	 * Set Kennwort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPassword (java.lang.String Password);

	/**
	 * Get Kennwort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPassword();

    /** Column definition for Password */
    public static final org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, Object> COLUMN_Password = new org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, Object>(I_MKTG_CleverReach_Config.class, "Password", null);
    /** Column name Password */
    public static final String COLUMNNAME_Password = "Password";

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
    public static final org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, Object>(I_MKTG_CleverReach_Config.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, org.compiere.model.I_AD_User>(I_MKTG_CleverReach_Config.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Registered EMail.
	 * Email of the responsible for the System
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setUserName (java.lang.String UserName);

	/**
	 * Get Registered EMail.
	 * Email of the responsible for the System
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUserName();

    /** Column definition for UserName */
    public static final org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, Object> COLUMN_UserName = new org.adempiere.model.ModelColumn<I_MKTG_CleverReach_Config, Object>(I_MKTG_CleverReach_Config.class, "UserName", null);
    /** Column name UserName */
    public static final String COLUMNNAME_UserName = "UserName";
}
