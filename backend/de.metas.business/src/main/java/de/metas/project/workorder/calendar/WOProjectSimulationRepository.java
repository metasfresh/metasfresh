package de.metas.project.workorder.calendar;

import de.metas.calendar.simulation.CalendarSimulationId;
import de.metas.project.workorder.WOProjectResourceSimulation;
import de.metas.project.workorder.WOProjectStepAndResourceId;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.project.workorder.WOProjectStepSimulation;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;

@Repository
public class WOProjectSimulationRepository
{
	// TODO: replace it with CCache when we will retrieve from DB
	private final ConcurrentHashMap<CalendarSimulationId, WOProjectSimulationPlan> simulationPlans = new ConcurrentHashMap<>();

	public WOProjectSimulationPlan getSimulationPlanById(@NonNull CalendarSimulationId simulationPlanId)
	{
		return simulationPlans.computeIfAbsent(simulationPlanId, this::retrieveSimulationPlanById);
	}

	private WOProjectSimulationPlan changeSimulationPlanById(
			@NonNull CalendarSimulationId simulationPlanId,
			@NonNull UnaryOperator<WOProjectSimulationPlan> mapper)
	{
		return simulationPlans.compute(
				simulationPlanId,
				(k, loadedSimulationPlan) -> {
					WOProjectSimulationPlan simulationPlan = loadedSimulationPlan != null ? loadedSimulationPlan : retrieveSimulationPlanById(simulationPlanId);
					final WOProjectSimulationPlan changedSimulationPlan = mapper.apply(simulationPlan);
					if (changedSimulationPlan == null)
					{
						throw new AdempiereException("Changing simulation plan to null not allowed");
					}

					saveToDB(changedSimulationPlan);

					return changedSimulationPlan;
				});
	}

	public void changeSimulationPlan(final WOProjectSimulationPlan plan)
	{
		simulationPlans.compute(
				plan.getSimulationPlanId(),
				(k, loadedSimulationPlan) -> {
					saveToDB(plan);
					return plan;
				});
	}

	private WOProjectSimulationPlan retrieveSimulationPlanById(@NonNull CalendarSimulationId simulationPlanId)
	{
		// TODO get it from DB
		return WOProjectSimulationPlan.builder()
				.simulationPlanId(simulationPlanId)
				.build();
	}

	private void saveToDB(@NonNull WOProjectSimulationPlan plan)
	{
		// TODO implement
	}

	public WOProjectResourceSimulation createOrUpdate(@NonNull final WOProjectResourceSimulation.UpdateRequest request)
	{
		final CalendarSimulationId simulationId = request.getSimulationId();
		final WOProjectStepAndResourceId projectStepAndResourceId = request.getProjectStepAndResourceId();

		final WOProjectSimulationPlan changedPlan = changeSimulationPlanById(
				simulationId,
				plan -> plan.mapByProjectResourceId(
						projectStepAndResourceId,
						simulation -> WOProjectResourceSimulation.reduce(simulation, request))
		);

		return changedPlan.getProjectResourceById(projectStepAndResourceId.getProjectResourceId());
	}

	public void createOrUpdate(@NonNull final WOProjectStepSimulation.UpdateRequest request)
	{
		final CalendarSimulationId simulationId = request.getSimulationId();
		final WOProjectStepId stepId = request.getStepId();

		changeSimulationPlanById(
				simulationId,
				plan -> plan.mapByStepId(
						stepId,
						simulation -> WOProjectStepSimulation.reduce(simulation, request))
		);
	}
}
