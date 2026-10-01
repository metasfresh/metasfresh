// Generated Model - DO NOT CHANGE
package de.metas.datev.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for DATEV_Export
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_DATEV_Export extends org.compiere.model.PO implements I_DATEV_Export, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 24376071L;

    /** Standard Constructor */
    public X_DATEV_Export (final Properties ctx, final int DATEV_Export_ID, @Nullable final String trxName)
    {
      super (ctx, DATEV_Export_ID, trxName);
    }

    /** Load Constructor */
    public X_DATEV_Export (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAdvisorNumber (final @Nullable java.lang.String AdvisorNumber)
	{
		set_ValueNoCheck (COLUMNNAME_AdvisorNumber, AdvisorNumber);
	}

	@Override
	public java.lang.String getAdvisorNumber()
	{
		return get_ValueAsString(COLUMNNAME_AdvisorNumber);
	}

	@Override
	public void setChartOfAccounts (final @Nullable java.lang.String ChartOfAccounts)
	{
		set_ValueNoCheck (COLUMNNAME_ChartOfAccounts, ChartOfAccounts);
	}

	@Override
	public java.lang.String getChartOfAccounts()
	{
		return get_ValueAsString(COLUMNNAME_ChartOfAccounts);
	}

	@Override
	public void setChartOfAccountsNumberLength (final int ChartOfAccountsNumberLength)
	{
		set_Value (COLUMNNAME_ChartOfAccountsNumberLength, ChartOfAccountsNumberLength);
	}

	@Override
	public int getChartOfAccountsNumberLength()
	{
		return get_ValueAsInt(COLUMNNAME_ChartOfAccountsNumberLength);
	}

	@Override
	public void setClientNumber (final @Nullable java.lang.String ClientNumber)
	{
		set_ValueNoCheck (COLUMNNAME_ClientNumber, ClientNumber);
	}

	@Override
	public java.lang.String getClientNumber()
	{
		return get_ValueAsString(COLUMNNAME_ClientNumber);
	}

	@Override
	public org.compiere.model.I_C_Period getC_Period()
	{
		return get_ValueAsPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class);
	}

	@Override
	public void setC_Period(final org.compiere.model.I_C_Period C_Period)
	{
		set_ValueFromPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class, C_Period);
	}

	@Override
	public void setC_Period_ID (final int C_Period_ID)
	{
		if (C_Period_ID < 1)
			set_Value (COLUMNNAME_C_Period_ID, null);
		else
			set_Value (COLUMNNAME_C_Period_ID, C_Period_ID);
	}

	@Override
	public int getC_Period_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Period_ID);
	}

	@Override
	public void setDateAcctFrom (final @Nullable java.sql.Timestamp DateAcctFrom)
	{
		set_Value (COLUMNNAME_DateAcctFrom, DateAcctFrom);
	}

	@Override
	public java.sql.Timestamp getDateAcctFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateAcctFrom);
	}

	@Override
	public void setDateAcctTo (final java.sql.Timestamp DateAcctTo)
	{
		set_Value (COLUMNNAME_DateAcctTo, DateAcctTo);
	}

	@Override
	public java.sql.Timestamp getDateAcctTo() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateAcctTo);
	}

	@Override
	public de.metas.datev.model.I_DATEV_Export_Config getDATEV_Export_Config()
	{
		return get_ValueAsPO(COLUMNNAME_DATEV_Export_Config_ID, de.metas.datev.model.I_DATEV_Export_Config.class);
	}

	@Override
	public void setDATEV_Export_Config(final de.metas.datev.model.I_DATEV_Export_Config DATEV_Export_Config)
	{
		set_ValueFromPO(COLUMNNAME_DATEV_Export_Config_ID, de.metas.datev.model.I_DATEV_Export_Config.class, DATEV_Export_Config);
	}

	@Override
	public void setDATEV_Export_Config_ID (final int DATEV_Export_Config_ID)
	{
		if (DATEV_Export_Config_ID < 1)
			set_Value (COLUMNNAME_DATEV_Export_Config_ID, null);
		else
			set_Value (COLUMNNAME_DATEV_Export_Config_ID, DATEV_Export_Config_ID);
	}

	@Override
	public int getDATEV_Export_Config_ID()
	{
		return get_ValueAsInt(COLUMNNAME_DATEV_Export_Config_ID);
	}

	@Override
	public void setDATEV_Export_ID (final int DATEV_Export_ID)
	{
		if (DATEV_Export_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DATEV_Export_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DATEV_Export_ID, DATEV_Export_ID);
	}

	@Override
	public int getDATEV_Export_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DATEV_Export_ID);
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
	public void setIsExcludeAlreadyExported (final boolean IsExcludeAlreadyExported)
	{
		set_Value (COLUMNNAME_IsExcludeAlreadyExported, IsExcludeAlreadyExported);
	}

	@Override
	public boolean isExcludeAlreadyExported() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsExcludeAlreadyExported);
	}

	@Override
	public void setIsNegateInboundAmounts (final boolean IsNegateInboundAmounts)
	{
		set_Value (COLUMNNAME_IsNegateInboundAmounts, IsNegateInboundAmounts);
	}

	@Override
	public boolean isNegateInboundAmounts() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsNegateInboundAmounts);
	}

	@Override
	public void setIsPlaceBPAccountsOnCredit (final boolean IsPlaceBPAccountsOnCredit)
	{
		set_Value (COLUMNNAME_IsPlaceBPAccountsOnCredit, IsPlaceBPAccountsOnCredit);
	}

	@Override
	public boolean isPlaceBPAccountsOnCredit() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPlaceBPAccountsOnCredit);
	}

	/** 
	 * IsSOTrx AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISSOTRX_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISSOTRX_Yes = "Y";
	/** No = N */
	public static final String ISSOTRX_No = "N";
	@Override
	public void setIsSOTrx (final @Nullable java.lang.String IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, IsSOTrx);
	}

	@Override
	public java.lang.String getIsSOTrx() 
	{
		return get_ValueAsString(COLUMNNAME_IsSOTrx);
	}

	@Override
	public void setIsSwitchCreditMemo (final boolean IsSwitchCreditMemo)
	{
		set_Value (COLUMNNAME_IsSwitchCreditMemo, IsSwitchCreditMemo);
	}

	@Override
	public boolean isSwitchCreditMemo() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSwitchCreditMemo);
	}

	@Override
	public void setOrigin (final @Nullable java.lang.String Origin)
	{
		set_ValueNoCheck (COLUMNNAME_Origin, Origin);
	}

	@Override
	public java.lang.String getOrigin()
	{
		return get_ValueAsString(COLUMNNAME_Origin);
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