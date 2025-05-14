/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.postgrest.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;

import javax.annotation.Nullable;

public class PostgRESTConfigId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static PostgRESTConfigId ofRepoId(final int repoId)
	{
		return new PostgRESTConfigId(repoId);
	}

	public static int toRepoId(@Nullable final PostgRESTConfigId postgRESTConfigId)
	{
		return postgRESTConfigId != null ? postgRESTConfigId.getRepoId() : -1;
	}

	private PostgRESTConfigId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "postgRESTConfigId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
