package de.metas.project.workorder.calendar;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.i18n.AdMessageKey;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.workorder.project.WOProject;
import de.metas.project.workorder.resource.WOProjectResource;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.resource.WOProjectResourceSimulation;
import de.metas.project.workorder.resource.WOProjectResources;
import de.metas.project.workorder.step.WOProjectStep;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.project.workorder.step.WOProjectStepSimulation;
import de.metas.project.workorder.step.WOProjectSteps;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.OldAndNewValues;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class WOProjectSimulationPlanEditor
{
	private static final AdMessageKey ERROR_MSG_STEP_CANNOT_BE_SHIFTED = AdMessageKey.of("de.metas.project.workorder.calendar.WoStepCannotBeShifted");

	@NonNull private final WOProject _originalProject;
	@NonNull private final WOProjectSteps _originalSteps;
	@NonNull private final WOProjectResources _originalProjectResources;

	@Getter
	private final SimulationPlanId simulationPlanId;
	private final WOProjectSimulationPlan initialSimulationPlan;
	private final HashMap<WOProjectStepId, WOProjectStepSimulation> simulationStepsById;
	private final HashSet<WOProjectResourceId> changedProjectResourceIds = new HashSet<>();
	private final HashMap<WOProjectResourceId, WOProjectResourceSimulation> simulationProjectResourcesById;

	@Builder
	private WOProjectSimulationPlanEditor(
			@NonNull final WOProject project,
			@NonNull final WOProjectSteps steps,
			@NonNull final WOProjectResources projectResources,
			@NonNull final WOProjectSimulationPlan currentSimulationPlan)
	{
		this._originalProject = project;
		this._originalSteps = steps;
		this._originalProjectResources = projectResources;

		this.simulationPlanId = currentSimulationPlan.getSimulationPlanId();
		this.initialSimulationPlan = currentSimulationPlan;
		this.simulationStepsById = new HashMap<>(currentSimulationPlan.getStepsById());
		this.simulationProjectResourcesById = new HashMap<>(currentSimulationPlan.getProjectResourcesById());
	}

	public WOProjectSimulationPlan toNewSimulationPlan()
	{
		return WOProjectSimulationPlan.builder()
				.simulationPlanId(simulationPlanId)
				.stepsById(simulationStepsById)
				.projectResourcesById(simulationProjectResourcesById)
				.build();
	}

	public void changeResourceDateRangeAndShiftSteps(
			@NonNull final WOProjectResourceId projectResourceId,
			@NonNull final CalendarDateRange newDateRange,
			@NonNull final WOProjectStepId stepId)
	{
		changeResource(projectResourceId, newDateRange);
		updateStepDateRangeFromResources(stepId);

		shiftLeftAllStepsBefore(stepId);
		shiftRightAllStepsAfter(stepId);
	}

	private void updateStepDateRangeFromResources(@NonNull final WOProjectStepId stepId)
	{
		final ImmutableList<WOProjectResource> projectResources = getProjectResourcesByStepId(stepId);
		final CalendarDateRange newDateRange = WOProjectResource.computeDateRangeToEncloseAll(projectResources);
		changeStep(stepId, newDateRange);
	}

	public WOProject getProject()
	{
		return this._originalProject;
	}

	private ProjectId getProjectId()
	{
		return getProject().getProjectId();
	}

	public WOProject getProjectById(final ProjectId projectId)
	{
		WOProject project = getProject();
		if (ProjectId.equals(project.getProjectId(), projectId))
		{
			return project;
		}
		else
		{
			throw new AdempiereException("Project " + projectId + " is not in scope");
		}
	}

	public WOProjectStep getStepById(final @NonNull WOProjectStepId stepId)
	{
		return adjustBySimulation(this._originalSteps.getById(stepId));
	}

	private List<WOProjectStep> getStepsBeforeFromLastToFirst(final @NonNull WOProjectStepId stepId)
	{
		return this._originalSteps.getStepsBeforeFromLastToFirst(stepId)
				.stream()
				.map(this::adjustBySimulation)
				.collect(ImmutableList.toImmutableList());
	}

	private List<WOProjectStep> getStepsAfterInOrder(final @NonNull WOProjectStepId stepId)
	{
		return this._originalSteps.getStepsAfterInOrder(stepId)
				.stream()
				.map(this::adjustBySimulation)
				.collect(ImmutableList.toImmutableList());
	}

	private ImmutableList<WOProjectResource> getProjectResourcesByStepId(final @NonNull WOProjectStepId stepId)
	{
		return this._originalProjectResources.streamByStepId(stepId)
				.map(this::adjustBySimulation)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public OldAndNewValues<WOProjectResource> getProjectResourceInitialAndNow(final @NonNull WOProjectResourceId projectResourceId)
	{
		final WOProjectResource original = this._originalProjectResources.getById(projectResourceId);
		final WOProjectResource initial = this.initialSimulationPlan.applyOn(original);
		final WOProjectResource now = adjustBySimulation(original);

		return OldAndNewValues.ofOldAndNewValues(initial, now);
	}

	@NonNull
	public <T> OldAndNewValues<T> mapProjectResourceInitialAndNow(
			final @NonNull WOProjectResourceId projectResourceId,
			final @NonNull Function<WOProjectResource, T> mapper)
	{
		return getProjectResourceInitialAndNow(projectResourceId).mapNonNulls(mapper);
	}

	public Stream<WOProjectResourceId> streamChangedProjectResourceIds()
	{
		return changedProjectResourceIds.stream();
	}

	private WOProjectStep adjustBySimulation(@NonNull final WOProjectStep step)
	{
		final WOProjectStepSimulation simulation = simulationStepsById.get(step.getWoProjectStepId());
		return simulation != null
				? simulation.applyOn(step)
				: step;
	}

	private WOProjectResource adjustBySimulation(@NonNull final WOProjectResource projectResource)
	{
		final WOProjectResourceSimulation simulation = simulationProjectResourcesById.get(projectResource.getWoProjectResourceId());
		return simulation != null
				? simulation.applyOn(projectResource)
				: projectResource;
	}

	private void changeStep(
			@NonNull final WOProjectStepId stepId,
			@NonNull final CalendarDateRange newDateRange)
	{
		simulationStepsById.compute(
				stepId,
				(k, existingSimulation) -> {
					final WOProjectStepSimulation.WOProjectStepSimulationBuilder builder;
					if (existingSimulation == null)
					{
						builder = WOProjectStepSimulation.builder()
								.projectId(getProjectId())
								.stepId(stepId);
					}
					else
					{
						builder = existingSimulation.toBuilder();
					}

					return builder.dateRange(newDateRange).build();
				});
	}

	private void changeResource(
			@NonNull final WOProjectResourceId projectResourceId,
			@NonNull final CalendarDateRange newDateRange)
	{
		simulationProjectResourcesById.compute(
				projectResourceId,
				(k, existingSimulation) -> {
					final WOProjectResourceSimulation.WOProjectResourceSimulationBuilder builder;
					if (existingSimulation == null)
					{
						builder = WOProjectResourceSimulation.builder()
								.projectResourceId(projectResourceId);
					}
					else
					{
						builder = existingSimulation.toBuilder();
					}

					return builder.dateRange(newDateRange).build();
				});

		changedProjectResourceIds.add(projectResourceId);
	}

	private void shiftLeftAllStepsBefore(@NonNull final WOProjectStepId stepId)
	{
		CalendarDateRange prevDateRange = getStepById(stepId).getDateRange();
		Check.assumeNotNull(prevDateRange, "DateRange cannot be null as simulation is taken into account!");

		for (final WOProjectStep step : getStepsBeforeFromLastToFirst(stepId))
		{
			validateStepCanBeShifted(step);

			CalendarDateRange currentDateRange = step.getDateRange();
			if (currentDateRange == null)
			{
				continue;
			}

			if (currentDateRange.getEndDate().isAfter(prevDateRange.getStartDate()))
			{
				final Duration offset = Duration.between(prevDateRange.getStartDate(), currentDateRange.getEndDate()).negated();
				currentDateRange = currentDateRange.plus(offset);
				changeStep(step.getWoProjectStepId(), currentDateRange);

				shiftStepResources(step.getWoProjectStepId(), offset);
			}

			prevDateRange = currentDateRange;
		}
	}

	private void shiftRightAllStepsAfter(@NonNull final WOProjectStepId stepId)
	{
		CalendarDateRange prevDateRange = getStepById(stepId).getDateRange();
		Check.assumeNotNull(prevDateRange, "DateRange cannot be null as simulation is taken into account!");

		for (final WOProjectStep step : getStepsAfterInOrder(stepId))
		{
			validateStepCanBeShifted(step);

			CalendarDateRange currentDateRange = step.getDateRange();
			if (currentDateRange == null)
			{
				continue;
			}

			if (currentDateRange.getStartDate().isBefore(prevDateRange.getEndDate()))
			{
				final Duration offset = Duration.between(currentDateRange.getStartDate(), prevDateRange.getEndDate());
				currentDateRange = currentDateRange.plus(offset);
				changeStep(step.getWoProjectStepId(), currentDateRange);

				shiftStepResources(step.getWoProjectStepId(), offset);
			}

			prevDateRange = currentDateRange;
		}
	}

	private void shiftStepResources(@NonNull final WOProjectStepId stepId, @NonNull final Duration offset)
	{
		if (offset.isZero())
		{
			return;
		}

		for (final WOProjectResource projectResource : getProjectResourcesByStepId(stepId))
		{
			final CalendarDateRange dateRange = projectResource.getDateRange();
			if (dateRange == null)
			{
				continue;
			}

			final CalendarDateRange newDateRange = dateRange.plus(offset);
			changeResource(projectResource.getWoProjectResourceId(), newDateRange);
		}
	}

	public ImmutableSet<ResourceId> getAffectedResourceIds()
	{
		return changedProjectResourceIds.stream()
				.map(_originalProjectResources::getById)
				.map(WOProjectResource::getResourceId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private void validateStepCanBeShifted(@NonNull final WOProjectStep step)
	{
		if (!step.isManuallyLocked())
		{
			return;
		}

		throw new AdempiereException(ERROR_MSG_STEP_CANNOT_BE_SHIFTED, step.getName())
				.markAsUserValidationError();
	}
}
