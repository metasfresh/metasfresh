/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_Recent_V
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_BPartner_Recent_V extends org.compiere.model.PO implements I_C_BPartner_Recent_V, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1462980482L;

    /** Standard Constructor */
    public X_C_BPartner_Recent_V (Properties ctx, int C_BPartner_Recent_V_ID, String trxName)
    {
      super (ctx, C_BPartner_Recent_V_ID, trxName);
      /** if (C_BPartner_Recent_V_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_C_BPartner_Recent_V (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
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

	/** Set C_BPartner_Recent_V_ID.
		@param C_BPartner_Recent_V_ID C_BPartner_Recent_V_ID	  */
	@Override
	public void setC_BPartner_Recent_V_ID (int C_BPartner_Recent_V_ID)
	{
		if (C_BPartner_Recent_V_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Recent_V_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Recent_V_ID, Integer.valueOf(C_BPartner_Recent_V_ID));
	}

	/** Get C_BPartner_Recent_V_ID.
		@return C_BPartner_Recent_V_ID	  */
	@Override
	public int getC_BPartner_Recent_V_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Recent_V_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}