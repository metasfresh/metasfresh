// Generated Model - DO NOT CHANGE
package de.metas.serviceprovider.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_EffortControl
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_S_EffortControl extends org.compiere.model.PO implements I_S_EffortControl, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1790612697L;

    /** Standard Constructor */
    public X_S_EffortControl (final Properties ctx, final int S_EffortControl_ID, @Nullable final String trxName)
    {
      super (ctx, S_EffortControl_ID, trxName);
    }

    /** Load Constructor */
    public X_S_EffortControl (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBudget (final @Nullable BigDecimal Budget)
	{
		set_Value (COLUMNNAME_Budget, Budget);
	}

	@Override
	public BigDecimal getBudget() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Budget);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setC_Activity_ID (final int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, C_Activity_ID);
	}

	@Override
	public int getC_Activity_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Activity_ID);
	}

	@Override
	public void setC_Project_ID (final int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, C_Project_ID);
	}

	@Override
	public int getC_Project_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_ID);
	}

	@Override
	public void setEffortAggregationKey (final java.lang.String EffortAggregationKey)
	{
		set_Value (COLUMNNAME_EffortAggregationKey, EffortAggregationKey);
	}

	@Override
	public java.lang.String getEffortAggregationKey() 
	{
		return get_ValueAsString(COLUMNNAME_EffortAggregationKey);
	}

	@Override
	public void setEffortSum (final @Nullable java.lang.String EffortSum)
	{
		set_Value (COLUMNNAME_EffortSum, EffortSum);
	}

	@Override
	public java.lang.String getEffortSum() 
	{
		return get_ValueAsString(COLUMNNAME_EffortSum);
	}

	@Override
	public void setEffortSumSeconds (final @Nullable BigDecimal EffortSumSeconds)
	{
		set_Value (COLUMNNAME_EffortSumSeconds, EffortSumSeconds);
	}

	@Override
	public BigDecimal getEffortSumSeconds() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_EffortSumSeconds);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setInvoiceableHours (final @Nullable BigDecimal InvoiceableHours)
	{
		set_Value (COLUMNNAME_InvoiceableHours, InvoiceableHours);
	}

	@Override
	public BigDecimal getInvoiceableHours() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_InvoiceableHours);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setIsIssueClosed (final boolean IsIssueClosed)
	{
		throw new IllegalArgumentException ("IsIssueClosed is virtual column");	}

	@Override
	public boolean isIssueClosed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsIssueClosed);
	}

	@Override
	public void setIsOverBudget (final boolean IsOverBudget)
	{
		set_Value (COLUMNNAME_IsOverBudget, IsOverBudget);
	}

	@Override
	public boolean isOverBudget() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOverBudget);
	}

	@Override
	public void setPendingEffortSum (final @Nullable java.lang.String PendingEffortSum)
	{
		set_Value (COLUMNNAME_PendingEffortSum, PendingEffortSum);
	}

	@Override
	public java.lang.String getPendingEffortSum() 
	{
		return get_ValueAsString(COLUMNNAME_PendingEffortSum);
	}

	@Override
	public void setPendingEffortSumSeconds (final @Nullable BigDecimal PendingEffortSumSeconds)
	{
		set_Value (COLUMNNAME_PendingEffortSumSeconds, PendingEffortSumSeconds);
	}

	@Override
	public BigDecimal getPendingEffortSumSeconds() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PendingEffortSumSeconds);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setS_EffortControl_ID (final int S_EffortControl_ID)
	{
		if (S_EffortControl_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_EffortControl_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_EffortControl_ID, S_EffortControl_ID);
	}

	@Override
	public int getS_EffortControl_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_EffortControl_ID);
	}
}