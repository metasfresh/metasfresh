package de.metas.distribution.ddorder.replenishment.event;

import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class DDOrderReplenishmentEventPublisher
{
	@NonNull private final IEventBusFactory eventBusFactory;

	private IEventBus eventBus()
	{
		return eventBusFactory.getEventBus(DDOrderReplenishmentConstants.TOPIC);
	}

	public void publishOne(@NonNull final DDOrderReplenishmentRequest request)
	{
		eventBus().enqueueEvent(DDOrderReplenishmentRequestConverter.toEvent(request));
	}

	public void publishAll(@NonNull final Collection<DDOrderReplenishmentRequest> requests)
	{
		requests.stream().distinct().forEach(this::publishOne);
	}
}
