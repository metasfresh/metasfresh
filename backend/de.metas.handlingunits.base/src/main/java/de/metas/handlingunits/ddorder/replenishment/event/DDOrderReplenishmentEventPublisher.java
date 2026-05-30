package de.metas.handlingunits.ddorder.replenishment.event;

import de.metas.event.Event;
import de.metas.event.IEventBusFactory;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
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
	private static final String EVENT_NAME = "DDOrderPickingReconcile";

	@NonNull private final IEventBusFactory eventBusFactory;

	public void publishOne(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		// shallBeLogged() is required so the event-bus sets up the EventLogEntryCollector thread-local
		// (EventBus.invokeEventListener → isWasLogged); without it the handler's invokeHandlerAndLog
		// fails with "Missing thread-local EventLogEntryCollector", and no AD_EventLog Done/Error is recorded.
		final Event event = Event.builder()
				.setEventName(EVENT_NAME)
				.putProperty(PROPERTY_shipmentScheduleId, shipmentScheduleId.getRepoId())
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
