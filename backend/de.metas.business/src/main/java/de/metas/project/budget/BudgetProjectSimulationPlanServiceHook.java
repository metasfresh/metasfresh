package de.metas.project.budget;

import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.simulation.SimulationPlanRef;
import de.metas.calendar.simulation.SimulationPlanServiceHook;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class BudgetProjectSimulationPlanServiceHook implements SimulationPlanServiceHook
{
	private final BudgetProjectSimulationService budgetProjectSimulationService;

	public BudgetProjectSimulationPlanServiceHook(final BudgetProjectSimulationService budgetProjectSimulationService) {this.budgetProjectSimulationService = budgetProjectSimulationService;}

	@Override
	public void onNewSimulationPlan(
			final @NonNull SimulationPlanRef simulationRef,
			final @Nullable SimulationPlanId copyFromSimulationPlanId)
	{
		if (copyFromSimulationPlanId != null)
		{
			budgetProjectSimulationService.copySimulationDataTo(copyFromSimulationPlanId, simulationRef.getId());
		}
	}

	@Override
	public void onComplete(@NonNull final SimulationPlanRef simulationRef)
	{
		budgetProjectSimulationService.completeSimulation(simulationRef);
	}
}
