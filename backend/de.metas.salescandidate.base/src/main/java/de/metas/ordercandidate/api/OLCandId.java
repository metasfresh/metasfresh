/*
 * #%L
 * de.metas.salescandidate.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ordercandidate.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class OLCandId implements RepoIdAware
{
	@JsonCreator
	public static OLCandId ofRepoId(final int repoId)
	{
		return new OLCandId(repoId);
	}

	public static OLCandId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new OLCandId(repoId) : null;
	}

	public static int toRepoId(@Nullable final OLCandId olCandId)
	{
		return olCandId != null ? olCandId.getRepoId() : -1;
	}

	int repoId;

	private OLCandId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_OLCand_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}