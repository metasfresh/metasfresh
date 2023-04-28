package de.metas.calendar.plan_optimizer;

import com.google.common.collect.ImmutableSet;
import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.plan_optimizer.domain.Resource;
import de.metas.calendar.plan_optimizer.domain.Step;
import de.metas.calendar.plan_optimizer.domain.StepId;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.common.util.CoalesceUtil;
import de.metas.i18n.BooleanWithReason;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.project.InternalPriority;
import de.metas.project.ProjectId;
import de.metas.project.workorder.calendar.WOProjectSimulationPlan;
import de.metas.project.workorder.calendar.WOProjectSimulationPlanEditor;
import de.metas.project.workorder.calendar.WOProjectSimulationService;
import de.metas.project.workorder.conflicts.WOProjectConflictService;
import de.metas.project.workorder.project.WOProject;
import de.metas.project.workorder.project.WOProjectService;
import de.metas.project.workorder.resource.WOProjectResource;
import de.metas.project.workorder.resource.WOProjectResourcesCollection;
import de.metas.project.workorder.step.WOProjectStep;
import de.metas.project.workorder.step.WOProjectSteps;
import de.metas.project.workorder.step.WOProjectStepsCollection;
import de.metas.resource.ResourceService;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

class DatabasePlanLoaderAndSaver implements PlanLoaderAndSaver
{
	private static final Logger logger = LogManager.getLogger(DatabasePlanLoaderAndSaver.class);
	private final IOrgDAO orgDAO;
	private final WOProjectService woProjectService;
	private final WOProjectSimulationService woProjectSimulationService;
	private final WOProjectConflictService woProjectConflictService;
	private final ResourceService resourceService;

	DatabasePlanLoaderAndSaver(
			@NonNull final IOrgDAO orgDAO,
			@NonNull final WOProjectService woProjectService,
			@NonNull final WOProjectSimulationService woProjectSimulationService,
			@NonNull final WOProjectConflictService woProjectConflictService,
			@NonNull final ResourceService resourceService)
	{
		this.orgDAO = orgDAO;
		this.woProjectService = woProjectService;
		this.woProjectSimulationService = woProjectSimulationService;
		this.woProjectConflictService = woProjectConflictService;
		this.resourceService = resourceService;
	}

	@Override
	public Plan getPlan(@NonNull final SimulationPlanId simulationId)
	{
		final List<WOProject> projects = woProjectService.getAllActiveProjects();
		if (projects.isEmpty())
		{
			return new Plan();
		}
		final ImmutableSet<ProjectId> projectIds = projects.stream().map(WOProject::getProjectId).collect(ImmutableSet.toImmutableSet());
		final WOProjectStepsCollection stepsByProjectId = woProjectService.getStepsByProjectIds(projectIds);
		final WOProjectResourcesCollection resources = woProjectService.getResourcesByProjectIds(projectIds);
		final ZoneId timeZone = orgDAO.getTimeZone(projects.get(0).getOrgId()); // use the time zone of the first project

		final WOProjectSimulationPlan simulationPlan = woProjectSimulationService.getSimulationPlanById(simulationId);

		final HashMap<ResourceId, Resource> optaPlannerResources = new HashMap<>();

		final ArrayList<Step> optaPlannerSteps = new ArrayList<>(resources.size());
		for (final WOProject woProject : projects)
		{
			final LocalDateTime projectStartDate = CoalesceUtil.optionalOfFirstNonNull(woProject.getDateOfProvisionByBPartner(), woProject.getDateContract(), woProject.getWoProjectCreatedDate())
					.map(date -> date.atZone(timeZone).toLocalDateTime().truncatedTo(Plan.PLANNING_TIME_PRECISION))
					.orElse(null);

			final WOProjectSteps steps = stepsByProjectId.getByProjectId(woProject.getProjectId());
			for (WOProjectStep step : steps.toOrderedList())
			{
				final LocalDateTime dueDate = CoalesceUtil.optionalOfFirstNonNull(step.getWoDueDate(), woProject.getDateFinish())
						.map(date -> date.atZone(timeZone).toLocalDateTime().truncatedTo(Plan.PLANNING_TIME_PRECISION))
						.orElse(null);
				if (dueDate == null)
				{
					logger.warn("Ignore step because due date could not be determined: {}", step);
					continue;
				}

				final LocalDateTime startDateMin = Optional.ofNullable(step.getDeliveryDate())
						.map(date -> date.atZone(timeZone).toLocalDateTime().truncatedTo(Plan.PLANNING_TIME_PRECISION))
						.orElse(projectStartDate);
				if (startDateMin == null)
				{
					logger.warn("Ignore step because StartDateMin could not be determined: {}", step);
					continue;
				}

				for (final WOProjectResource resourceOrig : resources.getByStepId(step.getWoProjectStepId()))
				{
					Duration duration = resourceOrig.getDuration();
					if (duration.toSeconds() <= 0)
					{
						duration = Duration.of(1, Plan.PLANNING_TIME_PRECISION);
						logger.info("Step/resource has invalid duration. Considering it {}: {}", duration, resourceOrig);
					}

					final WOProjectResource resource = simulationPlan.applyOn(resourceOrig);

					final LocalDateTime startDate = resource.getStartDate()
							.map(date -> date.atZone(timeZone).toLocalDateTime().truncatedTo(Plan.PLANNING_TIME_PRECISION))
							.orElse(null);
					final LocalDateTime endDate = resource.getEndDate()
							.map(date -> date.atZone(timeZone).toLocalDateTime().truncatedTo(Plan.PLANNING_TIME_PRECISION))
							.orElse(null);

					boolean manuallyLocked = step.isManuallyLocked();
					if (manuallyLocked && (startDate == null || endDate == null))
					{
						logger.info("Cannot consider resource as locked because it has no start/end date: {}", resource);
						manuallyLocked = false;
					}

					final Step optaPlannerStep = Step.builder()
							.id(StepId.builder()
									.woProjectStepId(resource.getWoProjectStepId())
									.woProjectResourceId(resource.getWoProjectResourceId())
									.build())
							.projectPriority(CoalesceUtil.coalesceNotNull(woProject.getInternalPriority(), InternalPriority.MEDIUM))
							.projectSeqNo(step.getSeqNo())
							.resource(optaPlannerResources.computeIfAbsent(resource.getResourceId(), this::createOptaPlannerResource))
							.duration(duration)
							.dueDate(dueDate)
							.startDateMin(startDateMin)
							.startDate(startDate)
							.endDate(endDate)
							.pinned(manuallyLocked)
							.build();

					final BooleanWithReason valid = optaPlannerStep.checkProblemFactsValid();
					if (valid.isFalse())
					{
						logger.info("Skip invalid step because `{}`: {}", valid.getReasonAsString(), step);
						continue;
					}

					optaPlannerSteps.add(optaPlannerStep);

				}
			}
		}

		final Plan optaPlannerPlan = new Plan();
		optaPlannerPlan.setSimulationId(simulationId);
		optaPlannerPlan.setTimeZone(timeZone);
		optaPlannerPlan.setStepsList(optaPlannerSteps);

		return optaPlannerPlan;
	}

	private de.metas.calendar.plan_optimizer.domain.Resource createOptaPlannerResource(final ResourceId resourceId)
	{
		final de.metas.resource.Resource resource = resourceService.getResourceById(resourceId);
		return new de.metas.calendar.plan_optimizer.domain.Resource(resource.getResourceId(), resource.getName().getDefaultValue());
	}

	@Override
	public void saveSolution(final Plan solution)
	{
		final SimulationPlanId simulationId = solution.getSimulationId();
		final ZoneId timeZone = solution.getTimeZone();

		final ImmutableSet<ProjectId> projectIds = solution.getStepsList()
				.stream()
				.map(Step::getProjectId)
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

		for (final Step optaPlannerStep : solution.getStepsList())
		{
			if (optaPlannerStep.getStartDate() == null || optaPlannerStep.getEndDate() == null)
			{
				logger.warn("Skip updating from {} because start/end date is not set", optaPlannerStep);
				continue;
			}

			final CalendarDateRange dateRange = CalendarDateRange.builder()
					.startDate(optaPlannerStep.getStartDate().atZone(timeZone).toInstant())
					.endDate(optaPlannerStep.getEndDate().atZone(timeZone).toInstant())
					.build();

			editor.changeResourceDateRange(
					optaPlannerStep.getId().getWoProjectResourceId(),
					dateRange,
					optaPlannerStep.getId().getWoProjectStepId());
		}

		runInCtx(clientId, orgId, () -> {
			final WOProjectSimulationPlan newSimulationPlan = editor.toNewSimulationPlan();
			woProjectSimulationService.savePlan(newSimulationPlan);

			final ImmutableSet<ResourceId> affectedResourceIds = editor.getAffectedResourceIds();
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
