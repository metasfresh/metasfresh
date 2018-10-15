package de.metas.handlingunits.picking.candidate.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_BPartner;
import de.metas.product.ProductId;
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
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final PickingCandidateRepository pickingCandidateRepository;

	private final ImmutableList<PickingCandidate> pickingCandidates;

	private final Map<ShipmentScheduleId, I_M_ShipmentSchedule> shipmentSchedulesCache = new HashMap<>();

	@Builder
	private ProcessPickingCandidatesCommand(
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			//
			@NonNull final List<PickingCandidate> pickingCandidates)
	{
		Check.assumeNotEmpty(pickingCandidates, "pickingCandidates is not empty");

		this.pickingCandidateRepository = pickingCandidateRepository;

		this.pickingCandidates = ImmutableList.copyOf(pickingCandidates);
	}

	public void perform()
	{
		pickingCandidates.forEach(PickingCandidate::assertDraft);

		trxManager.runInThreadInheritedTrx(() -> pickingCandidates.forEach(this::processInTrx));
	}

	private void processInTrx(final PickingCandidate pc)
	{
		shipmentSchedulesCache.clear(); // because we want to get the fresh QtyToDeliver each time!

		final IAllocationSource pickFromSource = HUListAllocationSourceDestination.ofHUId(pc.getPickFromHuId());
		final IHUProducerAllocationDestination packToDestination = createPackToDestination(pc);

		HULoader.of(pickFromSource, packToDestination)
				.load(createPackToAllocationRequest(pc));

		final HuId packedHuId = packToDestination.getSingleCreatedHuId();
		if (packedHuId == null)
		{
			throw new AdempiereException("Nothing packed for " + pc);
		}

		huShipmentScheduleBL.addQtyPickedAndUpdateHU(pc.getShipmentScheduleId(), pc.getQtyPicked(), packedHuId);

		pc.changeStatusToProcessed(packedHuId);
		pickingCandidateRepository.save(pc);
	}

	private IAllocationRequest createPackToAllocationRequest(final PickingCandidate pc)
	{
		final IHUContext huContext = huContextFactory.createMutableHUContextForProcessing();
		return AllocationUtils.createAllocationRequestBuilder()
				.setHUContext(huContext)
				.setProduct(getProductId(pc))
				.setQuantity(pc.getQtyPicked())
				.setFromReferencedTableRecord(pc.getId().toTableRecordReference())
				.setForceQtyAllocation(true)
				.create();
	}

	private IHUProducerAllocationDestination createPackToDestination(final PickingCandidate pickingCandidate)
	{
		final HuPackingInstructionsId packToInstructionsId = pickingCandidate.getPackToInstructionsId();
		if (packToInstructionsId == null)
		{
			throw new AdempiereException("Pack To Instructions shall be specified for " + pickingCandidate);
		}

		final I_M_ShipmentSchedule shipmentSchedule = getShipmentScheduleById(pickingCandidate.getShipmentScheduleId());
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

	private I_M_ShipmentSchedule getShipmentScheduleById(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return shipmentSchedulesCache.computeIfAbsent(shipmentScheduleId, shipmentSchedulesRepo::getById);
	}

	private ProductId getProductId(final PickingCandidate pc)
	{
		final I_M_ShipmentSchedule shipmentSchedule = getShipmentScheduleById(pc.getShipmentScheduleId());
		return ProductId.ofRepoId(shipmentSchedule.getM_Product_ID());
	}
}
