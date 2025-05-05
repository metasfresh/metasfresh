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

package de.metas.ui.web.calendar.json;

import de.metas.calendar.CalendarEntry;
import de.metas.calendar.CalendarEntryId;
import de.metas.calendar.CalendarGlobalId;
import de.metas.calendar.CalendarResourceId;
import de.metas.calendar.simulation.SimulationPlanId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.time.ZoneId;

@Value
@Builder
@Jacksonized
public class JsonCalendarEntry
{
	@NonNull CalendarEntryId entryId;

	@Nullable SimulationPlanId simulationId;

	@NonNull CalendarGlobalId calendarId;
	@NonNull CalendarResourceId resourceId;

	@Nullable String title;
	@Nullable String description;

	@NonNull JsonDateTime startDate;
	@NonNull JsonDateTime endDate;
	boolean allDay;

	boolean editable;
	@Nullable String color;
	@Nullable String url;
	@Nullable String help;

	public static JsonCalendarEntry of(
			@NonNull final CalendarEntry entry,
			@NonNull final ZoneId timeZone,
			@NonNull final String adLanguage)
	{
		return builder()
				.entryId(entry.getEntryId())
				.simulationId(entry.getSimulationId())
				.calendarId(entry.getCalendarId())
				.resourceId(entry.getResourceId())
				.title(entry.getTitle().translate(adLanguage))
				.description(entry.getDescription().translate(adLanguage))
				.startDate(JsonDateTime.ofInstant(entry.getDateRange().getStartDate(), timeZone))
				.endDate(JsonDateTime.ofInstant(entry.getDateRange().getEndDate(), timeZone))
				.allDay(entry.getDateRange().isAllDay())
				.editable(entry.isEditable())
				.color(entry.getColor())
				.url(entry.getUrl() != null ? entry.getUrl().toString() : null)
				.help(entry.getHelp())
				.build();
	}
}
