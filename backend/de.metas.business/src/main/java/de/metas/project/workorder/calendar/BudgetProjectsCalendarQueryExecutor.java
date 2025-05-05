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
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectResourcesCollection;
import de.metas.project.budget.BudgetProjectService;
import de.metas.project.budget.BudgetProjectSimulationPlan;
import de.metas.project.budget.BudgetProjectSimulationService;
import de.metas.project.workorder.resource.ResourceIdAndType;
import de.metas.resource.ResourceGroupId;
import de.metas.resource.ResourceService;
import de.metas.util.InSetPredicate;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

class BudgetProjectsCalendarQueryExecutor
{
	private final BudgetProjectService budgetProjectService;
	private final SimulationPlanService simulationPlanService;
	private final BudgetProjectSimulationService budgetProjectSimulationService;
	private final ResourceService resourceService;
	private final ToCalendarEntryConverter toCalendarEntry;

	// private final CalendarQuery calendarQuery;
	private final InSetPredicate<ProjectId> projectIds;
	private final InSetPredicate<CalendarResourceId> calendarResourceIds;
	private final SimulationPlanId simulationId;

	@Builder
	private BudgetProjectsCalendarQueryExecutor(
			final @NonNull BudgetProjectService budgetProjectService,
			final @NonNull SimulationPlanService simulationPlanService,
			final @NonNull BudgetProjectSimulationService budgetProjectSimulationService,
			final @NonNull ResourceService resourceService,
			final @NonNull ToCalendarEntryConverter toCalendarEntry,
			//
			final @NonNull InSetPredicate<ProjectId> projectIds,
			final @NonNull InSetPredicate<CalendarResourceId> calendarResourceIds,
			final @Nullable SimulationPlanId simulationId)
	{
		this.budgetProjectService = budgetProjectService;
		this.simulationPlanService = simulationPlanService;
		this.budgetProjectSimulationService = budgetProjectSimulationService;
		this.resourceService = resourceService;
		this.toCalendarEntry = toCalendarEntry;

		this.projectIds = projectIds;
		this.calendarResourceIds = calendarResourceIds;
		this.simulationId = simulationId;
	}

	public List<CalendarEntry> execute()
	{
		// TODO consider date range

		final InSetPredicate<ResourceGroupId> resourceGroupIds = getResourceGroupIdsPredicate();
		if (resourceGroupIds.isNone())
		{
			return ImmutableList.of();
		}

		final ImmutableMap<ProjectId, BudgetProject> budgetProjects = Maps.uniqueIndex(
				budgetProjectService.queryAllActiveProjects(resourceGroupIds, projectIds),
				BudgetProject::getProjectId);
		if (budgetProjects.isEmpty())
		{
			return ImmutableList.of();
		}

		final BudgetProjectResourcesCollection budgets = budgetProjectService.getBudgetsByProjectIds(budgetProjects.keySet());

		final SimulationPlanRef simulationPlanHeader = simulationId != null ? simulationPlanService.getById(simulationId) : null;
		final BudgetProjectSimulationPlan simulationPlan = simulationId != null ? budgetProjectSimulationService.getSimulationPlanById(simulationId) : null;

		return budgets.streamBudgets()
				.filter(budget -> resourceGroupIds.test(budget.getResourceGroupId()))
				.map(budget -> toCalendarEntry.from(
						simulationPlan != null ? simulationPlan.applyOn(budget) : budget,
						budgetProjects.get(budget.getProjectId()),
						simulationPlanHeader)
				)
				.collect(ImmutableList.toImmutableList());
	}

	private InSetPredicate<ResourceGroupId> getResourceGroupIdsPredicate()
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

				final ResourceIdAndType resourceIdAndType = ResourceIdAndType.ofCalendarResourceIdOrNull(calendarResourceId);
				if (resourceIdAndType != null)
				{
					resourceIdsSet.add(resourceIdAndType.getResourceId());
					continue;
				}

				final ResourceGroupId resourceGroupId = calendarResourceId.toResourceGroupIdOrNull();
				if (resourceGroupId != null)
				{
					resourceGroupIdsSet.add(resourceGroupId);
					//continue;
				}
			}

			resourceGroupIdsSet.addAll(resourceService.getGroupIdsByResourceIds(resourceIdsSet));

			return InSetPredicate.only(resourceGroupIdsSet);
		}
	}

}
