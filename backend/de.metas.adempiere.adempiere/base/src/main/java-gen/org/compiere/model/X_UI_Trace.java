// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for UI_Trace
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_UI_Trace extends org.compiere.model.PO implements I_UI_Trace, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1790682355L;

    /** Standard Constructor */
    public X_UI_Trace (final Properties ctx, final int UI_Trace_ID, @Nullable final String trxName)
    {
      super (ctx, UI_Trace_ID, trxName);
    }

    /** Load Constructor */
    public X_UI_Trace (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setEventData (final @Nullable java.lang.String EventData)
	{
		set_Value (COLUMNNAME_EventData, EventData);
	}

	@Override
	public java.lang.String getEventData() 
	{
		return get_ValueAsString(COLUMNNAME_EventData);
	}

	@Override
	public void setEventName (final java.lang.String EventName)
	{
		set_Value (COLUMNNAME_EventName, EventName);
	}

	@Override
	public java.lang.String getEventName() 
	{
		return get_ValueAsString(COLUMNNAME_EventName);
	}

	@Override
	public void setExternalId (final java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public java.lang.String getExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalId);
	}

	@Override
	public void setTimestamp (final java.sql.Timestamp Timestamp)
	{
		set_Value (COLUMNNAME_Timestamp, Timestamp);
	}

	@Override
	public java.sql.Timestamp getTimestamp() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_Timestamp);
	}

	@Override
	public void setUI_Trace_ID (final int UI_Trace_ID)
	{
		if (UI_Trace_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UI_Trace_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UI_Trace_ID, UI_Trace_ID);
	}

	@Override
	public int getUI_Trace_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_UI_Trace_ID);
	}
}