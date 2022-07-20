package de.metas.project.workorder.calendar;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.calendar.CalendarEntry;
import de.metas.calendar.CalendarResourceId;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.simulation.SimulationPlanRef;
import de.metas.calendar.simulation.SimulationPlanService;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.workorder.WOProject;
import de.metas.project.workorder.WOProjectService;
import de.metas.project.workorder.WOProjectStep;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.project.workorder.WOProjectSteps;
import de.metas.resource.ResourceGroupId;
import de.metas.resource.ResourceService;
import de.metas.util.InSetPredicate;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.HashSet;

public final class WOProjectsCalendarQueryExecutor
{
	private final ResourceService resourceService;
	private final SimulationPlanService simulationPlanService;
	private final WOProjectService woProjectService;
	private final WOProjectSimulationService woProjectSimulationService;
	private final ToCalendarEntryConverter toCalendarEntry;

	private final InSetPredicate<ProjectId> projectIds;
	private final InSetPredicate<CalendarResourceId> calendarResourceIds;
	private final SimulationPlanId simulationId;

	@Builder
	private WOProjectsCalendarQueryExecutor(
			final @NonNull ResourceService resourceService,
			final @NonNull SimulationPlanService simulationPlanService,
			final @NonNull WOProjectService woProjectService,
			final @NonNull WOProjectSimulationService woProjectSimulationService,
			final @NonNull ToCalendarEntryConverter toCalendarEntry,
			//
			final @NonNull InSetPredicate<ProjectId> projectIds,
			final @NonNull InSetPredicate<CalendarResourceId> calendarResourceIds,
			final @Nullable SimulationPlanId simulationId)
	{
		this.resourceService = resourceService;
		this.simulationPlanService = simulationPlanService;
		this.woProjectService = woProjectService;
		this.woProjectSimulationService = woProjectSimulationService;
		this.toCalendarEntry = toCalendarEntry;

		this.projectIds = projectIds;
		this.calendarResourceIds = calendarResourceIds;
		this.simulationId = simulationId;
	}

	public ImmutableList<CalendarEntry> execute()
	{
		// TODO consider date range

		final InSetPredicate<ResourceId> resourceIds = getResourceIdsPredicate();
		if (resourceIds.isNone())
		{
			return ImmutableList.of();
		}

		final ImmutableMap<ProjectId, WOProject> woProjectsById = Maps.uniqueIndex(
				woProjectService.queryActiveProjects(resourceIds, projectIds),
				WOProject::getProjectId);
		if (woProjectsById.isEmpty())
		{
			return ImmutableList.of(); // shall not happen
		}

		final ImmutableMap<WOProjectStepId, WOProjectStep> stepsById = woProjectService.getStepsByProjectIds(woProjectsById.keySet())
				.values()
				.stream()
				.flatMap(WOProjectSteps::stream)
				.collect(ImmutableMap.toImmutableMap(WOProjectStep::getId, step -> step));

		final SimulationPlanRef simulationPlanHeader = simulationId != null ? simulationPlanService.getById(simulationId) : null;
		final WOProjectSimulationPlan simulationPlan = simulationId != null ? woProjectSimulationService.getSimulationPlanById(simulationId) : null;

		return woProjectService
				.getResourcesByProjectIds(woProjectsById.keySet())
				.streamProjectResources()
				.filter(projectResource -> resourceIds.test(projectResource.getResourceId()))
				.map(resource -> toCalendarEntry.from(
						simulationPlan != null ? simulationPlan.applyOn(resource) : resource,
						stepsById.get(resource.getStepId()),
						woProjectsById.get(resource.getProjectId()),
						simulationPlanHeader)
				)
				.collect(ImmutableList.toImmutableList());
	}

	private InSetPredicate<ResourceId> getResourceIdsPredicate()
	{
		return getResourceIdsPredicate(calendarResourceIds, resourceService);
	}

	public static InSetPredicate<ResourceId> getResourceIdsPredicate(
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
			final HashSet<ResourceId> resourceIdsSet = new HashSet<>();
			final HashSet<ResourceGroupId> resourceGroupIdsSet = new HashSet<>();
			for (final CalendarResourceId calendarResourceId : calendarResourceIds.toSet())
			{
				if (calendarResourceId == null)
				{
					continue;
				}

				final ResourceId resourceId = calendarResourceId.toRepoIdOrNull(ResourceId.class);
				if (resourceId != null)
				{
					resourceIdsSet.add(resourceId);
					continue;
				}

				final ResourceGroupId resourceGroupId = calendarResourceId.toRepoIdOrNull(ResourceGroupId.class);
				if (resourceGroupId != null)
				{
					resourceGroupIdsSet.add(resourceGroupId);
					//continue;
				}
			}

			resourceIdsSet.addAll(resourceService.getActiveResourceIdsByGroupIds(resourceGroupIdsSet));

			return InSetPredicate.only(resourceIdsSet);
		}
	}

}
