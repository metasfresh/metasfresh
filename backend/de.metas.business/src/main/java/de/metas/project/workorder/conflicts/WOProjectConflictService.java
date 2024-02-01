package de.metas.project.workorder.conflicts;

import de.metas.calendar.conflicts.CalendarConflictEventsDispatcher;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.simulation.SimulationPlanRepository;
import de.metas.project.ProjectId;
import de.metas.project.workorder.calendar.WOProjectResourceCalendarQuery;
import de.metas.project.workorder.calendar.WOProjectSimulationPlan;
import de.metas.project.workorder.calendar.WOProjectSimulationRepository;
import de.metas.project.workorder.project.WOProjectRepository;
import de.metas.project.workorder.resource.ResourceIdAndType;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.resource.WOProjectResourceRepository;
import de.metas.util.InSetPredicate;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
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
			@NonNull final InSetPredicate<ResourceIdAndType> resourceIds)
	{
		if (resourceIds.isNone())
		{
			return ResourceAllocationConflicts.empty(simulationId);
		}

		final InSetPredicate<ProjectId> activeProjectIds = InSetPredicate.only(woProjectRepository.getActiveProjectIds());
		if (activeProjectIds.isNone())
		{
			return ResourceAllocationConflicts.empty(simulationId);
		}

		final InSetPredicate<WOProjectResourceId> projectResourceIds = woProjectResourceRepository.getProjectResourceIdsPredicate(
				WOProjectResourceCalendarQuery.builder()
						.resourceIds(resourceIds)
						.projectIds(activeProjectIds)
						.build());
		if (projectResourceIds.isNone())
		{
			return ResourceAllocationConflicts.empty(simulationId);
		}

		return conflictRepository.getActualAndSimulation(simulationId, activeProjectIds, projectResourceIds);
	}

	public void checkAllConflicts(@NonNull Collection<ResourceIdAndType> resourceIds)
	{
		newConflictsChecker()
				.resourceIds(resourceIds)
				.build()
				.execute();
	}

	public void checkAllConflictsExcludingSimulation(@NonNull Set<ResourceIdAndType> resourceIds, @NonNull final SimulationPlanId excludeSimulationId)
	{
		newConflictsChecker()
				.resourceIds(resourceIds)
				.excludeSimulationId(excludeSimulationId)
				.build()
				.execute();
	}

	public void checkSimulationConflicts(
			@NonNull final WOProjectSimulationPlan onlySimulation,
			@NonNull Set<ResourceIdAndType> resourceIds)
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
