/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Process_Stats
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Process_Stats extends org.compiere.model.PO implements I_AD_Process_Stats, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -735298489L;

    /** Standard Constructor */
    public X_AD_Process_Stats (Properties ctx, int AD_Process_Stats_ID, String trxName)
    {
      super (ctx, AD_Process_Stats_ID, trxName);
      /** if (AD_Process_Stats_ID == 0)
        {
			setAD_Process_ID (0);
			setAD_Process_Stats_ID (0);
			setStatistic_Count (0);
// 0
			setStatistic_Millis (0);
// 0
        } */
    }

    /** Load Constructor */
    public X_AD_Process_Stats (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Process getAD_Process() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class);
	}

	@Override
	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process)
	{
		set_ValueFromPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class, AD_Process);
	}

	/** Set Prozess.
		@param AD_Process_ID 
		Prozess oder Bericht
	  */
	@Override
	public void setAD_Process_ID (int AD_Process_ID)
	{
		if (AD_Process_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Process_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Process_ID, Integer.valueOf(AD_Process_ID));
	}

	/** Get Prozess.
		@return Prozess oder Bericht
	  */
	@Override
	public int getAD_Process_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Process_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Process Statistics.
		@param AD_Process_Stats_ID Process Statistics	  */
	@Override
	public void setAD_Process_Stats_ID (int AD_Process_Stats_ID)
	{
		if (AD_Process_Stats_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Process_Stats_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Process_Stats_ID, Integer.valueOf(AD_Process_Stats_ID));
	}

	/** Get Process Statistics.
		@return Process Statistics	  */
	@Override
	public int getAD_Process_Stats_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Process_Stats_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Statistic Count.
		@param Statistic_Count 
		Internal statistics how often the entity was used
	  */
	@Override
	public void setStatistic_Count (int Statistic_Count)
	{
		set_Value (COLUMNNAME_Statistic_Count, Integer.valueOf(Statistic_Count));
	}

	/** Get Statistic Count.
		@return Internal statistics how often the entity was used
	  */
	@Override
	public int getStatistic_Count () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Statistic_Count);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Statistic Milliseconds.
		@param Statistic_Millis 
		Internal statistics how many milliseconds a process took
	  */
	@Override
	public void setStatistic_Millis (int Statistic_Millis)
	{
		set_Value (COLUMNNAME_Statistic_Millis, Integer.valueOf(Statistic_Millis));
	}

	/** Get Statistic Milliseconds.
		@return Internal statistics how many milliseconds a process took
	  */
	@Override
	public int getStatistic_Millis () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Statistic_Millis);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}