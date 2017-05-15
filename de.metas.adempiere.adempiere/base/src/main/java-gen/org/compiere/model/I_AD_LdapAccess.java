package org.compiere.model;


/** Generated Interface for AD_LdapAccess
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_LdapAccess 
{

    /** TableName=AD_LdapAccess */
    public static final String Table_Name = "AD_LdapAccess";

    /** AD_Table_ID=904 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

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
    public static final org.adempiere.model.ModelColumn<I_AD_LdapAccess, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_LdapAccess, org.compiere.model.I_AD_Client>(I_AD_LdapAccess.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Ldap Access.
	 * Ldap Access Log
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_LdapAccess_ID (int AD_LdapAccess_ID);

	/**
	 * Get Ldap Access.
	 * Ldap Access Log
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_LdapAccess_ID();

    /** Column definition for AD_LdapAccess_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_LdapAccess, Object> COLUMN_AD_LdapAccess_ID = new org.adempiere.model.ModelColumn<I_AD_LdapAccess, Object>(I_AD_LdapAccess.class, "AD_LdapAccess_ID", null);
    /** Column name AD_LdapAccess_ID */
    public static final String COLUMNNAME_AD_LdapAccess_ID = "AD_LdapAccess_ID";

	/**
	 * Set LDAP-Server.
	 * LDAP Server to authenticate and authorize external systems based on Adempiere
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_LdapProcessor_ID (int AD_LdapProcessor_ID);

	/**
	 * Get LDAP-Server.
	 * LDAP Server to authenticate and authorize external systems based on Adempiere
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_LdapProcessor_ID();

	public org.compiere.model.I_AD_LdapProcessor getAD_LdapProcessor();

	public void setAD_LdapProcessor(org.compiere.model.I_AD_LdapProcessor AD_LdapProcessor);

    /** Column definition for AD_LdapProcessor_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_LdapAccess, org.compiere.model.I_AD_LdapProcessor> COLUMN_AD_LdapProcessor_ID = new org.adempiere.model.ModelColumn<I_AD_LdapAccess, org.compiere.model.I_AD_LdapProcessor>(I_AD_LdapAccess.class, "AD_LdapProcessor_ID", org.compiere.model.I_AD_LdapProcessor.class);
    /** Column name AD_LdapProcessor_ID */
    public static final String COLUMNNAME_AD_LdapProcessor_ID = "AD_LdapProcessor_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_LdapAccess, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_LdapAccess, org.compiere.model.I_AD_Org>(I_AD_LdapAccess.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User();

	public void setAD_User(org.compiere.model.I_AD_User AD_User);

    /** Column definition for AD_User_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_LdapAccess, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_AD_LdapAccess, org.compiere.model.I_AD_User>(I_AD_LdapAccess.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_LdapAccess, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_LdapAccess, Object>(I_AD_LdapAccess.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_LdapAccess, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_LdapAccess, org.compiere.model.I_AD_User>(I_AD_LdapAccess.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_LdapAccess, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_LdapAccess, Object>(I_AD_LdapAccess.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_AD_LdapAccess, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_LdapAccess, Object>(I_AD_LdapAccess.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Fehler.
	 * An Error occured in the execution
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsError (boolean IsError);

	/**
	 * Get Fehler.
	 * An Error occured in the execution
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isError();

    /** Column definition for IsError */
    public static final org.adempiere.model.ModelColumn<I_AD_LdapAccess, Object> COLUMN_IsError = new org.adempiere.model.ModelColumn<I_AD_LdapAccess, Object>(I_AD_LdapAccess.class, "IsError", null);
    /** Column name IsError */
    public static final String COLUMNNAME_IsError = "IsError";

	/**
	 * Set Interessengebiet.
	 * Interest Area or Topic
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_InterestArea_ID (int R_InterestArea_ID);

	/**
	 * Get Interessengebiet.
	 * Interest Area or Topic
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getR_InterestArea_ID();

	public org.compiere.model.I_R_InterestArea getR_InterestArea();

	public void setR_InterestArea(org.compiere.model.I_R_InterestArea R_InterestArea);

    /** Column definition for R_InterestArea_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_LdapAccess, org.compiere.model.I_R_InterestArea> COLUMN_R_InterestArea_ID = new org.adempiere.model.ModelColumn<I_AD_LdapAccess, org.compiere.model.I_R_InterestArea>(I_AD_LdapAccess.class, "R_InterestArea_ID", org.compiere.model.I_R_InterestArea.class);
    /** Column name R_InterestArea_ID */
    public static final String COLUMNNAME_R_InterestArea_ID = "R_InterestArea_ID";

	/**
	 * Set Zusammenfassung.
	 * Textual summary of this request
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSummary (java.lang.String Summary);

	/**
	 * Get Zusammenfassung.
	 * Textual summary of this request
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSummary();

    /** Column definition for Summary */
    public static final org.adempiere.model.ModelColumn<I_AD_LdapAccess, Object> COLUMN_Summary = new org.adempiere.model.ModelColumn<I_AD_LdapAccess, Object>(I_AD_LdapAccess.class, "Summary", null);
    /** Column name Summary */
    public static final String COLUMNNAME_Summary = "Summary";

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
    public static final org.adempiere.model.ModelColumn<I_AD_LdapAccess, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_LdapAccess, Object>(I_AD_LdapAccess.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_LdapAccess, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_LdapAccess, org.compiere.model.I_AD_User>(I_AD_LdapAccess.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
