/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for M_RMALine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_RMALine extends org.compiere.model.PO implements I_M_RMALine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -51165858L;

    /** Standard Constructor */
    public X_M_RMALine (Properties ctx, int M_RMALine_ID, String trxName)
    {
      super (ctx, M_RMALine_ID, trxName);
      /** if (M_RMALine_ID == 0)
        {
			setM_RMA_ID (0);
			setM_RMALine_ID (0);
			setProcessed (false);
			setQty (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_M_RMALine (Properties ctx, ResultSet rs, String trxName)
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
		@param Amt 
		Amount
	  */
	@Override
	public void setAmt (java.math.BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	/** Get Betrag.
		@return Amount
	  */
	@Override
	public java.math.BigDecimal getAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Charge_ID, org.compiere.model.I_C_Charge.class);
	}

	@Override
	public void setC_Charge(org.compiere.model.I_C_Charge C_Charge)
	{
		set_ValueFromPO(COLUMNNAME_C_Charge_ID, org.compiere.model.I_C_Charge.class, C_Charge);
	}

	/** Set Kosten.
		@param C_Charge_ID 
		Additional document charges
	  */
	@Override
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Kosten.
		@return Additional document charges
	  */
	@Override
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Zeile Nr..
		@param Line 
		Unique line for this document
	  */
	@Override
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Zeile Nr..
		@return Unique line for this document
	  */
	@Override
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zeilennetto.
		@param LineNetAmt 
		Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	@Override
	public void setLineNetAmt (java.math.BigDecimal LineNetAmt)
	{
		set_Value (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	/** Get Zeilennetto.
		@return Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	@Override
	public java.math.BigDecimal getLineNetAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineNetAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_M_InOutLine getM_InOutLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class);
	}

	@Override
	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class, M_InOutLine);
	}

	/** Set Versand-/Wareneingangsposition.
		@param M_InOutLine_ID 
		Line on Shipment or Receipt document
	  */
	@Override
	public void setM_InOutLine_ID (int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_Value (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
	}

	/** Get Versand-/Wareneingangsposition.
		@return Line on Shipment or Receipt document
	  */
	@Override
	public int getM_InOutLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_RMA getM_RMA() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_RMA_ID, org.compiere.model.I_M_RMA.class);
	}

	@Override
	public void setM_RMA(org.compiere.model.I_M_RMA M_RMA)
	{
		set_ValueFromPO(COLUMNNAME_M_RMA_ID, org.compiere.model.I_M_RMA.class, M_RMA);
	}

	/** Set Warenrücksendung - Freigabe (RMA).
		@param M_RMA_ID 
		Return Material Authorization
	  */
	@Override
	public void setM_RMA_ID (int M_RMA_ID)
	{
		if (M_RMA_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_RMA_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_RMA_ID, Integer.valueOf(M_RMA_ID));
	}

	/** Get Warenrücksendung - Freigabe (RMA).
		@return Return Material Authorization
	  */
	@Override
	public int getM_RMA_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_RMA_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RMA-Position.
		@param M_RMALine_ID 
		Return Material Authorization Line
	  */
	@Override
	public void setM_RMALine_ID (int M_RMALine_ID)
	{
		if (M_RMALine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_RMALine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_RMALine_ID, Integer.valueOf(M_RMALine_ID));
	}

	/** Get RMA-Position.
		@return Return Material Authorization Line
	  */
	@Override
	public int getM_RMALine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_RMALine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Menge.
		@param Qty 
		Quantity
	  */
	@Override
	public void setQty (java.math.BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Menge.
		@return Quantity
	  */
	@Override
	public java.math.BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Gelieferte Menge.
		@param QtyDelivered 
		Delivered Quantity
	  */
	@Override
	public void setQtyDelivered (java.math.BigDecimal QtyDelivered)
	{
		set_Value (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	/** Get Gelieferte Menge.
		@return Delivered Quantity
	  */
	@Override
	public java.math.BigDecimal getQtyDelivered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDelivered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Berechn. Menge.
		@param QtyInvoiced 
		Menge, die bereits in Rechnung gestellt wurde
	  */
	@Override
	public void setQtyInvoiced (java.math.BigDecimal QtyInvoiced)
	{
		set_Value (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	/** Get Berechn. Menge.
		@return Menge, die bereits in Rechnung gestellt wurde
	  */
	@Override
	public java.math.BigDecimal getQtyInvoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyInvoiced);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_M_RMALine getRef_RMALine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Ref_RMALine_ID, org.compiere.model.I_M_RMALine.class);
	}

	@Override
	public void setRef_RMALine(org.compiere.model.I_M_RMALine Ref_RMALine)
	{
		set_ValueFromPO(COLUMNNAME_Ref_RMALine_ID, org.compiere.model.I_M_RMALine.class, Ref_RMALine);
	}

	/** Set Referenced RMA Line.
		@param Ref_RMALine_ID Referenced RMA Line	  */
	@Override
	public void setRef_RMALine_ID (int Ref_RMALine_ID)
	{
		if (Ref_RMALine_ID < 1) 
			set_Value (COLUMNNAME_Ref_RMALine_ID, null);
		else 
			set_Value (COLUMNNAME_Ref_RMALine_ID, Integer.valueOf(Ref_RMALine_ID));
	}

	/** Get Referenced RMA Line.
		@return Referenced RMA Line	  */
	@Override
	public int getRef_RMALine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ref_RMALine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}