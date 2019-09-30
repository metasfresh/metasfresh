/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_140_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_cctop_140_v extends org.compiere.model.PO implements I_EDI_cctop_140_v, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1311868469L;

    /** Standard Constructor */
    public X_EDI_cctop_140_v (Properties ctx, int EDI_cctop_140_v_ID, String trxName)
    {
      super (ctx, EDI_cctop_140_v_ID, trxName);
      /** if (EDI_cctop_140_v_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_EDI_cctop_140_v (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Rabatt %.
		@param Discount 
		Abschlag in Prozent
	  */
	@Override
	public void setDiscount (int Discount)
	{
		set_Value (COLUMNNAME_Discount, Integer.valueOf(Discount));
	}

	/** Get Rabatt %.
		@return Abschlag in Prozent
	  */
	@Override
	public int getDiscount () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Discount);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Skontodatum.
		@param DiscountDate 
		Last Date for payments with discount
	  */
	@Override
	public void setDiscountDate (java.sql.Timestamp DiscountDate)
	{
		set_Value (COLUMNNAME_DiscountDate, DiscountDate);
	}

	/** Get Skontodatum.
		@return Last Date for payments with discount
	  */
	@Override
	public java.sql.Timestamp getDiscountDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DiscountDate);
	}

	/** Set Rabattfrist.
		@param DiscountDays 
		Anzahl der Tage ab Rechnungsstellung innerhalb derer der Rabatt gewährt wird
	  */
	@Override
	public void setDiscountDays (int DiscountDays)
	{
		set_Value (COLUMNNAME_DiscountDays, Integer.valueOf(DiscountDays));
	}

	/** Get Rabattfrist.
		@return Anzahl der Tage ab Rechnungsstellung innerhalb derer der Rabatt gewährt wird
	  */
	@Override
	public int getDiscountDays () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DiscountDays);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set EDI_cctop_140_v.
		@param EDI_cctop_140_v_ID EDI_cctop_140_v	  */
	@Override
	public void setEDI_cctop_140_v_ID (int EDI_cctop_140_v_ID)
	{
		if (EDI_cctop_140_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_140_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_140_v_ID, Integer.valueOf(EDI_cctop_140_v_ID));
	}

	/** Get EDI_cctop_140_v.
		@return EDI_cctop_140_v	  */
	@Override
	public int getEDI_cctop_140_v_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_cctop_140_v_ID);
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
}