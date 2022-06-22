package de.metas.project.workorder.calendar;

import de.metas.calendar.simulation.CalendarSimulationId;
import de.metas.project.workorder.WOProjectResourceSimulation;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class WOProjectSimulationRepository
{
	// TODO: replace it with CCache when we will retrieve from DB
	private final ConcurrentHashMap<CalendarSimulationId, WOProjectSimulationPlan> simulationPlans = new ConcurrentHashMap<>();

	public WOProjectSimulationPlan getSimulationPlanById(@NonNull CalendarSimulationId simulationPlanId)
	{
		return simulationPlans.computeIfAbsent(simulationPlanId, this::retrieveSimulationPlanById);
	}

	private WOProjectSimulationPlan retrieveSimulationPlanById(@NonNull CalendarSimulationId simulationPlanId)
	{
		// TODO get it from DB
		return new WOProjectSimulationPlan(simulationPlanId);
	}

	public WOProjectResourceSimulation createOrUpdate(@NonNull final WOProjectResourceSimulation.UpdateRequest request)
	{
		return getSimulationPlanById(request.getSimulationId())
				.changeById(
						request.getProjectStepAndResourceId(),
						simulation -> WOProjectResourceSimulation.reduce(simulation, request)
				);
	}

}
