// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Project_TimeBooking
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Project_TimeBooking extends org.compiere.model.PO implements I_C_Project_TimeBooking, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1474334691L;

    /** Standard Constructor */
    public X_C_Project_TimeBooking (final Properties ctx, final int C_Project_TimeBooking_ID, @Nullable final String trxName)
    {
      super (ctx, C_Project_TimeBooking_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Project_TimeBooking (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBookedDate (final java.sql.Timestamp BookedDate)
	{
		set_Value (COLUMNNAME_BookedDate, BookedDate);
	}

	@Override
	public java.sql.Timestamp getBookedDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_BookedDate);
	}

	@Override
	public void setBookedSeconds (final @Nullable BigDecimal BookedSeconds)
	{
		set_Value (COLUMNNAME_BookedSeconds, BookedSeconds);
	}

	@Override
	public BigDecimal getBookedSeconds() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_BookedSeconds);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setComments (final @Nullable java.lang.String Comments)
	{
		set_Value (COLUMNNAME_Comments, Comments);
	}

	@Override
	public java.lang.String getComments() 
	{
		return get_ValueAsString(COLUMNNAME_Comments);
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
	public void setC_Project_TimeBooking_ID (final int C_Project_TimeBooking_ID)
	{
		if (C_Project_TimeBooking_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_TimeBooking_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_TimeBooking_ID, C_Project_TimeBooking_ID);
	}

	@Override
	public int getC_Project_TimeBooking_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_TimeBooking_ID);
	}

	@Override
	public void setHoursAndMinutes (final java.lang.String HoursAndMinutes)
	{
		set_Value (COLUMNNAME_HoursAndMinutes, HoursAndMinutes);
	}

	@Override
	public java.lang.String getHoursAndMinutes() 
	{
		return get_ValueAsString(COLUMNNAME_HoursAndMinutes);
	}

	@Override
	public void setIsInvoiced (final boolean IsInvoiced)
	{
		set_Value (COLUMNNAME_IsInvoiced, IsInvoiced);
	}

	@Override
	public boolean isInvoiced() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInvoiced);
	}
}