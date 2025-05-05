package de.metas.calendar.plan_optimizer.domain;

import de.metas.project.ProjectId;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.util.NumberUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class StepId implements
		// IMPORTANT: Comparable is required by Timefold
		Comparable<StepId>
{
	@NonNull WOProjectStepId woProjectStepId;
	@Nullable WOProjectResourceId machineWOProjectResourceId;
	@Nullable WOProjectResourceId humanWOProjectResourceId;

	@Builder
	private StepId(
			@NonNull final WOProjectStepId woProjectStepId,
			@Nullable final WOProjectResourceId machineWOProjectResourceId,
			@Nullable final WOProjectResourceId humanWOProjectResourceId)
	{
		this.woProjectStepId = woProjectStepId;
		this.machineWOProjectResourceId = machineWOProjectResourceId;
		this.humanWOProjectResourceId = humanWOProjectResourceId;
	}

	public ProjectId getProjectId() {return woProjectStepId.getProjectId();}

	@Override
	public int compareTo(@NonNull final StepId other)
	{
		return NumberUtils.firstNonZero(
				() -> woProjectStepId.compareTo(other.woProjectStepId),
				() -> WOProjectResourceId.toRepoId(machineWOProjectResourceId) - WOProjectResourceId.toRepoId((other.machineWOProjectResourceId)),
				() -> WOProjectResourceId.toRepoId(humanWOProjectResourceId) - WOProjectResourceId.toRepoId((other.humanWOProjectResourceId))
		);
	}
}
