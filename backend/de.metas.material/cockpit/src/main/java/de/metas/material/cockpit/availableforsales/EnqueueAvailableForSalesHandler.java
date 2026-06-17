package de.metas.material.cockpit.availableforsales;

import de.metas.Profiles;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
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
		eventBusFactory.getEventBus(EnqueueAvailableForSalesPublisher.TOPIC).subscribe(this);
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
		return Env.switchContext(ctx);
	}

}
