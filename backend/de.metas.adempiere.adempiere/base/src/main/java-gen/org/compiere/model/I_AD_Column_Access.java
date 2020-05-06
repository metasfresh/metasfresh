package org.compiere.model;


/** Generated Interface for AD_Column_Access
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Column_Access 
{

    /** TableName=AD_Column_Access */
    public static final String Table_Name = "AD_Column_Access";

    /** AD_Table_ID=571 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

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
    public static final org.adempiere.model.ModelColumn<I_AD_Column_Access, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Column_Access, org.compiere.model.I_AD_Client>(I_AD_Column_Access.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Spalte.
	 * Column in the table
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Spalte.
	 * Column in the table
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Column_ID();

	public org.compiere.model.I_AD_Column getAD_Column();

	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column);

    /** Column definition for AD_Column_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Column_Access, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new org.adempiere.model.ModelColumn<I_AD_Column_Access, org.compiere.model.I_AD_Column>(I_AD_Column_Access.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Column_Access, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Column_Access, org.compiere.model.I_AD_Org>(I_AD_Column_Access.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Rolle.
	 * Responsibility Role
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Role_ID (int AD_Role_ID);

	/**
	 * Get Rolle.
	 * Responsibility Role
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Role_ID();

	public org.compiere.model.I_AD_Role getAD_Role();

	public void setAD_Role(org.compiere.model.I_AD_Role AD_Role);

    /** Column definition for AD_Role_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Column_Access, org.compiere.model.I_AD_Role> COLUMN_AD_Role_ID = new org.adempiere.model.ModelColumn<I_AD_Column_Access, org.compiere.model.I_AD_Role>(I_AD_Column_Access.class, "AD_Role_ID", org.compiere.model.I_AD_Role.class);
    /** Column name AD_Role_ID */
    public static final String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Column_Access, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_AD_Column_Access, org.compiere.model.I_AD_Table>(I_AD_Column_Access.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Column_Access, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Column_Access, Object>(I_AD_Column_Access.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Column_Access, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Column_Access, org.compiere.model.I_AD_User>(I_AD_Column_Access.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Column_Access, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Column_Access, Object>(I_AD_Column_Access.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Ausschluß.
	 * Exclude access to the data - if not selected Include access to the data
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsExclude (boolean IsExclude);

	/**
	 * Get Ausschluß.
	 * Exclude access to the data - if not selected Include access to the data
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isExclude();

    /** Column definition for IsExclude */
    public static final org.adempiere.model.ModelColumn<I_AD_Column_Access, Object> COLUMN_IsExclude = new org.adempiere.model.ModelColumn<I_AD_Column_Access, Object>(I_AD_Column_Access.class, "IsExclude", null);
    /** Column name IsExclude */
    public static final String COLUMNNAME_IsExclude = "IsExclude";

	/**
	 * Set Schreibgeschützt.
	 * Field is read only
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsReadOnly (boolean IsReadOnly);

	/**
	 * Get Schreibgeschützt.
	 * Field is read only
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isReadOnly();

    /** Column definition for IsReadOnly */
    public static final org.adempiere.model.ModelColumn<I_AD_Column_Access, Object> COLUMN_IsReadOnly = new org.adempiere.model.ModelColumn<I_AD_Column_Access, Object>(I_AD_Column_Access.class, "IsReadOnly", null);
    /** Column name IsReadOnly */
    public static final String COLUMNNAME_IsReadOnly = "IsReadOnly";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Column_Access, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Column_Access, Object>(I_AD_Column_Access.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Column_Access, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Column_Access, org.compiere.model.I_AD_User>(I_AD_Column_Access.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
