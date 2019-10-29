package de.metas.handlingunits.picking.candidate.commands;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingCandidateStatus;
import de.metas.handlingunits.picking.requests.PickRequest;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.picking.api.PickingSlotId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

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

public class PickHUCommand
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IShipmentSchedulePA shipmentSchedulesRepo = Services.get(IShipmentSchedulePA.class);
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final PickingCandidateRepository pickingCandidateRepository;

	private final ShipmentScheduleId shipmentScheduleId;
	private final HuId pickFromHuId;
	private final PickingSlotId pickingSlotId;
	private final Quantity qtyToPick;
	private final HuPackingInstructionsId packToId;
	private final boolean autoReview;

	private I_M_ShipmentSchedule _shipmentSchedule; // lazy

	@Builder
	private PickHUCommand(
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			@NonNull final PickRequest request)
	{
		this.pickingCandidateRepository = pickingCandidateRepository;

		this.shipmentScheduleId = request.getShipmentScheduleId();
		this.pickFromHuId = request.getPickFromHuId();
		this.pickingSlotId = request.getPickingSlotId();
		this.packToId = request.getPackToId();
		this.qtyToPick = request.getQtyToPick();
		this.autoReview = request.isAutoReview();
	}

	public PickHUResult perform()
	{
		return trxManager.callInThreadInheritedTrx(this::performInTrx);
	}

	private PickHUResult performInTrx()
	{
		final Quantity qtyToPick = getQtyToPick();
		if (qtyToPick.signum() <= 0)
		{
			throw new AdempiereException("Invalid quantity to pick: " + qtyToPick);
		}

		final PickingCandidate pickingCandidate = getOrCreatePickingCandidate();
		pickingCandidate.assertDraft();

		pickingCandidate.pick(qtyToPick);
		pickingCandidate.packTo(packToId);
		if (autoReview)
		{
			pickingCandidate.reviewPicking(qtyToPick.toBigDecimal());
		}

		pickingCandidateRepository.save(pickingCandidate);

		allocatePickingSlotIfPossible();

		return PickHUResult.builder()
				.pickingCandidate(pickingCandidate)
				.build();
	}

	private PickingCandidate getOrCreatePickingCandidate()
	{
		final PickingCandidate existingPickingCandidate = pickingCandidateRepository.streamByShipmentScheduleId(shipmentScheduleId)
				.filter(PickingCandidate::isDraft)
				.filter(pc -> PickingSlotId.equals(pickingSlotId, pc.getPickingSlotId()))
				.filter(pc -> HuId.equals(pickFromHuId, pc.getPickFromHuId()))
				.findFirst()
				.orElse(null);
		if (existingPickingCandidate != null)
		{
			return existingPickingCandidate;
		}
		else
		{
			return PickingCandidate.builder()
					.processingStatus(PickingCandidateStatus.Draft)
					.qtyPicked(Quantity.zero(getShipmentScheduleUOM()))
					.shipmentScheduleId(shipmentScheduleId)
					.pickFromHuId(pickFromHuId)
					.pickingSlotId(pickingSlotId)
					.build();
		}
	}

	private Quantity getQtyToPick()
	{
		if (qtyToPick != null)
		{
			return qtyToPick;
		}
		else
		{
			return getQtyFromHU();
		}
	}

	private Quantity getQtyFromHU()
	{
		final I_M_HU pickFromHU = handlingUnitsDAO.getById(pickFromHuId);

		final I_M_ShipmentSchedule shipmentSchedule = getShipmentSchedule();
		final ProductId productId = ProductId.ofRepoId(shipmentSchedule.getM_Product_ID());

		final IHUProductStorage productStorage = huContextFactory
				.createMutableHUContext()
				.getHUStorageFactory()
				.getStorage(pickFromHU)
				.getProductStorageOrNull(productId);

		// Allow empty storage. That's the case when we are adding a newly created HU
		if (productStorage == null)
		{
			final I_C_UOM uom = getShipmentScheduleUOM();
			return Quantity.zero(uom);
		}

		return productStorage.getQty();
	}

	private I_M_ShipmentSchedule getShipmentSchedule()
	{
		if (_shipmentSchedule == null)
		{
			_shipmentSchedule = shipmentSchedulesRepo.getById(shipmentScheduleId, I_M_ShipmentSchedule.class);
		}
		return _shipmentSchedule;
	}

	private I_C_UOM getShipmentScheduleUOM()
	{
		return shipmentScheduleBL.getUomOfProduct(getShipmentSchedule());
	}

	private void allocatePickingSlotIfPossible()
	{
		if (pickingSlotId == null)
		{
			return;
		}

		final I_M_ShipmentSchedule shipmentSchedule = getShipmentSchedule();
		final BPartnerId bpartnerId = shipmentScheduleEffectiveBL.getBPartnerId(shipmentSchedule);
		final BPartnerLocationId bpartnerLocationId = shipmentScheduleEffectiveBL.getBPartnerLocationId(shipmentSchedule);
		huPickingSlotBL.allocatePickingSlotIfPossible(pickingSlotId, bpartnerId, bpartnerLocationId);
	}
}
