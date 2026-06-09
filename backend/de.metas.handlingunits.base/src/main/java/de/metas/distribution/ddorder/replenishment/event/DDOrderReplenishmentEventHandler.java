package de.metas.distribution.ddorder.replenishment.event;

import de.metas.Profiles;
import de.metas.distribution.ddorder.replenishment.DDOrderPickingReplenishmentService;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.log.EventLogUserService;
import de.metas.organization.ClientAndOrgId;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
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
		try (final IAutoCloseable ignored = switchCtx(event))
		{
			final PickingJobScheduleId pickingJobScheduleId = extractPickingJobScheduleId(event);

			eventLogUserService.invokeHandlerAndLog(EventLogUserService.InvokeHandlerAndLogRequest.builder()
					.handlerClass(DDOrderReplenishmentEventHandler.class)
					.invokaction(() -> trxManager.runInThreadInheritedTrx(() -> replenishmentService.reconcile(pickingJobScheduleId)))
					.build());
		}
	}

	private static @NonNull PickingJobScheduleId extractPickingJobScheduleId(final @NonNull Event event)
	{
		return PickingJobScheduleId.ofRepoId(event.getPropertyAsInt(DDOrderReplenishmentEventPublisher.PROPERTY_pickingJobScheduleId, -1));
	}

	@Nullable
	private static ClientAndOrgId extractClientAndOrgId(final @NonNull Event event)
	{
		final int adClientId = event.getPropertyAsInt(DDOrderReplenishmentEventPublisher.PROPERTY_AD_Client_ID, -1);
		final int adOrgId = event.getPropertyAsInt(DDOrderReplenishmentEventPublisher.PROPERTY_AD_Org_ID, -1);
		// On afterDelete the assignment row is already gone, so the publisher could not resolve its client/org.
		// Fall through with no context switch in that case; the reconcile (VOID path) does not need a client/org.
		if (adClientId <= 0 || adOrgId < 0)
		{
			return null;
		}

		return ClientAndOrgId.ofClientAndOrg(adClientId, adOrgId);
	}

	private IAutoCloseable switchCtx(final @NonNull Event event)
	{
		final Properties ctx = Env.newTemporaryCtx();
		final ClientAndOrgId clientAndOrgId = extractClientAndOrgId(event);
		if (clientAndOrgId != null)
		{
			Env.setClientAndOrgId(ctx, clientAndOrgId);
		}
		return Env.switchContext(ctx);
	}

}
