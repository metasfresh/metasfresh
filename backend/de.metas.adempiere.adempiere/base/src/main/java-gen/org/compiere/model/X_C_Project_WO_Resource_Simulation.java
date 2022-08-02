// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Project_WO_Resource_Simulation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Project_WO_Resource_Simulation extends org.compiere.model.PO implements I_C_Project_WO_Resource_Simulation, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -864211424L;

    /** Standard Constructor */
    public X_C_Project_WO_Resource_Simulation (final Properties ctx, final int C_Project_WO_Resource_Simulation_ID, @Nullable final String trxName)
    {
      super (ctx, C_Project_WO_Resource_Simulation_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Project_WO_Resource_Simulation (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAssignDateFrom (final java.sql.Timestamp AssignDateFrom)
	{
		set_Value (COLUMNNAME_AssignDateFrom, AssignDateFrom);
	}

	@Override
	public java.sql.Timestamp getAssignDateFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_AssignDateFrom);
	}

	@Override
	public void setAssignDateFrom_Prev (final @Nullable java.sql.Timestamp AssignDateFrom_Prev)
	{
		set_Value (COLUMNNAME_AssignDateFrom_Prev, AssignDateFrom_Prev);
	}

	@Override
	public java.sql.Timestamp getAssignDateFrom_Prev() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_AssignDateFrom_Prev);
	}

	@Override
	public void setAssignDateTo (final java.sql.Timestamp AssignDateTo)
	{
		set_Value (COLUMNNAME_AssignDateTo, AssignDateTo);
	}

	@Override
	public java.sql.Timestamp getAssignDateTo() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_AssignDateTo);
	}

	@Override
	public void setAssignDateTo_Prev (final @Nullable java.sql.Timestamp AssignDateTo_Prev)
	{
		set_Value (COLUMNNAME_AssignDateTo_Prev, AssignDateTo_Prev);
	}

	@Override
	public java.sql.Timestamp getAssignDateTo_Prev() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_AssignDateTo_Prev);
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
	public org.compiere.model.I_C_Project_WO_Resource getC_Project_WO_Resource()
	{
		return get_ValueAsPO(COLUMNNAME_C_Project_WO_Resource_ID, org.compiere.model.I_C_Project_WO_Resource.class);
	}

	@Override
	public void setC_Project_WO_Resource(final org.compiere.model.I_C_Project_WO_Resource C_Project_WO_Resource)
	{
		set_ValueFromPO(COLUMNNAME_C_Project_WO_Resource_ID, org.compiere.model.I_C_Project_WO_Resource.class, C_Project_WO_Resource);
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
	public void setC_Project_WO_Resource_Simulation_ID (final int C_Project_WO_Resource_Simulation_ID)
	{
		if (C_Project_WO_Resource_Simulation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_WO_Resource_Simulation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_WO_Resource_Simulation_ID, C_Project_WO_Resource_Simulation_ID);
	}

	@Override
	public int getC_Project_WO_Resource_Simulation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_WO_Resource_Simulation_ID);
	}

	@Override
	public org.compiere.model.I_C_SimulationPlan getC_SimulationPlan()
	{
		return get_ValueAsPO(COLUMNNAME_C_SimulationPlan_ID, org.compiere.model.I_C_SimulationPlan.class);
	}

	@Override
	public void setC_SimulationPlan(final org.compiere.model.I_C_SimulationPlan C_SimulationPlan)
	{
		set_ValueFromPO(COLUMNNAME_C_SimulationPlan_ID, org.compiere.model.I_C_SimulationPlan.class, C_SimulationPlan);
	}

	@Override
	public void setC_SimulationPlan_ID (final int C_SimulationPlan_ID)
	{
		if (C_SimulationPlan_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_SimulationPlan_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_SimulationPlan_ID, C_SimulationPlan_ID);
	}

	@Override
	public int getC_SimulationPlan_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_SimulationPlan_ID);
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
	public void setIsAllDay_Prev (final boolean IsAllDay_Prev)
	{
		set_Value (COLUMNNAME_IsAllDay_Prev, IsAllDay_Prev);
	}

	@Override
	public boolean isAllDay_Prev() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllDay_Prev);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}
}