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
package de.metas.event.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_EventLog
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_EventLog extends org.compiere.model.PO implements I_AD_EventLog, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = 1575447785L;

    /** Standard Constructor */
    public X_AD_EventLog (final Properties ctx, final int AD_EventLog_ID, @Nullable final String trxName)
    {
      super (ctx, AD_EventLog_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_EventLog (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_EventLog_ID (final int AD_EventLog_ID)
	{
		if (AD_EventLog_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_EventLog_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_EventLog_ID, AD_EventLog_ID);
	}

	@Override
	public int getAD_EventLog_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_EventLog_ID);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setEventData (final @Nullable java.lang.String EventData)
	{
		set_ValueNoCheck (COLUMNNAME_EventData, EventData);
	}

	@Override
	public java.lang.String getEventData() 
	{
		return get_ValueAsString(COLUMNNAME_EventData);
	}

	/** 
	 * EventName AD_Reference_ID=541929
	 * Reference name: Material Event Names
	 */
	public static final int EVENTNAME_AD_Reference_ID=541929;
	/** AttributesChangedEvent = AttributesChangedEvent */
	public static final String EVENTNAME_AttributesChangedEvent = "AttributesChangedEvent";
	/** DDOrderCreatedEvent = DDOrderCreatedEvent */
	public static final String EVENTNAME_DDOrderCreatedEvent = "DDOrderCreatedEvent";
	/** DDOrderDocStatusChangedEvent = DDOrderDocStatusChangedEvent */
	public static final String EVENTNAME_DDOrderDocStatusChangedEvent = "DDOrderDocStatusChangedEvent";
	/** DDOrderDeletedEvent = DDOrderDeletedEvent */
	public static final String EVENTNAME_DDOrderDeletedEvent = "DDOrderDeletedEvent";
	/** DDOrderCandidateAdvisedEvent = DDOrderCandidateAdvisedEvent */
	public static final String EVENTNAME_DDOrderCandidateAdvisedEvent = "DDOrderCandidateAdvisedEvent";
	/** DDOrderCandidateCreatedEvent = DDOrderCandidateCreatedEvent */
	public static final String EVENTNAME_DDOrderCandidateCreatedEvent = "DDOrderCandidateCreatedEvent";
	/** DDOrderCandidateUpdatedEvent = DDOrderCandidateUpdatedEvent */
	public static final String EVENTNAME_DDOrderCandidateUpdatedEvent = "DDOrderCandidateUpdatedEvent";
	/** DDOrderCandidateRequestedEvent = DDOrderCandidateRequestedEvent */
	public static final String EVENTNAME_DDOrderCandidateRequestedEvent = "DDOrderCandidateRequestedEvent";
	/** ForecastCreatedEvent = ForecastCreatedEvent */
	public static final String EVENTNAME_ForecastCreatedEvent = "ForecastCreatedEvent";
	/** PickingRequestedEvent = PickingRequestedEvent */
	public static final String EVENTNAME_PickingRequestedEvent = "PickingRequestedEvent";
	/** PPOrderCreatedEvent = PPOrderCreatedEvent */
	public static final String EVENTNAME_PPOrderCreatedEvent = "PPOrderCreatedEvent";
	/** PPOrderDeletedEvent = PPOrderDeletedEvent */
	public static final String EVENTNAME_PPOrderDeletedEvent = "PPOrderDeletedEvent";
	/** PPOrderChangedEvent = PPOrderChangedEvent */
	public static final String EVENTNAME_PPOrderChangedEvent = "PPOrderChangedEvent";
	/** PPOrderRequestedEvent = PPOrderRequestedEvent */
	public static final String EVENTNAME_PPOrderRequestedEvent = "PPOrderRequestedEvent";
	/** PPOrderCandidateAdvisedEvent = PPOrderCandidateAdvisedEvent */
	public static final String EVENTNAME_PPOrderCandidateAdvisedEvent = "PPOrderCandidateAdvisedEvent";
	/** PPOrderCandidateRequestedEvent = PPOrderCandidateRequestedEvent */
	public static final String EVENTNAME_PPOrderCandidateRequestedEvent = "PPOrderCandidateRequestedEvent";
	/** PPOrderCandidateUpdatedEvent = PPOrderCandidateUpdatedEvent */
	public static final String EVENTNAME_PPOrderCandidateUpdatedEvent = "PPOrderCandidateUpdatedEvent";
	/** PPOrderCandidateCreatedEvent = PPOrderCandidateCreatedEvent */
	public static final String EVENTNAME_PPOrderCandidateCreatedEvent = "PPOrderCandidateCreatedEvent";
	/** PPOrderCandidateDeletedEvent = PPOrderCandidateDeletedEvent */
	public static final String EVENTNAME_PPOrderCandidateDeletedEvent = "PPOrderCandidateDeletedEvent";
	/** PurchaseCandidateAdvisedEvent = PurchaseCandidateAdvisedEvent */
	public static final String EVENTNAME_PurchaseCandidateAdvisedEvent = "PurchaseCandidateAdvisedEvent";
	/** PurchaseCandidateCreatedEvent = PurchaseCandidateCreatedEvent */
	public static final String EVENTNAME_PurchaseCandidateCreatedEvent = "PurchaseCandidateCreatedEvent";
	/** PurchaseCandidateUpdatedEvent = PurchaseCandidateUpdatedEvent */
	public static final String EVENTNAME_PurchaseCandidateUpdatedEvent = "PurchaseCandidateUpdatedEvent";
	/** PurchaseCandidateRequestedEvent = PurchaseCandidateRequestedEvent */
	public static final String EVENTNAME_PurchaseCandidateRequestedEvent = "PurchaseCandidateRequestedEvent";
	/** PurchaseOfferCreatedEvent = PurchaseOfferCreatedEvent */
	public static final String EVENTNAME_PurchaseOfferCreatedEvent = "PurchaseOfferCreatedEvent";
	/** PurchaseOfferUpdatedEvent = PurchaseOfferUpdatedEvent */
	public static final String EVENTNAME_PurchaseOfferUpdatedEvent = "PurchaseOfferUpdatedEvent";
	/** PurchaseOfferDeletedEvent = PurchaseOfferDeletedEvent */
	public static final String EVENTNAME_PurchaseOfferDeletedEvent = "PurchaseOfferDeletedEvent";
	/** ReceiptScheduleCreatedEvent = ReceiptScheduleCreatedEvent */
	public static final String EVENTNAME_ReceiptScheduleCreatedEvent = "ReceiptScheduleCreatedEvent";
	/** ReceiptScheduleDeletedEvent = ReceiptScheduleDeletedEvent */
	public static final String EVENTNAME_ReceiptScheduleDeletedEvent = "ReceiptScheduleDeletedEvent";
	/** ReceiptScheduleUpdatedEvent = ReceiptScheduleUpdatedEvent */
	public static final String EVENTNAME_ReceiptScheduleUpdatedEvent = "ReceiptScheduleUpdatedEvent";
	/** ShipmentScheduleCreatedEvent = ShipmentScheduleCreatedEvent */
	public static final String EVENTNAME_ShipmentScheduleCreatedEvent = "ShipmentScheduleCreatedEvent";
	/** ShipmentScheduleDeletedEvent = ShipmentScheduleDeletedEvent */
	public static final String EVENTNAME_ShipmentScheduleDeletedEvent = "ShipmentScheduleDeletedEvent";
	/** ShipmentScheduleUpdatedEvent = ShipmentScheduleUpdatedEvent */
	public static final String EVENTNAME_ShipmentScheduleUpdatedEvent = "ShipmentScheduleUpdatedEvent";
	/** StockChangedEvent = StockChangedEvent */
	public static final String EVENTNAME_StockChangedEvent = "StockChangedEvent";
	/** StockEstimateCreatedEvent = StockEstimateCreatedEvent */
	public static final String EVENTNAME_StockEstimateCreatedEvent = "StockEstimateCreatedEvent";
	/** SupplyRequiredEvent = SupplyRequiredEvent */
	public static final String EVENTNAME_SupplyRequiredEvent = "SupplyRequiredEvent";
	/** NoSupplyAdviceEvent = NoSupplyAdviceEvent */
	public static final String EVENTNAME_NoSupplyAdviceEvent = "NoSupplyAdviceEvent";
	/** TransactionCreatedEvent = TransactionCreatedEvent */
	public static final String EVENTNAME_TransactionCreatedEvent = "TransactionCreatedEvent";
	/** TransactionDeletedEvent = TransactionDeletedEvent */
	public static final String EVENTNAME_TransactionDeletedEvent = "TransactionDeletedEvent";
	/** StockCandidateChangedEvent = StockCandidateChangedEvent */
	public static final String EVENTNAME_StockCandidateChangedEvent = "StockCandidateChangedEvent";
	/** MaterialCandidateChangedEvent = MaterialCandidateChangedEvent */
	public static final String EVENTNAME_MaterialCandidateChangedEvent = "MaterialCandidateChangedEvent";
	/** SimulatedDemandCreatedEvent = SimulatedDemandCreatedEvent */
	public static final String EVENTNAME_SimulatedDemandCreatedEvent = "SimulatedDemandCreatedEvent";
	/** DeactivateAllSimulatedCandidatesEvent = DeactivateAllSimulatedCandidatesEvent */
	public static final String EVENTNAME_DeactivateAllSimulatedCandidatesEvent = "DeactivateAllSimulatedCandidatesEvent";
	/** AllEventsProcessedEvent = AllEventsProcessedEvent */
	public static final String EVENTNAME_AllEventsProcessedEvent = "AllEventsProcessedEvent";
	/** StockEstimateDeletedEvent = StockEstimateDeletedEvent */
	public static final String EVENTNAME_StockEstimateDeletedEvent = "StockEstimateDeletedEvent";
	@Override
	public void setEventName (final @Nullable java.lang.String EventName)
	{
		set_Value (COLUMNNAME_EventName, EventName);
	}

	@Override
	public java.lang.String getEventName() 
	{
		return get_ValueAsString(COLUMNNAME_EventName);
	}

	@Override
	public void setEventTime (final @Nullable java.sql.Timestamp EventTime)
	{
		set_ValueNoCheck (COLUMNNAME_EventTime, EventTime);
	}

	@Override
	public java.sql.Timestamp getEventTime() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_EventTime);
	}

	@Override
	public void setEventTopicName (final @Nullable java.lang.String EventTopicName)
	{
		set_Value (COLUMNNAME_EventTopicName, EventTopicName);
	}

	@Override
	public java.lang.String getEventTopicName() 
	{
		return get_ValueAsString(COLUMNNAME_EventTopicName);
	}

	/** 
	 * EventTypeName AD_Reference_ID=540802
	 * Reference name: EventTypeName
	 */
	public static final int EVENTTYPENAME_AD_Reference_ID=540802;
	/** LOCAL = LOCAL */
	public static final String EVENTTYPENAME_LOCAL = "LOCAL";
	/** DISTRIBUTED = DISTRIBUTED */
	public static final String EVENTTYPENAME_DISTRIBUTED = "DISTRIBUTED";
	@Override
	public void setEventTypeName (final @Nullable java.lang.String EventTypeName)
	{
		set_Value (COLUMNNAME_EventTypeName, EventTypeName);
	}

	@Override
	public java.lang.String getEventTypeName() 
	{
		return get_ValueAsString(COLUMNNAME_EventTypeName);
	}

	@Override
	public void setEvent_UUID (final java.lang.String Event_UUID)
	{
		set_ValueNoCheck (COLUMNNAME_Event_UUID, Event_UUID);
	}

	@Override
	public java.lang.String getEvent_UUID() 
	{
		return get_ValueAsString(COLUMNNAME_Event_UUID);
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
	public void setIsErrorAcknowledged (final boolean IsErrorAcknowledged)
	{
		set_Value (COLUMNNAME_IsErrorAcknowledged, IsErrorAcknowledged);
	}

	@Override
	public boolean isErrorAcknowledged() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsErrorAcknowledged);
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}
}