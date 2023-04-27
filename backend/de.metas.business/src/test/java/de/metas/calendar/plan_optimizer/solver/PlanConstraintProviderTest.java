package de.metas.calendar.plan_optimizer.solver;

import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.plan_optimizer.domain.Resource;
import de.metas.calendar.plan_optimizer.domain.Step;
import de.metas.calendar.plan_optimizer.domain.StepId;
import de.metas.product.ResourceId;
import de.metas.project.InternalPriority;
import de.metas.project.ProjectId;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.step.WOProjectStepId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.optaplanner.test.api.score.stream.ConstraintVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class PlanConstraintProviderTest
{
	private static final ProjectId PROJECT_ID1 = ProjectId.ofRepoId(1);
	private static final ProjectId PROJECT_ID2 = ProjectId.ofRepoId(2);
	private static final Resource RESOURCE = new Resource(ResourceId.ofRepoId(1), "R1");

	private static ConstraintVerifier<PlanConstraintProvider, Plan> constraintVerifier;

	private AtomicInteger nextProjectStepRepoId;

	@BeforeAll
	static void beforeAll()
	{
		constraintVerifier = ConstraintVerifier.build(new PlanConstraintProvider(), Plan.class, Step.class);
	}

	@BeforeEach
	void beforeEach()
	{
		nextProjectStepRepoId = new AtomicInteger(101);
	}

	StepId nextStepId(final ProjectId projectId)
	{
		final int stepRepoId = nextProjectStepRepoId.getAndIncrement();
		return StepId.builder()
				.woProjectStepId(WOProjectStepId.ofRepoId(projectId, stepRepoId))
				.woProjectResourceId(WOProjectResourceId.ofRepoId(projectId, stepRepoId))
				.build();
	}

	@Nested
	class projectStepsOverlap
	{
		Step step(final ProjectId projectId, final String startDate, String endDate)
		{
			Step step = new Step();
			step.setId(nextStepId(projectId));
			step.setStartDate(LocalDate.parse(startDate).atStartOfDay());
			step.setEndDate(LocalDate.parse(endDate).atStartOfDay());
			return step;
		}

		@Test
		void sameProject_overlapping()
		{
			constraintVerifier.verifyThat(PlanConstraintProvider::projectStepsOverlap)
					.given(
							step(PROJECT_ID1, "2023-04-01", "2023-04-05"),
							step(PROJECT_ID1, "2023-04-04", "2023-04-10")
					)
					.penalizesBy(1);
		}

		@Test
		void sameProject_connected()
		{
			constraintVerifier.verifyThat(PlanConstraintProvider::projectStepsOverlap)
					.given(
							step(PROJECT_ID1, "2023-04-01", "2023-04-05"),
							step(PROJECT_ID1, "2023-04-05", "2023-04-10")
					)
					.penalizesBy(0);
		}

		@Test
		void sameProject_notOverlapping()
		{
			constraintVerifier.verifyThat(PlanConstraintProvider::projectStepsOverlap)
					.given(
							step(PROJECT_ID1, "2023-04-01", "2023-04-05"),
							step(PROJECT_ID1, "2023-04-06", "2023-04-10")
					)
					.penalizesBy(0);
		}

		@Test
		void differentProjects_overlapping()
		{
			constraintVerifier.verifyThat(PlanConstraintProvider::projectStepsOverlap)
					.given(
							step(PROJECT_ID1, "2023-04-01", "2023-04-05"),
							step(PROJECT_ID2, "2023-04-04", "2023-04-10")
					)
					.penalizesBy(0);
		}

	}

	@Nested
	class projectStepsNotInOrder
	{
		Step step(final String date, int seqNo)
		{
			Step step = new Step();
			step.setStartDate(LocalDate.parse(date).atStartOfDay());
			step.setProjectSeqNo(seqNo);
			return step;
		}

		@Test
		void ascendingDates_ascendingSeqNo()
		{
			final Step step1 = step("2023-04-25", 10);
			final Step step2 = step("2023-04-26", 20);
			assertThat(PlanConstraintProvider.projectStepsNotInOrder(step1, step2)).isFalse();
		}

		@Test
		void ascendingDates_descendingSeqNo()
		{
			final Step step1 = step("2023-04-25", 20);
			final Step step2 = step("2023-04-26", 10);
			assertThat(PlanConstraintProvider.projectStepsNotInOrder(step1, step2)).isTrue();
		}

		@Test
		void descendingDates_ascendingSeqNo()
		{
			final Step step1 = step("2023-04-26", 10);
			final Step step2 = step("2023-04-25", 20);
			assertThat(PlanConstraintProvider.projectStepsNotInOrder(step1, step2)).isTrue();
		}

		@Test
		void descendingDates_descendingSeqNo()
		{
			final Step step1 = step("2023-04-26", 20);
			final Step step2 = step("2023-04-25", 10);
			assertThat(PlanConstraintProvider.projectStepsNotInOrder(step1, step2)).isFalse();
		}
	}

	@Nested
	class dueDate
	{
		@SuppressWarnings("SameParameterValue")
		Step step(final String endDateStr, final String dueDate)
		{
			Step step = new Step();
			final LocalDateTime endDate = LocalDate.parse(endDateStr).atStartOfDay();
			step.setEndDate(endDate);
			step.setDueDate(LocalDate.parse(dueDate).atStartOfDay());

			// IMPORANT: we must set StartDate because entities with any null planning variable are not selected by contraints forEach()
			// thx https://stackoverflow.com/a/73334167/2410079
			step.setStartDate(endDate.minusDays(1));

			return step;
		}

		@Test
		void endDate_before_dueDate()
		{
			constraintVerifier.verifyThat(PlanConstraintProvider::dueDate)
					.given(step("2023-04-25", "2023-04-26"))
					.penalizesBy(0);
		}

		@Test
		void endDate_equalsTo_dueDate()
		{
			constraintVerifier.verifyThat(PlanConstraintProvider::dueDate)
					.given(step("2023-04-26", "2023-04-26"))
					.penalizesBy(0);
		}

		@Test
		void endDate_after_dueDate()
		{
			//assertThat(step("2023-04-27", "2023-04-26").isDueDateNotRespected()).isTrue();

			constraintVerifier.verifyThat(PlanConstraintProvider::dueDate)
					.given(step("2023-04-27", "2023-04-26"))
					.penalizesBy(1);
		}

	}

	@Nested
	class stepsNotRespectingProjectPriority
	{
		Step step(final String date, InternalPriority priority)
		{
			Step step = new Step();
			step.setId(nextStepId(PROJECT_ID1)); // needed for contraint stream forEachUnique
			step.setResource(RESOURCE);
			step.setStartDate(LocalDate.parse(date).atStartOfDay());
			step.setProjectPriority(priority);
			return step;
		}

		@Test
		void sameStep()
		{
			final Step step1 = step("2023-04-25", InternalPriority.MEDIUM);
			final Step step2 = step("2023-04-25", InternalPriority.MEDIUM);
			assertThat(PlanConstraintProvider.stepsNotRespectingProjectPriority(step1, step2)).isFalse();
			constraintVerifier.verifyThat(PlanConstraintProvider::stepsNotRespectingProjectPriority).given(step1, step2).penalizesBy(0);
		}

		@Test
		void descendingDates_samePrio()
		{
			final Step step1 = step("2023-04-25", InternalPriority.MEDIUM);
			final Step step2 = step("2023-04-26", InternalPriority.MEDIUM);
			assertThat(PlanConstraintProvider.stepsNotRespectingProjectPriority(step1, step2)).isFalse();
			constraintVerifier.verifyThat(PlanConstraintProvider::stepsNotRespectingProjectPriority).given(step1, step2).penalizesBy(0);
		}

		@Test
		void sameDates_highToLowPrio()
		{
			final Step step1 = step("2023-04-25", InternalPriority.HIGH);
			final Step step2 = step("2023-04-25", InternalPriority.LOW);
			assertThat(PlanConstraintProvider.stepsNotRespectingProjectPriority(step1, step2)).isTrue();
			constraintVerifier.verifyThat(PlanConstraintProvider::stepsNotRespectingProjectPriority).given(step1, step2).penalizesBy(1);
		}

		@Test
		void sameDates_lowToHighPrio()
		{
			final Step step1 = step("2023-04-25", InternalPriority.LOW);
			final Step step2 = step("2023-04-25", InternalPriority.HIGH);
			assertThat(PlanConstraintProvider.stepsNotRespectingProjectPriority(step1, step2)).isTrue();
			constraintVerifier.verifyThat(PlanConstraintProvider::stepsNotRespectingProjectPriority).given(step1, step2).penalizesBy(1);
		}

		@Test
		void ascendingDates_samePrio()
		{
			final Step step1 = step("2023-04-25", InternalPriority.MEDIUM);
			final Step step2 = step("2023-04-26", InternalPriority.MEDIUM);
			assertThat(PlanConstraintProvider.stepsNotRespectingProjectPriority(step1, step2)).isFalse();
			constraintVerifier.verifyThat(PlanConstraintProvider::stepsNotRespectingProjectPriority).given(step1, step2).penalizesBy(0);
		}

		@Test
		void ascendingDates_highToLowPrio()
		{
			final Step step1 = step("2023-04-25", InternalPriority.HIGH);
			final Step step2 = step("2023-04-26", InternalPriority.LOW);
			assertThat(PlanConstraintProvider.stepsNotRespectingProjectPriority(step1, step2)).isFalse();
			constraintVerifier.verifyThat(PlanConstraintProvider::stepsNotRespectingProjectPriority).given(step1, step2).penalizesBy(0);
		}

		@Test
		void ascendingDates_lowToHighPrio()
		{
			final Step step1 = step("2023-04-25", InternalPriority.LOW);
			final Step step2 = step("2023-04-26", InternalPriority.HIGH);
			assertThat(PlanConstraintProvider.stepsNotRespectingProjectPriority(step1, step2)).isTrue();
			constraintVerifier.verifyThat(PlanConstraintProvider::stepsNotRespectingProjectPriority).given(step1, step2).penalizesBy(1);
		}

	}
}