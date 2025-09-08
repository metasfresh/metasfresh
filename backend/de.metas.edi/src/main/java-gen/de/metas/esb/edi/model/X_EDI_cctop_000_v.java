// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_000_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_EDI_cctop_000_v extends org.compiere.model.PO implements I_EDI_cctop_000_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1093740575L;

    /** Standard Constructor */
    public X_EDI_cctop_000_v (final Properties ctx, final int EDI_cctop_000_v_ID, @Nullable final String trxName)
    {
      super (ctx, EDI_cctop_000_v_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_cctop_000_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public void setEDI_cctop_000_v_ID (final int EDI_cctop_000_v_ID)
	{
		if (EDI_cctop_000_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_000_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_000_v_ID, EDI_cctop_000_v_ID);
	}

	@Override
	public int getEDI_cctop_000_v_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_cctop_000_v_ID);
	}

	@Override
	public void setEdiInvoicRecipientGLN (final @Nullable java.lang.String EdiInvoicRecipientGLN)
	{
		set_ValueNoCheck (COLUMNNAME_EdiInvoicRecipientGLN, EdiInvoicRecipientGLN);
	}

	@Override
	public java.lang.String getEdiInvoicRecipientGLN() 
	{
		return get_ValueAsString(COLUMNNAME_EdiInvoicRecipientGLN);
	}
}