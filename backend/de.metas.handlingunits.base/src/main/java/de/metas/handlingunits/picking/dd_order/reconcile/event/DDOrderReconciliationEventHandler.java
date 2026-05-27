package de.metas.handlingunits.picking.dd_order.reconcile.event;

import de.metas.Profiles;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.handlingunits.picking.dd_order.reconcile.DDOrderPickingReconcileBL;
import de.metas.inout.ShipmentScheduleId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Consumes DD_Order picking reconcile events from {@link DDOrderReconciliationTopic#TOPIC}.
 * Each event carries the {@code shipmentScheduleId} property, which is extracted and passed to the
 * reconciliation business logic in a new transaction.
 */
@Component
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class DDOrderReconciliationEventHandler implements IEventListener
{
	// EventBusFactory is a Spring @Service — must be constructor-injected, NOT Services.get.
	@NonNull private final DDOrderPickingReconcileBL reconcileBL;
	@NonNull private final IEventBusFactory eventBusFactory;
	// ITrxManager is an ISingletonService — Services.get is correct.
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@PostConstruct
	public void subscribe()
	{
		eventBusFactory.getEventBus(DDOrderReconciliationTopic.TOPIC).subscribe(this);
	}

	@Override
	public void onEvent(@NonNull final IEventBus eventBus, @NonNull final Event event)
	{
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(event.getPropertyAsInt(
				DDOrderReconciliationEventPublisher.PROPERTY_shipmentScheduleId, -1));
		trxManager.runInNewTrx(() -> reconcileBL.reconcile(scheduleId));
	}
}
