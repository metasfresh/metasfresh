// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_ConversionRate_Rule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_ConversionRate_Rule extends org.compiere.model.PO implements I_C_ConversionRate_Rule, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1651076087L;

    /** Standard Constructor */
    public X_C_ConversionRate_Rule (final Properties ctx, final int C_ConversionRate_Rule_ID, @Nullable final String trxName)
    {
      super (ctx, C_ConversionRate_Rule_ID, trxName);
    }

    /** Load Constructor */
    public X_C_ConversionRate_Rule (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_ConversionRate_Rule_ID (final int C_ConversionRate_Rule_ID)
	{
		if (C_ConversionRate_Rule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ConversionRate_Rule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ConversionRate_Rule_ID, C_ConversionRate_Rule_ID);
	}

	@Override
	public int getC_ConversionRate_Rule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ConversionRate_Rule_ID);
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
	public void setC_Currency_To_ID (final int C_Currency_To_ID)
	{
		if (C_Currency_To_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_To_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_To_ID, C_Currency_To_ID);
	}

	@Override
	public int getC_Currency_To_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_To_ID);
	}

	@Override
	public void setMultiplyRate_Max (final @Nullable BigDecimal MultiplyRate_Max)
	{
		set_Value (COLUMNNAME_MultiplyRate_Max, MultiplyRate_Max);
	}

	@Override
	public BigDecimal getMultiplyRate_Max() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_MultiplyRate_Max);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setMultiplyRate_Min (final @Nullable BigDecimal MultiplyRate_Min)
	{
		set_Value (COLUMNNAME_MultiplyRate_Min, MultiplyRate_Min);
	}

	@Override
	public BigDecimal getMultiplyRate_Min() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_MultiplyRate_Min);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}