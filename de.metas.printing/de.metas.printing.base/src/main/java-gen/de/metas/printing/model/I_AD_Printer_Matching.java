package de.metas.printing.model;


/** Generated Interface for AD_Printer_Matching
 *  @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public interface I_AD_Printer_Matching
{

    /** TableName=AD_Printer_Matching */
    public static final String Table_Name = "AD_Printer_Matching";

    /** AD_Table_ID=540450 */
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
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Matching, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<>(I_AD_Printer_Matching.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Matching, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<>(I_AD_Printer_Matching.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Printer Matching Config.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Printer_Config_ID (int AD_Printer_Config_ID);

	/**
	 * Get Printer Matching Config.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Printer_Config_ID();

	public de.metas.printing.model.I_AD_Printer_Config getAD_Printer_Config();

	public void setAD_Printer_Config(de.metas.printing.model.I_AD_Printer_Config AD_Printer_Config);

    /** Column definition for AD_Printer_Config_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Matching, de.metas.printing.model.I_AD_Printer_Config> COLUMN_AD_Printer_Config_ID = new org.adempiere.model.ModelColumn<>(I_AD_Printer_Matching.class, "AD_Printer_Config_ID", de.metas.printing.model.I_AD_Printer_Config.class);
    /** Column name AD_Printer_Config_ID */
    public static final String COLUMNNAME_AD_Printer_Config_ID = "AD_Printer_Config_ID";

	/**
	 * Set Hardware-Drucker.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_PrinterHW_ID (int AD_PrinterHW_ID);

	/**
	 * Get Hardware-Drucker.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_PrinterHW_ID();

	public de.metas.printing.model.I_AD_PrinterHW getAD_PrinterHW();

	public void setAD_PrinterHW(de.metas.printing.model.I_AD_PrinterHW AD_PrinterHW);

    /** Column definition for AD_PrinterHW_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Matching, de.metas.printing.model.I_AD_PrinterHW> COLUMN_AD_PrinterHW_ID = new org.adempiere.model.ModelColumn<>(I_AD_Printer_Matching.class, "AD_PrinterHW_ID", de.metas.printing.model.I_AD_PrinterHW.class);
    /** Column name AD_PrinterHW_ID */
    public static final String COLUMNNAME_AD_PrinterHW_ID = "AD_PrinterHW_ID";

	/**
	 * Set Logischer Drucker.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Printer_ID (int AD_Printer_ID);

	/**
	 * Get Logischer Drucker.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Printer_ID();

    /** Column definition for AD_Printer_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Matching, Object> COLUMN_AD_Printer_ID = new org.adempiere.model.ModelColumn<>(I_AD_Printer_Matching.class, "AD_Printer_ID", null);
    /** Column name AD_Printer_ID */
    public static final String COLUMNNAME_AD_Printer_ID = "AD_Printer_ID";

	/**
	 * Set Printer matching.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Printer_Matching_ID (int AD_Printer_Matching_ID);

	/**
	 * Get Printer matching.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Printer_Matching_ID();

    /** Column definition for AD_Printer_Matching_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Matching, Object> COLUMN_AD_Printer_Matching_ID = new org.adempiere.model.ModelColumn<>(I_AD_Printer_Matching.class, "AD_Printer_Matching_ID", null);
    /** Column name AD_Printer_Matching_ID */
    public static final String COLUMNNAME_AD_Printer_Matching_ID = "AD_Printer_Matching_ID";

	/**
	 * Set AD_Tray_Matching_IncludedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Tray_Matching_IncludedTab (java.lang.String AD_Tray_Matching_IncludedTab);

	/**
	 * Get AD_Tray_Matching_IncludedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAD_Tray_Matching_IncludedTab();

    /** Column definition for AD_Tray_Matching_IncludedTab */
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Matching, Object> COLUMN_AD_Tray_Matching_IncludedTab = new org.adempiere.model.ModelColumn<>(I_AD_Printer_Matching.class, "AD_Tray_Matching_IncludedTab", null);
    /** Column name AD_Tray_Matching_IncludedTab */
    public static final String COLUMNNAME_AD_Tray_Matching_IncludedTab = "AD_Tray_Matching_IncludedTab";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Matching, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<>(I_AD_Printer_Matching.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Matching, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<>(I_AD_Printer_Matching.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Matching, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<>(I_AD_Printer_Matching.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Matching, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<>(I_AD_Printer_Matching.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Matching, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<>(I_AD_Printer_Matching.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Printer_Matching, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<>(I_AD_Printer_Matching.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
