package de.metas.calendar.plan_optimizer.persistance;

import com.google.common.collect.ImmutableSet;
import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.plan_optimizer.domain.Resource;
import de.metas.calendar.plan_optimizer.domain.Step;
import de.metas.calendar.plan_optimizer.domain.StepId;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.common.util.CoalesceUtil;
import de.metas.i18n.BooleanWithReason;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.product.ResourceId;
import de.metas.project.InternalPriority;
import de.metas.project.ProjectId;
import de.metas.project.workorder.calendar.WOProjectSimulationPlan;
import de.metas.project.workorder.calendar.WOProjectSimulationService;
import de.metas.project.workorder.project.WOProject;
import de.metas.project.workorder.project.WOProjectService;
import de.metas.project.workorder.resource.WOProjectResource;
import de.metas.project.workorder.resource.WOProjectResourcesCollection;
import de.metas.project.workorder.step.WOProjectStep;
import de.metas.project.workorder.step.WOProjectStepsCollection;
import de.metas.resource.ResourceService;
import lombok.Builder;
import lombok.NonNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class DatabasePlanLoaderInstance
{
	//
	// Services
	private static final Logger logger = LogManager.getLogger(DatabasePlanLoaderInstance.class);
	private final IOrgDAO orgDAO;
	private final WOProjectService woProjectService;
	private final WOProjectSimulationService woProjectSimulationService;
	private final ResourceService resourceService;

	//
	// Params
	@NonNull final SimulationPlanId simulationId;

	//
	// State
	private WOProjectStepsCollection stepsByProjectId;
	private WOProjectResourcesCollection resources;
	private ZoneId timeZone;
	private WOProjectSimulationPlan simulationPlan;
	private final HashMap<ResourceId, Resource> optaPlannerResources = new HashMap<>();

	@Builder
	private DatabasePlanLoaderInstance(
			final @NonNull IOrgDAO orgDAO,
			final @NonNull WOProjectService woProjectService,
			final @NonNull WOProjectSimulationService woProjectSimulationService,
			final @NonNull ResourceService resourceService,
			//
			final @NonNull SimulationPlanId simulationId)
	{
		this.orgDAO = orgDAO;
		this.woProjectService = woProjectService;
		this.woProjectSimulationService = woProjectSimulationService;
		this.resourceService = resourceService;
		this.simulationId = simulationId;
	}

	public Plan load()
	{
		final List<WOProject> woProjects = woProjectService.getAllActiveProjects();
		if (woProjects.isEmpty())
		{
			return new Plan();
		}
		final ImmutableSet<ProjectId> projectIds = woProjects.stream().map(WOProject::getProjectId).collect(ImmutableSet.toImmutableSet());
		this.stepsByProjectId = woProjectService.getStepsByProjectIds(projectIds);
		this.resources = woProjectService.getResourcesByProjectIds(projectIds);
		this.timeZone = orgDAO.getTimeZone(woProjects.get(0).getOrgId()); // use the time zone of the first project
		this.simulationPlan = woProjectSimulationService.getSimulationPlanById(simulationId);

		final ArrayList<Step> stepsList = new ArrayList<>();
		for (final WOProject woProject : woProjects)
		{
			stepsList.addAll(loadStepsFromWOProject(woProject));
		}

		final Plan optaPlannerPlan = new Plan();
		optaPlannerPlan.setSimulationId(simulationId);
		optaPlannerPlan.setTimeZone(timeZone);
		optaPlannerPlan.setStepsList(stepsList);

		return optaPlannerPlan;
	}

	private List<Step> loadStepsFromWOProject(final WOProject woProject)
	{
		final LocalDateTime projectStartDate = extractProjectStartDate(woProject);

		final ArrayList<Step> stepsList = new ArrayList<>();
		Step prevStep = null;

		for (WOProjectStep woStep : stepsByProjectId.getByProjectId(woProject.getProjectId()).toOrderedList())
		{
			final LocalDateTime startDateMin = Optional.ofNullable(woStep.getDeliveryDate())
					.map(this::toLocalDateTime)
					.orElse(projectStartDate);
			if (startDateMin == null)
			{
				logger.warn("Ignore step because StartDateMin could not be determined: {}", woStep);
				continue;
			}

			final LocalDateTime dueDate = CoalesceUtil.optionalOfFirstNonNull(woStep.getWoDueDate(), woProject.getDateFinish())
					.map(this::toLocalDateTime)
					.orElse(null);
			if (dueDate == null)
			{
				logger.warn("Ignore step because due date could not be determined: {}", woStep);
				continue;
			}

			for (final WOProjectResource woStepResourceOrig : resources.getByStepId(woStep.getWoProjectStepId()))
			{
				final Step step = fromWOStepResource(woProject, woStep, woStepResourceOrig, prevStep, startDateMin, dueDate);
				if (step == null)
				{
					continue;
				}

				if (prevStep != null)
				{
					prevStep.setNextStep(step);
				}

				stepsList.add(step);

				prevStep = step;
			}
		}

		return stepsList;
	}

	@NonNull
	private LocalDateTime toLocalDateTime(final Instant date)
	{
		return date.atZone(timeZone).toLocalDateTime().truncatedTo(Plan.PLANNING_TIME_PRECISION);
	}

	@Nullable
	private LocalDateTime extractProjectStartDate(final WOProject woProject)
	{
		return CoalesceUtil.optionalOfFirstNonNull(woProject.getDateOfProvisionByBPartner(), woProject.getDateContract(), woProject.getWoProjectCreatedDate())
				.map(this::toLocalDateTime)
				.orElse(null);
	}

	@Nullable
	private Step fromWOStepResource(
			final WOProject woProject,
			final WOProjectStep woStep,
			final WOProjectResource woStepResourceOrig,
			Step prevStep,
			final LocalDateTime startDateMin,
			final LocalDateTime dueDate)
	{
		Duration duration = woStepResourceOrig.getDuration();
		if (duration.toSeconds() <= 0)
		{
			duration = Duration.of(1, Plan.PLANNING_TIME_PRECISION);
			logger.info("Step/resource has invalid duration. Considering it {}: {}", duration, woStepResourceOrig);
		}

		final WOProjectResource woStepResource = simulationPlan.applyOn(woStepResourceOrig);

		final LocalDateTime startDate = woStepResource.getStartDate()
				.map(this::toLocalDateTime)
				.orElse(null);
		final LocalDateTime endDate = woStepResource.getEndDate()
				.map(this::toLocalDateTime)
				.orElse(null);

		boolean manuallyLocked = woStep.isManuallyLocked();
		if (manuallyLocked && (startDate == null || endDate == null))
		{
			logger.info("Cannot consider resource as locked because it has no start/end date: {}", woStepResource);
			manuallyLocked = false;
		}

		final int delay = prevStep == null ? computeDelay(startDateMin, startDate) : computeDelay(prevStep.getEndDate(), startDate);

		final Step step = Step.builder()
				.id(StepId.builder()
						.woProjectStepId(woStepResource.getWoProjectStepId())
						.woProjectResourceId(woStepResource.getWoProjectResourceId())
						.build())
				.projectPriority(CoalesceUtil.coalesceNotNull(woProject.getInternalPriority(), InternalPriority.MEDIUM))
				.resource(toOptaPlannerResource(woStepResource))
				.duration(duration)
				.dueDate(dueDate)
				.startDateMin(startDateMin)
				.delay(delay)
				.pinned(manuallyLocked)
				.build();

		final BooleanWithReason valid = step.checkProblemFactsValid();
		if (valid.isFalse())
		{
			logger.info("Skip invalid woStep because `{}`: {}", valid.getReasonAsString(), woStep);
			return null;
		}

		if (prevStep != null)
		{
			prevStep.setNextStep(step);
			step.setPreviousStep(prevStep);
		}

		return step;
	}

	@NonNull
	private Resource toOptaPlannerResource(final WOProjectResource woStepResource)
	{
		return optaPlannerResources.computeIfAbsent(woStepResource.getResourceId(), this::createOptaPlannerResource);
	}

	private de.metas.calendar.plan_optimizer.domain.Resource createOptaPlannerResource(final ResourceId resourceId)
	{
		final de.metas.resource.Resource resource = resourceService.getResourceById(resourceId);
		return new de.metas.calendar.plan_optimizer.domain.Resource(resource.getResourceId(), resource.getName().getDefaultValue(), resource.getHumanResourceTestGroupId());
	}

	public static int computeDelay(@NonNull final LocalDateTime lastStepEndDate, @Nullable final LocalDateTime thisStepStartDate)
	{
		return thisStepStartDate != null && thisStepStartDate.isAfter(lastStepEndDate)
				? (int)Plan.PLANNING_TIME_PRECISION.between(lastStepEndDate, thisStepStartDate)
				: 0;
	}

}
