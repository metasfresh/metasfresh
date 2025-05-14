// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_140_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_EDI_cctop_140_v extends org.compiere.model.PO implements I_EDI_cctop_140_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1936200952L;

    /** Standard Constructor */
    public X_EDI_cctop_140_v (final Properties ctx, final int EDI_cctop_140_v_ID, @Nullable final String trxName)
    {
      super (ctx, EDI_cctop_140_v_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_cctop_140_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(final org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	@Override
	public void setC_Invoice_ID (final int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, C_Invoice_ID);
	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
	}

	@Override
	public void setDiscount (final int Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
	}

	@Override
	public int getDiscount() 
	{
		return get_ValueAsInt(COLUMNNAME_Discount);
	}

	@Override
	public void setDiscountAmt (final @Nullable BigDecimal DiscountAmt)
	{
		set_ValueNoCheck (COLUMNNAME_DiscountAmt, DiscountAmt);
	}

	@Override
	public BigDecimal getDiscountAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DiscountAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDiscountBaseAmt (final @Nullable BigDecimal DiscountBaseAmt)
	{
		set_ValueNoCheck (COLUMNNAME_DiscountBaseAmt, DiscountBaseAmt);
	}

	@Override
	public BigDecimal getDiscountBaseAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DiscountBaseAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDiscountDate (final @Nullable java.sql.Timestamp DiscountDate)
	{
		set_Value (COLUMNNAME_DiscountDate, DiscountDate);
	}

	@Override
	public java.sql.Timestamp getDiscountDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DiscountDate);
	}

	@Override
	public void setDiscountDays (final int DiscountDays)
	{
		set_Value (COLUMNNAME_DiscountDays, DiscountDays);
	}

	@Override
	public int getDiscountDays() 
	{
		return get_ValueAsInt(COLUMNNAME_DiscountDays);
	}

	@Override
	public void setEDI_cctop_140_v_ID (final int EDI_cctop_140_v_ID)
	{
		if (EDI_cctop_140_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_140_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_140_v_ID, EDI_cctop_140_v_ID);
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
	public void setEDI_cctop_invoic_v(final de.metas.esb.edi.model.I_EDI_cctop_invoic_v EDI_cctop_invoic_v)
	{
		set_ValueFromPO(COLUMNNAME_EDI_cctop_invoic_v_ID, de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class, EDI_cctop_invoic_v);
	}

	@Override
	public void setEDI_cctop_invoic_v_ID (final int EDI_cctop_invoic_v_ID)
	{
		if (EDI_cctop_invoic_v_ID < 1) 
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, null);
		else 
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, EDI_cctop_invoic_v_ID);
	}

	@Override
	public int getEDI_cctop_invoic_v_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_cctop_invoic_v_ID);
	}

	@Override
	public void setName (final @Nullable java.lang.String Name)
	{
		set_ValueNoCheck (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setRate (final @Nullable BigDecimal Rate)
	{
		set_Value (COLUMNNAME_Rate, Rate);
	}

	@Override
	public BigDecimal getRate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Rate);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}