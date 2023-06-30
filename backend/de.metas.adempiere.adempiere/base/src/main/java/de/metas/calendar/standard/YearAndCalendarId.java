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

package de.metas.calendar.standard;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class YearAndCalendarId
{
	@Getter
	YearId yearId;

	@Getter
	CalendarId calendarId;

	@JsonCreator
	public static YearAndCalendarId ofRepoId(final int calendarId, final int yearId)
	{
		return new YearAndCalendarId(CalendarId.ofRepoId(calendarId), YearId.ofRepoId(yearId));
	}

	@Nullable
	public static YearAndCalendarId ofRepoIdOrNull(@Nullable final Integer calendarId, @Nullable final Integer yearId)
	{
		return
				calendarId != null && calendarId > 0 && yearId != null && yearId > 0
						? new YearAndCalendarId(CalendarId.ofRepoId(calendarId), YearId.ofRepoId(yearId))
						: null;
	}

	private YearAndCalendarId(@NonNull final CalendarId calendarId, @NonNull final YearId yearId)
	{
		this.calendarId = calendarId;
		this.yearId = yearId;
	}
}
