/*
 * #%L
 * de.metas.vatid
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.vatid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Typed id of a {@code VATaxID_CheckLog} record — one row per VAT-ID online check attempt.
 */
@Value
public class VATaxIDCheckLogId implements RepoIdAware
{
	int repoId;

	private VATaxIDCheckLogId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "VATaxID_CheckLog_ID");
	}

	@JsonCreator
	public static VATaxIDCheckLogId ofRepoId(final int repoId)
	{
		return new VATaxIDCheckLogId(repoId);
	}

	@Nullable
	public static VATaxIDCheckLogId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new VATaxIDCheckLogId(repoId) : null;
	}

	public static int toRepoId(@Nullable final VATaxIDCheckLogId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
