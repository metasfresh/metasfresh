/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_120_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_cctop_120_v extends org.compiere.model.PO implements I_EDI_cctop_120_v, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1469243309L;

    /** Standard Constructor */
    public X_EDI_cctop_120_v (Properties ctx, int EDI_cctop_120_v_ID, String trxName)
    {
      super (ctx, EDI_cctop_120_v_ID, trxName);
      /** if (EDI_cctop_120_v_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_EDI_cctop_120_v (Properties ctx, ResultSet rs, String trxName)
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

	/** Set EDI_cctop_120_v.
		@param EDI_cctop_120_v_ID EDI_cctop_120_v	  */
	@Override
	public void setEDI_cctop_120_v_ID (int EDI_cctop_120_v_ID)
	{
		if (EDI_cctop_120_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_120_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_120_v_ID, Integer.valueOf(EDI_cctop_120_v_ID));
	}

	/** Get EDI_cctop_120_v.
		@return EDI_cctop_120_v	  */
	@Override
	public int getEDI_cctop_120_v_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_cctop_120_v_ID);
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

	/** Set ISO Währungscode.
		@param ISO_Code 
		Dreibuchstabiger ISO 4217 Code für die Währung
	  */
	@Override
	public void setISO_Code (java.lang.String ISO_Code)
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	/** Get ISO Währungscode.
		@return Dreibuchstabiger ISO 4217 Code für die Währung
	  */
	@Override
	public java.lang.String getISO_Code () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ISO_Code);
	}

	/** Set netdate.
		@param netdate netdate	  */
	@Override
	public void setnetdate (java.sql.Timestamp netdate)
	{
		set_Value (COLUMNNAME_netdate, netdate);
	}

	/** Get netdate.
		@return netdate	  */
	@Override
	public java.sql.Timestamp getnetdate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_netdate);
	}

	/** Set Tage Netto.
		@param NetDays 
		Anzahl Tage, bis Zahlung fällig wird.
	  */
	@Override
	public void setNetDays (int NetDays)
	{
		set_Value (COLUMNNAME_NetDays, Integer.valueOf(NetDays));
	}

	/** Get Tage Netto.
		@return Anzahl Tage, bis Zahlung fällig wird.
	  */
	@Override
	public int getNetDays () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NetDays);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set singlevat.
		@param singlevat singlevat	  */
	@Override
	public void setsinglevat (java.math.BigDecimal singlevat)
	{
		set_Value (COLUMNNAME_singlevat, singlevat);
	}

	/** Get singlevat.
		@return singlevat	  */
	@Override
	public java.math.BigDecimal getsinglevat () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_singlevat);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set taxfree.
		@param taxfree taxfree	  */
	@Override
	public void settaxfree (boolean taxfree)
	{
		set_Value (COLUMNNAME_taxfree, Boolean.valueOf(taxfree));
	}

	/** Get taxfree.
		@return taxfree	  */
	@Override
	public boolean istaxfree () 
	{
		Object oo = get_Value(COLUMNNAME_taxfree);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}