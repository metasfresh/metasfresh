package de.metas.process;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class AdProcessId implements RepoIdAware
{
	@JsonCreator
	public static AdProcessId ofRepoId(final int repoId)
	{
		return new AdProcessId(repoId);
	}

	public static AdProcessId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new AdProcessId(repoId) : null;
	}

	public static int toRepoId(@Nullable final AdProcessId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private AdProcessId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "AD_Process_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final AdProcessId id1, @Nullable final AdProcessId id2) { return Objects.equals(id1, id2); }
}
