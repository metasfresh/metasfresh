/** Generated Model - DO NOT CHANGE */
package de.metas.payment.paypal.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PayPal_Log
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PayPal_Log extends org.compiere.model.PO implements I_PayPal_Log, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1906447858L;

    /** Standard Constructor */
    public X_PayPal_Log (Properties ctx, int PayPal_Log_ID, String trxName)
    {
      super (ctx, PayPal_Log_ID, trxName);
      /** if (PayPal_Log_ID == 0)
        {
			setPayPal_Log_ID (0);
        } */
    }

    /** Load Constructor */
    public X_PayPal_Log (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	/** Set Rechnung.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Rechnung.
		@return Invoice Identifier
	  */
	@Override
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	/** Set Auftrag.
		@param C_Order_ID 
		Auftrag
	  */
	@Override
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Auftrag.
		@return Auftrag
	  */
	@Override
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Payment getC_Payment()
	{
		return get_ValueAsPO(COLUMNNAME_C_Payment_ID, org.compiere.model.I_C_Payment.class);
	}

	@Override
	public void setC_Payment(org.compiere.model.I_C_Payment C_Payment)
	{
		set_ValueFromPO(COLUMNNAME_C_Payment_ID, org.compiere.model.I_C_Payment.class, C_Payment);
	}

	/** Set Zahlung.
		@param C_Payment_ID 
		Zahlung
	  */
	@Override
	public void setC_Payment_ID (int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
	}

	/** Get Zahlung.
		@return Zahlung
	  */
	@Override
	public int getC_Payment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Payment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Payment_Reservation_Capture getC_Payment_Reservation_Capture()
	{
		return get_ValueAsPO(COLUMNNAME_C_Payment_Reservation_Capture_ID, org.compiere.model.I_C_Payment_Reservation_Capture.class);
	}

	@Override
	public void setC_Payment_Reservation_Capture(org.compiere.model.I_C_Payment_Reservation_Capture C_Payment_Reservation_Capture)
	{
		set_ValueFromPO(COLUMNNAME_C_Payment_Reservation_Capture_ID, org.compiere.model.I_C_Payment_Reservation_Capture.class, C_Payment_Reservation_Capture);
	}

	/** Set Payment Reservation Capture.
		@param C_Payment_Reservation_Capture_ID Payment Reservation Capture	  */
	@Override
	public void setC_Payment_Reservation_Capture_ID (int C_Payment_Reservation_Capture_ID)
	{
		if (C_Payment_Reservation_Capture_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_Reservation_Capture_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_Reservation_Capture_ID, Integer.valueOf(C_Payment_Reservation_Capture_ID));
	}

	/** Get Payment Reservation Capture.
		@return Payment Reservation Capture	  */
	@Override
	public int getC_Payment_Reservation_Capture_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Payment_Reservation_Capture_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Payment_Reservation getC_Payment_Reservation()
	{
		return get_ValueAsPO(COLUMNNAME_C_Payment_Reservation_ID, org.compiere.model.I_C_Payment_Reservation.class);
	}

	@Override
	public void setC_Payment_Reservation(org.compiere.model.I_C_Payment_Reservation C_Payment_Reservation)
	{
		set_ValueFromPO(COLUMNNAME_C_Payment_Reservation_ID, org.compiere.model.I_C_Payment_Reservation.class, C_Payment_Reservation);
	}

	/** Set Payment Reservation.
		@param C_Payment_Reservation_ID Payment Reservation	  */
	@Override
	public void setC_Payment_Reservation_ID (int C_Payment_Reservation_ID)
	{
		if (C_Payment_Reservation_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_Reservation_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_Reservation_ID, Integer.valueOf(C_Payment_Reservation_ID));
	}

	/** Get Payment Reservation.
		@return Payment Reservation	  */
	@Override
	public int getC_Payment_Reservation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Payment_Reservation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PayPal Log.
		@param PayPal_Log_ID PayPal Log	  */
	@Override
	public void setPayPal_Log_ID (int PayPal_Log_ID)
	{
		if (PayPal_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PayPal_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PayPal_Log_ID, Integer.valueOf(PayPal_Log_ID));
	}

	/** Get PayPal Log.
		@return PayPal Log	  */
	@Override
	public int getPayPal_Log_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PayPal_Log_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.payment.paypal.model.I_PayPal_Order getPayPal_Order()
	{
		return get_ValueAsPO(COLUMNNAME_PayPal_Order_ID, de.metas.payment.paypal.model.I_PayPal_Order.class);
	}

	@Override
	public void setPayPal_Order(de.metas.payment.paypal.model.I_PayPal_Order PayPal_Order)
	{
		set_ValueFromPO(COLUMNNAME_PayPal_Order_ID, de.metas.payment.paypal.model.I_PayPal_Order.class, PayPal_Order);
	}

	/** Set PayPal Order.
		@param PayPal_Order_ID PayPal Order	  */
	@Override
	public void setPayPal_Order_ID (int PayPal_Order_ID)
	{
		if (PayPal_Order_ID < 1) 
			set_Value (COLUMNNAME_PayPal_Order_ID, null);
		else 
			set_Value (COLUMNNAME_PayPal_Order_ID, Integer.valueOf(PayPal_Order_ID));
	}

	/** Get PayPal Order.
		@return PayPal Order	  */
	@Override
	public int getPayPal_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PayPal_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Request Body.
		@param RequestBody Request Body	  */
	@Override
	public void setRequestBody (java.lang.String RequestBody)
	{
		set_ValueNoCheck (COLUMNNAME_RequestBody, RequestBody);
	}

	/** Get Request Body.
		@return Request Body	  */
	@Override
	public java.lang.String getRequestBody () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RequestBody);
	}

	/** Set Request Headers.
		@param RequestHeaders Request Headers	  */
	@Override
	public void setRequestHeaders (java.lang.String RequestHeaders)
	{
		set_ValueNoCheck (COLUMNNAME_RequestHeaders, RequestHeaders);
	}

	/** Get Request Headers.
		@return Request Headers	  */
	@Override
	public java.lang.String getRequestHeaders () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RequestHeaders);
	}

	/** Set Request Method.
		@param RequestMethod Request Method	  */
	@Override
	public void setRequestMethod (java.lang.String RequestMethod)
	{
		set_ValueNoCheck (COLUMNNAME_RequestMethod, RequestMethod);
	}

	/** Get Request Method.
		@return Request Method	  */
	@Override
	public java.lang.String getRequestMethod () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RequestMethod);
	}

	/** Set Request Path.
		@param RequestPath Request Path	  */
	@Override
	public void setRequestPath (java.lang.String RequestPath)
	{
		set_ValueNoCheck (COLUMNNAME_RequestPath, RequestPath);
	}

	/** Get Request Path.
		@return Request Path	  */
	@Override
	public java.lang.String getRequestPath () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RequestPath);
	}

	/** Set Response Body.
		@param ResponseBody Response Body	  */
	@Override
	public void setResponseBody (java.lang.String ResponseBody)
	{
		set_ValueNoCheck (COLUMNNAME_ResponseBody, ResponseBody);
	}

	/** Get Response Body.
		@return Response Body	  */
	@Override
	public java.lang.String getResponseBody () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ResponseBody);
	}

	/** Set Antwort .
		@param ResponseCode Antwort 	  */
	@Override
	public void setResponseCode (int ResponseCode)
	{
		set_ValueNoCheck (COLUMNNAME_ResponseCode, Integer.valueOf(ResponseCode));
	}

	/** Get Antwort .
		@return Antwort 	  */
	@Override
	public int getResponseCode () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ResponseCode);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Response Headers.
		@param ResponseHeaders Response Headers	  */
	@Override
	public void setResponseHeaders (java.lang.String ResponseHeaders)
	{
		set_ValueNoCheck (COLUMNNAME_ResponseHeaders, ResponseHeaders);
	}

	/** Get Response Headers.
		@return Response Headers	  */
	@Override
	public java.lang.String getResponseHeaders () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ResponseHeaders);
	}
}