// Generated Model - DO NOT CHANGE
package de.metas.payment.paypal.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for PayPal_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PayPal_Log extends org.compiere.model.PO implements I_PayPal_Log, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2067691795L;

    /** Standard Constructor */
    public X_PayPal_Log (final Properties ctx, final int PayPal_Log_ID, @Nullable final String trxName)
    {
      super (ctx, PayPal_Log_ID, trxName);
    }

    /** Load Constructor */
    public X_PayPal_Log (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_Invoice getC_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(final org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	@Override
	public void setC_Invoice_ID (final int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, C_Invoice_ID);
	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(final org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID (final int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setC_Payment_ID (final int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_ID, C_Payment_ID);
	}

	@Override
	public int getC_Payment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Payment_ID);
	}

	@Override
	public org.compiere.model.I_C_Payment_Reservation_Capture getC_Payment_Reservation_Capture()
	{
		return get_ValueAsPO(COLUMNNAME_C_Payment_Reservation_Capture_ID, org.compiere.model.I_C_Payment_Reservation_Capture.class);
	}

	@Override
	public void setC_Payment_Reservation_Capture(final org.compiere.model.I_C_Payment_Reservation_Capture C_Payment_Reservation_Capture)
	{
		set_ValueFromPO(COLUMNNAME_C_Payment_Reservation_Capture_ID, org.compiere.model.I_C_Payment_Reservation_Capture.class, C_Payment_Reservation_Capture);
	}

	@Override
	public void setC_Payment_Reservation_Capture_ID (final int C_Payment_Reservation_Capture_ID)
	{
		if (C_Payment_Reservation_Capture_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_Reservation_Capture_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_Reservation_Capture_ID, C_Payment_Reservation_Capture_ID);
	}

	@Override
	public int getC_Payment_Reservation_Capture_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Payment_Reservation_Capture_ID);
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
	public void setPayPal_Log_ID (final int PayPal_Log_ID)
	{
		if (PayPal_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PayPal_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PayPal_Log_ID, PayPal_Log_ID);
	}

	@Override
	public int getPayPal_Log_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PayPal_Log_ID);
	}

	@Override
	public de.metas.payment.paypal.model.I_PayPal_Order getPayPal_Order()
	{
		return get_ValueAsPO(COLUMNNAME_PayPal_Order_ID, de.metas.payment.paypal.model.I_PayPal_Order.class);
	}

	@Override
	public void setPayPal_Order(final de.metas.payment.paypal.model.I_PayPal_Order PayPal_Order)
	{
		set_ValueFromPO(COLUMNNAME_PayPal_Order_ID, de.metas.payment.paypal.model.I_PayPal_Order.class, PayPal_Order);
	}

	@Override
	public void setPayPal_Order_ID (final int PayPal_Order_ID)
	{
		if (PayPal_Order_ID < 1) 
			set_Value (COLUMNNAME_PayPal_Order_ID, null);
		else 
			set_Value (COLUMNNAME_PayPal_Order_ID, PayPal_Order_ID);
	}

	@Override
	public int getPayPal_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PayPal_Order_ID);
	}

	@Override
	public void setRequestBody (final @Nullable java.lang.String RequestBody)
	{
		set_ValueNoCheck (COLUMNNAME_RequestBody, RequestBody);
	}

	@Override
	public java.lang.String getRequestBody() 
	{
		return get_ValueAsString(COLUMNNAME_RequestBody);
	}

	@Override
	public void setRequestHeaders (final @Nullable java.lang.String RequestHeaders)
	{
		set_ValueNoCheck (COLUMNNAME_RequestHeaders, RequestHeaders);
	}

	@Override
	public java.lang.String getRequestHeaders() 
	{
		return get_ValueAsString(COLUMNNAME_RequestHeaders);
	}

	@Override
	public void setRequestMethod (final @Nullable java.lang.String RequestMethod)
	{
		set_ValueNoCheck (COLUMNNAME_RequestMethod, RequestMethod);
	}

	@Override
	public java.lang.String getRequestMethod() 
	{
		return get_ValueAsString(COLUMNNAME_RequestMethod);
	}

	@Override
	public void setRequestPath (final @Nullable java.lang.String RequestPath)
	{
		set_ValueNoCheck (COLUMNNAME_RequestPath, RequestPath);
	}

	@Override
	public java.lang.String getRequestPath() 
	{
		return get_ValueAsString(COLUMNNAME_RequestPath);
	}

	@Override
	public void setResponseBody (final @Nullable java.lang.String ResponseBody)
	{
		set_ValueNoCheck (COLUMNNAME_ResponseBody, ResponseBody);
	}

	@Override
	public java.lang.String getResponseBody() 
	{
		return get_ValueAsString(COLUMNNAME_ResponseBody);
	}

	@Override
	public void setResponseCode (final int ResponseCode)
	{
		set_ValueNoCheck (COLUMNNAME_ResponseCode, ResponseCode);
	}

	@Override
	public int getResponseCode() 
	{
		return get_ValueAsInt(COLUMNNAME_ResponseCode);
	}

	@Override
	public void setResponseHeaders (final @Nullable java.lang.String ResponseHeaders)
	{
		set_ValueNoCheck (COLUMNNAME_ResponseHeaders, ResponseHeaders);
	}

	@Override
	public java.lang.String getResponseHeaders() 
	{
		return get_ValueAsString(COLUMNNAME_ResponseHeaders);
	}
}