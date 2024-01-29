package de.metas.calendar.plan_optimizer.domain;

import de.metas.product.ResourceId;
import de.metas.project.InternalPriority;
import de.metas.project.ProjectId;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.step.WOProjectStepId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class PlanTest
{
	private static final ProjectId PROJECT_ID = ProjectId.ofRepoId(1);

	@Nested
	class copy
	{
		StepId stepId(int id)
		{
			return StepId.builder()
					.woProjectStepId(WOProjectStepId.ofRepoId(PROJECT_ID, id))
					.machineWOProjectResourceId(WOProjectResourceId.ofRepoId(PROJECT_ID, 1))
					.build();
		}

		StepDef step(int id)
		{
			return StepDef.builder()
					.id(stepId(id))
					.projectPriority(InternalPriority.MEDIUM)
					.resource(Resource.builder().id(ResourceId.ofRepoId(1)).name("R1").build())
					.requiredResourceCapacity(Duration.ofHours(1))
					.requiredHumanCapacity(Duration.ZERO)
					.startDateMin(LocalDate.parse("2023-01-01").atStartOfDay())
					.dueDate(LocalDate.parse("2023-12-31").atTime(LocalTime.MAX))
					.build();
		}

		public Project project(final int count)
		{
			return Project.builder()
					.steps(IntStream.rangeClosed(1, count)
							.mapToObj(this::step)
							.collect(Collectors.toList()))
					.build();
		}

		@Test
		void assert_prev_next_are_correctly_linked()
		{
			final Plan plan = Plan.builder()
					.stepsList(new ArrayList<>(project(4).createAllocations()))
					.build();

			final Plan newPlan = plan.copy();

			assertThat(newPlan.getStepsList()).hasSize(4);

			//
			// Assert copied steps have the same ID but are not the same instance
			for (int i = 0; i < 4; i++)
			{
				assertThat(newPlan.getStepsList().get(i)).isNotSameAs(plan.getStepsList().get(i));
				assertThat(newPlan.getStepsList().get(i).getId()).isSameAs(plan.getStepsList().get(i).getId());
			}

			assertThat(newPlan.getStepsList().get(0).getPrevious()).isNull();
			assertThat(newPlan.getStepsList().get(0).getNext()).isEqualTo(newPlan.getStepsList().get(1));

			assertThat(newPlan.getStepsList().get(1).getPrevious()).isEqualTo(newPlan.getStepsList().get(0));
			assertThat(newPlan.getStepsList().get(1).getNext()).isEqualTo(newPlan.getStepsList().get(2));

			assertThat(newPlan.getStepsList().get(2).getPrevious()).isEqualTo(newPlan.getStepsList().get(1));
			assertThat(newPlan.getStepsList().get(2).getNext()).isEqualTo(newPlan.getStepsList().get(3));

			assertThat(newPlan.getStepsList().get(3).getPrevious()).isEqualTo(newPlan.getStepsList().get(2));
			assertThat(newPlan.getStepsList().get(3).getNext()).isNull();
		}
	}
}