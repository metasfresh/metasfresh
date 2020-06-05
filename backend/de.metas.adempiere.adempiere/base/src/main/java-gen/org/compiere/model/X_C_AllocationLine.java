/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_AllocationLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_AllocationLine extends org.compiere.model.PO implements I_C_AllocationLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 824458214L;

    /** Standard Constructor */
    public X_C_AllocationLine (Properties ctx, int C_AllocationLine_ID, String trxName)
    {
      super (ctx, C_AllocationLine_ID, trxName);
      /** if (C_AllocationLine_ID == 0)
        {
			setAmount (BigDecimal.ZERO);
			setC_AllocationHdr_ID (0);
			setC_AllocationLine_ID (0);
			setDiscountAmt (BigDecimal.ZERO);
			setPaymentWriteOffAmt (BigDecimal.ZERO); // 0
			setWriteOffAmt (BigDecimal.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_AllocationLine (Properties ctx, ResultSet rs, String trxName)
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
		Amount in a defined currency
	  */
	@Override
	public void setAmount (java.math.BigDecimal Amount)
	{
		set_ValueNoCheck (COLUMNNAME_Amount, Amount);
	}

	/** Get Betrag.
		@return Amount in a defined currency
	  */
	@Override
	public java.math.BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_C_AllocationHdr getC_AllocationHdr()
	{
		return get_ValueAsPO(COLUMNNAME_C_AllocationHdr_ID, org.compiere.model.I_C_AllocationHdr.class);
	}

	@Override
	public void setC_AllocationHdr(org.compiere.model.I_C_AllocationHdr C_AllocationHdr)
	{
		set_ValueFromPO(COLUMNNAME_C_AllocationHdr_ID, org.compiere.model.I_C_AllocationHdr.class, C_AllocationHdr);
	}

	/** Set Zuordnung.
		@param C_AllocationHdr_ID 
		Payment allocation
	  */
	@Override
	public void setC_AllocationHdr_ID (int C_AllocationHdr_ID)
	{
		if (C_AllocationHdr_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AllocationHdr_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AllocationHdr_ID, Integer.valueOf(C_AllocationHdr_ID));
	}

	/** Get Zuordnung.
		@return Payment allocation
	  */
	@Override
	public int getC_AllocationHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AllocationHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zuordnungs-Position.
		@param C_AllocationLine_ID 
		Allocation Line
	  */
	@Override
	public void setC_AllocationLine_ID (int C_AllocationLine_ID)
	{
		if (C_AllocationLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AllocationLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AllocationLine_ID, Integer.valueOf(C_AllocationLine_ID));
	}

	/** Get Zuordnungs-Position.
		@return Allocation Line
	  */
	@Override
	public int getC_AllocationLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AllocationLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Identifies a Business Partner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_CashLine getC_CashLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_CashLine_ID, org.compiere.model.I_C_CashLine.class);
	}

	@Override
	public void setC_CashLine(org.compiere.model.I_C_CashLine C_CashLine)
	{
		set_ValueFromPO(COLUMNNAME_C_CashLine_ID, org.compiere.model.I_C_CashLine.class, C_CashLine);
	}

	/** Set Cash Journal Line.
		@param C_CashLine_ID 
		Cash Journal Line
	  */
	@Override
	public void setC_CashLine_ID (int C_CashLine_ID)
	{
		if (C_CashLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CashLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CashLine_ID, Integer.valueOf(C_CashLine_ID));
	}

	/** Get Cash Journal Line.
		@return Cash Journal Line
	  */
	@Override
	public int getC_CashLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CashLine_ID);
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
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
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

	/** Set Zahlung.
		@param C_Payment_ID 
		Payment identifier
	  */
	@Override
	public void setC_Payment_ID (int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Payment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
	}

	/** Get Zahlung.
		@return Payment identifier
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
	public org.compiere.model.I_C_AllocationLine getCounter_AllocationLine()
	{
		return get_ValueAsPO(COLUMNNAME_Counter_AllocationLine_ID, org.compiere.model.I_C_AllocationLine.class);
	}

	@Override
	public void setCounter_AllocationLine(org.compiere.model.I_C_AllocationLine Counter_AllocationLine)
	{
		set_ValueFromPO(COLUMNNAME_Counter_AllocationLine_ID, org.compiere.model.I_C_AllocationLine.class, Counter_AllocationLine);
	}

	/** Set Partner-Zeile.
		@param Counter_AllocationLine_ID 
		Das Feld wird verwendet, wenn Rechnung gegen Rechnung oder Zahlung gegen Zahlung alloziert wird und referenziert die Zuordnungszeile des jeweiligen Pendants.
	  */
	@Override
	public void setCounter_AllocationLine_ID (int Counter_AllocationLine_ID)
	{
		if (Counter_AllocationLine_ID < 1) 
			set_Value (COLUMNNAME_Counter_AllocationLine_ID, null);
		else 
			set_Value (COLUMNNAME_Counter_AllocationLine_ID, Integer.valueOf(Counter_AllocationLine_ID));
	}

	/** Get Partner-Zeile.
		@return Das Feld wird verwendet, wenn Rechnung gegen Rechnung oder Zahlung gegen Zahlung alloziert wird und referenziert die Zuordnungszeile des jeweiligen Pendants.
	  */
	@Override
	public int getCounter_AllocationLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Counter_AllocationLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Datum.
		@param DateTrx 
		Vorgangsdatum
	  */
	@Override
	public void setDateTrx (java.sql.Timestamp DateTrx)
	{
		set_ValueNoCheck (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Datum.
		@return Vorgangsdatum
	  */
	@Override
	public java.sql.Timestamp getDateTrx () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** Set Skonto.
		@param DiscountAmt 
		Calculated amount of discount
	  */
	@Override
	public void setDiscountAmt (java.math.BigDecimal DiscountAmt)
	{
		set_ValueNoCheck (COLUMNNAME_DiscountAmt, DiscountAmt);
	}

	/** Get Skonto.
		@return Calculated amount of discount
	  */
	@Override
	public java.math.BigDecimal getDiscountAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DiscountAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Manuell.
		@param IsManual 
		This is a manual process
	  */
	@Override
	public void setIsManual (boolean IsManual)
	{
		set_ValueNoCheck (COLUMNNAME_IsManual, Boolean.valueOf(IsManual));
	}

	/** Get Manuell.
		@return This is a manual process
	  */
	@Override
	public boolean isManual () 
	{
		Object oo = get_Value(COLUMNNAME_IsManual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Over/Under Payment.
		@param OverUnderAmt 
		Over-Payment (unallocated) or Under-Payment (partial payment) Amount
	  */
	@Override
	public void setOverUnderAmt (java.math.BigDecimal OverUnderAmt)
	{
		set_Value (COLUMNNAME_OverUnderAmt, OverUnderAmt);
	}

	/** Get Over/Under Payment.
		@return Over-Payment (unallocated) or Under-Payment (partial payment) Amount
	  */
	@Override
	public java.math.BigDecimal getOverUnderAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OverUnderAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Abschreibung Betrag.
		@param PaymentWriteOffAmt 
		Abschreibung Betrag
	  */
	@Override
	public void setPaymentWriteOffAmt (java.math.BigDecimal PaymentWriteOffAmt)
	{
		set_Value (COLUMNNAME_PaymentWriteOffAmt, PaymentWriteOffAmt);
	}

	/** Get Abschreibung Betrag.
		@return Abschreibung Betrag
	  */
	@Override
	public java.math.BigDecimal getPaymentWriteOffAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PaymentWriteOffAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_C_AllocationLine getReversalLine()
	{
		return get_ValueAsPO(COLUMNNAME_ReversalLine_ID, org.compiere.model.I_C_AllocationLine.class);
	}

	@Override
	public void setReversalLine(org.compiere.model.I_C_AllocationLine ReversalLine)
	{
		set_ValueFromPO(COLUMNNAME_ReversalLine_ID, org.compiere.model.I_C_AllocationLine.class, ReversalLine);
	}

	/** Set Storno-Zeile.
		@param ReversalLine_ID Storno-Zeile	  */
	@Override
	public void setReversalLine_ID (int ReversalLine_ID)
	{
		if (ReversalLine_ID < 1) 
			set_Value (COLUMNNAME_ReversalLine_ID, null);
		else 
			set_Value (COLUMNNAME_ReversalLine_ID, Integer.valueOf(ReversalLine_ID));
	}

	/** Get Storno-Zeile.
		@return Storno-Zeile	  */
	@Override
	public int getReversalLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ReversalLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Abschreiben.
		@param WriteOffAmt 
		Amount to write-off
	  */
	@Override
	public void setWriteOffAmt (java.math.BigDecimal WriteOffAmt)
	{
		set_ValueNoCheck (COLUMNNAME_WriteOffAmt, WriteOffAmt);
	}

	/** Get Abschreiben.
		@return Amount to write-off
	  */
	@Override
	public java.math.BigDecimal getWriteOffAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_WriteOffAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}