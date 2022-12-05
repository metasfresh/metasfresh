// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Project_WO_Step_Simulation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Project_WO_Step_Simulation extends org.compiere.model.PO implements I_C_Project_WO_Step_Simulation, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1569300638L;

    /** Standard Constructor */
    public X_C_Project_WO_Step_Simulation (final Properties ctx, final int C_Project_WO_Step_Simulation_ID, @Nullable final String trxName)
    {
      super (ctx, C_Project_WO_Step_Simulation_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Project_WO_Step_Simulation (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Project_WO_Step_Simulation_ID (final int C_Project_WO_Step_Simulation_ID)
	{
		if (C_Project_WO_Step_Simulation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_WO_Step_Simulation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_WO_Step_Simulation_ID, C_Project_WO_Step_Simulation_ID);
	}

	@Override
	public int getC_Project_WO_Step_Simulation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_WO_Step_Simulation_ID);
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
	public void setDateEnd (final java.sql.Timestamp DateEnd)
	{
		set_Value (COLUMNNAME_DateEnd, DateEnd);
	}

	@Override
	public java.sql.Timestamp getDateEnd() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateEnd);
	}

	@Override
	public void setDateStart (final java.sql.Timestamp DateStart)
	{
		set_Value (COLUMNNAME_DateStart, DateStart);
	}

	@Override
	public java.sql.Timestamp getDateStart() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateStart);
	}
}