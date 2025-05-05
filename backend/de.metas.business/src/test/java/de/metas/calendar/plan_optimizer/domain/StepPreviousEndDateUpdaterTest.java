package de.metas.calendar.plan_optimizer.domain;

import de.metas.product.ResourceId;
import de.metas.project.InternalPriority;
import de.metas.project.ProjectId;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.step.WOProjectStepId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class StepPreviousEndDateUpdaterTest
{
	private static final ProjectId PROJECT_ID = ProjectId.ofRepoId(1);
	private static final Resource RESOURCE = Resource.builder().id(ResourceId.ofRepoId(1)).name("R1").build();

	private AtomicInteger nextProjectStepRepoId;

	private final LocalDateTime START_DATE_MIN = LocalDate.parse("2023-10-01").atStartOfDay();

	@BeforeEach
	void beforeEach()
	{
		nextProjectStepRepoId = new AtomicInteger(1);
	}

	StepId nextStepId()
	{
		final int stepRepoId = nextProjectStepRepoId.getAndIncrement();
		return StepId.builder()
				.woProjectStepId(WOProjectStepId.ofRepoId(PROJECT_ID, stepRepoId))
				.machineWOProjectResourceId(WOProjectResourceId.ofRepoId(PROJECT_ID, stepRepoId))
				.build();
	}

	StepDef.StepDefBuilder step(int duration)
	{
		return StepDef.builder()
				.id(nextStepId())
				.projectPriority(InternalPriority.MEDIUM)
				.resource(RESOURCE)
				.requiredResourceCapacity(Duration.of(duration, Plan.PLANNING_TIME_PRECISION))
				.requiredHumanCapacity(Duration.ZERO)
				.startDateMin(START_DATE_MIN)
				.dueDate(LocalDate.parse("2024-12-31").atStartOfDay()) // some not null value because we don't want to fail toString()
				;
	}

	@Test
	void updateStep()
	{
		final List<StepAllocation> allocations = Project.builder()
				.step(step(1).startDateMin(LocalDateTime.parse("2023-09-01T00:00")).build())
				.step(step(2).build())
				.build()
				.createAllocations();
		final StepAllocation step1 = allocations.get(0);
		final StepAllocation step2 = allocations.get(1);

		step1.setDelay(0);
		step2.setDelay(5);

		StepPreviousEndDateUpdater.updateStep(step1);
		assertThat(step1.getStartDate()).isEqualTo("2023-09-01T00:00");
		assertThat(step1.getEndDate()).isEqualTo("2023-09-01T01:00");
		assertThat(step2.getStartDate()).isEqualTo("2023-09-01T06:00");
		assertThat(step2.getEndDate()).isEqualTo("2023-09-01T08:00");
	}

}