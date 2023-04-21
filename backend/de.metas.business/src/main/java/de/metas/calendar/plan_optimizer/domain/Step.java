package de.metas.calendar.plan_optimizer.domain;

import de.metas.calendar.plan_optimizer.solver.EndDateUpdatingVariableListener;
import de.metas.project.ProjectId;
import de.metas.project.workorder.step.WOProjectStepId;
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
	@PlanningId private WOProjectStepId id;

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
			final WOProjectStepId id,
			final int projectSeqNo,
			final Resource resource,
			final Duration duration,
			final LocalDateTime dueDate)
	{
		this.id = id;
		this.projectSeqNo = projectSeqNo;
		this.resource = resource;
		this.duration = duration;
		this.dueDate = dueDate;
	}

	public ProjectId getProjectId() {return getId().getProjectId();}

	public void updateEndDate()
	{
		endDate = startDate != null ? startDate.plus(duration) : null;
	}

	public boolean isDueDateNotRespected()
	{
		return endDate == null || endDate.isAfter(dueDate);
	}
}
