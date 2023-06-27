package de.metas.calendar.plan_optimizer.domain;

import de.metas.project.ProjectId;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.step.WOProjectStepId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class StepId implements
		// IMPORTANT: Comparable is required by OptaPlanner
		Comparable<StepId>
{
	@NonNull WOProjectStepId woProjectStepId;
	@NonNull WOProjectResourceId woProjectResourceId;

	public ProjectId getProjectId() {return woProjectResourceId.getProjectId();}

	@Override
	public int compareTo(@NonNull final StepId other)
	{
		final int c = woProjectStepId.compareTo(other.woProjectStepId);
		if (c != 0)
		{
			return c;
		}

		return woProjectResourceId.compareTo(other.woProjectResourceId);
	}
}
