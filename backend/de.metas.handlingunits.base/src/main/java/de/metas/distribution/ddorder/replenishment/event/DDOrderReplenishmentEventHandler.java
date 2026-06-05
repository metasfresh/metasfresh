package de.metas.distribution.ddorder.replenishment.event;

import de.metas.Profiles;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.log.EventLogUserService;
import de.metas.distribution.ddorder.replenishment.DDOrderPickingReplenishmentService;
import de.metas.inout.ShipmentScheduleId;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Component
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class DDOrderReplenishmentEventHandler implements IEventListener
{
	@NonNull private final DDOrderPickingReplenishmentService replenishmentService;
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

		// This handler runs on an EventBus pool thread where Env has no AD_Client_ID/AD_Org_ID.
		// Restore the originating AD context (carried in the event) so downstream model creation
		// (e.g. newInstance(I_DD_Order.class)) inherits the right client/org instead of AD_Client_ID=0.
		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(
				event.getPropertyAsInt(DDOrderReplenishmentEventPublisher.PROPERTY_AD_Client_ID, -1),
				event.getPropertyAsInt(DDOrderReplenishmentEventPublisher.PROPERTY_AD_Org_ID, -1));

		try (final IAutoCloseable ignored = switchCtx(clientAndOrgId))
		{
			eventLogUserService.invokeHandlerAndLog(EventLogUserService.InvokeHandlerAndLogRequest.builder()
					.handlerClass(DDOrderReplenishmentEventHandler.class)
					.invokaction(() -> trxManager.runInThreadInheritedTrx(() -> replenishmentService.reconcile(scheduleId)))
					.build());
		}
	}

	private IAutoCloseable switchCtx(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		final Properties ctx = Env.newTemporaryCtx();
		Env.setClientAndOrgId(ctx, clientAndOrgId);
		return Env.switchContext(ctx);
	}
}
