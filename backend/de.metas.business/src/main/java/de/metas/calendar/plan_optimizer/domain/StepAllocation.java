package de.metas.calendar.plan_optimizer.domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.valuerange.CountableValueRange;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeFactory;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import ai.timefold.solver.core.api.domain.variable.ShadowVariable;
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

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@PlanningEntity
@Setter
@Getter
//@EqualsAndHashCode(doNotUseGetters = true) // IMPORTANT: do not use it because we have next/previous references
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
	public static final String FIELD_PreviousStepEndDate = "previousStepEndDate";
	@Nullable @Setter(AccessLevel.NONE) private LocalDateTime previousStepEndDate;

	//
	// Computed from shadow variables
	private boolean variablesComputed;
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
		this.humanResourceScheduledRange = humanResourceScheduledRange;
		this.variablesComputed = this.resourceScheduledRange != null || this.humanResourceScheduledRange != null;

		//System.out.println("ctor: " + FIELD_PreviousStepEndDate + "=" + this.previousStepEndDate + " -- " + id + " -- " + Trace.toOneLineStackTraceString());
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

		sb.append(PlanToStringHelper.toString(getStartDate(), getEndDate()));
		sb.append(": ");

		sb.append(stepDef.getResource()).append("=").append(PlanToStringHelper.toHoursString(stepDef.getRequiredResourceCapacity()));
		if (stepDef.getHumanResourceId() != null)
		{
			sb.append(", HR").append(stepDef.getHumanResourceId()).append("=").append(PlanToStringHelper.toHoursString(stepDef.getRequiredHumanCapacity()));
		}
		sb.append(", ID=").append(id);
		sb.append(", delay=").append(delay != null ? PlanToStringHelper.toHoursString(Duration.of(delay, Plan.PLANNING_TIME_PRECISION)) : null)
				.append("<").append(PlanToStringHelper.toHoursString(stepDef.computeDelayMax().truncatedTo(Plan.PLANNING_TIME_PRECISION)));
		if (stepDef.getPinnedStartDate() != null && previousStepEndDate != null)
		{
			sb.append("(").append(Duration.between(previousStepEndDate, stepDef.getPinnedStartDate())).append(")");
		}

		return sb.toString();
	}

	public BooleanWithReason checkProblemFactsValid() {return stepDef.checkProblemFactsValid();}

	@ValueRangeProvider
	public CountableValueRange<Integer> getDelayRange()
	{
		final int delayMaxInt = DurationUtils.toInt(stepDef.computeDelayMax(), Plan.PLANNING_TIME_PRECISION);
		return ValueRangeFactory.createIntValueRange(0, delayMaxInt);
	}

	public ProjectId getProjectId() {return getId().getProjectId();}

	public ResourceId getResourceId() {return getStepDef().getResourceId();}

	@Nullable
	public HumanResourceId getHumanResourceId() {return getStepDef().getHumanResourceId();}

	public InternalPriority getProjectPriority() {return getStepDef().getProjectPriority();}

	public boolean isFirstStep() {return previous == null;}

	public int getDelayAsInt() {return delay != null ? delay : 0;}

	private Duration getDelayAsDuration() {return Duration.of(getDelayAsInt(), Plan.PLANNING_TIME_PRECISION);}

	@ShadowVariable(variableListenerClass = StepPreviousEndDateUpdater.class, sourceVariableName = FIELD_delay)
	@Nullable
	public LocalDateTime getPreviousStepEndDate()
	{
		return previousStepEndDate;
	}

	public void setPreviousStepEndDateAndUpdate(@Nullable final LocalDateTime previousStepEndDate)
	{
		this.previousStepEndDate = previousStepEndDate;
		invalidateComputedVariables();
	}

	private void invalidateComputedVariables()
	{
		this.variablesComputed = false;
		this.resourceScheduledRange = null;
		this.humanResourceScheduledRange = null;
	}

	public void computeVariablesIfNeeded()
	{
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

		this.variablesComputed = true;
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
			return previousStepEndDate.plus(getDelayAsDuration());
		}
	}

	public boolean isScheduled()
	{
		computeVariablesIfNeeded();
		return resourceScheduledRange != null && (!stepDef.isRequiredHumanCapacity() || humanResourceScheduledRange != null);
	}

	@Nullable
	public LocalDateTime getStartDate()
	{
		computeVariablesIfNeeded();

		if (!isScheduled())
		{
			return null;
		}

		return TimeUtil.minOfNullables(
				resourceScheduledRange != null ? resourceScheduledRange.getStartDate() : null,
				humanResourceScheduledRange != null ? humanResourceScheduledRange.getStartDate() : null
		);
	}

	@Nullable
	public LocalDateTime getEndDate()
	{
		computeVariablesIfNeeded();

		if (!isScheduled())
		{
			return null;
		}

		return TimeUtil.maxOfNullables(
				resourceScheduledRange != null ? resourceScheduledRange.getEndDate() : null,
				humanResourceScheduledRange != null ? humanResourceScheduledRange.getEndDate() : null
		);
	}

	@Nullable
	public LocalDateTime getResourceScheduledStartDate()
	{
		computeVariablesIfNeeded();
		return resourceScheduledRange != null ? resourceScheduledRange.getStartDate() : null;
	}

	@Nullable
	public LocalDateTime getResourceScheduledEndDate()
	{
		computeVariablesIfNeeded();
		return resourceScheduledRange != null ? resourceScheduledRange.getEndDate() : null;
	}

	@Nullable
	public LocalDateTime getHumanResourceScheduledStartDate()
	{
		computeVariablesIfNeeded();
		return humanResourceScheduledRange != null ? humanResourceScheduledRange.getStartDate() : null;
	}

	@Nullable
	public LocalDateTime getHumanResourceScheduledEndDate()
	{
		computeVariablesIfNeeded();
		return humanResourceScheduledRange != null ? humanResourceScheduledRange.getEndDate() : null;
	}

	LocalDateTime getStartDateMin() {return stepDef.getStartDateMin();}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isStartDateMinRespected() {return getDurationBeforeStartDateMin().isZero();}

	private Duration getDurationBeforeStartDateMin()
	{
		final LocalDateTime startDateMin = stepDef.getStartDateMin();
		final LocalDateTime startDate = getStartDate();
		return startDate != null && startDate.isBefore(startDateMin) ? Duration.between(startDate, startDateMin) : Duration.ZERO;
	}

	public int getDurationBeforeStartDateMinAsInt()
	{
		return DurationUtils.toInt(getDurationBeforeStartDateMin(), Plan.PLANNING_TIME_PRECISION);
	}

	private Duration getDurationAfterDue()
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
