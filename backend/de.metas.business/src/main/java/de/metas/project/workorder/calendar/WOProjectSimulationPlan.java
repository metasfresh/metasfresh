package de.metas.project.workorder.calendar;

import com.google.common.collect.ImmutableMap;
import de.metas.calendar.simulation.CalendarSimulationId;
import de.metas.project.workorder.WOProjectResource;
import de.metas.project.workorder.WOProjectResourceId;
import de.metas.project.workorder.WOProjectResourceSimulation;
import de.metas.project.workorder.WOProjectStep;
import de.metas.project.workorder.WOProjectStepAndResourceId;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.project.workorder.WOProjectStepSimulation;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.function.UnaryOperator;

@ToString
final class WOProjectSimulationPlan
{
	@Getter
	@NonNull
	private final CalendarSimulationId simulationPlanId;

	@Getter(AccessLevel.PACKAGE)
	@NonNull
	private final ImmutableMap<WOProjectStepId, WOProjectStepSimulation> stepsById;

	@Getter(AccessLevel.PACKAGE)
	@NonNull
	private final ImmutableMap<WOProjectResourceId, WOProjectResourceSimulation> projectResourcesById;

	@Builder(toBuilder = true)
	private WOProjectSimulationPlan(
			@NonNull final CalendarSimulationId simulationPlanId,
			@Nullable Map<WOProjectStepId, WOProjectStepSimulation> stepsById,
			@Nullable final Map<WOProjectResourceId, WOProjectResourceSimulation> projectResourcesById)
	{
		this.simulationPlanId = simulationPlanId;
		this.stepsById = stepsById != null ? ImmutableMap.copyOf(stepsById) : ImmutableMap.of();
		this.projectResourcesById = projectResourcesById != null ? ImmutableMap.copyOf(projectResourcesById) : ImmutableMap.of();
	}

	WOProjectSimulationPlan mapByStepId(
			@NonNull WOProjectStepId stepId,
			@NonNull UnaryOperator<WOProjectStepSimulation> mapper)
	{
		@Nullable
		WOProjectStepSimulation simulation = getStepByIdOrNull(stepId);
		final WOProjectStepSimulation simulationChanged = mapper.apply(simulation);
		if (Objects.equals(simulation, simulationChanged))
		{
			// no changes
			return this;
		}
		else
		{
			Check.assumeNotNull(simulationChanged, "changed simulation cannot be null");
			Check.assumeEquals(simulationChanged.getSimulationId(), simulationPlanId, "simulationId");
			Check.assumeEquals(simulationChanged.getStepId(), stepId, "stepId");

			return toBuilder()
					.stepsById(CollectionUtils.mergeElementToMap(stepsById, simulationChanged, WOProjectStepSimulation::getStepId))
					.build();
		}
	}

	WOProjectSimulationPlan mapByProjectResourceId(
			@NonNull WOProjectStepAndResourceId woProjectStepAndResourceId,
			@NonNull UnaryOperator<WOProjectResourceSimulation> mapper)
	{
		@Nullable
		WOProjectResourceSimulation simulation = getProjectResourceByIdOrNull(woProjectStepAndResourceId.getProjectResourceId());
		final WOProjectResourceSimulation simulationChanged = mapper.apply(simulation);
		if (Objects.equals(simulation, simulationChanged))
		{
			// no changes
			return this;
		}
		else
		{
			Check.assumeNotNull(simulationChanged, "changed simulation cannot be null");
			Check.assumeEquals(simulationChanged.getSimulationId(), simulationPlanId, "simulationId");
			Check.assumeEquals(simulationChanged.getProjectStepAndResourceId(), woProjectStepAndResourceId, "projectStepAndResourceId");

			return toBuilder()
					.projectResourcesById(CollectionUtils.mergeElementToMap(projectResourcesById, simulationChanged, WOProjectResourceSimulation::getProjectResourceId))
					.build();
		}
	}

	@Nullable
	public WOProjectStepSimulation getStepByIdOrNull(@NonNull final WOProjectStepId stepId)
	{
		return stepsById.get(stepId);
	}

	@Nullable
	public WOProjectResourceSimulation getProjectResourceByIdOrNull(@NonNull final WOProjectResourceId woProjectResourceId)
	{
		return projectResourcesById.get(woProjectResourceId);
	}

	public WOProjectResourceSimulation getProjectResourceById(@NonNull final WOProjectResourceId woProjectResourceId)
	{
		return Check.assumeNotNull(
				getProjectResourceByIdOrNull(woProjectResourceId),
				"Project Resource shall exist for {} in {}", woProjectResourceId, this);
	}

	public WOProjectStep applyOn(@NonNull final WOProjectStep step)
	{
		final WOProjectStepSimulation simulation = getStepByIdOrNull(step.getId());
		return simulation != null
				? simulation.applyOn(step)
				: step;
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
