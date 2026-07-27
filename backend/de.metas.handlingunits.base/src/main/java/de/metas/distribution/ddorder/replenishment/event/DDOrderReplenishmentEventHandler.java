package de.metas.distribution.ddorder.replenishment.event;

import de.metas.Profiles;
import de.metas.distribution.ddorder.replenishment.DDOrderPickingReplenishmentService;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.log.EventLogUserService;
import de.metas.picking.api.PickingJobScheduleId;
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
	// Services.get(): legacy singleton lookup, not constructor-injected -> declared after the @RequiredArgsConstructor fields.
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@PostConstruct
	public void subscribe()
	{
		eventBusFactory.getEventBus(DDOrderReplenishmentConstants.TOPIC).subscribe(this);
	}

	@Override
	public void onEvent(@NonNull final IEventBus eventBus, @NonNull final Event event)
	{
		final DDOrderReplenishmentRequest request = DDOrderReplenishmentRequestConverter.fromEvent(event);

		try (final IAutoCloseable ignored = switchCtx(request))
		{
			// request.getTriggeredBy() is only the AD_EventLog anchor, not the unit of work: the reconcile runs on the whole product group.
			eventLogUserService.invokeHandlerAndLog(EventLogUserService.InvokeHandlerAndLogRequest.builder()
					.handlerClass(DDOrderReplenishmentEventHandler.class)
					.invokaction(() -> trxManager.runInThreadInheritedTrx(
							() -> replenishmentService.reconcile(request.getGroupKey(), request.getClientAndOrgId())))
					.build());
		}
	}

	private IAutoCloseable switchCtx(final @NonNull DDOrderReplenishmentRequest request)
	{
		final Properties ctx = Env.newTemporaryCtx();
		Env.setClientAndOrgId(ctx, request.getClientAndOrgId());
		return Env.switchContext(ctx);
	}

}
