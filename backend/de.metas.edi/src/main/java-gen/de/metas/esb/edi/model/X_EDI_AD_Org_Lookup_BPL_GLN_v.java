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

/** Generated Model for EDI_AD_Org_Lookup_BPL_GLN_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public class X_EDI_AD_Org_Lookup_BPL_GLN_v extends org.compiere.model.PO implements I_EDI_AD_Org_Lookup_BPL_GLN_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 596691634L;

    /** Standard Constructor */
    public X_EDI_AD_Org_Lookup_BPL_GLN_v (Properties ctx, int EDI_AD_Org_Lookup_BPL_GLN_v_ID, String trxName)
=======
@SuppressWarnings("unused")
public class X_EDI_AD_Org_Lookup_BPL_GLN_v extends org.compiere.model.PO implements I_EDI_AD_Org_Lookup_BPL_GLN_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1246016226L;

    /** Standard Constructor */
    public X_EDI_AD_Org_Lookup_BPL_GLN_v (final Properties ctx, final int EDI_AD_Org_Lookup_BPL_GLN_v_ID, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, EDI_AD_Org_Lookup_BPL_GLN_v_ID, trxName);
    }

    /** Load Constructor */
<<<<<<< HEAD
    public X_EDI_AD_Org_Lookup_BPL_GLN_v (Properties ctx, ResultSet rs, String trxName)
=======
    public X_EDI_AD_Org_Lookup_BPL_GLN_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setGLN (java.lang.String GLN)
=======
	public void setGLN (final @Nullable java.lang.String GLN)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_GLN, GLN);
	}

	@Override
	public java.lang.String getGLN() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_GLN);
=======
		return get_ValueAsString(COLUMNNAME_GLN);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}