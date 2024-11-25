// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_AD_Org_Lookup_BPL_GLN_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_EDI_AD_Org_Lookup_BPL_GLN_v extends org.compiere.model.PO implements I_EDI_AD_Org_Lookup_BPL_GLN_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1246016226L;

    /** Standard Constructor */
    public X_EDI_AD_Org_Lookup_BPL_GLN_v (final Properties ctx, final int EDI_AD_Org_Lookup_BPL_GLN_v_ID, @Nullable final String trxName)
    {
      super (ctx, EDI_AD_Org_Lookup_BPL_GLN_v_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_AD_Org_Lookup_BPL_GLN_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setGLN (final @Nullable java.lang.String GLN)
	{
		set_Value (COLUMNNAME_GLN, GLN);
	}

	@Override
	public java.lang.String getGLN() 
	{
		return get_ValueAsString(COLUMNNAME_GLN);
	}
}