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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.asyncbatchmilestone.AsyncBatchMilestoneService;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.shipmentschedule.api.impl.ShipmentServiceTestImpl;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.order.DeliveryRule;
import de.metas.process.IADPInstanceDAO;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static de.metas.async.Async_Constants.C_Async_Batch_InternalName_ShipmentSchedule;
import static de.metas.async.asyncbatchmilestone.MilestoneName.SHIPMENT_CREATION;

@Service
public class ShipmentService implements IShipmentService
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);

	private final AsyncBatchMilestoneService asyncBatchMilestoneService;

	@NonNull
	public static IShipmentService getInstance()
	{
		if (Adempiere.isUnitTestMode())
		{
			return new ShipmentServiceTestImpl(new ShipmentScheduleWithHUService());
		}
		else
		{
			return SpringContextHolder.instance.getBean(ShipmentService.class);
		}
	}

	public ShipmentService(@NonNull final AsyncBatchMilestoneService asyncBatchMilestoneService)
	{
		this.asyncBatchMilestoneService = asyncBatchMilestoneService;
	}

	@NonNull
	public ShipmentScheduleEnqueuer.Result generateShipments(@NonNull final GenerateShipmentsRequest request)
	{
		if (Check.isEmpty(request.getScheduleIds()))
		{
			throw new AdempiereException("No shipmentScheduleIds found on request!")
					.appendParametersToMessage()
					.setParameter("GenerateShipmentsRequest", request);
		}

		final Supplier<ShipmentScheduleEnqueuer.Result> generateShipmentsSupplier = () -> trxManager.callInNewTrx(() -> {
			validateAsyncBatchAssignment(request.getScheduleIds(), request.getAsyncBatchId());

			return enqueueShipmentSchedules(request);
		});

		// The process will wait until the schedules are processed because the next call might contain the same shipment schedules as the current one.
		// In this case enqueing the same shipmentschedule will fail, because it requires an exclusive lock and the sched is still enqueued from the current lock
		// See ShipmentScheduleEnqueuer.acquireLock(...)
		return asyncBatchMilestoneService.executeMilestone(generateShipmentsSupplier, request.getAsyncBatchId(), SHIPMENT_CREATION);
	}

	@NonNull
	public Set<InOutId> generateShipmentsForScheduleIds(@NonNull final GenerateShipmentsForSchedulesRequest request)
	{
		if (request.getScheduleIds().isEmpty())
		{
			return ImmutableSet.of();
		}

		final ImmutableMap<AsyncBatchId, ArrayList<ShipmentScheduleId>> asyncBatchId2ScheduleId = getShipmentScheduleIdByAsyncBatchId(request.getScheduleIds());

		return asyncBatchId2ScheduleId.keySet()
				.stream()
				.map(asyncBatchId -> {
					final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = ImmutableSet.copyOf(asyncBatchId2ScheduleId.get(asyncBatchId));

					final GenerateShipmentsRequest generateShipmentsRequest = toGenerateShipmentsRequest(asyncBatchId, shipmentScheduleIds, request.getQuantityTypeToUse(), request.getIsCompleteShipment(), request.getIsShipDateToday());

					generateShipments(generateShipmentsRequest);

					return retrieveInOutIdsByScheduleIds(shipmentScheduleIds);
				})
				.flatMap(Set::stream)
				.collect(ImmutableSet.toImmutableSet());
	}

	public boolean canSchedulesBeFulfilled(@NonNull final Set<ShipmentScheduleId> scheduleIds)
	{
		final Map<ShipmentScheduleId, de.metas.inoutcandidate.model.I_M_ShipmentSchedule> shipmentSchedulesByIds = shipmentSchedulePA.getByIds(scheduleIds, de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class);

		final List<de.metas.inoutcandidate.model.I_M_ShipmentSchedule> shipmentSchedules = ImmutableList.copyOf(shipmentSchedulesByIds.values());

		boolean canSchedulesBeFulfilled = true;

		for (final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule : shipmentSchedules)
		{
			final Quantity qtyOrdered = shipmentScheduleBL.getQtyOrdered(shipmentSchedule);
			final Quantity qtyToDeliver = shipmentScheduleBL.getQtyToDeliver(shipmentSchedule);

			if (qtyToDeliver.compareTo(qtyOrdered) < 0
					|| !shipmentSchedule.getDeliveryRule().equals(DeliveryRule.AVAILABILITY.getCode()))
			{
				canSchedulesBeFulfilled = false;
				break;
			}
		}

		return canSchedulesBeFulfilled;
	}

	@NonNull
	public Set<InOutId> retrieveInOutIdsByScheduleIds(@NonNull final Set<ShipmentScheduleId> ids)
	{
		return retrieveInOutLineByShipScheduleId(ids)
				.stream()
				.map(I_M_InOutLine::getM_InOut_ID)
				.map(InOutId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public List<I_M_InOutLine> retrieveInOutLineByShipScheduleId(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		return retrieveInOuLineIdByShipScheduleId(shipmentScheduleIds)
				.stream()
				.map(inOutDAO::getLineById)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public ImmutableMap<AsyncBatchId, ArrayList<ShipmentScheduleId>> getShipmentScheduleIdByAsyncBatchId(@NonNull final Set<ShipmentScheduleId> scheduleIds)
	{
		return trxManager.callInNewTrx(() -> groupSchedulesByAsyncBatch(scheduleIds));
	}

	@NonNull
	private Set<InOutLineId> retrieveInOuLineIdByShipScheduleId(@NonNull final Set<ShipmentScheduleId> ids)
	{
		return shipmentScheduleAllocDAO.retrieveOnShipmentLineRecordsByScheduleIds(ids)
				.values()
				.stream()
				.flatMap(List::stream)
				.map(I_M_ShipmentSchedule_QtyPicked::getM_InOutLine_ID)
				.map(InOutLineId::ofRepoIdOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	private void validateAsyncBatchAssignment(@NonNull final Set<ShipmentScheduleId> ids, @NonNull final AsyncBatchId asyncBatchId)
	{
		final int unassignedScheduleCount = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, ids)
				.addNotEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_C_Async_Batch_ID, asyncBatchId)
				.create()
				.count();

		if (unassignedScheduleCount > 0)
		{
			throw new AdempiereException("All given ids should be assigned to C_Async_Batch_ID=" + AsyncBatchId.toRepoId(asyncBatchId) + ", but there are " + unassignedScheduleCount + " unassigned scheduleIds.")
					.appendParametersToMessage()
					.setParameter("scheduleIds", ids);
		}
	}

	@NonNull
	private ShipmentScheduleEnqueuer.Result enqueueShipmentSchedules(@NonNull final GenerateShipmentsRequest request)
	{
		final ICompositeQueryFilter<I_M_ShipmentSchedule> queryFilters = queryBL
				.createCompositeQueryFilter(I_M_ShipmentSchedule.class)
				.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, request.getScheduleIds());

		final ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters workPackageParameters = ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters.builder()
				.adPInstanceId(adPInstanceDAO.createSelectionId())
				.queryFilters(queryFilters)
				.quantityType(request.getQuantityTypeToUse())
				.completeShipments(request.getIsCompleteShipment())
				.isShipmentDateToday(Boolean.TRUE.equals(request.getIsShipDateToday()))
				.advisedShipmentDocumentNos(request.extractShipmentDocumentNos())
				.qtysToDeliverOverride(request.getScheduleToQuantityToDeliverOverride())
				.build();

		return new ShipmentScheduleEnqueuer()
				.setContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.createWorkpackages(workPackageParameters);
	}

	@NonNull
	private static GenerateShipmentsRequest toGenerateShipmentsRequest(
			@NonNull final AsyncBatchId asyncBatchId,
			@NonNull final ImmutableSet<ShipmentScheduleId> scheduleIds,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse quantityTypeToUse,
			@NonNull final Boolean isCompleteShipment,
			@Nullable final Boolean isShipDateToday)
	{
		return GenerateShipmentsRequest.builder()
				.asyncBatchId(asyncBatchId)
				.scheduleIds(scheduleIds)
				.scheduleToExternalInfo(ImmutableMap.of())
				.scheduleToQuantityToDeliverOverride(ImmutableMap.of())
				.quantityTypeToUse(quantityTypeToUse)
				.isShipDateToday(isShipDateToday)
				.isCompleteShipment(isCompleteShipment)
				.build();
	}

	@NonNull
	private ImmutableMap<AsyncBatchId, ArrayList<ShipmentScheduleId>> groupSchedulesByAsyncBatch(@NonNull final Set<ShipmentScheduleId> scheduleIds)
	{
		final Map<ShipmentScheduleId, I_M_ShipmentSchedule> shipmentSchedules2Ids = shipmentSchedulePA.getByIds(scheduleIds, I_M_ShipmentSchedule.class);

		final Map<AsyncBatchId, ArrayList<ShipmentScheduleId>> asyncBatchId2ScheduleId = new HashMap<>();

		for (final I_M_ShipmentSchedule shipmentSchedule : shipmentSchedules2Ids.values())
		{
			final ArrayList<ShipmentScheduleId> currentShipmentSchedulesIds = new ArrayList<>();
			currentShipmentSchedulesIds.add(ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID()));

			final AsyncBatchId currentAsyncBatchId = AsyncBatchId.ofRepoIdOrNone(shipmentSchedule.getC_Async_Batch_ID());

			asyncBatchId2ScheduleId.merge(currentAsyncBatchId, currentShipmentSchedulesIds, CollectionUtils::mergeLists);
		}

		final ArrayList<ShipmentScheduleId> shipmentSchedulesIdsWithNoAsyncBatchId = asyncBatchId2ScheduleId.get(AsyncBatchId.NONE_ASYNC_BATCH_ID);

		Optional.ofNullable(shipmentSchedulesIdsWithNoAsyncBatchId)
				.flatMap(this::assignNewAsyncBatch)
				.ifPresent(batchId -> {
					asyncBatchId2ScheduleId.put(batchId, shipmentSchedulesIdsWithNoAsyncBatchId);
					asyncBatchId2ScheduleId.remove(AsyncBatchId.NONE_ASYNC_BATCH_ID);
				});

		return ImmutableMap.copyOf(asyncBatchId2ScheduleId);
	}

	@NonNull
	private Optional<AsyncBatchId> assignNewAsyncBatch(@NonNull final List<ShipmentScheduleId> shipmentScheduleIds)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return Optional.empty();
		}

		final ImmutableSet<ShipmentScheduleId> scheduleIds = shipmentScheduleIds.stream()
				.collect(ImmutableSet.toImmutableSet());

		final AsyncBatchId asyncBatchId = asyncBatchBL.newAsyncBatch(C_Async_Batch_InternalName_ShipmentSchedule);

		assignAsyncBatchIdToShipmentSchedules(scheduleIds, asyncBatchId);

		return Optional.of(asyncBatchId);
	}

	private void assignAsyncBatchIdToShipmentSchedules(@NonNull final Set<ShipmentScheduleId> ids, @NonNull final AsyncBatchId asyncBatchId)
	{
		ids.forEach(id -> shipmentScheduleBL.setAsyncBatch(id, asyncBatchId));
	}
}
