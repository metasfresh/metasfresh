package de.metas.calendar;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.MapMaker;
import de.metas.calendar.simulation.SimulationPlanId;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.util.lang.OldAndNewValues;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class CalendarContinuousQueryDispatcher
{
	private final ITrxManager trxManager;
	private final ConcurrentMap<CalendarQuery, CalendarContinuousQuery> map = new MapMaker().weakValues().makeMap();

	CalendarContinuousQueryDispatcher(@NonNull final ITrxManager trxManager)
	{
		this.trxManager = trxManager;
	}

	public CalendarContinuousQuery get(@NonNull final CalendarQuery query)
	{
		return map.computeIfAbsent(query, CalendarContinuousQuery::ofQuery);
	}

	private void dispatchAfterCommit(@NonNull final CalendarContinuousQuery.Event event)
	{
		dispatchEventsNow(ImmutableList.of(event));
	}

	private void dispatchAfterCommit(@NonNull final List<CalendarContinuousQuery.Event> eventsToDispatch)
	{
		if (eventsToDispatch.isEmpty())
		{
			return;
		}

		final ITrx currentTrx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		if (trxManager.isActive(currentTrx))
		{
			final ArrayList<CalendarContinuousQuery.Event> eventsCollector = currentTrx.getPropertyAndProcessAfterCommit(
					CalendarContinuousQueryDispatcher.class.getName(),
					ArrayList::new,
					events -> {
						dispatchEventsNow(events);
						events.clear();
					}
			);

			eventsCollector.addAll(eventsToDispatch);
		}
		else
		{
			dispatchEventsNow(eventsToDispatch);
		}

	}

	private void dispatchEventsNow(final List<CalendarContinuousQuery.Event> events)
	{
		if (events.isEmpty())
		{
			return;
		}

		for (final CalendarContinuousQuery continuousQuery : map.values())
		{
			continuousQuery.notifyChanged(events);
		}
	}

	void onEntryAdded(@NonNull final CalendarEntry entry)
	{
		dispatchAfterCommit(CalendarContinuousQuery.EntryChangedEvent.of(entry));
	}

	void onEntryUpdated(@NonNull final CalendarEntryUpdateResult result)
	{
		dispatchAfterCommit(toEvents(result));
	}

	void onEntryDeleted(
			@NonNull final CalendarEntryId entryId,
			@Nullable final SimulationPlanId simulationId)
	{
		dispatchAfterCommit(CalendarContinuousQuery.EntryDeletedEvent.of(CalendarEntryAndSimulationId.of(entryId, simulationId)));
	}

	@NonNull
	private static List<CalendarContinuousQuery.Event> toEvents(final @NonNull CalendarEntryUpdateResult result)
	{
		final ArrayList<CalendarContinuousQuery.Event> events = new ArrayList<>(toEvents(result.getChangedEntry()));
		result.getOtherChangedEntries().forEach(changedEntry -> events.addAll(toEvents(changedEntry)));
		return events;
	}

	private static List<CalendarContinuousQuery.Event> toEvents(@NonNull final OldAndNewValues<CalendarEntry> oldAndNewEntry)
	{
		if (!oldAndNewEntry.isValueChanged())
		{
			return ImmutableList.of();
		}

		final ArrayList<CalendarContinuousQuery.Event> events = new ArrayList<>();

		final CalendarEntry oldEntry = oldAndNewEntry.getOldValue();
		final CalendarEntry newEntry = oldAndNewEntry.getNewValue();
		if (oldEntry != null
				&& !CalendarEntryAndSimulationId.equals(oldEntry.getCalendarEntryAndSimulationId(), newEntry != null ? newEntry.getCalendarEntryAndSimulationId() : null))
		{
			events.add(CalendarContinuousQuery.EntryDeletedEvent.of(oldEntry.getCalendarEntryAndSimulationId()));
		}

		if (newEntry != null)
		{
			events.add(CalendarContinuousQuery.EntryChangedEvent.of(newEntry));
		}

		return events;
	}
}

