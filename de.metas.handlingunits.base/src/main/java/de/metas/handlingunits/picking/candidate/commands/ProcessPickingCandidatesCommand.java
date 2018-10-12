package de.metas.handlingunits.picking.candidate.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.warehouse.LocatorId;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_BPartner;
import de.metas.picking.service.IPackingItem;
import de.metas.picking.service.PackingItemPart;
import de.metas.picking.service.PackingItemPartId;
import de.metas.picking.service.PackingItems;
import de.metas.picking.service.impl.HU2PackingItemsAllocator;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

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
	private final IShipmentSchedulePA shipmentSchedulesRepo = Services.get(IShipmentSchedulePA.class);
	private final IHandlingUnitsDAO handlingUnitsRepo = Services.get(IHandlingUnitsDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final PickingCandidateRepository pickingCandidateRepository;

	private final ImmutableList<PickingCandidate> pickingCandidates;
	private final boolean allowOverDelivery;

	private Map<ShipmentScheduleId, I_M_ShipmentSchedule> shipmentSchedulesCache = new HashMap<>();

	@Builder
	private ProcessPickingCandidatesCommand(
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			//
			@NonNull final List<PickingCandidate> pickingCandidates,
			final boolean allowOverDelivery)
	{
		Check.assumeNotEmpty(pickingCandidates, "pickingCandidates is not empty");

		this.pickingCandidateRepository = pickingCandidateRepository;

		this.pickingCandidates = ImmutableList.copyOf(pickingCandidates);
		this.allowOverDelivery = allowOverDelivery;
	}

	public void perform()
	{
		pickingCandidates.forEach(this::process);
	}

	private void process(final PickingCandidate pickingCandidate)
	{
		trxManager.runInThreadInheritedTrx(() -> processInTrx(pickingCandidate));

		shipmentSchedulesCache.clear(); // because we want to get the fresh QtyToDeliver each time!
	}

	private void processInTrx(final PickingCandidate pickingCandidate)
	{
		pickingCandidate.assertDraft();
		
		final IPackingItem itemToPack = createPackingItem(pickingCandidate);

		final HuId pickFromHuId = pickingCandidate.getPickFromHuId();
		final I_M_HU pickFromHU = handlingUnitsRepo.getByIdOutOfTrx(pickFromHuId); // HU2PackingItemsAllocator requires out-of-trx

		final IHUProducerAllocationDestination packToDestination = createPackToDestinationOrNull(pickingCandidate);

		HU2PackingItemsAllocator.builder()
				.pickFromHU(pickFromHU)
				.itemToPack(itemToPack)
				.qtyToPack(itemToPack.getQtySum())
				.packToDestination(packToDestination)
				.allowOverDelivery(allowOverDelivery)
				.allocate();

		final HuId packedToHuId = Optional.ofNullable(packToDestination)
				.map(IHUProducerAllocationDestination::getSingleCreatedHU)
				.map(I_M_HU::getM_HU_ID)
				.map(HuId::ofRepoId)
				.orElse(pickFromHuId);

		pickingCandidate.changeStatusToProcessed(packedToHuId);
		pickingCandidateRepository.save(pickingCandidate);
	}

	private IHUProducerAllocationDestination createPackToDestinationOrNull(final PickingCandidate pickingCandidate)
	{
		final HuPackingInstructionsId packToInstructionsId = pickingCandidate.getPackToInstructionsId();
		if (packToInstructionsId == null)
		{
			return null;
		}

		I_M_ShipmentSchedule shipmentSchedule = getShipmentScheduleById(pickingCandidate.getShipmentScheduleId());
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final I_C_BPartner bpartner = shipmentScheduleEffectiveBL.getBPartner(shipmentSchedule);
		final BPartnerLocationId bpartnerLocationId = shipmentScheduleEffectiveBL.getBPartnerLocationId(shipmentSchedule);
		final LocatorId locatorId = shipmentScheduleEffectiveBL.getDefaultLocatorId(shipmentSchedule);

		return HUProducerDestination.of(packToInstructionsId)
				.setMaxHUsToCreate(1)
				.setC_BPartner(bpartner)
				.setC_BPartner_Location_ID(bpartnerLocationId.getRepoId())
				.setHUStatus(X_M_HU.HUSTATUS_Picked)
				.setLocatorId(locatorId);
	}

	private IPackingItem createPackingItem(final PickingCandidate pc)
	{
		final ShipmentScheduleId shipmentScheduleId = pc.getShipmentScheduleId();
		final I_M_ShipmentSchedule sched = getShipmentScheduleById(shipmentScheduleId);

		PackingItemPart part = PackingItems.newPackingItemPart(sched)
				.id(PackingItemPartId.of(shipmentScheduleId)) // TODO: include some picking candidate ID in partId
				.qty(pc.getQtyPicked())
				.build();

		return PackingItems.newPackingItem(part);
	}

	private I_M_ShipmentSchedule getShipmentScheduleById(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return shipmentSchedulesCache.computeIfAbsent(shipmentScheduleId, shipmentSchedulesRepo::getById);
	}
}
