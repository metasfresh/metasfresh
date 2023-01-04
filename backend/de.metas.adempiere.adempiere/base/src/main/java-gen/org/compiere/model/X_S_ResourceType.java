// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_ResourceType
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_S_ResourceType extends org.compiere.model.PO implements I_S_ResourceType, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 349098420L;

    /** Standard Constructor */
    public X_S_ResourceType (final Properties ctx, final int S_ResourceType_ID, @Nullable final String trxName)
    {
      super (ctx, S_ResourceType_ID, trxName);
    }

    /** Load Constructor */
    public X_S_ResourceType (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAllowUoMFractions (final boolean AllowUoMFractions)
	{
		set_Value (COLUMNNAME_AllowUoMFractions, AllowUoMFractions);
	}

	@Override
	public boolean isAllowUoMFractions() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AllowUoMFractions);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setChargeableQty (final int ChargeableQty)
	{
		set_Value (COLUMNNAME_ChargeableQty, ChargeableQty);
	}

	@Override
	public int getChargeableQty() 
	{
		return get_ValueAsInt(COLUMNNAME_ChargeableQty);
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
	public void setIsSingleAssignment (final boolean IsSingleAssignment)
	{
		set_Value (COLUMNNAME_IsSingleAssignment, IsSingleAssignment);
	}

	@Override
	public boolean isSingleAssignment() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSingleAssignment);
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
	public void setM_Product_Category_ID (final int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, M_Product_Category_ID);
	}

	@Override
	public int getM_Product_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_ID);
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
	public void setS_ResourceType_ID (final int S_ResourceType_ID)
	{
		if (S_ResourceType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_ResourceType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_ResourceType_ID, S_ResourceType_ID);
	}

	@Override
	public int getS_ResourceType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_ResourceType_ID);
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

	@Override
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}