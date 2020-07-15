package de.metas.handlingunits.picking.candidate.commands;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.requests.AddQtyToHURequest;
import de.metas.inoutcandidate.api.IPackagingDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.logging.LogManager;
import de.metas.picking.api.PickingConfigRepository;
import de.metas.picking.api.PickingSlotId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import java.util.List;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class AddQtyToHUCommand
{
	private static final Logger logger = LogManager.getLogger(AddQtyToHUCommand.class);

	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
	private final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
	private final IPackagingDAO packingDAO = Services.get(IPackagingDAO.class);
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	private final PickingCandidateRepository pickingCandidateRepository;

	private final ImmutableList<HuId> sourceHUIds;
	private final Quantity qtyToPack;
	private final HuId packToHuId;
	private final PickingSlotId pickingSlotId;
	private final boolean allowOverDelivery;

	private final ShipmentScheduleId shipmentScheduleId;
	private final I_M_ShipmentSchedule shipmentSchedule;
	private final ProductId productId;
	private final Quantity qtyToDeliverTarget;

	@Builder
	private AddQtyToHUCommand(
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			@NonNull final AddQtyToHURequest request)
	{
		this.sourceHUIds = request.getSourceHUIds();
		final IShipmentSchedulePA shipmentSchedulesRepo = Services.get(IShipmentSchedulePA.class);
		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

		this.pickingCandidateRepository = pickingCandidateRepository;
		this.packToHuId = request.getPackToHuId();
		this.pickingSlotId = request.getPickingSlotId();
		this.allowOverDelivery = request.isAllowOverDelivery();

		this.shipmentScheduleId = request.getShipmentScheduleId();
		shipmentSchedule = shipmentSchedulesRepo.getById(shipmentScheduleId, I_M_ShipmentSchedule.class);
		productId = ProductId.ofRepoId(shipmentSchedule.getM_Product_ID());
		qtyToDeliverTarget = shipmentScheduleBL.getQtyToDeliver(shipmentSchedule);

		this.qtyToPack = request.getQtyToPack();

	}

	/**
	 * @return the quantity that was effectively added. We can only add the quantity that's still left in our source HUs.
	 */
	@NonNull
	public Quantity performAndGetQtyPicked()
	{
		if (!allowOverDelivery)
		{
			assertNotOverDelivery();
		}

		final PickingCandidate candidate = getOrCreatePickingCandidate();

		final HUListAllocationSourceDestination source = createFromSourceHUsAllocationSource();
		final IAllocationDestination destination = createAllocationDestination(packToHuId);

		// NOTE: create the context with the tread-inherited transaction,
		// otherwise, the loader won't be able to access the HU's material item and therefore won't load anything!
		final IAllocationRequest request = AllocationUtils.createAllocationRequestBuilder()
				.setHUContext(huContextFactory.createMutableHUContextForProcessing())
				.setProduct(productsRepo.getById(productId))
				.setQuantity(qtyToPack)
				.setDateAsToday()
				.setFromReferencedTableRecord(pickingCandidateRepository.toTableRecordReference(candidate)) // the m_hu_trx_Line coming out of this will reference the picking candidate
				.setForceQtyAllocation(true)
				.create();

		// Load QtyCU to HU(destination)
		final IAllocationResult loadResult = HULoader.of(source, destination)
				.setAllowPartialLoads(true) // don't fail if the the picking staff attempted to to pick more than the TU's capacity
				.setAllowPartialUnloads(true) // don't fail if the the picking staff attempted to to pick more than the shipment schedule's quantity to deliver.
				.load(request);
		logger.info("addQtyToHU done; huId={}, qtyCU={}, loadResult={}", packToHuId, qtyToPack, loadResult);

		// Update the candidate
		final Quantity qtyPicked = Quantity.of(loadResult.getQtyAllocated(), request.getC_UOM());

		addQtyToCandidate(candidate, productId, qtyPicked);

		return qtyPicked;
	}

	private PickingCandidate getOrCreatePickingCandidate()
	{
		final PickingCandidate existingCandidate = pickingCandidateRepository
				.getByShipmentScheduleIdAndHuIdAndPickingSlotId(shipmentScheduleId, packToHuId, pickingSlotId)
				.orElse(null);
		if (existingCandidate != null)
		{
			return existingCandidate;
		}

		final PickingCandidate newCandidate = PickingCandidate.builder()
				.qtyPicked(qtyToPack.toZero())
				.shipmentScheduleId(shipmentScheduleId)
				.pickFrom(PickFrom.ofHuId(packToHuId)) // TODO use source HU ID
				.packedToHuId(packToHuId)
				.pickingSlotId(pickingSlotId)
				.build();
		pickingCandidateRepository.save(newCandidate);
		return newCandidate;
	}

	/**
	 * Source - take the preselected sourceHUs
	 *
	 * @return
	 */
	private HUListAllocationSourceDestination createFromSourceHUsAllocationSource()
	{
		final List<I_M_HU> sourceHUs = handlingUnitsDAO.getByIds(sourceHUIds);
		final HUListAllocationSourceDestination source = HUListAllocationSourceDestination.of(sourceHUs);
		source.setDestroyEmptyHUs(false); // don't automatically destroy them. we will do that ourselves if the sourceHUs are empty at the time we process our picking candidates

		return source;
	}

	private IAllocationDestination createAllocationDestination(final HuId huId)
	{
		final I_M_HU hu = handlingUnitsDAO.getById(huId);

		// we made sure that the source HU is active, so the target HU also needs to be active. Otherwise, goods would just seem to vanish
		if (!huStatusBL.isStatusActive(hu))
		{
			throw new AdempiereException("not an active HU").setParameter("hu", hu);
		}
		final IAllocationDestination destination = HUListAllocationSourceDestination.of(hu);

		return destination;
	}

	private void addQtyToCandidate(
			@NonNull final PickingCandidate candidate,
			@NonNull final ProductId productId,
			@NonNull final Quantity qtyToAdd)
	{
		final Quantity qtyNew;
		if (candidate.getQtyPicked().signum() == 0)
		{
			qtyNew = qtyToAdd;
		}
		else
		{
			final UOMConversionContext conversionCtx = UOMConversionContext.of(productId);
			final Quantity qty = candidate.getQtyPicked();
			final Quantity qtyToAddConv = uomConversionBL.convertQuantityTo(qtyToAdd, conversionCtx, UomId.ofRepoId(qty.getUOM().getC_UOM_ID()));
			qtyNew = qty.add(qtyToAddConv);
		}

		candidate.pick(qtyNew);
		pickingCandidateRepository.save(candidate);
	}

	private void assertNotOverDelivery()
	{
		Quantity qtyToDeliver = qtyToDeliverTarget;

		final Quantity qtyPickedPlanned = packingDAO.retrieveQtyPickedPlanned(shipmentScheduleId).orElse(null);
		if (qtyPickedPlanned != null)
		{
			qtyToDeliver = qtyToDeliver.subtract(qtyPickedPlanned);
		}

		if (qtyToPack.compareTo(qtyToDeliver) > 0)
		{
			throw new AdempiereException("@" + PickingConfigRepository.MSG_WEBUI_Picking_OverdeliveryNotAllowed + "@")
					.appendParametersToMessage()
					.setParameter("qtyToDeliverTarget", qtyToDeliverTarget)
					.setParameter("qtyPickedPlanned", qtyPickedPlanned)
					.setParameter("qtyToPack", qtyToPack);
		}
	}
}
