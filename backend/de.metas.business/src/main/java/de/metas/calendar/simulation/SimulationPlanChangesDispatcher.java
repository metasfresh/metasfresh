package de.metas.calendar.simulation;

import de.metas.logging.LogManager;
import lombok.NonNull;
import org.slf4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class SimulationPlanChangesDispatcher
{
	private static final Logger logger = LogManager.getLogger(SimulationPlanChangesDispatcher.class);

	private final ConcurrentHashMap<SimulationPlanId, ListenersGroup> listenersBySimulationId = new ConcurrentHashMap<>();

	public void subscribe(@NonNull final SimulationPlanId simulationId, @NonNull final SimulationPlanChangesListener listener)
	{
		listenersBySimulationId
				.computeIfAbsent(simulationId, (k) -> new ListenersGroup())
				.subscribe(listener);
	}

	public void unsubscribe(@NonNull final SimulationPlanId simulationId, @NonNull final SimulationPlanChangesListener listener)
	{
		listenersBySimulationId.computeIfPresent(
				simulationId,
				(k, listenersGroup) -> {
					listenersGroup.unsubscribe(listener);
					// remove the listenersGroup if there are no more subscriptions
					return listenersGroup.hasSubscriptions() ? listenersGroup : null;
				});
	}

	public void notifyOnAfterComplete(@NonNull SimulationPlanRef simulationRef)
	{
		final ListenersGroup listenersGroup = listenersBySimulationId.get(simulationRef.getId());
		if (listenersGroup == null)
		{
			return;
		}

		listenersGroup.notifyOnAfterComplete(simulationRef);
	}

	private static class ListenersGroup
	{
		private final CopyOnWriteArrayList<SimulationPlanChangesListener> listeners = new CopyOnWriteArrayList<>();

		public void subscribe(@NonNull final SimulationPlanChangesListener listener)
		{
			listeners.addIfAbsent(listener);
		}

		public void unsubscribe(@NonNull final SimulationPlanChangesListener listener)
		{
			listeners.remove(listener);
		}

		public boolean hasSubscriptions()
		{
			return !listeners.isEmpty();
		}

		public void notifyOnAfterComplete(@NonNull SimulationPlanRef simulationRef)
		{
			forEachListener("onSimulationPlanAfterComplete", listener -> listener.onSimulationPlanAfterComplete(simulationRef));
		}

		@SuppressWarnings("SameParameterValue")
		private void forEachListener(final String actionName, final Consumer<SimulationPlanChangesListener> action)
		{
			listeners.forEach(listener -> {
				try
				{
					action.accept(listener);
				}
				catch (Exception ex)
				{
					logger.warn("Failed while firing `{}` on listener `{}`. Ignored.", actionName, listener, ex);
				}
			});
		}
	}
}
