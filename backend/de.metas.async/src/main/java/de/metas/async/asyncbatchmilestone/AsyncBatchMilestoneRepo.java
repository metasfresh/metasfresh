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

import com.google.common.collect.ImmutableList;
import de.metas.async.AsyncBatchId;
import de.metas.async.model.I_C_Async_Batch_Milestone;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.loadOrNew;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class AsyncBatchMilestoneRepo
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public AsyncBatchMilestone save(@NonNull final AsyncBatchMilestone request)
	{
		final I_C_Async_Batch_Milestone record = prepareAsyncBatchMilestone(request);
		saveRecord(record);

		return toAsyncBatchMilestone(record);
	}

	@NonNull
	public AsyncBatchMilestone getById(@NonNull final AsyncBatchMilestoneId id)
	{
		final I_C_Async_Batch_Milestone record = queryBL.createQueryBuilder(I_C_Async_Batch_Milestone.class)
				.addEqualsFilter(I_C_Async_Batch_Milestone.COLUMN_C_Async_Batch_Milestone_ID, id)
				.create()
				.firstOnlyNotNull(I_C_Async_Batch_Milestone.class);

		return toAsyncBatchMilestone(record);
	}

	@NonNull
	public List<AsyncBatchMilestone> getByQuery(@NonNull final AsyncBatchMilestoneQuery query)
	{
		final IQueryBuilder<I_C_Async_Batch_Milestone> queryBuilder = queryBL.createQueryBuilder(I_C_Async_Batch_Milestone.class);

		if (query.getAsyncBatchId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_Async_Batch_Milestone.COLUMNNAME_C_Async_Batch_ID, query.getAsyncBatchId());
		}

		if (query.getProcessed() != null)
		{
			queryBuilder.addEqualsFilter(I_C_Async_Batch_Milestone.COLUMNNAME_Processed, query.getProcessed());
		}

		return queryBuilder
				.create()
				.stream()
				.map(this::toAsyncBatchMilestone)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private I_C_Async_Batch_Milestone prepareAsyncBatchMilestone(@NonNull final AsyncBatchMilestone request)
	{
		final I_C_Async_Batch_Milestone record = loadOrNew(request.getId(), I_C_Async_Batch_Milestone.class);

		record.setC_Async_Batch_ID(request.getAsyncBatchId().getRepoId());
		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setName(request.getMilestoneName().getCode());
		record.setProcessed(request.isProcessed());

		return record;
	}

	@NonNull
	private AsyncBatchMilestone toAsyncBatchMilestone(@NonNull final I_C_Async_Batch_Milestone record)
	{
		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoId(record.getC_Async_Batch_ID());

		return AsyncBatchMilestone.builder()
				.id(AsyncBatchMilestoneId.ofRepoId(asyncBatchId, record.getC_Async_Batch_Milestone_ID()))
				.asyncBatchId(asyncBatchId)
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.milestoneName(MilestoneName.ofCode(record.getName()))
				.processed(record.isProcessed())
				.build();
	}
}
