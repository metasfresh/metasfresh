package de.metas.calendar.continuous_query;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.CalendarEntryAndSimulationId;
import de.metas.calendar.CalendarEntryId;
import de.metas.calendar.CalendarService;
import de.metas.calendar.CalendarServicesMap;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.HashMap;

class EventsResolver
{
	private final CalendarServicesMap calendarServices;
	private final ImmutableList<Event> originalEvents;

	private final HashMap<SimulationPlanId, ImmutableList<Event>> resolvedEventsMap = new HashMap<>();

	EventsResolver(
			@NonNull final CalendarServicesMap calendarServices,
			@NonNull final ImmutableList<Event> originalEvents)
	{
		this.calendarServices = calendarServices;
		this.originalEvents = originalEvents;
	}

	public ImmutableList<Event> getBy(@Nullable final SimulationPlanId simulationId)
	{
		return resolvedEventsMap.computeIfAbsent(simulationId, this::resolveEvents);
	}

	private ImmutableList<Event> resolveEvents(@Nullable final SimulationPlanId simulationId)
	{
		return CollectionUtils.map(originalEvents, event -> resolveEvent(event, simulationId));
	}

	private Event resolveEvent(
			@NonNull final Event event,
			@Nullable final SimulationPlanId simulationId)
	{
		if (event instanceof EntryIdChangedEvent)
		{
			final CalendarEntryId entryId = ((EntryIdChangedEvent)event).getEntryId();
			final CalendarService calendarService = calendarServices.getById(entryId.getCalendarServiceId());
			return calendarService.getEntryById(entryId, simulationId)
					.map(EntryChangedEvent::of)
					.map(changedEvent -> (Event)changedEvent)
					.orElseGet(() -> EntryDeletedEvent.of(CalendarEntryAndSimulationId.of(entryId, simulationId)));
		}
		else if (event instanceof EntryDeletedEvent)
		{
			final EntryDeletedEvent entryDeletedEvent = (EntryDeletedEvent)event;

			return entryDeletedEvent.getSimulationId() == null
					? entryDeletedEvent.withSimulationId(simulationId)
					: entryDeletedEvent;
		}
		else
		{
			return event;
		}
	}

}
