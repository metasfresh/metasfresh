/** Generated Model - DO NOT CHANGE */
package de.metas.serviceprovider.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_TimeBooking
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_S_TimeBooking extends org.compiere.model.PO implements I_S_TimeBooking, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1604655305L;

    /** Standard Constructor */
    public X_S_TimeBooking (Properties ctx, int S_TimeBooking_ID, String trxName)
    {
      super (ctx, S_TimeBooking_ID, trxName);
      /** if (S_TimeBooking_ID == 0)
        {
			setAD_User_Performing_ID (0);
			setEffortHours (0); // 0
			setEffortMinutes (0); // 0
			setS_Issue_ID (0);
			setS_TimeBooking_ID (0);
        } */
    }

    /** Load Constructor */
    public X_S_TimeBooking (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Erbringende Person.
		@param AD_User_Performing_ID Erbringende Person	  */
	@Override
	public void setAD_User_Performing_ID (int AD_User_Performing_ID)
	{
		if (AD_User_Performing_ID < 1) 
			set_Value (COLUMNNAME_AD_User_Performing_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_Performing_ID, Integer.valueOf(AD_User_Performing_ID));
	}

	/** Get Erbringende Person.
		@return Erbringende Person	  */
	@Override
	public int getAD_User_Performing_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_Performing_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Stunden.
		@param EffortHours Stunden	  */
	@Override
	public void setEffortHours (int EffortHours)
	{
		set_Value (COLUMNNAME_EffortHours, Integer.valueOf(EffortHours));
	}

	/** Get Stunden.
		@return Stunden	  */
	@Override
	public int getEffortHours () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EffortHours);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Minuten.
		@param EffortMinutes Minuten	  */
	@Override
	public void setEffortMinutes (int EffortMinutes)
	{
		set_Value (COLUMNNAME_EffortMinutes, Integer.valueOf(EffortMinutes));
	}

	/** Get Minuten.
		@return Minuten	  */
	@Override
	public int getEffortMinutes () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EffortMinutes);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.serviceprovider.model.I_S_Issue getS_Issue()
	{
		return get_ValueAsPO(COLUMNNAME_S_Issue_ID, de.metas.serviceprovider.model.I_S_Issue.class);
	}

	@Override
	public void setS_Issue(de.metas.serviceprovider.model.I_S_Issue S_Issue)
	{
		set_ValueFromPO(COLUMNNAME_S_Issue_ID, de.metas.serviceprovider.model.I_S_Issue.class, S_Issue);
	}

	/** Set Issue.
		@param S_Issue_ID Issue	  */
	@Override
	public void setS_Issue_ID (int S_Issue_ID)
	{
		if (S_Issue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_Issue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_Issue_ID, Integer.valueOf(S_Issue_ID));
	}

	/** Get Issue.
		@return Issue	  */
	@Override
	public int getS_Issue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_Issue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set S_TimeBooking.
		@param S_TimeBooking_ID S_TimeBooking	  */
	@Override
	public void setS_TimeBooking_ID (int S_TimeBooking_ID)
	{
		if (S_TimeBooking_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_TimeBooking_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_TimeBooking_ID, Integer.valueOf(S_TimeBooking_ID));
	}

	/** Get S_TimeBooking.
		@return S_TimeBooking	  */
	@Override
	public int getS_TimeBooking_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_TimeBooking_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}