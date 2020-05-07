package org.compiere.model;


/** Generated Interface for AD_Ref_Table
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Ref_Table 
{

    /** TableName=AD_Ref_Table */
    public static final String Table_Name = "AD_Ref_Table";

    /** AD_Table_ID=103 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_Table, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Ref_Table, org.compiere.model.I_AD_Client>(I_AD_Ref_Table.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Anzeige-Spalte.
	 * Column that will display
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Display (int AD_Display);

	/**
	 * Get Anzeige-Spalte.
	 * Column that will display
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Display();

	public org.compiere.model.I_AD_Column getAD_Disp();

	public void setAD_Disp(org.compiere.model.I_AD_Column AD_Disp);

    /** Column definition for AD_Display */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_Table, org.compiere.model.I_AD_Column> COLUMN_AD_Display = new org.adempiere.model.ModelColumn<I_AD_Ref_Table, org.compiere.model.I_AD_Column>(I_AD_Ref_Table.class, "AD_Display", org.compiere.model.I_AD_Column.class);
    /** Column name AD_Display */
    public static final String COLUMNNAME_AD_Display = "AD_Display";

	/**
	 * Set Schlüssel-Spalte.
	 * Unique identifier of a record
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Key (int AD_Key);

	/**
	 * Get Schlüssel-Spalte.
	 * Unique identifier of a record
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Key();

	public org.compiere.model.I_AD_Column getAD_();

	public void setAD_(org.compiere.model.I_AD_Column AD_);

    /** Column definition for AD_Key */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_Table, org.compiere.model.I_AD_Column> COLUMN_AD_Key = new org.adempiere.model.ModelColumn<I_AD_Ref_Table, org.compiere.model.I_AD_Column>(I_AD_Ref_Table.class, "AD_Key", org.compiere.model.I_AD_Column.class);
    /** Column name AD_Key */
    public static final String COLUMNNAME_AD_Key = "AD_Key";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_Table, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Ref_Table, org.compiere.model.I_AD_Org>(I_AD_Ref_Table.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Referenz.
	 * System Reference and Validation
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Reference_ID (int AD_Reference_ID);

	/**
	 * Get Referenz.
	 * System Reference and Validation
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Reference_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference();

	public void setAD_Reference(org.compiere.model.I_AD_Reference AD_Reference);

    /** Column definition for AD_Reference_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_Table, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_ID = new org.adempiere.model.ModelColumn<I_AD_Ref_Table, org.compiere.model.I_AD_Reference>(I_AD_Ref_Table.class, "AD_Reference_ID", org.compiere.model.I_AD_Reference.class);
    /** Column name AD_Reference_ID */
    public static final String COLUMNNAME_AD_Reference_ID = "AD_Reference_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_Table, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_AD_Ref_Table, org.compiere.model.I_AD_Table>(I_AD_Ref_Table.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Fenster.
	 * Data entry or display window
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Window_ID (int AD_Window_ID);

	/**
	 * Get Fenster.
	 * Data entry or display window
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Window_ID();

	public org.compiere.model.I_AD_Window getAD_Window();

	public void setAD_Window(org.compiere.model.I_AD_Window AD_Window);

    /** Column definition for AD_Window_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_Table, org.compiere.model.I_AD_Window> COLUMN_AD_Window_ID = new org.adempiere.model.ModelColumn<I_AD_Ref_Table, org.compiere.model.I_AD_Window>(I_AD_Ref_Table.class, "AD_Window_ID", org.compiere.model.I_AD_Window.class);
    /** Column name AD_Window_ID */
    public static final String COLUMNNAME_AD_Window_ID = "AD_Window_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_Table, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Ref_Table, Object>(I_AD_Ref_Table.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_Table, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Ref_Table, org.compiere.model.I_AD_User>(I_AD_Ref_Table.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEntityType();

    /** Column definition for EntityType */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_Table, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_Ref_Table, Object>(I_AD_Ref_Table.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_Table, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Ref_Table, Object>(I_AD_Ref_Table.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set 'Value' anzeigen.
	 * Displays Value column with the Display column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsValueDisplayed (boolean IsValueDisplayed);

	/**
	 * Get 'Value' anzeigen.
	 * Displays Value column with the Display column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isValueDisplayed();

    /** Column definition for IsValueDisplayed */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_Table, Object> COLUMN_IsValueDisplayed = new org.adempiere.model.ModelColumn<I_AD_Ref_Table, Object>(I_AD_Ref_Table.class, "IsValueDisplayed", null);
    /** Column name IsValueDisplayed */
    public static final String COLUMNNAME_IsValueDisplayed = "IsValueDisplayed";

	/**
	 * Set Sql ORDER BY.
	 * Fully qualified ORDER BY clause
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrderByClause (java.lang.String OrderByClause);

	/**
	 * Get Sql ORDER BY.
	 * Fully qualified ORDER BY clause
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getOrderByClause();

    /** Column definition for OrderByClause */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_Table, Object> COLUMN_OrderByClause = new org.adempiere.model.ModelColumn<I_AD_Ref_Table, Object>(I_AD_Ref_Table.class, "OrderByClause", null);
    /** Column name OrderByClause */
    public static final String COLUMNNAME_OrderByClause = "OrderByClause";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_Table, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Ref_Table, Object>(I_AD_Ref_Table.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_Table, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Ref_Table, org.compiere.model.I_AD_User>(I_AD_Ref_Table.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Sql WHERE.
	 * Fully qualified SQL WHERE clause
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWhereClause (java.lang.String WhereClause);

	/**
	 * Get Sql WHERE.
	 * Fully qualified SQL WHERE clause
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWhereClause();

    /** Column definition for WhereClause */
    public static final org.adempiere.model.ModelColumn<I_AD_Ref_Table, Object> COLUMN_WhereClause = new org.adempiere.model.ModelColumn<I_AD_Ref_Table, Object>(I_AD_Ref_Table.class, "WhereClause", null);
    /** Column name WhereClause */
    public static final String COLUMNNAME_WhereClause = "WhereClause";
}
