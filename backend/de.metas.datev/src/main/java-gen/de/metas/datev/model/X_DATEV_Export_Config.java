// Generated Model - DO NOT CHANGE
package de.metas.datev.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for DATEV_Export_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_DATEV_Export_Config extends org.compiere.model.PO implements I_DATEV_Export_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -54075521L;

    /** Standard Constructor */
    public X_DATEV_Export_Config (final Properties ctx, final int DATEV_Export_Config_ID, @Nullable final String trxName)
    {
      super (ctx, DATEV_Export_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_DATEV_Export_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAdvisorNumber (final java.lang.String AdvisorNumber)
	{
		set_Value (COLUMNNAME_AdvisorNumber, AdvisorNumber);
	}

	@Override
	public java.lang.String getAdvisorNumber() 
	{
		return get_ValueAsString(COLUMNNAME_AdvisorNumber);
	}

	@Override
	public void setChartOfAccounts (final java.lang.String ChartOfAccounts)
	{
		set_Value (COLUMNNAME_ChartOfAccounts, ChartOfAccounts);
	}

	@Override
	public java.lang.String getChartOfAccounts() 
	{
		return get_ValueAsString(COLUMNNAME_ChartOfAccounts);
	}

	@Override
	public void setChartOfAccountsNumberLength (final BigDecimal ChartOfAccountsNumberLength)
	{
		set_Value (COLUMNNAME_ChartOfAccountsNumberLength, ChartOfAccountsNumberLength);
	}

	@Override
	public BigDecimal getChartOfAccountsNumberLength() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ChartOfAccountsNumberLength);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setClientNumber (final java.lang.String ClientNumber)
	{
		set_Value (COLUMNNAME_ClientNumber, ClientNumber);
	}

	@Override
	public java.lang.String getClientNumber() 
	{
		return get_ValueAsString(COLUMNNAME_ClientNumber);
	}

	@Override
	public void setDATEV_Export_Config_ID (final int DATEV_Export_Config_ID)
	{
		if (DATEV_Export_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DATEV_Export_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DATEV_Export_Config_ID, DATEV_Export_Config_ID);
	}

	@Override
	public int getDATEV_Export_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DATEV_Export_Config_ID);
	}
}