package de.metas.project.workorder.calendar;

import com.google.common.collect.ImmutableMap;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.project.workorder.WOProjectResource;
import de.metas.project.workorder.WOProjectResourceId;
import de.metas.project.workorder.WOProjectResourceSimulation;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.project.workorder.WOProjectStepSimulation;
import de.metas.util.collections.CollectionUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.Map;

@ToString
final class WOProjectSimulationPlan
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
			@Nullable Map<WOProjectStepId, WOProjectStepSimulation> stepsById,
			@Nullable final Map<WOProjectResourceId, WOProjectResourceSimulation> projectResourcesById)
	{
		this.simulationPlanId = simulationPlanId;
		this.stepsById = stepsById != null ? ImmutableMap.copyOf(stepsById) : ImmutableMap.of();
		this.projectResourcesById = projectResourcesById != null ? ImmutableMap.copyOf(projectResourcesById) : ImmutableMap.of();
	}

	@Nullable
	public WOProjectResourceSimulation getProjectResourceByIdOrNull(@NonNull final WOProjectResourceId woProjectResourceId)
	{
		return projectResourcesById.get(woProjectResourceId);
	}

	public WOProjectResource applyOn(@NonNull final WOProjectResource woProjectResource)
	{
		final WOProjectResourceSimulation simulation = getProjectResourceByIdOrNull(woProjectResource.getId());
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
}
