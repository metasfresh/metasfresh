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
import de.metas.i18n.TranslatableStrings;
import de.metas.project.ProjectId;
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectResource;
import de.metas.project.budget.BudgetProjectResources;
import de.metas.project.budget.BudgetProjectService;
import de.metas.project.workorder.WOProject;
import de.metas.project.workorder.WOProjectResource;
import de.metas.project.workorder.WOProjectResources;
import de.metas.project.workorder.WOProjectService;
import de.metas.resource.Resource;
import de.metas.resource.ResourceGroup;
import de.metas.resource.ResourceService;
import de.metas.user.UserId;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class WOProjectCalendarService implements CalendarService
{
	private static final CalendarServiceId ID = CalendarServiceId.ofString("WOProject");

	private static final CalendarGlobalId CALENDAR_ID = CalendarGlobalId.of(ID, "default");

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

		// TODO consider onltResourceIds
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

		final ArrayList<CalendarEntry> result = new ArrayList<>();

		//
		// Budget projects
		{
			final ImmutableMap<ProjectId, BudgetProject> budgetProjects = Maps.uniqueIndex(budgetProjectService.getAllActiveProjects(), BudgetProject::getProjectId);
			final Map<ProjectId, BudgetProjectResources> budgetsByProjectId = budgetProjectService.getBudgetsByProjectIds(budgetProjects.keySet());

			budgetsByProjectId.values()
					.stream()
					.flatMap(budgets -> budgets.getBudgets().stream())
					.map(budget -> toCalendarEntry(budget, budgetProjects.get(budget.getProjectId())))
					.forEach(result::add);
		}

		//
		// Work Order Projects
		{
			final ImmutableMap<ProjectId, WOProject> woProjects = Maps.uniqueIndex(woProjectService.getAllActiveProjects(), WOProject::getProjectId);
			final Map<ProjectId, WOProjectResources> resourcesByProjectId = woProjectService.getResourcesByProjectIds(woProjects.keySet());

			resourcesByProjectId.values()
					.stream()
					.flatMap(resources -> resources.getResources().stream())
					.map(resource -> toCalendarEntry(resource, woProjects.get(resource.getProjectId())))
					.forEach(result::add);
		}

		//
		//

		return result.stream();
	}

	private static CalendarEntry toCalendarEntry(@NonNull BudgetProjectResource budget, @NonNull BudgetProject project)
	{
		return CalendarEntry.builder()
				.entryId(CalendarEntryId.ofRepoId(CALENDAR_ID, budget.getId()))
				.calendarId(CALENDAR_ID)
				.resourceId(CalendarResourceId.ofRepoId(CoalesceUtil.coalesceNotNull(budget.getResourceId(), budget.getResourceGroupId())))
				.title(project.getName())
				.description(budget.getDescription())
				.dateRange(CalendarDateRange.builder()
						.startDate(budget.getStartDate())
						.endDate(budget.getEndDate())
						.build())
				.build();
	}

	private static CalendarEntry toCalendarEntry(@NonNull WOProjectResource resource, @NonNull WOProject project)
	{
		return CalendarEntry.builder()
				.entryId(CalendarEntryId.ofRepoId(CALENDAR_ID, resource.getId()))
				.calendarId(CALENDAR_ID)
				.resourceId(CalendarResourceId.ofRepoId(resource.getResourceId()))
				.title(project.getName())
				.description(resource.getDescription())
				.dateRange(resource.getDateRange())
				.build();
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
}
