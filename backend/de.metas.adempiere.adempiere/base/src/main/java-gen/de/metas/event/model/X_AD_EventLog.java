/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

// Generated Model - DO NOT CHANGE
package de.metas.event.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_EventLog
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_EventLog extends org.compiere.model.PO implements I_AD_EventLog, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1783537854L;

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
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
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
	public void setC_Queue_WorkPackage_ID (final int C_Queue_WorkPackage_ID)
	{
		if (C_Queue_WorkPackage_ID < 1) 
			set_Value (COLUMNNAME_C_Queue_WorkPackage_ID, null);
		else 
			set_Value (COLUMNNAME_C_Queue_WorkPackage_ID, C_Queue_WorkPackage_ID);
	}

	@Override
	public int getC_Queue_WorkPackage_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Queue_WorkPackage_ID);
	}

	@Override
	public void setEventData (final @Nullable java.lang.String EventData)
	{
		set_ValueNoCheck (COLUMNNAME_EventData, EventData);
	}

	@Override
	public java.lang.String getEventData() 
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
	public void setEventTopicName (final @Nullable java.lang.String EventTopicName)
	{
		set_Value (COLUMNNAME_EventTopicName, EventTopicName);
	}

	@Override
	public java.lang.String getEventTopicName() 
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
	/** REMOTE = REMOTE */
	public static final String EVENTTYPENAME_REMOTE = "REMOTE";
	@Override
	public void setEventTypeName (final @Nullable java.lang.String EventTypeName)
	{
		set_Value (COLUMNNAME_EventTypeName, EventTypeName);
	}

	@Override
	public java.lang.String getEventTypeName() 
	{
		return get_ValueAsString(COLUMNNAME_EventTypeName);
	}

	@Override
	public void setEvent_UUID (final java.lang.String Event_UUID)
	{
		set_ValueNoCheck (COLUMNNAME_Event_UUID, Event_UUID);
	}

	@Override
	public java.lang.String getEvent_UUID() 
	{
		return get_ValueAsString(COLUMNNAME_Event_UUID);
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