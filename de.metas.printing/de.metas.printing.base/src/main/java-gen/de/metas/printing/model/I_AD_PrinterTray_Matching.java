package de.metas.printing.model;


/** Generated Interface for AD_PrinterTray_Matching
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_PrinterTray_Matching 
{

    /** TableName=AD_PrinterTray_Matching */
    public static final String Table_Name = "AD_PrinterTray_Matching";

    /** AD_Table_ID=540452 */
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
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterTray_Matching, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_PrinterTray_Matching, org.compiere.model.I_AD_Client>(I_AD_PrinterTray_Matching.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterTray_Matching, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_PrinterTray_Matching, org.compiere.model.I_AD_Org>(I_AD_PrinterTray_Matching.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Hardware-Schacht.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_PrinterHW_MediaTray_ID (int AD_PrinterHW_MediaTray_ID);

	/**
	 * Get Hardware-Schacht.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_PrinterHW_MediaTray_ID();

	public de.metas.printing.model.I_AD_PrinterHW_MediaTray getAD_PrinterHW_MediaTray();

	public void setAD_PrinterHW_MediaTray(de.metas.printing.model.I_AD_PrinterHW_MediaTray AD_PrinterHW_MediaTray);

    /** Column definition for AD_PrinterHW_MediaTray_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterTray_Matching, de.metas.printing.model.I_AD_PrinterHW_MediaTray> COLUMN_AD_PrinterHW_MediaTray_ID = new org.adempiere.model.ModelColumn<I_AD_PrinterTray_Matching, de.metas.printing.model.I_AD_PrinterHW_MediaTray>(I_AD_PrinterTray_Matching.class, "AD_PrinterHW_MediaTray_ID", de.metas.printing.model.I_AD_PrinterHW_MediaTray.class);
    /** Column name AD_PrinterHW_MediaTray_ID */
    public static final String COLUMNNAME_AD_PrinterHW_MediaTray_ID = "AD_PrinterHW_MediaTray_ID";

	/**
	 * Set Printer matching.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Printer_Matching_ID (int AD_Printer_Matching_ID);

	/**
	 * Get Printer matching.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Printer_Matching_ID();

	public de.metas.printing.model.I_AD_Printer_Matching getAD_Printer_Matching();

	public void setAD_Printer_Matching(de.metas.printing.model.I_AD_Printer_Matching AD_Printer_Matching);

    /** Column definition for AD_Printer_Matching_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterTray_Matching, de.metas.printing.model.I_AD_Printer_Matching> COLUMN_AD_Printer_Matching_ID = new org.adempiere.model.ModelColumn<I_AD_PrinterTray_Matching, de.metas.printing.model.I_AD_Printer_Matching>(I_AD_PrinterTray_Matching.class, "AD_Printer_Matching_ID", de.metas.printing.model.I_AD_Printer_Matching.class);
    /** Column name AD_Printer_Matching_ID */
    public static final String COLUMNNAME_AD_Printer_Matching_ID = "AD_Printer_Matching_ID";

	/**
	 * Set Logischer Schacht.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Printer_Tray_ID (int AD_Printer_Tray_ID);

	/**
	 * Get Logischer Schacht.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Printer_Tray_ID();

	public de.metas.printing.model.I_AD_Printer_Tray getAD_Printer_Tray();

	public void setAD_Printer_Tray(de.metas.printing.model.I_AD_Printer_Tray AD_Printer_Tray);

    /** Column definition for AD_Printer_Tray_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterTray_Matching, de.metas.printing.model.I_AD_Printer_Tray> COLUMN_AD_Printer_Tray_ID = new org.adempiere.model.ModelColumn<I_AD_PrinterTray_Matching, de.metas.printing.model.I_AD_Printer_Tray>(I_AD_PrinterTray_Matching.class, "AD_Printer_Tray_ID", de.metas.printing.model.I_AD_Printer_Tray.class);
    /** Column name AD_Printer_Tray_ID */
    public static final String COLUMNNAME_AD_Printer_Tray_ID = "AD_Printer_Tray_ID";

	/**
	 * Set Printer tray matching.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_PrinterTray_Matching_ID (int AD_PrinterTray_Matching_ID);

	/**
	 * Get Printer tray matching.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_PrinterTray_Matching_ID();

    /** Column definition for AD_PrinterTray_Matching_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterTray_Matching, Object> COLUMN_AD_PrinterTray_Matching_ID = new org.adempiere.model.ModelColumn<I_AD_PrinterTray_Matching, Object>(I_AD_PrinterTray_Matching.class, "AD_PrinterTray_Matching_ID", null);
    /** Column name AD_PrinterTray_Matching_ID */
    public static final String COLUMNNAME_AD_PrinterTray_Matching_ID = "AD_PrinterTray_Matching_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterTray_Matching, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_PrinterTray_Matching, Object>(I_AD_PrinterTray_Matching.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterTray_Matching, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_PrinterTray_Matching, org.compiere.model.I_AD_User>(I_AD_PrinterTray_Matching.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterTray_Matching, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_PrinterTray_Matching, Object>(I_AD_PrinterTray_Matching.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterTray_Matching, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_PrinterTray_Matching, Object>(I_AD_PrinterTray_Matching.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_PrinterTray_Matching, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_PrinterTray_Matching, org.compiere.model.I_AD_User>(I_AD_PrinterTray_Matching.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
