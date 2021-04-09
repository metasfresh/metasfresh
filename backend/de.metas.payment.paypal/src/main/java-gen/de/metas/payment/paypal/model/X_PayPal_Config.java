// Generated Model - DO NOT CHANGE
package de.metas.payment.paypal.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for PayPal_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PayPal_Config extends org.compiere.model.PO implements I_PayPal_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1810738467L;

    /** Standard Constructor */
    public X_PayPal_Config (final Properties ctx, final int PayPal_Config_ID, @Nullable final String trxName)
    {
      super (ctx, PayPal_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_PayPal_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setPayPal_BaseUrl (final @Nullable java.lang.String PayPal_BaseUrl)
	{
		set_Value (COLUMNNAME_PayPal_BaseUrl, PayPal_BaseUrl);
	}

	@Override
	public java.lang.String getPayPal_BaseUrl() 
	{
		return get_ValueAsString(COLUMNNAME_PayPal_BaseUrl);
	}

	@Override
	public void setPayPal_ClientId (final java.lang.String PayPal_ClientId)
	{
		set_Value (COLUMNNAME_PayPal_ClientId, PayPal_ClientId);
	}

	@Override
	public java.lang.String getPayPal_ClientId() 
	{
		return get_ValueAsString(COLUMNNAME_PayPal_ClientId);
	}

	@Override
	public void setPayPal_ClientSecret (final java.lang.String PayPal_ClientSecret)
	{
		set_Value (COLUMNNAME_PayPal_ClientSecret, PayPal_ClientSecret);
	}

	@Override
	public java.lang.String getPayPal_ClientSecret() 
	{
		return get_ValueAsString(COLUMNNAME_PayPal_ClientSecret);
	}

	@Override
	public void setPayPal_Config_ID (final int PayPal_Config_ID)
	{
		if (PayPal_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PayPal_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PayPal_Config_ID, PayPal_Config_ID);
	}

	@Override
	public int getPayPal_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PayPal_Config_ID);
	}

	@Override
	public org.compiere.model.I_R_MailText getPayPal_PayerApprovalRequest_MailTemplate()
	{
		return get_ValueAsPO(COLUMNNAME_PayPal_PayerApprovalRequest_MailTemplate_ID, org.compiere.model.I_R_MailText.class);
	}

	@Override
	public void setPayPal_PayerApprovalRequest_MailTemplate(final org.compiere.model.I_R_MailText PayPal_PayerApprovalRequest_MailTemplate)
	{
		set_ValueFromPO(COLUMNNAME_PayPal_PayerApprovalRequest_MailTemplate_ID, org.compiere.model.I_R_MailText.class, PayPal_PayerApprovalRequest_MailTemplate);
	}

	@Override
	public void setPayPal_PayerApprovalRequest_MailTemplate_ID (final int PayPal_PayerApprovalRequest_MailTemplate_ID)
	{
		if (PayPal_PayerApprovalRequest_MailTemplate_ID < 1) 
			set_Value (COLUMNNAME_PayPal_PayerApprovalRequest_MailTemplate_ID, null);
		else 
			set_Value (COLUMNNAME_PayPal_PayerApprovalRequest_MailTemplate_ID, PayPal_PayerApprovalRequest_MailTemplate_ID);
	}

	@Override
	public int getPayPal_PayerApprovalRequest_MailTemplate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PayPal_PayerApprovalRequest_MailTemplate_ID);
	}

	@Override
	public void setPayPal_PaymentApprovedCallbackUrl (final @Nullable java.lang.String PayPal_PaymentApprovedCallbackUrl)
	{
		set_Value (COLUMNNAME_PayPal_PaymentApprovedCallbackUrl, PayPal_PaymentApprovedCallbackUrl);
	}

	@Override
	public java.lang.String getPayPal_PaymentApprovedCallbackUrl() 
	{
		return get_ValueAsString(COLUMNNAME_PayPal_PaymentApprovedCallbackUrl);
	}

	@Override
	public void setPayPal_Sandbox (final boolean PayPal_Sandbox)
	{
		set_Value (COLUMNNAME_PayPal_Sandbox, PayPal_Sandbox);
	}

	@Override
	public boolean isPayPal_Sandbox() 
	{
		return get_ValueAsBoolean(COLUMNNAME_PayPal_Sandbox);
	}

	@Override
	public void setPayPal_WebUrl (final @Nullable java.lang.String PayPal_WebUrl)
	{
		set_Value (COLUMNNAME_PayPal_WebUrl, PayPal_WebUrl);
	}

	@Override
	public java.lang.String getPayPal_WebUrl() 
	{
		return get_ValueAsString(COLUMNNAME_PayPal_WebUrl);
	}
}