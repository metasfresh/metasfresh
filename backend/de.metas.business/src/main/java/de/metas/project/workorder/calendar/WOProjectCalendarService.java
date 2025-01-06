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
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
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
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.project.ProjectId;
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectResource;
import de.metas.project.budget.BudgetProjectResourceId;
import de.metas.project.budget.BudgetProjectResourceSimulation;
import de.metas.project.budget.BudgetProjectService;
import de.metas.project.budget.BudgetProjectSimulationService;
import de.metas.project.service.ProjectRepository;
import de.metas.project.workorder.conflicts.WOProjectConflictService;
import de.metas.project.workorder.project.WOProjectService;
import de.metas.project.workorder.resource.ResourceIdAndType;
import de.metas.project.workorder.resource.WOProjectResource;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.project.workorder.resource.WOProjectResources;
import de.metas.project.workorder.resource.WOResourceType;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.resource.Resource;
import de.metas.resource.ResourceGroup;
import de.metas.resource.ResourceGroupId;
import de.metas.resource.ResourceService;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.InSetPredicate;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.OldAndNewValues;
import org.elasticsearch.common.util.set.Sets;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

@Component
public class WOProjectCalendarService implements CalendarService
{
	private static final CalendarServiceId ID = CalendarServiceId.ofString("WOProject");

	static final CalendarGlobalId CALENDAR_ID = CalendarGlobalId.of(ID, "default");

	private final ResourceService resourceService;
	private final SimulationPlanService simulationPlanService;
	private final ProjectRepository genericProjectRepository;
	private final BudgetProjectService budgetProjectService;
	private final BudgetProjectSimulationService budgetProjectSimulationService;
	private final WOProjectService woProjectService;
	private final WOProjectSimulationService woProjectSimulationService;
	private final WOProjectConflictService woProjectConflictService;

	public WOProjectCalendarService(
			final ResourceService resourceService,
			final SimulationPlanService simulationPlanService,
			final ProjectRepository genericProjectRepository,
			final WOProjectService woProjectService,
			final BudgetProjectService budgetProjectService,
			final BudgetProjectSimulationService budgetProjectSimulationService,
			final WOProjectSimulationService woProjectSimulationService,
			final WOProjectConflictService woProjectConflictService)
	{
		this.resourceService = resourceService;
		this.simulationPlanService = simulationPlanService;
		this.genericProjectRepository = genericProjectRepository;
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
			result.add(toCalendarResourceRef(resource, WOResourceType.MACHINE));
			result.add(toCalendarResourceRef(resource, WOResourceType.HUMAN));
		}

		for (final ResourceGroup resourceGroup : resourceService.getAllActiveGroups())
		{
			result.add(toCalendarResourceRef(resourceGroup));
		}

		return ImmutableSet.copyOf(result);
	}

	private static CalendarResourceRef toCalendarResourceRef(@NonNull final Resource resource, @NonNull final WOResourceType type)
	{
		ITranslatableString name = resource.getName();
		if (type != WOResourceType.MACHINE)
		{
			name = TranslatableStrings.builder().append(name).append(" / ").append(type.name()).build();
		}

		return CalendarResourceRef.builder()
				.calendarResourceId(ResourceIdAndType.of(resource.getResourceId(), type).toCalendarResourceId())
				.name(name)
				.parentId(resource.getResourceGroupId() != null ? toCalendarResourceId(resource.getResourceGroupId()) : null)
				.build();
	}

	private static CalendarResourceRef toCalendarResourceRef(final ResourceGroup resourceGroup)
	{
		return CalendarResourceRef.builder()
				.calendarResourceId(toCalendarResourceId(resourceGroup.getId()))
				.name(resourceGroup.getName())
				.build();
	}

	private static CalendarResourceId toCalendarResourceId(@NonNull final ResourceGroupId resourceGroupId) {return CalendarResourceId.ofResourceGroupId(resourceGroupId);}

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

		final InSetPredicate<ProjectId> projectIds = getProjectIdsPredicate(
				calendarQuery.getOnlyProjectId(),
				calendarQuery.getOnlyCustomerId(),
				calendarQuery.getOnlyProjectResponsibleId());
		if (projectIds.isNone())
		{
			return Stream.empty();
		}

		final ToCalendarEntryConverter toCalendarEntry = new ToCalendarEntryConverter(woProjectService);

		final ArrayList<CalendarEntry> result = new ArrayList<>();
		result.addAll(
				WOProjectsCalendarQueryExecutor.builder()
						.resourceService(resourceService)
						.simulationPlanService(simulationPlanService)
						.woProjectService(woProjectService)
						.woProjectSimulationService(woProjectSimulationService)
						.toCalendarEntry(toCalendarEntry)
						//
						.projectIds(projectIds)
						.calendarResourceIds(calendarQuery.getResourceIds())
						.simulationId(calendarQuery.getSimulationId())
						.startDate(calendarQuery.getStartDate())
						.endDate(calendarQuery.getEndDate())
						.skipAllocatedResources(calendarQuery.isSkipAllocatedResources())
						.execute());
		result.addAll(
				BudgetProjectsCalendarQueryExecutor.builder()
						.resourceService(resourceService)
						.simulationPlanService(simulationPlanService)
						.budgetProjectService(budgetProjectService)
						.budgetProjectSimulationService(budgetProjectSimulationService)
						.toCalendarEntry(toCalendarEntry)
						//
						.projectIds(projectIds)
						.calendarResourceIds(calendarQuery.getResourceIds())
						.simulationId(calendarQuery.getSimulationId())
						//
						.build().execute());

		return result.stream();
	}

	public InSetPredicate<ProjectId> getProjectIdsPredicate(
			@Nullable final ProjectId onlyProjectId,
			@Nullable final BPartnerId onlyCustomerId,
			@Nullable final UserId onlyResponsibleId)
	{
		final WOProjectCalendarQuery query = WOProjectCalendarQuery.builder()
				.projectIds(onlyProjectId != null ? InSetPredicate.only(onlyProjectId) : InSetPredicate.any())
				.onlyCustomerId(onlyCustomerId)
				.onlyResponsibleId(onlyResponsibleId)
				.build();
		if (query.isAny())
		{
			return InSetPredicate.any();
		}
		final ImmutableSet<ProjectId> woProjectIds = woProjectService.getActiveProjectIds(query);

		ImmutableSet<ProjectId> budgetProjectIds = ImmutableSet.of();
		if (onlyProjectId != null && woProjectIds.isEmpty())
		{
			final BudgetProject budgetProject = budgetProjectService.getById(onlyProjectId).orElse(null);
			if (budgetProject != null)
			{
				budgetProjectIds = ImmutableSet.of(budgetProject.getProjectId());
			}
		}

		final ImmutableSet<ProjectId> projectIdsExpanded = expandWithUpAndDownStreams(Sets.union(woProjectIds, budgetProjectIds));
		return InSetPredicate.only(projectIdsExpanded);
	}

	/**
	 * Expand given projectIds:
	 * <ul>
	 *     <li>up stream: fetch all parent projects, basically to have all the budget project hierarchy</li>
	 *     <li>down stream: fetch ALL children projects. So in case of a budget project we will get all child budget projects and work order projects beneath</li>
	 * </ul>
	 */
	private ImmutableSet<ProjectId> expandWithUpAndDownStreams(final Set<ProjectId> projectIds)
	{
		return ImmutableSet.<ProjectId>builder()
				.addAll(genericProjectRepository.getProjectIdsUpStream(projectIds))
				.addAll(genericProjectRepository.getProjectIdsDownStream(projectIds))
				.build();

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

	@NonNull
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

		final ToCalendarEntryConverter toCalendarEntry = new ToCalendarEntryConverter(woProjectService);

		final OldAndNewValues<CalendarEntry> result = budgetProjectSimulationService
				.createOrUpdate(
						BudgetProjectResourceSimulation.UpdateRequest.builder()
								.simulationId(simulationId)
								.projectResourceId(projectResourceId)
								.dateRange(CoalesceUtil.coalesceNotNull(request.getDateRange(), actualBudget.getDateRange()))
								.build())
				.map(simulation -> simulation != null ? simulation.applyOn(actualBudget) : actualBudget)
				.map(budget -> toCalendarEntry.from(
						actualBudget,
						project,
						simulationPlanHeader));

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

		final WOProjectStepId stepId = projectResources.getStepId(projectResourceId);
		simulationEditor.changeResourceDateRangeAndShiftSteps(projectResourceId, request.getDateRange(), stepId);

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
		final Function<WOProjectResource, CalendarEntry> toCalendarEntry = new ToCalendarEntryConverter(woProjectService).asFunction(simulationPlanHeader, simulationEditor);

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
	@NonNull
	public Optional<CalendarEntry> getEntryById(
			@NonNull final CalendarEntryId entryId,
			@Nullable final SimulationPlanId simulationId)
	{
		final SimulationPlanRef simulationPlanHeader = simulationId != null ? simulationPlanService.getById(simulationId) : null;

		return BudgetAndWOCalendarEntryIdConverters.withProjectResourceId(
				entryId,
				budgetProjectResourceId -> Optional.of(getEntryByBudgetResourceId(budgetProjectResourceId, simulationPlanHeader)),
				woProjectResourceId -> getEntryByWOProjectResourceId(woProjectResourceId, simulationPlanHeader));
	}

	@NonNull
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

		final ToCalendarEntryConverter toCalendarEntry = new ToCalendarEntryConverter(woProjectService);

		return toCalendarEntry.from(budget, project, simulationPlanHeader);
	}

	@NonNull
	private Optional<CalendarEntry> getEntryByWOProjectResourceId(
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

		final ToCalendarEntryConverter toCalendarEntry = new ToCalendarEntryConverter(woProjectService);
		return toCalendarEntry.from(
				resource,
				woProjectService.getStepsByProjectId(projectId).getById(stepId),
				woProjectService.getById(projectId),
				simulationPlanHeader);
	}
}
