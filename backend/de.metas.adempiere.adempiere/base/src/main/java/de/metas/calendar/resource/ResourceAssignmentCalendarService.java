/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.calendar.CalendarEntry;
import de.metas.calendar.CalendarEntryAddRequest;
import de.metas.calendar.CalendarGlobalId;
import de.metas.calendar.CalendarQuery;
import de.metas.calendar.CalendarRef;
import de.metas.calendar.CalendarService;
import de.metas.calendar.CalendarServiceId;
import de.metas.product.ResourceId;
import de.metas.resource.Resource;
import de.metas.resource.ResourceAssignment;
import de.metas.resource.ResourceAssignmentCreateRequest;
import de.metas.resource.ResourceAssignmentQuery;
import de.metas.resource.ResourceAssignmentRepository;
import de.metas.resource.ResourceRepository;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Component
public class ResourceAssignmentCalendarService implements CalendarService
{
	private static final CalendarServiceId ID = CalendarServiceId.ofString("ResourceAssignment");

	private final ResourceRepository resourceRepository;
	private final ResourceAssignmentRepository resourceAssignmentRepository;

	public ResourceAssignmentCalendarService(
			@NonNull final ResourceRepository resourceRepository,
			@NonNull final ResourceAssignmentRepository resourceAssignmentRepository)
	{
		this.resourceRepository = resourceRepository;
		this.resourceAssignmentRepository = resourceAssignmentRepository;
	}

	@Override
	public CalendarServiceId getCalendarServiceId()
	{
		return ID;
	}

	@Override
	public List<CalendarRef> getAvailableCalendars(final UserId userId)
	{
		return resourceRepository.getResources()
				.stream()
				.map(ResourceAssignmentCalendarService::toCalendarRef)
				.collect(ImmutableList.toImmutableList());
	}

	private static CalendarRef toCalendarRef(final Resource resource)
	{
		return CalendarRef.of(toCalendarGlobalId(resource.getResourceId()), resource.getName());
	}

	private static CalendarGlobalId toCalendarGlobalId(@NonNull final ResourceId resourceId)
	{
		return CalendarGlobalId.of(ID, resourceId);
	}

	@Nullable
	private static ResourceId toResourceIdOrNull(@NonNull final CalendarGlobalId calendarId)
	{
		return CalendarServiceId.equals(calendarId.getCalendarServiceId(), ID)
				? calendarId.getAsRepoId(ResourceId::ofRepoId)
				: null;
	}

	private static ResourceId toResourceId(@NonNull final CalendarGlobalId calendarId)
	{
		final ResourceId resourceId = toResourceIdOrNull(calendarId);
		if (resourceId == null)
		{
			throw new AdempiereException("Invalid resources calendar ID: " + calendarId);
		}
		return resourceId;
	}

	@Override
	public Stream<CalendarEntry> query(@NonNull final CalendarQuery query)
	{
		final ResourceAssignmentQuery resourceAssignmentQuery = toResourceAssignmentQueryOrNull(query);
		if (resourceAssignmentQuery == null)
		{
			return Stream.empty();
		}

		return resourceAssignmentRepository.query(resourceAssignmentQuery)
				.map(ResourceAssignmentCalendarService::toCalendarEntry);
	}

	@Nullable
	private static ResourceAssignmentQuery toResourceAssignmentQueryOrNull(final CalendarQuery calendarQuery)
	{
		if (!calendarQuery.isMatchingCalendarServiceId(ID))
		{
			return null;
		}

		final ImmutableSet<ResourceId> onlyResourceIds;
		if (!calendarQuery.getOnlyCalendarIds().isEmpty())
		{
			onlyResourceIds = calendarQuery.getOnlyCalendarIds()
					.stream()
					.map(ResourceAssignmentCalendarService::toResourceIdOrNull)
					.filter(Objects::nonNull)
					.collect(ImmutableSet.toImmutableSet());

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
				.entryId(resourceAssignment.getId().getAsString())
				.calendarId(toCalendarGlobalId(resourceAssignment.getResourceId()))
				.startDate(resourceAssignment.getStartDate())
				.endDate(resourceAssignment.getEndDate())
				.title(resourceAssignment.getName())
				.description(resourceAssignment.getDescription())
				.build();
	}

	@Override
	public CalendarEntry addEntry(@NonNull final CalendarEntryAddRequest request)
	{
		final ResourceAssignment resourceAssignment = resourceAssignmentRepository.create(ResourceAssignmentCreateRequest.builder()
				.resourceId(toResourceId(request.getCalendarId()))
				.startDate(request.getStartDate())
				.endDate(request.getEndDate())
				.name(request.getTitle())
				.description(request.getDescription())
				.build());

		return toCalendarEntry(resourceAssignment);
	}
}
