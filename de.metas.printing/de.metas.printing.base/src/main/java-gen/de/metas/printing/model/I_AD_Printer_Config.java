package de.metas.printing.model;


/** Generated Interface for AD_Printer_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Printer_Config 
{

    /** TableName=AD_Printer_Config */
    public static final String Table_Name = "AD_Printer_Config";

    /** AD_Table_ID=540637 */
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
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Config, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Printer_Config, org.compiere.model.I_AD_Client>(I_AD_Printer_Config.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Config, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Printer_Config, org.compiere.model.I_AD_Org>(I_AD_Printer_Config.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Printer Matching Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Printer_Config_ID (int AD_Printer_Config_ID);

	/**
	 * Get Printer Matching Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Printer_Config_ID();

    /** Column definition for AD_Printer_Config_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object> COLUMN_AD_Printer_Config_ID = new org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object>(I_AD_Printer_Config.class, "AD_Printer_Config_ID", null);
    /** Column name AD_Printer_Config_ID */
    public static final String COLUMNNAME_AD_Printer_Config_ID = "AD_Printer_Config_ID";

	/**
	 * Set Benutzte Konfiguration.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Printer_Config_Shared_ID (int AD_Printer_Config_Shared_ID);

	/**
	 * Get Benutzte Konfiguration.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Printer_Config_Shared_ID();

	public de.metas.printing.model.I_AD_Printer_Config getAD_Printer_Config_Shared();

	public void setAD_Printer_Config_Shared(de.metas.printing.model.I_AD_Printer_Config AD_Printer_Config_Shared);

    /** Column definition for AD_Printer_Config_Shared_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Config, de.metas.printing.model.I_AD_Printer_Config> COLUMN_AD_Printer_Config_Shared_ID = new org.adempiere.model.ModelColumn<I_AD_Printer_Config, de.metas.printing.model.I_AD_Printer_Config>(I_AD_Printer_Config.class, "AD_Printer_Config_Shared_ID", de.metas.printing.model.I_AD_Printer_Config.class);
    /** Column name AD_Printer_Config_Shared_ID */
    public static final String COLUMNNAME_AD_Printer_Config_Shared_ID = "AD_Printer_Config_Shared_ID";

	/**
	 * Set Host Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setConfigHostKey (java.lang.String ConfigHostKey);

	/**
	 * Get Host Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getConfigHostKey();

    /** Column definition for ConfigHostKey */
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object> COLUMN_ConfigHostKey = new org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object>(I_AD_Printer_Config.class, "ConfigHostKey", null);
    /** Column name ConfigHostKey */
    public static final String COLUMNNAME_ConfigHostKey = "ConfigHostKey";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object>(I_AD_Printer_Config.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Config, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Printer_Config, org.compiere.model.I_AD_User>(I_AD_Printer_Config.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object>(I_AD_Printer_Config.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Geteilt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSharedPrinterConfig (boolean IsSharedPrinterConfig);

	/**
	 * Get Geteilt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSharedPrinterConfig();

    /** Column definition for IsSharedPrinterConfig */
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object> COLUMN_IsSharedPrinterConfig = new org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object>(I_AD_Printer_Config.class, "IsSharedPrinterConfig", null);
    /** Column name IsSharedPrinterConfig */
    public static final String COLUMNNAME_IsSharedPrinterConfig = "IsSharedPrinterConfig";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Printer_Config, Object>(I_AD_Printer_Config.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Config, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Printer_Config, org.compiere.model.I_AD_User>(I_AD_Printer_Config.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
