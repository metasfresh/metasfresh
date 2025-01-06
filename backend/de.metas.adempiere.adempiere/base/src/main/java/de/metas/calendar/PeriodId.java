/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.calendar.standard.YearId;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class PeriodId implements RepoIdAware
{
	int repoId;

	@Getter
	YearId yearId;

	@JsonCreator
	public static PeriodId ofRepoId(@NonNull final YearId yearId, final int repoId)
	{
		return new PeriodId(yearId, repoId);
	}

	private PeriodId(@Nullable final YearId yearAndCalendarId, final int repoId)
	{
		this.yearId = yearAndCalendarId;
		this.repoId = Check.assumeGreaterThanZero(repoId, "periodId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
