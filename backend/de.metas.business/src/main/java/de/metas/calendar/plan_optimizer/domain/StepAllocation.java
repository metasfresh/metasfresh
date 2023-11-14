package de.metas.calendar.plan_optimizer.domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.valuerange.CountableValueRange;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeFactory;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import ai.timefold.solver.core.api.domain.variable.ShadowVariable;
import ai.timefold.solver.core.api.score.director.ScoreDirector;
import de.metas.calendar.plan_optimizer.solver.DelayStrengthComparator;
import de.metas.i18n.BooleanWithReason;
import de.metas.product.ResourceId;
import de.metas.project.InternalPriority;
import de.metas.project.ProjectId;
import de.metas.resource.ResourceAvailabilityRanges;
import de.metas.util.time.DurationUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.compiere.util.TimeUtil;
import org.threeten.extra.YearWeek;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@PlanningEntity
@Setter
@Getter
//@EqualsAndHashCode(doNotUseGetters = true) // IMPORTANT: do not use it because we have next/prev Step refs
public class StepAllocation
{
	@PlanningId @NonNull private final StepAllocationId id;

	@NonNull private final StepDef stepDef;

	@Nullable private StepAllocation previous;
	@Nullable private StepAllocation next;

	/**
	 * Delay it's the offset from previous step end. The delay is measured in {@link Plan#PLANNING_TIME_PRECISION}.
	 */
	public static final String FIELD_delay = "delay";
	@PlanningVariable(strengthComparatorClass = DelayStrengthComparator.class, nullable = true)
	@Nullable private Integer delay;

	//
	// Shadow variables
	private static final String FIELD_PreviousStepEndDate = "previousStepEndDate";
	@Nullable @Setter(AccessLevel.NONE) private LocalDateTime previousStepEndDate;

	//
	// Computed from shadow variables
	@Nullable private ResourceAvailabilityRanges resourceScheduledRange;
	@Nullable private ResourceAvailabilityRanges humanResourceScheduledRange;

	@Builder(toBuilder = true)
	private StepAllocation(
			@NonNull final StepAllocationId id,
			@NonNull final StepDef stepDef,
			@Nullable final StepAllocation previous,
			@Nullable final StepAllocation next,
			@Nullable final Integer delay,
			@Nullable final LocalDateTime previousStepEndDate,
			@Nullable final ResourceAvailabilityRanges resourceScheduledRange,
			@Nullable final ResourceAvailabilityRanges humanResourceScheduledRange)
	{
		this.id = id;
		this.stepDef = stepDef;
		this.previous = previous;
		this.next = next;
		this.delay = delay;

		this.previousStepEndDate = previousStepEndDate;
		this.resourceScheduledRange = resourceScheduledRange;
		this.humanResourceScheduledRange = humanResourceScheduledRange != null ? humanResourceScheduledRange : resourceScheduledRange;
	}

	@Override
	public String toString()
	{
		// NOTE: keep it concise, important for Timefold troubleshooting
		final StringBuilder sb = new StringBuilder();

		if (stepDef.getPinnedStartDate() != null)
		{
			sb.append("(P)");
		}
		else
		{
			sb.append("   ");
		}

		final LocalDateTime startDate = getStartDate();
		final LocalDateTime endDate = getEndDate();

		sb.append(startDate).append(" -> ");

		if (startDate != null && endDate != null && startDate.toLocalDate().equals(endDate.toLocalDate()))
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

		sb.append(": ");

		sb.append("duration=").append(stepDef.getRequiredResourceCapacity()).append("/").append(stepDef.getRequiredHumanCapacity());
		sb.append(", ").append(stepDef.getResource());
		sb.append(", ID=").append(id);
		sb.append(", delay=").append(delay);
		if (stepDef.getPinnedStartDate() != null && previousStepEndDate != null)
		{
			sb.append("(").append(Duration.between(previousStepEndDate, stepDef.getPinnedStartDate())).append(")");
		}

		return sb.toString();
	}

	public BooleanWithReason checkProblemFactsValid()
	{
		final LocalDateTime startDateMin = stepDef.getStartDateMin();
		final LocalDateTime dueDate = stepDef.getDueDate();
		final Duration requiredResourceCapacity = stepDef.getRequiredHumanCapacity();
		final Duration requiredHumanCapacity = stepDef.getRequiredHumanCapacity();
		final HumanResourceCapacity humanResourceCapacity = stepDef.getHumanResourceCapacity();

		if (!startDateMin.isBefore(dueDate))
		{
			return BooleanWithReason.falseBecause("StartDateMin shall be before DueDate");
		}
		final Duration durationMax = Duration.between(startDateMin, dueDate);

		if (requiredResourceCapacity.getSeconds() <= 0)
		{
			return BooleanWithReason.falseBecause("Duration must be set and must be positive");
		}
		if (requiredResourceCapacity.compareTo(durationMax) > 0)
		{
			return BooleanWithReason.falseBecause("Duration does not fit into StartDateMin/DueDate interval");
		}

		if (requiredHumanCapacity.getSeconds() > 0 && humanResourceCapacity == null)
		{
			return BooleanWithReason.falseBecause("Human Resource capacity shall be specified when step requires human capacity");
		}

		return BooleanWithReason.TRUE;
	}

	@ValueRangeProvider
	public CountableValueRange<Integer> getDelayRange()
	{
		final int delayMax = stepDef.computeDelayMax();
		return ValueRangeFactory.createIntValueRange(0, delayMax);
	}

	public ProjectId getProjectId() {return getId().getProjectId();}

	public ResourceId getResourceId() {return getStepDef().getResourceId();}

	public InternalPriority getProjectPriority() {return getStepDef().getProjectPriority();}

	public boolean isRequiredHumanCapacity() {return stepDef.isRequiredHumanCapacity();}

	private int getDelayAsInt() {return delay != null ? delay : 0;}

	private Duration getDelayAsDuration() {return Duration.of(getDelayAsInt(), Plan.PLANNING_TIME_PRECISION);}

	public int getAccumulatedDelayAsInt()
	{
		int accumulatedDelay = 0;
		for (StepAllocation step = this; step != null; step = step.getPrevious())
		{
			accumulatedDelay += step.getDelayAsInt();
		}

		return accumulatedDelay;
	}

	@ShadowVariable(variableListenerClass = StepPreviousEndDateUpdater.class, sourceVariableName = FIELD_delay)
	@Nullable
	public LocalDateTime getPreviousStepEndDate()
	{
		return previousStepEndDate;
	}

	public void setPreviousStepEndDateAndUpdate(@Nullable final LocalDateTime previousStepEndDate)
	{
		setPreviousStepEndDateAndUpdate(previousStepEndDate, null);
	}

	public void setPreviousStepEndDateAndUpdate(@Nullable final LocalDateTime previousStepEndDate, @Nullable final ScoreDirector<Plan> scoreDirector)
	{
		if (scoreDirector != null)
		{
			scoreDirector.beforeVariableChanged(this, FIELD_PreviousStepEndDate);
		}

		this.previousStepEndDate = previousStepEndDate;

		final LocalDateTime startDate = computeStartDate();
		if (startDate != null)
		{
			this.resourceScheduledRange = stepDef.computeResourceScheduledRange(startDate);
			this.humanResourceScheduledRange = stepDef.computeHumanResourceScheduledRange(startDate);
		}
		else
		{
			this.resourceScheduledRange = null;
			this.humanResourceScheduledRange = null;
		}

		if (scoreDirector != null)
		{
			scoreDirector.afterVariableChanged(this, FIELD_PreviousStepEndDate);
		}
	}

	@Nullable
	private LocalDateTime computeStartDate()
	{
		final LocalDateTime pinnedStartDate = stepDef.getPinnedStartDate();
		if (pinnedStartDate != null)
		{
			return pinnedStartDate;
		}
		else if (previousStepEndDate == null)
		{
			return null;
		}
		else
		{
			final Duration delayDuration = getDelayAsDuration();
			return previousStepEndDate.plus(delayDuration);
		}
	}

	@Nullable
	public LocalDateTime getStartDate()
	{
		return resourceScheduledRange != null && (!stepDef.isRequiredHumanCapacity() || humanResourceScheduledRange != null)
				? TimeUtil.minOfNullables(resourceScheduledRange.getStartDate(), humanResourceScheduledRange != null ? humanResourceScheduledRange.getStartDate() : null)
				: null;
	}

	@Nullable
	public LocalDateTime getEndDate()
	{
		return resourceScheduledRange != null && (!stepDef.isRequiredHumanCapacity() || humanResourceScheduledRange != null)
				? TimeUtil.minOfNullables(resourceScheduledRange.getEndDate(), humanResourceScheduledRange != null ? humanResourceScheduledRange.getEndDate() : null)
				: null;
	}

	@Nullable
	public LocalDateTime getResourceScheduledStartDate()
	{
		return resourceScheduledRange != null && humanResourceScheduledRange != null
				? resourceScheduledRange.getStartDate()
				: null;
	}

	@Nullable
	public LocalDateTime getResourceScheduledEndDate()
	{
		return resourceScheduledRange != null && humanResourceScheduledRange != null
				? resourceScheduledRange.getEndDate()
				: null;
	}

	public LocalDateTime getStartDateMin() {return stepDef.getStartDateMin();}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isStartDateMinRespected() {return getDurationBeforeStartDateMin().isZero();}

	public Duration getDurationBeforeStartDateMin()
	{
		final LocalDateTime startDateMin = stepDef.getStartDateMin();
		final LocalDateTime startDate = getStartDate();
		return startDate != null && startDate.isBefore(startDateMin) ? Duration.between(startDate, startDateMin) : Duration.ZERO;
	}

	public int getDurationBeforeStartDateMinAsInt()
	{
		return DurationUtils.toInt(getDurationBeforeStartDateMin(), Plan.PLANNING_TIME_PRECISION);
	}

	public Duration getDurationAfterDue()
	{
		final LocalDateTime dueDate = stepDef.getDueDate();
		final LocalDateTime endDate = getEndDate();
		return endDate != null && endDate.isAfter(dueDate) ? Duration.between(dueDate, endDate) : Duration.ZERO;
	}

	public int getDurationAfterDueAsInt()
	{
		return DurationUtils.toInt(getDurationAfterDue(), Plan.PLANNING_TIME_PRECISION);
	}

	public boolean isDueDateNotRespected() {return !isDueDateRespected();}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isDueDateRespected() {return getDurationAfterDue().isZero();}

	private Duration getDurationFromEndToDueDate()
	{
		return Duration.between(Objects.requireNonNull(getEndDate()), stepDef.getDueDate());
	}

	public int getDurationFromEndToDueDateInHoursAbs() {return Math.abs((int)getDurationFromEndToDueDate().toHours());}
}
