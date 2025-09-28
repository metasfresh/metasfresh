/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.shipmentschedule.api;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.service.AsyncBatchService;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.shipmentschedule.api.GenerateShipmentsRequest.GenerateShipmentsRequestBuilder;
import de.metas.handlingunits.shipmentschedule.api.impl.ShipmentServiceTestImpl;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.order.DeliveryRule;
import de.metas.order.OrderLineId;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.api.OLCandId;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.process.IADPInstanceDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static de.metas.async.Async_Constants.C_Async_Batch_InternalName_ShipmentSchedule;

@Service
@RequiredArgsConstructor
public class ShipmentService implements IShipmentService
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
	private final IOLCandDAO olCandDAO = Services.get(IOLCandDAO.class);

	@NonNull private final AsyncBatchService asyncBatchService;
	@NonNull private final ShipmentScheduleWithHUService shipmentScheduleWithHUService;

	@NonNull
	public static IShipmentService getInstance()
	{
		if (Adempiere.isUnitTestMode())
		{
			return ShipmentServiceTestImpl.newInstanceForUnitTesting();
		}
		else
		{
			return SpringContextHolder.instance.getBean(ShipmentService.class);
		}
	}

	/**
	 * <b>Important:</b> if called with {@link GenerateShipmentsRequest#isWaitForShipments()} {@code false},<br/>
	 * and there is already an unprocessed workpackage with the same shipment-schedules, the method will fail.<br/>
	 */
	@NonNull
	public ShipmentScheduleEnqueuer.Result generateShipments(@NonNull final GenerateShipmentsRequest request)
	{
		if (request.getScheduleIds().isEmpty())
		{
			throw new AdempiereException("No shipmentScheduleIds found on request!")
					.appendParametersToMessage()
					.setParameter("GenerateShipmentsRequest", request);
		}

		if (request.isWaitForShipments())
		{
			// The thread will wait until the schedules are processed, because the next call might contain the same shipment schedules as the current one.
			return asyncBatchService.executeBatch(() -> {
				validateAsyncBatchAssignment(request.getScheduleIds(), request.getAsyncBatchId());
				return enqueueShipmentSchedules(request);
			}, request.getAsyncBatchId());
		}
		else
		{
			// Just enqueue the workpackages and move on
			return enqueueShipmentSchedules(request);
		}
	}

	/**
	 * <b>Important:</b> if called with {@link GenerateShipmentsForSchedulesRequest#isWaitForShipments()} {@code false},<br/>
	 * the warning from {@link #generateShipments(GenerateShipmentsRequest)} applies.
	 */
	@NonNull
	public Set<InOutId> generateShipmentsForScheduleIds(@NonNull final GenerateShipmentsForSchedulesRequest request)
	{
		final ShipmentScheduleAndJobScheduleIdSet allScheduleIds = getEffectiveScheduleIdsToBeShipped(request);
		if (allScheduleIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final GenerateShipmentsRequestBuilder generateShipmentsRequestTemplate = GenerateShipmentsRequest.builder()
				.onlyLUIds(request.getOnlyLUIds())
				.scheduleToExternalInfo(ImmutableMap.of())
				.scheduleToQuantityToDeliverOverride(QtyToDeliverMap.EMPTY)
				.quantityTypeToUse(request.getQuantityTypeToUse())
				.onTheFlyPickToPackingInstructions(request.isOnTheFlyPickToPackingInstructions())
				.isShipDateToday(request.getIsShipDateToday())
				.isCompleteShipment(request.getIsCompleteShipment())
				.isCloseShipmentSchedules(request.isCloseShipmentSchedules())
				.waitForShipments(request.isWaitForShipments())
				// .asyncBatchId()
				// .scheduleIds()
				;

		groupSchedulesByAsyncBatch(allScheduleIds)
				.forEach((asyncBatchId, scheduleIds) -> generateShipments(
						generateShipmentsRequestTemplate
								.asyncBatchId(asyncBatchId)
								.scheduleIds(scheduleIds)
								.build()
				));

		return retrieveInOutIdsByScheduleIds(allScheduleIds.getShipmentScheduleIds());
	}

	private ShipmentScheduleAndJobScheduleIdSet getEffectiveScheduleIdsToBeShipped(@NonNull final GenerateShipmentsForSchedulesRequest request)
	{
		final ShipmentScheduleAndJobScheduleIdSet scheduleIds = request.getScheduleIds();
		if (request.getQuantityTypeToUse().isOnlyUsePicked())
		{
			final ImmutableSet<ShipmentScheduleId> selectedShipmentScheduleIds = shipmentScheduleWithHUService.retrieveNotShippedRecords(scheduleIds, request.getOnlyLUIds())
					.stream()
					.map(record -> ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()))
					.collect(ImmutableSet.toImmutableSet());

			if (selectedShipmentScheduleIds.isEmpty())
			{
				return ShipmentScheduleAndJobScheduleIdSet.EMPTY;
			}
			else if (scheduleIds != null)
			{
				return scheduleIds.retainOnlyShipmentScheduleIds(selectedShipmentScheduleIds);
			}
			else
			{
				return ShipmentScheduleAndJobScheduleIdSet.ofShipmentScheduleIds(selectedShipmentScheduleIds);
			}
		}
		else
		{
			return scheduleIds != null ? scheduleIds : ShipmentScheduleAndJobScheduleIdSet.EMPTY;
		}
	}

	/**
	 * Checks if there are enough stocks for all schedule ids to be fulfilled and if the delivery rule is `Availability`.
	 *
	 * @param scheduleIds - ids to be validated if it can be shipped
	 */
	public boolean canSchedulesBeFulfilled(@NonNull final Set<ShipmentScheduleId> scheduleIds)
	{
		final Map<ShipmentScheduleId, de.metas.inoutcandidate.model.I_M_ShipmentSchedule> shipmentSchedulesByIds = shipmentScheduleBL.getByIds(scheduleIds);

		final List<de.metas.inoutcandidate.model.I_M_ShipmentSchedule> shipmentSchedules = ImmutableList.copyOf(shipmentSchedulesByIds.values());

		boolean canSchedulesBeFulfilled = true;

		for (final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule : shipmentSchedules)
		{
			final Quantity qtyOrdered = shipmentScheduleBL.getQtyOrdered(shipmentSchedule);
			final Quantity qtyToDeliver = shipmentScheduleBL.getQtyToDeliver(shipmentSchedule);
			final DeliveryRule deliveryRule = DeliveryRule.ofCode(shipmentSchedule.getDeliveryRule());

			if (qtyToDeliver.compareTo(qtyOrdered) < 0 || !deliveryRule.isBasedOnDelivery())
			{
				canSchedulesBeFulfilled = false;
				break;
			}
		}

		return canSchedulesBeFulfilled;
	}

	@NonNull
	public Set<InOutId> retrieveInOutIdsByScheduleIds(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		return retrieveInOutIdsByScheduleIds(ShipmentScheduleAndJobScheduleIdSet.ofShipmentScheduleIds(shipmentScheduleIds));
	}

	@NonNull
	public Set<InOutId> retrieveInOutIdsByScheduleIds(@NonNull final ShipmentScheduleAndJobScheduleIdSet scheduleIds)
	{
		return retrieveInOutLineByShipScheduleId(scheduleIds)
				.stream()
				.map(I_M_InOutLine::getM_InOut_ID)
				.map(InOutId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public List<I_M_InOutLine> retrieveInOutLineByShipScheduleId(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		return retrieveInOutLineByShipScheduleId(ShipmentScheduleAndJobScheduleIdSet.ofShipmentScheduleIds(shipmentScheduleIds));
	}

	@NonNull
	public List<I_M_InOutLine> retrieveInOutLineByShipScheduleId(@NonNull final ShipmentScheduleAndJobScheduleIdSet scheduleIds)
	{
		final Set<InOutLineId> inOutLineIds = shipmentScheduleWithHUService.retrieveInOuLineIdByShipScheduleId(scheduleIds);
		return inOutDAO.getLinesByIds(inOutLineIds, I_M_InOutLine.class);
	}

	@NonNull
	public Set<InOutId> generateShipmentsForOLCands(@NonNull final Map<AsyncBatchId, List<OLCandId>> asyncBatchId2OLCandIds)
	{
		if (asyncBatchId2OLCandIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return asyncBatchId2OLCandIds.keySet()
				.stream()
				.map(asyncBatchId -> {
					final ImmutableSet<OLCandId> olCandIdImmutableSet = ImmutableSet.copyOf(asyncBatchId2OLCandIds.get(asyncBatchId));

					return generateShipmentForBatch(olCandIdImmutableSet, asyncBatchId);
				})
				.flatMap(Set::stream)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public IQueryFilter<I_M_ShipmentSchedule> createShipmentScheduleEnqueuerQueryFilters(
			@NonNull final IQueryFilter<I_M_ShipmentSchedule> selectionFilter,
			@Nullable final Instant nowInstant)
	{
		final ICompositeQueryFilter<I_M_ShipmentSchedule> filters = queryBL.createCompositeQueryFilter(I_M_ShipmentSchedule.class)
				.addFilter(selectionFilter);

		//
		// Filter only those which are not yet processed
		filters.addEqualsFilter(de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_Processed, false);

		if (nowInstant != null)
		{
			final IQuery<I_M_Packageable_V> subQueryPackageable = queryBL.createQueryBuilder(I_M_Packageable_V.class)
					.filter(queryBL.createCompositeQueryFilter(I_M_Packageable_V.class)
							.setJoinOr()
							.addCompareFilter(I_M_Packageable_V.COLUMNNAME_PreparationDate, CompareQueryFilter.Operator.LESS_OR_EQUAL, nowInstant)
							.addEqualsFilter(I_M_Packageable_V.COLUMNNAME_IsFixedPreparationDate, false)
					)
					.filter(queryBL.createCompositeQueryFilter(I_M_Packageable_V.class)
							.setJoinOr()
							.addCompareFilter(I_M_Packageable_V.COLUMNNAME_DatePromised, CompareQueryFilter.Operator.LESS_OR_EQUAL, nowInstant)
							.addEqualsFilter(I_M_Packageable_V.COLUMNNAME_IsFixedDatePromised, false)
					)
					.create();

			filters.addInSubQueryFilter()
					.matchingColumnNames(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, I_M_Packageable_V.COLUMNNAME_M_ShipmentSchedule_ID)
					.subQuery(subQueryPackageable)
					.end();
		}

		return filters;
	}

	private void validateAsyncBatchAssignment(@NonNull final ShipmentScheduleAndJobScheduleIdSet scheduleIds, @NonNull final AsyncBatchId asyncBatchId)
	{
		final int unassignedScheduleCount = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, scheduleIds.getShipmentScheduleIds())
				.addNotEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_C_Async_Batch_ID, asyncBatchId)
				.create()
				.count();

		if (unassignedScheduleCount > 0)
		{
			throw new AdempiereException("All given ids should be assigned to C_Async_Batch_ID=" + AsyncBatchId.toRepoId(asyncBatchId) + ", but there are " + unassignedScheduleCount + " unassigned scheduleIds.")
					.appendParametersToMessage()
					.setParameter("scheduleIds", scheduleIds);
		}
	}

	@NonNull
	private ShipmentScheduleEnqueuer.Result enqueueShipmentSchedules(@NonNull final GenerateShipmentsRequest request)
	{
		final ShipmentScheduleWorkPackageParameters workPackageParameters = ShipmentScheduleWorkPackageParameters.builder()
				.adPInstanceId(adPInstanceDAO.createSelectionId())
				.scheduleIds(request.getScheduleIds())
				.onlyLUIds(request.getOnlyLUIds())
				.quantityType(request.getQuantityTypeToUse())
				.onTheFlyPickToPackingInstructions(request.isOnTheFlyPickToPackingInstructions())
				.completeShipments(request.getIsCompleteShipment())
				.isCloseShipmentSchedules(request.isCloseShipmentSchedules())
				.isShipmentDateToday(Boolean.TRUE.equals(request.getIsShipDateToday()))
				.advisedShipmentDocumentNos(request.extractShipmentDocumentNos())
				.qtysToDeliverOverride(request.getScheduleToQuantityToDeliverOverride())
				.build();

		return ShipmentScheduleEnqueuer.newInstance()
				.createWorkpackages(workPackageParameters);
	}

	@NonNull
	public ImmutableMap<AsyncBatchId, ShipmentScheduleAndJobScheduleIdSet> groupSchedulesByAsyncBatch(@NonNull final ShipmentScheduleAndJobScheduleIdSet scheduleIds)
	{
		return trxManager.callInNewTrx(() -> groupSchedulesByAsyncBatch0(scheduleIds));
	}

	@NonNull
	private ImmutableMap<AsyncBatchId, ShipmentScheduleAndJobScheduleIdSet> groupSchedulesByAsyncBatch0(@NonNull final ShipmentScheduleAndJobScheduleIdSet scheduleIds2)
	{
		final Map<ShipmentScheduleId, de.metas.inoutcandidate.model.I_M_ShipmentSchedule> shipmentSchedulesById = shipmentScheduleBL.getByIds(scheduleIds2.getShipmentScheduleIds());

		final ArrayListMultimap<AsyncBatchId, ShipmentScheduleAndJobScheduleId> scheduleIdsByAsyncBatchId = ArrayListMultimap.create();
		final HashSet<ShipmentScheduleAndJobScheduleId> scheduleIdsWithoutAsyncBatchId = new HashSet<>();
		final ArrayList<de.metas.inoutcandidate.model.I_M_ShipmentSchedule> shipmentSchedulesWithoutAsyncBatchId = new ArrayList<>();

		for (final ShipmentScheduleAndJobScheduleId shipmentScheduleAndJobScheduleId : scheduleIds2)
		{
			final ShipmentScheduleId shipmentScheduleId = shipmentScheduleAndJobScheduleId.getShipmentScheduleId();
			final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule = shipmentSchedulesById.get(shipmentScheduleId);
			if (shipmentSchedule == null)
			{
				continue;
			}

			final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoIdOrNull(shipmentSchedule.getC_Async_Batch_ID());
			if (asyncBatchId == null)
			{
				scheduleIdsWithoutAsyncBatchId.add(shipmentScheduleAndJobScheduleId);
				shipmentSchedulesWithoutAsyncBatchId.add(shipmentSchedule);
			}
			else
			{
				scheduleIdsByAsyncBatchId.put(asyncBatchId, shipmentScheduleAndJobScheduleId);
			}
		}

		//
		// If there are shipment schedules without async batch assignment,
		// then create a new async batch and assign those
		if (!shipmentSchedulesWithoutAsyncBatchId.isEmpty())
		{
			final AsyncBatchId newAsyncBatchId = asyncBatchBL.newAsyncBatch(C_Async_Batch_InternalName_ShipmentSchedule);
			shipmentScheduleBL.setAsyncBatchAndSave(shipmentSchedulesWithoutAsyncBatchId, newAsyncBatchId);
			scheduleIdsByAsyncBatchId.putAll(newAsyncBatchId, scheduleIdsWithoutAsyncBatchId);

			shipmentSchedulesWithoutAsyncBatchId.clear();
			scheduleIdsWithoutAsyncBatchId.clear();
		}

		//
		// Build up and return the result
		final ImmutableMap.Builder<AsyncBatchId, ShipmentScheduleAndJobScheduleIdSet> result = ImmutableMap.builder();
		for (final AsyncBatchId asyncBatchId : scheduleIdsByAsyncBatchId.keySet())
		{
			result.put(asyncBatchId, ShipmentScheduleAndJobScheduleIdSet.ofCollection(scheduleIdsByAsyncBatchId.get(asyncBatchId)));
		}
		return result.build();
	}

	@NonNull
	private Set<InOutId> generateShipmentForBatch(@NonNull final Set<OLCandId> olCandIds, @NonNull final AsyncBatchId olCandsAsyncBatchId)
	{
		if (olCandIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final QtyToDeliverMap qtyToDeliverMap = getShipmentScheduleId2QtyToDeliver(olCandIds, olCandsAsyncBatchId);

		if (qtyToDeliverMap.isEmpty())
		{
			return ImmutableSet.of();
		}

		//dev-note: if we came this far, we know all shipment schedules are assigned to the async batch identified by the input param:"asyncBatchId"
		final GenerateShipmentsRequest generateShipmentsRequest = GenerateShipmentsRequest.builder()
				.asyncBatchId(olCandsAsyncBatchId)
				.scheduleIds(ShipmentScheduleAndJobScheduleIdSet.ofShipmentScheduleIds(qtyToDeliverMap.getShipmentScheduleIds()))
				.scheduleToExternalInfo(ImmutableMap.of())
				.scheduleToQuantityToDeliverOverride(qtyToDeliverMap)
				.quantityTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER)
				.onTheFlyPickToPackingInstructions(true) // we might need to create a shipper transportation, so we need TUs
				.isCompleteShipment(true)
				.build();

		generateShipments(generateShipmentsRequest);

		return retrieveInOutIdsByScheduleIds(qtyToDeliverMap.getShipmentScheduleIds());
	}

	private QtyToDeliverMap getShipmentScheduleId2QtyToDeliver(
			@NonNull final Set<OLCandId> olCandIds,
			@NonNull final AsyncBatchId asyncBatchId)
	{
		final Map<OLCandId, OrderLineId> olCandId2OrderLineId = olCandDAO.retrieveOLCandIdToOrderLineId(olCandIds);

		if (olCandId2OrderLineId == null || olCandId2OrderLineId.isEmpty())
		{
			return QtyToDeliverMap.EMPTY;
		}

		final Map<OLCandId, I_C_OLCand> olCandsById = olCandDAO.retrieveByIds(olCandIds);

		final ImmutableMap.Builder<ShipmentScheduleId, StockQtyAndUOMQty> scheduleId2QtyShipped = ImmutableMap.builder();

		for (final Map.Entry<OLCandId, OrderLineId> olCand2OrderLineEntry : olCandId2OrderLineId.entrySet())
		{
			final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleBL.getByOrderLineId(olCand2OrderLineEntry.getValue());

			if (shipmentSchedule == null || shipmentSchedule.isProcessed())
			{
				continue;
			}

			final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());

			final I_C_OLCand olCand = olCandsById.get(olCand2OrderLineEntry.getKey());

			if (olCand.getC_Async_Batch_ID() != shipmentSchedule.getC_Async_Batch_ID() || olCand.getC_Async_Batch_ID() != asyncBatchId.getRepoId())
			{
				throw new AdempiereException("olCand.getC_Async_Batch_ID() != shipmentSchedule.getC_Async_Batch_ID() || olCand.getC_Async_Batch_ID() != asyncBatchId")
						.appendParametersToMessage()
						.setParameter("C_OLCand_ID", olCand.getC_OLCand_ID())
						.setParameter("M_ShipmentSchedule_ID", shipmentSchedule.getM_ShipmentSchedule_ID())
						.setParameter("olCand.getC_Async_Batch_ID()", olCand.getC_Async_Batch_ID())
						.setParameter("shipmentSchedule.getC_Async_Batch_ID()", shipmentSchedule.getC_Async_Batch_ID())
						.setParameter("AsyncBatchId", asyncBatchId);
			}

			final StockQtyAndUOMQty qtyToDeliver = getQtyToDeliver(shipmentSchedule, olCand);
			if (qtyToDeliver != null)
			{
				scheduleId2QtyShipped.put(scheduleId, qtyToDeliver);
			}
		}

		return QtyToDeliverMap.ofMap(scheduleId2QtyShipped.build());
	}

	/**
	 * @return qty to deliver or null if the caller wants *no* shipment
	 */
	@Nullable
	private StockQtyAndUOMQty getQtyToDeliver(final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule, final I_C_OLCand olCand)
	{
		final StockQtyAndUOMQty qtyShipped = olCandEffectiveValuesBL.getQtyShipped(olCand).orElse(null);
		if (qtyShipped == null)
		{
			// not specified; -> let metasfresh decide
			final Quantity qtyToDeliver = shipmentScheduleBL.getQtyToDeliver(shipmentSchedule);
			final ProductId productId = ProductId.ofRepoId(shipmentSchedule.getM_Product_ID());
			return StockQtyAndUOMQtys.ofQtyInStockUOM(qtyToDeliver, productId);
		}
		else if (qtyShipped.signum() <= 0)
		{
			// the caller wants *no* shipment
			return null;
		}
		else
		{
			return qtyShipped;
		}

	}
}
