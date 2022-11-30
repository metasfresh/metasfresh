package de.metas.handlingunits.shipmentschedule.api.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inoutcandidate.invalidation.impl.ShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleSegmentBuilder;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleSegments;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_M_InOutLine;

import java.util.List;

/**
 * Subclass of {@link ShipmentScheduleInvalidateBL} with HU-aware code. The concrete benefit of this is that we can create BPartner-specific storage segments which in turn allow us to invalidate less
 * shipment schedules.
 *
 * @author ts
 *
 */
public class HUShipmentScheduleInvalidateBL extends ShipmentScheduleInvalidateBL
{
	private final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);

	public HUShipmentScheduleInvalidateBL(@NonNull final PickingBOMService pickingBOMService)
	{
		super(pickingBOMService);
	}

	@Override
	protected IShipmentScheduleSegment createSegmentForInOutLine(final int bPartnerId, final @NonNull I_M_InOutLine inoutLine)
	{
		final ShipmentScheduleSegmentBuilder storageSegmentBuilder = ShipmentScheduleSegments.builder();
		storageSegmentBuilder
				.productId(inoutLine.getM_Product_ID())
				.locatorId(inoutLine.getM_Locator_ID())
				.attributeSetInstanceId(inoutLine.getM_AttributeSetInstance_ID());

		final List<I_M_HU> husForInOutLine = huAssignmentDAO.retrieveTopLevelHUsForModel(inoutLine);
		if (husForInOutLine.isEmpty())
		{
			storageSegmentBuilder.bpartnerId(0); // don't restrict to any partner
		}

		for (final I_M_HU hu : husForInOutLine)
		{
			storageSegmentBuilder.bpartnerId(hu.getC_BPartner_ID());
		}

		final IShipmentScheduleSegment storageSegment = storageSegmentBuilder.build();
		return storageSegment;
	}

	@Override
	protected IShipmentScheduleSegment createSegmentForShipmentSchedule(final @NonNull I_M_ShipmentSchedule schedule)
	{
		boolean maybeCanRestrictToCertainPartners = false; // we will set this to true if there is any TU assigned to the shipment schedule

		final ShipmentScheduleSegmentBuilder storageSegmentBuilder = ShipmentScheduleSegments.builder();

		final List<I_M_ShipmentSchedule_QtyPicked> pickedNotDeliveredRecords = shipmentScheduleAllocDAO.retrieveNotOnShipmentLineRecords(schedule, I_M_ShipmentSchedule_QtyPicked.class);
		for (final I_M_ShipmentSchedule_QtyPicked pickedNotDeliveredRecord : pickedNotDeliveredRecords)
		{
			if (pickedNotDeliveredRecord.getM_TU_HU_ID() > 0)
			{
				maybeCanRestrictToCertainPartners = true;

				// note that the C_BPartner_ID might still be 0. If any one of several IDs is 0, then there won't be a restriction.
				final I_M_HU tu = pickedNotDeliveredRecord.getM_TU_HU();
				storageSegmentBuilder.bpartnerId(tu.getC_BPartner_ID());
			}
		}

		if (!maybeCanRestrictToCertainPartners)
		{
			// we are sure that we didn't find any C_BPartner_ID. Add one 0 explicitly (required by the logic in ShipmentScheduleDAO).
			storageSegmentBuilder.bpartnerId(0); //
		}

		// finalize the builder and create the segment
		final IShipmentScheduleSegment storageSegment = storageSegmentBuilder
				.productId(schedule.getM_Product_ID())
				.warehouseId(shipmentScheduleEffectiveBL.getWarehouseId(schedule))
				.attributeSetInstanceId(schedule.getM_AttributeSetInstance_ID())
				.build();
		return storageSegment;
	}
}
