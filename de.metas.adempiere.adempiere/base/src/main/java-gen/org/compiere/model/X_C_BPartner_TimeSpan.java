/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_TimeSpan
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_BPartner_TimeSpan extends org.compiere.model.PO implements I_C_BPartner_TimeSpan, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1223245977L;

    /** Standard Constructor */
    public X_C_BPartner_TimeSpan (Properties ctx, int C_BPartner_TimeSpan_ID, String trxName)
    {
      super (ctx, C_BPartner_TimeSpan_ID, trxName);
      /** if (C_BPartner_TimeSpan_ID == 0)
        {
			setC_BPartner_TimeSpan_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_BPartner_TimeSpan (Properties ctx, ResultSet rs, String trxName)
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
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	/** 
	 * C_BPartner_TimeSpan AD_Reference_ID=540937
	 * Reference name: C_BPartner_TimeSpan_List
	 */
	public static final int C_BPARTNER_TIMESPAN_AD_Reference_ID=540937;
	/** Neukunde = N */
	public static final String C_BPARTNER_TIMESPAN_Neukunde = "N";
	/** Stammkunde = S */
	public static final String C_BPARTNER_TIMESPAN_Stammkunde = "S";
	/** Set C_BPartner_TimeSpan.
		@param C_BPartner_TimeSpan C_BPartner_TimeSpan	  */
	@Override
	public void setC_BPartner_TimeSpan (java.lang.String C_BPartner_TimeSpan)
	{

		set_Value (COLUMNNAME_C_BPartner_TimeSpan, C_BPartner_TimeSpan);
	}

	/** Get C_BPartner_TimeSpan.
		@return C_BPartner_TimeSpan	  */
	@Override
	public java.lang.String getC_BPartner_TimeSpan () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_C_BPartner_TimeSpan);
	}

	/** Set Partner Time Span.
		@param C_BPartner_TimeSpan_ID Partner Time Span	  */
	@Override
	public void setC_BPartner_TimeSpan_ID (int C_BPartner_TimeSpan_ID)
	{
		if (C_BPartner_TimeSpan_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_TimeSpan_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_TimeSpan_ID, Integer.valueOf(C_BPartner_TimeSpan_ID));
	}

	/** Get Partner Time Span.
		@return Partner Time Span	  */
	@Override
	public int getC_BPartner_TimeSpan_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_TimeSpan_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}