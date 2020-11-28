/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_C_BPartner_Lookup_BPL_GLN_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_C_BPartner_Lookup_BPL_GLN_v extends org.compiere.model.PO implements I_EDI_C_BPartner_Lookup_BPL_GLN_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1119152613L;

    /** Standard Constructor */
    public X_EDI_C_BPartner_Lookup_BPL_GLN_v (Properties ctx, int EDI_C_BPartner_Lookup_BPL_GLN_v_ID, String trxName)
    {
      super (ctx, EDI_C_BPartner_Lookup_BPL_GLN_v_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_C_BPartner_Lookup_BPL_GLN_v (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setGLN (java.lang.String GLN)
	{
		set_Value (COLUMNNAME_GLN, GLN);
	}

	@Override
	public java.lang.String getGLN() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GLN);
	}

	@Override
	public void setStoreGLN (java.lang.String StoreGLN)
	{
		set_Value (COLUMNNAME_StoreGLN, StoreGLN);
	}

	@Override
	public java.lang.String getStoreGLN() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_StoreGLN);
	}
}