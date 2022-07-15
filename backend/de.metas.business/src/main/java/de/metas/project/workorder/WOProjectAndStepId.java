package de.metas.project.workorder;

import de.metas.project.ProjectId;
import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class WOProjectAndStepId
{
	@NonNull ProjectId projectId;
	@NonNull WOProjectStepId stepId;

	public static WOProjectAndStepId ofRepoId(final int projectRepoId, final int stepRepoId)
	{
		return of(ProjectId.ofRepoId(projectRepoId), WOProjectStepId.ofRepoId(stepRepoId));
	}
}
