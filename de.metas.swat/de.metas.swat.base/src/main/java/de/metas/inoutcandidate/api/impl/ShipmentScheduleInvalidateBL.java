package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

import com.google.common.collect.ImmutableList;

import de.metas.inout.IInOutDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.storage.IStorageBL;
import de.metas.storage.IStorageListeners;
import de.metas.storage.IStorageSegment;
import de.metas.storage.IStorageSegmentBuilder;
import lombok.NonNull;

public class ShipmentScheduleInvalidateBL implements IShipmentScheduleInvalidateBL
{
	@Override
	public void invalidateJustForLines(final I_M_InOut shipment)
	{
		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

		final Map<Integer, I_M_ShipmentSchedule> id2sched = new HashMap<>();

		for (final I_M_InOutLine inoutLine : inOutDAO.retrieveLines(shipment))
		{
			addSchedsForInOutLine(id2sched, inoutLine);
		}
		shipmentSchedulePA.invalidate(id2sched.values(), InterfaceWrapperHelper.getTrxName(shipment));
	}

	@Override
	public void invalidateJustForLine(final I_M_InOutLine shipmentLine)
	{
		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);

		final Map<Integer, I_M_ShipmentSchedule> id2sched = new HashMap<>();

		addSchedsForInOutLine(id2sched, shipmentLine);
		shipmentSchedulePA.invalidate(id2sched.values(), InterfaceWrapperHelper.getTrxName(shipmentLine));
	}

	private void addSchedsForInOutLine(
			@NonNull final Map<Integer, I_M_ShipmentSchedule> id2sched,
			@NonNull final I_M_InOutLine inoutLine)
	{
		final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);

		final List<I_M_ShipmentSchedule_QtyPicked> allocs = shipmentScheduleAllocDAO.retrieveAllForInOutLine(inoutLine, I_M_ShipmentSchedule_QtyPicked.class);
		for (final I_M_ShipmentSchedule_QtyPicked alloc : allocs)
		{
			// check if the ID is already there, to spare us from loading the sched
			if (id2sched.containsKey(alloc.getM_ShipmentSchedule_ID()))
			{
				continue;
			}
			id2sched.put(alloc.getM_ShipmentSchedule_ID(), alloc.getM_ShipmentSchedule());
		}
	}

	@Override
	public void invalidateSegmentsForLines(final I_M_InOut shipment)
	{
		final IStorageListeners storageListeners = Services.get(IStorageListeners.class);

		final List<IStorageSegment> storageSegments = new ArrayList<>();

		for (final I_M_InOutLine inoutLine : Services.get(IInOutDAO.class).retrieveLines(shipment))
		{
			final IStorageSegment storageSegment = createSegmentForInOutLine(shipment.getC_BPartner_ID(), inoutLine);
			storageSegments.add(storageSegment);
		}
		storageListeners.notifyStorageSegmentsChanged(storageSegments);
	}

	@Override
	public void invalidateSegmentForLine(final I_M_InOutLine shipmentLine)
	{
		final IStorageListeners storageListeners = Services.get(IStorageListeners.class);

		final IStorageSegment storageSegment = createSegmentForInOutLine(shipmentLine.getM_InOut().getC_BPartner_ID(), shipmentLine);
		storageListeners.notifyStorageSegmentChanged(storageSegment);
	}

	/**
	 * Note that this method is overridden in the de.metas.handlingunits.base module!
	 * TODO: don't override this whole method, there are plenty of better ways
	 */
	protected IStorageSegment createSegmentForInOutLine(final int bPartnerId, @NonNull final I_M_InOutLine inoutLine)
	{
		final IStorageBL storageBL = Services.get(IStorageBL.class);

		final IStorageSegmentBuilder storageSegmentBuilder = storageBL.createStorageSegmentBuilder();
		final IStorageSegment storageSegment = storageSegmentBuilder
				.addC_BPartner_ID(0) // we can't restrict the segment to the inOut-partner, because we don't know if the qty could in theory be reallocated to a *different* partner.
				// So we have to notify *all* partners' segments.
				.addM_Product_ID(inoutLine.getM_Product_ID())
				.addM_Locator_ID(inoutLine.getM_Locator_ID())
				.addM_AttributeSetInstance_ID(inoutLine.getM_AttributeSetInstance_ID())
				.build();
		return storageSegment;
	}

	@Override
	public void invalidateSegmentForShipmentSchedule(@NonNull final I_M_ShipmentSchedule schedule)
	{
		//
		// If shipment schedule updater is currently running in this thread, it means that updater changed this record
		// so there is NO need to invalidate it again.
		if (Services.get(IShipmentScheduleUpdater.class).isRunning())
		{
			return;
		}

		final IStorageListeners storageListeners = Services.get(IStorageListeners.class);
		final IStorageSegment storageSegment = createSegmentForShipmentSchedule(schedule);
		storageListeners.notifyStorageSegmentChanged(storageSegment);
	}

	/**
	 * Note that this method is overridden in the de.metas.handlingunits.base module!
	 * TODO: don't override this whole method, there are plenty of better ways
	 */
	protected IStorageSegment createSegmentForShipmentSchedule(@NonNull final I_M_ShipmentSchedule schedule)
	{
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		final IStorageBL storageBL = Services.get(IStorageBL.class);
		final IStorageSegmentBuilder storageSegmentBuilder = storageBL.createStorageSegmentBuilder();

		// we can't restrict the segment to the sched's bpartner, because we don't know if the qty could in theory be reallocated to a *different* partner.
		// So we have to notify *all* partners' segments.
		final int bpartnerId = 0;
		final IStorageSegment storageSegment = storageSegmentBuilder
				.addC_BPartner_ID(bpartnerId)
				.addM_Product_ID(schedule.getM_Product_ID())
				.addM_Warehouse(shipmentScheduleEffectiveBL.getWarehouse(schedule))
				.addM_AttributeSetInstance_ID(schedule.getM_AttributeSetInstance_ID())
				.build();
		return storageSegment;
	}

	@Override
	public void invalidateSegmentForOrderLine(@NonNull final I_C_OrderLine orderLine)
	{
		final IStorageListeners storageListeners = Services.get(IStorageListeners.class);
		final IStorageBL storageBL = Services.get(IStorageBL.class);

		final IStorageSegmentBuilder storageSegmentBuilder = storageBL.createStorageSegmentBuilder();

		// we can't restrict the segment to the sched's bpartner, because we don't know if the qty could in theory be reallocated to a *different* partner.
		// So we have to notify *all* partners' segments.
		final int bpartnerId = 0;
		final IStorageSegment storageSegment = storageSegmentBuilder
				.addC_BPartner_ID(bpartnerId)
				.addM_Product_ID(orderLine.getM_Product_ID())
				.addM_Warehouse(orderLine.getM_Warehouse())
				.addM_AttributeSetInstance_ID(orderLine.getM_AttributeSetInstance_ID())
				.build();
		storageListeners.notifyStorageSegmentChanged(storageSegment);
	}

	@Override
	public void invalidateJustForOrderLine(@NonNull final I_C_OrderLine orderLine)
	{
		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
		final I_M_ShipmentSchedule sched = shipmentSchedulePA.retrieveForOrderLine(orderLine);
		if(sched == null)
		{
			return;
		}

		shipmentSchedulePA.invalidate(ImmutableList.of(sched), InterfaceWrapperHelper.getTrxName(orderLine));
	}

}
