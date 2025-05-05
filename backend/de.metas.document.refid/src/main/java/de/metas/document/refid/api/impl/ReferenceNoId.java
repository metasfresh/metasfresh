/*
 * #%L
 * de.metas.document.refid
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

package de.metas.document.refid.api.impl;

import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class ReferenceNoId implements RepoIdAware
{
	int repoId;

	public static ReferenceNoId ofRepoId(final int repoId)
	{
		return new ReferenceNoId(repoId);
	}

	@Nullable
	public static ReferenceNoId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new ReferenceNoId(repoId) : null;
	}

	private ReferenceNoId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_ReferenceNo");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final ReferenceNoId referenceNoId)
	{
		return toRepoIdOr(referenceNoId, -1);
	}

	public static int toRepoIdOr(@Nullable final ReferenceNoId referenceNoId, final int defaultValue)
	{
		return referenceNoId != null ? referenceNoId.getRepoId() : defaultValue;
	}

	public static boolean equals(@Nullable final ReferenceNoId id1, @Nullable final ReferenceNoId id2)
	{
		return Objects.equals(id1, id2);
	}
}
