/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_120_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_cctop_120_v extends org.compiere.model.PO implements I_EDI_cctop_120_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2068725549L;

    /** Standard Constructor */
    public X_EDI_cctop_120_v (Properties ctx, int EDI_cctop_120_v_ID, String trxName)
    {
      super (ctx, EDI_cctop_120_v_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_cctop_120_v (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
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

	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
	}

	@Override
	public void setEDI_cctop_120_v_ID (int EDI_cctop_120_v_ID)
	{
		if (EDI_cctop_120_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_120_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_120_v_ID, Integer.valueOf(EDI_cctop_120_v_ID));
	}

	@Override
	public int getEDI_cctop_120_v_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_cctop_120_v_ID);
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

	@Override
	public void setEDI_cctop_invoic_v_ID (int EDI_cctop_invoic_v_ID)
	{
		if (EDI_cctop_invoic_v_ID < 1) 
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, null);
		else 
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, Integer.valueOf(EDI_cctop_invoic_v_ID));
	}

	@Override
	public int getEDI_cctop_invoic_v_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_cctop_invoic_v_ID);
	}

	@Override
	public void setISO_Code (java.lang.String ISO_Code)
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	@Override
	public java.lang.String getISO_Code() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ISO_Code);
	}

	@Override
	public void setnetdate (java.sql.Timestamp netdate)
	{
		set_Value (COLUMNNAME_netdate, netdate);
	}

	@Override
	public java.sql.Timestamp getnetdate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_netdate);
	}

	@Override
	public void setNetDays (int NetDays)
	{
		set_Value (COLUMNNAME_NetDays, Integer.valueOf(NetDays));
	}

	@Override
	public int getNetDays() 
	{
		return get_ValueAsInt(COLUMNNAME_NetDays);
	}

	@Override
	public void setsinglevat (java.math.BigDecimal singlevat)
	{
		set_Value (COLUMNNAME_singlevat, singlevat);
	}

	@Override
	public java.math.BigDecimal getsinglevat() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_singlevat);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void settaxfree (boolean taxfree)
	{
		set_Value (COLUMNNAME_taxfree, Boolean.valueOf(taxfree));
	}

	@Override
	public boolean istaxfree() 
	{
		return get_ValueAsBoolean(COLUMNNAME_taxfree);
	}
}