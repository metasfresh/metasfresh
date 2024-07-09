package de.metas.calendar.plan_optimizer.domain;

import de.metas.project.ProjectId;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.step.WOProjectStepId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StepIdTest
{
	@SuppressWarnings("SameParameterValue")
	private static StepId stepId(final int projectRepoId, final int stepRepoId, final Integer machineStepResourceRepoId, final Integer humanStepResourceRepoId)
	{
		final ProjectId projectId = ProjectId.ofRepoId(projectRepoId);
		return StepId.builder()
				.woProjectStepId(WOProjectStepId.ofRepoId(projectId, stepRepoId))
				.machineWOProjectResourceId(machineStepResourceRepoId != null ? WOProjectResourceId.ofRepoId(projectId, machineStepResourceRepoId) : null)
				.humanWOProjectResourceId(humanStepResourceRepoId != null ? WOProjectResourceId.ofRepoId(projectId, humanStepResourceRepoId) : null)
				.build();
	}

	@Test
	void compareTo()
	{
		final StepId stepId1 = stepId(1, 2, 3, null);
		final StepId stepId2 = stepId(1, 2, 4, null);
		//noinspection AssertThatComparable
		assertThat(stepId1.compareTo(stepId1)).isZero();
		assertThat(stepId1).isLessThan(stepId2);
		assertThat(stepId2).isGreaterThan(stepId1);
	}
}