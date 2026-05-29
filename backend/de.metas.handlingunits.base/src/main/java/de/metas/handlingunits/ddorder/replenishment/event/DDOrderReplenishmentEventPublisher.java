package de.metas.handlingunits.picking.dd_order.reconcile.event;

import de.metas.event.Event;
import de.metas.event.IEventBusFactory;
import de.metas.inout.ShipmentScheduleId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Thin glue that publishes DD_Order picking reconcile events to {@link DDOrderReconciliationTopic#TOPIC}.
 * Each event carries the {@code shipmentScheduleId} property (int).
 *
 * <p>This is glue only: it does not decide <em>whether</em> or <em>when</em> to publish (coalescing,
 * packing-warehouse gating, after-commit registration) — that logic lives in the BL service.</p>
 */
@Component
@RequiredArgsConstructor
public class DDOrderReconciliationEventPublisher
{
	public static final String PROPERTY_shipmentScheduleId = "shipmentScheduleId";
	private static final String EVENT_NAME = "DDOrderPickingReconcile";

	// EventBusFactory is a Spring @Service — must be constructor-injected, NOT Services.get
	// (Services.get can only instantiate ISingletonService impls with a default constructor).
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
		eventBusFactory.getEventBus(DDOrderReconciliationTopic.TOPIC).enqueueEvent(event);
	}

	/**
	 * Publishes exactly one event per <em>distinct</em> shipment schedule id in the given collection.
	 * Duplicates are coalesced so that repeated reconcile requests for the same schedule (within one
	 * transaction) result in a single event.
	 *
	 * <p>The dedup here is intentional: the BL after-commit accumulator does NOT dedup, so equal ids arriving
	 * from multiple within-trx afterSave calls land here together and collapse via the {@link LinkedHashSet}.</p>
	 */
	public void publishAll(@NonNull final Collection<ShipmentScheduleId> shipmentScheduleIds)
	{
		for (final ShipmentScheduleId shipmentScheduleId : new LinkedHashSet<>(shipmentScheduleIds))
		{
			publishOne(shipmentScheduleId);
		}
	}
}
