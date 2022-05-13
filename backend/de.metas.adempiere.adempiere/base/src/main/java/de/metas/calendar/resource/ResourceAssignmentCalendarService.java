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

import com.google.common.collect.ImmutableSet;
import de.metas.calendar.CalendarEntry;
import de.metas.calendar.CalendarEntryAddRequest;
import de.metas.calendar.CalendarEntryId;
import de.metas.calendar.CalendarGlobalId;
import de.metas.calendar.CalendarQuery;
import de.metas.calendar.CalendarRef;
import de.metas.calendar.CalendarResourceId;
import de.metas.calendar.CalendarResourceRef;
import de.metas.calendar.CalendarService;
import de.metas.calendar.CalendarServiceId;
import de.metas.i18n.TranslatableStrings;
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
import java.util.stream.Stream;

@Component
public class ResourceAssignmentCalendarService implements CalendarService
{
	private static final CalendarServiceId ID = CalendarServiceId.ofString("ResourceAssignment");

	private static final CalendarGlobalId CALENDAR_ID = CalendarGlobalId.of(ID, "default");

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
	public Stream<CalendarRef> streamAvailableCalendars(final UserId userId)
	{
		final CalendarRef calendar = CalendarRef.builder()
				.calendarId(CALENDAR_ID)
				.name(TranslatableStrings.adElementOrMessage("Default"))
				.resources(resourceRepository.getResources()
						.stream()
						.map(ResourceAssignmentCalendarService::toCalendarResourceRef)
						.collect(ImmutableSet.toImmutableSet()))
				.build();

		return Stream.of(calendar);
	}

	private static CalendarResourceRef toCalendarResourceRef(final Resource resource)
	{
		return CalendarResourceRef.of(CalendarResourceId.ofRepoId(resource.getResourceId()), resource.getName());
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
		if (!calendarQuery.isMatchingCalendarId(CALENDAR_ID))
		{
			return null;
		}

		final ImmutableSet<ResourceId> onlyResourceIds;
		if (!calendarQuery.getOnlyResourceIds().isEmpty())
		{
			onlyResourceIds = calendarQuery.getOnlyResourceIds()
					.stream()
					.map(calendarResourceId -> calendarResourceId.toRepoId(ResourceId.class))
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
				.entryId(CalendarEntryId.ofRepoId(resourceAssignment.getId()))
				.calendarId(CALENDAR_ID)
				.resourceId(CalendarResourceId.ofRepoId(resourceAssignment.getResourceId()))
				.startDate(resourceAssignment.getStartDate())
				.endDate(resourceAssignment.getEndDate())
				.title(resourceAssignment.getName())
				.description(resourceAssignment.getDescription())
				.build();
	}

	@Override
	public CalendarEntry addEntry(@NonNull final CalendarEntryAddRequest request)
	{
		if (request.getCalendarId() != null && !request.getCalendarId().equals(CALENDAR_ID))
		{
			throw new AdempiereException("Invalid calendar ID: " + request.getCalendarId());
		}

		if (request.getResourceId() == null)
		{
			throw new AdempiereException("Fill mandatory `resourceId`");
		}

		final ResourceAssignment resourceAssignment = resourceAssignmentRepository.create(ResourceAssignmentCreateRequest.builder()
				.resourceId(request.getResourceId().toRepoId(ResourceId.class))
				.startDate(request.getStartDate())
				.endDate(request.getEndDate())
				.name(request.getTitle())
				.description(request.getDescription())
				.build());

		return toCalendarEntry(resourceAssignment);
	}
}
