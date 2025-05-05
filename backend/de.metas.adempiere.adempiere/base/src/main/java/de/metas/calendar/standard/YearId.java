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
import de.metas.util.lang.RepoIdAwares;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@RepoIdAwares.SkipTest
public class YearId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static YearId ofRepoId(final int repoId)
	{
		return new YearId(repoId);
	}

	@Nullable
	public static YearId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return
				repoId != null && repoId > 0
						? ofRepoId(repoId)
						: null;
	}

	public static int toRepoId(@Nullable final YearId yearId)
	{
		return yearId != null ? yearId.getRepoId() : -1;
	}

	private YearId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Year_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}
}
