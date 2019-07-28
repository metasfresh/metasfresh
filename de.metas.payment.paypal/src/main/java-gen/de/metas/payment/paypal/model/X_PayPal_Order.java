/** Generated Model - DO NOT CHANGE */
package de.metas.payment.paypal.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PayPal_Order
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PayPal_Order extends org.compiere.model.PO implements I_PayPal_Order, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1445308455L;

    /** Standard Constructor */
    public X_PayPal_Order (Properties ctx, int PayPal_Order_ID, String trxName)
    {
      super (ctx, PayPal_Order_ID, trxName);
      /** if (PayPal_Order_ID == 0)
        {
			setC_Payment_Reservation_ID (0);
			setPayPal_Order_ID (0);
			setStatus (null);
        } */
    }

    /** Load Constructor */
    public X_PayPal_Order (Properties ctx, ResultSet rs, String trxName)
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

	/** Set External ID.
		@param ExternalId External ID	  */
	@Override
	public void setExternalId (java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	/** Get External ID.
		@return External ID	  */
	@Override
	public java.lang.String getExternalId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalId);
	}

	/** Set PayPal AuthorizationId.
		@param PayPal_AuthorizationId PayPal AuthorizationId	  */
	@Override
	public void setPayPal_AuthorizationId (java.lang.String PayPal_AuthorizationId)
	{
		set_Value (COLUMNNAME_PayPal_AuthorizationId, PayPal_AuthorizationId);
	}

	/** Get PayPal AuthorizationId.
		@return PayPal AuthorizationId	  */
	@Override
	public java.lang.String getPayPal_AuthorizationId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PayPal_AuthorizationId);
	}

	/** Set PayPal Order.
		@param PayPal_Order_ID PayPal Order	  */
	@Override
	public void setPayPal_Order_ID (int PayPal_Order_ID)
	{
		if (PayPal_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PayPal_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PayPal_Order_ID, Integer.valueOf(PayPal_Order_ID));
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

	/** Set Order JSON.
		@param PayPal_OrderJSON Order JSON	  */
	@Override
	public void setPayPal_OrderJSON (java.lang.String PayPal_OrderJSON)
	{
		set_Value (COLUMNNAME_PayPal_OrderJSON, PayPal_OrderJSON);
	}

	/** Get Order JSON.
		@return Order JSON	  */
	@Override
	public java.lang.String getPayPal_OrderJSON () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PayPal_OrderJSON);
	}

	/** Set Payer Approve URL.
		@param PayPal_PayerApproveUrl Payer Approve URL	  */
	@Override
	public void setPayPal_PayerApproveUrl (java.lang.String PayPal_PayerApproveUrl)
	{
		set_Value (COLUMNNAME_PayPal_PayerApproveUrl, PayPal_PayerApproveUrl);
	}

	/** Get Payer Approve URL.
		@return Payer Approve URL	  */
	@Override
	public java.lang.String getPayPal_PayerApproveUrl () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PayPal_PayerApproveUrl);
	}

	/** Set Status.
		@param Status Status	  */
	@Override
	public void setStatus (java.lang.String Status)
	{
		set_Value (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status	  */
	@Override
	public java.lang.String getStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Status);
	}
}