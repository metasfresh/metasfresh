package de.metas.project.workorder.calendar;

import com.google.common.collect.ImmutableSet;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.product.ResourceId;
import de.metas.project.workorder.WOProjectService;
import de.metas.project.workorder.conflicts.WOProjectConflictService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

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

	public WOProjectSimulationPlan copySimulationDataTo(
			@NonNull final SimulationPlanId fromSimulationId,
			@NonNull final SimulationPlanId toSimulationId)
	{
		return woProjectSimulationRepository.copySimulationDataTo(fromSimulationId, toSimulationId);
	}

	public void completeSimulation(@NonNull final SimulationPlanId simulationId)
	{
		WOProjectSimulationCompleteCommand.builder()
				.woProjectService(woProjectService)
				.woProjectSimulationRepository(woProjectSimulationRepository)
				.woProjectConflictService(woProjectConflictService)
				.simulationId(simulationId)
				.build()
				.execute();
	}

	public void checkSimulationConflicts(@NonNull final WOProjectSimulationPlan simulationPlan)
	{
		final ImmutableSet<ResourceId> resourceIds = woProjectService.getResourceIdsByProjectResourceIds(simulationPlan.getProjectResourceIds());
		if (resourceIds.isEmpty())
		{
			return;
		}

		woProjectConflictService.checkSimulationConflicts(simulationPlan, resourceIds);
	}
}
