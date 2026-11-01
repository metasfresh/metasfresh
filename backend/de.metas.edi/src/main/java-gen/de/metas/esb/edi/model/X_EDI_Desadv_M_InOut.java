// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_Desadv_M_InOut
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public class X_EDI_Desadv_M_InOut extends org.compiere.model.PO implements I_EDI_Desadv_M_InOut, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = 1L;

    /** Standard Constructor */
    public X_EDI_Desadv_M_InOut (final Properties ctx, final int EDI_Desadv_M_InOut_ID, @Nullable final String trxName)
    {
      super (ctx, EDI_Desadv_M_InOut_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_Desadv_M_InOut (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public de.metas.esb.edi.model.I_EDI_Desadv getEDI_Desadv()
	{
		return get_ValueAsPO(COLUMNNAME_EDI_Desadv_ID, de.metas.esb.edi.model.I_EDI_Desadv.class);
	}

	@Override
	public void setEDI_Desadv(final de.metas.esb.edi.model.I_EDI_Desadv EDI_Desadv)
	{
		set_ValueFromPO(COLUMNNAME_EDI_Desadv_ID, de.metas.esb.edi.model.I_EDI_Desadv.class, EDI_Desadv);
	}

	@Override
	public void setEDI_Desadv_ID (final int EDI_Desadv_ID)
	{
		if (EDI_Desadv_ID < 1)
			set_Value (COLUMNNAME_EDI_Desadv_ID, null);
		else
			set_Value (COLUMNNAME_EDI_Desadv_ID, EDI_Desadv_ID);
	}

	@Override
	public int getEDI_Desadv_ID()
	{
		return get_ValueAsInt(COLUMNNAME_EDI_Desadv_ID);
	}

	@Override
	public void setEDI_Desadv_M_InOut_ID (final int EDI_Desadv_M_InOut_ID)
	{
		if (EDI_Desadv_M_InOut_ID < 1)
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_M_InOut_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_M_InOut_ID, EDI_Desadv_M_InOut_ID);
	}

	@Override
	public int getEDI_Desadv_M_InOut_ID()
	{
		return get_ValueAsInt(COLUMNNAME_EDI_Desadv_M_InOut_ID);
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(final org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	@Override
	public void setM_InOut_ID (final int M_InOut_ID)
	{
		if (M_InOut_ID < 1)
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else
			set_Value (COLUMNNAME_M_InOut_ID, M_InOut_ID);
	}

	@Override
	public int getM_InOut_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_InOut_ID);
	}
}
