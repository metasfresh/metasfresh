package de.metas.distribution.ddorder.replenishment.event;

import de.metas.event.Event;
import de.metas.event.IEventBusFactory;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedHashSet;

@Component
@RequiredArgsConstructor
public class DDOrderReplenishmentEventPublisher
{
	public static final String PROPERTY_shipmentScheduleId = "shipmentScheduleId";
	public static final String PROPERTY_AD_Client_ID = "AD_Client_ID";
	public static final String PROPERTY_AD_Org_ID = "AD_Org_ID";
	private static final String EVENT_NAME = "DDOrderPickingReconcile";

	@NonNull private final IEventBusFactory eventBusFactory;
	@NonNull private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

	public void publishOne(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		// The schedule's AD_Client_ID/AD_Org_ID are carried into the async handler (which runs on an EventBus pool thread with no AD context).
		final I_M_ShipmentSchedule schedule = shipmentScheduleBL.getById(shipmentScheduleId);

		// shallBeLogged() is required: without it the event-bus never sets up the EventLogEntryCollector
		// thread-local, the handler throws "Missing thread-local EventLogEntryCollector", and no AD_EventLog is recorded.
		final Event event = Event.builder()
				.setEventName(EVENT_NAME)
				.putProperty(PROPERTY_shipmentScheduleId, shipmentScheduleId.getRepoId())
				.putProperty(PROPERTY_AD_Client_ID, schedule.getAD_Client_ID())
				.putProperty(PROPERTY_AD_Org_ID, schedule.getAD_Org_ID())
				// Tie the resulting AD_EventLog (and thus its AD_EventLog_Entry rows) to the schedule that
				// triggered the reconcile. EventLogService.saveEvent copies this into AD_EventLog.AD_Table_ID /
				// Record_ID, which lets callers (incl. tests) pin a log entry to its originating schedule
				// instead of matching any reconcile entry globally.
				.setSourceRecordReference(TableRecordReference.of(I_M_ShipmentSchedule.Table_Name, shipmentScheduleId.getRepoId()))
				.shallBeLogged()
				.build();
		eventBusFactory.getEventBus(DDOrderReplenishmentConstants.TOPIC).enqueueEvent(event);
	}

	public void publishAll(@NonNull final Collection<ShipmentScheduleId> shipmentScheduleIds)
	{
		for (final ShipmentScheduleId shipmentScheduleId : new LinkedHashSet<>(shipmentScheduleIds))
		{
			publishOne(shipmentScheduleId);
		}
	}
}
