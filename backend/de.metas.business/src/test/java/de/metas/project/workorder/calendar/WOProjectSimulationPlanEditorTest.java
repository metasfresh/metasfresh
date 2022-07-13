package de.metas.project.workorder.calendar;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.workorder.WOProject;
import de.metas.project.workorder.WOProjectAndResourceId;
import de.metas.project.workorder.WOProjectResource;
import de.metas.project.workorder.WOProjectResourceId;
import de.metas.project.workorder.WOProjectResources;
import de.metas.project.workorder.WOProjectStep;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.project.workorder.WOProjectSteps;
import de.metas.test.SnapshotFunctionFactory;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;

class WOProjectSimulationPlanEditorTest
{
	public static final Instant refInstant = LocalDate.parse("2022-06-01").atStartOfDay(ZoneId.of("Europe/Berlin")).toInstant();

	@BeforeAll
	static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG, SnapshotFunctionFactory.newFunction());
	}

	@AfterAll
	static void afterAll()
	{
		validateSnapshots();
	}

	private static Instant instant(int day) {return refInstant.plus(day - 1, ChronoUnit.DAYS);}

	private static CalendarDateRange allDay(int startDay, int endDay)
	{
		return CalendarDateRange.builder()
				.startDate(instant(startDay))
				.endDate(instant(endDay))
				.allDay(true)
				.build();
	}

	@Test
	void test()
	{
		final WOProjectSimulationPlanEditor planEditor = WOProjectSimulationPlanEditor.builder()
				.project(WOProject.builder()
						.projectId(ProjectId.ofRepoId(1))
						.name("work order 1")
						.build())
				.steps(WOProjectSteps.builder()
						.projectId(ProjectId.ofRepoId(1))
						.steps(ImmutableList.of(
								WOProjectStep.builder()
										.id(WOProjectStepId.ofRepoId(1))
										.projectId(ProjectId.ofRepoId(1))
										.seqNo(10)
										.name("step 1")
										.dateRange(allDay(1, 5))
										.build(),
								WOProjectStep.builder()
										.id(WOProjectStepId.ofRepoId(2))
										.projectId(ProjectId.ofRepoId(1))
										.seqNo(20)
										.name("step 2")
										.dateRange(allDay(6, 9))
										.build()
						))
						.build())
				.projectResources(WOProjectResources.builder()
						.projectId(ProjectId.ofRepoId(1))
						.resources(ImmutableList.of(
								WOProjectResource.builder()
										.id(WOProjectResourceId.ofRepoId(1))
										.projectId(ProjectId.ofRepoId(1))
										.stepId(WOProjectStepId.ofRepoId(1))
										.resourceId(ResourceId.ofRepoId(1))
										.dateRange(allDay(1, 3))
										.durationUnit(ChronoUnit.HOURS)
										.duration(allDay(1, 3).getDuration())
										.build(),
								WOProjectResource.builder()
										.id(WOProjectResourceId.ofRepoId(2))
										.projectId(ProjectId.ofRepoId(1))
										.stepId(WOProjectStepId.ofRepoId(1))
										.resourceId(ResourceId.ofRepoId(2))
										.dateRange(allDay(4, 5))
										.durationUnit(ChronoUnit.HOURS)
										.duration(allDay(4, 5).getDuration())
										.build(),
								WOProjectResource.builder()
										.id(WOProjectResourceId.ofRepoId(3))
										.projectId(ProjectId.ofRepoId(1))
										.stepId(WOProjectStepId.ofRepoId(2))
										.resourceId(ResourceId.ofRepoId(1))
										.dateRange(allDay(6, 8))
										.durationUnit(ChronoUnit.HOURS)
										.duration(allDay(6, 8).getDuration())
										.build(),
								WOProjectResource.builder()
										.id(WOProjectResourceId.ofRepoId(4))
										.projectId(ProjectId.ofRepoId(1))
										.stepId(WOProjectStepId.ofRepoId(2))
										.resourceId(ResourceId.ofRepoId(2))
										.dateRange(allDay(8, 9))
										.durationUnit(ChronoUnit.HOURS)
										.duration(allDay(8, 9).getDuration())
										.build()
						))
						.build())
				.currentSimulationPlan(WOProjectSimulationPlan.builder()
						.simulationPlanId(SimulationPlanId.ofRepoId(1))
						.build())
				.build();

		planEditor.changeResourceDateRangeAndShiftSteps(
				WOProjectAndResourceId.of(ProjectId.ofRepoId(1), WOProjectResourceId.ofRepoId(1)),
				allDay(11, 13),
				WOProjectStepId.ofRepoId(1));

		final WOProjectSimulationPlan newSimulationPlan = planEditor.toNewSimulationPlan();
		System.out.println(newSimulationPlan);
		expect(newSimulationPlan).toMatchSnapshot();

	}
}