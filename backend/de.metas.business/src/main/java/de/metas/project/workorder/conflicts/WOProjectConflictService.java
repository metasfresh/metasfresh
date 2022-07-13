package de.metas.project.workorder.conflicts;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.calendar.simulation.SimulationPlanId;
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
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;

@Service
public class WOProjectConflictService
{
	@NonNull private final WOProjectResourceConflictRepository conflictRepository;
	@NonNull private final WOProjectSimulationRepository woProjectSimulationRepository;

	@NonNull private final WOProjectRepository woProjectRepository;
	@NonNull private final WOProjectResourceRepository woProjectResourceRepository;

	public WOProjectConflictService(
			final @NonNull WOProjectResourceConflictRepository conflictRepository,
			final @NonNull WOProjectSimulationRepository woProjectSimulationRepository,
			final @NonNull WOProjectRepository woProjectRepository,
			final @NonNull WOProjectResourceRepository woProjectResourceRepository)
	{
		this.conflictRepository = conflictRepository;
		this.woProjectSimulationRepository = woProjectSimulationRepository;
		this.woProjectRepository = woProjectRepository;
		this.woProjectResourceRepository = woProjectResourceRepository;
	}

	public ResourceAllocationConflicts getActualAndSimulation(@Nullable final SimulationPlanId simulationId)
	{
		final Set<ProjectId> activeProjectIds = woProjectRepository.getAllActiveProjectIds();
		if (activeProjectIds.isEmpty())
		{
			return ResourceAllocationConflicts.empty(simulationId);
		}

		return conflictRepository.getActualAndSimulation(simulationId, activeProjectIds);
	}

	public void checkConflicts(@NonNull Set<ResourceId> resourceIds)
	{
		checkConflicts0(null, resourceIds);
	}

	public void checkSimulationConflicts(
			@NonNull final WOProjectSimulationPlan onlySimulation,
			@NonNull Set<ResourceId> resourceIds)
	{
		checkConflicts0(onlySimulation, resourceIds);
	}


	private void checkConflicts0(
			@Nullable final WOProjectSimulationPlan onlySimulation,
			@NonNull Set<ResourceId> resourceIds)
	{
		if (resourceIds.isEmpty())
		{
			return;
		}

		final Set<ProjectId> activeProjectIds = woProjectRepository.getAllActiveProjectIds();
		if (activeProjectIds.isEmpty())
		{
			return;
		}

		final ImmutableMap<WOProjectResourceId, WOProjectResource> projectResources = woProjectResourceRepository.streamByResourceIds(resourceIds, activeProjectIds)
				.collect(GuavaCollectors.toImmutableMapByKey(WOProjectResource::getId));
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
			conflictRepository.save(actualConflicts, projectResources.keySet());

			simulationsToCheck = woProjectSimulationRepository.getByResourceIds(projectResources.keySet());
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
			conflictRepository.save(simulationOnlyConflicts, projectResources.keySet());
		}
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
				.projectAndResourceId(projectResource.getWOProjectAndResourceId())
				.appliedSimulationId(appliedSimulationId)
				.dateRange(projectResource.getDateRange())
				.build();
	}
}
