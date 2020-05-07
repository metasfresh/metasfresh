package de.metas.rfq.model;


/** Generated Interface for C_RfQ_Topic
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_RfQ_Topic 
{

    /** TableName=C_RfQ_Topic */
    public static final String Table_Name = "C_RfQ_Topic";

    /** AD_Table_ID=671 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_AD_Client>(I_C_RfQ_Topic.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_AD_Org>(I_C_RfQ_Topic.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ausschreibungs-Thema.
	 * Topic for Request for Quotations
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQ_Topic_ID (int C_RfQ_Topic_ID);

	/**
	 * Get Ausschreibungs-Thema.
	 * Topic for Request for Quotations
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQ_Topic_ID();

    /** Column definition for C_RfQ_Topic_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_Topic, Object> COLUMN_C_RfQ_Topic_ID = new org.adempiere.model.ModelColumn<I_C_RfQ_Topic, Object>(I_C_RfQ_Topic.class, "C_RfQ_Topic_ID", null);
    /** Column name C_RfQ_Topic_ID */
    public static final String COLUMNNAME_C_RfQ_Topic_ID = "C_RfQ_Topic_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_Topic, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_RfQ_Topic, Object>(I_C_RfQ_Topic.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_AD_User>(I_C_RfQ_Topic.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_Topic, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_RfQ_Topic, Object>(I_C_RfQ_Topic.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_Topic, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_RfQ_Topic, Object>(I_C_RfQ_Topic.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Selbstbedienung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSelfService (boolean IsSelfService);

	/**
	 * Get Selbstbedienung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSelfService();

    /** Column definition for IsSelfService */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_Topic, Object> COLUMN_IsSelfService = new org.adempiere.model.ModelColumn<I_C_RfQ_Topic, Object>(I_C_RfQ_Topic.class, "IsSelfService", null);
    /** Column name IsSelfService */
    public static final String COLUMNNAME_IsSelfService = "IsSelfService";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_Topic, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_RfQ_Topic, Object>(I_C_RfQ_Topic.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set RfQ Invitation mail text.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRfQ_Invitation_MailText_ID (int RfQ_Invitation_MailText_ID);

	/**
	 * Get RfQ Invitation mail text.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRfQ_Invitation_MailText_ID();

	public org.compiere.model.I_R_MailText getRfQ_Invitation_MailText();

	public void setRfQ_Invitation_MailText(org.compiere.model.I_R_MailText RfQ_Invitation_MailText);

    /** Column definition for RfQ_Invitation_MailText_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_R_MailText> COLUMN_RfQ_Invitation_MailText_ID = new org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_R_MailText>(I_C_RfQ_Topic.class, "RfQ_Invitation_MailText_ID", org.compiere.model.I_R_MailText.class);
    /** Column name RfQ_Invitation_MailText_ID */
    public static final String COLUMNNAME_RfQ_Invitation_MailText_ID = "RfQ_Invitation_MailText_ID";

	/**
	 * Set RfQ Invitation Druck - Format.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRfQ_Invitation_PrintFormat_ID (int RfQ_Invitation_PrintFormat_ID);

	/**
	 * Get RfQ Invitation Druck - Format.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRfQ_Invitation_PrintFormat_ID();

	public org.compiere.model.I_AD_PrintFormat getRfQ_Invitation_PrintFormat();

	public void setRfQ_Invitation_PrintFormat(org.compiere.model.I_AD_PrintFormat RfQ_Invitation_PrintFormat);

    /** Column definition for RfQ_Invitation_PrintFormat_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_AD_PrintFormat> COLUMN_RfQ_Invitation_PrintFormat_ID = new org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_AD_PrintFormat>(I_C_RfQ_Topic.class, "RfQ_Invitation_PrintFormat_ID", org.compiere.model.I_AD_PrintFormat.class);
    /** Column name RfQ_Invitation_PrintFormat_ID */
    public static final String COLUMNNAME_RfQ_Invitation_PrintFormat_ID = "RfQ_Invitation_PrintFormat_ID";

	/**
	 * Set RfQ without Qty Invitation mail text.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRfQ_InvitationWithoutQty_MailText_ID (int RfQ_InvitationWithoutQty_MailText_ID);

	/**
	 * Get RfQ without Qty Invitation mail text.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRfQ_InvitationWithoutQty_MailText_ID();

	public org.compiere.model.I_R_MailText getRfQ_InvitationWithoutQty_MailText();

	public void setRfQ_InvitationWithoutQty_MailText(org.compiere.model.I_R_MailText RfQ_InvitationWithoutQty_MailText);

    /** Column definition for RfQ_InvitationWithoutQty_MailText_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_R_MailText> COLUMN_RfQ_InvitationWithoutQty_MailText_ID = new org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_R_MailText>(I_C_RfQ_Topic.class, "RfQ_InvitationWithoutQty_MailText_ID", org.compiere.model.I_R_MailText.class);
    /** Column name RfQ_InvitationWithoutQty_MailText_ID */
    public static final String COLUMNNAME_RfQ_InvitationWithoutQty_MailText_ID = "RfQ_InvitationWithoutQty_MailText_ID";

	/**
	 * Set RfQ without Qty Invitation Druck - Format.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRfQ_InvitationWithoutQty_PrintFormat_ID (int RfQ_InvitationWithoutQty_PrintFormat_ID);

	/**
	 * Get RfQ without Qty Invitation Druck - Format.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRfQ_InvitationWithoutQty_PrintFormat_ID();

	public org.compiere.model.I_AD_PrintFormat getRfQ_InvitationWithoutQty_PrintFormat();

	public void setRfQ_InvitationWithoutQty_PrintFormat(org.compiere.model.I_AD_PrintFormat RfQ_InvitationWithoutQty_PrintFormat);

    /** Column definition for RfQ_InvitationWithoutQty_PrintFormat_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_AD_PrintFormat> COLUMN_RfQ_InvitationWithoutQty_PrintFormat_ID = new org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_AD_PrintFormat>(I_C_RfQ_Topic.class, "RfQ_InvitationWithoutQty_PrintFormat_ID", org.compiere.model.I_AD_PrintFormat.class);
    /** Column name RfQ_InvitationWithoutQty_PrintFormat_ID */
    public static final String COLUMNNAME_RfQ_InvitationWithoutQty_PrintFormat_ID = "RfQ_InvitationWithoutQty_PrintFormat_ID";

	/**
	 * Set RfQ lost mail text.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRfQ_Lost_MailText_ID (int RfQ_Lost_MailText_ID);

	/**
	 * Get RfQ lost mail text.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRfQ_Lost_MailText_ID();

	public org.compiere.model.I_R_MailText getRfQ_Lost_MailText();

	public void setRfQ_Lost_MailText(org.compiere.model.I_R_MailText RfQ_Lost_MailText);

    /** Column definition for RfQ_Lost_MailText_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_R_MailText> COLUMN_RfQ_Lost_MailText_ID = new org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_R_MailText>(I_C_RfQ_Topic.class, "RfQ_Lost_MailText_ID", org.compiere.model.I_R_MailText.class);
    /** Column name RfQ_Lost_MailText_ID */
    public static final String COLUMNNAME_RfQ_Lost_MailText_ID = "RfQ_Lost_MailText_ID";

	/**
	 * Set RfQ Lost Druck - Format.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRfQ_Lost_PrintFormat_ID (int RfQ_Lost_PrintFormat_ID);

	/**
	 * Get RfQ Lost Druck - Format.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRfQ_Lost_PrintFormat_ID();

	public org.compiere.model.I_AD_PrintFormat getRfQ_Lost_PrintFormat();

	public void setRfQ_Lost_PrintFormat(org.compiere.model.I_AD_PrintFormat RfQ_Lost_PrintFormat);

    /** Column definition for RfQ_Lost_PrintFormat_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_AD_PrintFormat> COLUMN_RfQ_Lost_PrintFormat_ID = new org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_AD_PrintFormat>(I_C_RfQ_Topic.class, "RfQ_Lost_PrintFormat_ID", org.compiere.model.I_AD_PrintFormat.class);
    /** Column name RfQ_Lost_PrintFormat_ID */
    public static final String COLUMNNAME_RfQ_Lost_PrintFormat_ID = "RfQ_Lost_PrintFormat_ID";

	/**
	 * Set RfQ win mail text.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRfQ_Win_MailText_ID (int RfQ_Win_MailText_ID);

	/**
	 * Get RfQ win mail text.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRfQ_Win_MailText_ID();

	public org.compiere.model.I_R_MailText getRfQ_Win_MailText();

	public void setRfQ_Win_MailText(org.compiere.model.I_R_MailText RfQ_Win_MailText);

    /** Column definition for RfQ_Win_MailText_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_R_MailText> COLUMN_RfQ_Win_MailText_ID = new org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_R_MailText>(I_C_RfQ_Topic.class, "RfQ_Win_MailText_ID", org.compiere.model.I_R_MailText.class);
    /** Column name RfQ_Win_MailText_ID */
    public static final String COLUMNNAME_RfQ_Win_MailText_ID = "RfQ_Win_MailText_ID";

	/**
	 * Set RfQ Won Druck - Format.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRfQ_Win_PrintFormat_ID (int RfQ_Win_PrintFormat_ID);

	/**
	 * Get RfQ Won Druck - Format.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRfQ_Win_PrintFormat_ID();

	public org.compiere.model.I_AD_PrintFormat getRfQ_Win_PrintFormat();

	public void setRfQ_Win_PrintFormat(org.compiere.model.I_AD_PrintFormat RfQ_Win_PrintFormat);

    /** Column definition for RfQ_Win_PrintFormat_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_AD_PrintFormat> COLUMN_RfQ_Win_PrintFormat_ID = new org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_AD_PrintFormat>(I_C_RfQ_Topic.class, "RfQ_Win_PrintFormat_ID", org.compiere.model.I_AD_PrintFormat.class);
    /** Column name RfQ_Win_PrintFormat_ID */
    public static final String COLUMNNAME_RfQ_Win_PrintFormat_ID = "RfQ_Win_PrintFormat_ID";

	/**
	 * Set Ausschreibung Art.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRfQType (java.lang.String RfQType);

	/**
	 * Get Ausschreibung Art.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRfQType();

    /** Column definition for RfQType */
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_Topic, Object> COLUMN_RfQType = new org.adempiere.model.ModelColumn<I_C_RfQ_Topic, Object>(I_C_RfQ_Topic.class, "RfQType", null);
    /** Column name RfQType */
    public static final String COLUMNNAME_RfQType = "RfQType";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_Topic, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_RfQ_Topic, Object>(I_C_RfQ_Topic.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_RfQ_Topic, org.compiere.model.I_AD_User>(I_C_RfQ_Topic.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
