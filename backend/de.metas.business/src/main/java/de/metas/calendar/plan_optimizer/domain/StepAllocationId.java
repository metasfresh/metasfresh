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

	@Override
	public String toString()
	{
		return "P" + stepId.getProjectId().getRepoId()
				+ "-S" + stepId.getWoProjectStepId().getRepoId();
	}

	@Override
	public int compareTo(@NonNull final StepAllocationId other)
	{
		return stepId.compareTo(other.stepId);
	}

	public ProjectId getProjectId() {return stepId.getProjectId();}

	public WOProjectStepId getWoProjectStepId() {return stepId.getWoProjectStepId();}

	public WOProjectResourceId getMachineWOProjectResourceId() {return stepId.getMachineWOProjectResourceId();}

	public WOProjectResourceId getHumanWOProjectResourceId() {return stepId.getHumanWOProjectResourceId();}
}
