// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_Trx_Hdr
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_Trx_Hdr extends org.compiere.model.PO implements I_M_HU_Trx_Hdr, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1640376219L;

    /** Standard Constructor */
    public X_M_HU_Trx_Hdr (final Properties ctx, final int M_HU_Trx_Hdr_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_Trx_Hdr_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_Trx_Hdr (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_HU_Trx_Hdr_ID (final int M_HU_Trx_Hdr_ID)
	{
		if (M_HU_Trx_Hdr_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Hdr_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Hdr_ID, M_HU_Trx_Hdr_ID);
	}

	@Override
	public int getM_HU_Trx_Hdr_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_Trx_Hdr_ID);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}
}