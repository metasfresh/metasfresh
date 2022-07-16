package de.metas.project.workorder.calendar;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.product.ResourceId;
import de.metas.project.workorder.WOProjectResource;
import de.metas.project.workorder.WOProjectResourceId;
import de.metas.project.workorder.WOProjectResourceSimulation;
import de.metas.project.workorder.WOProjectService;
import de.metas.project.workorder.conflicts.WOProjectConflictService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;

@Service
public class WOProjectSimulationService
{
	private final WOProjectService woProjectService;

	private final WOProjectSimulationRepository woProjectSimulationRepository;
	private final WOProjectConflictService woProjectConflictService;

	public WOProjectSimulationService(
			final @NonNull WOProjectService woProjectService,
			final @NonNull WOProjectSimulationRepository woProjectSimulationRepository,
			final @NonNull WOProjectConflictService woProjectConflictService)
	{
		this.woProjectService = woProjectService;
		this.woProjectSimulationRepository = woProjectSimulationRepository;
		this.woProjectConflictService = woProjectConflictService;
	}

	public WOProjectSimulationPlan getSimulationPlanById(@NonNull final SimulationPlanId simulationId)
	{
		return woProjectSimulationRepository.getById(simulationId);
	}

	public void savePlan(final WOProjectSimulationPlan plan)
	{
		woProjectSimulationRepository.savePlan(plan);
	}

	public void copySimulationDataTo(
			@NonNull final SimulationPlanId fromSimulationId,
			@NonNull final SimulationPlanId toSimulationId)
	{
		woProjectSimulationRepository.copySimulationDataTo(fromSimulationId, toSimulationId);
	}

	public void completeSimulation(@NonNull final SimulationPlanId simulationId)
	{
		final WOProjectSimulationPlan simulationPlan = woProjectSimulationRepository.getById(simulationId);

		woProjectConflictService.assertAllConflictsApproved(simulationId);

		final ImmutableMap<WOProjectResourceId, WOProjectResourceSimulation> projectResourceSimulationsToApply = Maps.uniqueIndex(simulationPlan.getProjectResources(), WOProjectResourceSimulation::getProjectResourceId);

		final HashMap<WOProjectResourceId, WOProjectResourceSimulation> projectResourceSimulationsApplied = new HashMap<>();
		final HashSet<ResourceId> affectedResourceIds = new HashSet<>();

		woProjectService.updateProjectResourcesByIds(
				projectResourceSimulationsToApply.keySet(),
				projectResource -> {
					final WOProjectResourceSimulation simulation = projectResourceSimulationsToApply.get(projectResource.getId());

					// do nothing if simulation was already applied. shall not happen.
					if (simulation.isAppliedOnActualData())
					{
						return projectResource;
					}

					final WOProjectResource changedProjectResource = simulation.applyOn(projectResource);
					final WOProjectResourceSimulation appliedSimulation = simulation.markingAsApplied(projectResource.getDateRange());
					projectResourceSimulationsApplied.put(appliedSimulation.getProjectResourceId(), appliedSimulation);

					affectedResourceIds.add(projectResource.getResourceId());

					return changedProjectResource;
				});

		final WOProjectSimulationPlan simulationPlanApplied = simulationPlan.toBuilder()
				.projectResourcesById(projectResourceSimulationsApplied)
				.build();
		woProjectSimulationRepository.savePlan(simulationPlanApplied);

		woProjectConflictService.checkConflicts(affectedResourceIds);
		//woProjectConflictService.deleteBySimulationId(simulationId); // TODO: instead of deleting them, maybe we shall just mark them as processed or IsActive=N
	}
}
