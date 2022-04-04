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

package de.metas.calendar;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class MultiCalendarService
{
	private final ImmutableList<CalendarService> calendarServices;
	private final ImmutableMap<CalendarServiceId, CalendarService> calendarServicesById;

	public MultiCalendarService(
			@NonNull final List<CalendarService> calendarServices)
	{
		this.calendarServices = ImmutableList.copyOf(calendarServices);
		this.calendarServicesById = Maps.uniqueIndex(calendarServices, CalendarService::getCalendarServiceId);
	}

	private CalendarService getCalendarServiceById(@NonNull final CalendarServiceId calendarServiceId)
	{
		final CalendarService calendarService = calendarServicesById.get(calendarServiceId);
		if (calendarService == null)
		{
			throw new AdempiereException("No calendar service found for " + calendarServiceId);
		}
		return calendarService;
	}

	public Stream<CalendarRef> streamAvailableCalendars(@NonNull final UserId userId)
	{
		return calendarServices.stream()
				.flatMap(calendarService -> calendarService.getAvailableCalendars(userId).stream());
	}

	public Stream<CalendarEntry> query(@NonNull final CalendarQuery query)
	{
		return calendarServices.stream()
				.flatMap(calendarService -> calendarService.query(query));
	}

	public CalendarEntry addEntry(@NonNull final CalendarEntryAddRequest request)
	{
		return getCalendarServiceById(request.getCalendarId().getCalendarServiceId())
				.addEntry(request);
	}
}
