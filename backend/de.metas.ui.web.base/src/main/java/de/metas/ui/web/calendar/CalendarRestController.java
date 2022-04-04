/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.calendar;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.CalendarEntry;
import de.metas.calendar.CalendarEntryAddRequest;
import de.metas.calendar.CalendarQuery;
import de.metas.calendar.MultiCalendarService;
import de.metas.ui.web.calendar.json.JsonCalendarEntriesQuery;
import de.metas.ui.web.calendar.json.JsonCalendarEntriesQueryResponse;
import de.metas.ui.web.calendar.json.JsonCalendarEntry;
import de.metas.ui.web.calendar.json.JsonCalendarEntryAddRequest;
import de.metas.ui.web.calendar.json.JsonCalendarRef;
import de.metas.ui.web.calendar.json.JsonGetAvailableCalendarsResponse;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.json.DateTimeConverters;
import de.metas.user.UserId;
import io.swagger.annotations.Api;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import java.time.ZoneId;

@Api
@RestController
@RequestMapping(WebConfig.ENDPOINT_ROOT + "/calendars")
public class CalendarRestController
{
	private final UserSession userSession;
	private final MultiCalendarService calendarService;

	public CalendarRestController(
			@NonNull final UserSession userSession,
			@NonNull final MultiCalendarService calendarService)
	{
		this.userSession = userSession;
		this.calendarService = calendarService;
	}

	@GetMapping("/available")
	public JsonGetAvailableCalendarsResponse getAvailableCalendars()
	{
		final UserId loggedUserId = userSession.getLoggedUserId();
		final String adLanguage = userSession.getAD_Language();

		final ImmutableList<JsonCalendarRef> jsonCalendarRefs = calendarService.streamAvailableCalendars(loggedUserId)
				.map(calendarRef -> JsonCalendarRef.of(calendarRef, adLanguage))
				.collect(ImmutableList.toImmutableList());

		return JsonGetAvailableCalendarsResponse.builder()
				.calendars(jsonCalendarRefs)
				.build();
	}

	@PostMapping("/entries/query")
	public JsonCalendarEntriesQueryResponse queryCalendarEntries(
			@RequestBody(required = false) @Nullable final JsonCalendarEntriesQuery query)
	{
		userSession.assertLoggedIn();

		final UserId loggedUserId = userSession.getLoggedUserId();
		final ZoneId timeZone = userSession.getTimeZone();

		final ImmutableList<JsonCalendarEntry> jsonEntries = calendarService.query(toCalendarQuery(query, loggedUserId))
				.map(entry -> JsonCalendarEntry.of(entry, timeZone))
				.collect(ImmutableList.toImmutableList());

		return JsonCalendarEntriesQueryResponse.builder()
				.entries(jsonEntries)
				.build();
	}

	private static CalendarQuery toCalendarQuery(@Nullable final JsonCalendarEntriesQuery query, @NonNull final UserId loggedUserId)
	{
		final CalendarQuery.CalendarQueryBuilder result = CalendarQuery.builder()
				.availableForUserId(loggedUserId);

		if (query != null)
		{
			result.onlyCalendarIds(query.getCalendarIds());

			if (query.getStartDate() != null)
			{
				result.startDate(DateTimeConverters.fromObjectToZonedDateTime(query.getStartDate()));
			}

			if (query.getEndDate() != null)
			{
				result.endDate(DateTimeConverters.fromObjectToZonedDateTime(query.getEndDate()));
			}
		}

		return result.build();
	}

	@PostMapping("/entries/add")
	public JsonCalendarEntry addCalendarEntry(@NonNull final JsonCalendarEntryAddRequest request)
	{
		userSession.assertLoggedIn();

		final CalendarEntry calendarEntry = calendarService.addEntry(CalendarEntryAddRequest.builder()
				.userId(userSession.getLoggedUserId())
				.calendarId(request.getCalendarId())
				.startDate(DateTimeConverters.fromObjectToZonedDateTime(request.getStartDate()))
				.endDate(DateTimeConverters.fromObjectToZonedDateTime(request.getEndDate()))
				.title(request.getTitle())
				.description(request.getDescription())
				.build());

		return JsonCalendarEntry.of(calendarEntry, userSession.getTimeZone());
	}
}
