/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Country_Sequence
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Country_Sequence extends org.compiere.model.PO implements I_C_Country_Sequence, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -231500775L;

    /** Standard Constructor */
    public X_C_Country_Sequence (Properties ctx, int C_Country_Sequence_ID, String trxName)
    {
      super (ctx, C_Country_Sequence_ID, trxName);
      /** if (C_Country_Sequence_ID == 0)
        {
			setC_Country_Sequence_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_Country_Sequence (Properties ctx, ResultSet rs, String trxName)
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

	/** 
	 * AD_Language AD_Reference_ID=106
	 * Reference name: AD_Language
	 */
	public static final int AD_LANGUAGE_AD_Reference_ID=106;
	/** Set Sprache.
		@param AD_Language 
		Sprache für diesen Eintrag
	  */
	@Override
	public void setAD_Language (java.lang.String AD_Language)
	{

		set_Value (COLUMNNAME_AD_Language, AD_Language);
	}

	/** Get Sprache.
		@return Sprache für diesen Eintrag
	  */
	@Override
	public java.lang.String getAD_Language () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AD_Language);
	}

	@Override
	public org.compiere.model.I_C_Country getC_Country() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setC_Country(org.compiere.model.I_C_Country C_Country)
	{
		set_ValueFromPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class, C_Country);
	}

	/** Set Land.
		@param C_Country_ID 
		Land
	  */
	@Override
	public void setC_Country_ID (int C_Country_ID)
	{
		if (C_Country_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Country_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
	}

	/** Get Land.
		@return Land
	  */
	@Override
	public int getC_Country_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Country Sequence.
		@param C_Country_Sequence_ID Country Sequence	  */
	@Override
	public void setC_Country_Sequence_ID (int C_Country_Sequence_ID)
	{
		if (C_Country_Sequence_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Country_Sequence_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Country_Sequence_ID, Integer.valueOf(C_Country_Sequence_ID));
	}

	/** Get Country Sequence.
		@return Country Sequence	  */
	@Override
	public int getC_Country_Sequence_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_Sequence_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Adress-Druckformat.
		@param DisplaySequence 
		Format for printing this Address
	  */
	@Override
	public void setDisplaySequence (java.lang.String DisplaySequence)
	{
		set_Value (COLUMNNAME_DisplaySequence, DisplaySequence);
	}

	/** Get Adress-Druckformat.
		@return Format for printing this Address
	  */
	@Override
	public java.lang.String getDisplaySequence () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DisplaySequence);
	}

	/** Set Local Address Format.
		@param DisplaySequenceLocal 
		Format for printing this Address locally
	  */
	@Override
	public void setDisplaySequenceLocal (java.lang.String DisplaySequenceLocal)
	{
		set_Value (COLUMNNAME_DisplaySequenceLocal, DisplaySequenceLocal);
	}

	/** Get Local Address Format.
		@return Format for printing this Address locally
	  */
	@Override
	public java.lang.String getDisplaySequenceLocal () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DisplaySequenceLocal);
	}
}