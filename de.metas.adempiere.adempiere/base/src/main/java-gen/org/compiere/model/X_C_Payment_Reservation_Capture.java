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
	private static final long serialVersionUID = 1284747645L;

    /** Standard Constructor */
    public X_C_Payment_Reservation_Capture (Properties ctx, int C_Payment_Reservation_Capture_ID, String trxName)
    {
      super (ctx, C_Payment_Reservation_Capture_ID, trxName);
      /** if (C_Payment_Reservation_Capture_ID == 0)
        {
			setAmount (BigDecimal.ZERO);
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