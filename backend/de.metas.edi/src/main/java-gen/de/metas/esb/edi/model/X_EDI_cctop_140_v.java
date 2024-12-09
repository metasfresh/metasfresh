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

/** Generated Model for EDI_cctop_140_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public class X_EDI_cctop_140_v extends org.compiere.model.PO implements I_EDI_cctop_140_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -896678396L;

    /** Standard Constructor */
    public X_EDI_cctop_140_v (Properties ctx, int EDI_cctop_140_v_ID, String trxName)
=======
@SuppressWarnings("unused")
public class X_EDI_cctop_140_v extends org.compiere.model.PO implements I_EDI_cctop_140_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1936200952L;

    /** Standard Constructor */
    public X_EDI_cctop_140_v (final Properties ctx, final int EDI_cctop_140_v_ID, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, EDI_cctop_140_v_ID, trxName);
    }

    /** Load Constructor */
<<<<<<< HEAD
    public X_EDI_cctop_140_v (Properties ctx, ResultSet rs, String trxName)
=======
    public X_EDI_cctop_140_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDiscount (int Discount)
	{
		set_Value (COLUMNNAME_Discount, Integer.valueOf(Discount));
=======
	public void setDiscount (final int Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getDiscount() 
	{
		return get_ValueAsInt(COLUMNNAME_Discount);
	}

	@Override
<<<<<<< HEAD
	public void setDiscountDate (java.sql.Timestamp DiscountDate)
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_DiscountDate, DiscountDate);
	}

	@Override
	public java.sql.Timestamp getDiscountDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DiscountDate);
	}

	@Override
<<<<<<< HEAD
	public void setDiscountDays (int DiscountDays)
	{
		set_Value (COLUMNNAME_DiscountDays, Integer.valueOf(DiscountDays));
=======
	public void setDiscountDays (final int DiscountDays)
	{
		set_Value (COLUMNNAME_DiscountDays, DiscountDays);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getDiscountDays() 
	{
		return get_ValueAsInt(COLUMNNAME_DiscountDays);
	}

	@Override
<<<<<<< HEAD
	public void setEDI_cctop_140_v_ID (int EDI_cctop_140_v_ID)
=======
	public void setEDI_cctop_140_v_ID (final int EDI_cctop_140_v_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (EDI_cctop_140_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_140_v_ID, null);
		else 
<<<<<<< HEAD
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_140_v_ID, Integer.valueOf(EDI_cctop_140_v_ID));
=======
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_140_v_ID, EDI_cctop_140_v_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
	public void setRate (java.math.BigDecimal Rate)
=======
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
}