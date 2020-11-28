/** Generated Model - DO NOT CHANGE */
package de.metas.event.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_EventLog
 *  @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_AD_EventLog extends org.compiere.model.PO implements I_AD_EventLog, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = -813056443L;

    /** Standard Constructor */
    public X_AD_EventLog (Properties ctx, int AD_EventLog_ID, String trxName)
    {
      super (ctx, AD_EventLog_ID, trxName);
      /** if (AD_EventLog_ID == 0)
        {
			setAD_EventLog_ID (0);
			setEvent_UUID (null);
			setIsError (false); // N
			setIsErrorAcknowledged (false); // N
        } */
    }

    /** Load Constructor */
    public X_AD_EventLog (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Event log.
		@param AD_EventLog_ID Event log	  */
	@Override
	public void setAD_EventLog_ID (int AD_EventLog_ID)
	{
		if (AD_EventLog_ID < 1)
			set_ValueNoCheck (COLUMNNAME_AD_EventLog_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_EventLog_ID, Integer.valueOf(AD_EventLog_ID));
	}

	/** Get Event log.
		@return Event log	  */
	@Override
	public int getAD_EventLog_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_EventLog_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Daten.
		@param EventData Daten	  */
	@Override
	public void setEventData (java.lang.String EventData)
	{
		set_ValueNoCheck (COLUMNNAME_EventData, EventData);
	}

	/** Get Daten.
		@return Daten	  */
	@Override
	public java.lang.String getEventData ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_EventData);
	}

	/** Set Zeitpunkt.
		@param EventTime Zeitpunkt	  */
	@Override
	public void setEventTime (java.sql.Timestamp EventTime)
	{
		set_ValueNoCheck (COLUMNNAME_EventTime, EventTime);
	}

	/** Get Zeitpunkt.
		@return Zeitpunkt	  */
	@Override
	public java.sql.Timestamp getEventTime ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_EventTime);
	}

	/** Set Topicname.
		@param EventTopicName Topicname	  */
	@Override
	public void setEventTopicName (java.lang.String EventTopicName)
	{
		set_Value (COLUMNNAME_EventTopicName, EventTopicName);
	}

	/** Get Topicname.
		@return Topicname	  */
	@Override
	public java.lang.String getEventTopicName ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_EventTopicName);
	}

	/**
	 * EventTypeName AD_Reference_ID=540802
	 * Reference name: EventTypeName
	 */
	public static final int EVENTTYPENAME_AD_Reference_ID=540802;
	/** LOCAL = LOCAL */
	public static final String EVENTTYPENAME_LOCAL = "LOCAL";
	/** REMOTE = REMOTE */
	public static final String EVENTTYPENAME_REMOTE = "REMOTE";
	/** Set Type.
		@param EventTypeName Type	  */
	@Override
	public void setEventTypeName (java.lang.String EventTypeName)
	{

		set_Value (COLUMNNAME_EventTypeName, EventTypeName);
	}

	/** Get Type.
		@return Type	  */
	@Override
	public java.lang.String getEventTypeName ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_EventTypeName);
	}

	/** Set Event UUID.
		@param Event_UUID Event UUID	  */
	@Override
	public void setEvent_UUID (java.lang.String Event_UUID)
	{
		set_ValueNoCheck (COLUMNNAME_Event_UUID, Event_UUID);
	}

	/** Get Event UUID.
		@return Event UUID	  */
	@Override
	public java.lang.String getEvent_UUID ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Event_UUID);
	}

	/** Set Fehler.
		@param IsError
		Ein Fehler ist bei der Durchführung aufgetreten
	  */
	@Override
	public void setIsError (boolean IsError)
	{
		set_Value (COLUMNNAME_IsError, Boolean.valueOf(IsError));
	}

	/** Get Fehler.
		@return Ein Fehler ist bei der Durchführung aufgetreten
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

	/** Set Fehler zur Kentnis genommen.
		@param IsErrorAcknowledged Fehler zur Kentnis genommen	  */
	@Override
	public void setIsErrorAcknowledged (boolean IsErrorAcknowledged)
	{
		set_Value (COLUMNNAME_IsErrorAcknowledged, Boolean.valueOf(IsErrorAcknowledged));
	}

	/** Get Fehler zur Kentnis genommen.
		@return Fehler zur Kentnis genommen	  */
	@Override
	public boolean isErrorAcknowledged ()
	{
		Object oo = get_Value(COLUMNNAME_IsErrorAcknowledged);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}
}