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
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class YearAndCalendarId
{
	int repoId;

	CalendarId calendarId;

	@JsonCreator
	public static YearAndCalendarId ofRepoId(final int calendarId, final int repoId)
	{
		return new YearAndCalendarId(CalendarId.ofRepoId(calendarId), repoId);
	}

	@Nullable
	public static YearAndCalendarId ofRepoIdOrNull(@Nullable final Integer calendarId, @Nullable final Integer repoId)
	{
		return
				calendarId != null && calendarId > 0 && repoId != null && repoId > 0
						? new YearAndCalendarId(CalendarId.ofRepoId(calendarId), repoId)
						: null;
	}

	public static int toYearRepoId(@Nullable final YearAndCalendarId yearAndCalendarId)
	{
		return yearAndCalendarId != null ? yearAndCalendarId.getRepoId() : -1;
	}

	private YearAndCalendarId(@NonNull final CalendarId calendarId, final int repoId)
	{
		this.calendarId = calendarId;
		this.repoId = Check.assumeGreaterThanZero(repoId, "yearId");

	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}
}
