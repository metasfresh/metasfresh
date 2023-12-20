// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Sequence_No
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Sequence_No extends org.compiere.model.PO implements I_AD_Sequence_No, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2106895063L;

    /** Standard Constructor */
    public X_AD_Sequence_No (final Properties ctx, final int AD_Sequence_No_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Sequence_No_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Sequence_No (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Sequence getAD_Sequence()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Sequence_ID, org.compiere.model.I_AD_Sequence.class);
	}

	@Override
	public void setAD_Sequence(final org.compiere.model.I_AD_Sequence AD_Sequence)
	{
		set_ValueFromPO(COLUMNNAME_AD_Sequence_ID, org.compiere.model.I_AD_Sequence.class, AD_Sequence);
	}

	@Override
	public void setAD_Sequence_ID (final int AD_Sequence_ID)
	{
		if (AD_Sequence_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Sequence_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Sequence_ID, AD_Sequence_ID);
	}

	@Override
	public int getAD_Sequence_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Sequence_ID);
	}

	@Override
	public void setCalendarMonth (final @Nullable java.lang.String CalendarMonth)
	{
		set_Value (COLUMNNAME_CalendarMonth, CalendarMonth);
	}

	@Override
	public java.lang.String getCalendarMonth() 
	{
		return get_ValueAsString(COLUMNNAME_CalendarMonth);
	}

	@Override
	public void setCalendarYear (final java.lang.String CalendarYear)
	{
		set_ValueNoCheck (COLUMNNAME_CalendarYear, CalendarYear);
	}

	@Override
	public java.lang.String getCalendarYear() 
	{
		return get_ValueAsString(COLUMNNAME_CalendarYear);
	}

	@Override
	public void setCurrentNext (final int CurrentNext)
	{
		set_Value (COLUMNNAME_CurrentNext, CurrentNext);
	}

	@Override
	public int getCurrentNext() 
	{
		return get_ValueAsInt(COLUMNNAME_CurrentNext);
	}
}