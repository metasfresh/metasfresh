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
import lombok.Builder;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
public class AsyncBatchMilestoneQuery
{
	@Nullable
	AsyncBatchId asyncBatchId;

	@Nullable
	Boolean processed;

	@Builder
	public AsyncBatchMilestoneQuery(
			@Nullable final AsyncBatchId asyncBatchId,
			@Nullable final Boolean processed)
	{
		if (asyncBatchId == null && processed == null)
		{
			throw new AdempiereException("At least one of attributes: asyncBatchId or processed needs to be provided when creating an AsyncBatchMilestoneQuery");
		}

		this.asyncBatchId = asyncBatchId;
		this.processed = processed;
	}
}
