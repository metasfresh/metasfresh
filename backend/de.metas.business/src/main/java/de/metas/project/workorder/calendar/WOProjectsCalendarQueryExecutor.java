package de.metas.project.workorder.calendar;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.calendar.CalendarEntry;
import de.metas.calendar.CalendarResourceId;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.simulation.SimulationPlanRef;
import de.metas.calendar.simulation.SimulationPlanService;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.project.ProjectId;
import de.metas.project.workorder.project.WOProject;
import de.metas.project.workorder.project.WOProjectService;
import de.metas.project.workorder.resource.ResourceIdAndType;
import de.metas.project.workorder.resource.WOProjectResource;
import de.metas.project.workorder.step.WOProjectStep;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.resource.ResourceGroupId;
import de.metas.resource.ResourceService;
import de.metas.util.InSetPredicate;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import javax.validation.constraints.Null;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;

@SuppressWarnings("ALL")
public final class WOProjectsCalendarQueryExecutor
{
	// Services:
	private final ResourceService resourceService;
	private final SimulationPlanService simulationPlanService;
	private final WOProjectService woProjectService;
	private final WOProjectSimulationService woProjectSimulationService;
	private final ToCalendarEntryConverter toCalendarEntry;

	//
	// Params
	@NonNull private final InSetPredicate<ProjectId> projectIds;
	@NonNull private final InSetPredicate<CalendarResourceId> calendarResourceIds;
	@Null private final SimulationPlanId simulationId;
	@Nullable private final Instant startDate;
	@Nullable private final Instant endDate;

	//
	// State
	private ImmutableList<WOProjectResource> _projectResources;
	private ImmutableMap<ProjectId, WOProject> _activeProjectsById;
	private ImmutableMap<WOProjectStepId, WOProjectStep> _stepsById;
	private SimulationHeaderAndPlan _simulationHeaderAndPlan;

	private final boolean skipAllocatedResources;

	@Builder
	private WOProjectsCalendarQueryExecutor(
			final @NonNull ResourceService resourceService,
			final @NonNull SimulationPlanService simulationPlanService,
			final @NonNull WOProjectService woProjectService,
			final @NonNull WOProjectSimulationService woProjectSimulationService,
			final @Nullable ToCalendarEntryConverter toCalendarEntry,
			//
			final @NonNull InSetPredicate<ProjectId> projectIds,
			final @NonNull InSetPredicate<CalendarResourceId> calendarResourceIds,
			final @Nullable SimulationPlanId simulationId,
			final @Nullable Instant startDate,
			final @Nullable Instant endDate,
			final boolean skipAllocatedResources)
	{
		this.resourceService = resourceService;
		this.simulationPlanService = simulationPlanService;
		this.woProjectService = woProjectService;
		this.woProjectSimulationService = woProjectSimulationService;
		this.toCalendarEntry = toCalendarEntry != null ? toCalendarEntry : new ToCalendarEntryConverter(woProjectService);

		this.projectIds = projectIds;
		this.calendarResourceIds = calendarResourceIds;
		this.simulationId = simulationId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.skipAllocatedResources = skipAllocatedResources;
	}

	public static class WOProjectsCalendarQueryExecutorBuilder
	{
		public ImmutableList<CalendarEntry> execute() {return build().execute();}
	}

	@NonNull
	public ImmutableList<CalendarEntry> execute()
	{
		final SimulationHeaderAndPlan simulationHeaderAndPlan = getSimulationHeaderAndPlan();

		return getProjectResources()
				.stream()
				.map(simulationHeaderAndPlan::applyOn)
				.filter(this::isActiveProject)
				.filter(this::isNotFullyAllocated)
				.filter(this::isStartAndEndDatesMatching)
				.map(this::toCalendarEntry)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(ImmutableList.toImmutableList());
	}

	private ImmutableList<WOProjectResource> getProjectResources()
	{
		if (this._projectResources == null)
		{

			this._projectResources = woProjectService.getResources(
					WOProjectResourceCalendarQuery.builder()
							.projectIds(projectIds)
							.resourceIds(getResourceIdsPredicate(calendarResourceIds, resourceService))
							//
							// NOTE: in case we deal with a simulation then filter by start/end date later, when we apply the simulation changes too
							.startDate(simulationId != null ? null : startDate)
							.endDate(simulationId != null ? null : endDate)
							//
							.build());
		}
		return this._projectResources;
	}

	private ImmutableMap<ProjectId, WOProject> getActiveProjects()
	{
		if (this._activeProjectsById == null)
		{
			final ImmutableList<WOProjectResource> projectResources = getProjectResources();
			final ImmutableSet<ProjectId> projectIds = projectResources.stream().map(WOProjectResource::getProjectId).collect(ImmutableSet.toImmutableSet());
			this._activeProjectsById = Maps.uniqueIndex(
					woProjectService.getAllActiveProjects(projectIds),
					WOProject::getProjectId);
		}
		return this._activeProjectsById;
	}

	private ImmutableMap<WOProjectStepId, WOProjectStep> getSteps()
	{
		if (this._stepsById == null)
		{
			final ImmutableList<WOProjectResource> projectResources = getProjectResources();
			final ImmutableSet<WOProjectStepId> stepIds = projectResources.stream().map(WOProjectResource::getWoProjectStepId).collect(ImmutableSet.toImmutableSet());
			this._stepsById = Maps.uniqueIndex(woProjectService.getStepsByIds(stepIds), WOProjectStep::getWoProjectStepId);
		}
		return this._stepsById;
	}

	private WOProjectStep getStep(@NonNull final WOProjectResource projectResource)
	{
		return getSteps().get(projectResource.getWoProjectStepId());
	}

	@NonNull
	private SimulationHeaderAndPlan getSimulationHeaderAndPlan()
	{
		if (this._simulationHeaderAndPlan == null)
		{
			final SimulationPlanRef header = simulationId != null ? simulationPlanService.getById(simulationId) : null;
			final WOProjectSimulationPlan plan = simulationId != null ? woProjectSimulationService.getSimulationPlanById(simulationId) : null;
			this._simulationHeaderAndPlan = SimulationHeaderAndPlan.of(header, plan);
		}
		return this._simulationHeaderAndPlan;
	}

	private WOProject getActiveProject(final @NonNull WOProjectResource projectResource)
	{
		return getActiveProjects().get(projectResource.getProjectId());
	}

	@NonNull
	private Optional<CalendarEntry> toCalendarEntry(@NonNull final WOProjectResource projectResource)
	{
		final SimulationHeaderAndPlan simulationHeaderAndPlan = getSimulationHeaderAndPlan();

		return toCalendarEntry.from(
				simulationHeaderAndPlan.applyOn(projectResource),
				getStep(projectResource),
				getActiveProject(projectResource),
				simulationHeaderAndPlan.getHeader());
	}

	private boolean isActiveProject(@NonNull final WOProjectResource projectResource)
	{
		return getActiveProject(projectResource) != null;
	}

	private boolean isNotFullyAllocated(@NonNull final WOProjectResource projectResource)
	{
		if (!skipAllocatedResources)
		{
			return true;
		}

		return projectResource.isNotFullyResolved();
	}

	private boolean isStartAndEndDatesMatching(final WOProjectResource projectResource)
	{
		if (startDate == null && endDate == null)
		{
			return true;
		}

		final CalendarDateRange dateRange = projectResource.getDateRange();
		return dateRange != null && dateRange.isOverlappingWith(startDate, endDate);
	}

	public static InSetPredicate<ResourceIdAndType> getResourceIdsPredicate(
			@NonNull final InSetPredicate<CalendarResourceId> calendarResourceIds,
			@NonNull final ResourceService resourceService)
	{
		if (calendarResourceIds.isAny())
		{
			return InSetPredicate.any();
		}
		else if (calendarResourceIds.isNone())
		{
			return InSetPredicate.none();
		}
		else
		{
			final HashSet<ResourceIdAndType> resourceIdsSet = new HashSet<>();
			final HashSet<ResourceGroupId> resourceGroupIdsSet = new HashSet<>();
			for (final CalendarResourceId calendarResourceId : calendarResourceIds.toSet())
			{
				if (calendarResourceId == null)
				{
					continue;
				}

				final ResourceIdAndType resourceId = ResourceIdAndType.ofCalendarResourceIdOrNull(calendarResourceId);
				if (resourceId != null)
				{
					resourceIdsSet.add(resourceId);
					continue;
				}

				final ResourceGroupId resourceGroupId = calendarResourceId.toResourceGroupIdOrNull();
				if (resourceGroupId != null)
				{
					resourceGroupIdsSet.add(resourceGroupId);
					//continue;
				}
			}

			resourceService.getActiveResourceIdsByGroupIds(resourceGroupIdsSet)
					.stream()
					.flatMap(ResourceIdAndType::streamForAllTypes)
					.forEach(resourceIdsSet::add);

			return InSetPredicate.only(resourceIdsSet);
		}
	}

	//
	//
	//

	@Value(staticConstructor = "of")
	private static class SimulationHeaderAndPlan
	{
		@Nullable SimulationPlanRef header;
		@Nullable WOProjectSimulationPlan plan;

		public WOProjectResource applyOn(@NonNull final WOProjectResource projectResource)
		{
			return plan != null ? plan.applyOn(projectResource) : projectResource;
		}
	}
}
