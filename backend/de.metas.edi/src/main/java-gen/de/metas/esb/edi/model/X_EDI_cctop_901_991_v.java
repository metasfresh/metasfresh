/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_901_991_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_cctop_901_991_v extends org.compiere.model.PO implements I_EDI_cctop_901_991_v, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1184217112L;

    /** Standard Constructor */
    public X_EDI_cctop_901_991_v (Properties ctx, int EDI_cctop_901_991_v_ID, String trxName)
    {
      super (ctx, EDI_cctop_901_991_v_ID, trxName);
      /** if (EDI_cctop_901_991_v_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_EDI_cctop_901_991_v (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Invoice.
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

	/** Get Invoice.
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

	/** Set EDI_cctop_901_991_v.
		@param EDI_cctop_901_991_v_ID EDI_cctop_901_991_v	  */
	@Override
	public void setEDI_cctop_901_991_v_ID (int EDI_cctop_901_991_v_ID)
	{
		if (EDI_cctop_901_991_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_901_991_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_901_991_v_ID, Integer.valueOf(EDI_cctop_901_991_v_ID));
	}

	/** Get EDI_cctop_901_991_v.
		@return EDI_cctop_901_991_v	  */
	@Override
	public int getEDI_cctop_901_991_v_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_cctop_901_991_v_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.esb.edi.model.I_EDI_cctop_invoic_v getEDI_cctop_invoic_v()
	{
		return get_ValueAsPO(COLUMNNAME_EDI_cctop_invoic_v_ID, de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class);
	}

	@Override
	public void setEDI_cctop_invoic_v(de.metas.esb.edi.model.I_EDI_cctop_invoic_v EDI_cctop_invoic_v)
	{
		set_ValueFromPO(COLUMNNAME_EDI_cctop_invoic_v_ID, de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class, EDI_cctop_invoic_v);
	}

	/** Set EDI_cctop_invoic_v.
		@param EDI_cctop_invoic_v_ID EDI_cctop_invoic_v	  */
	@Override
	public void setEDI_cctop_invoic_v_ID (int EDI_cctop_invoic_v_ID)
	{
		if (EDI_cctop_invoic_v_ID < 1) 
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, null);
		else 
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, Integer.valueOf(EDI_cctop_invoic_v_ID));
	}

	/** Get EDI_cctop_invoic_v.
		@return EDI_cctop_invoic_v	  */
	@Override
	public int getEDI_cctop_invoic_v_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_cctop_invoic_v_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Referenznummer.
		@param ESRReferenceNumber 
		Ihre Kunden- oder Lieferantennummer beim Geschäftspartner
	  */
	@Override
	public void setESRReferenceNumber (java.lang.String ESRReferenceNumber)
	{
		set_Value (COLUMNNAME_ESRReferenceNumber, ESRReferenceNumber);
	}

	/** Get Referenznummer.
		@return Ihre Kunden- oder Lieferantennummer beim Geschäftspartner
	  */
	@Override
	public java.lang.String getESRReferenceNumber () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ESRReferenceNumber);
	}

	/** Set Satz.
		@param Rate 
		Rate or Tax or Exchange
	  */
	@Override
	public void setRate (java.math.BigDecimal Rate)
	{
		set_Value (COLUMNNAME_Rate, Rate);
	}

	/** Get Satz.
		@return Rate or Tax or Exchange
	  */
	@Override
	public java.math.BigDecimal getRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Rate);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Steuerbetrag.
		@param TaxAmt 
		Steuerbetrag für diesen Beleg
	  */
	@Override
	public void setTaxAmt (java.math.BigDecimal TaxAmt)
	{
		set_Value (COLUMNNAME_TaxAmt, TaxAmt);
	}

	/** Get Steuerbetrag.
		@return Steuerbetrag für diesen Beleg
	  */
	@Override
	public java.math.BigDecimal getTaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TaxAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Bezugswert.
		@param TaxBaseAmt 
		Bezugswert für die Berechnung der Steuer
	  */
	@Override
	public void setTaxBaseAmt (java.math.BigDecimal TaxBaseAmt)
	{
		set_Value (COLUMNNAME_TaxBaseAmt, TaxBaseAmt);
	}

	/** Get Bezugswert.
		@return Bezugswert für die Berechnung der Steuer
	  */
	@Override
	public java.math.BigDecimal getTaxBaseAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TaxBaseAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	@Override
	public void setTotalAmt (java.math.BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	@Override
	public java.math.BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}