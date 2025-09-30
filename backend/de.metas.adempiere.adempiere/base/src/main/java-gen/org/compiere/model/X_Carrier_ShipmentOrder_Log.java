/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

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

	private static final long serialVersionUID = -1402081124L;

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