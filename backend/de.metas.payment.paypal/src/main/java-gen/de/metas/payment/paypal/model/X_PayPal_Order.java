// Generated Model - DO NOT CHANGE
package de.metas.payment.paypal.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for PayPal_Order
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PayPal_Order extends org.compiere.model.PO implements I_PayPal_Order, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2023562986L;

    /** Standard Constructor */
    public X_PayPal_Order (final Properties ctx, final int PayPal_Order_ID, @Nullable final String trxName)
    {
      super (ctx, PayPal_Order_ID, trxName);
    }

    /** Load Constructor */
    public X_PayPal_Order (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_Payment_Reservation getC_Payment_Reservation()
	{
		return get_ValueAsPO(COLUMNNAME_C_Payment_Reservation_ID, org.compiere.model.I_C_Payment_Reservation.class);
	}

	@Override
	public void setC_Payment_Reservation(final org.compiere.model.I_C_Payment_Reservation C_Payment_Reservation)
	{
		set_ValueFromPO(COLUMNNAME_C_Payment_Reservation_ID, org.compiere.model.I_C_Payment_Reservation.class, C_Payment_Reservation);
	}

	@Override
	public void setC_Payment_Reservation_ID (final int C_Payment_Reservation_ID)
	{
		if (C_Payment_Reservation_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_Reservation_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_Reservation_ID, C_Payment_Reservation_ID);
	}

	@Override
	public int getC_Payment_Reservation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Payment_Reservation_ID);
	}

	@Override
	public void setExternalId (final @Nullable java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public java.lang.String getExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalId);
	}

	@Override
	public void setPayPal_AuthorizationId (final @Nullable java.lang.String PayPal_AuthorizationId)
	{
		set_Value (COLUMNNAME_PayPal_AuthorizationId, PayPal_AuthorizationId);
	}

	@Override
	public java.lang.String getPayPal_AuthorizationId() 
	{
		return get_ValueAsString(COLUMNNAME_PayPal_AuthorizationId);
	}

	@Override
	public void setPayPal_Order_ID (final int PayPal_Order_ID)
	{
		if (PayPal_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PayPal_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PayPal_Order_ID, PayPal_Order_ID);
	}

	@Override
	public int getPayPal_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PayPal_Order_ID);
	}

	@Override
	public void setPayPal_OrderJSON (final @Nullable java.lang.String PayPal_OrderJSON)
	{
		set_Value (COLUMNNAME_PayPal_OrderJSON, PayPal_OrderJSON);
	}

	@Override
	public java.lang.String getPayPal_OrderJSON() 
	{
		return get_ValueAsString(COLUMNNAME_PayPal_OrderJSON);
	}

	@Override
	public void setPayPal_PayerApproveUrl (final @Nullable java.lang.String PayPal_PayerApproveUrl)
	{
		set_Value (COLUMNNAME_PayPal_PayerApproveUrl, PayPal_PayerApproveUrl);
	}

	@Override
	public java.lang.String getPayPal_PayerApproveUrl() 
	{
		return get_ValueAsString(COLUMNNAME_PayPal_PayerApproveUrl);
	}

	@Override
	public void setStatus (final java.lang.String Status)
	{
		set_Value (COLUMNNAME_Status, Status);
	}

	@Override
	public java.lang.String getStatus() 
	{
		return get_ValueAsString(COLUMNNAME_Status);
	}
}