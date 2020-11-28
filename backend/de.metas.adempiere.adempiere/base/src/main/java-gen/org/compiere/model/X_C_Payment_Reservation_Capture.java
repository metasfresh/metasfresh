/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Payment_Reservation_Capture
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Payment_Reservation_Capture extends org.compiere.model.PO implements I_C_Payment_Reservation_Capture, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -234249769L;

    /** Standard Constructor */
    public X_C_Payment_Reservation_Capture (Properties ctx, int C_Payment_Reservation_Capture_ID, String trxName)
    {
      super (ctx, C_Payment_Reservation_Capture_ID, trxName);
      /** if (C_Payment_Reservation_Capture_ID == 0)
        {
			setAmount (BigDecimal.ZERO);
			setC_Currency_ID (0);
			setC_Invoice_ID (0);
			setC_Order_ID (0);
			setC_Payment_ID (0);
			setC_Payment_Reservation_Capture_ID (0);
			setC_Payment_Reservation_ID (0);
			setStatus (null);
        } */
    }

    /** Load Constructor */
    public X_C_Payment_Reservation_Capture (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Betrag.
		@param Amount 
		Betrag in einer definierten Währung
	  */
	@Override
	public void setAmount (java.math.BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Betrag.
		@return Betrag in einer definierten Währung
	  */
	@Override
	public java.math.BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Währung.
		@param C_Currency_ID 
		Die Währung für diesen Eintrag
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return Die Währung für diesen Eintrag
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
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

	/** Set Payment Reservation Capture.
		@param C_Payment_Reservation_Capture_ID Payment Reservation Capture	  */
	@Override
	public void setC_Payment_Reservation_Capture_ID (int C_Payment_Reservation_Capture_ID)
	{
		if (C_Payment_Reservation_Capture_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Payment_Reservation_Capture_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Payment_Reservation_Capture_ID, Integer.valueOf(C_Payment_Reservation_Capture_ID));
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
			set_ValueNoCheck (COLUMNNAME_C_Payment_Reservation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Payment_Reservation_ID, Integer.valueOf(C_Payment_Reservation_ID));
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