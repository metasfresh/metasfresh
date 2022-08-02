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
import lombok.Value;

@Value
public class CalendarId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static CalendarId ofRepoId(final int repoId)
	{
		return new CalendarId(repoId);
	}

	public static CalendarId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new CalendarId(repoId) : null;
	}

	public static int toRepoId(final CalendarId CalendarId)
	{
		return CalendarId != null ? CalendarId.getRepoId() : -1;
	}

	private CalendarId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}
}
