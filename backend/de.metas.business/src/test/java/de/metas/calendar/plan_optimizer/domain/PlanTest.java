package de.metas.calendar.plan_optimizer.domain;

import de.metas.product.ResourceId;
import de.metas.project.InternalPriority;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.step.WOProjectStepId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class PlanTest
{
	@Nested
	class copy
	{
		StepId stepId(int id)
		{
			return StepId.builder()
					.woProjectStepId(WOProjectStepId.ofRepoId(1, id))
					.woProjectResourceId(WOProjectResourceId.ofRepoId(1, 1))
					.build();
		}

		Step step(int id)
		{
			return Step.builder()
					.id(stepId(id))
					.projectPriority(InternalPriority.MEDIUM)
					.resource(new Resource(ResourceId.ofRepoId(1), "R1", null))
					.requiredResourceCapacity(Duration.ofHours(1))
					.requiredHumanCapacity(Duration.ZERO)
					.startDateMin(LocalDate.parse("2023-01-01").atStartOfDay())
					.dueDate(LocalDate.parse("2023-12-31").atTime(LocalTime.MAX))
					.build();
		}

		public ArrayList<Step> steps(final int count)
		{
			final ArrayList<Step> result = new ArrayList<>(count);

			Step prevStep = null;
			for (int id = 1; id <= count; id++)
			{
				final Step step = step(id);
				result.add(step);
				if (prevStep != null)
				{
					prevStep.setNextStep(step);
					step.setPreviousStep(prevStep);
				}

				prevStep = step;
			}

			return result;
		}

		@Test
		void assert_prev_next_are_correctly_linked()
		{
			final Plan plan = Plan.newInstance();
			plan.setStepsList(steps(4));

			final Plan newPlan = plan.copy();

			assertThat(newPlan.getStepsList()).hasSize(4);

			//
			// Assert copied steps have the same ID but are not the same instance
			for (int i = 0; i < 4; i++)
			{
				assertThat(newPlan.getStepsList().get(i)).isNotSameAs(plan.getStepsList().get(i));
				assertThat(newPlan.getStepsList().get(i).getId()).isSameAs(plan.getStepsList().get(i).getId());
			}

			assertThat(newPlan.getStepsList().get(0).getPreviousStep()).isNull();
			assertThat(newPlan.getStepsList().get(0).getNextStep()).isEqualTo(newPlan.getStepsList().get(1));

			assertThat(newPlan.getStepsList().get(1).getPreviousStep()).isEqualTo(newPlan.getStepsList().get(0));
			assertThat(newPlan.getStepsList().get(1).getNextStep()).isEqualTo(newPlan.getStepsList().get(2));

			assertThat(newPlan.getStepsList().get(2).getPreviousStep()).isEqualTo(newPlan.getStepsList().get(1));
			assertThat(newPlan.getStepsList().get(2).getNextStep()).isEqualTo(newPlan.getStepsList().get(3));

			assertThat(newPlan.getStepsList().get(3).getPreviousStep()).isEqualTo(newPlan.getStepsList().get(2));
			assertThat(newPlan.getStepsList().get(3).getNextStep()).isNull();
		}
	}
}