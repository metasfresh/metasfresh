package de.metas.datev.model;


/** Generated Interface for DATEV_Export
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DATEV_Export 
{

    /** TableName=DATEV_Export */
    public static final String Table_Name = "DATEV_Export";

    /** AD_Table_ID=540934 */
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
    public static final org.adempiere.model.ModelColumn<I_DATEV_Export, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DATEV_Export, org.compiere.model.I_AD_Client>(I_DATEV_Export.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_DATEV_Export, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DATEV_Export, org.compiere.model.I_AD_Org>(I_DATEV_Export.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_DATEV_Export, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DATEV_Export, Object>(I_DATEV_Export.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_DATEV_Export, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DATEV_Export, org.compiere.model.I_AD_User>(I_DATEV_Export.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Buchungsdatum von.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateAcctFrom (java.sql.Timestamp DateAcctFrom);

	/**
	 * Get Buchungsdatum von.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateAcctFrom();

    /** Column definition for DateAcctFrom */
    public static final org.adempiere.model.ModelColumn<I_DATEV_Export, Object> COLUMN_DateAcctFrom = new org.adempiere.model.ModelColumn<I_DATEV_Export, Object>(I_DATEV_Export.class, "DateAcctFrom", null);
    /** Column name DateAcctFrom */
    public static final String COLUMNNAME_DateAcctFrom = "DateAcctFrom";

	/**
	 * Set Buchungsdatum bis.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateAcctTo (java.sql.Timestamp DateAcctTo);

	/**
	 * Get Buchungsdatum bis.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateAcctTo();

    /** Column definition for DateAcctTo */
    public static final org.adempiere.model.ModelColumn<I_DATEV_Export, Object> COLUMN_DateAcctTo = new org.adempiere.model.ModelColumn<I_DATEV_Export, Object>(I_DATEV_Export.class, "DateAcctTo", null);
    /** Column name DateAcctTo */
    public static final String COLUMNNAME_DateAcctTo = "DateAcctTo";

	/**
	 * Set DATEV Export.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDATEV_Export_ID (int DATEV_Export_ID);

	/**
	 * Get DATEV Export.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDATEV_Export_ID();

    /** Column definition for DATEV_Export_ID */
    public static final org.adempiere.model.ModelColumn<I_DATEV_Export, Object> COLUMN_DATEV_Export_ID = new org.adempiere.model.ModelColumn<I_DATEV_Export, Object>(I_DATEV_Export.class, "DATEV_Export_ID", null);
    /** Column name DATEV_Export_ID */
    public static final String COLUMNNAME_DATEV_Export_ID = "DATEV_Export_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DATEV_Export, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_DATEV_Export, Object>(I_DATEV_Export.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_DATEV_Export, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DATEV_Export, Object>(I_DATEV_Export.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Exclude already exported.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsExcludeAlreadyExported (boolean IsExcludeAlreadyExported);

	/**
	 * Get Exclude already exported.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isExcludeAlreadyExported();

    /** Column definition for IsExcludeAlreadyExported */
    public static final org.adempiere.model.ModelColumn<I_DATEV_Export, Object> COLUMN_IsExcludeAlreadyExported = new org.adempiere.model.ModelColumn<I_DATEV_Export, Object>(I_DATEV_Export.class, "IsExcludeAlreadyExported", null);
    /** Column name IsExcludeAlreadyExported */
    public static final String COLUMNNAME_IsExcludeAlreadyExported = "IsExcludeAlreadyExported";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_DATEV_Export, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_DATEV_Export, Object>(I_DATEV_Export.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

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
    public static final org.adempiere.model.ModelColumn<I_DATEV_Export, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DATEV_Export, Object>(I_DATEV_Export.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_DATEV_Export, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DATEV_Export, org.compiere.model.I_AD_User>(I_DATEV_Export.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
