package org.compiere.model;


/** Generated Interface for AD_UserGroup_User_Assign
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_UserGroup_User_Assign 
{

    /** TableName=AD_UserGroup_User_Assign */
    public static final String Table_Name = "AD_UserGroup_User_Assign";

    /** AD_Table_ID=541188 */
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
    public static final org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, org.compiere.model.I_AD_Client>(I_AD_UserGroup_User_Assign.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, org.compiere.model.I_AD_Org>(I_AD_UserGroup_User_Assign.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User();

	public void setAD_User(org.compiere.model.I_AD_User AD_User);

    /** Column definition for AD_User_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, org.compiere.model.I_AD_User>(I_AD_UserGroup_User_Assign.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Users Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_UserGroup_ID (int AD_UserGroup_ID);

	/**
	 * Get Users Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_UserGroup_ID();

	public org.compiere.model.I_AD_UserGroup getAD_UserGroup();

	public void setAD_UserGroup(org.compiere.model.I_AD_UserGroup AD_UserGroup);

    /** Column definition for AD_UserGroup_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, org.compiere.model.I_AD_UserGroup> COLUMN_AD_UserGroup_ID = new org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, org.compiere.model.I_AD_UserGroup>(I_AD_UserGroup_User_Assign.class, "AD_UserGroup_ID", org.compiere.model.I_AD_UserGroup.class);
    /** Column name AD_UserGroup_ID */
    public static final String COLUMNNAME_AD_UserGroup_ID = "AD_UserGroup_ID";

	/**
	 * Set Users Group User Assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_UserGroup_User_Assign_ID (int AD_UserGroup_User_Assign_ID);

	/**
	 * Get Users Group User Assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_UserGroup_User_Assign_ID();

    /** Column definition for AD_UserGroup_User_Assign_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, Object> COLUMN_AD_UserGroup_User_Assign_ID = new org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, Object>(I_AD_UserGroup_User_Assign.class, "AD_UserGroup_User_Assign_ID", null);
    /** Column name AD_UserGroup_User_Assign_ID */
    public static final String COLUMNNAME_AD_UserGroup_User_Assign_ID = "AD_UserGroup_User_Assign_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, Object>(I_AD_UserGroup_User_Assign.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, org.compiere.model.I_AD_User>(I_AD_UserGroup_User_Assign.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, Object>(I_AD_UserGroup_User_Assign.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, Object>(I_AD_UserGroup_User_Assign.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, org.compiere.model.I_AD_User>(I_AD_UserGroup_User_Assign.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, Object>(I_AD_UserGroup_User_Assign.class, "ValidFrom", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, Object> COLUMN_ValidTo = new org.adempiere.model.ModelColumn<I_AD_UserGroup_User_Assign, Object>(I_AD_UserGroup_User_Assign.class, "ValidTo", null);
    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";
}
