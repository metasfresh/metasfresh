// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_901_991_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_EDI_cctop_901_991_v extends org.compiere.model.PO implements I_EDI_cctop_901_991_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1419851275L;

    /** Standard Constructor */
    public X_EDI_cctop_901_991_v (final Properties ctx, final int EDI_cctop_901_991_v_ID, @Nullable final String trxName)
    {
      super (ctx, EDI_cctop_901_991_v_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_cctop_901_991_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setEDI_cctop_901_991_v_ID (final int EDI_cctop_901_991_v_ID)
	{
		if (EDI_cctop_901_991_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_901_991_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_901_991_v_ID, EDI_cctop_901_991_v_ID);
	}

	@Override
	public int getEDI_cctop_901_991_v_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_cctop_901_991_v_ID);
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
	public void setESRReferenceNumber (final @Nullable java.lang.String ESRReferenceNumber)
	{
		set_Value (COLUMNNAME_ESRReferenceNumber, ESRReferenceNumber);
	}

	@Override
	public java.lang.String getESRReferenceNumber() 
	{
		return get_ValueAsString(COLUMNNAME_ESRReferenceNumber);
	}

	@Override
	public void setIsMainVAT (final boolean IsMainVAT)
	{
		set_ValueNoCheck (COLUMNNAME_IsMainVAT, IsMainVAT);
	}

	@Override
	public boolean isMainVAT() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMainVAT);
	}

	@Override
	public void setIsTaxExempt (final boolean IsTaxExempt)
	{
		set_ValueNoCheck (COLUMNNAME_IsTaxExempt, IsTaxExempt);
	}

	@Override
	public boolean isTaxExempt() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTaxExempt);
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

	@Override
	public void setSurchargeAmt (final BigDecimal SurchargeAmt)
	{
		set_ValueNoCheck (COLUMNNAME_SurchargeAmt, SurchargeAmt);
	}

	@Override
	public BigDecimal getSurchargeAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_SurchargeAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTaxAmt (final @Nullable BigDecimal TaxAmt)
	{
		set_Value (COLUMNNAME_TaxAmt, TaxAmt);
	}

	@Override
	public BigDecimal getTaxAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TaxAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTaxAmtWithSurchargeAmt (final BigDecimal TaxAmtWithSurchargeAmt)
	{
		set_ValueNoCheck (COLUMNNAME_TaxAmtWithSurchargeAmt, TaxAmtWithSurchargeAmt);
	}

	@Override
	public BigDecimal getTaxAmtWithSurchargeAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TaxAmtWithSurchargeAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTaxBaseAmt (final @Nullable BigDecimal TaxBaseAmt)
	{
		set_Value (COLUMNNAME_TaxBaseAmt, TaxBaseAmt);
	}

	@Override
	public BigDecimal getTaxBaseAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TaxBaseAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTaxBaseAmtWithSurchargeAmt (final BigDecimal TaxBaseAmtWithSurchargeAmt)
	{
		set_ValueNoCheck (COLUMNNAME_TaxBaseAmtWithSurchargeAmt, TaxBaseAmtWithSurchargeAmt);
	}

	@Override
	public BigDecimal getTaxBaseAmtWithSurchargeAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TaxBaseAmtWithSurchargeAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTotalAmt (final @Nullable BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	@Override
	public BigDecimal getTotalAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}