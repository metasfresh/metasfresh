package de.metas.project.workorder.conflicts;

import de.metas.calendar.conflicts.CalendarConflictEventsDispatcher;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.simulation.SimulationPlanRepository;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.workorder.WOProjectRepository;
import de.metas.project.workorder.WOProjectResourceId;
import de.metas.project.workorder.WOProjectResourceRepository;
import de.metas.project.workorder.calendar.WOProjectSimulationPlan;
import de.metas.project.workorder.calendar.WOProjectSimulationRepository;
import de.metas.util.InSetPredicate;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Set;

@Service
public class WOProjectConflictService
{
	@NonNull private final SimulationPlanRepository simulationPlanRepository;
	@NonNull private final WOProjectResourceConflictRepository conflictRepository;
	@NonNull private final WOProjectSimulationRepository woProjectSimulationRepository;

	@NonNull private final WOProjectRepository woProjectRepository;
	@NonNull private final WOProjectResourceRepository woProjectResourceRepository;
	@NonNull private final CalendarConflictEventsDispatcher eventsDispatcher;

	public WOProjectConflictService(
			final @NonNull SimulationPlanRepository simulationPlanRepository,
			final @NonNull WOProjectResourceConflictRepository conflictRepository,
			final @NonNull WOProjectSimulationRepository woProjectSimulationRepository,
			final @NonNull WOProjectRepository woProjectRepository,
			final @NonNull WOProjectResourceRepository woProjectResourceRepository,
			final @NonNull CalendarConflictEventsDispatcher eventsDispatcher)
	{
		this.simulationPlanRepository = simulationPlanRepository;
		this.conflictRepository = conflictRepository;
		this.woProjectSimulationRepository = woProjectSimulationRepository;
		this.woProjectRepository = woProjectRepository;
		this.woProjectResourceRepository = woProjectResourceRepository;
		this.eventsDispatcher = eventsDispatcher;
	}

	public ResourceAllocationConflicts getActualAndSimulation(
			@Nullable final SimulationPlanId simulationId,
			@NonNull final InSetPredicate<ResourceId> resourceIds,
			@NonNull final InSetPredicate<ProjectId> projectIds)
	{
		if (resourceIds.isNone())
		{
			return ResourceAllocationConflicts.empty(simulationId);
		}

		final InSetPredicate<ProjectId> activeProjectIds = InSetPredicate.only(woProjectRepository.getActiveProjectIds(projectIds));
		if (activeProjectIds.isNone())
		{
			return ResourceAllocationConflicts.empty(simulationId);
		}

		final InSetPredicate<WOProjectResourceId> projectResourceIds = woProjectResourceRepository.getProjectResourceIdsPredicateByResourceIds(
				resourceIds,
				activeProjectIds);
		if(projectResourceIds.isNone())
		{
			return ResourceAllocationConflicts.empty(simulationId);
		}

		return conflictRepository.getActualAndSimulation(simulationId, activeProjectIds, projectResourceIds);
	}

	public void checkAllConflicts(@NonNull Set<ResourceId> resourceIds)
	{
		newConflictsChecker()
				.resourceIds(resourceIds)
				.build()
				.execute();
	}

	public void checkAllConflictsExcludingSimulation(@NonNull Set<ResourceId> resourceIds, @NonNull final SimulationPlanId excludeSimulationId)
	{
		newConflictsChecker()
				.resourceIds(resourceIds)
				.excludeSimulationId(excludeSimulationId)
				.build()
				.execute();
	}

	public void checkSimulationConflicts(
			@NonNull final WOProjectSimulationPlan onlySimulation,
			@NonNull Set<ResourceId> resourceIds)
	{
		newConflictsChecker()
				.resourceIds(resourceIds)
				.onlySimulation(onlySimulation)
				.build()
				.execute();
	}

	private WOProjectConflictsCheckerCommand.WOProjectConflictsCheckerCommandBuilder newConflictsChecker()
	{
		return WOProjectConflictsCheckerCommand.builder()
				.simulationPlanRepository(simulationPlanRepository)
				.conflictRepository(conflictRepository)
				.woProjectSimulationRepository(woProjectSimulationRepository)
				.woProjectRepository(woProjectRepository)
				.woProjectResourceRepository(woProjectResourceRepository)
				.eventsDispatcher(eventsDispatcher);
	}

	public void assertAllConflictsApproved(@NonNull final SimulationPlanId simulationId)
	{
		final ResourceAllocationConflicts simulationOnlyConflicts = conflictRepository.getSimulationOnly(simulationId);
		if (!simulationOnlyConflicts.isSimulationConflictsApproved())
		{
			throw new AdempiereException("All conflicts must be approved first"); // TODO trl
		}
	}

	public void deactivateBySimulationId(@NonNull final SimulationPlanId simulationId)
	{
		conflictRepository.deactivateBySimulationId(simulationId);
	}
}
