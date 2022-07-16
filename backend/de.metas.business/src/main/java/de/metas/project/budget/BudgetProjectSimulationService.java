package de.metas.project.budget;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.simulation.SimulationPlanRef;
import lombok.NonNull;
import org.adempiere.util.lang.OldAndNewValues;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class BudgetProjectSimulationService
{
	private final BudgetProjectService budgetProjectService;
	private final BudgetProjectSimulationRepository budgetProjectSimulationRepository;

	public BudgetProjectSimulationService(
			final @NonNull BudgetProjectService budgetProjectService,
			final @NonNull BudgetProjectSimulationRepository budgetProjectSimulationRepository)
	{
		this.budgetProjectService = budgetProjectService;
		this.budgetProjectSimulationRepository = budgetProjectSimulationRepository;
	}

	public void copySimulationDataTo(
			@NonNull final SimulationPlanId fromSimulationId,
			@NonNull final SimulationPlanId toSimulationId)
	{
		budgetProjectSimulationRepository.copySimulationDataTo(fromSimulationId, toSimulationId);
	}

	public BudgetProjectSimulationPlan getSimulationPlanById(@NonNull final SimulationPlanId simulationId)
	{
		return budgetProjectSimulationRepository.getSimulationPlanById(simulationId);
	}

	public OldAndNewValues<BudgetProjectResourceSimulation> createOrUpdate(@NonNull final BudgetProjectResourceSimulation.UpdateRequest request)
	{
		return budgetProjectSimulationRepository.createOrUpdate(request);
	}

	public void completeSimulation(@NonNull final SimulationPlanRef simulationRef)
	{
		final BudgetProjectSimulationPlan simulationPlan = budgetProjectSimulationRepository.getSimulationPlanById(simulationRef.getId());

		final ImmutableMap<BudgetProjectResourceId, BudgetProjectResourceSimulation> projectResourceSimulationsToApply = Maps.uniqueIndex(simulationPlan.getAll(), BudgetProjectResourceSimulation::getProjectResourceId);

		final HashMap<BudgetProjectResourceId, BudgetProjectResourceSimulation> projectResourceSimulationsApplied = new HashMap<>();

		budgetProjectService.updateProjectResourcesByIds(
				projectResourceSimulationsToApply.keySet(),
				projectResource -> {
					final BudgetProjectResourceSimulation simulation = projectResourceSimulationsToApply.get(projectResource.getId());

					// do nothing if simulation was already applied. shall not happen.
					if (simulation.isAppliedOnActualData())
					{
						return projectResource;
					}

					final BudgetProjectResource changedProjectResource = simulation.applyOn(projectResource);
					final BudgetProjectResourceSimulation appliedSimulation = simulation.markingAsApplied(projectResource.getDateRange());
					projectResourceSimulationsApplied.put(appliedSimulation.getProjectResourceId(), appliedSimulation);

					return changedProjectResource;
				});

		final BudgetProjectSimulationPlan simulationPlanApplied = simulationPlan.toBuilder()
				.projectResourcesById(projectResourceSimulationsApplied)
				.build();
		budgetProjectSimulationRepository.savePlan(simulationPlanApplied);
	}
}
