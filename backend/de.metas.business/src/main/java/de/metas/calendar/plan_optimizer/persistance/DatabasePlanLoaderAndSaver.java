package de.metas.calendar.plan_optimizer.persistance;

import com.google.common.collect.ImmutableSet;
import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.plan_optimizer.domain.StepAllocation;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.project.workorder.calendar.WOProjectSimulationPlan;
import de.metas.project.workorder.calendar.WOProjectSimulationPlanEditor;
import de.metas.project.workorder.calendar.WOProjectSimulationService;
import de.metas.project.workorder.conflicts.WOProjectConflictService;
import de.metas.project.workorder.project.WOProject;
import de.metas.project.workorder.project.WOProjectService;
import de.metas.project.workorder.resource.ResourceIdAndType;
import de.metas.resource.HumanResourceTestGroupService;
import de.metas.resource.ResourceService;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.time.ZoneId;
import java.util.List;
import java.util.Properties;

public class DatabasePlanLoaderAndSaver implements PlanLoaderAndSaver
{
	private static final Logger logger = LogManager.getLogger(DatabasePlanLoaderAndSaver.class);
	private final IOrgDAO orgDAO;
	private final WOProjectService woProjectService;
	private final WOProjectSimulationService woProjectSimulationService;
	private final WOProjectConflictService woProjectConflictService;
	private final ResourceService resourceService;
	private final HumanResourceTestGroupService humanResourceTestGroupService;

	public DatabasePlanLoaderAndSaver(
			@NonNull final IOrgDAO orgDAO,
			@NonNull final WOProjectService woProjectService,
			@NonNull final WOProjectSimulationService woProjectSimulationService,
			@NonNull final WOProjectConflictService woProjectConflictService,
			@NonNull final ResourceService resourceService,
			@NonNull final HumanResourceTestGroupService humanResourceTestGroupService)
	{
		this.orgDAO = orgDAO;
		this.woProjectService = woProjectService;
		this.woProjectSimulationService = woProjectSimulationService;
		this.woProjectConflictService = woProjectConflictService;
		this.resourceService = resourceService;
		this.humanResourceTestGroupService = humanResourceTestGroupService;
	}

	@Override
	public Plan getPlan(@NonNull final SimulationPlanId simulationId)
	{
		return DatabasePlanLoaderInstance.builder()
				.orgDAO(orgDAO)
				.woProjectService(woProjectService)
				.woProjectSimulationService(woProjectSimulationService)
				.resourceService(resourceService)
				.humanResourceTestGroupService(humanResourceTestGroupService)
				.simulationId(simulationId)
				.build()
				.load();
	}

	@Override
	public void saveSolution(final Plan solution)
	{
		final SimulationPlanId simulationId = solution.getSimulationId();
		final ZoneId timeZone = solution.getTimeZone();

		final ImmutableSet<ProjectId> projectIds = solution.getStepsList()
				.stream()
				.map(StepAllocation::getProjectId)
				.collect(ImmutableSet.toImmutableSet());
		if (projectIds.isEmpty())
		{
			return;
		}
		final List<WOProject> projects = woProjectService.getAllActiveProjects(projectIds);
		final ClientId clientId = CollectionUtils.extractSingleElement(projects, WOProject::getClientId);
		final OrgId orgId = CollectionUtils.extractSingleElementOrDefault(projects, WOProject::getOrgId, OrgId.ANY);

		final WOProjectSimulationPlanEditor editor = WOProjectSimulationPlanEditor.builder()
				.projects(projects)
				.stepsCollection(woProjectService.getStepsByProjectIds(projectIds).toCollection())
				.projectResourcesCollection(woProjectService.getResourcesByProjectIds(projectIds).toCollection())
				.currentSimulationPlan(woProjectSimulationService.getSimulationPlanById(simulationId))
				.build();

		for (final StepAllocation step : solution.getStepsList())
		{
			if (step.getStartDate() == null || step.getEndDate() == null)
			{
				logger.warn("Skip updating from {} because start/end date is not set", step);
				continue;
			}

			if (step.getResourceScheduledRange() != null)
			{
				editor.changeResourceDateRange(
						step.getId().getMachineWOProjectResourceId(),
						step.getResourceScheduledRange().toCalendarDateRange(timeZone),
						step.getId().getWoProjectStepId());
			}
			if (step.getHumanResourceScheduledRange() != null)
			{
				editor.changeResourceDateRange(
						step.getId().getHumanWOProjectResourceId(),
						step.getHumanResourceScheduledRange().toCalendarDateRange(timeZone),
						step.getId().getWoProjectStepId());
			}
		}

		runInCtx(clientId, orgId, () -> {
			final WOProjectSimulationPlan newSimulationPlan = editor.toNewSimulationPlan();
			woProjectSimulationService.savePlan(newSimulationPlan);

			final ImmutableSet<ResourceIdAndType> affectedResourceIds = editor.getAffectedResourceIds();
			if (!affectedResourceIds.isEmpty())
			{
				woProjectConflictService.checkSimulationConflicts(newSimulationPlan, affectedResourceIds);
			}
		});
	}

	private static void runInCtx(ClientId clientId, OrgId orgId, Runnable runnable)
	{
		final Properties ctx = Env.newTemporaryCtx();
		Env.setClientId(ctx, clientId);
		Env.setOrgId(ctx, orgId);
		try (final IAutoCloseable ignored = Env.switchContext(ctx))
		{
			runnable.run();
		}
	}
}
