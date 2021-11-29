/*
 * #%L
 * de.metas.async
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

package de.metas.async.asyncbatchmilestone;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.async.AsyncBatchId;
import de.metas.async.model.I_C_Async_Batch_Milestone;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class AsyncBatchMilestoneId implements RepoIdAware
{
	int repoId;

	@NonNull
	AsyncBatchId asyncBatchId;

	@JsonCreator
	public static AsyncBatchMilestoneId ofRepoId(@NonNull final AsyncBatchId asyncBatchId, final int repoId)
	{
		return new AsyncBatchMilestoneId(asyncBatchId, repoId);
	}

	@Nullable
	public static AsyncBatchMilestoneId ofRepoIdOrNull(@Nullable final AsyncBatchId asyncBatchId, final int repoId)
	{
		return asyncBatchId != null && repoId > 0 ? new AsyncBatchMilestoneId(asyncBatchId, repoId) : null;
	}

	public static int toRepoId(@Nullable final AsyncBatchMilestoneId AsyncBatchMilestoneId)
	{
		return AsyncBatchMilestoneId != null ? AsyncBatchMilestoneId.getRepoId() : -1;
	}

	private AsyncBatchMilestoneId(final @NonNull AsyncBatchId asyncBatchId, final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_C_Async_Batch_Milestone.COLUMNNAME_C_Async_Batch_Milestone_ID);
		this.asyncBatchId = asyncBatchId;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
