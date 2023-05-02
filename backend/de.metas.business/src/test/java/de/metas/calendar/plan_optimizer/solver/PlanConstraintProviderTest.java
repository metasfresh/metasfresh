package de.metas.calendar.plan_optimizer.solver;

import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.plan_optimizer.domain.Resource;
import de.metas.calendar.plan_optimizer.domain.Step;
import de.metas.calendar.plan_optimizer.domain.StepId;
import de.metas.calendar.plan_optimizer.persistance.DatabasePlanLoaderInstance;
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

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class PlanConstraintProviderTest
{
	private static final ProjectId PROJECT_ID1 = ProjectId.ofRepoId(1);
	private static final ProjectId PROJECT_ID2 = ProjectId.ofRepoId(2);
	private static final Resource RESOURCE = new Resource(ResourceId.ofRepoId(1), "R1");
	private static final Resource RESOURCE2 = new Resource(ResourceId.ofRepoId(2), "R2");

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
	class resourceConflict
	{
		LocalDateTime START_DATE_MIN = LocalDate.parse("2023-04-01").atStartOfDay();

		Step step(final Resource resource, final String startDateStr, final String endDateStr)
		{
			final LocalDateTime startDate = LocalDate.parse(startDateStr).atStartOfDay();
			final LocalDateTime endDate = LocalDate.parse(endDateStr).atStartOfDay();
			final Duration duration = Duration.between(startDate, endDate);

			Step step = new Step();
			step.setId(nextStepId(PROJECT_ID1));
			step.setResource(resource);
			step.setStartDateMin(START_DATE_MIN);
			step.setDueDate(endDate); // some not null value because we don't want to fail toString()
			step.setDuration(duration);
			step.setDelay(DatabasePlanLoaderInstance.computeDelay(step.getStartDateMin(), startDate));

			assertThat(step.getStartDate()).isEqualTo(startDate);
			assertThat(step.getEndDate()).isEqualTo(endDate);

			return step;
		}

		@Test
		void sameResource_overlapping()
		{
			final Step step1 = step(RESOURCE, "2023-04-01", "2023-04-05");
			final Step step2 = step(RESOURCE, "2023-04-04", "2023-04-10");
			constraintVerifier.verifyThat(PlanConstraintProvider::resourceConflict).given(step1, step2).penalizesBy(24); // 24h=1day
		}

		@Test
		void sameResource_connected()
		{
			constraintVerifier.verifyThat(PlanConstraintProvider::resourceConflict)
					.given(
							step(RESOURCE, "2023-04-01", "2023-04-05"),
							step(RESOURCE, "2023-04-05", "2023-04-10")
					)
					.penalizesBy(0);
		}

		@Test
		void sameResource_notOverlapping()
		{
			constraintVerifier.verifyThat(PlanConstraintProvider::resourceConflict)
					.given(
							step(RESOURCE, "2023-04-01", "2023-04-05"),
							step(RESOURCE, "2023-04-06", "2023-04-10")
					)
					.penalizesBy(0);
		}

		@Test
		void differentResources_overlapping()
		{
			constraintVerifier.verifyThat(PlanConstraintProvider::resourceConflict)
					.given(
							step(RESOURCE, "2023-04-01", "2023-04-05"),
							step(RESOURCE2, "2023-04-04", "2023-04-10")
					)
					.penalizesBy(0);
		}
	}

	@Nested
	class dueDate
	{
		@SuppressWarnings("SameParameterValue")
		Step step(final String endDateStr, final String dueDate)
		{
			final LocalDateTime endDate = LocalDate.parse(endDateStr).atStartOfDay();
			final Duration duration = Duration.of(1, Plan.PLANNING_TIME_PRECISION);
			final LocalDateTime startDate = endDate.minus(duration);

			Step step = new Step();
			step.setId(nextStepId(PROJECT_ID1));
			step.setStartDateMin(startDate);
			step.setDelay(0);
			step.setDuration(duration);
			step.setDueDate(LocalDate.parse(dueDate).atStartOfDay());

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
		void endDate_after_dueDate_with_24h()
		{
			//assertThat(step("2023-04-27", "2023-04-26").isDueDateNotRespected()).isTrue();

			constraintVerifier.verifyThat(PlanConstraintProvider::dueDate)
					.given(step("2023-04-27", "2023-04-26"))
					.penalizesBy(24); // 24h=1day
		}

		@Test
		void endDate_after_dueDate_with_48h()
		{
			//assertThat(step("2023-04-27", "2023-04-26").isDueDateNotRespected()).isTrue();

			constraintVerifier.verifyThat(PlanConstraintProvider::dueDate)
					.given(step("2023-04-28", "2023-04-26"))
					.penalizesBy(48); // 24h=1day
		}

	}

	@Nested
	class stepsNotRespectingProjectPriority
	{
		LocalDateTime START_DATE_MIN = LocalDate.parse("2023-04-25").atStartOfDay();
		private HashMap<ProjectId, Step> lastStepByProjectId;

		@BeforeEach
		void beforeEach()
		{
			lastStepByProjectId = new HashMap<>();
		}

		Step step(final ProjectId projectId, final String startDateStr, InternalPriority priority)
		{
			final LocalDateTime startDate = LocalDate.parse(startDateStr).atStartOfDay();

			Step step = new Step();
			step.setId(nextStepId(projectId)); // needed for contraint stream forEachUnique
			step.setResource(RESOURCE);
			step.setProjectPriority(priority);
			step.setStartDateMin(START_DATE_MIN);
			step.setDueDate(LocalDate.parse("2024-12-31").atStartOfDay()); // some not null value because we don't want to fail toString()
			step.setDuration(Duration.of(1, Plan.PLANNING_TIME_PRECISION));

			Step prevStep = this.lastStepByProjectId.get(projectId);
			if (prevStep != null)
			{
				step.setPreviousStep(prevStep);
				prevStep.setNextStep(step);

				step.setDelay(DatabasePlanLoaderInstance.computeDelay(prevStep.getEndDate(), startDate));
			}
			else
			{
				step.setDelay(DatabasePlanLoaderInstance.computeDelay(step.getStartDateMin(), startDate));
			}

			this.lastStepByProjectId.put(projectId, step);

			return step;
		}

		@Test
		void sameStep()
		{
			final Step step1 = step(PROJECT_ID1, "2023-04-25", InternalPriority.MEDIUM);
			final Step step2 = step(PROJECT_ID2, "2023-04-25", InternalPriority.MEDIUM);
			assertThat(PlanConstraintProvider.stepsNotRespectingProjectPriority(step1, step2)).isFalse();
			constraintVerifier.verifyThat(PlanConstraintProvider::stepsNotRespectingProjectPriority).given(step1, step2).penalizesBy(0);
		}

		@Test
		void descendingDates_samePrio()
		{
			final Step step1 = step(PROJECT_ID1, "2023-04-25", InternalPriority.MEDIUM);
			final Step step2 = step(PROJECT_ID2, "2023-04-26", InternalPriority.MEDIUM);
			assertThat(PlanConstraintProvider.stepsNotRespectingProjectPriority(step1, step2)).isFalse();
			constraintVerifier.verifyThat(PlanConstraintProvider::stepsNotRespectingProjectPriority).given(step1, step2).penalizesBy(0);
		}

		@Test
		void sameDates_highToLowPrio()
		{
			final Step step1 = step(PROJECT_ID1, "2023-04-25", InternalPriority.HIGH);
			final Step step2 = step(PROJECT_ID2, "2023-04-25", InternalPriority.LOW);
			assertThat(PlanConstraintProvider.stepsNotRespectingProjectPriority(step1, step2)).isTrue();
			constraintVerifier.verifyThat(PlanConstraintProvider::stepsNotRespectingProjectPriority).given(step1, step2).penalizesBy(1);
		}

		@Test
		void sameDates_lowToHighPrio()
		{
			final Step step1 = step(PROJECT_ID1, "2023-04-25", InternalPriority.LOW);
			final Step step2 = step(PROJECT_ID2, "2023-04-25", InternalPriority.HIGH);
			assertThat(PlanConstraintProvider.stepsNotRespectingProjectPriority(step1, step2)).isTrue();
			constraintVerifier.verifyThat(PlanConstraintProvider::stepsNotRespectingProjectPriority).given(step1, step2).penalizesBy(1);
		}

		@Test
		void ascendingDates_samePrio()
		{
			final Step step1 = step(PROJECT_ID1, "2023-04-25", InternalPriority.MEDIUM);
			final Step step2 = step(PROJECT_ID2, "2023-04-26", InternalPriority.MEDIUM);
			assertThat(PlanConstraintProvider.stepsNotRespectingProjectPriority(step1, step2)).isFalse();
			constraintVerifier.verifyThat(PlanConstraintProvider::stepsNotRespectingProjectPriority).given(step1, step2).penalizesBy(0);
		}

		@Test
		void ascendingDates_highToLowPrio()
		{
			final Step step1 = step(PROJECT_ID1, "2023-04-25", InternalPriority.HIGH);
			final Step step2 = step(PROJECT_ID2, "2023-04-26", InternalPriority.LOW);
			assertThat(PlanConstraintProvider.stepsNotRespectingProjectPriority(step1, step2)).isFalse();
			constraintVerifier.verifyThat(PlanConstraintProvider::stepsNotRespectingProjectPriority).given(step1, step2).penalizesBy(0);
		}

		@Test
		void ascendingDates_lowToHighPrio()
		{
			final Step step1 = step(PROJECT_ID1, "2023-04-25", InternalPriority.LOW);
			final Step step2 = step(PROJECT_ID2, "2023-04-26", InternalPriority.HIGH);
			assertThat(PlanConstraintProvider.stepsNotRespectingProjectPriority(step1, step2)).isTrue();
			constraintVerifier.verifyThat(PlanConstraintProvider::stepsNotRespectingProjectPriority).given(step1, step2).penalizesBy(1);
		}

	}
}