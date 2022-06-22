package de.metas.project.budget;

import de.metas.calendar.simulation.CalendarSimulationId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class BudgetProjectSimulationRepository
{
	// TODO: replace it with CCache when we will retrieve from DB
	private final ConcurrentHashMap<CalendarSimulationId, BudgetProjectSimulationPlan> simulationPlans = new ConcurrentHashMap<>();

	public BudgetProjectSimulationPlan getSimulationPlanById(@NonNull CalendarSimulationId simulationPlanId)
	{
		return simulationPlans.computeIfAbsent(simulationPlanId, this::retrieveSimulationPlanById);
	}

	private BudgetProjectSimulationPlan retrieveSimulationPlanById(@NonNull CalendarSimulationId simulationPlanId)
	{
		// TODO get it from DB
		return new BudgetProjectSimulationPlan(simulationPlanId);
	}

	public BudgetProjectResourceSimulation createOrUpdate(@NonNull final BudgetProjectResourceSimulation.UpdateRequest request)
	{
		return getSimulationPlanById(request.getSimulationId())
				.changeById(
						request.getProjectAndResourceId(),
						simulation -> BudgetProjectResourceSimulation.reduce(simulation, request)
				);
	}

}
