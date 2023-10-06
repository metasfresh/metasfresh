package de.metas.calendar.plan_optimizer.domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.entity.PlanningPin;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.valuerange.CountableValueRange;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeFactory;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import ai.timefold.solver.core.api.domain.variable.ShadowVariable;
import ai.timefold.solver.core.api.score.director.ScoreDirector;
import de.metas.calendar.plan_optimizer.solver.DelayStrengthComparator;
import de.metas.calendar.plan_optimizer.solver.weekly_capacities.YearWeek;
import de.metas.i18n.BooleanWithReason;
import de.metas.project.InternalPriority;
import de.metas.project.ProjectId;
import de.metas.util.time.DurationUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.LocalDateTime;

@PlanningEntity
@Setter
@Getter
//@EqualsAndHashCode(doNotUseGetters = true) // IMPORTANT: do not use it beucase we have next/prev Step refs
public class Step
{
	@PlanningId private StepId id;

	private Step previousStep;
	private Step nextStep;

	InternalPriority projectPriority;

	private Resource resource;
	private Duration humanResourceTestGroupDuration;

	private Duration duration;
	private LocalDateTime startDateMin;
	private LocalDateTime dueDate;

	@PlanningPin boolean pinned;

	/**
	 * Delay it's the offset from previous step end. The delay is measured in {@link Plan#PLANNING_TIME_PRECISION}.
	 */
	public static final String FIELD_delay = "delay";
	@PlanningVariable(strengthComparatorClass = DelayStrengthComparator.class, nullable = true)
	private Integer delay;

	//
	// Shadow variables
	private static final String FIELD_PreviousStepEndDate = "previousStepEndDate";
	@Setter(AccessLevel.NONE) private LocalDateTime previousStepEndDate;

	//
	// Computed from shadow variables
	private LocalDateTime startDate;
	private LocalDateTime endDate;

	@Builder(toBuilder = true)
	private Step(
			@NonNull final StepId id,
			@Nullable final Step previousStep,
			@Nullable final Step nextStep,
			@NonNull final InternalPriority projectPriority,
			@NonNull final Resource resource,
			@NonNull final Duration humanResourceTestGroupDuration,
			@NonNull final Duration duration,
			@NonNull final LocalDateTime startDateMin,
			@NonNull final LocalDateTime dueDate,
			@Nullable final Integer delay,
			final boolean pinned,
			@Nullable final LocalDateTime previousStepEndDate,
			@Nullable final LocalDateTime startDate,
			@Nullable final LocalDateTime endDate)
	{
		this.id = id;
		this.previousStep = previousStep;
		this.nextStep = nextStep;
		this.projectPriority = projectPriority;
		this.resource = resource;
		this.humanResourceTestGroupDuration = humanResourceTestGroupDuration;
		this.duration = duration;
		this.dueDate = dueDate;
		this.startDateMin = startDateMin;
		this.delay = delay;
		this.pinned = pinned;

		this.previousStepEndDate = previousStepEndDate;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	@Override
	public String toString()
	{
		// NOTE: keep it concise, important for Timefold troubleshooting
		final StringBuilder sb = new StringBuilder();

		final LocalDateTime startDate = getStartDate();
		sb.append(startDate).append(" -> ");

		final LocalDateTime endDate = getEndDate();
		if (startDate != null && startDate.toLocalDate().equals(endDate.toLocalDate()))
		{
			sb.append(endDate.toLocalTime());
		}
		else
		{
			sb.append(endDate);
		}

		if (startDate != null)
		{
			sb.append(" (WK").append(YearWeek.from(startDate).getWeek()).append(")");
		}
		//sb.append(" (").append(duration).append(")");

		sb.append(": ");

		// + ": dueDate=" + dueDate
		// + ", startDateMin=" + startDateMin
		// + ", delay=" + getDelayAsDuration() + "(max. " + computeDelayMax() + ")"

		sb.append(resource);
		sb.append(", P=").append(getProjectId().getRepoId());

		if (humanResourceTestGroupDuration != null && !humanResourceTestGroupDuration.isZero())
		{
			sb.append(", duration(HR)=").append(humanResourceTestGroupDuration);
		}

		if (id != null)
		{
			sb.append(", ID=").append(id.getWoProjectResourceId().getRepoId());
		}

		sb.append(", delay=").append(delay);

		return sb.toString();
	}

	public BooleanWithReason checkProblemFactsValid()
	{
		if (startDateMin == null)
		{
			return BooleanWithReason.falseBecause("StartDateMin not set");
		}
		if (dueDate == null)
		{
			return BooleanWithReason.falseBecause("DueDate not set");
		}
		if (!startDateMin.isBefore(dueDate))
		{
			return BooleanWithReason.falseBecause("StartDateMin shall be before DueDate");
		}
		if (duration == null || duration.getSeconds() <= 0)
		{
			return BooleanWithReason.falseBecause("Duration must be set and must be positive");
		}

		final Duration durationMax = Duration.between(startDateMin, dueDate);
		if (duration.compareTo(durationMax) > 0)
		{
			return BooleanWithReason.falseBecause("Duration does not fit into StartDateMin/DueDate interval");
		}

		return BooleanWithReason.TRUE;
	}

	@ValueRangeProvider
	public CountableValueRange<Integer> getDelayRange()
	{
		final int delayMax = computeDelayMax();
		return ValueRangeFactory.createIntValueRange(0, delayMax);
	}

	private int computeDelayMax()
	{
		return DurationUtils.toInt(Duration.between(startDateMin, dueDate), Plan.PLANNING_TIME_PRECISION)
				- DurationUtils.toInt(duration, Plan.PLANNING_TIME_PRECISION);
	}

	public ProjectId getProjectId() {return getId().getProjectId();}

	private int getDelayAsInt() {return delay != null ? delay : 0;}

	private Duration getDelayAsDuration() {return Duration.of(getDelayAsInt(), Plan.PLANNING_TIME_PRECISION);}

	@ShadowVariable(variableListenerClass = StepPreviousEndDateUpdater.class, sourceVariableName = FIELD_delay)
	public LocalDateTime getPreviousStepEndDate()
	{
		return previousStepEndDate;
	}

	public void setPreviousStepEndDateAndUpdate(@Nullable final LocalDateTime previousStepEndDate, @Nullable final ScoreDirector<Plan> scoreDirector)
	{
		if (scoreDirector != null)
		{
			scoreDirector.beforeVariableChanged(this, FIELD_PreviousStepEndDate);
		}

		this.previousStepEndDate = previousStepEndDate;
		if (previousStepEndDate == null)
		{
			this.startDate = null;
			this.endDate = null;
		}
		else
		{
			final Duration delayDuration = getDelayAsDuration();
			this.startDate = previousStepEndDate.plus(delayDuration);
			this.endDate = this.startDate.plus(duration);
		}

		if (scoreDirector != null)
		{
			scoreDirector.afterVariableChanged(this, FIELD_PreviousStepEndDate);
		}
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isStartDateMinRespected() {return getDurationBeforeStartDateMin().isZero();}

	public Duration getDurationBeforeStartDateMin()
	{
		final LocalDateTime startDate = getStartDate();
		return startDate.isBefore(startDateMin) ? Duration.between(startDate, startDateMin) : Duration.ZERO;
	}

	public int getDurationBeforeStartDateMinAsInt()
	{
		return DurationUtils.toInt(getDurationBeforeStartDateMin(), Plan.PLANNING_TIME_PRECISION);
	}

	public Duration getDurationAfterDue()
	{
		final LocalDateTime endDate = getEndDate();
		return endDate.isAfter(dueDate) ? Duration.between(dueDate, endDate) : Duration.ZERO;
	}

	public int getDurationAfterDueAsInt()
	{
		return DurationUtils.toInt(getDurationAfterDue(), Plan.PLANNING_TIME_PRECISION);
	}

	public boolean isDueDateNotRespected() {return !isDueDateRespected();}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isDueDateRespected() {return getDurationAfterDue().isZero();}

	private Duration getDurationFromEndToDueDate() {return Duration.between(getEndDate(), dueDate);}

	public int getDurationFromEndToDueDateInHoursAbs() {return Math.abs((int)getDurationFromEndToDueDate().toHours());}
}
