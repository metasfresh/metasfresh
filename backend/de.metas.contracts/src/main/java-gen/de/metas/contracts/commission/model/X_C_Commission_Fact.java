// Generated Model - DO NOT CHANGE
package de.metas.contracts.commission.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Commission_Fact
 *  @author metasfresh (generated) 
 */
public class X_C_Commission_Fact extends org.compiere.model.PO implements I_C_Commission_Fact, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -861662769L;

    /** Standard Constructor */
    public X_C_Commission_Fact (final Properties ctx, final int C_Commission_Fact_ID, @Nullable final String trxName)
    {
      super (ctx, C_Commission_Fact_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Commission_Fact (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Commission_Fact_ID (final int C_Commission_Fact_ID)
	{
		if (C_Commission_Fact_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Fact_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Fact_ID, C_Commission_Fact_ID);
	}

	@Override
	public int getC_Commission_Fact_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Commission_Fact_ID);
	}

	@Override
	public de.metas.contracts.commission.model.I_C_Commission_Share getC_Commission_Share()
	{
		return get_ValueAsPO(COLUMNNAME_C_Commission_Share_ID, de.metas.contracts.commission.model.I_C_Commission_Share.class);
	}

	@Override
	public void setC_Commission_Share(final de.metas.contracts.commission.model.I_C_Commission_Share C_Commission_Share)
	{
		set_ValueFromPO(COLUMNNAME_C_Commission_Share_ID, de.metas.contracts.commission.model.I_C_Commission_Share.class, C_Commission_Share);
	}

	@Override
	public void setC_Commission_Share_ID (final int C_Commission_Share_ID)
	{
		if (C_Commission_Share_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Share_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Share_ID, C_Commission_Share_ID);
	}

	@Override
	public int getC_Commission_Share_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Commission_Share_ID);
	}

	@Override
	public void setC_Invoice_Candidate_Commission_ID (final int C_Invoice_Candidate_Commission_ID)
	{
		if (C_Invoice_Candidate_Commission_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_Commission_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_Commission_ID, C_Invoice_Candidate_Commission_ID);
	}

	@Override
	public int getC_Invoice_Candidate_Commission_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Candidate_Commission_ID);
	}

	/** 
	 * Commission_Fact_State AD_Reference_ID=541042
	 * Reference name: C_Commission_Fact_State
	 */
	public static final int COMMISSION_FACT_STATE_AD_Reference_ID=541042;
	/** FORECASTED = FORECASTED */
	public static final String COMMISSION_FACT_STATE_FORECASTED = "FORECASTED";
	/** INVOICEABLE = INVOICEABLE */
	public static final String COMMISSION_FACT_STATE_INVOICEABLE = "INVOICEABLE";
	/** INVOICED = INVOICED */
	public static final String COMMISSION_FACT_STATE_INVOICED = "INVOICED";
	/** SETTLED = SETTLED */
	public static final String COMMISSION_FACT_STATE_SETTLED = "SETTLED";
	/** TO_SETTLE = TO_SETTLE */
	public static final String COMMISSION_FACT_STATE_TO_SETTLE = "TO_SETTLE";
	@Override
	public void setCommission_Fact_State (final java.lang.String Commission_Fact_State)
	{
		set_ValueNoCheck (COLUMNNAME_Commission_Fact_State, Commission_Fact_State);
	}

	@Override
	public java.lang.String getCommission_Fact_State() 
	{
		return get_ValueAsString(COLUMNNAME_Commission_Fact_State);
	}

	@Override
	public void setCommissionFactTimestamp (final java.lang.String CommissionFactTimestamp)
	{
		set_ValueNoCheck (COLUMNNAME_CommissionFactTimestamp, CommissionFactTimestamp);
	}

	@Override
	public java.lang.String getCommissionFactTimestamp() 
	{
		return get_ValueAsString(COLUMNNAME_CommissionFactTimestamp);
	}

	@Override
	public void setCommissionPoints (final BigDecimal CommissionPoints)
	{
		set_ValueNoCheck (COLUMNNAME_CommissionPoints, CommissionPoints);
	}

	@Override
	public BigDecimal getCommissionPoints() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CommissionPoints);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}