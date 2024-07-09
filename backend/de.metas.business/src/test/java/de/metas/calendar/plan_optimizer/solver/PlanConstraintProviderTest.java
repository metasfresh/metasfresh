package de.metas.calendar.plan_optimizer.solver;

import ai.timefold.solver.test.api.score.stream.ConstraintVerifier;
import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.plan_optimizer.domain.Project;
import de.metas.calendar.plan_optimizer.domain.Resource;
import de.metas.calendar.plan_optimizer.domain.StepAllocation;
import de.metas.calendar.plan_optimizer.domain.StepDef;
import de.metas.calendar.plan_optimizer.domain.StepId;
import de.metas.product.ResourceId;
import de.metas.project.InternalPriority;
import de.metas.project.ProjectId;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.resource.DatabaseHumanResourceTestGroupRepository;
import de.metas.resource.HumanResourceTestGroupService;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class PlanConstraintProviderTest
{
	private static final ProjectId PROJECT_ID1 = ProjectId.ofRepoId(1);
	private static final ProjectId PROJECT_ID2 = ProjectId.ofRepoId(2);
	private static final Resource RESOURCE = Resource.builder().id(ResourceId.ofRepoId(1)).name("R1").build();
	private static final Resource RESOURCE2 = Resource.builder().id(ResourceId.ofRepoId(2)).name("R2").build();

	private static ConstraintVerifier<PlanConstraintProvider, Plan> constraintVerifier;

	private AtomicInteger nextProjectStepRepoId;

	@BeforeAll
	static void beforeAll()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new HumanResourceTestGroupService(new DatabaseHumanResourceTestGroupRepository()));
		constraintVerifier = ConstraintVerifier.build(new PlanConstraintProvider(), Plan.class, StepAllocation.class);
	}

	@BeforeEach
	void beforeEach()
	{
		this.nextProjectStepRepoId = new AtomicInteger(101);
	}

	StepId nextStepId(final ProjectId projectId)
	{
		final int stepRepoId = nextProjectStepRepoId.getAndIncrement();
		return StepId.builder()
				.woProjectStepId(WOProjectStepId.ofRepoId(projectId, stepRepoId))
				.machineWOProjectResourceId(WOProjectResourceId.ofRepoId(projectId, stepRepoId))
				.build();
	}

	@Nested
	class resourceConflict
	{
		LocalDateTime START_DATE_MIN = LocalDate.parse("2023-04-01").atStartOfDay();

		StepAllocation step(final ProjectId projectId, final Resource resource, final String startDateStr, final String endDateStr)
		{
			final LocalDateTime startDate = LocalDate.parse(startDateStr).atStartOfDay();
			final LocalDateTime endDate = LocalDate.parse(endDateStr).atStartOfDay();

			final StepAllocation allocation = Project.builder()
					.step(StepDef.builder()
							.id(nextStepId(projectId))
							.projectPriority(InternalPriority.MEDIUM)
							.resource(resource)
							.requiredResourceCapacity(Duration.between(startDate, endDate))
							.requiredHumanCapacity(Duration.ZERO)
							.startDateMin(START_DATE_MIN)
							.dueDate(endDate) // some not null value because we don't want to fail toString()
							.initialStartDate(startDate)
							.initialEndDate(endDate)
							.build())
					.build()
					.createAllocations()
					.get(0);

			assertThat(allocation.getResourceScheduledStartDate()).isEqualTo(startDate);
			assertThat(allocation.getResourceScheduledEndDate()).isEqualTo(endDate);

			return allocation;
		}

		@Test
		void sameResource_overlapping()
		{
			final StepAllocation step1 = step(PROJECT_ID1, RESOURCE, "2023-04-01", "2023-04-05");
			final StepAllocation step2 = step(PROJECT_ID2, RESOURCE, "2023-04-04", "2023-04-10");
			constraintVerifier.verifyThat(PlanConstraintProvider::resourceConflict).given(step1, step2).penalizesBy(24); // 24h=1day
		}

		@Test
		void sameResource_connected()
		{
			constraintVerifier.verifyThat(PlanConstraintProvider::resourceConflict)
					.given(
							step(PROJECT_ID1, RESOURCE, "2023-04-01", "2023-04-05"),
							step(PROJECT_ID2, RESOURCE, "2023-04-05", "2023-04-10")
					)
					.penalizesBy(0);
		}

		@Test
		void sameResource_notOverlapping()
		{
			constraintVerifier.verifyThat(PlanConstraintProvider::resourceConflict)
					.given(
							step(PROJECT_ID1, RESOURCE, "2023-04-01", "2023-04-05"),
							step(PROJECT_ID2, RESOURCE, "2023-04-06", "2023-04-10")
					)
					.penalizesBy(0);
		}

		@Test
		void differentResources_overlapping()
		{
			constraintVerifier.verifyThat(PlanConstraintProvider::resourceConflict)
					.given(
							step(PROJECT_ID1, RESOURCE, "2023-04-01", "2023-04-05"),
							step(PROJECT_ID2, RESOURCE2, "2023-04-04", "2023-04-10")
					)
					.penalizesBy(0);
		}
	}

	@Nested
	class dueDate
	{
		@SuppressWarnings("SameParameterValue")
		StepAllocation step(final String endDateStr, final String dueDate)
		{
			final LocalDateTime endDate = LocalDate.parse(endDateStr).atStartOfDay();
			final Duration duration = Duration.of(1, Plan.PLANNING_TIME_PRECISION);
			final LocalDateTime startDate = endDate.minus(duration);

			return Project.builder()
					.step(StepDef.builder()
							.id(nextStepId(PROJECT_ID1))
							.projectPriority(InternalPriority.MEDIUM)
							.resource(RESOURCE)
							.requiredResourceCapacity(duration)
							.requiredHumanCapacity(Duration.ZERO)
							.startDateMin(startDate)
							.dueDate(LocalDate.parse(dueDate).atStartOfDay())
							.initialStartDate(startDate)
							.initialEndDate(endDate)
							.build())
					.build()
					.createAllocations()
					.get(0);
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
			assertThat(step("2023-04-27", "2023-04-26").isDueDateNotRespected()).isTrue();

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

		StepAllocation step(final ProjectId projectId, final String startDateStr, InternalPriority priority)
		{
			final LocalDateTime startDate = LocalDate.parse(startDateStr).atStartOfDay();
			final Duration duration = Duration.of(1, Plan.PLANNING_TIME_PRECISION);

			return Project.builder()
					.step(StepDef.builder()
							.id(nextStepId(projectId)) // needed for constraint stream forEachUnique
							.projectPriority(priority)
							.resource(RESOURCE)
							.requiredResourceCapacity(duration)
							.requiredHumanCapacity(Duration.ZERO)
							.startDateMin(START_DATE_MIN)
							.dueDate(LocalDate.parse("2024-12-31").atStartOfDay()) // some not null value because we don't want to fail toString()
							.initialStartDate(startDate)
							.initialEndDate(startDate.plus(duration))
							.build())
					.build()
					.createAllocations()
					.get(0);
		}

		@Test
		void sameStep()
		{
			final StepAllocation step1 = step(PROJECT_ID1, "2023-04-25", InternalPriority.MEDIUM);
			final StepAllocation step2 = step(PROJECT_ID2, "2023-04-25", InternalPriority.MEDIUM);
			assertThat(PlanConstraintProvider.stepsNotRespectingProjectPriority(step1, step2)).isFalse();
			constraintVerifier.verifyThat(PlanConstraintProvider::stepsNotRespectingProjectPriority).given(step1, step2).penalizesBy(0);
		}

		@Test
		void descendingDates_samePrio()
		{
			final StepAllocation step1 = step(PROJECT_ID1, "2023-04-25", InternalPriority.MEDIUM);
			final StepAllocation step2 = step(PROJECT_ID2, "2023-04-26", InternalPriority.MEDIUM);
			assertThat(PlanConstraintProvider.stepsNotRespectingProjectPriority(step1, step2)).isFalse();
			constraintVerifier.verifyThat(PlanConstraintProvider::stepsNotRespectingProjectPriority).given(step1, step2).penalizesBy(0);
		}

		@Test
		void sameDates_highToLowPrio()
		{
			final StepAllocation step1 = step(PROJECT_ID1, "2023-04-25", InternalPriority.HIGH);
			final StepAllocation step2 = step(PROJECT_ID2, "2023-04-25", InternalPriority.LOW);
			assertThat(PlanConstraintProvider.stepsNotRespectingProjectPriority(step1, step2)).isTrue();
			constraintVerifier.verifyThat(PlanConstraintProvider::stepsNotRespectingProjectPriority).given(step1, step2).penalizesBy(1);
		}

		@Test
		void sameDates_lowToHighPrio()
		{
			final StepAllocation step1 = step(PROJECT_ID1, "2023-04-25", InternalPriority.LOW);
			final StepAllocation step2 = step(PROJECT_ID2, "2023-04-25", InternalPriority.HIGH);
			assertThat(PlanConstraintProvider.stepsNotRespectingProjectPriority(step1, step2)).isTrue();
			constraintVerifier.verifyThat(PlanConstraintProvider::stepsNotRespectingProjectPriority).given(step1, step2).penalizesBy(1);
		}

		@Test
		void ascendingDates_samePrio()
		{
			final StepAllocation step1 = step(PROJECT_ID1, "2023-04-25", InternalPriority.MEDIUM);
			final StepAllocation step2 = step(PROJECT_ID2, "2023-04-26", InternalPriority.MEDIUM);
			assertThat(PlanConstraintProvider.stepsNotRespectingProjectPriority(step1, step2)).isFalse();
			constraintVerifier.verifyThat(PlanConstraintProvider::stepsNotRespectingProjectPriority).given(step1, step2).penalizesBy(0);
		}

		@Test
		void ascendingDates_highToLowPrio()
		{
			final StepAllocation step1 = step(PROJECT_ID1, "2023-04-25", InternalPriority.HIGH);
			final StepAllocation step2 = step(PROJECT_ID2, "2023-04-26", InternalPriority.LOW);
			assertThat(PlanConstraintProvider.stepsNotRespectingProjectPriority(step1, step2)).isFalse();
			constraintVerifier.verifyThat(PlanConstraintProvider::stepsNotRespectingProjectPriority).given(step1, step2).penalizesBy(0);
		}

		@Test
		void ascendingDates_lowToHighPrio()
		{
			final StepAllocation step1 = step(PROJECT_ID1, "2023-04-25", InternalPriority.LOW);
			final StepAllocation step2 = step(PROJECT_ID2, "2023-04-26", InternalPriority.HIGH);
			assertThat(PlanConstraintProvider.stepsNotRespectingProjectPriority(step1, step2)).isTrue();
			constraintVerifier.verifyThat(PlanConstraintProvider::stepsNotRespectingProjectPriority).given(step1, step2).penalizesBy(25);
		}
	}
}