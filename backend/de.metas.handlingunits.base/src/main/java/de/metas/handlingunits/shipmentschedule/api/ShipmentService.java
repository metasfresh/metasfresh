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
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.async.asyncbatchmilestone.AsyncBatchMilestoneService;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.process.IADPInstanceDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import static de.metas.async.asyncbatchmilestone.MilestoneName.SHIPMENT_CREATION;

@Service
public class ShipmentService
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

	private final AsyncBatchMilestoneService asyncBatchMilestoneService;

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

		validateAsyncBatchAssignment(request.getScheduleIds(), request.getAsyncBatchId());

		final Supplier<ShipmentScheduleEnqueuer.Result> generateShipmentsSupplier = () -> trxManager.callInNewTrx(() -> enqueueShipmentSchedules(request));

		// The process will wait until the schedules are processed because the next call might contain the same shipment schedules as the current one.
		// In this case enqueing the same shipmentschedule will fail, because it requires a an exclusive lock and the sched is still enqueued from the current lock
		// See ShipmentScheduleEnqueuer.acquireLock(...)
		return asyncBatchMilestoneService.executeMilestone(generateShipmentsSupplier, request.getAsyncBatchId(), SHIPMENT_CREATION);
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

	public void assignAsyncBatchIdToShipmentSchedules(@NonNull final Set<ShipmentScheduleId> ids, @NonNull final AsyncBatchId asyncBatchId)
	{
		ids.forEach(id -> shipmentScheduleBL.setAsyncBatch(id, asyncBatchId));
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
			throw new AdempiereException("Found a number of unassigned scheduleIds! Count: " + unassignedScheduleCount)
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
				.completeShipments(true)
				.advisedShipmentDocumentNos(request.extractShipmentDocumentNos())
				.qtysToDeliverOverride(request.getScheduleToQuantityToDeliverOverride())
				.build();

		return new ShipmentScheduleEnqueuer()
				.setContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.createWorkpackages(workPackageParameters);
	}
}
