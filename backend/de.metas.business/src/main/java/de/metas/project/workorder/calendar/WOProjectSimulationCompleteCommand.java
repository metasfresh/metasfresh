package de.metas.project.workorder.calendar;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.project.workorder.conflicts.WOProjectConflictService;
import de.metas.project.workorder.project.WOProjectService;
import de.metas.project.workorder.resource.ResourceIdAndType;
import de.metas.project.workorder.resource.WOProjectResource;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.resource.WOProjectResourceSimulation;
import lombok.Builder;
import lombok.NonNull;

import java.util.HashMap;
import java.util.HashSet;

class WOProjectSimulationCompleteCommand
{
	// services:
	@NonNull private final WOProjectService woProjectService;
	@NonNull private final WOProjectSimulationRepository woProjectSimulationRepository;
	@NonNull private final WOProjectConflictService woProjectConflictService;

	// params:
	@NonNull final SimulationPlanId simulationId;

	@Builder
	private WOProjectSimulationCompleteCommand(
			final @NonNull WOProjectService woProjectService,
			final @NonNull WOProjectSimulationRepository woProjectSimulationRepository,
			final @NonNull WOProjectConflictService woProjectConflictService,
			//
			final @NonNull SimulationPlanId simulationId)
	{
		this.woProjectService = woProjectService;
		this.woProjectSimulationRepository = woProjectSimulationRepository;
		this.woProjectConflictService = woProjectConflictService;

		this.simulationId = simulationId;
	}

	public void execute()
	{
		final WOProjectSimulationPlan simulationPlan = woProjectSimulationRepository.getById(simulationId);

		woProjectConflictService.assertAllConflictsApproved(simulationId);

		final ImmutableMap<WOProjectResourceId, WOProjectResourceSimulation> projectResourceSimulationsToApply = Maps.uniqueIndex(simulationPlan.getProjectResources(), WOProjectResourceSimulation::getProjectResourceId);

		final HashMap<WOProjectResourceId, WOProjectResourceSimulation> projectResourceSimulationsApplied = new HashMap<>();
		final HashSet<ResourceIdAndType> affectedResourceIds = new HashSet<>();

		woProjectService.updateProjectResourcesByIds(
				projectResourceSimulationsToApply.keySet(),
				projectResource -> {
					final WOProjectResourceSimulation simulation = projectResourceSimulationsToApply.get(projectResource.getWoProjectResourceId());

					// do nothing if simulation was already applied. shall not happen.
					if (simulation.isAppliedOnActualData())
					{
						return projectResource;
					}

					final WOProjectResource changedProjectResource = simulation.applyOn(projectResource);
					final WOProjectResourceSimulation appliedSimulation = simulation.markingAsApplied(projectResource.getDateRange());
					projectResourceSimulationsApplied.put(appliedSimulation.getProjectResourceId(), appliedSimulation);

					affectedResourceIds.add(projectResource.getResourceIdAndType());

					return changedProjectResource;
				});

		final WOProjectSimulationPlan simulationPlanApplied = simulationPlan.toBuilder()
				.projectResourcesById(projectResourceSimulationsApplied)
				.build();
		woProjectSimulationRepository.savePlan(simulationPlanApplied);

		woProjectConflictService.checkAllConflictsExcludingSimulation(affectedResourceIds, simulationId);
		woProjectConflictService.deactivateBySimulationId(simulationId);
	}
}
