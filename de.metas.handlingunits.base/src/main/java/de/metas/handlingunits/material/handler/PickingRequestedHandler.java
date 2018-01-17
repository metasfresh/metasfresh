package de.metas.handlingunits.material.handler;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.Collection;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.Supplier;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner_Location;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.event.log.EventLogUserService;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.picking.PickingRequestedEvent;
import de.metas.picking.api.IPickingSlotDAO;
import de.metas.picking.api.IPickingSlotDAO.PickingSlotQuery;
import de.metas.picking.model.I_M_PickingSlot;
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

@Service
@Profile(Profiles.PROFILE_App)
public class PickingRequestedHandler implements MaterialEventHandler<PickingRequestedEvent>
{

	private final PickingCandidateService pickingCandidateService;
	private final EventLogUserService eventLogUserService;

	public PickingRequestedHandler(
			@NonNull final PickingCandidateService pickingCandidateService,
			@NonNull final EventLogUserService eventLogUserService)
	{
		this.pickingCandidateService = pickingCandidateService;
		this.eventLogUserService = eventLogUserService;

	}

	@Override
	public Collection<Class<? extends PickingRequestedEvent>> getHandeledEventType()
	{
		return ImmutableList.of(PickingRequestedEvent.class);
	}

	@Override
	public void validateEvent(PickingRequestedEvent event)
	{
		event.assertValid();
	}

	@Override
	public void handleEvent(@NonNull final PickingRequestedEvent event)
	{
		final int shipmentScheduleId = event.getShipmentScheduleId();
		final int pickingSlotId;
		if (event.getPickingSlotId() > 0)
		{
			pickingSlotId = event.getPickingSlotId();
		}
		else
		{
			pickingSlotId = allocatePickingSlot(shipmentScheduleId);
		}

		for (final int huId : event.getTopLevelHuIdsToPick())
		{
			// NOTE: we are not moving the HU to shipment schedule's locator.
			pickingCandidateService.addHUToPickingSlot(
					huId,
					pickingSlotId,
					shipmentScheduleId);
		}

		pickingCandidateService.processForHUIds(
				event.getTopLevelHuIdsToPick(),
				pickingSlotId,
				OptionalInt.of(shipmentScheduleId));
	}

	private int allocatePickingSlot(final int shipmentScheduleId)
	{
		final I_M_ShipmentSchedule shipmentSchedule = load(shipmentScheduleId, I_M_ShipmentSchedule.class);
		final int bpLocationId = Services.get(IShipmentScheduleEffectiveBL.class).getC_BP_Location_ID(shipmentSchedule);
		final PickingSlotQuery pickingSlotQuery = PickingSlotQuery.builder()
				.availableForBPartnerLocationId(bpLocationId)
				.build();

		final List<I_M_PickingSlot> pickingSlots = Services.get(IPickingSlotDAO.class).retrievePickingSlots(pickingSlotQuery);

		Check.errorIf(pickingSlots.isEmpty(),
				"Found no picking slot for C_BP_Location_ID={}; C_BPartner_ID={}; shipmentSchedule={}",
				bpLocationId,
				(Supplier<Object>)() -> loadOutOfTrx(bpLocationId, I_C_BPartner_Location.class).getC_BPartner_ID(),
				shipmentSchedule);

		final I_M_PickingSlot firstPickingSlot = pickingSlots.get(0);

		eventLogUserService.newLogEntry(PickingRequestedHandler.class)
				.formattedMessage("Retrieved an available picking slot, because none was set in the event; pickingSlot={}", firstPickingSlot)
				.storeEntry();
		return firstPickingSlot.getM_PickingSlot_ID();
	}

}
