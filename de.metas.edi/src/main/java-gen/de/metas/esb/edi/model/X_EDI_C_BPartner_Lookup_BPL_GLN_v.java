/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_C_BPartner_Lookup_BPL_GLN_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_C_BPartner_Lookup_BPL_GLN_v extends org.compiere.model.PO implements I_EDI_C_BPartner_Lookup_BPL_GLN_v, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 384302997L;

    /** Standard Constructor */
    public X_EDI_C_BPartner_Lookup_BPL_GLN_v (Properties ctx, int EDI_C_BPartner_Lookup_BPL_GLN_v_ID, String trxName)
    {
      super (ctx, EDI_C_BPartner_Lookup_BPL_GLN_v_ID, trxName);
      /** if (EDI_C_BPartner_Lookup_BPL_GLN_v_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_EDI_C_BPartner_Lookup_BPL_GLN_v (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set GLN.
		@param GLN GLN	  */
	@Override
	public void setGLN (java.lang.String GLN)
	{
		set_Value (COLUMNNAME_GLN, GLN);
	}

	/** Get GLN.
		@return GLN	  */
	@Override
	public java.lang.String getGLN () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GLN);
	}

	/** Set StoreGLN.
		@param StoreGLN StoreGLN	  */
	@Override
	public void setStoreGLN (java.lang.String StoreGLN)
	{
		set_Value (COLUMNNAME_StoreGLN, StoreGLN);
	}

	/** Get StoreGLN.
		@return StoreGLN	  */
	@Override
	public java.lang.String getStoreGLN () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_StoreGLN);
	}
}