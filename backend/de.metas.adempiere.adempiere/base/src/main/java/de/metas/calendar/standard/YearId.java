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
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class YearId implements RepoIdAware
{
	int repoId;

	CalendarId calendarId;

	@JsonCreator
	public static YearId ofRepoId(final int calendarId, final int repoId)
	{
		return new YearId(CalendarId.ofRepoId(calendarId), repoId);
	}

	@Nullable
	public static YearId ofRepoIdOrNull(@Nullable final Integer calendarId, @Nullable final Integer repoId)
	{
		return
				calendarId != null && calendarId > 0 && repoId != null && repoId > 0
						? new YearId(CalendarId.ofRepoId(calendarId), repoId)
						: null;
	}

	public static int toRepoId(@Nullable final YearId yearId)
	{
		return yearId != null ? yearId.getRepoId() : -1;
	}

	private YearId(@NonNull final CalendarId calendarId, final int repoId)
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
