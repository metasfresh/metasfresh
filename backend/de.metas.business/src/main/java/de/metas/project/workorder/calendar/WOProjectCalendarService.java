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
import de.metas.common.util.CoalesceUtil;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.project.ProjectId;
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectAndResourceId;
import de.metas.project.budget.BudgetProjectResource;
import de.metas.project.budget.BudgetProjectResourceId;
import de.metas.project.budget.BudgetProjectResourceSimulation;
import de.metas.project.budget.BudgetProjectResources;
import de.metas.project.budget.BudgetProjectService;
import de.metas.project.budget.BudgetProjectSimulationPlan;
import de.metas.project.budget.BudgetProjectSimulationRepository;
import de.metas.project.workorder.WOProject;
import de.metas.project.workorder.WOProjectResource;
import de.metas.project.workorder.WOProjectResourceId;
import de.metas.project.workorder.WOProjectResources;
import de.metas.project.workorder.WOProjectService;
import de.metas.project.workorder.WOProjectStep;
import de.metas.project.workorder.WOProjectStepAndResourceId;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.project.workorder.WOProjectSteps;
import de.metas.resource.Resource;
import de.metas.resource.ResourceGroup;
import de.metas.resource.ResourceService;
import de.metas.uom.IUOMDAO;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAwares;
import de.metas.util.time.DurationUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

@Component
public class WOProjectCalendarService implements CalendarService
{
	private static final Logger logger = LogManager.getLogger(WOProjectCalendarService.class);
	private static final CalendarServiceId ID = CalendarServiceId.ofString("WOProject");

	private static final CalendarGlobalId CALENDAR_ID = CalendarGlobalId.of(ID, "default");

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final ResourceService resourceService;
	private final WOProjectService woProjectService;
	private final WOProjectSimulationService woProjectSimulationService;
	private final BudgetProjectService budgetProjectService;
	private final BudgetProjectSimulationRepository budgetProjectSimulationRepository;

	public WOProjectCalendarService(
			final ResourceService resourceService,
			final WOProjectService woProjectService,
			final WOProjectSimulationService woProjectSimulationService,
			final BudgetProjectService budgetProjectService,
			final BudgetProjectSimulationRepository budgetProjectSimulationRepository)
	{
		this.resourceService = resourceService;
		this.woProjectService = woProjectService;
		this.woProjectSimulationService = woProjectSimulationService;
		this.budgetProjectService = budgetProjectService;
		this.budgetProjectSimulationRepository = budgetProjectSimulationRepository;
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

		// TODO consider onlyResourceIds
		// final ImmutableSet<ResourceId> onlyResourceIds;
		// if (!calendarQuery.getOnlyResourceIds().isEmpty())
		// {
		// 	onlyResourceIds = calendarQuery.getOnlyResourceIdsOfType(ResourceId.class);
		// 	if (onlyResourceIds.isEmpty())
		// 	{
		// 		return null;
		// 	}
		//
		// 	// TODO calendarQuery.getOnlyResourceIdsOfType(ResourceGroupId.class);
		// }
		// else
		// {
		// 	onlyResourceIds = null;
		// }

		// TODO consider date range

		final WOProjectFrontendURLsProvider frontendURLs = new WOProjectFrontendURLsProvider();

		final ArrayList<CalendarEntry> result = new ArrayList<>();

		//
		// Budget projects
		{
			final ImmutableMap<ProjectId, BudgetProject> budgetProjects = Maps.uniqueIndex(budgetProjectService.getAllActiveProjects(), BudgetProject::getProjectId);
			final Map<ProjectId, BudgetProjectResources> budgetsByProjectId = budgetProjectService.getBudgetsByProjectIds(budgetProjects.keySet());

			final BudgetProjectSimulationPlan simulationPlan = calendarQuery.getSimulationId() != null ? budgetProjectSimulationRepository.getSimulationPlanById(calendarQuery.getSimulationId()) : null;

			budgetsByProjectId.values()
					.stream()
					.flatMap(budgets -> budgets.getBudgets().stream())
					.map(budget -> toCalendarEntry(
							budget,
							simulationPlan != null ? simulationPlan.getByProjectResourceIdOrNull(budget.getId()) : null,
							budgetProjects.get(budget.getProjectId()),
							simulationPlan != null ? simulationPlan.getSimulationPlanId() : null,
							frontendURLs)
					)
					.forEach(result::add);
		}

		//
		// Work Order Projects
		{
			final ImmutableMap<ProjectId, WOProject> woProjects = Maps.uniqueIndex(woProjectService.getAllActiveProjects(), WOProject::getProjectId);
			final ImmutableMap<WOProjectStepId, WOProjectStep> stepsById = woProjectService.getStepsByProjectIds(woProjects.keySet())
					.values()
					.stream()
					.flatMap(WOProjectSteps::stream)
					.collect(ImmutableMap.toImmutableMap(WOProjectStep::getId, step -> step));

			final WOProjectSimulationPlan simulationPlan = calendarQuery.getSimulationId() != null ? woProjectSimulationService.getSimulationPlanById(calendarQuery.getSimulationId()) : null;

			woProjectService.getResourcesByProjectIds(woProjects.keySet())
					.stream()
					.flatMap(WOProjectResources::stream)
					.map(resource -> toCalendarEntry(
							simulationPlan != null ? simulationPlan.applyOn(resource) : resource,
							stepsById.get(resource.getStepId()),
							woProjects.get(resource.getProjectId()),
							simulationPlan != null ? simulationPlan.getSimulationPlanId() : null,
							frontendURLs)
					)
					.forEach(result::add);
		}

		//
		//

		return result.stream();
	}

	private CalendarEntry toCalendarEntry(
			@NonNull final BudgetProjectResource budget0,
			@Nullable final BudgetProjectResourceSimulation budgetSimulation,
			@NonNull final BudgetProject project,
			@Nullable final SimulationPlanId simulationId,
			@NonNull final WOProjectFrontendURLsProvider frontendURLs)
	{
		final BudgetProjectResource budgetEffective = budgetSimulation != null ? budgetSimulation.applyOn(budget0) : budget0;

		return CalendarEntry.builder()
				.entryId(CalendarEntryIdConverters.from(budgetEffective.getProjectId(), budgetEffective.getId()))
				.simulationId(simulationId)
				.calendarId(CALENDAR_ID)
				.resourceId(CalendarResourceId.ofRepoId(CoalesceUtil.coalesceNotNull(budgetEffective.getResourceId(), budgetEffective.getResourceGroupId())))
				.title(TranslatableStrings.builder()
						.append(project.getName())
						.append(" - ")
						.appendQty(budgetEffective.getPlannedDuration().toBigDecimal(), budgetEffective.getPlannedDuration().getUOMSymbol())
						.build())
				.description(TranslatableStrings.anyLanguage(budgetEffective.getDescription()))
				.dateRange(budgetEffective.getDateRange())
				.editable(simulationId != null)
				.color("#89D72D") // metasfresh green
				.url(frontendURLs.getFrontendURL(budgetEffective.getProjectId()).orElse(null))
				.build();
	}

	private CalendarEntry toCalendarEntry(
			@NonNull final WOProjectResource resource,
			@NonNull final WOProjectStep step,
			@NonNull final WOProject project,
			@Nullable final SimulationPlanId simulationId,
			@NonNull final WOProjectFrontendURLsProvider frontendURLs)
	{
		final int durationInt = DurationUtils.toInt(resource.getDuration(), resource.getDurationUnit());
		final String durationUomSymbol = getTemporalUnitSymbolOrEmpty(resource.getDurationUnit());

		return CalendarEntry.builder()
				.entryId(CalendarEntryIdConverters.from(resource.getProjectId(), resource.getStepId(), resource.getId()))
				.simulationId(simulationId)
				.calendarId(CALENDAR_ID)
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
				.editable(simulationId != null)
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

		return CalendarEntryIdConverters.withProjectResourceId(
				request.getEntryId(),
				budgetProjectAndResourceId -> updateEntry_BudgetProjectResource(request, budgetProjectAndResourceId),
				projectStepAndResourceId -> updateEntry_WOProjectResource(request, projectStepAndResourceId));
	}

	private CalendarEntryUpdateResult updateEntry_BudgetProjectResource(@NonNull final CalendarEntryUpdateRequest request, @NonNull final BudgetProjectAndResourceId projectAndResourceId)
	{
		final BudgetProject project = budgetProjectService.getById(projectAndResourceId.getProjectId())
				.orElseThrow(() -> new AdempiereException("No Budget Project found for " + projectAndResourceId.getProjectId()));

		final BudgetProjectResource budget = budgetProjectService.getBudgetsById(projectAndResourceId.getProjectId(), projectAndResourceId.getProjectResourceId());

		final BudgetProjectResourceSimulation budgetSimulation = budgetProjectSimulationRepository.createOrUpdate(BudgetProjectResourceSimulation.UpdateRequest.builder()
				.simulationId(Check.assumeNotNull(request.getSimulationId(), "simulationId is set: {}", request))
				.projectAndResourceId(projectAndResourceId)
				.dateRange(CoalesceUtil.coalesceNotNull(request.getDateRange(), budget.getDateRange()))
				.build());

		final WOProjectFrontendURLsProvider frontendURLs = new WOProjectFrontendURLsProvider();

		return CalendarEntryUpdateResult.ofChangedEntry(
				toCalendarEntry(
						budget,
						budgetSimulation,
						project,
						request.getSimulationId(),
						frontendURLs)
		);
	}

	private CalendarEntryUpdateResult updateEntry_WOProjectResource(
			@NonNull final CalendarEntryUpdateRequest request,
			@NonNull final WOProjectStepAndResourceId projectStepAndResourceId)
	{
		final WOProjectSimulationPlanEditor simulationEditor = WOProjectSimulationPlanEditor.builder()
				.project(woProjectService.getById(projectStepAndResourceId.getProjectId()))
				.steps(woProjectService.getStepsByProjectId(projectStepAndResourceId.getProjectId()))
				.projectResources(woProjectService.getResourcesByProjectId(projectStepAndResourceId.getProjectId()))
				.currentSimulationPlan(woProjectSimulationService.getSimulationPlanById(request.getSimulationId()))
				.build();

		if (request.getDateRange() != null)
		{
			simulationEditor.changeResourceDateRangeAndShiftSteps(projectStepAndResourceId, request.getDateRange());
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
		woProjectSimulationService.savePlan(simulationEditor.toNewSimulationPlan());

		//
		// toCalendarEntry converter:
		final WOProjectFrontendURLsProvider frontendURLs = new WOProjectFrontendURLsProvider();
		final Function<WOProjectResource, CalendarEntry> toCalendarEntry = woProjectResource -> toCalendarEntry(
				woProjectResource,
				simulationEditor.getStepById(woProjectResource.getStepId()),
				simulationEditor.getProjectById(woProjectResource.getProjectId()),
				simulationEditor.getSimulationPlanId(),
				frontendURLs);

		//
		return CalendarEntryUpdateResult.builder()
				.changedEntry(toCalendarEntry.apply(simulationEditor.getProjectResourceById(projectStepAndResourceId.getProjectResourceId())))
				.otherChangedEntries(simulationEditor.getChangedProjectResources()
						.filter(projectResource -> !WOProjectResourceId.equals(projectResource.getId(), projectStepAndResourceId.getProjectResourceId()))
						.map(toCalendarEntry)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	@Override
	public void deleteEntryById(final CalendarEntryId entryId, @Nullable SimulationPlanId simulationId)
	{
		throw new UnsupportedOperationException();
	}

	//
	//
	//
	//
	//

	private static class CalendarEntryIdConverters
	{
		private static final String TYPE_Budget = "budget";
		private static final String TYPE_WorkOrder = "WO";

		public static <T> T withProjectResourceId(
				@NonNull final CalendarEntryId entryId,
				@NonNull final Function<BudgetProjectAndResourceId, T> budgetResourceIdMapper,
				@NonNull final Function<WOProjectStepAndResourceId, T> woProjectResourceIdMapper)
		{
			CALENDAR_ID.assertEqualsTo(entryId.getCalendarId());
			try
			{
				final ImmutableList<String> entryLocalIds = entryId.getEntryLocalIds();
				final String type = entryLocalIds.get(0);
				if (TYPE_Budget.equals(type))
				{
					return budgetResourceIdMapper.apply(
							BudgetProjectAndResourceId.of(
									RepoIdAwares.ofObject(entryLocalIds.get(1), ProjectId.class),
									RepoIdAwares.ofObject(entryLocalIds.get(2), BudgetProjectResourceId.class)));
				}
				else if (TYPE_WorkOrder.equals(type))
				{
					return woProjectResourceIdMapper.apply(
							WOProjectStepAndResourceId.of(
									RepoIdAwares.ofObject(entryLocalIds.get(1), ProjectId.class),
									RepoIdAwares.ofObject(entryLocalIds.get(2), WOProjectStepId.class),
									RepoIdAwares.ofObject(entryLocalIds.get(3), WOProjectResourceId.class)));
				}
				else
				{
					throw new AdempiereException("Invalid ID: " + entryId);
				}
			}
			catch (AdempiereException ex)
			{
				throw ex;
			}
			catch (Exception ex)
			{
				throw new AdempiereException("Invalid ID: " + entryId, ex);
			}
		}

		public static CalendarEntryId from(@NonNull WOProjectStepAndResourceId woProjectResourceId)
		{
			return from(woProjectResourceId.getProjectId(), woProjectResourceId.getStepId(), woProjectResourceId.getProjectResourceId());
		}

		public static CalendarEntryId from(@NonNull ProjectId projectId, @NonNull WOProjectStepId stepId, @NonNull WOProjectResourceId projectResourceId)
		{
			return CalendarEntryId.ofCalendarAndLocalIds(
					CALENDAR_ID,
					TYPE_WorkOrder,
					String.valueOf(projectId.getRepoId()),
					String.valueOf(stepId.getRepoId()),
					String.valueOf(projectResourceId.getRepoId()));
		}

		public static CalendarEntryId from(@NonNull BudgetProjectAndResourceId budgetProjectAndResourceId)
		{
			return from(budgetProjectAndResourceId.getProjectId(), budgetProjectAndResourceId.getProjectResourceId());
		}

		public static CalendarEntryId from(@NonNull ProjectId projectId, @NonNull BudgetProjectResourceId projectResourceId)
		{
			return CalendarEntryId.ofCalendarAndLocalIds(
					CALENDAR_ID,
					TYPE_Budget,
					String.valueOf(projectId.getRepoId()),
					String.valueOf(projectResourceId.getRepoId()));
		}
	}
}
