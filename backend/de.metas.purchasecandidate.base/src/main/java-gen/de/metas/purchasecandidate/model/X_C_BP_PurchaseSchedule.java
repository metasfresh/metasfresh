// Generated Model - DO NOT CHANGE
package de.metas.purchasecandidate.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BP_PurchaseSchedule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BP_PurchaseSchedule extends org.compiere.model.PO implements I_C_BP_PurchaseSchedule, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -147685794L;

    /** Standard Constructor */
    public X_C_BP_PurchaseSchedule (final Properties ctx, final int C_BP_PurchaseSchedule_ID, @Nullable final String trxName)
    {
      super (ctx, C_BP_PurchaseSchedule_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BP_PurchaseSchedule (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BP_PurchaseSchedule_ID (final int C_BP_PurchaseSchedule_ID)
	{
		if (C_BP_PurchaseSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_PurchaseSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_PurchaseSchedule_ID, C_BP_PurchaseSchedule_ID);
	}

	@Override
	public int getC_BP_PurchaseSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_PurchaseSchedule_ID);
	}

	@Override
	public org.compiere.model.I_C_Calendar getC_Calendar()
	{
		return get_ValueAsPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class);
	}

	@Override
	public void setC_Calendar(final org.compiere.model.I_C_Calendar C_Calendar)
	{
		set_ValueFromPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class, C_Calendar);
	}

	@Override
	public void setC_Calendar_ID (final int C_Calendar_ID)
	{
		if (C_Calendar_ID < 1) 
			set_Value (COLUMNNAME_C_Calendar_ID, null);
		else 
			set_Value (COLUMNNAME_C_Calendar_ID, C_Calendar_ID);
	}

	@Override
	public int getC_Calendar_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Calendar_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setFrequency (final int Frequency)
	{
		set_Value (COLUMNNAME_Frequency, Frequency);
	}

	@Override
	public int getFrequency() 
	{
		return get_ValueAsInt(COLUMNNAME_Frequency);
	}

	/** 
	 * FrequencyType AD_Reference_ID=540859
	 * Reference name: C_BP_PurchaseSchedule_FrequencyType
	 */
	public static final int FREQUENCYTYPE_AD_Reference_ID=540859;
	/** Weekly = W */
	public static final String FREQUENCYTYPE_Weekly = "W";
	/** Monthly = M */
	public static final String FREQUENCYTYPE_Monthly = "M";
	@Override
	public void setFrequencyType (final java.lang.String FrequencyType)
	{
		set_Value (COLUMNNAME_FrequencyType, FrequencyType);
	}

	@Override
	public java.lang.String getFrequencyType() 
	{
		return get_ValueAsString(COLUMNNAME_FrequencyType);
	}

	@Override
	public void setLeadTimeOffset (final int LeadTimeOffset)
	{
		set_Value (COLUMNNAME_LeadTimeOffset, LeadTimeOffset);
	}

	@Override
	public int getLeadTimeOffset() 
	{
		return get_ValueAsInt(COLUMNNAME_LeadTimeOffset);
	}

	@Override
	public void setMonthDay (final int MonthDay)
	{
		set_Value (COLUMNNAME_MonthDay, MonthDay);
	}

	@Override
	public int getMonthDay() 
	{
		return get_ValueAsInt(COLUMNNAME_MonthDay);
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
	public void setPreparationTime_1 (final @Nullable java.sql.Timestamp PreparationTime_1)
	{
		set_Value (COLUMNNAME_PreparationTime_1, PreparationTime_1);
	}

	@Override
	public java.sql.Timestamp getPreparationTime_1() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PreparationTime_1);
	}

	@Override
	public void setPreparationTime_2 (final @Nullable java.sql.Timestamp PreparationTime_2)
	{
		set_Value (COLUMNNAME_PreparationTime_2, PreparationTime_2);
	}

	@Override
	public java.sql.Timestamp getPreparationTime_2() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PreparationTime_2);
	}

	@Override
	public void setPreparationTime_3 (final @Nullable java.sql.Timestamp PreparationTime_3)
	{
		set_Value (COLUMNNAME_PreparationTime_3, PreparationTime_3);
	}

	@Override
	public java.sql.Timestamp getPreparationTime_3() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PreparationTime_3);
	}

	@Override
	public void setPreparationTime_4 (final @Nullable java.sql.Timestamp PreparationTime_4)
	{
		set_Value (COLUMNNAME_PreparationTime_4, PreparationTime_4);
	}

	@Override
	public java.sql.Timestamp getPreparationTime_4() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PreparationTime_4);
	}

	@Override
	public void setPreparationTime_5 (final @Nullable java.sql.Timestamp PreparationTime_5)
	{
		set_Value (COLUMNNAME_PreparationTime_5, PreparationTime_5);
	}

	@Override
	public java.sql.Timestamp getPreparationTime_5() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PreparationTime_5);
	}

	@Override
	public void setPreparationTime_6 (final @Nullable java.sql.Timestamp PreparationTime_6)
	{
		set_Value (COLUMNNAME_PreparationTime_6, PreparationTime_6);
	}

	@Override
	public java.sql.Timestamp getPreparationTime_6() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PreparationTime_6);
	}

	@Override
	public void setPreparationTime_7 (final @Nullable java.sql.Timestamp PreparationTime_7)
	{
		set_Value (COLUMNNAME_PreparationTime_7, PreparationTime_7);
	}

	@Override
	public java.sql.Timestamp getPreparationTime_7() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PreparationTime_7);
	}

	@Override
	public void setReminderTimeInMin (final int ReminderTimeInMin)
	{
		set_Value (COLUMNNAME_ReminderTimeInMin, ReminderTimeInMin);
	}

	@Override
	public int getReminderTimeInMin() 
	{
		return get_ValueAsInt(COLUMNNAME_ReminderTimeInMin);
	}

	@Override
	public void setValidFrom (final java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	@Override
	public java.sql.Timestamp getValidFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidFrom);
	}
}