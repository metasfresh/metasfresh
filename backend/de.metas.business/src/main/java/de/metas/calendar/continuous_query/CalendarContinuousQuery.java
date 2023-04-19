package de.metas.calendar.continuous_query;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.CalendarEntry;
import de.metas.calendar.CalendarQuery;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.logging.LogManager;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.ToString;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.concurrent.CopyOnWriteArrayList;

@ToString
public class CalendarContinuousQuery
{
	private static final Logger logger = LogManager.getLogger(CalendarContinuousQuery.class);

	private final CalendarQuery query;
	private final CopyOnWriteArrayList<CalendarContinuousQueryListener> listeners = new CopyOnWriteArrayList<>();

	private CalendarContinuousQuery(@NonNull final CalendarQuery query)
	{
		this.query = query;
	}

	public static CalendarContinuousQuery ofQuery(@NonNull final CalendarQuery query) {return new CalendarContinuousQuery(query);}

	public void subscribe(@NonNull final CalendarContinuousQueryListener listener)
	{
		listeners.addIfAbsent(listener);
	}

	public void unsubscribe(@NonNull final CalendarContinuousQueryListener listener)
	{
		listeners.remove(listener);
	}

	public boolean hasSubscriptions()
	{
		return !listeners.isEmpty();
	}

	@Nullable
	public SimulationPlanId getSimulationId() {return query.getSimulationId();}

	void notifyChanged(@NonNull final ImmutableList<Event> events)
	{
		if (listeners.isEmpty())
		{
			return;
		}

		final ImmutableList<Event> relevantEvents = CollectionUtils.filter(events, this::isEventMatchingQuery);
		if (relevantEvents.isEmpty())
		{
			return;
		}

		listeners.forEach(listener -> listener.onCalendarContinuousQueryEvents(relevantEvents));
	}

	private boolean isEventMatchingQuery(@NonNull final Event event)
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
			logger.warn("Unknown event type: {}", event);
			return false;
		}
	}
}
