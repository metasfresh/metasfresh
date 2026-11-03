package de.metas.material.cockpit.availableforsales.event;

import com.google.common.collect.ImmutableList;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.material.cockpit.availableforsales.EnqueueAvailableForSalesRequest;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class EnqueueAvailableForSalesPublisher
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IEventBusFactory eventBusFactory;

	private IEventBus eventBus()
	{
		return eventBusFactory.getEventBus(EnqueueAvailableForSalesConfiguration.TOPIC);
	}

	public void publishAfterCommit(@NonNull final EnqueueAvailableForSalesRequest request)
	{
		trxManager.accumulateAndProcessAfterCommit(
				"EnqueueAvailableForSalesPublisher.publishAfterCommit",
				ImmutableList.of(request),
				this::publishAll
		);
	}

	private void publishAll(@NonNull final Collection<EnqueueAvailableForSalesRequest> requests)
	{
		requests.stream().distinct().forEach(this::publishOne);
	}

	private void publishOne(@NonNull final EnqueueAvailableForSalesRequest request)
	{
		eventBus().enqueueEvent(EnqueueAvailableForSalesConverter.toEvent(request));
	}
}
