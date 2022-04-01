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
import de.metas.calendar.CalendarEntry;
import de.metas.calendar.CalendarGlobalId;
import de.metas.calendar.CalendarQuery;
import de.metas.calendar.CalendarRef;
import de.metas.calendar.CalendarResourceId;
import de.metas.calendar.CalendarService;
import de.metas.calendar.CalendarServiceId;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.ResourceId;
import de.metas.user.UserId;
import lombok.NonNull;
import org.compiere.model.I_S_Resource;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

@Component
public class ResourceAssignmentCalendarService implements CalendarService
{
	private static final CalendarServiceId ID = CalendarServiceId.ofString("ResourceAssignment");
	private static final CalendarGlobalId CALENDAR_ID = CalendarGlobalId.of(ID, "standard");
	private static final CalendarRef CALENDAR_REF = CalendarRef.of(
			CALENDAR_ID,
			TranslatableStrings.anyLanguage("Resource Assignment Calendar"));

	private final ResourceAssignmentRepository resourceAssignmentRepository;

	public ResourceAssignmentCalendarService(
			@NonNull final ResourceAssignmentRepository resourceAssignmentRepository)
	{
		this.resourceAssignmentRepository = resourceAssignmentRepository;
	}

	@Override
	public List<CalendarRef> getAvailableCalendars(final UserId userId)
	{
		return ImmutableList.of(CALENDAR_REF);
	}

	@Override
	public Stream<CalendarEntry> query(@NonNull final CalendarQuery query)
	{
		final ResourceAssignmentQuery resourceAssignmentQuery = toResourceAssignmentQuery(query);
		if (resourceAssignmentQuery == null)
		{
			return Stream.empty();
		}

		return resourceAssignmentRepository.query(resourceAssignmentQuery)
				.map(ResourceAssignmentCalendarService::toCalendarEntry);
	}

	@Nullable
	private static ResourceAssignmentQuery toResourceAssignmentQuery(final CalendarQuery calendarQuery)
	{
		if (!calendarQuery.isMatchingCalendarServiceId(ID))
		{
			return null;
		}
		if (!calendarQuery.isMatchingCalendarId(CALENDAR_ID))
		{
			return null;
		}

		return ResourceAssignmentQuery.builder()
				.build();
	}

	private static CalendarEntry toCalendarEntry(@NonNull final ResourceAssignment resourceAssignment)
	{
		return CalendarEntry.builder()
				.entryId(resourceAssignment.getId().getAsString())
				.calendarId(CALENDAR_ID)
				.resourceId(toCalendarResourceId(resourceAssignment.getResourceId()))
				.startDate(resourceAssignment.getStartDate())
				.endDate(resourceAssignment.getEndDate())
				.title(resourceAssignment.getName())
				.description(resourceAssignment.getDescription())
				.build();
	}

	private static CalendarResourceId toCalendarResourceId(@NonNull final ResourceId resourceId)
	{
		return CalendarResourceId.of(I_S_Resource.Table_Name, String.valueOf(resourceId.getRepoId()));
	}
}
