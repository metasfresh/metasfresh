// Generated Model - DO NOT CHANGE
package de.metas.serviceprovider.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for S_TimeBooking
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_S_TimeBooking extends org.compiere.model.PO implements I_S_TimeBooking, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 770926882L;

    /** Standard Constructor */
    public X_S_TimeBooking (final Properties ctx, final int S_TimeBooking_ID, @Nullable final String trxName)
    {
      super (ctx, S_TimeBooking_ID, trxName);
    }

    /** Load Constructor */
    public X_S_TimeBooking (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_User_Performing_ID (final int AD_User_Performing_ID)
	{
		if (AD_User_Performing_ID < 1) 
			set_Value (COLUMNNAME_AD_User_Performing_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_Performing_ID, AD_User_Performing_ID);
	}

	@Override
	public int getAD_User_Performing_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_Performing_ID);
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
	public de.metas.serviceprovider.model.I_S_Issue getS_Issue()
	{
		return get_ValueAsPO(COLUMNNAME_S_Issue_ID, de.metas.serviceprovider.model.I_S_Issue.class);
	}

	@Override
	public void setS_Issue(final de.metas.serviceprovider.model.I_S_Issue S_Issue)
	{
		set_ValueFromPO(COLUMNNAME_S_Issue_ID, de.metas.serviceprovider.model.I_S_Issue.class, S_Issue);
	}

	@Override
	public void setS_Issue_ID (final int S_Issue_ID)
	{
		if (S_Issue_ID < 1) 
			set_Value (COLUMNNAME_S_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_S_Issue_ID, S_Issue_ID);
	}

	@Override
	public int getS_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_Issue_ID);
	}

	@Override
	public void setS_TimeBooking_ID (final int S_TimeBooking_ID)
	{
		if (S_TimeBooking_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_TimeBooking_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_TimeBooking_ID, S_TimeBooking_ID);
	}

	@Override
	public int getS_TimeBooking_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_TimeBooking_ID);
	}
}