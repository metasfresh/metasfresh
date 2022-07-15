// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_SimulationPlan
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_SimulationPlan extends org.compiere.model.PO implements I_C_SimulationPlan, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 880106132L;

    /** Standard Constructor */
    public X_C_SimulationPlan (final Properties ctx, final int C_SimulationPlan_ID, @Nullable final String trxName)
    {
      super (ctx, C_SimulationPlan_ID, trxName);
    }

    /** Load Constructor */
    public X_C_SimulationPlan (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_User_Responsible_ID (final int AD_User_Responsible_ID)
	{
		if (AD_User_Responsible_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_Responsible_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_Responsible_ID, AD_User_Responsible_ID);
	}

	@Override
	public int getAD_User_Responsible_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_Responsible_ID);
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