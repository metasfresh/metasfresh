package de.metas.payment.paypal.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for PayPal_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_PayPal_Config 
{

	String Table_Name = "PayPal_Config";

//	/** AD_Table_ID=541388 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_PayPal_Config, Object> COLUMN_Created = new ModelColumn<>(I_PayPal_Config.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_PayPal_Config, Object> COLUMN_Description = new ModelColumn<>(I_PayPal_Config.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_PayPal_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_PayPal_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Base URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPayPal_BaseUrl (@Nullable java.lang.String PayPal_BaseUrl);

	/**
	 * Get Base URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPayPal_BaseUrl();

	ModelColumn<I_PayPal_Config, Object> COLUMN_PayPal_BaseUrl = new ModelColumn<>(I_PayPal_Config.class, "PayPal_BaseUrl", null);
	String COLUMNNAME_PayPal_BaseUrl = "PayPal_BaseUrl";

	/**
	 * Set Client ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPayPal_ClientId (java.lang.String PayPal_ClientId);

	/**
	 * Get Client ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPayPal_ClientId();

	ModelColumn<I_PayPal_Config, Object> COLUMN_PayPal_ClientId = new ModelColumn<>(I_PayPal_Config.class, "PayPal_ClientId", null);
	String COLUMNNAME_PayPal_ClientId = "PayPal_ClientId";

	/**
	 * Set Client Secret.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPayPal_ClientSecret (java.lang.String PayPal_ClientSecret);

	/**
	 * Get Client Secret.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPayPal_ClientSecret();

	ModelColumn<I_PayPal_Config, Object> COLUMN_PayPal_ClientSecret = new ModelColumn<>(I_PayPal_Config.class, "PayPal_ClientSecret", null);
	String COLUMNNAME_PayPal_ClientSecret = "PayPal_ClientSecret";

	/**
	 * Set PayPal Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPayPal_Config_ID (int PayPal_Config_ID);

	/**
	 * Get PayPal Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPayPal_Config_ID();

	ModelColumn<I_PayPal_Config, Object> COLUMN_PayPal_Config_ID = new ModelColumn<>(I_PayPal_Config.class, "PayPal_Config_ID", null);
	String COLUMNNAME_PayPal_Config_ID = "PayPal_Config_ID";

	/**
	 * Set Payer Approval Request Mail Template.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPayPal_PayerApprovalRequest_MailTemplate_ID (int PayPal_PayerApprovalRequest_MailTemplate_ID);

	/**
	 * Get Payer Approval Request Mail Template.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPayPal_PayerApprovalRequest_MailTemplate_ID();

	org.compiere.model.I_R_MailText getPayPal_PayerApprovalRequest_MailTemplate();

	void setPayPal_PayerApprovalRequest_MailTemplate(org.compiere.model.I_R_MailText PayPal_PayerApprovalRequest_MailTemplate);

	ModelColumn<I_PayPal_Config, org.compiere.model.I_R_MailText> COLUMN_PayPal_PayerApprovalRequest_MailTemplate_ID = new ModelColumn<>(I_PayPal_Config.class, "PayPal_PayerApprovalRequest_MailTemplate_ID", org.compiere.model.I_R_MailText.class);
	String COLUMNNAME_PayPal_PayerApprovalRequest_MailTemplate_ID = "PayPal_PayerApprovalRequest_MailTemplate_ID";

	/**
	 * Set Payment Approved Callback URL.
	 * Called by PayPal when the payer approved the payment.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPayPal_PaymentApprovedCallbackUrl (@Nullable java.lang.String PayPal_PaymentApprovedCallbackUrl);

	/**
	 * Get Payment Approved Callback URL.
	 * Called by PayPal when the payer approved the payment.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPayPal_PaymentApprovedCallbackUrl();

	ModelColumn<I_PayPal_Config, Object> COLUMN_PayPal_PaymentApprovedCallbackUrl = new ModelColumn<>(I_PayPal_Config.class, "PayPal_PaymentApprovedCallbackUrl", null);
	String COLUMNNAME_PayPal_PaymentApprovedCallbackUrl = "PayPal_PaymentApprovedCallbackUrl";

	/**
	 * Set Sandbox.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPayPal_Sandbox (boolean PayPal_Sandbox);

	/**
	 * Get Sandbox.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPayPal_Sandbox();

	ModelColumn<I_PayPal_Config, Object> COLUMN_PayPal_Sandbox = new ModelColumn<>(I_PayPal_Config.class, "PayPal_Sandbox", null);
	String COLUMNNAME_PayPal_Sandbox = "PayPal_Sandbox";

	/**
	 * Set Web URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPayPal_WebUrl (@Nullable java.lang.String PayPal_WebUrl);

	/**
	 * Get Web URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPayPal_WebUrl();

	ModelColumn<I_PayPal_Config, Object> COLUMN_PayPal_WebUrl = new ModelColumn<>(I_PayPal_Config.class, "PayPal_WebUrl", null);
	String COLUMNNAME_PayPal_WebUrl = "PayPal_WebUrl";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PayPal_Config, Object> COLUMN_Updated = new ModelColumn<>(I_PayPal_Config.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
