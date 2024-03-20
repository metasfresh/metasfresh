// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for S_HumanResourceTestGroup
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_S_HumanResourceTestGroup extends org.compiere.model.PO implements I_S_HumanResourceTestGroup, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1398787240L;

    /** Standard Constructor */
    public X_S_HumanResourceTestGroup (final Properties ctx, final int S_HumanResourceTestGroup_ID, @Nullable final String trxName)
    {
      super (ctx, S_HumanResourceTestGroup_ID, trxName);
    }

    /** Load Constructor */
    public X_S_HumanResourceTestGroup (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDepartment (final java.lang.String Department)
	{
		set_Value (COLUMNNAME_Department, Department);
	}

	@Override
	public java.lang.String getDepartment() 
	{
		return get_ValueAsString(COLUMNNAME_Department);
	}

	@Override
	public void setGroupIdentifier (final java.lang.String GroupIdentifier)
	{
		set_Value (COLUMNNAME_GroupIdentifier, GroupIdentifier);
	}

	@Override
	public java.lang.String getGroupIdentifier() 
	{
		return get_ValueAsString(COLUMNNAME_GroupIdentifier);
	}

	@Override
	public void setIsDateSlot (final boolean IsDateSlot)
	{
		set_Value (COLUMNNAME_IsDateSlot, IsDateSlot);
	}

	@Override
	public boolean isDateSlot() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDateSlot);
	}

	@Override
	public void setIsTimeSlot (final boolean IsTimeSlot)
	{
		set_Value (COLUMNNAME_IsTimeSlot, IsTimeSlot);
	}

	@Override
	public boolean isTimeSlot() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTimeSlot);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setOnFriday (final boolean OnFriday)
	{
		set_Value (COLUMNNAME_OnFriday, OnFriday);
	}

	@Override
	public boolean isOnFriday() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OnFriday);
	}

	@Override
	public void setOnMonday (final boolean OnMonday)
	{
		set_Value (COLUMNNAME_OnMonday, OnMonday);
	}

	@Override
	public boolean isOnMonday() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OnMonday);
	}

	@Override
	public void setOnSaturday (final boolean OnSaturday)
	{
		set_Value (COLUMNNAME_OnSaturday, OnSaturday);
	}

	@Override
	public boolean isOnSaturday() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OnSaturday);
	}

	@Override
	public void setOnSunday (final boolean OnSunday)
	{
		set_Value (COLUMNNAME_OnSunday, OnSunday);
	}

	@Override
	public boolean isOnSunday() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OnSunday);
	}

	@Override
	public void setOnThursday (final boolean OnThursday)
	{
		set_Value (COLUMNNAME_OnThursday, OnThursday);
	}

	@Override
	public boolean isOnThursday() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OnThursday);
	}

	@Override
	public void setOnTuesday (final boolean OnTuesday)
	{
		set_Value (COLUMNNAME_OnTuesday, OnTuesday);
	}

	@Override
	public boolean isOnTuesday() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OnTuesday);
	}

	@Override
	public void setOnWednesday (final boolean OnWednesday)
	{
		set_Value (COLUMNNAME_OnWednesday, OnWednesday);
	}

	@Override
	public boolean isOnWednesday() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OnWednesday);
	}

	@Override
	public void setS_HumanResourceTestGroup_ID (final int S_HumanResourceTestGroup_ID)
	{
		if (S_HumanResourceTestGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_HumanResourceTestGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_HumanResourceTestGroup_ID, S_HumanResourceTestGroup_ID);
	}

	@Override
	public int getS_HumanResourceTestGroup_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_HumanResourceTestGroup_ID);
	}

	@Override
	public void setTimeSlotEnd (final @Nullable java.sql.Timestamp TimeSlotEnd)
	{
		set_Value (COLUMNNAME_TimeSlotEnd, TimeSlotEnd);
	}

	@Override
	public java.sql.Timestamp getTimeSlotEnd() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_TimeSlotEnd);
	}

	@Override
	public void setTimeSlotStart (final @Nullable java.sql.Timestamp TimeSlotStart)
	{
		set_Value (COLUMNNAME_TimeSlotStart, TimeSlotStart);
	}

	@Override
	public java.sql.Timestamp getTimeSlotStart() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_TimeSlotStart);
	}
}