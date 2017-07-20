/** Generated Model - DO NOT CHANGE */
package de.metas.inoutcandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_ReceiptSchedule_Alloc
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_ReceiptSchedule_Alloc extends org.compiere.model.PO implements I_M_ReceiptSchedule_Alloc, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1325249708L;

    /** Standard Constructor */
    public X_M_ReceiptSchedule_Alloc (Properties ctx, int M_ReceiptSchedule_Alloc_ID, String trxName)
    {
      super (ctx, M_ReceiptSchedule_Alloc_ID, trxName);
      /** if (M_ReceiptSchedule_Alloc_ID == 0)
        {
			setM_ReceiptSchedule_Alloc_ID (0);
			setM_ReceiptSchedule_ID (0);
			setQtyWithIssues (BigDecimal.ZERO); // 0
        } */
    }

    /** Load Constructor */
    public X_M_ReceiptSchedule_Alloc (Properties ctx, ResultSet rs, String trxName)
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

	/** 
	 * DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Set Belegstatus.
		@param DocStatus 
		The current status of the document
	  */
	@Override
	public void setDocStatus (java.lang.String DocStatus)
	{

		throw new IllegalArgumentException ("DocStatus is virtual column");	}

	/** Get Belegstatus.
		@return The current status of the document
	  */
	@Override
	public java.lang.String getDocStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocStatus);
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	/** Set Lieferung/Wareneingang.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	@Override
	public void setM_InOut_ID (int M_InOut_ID)
	{
		throw new IllegalArgumentException ("M_InOut_ID is virtual column");	}

	/** Get Lieferung/Wareneingang.
		@return Material Shipment Document
	  */
	@Override
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
		Position auf Versand- oder Wareneingangsbeleg
	  */
	@Override
	public void setM_InOutLine_ID (int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
	}

	/** Get Versand-/Wareneingangsposition.
		@return Position auf Versand- oder Wareneingangsbeleg
	  */
	@Override
	public int getM_InOutLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Wareneingangsdispo - Wareneingangszeile.
		@param M_ReceiptSchedule_Alloc_ID Wareneingangsdispo - Wareneingangszeile	  */
	@Override
	public void setM_ReceiptSchedule_Alloc_ID (int M_ReceiptSchedule_Alloc_ID)
	{
		if (M_ReceiptSchedule_Alloc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ReceiptSchedule_Alloc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ReceiptSchedule_Alloc_ID, Integer.valueOf(M_ReceiptSchedule_Alloc_ID));
	}

	/** Get Wareneingangsdispo - Wareneingangszeile.
		@return Wareneingangsdispo - Wareneingangszeile	  */
	@Override
	public int getM_ReceiptSchedule_Alloc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ReceiptSchedule_Alloc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.inoutcandidate.model.I_M_ReceiptSchedule getM_ReceiptSchedule() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_ReceiptSchedule_ID, de.metas.inoutcandidate.model.I_M_ReceiptSchedule.class);
	}

	@Override
	public void setM_ReceiptSchedule(de.metas.inoutcandidate.model.I_M_ReceiptSchedule M_ReceiptSchedule)
	{
		set_ValueFromPO(COLUMNNAME_M_ReceiptSchedule_ID, de.metas.inoutcandidate.model.I_M_ReceiptSchedule.class, M_ReceiptSchedule);
	}

	/** Set Wareneingangsdisposition.
		@param M_ReceiptSchedule_ID Wareneingangsdisposition	  */
	@Override
	public void setM_ReceiptSchedule_ID (int M_ReceiptSchedule_ID)
	{
		if (M_ReceiptSchedule_ID < 1) 
			set_Value (COLUMNNAME_M_ReceiptSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_M_ReceiptSchedule_ID, Integer.valueOf(M_ReceiptSchedule_ID));
	}

	/** Get Wareneingangsdisposition.
		@return Wareneingangsdisposition	  */
	@Override
	public int getM_ReceiptSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ReceiptSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zugewiesene Menge.
		@param QtyAllocated Zugewiesene Menge	  */
	@Override
	public void setQtyAllocated (java.math.BigDecimal QtyAllocated)
	{
		set_ValueNoCheck (COLUMNNAME_QtyAllocated, QtyAllocated);
	}

	/** Get Zugewiesene Menge.
		@return Zugewiesene Menge	  */
	@Override
	public java.math.BigDecimal getQtyAllocated () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyAllocated);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Minderwertige Menge.
		@param QtyWithIssues 
		Mengen-Summe der zugeordneten Lieferzeilen, die mit "im Disput" markiert sind und nicht fakturiert werden sollen.
	  */
	@Override
	public void setQtyWithIssues (java.math.BigDecimal QtyWithIssues)
	{
		set_ValueNoCheck (COLUMNNAME_QtyWithIssues, QtyWithIssues);
	}

	/** Get Minderwertige Menge.
		@return Mengen-Summe der zugeordneten Lieferzeilen, die mit "im Disput" markiert sind und nicht fakturiert werden sollen.
	  */
	@Override
	public java.math.BigDecimal getQtyWithIssues () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyWithIssues);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Qualitätsabzug %.
		@param QualityDiscountPercent Qualitätsabzug %	  */
	@Override
	public void setQualityDiscountPercent (java.math.BigDecimal QualityDiscountPercent)
	{
		throw new IllegalArgumentException ("QualityDiscountPercent is virtual column");	}

	/** Get Qualitätsabzug %.
		@return Qualitätsabzug %	  */
	@Override
	public java.math.BigDecimal getQualityDiscountPercent () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QualityDiscountPercent);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Qualität-Notiz.
		@param QualityNote Qualität-Notiz	  */
	@Override
	public void setQualityNote (java.lang.String QualityNote)
	{
		throw new IllegalArgumentException ("QualityNote is virtual column");	}

	/** Get Qualität-Notiz.
		@return Qualität-Notiz	  */
	@Override
	public java.lang.String getQualityNote () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_QualityNote);
	}
}