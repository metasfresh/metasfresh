package de.metas.handlingunits.picking.candidate.commands;

import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingCandidateStatus;
import de.metas.handlingunits.picking.requests.PickRequest;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.picking.api.PickingSlotId;
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

public class CreatePickingCandidatesCommand
{
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IShipmentSchedulePA shipmentSchedulesRepo = Services.get(IShipmentSchedulePA.class);
	private final PickingCandidateRepository pickingCandidateRepository;

	private final ShipmentScheduleId shipmentScheduleId;
	private final HuId pickFromHuId;
	private final PickingSlotId pickingSlotId;

	@Builder
	private CreatePickingCandidatesCommand(
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			@NonNull final PickRequest request)
	{
		this.pickingCandidateRepository = pickingCandidateRepository;

		this.shipmentScheduleId = request.getShipmentScheduleId();
		this.pickFromHuId = request.getPickFromHuId();
		this.pickingSlotId = request.getPickingSlotId();
	}

	public PickHUResult perform()
	{
		final PickingCandidate pickingCandidate = createPickingCandidate();
		pickingCandidate.assertDraft();

		pickingCandidateRepository.save(pickingCandidate);

		return PickHUResult.builder()
				.pickingCandidate(pickingCandidate)
				.build();
	}

	public PickingCandidate createPickingCandidate()
	{
		return PickingCandidate.builder()
				.processingStatus(PickingCandidateStatus.Draft)
				.qtyPicked(Quantity.zero(getShipmentScheduleUOM()))
				.shipmentScheduleId(shipmentScheduleId)
				.pickFromHuId(pickFromHuId)
				.pickingSlotId(pickingSlotId)
				.build();
	}

	private I_C_UOM getShipmentScheduleUOM()
	{
		final I_M_ShipmentSchedule shipmentSchedule = shipmentSchedulesRepo.getById(shipmentScheduleId, I_M_ShipmentSchedule.class);
		return shipmentScheduleBL.getUomOfProduct(shipmentSchedule);
	}
}
