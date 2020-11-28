package de.metas.handlingunits.material.handler;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.compiere.model.I_C_BPartner_Location;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import ch.qos.logback.classic.Level;
import de.metas.Profiles;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.requests.PickRequest;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.picking.PickingRequestedEvent;
import de.metas.picking.api.IPickingSlotDAO;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotQuery;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
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
	private static final Logger logger = LogManager.getLogger(PickingRequestedHandler.class);

	private final PickingCandidateService pickingCandidateService;

	public PickingRequestedHandler(
			@NonNull final PickingCandidateService pickingCandidateService)
	{
		this.pickingCandidateService = pickingCandidateService;
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
		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(event.getShipmentScheduleId());
		final PickingSlotId pickingSlotId;
		if (event.getPickingSlotId() > 0)
		{
			pickingSlotId = PickingSlotId.ofRepoId(event.getPickingSlotId());
		}
		else
		{
			pickingSlotId = allocatePickingSlot(shipmentScheduleId);
		}

		final Set<HuId> huIds = HuId.ofRepoIds(event.getTopLevelHuIdsToPick());
		for (final HuId huIdToPick : HuId.ofRepoIds(event.getTopLevelHuIdsToPick()))
		{
			// NOTE: we are not moving the HU to shipment schedule's locator.
			pickingCandidateService.pickHU(PickRequest.builder()
					.shipmentScheduleId(shipmentScheduleId)
					.pickFrom(PickFrom.ofHuId(huIdToPick))
					.pickingSlotId(pickingSlotId)
					.build());
		}

		pickingCandidateService.processForHUIds(huIds, shipmentScheduleId);
	}

	private PickingSlotId allocatePickingSlot(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final I_M_ShipmentSchedule shipmentSchedule = Services.get(IShipmentSchedulePA.class).getById(shipmentScheduleId);
		final BPartnerLocationId bpLocationId = Services.get(IShipmentScheduleEffectiveBL.class).getBPartnerLocationId(shipmentSchedule);
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

		Loggables.withLogger(logger, Level.DEBUG).addLog(
				"Retrieved an available picking slot, because none was set in the event; pickingSlot={}",
				firstPickingSlot);

		return PickingSlotId.ofRepoId(firstPickingSlot.getM_PickingSlot_ID());
	}

}
