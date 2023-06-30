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
import lombok.NonNull;

import javax.annotation.Nullable;

public record YearAndCalendarId(@NonNull YearId yearId, @NonNull CalendarId calendarId)
{
	@JsonCreator
	public static YearAndCalendarId ofRepoId(final int yearId, final int calendarId)
	{
		return ofRepoId(YearId.ofRepoId(yearId), CalendarId.ofRepoId(calendarId));
	}

	@JsonCreator
	public static YearAndCalendarId ofRepoId(@NonNull final YearId yearId, @NonNull final CalendarId calendarId)
	{
		return new YearAndCalendarId(yearId, calendarId);
	}

	@Nullable
	public static YearAndCalendarId ofRepoIdOrNull(@Nullable final Integer yearId, @Nullable final Integer calendarId)
	{
		return
				calendarId != null && calendarId > 0 && yearId != null && yearId > 0
						? new YearAndCalendarId(YearId.ofRepoId(yearId), CalendarId.ofRepoId(calendarId))
						: null;
	}
}
