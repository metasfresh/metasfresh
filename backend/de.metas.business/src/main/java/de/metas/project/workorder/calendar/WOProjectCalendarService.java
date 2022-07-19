/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.project.workorder.calendar;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.calendar.CalendarEntry;
import de.metas.calendar.CalendarEntryAddRequest;
import de.metas.calendar.CalendarEntryId;
import de.metas.calendar.CalendarEntryUpdateRequest;
import de.metas.calendar.CalendarEntryUpdateResult;
import de.metas.calendar.CalendarGlobalId;
import de.metas.calendar.CalendarQuery;
import de.metas.calendar.CalendarRef;
import de.metas.calendar.CalendarResourceId;
import de.metas.calendar.CalendarResourceRef;
import de.metas.calendar.CalendarService;
import de.metas.calendar.CalendarServiceId;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.simulation.SimulationPlanRef;
import de.metas.calendar.simulation.SimulationPlanService;
import de.metas.common.util.CoalesceUtil;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectResource;
import de.metas.project.budget.BudgetProjectResourceId;
import de.metas.project.budget.BudgetProjectResourceSimulation;
import de.metas.project.budget.BudgetProjectResources;
import de.metas.project.budget.BudgetProjectService;
import de.metas.project.budget.BudgetProjectSimulationPlan;
import de.metas.project.budget.BudgetProjectSimulationService;
import de.metas.project.workorder.WOProject;
import de.metas.project.workorder.WOProjectResource;
import de.metas.project.workorder.WOProjectResourceId;
import de.metas.project.workorder.WOProjectResources;
import de.metas.project.workorder.WOProjectResourcesCollection;
import de.metas.project.workorder.WOProjectService;
import de.metas.project.workorder.WOProjectStep;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.project.workorder.WOProjectSteps;
import de.metas.project.workorder.conflicts.WOProjectConflictService;
import de.metas.resource.Resource;
import de.metas.resource.ResourceGroup;
import de.metas.resource.ResourceGroupId;
import de.metas.resource.ResourceService;
import de.metas.uom.IUOMDAO;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.InSetPredicate;
import de.metas.util.Services;
import de.metas.util.time.DurationUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.OldAndNewValues;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

@Component
public class WOProjectCalendarService implements CalendarService
{
	private static final Logger logger = LogManager.getLogger(WOProjectCalendarService.class);
	private static final CalendarServiceId ID = CalendarServiceId.ofString("WOProject");

	static final CalendarGlobalId CALENDAR_ID = CalendarGlobalId.of(ID, "default");

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final ResourceService resourceService;
	private final SimulationPlanService simulationPlanService;
	private final BudgetProjectService budgetProjectService;
	private final BudgetProjectSimulationService budgetProjectSimulationService;
	private final WOProjectService woProjectService;
	private final WOProjectSimulationService woProjectSimulationService;
	private final WOProjectConflictService woProjectConflictService;

	public WOProjectCalendarService(
			final ResourceService resourceService,
			final SimulationPlanService simulationPlanService,
			final WOProjectService woProjectService,
			final BudgetProjectService budgetProjectService,
			final BudgetProjectSimulationService budgetProjectSimulationService,
			final WOProjectSimulationService woProjectSimulationService,
			final WOProjectConflictService woProjectConflictService)
	{
		this.resourceService = resourceService;
		this.simulationPlanService = simulationPlanService;
		this.budgetProjectService = budgetProjectService;
		this.budgetProjectSimulationService = budgetProjectSimulationService;
		this.woProjectService = woProjectService;
		this.woProjectSimulationService = woProjectSimulationService;
		this.woProjectConflictService = woProjectConflictService;
	}

	@Override
	public CalendarServiceId getCalendarServiceId()
	{
		return ID;
	}

	@Override
	public Stream<CalendarRef> streamAvailableCalendars(final UserId userId)
	{
		final CalendarRef calendar = CalendarRef.builder()
				.calendarId(CALENDAR_ID)
				.name(TranslatableStrings.adElementOrMessage("S_Resource_ID"))
				.resources(getAllResourcesAndGroups())
				.build();

		return Stream.of(calendar);
	}

	private ImmutableSet<CalendarResourceRef> getAllResourcesAndGroups()
	{
		final HashSet<CalendarResourceRef> result = new HashSet<>();
		for (final Resource resource : resourceService.getAllActiveResources())
		{
			result.add(toCalendarResourceRef(resource));
		}

		for (final ResourceGroup resourceGroup : resourceService.getAllActiveGroups())
		{
			result.add(toCalendarResourceRef(resourceGroup));
		}

		return ImmutableSet.copyOf(result);
	}

	private static CalendarResourceRef toCalendarResourceRef(final Resource resource)
	{
		return CalendarResourceRef.builder()
				.calendarResourceId(CalendarResourceId.ofRepoId(resource.getResourceId()))
				.name(resource.getName())
				.parentId(CalendarResourceId.ofNullableRepoId(resource.getResourceGroupId()))
				.build();
	}

	private static CalendarResourceRef toCalendarResourceRef(final ResourceGroup resourceGroup)
	{
		return CalendarResourceRef.builder()
				.calendarResourceId(CalendarResourceId.ofRepoId(resourceGroup.getId()))
				.name(resourceGroup.getName())
				.build();
	}

	@Override
	public Stream<CalendarEntry> query(final CalendarQuery calendarQuery)
	{
		if (!calendarQuery.isMatchingCalendarServiceId(ID))
		{
			return Stream.empty();
		}
		if (!calendarQuery.isMatchingCalendarId(CALENDAR_ID))
		{
			return Stream.empty();
		}

		final WOProjectFrontendURLsProvider frontendURLs = new WOProjectFrontendURLsProvider();

		final ArrayList<CalendarEntry> result = new ArrayList<>();
		result.addAll(query_BudgetProjects(calendarQuery, frontendURLs));
		result.addAll(query_WOProjects(calendarQuery, frontendURLs));
		return result.stream();
	}

	private List<CalendarEntry> query_WOProjects(final CalendarQuery calendarQuery, final WOProjectFrontendURLsProvider frontendURLs)
	{
		// TODO consider date range

		final InSetPredicate<ResourceId> resourceIds = getResourceIdsPredicate(calendarQuery.getResourceIds());
		if (resourceIds.isNone())
		{
			return ImmutableList.of();
		}

		final ImmutableMap<ProjectId, WOProject> woProjectsById = Maps.uniqueIndex(woProjectService.queryActiveProjects(resourceIds), WOProject::getProjectId);
		final ImmutableMap<WOProjectStepId, WOProjectStep> stepsById = woProjectService.getStepsByProjectIds(woProjectsById.keySet())
				.values()
				.stream()
				.flatMap(WOProjectSteps::stream)
				.collect(ImmutableMap.toImmutableMap(WOProjectStep::getId, step -> step));

		final SimulationPlanId simulationId = calendarQuery.getSimulationId();
		final SimulationPlanRef simulationPlanHeader = simulationId != null ? simulationPlanService.getById(simulationId) : null;
		final WOProjectSimulationPlan simulationPlan = simulationId != null ? woProjectSimulationService.getSimulationPlanById(simulationId) : null;

		final WOProjectResourcesCollection allProjectResources = woProjectService.getResourcesByProjectIds(woProjectsById.keySet());

		return allProjectResources.streamProjectResources()
				.filter(projectResource -> resourceIds.test(projectResource.getResourceId()))
				.map(resource -> toCalendarEntry(
						simulationPlan != null ? simulationPlan.applyOn(resource) : resource,
						stepsById.get(resource.getStepId()),
						woProjectsById.get(resource.getProjectId()),
						simulationPlanHeader,
						frontendURLs)
				)
				.collect(ImmutableList.toImmutableList());
	}

	private InSetPredicate<ResourceId> getResourceIdsPredicate(final InSetPredicate<CalendarResourceId> calendarResourceIds)
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

	private List<CalendarEntry> query_BudgetProjects(final CalendarQuery calendarQuery, final WOProjectFrontendURLsProvider frontendURLs)
	{
		// TODO consider onlyResourceIds
		// TODO consider date range

		final ImmutableMap<ProjectId, BudgetProject> budgetProjects = Maps.uniqueIndex(budgetProjectService.getAllActiveProjects(), BudgetProject::getProjectId);
		final Map<ProjectId, BudgetProjectResources> budgetsByProjectId = budgetProjectService.getBudgetsByProjectIds(budgetProjects.keySet());

		final SimulationPlanId simulationId = calendarQuery.getSimulationId();
		final SimulationPlanRef simulationPlanHeader = simulationId != null ? simulationPlanService.getById(simulationId) : null;
		final BudgetProjectSimulationPlan simulationPlan = simulationId != null ? budgetProjectSimulationService.getSimulationPlanById(simulationId) : null;

		return budgetsByProjectId.values()
				.stream()
				.flatMap(budgets -> budgets.getBudgets().stream())
				.map(budget -> toCalendarEntry(
						simulationPlan != null ? simulationPlan.applyOn(budget) : budget,
						budgetProjects.get(budget.getProjectId()),
						simulationPlanHeader,
						frontendURLs)
				)
				.collect(ImmutableList.toImmutableList());
	}

	private CalendarEntry toCalendarEntry(
			@NonNull final BudgetProjectResource budget,
			@NonNull final BudgetProject project,
			@Nullable final SimulationPlanRef simulationHeaderRef,
			@NonNull final WOProjectFrontendURLsProvider frontendURLs)
	{
		return CalendarEntry.builder()
				.entryId(BudgetAndWOCalendarEntryIdConverters.from(budget.getId()))
				.simulationId(simulationHeaderRef != null ? simulationHeaderRef.getId() : null)
				.resourceId(CalendarResourceId.ofRepoId(CoalesceUtil.coalesceNotNull(budget.getResourceId(), budget.getResourceGroupId())))
				.title(TranslatableStrings.builder()
						.append(project.getName())
						.append(" - ")
						.appendQty(budget.getPlannedDuration().toBigDecimal(), budget.getPlannedDuration().getUOMSymbol())
						.build())
				.description(TranslatableStrings.anyLanguage(budget.getDescription()))
				.dateRange(budget.getDateRange())
				.editable(simulationHeaderRef != null && simulationHeaderRef.isEditable())
				.color("#89D72D") // metasfresh green
				.url(frontendURLs.getFrontendURL(budget.getProjectId()).orElse(null))
				.build();
	}

	private CalendarEntry toCalendarEntry(
			@NonNull final WOProjectResource resource,
			@NonNull final WOProjectStep step,
			@NonNull final WOProject project,
			@Nullable final SimulationPlanRef simulationHeaderRef,
			@NonNull final WOProjectFrontendURLsProvider frontendURLs)
	{
		final int durationInt = DurationUtils.toInt(resource.getDuration(), resource.getDurationUnit());
		final String durationUomSymbol = getTemporalUnitSymbolOrEmpty(resource.getDurationUnit());

		return CalendarEntry.builder()
				.entryId(BudgetAndWOCalendarEntryIdConverters.from(resource.getId()))
				.simulationId(simulationHeaderRef != null ? simulationHeaderRef.getId() : null)
				.resourceId(CalendarResourceId.ofRepoId(resource.getResourceId()))
				.title(TranslatableStrings.builder()
						.append(project.getName())
						.append(" - ")
						.append(step.getSeqNo() + "_" + step.getName())
						.append(" - ")
						.appendQty(durationInt, durationUomSymbol)
						.build()
				)
				.description(TranslatableStrings.anyLanguage(resource.getDescription()))
				.dateRange(resource.getDateRange())
				.editable(simulationHeaderRef != null && simulationHeaderRef.isEditable())
				.color("#FFCF60") // orange-ish
				.url(frontendURLs.getFrontendURL(resource.getProjectId()).orElse(null))
				.build();
	}

	private String getTemporalUnitSymbolOrEmpty(final @NonNull TemporalUnit temporalUnit)
	{
		try
		{
			return StringUtils.trimToEmpty(uomDAO.getByTemporalUnit(temporalUnit).getUOMSymbol());
		}
		catch (final Exception ex)
		{
			logger.warn("Failed to get UOM Symbol for TemporalUnit: {}", temporalUnit, ex);
			return "";
		}
	}

	@Override
	public CalendarEntry addEntry(final CalendarEntryAddRequest request)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public CalendarEntryUpdateResult updateEntry(final CalendarEntryUpdateRequest request)
	{
		CALENDAR_ID.assertEqualsTo(request.getCalendarId());

		if (request.getSimulationId() == null)
		{
			throw new AdempiereException("Changing entries outside of simulation is not allowed");
		}

		return BudgetAndWOCalendarEntryIdConverters.withProjectResourceId(
				request.getEntryId(),
				budgetProjectResourceId -> updateEntry_BudgetProjectResource(request, budgetProjectResourceId),
				projectResourceId -> updateEntry_WOProjectResource(request, projectResourceId));
	}

	private CalendarEntryUpdateResult updateEntry_BudgetProjectResource(
			@NonNull final CalendarEntryUpdateRequest request,
			@NonNull final BudgetProjectResourceId projectResourceId)
	{
		final SimulationPlanId simulationId = Check.assumeNotNull(request.getSimulationId(), "simulationId is set: {}", request);
		final SimulationPlanRef simulationPlanHeader = simulationPlanService.getById(simulationId);
		simulationPlanHeader.assertEditable();

		final BudgetProject project = budgetProjectService.getById(projectResourceId.getProjectId())
				.orElseThrow(() -> new AdempiereException("No Budget Project found for " + projectResourceId.getProjectId()));

		final BudgetProjectResource actualBudget = budgetProjectService.getBudgetsById(projectResourceId);

		final WOProjectFrontendURLsProvider frontendURLs = new WOProjectFrontendURLsProvider();

		final OldAndNewValues<CalendarEntry> result = budgetProjectSimulationService
				.createOrUpdate(
						BudgetProjectResourceSimulation.UpdateRequest.builder()
								.simulationId(simulationId)
								.projectResourceId(projectResourceId)
								.dateRange(CoalesceUtil.coalesceNotNull(request.getDateRange(), actualBudget.getDateRange()))
								.build())
				.map(simulation -> simulation != null ? simulation.applyOn(actualBudget) : actualBudget)
				.map(budget -> toCalendarEntry(
						actualBudget,
						project,
						simulationPlanHeader,
						frontendURLs));

		return CalendarEntryUpdateResult.ofChangedEntry(result);
	}

	private CalendarEntryUpdateResult updateEntry_WOProjectResource(
			@NonNull final CalendarEntryUpdateRequest request,
			@NonNull final WOProjectResourceId projectResourceId)
	{
		final SimulationPlanId simulationId = Check.assumeNotNull(request.getSimulationId(), "simulationId shall be set: {}", request);
		final SimulationPlanRef simulationPlanHeader = simulationPlanService.getById(simulationId);
		simulationPlanHeader.assertEditable();

		final WOProjectResources projectResources = woProjectService.getResourcesByProjectId(projectResourceId.getProjectId());

		final WOProjectSimulationPlanEditor simulationEditor = WOProjectSimulationPlanEditor.builder()
				.project(woProjectService.getById(projectResourceId.getProjectId()))
				.steps(woProjectService.getStepsByProjectId(projectResourceId.getProjectId()))
				.projectResources(projectResources)
				.currentSimulationPlan(woProjectSimulationService.getSimulationPlanById(simulationId))
				.build();

		if (request.getDateRange() != null)
		{
			final WOProjectStepId stepId = projectResources.getStepId(projectResourceId);
			simulationEditor.changeResourceDateRangeAndShiftSteps(projectResourceId, request.getDateRange(), stepId);
		}
		if (!Check.isBlank(request.getTitle()))
		{
			throw new AdempiereException("Changing title is not supported yet");
		}
		if (!Check.isBlank(request.getDescription()))
		{
			throw new AdempiereException("Changing description is not supported yet");

		}

		//
		// Save changed plan to database
		final WOProjectSimulationPlan simulation = simulationEditor.toNewSimulationPlan();
		woProjectSimulationService.savePlan(simulation);

		//
		// Check conflicts
		woProjectConflictService.checkSimulationConflicts(simulation, simulationEditor.getAffectedResourceIds());

		//
		// toCalendarEntry converter:
		final WOProjectFrontendURLsProvider frontendURLs = new WOProjectFrontendURLsProvider();
		final Function<WOProjectResource, CalendarEntry> toCalendarEntry = woProjectResource -> toCalendarEntry(
				woProjectResource,
				simulationEditor.getStepById(woProjectResource.getStepId()),
				simulationEditor.getProjectById(woProjectResource.getProjectId()),
				simulationPlanHeader,
				frontendURLs);

		//
		return CalendarEntryUpdateResult.builder()
				.changedEntry(simulationEditor.mapProjectResourceInitialAndNow(projectResourceId, toCalendarEntry))
				.otherChangedEntries(
						simulationEditor.streamChangedProjectResourceIds()
								.filter(changedProjectResourceId -> !WOProjectResourceId.equals(projectResourceId, changedProjectResourceId))
								.map(changedProjectResourceId -> simulationEditor.mapProjectResourceInitialAndNow(changedProjectResourceId, toCalendarEntry))
								.collect(ImmutableList.toImmutableList())
				)
				.build();
	}

	@Override
	public void deleteEntryById(final @NonNull CalendarEntryId entryId, @Nullable SimulationPlanId simulationId)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public CalendarEntry getEntryById(
			@NonNull final CalendarEntryId entryId,
			@Nullable final SimulationPlanId simulationId)
	{
		final SimulationPlanRef simulationPlanHeader = simulationId != null ? simulationPlanService.getById(simulationId) : null;

		return BudgetAndWOCalendarEntryIdConverters.withProjectResourceId(
				entryId,
				budgetProjectResourceId -> getEntryByBudgetResourceId(budgetProjectResourceId, simulationPlanHeader),
				woProjectResourceId -> getEntryByWOProjectResourceId(woProjectResourceId, simulationPlanHeader));
	}

	private CalendarEntry getEntryByBudgetResourceId(
			@NonNull final BudgetProjectResourceId budgetProjectResourceId,
			@Nullable final SimulationPlanRef simulationPlanHeader)
	{
		BudgetProjectResource budget = budgetProjectService.getBudgetsById(budgetProjectResourceId);
		if (simulationPlanHeader != null)
		{
			budget = budgetProjectSimulationService.getSimulationPlanById(simulationPlanHeader.getId()).applyOn(budget);
		}

		final BudgetProject project = budgetProjectService.getById(budgetProjectResourceId.getProjectId())
				.orElseThrow(() -> new AdempiereException("No project found for " + budgetProjectResourceId));
		final WOProjectFrontendURLsProvider frontendUrls = new WOProjectFrontendURLsProvider();

		return toCalendarEntry(budget, project, simulationPlanHeader, frontendUrls);
	}

	private CalendarEntry getEntryByWOProjectResourceId(
			@NonNull final WOProjectResourceId projectResourceId,
			@Nullable final SimulationPlanRef simulationPlanHeader)
	{
		final ProjectId projectId = projectResourceId.getProjectId();

		final WOProjectResources projectResources = woProjectService.getResourcesByProjectId(projectId);
		final WOProjectStepId stepId = projectResources.getStepId(projectResourceId);

		WOProjectResource resource = projectResources.getById(projectResourceId);
		if (simulationPlanHeader != null)
		{
			resource = woProjectSimulationService.getSimulationPlanById(simulationPlanHeader.getId()).applyOn(resource);
		}

		final WOProjectFrontendURLsProvider frontendUrls = new WOProjectFrontendURLsProvider();

		return toCalendarEntry(
				resource,
				woProjectService.getStepsByProjectId(projectId).getById(stepId),
				woProjectService.getById(projectId),
				simulationPlanHeader,
				frontendUrls);
	}
}
