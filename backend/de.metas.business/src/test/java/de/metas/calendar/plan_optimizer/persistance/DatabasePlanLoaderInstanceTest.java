package de.metas.calendar.plan_optimizer.persistance;

import de.metas.business.BusinessTestHelper;
import de.metas.calendar.conflicts.CalendarConflictEventsDispatcher;
import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.plan_optimizer.domain.StepAllocation;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.simulation.SimulationPlanRepository;
import de.metas.common.util.time.SystemTime;
import de.metas.organization.IOrgDAO;
import de.metas.product.ResourceId;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeRepository;
import de.metas.project.status.RStatusRepository;
import de.metas.project.status.RStatusService;
import de.metas.project.workorder.calendar.WOProjectSimulationRepository;
import de.metas.project.workorder.calendar.WOProjectSimulationService;
import de.metas.project.workorder.conflicts.WOProjectConflictService;
import de.metas.project.workorder.conflicts.WOProjectResourceConflictRepository;
import de.metas.project.workorder.project.WOProjectRepository;
import de.metas.project.workorder.project.WOProjectService;
import de.metas.project.workorder.resource.WOProjectResourceRepository;
import de.metas.project.workorder.step.WOProjectStepRepository;
import de.metas.resource.DatabaseHumanResourceTestGroupRepository;
import de.metas.resource.HumanResourceTestGroupService;
import de.metas.resource.ResourceService;
import de.metas.resource.ResourceTypeId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import de.metas.workflow.WFDurationUnit;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_Project_WO_Resource;
import org.compiere.model.I_C_Project_WO_Step;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_S_Resource;
import org.compiere.model.I_S_ResourceType;
import org.compiere.model.X_C_UOM;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;

import static shadow.org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
class DatabasePlanLoaderInstanceTest
{
	private static final ZoneId TIME_ZONE = ZoneId.of("Europe/Berlin");
	private static final SimulationPlanId simulationId = SimulationPlanId.ofRepoId(111);

	private I_C_UOM uomHour;
	private ResourceTypeId resourceTypeId;
	private final HashMap<String, ResourceId> resourceIdsByName = new HashMap<>();

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);
		SystemTime.setFixedTimeSource(LocalDate.parse("2023-10-01").atStartOfDay().atZone(TIME_ZONE));

		this.uomHour = BusinessTestHelper.createUOM(X12DE355.HOUR.getCode(), X_C_UOM.UOMTYPE_Time, 0);

		this.resourceTypeId = createResourceType();
	}

	private ResourceTypeId createResourceType()
	{
		final I_S_ResourceType record = InterfaceWrapperHelper.newInstance(I_S_ResourceType.class);
		record.setValue("resource type");
		record.setName("resource type");
		record.setC_UOM_ID(uomHour.getC_UOM_ID());
		record.setM_Product_Category_ID(1);
		InterfaceWrapperHelper.saveRecord(record);
		return ResourceTypeId.ofRepoId(record.getS_ResourceType_ID());
	}

	private ResourceId resource(@NonNull final String name)
	{
		return resourceIdsByName.computeIfAbsent(name, k -> {
			final I_S_Resource record = InterfaceWrapperHelper.newInstance(I_S_Resource.class);
			record.setValue(name);
			record.setName(name);
			record.setS_ResourceType_ID(resourceTypeId.getRepoId());
			InterfaceWrapperHelper.saveRecord(record);
			return ResourceId.ofRepoId(record.getS_Resource_ID());
		});
	}

	private static Timestamp parseTimestamp(@Nullable final String date)
	{
		if (date == null)
		{
			return null;
		}

		final Instant instant = LocalDateTime.parse(date).atZone(TIME_ZONE).toInstant();
		return Timestamp.from(instant);
	}

	@Builder(builderMethodName = "projectRecord", builderClassName = "$ProjectRecordBuilder")
	private ProjectId createProjectRecord(
			@NonNull String dateOfProvisionByBPartner,
			@NonNull String dateFinish
	)
	{
		final I_C_Project record = InterfaceWrapperHelper.newInstance(I_C_Project.class);
		record.setProjectCategory(ProjectCategory.WorkOrderJob.getCode());
		record.setName("Project");
		record.setValue("Project");
		record.setC_Currency_ID(1);
		record.setC_ProjectType_ID(1);
		record.setDateOfProvisionByBPartner(parseTimestamp(dateOfProvisionByBPartner));
		record.setDateFinish(parseTimestamp(dateFinish));
		InterfaceWrapperHelper.saveRecord(record);
		return ProjectId.ofRepoId(record.getC_Project_ID());
	}

	@Builder(builderMethodName = "projectResourceRecord", builderClassName = "$ProjectResourceRecordBuilder")
	private void createProjectResourceRecord(
			@NonNull ProjectId projectId,
			int seqNo,
			@NonNull String resource,
			@Nullable String startDate,
			int durationInHours,
			boolean locked
	)
	{
		final Timestamp startDateTS = parseTimestamp(startDate);
		final Timestamp endDateTS = startDate != null ? TimeUtil.addHours(startDateTS, durationInHours) : null;

		final I_C_Project_WO_Step stepRecord = InterfaceWrapperHelper.newInstance(I_C_Project_WO_Step.class);
		stepRecord.setC_Project_ID(projectId.getRepoId());
		stepRecord.setSeqNo(seqNo);
		stepRecord.setName("step-" + seqNo);
		stepRecord.setDateStart(startDateTS);
		stepRecord.setDateEnd(endDateTS);
		stepRecord.setIsManuallyLocked(locked);
		InterfaceWrapperHelper.saveRecord(stepRecord);

		final I_C_Project_WO_Resource resourceRecord = InterfaceWrapperHelper.newInstance(I_C_Project_WO_Resource.class);
		resourceRecord.setC_Project_ID(projectId.getRepoId());
		resourceRecord.setC_Project_WO_Step_ID(stepRecord.getC_Project_WO_Step_ID());
		resourceRecord.setS_Resource_ID(resource(resource).getRepoId());
		resourceRecord.setAssignDateFrom(startDateTS);
		resourceRecord.setAssignDateTo(endDateTS);
		resourceRecord.setIsAllDay(false);
		resourceRecord.setDurationUnit(WFDurationUnit.Hour.getCode());
		resourceRecord.setDuration(BigDecimal.valueOf(durationInHours));

		InterfaceWrapperHelper.save(resourceRecord);
	}

	private static DatabasePlanLoaderInstance newDatabasePlanLoaderInstance()
	{
		final WOProjectRepository woProjectRepository = new WOProjectRepository();
		final WOProjectResourceRepository woProjectResourceRepository = new WOProjectResourceRepository();
		final WOProjectService woProjectService = new WOProjectService(
				woProjectRepository,
				woProjectResourceRepository,
				new WOProjectStepRepository(),
				new RStatusService(new RStatusRepository()),
				new ProjectTypeRepository()
		);

		final WOProjectSimulationRepository woProjectSimulationRepository = new WOProjectSimulationRepository();
		final WOProjectSimulationService woProjectSimulationService = new WOProjectSimulationService(
				woProjectService,
				woProjectSimulationRepository,
				new WOProjectConflictService(
						new SimulationPlanRepository(),
						new WOProjectResourceConflictRepository(),
						woProjectSimulationRepository,
						woProjectRepository,
						woProjectResourceRepository,
						new CalendarConflictEventsDispatcher()
				)
		);

		return DatabasePlanLoaderInstance.builder()
				.orgDAO(Services.get(IOrgDAO.class))
				.woProjectService(woProjectService)
				.woProjectSimulationService(woProjectSimulationService)
				.resourceService(ResourceService.newInstanceForJUnitTesting())
				.humanResourceTestGroupService(new HumanResourceTestGroupService(new DatabaseHumanResourceTestGroupRepository()))
				.simulationId(simulationId)
				.build();
	}

	@Test
	void test()
	{
		final ProjectId projectId = projectRecord().dateOfProvisionByBPartner("2023-10-05T05:00").dateFinish("2023-10-31T23:59").build();
		projectResourceRecord().projectId(projectId).seqNo(10).resource("R1").startDate("2023-10-05T10:00").durationInHours(1).build();
		projectResourceRecord().projectId(projectId).seqNo(20).resource("R2").startDate("2023-10-05T13:00").durationInHours(1).build();
		projectResourceRecord().projectId(projectId).seqNo(30).resource("R3").startDate("2023-10-05T22:00").durationInHours(1).locked(true).build();

		final DatabasePlanLoaderInstance loader = newDatabasePlanLoaderInstance();
		final Plan plan = loader.load();
		System.out.println(plan);

		final ArrayList<StepAllocation> steps = plan.getStepsList();
		assertThat(steps).hasSize(3);

		assertThat(steps.get(0).getStartDate()).isEqualTo("2023-10-05T10:00");
		assertThat(steps.get(0).getEndDate()).isEqualTo("2023-10-05T11:00");
		assertThat(steps.get(0).getStepDef().getPinnedStartDate()).isNull();
		assertThat(steps.get(0).getPrevious()).isNull();
		assertThat(steps.get(0).getNext()).isSameAs(steps.get(1));

		assertThat(steps.get(1).getStartDate()).isEqualTo("2023-10-05T13:00");
		assertThat(steps.get(1).getEndDate()).isEqualTo("2023-10-05T14:00");
		assertThat(steps.get(1).getStepDef().getPinnedStartDate()).isNull();
		assertThat(steps.get(1).getPrevious()).isSameAs(steps.get(0));
		assertThat(steps.get(1).getNext()).isSameAs(steps.get(2));

		assertThat(steps.get(2).getStartDate()).isEqualTo("2023-10-05T22:00");
		assertThat(steps.get(2).getEndDate()).isEqualTo("2023-10-05T23:00");
		assertThat(steps.get(2).getStepDef().getPinnedStartDate()).isEqualTo(steps.get(2).getStartDate());
		assertThat(steps.get(2).getPrevious()).isSameAs(steps.get(1));
		assertThat(steps.get(2).getNext()).isNull();

	}
}