package de.metas.calendar.conflicts;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
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
			simulationChangesListenersMap.compute(
					simulationId,
					(simulationId0, listenersGroup) -> {
						if (listenersGroup != null)
						{
							listenersGroup.unsubscribe(listener);
							if (listenersGroup.hasSubscriptions())
							{
								return listenersGroup;
							}
							else
							{
								return null;
							}
						}
						else
						{
							return null;
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

	public void notifyChanges(@Nullable final SimulationPlanId simulationId, @NonNull final Supplier<CalendarConflictChangesEvent> event)
	{
		final ImmutableList<CalendarConflictChangesLazyEvent> lazyEvents = ImmutableList.of(CalendarConflictChangesLazyEvent.of(simulationId, event));

		final ITrx trx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		if (trxManager.isActive(trx))
		{
			trx.accumulateAndProcessAfterCommit(TRX_PROPERTY_NAME, lazyEvents, this::notifyChangesNow);
		}
		else
		{
			notifyChangesNow(lazyEvents);
		}
	}

	private void notifyChangesNow(ImmutableList<CalendarConflictChangesLazyEvent> lazyEvents)
	{
		if (lazyEvents.isEmpty())
		{
			return;
		}

		final ImmutableListMultimap<SimulationPlanId, CalendarConflictChangesLazyEvent>
				lazyEventsBySimulationId = Multimaps.index(lazyEvents, CalendarConflictChangesLazyEvent::getSimulationId);

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

		public void notifyChangesNow(@NonNull final ImmutableList<CalendarConflictChangesLazyEvent> lazyEvents)
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
