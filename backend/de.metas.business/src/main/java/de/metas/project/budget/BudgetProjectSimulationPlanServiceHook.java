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
	private final BudgetProjectSimulationRepository budgetProjectSimulationRepository;

	public BudgetProjectSimulationPlanServiceHook(final BudgetProjectSimulationRepository budgetProjectSimulationRepository) {this.budgetProjectSimulationRepository = budgetProjectSimulationRepository;}

	@Override
	public void onNewSimulationPlan(
			final @NonNull SimulationPlanRef simulationRef,
			final @Nullable SimulationPlanId copyFromSimulationPlanId)
	{
		if (copyFromSimulationPlanId != null)
		{
			budgetProjectSimulationRepository.copySimulationDataTo(copyFromSimulationPlanId, simulationRef.getId());
		}
	}
}
