package org.compiere.model;


/** Generated Interface for AD_ColumnCallout
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_ColumnCallout 
{

    /** TableName=AD_ColumnCallout */
    public static final String Table_Name = "AD_ColumnCallout";

    /** AD_Table_ID=53262 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

    /** Load Meta Data */

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ColumnCallout, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_ColumnCallout, org.compiere.model.I_AD_Client>(I_AD_ColumnCallout.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Column.
	 * Column in the table
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Column.
	 * Column in the table
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Column_ID();

	public org.compiere.model.I_AD_Column getAD_Column();

	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column);

    /** Column definition for AD_Column_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ColumnCallout, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new org.adempiere.model.ModelColumn<I_AD_ColumnCallout, org.compiere.model.I_AD_Column>(I_AD_ColumnCallout.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
    /** Column name AD_Column_ID */
    public static final String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

	/**
	 * Set Column Callout.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_ColumnCallout_ID (int AD_ColumnCallout_ID);

	/**
	 * Get Column Callout.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_ColumnCallout_ID();

    /** Column definition for AD_ColumnCallout_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ColumnCallout, Object> COLUMN_AD_ColumnCallout_ID = new org.adempiere.model.ModelColumn<I_AD_ColumnCallout, Object>(I_AD_ColumnCallout.class, "AD_ColumnCallout_ID", null);
    /** Column name AD_ColumnCallout_ID */
    public static final String COLUMNNAME_AD_ColumnCallout_ID = "AD_ColumnCallout_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_ColumnCallout, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_ColumnCallout, org.compiere.model.I_AD_Org>(I_AD_ColumnCallout.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ColumnCallout, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_AD_ColumnCallout, org.compiere.model.I_AD_Table>(I_AD_ColumnCallout.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Java-Klasse.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setClassname (java.lang.String Classname);

	/**
	 * Get Java-Klasse.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getClassname();

    /** Column definition for Classname */
    public static final org.adempiere.model.ModelColumn<I_AD_ColumnCallout, Object> COLUMN_Classname = new org.adempiere.model.ModelColumn<I_AD_ColumnCallout, Object>(I_AD_ColumnCallout.class, "Classname", null);
    /** Column name Classname */
    public static final String COLUMNNAME_Classname = "Classname";

	/**
	 * Set Spaltenname.
	 * Name der Spalte in der Datenbank
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setColumnName (java.lang.String ColumnName);

	/**
	 * Get Spaltenname.
	 * Name der Spalte in der Datenbank
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getColumnName();

    /** Column definition for ColumnName */
    public static final org.adempiere.model.ModelColumn<I_AD_ColumnCallout, Object> COLUMN_ColumnName = new org.adempiere.model.ModelColumn<I_AD_ColumnCallout, Object>(I_AD_ColumnCallout.class, "ColumnName", null);
    /** Column name ColumnName */
    public static final String COLUMNNAME_ColumnName = "ColumnName";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_AD_ColumnCallout, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_ColumnCallout, Object>(I_AD_ColumnCallout.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_ColumnCallout, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_ColumnCallout, org.compiere.model.I_AD_User>(I_AD_ColumnCallout.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_ColumnCallout, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_ColumnCallout, Object>(I_AD_ColumnCallout.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Entity Type.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entity Type.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEntityType();

    /** Column definition for EntityType */
    public static final org.adempiere.model.ModelColumn<I_AD_ColumnCallout, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_ColumnCallout, Object>(I_AD_ColumnCallout.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_AD_ColumnCallout, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_ColumnCallout, Object>(I_AD_ColumnCallout.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Sequence.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Sequence.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_AD_ColumnCallout, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_AD_ColumnCallout, Object>(I_AD_ColumnCallout.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_AD_ColumnCallout, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_ColumnCallout, Object>(I_AD_ColumnCallout.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_ColumnCallout, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_ColumnCallout, org.compiere.model.I_AD_User>(I_AD_ColumnCallout.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
