<<<<<<< HEAD
/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

=======
// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import javax.annotation.Nullable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_901_991_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public class X_EDI_cctop_901_991_v extends org.compiere.model.PO implements I_EDI_cctop_901_991_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -361433412L;

    /** Standard Constructor */
    public X_EDI_cctop_901_991_v (Properties ctx, int EDI_cctop_901_991_v_ID, String trxName)
=======
@SuppressWarnings("unused")
public class X_EDI_cctop_901_991_v extends org.compiere.model.PO implements I_EDI_cctop_901_991_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1419851275L;

    /** Standard Constructor */
    public X_EDI_cctop_901_991_v (final Properties ctx, final int EDI_cctop_901_991_v_ID, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, EDI_cctop_901_991_v_ID, trxName);
    }

    /** Load Constructor */
<<<<<<< HEAD
    public X_EDI_cctop_901_991_v (Properties ctx, ResultSet rs, String trxName)
=======
    public X_EDI_cctop_901_991_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
<<<<<<< HEAD
	protected org.compiere.model.POInfo initPO(Properties ctx)
=======
	protected org.compiere.model.POInfo initPO(final Properties ctx)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
<<<<<<< HEAD
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
=======
	public void setC_Invoice(final org.compiere.model.I_C_Invoice C_Invoice)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	@Override
<<<<<<< HEAD
	public void setC_Invoice_ID (int C_Invoice_ID)
=======
	public void setC_Invoice_ID (final int C_Invoice_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
<<<<<<< HEAD
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
=======
			set_Value (COLUMNNAME_C_Invoice_ID, C_Invoice_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
	}

	@Override
<<<<<<< HEAD
	public void setEDI_cctop_901_991_v_ID (int EDI_cctop_901_991_v_ID)
=======
	public void setEDI_cctop_901_991_v_ID (final int EDI_cctop_901_991_v_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (EDI_cctop_901_991_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_901_991_v_ID, null);
		else 
<<<<<<< HEAD
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_901_991_v_ID, Integer.valueOf(EDI_cctop_901_991_v_ID));
=======
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_901_991_v_ID, EDI_cctop_901_991_v_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	public void setEDI_cctop_invoic_v(de.metas.esb.edi.model.I_EDI_cctop_invoic_v EDI_cctop_invoic_v)
=======
	public void setEDI_cctop_invoic_v(final de.metas.esb.edi.model.I_EDI_cctop_invoic_v EDI_cctop_invoic_v)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_EDI_cctop_invoic_v_ID, de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class, EDI_cctop_invoic_v);
	}

	@Override
<<<<<<< HEAD
	public void setEDI_cctop_invoic_v_ID (int EDI_cctop_invoic_v_ID)
=======
	public void setEDI_cctop_invoic_v_ID (final int EDI_cctop_invoic_v_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (EDI_cctop_invoic_v_ID < 1) 
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, null);
		else 
<<<<<<< HEAD
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, Integer.valueOf(EDI_cctop_invoic_v_ID));
=======
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, EDI_cctop_invoic_v_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getEDI_cctop_invoic_v_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_cctop_invoic_v_ID);
	}

	@Override
<<<<<<< HEAD
	public void setESRReferenceNumber (java.lang.String ESRReferenceNumber)
=======
	public void setESRReferenceNumber (final @Nullable java.lang.String ESRReferenceNumber)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_ESRReferenceNumber, ESRReferenceNumber);
	}

	@Override
	public java.lang.String getESRReferenceNumber() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_ESRReferenceNumber);
	}

	@Override
	public void setRate (java.math.BigDecimal Rate)
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Rate, Rate);
	}

	@Override
<<<<<<< HEAD
	public java.math.BigDecimal getRate() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Rate);
=======
	public BigDecimal getRate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Rate);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setTaxAmt (java.math.BigDecimal TaxAmt)
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_TaxAmt, TaxAmt);
	}

	@Override
<<<<<<< HEAD
	public java.math.BigDecimal getTaxAmt() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TaxAmt);
=======
	public BigDecimal getTaxAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TaxAmt);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setTaxBaseAmt (java.math.BigDecimal TaxBaseAmt)
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_TaxBaseAmt, TaxBaseAmt);
	}

	@Override
<<<<<<< HEAD
	public java.math.BigDecimal getTaxBaseAmt() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TaxBaseAmt);
=======
	public BigDecimal getTaxBaseAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TaxBaseAmt);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setTotalAmt (java.math.BigDecimal TotalAmt)
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	@Override
<<<<<<< HEAD
	public java.math.BigDecimal getTotalAmt() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalAmt);
=======
	public BigDecimal getTotalAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalAmt);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}
}