package de.metas.calendar.plan_optimizer.persistance;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.calendar.plan_optimizer.domain.HumanResource;
import de.metas.calendar.plan_optimizer.domain.HumanResourceId;
import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.plan_optimizer.domain.Project;
import de.metas.calendar.plan_optimizer.domain.Resource;
import de.metas.calendar.plan_optimizer.domain.StepDef;
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
import de.metas.project.workorder.resource.WOResourceType;
import de.metas.project.workorder.step.WOProjectStep;
import de.metas.project.workorder.step.WOProjectStepsCollection;
import de.metas.resource.HumanResourceTestGroup;
import de.metas.resource.HumanResourceTestGroupId;
import de.metas.resource.HumanResourceTestGroupService;
import de.metas.resource.ResourceService;
import de.metas.resource.ResourceType;
import de.metas.resource.ResourceWeeklyAvailability;
import de.metas.util.Check;
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
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DatabasePlanLoaderInstance
{
	//
	// Services
	private static final Logger logger = LogManager.getLogger(DatabasePlanLoaderInstance.class);
	private final IOrgDAO orgDAO;
	private final WOProjectService woProjectService;
	private final WOProjectSimulationService woProjectSimulationService;
	private final ResourceService resourceService;
	private final HumanResourceTestGroupService humanResourceTestGroupService;

	//
	// Params
	@NonNull final SimulationPlanId simulationId;

	//
	// State
	private WOProjectStepsCollection stepsByProjectId;
	private WOProjectResourcesCollection resources;
	private ZoneId timeZone;
	private WOProjectSimulationPlan simulationPlan;
	private final HashMap<ResourceId, Resource> timefoldResources = new HashMap<>();

	@Builder
	private DatabasePlanLoaderInstance(
			final @NonNull IOrgDAO orgDAO,
			final @NonNull WOProjectService woProjectService,
			final @NonNull WOProjectSimulationService woProjectSimulationService,
			final @NonNull ResourceService resourceService,
			final @NonNull HumanResourceTestGroupService humanResourceTestGroupService,
			//
			final @NonNull SimulationPlanId simulationId)
	{
		this.orgDAO = orgDAO;
		this.woProjectService = woProjectService;
		this.woProjectSimulationService = woProjectSimulationService;
		this.resourceService = resourceService;
		this.humanResourceTestGroupService = humanResourceTestGroupService;
		this.simulationId = simulationId;
	}

	public Plan load()
	{
		final List<WOProject> woProjects = woProjectService.getAllActiveProjects();
		if (woProjects.isEmpty())
		{
			return Plan.newInstance();
		}
		final ImmutableSet<ProjectId> projectIds = woProjects.stream().map(WOProject::getProjectId).collect(ImmutableSet.toImmutableSet());
		this.stepsByProjectId = woProjectService.getStepsByProjectIds(projectIds);
		this.resources = woProjectService.getResourcesByProjectIds(projectIds);
		this.timeZone = orgDAO.getTimeZone(woProjects.get(0).getOrgId()); // use the time zone of the first project
		this.simulationPlan = woProjectSimulationService.getSimulationPlanById(simulationId);

		final ArrayList<Project> projects = new ArrayList<>();
		for (final WOProject woProject : woProjects)
		{
			final Project project = Project.builder()
					.steps(loadStepsFromWOProject(woProject))
					.build();
			projects.add(project);
		}

		final Plan plan = Plan.newInstance();
		plan.setSimulationId(simulationId);
		plan.setTimeZone(timeZone);
		plan.setStepsList(new ArrayList<>(Project.createAllocations(projects)));

		return plan;
	}

	private List<StepDef> loadStepsFromWOProject(final WOProject woProject)
	{
		final LocalDateTime projectStartDate = extractProjectStartDate(woProject);

		final ArrayList<StepDef> stepsList = new ArrayList<>();
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

			final Map<ResourceId, ImmutableMap<WOResourceType, WOProjectResource>> woStepResources = this.resources.streamByStepId(woStep.getWoProjectStepId())
					.collect(Collectors.groupingBy(
							resource -> resource.getResourceIdAndType().getResourceId(),
							ImmutableMap.toImmutableMap(
									resource -> resource.getResourceIdAndType().getType(),
									resource -> resource
							)
					));

			for (final ResourceId resourceId : woStepResources.keySet())
			{
				WOProjectResource machineProjectResource = woStepResources.get(resourceId).get(WOResourceType.MACHINE);
				WOProjectResource humanProjectResource = woStepResources.get(resourceId).get(WOResourceType.HUMAN);
				final StepDef step = fromWOStepResource(woProject, woStep, machineProjectResource, humanProjectResource, startDateMin, dueDate);
				if (step == null)
				{
					continue;
				}

				stepsList.add(step);
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
	private StepDef fromWOStepResource(
			@NonNull final WOProject woProject,
			@NonNull final WOProjectStep woStep,
			@Nullable final WOProjectResource woStepResourceOrig_Machine,
			@Nullable final WOProjectResource woStepResourceOrig_Human,
			@NonNull final LocalDateTime startDateMin,
			@NonNull final LocalDateTime dueDate)
	{
		if (woStepResourceOrig_Machine == null && woStepResourceOrig_Human == null)
		{
			return null;
		}

		final WOProjectResource woStepResource_Machine = woStepResourceOrig_Machine != null ? simulationPlan.applyOn(woStepResourceOrig_Machine) : null;
		final WOProjectResource woStepResource_Human = woStepResourceOrig_Human != null ? simulationPlan.applyOn(woStepResourceOrig_Human) : null;

		final LocalDateTime startDate = Optional.ofNullable(woStepResource_Machine).flatMap(WOProjectResource::getStartDate).map(this::toLocalDateTime).orElse(null);
		final LocalDateTime endDate = Optional.ofNullable(woStepResource_Machine).flatMap(WOProjectResource::getEndDate).map(this::toLocalDateTime).orElse(null);

		boolean pinned = woStep.isManuallyLocked() || woStep.inTesting();
		if (pinned && (startDate == null || endDate == null))
		{
			logger.info("Cannot consider resource as locked because it has no start/end date: {}", woStepResource_Machine);
			pinned = false;
		}

		Duration requiredMachineCapacity = Optional.ofNullable(woStep.getWoPlannedResourceDurationHours()).map(Duration::ofHours).orElse(Duration.ZERO);
		if (requiredMachineCapacity.toSeconds() <= 0)
		{
			requiredMachineCapacity = Duration.of(1, Plan.PLANNING_TIME_PRECISION);
			logger.info("Step/machine has invalid required capacity. Considering it {}: {}", requiredMachineCapacity, woStepResourceOrig_Machine);
		}
		final Duration requiredHumanCapacity = Optional.ofNullable(woStep.getWoPlannedPersonDurationHours()).map(Duration::ofHours).orElse(Duration.ZERO);

		final StepDef stepDef = StepDef.builder()
				.id(StepId.builder()
						.woProjectStepId(getFieldValueExpectingEquals(woStepResource_Machine, woStepResource_Human, WOProjectResource::getWoProjectStepId))
						.machineWOProjectResourceId(woStepResource_Machine != null ? woStepResource_Machine.getWoProjectResourceId() : null)
						.humanWOProjectResourceId(woStepResource_Human != null ? woStepResource_Human.getWoProjectResourceId() : null)
						.build())
				.projectPriority(CoalesceUtil.coalesceNotNull(woProject.getInternalPriority(), InternalPriority.MEDIUM))
				.resource(toTimefoldResource(getFieldValueExpectingEquals(woStepResource_Machine, woStepResource_Human, resource -> resource.getResourceIdAndType().getResourceId())))
				.requiredResourceCapacity(requiredMachineCapacity)
				.requiredHumanCapacity(requiredHumanCapacity)
				.dueDate(dueDate)
				.startDateMin(startDateMin)
				.pinnedStartDate(pinned ? startDate : null)
				//
				.initialStartDate(startDate)
				.initialEndDate(endDate)
				//
				.build();

		final BooleanWithReason valid = stepDef.checkProblemFactsValid();
		if (valid.isFalse())
		{
			logger.info("Skip invalid woStep because `{}`: {}", valid.getReasonAsString(), woStep);
			return null;
		}

		return stepDef;
	}

	private static <T> T getFieldValueExpectingEquals(
			@Nullable final WOProjectResource resource1,
			@Nullable final WOProjectResource resource2,
			@NonNull final Function<WOProjectResource, T> valueGetter)
	{
		if (resource1 == null)
		{
			if (resource2 == null)
			{
				return null;
			}
			else
			{
				return valueGetter.apply(resource2);
			}
		}
		else
		{
			if (resource2 == null)
			{
				return valueGetter.apply(resource1);
			}
			else
			{
				final T value1 = valueGetter.apply(resource1);
				final T value2 = valueGetter.apply(resource2);
				Check.assumeEquals(value1, value2, "Extracted values shall be the same for: {}, {} when using {}. But we got: {}, {}", resource1, resource2, valueGetter, value1, value2);
				return value1;
			}
		}
	}

	@NonNull
	private Resource toTimefoldResource(@NonNull final ResourceId resourceId)
	{
		return timefoldResources.computeIfAbsent(resourceId, this::createTimefoldResource);
	}

	private de.metas.calendar.plan_optimizer.domain.Resource createTimefoldResource(final ResourceId resourceId)
	{
		final de.metas.resource.Resource resource = resourceService.getResourceById(resourceId);
		final ResourceType resourceType = resourceService.getResourceTypeById(resource.getResourceTypeId());
		return de.metas.calendar.plan_optimizer.domain.Resource.builder()
				.id(resource.getResourceId())
				.name(resource.getName().getDefaultValue())
				.availability(resourceType.getAvailability().timeSlotTruncatedTo(Plan.PLANNING_TIME_PRECISION))
				.humanResource(getHumanResourceCapacity(resource.getHumanResourceTestGroupId()))
				.build();
	}

	@Nullable
	private HumanResource getHumanResourceCapacity(@Nullable final HumanResourceTestGroupId groupId)
	{
		if (groupId == null)
		{
			return null;
		}

		final HumanResourceTestGroup group = humanResourceTestGroupService.getById(groupId);
		final ResourceWeeklyAvailability availability = group.getAvailability().timeSlotTruncatedTo(Plan.PLANNING_TIME_PRECISION);

		return HumanResource.builder()
				.id(HumanResourceId.of(group.getId()))
				.availability(availability)
				.build();
	}
}
