package de.metas.calendar.plan_optimizer.domain;

import de.metas.calendar.plan_optimizer.solver.EndDateUpdatingVariableListener;
import de.metas.project.InternalPriority;
import de.metas.project.ProjectId;
import lombok.Builder;
import lombok.Data;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
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

	private LocalDateTime dueDate;

	public static final String FIELD_startDate = "startDate";
	@PlanningVariable
	@Nullable private LocalDateTime startDate;

	public static final String FIELD_endDate = "endDate";
	@ShadowVariable(variableListenerClass = EndDateUpdatingVariableListener.class, sourceVariableName = FIELD_startDate)
	@Nullable private LocalDateTime endDate;

	// No-arg constructor required for OptaPlanner
	public Step() {}

	@Builder
	private Step(
			final StepId id,
			final InternalPriority projectPriority,
			final int projectSeqNo,
			final Resource resource,
			final Duration duration,
			final LocalDateTime dueDate,
			final LocalDateTime startDate,
			final LocalDateTime endDate)
	{
		this.id = id;
		this.projectPriority = projectPriority;
		this.projectSeqNo = projectSeqNo;
		this.resource = resource;
		this.duration = duration;
		this.dueDate = dueDate;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public ProjectId getProjectId() {return getId().getProjectId();}

	public void updateEndDate()
	{
		endDate = startDate != null ? startDate.plus(duration) : null;
	}

	public boolean isDueDateNotRespected()
	{
		return dueDate != null && (endDate == null || endDate.isAfter(dueDate));
	}
}
