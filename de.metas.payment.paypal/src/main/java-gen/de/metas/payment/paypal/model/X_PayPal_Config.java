/** Generated Model - DO NOT CHANGE */
package de.metas.payment.paypal.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PayPal_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PayPal_Config extends org.compiere.model.PO implements I_PayPal_Config, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1280854652L;

    /** Standard Constructor */
    public X_PayPal_Config (Properties ctx, int PayPal_Config_ID, String trxName)
    {
      super (ctx, PayPal_Config_ID, trxName);
      /** if (PayPal_Config_ID == 0)
        {
			setPayPal_ClientId (null);
			setPayPal_ClientSecret (null);
			setPayPal_Config_ID (0);
			setPayPal_PayerApprovalRequest_MailTemplate_ID (0);
			setPayPal_PaymentApprovedCallbackUrl (null);
			setPayPal_Sandbox (false); // N
        } */
    }

    /** Load Constructor */
    public X_PayPal_Config (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Base URL.
		@param PayPal_BaseUrl Base URL	  */
	@Override
	public void setPayPal_BaseUrl (java.lang.String PayPal_BaseUrl)
	{
		set_Value (COLUMNNAME_PayPal_BaseUrl, PayPal_BaseUrl);
	}

	/** Get Base URL.
		@return Base URL	  */
	@Override
	public java.lang.String getPayPal_BaseUrl () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PayPal_BaseUrl);
	}

	/** Set Client ID.
		@param PayPal_ClientId Client ID	  */
	@Override
	public void setPayPal_ClientId (java.lang.String PayPal_ClientId)
	{
		set_Value (COLUMNNAME_PayPal_ClientId, PayPal_ClientId);
	}

	/** Get Client ID.
		@return Client ID	  */
	@Override
	public java.lang.String getPayPal_ClientId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PayPal_ClientId);
	}

	/** Set Client Secret.
		@param PayPal_ClientSecret Client Secret	  */
	@Override
	public void setPayPal_ClientSecret (java.lang.String PayPal_ClientSecret)
	{
		set_Value (COLUMNNAME_PayPal_ClientSecret, PayPal_ClientSecret);
	}

	/** Get Client Secret.
		@return Client Secret	  */
	@Override
	public java.lang.String getPayPal_ClientSecret () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PayPal_ClientSecret);
	}

	/** Set PayPal Config.
		@param PayPal_Config_ID PayPal Config	  */
	@Override
	public void setPayPal_Config_ID (int PayPal_Config_ID)
	{
		if (PayPal_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PayPal_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PayPal_Config_ID, Integer.valueOf(PayPal_Config_ID));
	}

	/** Get PayPal Config.
		@return PayPal Config	  */
	@Override
	public int getPayPal_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PayPal_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_R_MailText getPayPal_PayerApprovalRequest_MailTemplate()
	{
		return get_ValueAsPO(COLUMNNAME_PayPal_PayerApprovalRequest_MailTemplate_ID, org.compiere.model.I_R_MailText.class);
	}

	@Override
	public void setPayPal_PayerApprovalRequest_MailTemplate(org.compiere.model.I_R_MailText PayPal_PayerApprovalRequest_MailTemplate)
	{
		set_ValueFromPO(COLUMNNAME_PayPal_PayerApprovalRequest_MailTemplate_ID, org.compiere.model.I_R_MailText.class, PayPal_PayerApprovalRequest_MailTemplate);
	}

	/** Set Payer Approval Request Mail Template.
		@param PayPal_PayerApprovalRequest_MailTemplate_ID Payer Approval Request Mail Template	  */
	@Override
	public void setPayPal_PayerApprovalRequest_MailTemplate_ID (int PayPal_PayerApprovalRequest_MailTemplate_ID)
	{
		if (PayPal_PayerApprovalRequest_MailTemplate_ID < 1) 
			set_Value (COLUMNNAME_PayPal_PayerApprovalRequest_MailTemplate_ID, null);
		else 
			set_Value (COLUMNNAME_PayPal_PayerApprovalRequest_MailTemplate_ID, Integer.valueOf(PayPal_PayerApprovalRequest_MailTemplate_ID));
	}

	/** Get Payer Approval Request Mail Template.
		@return Payer Approval Request Mail Template	  */
	@Override
	public int getPayPal_PayerApprovalRequest_MailTemplate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PayPal_PayerApprovalRequest_MailTemplate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Payment Approved Callback URL.
		@param PayPal_PaymentApprovedCallbackUrl 
		Called by PayPal when the payer approved the payment.
	  */
	@Override
	public void setPayPal_PaymentApprovedCallbackUrl (java.lang.String PayPal_PaymentApprovedCallbackUrl)
	{
		set_Value (COLUMNNAME_PayPal_PaymentApprovedCallbackUrl, PayPal_PaymentApprovedCallbackUrl);
	}

	/** Get Payment Approved Callback URL.
		@return Called by PayPal when the payer approved the payment.
	  */
	@Override
	public java.lang.String getPayPal_PaymentApprovedCallbackUrl () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PayPal_PaymentApprovedCallbackUrl);
	}

	/** Set Sandbox.
		@param PayPal_Sandbox Sandbox	  */
	@Override
	public void setPayPal_Sandbox (boolean PayPal_Sandbox)
	{
		set_Value (COLUMNNAME_PayPal_Sandbox, Boolean.valueOf(PayPal_Sandbox));
	}

	/** Get Sandbox.
		@return Sandbox	  */
	@Override
	public boolean isPayPal_Sandbox () 
	{
		Object oo = get_Value(COLUMNNAME_PayPal_Sandbox);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Web URL.
		@param PayPal_WebUrl Web URL	  */
	@Override
	public void setPayPal_WebUrl (java.lang.String PayPal_WebUrl)
	{
		set_Value (COLUMNNAME_PayPal_WebUrl, PayPal_WebUrl);
	}

	/** Get Web URL.
		@return Web URL	  */
	@Override
	public java.lang.String getPayPal_WebUrl () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PayPal_WebUrl);
	}
}