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

package de.metas.ordercandidate.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.asyncbatchmilestone.AsyncBatchMilestoneService;
import de.metas.order.OrderId;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.api.OLCandId;
import de.metas.ordercandidate.api.async.C_OLCandToOrderEnqueuer;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static de.metas.async.Async_Constants.C_Async_Batch_InternalName_OLCand_Processing;
import static de.metas.async.Async_Constants.C_OlCandProcessor_ID_Default;
import static de.metas.async.asyncbatchmilestone.MilestoneName.SALES_ORDER_CREATION;

@Service
public class OrderService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IOLCandDAO olCandDAO = Services.get(IOLCandDAO.class);
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);

	private final AsyncBatchMilestoneService asyncBatchMilestoneService;
	private final C_OLCandToOrderEnqueuer olCandToOrderEnqueuer;

	public OrderService(
			@NonNull final AsyncBatchMilestoneService asyncBatchMilestoneService,
			@NonNull final C_OLCandToOrderEnqueuer olCandToOrderEnqueuer)
	{
		this.asyncBatchMilestoneService = asyncBatchMilestoneService;
		this.olCandToOrderEnqueuer = olCandToOrderEnqueuer;
	}

	@NonNull
	public Set<OrderId> generateOrderSync(@NonNull final Map<AsyncBatchId, List<OLCandId>> asyncBatchId2OLCandIds)
	{
		if (asyncBatchId2OLCandIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		asyncBatchId2OLCandIds.keySet().forEach(this::generateOrdersForBatch);

		final ImmutableSet<OLCandId> olCandIds = asyncBatchId2OLCandIds.values()
				.stream()
				.flatMap(List::stream)
				.collect(ImmutableSet.toImmutableSet());

		return olCandDAO.getOrderIdsByOLCandIds(olCandIds);
	}

	@NonNull
	public ImmutableMap<AsyncBatchId, List<OLCandId>> getAsyncBathId2OLCandIds(@NonNull final Set<OLCandId> olCandIds)
	{
		if (olCandIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final Map<OLCandId, I_C_OLCand> olCandsById = olCandDAO.retrieveByIds(olCandIds);

		final HashMap<AsyncBatchId, ArrayList<OLCandId>> asyncBatchId2OLCands = new HashMap<>();

		olCandsById.values()
				.forEach(olCand -> {
					final AsyncBatchId currentAsyncBatchId = AsyncBatchId.ofRepoIdOrNone(olCand.getC_Async_Batch_ID());

					final ArrayList<OLCandId> currentOLCands = new ArrayList<>();
					currentOLCands.add(OLCandId.ofRepoId(olCand.getC_OLCand_ID()));

					asyncBatchId2OLCands.merge(currentAsyncBatchId, currentOLCands, CollectionUtils::mergeLists);
				});

		Optional.ofNullable(asyncBatchId2OLCands.get(AsyncBatchId.NONE_ASYNC_BATCH_ID))
				.ifPresent(noAsyncBatchOLCands -> {
					final AsyncBatchId asyncBatchId = asyncBatchBL.newAsyncBatch(C_Async_Batch_InternalName_OLCand_Processing);

					olCandDAO.assignAsyncBatchId(olCandIds, asyncBatchId);

					asyncBatchId2OLCands.put(asyncBatchId, noAsyncBatchOLCands);
					asyncBatchId2OLCands.remove(AsyncBatchId.NONE_ASYNC_BATCH_ID);
				});

		return ImmutableMap.copyOf(asyncBatchId2OLCands);
	}

	private void generateOrdersForBatch(@NonNull final AsyncBatchId asyncBatchId)
	{
		final Supplier<Void> action = () -> {
			trxManager.runInNewTrx(
					() -> olCandToOrderEnqueuer.enqueue(C_OlCandProcessor_ID_Default, asyncBatchId));
			return null;
		};

		asyncBatchMilestoneService.executeMilestone(action, asyncBatchId, SALES_ORDER_CREATION);
	}
}
