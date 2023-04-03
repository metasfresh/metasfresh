package de.metas.project.workorder.calendar;

import de.metas.calendar.conflicts.CalendarConflictEventsDispatcher;
import de.metas.calendar.simulation.SimulationPlanRepository;
import de.metas.calendar.simulation.SimulationPlanService;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectId;
import de.metas.project.ProjectType;
import de.metas.project.ProjectTypeRepository;
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectRepository;
import de.metas.project.budget.BudgetProjectResourceRepository;
import de.metas.project.budget.BudgetProjectService;
import de.metas.project.budget.BudgetProjectSimulationRepository;
import de.metas.project.budget.BudgetProjectSimulationService;
import de.metas.project.budget.CreateBudgetProjectRequest;
import de.metas.project.service.PlainProjectRepository;
import de.metas.project.status.RStatusRepository;
import de.metas.project.status.RStatusService;
import de.metas.project.workorder.conflicts.WOProjectConflictService;
import de.metas.project.workorder.conflicts.WOProjectResourceConflictRepository;
import de.metas.project.workorder.project.CreateWOProjectRequest;
import de.metas.project.workorder.project.WOProject;
import de.metas.project.workorder.project.WOProjectRepository;
import de.metas.project.workorder.project.WOProjectService;
import de.metas.project.workorder.resource.WOProjectResourceRepository;
import de.metas.project.workorder.step.WOProjectStepRepository;
import de.metas.resource.ResourceService;
import de.metas.util.InSetPredicate;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_ProjectType;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class WOProjectCalendarServiceTest
{
	private WOProjectCalendarService woProjectCalendarService;
	private BudgetProjectRepository budgetProjectRepository;
	private WOProjectRepository woProjectRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);

		setupServices();
	}

	private void setupServices()
	{
		final ResourceService resourceService = ResourceService.newInstanceForJUnitTesting();
		this.budgetProjectRepository = new BudgetProjectRepository();
		this.woProjectRepository = new WOProjectRepository();
		final WOProjectResourceRepository woProjectResourceRepository = new WOProjectResourceRepository();
		final RStatusRepository statusRepository = new RStatusRepository();
		final WOProjectService woProjectService = new WOProjectService(woProjectRepository, woProjectResourceRepository, new WOProjectStepRepository(), new RStatusService(statusRepository), new ProjectTypeRepository());
		final BudgetProjectService budgetProjectService = new BudgetProjectService(resourceService, budgetProjectRepository, new BudgetProjectResourceRepository());
		final WOProjectSimulationRepository woProjectSimulationRepository = new WOProjectSimulationRepository();
		final SimulationPlanRepository simulationPlanRepository = new SimulationPlanRepository();
		final WOProjectConflictService woProjectConflictService = new WOProjectConflictService(
				simulationPlanRepository,
				new WOProjectResourceConflictRepository(),
				woProjectSimulationRepository,
				woProjectRepository,
				woProjectResourceRepository,
				new CalendarConflictEventsDispatcher()
		);
		this.woProjectCalendarService = new WOProjectCalendarService(
				resourceService,
				new SimulationPlanService(simulationPlanRepository, Optional.empty()),
				new PlainProjectRepository(),
				woProjectService,
				budgetProjectService,
				new BudgetProjectSimulationService(budgetProjectService, new BudgetProjectSimulationRepository()),
				new WOProjectSimulationService(woProjectService, woProjectSimulationRepository, woProjectConflictService),
				woProjectConflictService
		);
	}

	private ProjectType projectType(@NonNull final ProjectCategory projectCategory)
	{
		final I_C_ProjectType projectTypeRecord = InterfaceWrapperHelper.newInstance(I_C_ProjectType.class);
		projectTypeRecord.setProjectCategory(projectCategory.getCode());
		projectTypeRecord.setR_StatusCategory_ID(111);
		InterfaceWrapperHelper.save(projectTypeRecord);

		return ProjectTypeRepository.toProjectType(projectTypeRecord);
	}

	@Nested
	class getProjectIdsPredicate
	{
		@Test
		void no_filters()
		{
			final InSetPredicate<ProjectId> projectIds = woProjectCalendarService.getProjectIdsPredicate(
					null, // onlyProjectId,
					null, // onlyCustomerId,
					null // onlyResponsibleId
			);

			assertThat(projectIds).isEqualTo(InSetPredicate.any());
		}

		@Test
		void only_given_BudgetProject()
		{
			final BudgetProject budgetProject = budgetProjectRepository.create(CreateBudgetProjectRequest.builder()
					.value("test")
					.name("test")
					.orgId(OrgId.MAIN)
					.currencyId(CurrencyId.ofRepoId(102))
					.projectType(projectType(ProjectCategory.Budget))
					.build());

			final InSetPredicate<ProjectId> projectIds = woProjectCalendarService.getProjectIdsPredicate(
					budgetProject.getProjectId(), // onlyProjectId,
					null, // onlyCustomerId,
					null // onlyResponsibleId
			);

			assertThat(projectIds).isEqualTo(InSetPredicate.only(budgetProject.getProjectId()));
		}

		@Test
		void only_given_WOProject()
		{
			final WOProject woProject = woProjectRepository.create(CreateWOProjectRequest.builder()
					.value("test")
					.name("test")
					.orgId(OrgId.MAIN)
					.currencyId(CurrencyId.ofRepoId(102))
					.projectType(projectType(ProjectCategory.WorkOrderJob))
					.build());

			final InSetPredicate<ProjectId> projectIds = woProjectCalendarService.getProjectIdsPredicate(
					woProject.getProjectId(), // onlyProjectId,
					null, // onlyCustomerId,
					null // onlyResponsibleId
			);

			assertThat(projectIds).isEqualTo(InSetPredicate.only(woProject.getProjectId()));
		}
	}
}