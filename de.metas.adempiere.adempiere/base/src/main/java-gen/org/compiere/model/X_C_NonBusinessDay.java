/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_NonBusinessDay
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_NonBusinessDay extends org.compiere.model.PO implements I_C_NonBusinessDay, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 611723878L;

    /** Standard Constructor */
    public X_C_NonBusinessDay (Properties ctx, int C_NonBusinessDay_ID, String trxName)
    {
      super (ctx, C_NonBusinessDay_ID, trxName);
      /** if (C_NonBusinessDay_ID == 0)
        {
			setC_Calendar_ID (0);
			setC_NonBusinessDay_ID (0);
			setDate1 (new Timestamp( System.currentTimeMillis() ));
			setIsRepeat (false); // N
        } */
    }

    /** Load Constructor */
    public X_C_NonBusinessDay (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_Calendar getC_Calendar() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class);
	}

	@Override
	public void setC_Calendar(org.compiere.model.I_C_Calendar C_Calendar)
	{
		set_ValueFromPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class, C_Calendar);
	}

	/** Set Kalender.
		@param C_Calendar_ID 
		Accounting Calendar Name
	  */
	@Override
	public void setC_Calendar_ID (int C_Calendar_ID)
	{
		if (C_Calendar_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Calendar_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Calendar_ID, Integer.valueOf(C_Calendar_ID));
	}

	/** Get Kalender.
		@return Accounting Calendar Name
	  */
	@Override
	public int getC_Calendar_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Calendar_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Geschäftsfreier Tag.
		@param C_NonBusinessDay_ID 
		Day on which business is not transacted
	  */
	@Override
	public void setC_NonBusinessDay_ID (int C_NonBusinessDay_ID)
	{
		if (C_NonBusinessDay_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_NonBusinessDay_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_NonBusinessDay_ID, Integer.valueOf(C_NonBusinessDay_ID));
	}

	/** Get Geschäftsfreier Tag.
		@return Day on which business is not transacted
	  */
	@Override
	public int getC_NonBusinessDay_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_NonBusinessDay_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Datum.
		@param Date1 
		Date when business is not conducted
	  */
	@Override
	public void setDate1 (java.sql.Timestamp Date1)
	{
		set_Value (COLUMNNAME_Date1, Date1);
	}

	/** Get Datum.
		@return Date when business is not conducted
	  */
	@Override
	public java.sql.Timestamp getDate1 () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_Date1);
	}

	/** Set Enddatum.
		@param EndDate 
		Last effective date (inclusive)
	  */
	@Override
	public void setEndDate (java.sql.Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get Enddatum.
		@return Last effective date (inclusive)
	  */
	@Override
	public java.sql.Timestamp getEndDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_EndDate);
	}

	/** 
	 * Frequency AD_Reference_ID=540870
	 * Reference name: C_NonBusinessDay_Frequency
	 */
	public static final int FREQUENCY_AD_Reference_ID=540870;
	/** Weekly = W */
	public static final String FREQUENCY_Weekly = "W";
	/** Yearly = Y */
	public static final String FREQUENCY_Yearly = "Y";
	/** Set Häufigkeit.
		@param Frequency 
		Häufigkeit von Ereignissen
	  */
	@Override
	public void setFrequency (java.lang.String Frequency)
	{

		set_Value (COLUMNNAME_Frequency, Frequency);
	}

	/** Get Häufigkeit.
		@return Häufigkeit von Ereignissen
	  */
	@Override
	public java.lang.String getFrequency () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Frequency);
	}

	/** Set Repeat.
		@param IsRepeat Repeat	  */
	@Override
	public void setIsRepeat (boolean IsRepeat)
	{
		set_Value (COLUMNNAME_IsRepeat, Boolean.valueOf(IsRepeat));
	}

	/** Get Repeat.
		@return Repeat	  */
	@Override
	public boolean isRepeat () 
	{
		Object oo = get_Value(COLUMNNAME_IsRepeat);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}
}