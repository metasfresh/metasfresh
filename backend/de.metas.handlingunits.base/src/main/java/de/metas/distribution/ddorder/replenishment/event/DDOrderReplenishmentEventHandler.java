package de.metas.distribution.ddorder.replenishment.event;

import de.metas.Profiles;
import de.metas.distribution.ddorder.replenishment.DDOrderPickingReplenishmentService;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.log.EventLogUserService;
import de.metas.inout.ShipmentScheduleId;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
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
		try (final IAutoCloseable ignored = switchCtx(event))
		{
			final ShipmentScheduleId scheduleId = extractShipmentScheduleId(event);

			eventLogUserService.invokeHandlerAndLog(EventLogUserService.InvokeHandlerAndLogRequest.builder()
					.handlerClass(DDOrderReplenishmentEventHandler.class)
					.invokaction(() -> trxManager.runInThreadInheritedTrx(() -> replenishmentService.reconcile(scheduleId)))
					.build());
		}
	}

	private static @NonNull ShipmentScheduleId extractShipmentScheduleId(final @NonNull Event event)
	{
		return ShipmentScheduleId.ofRepoId(event.getPropertyAsInt(DDOrderReplenishmentEventPublisher.PROPERTY_shipmentScheduleId, -1));
	}

	private static @NonNull ClientAndOrgId extractClientAndOrgId(final @NonNull Event event)
	{
		final int adClientId = event.getPropertyAsInt(DDOrderReplenishmentEventPublisher.PROPERTY_AD_Client_ID, -1);
		final int adOrgId = event.getPropertyAsInt(DDOrderReplenishmentEventPublisher.PROPERTY_AD_Org_ID, -1);
		if (adClientId <= 0 || adOrgId < 0)
		{
			throw new AdempiereException(StringUtils.formatMessage(
					"DD_Order replenishment event is missing AD_Client_ID/AD_Org_ID (old publisher?): clientId={0}, orgId={1}",
					adClientId, adOrgId));
		}

		return ClientAndOrgId.ofClientAndOrg(adClientId, adOrgId);
	}

	private IAutoCloseable switchCtx(final @NonNull Event event)
	{
		final Properties ctx = Env.newTemporaryCtx();
		Env.setClientAndOrgId(ctx, extractClientAndOrgId(event));
		return Env.switchContext(ctx);
	}

}
