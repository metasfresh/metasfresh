package de.metas.handlingunits.ddorder.replenishment.event;

import de.metas.Profiles;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.log.EventLogUserService;
import de.metas.handlingunits.ddorder.replenishment.DDOrderPickingReplenishmentService;
import de.metas.inout.ShipmentScheduleId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class DDOrderReplenishmentEventHandler implements IEventListener
{
	@NonNull private final DDOrderPickingReplenishmentService reconcileService;
	@NonNull private final IEventBusFactory eventBusFactory;
	@NonNull private final EventLogUserService eventLogUserService;
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@PostConstruct
	public void subscribe()
	{
		eventBusFactory.getEventBus(DDOrderReplenishmentConstants.TOPIC).subscribe(this);
	}

	@Override
	public void onEvent(@NonNull final IEventBus eventBus, @NonNull final Event event)
	{
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(event.getPropertyAsInt(
				DDOrderReplenishmentEventPublisher.PROPERTY_shipmentScheduleId, -1));

		eventLogUserService.invokeHandlerAndLog(EventLogUserService.InvokeHandlerAndLogRequest.builder()
				.handlerClass(DDOrderReplenishmentEventHandler.class)
				.invokaction(() -> trxManager.runInThreadInheritedTrx(() -> reconcileService.reconcile(scheduleId)))
				.build());
	}
}
