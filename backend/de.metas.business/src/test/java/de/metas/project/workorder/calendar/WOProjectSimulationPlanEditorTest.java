package de.metas.project.workorder.calendar;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.google.common.collect.ImmutableList;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.project.workorder.project.WOProject;
import de.metas.project.workorder.resource.ResourceIdAndType;
import de.metas.project.workorder.resource.WOProjectResource;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.resource.WOProjectResources;
import de.metas.project.workorder.step.WOProjectStep;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.project.workorder.step.WOProjectSteps;
import de.metas.workflow.WFDurationUnit;
import org.adempiere.service.ClientId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@ExtendWith(SnapshotExtension.class)
class WOProjectSimulationPlanEditorTest
{
	public static final Instant refInstant = LocalDate.parse("2022-06-01").atStartOfDay(ZoneId.of("Europe/Berlin")).toInstant();
	@SuppressWarnings("unused") private Expect expect;

	private static Instant instant(final int day)
	{
		return refInstant.plus(day - 1, ChronoUnit.DAYS);
	}

	private static CalendarDateRange allDay(final int startDay, final int endDay)
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
						.orgId(OrgId.ANY)
						.clientId(ClientId.METASFRESH)
						.projectId(ProjectId.ofRepoId(1))
						.currencyId(CurrencyId.ofRepoId(11))
						.name("work order 1")
						.value("value")
						.projectTypeId(ProjectTypeId.ofRepoId(11))
						.isActive(true)
						.build())
				.steps(WOProjectSteps.builder()
						.projectId(ProjectId.ofRepoId(1))
						.steps(ImmutableList.of(
								WOProjectStep.builder()
										.woProjectStepId(WOProjectStepId.ofRepoId(1, 1))
										.name("step 1")
										.seqNo(10)
										.dateRange(allDay(1, 5))
										.orgId(OrgId.ANY)
										.build(),
								WOProjectStep.builder()
										.woProjectStepId(WOProjectStepId.ofRepoId(1, 2))
										.name("step 2")
										.seqNo(20)
										.dateRange(allDay(6, 9))
										.orgId(OrgId.ANY)
										.build()
						))
						.build())
				.projectResources(WOProjectResources.builder()
						.projectId(ProjectId.ofRepoId(1))
						.resources(ImmutableList.of(
								WOProjectResource.builder()
										.woProjectResourceId(WOProjectResourceId.ofRepoId(1, 1))
										.woProjectStepId(WOProjectStepId.ofRepoId(1, 1))
										.resourceIdAndType(ResourceIdAndType.machine(ResourceId.ofRepoId(1)))
										.dateRange(allDay(1, 3))
										.orgId(OrgId.ANY)
										.durationUnit(WFDurationUnit.ofTemporalUnit(ChronoUnit.HOURS))
										.duration(allDay(1, 3).getDuration())
										.build(),
								WOProjectResource.builder()
										.woProjectResourceId(WOProjectResourceId.ofRepoId(1, 2))
										.woProjectStepId(WOProjectStepId.ofRepoId(1, 1))
										.resourceIdAndType(ResourceIdAndType.machine(ResourceId.ofRepoId(2)))
										.dateRange(allDay(4, 5))
										.orgId(OrgId.ANY)
										.durationUnit(WFDurationUnit.ofTemporalUnit(ChronoUnit.HOURS))
										.duration(allDay(4, 5).getDuration())
										.build(),
								WOProjectResource.builder()
										.woProjectResourceId(WOProjectResourceId.ofRepoId(1, 3))
										.woProjectStepId(WOProjectStepId.ofRepoId(1, 2))
										.resourceIdAndType(ResourceIdAndType.machine(ResourceId.ofRepoId(1)))
										.dateRange(allDay(6, 8))
										.orgId(OrgId.ANY)
										.durationUnit(WFDurationUnit.ofTemporalUnit(ChronoUnit.HOURS))
										.duration(allDay(6, 8).getDuration())
										.build(),
								WOProjectResource.builder()
										.woProjectResourceId(WOProjectResourceId.ofRepoId(1, 4))
										.woProjectStepId(WOProjectStepId.ofRepoId(1, 2))
										.resourceIdAndType(ResourceIdAndType.machine(ResourceId.ofRepoId(2)))
										.dateRange(allDay(8, 9))
										.orgId(OrgId.ANY)
										.durationUnit(WFDurationUnit.ofTemporalUnit(ChronoUnit.HOURS))
										.duration(allDay(8, 9).getDuration())
										.build()
						))
						.build())
				.currentSimulationPlan(WOProjectSimulationPlan.builder()
						.simulationPlanId(SimulationPlanId.ofRepoId(1))
						.build())
				.build();

		planEditor.changeResourceDateRangeAndShiftSteps(
				WOProjectResourceId.ofRepoId(1, 1),
				allDay(11, 13),
				WOProjectStepId.ofRepoId(1, 1));

		final WOProjectSimulationPlan newSimulationPlan = planEditor.toNewSimulationPlan();
		System.out.println(newSimulationPlan);
		expect.serializer("orderedJson").toMatchSnapshot(newSimulationPlan);

	}
}