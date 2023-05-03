package de.metas.calendar.plan_optimizer.domain;

import de.metas.calendar.plan_optimizer.solver.DelayStrengthComparator;
import de.metas.i18n.BooleanWithReason;
import de.metas.product.ResourceId;
import de.metas.project.InternalPriority;
import de.metas.project.ProjectId;
import de.metas.util.time.DurationUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.entity.PlanningPin;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.valuerange.CountableValueRange;
import org.optaplanner.core.api.domain.valuerange.ValueRangeFactory;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

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

	private Duration duration;
	private LocalDateTime startDateMin;
	private LocalDateTime dueDate;

	/**
	 * Delay it's the offset from previous step end.
	 * The delay is measured in {@link Plan#PLANNING_TIME_PRECISION}
	 */
	public static final String FIELD_delay = "delay";
	@PlanningVariable(strengthComparatorClass = DelayStrengthComparator.class)
	private Integer delay;

	@PlanningPin
	boolean pinned;

	// No-arg constructor required for OptaPlanner
	public Step() {}

	@Builder
	private Step(
			@NonNull final StepId id,
			@Nullable Step previousStep,
			@Nullable Step nextStep,
			@NonNull final InternalPriority projectPriority,
			@NonNull final Resource resource,
			@NonNull final Duration duration,
			@NonNull final LocalDateTime startDateMin,
			@NonNull final LocalDateTime dueDate,
			final int delay,
			final boolean pinned)
	{
		this.id = id;
		this.previousStep = previousStep;
		this.nextStep = nextStep;
		this.projectPriority = projectPriority;
		this.resource = resource;
		this.duration = duration;
		this.dueDate = dueDate;
		this.startDateMin = startDateMin;
		this.delay = delay;
		this.pinned = pinned;
	}

	@Override
	public String toString()
	{
		// NOTE: keep it concise, important for optaplanner troubleshooting
		return getStartDate() + " -> " + getEndDate()
				+ " (" + duration + ")"
				+ ": dueDate=" + dueDate
				+ ", startDateMin=" + startDateMin
				+ ", delay=" + getDelayAsDuration() + "(max. " + computeDelayMax() + ")"
				+ ", " + resource
				+ ", " + getProjectId()
				+ ", ID=" + (id != null ? id.getWoProjectResourceId().getRepoId() : "?");
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

	public LocalDateTime getStartDate()
	{
		final Duration delayDuration = getDelayAsDuration();
		if (previousStep == null)
		{
			return startDateMin.plus(delayDuration);
		}
		else
		{
			return previousStep.getEndDate().plus(delayDuration);
		}
	}

	public LocalDateTime getEndDate()
	{
		return getStartDate().plus(duration);
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
