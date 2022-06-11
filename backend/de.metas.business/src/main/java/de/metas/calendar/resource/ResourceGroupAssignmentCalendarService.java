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

package de.metas.calendar.resource;

import com.google.common.collect.ImmutableSet;
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
import de.metas.i18n.TranslatableStrings;
import de.metas.resource.ResourceGroup;
import de.metas.resource.ResourceGroupAssignment;
import de.metas.resource.ResourceGroupAssignmentCreateRequest;
import de.metas.resource.ResourceGroupAssignmentId;
import de.metas.resource.ResourceGroupAssignmentQuery;
import de.metas.resource.ResourceGroupId;
import de.metas.resource.ResourceService;
import de.metas.user.UserId;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.stream.Stream;

// @Component
public class ResourceGroupAssignmentCalendarService implements CalendarService
{
	private static final CalendarServiceId ID = CalendarServiceId.ofString("ResourceGroupAssignment");

	private static final CalendarGlobalId CALENDAR_ID = CalendarGlobalId.of(ID, "default");

	private final ResourceService resourceService;

	public ResourceGroupAssignmentCalendarService(@NonNull final ResourceService resourceService)
	{
		this.resourceService = resourceService;
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
				.name(TranslatableStrings.adElementOrMessage("S_Resource_Group_ID"))
				.resources(resourceService.getAllActiveGroups()
						.stream()
						.map(ResourceGroupAssignmentCalendarService::toCalendarResourceRef)
						.collect(ImmutableSet.toImmutableSet()))
				.build();

		return Stream.of(calendar);
	}

	static CalendarResourceRef toCalendarResourceRef(final ResourceGroup resourceGroup)
	{
		return CalendarResourceRef.builder()
				.calendarResourceId(CalendarResourceId.ofRepoId(resourceGroup.getId()))
				.name(resourceGroup.getName())
				.build();
	}

	@Override
	public Stream<CalendarEntry> query(@NonNull final CalendarQuery query)
	{
		final ResourceGroupAssignmentQuery resourceGroupAssignmentQuery = toResourceAssignmentQueryOrNull(query);
		if (resourceGroupAssignmentQuery == null)
		{
			return Stream.empty();
		}

		return resourceService.queryResourceGroupAssignments(resourceGroupAssignmentQuery)
				.map(ResourceGroupAssignmentCalendarService::toCalendarEntry);
	}

	@Nullable
	private static ResourceGroupAssignmentQuery toResourceAssignmentQueryOrNull(final CalendarQuery calendarQuery)
	{
		if (!calendarQuery.isMatchingCalendarServiceId(ID))
		{
			return null;
		}
		if (!calendarQuery.isMatchingCalendarId(CALENDAR_ID))
		{
			return null;
		}

		final ImmutableSet<ResourceGroupId> onlyResourceGroupIds;
		if (!calendarQuery.getOnlyResourceIds().isEmpty())
		{
			onlyResourceGroupIds = calendarQuery.getOnlyResourceIdsOfType(ResourceGroupId.class);
			if (onlyResourceGroupIds.isEmpty())
			{
				return null;
			}
		}
		else
		{
			onlyResourceGroupIds = null;
		}

		return ResourceGroupAssignmentQuery.builder()
				.onlyResourceGroupIds(onlyResourceGroupIds)
				.startDate(calendarQuery.getStartDate())
				.endDate(calendarQuery.getEndDate())
				.build();
	}

	private static CalendarEntry toCalendarEntry(@NonNull final ResourceGroupAssignment resourceGroupAssignment)
	{
		return CalendarEntry.builder()
				.entryId(CalendarEntryId.ofRepoId(CALENDAR_ID, resourceGroupAssignment.getId()))
				.calendarId(CALENDAR_ID)
				.resourceId(CalendarResourceId.ofRepoId(resourceGroupAssignment.getResourceGroupId()))
				.title(TranslatableStrings.anyLanguage(resourceGroupAssignment.getName()))
				.description(TranslatableStrings.anyLanguage(resourceGroupAssignment.getDescription()))
				.dateRange(resourceGroupAssignment.getDateRange())
				.editable(true)
				.build();
	}

	private static void assertValidCalendarId(@Nullable final CalendarGlobalId calendarId)
	{
		if (calendarId != null && !calendarId.equals(CALENDAR_ID))
		{
			throw new AdempiereException("Invalid calendar ID `" + calendarId + "`. Expected: " + CALENDAR_ID);
		}
	}

	@Override
	public CalendarEntry addEntry(@NonNull final CalendarEntryAddRequest request)
	{
		assertValidCalendarId(request.getCalendarId());

		if (request.getResourceId() == null)
		{
			throw new AdempiereException("Fill mandatory `resourceId`");
		}

		final ResourceGroupAssignment assignment = resourceService.createResourceGroupAssignment(
				ResourceGroupAssignmentCreateRequest.builder()
						.resourceGroupId(request.getResourceId().toRepoId(ResourceGroupId.class))
						.name(request.getTitle())
						.description(request.getDescription())
						.dateRange(request.getDateRange())
						.build());

		return toCalendarEntry(assignment);
	}

	@Override
	public CalendarEntry updateEntry(@NonNull final CalendarEntryUpdateRequest request)
	{
		assertValidCalendarId(request.getCalendarId());
		final ResourceGroupAssignment changedAssignment = resourceService.changeResourceGroupAssignmentById(
				toResourceGroupAssignmentId(request.getEntryId()),
				assignment -> updateResourceAssignment(assignment, request)
		);

		return toCalendarEntry(changedAssignment);
	}

	private ResourceGroupAssignmentId toResourceGroupAssignmentId(@NonNull final CalendarEntryId entryId)
	{
		assertValidCalendarId(entryId.getCalendarId());
		return entryId.toRepoId(ResourceGroupAssignmentId.class);
	}

	@Override
	public void deleteEntryById(@NonNull final CalendarEntryId entryId)
	{
		resourceService.deleteResourceGroupAssignment(toResourceGroupAssignmentId(entryId));
	}

	private static ResourceGroupAssignment updateResourceAssignment(
			@NonNull final ResourceGroupAssignment assignment,
			@NonNull final CalendarEntryUpdateRequest request)
	{
		final ResourceGroupAssignment.ResourceGroupAssignmentBuilder builder = assignment.toBuilder();

		// @NonNull UserId updatedByUserId;
		// @NonNull CalendarEntryId entryId;
		// @Nullable CalendarGlobalId calendarId;

		if (request.getResourceId() != null)
		{
			builder.resourceGroupId(request.getResourceId().toRepoId(ResourceGroupId.class));
		}

		if (request.getDateRange() != null)
		{
			builder.dateRange(request.getDateRange());
		}

		if (request.getTitle() != null)
		{
			builder.name(request.getTitle());
		}

		if (request.getDescription() != null)
		{
			builder.description(StringUtils.trimBlankToNull(request.getDescription()));
		}

		return builder.build();
	}
}
