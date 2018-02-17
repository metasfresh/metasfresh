package de.metas.datev.model;


/** Generated Interface for DATEV_ExportFormatColumn
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DATEV_ExportFormatColumn 
{

    /** TableName=DATEV_ExportFormatColumn */
    public static final String Table_Name = "DATEV_ExportFormatColumn";

    /** AD_Table_ID=540939 */
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
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, org.compiere.model.I_AD_Client>(I_DATEV_ExportFormatColumn.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Spalte.
	 * Spalte in der Tabelle
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Spalte.
	 * Spalte in der Tabelle
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Column_ID();

	public org.compiere.model.I_AD_Column getAD_Column();

	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column);

    /** Column definition for AD_Column_ID */
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, org.compiere.model.I_AD_Column>(I_DATEV_ExportFormatColumn.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
    /** Column name AD_Column_ID */
    public static final String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, org.compiere.model.I_AD_Org>(I_DATEV_ExportFormatColumn.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, Object>(I_DATEV_ExportFormatColumn.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, org.compiere.model.I_AD_User>(I_DATEV_ExportFormatColumn.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set DATEV Export Format.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDATEV_ExportFormat_ID (int DATEV_ExportFormat_ID);

	/**
	 * Get DATEV Export Format.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDATEV_ExportFormat_ID();

	public de.metas.datev.model.I_DATEV_ExportFormat getDATEV_ExportFormat();

	public void setDATEV_ExportFormat(de.metas.datev.model.I_DATEV_ExportFormat DATEV_ExportFormat);

    /** Column definition for DATEV_ExportFormat_ID */
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, de.metas.datev.model.I_DATEV_ExportFormat> COLUMN_DATEV_ExportFormat_ID = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, de.metas.datev.model.I_DATEV_ExportFormat>(I_DATEV_ExportFormatColumn.class, "DATEV_ExportFormat_ID", de.metas.datev.model.I_DATEV_ExportFormat.class);
    /** Column name DATEV_ExportFormat_ID */
    public static final String COLUMNNAME_DATEV_ExportFormat_ID = "DATEV_ExportFormat_ID";

	/**
	 * Set DATEV Export Format Column.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDATEV_ExportFormatColumn_ID (int DATEV_ExportFormatColumn_ID);

	/**
	 * Get DATEV Export Format Column.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDATEV_ExportFormatColumn_ID();

    /** Column definition for DATEV_ExportFormatColumn_ID */
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, Object> COLUMN_DATEV_ExportFormatColumn_ID = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, Object>(I_DATEV_ExportFormatColumn.class, "DATEV_ExportFormatColumn_ID", null);
    /** Column name DATEV_ExportFormatColumn_ID */
    public static final String COLUMNNAME_DATEV_ExportFormatColumn_ID = "DATEV_ExportFormatColumn_ID";

	/**
	 * Set Format Pattern.
	 * The pattern used to format a number or date.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFormatPattern (java.lang.String FormatPattern);

	/**
	 * Get Format Pattern.
	 * The pattern used to format a number or date.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFormatPattern();

    /** Column definition for FormatPattern */
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, Object> COLUMN_FormatPattern = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, Object>(I_DATEV_ExportFormatColumn.class, "FormatPattern", null);
    /** Column name FormatPattern */
    public static final String COLUMNNAME_FormatPattern = "FormatPattern";

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
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, Object>(I_DATEV_ExportFormatColumn.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, Object>(I_DATEV_ExportFormatColumn.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, Object>(I_DATEV_ExportFormatColumn.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

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
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, Object>(I_DATEV_ExportFormatColumn.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DATEV_ExportFormatColumn, org.compiere.model.I_AD_User>(I_DATEV_ExportFormatColumn.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
