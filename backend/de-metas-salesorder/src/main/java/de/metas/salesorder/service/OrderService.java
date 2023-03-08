/*
 * #%L
 * de-metas-salesorder
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

package de.metas.salesorder.service;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IEnqueueResult;
import de.metas.async.service.AsyncBatchService;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor;
import de.metas.logging.LogManager;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.api.OLCandId;
import de.metas.ordercandidate.api.async.C_OLCandToOrderEnqueuer;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import de.metas.common.util.pair.ImmutablePair;
import org.compiere.model.I_C_Order;
import org.compiere.model.X_C_Order;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static de.metas.async.Async_Constants.C_Async_Batch_InternalName_EnqueueScheduleForOrder;
import static de.metas.async.Async_Constants.C_Async_Batch_InternalName_OLCand_Processing;

@Service
public class OrderService
{
	private static final Logger logger = LogManager.getLogger(OrderService.class);

	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final IOLCandDAO olCandDAO = Services.get(IOLCandDAO.class);
	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);

	private final AsyncBatchService asyncBatchService;

	private final C_OLCandToOrderEnqueuer olCandToOrderEnqueuer;

	public OrderService(
			@NonNull final AsyncBatchService asyncBatchService,
			@NonNull final C_OLCandToOrderEnqueuer olCandToOrderEnqueuer)
	{
		this.asyncBatchService = asyncBatchService;
		this.olCandToOrderEnqueuer = olCandToOrderEnqueuer;
	}

	@NonNull
	public Set<OrderId> generateOrderSync(@NonNull final Map<AsyncBatchId, List<OLCandId>> asyncBatchId2OLCandIds)
	{
		if (asyncBatchId2OLCandIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		for (final AsyncBatchId asyncBatchId : asyncBatchId2OLCandIds.keySet())
		{
			generateOrdersForBatch(asyncBatchId);
		}

		final ImmutableSet<OLCandId> olCandIds = asyncBatchId2OLCandIds.values()
				.stream()
				.flatMap(List::stream)
				.collect(ImmutableSet.toImmutableSet());

		return olCandDAO.getOrderIdsByOLCandIds(olCandIds);
	}

	@NonNull
	public ImmutableMap<AsyncBatchId, List<OLCandId>> getAsyncBatchId2OLCandIds(@NonNull final Set<OLCandId> olCandIds)
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

					// the asyncBatchId will be propagated through C_OLCand, M_ShipmentSchedule and C_Invoice_candidate.
					// If will be used when we create workpackages (and wait for them!) that in turn create actual the actual orders, shipments and invoices
					final AsyncBatchId asyncBatchId = asyncBatchBL.newAsyncBatch(C_Async_Batch_InternalName_OLCand_Processing);

					olCandDAO.assignAsyncBatchId(olCandIds, asyncBatchId);

					asyncBatchId2OLCands.put(asyncBatchId, noAsyncBatchOLCands);
					asyncBatchId2OLCands.remove(AsyncBatchId.NONE_ASYNC_BATCH_ID);
				});

		return ImmutableMap.copyOf(asyncBatchId2OLCands);
	}

	@NonNull
	public ImmutableSet<ShipmentScheduleId> generateSchedules(@NonNull final OrderId orderId)
	{
		final I_C_Order order = orderDAO.getById(orderId);

		if (!order.getDocStatus().equals(X_C_Order.DOCSTATUS_Completed))
		{
			Loggables.withLogger(logger, Level.INFO).addLog("Returning! Order not COMPLETED!");
			return ImmutableSet.of();
		}

		generateMissingShipmentSchedulesFromOrder(order);

		return shipmentSchedulePA.retrieveScheduleIdsByOrderId(orderId);
	}

	private void generateOrdersForBatch(@NonNull final AsyncBatchId asyncBatchId)
	{
		final Supplier<IEnqueueResult> action = () -> olCandToOrderEnqueuer.enqueueBatch(asyncBatchId);

		asyncBatchService.executeBatch(action, asyncBatchId);
	}

	private void generateMissingShipmentSchedulesFromOrder(@NonNull final I_C_Order order)
	{
		final ImmutablePair<AsyncBatchId, I_C_Order> batchIdWithOrder = asyncBatchBL.assignPermAsyncBatchToModelIfMissing(order, C_Async_Batch_InternalName_EnqueueScheduleForOrder);

		final Supplier<IEnqueueResult> action = () -> CreateMissingShipmentSchedulesWorkpackageProcessor.scheduleIfNotPostponed(batchIdWithOrder.getRight());

		asyncBatchService.executeBatch(action, batchIdWithOrder.getLeft());
	}
}
