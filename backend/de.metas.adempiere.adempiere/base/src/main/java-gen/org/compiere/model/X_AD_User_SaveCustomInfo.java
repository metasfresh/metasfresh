/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_User_SaveCustomInfo
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_User_SaveCustomInfo extends org.compiere.model.PO implements I_AD_User_SaveCustomInfo, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 138964010L;

    /** Standard Constructor */
    public X_AD_User_SaveCustomInfo (Properties ctx, int AD_User_SaveCustomInfo_ID, String trxName)
    {
      super (ctx, AD_User_SaveCustomInfo_ID, trxName);
      /** if (AD_User_SaveCustomInfo_ID == 0)
        {
			setAD_User_SaveCustomInfo_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_User_SaveCustomInfo (Properties ctx, ResultSet rs, String trxName)
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

	/** Set User Save Custom Info.
		@param AD_User_SaveCustomInfo_ID User Save Custom Info	  */
	@Override
	public void setAD_User_SaveCustomInfo_ID (int AD_User_SaveCustomInfo_ID)
	{
		if (AD_User_SaveCustomInfo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_SaveCustomInfo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_SaveCustomInfo_ID, Integer.valueOf(AD_User_SaveCustomInfo_ID));
	}

	/** Get User Save Custom Info.
		@return User Save Custom Info	  */
	@Override
	public int getAD_User_SaveCustomInfo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_SaveCustomInfo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Capture Sequence.
		@param CaptureSequence Capture Sequence	  */
	@Override
	public void setCaptureSequence (java.lang.String CaptureSequence)
	{
		set_Value (COLUMNNAME_CaptureSequence, CaptureSequence);
	}

	/** Get Capture Sequence.
		@return Capture Sequence	  */
	@Override
	public java.lang.String getCaptureSequence () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CaptureSequence);
	}
}