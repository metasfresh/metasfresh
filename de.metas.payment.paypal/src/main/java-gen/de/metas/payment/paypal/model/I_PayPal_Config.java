package de.metas.payment.paypal.model;


/** Generated Interface for PayPal_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_PayPal_Config 
{

    /** TableName=PayPal_Config */
    public static final String Table_Name = "PayPal_Config";

    /** AD_Table_ID=541388 */
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

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Config, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_PayPal_Config, org.compiere.model.I_AD_Client>(I_PayPal_Config.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Config, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_PayPal_Config, org.compiere.model.I_AD_Org>(I_PayPal_Config.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PayPal_Config, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_PayPal_Config, Object>(I_PayPal_Config.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_PayPal_Config, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_PayPal_Config, org.compiere.model.I_AD_User>(I_PayPal_Config.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_PayPal_Config, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_PayPal_Config, Object>(I_PayPal_Config.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_PayPal_Config, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_PayPal_Config, Object>(I_PayPal_Config.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Base URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPayPal_BaseUrl (java.lang.String PayPal_BaseUrl);

	/**
	 * Get Base URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPayPal_BaseUrl();

    /** Column definition for PayPal_BaseUrl */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Config, Object> COLUMN_PayPal_BaseUrl = new org.adempiere.model.ModelColumn<I_PayPal_Config, Object>(I_PayPal_Config.class, "PayPal_BaseUrl", null);
    /** Column name PayPal_BaseUrl */
    public static final String COLUMNNAME_PayPal_BaseUrl = "PayPal_BaseUrl";

	/**
	 * Set Client ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPayPal_ClientId (java.lang.String PayPal_ClientId);

	/**
	 * Get Client ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPayPal_ClientId();

    /** Column definition for PayPal_ClientId */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Config, Object> COLUMN_PayPal_ClientId = new org.adempiere.model.ModelColumn<I_PayPal_Config, Object>(I_PayPal_Config.class, "PayPal_ClientId", null);
    /** Column name PayPal_ClientId */
    public static final String COLUMNNAME_PayPal_ClientId = "PayPal_ClientId";

	/**
	 * Set Client Secret.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPayPal_ClientSecret (java.lang.String PayPal_ClientSecret);

	/**
	 * Get Client Secret.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPayPal_ClientSecret();

    /** Column definition for PayPal_ClientSecret */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Config, Object> COLUMN_PayPal_ClientSecret = new org.adempiere.model.ModelColumn<I_PayPal_Config, Object>(I_PayPal_Config.class, "PayPal_ClientSecret", null);
    /** Column name PayPal_ClientSecret */
    public static final String COLUMNNAME_PayPal_ClientSecret = "PayPal_ClientSecret";

	/**
	 * Set PayPal Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPayPal_Config_ID (int PayPal_Config_ID);

	/**
	 * Get PayPal Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPayPal_Config_ID();

    /** Column definition for PayPal_Config_ID */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Config, Object> COLUMN_PayPal_Config_ID = new org.adempiere.model.ModelColumn<I_PayPal_Config, Object>(I_PayPal_Config.class, "PayPal_Config_ID", null);
    /** Column name PayPal_Config_ID */
    public static final String COLUMNNAME_PayPal_Config_ID = "PayPal_Config_ID";

	/**
	 * Set Payer Approval Request Mail Template.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPayPal_PayerApprovalRequest_MailTemplate_ID (int PayPal_PayerApprovalRequest_MailTemplate_ID);

	/**
	 * Get Payer Approval Request Mail Template.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPayPal_PayerApprovalRequest_MailTemplate_ID();

	public org.compiere.model.I_R_MailText getPayPal_PayerApprovalRequest_MailTemplate();

	public void setPayPal_PayerApprovalRequest_MailTemplate(org.compiere.model.I_R_MailText PayPal_PayerApprovalRequest_MailTemplate);

    /** Column definition for PayPal_PayerApprovalRequest_MailTemplate_ID */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Config, org.compiere.model.I_R_MailText> COLUMN_PayPal_PayerApprovalRequest_MailTemplate_ID = new org.adempiere.model.ModelColumn<I_PayPal_Config, org.compiere.model.I_R_MailText>(I_PayPal_Config.class, "PayPal_PayerApprovalRequest_MailTemplate_ID", org.compiere.model.I_R_MailText.class);
    /** Column name PayPal_PayerApprovalRequest_MailTemplate_ID */
    public static final String COLUMNNAME_PayPal_PayerApprovalRequest_MailTemplate_ID = "PayPal_PayerApprovalRequest_MailTemplate_ID";

	/**
	 * Set Payment Approved Callback URL.
	 * Called by PayPal when the payer approved the payment.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPayPal_PaymentApprovedCallbackUrl (java.lang.String PayPal_PaymentApprovedCallbackUrl);

	/**
	 * Get Payment Approved Callback URL.
	 * Called by PayPal when the payer approved the payment.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPayPal_PaymentApprovedCallbackUrl();

    /** Column definition for PayPal_PaymentApprovedCallbackUrl */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Config, Object> COLUMN_PayPal_PaymentApprovedCallbackUrl = new org.adempiere.model.ModelColumn<I_PayPal_Config, Object>(I_PayPal_Config.class, "PayPal_PaymentApprovedCallbackUrl", null);
    /** Column name PayPal_PaymentApprovedCallbackUrl */
    public static final String COLUMNNAME_PayPal_PaymentApprovedCallbackUrl = "PayPal_PaymentApprovedCallbackUrl";

	/**
	 * Set Sandbox.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPayPal_Sandbox (boolean PayPal_Sandbox);

	/**
	 * Get Sandbox.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPayPal_Sandbox();

    /** Column definition for PayPal_Sandbox */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Config, Object> COLUMN_PayPal_Sandbox = new org.adempiere.model.ModelColumn<I_PayPal_Config, Object>(I_PayPal_Config.class, "PayPal_Sandbox", null);
    /** Column name PayPal_Sandbox */
    public static final String COLUMNNAME_PayPal_Sandbox = "PayPal_Sandbox";

	/**
	 * Set Web URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPayPal_WebUrl (java.lang.String PayPal_WebUrl);

	/**
	 * Get Web URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPayPal_WebUrl();

    /** Column definition for PayPal_WebUrl */
    public static final org.adempiere.model.ModelColumn<I_PayPal_Config, Object> COLUMN_PayPal_WebUrl = new org.adempiere.model.ModelColumn<I_PayPal_Config, Object>(I_PayPal_Config.class, "PayPal_WebUrl", null);
    /** Column name PayPal_WebUrl */
    public static final String COLUMNNAME_PayPal_WebUrl = "PayPal_WebUrl";

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
    public static final org.adempiere.model.ModelColumn<I_PayPal_Config, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_PayPal_Config, Object>(I_PayPal_Config.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_PayPal_Config, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_PayPal_Config, org.compiere.model.I_AD_User>(I_PayPal_Config.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
