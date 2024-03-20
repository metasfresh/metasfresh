// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for S_Resource
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public class X_S_Resource extends org.compiere.model.PO implements I_S_Resource, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1720848993L;

    /** Standard Constructor */
    public X_S_Resource (final Properties ctx, final int S_Resource_ID, @Nullable final String trxName)
    {
      super (ctx, S_Resource_ID, trxName);
    }

    /** Load Constructor */
    public X_S_Resource (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0)
			set_Value (COLUMNNAME_AD_User_ID, null);
		else
			set_Value (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID()
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public void setCapacityPerProductionCycle (final @Nullable BigDecimal CapacityPerProductionCycle)
	{
		set_Value (COLUMNNAME_CapacityPerProductionCycle, CapacityPerProductionCycle);
	}

	@Override
	public BigDecimal getCapacityPerProductionCycle()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CapacityPerProductionCycle);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCapacityPerProductionCycle_UOM_ID (final int CapacityPerProductionCycle_UOM_ID)
	{
		if (CapacityPerProductionCycle_UOM_ID < 1)
			set_Value (COLUMNNAME_CapacityPerProductionCycle_UOM_ID, null);
		else
			set_Value (COLUMNNAME_CapacityPerProductionCycle_UOM_ID, CapacityPerProductionCycle_UOM_ID);
	}

	@Override
	public int getCapacityPerProductionCycle_UOM_ID()
	{
		return get_ValueAsInt(COLUMNNAME_CapacityPerProductionCycle_UOM_ID);
	}

	@Override
	public void setChargeableQty (final @Nullable BigDecimal ChargeableQty)
	{
		set_Value (COLUMNNAME_ChargeableQty, ChargeableQty);
	}

	@Override
	public BigDecimal getChargeableQty()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ChargeableQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.compiere.model.I_C_Workplace getC_Workplace()
	{
		return get_ValueAsPO(COLUMNNAME_C_Workplace_ID, org.compiere.model.I_C_Workplace.class);
	}

	@Override
	public void setC_Workplace(final org.compiere.model.I_C_Workplace C_Workplace)
	{
		set_ValueFromPO(COLUMNNAME_C_Workplace_ID, org.compiere.model.I_C_Workplace.class, C_Workplace);
	}

	@Override
	public void setC_Workplace_ID (final int C_Workplace_ID)
	{
		if (C_Workplace_ID < 1)
			set_Value (COLUMNNAME_C_Workplace_ID, null);
		else
			set_Value (COLUMNNAME_C_Workplace_ID, C_Workplace_ID);
	}

	@Override
	public int getC_Workplace_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Workplace_ID);
	}

	@Override
	public void setDailyCapacity (final @Nullable BigDecimal DailyCapacity)
	{
		set_Value (COLUMNNAME_DailyCapacity, DailyCapacity);
	}

	@Override
	public BigDecimal getDailyCapacity()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DailyCapacity);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setInternalName (final @Nullable java.lang.String InternalName)
	{
		set_Value (COLUMNNAME_InternalName, InternalName);
	}

	@Override
	public java.lang.String getInternalName()
	{
		return get_ValueAsString(COLUMNNAME_InternalName);
	}

	@Override
	public void setIsAvailable (final boolean IsAvailable)
	{
		set_Value (COLUMNNAME_IsAvailable, IsAvailable);
	}

	@Override
	public boolean isAvailable()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAvailable);
	}

	@Override
	public void setIsManufacturingResource (final boolean IsManufacturingResource)
	{
		set_Value (COLUMNNAME_IsManufacturingResource, IsManufacturingResource);
	}

	@Override
	public boolean isManufacturingResource()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsManufacturingResource);
	}

	/**
	 * ManufacturingResourceType AD_Reference_ID=53223
	 * Reference name: S_Resource MFG Type
	 */
	public static final int MANUFACTURINGRESOURCETYPE_AD_Reference_ID=53223;
	/** ProductionLine = PL */
	public static final String MANUFACTURINGRESOURCETYPE_ProductionLine = "PL";
	/** Plant = PT */
	public static final String MANUFACTURINGRESOURCETYPE_Plant = "PT";
	/** WorkCenter = WC */
	public static final String MANUFACTURINGRESOURCETYPE_WorkCenter = "WC";
	/** WorkStation = WS */
	public static final String MANUFACTURINGRESOURCETYPE_WorkStation = "WS";
	@Override
	public void setManufacturingResourceType (final @Nullable java.lang.String ManufacturingResourceType)
	{
		set_Value (COLUMNNAME_ManufacturingResourceType, ManufacturingResourceType);
	}

	@Override
	public java.lang.String getManufacturingResourceType()
	{
		return get_ValueAsString(COLUMNNAME_ManufacturingResourceType);
	}

	/**
	 * MRP_Exclude AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int MRP_EXCLUDE_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String MRP_EXCLUDE_Yes = "Y";
	/** No = N */
	public static final String MRP_EXCLUDE_No = "N";
	@Override
	public void setMRP_Exclude (final @Nullable java.lang.String MRP_Exclude)
	{
		set_Value (COLUMNNAME_MRP_Exclude, MRP_Exclude);
	}

	@Override
	public java.lang.String getMRP_Exclude()
	{
		return get_ValueAsString(COLUMNNAME_MRP_Exclude);
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
	public void setPercentUtilization (final BigDecimal PercentUtilization)
	{
		set_Value (COLUMNNAME_PercentUtilization, PercentUtilization);
	}

	@Override
	public BigDecimal getPercentUtilization()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PercentUtilization);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPlanningHorizon (final int PlanningHorizon)
	{
		set_Value (COLUMNNAME_PlanningHorizon, PlanningHorizon);
	}

	@Override
	public int getPlanningHorizon()
	{
		return get_ValueAsInt(COLUMNNAME_PlanningHorizon);
	}

	@Override
	public void setQueuingTime (final @Nullable BigDecimal QueuingTime)
	{
		set_Value (COLUMNNAME_QueuingTime, QueuingTime);
	}

	@Override
	public BigDecimal getQueuingTime()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QueuingTime);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.compiere.model.I_S_HumanResourceTestGroup getS_HumanResourceTestGroup()
	{
		return get_ValueAsPO(COLUMNNAME_S_HumanResourceTestGroup_ID, org.compiere.model.I_S_HumanResourceTestGroup.class);
	}

	@Override
	public void setS_HumanResourceTestGroup(final org.compiere.model.I_S_HumanResourceTestGroup S_HumanResourceTestGroup)
	{
		set_ValueFromPO(COLUMNNAME_S_HumanResourceTestGroup_ID, org.compiere.model.I_S_HumanResourceTestGroup.class, S_HumanResourceTestGroup);
	}

	@Override
	public void setS_HumanResourceTestGroup_ID (final int S_HumanResourceTestGroup_ID)
	{
		if (S_HumanResourceTestGroup_ID < 1)
			set_Value (COLUMNNAME_S_HumanResourceTestGroup_ID, null);
		else
			set_Value (COLUMNNAME_S_HumanResourceTestGroup_ID, S_HumanResourceTestGroup_ID);
	}

	@Override
	public int getS_HumanResourceTestGroup_ID()
	{
		return get_ValueAsInt(COLUMNNAME_S_HumanResourceTestGroup_ID);
	}

	@Override
	public void setS_Resource_Group_ID (final int S_Resource_Group_ID)
	{
		if (S_Resource_Group_ID < 1)
			set_Value (COLUMNNAME_S_Resource_Group_ID, null);
		else
			set_Value (COLUMNNAME_S_Resource_Group_ID, S_Resource_Group_ID);
	}

	@Override
	public int getS_Resource_Group_ID()
	{
		return get_ValueAsInt(COLUMNNAME_S_Resource_Group_ID);
	}

	@Override
	public void setS_Resource_ID (final int S_Resource_ID)
	{
		if (S_Resource_ID < 1)
			set_ValueNoCheck (COLUMNNAME_S_Resource_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_S_Resource_ID, S_Resource_ID);
	}

	@Override
	public int getS_Resource_ID()
	{
		return get_ValueAsInt(COLUMNNAME_S_Resource_ID);
	}

	@Override
	public void setS_ResourceType_ID (final int S_ResourceType_ID)
	{
		if (S_ResourceType_ID < 1)
			set_Value (COLUMNNAME_S_ResourceType_ID, null);
		else
			set_Value (COLUMNNAME_S_ResourceType_ID, S_ResourceType_ID);
	}

	@Override
	public int getS_ResourceType_ID()
	{
		return get_ValueAsInt(COLUMNNAME_S_ResourceType_ID);
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

	@Override
	public void setWaitingTime (final @Nullable BigDecimal WaitingTime)
	{
		set_Value (COLUMNNAME_WaitingTime, WaitingTime);
	}

	@Override
	public BigDecimal getWaitingTime()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_WaitingTime);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}