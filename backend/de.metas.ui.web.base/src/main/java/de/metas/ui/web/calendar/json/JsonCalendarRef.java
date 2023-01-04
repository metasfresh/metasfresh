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

import com.google.common.collect.ImmutableSet;
import de.metas.calendar.CalendarGlobalId;
import de.metas.calendar.CalendarRef;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Value
@Builder
@Jacksonized
public class JsonCalendarRef
{
	@NonNull CalendarGlobalId calendarId;
	@NonNull String name;
	@NonNull Set<JsonCalendarResourceRef> resources;

	public static JsonCalendarRef of(
			@NonNull final CalendarRef calendarRef,
			@NonNull final String adLanguage)
	{
		return builder()
				.calendarId(calendarRef.getCalendarId())
				.name(calendarRef.getName().translate(adLanguage))
				.resources(calendarRef.getResources().stream()
						.map(resource -> JsonCalendarResourceRef.of(resource, adLanguage))
						.collect(ImmutableSet.toImmutableSet()))
				.build();
	}
}
