package de.metas.calendar;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.simulation.SimulationPlanId;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ToString
public class CalendarContinuousQuery
{
	private final CalendarQuery query;
	private final CopyOnWriteArrayList<Listener> listeners = new CopyOnWriteArrayList<>();

	private CalendarContinuousQuery(@NonNull final CalendarQuery query)
	{
		this.query = query;
	}

	public static CalendarContinuousQuery ofQuery(@NonNull final CalendarQuery query) {return new CalendarContinuousQuery(query);}

	public void subscribe(@NonNull final Listener listener)
	{
		listeners.addIfAbsent(listener);
	}

	public void unsubscribe(@NonNull final Listener listener)
	{
		listeners.remove(listener);
	}

	void notifyChanged(@NonNull final List<Event> events)
	{
		if (listeners.isEmpty() || events.isEmpty())
		{
			return;
		}

		final ImmutableList<Event> relevantEvents = events.stream()
				.filter(this::isEventMatchingQuery)
				.collect(ImmutableList.toImmutableList());
		if (relevantEvents.isEmpty())
		{
			return;
		}

		listeners.forEach(listener -> listener.onEvents(relevantEvents));
	}

	private boolean isEventMatchingQuery(@NonNull final CalendarContinuousQuery.Event event)
	{
		if (event instanceof EntryChangedEvent)
		{
			final CalendarEntry entry = ((EntryChangedEvent)event).getEntry();
			return query.isMatchingEntry(entry);
		}
		else if (event instanceof EntryDeletedEvent)
		{
			final EntryDeletedEvent entryDeletedEvent = (EntryDeletedEvent)event;
			return query.isMatchingSimulationId(entryDeletedEvent.getSimulationId());
		}
		else
		{
			return false;
		}
	}

	//
	//
	//

	public interface Event {}

	@Value(staticConstructor = "of")
	public static class EntryChangedEvent implements Event
	{
		@NonNull CalendarEntry entry;
	}

	@Value(staticConstructor = "of")
	public static class EntryDeletedEvent implements Event
	{
		@NonNull CalendarEntryAndSimulationId entryAndSimulationId;

		public CalendarEntryId getEntryId() {return getEntryAndSimulationId().getEntryId();}

		public SimulationPlanId getSimulationId() {return getEntryAndSimulationId().getSimulationId();}
	}

	public interface Listener
	{
		void onEvents(@NonNull final List<Event> events);
	}
}
