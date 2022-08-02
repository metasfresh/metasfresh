package de.metas.project.workorder.conflicts;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.calendar.conflicts.CalendarConflictEventsDispatcher;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.simulation.SimulationPlanRepository;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.workorder.WOProjectRepository;
import de.metas.project.workorder.WOProjectResource;
import de.metas.project.workorder.WOProjectResourceId;
import de.metas.project.workorder.WOProjectResourceRepository;
import de.metas.project.workorder.WOProjectResourceSimulation;
import de.metas.project.workorder.calendar.WOProjectSimulationPlan;
import de.metas.project.workorder.calendar.WOProjectSimulationRepository;
import de.metas.util.GuavaCollectors;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;

import static de.metas.project.workorder.conflicts.WOProjectCalendarConflictsConverters.toEvent;

class WOProjectConflictsCheckerCommand
{
	// services:
	@NonNull private final SimulationPlanRepository simulationPlanRepository;
	@NonNull private final WOProjectResourceConflictRepository conflictRepository;
	@NonNull private final WOProjectSimulationRepository woProjectSimulationRepository;
	@NonNull private final WOProjectRepository woProjectRepository;
	@NonNull private final WOProjectResourceRepository woProjectResourceRepository;
	@NonNull private final CalendarConflictEventsDispatcher eventsDispatcher;

	// params:
	@NonNull private final ImmutableSet<ResourceId> resourceIds;
	@Nullable private final WOProjectSimulationPlan onlySimulation;
	@Nullable private final SimulationPlanId excludeSimulationId;

	@Builder
	private WOProjectConflictsCheckerCommand(
			@NonNull final SimulationPlanRepository simulationPlanRepository,
			@NonNull final WOProjectResourceConflictRepository conflictRepository,
			@NonNull final WOProjectSimulationRepository woProjectSimulationRepository,
			@NonNull final WOProjectRepository woProjectRepository,
			@NonNull final WOProjectResourceRepository woProjectResourceRepository,
			@NonNull final CalendarConflictEventsDispatcher eventsDispatcher,
			//
			@NonNull final Collection<ResourceId> resourceIds,
			@Nullable final WOProjectSimulationPlan onlySimulation,
			@Nullable final SimulationPlanId excludeSimulationId)
	{
		this.simulationPlanRepository = simulationPlanRepository;
		this.conflictRepository = conflictRepository;
		this.woProjectSimulationRepository = woProjectSimulationRepository;
		this.woProjectRepository = woProjectRepository;
		this.woProjectResourceRepository = woProjectResourceRepository;
		this.eventsDispatcher = eventsDispatcher;

		this.resourceIds = ImmutableSet.copyOf(resourceIds);
		this.onlySimulation = onlySimulation;
		this.excludeSimulationId = excludeSimulationId;
	}

	public void execute()
	{
		final ImmutableMap<WOProjectResourceId, WOProjectResource> projectResources = getProjectResources();
		if (projectResources.isEmpty())
		{
			return;
		}

		final ResourceAllocationConflicts actualConflicts;
		final Collection<WOProjectSimulationPlan> simulationsToCheck;
		if (onlySimulation == null)
		{
			final ResourceAllocations resourceAllocations = toResourceAllocations(projectResources.values(), null);
			actualConflicts = resourceAllocations.findActualConflicts();
			saveAndNotify(actualConflicts, projectResources.keySet());

			simulationsToCheck = woProjectSimulationRepository.getByResourceIdsAndSimulationIds(projectResources.keySet(), getDraftSimulationIds());
		}
		else
		{
			actualConflicts = conflictRepository.getActualConflicts(projectResources.keySet());
			simulationsToCheck = ImmutableList.of(onlySimulation);
		}

		//
		for (final WOProjectSimulationPlan simulationToCheck : simulationsToCheck)
		{
			final ResourceAllocations resourceAllocations = toResourceAllocations(projectResources.values(), simulationToCheck);
			final ResourceAllocationConflicts simulationOnlyConflicts = resourceAllocations.findSimulationOnlyConflicts(actualConflicts);
			saveAndNotify(simulationOnlyConflicts, projectResources.keySet());
		}
	}

	private Set<SimulationPlanId> getDraftSimulationIds()
	{
		ImmutableSet<SimulationPlanId> draftSimulationIds = simulationPlanRepository.getDraftSimulationIds();
		if (excludeSimulationId != null)
		{
			draftSimulationIds = CollectionUtils.removeElement(draftSimulationIds, excludeSimulationId);
		}
		return draftSimulationIds;
	}

	private ImmutableMap<WOProjectResourceId, WOProjectResource> getProjectResources()
	{
		if (resourceIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final Set<ProjectId> activeProjectIds = woProjectRepository.getAllActiveProjectIds();
		if (activeProjectIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		return woProjectResourceRepository
				.streamByResourceIds(resourceIds, activeProjectIds)
				.collect(GuavaCollectors.toImmutableMapByKey(WOProjectResource::getId));
	}

	private void saveAndNotify(
			@NonNull final ResourceAllocationConflicts conflicts,
			@NonNull final ImmutableSet<WOProjectResourceId> projectResourceIds)
	{
		conflictRepository.save(conflicts, projectResourceIds);
		eventsDispatcher.notifyChangesAfterCommit(conflicts.getSimulationId(), () -> toEvent(conflicts, projectResourceIds));
	}

	private static ResourceAllocations toResourceAllocations(
			@NonNull final Collection<WOProjectResource> projectResources,
			@Nullable final WOProjectSimulationPlan simulation)
	{
		final ImmutableList.Builder<ResourceAllocation> result = ImmutableList.builder();

		for (final WOProjectResource projectResource : projectResources)
		{
			final WOProjectResourceSimulation projectResourceSimulation = simulation != null
					? simulation.getProjectResourceByIdOrNull(projectResource.getId())
					: null;

			final ResourceAllocation resourceAllocation;
			if (projectResourceSimulation != null)
			{
				resourceAllocation = toResourceAllocation(projectResource.getResourceId(), projectResourceSimulation.applyOn(projectResource), simulation.getSimulationPlanId());
			}
			else
			{
				resourceAllocation = toResourceAllocation(projectResource.getResourceId(), projectResource, null);
			}

			result.add(resourceAllocation);
		}

		return ResourceAllocations.of(
				simulation != null ? simulation.getSimulationPlanId() : null,
				result.build());
	}

	private static ResourceAllocation toResourceAllocation(
			@NonNull ResourceId resourceId,
			@NonNull final WOProjectResource projectResource,
			@Nullable final SimulationPlanId appliedSimulationId)
	{
		return ResourceAllocation.builder()
				.resourceId(resourceId)
				.projectResourceId(projectResource.getId())
				.appliedSimulationId(appliedSimulationId)
				.dateRange(projectResource.getDateRange())
				.build();
	}

}
