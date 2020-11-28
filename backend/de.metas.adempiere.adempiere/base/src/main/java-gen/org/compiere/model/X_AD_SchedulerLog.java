/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_SchedulerLog
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_SchedulerLog extends org.compiere.model.PO implements I_AD_SchedulerLog, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1311185518L;

    /** Standard Constructor */
    public X_AD_SchedulerLog (Properties ctx, int AD_SchedulerLog_ID, String trxName)
    {
      super (ctx, AD_SchedulerLog_ID, trxName);
      /** if (AD_SchedulerLog_ID == 0)
        {
			setAD_Scheduler_ID (0);
			setAD_SchedulerLog_ID (0);
			setIsError (false);
        } */
    }

    /** Load Constructor */
    public X_AD_SchedulerLog (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_PInstance getAD_PInstance() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class);
	}

	@Override
	public void setAD_PInstance(org.compiere.model.I_AD_PInstance AD_PInstance)
	{
		set_ValueFromPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class, AD_PInstance);
	}

	/** Set Prozess-Instanz.
		@param AD_PInstance_ID 
		Instanz eines Prozesses
	  */
	@Override
	public void setAD_PInstance_ID (int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_Value (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PInstance_ID, Integer.valueOf(AD_PInstance_ID));
	}

	/** Get Prozess-Instanz.
		@return Instanz eines Prozesses
	  */
	@Override
	public int getAD_PInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Scheduler getAD_Scheduler() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Scheduler_ID, org.compiere.model.I_AD_Scheduler.class);
	}

	@Override
	public void setAD_Scheduler(org.compiere.model.I_AD_Scheduler AD_Scheduler)
	{
		set_ValueFromPO(COLUMNNAME_AD_Scheduler_ID, org.compiere.model.I_AD_Scheduler.class, AD_Scheduler);
	}

	/** Set Ablaufsteuerung.
		@param AD_Scheduler_ID 
		Schedule Processes
	  */
	@Override
	public void setAD_Scheduler_ID (int AD_Scheduler_ID)
	{
		if (AD_Scheduler_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Scheduler_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Scheduler_ID, Integer.valueOf(AD_Scheduler_ID));
	}

	/** Get Ablaufsteuerung.
		@return Schedule Processes
	  */
	@Override
	public int getAD_Scheduler_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Scheduler_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Ablauf-Protokoll.
		@param AD_SchedulerLog_ID 
		Result of the execution of the Scheduler
	  */
	@Override
	public void setAD_SchedulerLog_ID (int AD_SchedulerLog_ID)
	{
		if (AD_SchedulerLog_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_SchedulerLog_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_SchedulerLog_ID, Integer.valueOf(AD_SchedulerLog_ID));
	}

	/** Get Ablauf-Protokoll.
		@return Result of the execution of the Scheduler
	  */
	@Override
	public int getAD_SchedulerLog_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_SchedulerLog_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Binärwert.
		@param BinaryData 
		Binary Data
	  */
	@Override
	public void setBinaryData (byte[] BinaryData)
	{
		set_Value (COLUMNNAME_BinaryData, BinaryData);
	}

	/** Get Binärwert.
		@return Binary Data
	  */
	@Override
	public byte[] getBinaryData () 
	{
		return (byte[])get_Value(COLUMNNAME_BinaryData);
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Fehler.
		@param IsError 
		An Error occured in the execution
	  */
	@Override
	public void setIsError (boolean IsError)
	{
		set_Value (COLUMNNAME_IsError, Boolean.valueOf(IsError));
	}

	/** Get Fehler.
		@return An Error occured in the execution
	  */
	@Override
	public boolean isError () 
	{
		Object oo = get_Value(COLUMNNAME_IsError);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Referenz.
		@param Reference 
		Reference for this record
	  */
	@Override
	public void setReference (java.lang.String Reference)
	{
		set_Value (COLUMNNAME_Reference, Reference);
	}

	/** Get Referenz.
		@return Reference for this record
	  */
	@Override
	public java.lang.String getReference () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Reference);
	}

	/** Set Zusammenfassung.
		@param Summary 
		Textual summary of this request
	  */
	@Override
	public void setSummary (java.lang.String Summary)
	{
		set_Value (COLUMNNAME_Summary, Summary);
	}

	/** Get Zusammenfassung.
		@return Textual summary of this request
	  */
	@Override
	public java.lang.String getSummary () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Summary);
	}

	/** Set Mitteilung.
		@param TextMsg 
		Text Message
	  */
	@Override
	public void setTextMsg (java.lang.String TextMsg)
	{
		set_Value (COLUMNNAME_TextMsg, TextMsg);
	}

	/** Get Mitteilung.
		@return Text Message
	  */
	@Override
	public java.lang.String getTextMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TextMsg);
	}
}