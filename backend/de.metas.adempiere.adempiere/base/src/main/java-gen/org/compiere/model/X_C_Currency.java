// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Currency
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Currency extends org.compiere.model.PO implements I_C_Currency, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1586514238L;

    /** Standard Constructor */
    public X_C_Currency (final Properties ctx, final int C_Currency_ID, @Nullable final String trxName)
    {
      super (ctx, C_Currency_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Currency (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public void setCostingPrecision (final int CostingPrecision)
	{
		set_Value (COLUMNNAME_CostingPrecision, CostingPrecision);
	}

	@Override
	public int getCostingPrecision() 
	{
		return get_ValueAsInt(COLUMNNAME_CostingPrecision);
	}

	@Override
	public void setCurSymbol (final @Nullable java.lang.String CurSymbol)
	{
		set_Value (COLUMNNAME_CurSymbol, CurSymbol);
	}

	@Override
	public java.lang.String getCurSymbol() 
	{
		return get_ValueAsString(COLUMNNAME_CurSymbol);
	}

	@Override
	public void setDescription (final java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setEMUEntryDate (final @Nullable java.sql.Timestamp EMUEntryDate)
	{
		set_Value (COLUMNNAME_EMUEntryDate, EMUEntryDate);
	}

	@Override
	public java.sql.Timestamp getEMUEntryDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_EMUEntryDate);
	}

	@Override
	public void setEMURate (final @Nullable BigDecimal EMURate)
	{
		set_Value (COLUMNNAME_EMURate, EMURate);
	}

	@Override
	public BigDecimal getEMURate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_EMURate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setIsApply5CentCashRounding (final boolean IsApply5CentCashRounding)
	{
		set_Value (COLUMNNAME_IsApply5CentCashRounding, IsApply5CentCashRounding);
	}

	@Override
	public boolean isApply5CentCashRounding() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsApply5CentCashRounding);
	}

	@Override
	public void setIsEMUMember (final boolean IsEMUMember)
	{
		set_Value (COLUMNNAME_IsEMUMember, IsEMUMember);
	}

	@Override
	public boolean isEMUMember() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEMUMember);
	}

	@Override
	public void setIsEuro (final boolean IsEuro)
	{
		set_Value (COLUMNNAME_IsEuro, IsEuro);
	}

	@Override
	public boolean isEuro() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEuro);
	}

	@Override
	public void setISO_Code (final java.lang.String ISO_Code)
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	@Override
	public java.lang.String getISO_Code() 
	{
		return get_ValueAsString(COLUMNNAME_ISO_Code);
	}

	@Override
	public void setRoundOffFactor (final @Nullable BigDecimal RoundOffFactor)
	{
		set_Value (COLUMNNAME_RoundOffFactor, RoundOffFactor);
	}

	@Override
	public BigDecimal getRoundOffFactor() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_RoundOffFactor);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setStdPrecision (final int StdPrecision)
	{
		set_Value (COLUMNNAME_StdPrecision, StdPrecision);
	}

	@Override
	public int getStdPrecision() 
	{
		return get_ValueAsInt(COLUMNNAME_StdPrecision);
	}
}