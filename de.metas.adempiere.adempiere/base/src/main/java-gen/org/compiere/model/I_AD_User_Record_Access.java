package org.compiere.model;


/** Generated Interface for AD_User_Record_Access
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_User_Record_Access 
{

    /** TableName=AD_User_Record_Access */
    public static final String Table_Name = "AD_User_Record_Access";

    /** AD_Table_ID=541196 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

    /** Load Meta Data */

	/**
	 * Set Access.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAccess (java.lang.String Access);

	/**
	 * Get Access.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAccess();

    /** Column definition for Access */
    public static final org.adempiere.model.ModelColumn<I_AD_User_Record_Access, Object> COLUMN_Access = new org.adempiere.model.ModelColumn<I_AD_User_Record_Access, Object>(I_AD_User_Record_Access.class, "Access", null);
    /** Column name Access */
    public static final String COLUMNNAME_Access = "Access";

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
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set User Record Access.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_User_Record_Access_ID (int AD_User_Record_Access_ID);

	/**
	 * Get User Record Access.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_User_Record_Access_ID();

    /** Column definition for AD_User_Record_Access_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User_Record_Access, Object> COLUMN_AD_User_Record_Access_ID = new org.adempiere.model.ModelColumn<I_AD_User_Record_Access, Object>(I_AD_User_Record_Access.class, "AD_User_Record_Access_ID", null);
    /** Column name AD_User_Record_Access_ID */
    public static final String COLUMNNAME_AD_User_Record_Access_ID = "AD_User_Record_Access_ID";

	/**
	 * Set Nutzergruppe.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_UserGroup_ID (int AD_UserGroup_ID);

	/**
	 * Get Nutzergruppe.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_UserGroup_ID();

	public org.compiere.model.I_AD_UserGroup getAD_UserGroup();

	public void setAD_UserGroup(org.compiere.model.I_AD_UserGroup AD_UserGroup);

    /** Column definition for AD_UserGroup_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User_Record_Access, org.compiere.model.I_AD_UserGroup> COLUMN_AD_UserGroup_ID = new org.adempiere.model.ModelColumn<I_AD_User_Record_Access, org.compiere.model.I_AD_UserGroup>(I_AD_User_Record_Access.class, "AD_UserGroup_ID", org.compiere.model.I_AD_UserGroup.class);
    /** Column name AD_UserGroup_ID */
    public static final String COLUMNNAME_AD_UserGroup_ID = "AD_UserGroup_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_User_Record_Access, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_User_Record_Access, Object>(I_AD_User_Record_Access.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_User_Record_Access, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_User_Record_Access, Object>(I_AD_User_Record_Access.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Permission Issuer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPermissionIssuer (java.lang.String PermissionIssuer);

	/**
	 * Get Permission Issuer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPermissionIssuer();

    /** Column definition for PermissionIssuer */
    public static final org.adempiere.model.ModelColumn<I_AD_User_Record_Access, Object> COLUMN_PermissionIssuer = new org.adempiere.model.ModelColumn<I_AD_User_Record_Access, Object>(I_AD_User_Record_Access.class, "PermissionIssuer", null);
    /** Column name PermissionIssuer */
    public static final String COLUMNNAME_PermissionIssuer = "PermissionIssuer";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User_Record_Access, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_AD_User_Record_Access, Object>(I_AD_User_Record_Access.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_User_Record_Access, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_User_Record_Access, Object>(I_AD_User_Record_Access.class, "Updated", null);
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

	/**
	 * Set Gültig ab.
	 * Gültig ab inklusiv (erster Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Gültig ab.
	 * Gültig ab inklusiv (erster Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidFrom();

    /** Column definition for ValidFrom */
    public static final org.adempiere.model.ModelColumn<I_AD_User_Record_Access, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_AD_User_Record_Access, Object>(I_AD_User_Record_Access.class, "ValidFrom", null);
    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Gültig bis.
	 * Gültig bis inklusiv (letzter Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValidTo (java.sql.Timestamp ValidTo);

	/**
	 * Get Gültig bis.
	 * Gültig bis inklusiv (letzter Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidTo();

    /** Column definition for ValidTo */
    public static final org.adempiere.model.ModelColumn<I_AD_User_Record_Access, Object> COLUMN_ValidTo = new org.adempiere.model.ModelColumn<I_AD_User_Record_Access, Object>(I_AD_User_Record_Access.class, "ValidTo", null);
    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";
}
