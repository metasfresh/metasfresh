// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for Carrier_ShipmentOrder_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Carrier_ShipmentOrder_Log extends org.compiere.model.PO implements I_Carrier_ShipmentOrder_Log, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 115259953L;

    /** Standard Constructor */
    public X_Carrier_ShipmentOrder_Log (final Properties ctx, final int Carrier_ShipmentOrder_Log_ID, @Nullable final String trxName)
    {
      super (ctx, Carrier_ShipmentOrder_Log_ID, trxName);
    }

    /** Load Constructor */
    public X_Carrier_ShipmentOrder_Log (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setCarrier_ShipmentOrder_ID (final int Carrier_ShipmentOrder_ID)
	{
		if (Carrier_ShipmentOrder_ID < 1) 
			set_Value (COLUMNNAME_Carrier_ShipmentOrder_ID, null);
		else 
			set_Value (COLUMNNAME_Carrier_ShipmentOrder_ID, Carrier_ShipmentOrder_ID);
	}

	@Override
	public int getCarrier_ShipmentOrder_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Carrier_ShipmentOrder_ID);
	}

	@Override
	public void setCarrier_ShipmentOrder_Log_ID (final int Carrier_ShipmentOrder_Log_ID)
	{
		if (Carrier_ShipmentOrder_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Carrier_ShipmentOrder_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Carrier_ShipmentOrder_Log_ID, Carrier_ShipmentOrder_Log_ID);
	}

	@Override
	public int getCarrier_ShipmentOrder_Log_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Carrier_ShipmentOrder_Log_ID);
	}

	@Override
	public void setDurationMillis (final int DurationMillis)
	{
		set_Value (COLUMNNAME_DurationMillis, DurationMillis);
	}

	@Override
	public int getDurationMillis() 
	{
		return get_ValueAsInt(COLUMNNAME_DurationMillis);
	}

	@Override
	public void setIsError (final boolean IsError)
	{
		set_Value (COLUMNNAME_IsError, IsError);
	}

	@Override
	public boolean isError() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsError);
	}

	@Override
	public void setRequestID (final java.lang.String RequestID)
	{
		set_Value (COLUMNNAME_RequestID, RequestID);
	}

	@Override
	public java.lang.String getRequestID() 
	{
		return get_ValueAsString(COLUMNNAME_RequestID);
	}

	@Override
	public void setRequestMessage (final @Nullable java.lang.String RequestMessage)
	{
		set_Value (COLUMNNAME_RequestMessage, RequestMessage);
	}

	@Override
	public java.lang.String getRequestMessage() 
	{
		return get_ValueAsString(COLUMNNAME_RequestMessage);
	}

	@Override
	public void setResponseMessage (final @Nullable java.lang.String ResponseMessage)
	{
		set_Value (COLUMNNAME_ResponseMessage, ResponseMessage);
	}

	@Override
	public java.lang.String getResponseMessage() 
	{
		return get_ValueAsString(COLUMNNAME_ResponseMessage);
	}
}