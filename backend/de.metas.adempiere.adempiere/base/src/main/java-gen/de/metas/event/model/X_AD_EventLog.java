// Generated Model - DO NOT CHANGE
package de.metas.event.model;

import org.compiere.model.I_Persistent;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_EventLog
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_EventLog extends PO implements I_AD_EventLog, I_Persistent
{

	private static final long serialVersionUID = 1490465060L;

	/** Standard Constructor */
	public X_AD_EventLog (final Properties ctx, final int AD_EventLog_ID, @Nullable final String trxName)
	{
		super (ctx, AD_EventLog_ID, trxName);
	}

	/** Load Constructor */
	public X_AD_EventLog (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
	{
		super (ctx, rs, trxName);
	}


	/** Load Meta Data */
	@Override
	protected POInfo initPO(final Properties ctx)
	{
		return POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAD_EventLog_ID (final int AD_EventLog_ID)
	{
		if (AD_EventLog_ID < 1)
			set_ValueNoCheck (COLUMNNAME_AD_EventLog_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_EventLog_ID, AD_EventLog_ID);
	}

	@Override
	public int getAD_EventLog_ID()
	{
		return get_ValueAsInt(COLUMNNAME_AD_EventLog_ID);
	}

	@Override
	public void setEvent_UUID (final String Event_UUID)
	{
		set_ValueNoCheck (COLUMNNAME_Event_UUID, Event_UUID);
	}

	@Override
	public String getEvent_UUID()
	{
		return get_ValueAsString(COLUMNNAME_Event_UUID);
	}

	@Override
	public void setEventData (final @Nullable String EventData)
	{
		set_ValueNoCheck (COLUMNNAME_EventData, EventData);
	}

	@Override
	public String getEventData()
	{
		return get_ValueAsString(COLUMNNAME_EventData);
	}

	@Override
	public void setEventTime (final @Nullable java.sql.Timestamp EventTime)
	{
		set_ValueNoCheck (COLUMNNAME_EventTime, EventTime);
	}

	@Override
	public java.sql.Timestamp getEventTime()
	{
		return get_ValueAsTimestamp(COLUMNNAME_EventTime);
	}

	@Override
	public void setEventTopicName (final @Nullable String EventTopicName)
	{
		set_Value (COLUMNNAME_EventTopicName, EventTopicName);
	}

	@Override
	public String getEventTopicName()
	{
		return get_ValueAsString(COLUMNNAME_EventTopicName);
	}

	/**
	 * EventTypeName AD_Reference_ID=540802
	 * Reference name: EventTypeName
	 */
	public static final int EVENTTYPENAME_AD_Reference_ID=540802;
	/** LOCAL = LOCAL */
	public static final String EVENTTYPENAME_LOCAL = "LOCAL";
	/** DISTRIBUTED = DISTRIBUTED */
	public static final String EVENTTYPENAME_DISTRIBUTED = "DISTRIBUTED";
	@Override
	public void setEventTypeName (final @Nullable String EventTypeName)
	{
		set_Value (COLUMNNAME_EventTypeName, EventTypeName);
	}

	@Override
	public String getEventTypeName()
	{
		return get_ValueAsString(COLUMNNAME_EventTypeName);
	}

	@Override
	public void setIsError (final boolean IsError)
	{
		set_Value (COLUMNNAME_IsError, IsError);
	}

	@Override
	public boolean isError()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsError);
	}

	@Override
	public void setIsErrorAcknowledged (final boolean IsErrorAcknowledged)
	{
		set_Value (COLUMNNAME_IsErrorAcknowledged, IsErrorAcknowledged);
	}

	@Override
	public boolean isErrorAcknowledged()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsErrorAcknowledged);
	}
}