// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Project_WO_Resource
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Project_WO_Resource extends org.compiere.model.PO implements I_C_Project_WO_Resource, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 472261246L;

    /** Standard Constructor */
    public X_C_Project_WO_Resource (final Properties ctx, final int C_Project_WO_Resource_ID, @Nullable final String trxName)
    {
      super (ctx, C_Project_WO_Resource_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Project_WO_Resource (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAssignDateFrom (final @Nullable java.sql.Timestamp AssignDateFrom)
	{
		set_Value (COLUMNNAME_AssignDateFrom, AssignDateFrom);
	}

	@Override
	public java.sql.Timestamp getAssignDateFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_AssignDateFrom);
	}

	@Override
	public void setAssignDateTo (final @Nullable java.sql.Timestamp AssignDateTo)
	{
		set_Value (COLUMNNAME_AssignDateTo, AssignDateTo);
	}

	@Override
	public java.sql.Timestamp getAssignDateTo() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_AssignDateTo);
	}

	@Override
	public void setBudget_Project_ID (final int Budget_Project_ID)
	{
		if (Budget_Project_ID < 1) 
			set_Value (COLUMNNAME_Budget_Project_ID, null);
		else 
			set_Value (COLUMNNAME_Budget_Project_ID, Budget_Project_ID);
	}

	@Override
	public int getBudget_Project_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Budget_Project_ID);
	}

	@Override
	public void setC_Project_ID (final int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, C_Project_ID);
	}

	@Override
	public int getC_Project_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_ID);
	}

	@Override
	public org.compiere.model.I_C_Project_Resource_Budget getC_Project_Resource_Budget()
	{
		return get_ValueAsPO(COLUMNNAME_C_Project_Resource_Budget_ID, org.compiere.model.I_C_Project_Resource_Budget.class);
	}

	@Override
	public void setC_Project_Resource_Budget(final org.compiere.model.I_C_Project_Resource_Budget C_Project_Resource_Budget)
	{
		set_ValueFromPO(COLUMNNAME_C_Project_Resource_Budget_ID, org.compiere.model.I_C_Project_Resource_Budget.class, C_Project_Resource_Budget);
	}

	@Override
	public void setC_Project_Resource_Budget_ID (final int C_Project_Resource_Budget_ID)
	{
		if (C_Project_Resource_Budget_ID < 1) 
			set_Value (COLUMNNAME_C_Project_Resource_Budget_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_Resource_Budget_ID, C_Project_Resource_Budget_ID);
	}

	@Override
	public int getC_Project_Resource_Budget_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_Resource_Budget_ID);
	}

	@Override
	public void setC_Project_WO_Resource_ID (final int C_Project_WO_Resource_ID)
	{
		if (C_Project_WO_Resource_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_WO_Resource_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_WO_Resource_ID, C_Project_WO_Resource_ID);
	}

	@Override
	public int getC_Project_WO_Resource_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_WO_Resource_ID);
	}

	@Override
	public org.compiere.model.I_C_Project_WO_Step getC_Project_WO_Step()
	{
		return get_ValueAsPO(COLUMNNAME_C_Project_WO_Step_ID, org.compiere.model.I_C_Project_WO_Step.class);
	}

	@Override
	public void setC_Project_WO_Step(final org.compiere.model.I_C_Project_WO_Step C_Project_WO_Step)
	{
		set_ValueFromPO(COLUMNNAME_C_Project_WO_Step_ID, org.compiere.model.I_C_Project_WO_Step.class, C_Project_WO_Step);
	}

	@Override
	public void setC_Project_WO_Step_ID (final int C_Project_WO_Step_ID)
	{
		if (C_Project_WO_Step_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_WO_Step_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_WO_Step_ID, C_Project_WO_Step_ID);
	}

	@Override
	public int getC_Project_WO_Step_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_WO_Step_ID);
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
	public void setDuration (final BigDecimal Duration)
	{
		set_Value (COLUMNNAME_Duration, Duration);
	}

	@Override
	public BigDecimal getDuration() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Duration);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * DurationUnit AD_Reference_ID=299
	 * Reference name: WF_DurationUnit
	 */
	public static final int DURATIONUNIT_AD_Reference_ID=299;
	/** Year = Y */
	public static final String DURATIONUNIT_Year = "Y";
	/** Month = M */
	public static final String DURATIONUNIT_Month = "M";
	/** Day = D */
	public static final String DURATIONUNIT_Day = "D";
	/** Hour = h */
	public static final String DURATIONUNIT_Hour = "h";
	/** Minute = m */
	public static final String DURATIONUNIT_Minute = "m";
	/** Second = s */
	public static final String DURATIONUNIT_Second = "s";
	@Override
	public void setDurationUnit (final java.lang.String DurationUnit)
	{
		set_Value (COLUMNNAME_DurationUnit, DurationUnit);
	}

	@Override
	public java.lang.String getDurationUnit() 
	{
		return get_ValueAsString(COLUMNNAME_DurationUnit);
	}

	@Override
	public void setExternalId (final @Nullable java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public java.lang.String getExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalId);
	}

	@Override
	public void setIsAllDay (final boolean IsAllDay)
	{
		set_Value (COLUMNNAME_IsAllDay, IsAllDay);
	}

	@Override
	public boolean isAllDay() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllDay);
	}

	@Override
	public void setResolvedHours (final int ResolvedHours)
	{
		set_Value (COLUMNNAME_ResolvedHours, ResolvedHours);
	}

	@Override
	public int getResolvedHours() 
	{
		return get_ValueAsInt(COLUMNNAME_ResolvedHours);
	}

	/** 
	 * ResourceType AD_Reference_ID=541846
	 * Reference name: C_Project_WO_Resource_Type
	 */
	public static final int RESOURCETYPE_AD_Reference_ID=541846;
	/** Machine = M */
	public static final String RESOURCETYPE_Machine = "M";
	/** Human Resource = H */
	public static final String RESOURCETYPE_HumanResource = "H";
	@Override
	public void setResourceType (final java.lang.String ResourceType)
	{
		set_Value (COLUMNNAME_ResourceType, ResourceType);
	}

	@Override
	public java.lang.String getResourceType() 
	{
		return get_ValueAsString(COLUMNNAME_ResourceType);
	}

	@Override
	public void setS_Resource_ID (final int S_Resource_ID)
	{
		if (S_Resource_ID < 1) 
			set_Value (COLUMNNAME_S_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_S_Resource_ID, S_Resource_ID);
	}

	@Override
	public int getS_Resource_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_Resource_ID);
	}

	@Override
	public void setWOPlannedPersonDurationHours (final @Nullable BigDecimal WOPlannedPersonDurationHours)
	{
		throw new IllegalArgumentException ("WOPlannedPersonDurationHours is virtual column");	}

	@Override
	public BigDecimal getWOPlannedPersonDurationHours() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_WOPlannedPersonDurationHours);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setWOPlannedResourceDurationHours (final @Nullable BigDecimal WOPlannedResourceDurationHours)
	{
		throw new IllegalArgumentException ("WOPlannedResourceDurationHours is virtual column");	}

	@Override
	public BigDecimal getWOPlannedResourceDurationHours() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_WOPlannedResourceDurationHours);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setWOTestFacilityGroupName (final @Nullable java.lang.String WOTestFacilityGroupName)
	{
		set_Value (COLUMNNAME_WOTestFacilityGroupName, WOTestFacilityGroupName);
	}

	@Override
	public java.lang.String getWOTestFacilityGroupName() 
	{
		return get_ValueAsString(COLUMNNAME_WOTestFacilityGroupName);
	}
}