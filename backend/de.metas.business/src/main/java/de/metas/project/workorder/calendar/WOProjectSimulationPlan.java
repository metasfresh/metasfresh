package de.metas.project.workorder.calendar;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.project.workorder.resource.WOProjectResource;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.resource.WOProjectResourceSimulation;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.project.workorder.step.WOProjectStepSimulation;
import de.metas.util.collections.CollectionUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@ToString
public final class WOProjectSimulationPlan
{
	@Getter
	@NonNull
	private final SimulationPlanId simulationPlanId;

	@Getter(AccessLevel.PACKAGE)
	@NonNull
	private final ImmutableMap<WOProjectStepId, WOProjectStepSimulation> stepsById;

	@Getter(AccessLevel.PACKAGE)
	@NonNull
	private final ImmutableMap<WOProjectResourceId, WOProjectResourceSimulation> projectResourcesById;

	@Builder(toBuilder = true)
	private WOProjectSimulationPlan(
			@NonNull final SimulationPlanId simulationPlanId,
			@Nullable final Map<WOProjectStepId, WOProjectStepSimulation> stepsById,
			@Nullable final Map<WOProjectResourceId, WOProjectResourceSimulation> projectResourcesById)
	{
		this.simulationPlanId = simulationPlanId;
		this.stepsById = stepsById != null ? ImmutableMap.copyOf(stepsById) : ImmutableMap.of();
		this.projectResourcesById = projectResourcesById != null ? ImmutableMap.copyOf(projectResourcesById) : ImmutableMap.of();
	}

	public ImmutableSet<WOProjectResourceId> getProjectResourceIds() {return projectResourcesById.keySet();}

	public Collection<WOProjectStepSimulation> getSteps()
	{
		return stepsById.values();
	}

	public Collection<WOProjectResourceSimulation> getProjectResources()
	{
		return projectResourcesById.values();
	}

	@Nullable
	public WOProjectResourceSimulation getProjectResourceByIdOrNull(@NonNull final WOProjectResourceId woProjectResourceId)
	{
		return projectResourcesById.get(woProjectResourceId);
	}

	@Nullable
	public WOProjectStepSimulation getProjectStepByIdOrNull(@NonNull final WOProjectStepId woProjectStepId)
	{
		return stepsById.get(woProjectStepId);
	}

	public WOProjectResource applyOn(@NonNull final WOProjectResource woProjectResource)
	{
		final WOProjectResourceSimulation simulation = getProjectResourceByIdOrNull(woProjectResource.getWoProjectResourceId());
		return simulation != null
				? simulation.applyOn(woProjectResource)
				: woProjectResource;
	}

	public WOProjectSimulationPlan mergeFrom(@NonNull final WOProjectSimulationPlan from)
	{
		return toBuilder()
				.stepsById(CollectionUtils.mergeMaps(this.stepsById, from.stepsById))
				.projectResourcesById(CollectionUtils.mergeMaps(this.projectResourcesById, from.projectResourcesById))
				.build();
	}

	@NonNull
	public WOProjectSimulationPlan removeResourceSimulation(@NonNull final Set<WOProjectResourceId> woProjectResourceIds)
	{
		final ImmutableSet<WOProjectResourceId> filteredResourcesIds = CollectionUtils.difference(this.projectResourcesById.keySet(), woProjectResourceIds);

		final Map<WOProjectResourceId, WOProjectResourceSimulation> filteredResourcesById = filteredResourcesIds.stream()
				.map(resourceId -> getProjectResourcesById().get(resourceId))
				.filter(Objects::nonNull)
				.collect(Collectors.toMap(WOProjectResourceSimulation::getProjectResourceId, Function.identity()));

		return toBuilder()
				.projectResourcesById(filteredResourcesById)
				.build();
	}
}
