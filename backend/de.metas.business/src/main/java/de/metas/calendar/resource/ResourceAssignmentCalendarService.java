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
import de.metas.calendar.simulation.CalendarSimulationId;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.ResourceId;
import de.metas.resource.Resource;
import de.metas.resource.ResourceAssignment;
import de.metas.resource.ResourceAssignmentCreateRequest;
import de.metas.resource.ResourceAssignmentId;
import de.metas.resource.ResourceAssignmentQuery;
import de.metas.resource.ResourceGroup;
import de.metas.resource.ResourceGroupId;
import de.metas.resource.ResourceService;
import de.metas.user.UserId;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.stream.Stream;

//@Component
@Deprecated
public class ResourceAssignmentCalendarService implements CalendarService
{
	private static final CalendarServiceId ID = CalendarServiceId.ofString("ResourceAssignment");

	private static final CalendarGlobalId CALENDAR_ID = CalendarGlobalId.of(ID, "default");

	private final ResourceService resourceService;

	public ResourceAssignmentCalendarService(@NonNull final ResourceService resourceService)
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
				.name(TranslatableStrings.adElementOrMessage("S_Resource_ID"))
				.resources(getResources())
				.build();

		return Stream.of(calendar);
	}

	private ImmutableSet<CalendarResourceRef> getResources()
	{
		final HashSet<CalendarResourceRef> result = new HashSet<>();
		final HashSet<ResourceGroupId> resourceGroupIds = new HashSet<>();
		for (final Resource resource : resourceService.getAllActiveResources())
		{
			result.add(toCalendarResourceRef(resource));
			if (resource.getResourceGroupId() != null)
			{
				resourceGroupIds.add(resource.getResourceGroupId());
			}
		}

		for (final ResourceGroup resourceGroup : resourceService.getGroupsByIds(resourceGroupIds))
		{
			result.add(ResourceGroupAssignmentCalendarService.toCalendarResourceRef(resourceGroup));
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

	@Override
	public Stream<CalendarEntry> query(@NonNull final CalendarQuery query)
	{
		final ResourceAssignmentQuery resourceAssignmentQuery = toResourceAssignmentQueryOrNull(query);
		if (resourceAssignmentQuery == null)
		{
			return Stream.empty();
		}

		return resourceService.queryResourceAssignments(resourceAssignmentQuery)
				.map(ResourceAssignmentCalendarService::toCalendarEntry);
	}

	@Nullable
	private static ResourceAssignmentQuery toResourceAssignmentQueryOrNull(final CalendarQuery calendarQuery)
	{
		if (!calendarQuery.isMatchingCalendarServiceId(ID))
		{
			return null;
		}
		if (!calendarQuery.isMatchingCalendarId(CALENDAR_ID))
		{
			return null;
		}

		final ImmutableSet<ResourceId> onlyResourceIds;
		if (!calendarQuery.getOnlyResourceIds().isEmpty())
		{
			onlyResourceIds = calendarQuery.getOnlyResourceIdsOfType(ResourceId.class);
			if (onlyResourceIds.isEmpty())
			{
				return null;
			}
		}
		else
		{
			onlyResourceIds = null;
		}

		return ResourceAssignmentQuery.builder()
				.onlyResourceIds(onlyResourceIds)
				.startDate(calendarQuery.getStartDate())
				.endDate(calendarQuery.getEndDate())
				.build();
	}

	private static CalendarEntry toCalendarEntry(@NonNull final ResourceAssignment resourceAssignment)
	{
		return CalendarEntry.builder()
				.entryId(CalendarEntryId.ofRepoId(CALENDAR_ID, resourceAssignment.getId()))
				.calendarId(CALENDAR_ID)
				.resourceId(CalendarResourceId.ofRepoId(resourceAssignment.getResourceId()))
				.title(TranslatableStrings.anyLanguage(resourceAssignment.getName()))
				.description(TranslatableStrings.anyLanguage(resourceAssignment.getDescription()))
				.dateRange(resourceAssignment.getDateRange())
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
		if (request.getSimulationId() != null)
		{
			throw new UnsupportedOperationException("Simulation is not supported");
		}

		if (request.getResourceId() == null)
		{
			throw new AdempiereException("Fill mandatory `resourceId`");
		}

		final ResourceAssignment resourceAssignment = resourceService.createResourceAssignment(ResourceAssignmentCreateRequest.builder()
				.resourceId(request.getResourceId().toRepoId(ResourceId.class))
				.name(request.getTitle())
				.description(request.getDescription())
				.dateRange(request.getDateRange())
				.build());

		return toCalendarEntry(resourceAssignment);
	}

	@Override
	public CalendarEntry updateEntry(@NonNull final CalendarEntryUpdateRequest request)
	{
		assertValidCalendarId(request.getCalendarId());
		if (request.getSimulationId() != null)
		{
			throw new UnsupportedOperationException("Simulation is not supported");
		}

		final ResourceAssignment changedResourceAssignment = resourceService.changeResourceAssignment(
				toResourceAssignmentId(request.getEntryId()),
				resourceAssignment -> updateResourceAssignment(resourceAssignment, request)
		);

		return toCalendarEntry(changedResourceAssignment);
	}

	private ResourceAssignmentId toResourceAssignmentId(@NonNull final CalendarEntryId entryId)
	{
		assertValidCalendarId(entryId.getCalendarId());
		return entryId.toRepoId(ResourceAssignmentId.class);
	}

	@Override
	public void deleteEntryById(@NonNull final CalendarEntryId entryId, @Nullable CalendarSimulationId simulationId)
	{
		if (simulationId != null)
		{
			throw new UnsupportedOperationException("Simulation is not supported");
		}

		resourceService.deleteResourceAssignment(toResourceAssignmentId(entryId));
	}

	private static ResourceAssignment updateResourceAssignment(
			@NonNull final ResourceAssignment resourceAssignment,
			@NonNull final CalendarEntryUpdateRequest request)
	{
		final ResourceAssignment.ResourceAssignmentBuilder builder = resourceAssignment.toBuilder();

		// @NonNull UserId updatedByUserId;
		// @NonNull CalendarEntryId entryId;
		// @Nullable CalendarGlobalId calendarId;

		if (request.getResourceId() != null)
		{
			builder.resourceId(request.getResourceId().toRepoId(ResourceId.class));
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
