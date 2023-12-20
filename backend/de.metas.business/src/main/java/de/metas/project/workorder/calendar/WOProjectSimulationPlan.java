package de.metas.project.workorder.calendar;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.project.workorder.resource.WOProjectResource;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.resource.WOProjectResourceSimulation;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.project.workorder.step.WOProjectStepSimulation;
import de.metas.project.workorder.step.WOStepResources;
import de.metas.util.collections.CollectionUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

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

	public ImmutableSet<WOProjectResourceId> getProjectResourceIds()
	{
		return projectResourcesById.keySet();
	}

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
	public WOProjectSimulationPlan removeSimulationForStepAndResources(@NonNull final WOStepResources woStepResources)
	{
		final ImmutableMap<WOProjectStepId, WOProjectStepSimulation> filteredStepsById = stepsById.keySet()
				.stream()
				.filter(woProjectStepId -> !woStepResources.getWoProjectStepId().equals(woProjectStepId))
				.collect(ImmutableMap.toImmutableMap(Function.identity(), stepsById::get));

		final ImmutableMap<WOProjectResourceId, WOProjectResourceSimulation> filteredResourcesById = projectResourcesById.keySet()
				.stream()
				.filter(resourceId -> !woStepResources.hasResource(resourceId))
				.collect(ImmutableMap.toImmutableMap(Function.identity(), projectResourcesById::get));

		return toBuilder()
				.stepsById(filteredStepsById)
				.projectResourcesById(filteredResourcesById)
				.build();
	}
}
