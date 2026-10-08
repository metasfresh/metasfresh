package de.metas.order;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.order.PurchaseOrderProjectListener.ProjectCreatedEvent;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Component
public class PurchaseOrderProjectListenerDispatcher
{
	private static final Topic TOPIC = Topic.localAndAsync("de.metas.order.PurchaseOrderProjectListenerDispatcher.events");

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IEventBus eventBus;
	@NonNull private final ImmutableList<PurchaseOrderProjectListener> listeners;

	public PurchaseOrderProjectListenerDispatcher(
			@NonNull final Optional<List<PurchaseOrderProjectListener>> listeners,
			@NonNull final IEventBusFactory eventBusFactory)
	{
		this.listeners = listeners.map(ImmutableList::copyOf).orElseGet(ImmutableList::of);
		this.eventBus = eventBusFactory.getEventBus(TOPIC);
	}

	@PostConstruct
	public void postConstruct()
	{
		this.eventBus.subscribeOn(ProjectCreatedEvent.class, this::handleEvent);
	}

	public void fireProjectCreatedEvent(@NonNull ProjectCreatedEvent event)
	{
		trxManager.accumulateAndProcessAfterCommit(
				TOPIC.getName(),
				ImmutableList.of(event),
				this::fireProjectCreatedEventsNow
		);
	}

	private void fireProjectCreatedEventsNow(@NonNull List<ProjectCreatedEvent> events)
	{
		final ImmutableSet<ProjectCreatedEvent> uniqueEvents = ImmutableSet.copyOf(events);
		eventBus.enqueueObjectsCollection(uniqueEvents);
	}

	private void handleEvent(@NonNull ProjectCreatedEvent event)
	{
		listeners.forEach(listener -> listener.onCreated(event));
	}
}
