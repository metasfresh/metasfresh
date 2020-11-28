package org.compiere.model;


/** Generated Interface for AD_SQLColumn_SourceTableColumn
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_SQLColumn_SourceTableColumn 
{

    /** TableName=AD_SQLColumn_SourceTableColumn */
    public static final String Table_Name = "AD_SQLColumn_SourceTableColumn";

    /** AD_Table_ID=541464 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

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
    public static final org.adempiere.model.ModelColumn<I_AD_SQLColumn_SourceTableColumn, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new org.adempiere.model.ModelColumn<I_AD_SQLColumn_SourceTableColumn, org.compiere.model.I_AD_Column>(I_AD_SQLColumn_SourceTableColumn.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
    /** Column name AD_Column_ID */
    public static final String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

	/**
	 * Set Organisation.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set SQL Column's Source Table Column.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_SQLColumn_SourceTableColumn_ID (int AD_SQLColumn_SourceTableColumn_ID);

	/**
	 * Get SQL Column's Source Table Column.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_SQLColumn_SourceTableColumn_ID();

    /** Column definition for AD_SQLColumn_SourceTableColumn_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_SQLColumn_SourceTableColumn, Object> COLUMN_AD_SQLColumn_SourceTableColumn_ID = new org.adempiere.model.ModelColumn<I_AD_SQLColumn_SourceTableColumn, Object>(I_AD_SQLColumn_SourceTableColumn.class, "AD_SQLColumn_SourceTableColumn_ID", null);
    /** Column name AD_SQLColumn_SourceTableColumn_ID */
    public static final String COLUMNNAME_AD_SQLColumn_SourceTableColumn_ID = "AD_SQLColumn_SourceTableColumn_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_SQLColumn_SourceTableColumn, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_SQLColumn_SourceTableColumn, Object>(I_AD_SQLColumn_SourceTableColumn.class, "Created", null);
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

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Fetch Target Records Method.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFetchTargetRecordsMethod (java.lang.String FetchTargetRecordsMethod);

	/**
	 * Get Fetch Target Records Method.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFetchTargetRecordsMethod();

    /** Column definition for FetchTargetRecordsMethod */
    public static final org.adempiere.model.ModelColumn<I_AD_SQLColumn_SourceTableColumn, Object> COLUMN_FetchTargetRecordsMethod = new org.adempiere.model.ModelColumn<I_AD_SQLColumn_SourceTableColumn, Object>(I_AD_SQLColumn_SourceTableColumn.class, "FetchTargetRecordsMethod", null);
    /** Column name FetchTargetRecordsMethod */
    public static final String COLUMNNAME_FetchTargetRecordsMethod = "FetchTargetRecordsMethod";

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
    public static final org.adempiere.model.ModelColumn<I_AD_SQLColumn_SourceTableColumn, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_SQLColumn_SourceTableColumn, Object>(I_AD_SQLColumn_SourceTableColumn.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Link Column.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLink_Column_ID (int Link_Column_ID);

	/**
	 * Get Link Column.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLink_Column_ID();

	public org.compiere.model.I_AD_Column getLink_Column();

	public void setLink_Column(org.compiere.model.I_AD_Column Link_Column);

    /** Column definition for Link_Column_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_SQLColumn_SourceTableColumn, org.compiere.model.I_AD_Column> COLUMN_Link_Column_ID = new org.adempiere.model.ModelColumn<I_AD_SQLColumn_SourceTableColumn, org.compiere.model.I_AD_Column>(I_AD_SQLColumn_SourceTableColumn.class, "Link_Column_ID", org.compiere.model.I_AD_Column.class);
    /** Column name Link_Column_ID */
    public static final String COLUMNNAME_Link_Column_ID = "Link_Column_ID";

	/**
	 * Set Source Column.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSource_Column_ID (int Source_Column_ID);

	/**
	 * Get Source Column.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSource_Column_ID();

	public org.compiere.model.I_AD_Column getSource_Column();

	public void setSource_Column(org.compiere.model.I_AD_Column Source_Column);

    /** Column definition for Source_Column_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_SQLColumn_SourceTableColumn, org.compiere.model.I_AD_Column> COLUMN_Source_Column_ID = new org.adempiere.model.ModelColumn<I_AD_SQLColumn_SourceTableColumn, org.compiere.model.I_AD_Column>(I_AD_SQLColumn_SourceTableColumn.class, "Source_Column_ID", org.compiere.model.I_AD_Column.class);
    /** Column name Source_Column_ID */
    public static final String COLUMNNAME_Source_Column_ID = "Source_Column_ID";

	/**
	 * Set Source Table.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSource_Table_ID (int Source_Table_ID);

	/**
	 * Get Source Table.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSource_Table_ID();

    /** Column name Source_Table_ID */
    public static final String COLUMNNAME_Source_Table_ID = "Source_Table_ID";

	/**
	 * Set SQL to get Target Record IDs by Source Record IDs.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSQL_GetTargetRecordIdBySourceRecordId (java.lang.String SQL_GetTargetRecordIdBySourceRecordId);

	/**
	 * Get SQL to get Target Record IDs by Source Record IDs.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSQL_GetTargetRecordIdBySourceRecordId();

    /** Column definition for SQL_GetTargetRecordIdBySourceRecordId */
    public static final org.adempiere.model.ModelColumn<I_AD_SQLColumn_SourceTableColumn, Object> COLUMN_SQL_GetTargetRecordIdBySourceRecordId = new org.adempiere.model.ModelColumn<I_AD_SQLColumn_SourceTableColumn, Object>(I_AD_SQLColumn_SourceTableColumn.class, "SQL_GetTargetRecordIdBySourceRecordId", null);
    /** Column name SQL_GetTargetRecordIdBySourceRecordId */
    public static final String COLUMNNAME_SQL_GetTargetRecordIdBySourceRecordId = "SQL_GetTargetRecordIdBySourceRecordId";

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
    public static final org.adempiere.model.ModelColumn<I_AD_SQLColumn_SourceTableColumn, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_SQLColumn_SourceTableColumn, Object>(I_AD_SQLColumn_SourceTableColumn.class, "Updated", null);
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

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
