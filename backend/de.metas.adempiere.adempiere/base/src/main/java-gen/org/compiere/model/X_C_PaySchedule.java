// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_PaySchedule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_PaySchedule extends org.compiere.model.PO implements I_C_PaySchedule, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1107204509L;

    /** Standard Constructor */
    public X_C_PaySchedule (final Properties ctx, final int C_PaySchedule_ID, @Nullable final String trxName)
    {
      super (ctx, C_PaySchedule_ID, trxName);
    }

    /** Load Constructor */
    public X_C_PaySchedule (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_PaymentTerm_ID (final int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PaymentTerm_ID, C_PaymentTerm_ID);
	}

	@Override
	public int getC_PaymentTerm_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PaymentTerm_ID);
	}

	@Override
	public void setC_PaySchedule_ID (final int C_PaySchedule_ID)
	{
		if (C_PaySchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PaySchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PaySchedule_ID, C_PaySchedule_ID);
	}

	@Override
	public int getC_PaySchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PaySchedule_ID);
	}

	@Override
	public void setDiscount (final BigDecimal Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
	}

	@Override
	public BigDecimal getDiscount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Discount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDiscountDays (final int DiscountDays)
	{
		set_Value (COLUMNNAME_DiscountDays, DiscountDays);
	}

	@Override
	public int getDiscountDays() 
	{
		return get_ValueAsInt(COLUMNNAME_DiscountDays);
	}

	@Override
	public void setGraceDays (final int GraceDays)
	{
		set_Value (COLUMNNAME_GraceDays, GraceDays);
	}

	@Override
	public int getGraceDays() 
	{
		return get_ValueAsInt(COLUMNNAME_GraceDays);
	}

	@Override
	public void setIsValid (final boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, IsValid);
	}

	@Override
	public boolean isValid() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsValid);
	}

	/** 
	 * NetDay AD_Reference_ID=167
	 * Reference name: Weekdays
	 */
	public static final int NETDAY_AD_Reference_ID=167;
	/** Sunday = 7 */
	public static final String NETDAY_Sunday = "7";
	/** Monday = 1 */
	public static final String NETDAY_Monday = "1";
	/** Tuesday = 2 */
	public static final String NETDAY_Tuesday = "2";
	/** Wednesday = 3 */
	public static final String NETDAY_Wednesday = "3";
	/** Thursday = 4 */
	public static final String NETDAY_Thursday = "4";
	/** Friday = 5 */
	public static final String NETDAY_Friday = "5";
	/** Saturday = 6 */
	public static final String NETDAY_Saturday = "6";
	@Override
	public void setNetDay (final @Nullable java.lang.String NetDay)
	{
		set_Value (COLUMNNAME_NetDay, NetDay);
	}

	@Override
	public java.lang.String getNetDay() 
	{
		return get_ValueAsString(COLUMNNAME_NetDay);
	}

	@Override
	public void setNetDays (final int NetDays)
	{
		set_Value (COLUMNNAME_NetDays, NetDays);
	}

	@Override
	public int getNetDays() 
	{
		return get_ValueAsInt(COLUMNNAME_NetDays);
	}

	@Override
	public void setPercentage (final BigDecimal Percentage)
	{
		set_Value (COLUMNNAME_Percentage, Percentage);
	}

	@Override
	public BigDecimal getPercentage() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Percentage);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}