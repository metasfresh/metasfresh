// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Project_Resource_Budget_Simulation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Project_Resource_Budget_Simulation extends org.compiere.model.PO implements I_C_Project_Resource_Budget_Simulation, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1345973835L;

    /** Standard Constructor */
    public X_C_Project_Resource_Budget_Simulation (final Properties ctx, final int C_Project_Resource_Budget_Simulation_ID, @Nullable final String trxName)
    {
      super (ctx, C_Project_Resource_Budget_Simulation_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Project_Resource_Budget_Simulation (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
			set_ValueNoCheck (COLUMNNAME_C_Project_Resource_Budget_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_Resource_Budget_ID, C_Project_Resource_Budget_ID);
	}

	@Override
	public int getC_Project_Resource_Budget_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_Resource_Budget_ID);
	}

	@Override
	public void setC_Project_Resource_Budget_Simulation_ID (final int C_Project_Resource_Budget_Simulation_ID)
	{
		if (C_Project_Resource_Budget_Simulation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_Resource_Budget_Simulation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_Resource_Budget_Simulation_ID, C_Project_Resource_Budget_Simulation_ID);
	}

	@Override
	public int getC_Project_Resource_Budget_Simulation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_Resource_Budget_Simulation_ID);
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
	public void setDateFinishPlan (final java.sql.Timestamp DateFinishPlan)
	{
		set_Value (COLUMNNAME_DateFinishPlan, DateFinishPlan);
	}

	@Override
	public java.sql.Timestamp getDateFinishPlan() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateFinishPlan);
	}

	@Override
	public void setDateFinishPlan_Prev (final @Nullable java.sql.Timestamp DateFinishPlan_Prev)
	{
		set_Value (COLUMNNAME_DateFinishPlan_Prev, DateFinishPlan_Prev);
	}

	@Override
	public java.sql.Timestamp getDateFinishPlan_Prev() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateFinishPlan_Prev);
	}

	@Override
	public void setDateStartPlan (final java.sql.Timestamp DateStartPlan)
	{
		set_Value (COLUMNNAME_DateStartPlan, DateStartPlan);
	}

	@Override
	public java.sql.Timestamp getDateStartPlan() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateStartPlan);
	}

	@Override
	public void setDateStartPlan_Prev (final @Nullable java.sql.Timestamp DateStartPlan_Prev)
	{
		set_Value (COLUMNNAME_DateStartPlan_Prev, DateStartPlan_Prev);
	}

	@Override
	public java.sql.Timestamp getDateStartPlan_Prev() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateStartPlan_Prev);
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