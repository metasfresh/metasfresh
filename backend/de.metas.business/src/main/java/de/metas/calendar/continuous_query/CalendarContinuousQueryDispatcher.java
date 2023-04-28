package de.metas.calendar.continuous_query;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.MapMaker;
import de.metas.calendar.CalendarEntry;
import de.metas.calendar.CalendarEntryAndSimulationId;
import de.metas.calendar.CalendarEntryId;
import de.metas.calendar.CalendarEntryUpdateResult;
import de.metas.calendar.CalendarQuery;
import de.metas.calendar.CalendarServicesMap;
import de.metas.calendar.simulation.SimulationPlanId;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.util.lang.OldAndNewValues;

import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentMap;

public class CalendarContinuousQueryDispatcher
{
	private final ITrxManager trxManager;
	private final ConcurrentMap<CalendarQuery, CalendarContinuousQuery> map = new MapMaker().weakValues().makeMap();
	private final CalendarServicesMap calendarServices;

	public CalendarContinuousQueryDispatcher(
			@NonNull final ITrxManager trxManager,
			@NonNull final CalendarServicesMap calendarServices)
	{
		this.trxManager = trxManager;
		this.calendarServices = calendarServices;
	}

	public CalendarContinuousQuery getOrCreate(@NonNull final CalendarQuery query)
	{
		return map.computeIfAbsent(query, CalendarContinuousQuery::ofQuery);
	}

	private void dispatchAfterCommit(@NonNull final Event eventToDispatch)
	{
		dispatchAfterCommit(ImmutableList.of(eventToDispatch));
	}

	private void dispatchAfterCommit(@NonNull final ImmutableList<Event> eventsToDispatch)
	{
		if (eventsToDispatch.isEmpty())
		{
			return;
		}

		final ITrx currentTrx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		if (trxManager.isActive(currentTrx))
		{
			currentTrx.accumulateAndProcessAfterCommit(
					CalendarContinuousQueryDispatcher.class.getName(),
					eventsToDispatch,
					this::dispatchEventsNow);
		}
		else
		{
			dispatchEventsNow(eventsToDispatch);
		}

	}

	private void dispatchEventsNow(@NonNull final ImmutableList<Event> events)
	{
		if (events.isEmpty())
		{
			return;
		}

		EventsResolver eventsResolver = null;

		for (final CalendarContinuousQuery continuousQuery : map.values())
		{
			if (continuousQuery.hasSubscriptions())
			{
				if (eventsResolver == null)
				{
					eventsResolver = new EventsResolver(calendarServices, events);
				}
				final ImmutableList<Event> eventsEffective = eventsResolver.getBy(continuousQuery.getSimulationId());

				continuousQuery.notifyChanged(eventsEffective);
			}
		}
	}

	public void onEntryAdded(@NonNull final CalendarEntry entry)
	{
		dispatchAfterCommit(EntryChangedEvent.of(entry));
	}

	public void onEntryUpdated(@NonNull final CalendarEntryUpdateResult result)
	{
		dispatchAfterCommit(toEvents(result));
	}

	public void onEntryUpdated(@NonNull final CalendarEntryId entryId)
	{
		dispatchAfterCommit(EntryIdChangedEvent.of(entryId));
	}

	public void onEntryDeleted(
			@NonNull final CalendarEntryId entryId,
			@Nullable final SimulationPlanId simulationId)
	{
		dispatchAfterCommit(EntryDeletedEvent.of(CalendarEntryAndSimulationId.of(entryId, simulationId)));
	}

	@NonNull
	private static ImmutableList<Event> toEvents(final @NonNull CalendarEntryUpdateResult result)
	{
		final ImmutableList.Builder<Event> builder = ImmutableList.builder();
		builder.addAll(toEvents(result.getChangedEntry()));
		result.getOtherChangedEntries().forEach(changedEntry -> builder.addAll(toEvents(changedEntry)));
		return builder.build();
	}

	private static ImmutableList<Event> toEvents(@NonNull final OldAndNewValues<CalendarEntry> oldAndNewEntry)
	{
		if (!oldAndNewEntry.isValueChanged())
		{
			return ImmutableList.of();
		}

		final ImmutableList.Builder<Event> builder = ImmutableList.builder();

		final CalendarEntry oldEntry = oldAndNewEntry.getOldValue();
		final CalendarEntry newEntry = oldAndNewEntry.getNewValue();
		if (oldEntry != null
				&& !CalendarEntryAndSimulationId.equals(oldEntry.getCalendarEntryAndSimulationId(), newEntry != null ? newEntry.getCalendarEntryAndSimulationId() : null))
		{
			builder.add(EntryDeletedEvent.of(oldEntry.getCalendarEntryAndSimulationId()));
		}

		if (newEntry != null)
		{
			builder.add(EntryChangedEvent.of(newEntry));
		}

		return builder.build();
	}
}

