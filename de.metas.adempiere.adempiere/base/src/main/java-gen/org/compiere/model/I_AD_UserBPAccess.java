package org.compiere.model;


/** Generated Interface for AD_UserBPAccess
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_UserBPAccess 
{

    /** TableName=AD_UserBPAccess */
    public static final String Table_Name = "AD_UserBPAccess";

    /** AD_Table_ID=813 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(2);

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
    public static final org.adempiere.model.ModelColumn<I_AD_UserBPAccess, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_UserBPAccess, org.compiere.model.I_AD_Client>(I_AD_UserBPAccess.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_UserBPAccess, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_UserBPAccess, org.compiere.model.I_AD_Org>(I_AD_UserBPAccess.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_UserBPAccess, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_AD_UserBPAccess, org.compiere.model.I_AD_User>(I_AD_UserBPAccess.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set User BP Access.
	 * User/concat access to Business Partner information and resources
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_UserBPAccess_ID (int AD_UserBPAccess_ID);

	/**
	 * Get User BP Access.
	 * User/concat access to Business Partner information and resources
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_UserBPAccess_ID();

    /** Column definition for AD_UserBPAccess_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_UserBPAccess, Object> COLUMN_AD_UserBPAccess_ID = new org.adempiere.model.ModelColumn<I_AD_UserBPAccess, Object>(I_AD_UserBPAccess.class, "AD_UserBPAccess_ID", null);
    /** Column name AD_UserBPAccess_ID */
    public static final String COLUMNNAME_AD_UserBPAccess_ID = "AD_UserBPAccess_ID";

	/**
	 * Set Access Type.
	 * Type of Access of the user/contact to Business Partner information and resources
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setBPAccessType (java.lang.String BPAccessType);

	/**
	 * Get Access Type.
	 * Type of Access of the user/contact to Business Partner information and resources
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPAccessType();

    /** Column definition for BPAccessType */
    public static final org.adempiere.model.ModelColumn<I_AD_UserBPAccess, Object> COLUMN_BPAccessType = new org.adempiere.model.ModelColumn<I_AD_UserBPAccess, Object>(I_AD_UserBPAccess.class, "BPAccessType", null);
    /** Column name BPAccessType */
    public static final String COLUMNNAME_BPAccessType = "BPAccessType";

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
    public static final org.adempiere.model.ModelColumn<I_AD_UserBPAccess, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_UserBPAccess, Object>(I_AD_UserBPAccess.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_UserBPAccess, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_UserBPAccess, org.compiere.model.I_AD_User>(I_AD_UserBPAccess.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Document BaseType.
	 * Logical type of document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocBaseType (java.lang.String DocBaseType);

	/**
	 * Get Document BaseType.
	 * Logical type of document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocBaseType();

    /** Column definition for DocBaseType */
    public static final org.adempiere.model.ModelColumn<I_AD_UserBPAccess, Object> COLUMN_DocBaseType = new org.adempiere.model.ModelColumn<I_AD_UserBPAccess, Object>(I_AD_UserBPAccess.class, "DocBaseType", null);
    /** Column name DocBaseType */
    public static final String COLUMNNAME_DocBaseType = "DocBaseType";

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
    public static final org.adempiere.model.ModelColumn<I_AD_UserBPAccess, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_UserBPAccess, Object>(I_AD_UserBPAccess.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Anfrageart.
	 * Type of request (e.g. Inquiry, Complaint, ..)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_RequestType_ID (int R_RequestType_ID);

	/**
	 * Get Anfrageart.
	 * Type of request (e.g. Inquiry, Complaint, ..)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getR_RequestType_ID();

	public org.compiere.model.I_R_RequestType getR_RequestType();

	public void setR_RequestType(org.compiere.model.I_R_RequestType R_RequestType);

    /** Column definition for R_RequestType_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_UserBPAccess, org.compiere.model.I_R_RequestType> COLUMN_R_RequestType_ID = new org.adempiere.model.ModelColumn<I_AD_UserBPAccess, org.compiere.model.I_R_RequestType>(I_AD_UserBPAccess.class, "R_RequestType_ID", org.compiere.model.I_R_RequestType.class);
    /** Column name R_RequestType_ID */
    public static final String COLUMNNAME_R_RequestType_ID = "R_RequestType_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_UserBPAccess, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_UserBPAccess, Object>(I_AD_UserBPAccess.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_UserBPAccess, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_UserBPAccess, org.compiere.model.I_AD_User>(I_AD_UserBPAccess.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
