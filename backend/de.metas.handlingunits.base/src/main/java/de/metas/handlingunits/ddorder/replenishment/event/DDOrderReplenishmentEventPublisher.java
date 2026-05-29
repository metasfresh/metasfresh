package de.metas.handlingunits.ddorder.replenishment.event;

import de.metas.event.Event;
import de.metas.event.IEventBusFactory;
import de.metas.inout.ShipmentScheduleId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
