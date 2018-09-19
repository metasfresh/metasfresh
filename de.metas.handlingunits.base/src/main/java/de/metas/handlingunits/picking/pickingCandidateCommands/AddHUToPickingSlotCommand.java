package de.metas.handlingunits.picking.pickingCandidateCommands;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.picking.api.PickingSlotId;
import de.metas.quantity.Quantity;
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

public class AddHUToPickingSlotCommand
{
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final IShipmentSchedulePA shipmentSchedulesRepo = Services.get(IShipmentSchedulePA.class);
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final PickingCandidateRepository pickingCandidateRepository;

	private final HuId huId;
	private final PickingSlotId pickingSlotId;
	private final ShipmentScheduleId shipmentScheduleId;

	private I_M_ShipmentSchedule shipmentSchedule;

	@Builder
	private AddHUToPickingSlotCommand(
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			@NonNull final HuId huId,
			@NonNull final PickingSlotId pickingSlotId,
			@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		this.pickingCandidateRepository = pickingCandidateRepository;
		this.huId = huId;
		this.pickingSlotId = pickingSlotId;
		this.shipmentScheduleId = shipmentScheduleId;
	}

	public void perform()
	{
		shipmentSchedule = shipmentSchedulesRepo.getById(shipmentScheduleId, I_M_ShipmentSchedule.class);

		final Quantity qty = getQtyFromHU();

		final PickingCandidate pickingCandidate = pickingCandidateRepository.getByShipmentScheduleIdAndHuIdAndPickingSlotId(huId, shipmentScheduleId, pickingSlotId)
				.orElseGet(() -> PickingCandidate.builder()
						.qtyPicked(qty)
						.huId(huId)
						.shipmentScheduleId(shipmentScheduleId)
						.pickingSlotId(pickingSlotId)
						.build());
		pickingCandidate.setQtyPicked(qty);
		pickingCandidateRepository.save(pickingCandidate);

		//
		// Try allocating the picking slot
		{
			final BPartnerId bpartnerId = shipmentScheduleEffectiveBL.getBPartnerId(shipmentSchedule);
			final int bpartnerLocationId = shipmentScheduleEffectiveBL.getC_BP_Location_ID(shipmentSchedule);
			huPickingSlotBL.allocatePickingSlotIfPossible(pickingSlotId, bpartnerId, bpartnerLocationId);
		}
	}

	private Quantity getQtyFromHU()
	{
		final I_M_HU hu = handlingUnitsDAO.getById(huId);

		final List<IHUProductStorage> productStorages = huContextFactory
				.createMutableHUContext()
				.getHUStorageFactory()
				.getStorage(hu)
				.getProductStorages();
		if (productStorages.isEmpty())
		{
			// TODO: don't return null but return ZERO using product's UOM

			// Allow empty storage. That's the case when we are adding a newly created HU
			final I_C_UOM uom = Services.get(IShipmentScheduleBL.class).getUomOfProduct(shipmentSchedule);
			return Quantity.zero(uom);
			// throw new AdempiereException("HU is empty").setParameter("hu", hu);
		}
		else if (productStorages.size() > 1)
		{
			throw new AdempiereException("HU has more than one product").setParameter("productStorages", productStorages);
		}
		else
		{
			final IHUProductStorage productStorage = productStorages.get(0);
			return Quantity.of(productStorage.getQty(), productStorage.getC_UOM());
		}
	}
}
