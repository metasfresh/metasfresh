/*
 * #%L
 * de.metas.async
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

package de.metas.async.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import java.util.Optional;

@Value
public class AsyncBatchTypeId implements RepoIdAware
{
	int repoId;

	private AsyncBatchTypeId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Async_Batch_Type_ID");
	}

	@JsonCreator
	public static AsyncBatchTypeId ofRepoId(final int repoId)
	{
		return new AsyncBatchTypeId(repoId);
	}

	public static AsyncBatchTypeId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new AsyncBatchTypeId(repoId) : null;
	}

	public static Optional<AsyncBatchTypeId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
