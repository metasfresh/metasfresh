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

import de.metas.async.AsyncBatchId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
public class AsyncBatchMilestone
{
	@Nullable
	AsyncBatchMilestoneId id;

	@NonNull
	AsyncBatchId asyncBatchId;

	@NonNull
	OrgId orgId;

	@NonNull
	MilestoneName milestoneName;

	boolean processed;

	@Builder(toBuilder = true)
	AsyncBatchMilestone(
			@Nullable final AsyncBatchMilestoneId id,
			@NonNull final AsyncBatchId asyncBatchId,
			@NonNull final OrgId orgId,
			@NonNull final MilestoneName milestoneName,
			final boolean processed)
	{
		this.id = id;
		this.asyncBatchId = asyncBatchId;
		this.orgId = orgId;
		this.milestoneName = milestoneName;
		this.processed = processed;
	}

	@NonNull
	public AsyncBatchMilestoneId getIdNotNull()
	{
		if (id == null)
		{
			throw new AdempiereException("getIdNotNull() should be called only for already persisted AsyncBatchMilestone record!")
					.appendParametersToMessage()
					.setParameter("AsyncBatchMilestoneId", this);
		}
		return id;
	}
}
