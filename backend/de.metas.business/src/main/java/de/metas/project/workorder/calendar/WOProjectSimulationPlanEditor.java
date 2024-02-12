package de.metas.project.workorder.calendar;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.AdMessageKey;
import de.metas.project.ProjectId;
import de.metas.project.workorder.project.WOProject;
import de.metas.project.workorder.resource.ResourceIdAndType;
import de.metas.project.workorder.resource.WOProjectResource;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.resource.WOProjectResourceSimulation;
import de.metas.project.workorder.resource.WOProjectResources;
import de.metas.project.workorder.resource.WOProjectResourcesCollection;
import de.metas.project.workorder.step.WOProjectStep;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.project.workorder.step.WOProjectStepSimulation;
import de.metas.project.workorder.step.WOProjectSteps;
import de.metas.project.workorder.step.WOProjectStepsCollection;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.OldAndNewValues;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static de.metas.project.ProjectConstants.DEFAULT_DURATION;

public class WOProjectSimulationPlanEditor
{
	private static final AdMessageKey ERROR_MSG_STEP_CANNOT_BE_SHIFTED = AdMessageKey.of("de.metas.project.workorder.calendar.WoStepCannotBeShifted");
	private static final AdMessageKey ERROR_MSG_STEP_NO_AVAILABLE_DURATION = AdMessageKey.of("de.metas.project.workorder.calendar.NoAvailableDuration");

	@NonNull
	private final ImmutableMap<ProjectId, WOProject> _originalProjects;
	@NonNull
	private final WOProjectStepsCollection _originalStepsCollection;
	@NonNull
	private final WOProjectResourcesCollection _originalProjectResourcesCollection;

	@Getter
	private final SimulationPlanId simulationPlanId;
	private final WOProjectSimulationPlan initialSimulationPlan;
	private final HashMap<WOProjectStepId, WOProjectStepSimulation> simulationStepsById;
	private final HashSet<WOProjectResourceId> changedProjectResourceIds = new HashSet<>();
	private final HashMap<WOProjectResourceId, WOProjectResourceSimulation> simulationProjectResourcesById;

	@Builder
	private WOProjectSimulationPlanEditor(
			@NonNull @Singular final Collection<WOProject> projects,
			@NonNull @Singular("steps") final Collection<WOProjectSteps> stepsCollection,
			@NonNull @Singular("projectResources") final Collection<WOProjectResources> projectResourcesCollection,
			@NonNull final WOProjectSimulationPlan currentSimulationPlan)
	{
		this._originalProjects = Maps.uniqueIndex(projects, WOProject::getProjectId);
		this._originalStepsCollection = WOProjectStepsCollection.ofCollection(stepsCollection);
		this._originalProjectResourcesCollection = WOProjectResourcesCollection.ofCollection(projectResourcesCollection);

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
		changeResourceDateRange(projectResourceId, newDateRange, stepId);

		shiftLeftAllStepsBefore(stepId);
		shiftRightAllStepsAfter(stepId);
	}

	public void changeResourceDateRange(
			@NonNull final WOProjectResourceId projectResourceId,
			@NonNull final CalendarDateRange newDateRange,
			@NonNull final WOProjectStepId stepId)
	{
		changeResource(projectResourceId, newDateRange);
		updateStepDateRangeFromResources(stepId);
	}

	private void updateStepDateRangeFromResources(@NonNull final WOProjectStepId stepId)
	{
		final ImmutableList<WOProjectResource> projectResources = getProjectResourcesByStepId(stepId);
		final CalendarDateRange newDateRange = WOProjectResource.computeDateRangeToEncloseAll(projectResources);
		changeStep(stepId, newDateRange);
	}

	public WOProject getProjectById(final ProjectId projectId)
	{
		final WOProject project = _originalProjects.get(projectId);
		if (project == null)
		{
			throw new AdempiereException("Project " + projectId + " is not in scope");
		}
		return project;
	}

	public WOProjectStep getStepById(final @NonNull WOProjectStepId stepId)
	{
		return adjustBySimulation(this._originalStepsCollection.getById(stepId));
	}

	private List<WOProjectStep> getStepsBeforeFromLastToFirst(final @NonNull WOProjectStepId stepId)
	{
		return this._originalStepsCollection.getByProjectId(stepId.getProjectId())
				.getStepsBeforeFromLastToFirst(stepId)
				.stream()
				.map(this::adjustBySimulation)
				.collect(ImmutableList.toImmutableList());
	}

	private List<WOProjectStep> getStepsAfterInOrder(final @NonNull WOProjectStepId stepId)
	{
		return this._originalStepsCollection.getByProjectId(stepId.getProjectId())
				.getStepsAfterInOrder(stepId)
				.stream()
				.map(this::adjustBySimulation)
				.collect(ImmutableList.toImmutableList());
	}

	private ImmutableList<WOProjectResource> getProjectResourcesByStepId(final @NonNull WOProjectStepId stepId)
	{
		return this._originalProjectResourcesCollection.streamByStepId(stepId)
				.map(this::adjustBySimulation)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public OldAndNewValues<WOProjectResource> getProjectResourceInitialAndNow(final @NonNull WOProjectResourceId projectResourceId)
	{
		final WOProjectResource original = this._originalProjectResourcesCollection.getById(projectResourceId);
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
								.projectId(stepId.getProjectId())
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
			CalendarDateRange currentDateRange = step.getDateRange();
			if (currentDateRange == null)
			{
				continue;
			}

			if (currentDateRange.getEndDate().isAfter(prevDateRange.getStartDate()))
			{
				validateStepCanBeShifted(step);

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
			CalendarDateRange currentDateRange = step.getDateRange();
			if (currentDateRange == null)
			{
				continue;
			}

			if (currentDateRange.getStartDate().isBefore(prevDateRange.getEndDate()))
			{
				validateStepCanBeShifted(step);

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

	public ImmutableSet<ResourceIdAndType> getAffectedResourceIds()
	{
		return changedProjectResourceIds.stream()
				.map(_originalProjectResourcesCollection::getById)
				.map(WOProjectResource::getResourceIdAndType)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public CalendarDateRange suggestDateRange(@NonNull final WOProjectStepId stepId)
	{
		final WOProjectStep step = getStepById(stepId);

		if (step.getDateRange() != null)
		{
			return step.getDateRange();
		}

		final Instant lastBeforeStepDate = getStepsBeforeFromLastToFirst(stepId)
				.stream()
				.filter(woProjectStep -> woProjectStep.getDateRange() != null)
				.findFirst()
				.map(WOProjectStep::getDateRange)
				.map(CalendarDateRange::getEndDate)
				.orElse(null);

		final Instant firstAfterStepDate = getStepsAfterInOrder(stepId)
				.stream()
				.filter(woProjectStep -> woProjectStep.getDateRange() != null)
				.findFirst()
				.map(WOProjectStep::getDateRange)
				.map(CalendarDateRange::getStartDate)
				.orElse(null);

		final Duration stepDuration = computeStepDuration(stepId);

		if (lastBeforeStepDate == null && firstAfterStepDate == null)
		{
			return getDefaultCalendarDateRange(stepId.getProjectId(), stepDuration);
		}

		final CalendarDateRange proposedDateRange = Optional.ofNullable(lastBeforeStepDate)
				.map(startDate -> CalendarDateRange.ofStartDate(startDate, stepDuration))
				.orElseGet(() -> CalendarDateRange.ofEndDate(firstAfterStepDate, stepDuration));

		if (firstAfterStepDate != null && proposedDateRange.getEndDate().isAfter(firstAfterStepDate))
		{
			Check.assumeNotNull(lastBeforeStepDate, "There will never be a collision if there is no before date constraint!");

			final Duration availableDuration = Duration.between(lastBeforeStepDate, firstAfterStepDate);
			throw new AdempiereException(ERROR_MSG_STEP_NO_AVAILABLE_DURATION, step.getSeqNo(), stepDuration, availableDuration)
					.markAsUserValidationError();
		}

		return proposedDateRange;
	}

	@NonNull
	private CalendarDateRange getDefaultCalendarDateRange(@NonNull ProjectId projectId, @NonNull final Duration stepDuration)
	{
		final Instant defaultStartDate = Optional.ofNullable(getProjectById(projectId).getDateContract())
				.orElse(SystemTime.asInstant());

		return CalendarDateRange.builder()
				.startDate(defaultStartDate)
				.endDate(defaultStartDate.plus(stepDuration))
				.build();
	}

	@NonNull
	private Duration computeStepDuration(@NonNull final WOProjectStepId stepId)
	{
		final Duration resourcesDuration = getProjectResourcesByStepId(stepId)
				.stream()
				.map(WOProjectResource::getDuration)
				.reduce(Duration.ZERO, Duration::plus);

		if (resourcesDuration.isZero())
		{
			return DEFAULT_DURATION;
		}

		return resourcesDuration;
	}

	private static void validateStepCanBeShifted(@NonNull final WOProjectStep step)
	{
		if (!step.isManuallyLocked())
		{
			return;
		}

		throw new AdempiereException(ERROR_MSG_STEP_CANNOT_BE_SHIFTED, step.getName())
				.markAsUserValidationError();
	}
}
