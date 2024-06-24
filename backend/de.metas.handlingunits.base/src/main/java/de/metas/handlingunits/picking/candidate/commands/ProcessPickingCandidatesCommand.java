package de.metas.handlingunits.picking.candidate.commands;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidateIssueToBOMLine;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.candidate.commands.PackToHUsProducer.PackToInfo;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueProducer.ProcessIssueCandidatesPolicy;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IssueCandidateGeneratedBy;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.util.CatchWeightHelper;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.api.PPOrderId;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
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

	//
	// Params
	private final ImmutableSet<PickingCandidateId> pickingCandidateIds;

	//
	// State
	private final ShipmentSchedulesCache shipmentSchedulesCache;
	private final PackToMap packToMap;

	@Builder
	private ProcessPickingCandidatesCommand(
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			@NonNull final InventoryService inventoryService,
			@NonNull final ProcessPickingCandidatesRequest request)
	{
		this.pickingCandidateRepository = pickingCandidateRepository;

		this.pickingCandidateIds = request.getPickingCandidateIds();

		this.shipmentSchedulesCache = new ShipmentSchedulesCache(huShipmentScheduleBL);

		final PackToHUsProducer packToHUsProducer = PackToHUsProducer.builder()
				.handlingUnitsBL(Services.get(IHandlingUnitsBL.class))
				.huPIItemProductBL(Services.get(IHUPIItemProductBL.class))
				.uomConversionBL(Services.get(IUOMConversionBL.class))
				.inventoryService(inventoryService)
				.alwaysPackEachCandidateInItsOwnHU(request.isAlwaysPackEachCandidateInItsOwnHU())
				.build();

		this.packToMap = new PackToMap(packToHUsProducer);
	}

	public ProcessPickingCandidatesResult execute()
	{
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private ProcessPickingCandidatesResult executeInTrx()
	{
		//
		// Load data required for processing
		final List<PickingCandidate> pickingCandidates = pickingCandidateRepository.getByIds(pickingCandidateIds);
		pickingCandidates.forEach(this::assertEligibleForProcessing);
		packToMap.setupPackToInfosFromPickingCandidates(pickingCandidates, shipmentSchedulesCache);

		//
		// Effectively process each line
		pickingCandidates.forEach(this::processPickingCandidate);

		//
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

	private void processPickingCandidate(@NonNull final PickingCandidate pickingCandidate)
	{
		shipmentSchedulesCache.clear(); // because we want to get the fresh QtyToDeliver each time!

		final HuId packedToHuId;
		if (pickingCandidate.isRejectedToPick())
		{
			final I_M_ShipmentSchedule shipmentSchedule = shipmentSchedulesCache.getById(pickingCandidate.getShipmentScheduleId());
			closeShipmentScheduleAndInvoiceCandidates(shipmentSchedule);
			packedToHuId = null;
		}
		else if (pickingCandidate.getPickFrom().isPickFromHU())
		{
			final HuId pickFromHUId = Objects.requireNonNull(pickingCandidate.getPickFrom().getHuId());
			packedToHuId = packToHU(pickingCandidate, pickFromHUId);
		}
		else if (pickingCandidate.getPickFrom().isPickFromPickingOrder())
		{
			final PPOrderId pickingOrderId = Objects.requireNonNull(pickingCandidate.getPickFrom().getPickingOrderId());
			final PickingCandidateId pickingCandidateId = Objects.requireNonNull(pickingCandidate.getId());

			issueRawProductsToPickingOrder(
					pickingOrderId,
					pickingCandidate.getIssuesToPickingOrder(),
					pickingCandidateId);

			final HuId vhuId = receiveVHUFromPickingOrder(pickingCandidate);

			packedToHuId = packToHU(pickingCandidate, vhuId);

			ppOrderService.closeOrder(pickingOrderId);
		}
		else
		{
			throw new AdempiereException("Unknown " + pickingCandidate.getPickFrom());
		}

		pickingCandidate.changeStatusToProcessed(packedToHuId);
		pickingCandidateRepository.save(pickingCandidate);
	}

	private HuId packToHU(
			@NonNull final PickingCandidate pickingCandidate,
			@NonNull final HuId pickFromHUId)
	{
		final IHUContext huContext = huContextFactory.createMutableHUContextForProcessing();
		final I_M_ShipmentSchedule shipmentSchedule = shipmentSchedulesCache.getById(pickingCandidate.getShipmentScheduleId());
		final ProductId productId = ProductId.ofRepoId(shipmentSchedule.getM_Product_ID());
		final Quantity qtyPicked = pickingCandidate.getQtyPicked();
		final PickingCandidateId pickingCandidateId = Objects.requireNonNull(pickingCandidate.getId());

		final I_M_HU packedToHU = packToMap.packToSingleHU(
				huContext,
				pickFromHUId,
				productId,
				qtyPicked,
				pickingCandidateId);

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
				.generatedBy(IssueCandidateGeneratedBy.ofPickingCandidateId(pickingCandidateId))
				.createIssue(issueFromHU);
	}

	private HuId receiveVHUFromPickingOrder(@NonNull final PickingCandidate pickingCandidate)
	{
		final LocatorId shipFromLocatorId = shipmentSchedulesCache.getShipFromLocatorId(pickingCandidate.getShipmentScheduleId());
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

	//
	//
	// --------------------------------------------------------------------------------
	//
	//

	private static class ShipmentSchedulesCache
	{
		private final IHUShipmentScheduleBL huShipmentScheduleBL;

		private final HashMap<ShipmentScheduleId, I_M_ShipmentSchedule> shipmentSchedules = new HashMap<>();

		private ShipmentSchedulesCache(final IHUShipmentScheduleBL huShipmentScheduleBL)
		{
			this.huShipmentScheduleBL = huShipmentScheduleBL;
		}

		public void clear() {shipmentSchedules.clear();}

		public I_M_ShipmentSchedule getById(@NonNull final ShipmentScheduleId shipmentScheduleId)
		{
			return shipmentSchedules.computeIfAbsent(shipmentScheduleId, huShipmentScheduleBL::getById);
		}

		public LocatorId getShipFromLocatorId(@NonNull final ShipmentScheduleId shipmentScheduleId)
		{
			final I_M_ShipmentSchedule shipmentSchedule = getById(shipmentScheduleId);
			return huShipmentScheduleBL.getDefaultLocatorId(shipmentSchedule);
		}

		public BPartnerLocationId getShipToBPLocationId(@NonNull final ShipmentScheduleId shipmentScheduleId)
		{
			final I_M_ShipmentSchedule shipmentSchedule = getById(shipmentScheduleId);
			return huShipmentScheduleBL.getBPartnerLocationId(shipmentSchedule);
		}

		public ProductId getProductId(@NonNull final ShipmentScheduleId shipmentScheduleId)
		{
			final I_M_ShipmentSchedule shipmentSchedule = getById(shipmentScheduleId);
			return ProductId.ofRepoId(shipmentSchedule.getM_Product_ID());
		}
	}

	//
	//
	// --------------------------------------------------------------------------------
	//
	//

	private static class PackToMap
	{
		private final PackToHUsProducer packToHUsProducer;

		private final HashMap<PickingCandidateId, PackToInfo> packToInfos = new HashMap<>();
		private final HashMultimap<PackToInfo, PickingCandidateId> pickingCandidateIdsByPackToInfo = HashMultimap.create();

		public PackToMap(@NonNull final PackToHUsProducer packToHUsProducer)
		{
			this.packToHUsProducer = packToHUsProducer;
		}

		public void setupPackToInfosFromPickingCandidates(
				@NonNull final List<PickingCandidate> pickingCandidates,
				@NonNull final ShipmentSchedulesCache shipmentSchedulesCache)
		{
			for (final PickingCandidate pickingCandidate : pickingCandidates)
			{
				final PackToInfo packToDestinationInfo = extractPackToInfo(pickingCandidate, shipmentSchedulesCache);
				final PickingCandidateId pickingCandidateId = Objects.requireNonNull(pickingCandidate.getId());

				this.packToInfos.put(pickingCandidateId, packToDestinationInfo);
				this.pickingCandidateIdsByPackToInfo.put(packToDestinationInfo, pickingCandidateId);
			}
		}

		private PackToInfo extractPackToInfo(
				@NonNull final PickingCandidate pickingCandidate,
				@NonNull final ShipmentSchedulesCache shipmentSchedulesCache)
		{
			final PackToSpec packToSpec = pickingCandidate.getPackToSpec();
			if (packToSpec == null)
			{
				throw new AdempiereException("Pack To Instructions shall be specified for " + pickingCandidate);
			}

			final ShipmentScheduleId shipmentScheduleId = pickingCandidate.getShipmentScheduleId();

			return packToHUsProducer.extractPackToInfo(
					packToSpec,
					shipmentSchedulesCache.getShipToBPLocationId(shipmentScheduleId),
					shipmentSchedulesCache.getShipFromLocatorId(shipmentScheduleId));
		}

		public I_M_HU packToSingleHU(
				@NonNull final IHUContext huContext,
				@NonNull final HuId pickFromHUId,
				@NonNull final ProductId productId,
				@NonNull final Quantity qtyPicked,
				@NonNull final PickingCandidateId pickingCandidateId)
		{
			final PackToInfo packToInfo = getPackToInfo(pickingCandidateId);
			final boolean checkIfAlreadyPacked = isOnlyOnePickingCandidatePackedTo(packToInfo);
			final List<I_M_HU> packedToHUs = packToHUsProducer.packToHU(
					huContext,
					pickFromHUId,
					packToInfo,
					productId,
					qtyPicked,
					null,
					pickingCandidateId.toTableRecordReference(),
					checkIfAlreadyPacked,
					false);

			if (packedToHUs.isEmpty())
			{
				throw new AdempiereException("Nothing was packed from " + pickFromHUId);
			}
			else if (packedToHUs.size() != 1)
			{
				final String packedHUsDisplayStr = packedToHUs.stream().map(hu -> String.valueOf(hu.getM_HU_ID())).collect(Collectors.joining(", "));
				throw new AdempiereException("More than one HU was packed from " + pickFromHUId + ": " + packedHUsDisplayStr)
						.appendParametersToMessage()
						.setParameter("qtyPicked", qtyPicked);
			}
			else
			{
				return CollectionUtils.singleElement(packedToHUs);
			}
		}

		private PackToInfo getPackToInfo(final PickingCandidateId pickingCandidateId)
		{
			final PackToInfo packToInfo = packToInfos.get(pickingCandidateId);
			if (packToInfo == null)
			{
				throw new AdempiereException("No Pack To Infos computed for " + pickingCandidateId);
			}
			return packToInfo;
		}

		private boolean isOnlyOnePickingCandidatePackedTo(final PackToInfo packToInfo)
		{
			return pickingCandidateIdsByPackToInfo.get(packToInfo).size() == 1;
		}
	}
}
