package org.compiere.model;


/** Generated Interface for AD_Process_Access
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Process_Access 
{

    /** TableName=AD_Process_Access */
    public static final String Table_Name = "AD_Process_Access";

    /** AD_Table_ID=197 */
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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Access, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Process_Access, org.compiere.model.I_AD_Client>(I_AD_Process_Access.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Access, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Process_Access, org.compiere.model.I_AD_Org>(I_AD_Process_Access.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Prozess.
	 * Process or Report
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Prozess.
	 * Process or Report
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Process_ID();

	public org.compiere.model.I_AD_Process getAD_Process();

	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process);

    /** Column definition for AD_Process_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Access, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new org.adempiere.model.ModelColumn<I_AD_Process_Access, org.compiere.model.I_AD_Process>(I_AD_Process_Access.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
    /** Column name AD_Process_ID */
    public static final String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Access, org.compiere.model.I_AD_Role> COLUMN_AD_Role_ID = new org.adempiere.model.ModelColumn<I_AD_Process_Access, org.compiere.model.I_AD_Role>(I_AD_Process_Access.class, "AD_Role_ID", org.compiere.model.I_AD_Role.class);
    /** Column name AD_Role_ID */
    public static final String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Access, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Process_Access, Object>(I_AD_Process_Access.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Access, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Process_Access, org.compiere.model.I_AD_User>(I_AD_Process_Access.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Access, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Process_Access, Object>(I_AD_Process_Access.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Lesen und Schreiben.
	 * Field is read / write
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsReadWrite (boolean IsReadWrite);

	/**
	 * Get Lesen und Schreiben.
	 * Field is read / write
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isReadWrite();

    /** Column definition for IsReadWrite */
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Access, Object> COLUMN_IsReadWrite = new org.adempiere.model.ModelColumn<I_AD_Process_Access, Object>(I_AD_Process_Access.class, "IsReadWrite", null);
    /** Column name IsReadWrite */
    public static final String COLUMNNAME_IsReadWrite = "IsReadWrite";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Access, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Process_Access, Object>(I_AD_Process_Access.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Access, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Process_Access, org.compiere.model.I_AD_User>(I_AD_Process_Access.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
