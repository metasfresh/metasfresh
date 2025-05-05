package de.metas.calendar.plan_optimizer;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SimulationOptimizerStatusDispatcher
{
	private static final Logger logger = LogManager.getLogger(SimulationOptimizerStatusDispatcher.class);
	private final ConcurrentHashMap<SimulationPlanId, CopyOnWriteArrayList<SimulationOptimizerStatusListener>> listenersMap = new ConcurrentHashMap<>();

	public void subscribe(@NonNull final SimulationPlanId simulationId, @NonNull final SimulationOptimizerStatusListener listener)
	{
		final CopyOnWriteArrayList<SimulationOptimizerStatusListener> listeners = listenersMap.computeIfAbsent(simulationId, k -> new CopyOnWriteArrayList<>());
		listeners.addIfAbsent(listener);
	}

	public void unsubscribe(@NonNull final SimulationPlanId simulationId, @NonNull final SimulationOptimizerStatusListener listener)
	{
		final CopyOnWriteArrayList<SimulationOptimizerStatusListener> listeners = listenersMap.get(simulationId);
		if (listeners != null)
		{
			listeners.remove(listener);
		}
	}

	private List<SimulationOptimizerStatusListener> getListeners(@NonNull final SimulationPlanId simulationId)
	{
		final CopyOnWriteArrayList<SimulationOptimizerStatusListener> listeners = listenersMap.get(simulationId);
		return listeners != null ? listeners : ImmutableList.of();
	}

	public void notifyStarted(@NonNull final SimulationPlanId simulationId)
	{
		getListeners(simulationId).forEach(listener -> {
			try
			{
				listener.onSimulationOptimizerStarted(simulationId);
			}
			catch (final Exception ex)
			{
				logger.warn("Failed invoking onSimulationOptimizerStarted: listener={}, simulationId={}", listener, simulationId);
			}
		});
	}

	public void notifyProgress(@NonNull final Plan plan)
	{
		getListeners(plan.getSimulationId()).forEach(listener -> {
			try
			{
				listener.onSimulationOptimizerProgress(plan);
			}
			catch (final Exception ex)
			{
				logger.warn("Failed invoking onSimulationOptimizerProgress: listener={}, plan={}", listener, plan);
			}
		});
	}

	public void notifyStopped(@NonNull final SimulationPlanId simulationId)
	{
		getListeners(simulationId).forEach(listener -> {
			try
			{
				listener.onSimulationOptimizerStopped(simulationId);
			}
			catch (final Exception ex)
			{
				logger.warn("Failed invoking onSimulationOptimizerStopped: listener={}, simulationId={}", listener, simulationId);
			}
		});
	}
}
