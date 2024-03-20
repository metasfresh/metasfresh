// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Project_WO_Resource_Conflict
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Project_WO_Resource_Conflict extends org.compiere.model.PO implements I_C_Project_WO_Resource_Conflict, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -248514818L;

    /** Standard Constructor */
    public X_C_Project_WO_Resource_Conflict (final Properties ctx, final int C_Project_WO_Resource_Conflict_ID, @Nullable final String trxName)
    {
      super (ctx, C_Project_WO_Resource_Conflict_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Project_WO_Resource_Conflict (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Project2_ID (final int C_Project2_ID)
	{
		if (C_Project2_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project2_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project2_ID, C_Project2_ID);
	}

	@Override
	public int getC_Project2_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project2_ID);
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
	public org.compiere.model.I_C_Project_WO_Resource getC_Project_WO_Resource2()
	{
		return get_ValueAsPO(COLUMNNAME_C_Project_WO_Resource2_ID, org.compiere.model.I_C_Project_WO_Resource.class);
	}

	@Override
	public void setC_Project_WO_Resource2(final org.compiere.model.I_C_Project_WO_Resource C_Project_WO_Resource2)
	{
		set_ValueFromPO(COLUMNNAME_C_Project_WO_Resource2_ID, org.compiere.model.I_C_Project_WO_Resource.class, C_Project_WO_Resource2);
	}

	@Override
	public void setC_Project_WO_Resource2_ID (final int C_Project_WO_Resource2_ID)
	{
		if (C_Project_WO_Resource2_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_WO_Resource2_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_WO_Resource2_ID, C_Project_WO_Resource2_ID);
	}

	@Override
	public int getC_Project_WO_Resource2_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_WO_Resource2_ID);
	}

	@Override
	public void setC_Project_WO_Resource_Conflict_ID (final int C_Project_WO_Resource_Conflict_ID)
	{
		if (C_Project_WO_Resource_Conflict_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_WO_Resource_Conflict_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_WO_Resource_Conflict_ID, C_Project_WO_Resource_Conflict_ID);
	}

	@Override
	public int getC_Project_WO_Resource_Conflict_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_WO_Resource_Conflict_ID);
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
	public void setIsApproved (final boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, IsApproved);
	}

	@Override
	public boolean isApproved() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsApproved);
	}

	/** 
	 * Status AD_Reference_ID=541601
	 * Reference name: C_Project_WO_Resource_Conflict_Status
	 */
	public static final int STATUS_AD_Reference_ID=541601;
	/** Conflict = C */
	public static final String STATUS_Conflict = "C";
	/** Resolved = R */
	public static final String STATUS_Resolved = "R";
	@Override
	public void setStatus (final java.lang.String Status)
	{
		set_Value (COLUMNNAME_Status, Status);
	}

	@Override
	public java.lang.String getStatus() 
	{
		return get_ValueAsString(COLUMNNAME_Status);
	}
}