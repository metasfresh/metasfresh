package de.metas.material.cockpit.availableforsales.event;

import de.metas.Profiles;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.material.cockpit.availableforsales.AvailableForSalesService;
import de.metas.material.cockpit.availableforsales.EnqueueAvailableForSalesRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Component
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class EnqueueAvailableForSalesHandler implements IEventListener
{
	@NonNull private final AvailableForSalesService availableForSalesService;
	@NonNull private final IEventBusFactory eventBusFactory;

	@PostConstruct
	public void subscribe()
	{
		eventBusFactory.getEventBus(EnqueueAvailableForSalesConfiguration.TOPIC).subscribe(this);
	}

	@Override
	public void onEvent(final IEventBus eventBus_NOTUSED, final Event event)
	{
		final EnqueueAvailableForSalesRequest request = EnqueueAvailableForSalesConverter.fromEvent(event);
		try (final IAutoCloseable ignored = switchCtx(request))
		{
			availableForSalesService.syncAvailableForSalesTable(request.getAvailableForSalesQuery());
		}
	}

	private IAutoCloseable switchCtx(final @NonNull EnqueueAvailableForSalesRequest request)
	{
		final Properties ctx = Env.newTemporaryCtx();
		Env.setClientAndOrgId(ctx, request.getAvailableForSalesQuery().getClientAndOrgId());
		// Apply the originating user/role that the publisher captured (serialized in the event), so the
		// recompute runs under the same security context — not the system default.
		if (request.getContextUserId() != null)
		{
			Env.setLoggedUserId(ctx, request.getContextUserId());
		}
		if (request.getContextRoleId() != null)
		{
			Env.setLoggedRoleId(ctx, request.getContextRoleId());
		}
		return Env.switchContext(ctx);
	}

}
