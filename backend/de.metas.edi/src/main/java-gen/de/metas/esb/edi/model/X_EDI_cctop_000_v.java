<<<<<<< HEAD
/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

=======
// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import javax.annotation.Nullable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_000_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public class X_EDI_cctop_000_v extends org.compiere.model.PO implements I_EDI_cctop_000_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 25785628L;

    /** Standard Constructor */
    public X_EDI_cctop_000_v (Properties ctx, int EDI_cctop_000_v_ID, String trxName)
=======
@SuppressWarnings("unused")
public class X_EDI_cctop_000_v extends org.compiere.model.PO implements I_EDI_cctop_000_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1093740575L;

    /** Standard Constructor */
    public X_EDI_cctop_000_v (final Properties ctx, final int EDI_cctop_000_v_ID, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, EDI_cctop_000_v_ID, trxName);
    }

    /** Load Constructor */
<<<<<<< HEAD
    public X_EDI_cctop_000_v (Properties ctx, ResultSet rs, String trxName)
=======
    public X_EDI_cctop_000_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
<<<<<<< HEAD
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
=======
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
<<<<<<< HEAD
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
=======
			set_Value (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
<<<<<<< HEAD
	public void setEDI_cctop_000_v_ID (int EDI_cctop_000_v_ID)
=======
	public void setEDI_cctop_000_v_ID (final int EDI_cctop_000_v_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (EDI_cctop_000_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_000_v_ID, null);
		else 
<<<<<<< HEAD
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_000_v_ID, Integer.valueOf(EDI_cctop_000_v_ID));
=======
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_000_v_ID, EDI_cctop_000_v_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getEDI_cctop_000_v_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_cctop_000_v_ID);
	}

	@Override
<<<<<<< HEAD
	public void setEdiDesadvRecipientGLN (java.lang.String EdiDesadvRecipientGLN)
	{
		set_Value (COLUMNNAME_EdiDesadvRecipientGLN, EdiDesadvRecipientGLN);
	}

	@Override
	public java.lang.String getEdiDesadvRecipientGLN() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EdiDesadvRecipientGLN);
=======
	public void setEdiInvoicRecipientGLN (final @Nullable java.lang.String EdiInvoicRecipientGLN)
	{
		set_ValueNoCheck (COLUMNNAME_EdiInvoicRecipientGLN, EdiInvoicRecipientGLN);
	}

	@Override
	public java.lang.String getEdiInvoicRecipientGLN() 
	{
		return get_ValueAsString(COLUMNNAME_EdiInvoicRecipientGLN);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}