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

/** Generated Model for EDI_C_BPartner_Lookup_BPL_GLN_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public class X_EDI_C_BPartner_Lookup_BPL_GLN_v extends org.compiere.model.PO implements I_EDI_C_BPartner_Lookup_BPL_GLN_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1119152613L;

    /** Standard Constructor */
    public X_EDI_C_BPartner_Lookup_BPL_GLN_v (Properties ctx, int EDI_C_BPartner_Lookup_BPL_GLN_v_ID, String trxName)
=======
@SuppressWarnings("unused")
public class X_EDI_C_BPartner_Lookup_BPL_GLN_v extends org.compiere.model.PO implements I_EDI_C_BPartner_Lookup_BPL_GLN_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 948019275L;

    /** Standard Constructor */
    public X_EDI_C_BPartner_Lookup_BPL_GLN_v (final Properties ctx, final int EDI_C_BPartner_Lookup_BPL_GLN_v_ID, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, EDI_C_BPartner_Lookup_BPL_GLN_v_ID, trxName);
    }

    /** Load Constructor */
<<<<<<< HEAD
    public X_EDI_C_BPartner_Lookup_BPL_GLN_v (Properties ctx, ResultSet rs, String trxName)
=======
    public X_EDI_C_BPartner_Lookup_BPL_GLN_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_ID (int C_BPartner_ID)
=======
	public void setC_BPartner_ID (final int C_BPartner_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
<<<<<<< HEAD
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
=======
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
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
	}

	@Override
	public void setStoreGLN (java.lang.String StoreGLN)
=======
		return get_ValueAsString(COLUMNNAME_GLN);
	}

	@Override
	public void setLookup_Label (final @Nullable java.lang.String Lookup_Label)
	{
		set_ValueNoCheck (COLUMNNAME_Lookup_Label, Lookup_Label);
	}

	@Override
	public java.lang.String getLookup_Label() 
	{
		return get_ValueAsString(COLUMNNAME_Lookup_Label);
	}

	@Override
	public void setStoreGLN (final @Nullable java.lang.String StoreGLN)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_StoreGLN, StoreGLN);
	}

	@Override
	public java.lang.String getStoreGLN() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_StoreGLN);
=======
		return get_ValueAsString(COLUMNNAME_StoreGLN);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}