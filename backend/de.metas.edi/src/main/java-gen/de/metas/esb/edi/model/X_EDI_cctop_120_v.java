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

/** Generated Model for EDI_cctop_120_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public class X_EDI_cctop_120_v extends org.compiere.model.PO implements I_EDI_cctop_120_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2068725549L;

    /** Standard Constructor */
    public X_EDI_cctop_120_v (Properties ctx, int EDI_cctop_120_v_ID, String trxName)
=======
@SuppressWarnings("unused")
public class X_EDI_cctop_120_v extends org.compiere.model.PO implements I_EDI_cctop_120_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1318976726L;

    /** Standard Constructor */
    public X_EDI_cctop_120_v (final Properties ctx, final int EDI_cctop_120_v_ID, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, EDI_cctop_120_v_ID, trxName);
    }

    /** Load Constructor */
<<<<<<< HEAD
    public X_EDI_cctop_120_v (Properties ctx, ResultSet rs, String trxName)
=======
    public X_EDI_cctop_120_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setEDI_cctop_120_v_ID (int EDI_cctop_120_v_ID)
=======
	public void setEDI_cctop_120_v_ID (final int EDI_cctop_120_v_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (EDI_cctop_120_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_120_v_ID, null);
		else 
<<<<<<< HEAD
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_120_v_ID, Integer.valueOf(EDI_cctop_120_v_ID));
=======
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_120_v_ID, EDI_cctop_120_v_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
	public void setISO_Code (java.lang.String ISO_Code)
=======
	public void setISO_Code (final @Nullable java.lang.String ISO_Code)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	@Override
	public java.lang.String getISO_Code() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_ISO_Code);
	}

	@Override
	public void setnetdate (java.sql.Timestamp netdate)
=======
		return get_ValueAsString(COLUMNNAME_ISO_Code);
	}

	@Override
	public void setnetdate (final @Nullable java.sql.Timestamp netdate)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_netdate, netdate);
	}

	@Override
	public java.sql.Timestamp getnetdate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_netdate);
	}

	@Override
<<<<<<< HEAD
	public void setNetDays (int NetDays)
	{
		set_Value (COLUMNNAME_NetDays, Integer.valueOf(NetDays));
=======
	public void setNetDays (final int NetDays)
	{
		set_Value (COLUMNNAME_NetDays, NetDays);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getNetDays() 
	{
		return get_ValueAsInt(COLUMNNAME_NetDays);
	}

	@Override
<<<<<<< HEAD
	public void setsinglevat (java.math.BigDecimal singlevat)
=======
	public void setsinglevat (final @Nullable BigDecimal singlevat)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_singlevat, singlevat);
	}

	@Override
<<<<<<< HEAD
	public java.math.BigDecimal getsinglevat() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_singlevat);
=======
	public BigDecimal getsinglevat() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_singlevat);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void settaxfree (boolean taxfree)
	{
		set_Value (COLUMNNAME_taxfree, Boolean.valueOf(taxfree));
=======
	public void settaxfree (final boolean taxfree)
	{
		set_Value (COLUMNNAME_taxfree, taxfree);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public boolean istaxfree() 
	{
		return get_ValueAsBoolean(COLUMNNAME_taxfree);
	}
}