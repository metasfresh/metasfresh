package de.metas.calendar.plan_optimizer.domain;

import de.metas.calendar.plan_optimizer.solver.EndDateUpdatingVariableListener;
import de.metas.project.InternalPriority;
import de.metas.project.ProjectId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.entity.PlanningPin;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.valuerange.CountableValueRange;
import org.optaplanner.core.api.domain.valuerange.ValueRangeFactory;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.core.api.domain.variable.ShadowVariable;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.LocalDateTime;

@PlanningEntity
@Data
public class Step
{
	@PlanningId private StepId id;

	InternalPriority projectPriority;
	int projectSeqNo;

	private Resource resource;
	private Duration duration;

	private LocalDateTime startDateMin;
	private LocalDateTime dueDate;

	public static final String FIELD_startDate = "startDate";
	@PlanningVariable
	@Nullable private LocalDateTime startDate;

	public static final String FIELD_endDate = "endDate";
	@ShadowVariable(variableListenerClass = EndDateUpdatingVariableListener.class, sourceVariableName = FIELD_startDate)
	@Nullable private LocalDateTime endDate;

	@PlanningPin
	boolean pinned;

	// No-arg constructor required for OptaPlanner
	public Step() {}

	@Builder
	private Step(
			@NonNull final StepId id,
			@NonNull final InternalPriority projectPriority,
			final int projectSeqNo,
			@NonNull final Resource resource,
			@NonNull final Duration duration,
			@NonNull final LocalDateTime dueDate,
			@NonNull final LocalDateTime startDateMin,
			@Nullable final LocalDateTime startDate,
			@Nullable final LocalDateTime endDate,
			final boolean pinned)
	{
		this.id = id;
		this.projectPriority = projectPriority;
		this.projectSeqNo = projectSeqNo;
		this.resource = resource;
		this.duration = duration;
		this.dueDate = dueDate;
		this.startDateMin = startDateMin;
		this.startDate = startDate;
		this.endDate = endDate;
		this.pinned = pinned;
	}

	@Override
	public String toString()
	{
		// NOTE: keep it concise, important for optaplanner troubleshooting
		return startDate + " -> " + endDate
				+ " (" + duration + ")"
				+ ": dueDate=" + dueDate
				+ ", startDateMin=" + startDateMin
				+ ", " + resource
				+ ", ID=" + (id != null ? id.getWoProjectResourceId().getRepoId() : "?");
	}

	@ValueRangeProvider
	public CountableValueRange<LocalDateTime> createStartDateList()
	{
		final LocalDateTime startDateMax = dueDate.minus(duration);
		return ValueRangeFactory.createLocalDateTimeValueRange(startDateMin, startDateMax, 1, Plan.PLANNING_TIME_PRECISION);
	}

	public ProjectId getProjectId() {return getId().getProjectId();}

	public void updateEndDate()
	{
		endDate = startDate != null ? startDate.plus(duration) : null;
	}

	public boolean isStartDateMinRespected()
	{
		return startDate != null && !startDate.isBefore(startDateMin);
	}

	public boolean isDueDateNotRespected()
	{
		return dueDate != null && (endDate == null || endDate.isAfter(dueDate));
	}

	public Duration getDurationFromEndToDueDate()
	{
		if (dueDate == null || endDate == null)
		{
			return Duration.ZERO;
		}
		else
		{
			return Duration.between(endDate, dueDate);
		}
	}

	public int getDurationFromEndToDueDateInHoursAbs()
	{
		return Math.abs((int)getDurationFromEndToDueDate().toHours());
	}
}
