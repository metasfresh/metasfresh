// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Project_Resource_Budget
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Project_Resource_Budget extends org.compiere.model.PO implements I_C_Project_Resource_Budget, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1429055759L;

    /** Standard Constructor */
    public X_C_Project_Resource_Budget (final Properties ctx, final int C_Project_Resource_Budget_ID, @Nullable final String trxName)
    {
      super (ctx, C_Project_Resource_Budget_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Project_Resource_Budget (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
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
	public void setC_UOM_Time_ID (final int C_UOM_Time_ID)
	{
		if (C_UOM_Time_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_Time_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_Time_ID, C_UOM_Time_ID);
	}

	@Override
	public int getC_UOM_Time_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_Time_ID);
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
	public void setPlannedAmt (final BigDecimal PlannedAmt)
	{
		set_Value (COLUMNNAME_PlannedAmt, PlannedAmt);
	}

	@Override
	public BigDecimal getPlannedAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPlannedDuration (final BigDecimal PlannedDuration)
	{
		set_Value (COLUMNNAME_PlannedDuration, PlannedDuration);
	}

	@Override
	public BigDecimal getPlannedDuration() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedDuration);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPricePerTimeUOM (final BigDecimal PricePerTimeUOM)
	{
		set_Value (COLUMNNAME_PricePerTimeUOM, PricePerTimeUOM);
	}

	@Override
	public BigDecimal getPricePerTimeUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PricePerTimeUOM);
		return bd != null ? bd : BigDecimal.ZERO;
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
			set_Value (COLUMNNAME_S_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_S_Resource_ID, S_Resource_ID);
	}

	@Override
	public int getS_Resource_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_Resource_ID);
	}
}