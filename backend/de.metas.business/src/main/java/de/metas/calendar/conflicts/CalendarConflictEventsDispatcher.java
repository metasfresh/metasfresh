package de.metas.calendar.conflicts;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

@Service
public class CalendarConflictEventsDispatcher
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final ListenersGroup actualChangesListeners = new ListenersGroup();
	private final ConcurrentHashMap<SimulationPlanId, ListenersGroup> simulationChangesListenersMap = new ConcurrentHashMap<>();
	private static final String TRX_PROPERTY_NAME = CalendarConflictEventsDispatcher.class.getName() + ".lazyEvents";

	public void subscribe(@Nullable SimulationPlanId simulationId, @NonNull final CalendarConflictEventsListener listener)
	{
		if (simulationId == null)
		{
			actualChangesListeners.subscribe(listener);
		}
		else
		{
			simulationChangesListenersMap.compute(
					simulationId,
					(k, existingListenersGroup) -> {
						final ListenersGroup listenersGroup = existingListenersGroup != null
								? existingListenersGroup
								: new ListenersGroup();

						listenersGroup.subscribe(listener);

						return listenersGroup;
					}
			);
		}
	}

	public void unsubscribe(@Nullable SimulationPlanId simulationId, @NonNull final CalendarConflictEventsListener listener)
	{
		if (simulationId == null)
		{
			actualChangesListeners.unsubscribe(listener);
		}
		else
		{
			simulationChangesListenersMap.computeIfPresent(
					simulationId,
					(simulationId0, listenersGroup) -> {
						listenersGroup.unsubscribe(listener);
						if (listenersGroup.hasSubscriptions())
						{
							return listenersGroup;
						}
						else
						{
							return null; // remove the listenersGroup
						}
					});
		}
	}

	private ListenersGroup getListenersGroupOrNull(@Nullable SimulationPlanId simulationId)
	{
		if (simulationId == null)
		{
			return actualChangesListeners;
		}
		else
		{
			return simulationChangesListenersMap.get(simulationId);
		}
	}

	public void notifyChangesAfterCommit(@Nullable final SimulationPlanId simulationId, @NonNull final Supplier<CalendarConflictChangesEvent> event)
	{
		trxManager.accumulateAndProcessAfterCommit(
				TRX_PROPERTY_NAME,
				ImmutableList.of(CalendarConflictChangesLazyEvent.of(simulationId, event)),
				this::notifyChangesNow);
	}

	private void notifyChangesNow(@NonNull final ImmutableList<CalendarConflictChangesLazyEvent> lazyEvents)
	{
		if (lazyEvents.isEmpty())
		{
			return;
		}

		// using ArrayListMultimap because SimulationId might be null too
		final ArrayListMultimap<SimulationPlanId, CalendarConflictChangesLazyEvent> lazyEventsBySimulationId = lazyEvents.stream()
				.collect(GuavaCollectors.toArrayListMultimapByKey(CalendarConflictChangesLazyEvent::getSimulationId));

		for (final SimulationPlanId simulationId : lazyEventsBySimulationId.keySet())
		{
			final ListenersGroup listenersGroup = getListenersGroupOrNull(simulationId);
			if (listenersGroup != null)
			{
				listenersGroup.notifyChangesNow(lazyEventsBySimulationId.get(simulationId));
			}
		}
	}

	//
	//
	//
	//
	//

	private static class ListenersGroup
	{
		private final CopyOnWriteArrayList<CalendarConflictEventsListener> listeners = new CopyOnWriteArrayList<>();

		public void subscribe(@NonNull final CalendarConflictEventsListener listener)
		{
			listeners.addIfAbsent(listener);
		}

		public void unsubscribe(@NonNull final CalendarConflictEventsListener listener)
		{
			listeners.remove(listener);
		}

		public boolean hasSubscriptions()
		{
			return !listeners.isEmpty();
		}

		public void notifyChangesNow(@NonNull final List<CalendarConflictChangesLazyEvent> lazyEvents)
		{
			if (listeners.isEmpty())
			{
				return;
			}

			final ImmutableList<CalendarConflictChangesEvent> events = lazyEvents
					.stream()
					.map(CalendarConflictChangesLazyEvent::toEvent)
					.collect(ImmutableList.toImmutableList());
			if (events.isEmpty())
			{
				return;
			}

			listeners.forEach(listener -> listener.onCalendarConflictChangesEvents(events));
		}
	}
}
