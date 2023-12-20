// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for GPLR_Report_Summary
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_GPLR_Report_Summary extends org.compiere.model.PO implements I_GPLR_Report_Summary, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -689304423L;

    /** Standard Constructor */
    public X_GPLR_Report_Summary (final Properties ctx, final int GPLR_Report_Summary_ID, @Nullable final String trxName)
    {
      super (ctx, GPLR_Report_Summary_ID, trxName);
    }

    /** Load Constructor */
    public X_GPLR_Report_Summary (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setCharges_FC (final BigDecimal Charges_FC)
	{
		set_Value (COLUMNNAME_Charges_FC, Charges_FC);
	}

	@Override
	public BigDecimal getCharges_FC() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Charges_FC);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCharges_LC (final BigDecimal Charges_LC)
	{
		set_Value (COLUMNNAME_Charges_LC, Charges_LC);
	}

	@Override
	public BigDecimal getCharges_LC() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Charges_LC);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCOGS_LC (final BigDecimal COGS_LC)
	{
		set_Value (COLUMNNAME_COGS_LC, COGS_LC);
	}

	@Override
	public BigDecimal getCOGS_LC() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_COGS_LC);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setEstimated_FC (final BigDecimal Estimated_FC)
	{
		set_Value (COLUMNNAME_Estimated_FC, Estimated_FC);
	}

	@Override
	public BigDecimal getEstimated_FC() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Estimated_FC);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setEstimated_LC (final BigDecimal Estimated_LC)
	{
		set_Value (COLUMNNAME_Estimated_LC, Estimated_LC);
	}

	@Override
	public BigDecimal getEstimated_LC() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Estimated_LC);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setForeignCurrencyCode (final java.lang.String ForeignCurrencyCode)
	{
		set_Value (COLUMNNAME_ForeignCurrencyCode, ForeignCurrencyCode);
	}

	@Override
	public java.lang.String getForeignCurrencyCode() 
	{
		return get_ValueAsString(COLUMNNAME_ForeignCurrencyCode);
	}

	@Override
	public org.compiere.model.I_GPLR_Report getGPLR_Report()
	{
		return get_ValueAsPO(COLUMNNAME_GPLR_Report_ID, org.compiere.model.I_GPLR_Report.class);
	}

	@Override
	public void setGPLR_Report(final org.compiere.model.I_GPLR_Report GPLR_Report)
	{
		set_ValueFromPO(COLUMNNAME_GPLR_Report_ID, org.compiere.model.I_GPLR_Report.class, GPLR_Report);
	}

	@Override
	public void setGPLR_Report_ID (final int GPLR_Report_ID)
	{
		if (GPLR_Report_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_ID, GPLR_Report_ID);
	}

	@Override
	public int getGPLR_Report_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GPLR_Report_ID);
	}

	@Override
	public void setGPLR_Report_Summary_ID (final int GPLR_Report_Summary_ID)
	{
		if (GPLR_Report_Summary_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_Summary_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_Summary_ID, GPLR_Report_Summary_ID);
	}

	@Override
	public int getGPLR_Report_Summary_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GPLR_Report_Summary_ID);
	}

	@Override
	public void setLocalCurrencyCode (final java.lang.String LocalCurrencyCode)
	{
		set_Value (COLUMNNAME_LocalCurrencyCode, LocalCurrencyCode);
	}

	@Override
	public java.lang.String getLocalCurrencyCode() 
	{
		return get_ValueAsString(COLUMNNAME_LocalCurrencyCode);
	}

	@Override
	public void setProfitOrLoss_LC (final BigDecimal ProfitOrLoss_LC)
	{
		set_Value (COLUMNNAME_ProfitOrLoss_LC, ProfitOrLoss_LC);
	}

	@Override
	public BigDecimal getProfitOrLoss_LC() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ProfitOrLoss_LC);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setProfitRate (final BigDecimal ProfitRate)
	{
		set_Value (COLUMNNAME_ProfitRate, ProfitRate);
	}

	@Override
	public BigDecimal getProfitRate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ProfitRate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSales_FC (final BigDecimal Sales_FC)
	{
		set_Value (COLUMNNAME_Sales_FC, Sales_FC);
	}

	@Override
	public BigDecimal getSales_FC() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Sales_FC);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSales_LC (final BigDecimal Sales_LC)
	{
		set_Value (COLUMNNAME_Sales_LC, Sales_LC);
	}

	@Override
	public BigDecimal getSales_LC() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Sales_LC);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTaxes_LC (final BigDecimal Taxes_LC)
	{
		set_Value (COLUMNNAME_Taxes_LC, Taxes_LC);
	}

	@Override
	public BigDecimal getTaxes_LC() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Taxes_LC);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}