package de.metas.calendar.plan_optimizer.domain;

import de.metas.project.ProjectId;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.step.WOProjectStepId;
import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class StepAllocationId
		implements Comparable<StepAllocationId> // IMPORTANT: Comparable is required by Timefold
{
	@NonNull StepId stepId;
	@NonNull StepAllocationType type;

	@Override
	public String toString()
	{
		return "P" + stepId.getProjectId().getRepoId()
				+ "-S" + stepId.getWoProjectResourceId().getRepoId()
				+ "-" + type;
	}

	@Override
	public int compareTo(@NonNull final StepAllocationId other)
	{
		final int c = stepId.compareTo(other.stepId);
		if (c != 0)
		{
			return c;
		}

		return type.compareTo(other.type);
	}

	public ProjectId getProjectId() {return stepId.getProjectId();}

	public WOProjectResourceId getWoProjectResourceId() {return stepId.getWoProjectResourceId();}

	public WOProjectStepId getWoProjectStepId() {return stepId.getWoProjectStepId();}
}
