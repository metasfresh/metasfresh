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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.calendar.CalendarEntry;
import de.metas.calendar.CalendarEntryAddRequest;
import de.metas.calendar.CalendarEntryId;
import de.metas.calendar.CalendarEntryUpdateRequest;
import de.metas.calendar.CalendarGlobalId;
import de.metas.calendar.CalendarQuery;
import de.metas.calendar.CalendarRef;
import de.metas.calendar.CalendarResourceId;
import de.metas.calendar.CalendarResourceRef;
import de.metas.calendar.CalendarService;
import de.metas.calendar.CalendarServiceId;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.project.ProjectId;
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectResource;
import de.metas.project.budget.BudgetProjectResources;
import de.metas.project.budget.BudgetProjectService;
import de.metas.project.workorder.WOProject;
import de.metas.project.workorder.WOProjectResource;
import de.metas.project.workorder.WOProjectResources;
import de.metas.project.workorder.WOProjectService;
import de.metas.project.workorder.WOProjectStep;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.project.workorder.WOProjectSteps;
import de.metas.resource.Resource;
import de.metas.resource.ResourceGroup;
import de.metas.resource.ResourceService;
import de.metas.ui.web.WebuiURLs;
import de.metas.uom.IUOMDAO;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.time.DurationUtils;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.apache.commons.lang3.StringUtils;
import org.compiere.model.I_C_Project;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
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
	private final BudgetProjectService budgetProjectService;

	public WOProjectCalendarService(
			final ResourceService resourceService,
			final WOProjectService woProjectService,
			final BudgetProjectService budgetProjectService)
	{
		this.resourceService = resourceService;
		this.woProjectService = woProjectService;
		this.budgetProjectService = budgetProjectService;
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

		final FrontendURLs frontendURLs = new FrontendURLs();

		final ArrayList<CalendarEntry> result = new ArrayList<>();

		//
		// Budget projects
		{
			final ImmutableMap<ProjectId, BudgetProject> budgetProjects = Maps.uniqueIndex(budgetProjectService.getAllActiveProjects(), BudgetProject::getProjectId);
			final Map<ProjectId, BudgetProjectResources> budgetsByProjectId = budgetProjectService.getBudgetsByProjectIds(budgetProjects.keySet());

			budgetsByProjectId.values()
					.stream()
					.flatMap(budgets -> budgets.getBudgets().stream())
					.map(budget -> toCalendarEntry(
							budget,
							budgetProjects.get(budget.getProjectId()),
							frontendURLs)
					)
					.forEach(result::add);
		}

		//
		// Work Order Projects
		{
			final ImmutableMap<ProjectId, WOProject> woProjects = Maps.uniqueIndex(woProjectService.getAllActiveProjects(), WOProject::getProjectId);
			final Map<ProjectId, WOProjectResources> resourcesByProjectId = woProjectService.getResourcesByProjectIds(woProjects.keySet());
			final ImmutableMap<WOProjectStepId, WOProjectStep> stepsById = woProjectService.getStepsByProjectIds(woProjects.keySet())
					.values()
					.stream()
					.flatMap(WOProjectSteps::stream)
					.collect(ImmutableMap.toImmutableMap(WOProjectStep::getId, step -> step));

			resourcesByProjectId.values()
					.stream()
					.flatMap(resources -> resources.getResources().stream())
					.map(resource -> toCalendarEntry(
							resource,
							stepsById.get(resource.getStepId()),
							woProjects.get(resource.getProjectId()),
							frontendURLs)
					)
					.forEach(result::add);
		}

		//
		//

		return result.stream();
	}

	private CalendarEntry toCalendarEntry(
			@NonNull final BudgetProjectResource budget,
			@NonNull final BudgetProject project,
			@NonNull final FrontendURLs frontendURLs)
	{
		return CalendarEntry.builder()
				.entryId(CalendarEntryId.ofRepoId(CALENDAR_ID, budget.getId()))
				.calendarId(CALENDAR_ID)
				.resourceId(CalendarResourceId.ofRepoId(CoalesceUtil.coalesceNotNull(budget.getResourceId(), budget.getResourceGroupId())))
				.title(TranslatableStrings.builder()
						.append(project.getName())
						.append(" - ")
						.appendQty(budget.getPlannedDuration().toBigDecimal(), budget.getPlannedDuration().getUOMSymbol())
						.build())
				.description(TranslatableStrings.anyLanguage(budget.getDescription()))
				.dateRange(CalendarDateRange.builder()
						.startDate(budget.getStartDate())
						.endDate(budget.getEndDate())
						.build())
				.editable(false)
				.color("#89D72D") // metasfresh green
				.url(frontendURLs.getFrontendURL(budget.getProjectId()).orElse(null))
				.build();
	}

	private CalendarEntry toCalendarEntry(
			@NonNull final WOProjectResource resource,
			@NonNull final WOProjectStep step,
			@NonNull final WOProject project,
			@NonNull final FrontendURLs frontendURLs)
	{
		final int durationInt = DurationUtils.toInt(resource.getDuration(), resource.getDurationUnit());
		final String durationUomSymbol = getTemporalUnitSymbolOrEmpty(resource.getDurationUnit());

		return CalendarEntry.builder()
				.entryId(CalendarEntryId.ofRepoId(CALENDAR_ID, resource.getId()))
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
				.editable(false)
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
	public CalendarEntry updateEntry(final CalendarEntryUpdateRequest request)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteEntryById(final CalendarEntryId entryId)
	{
		throw new UnsupportedOperationException();
	}

	//
	//
	//
	//
	//

	private static class FrontendURLs
	{
		private final WebuiURLs webuiURLs = WebuiURLs.newInstance();

		private final HashMap<ProjectId, Optional<URI>> cache = new HashMap<>();

		private Optional<URI> getFrontendURL(final ProjectId projectId)
		{
			return cache.computeIfAbsent(projectId, this::computeFrontendURL);
		}

		private Optional<URI> computeFrontendURL(@NonNull final ProjectId projectId)
		{
			final AdWindowId adWindowId = RecordWindowFinder.newInstance(TableRecordReference.of(I_C_Project.Table_Name, projectId))
					.checkRecordPresentInWindow() // IMPORTANT: make sure the right Project window is picked
					.findAdWindowId()
					.orElse(null);
			if (adWindowId == null)
			{
				return Optional.empty();
			}

			final String url = webuiURLs.getDocumentUrl(adWindowId, projectId.getRepoId());
			if (url == null)
			{
				return Optional.empty();
			}

			try
			{
				return Optional.of(new URI(url));
			}
			catch (final URISyntaxException e)
			{
				logger.warn("Failed converting `{}` to URI", url, e);
				return Optional.empty();
			}
		}
	}
}
