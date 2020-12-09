package de.metas.handlingunits.picking.candidate.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidateIssueToBOMLine;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueProducer.ProcessIssueCandidatesPolicy;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.util.CatchWeightHelper;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class ProcessPickingCandidatesCommand
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);

	private final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	private final IInvoiceCandBL invoiceCandidatesService = Services.get(IInvoiceCandBL.class);
	private final IHUPPOrderBL ppOrderService = Services.get(IHUPPOrderBL.class);
	private final PickingCandidateRepository pickingCandidateRepository;

	private final ImmutableSet<PickingCandidateId> pickingCandidateIds;

	private static final int PACKAGE_NO_ZERO = 0;
	private final AtomicInteger PACKAGE_NO_SEQUENCE = new AtomicInteger(100);
	private final Map<PackToDestinationKey, IHUProducerAllocationDestination> packToDestinations = new HashMap<>();

	private final Map<ShipmentScheduleId, I_M_ShipmentSchedule> shipmentSchedulesCache = new HashMap<>();

	@Builder
	private ProcessPickingCandidatesCommand(
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			//
			@NonNull @Singular final Set<PickingCandidateId> pickingCandidateIds)
	{
		Check.assumeNotEmpty(pickingCandidateIds, "pickingCandidateIds is not empty");

		this.pickingCandidateRepository = pickingCandidateRepository;

		this.pickingCandidateIds = ImmutableSet.copyOf(pickingCandidateIds);
	}

	public ProcessPickingCandidatesResult perform()
	{
		final List<PickingCandidate> pickingCandidates = pickingCandidateRepository.getByIds(pickingCandidateIds);
		pickingCandidates.forEach(this::assertEligibleForProcessing);

		trxManager.runInThreadInheritedTrx(() -> pickingCandidates.forEach(this::processInTrx));

		return ProcessPickingCandidatesResult.builder()
				.pickingCandidates(pickingCandidates)
				.build();
	}

	private void assertEligibleForProcessing(final PickingCandidate pickingCandidate)
	{
		pickingCandidate.assertDraft();

		if (pickingCandidate.isRejectedToPick())
		{
			return; // OK
		}

		if (pickingCandidate.getPackedToHuId() != null)
		{
			throw new AdempiereException("Picking candidate shall not be already packed: " + pickingCandidate);
		}
	}

	private void processInTrx(@NonNull final PickingCandidate pickingCandidate)
	{
		shipmentSchedulesCache.clear(); // because we want to get the fresh QtyToDeliver each time!

		final HuId packedToHuId;
		if (pickingCandidate.isRejectedToPick())
		{
			final I_M_ShipmentSchedule shipmentSchedule = getShipmentScheduleById(pickingCandidate.getShipmentScheduleId());
			closeShipmentScheduleAndInvoiceCandidates(shipmentSchedule);
			packedToHuId = null;
		}
		else if (pickingCandidate.getPickFrom().isPickFromHU())
		{
			final HuId pickFromHUId = pickingCandidate.getPickFrom().getHuId();

			packedToHuId = packToHU(pickingCandidate, pickFromHUId);
		}
		else if (pickingCandidate.getPickFrom().isPickFromPickingOrder())
		{
			final PPOrderId pickingOrderId = pickingCandidate.getPickFrom().getPickingOrderId();

			issueRawProductsToPickingOrder(
					pickingOrderId,
					pickingCandidate.getIssuesToPickingOrder(),
					pickingCandidate.getId());

			final HuId vhuId = receiveVHUFromPickingOrder(pickingCandidate);

			packedToHuId = packToHU(pickingCandidate, vhuId);

			ppOrderService.closeOrder(pickingOrderId);
		}
		else
		{
			throw new AdempiereException("Unknow " + pickingCandidate.getPickFrom());
		}

		pickingCandidate.changeStatusToProcessed(packedToHuId);
		pickingCandidateRepository.save(pickingCandidate);
	}

	private HuId packToHU(
			@NonNull final PickingCandidate pickingCandidate,
			@NonNull final HuId pickFromHUId)
	{
		final IAllocationSource pickFromSource = HUListAllocationSourceDestination.ofHUId(pickFromHUId)
				.setDestroyEmptyHUs(true);
		final IHUProducerAllocationDestination packToDestination = getPackToDestination(pickingCandidate);

		final I_M_ShipmentSchedule shipmentSchedule = getShipmentScheduleById(pickingCandidate.getShipmentScheduleId());
		final ProductId productId = ProductId.ofRepoId(shipmentSchedule.getM_Product_ID());

		final Quantity qtyPicked = pickingCandidate.getQtyPicked();

		final IHUContext huContext = huContextFactory.createMutableHUContextForProcessing();
		final IAllocationRequest request = AllocationUtils.createAllocationRequestBuilder()
				.setHUContext(huContext)
				.setProduct(productId)
				.setQuantity(qtyPicked)
				.setFromReferencedTableRecord(pickingCandidate.getId().toTableRecordReference())
				.setForceQtyAllocation(true)
				.create();

		HULoader.of(pickFromSource, packToDestination).load(request);
		final I_M_HU packedToHU = packToDestination.getSingleCreatedHU()
				.orElseThrow(() -> new AdempiereException("Nothing packed for " + pickingCandidate));

		addShipmentScheduleQtyPickedAndUpdateHU(shipmentSchedule, packedToHU, qtyPicked, huContext);

		huContext.flush();

		return HuId.ofRepoId(packedToHU.getM_HU_ID());
	}

	private void addShipmentScheduleQtyPickedAndUpdateHU(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final I_M_HU hu,
			@NonNull final Quantity qtyPicked,
			@NonNull final IHUContext huContext)
	{
		final ProductId productId = ProductId.ofRepoId(shipmentSchedule.getM_Product_ID());

		final boolean anonymousHuPickedOnTheFly = false;
		huShipmentScheduleBL.addQtyPickedAndUpdateHU(
				shipmentSchedule,
				CatchWeightHelper.extractQtys(
						huContext,
						productId,
						qtyPicked,
						hu),
				hu,
				huContext,
				anonymousHuPickedOnTheFly);
	}

	private void issueRawProductsToPickingOrder(
			@NonNull final PPOrderId pickingOrderId,
			@NonNull final ImmutableList<PickingCandidateIssueToBOMLine> issuesToPickingOrder,
			@NonNull final PickingCandidateId pickingCandidateId)
	{
		for (final PickingCandidateIssueToBOMLine issueToPickingOrder : issuesToPickingOrder)
		{
			issueRawProductsToPickingOrder(pickingOrderId, issueToPickingOrder, pickingCandidateId);
		}
	}

	private void issueRawProductsToPickingOrder(
			@NonNull final PPOrderId pickingOrderId,
			@NonNull final PickingCandidateIssueToBOMLine issueToPickingOrder,
			@NonNull final PickingCandidateId pickingCandidateId)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUPPOrderBL ppOrderBL = Services.get(IHUPPOrderBL.class);

		final HuId issueFromHUId = issueToPickingOrder.getIssueFromHUId();
		final I_M_HU issueFromHU = handlingUnitsBL.getById(issueFromHUId);

		ppOrderBL.createIssueProducer(pickingOrderId)
				.targetOrderBOMLine(issueToPickingOrder.getIssueToOrderBOMLineId())
				.fixedQtyToIssue(issueToPickingOrder.getQtyToIssue())
				.movementDate(SystemTime.asZonedDateTime())
				.processCandidates(ProcessIssueCandidatesPolicy.ALWAYS)
				.changeHUStatusToIssued(false)
				.pickingCandidateId(pickingCandidateId)
				.createIssue(issueFromHU);
	}

	private HuId receiveVHUFromPickingOrder(@NonNull final PickingCandidate pickingCandidate)
	{
		final LocatorId shipFromLocatorId = getShipFromLocatorId(pickingCandidate.getShipmentScheduleId());
		final PPOrderId pickingOrderId = pickingCandidate.getPickFrom().getPickingOrderId();

		final I_M_HU vhu = ppOrderService.receivingMainProduct(pickingOrderId)
				.locatorId(shipFromLocatorId)
				.pickingCandidateId(pickingCandidate.getId())
				.receiveVHU(pickingCandidate.getQtyPicked());

		return HuId.ofRepoId(vhu.getM_HU_ID());
	}

	private void closeShipmentScheduleAndInvoiceCandidates(final I_M_ShipmentSchedule shipmentSchedule)
	{
		if (shipmentSchedule.isClosed())
		{
			return;
		}

		huShipmentScheduleBL.closeShipmentSchedule(shipmentSchedule);

		//
		// Close related invoices candidates too
		final OrderLineId salesOrderLineId = OrderLineId.ofRepoIdOrNull(shipmentSchedule.getC_OrderLine_ID());
		invoiceCandidatesService.closeInvoiceCandidatesByOrderLineId(salesOrderLineId);
	}

	private IHUProducerAllocationDestination getPackToDestination(final PickingCandidate pickingCandidate)
	{
		final PackToDestinationKey key = createPackToDestinationKey(pickingCandidate);
		return packToDestinations.computeIfAbsent(key, this::createPackToDestination);
	}

	private PackToDestinationKey createPackToDestinationKey(final PickingCandidate pickingCandidate)
	{
		final HuPackingInstructionsId packToInstructionsId = pickingCandidate.getPackToInstructionsId();
		if (packToInstructionsId == null)
		{
			throw new AdempiereException("Pack To Instructions shall be specified for " + pickingCandidate);
		}

		// PackageNo: if we deal with virtual handling units, pack each candidate individually (in a new VHU).
		// If we deal with some real packing instructions then we pack all candidates with same instructions in same HU.
		final int packageNo = packToInstructionsId.isVirtual() ? PACKAGE_NO_SEQUENCE.getAndIncrement() : PACKAGE_NO_ZERO;

		final ShipmentScheduleId shipmentScheduleId = pickingCandidate.getShipmentScheduleId();
		final BPartnerLocationId shipToBPLocationId = getShipToBPLocationId(shipmentScheduleId);
		final LocatorId shipFromLocatorId = getShipFromLocatorId(shipmentScheduleId);

		return PackToDestinationKey.builder()
				.packToInstructionsId(packToInstructionsId)
				.shipToBPLocationId(shipToBPLocationId)
				.shipFromLocatorId(shipFromLocatorId)
				.packageNo(packageNo)
				.build();
	}

	private IHUProducerAllocationDestination createPackToDestination(final PackToDestinationKey key)
	{
		final HuPackingInstructionsId packToInstructionsId = key.getPackToInstructionsId();
		final BPartnerLocationId shipToBPLocationId = key.getShipToBPLocationId();
		final LocatorId shipFromLocatorId = key.getShipFromLocatorId();

		return HUProducerDestination.of(packToInstructionsId)
				.setMaxHUsToCreate(1)
				.setBPartnerAndLocationId(shipToBPLocationId)
				.setHUStatus(X_M_HU.HUSTATUS_Picked)
				.setLocatorId(shipFromLocatorId);
	}

	private LocatorId getShipFromLocatorId(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final I_M_ShipmentSchedule shipmentSchedule = getShipmentScheduleById(shipmentScheduleId);
		return huShipmentScheduleBL.getDefaultLocatorId(shipmentSchedule);
	}

	private BPartnerLocationId getShipToBPLocationId(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final I_M_ShipmentSchedule shipmentSchedule = getShipmentScheduleById(shipmentScheduleId);
		return huShipmentScheduleBL.getBPartnerLocationId(shipmentSchedule);
	}

	private I_M_ShipmentSchedule getShipmentScheduleById(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return shipmentSchedulesCache.computeIfAbsent(shipmentScheduleId, huShipmentScheduleBL::getById);
	}

	@lombok.Value
	@lombok.Builder
	private static class PackToDestinationKey
	{
		@NonNull
		final HuPackingInstructionsId packToInstructionsId;
		@NonNull
		final BPartnerLocationId shipToBPLocationId;
		@NonNull
		final LocatorId shipFromLocatorId;

		final int packageNo;
	}
}
