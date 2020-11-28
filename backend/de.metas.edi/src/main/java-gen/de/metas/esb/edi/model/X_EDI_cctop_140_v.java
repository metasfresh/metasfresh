/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_140_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_cctop_140_v extends org.compiere.model.PO implements I_EDI_cctop_140_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -896678396L;

    /** Standard Constructor */
    public X_EDI_cctop_140_v (Properties ctx, int EDI_cctop_140_v_ID, String trxName)
    {
      super (ctx, EDI_cctop_140_v_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_cctop_140_v (Properties ctx, ResultSet rs, String trxName)
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
	public void setDiscount (int Discount)
	{
		set_Value (COLUMNNAME_Discount, Integer.valueOf(Discount));
	}

	@Override
	public int getDiscount() 
	{
		return get_ValueAsInt(COLUMNNAME_Discount);
	}

	@Override
	public void setDiscountDate (java.sql.Timestamp DiscountDate)
	{
		set_Value (COLUMNNAME_DiscountDate, DiscountDate);
	}

	@Override
	public java.sql.Timestamp getDiscountDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DiscountDate);
	}

	@Override
	public void setDiscountDays (int DiscountDays)
	{
		set_Value (COLUMNNAME_DiscountDays, Integer.valueOf(DiscountDays));
	}

	@Override
	public int getDiscountDays() 
	{
		return get_ValueAsInt(COLUMNNAME_DiscountDays);
	}

	@Override
	public void setEDI_cctop_140_v_ID (int EDI_cctop_140_v_ID)
	{
		if (EDI_cctop_140_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_140_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_140_v_ID, Integer.valueOf(EDI_cctop_140_v_ID));
	}

	@Override
	public int getEDI_cctop_140_v_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_cctop_140_v_ID);
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
	public void setRate (java.math.BigDecimal Rate)
	{
		set_Value (COLUMNNAME_Rate, Rate);
	}

	@Override
	public java.math.BigDecimal getRate() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Rate);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}