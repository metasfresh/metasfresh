// Generated Model - DO NOT CHANGE
package de.metas.shipper.gateway.dhl.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for Dhl_ShipmentOrder_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Dhl_ShipmentOrder_Log extends org.compiere.model.PO implements I_Dhl_ShipmentOrder_Log, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -675920454L;

    /** Standard Constructor */
    public X_Dhl_ShipmentOrder_Log (final Properties ctx, final int Dhl_ShipmentOrder_Log_ID, @Nullable final String trxName)
    {
      super (ctx, Dhl_ShipmentOrder_Log_ID, trxName);
    }

    /** Load Constructor */
    public X_Dhl_ShipmentOrder_Log (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Issue_ID (final int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, AD_Issue_ID);
	}

	@Override
	public int getAD_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

	@Override
	public void setConfigSummary (final @Nullable java.lang.String ConfigSummary)
	{
		set_Value (COLUMNNAME_ConfigSummary, ConfigSummary);
	}

	@Override
	public java.lang.String getConfigSummary() 
	{
		return get_ValueAsString(COLUMNNAME_ConfigSummary);
	}

	@Override
	public void setDhl_ShipmentOrder_Log_ID (final int Dhl_ShipmentOrder_Log_ID)
	{
		if (Dhl_ShipmentOrder_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Dhl_ShipmentOrder_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Dhl_ShipmentOrder_Log_ID, Dhl_ShipmentOrder_Log_ID);
	}

	@Override
	public int getDhl_ShipmentOrder_Log_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Dhl_ShipmentOrder_Log_ID);
	}

	@Override
	public de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest getDHL_ShipmentOrderRequest()
	{
		return get_ValueAsPO(COLUMNNAME_DHL_ShipmentOrderRequest_ID, de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest.class);
	}

	@Override
	public void setDHL_ShipmentOrderRequest(final de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest DHL_ShipmentOrderRequest)
	{
		set_ValueFromPO(COLUMNNAME_DHL_ShipmentOrderRequest_ID, de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest.class, DHL_ShipmentOrderRequest);
	}

	@Override
	public void setDHL_ShipmentOrderRequest_ID (final int DHL_ShipmentOrderRequest_ID)
	{
		if (DHL_ShipmentOrderRequest_ID < 1) 
			set_Value (COLUMNNAME_DHL_ShipmentOrderRequest_ID, null);
		else 
			set_Value (COLUMNNAME_DHL_ShipmentOrderRequest_ID, DHL_ShipmentOrderRequest_ID);
	}

	@Override
	public int getDHL_ShipmentOrderRequest_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DHL_ShipmentOrderRequest_ID);
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