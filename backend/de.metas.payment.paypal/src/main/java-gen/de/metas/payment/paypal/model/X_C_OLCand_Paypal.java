// Generated Model - DO NOT CHANGE
package de.metas.payment.paypal.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_OLCand_Paypal
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_OLCand_Paypal extends org.compiere.model.PO implements I_C_OLCand_Paypal, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 865508190L;

    /** Standard Constructor */
    public X_C_OLCand_Paypal (final Properties ctx, final int C_OLCand_Paypal_ID, @Nullable final String trxName)
    {
      super (ctx, C_OLCand_Paypal_ID, trxName);
    }

    /** Load Constructor */
    public X_C_OLCand_Paypal (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_OLCand_ID (final int C_OLCand_ID)
	{
		if (C_OLCand_ID < 1) 
			set_Value (COLUMNNAME_C_OLCand_ID, null);
		else 
			set_Value (COLUMNNAME_C_OLCand_ID, C_OLCand_ID);
	}

	@Override
	public int getC_OLCand_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OLCand_ID);
	}

	@Override
	public void setC_OLCand_Paypal_ID (final int C_OLCand_Paypal_ID)
	{
		if (C_OLCand_Paypal_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OLCand_Paypal_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OLCand_Paypal_ID, C_OLCand_Paypal_ID);
	}

	@Override
	public int getC_OLCand_Paypal_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OLCand_Paypal_ID);
	}

	@Override
	public void setPayPal_Token (final @Nullable java.lang.String PayPal_Token)
	{
		set_Value (COLUMNNAME_PayPal_Token, PayPal_Token);
	}

	@Override
	public java.lang.String getPayPal_Token() 
	{
		return get_ValueAsString(COLUMNNAME_PayPal_Token);
	}

	@Override
	public void setPayPal_Transaction_ID (final @Nullable java.lang.String PayPal_Transaction_ID)
	{
		set_Value (COLUMNNAME_PayPal_Transaction_ID, PayPal_Transaction_ID);
	}

	@Override
	public java.lang.String getPayPal_Transaction_ID() 
	{
		return get_ValueAsString(COLUMNNAME_PayPal_Transaction_ID);
	}
}