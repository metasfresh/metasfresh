package de.metas.marketing.base.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

/*
 * #%L
 * marketing-base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class PlatformId implements RepoIdAware
{
	int repoId;

	private PlatformId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "MKTG_Platform_ID");
	}

	public static PlatformId ofRepoId(final int repoId)
	{
		return new PlatformId(repoId);
	}

	@Nullable
	public static PlatformId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? ofRepoId(repoId) : null;}

	@JsonCreator
	public static PlatformId ofString(final String string)
	{
		return ofRepoId(Integer.parseInt(string));
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}

	public static boolean equals(@Nullable final PlatformId id1, @Nullable final PlatformId id2)
	{
		return Objects.equals(id1, id2);
	}
}
