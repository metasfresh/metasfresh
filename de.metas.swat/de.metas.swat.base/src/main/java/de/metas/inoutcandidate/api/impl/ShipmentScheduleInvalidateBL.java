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
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.service.ITaskExecutorService;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;

import de.metas.inout.IInOutDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.api.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.order.OrderLineId;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.storage.IStorageBL;
import de.metas.storage.IStorageSegment;
import de.metas.storage.IStorageSegmentBuilder;
import de.metas.util.Services;
import lombok.NonNull;

public class ShipmentScheduleInvalidateBL implements IShipmentScheduleInvalidateBL
{
	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	private final IShipmentScheduleInvalidateRepository invalidSchedulesRepo = Services.get(IShipmentScheduleInvalidateRepository.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	protected final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
	protected final IStorageBL storageBL = Services.get(IStorageBL.class);
	protected final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

	private boolean isShipmentScheduleUpdaterRunning()
	{
		// NOTE: cannot declare on top as field because it's a spring bean and it would make junit tests fail
		final IShipmentScheduleUpdater shipmentScheduleUpdater = Services.get(IShipmentScheduleUpdater.class);

		return shipmentScheduleUpdater.isRunning();
	}

	@Override
	public boolean isInvalid(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return invalidSchedulesRepo.isInvalid(shipmentScheduleId);
	}

	@Override
	public void invalidateShipmentSchedule(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		invalidateShipmentSchedules(ImmutableSet.of(shipmentScheduleId));
	}

	@Override
	public void invalidateShipmentSchedules(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		invalidSchedulesRepo.invalidateShipmentSchedules(shipmentScheduleIds);
	}

	@Override
	public void invalidateStorageSegment(@NonNull final IStorageSegment storageSegment)
	{
		final PInstanceId addToSelectionId = null;
		invalidSchedulesRepo.invalidateStorageSegments(ImmutableSet.of(storageSegment), addToSelectionId);
	}

	@Override
	public void invalidateStorageSegments(final Collection<IStorageSegment> storageSegments)
	{
		final PInstanceId addToSelectionId = null;
		invalidSchedulesRepo.invalidateStorageSegments(storageSegments, addToSelectionId);
	}

	@Override
	public void invalidateStorageSegments(
			@Nullable final Collection<IStorageSegment> storageSegments,
			@Nullable final PInstanceId addToSelectionId)
	{
		invalidSchedulesRepo.invalidateStorageSegments(storageSegments, addToSelectionId);
	}

	@Override
	public void invalidateJustForLines(final I_M_InOut shipment)
	{
		final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = inOutDAO.retrieveLines(shipment)
				.stream()
				.flatMap(this::streamShipmentScheduleIdsForInOutLine)
				.collect(ImmutableSet.toImmutableSet());

		invalidateShipmentSchedules(shipmentScheduleIds);
	}

	@Override
	public void invalidateJustForLine(final I_M_InOutLine shipmentLine)
	{
		final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = streamShipmentScheduleIdsForInOutLine(shipmentLine)
				.collect(ImmutableSet.toImmutableSet());

		invalidateShipmentSchedules(shipmentScheduleIds);
	}

	private Stream<ShipmentScheduleId> streamShipmentScheduleIdsForInOutLine(@NonNull final I_M_InOutLine inoutLine)
	{
		return shipmentScheduleAllocDAO.retrieveAllForInOutLine(inoutLine, I_M_ShipmentSchedule_QtyPicked.class)
				.stream()
				.map(alloc -> ShipmentScheduleId.ofRepoIdOrNull(alloc.getM_ShipmentSchedule_ID()))
				.filter(Predicates.notNull()); // shall not happen
	}

	@Override
	public void notifySegmentsChangedForShipment(final I_M_InOut shipment)
	{
		final List<IStorageSegment> storageSegments = new ArrayList<>();

		final int bpartnerId = shipment.getC_BPartner_ID();
		for (final I_M_InOutLine inoutLine : inOutDAO.retrieveLines(shipment))
		{
			final IStorageSegment storageSegment = createSegmentForInOutLine(bpartnerId, inoutLine);
			storageSegments.add(storageSegment);
		}

		notifySegmentsChanged(storageSegments);
	}

	@Override
	public void notifySegmentChangedForShipmentLine(final I_M_InOutLine shipmentLine)
	{
		final IStorageSegment storageSegment = createSegmentForInOutLine(shipmentLine.getM_InOut().getC_BPartner_ID(), shipmentLine);
		notifySegmentChanged(storageSegment);
	}

	/**
	 * Note that this method is overridden in the de.metas.handlingunits.base module!
	 * TODO: don't override this whole method, there are plenty of better ways
	 */
	protected IStorageSegment createSegmentForInOutLine(final int bPartnerId, @NonNull final I_M_InOutLine inoutLine)
	{
		return storageBL.createStorageSegmentBuilder()
				.addC_BPartner_ID(0) // we can't restrict the segment to the inOut-partner, because we don't know if the qty could in theory be reallocated to a *different* partner.
				// So we have to notify *all* partners' segments.
				.addM_Product_ID(inoutLine.getM_Product_ID())
				.addM_Locator_ID(inoutLine.getM_Locator_ID())
				.addM_AttributeSetInstance_ID(inoutLine.getM_AttributeSetInstance_ID())
				.build();
	}

	@Override
	public void notifySegmentChangedForShipmentSchedule(@NonNull final I_M_ShipmentSchedule schedule)
	{
		//
		// If shipment schedule updater is currently running in this thread, it means that updater changed this record
		// so there is NO need to invalidate it again.
		if (isShipmentScheduleUpdaterRunning())
		{
			return;
		}

		final IStorageSegment storageSegment = createSegmentForShipmentSchedule(schedule);
		notifySegmentChanged(storageSegment);
	}

	/**
	 * Note that this method is overridden in the de.metas.handlingunits.base module!
	 * TODO: don't override this whole method, there are plenty of better ways
	 */
	protected IStorageSegment createSegmentForShipmentSchedule(@NonNull final I_M_ShipmentSchedule schedule)
	{
		// we can't restrict the segment to the sched's bpartner, because we don't know if the qty could in theory be reallocated to a *different* partner.
		// So we have to notify *all* partners' segments.
		final int bpartnerId = 0;

		return storageBL.createStorageSegmentBuilder()
				.addC_BPartner_ID(bpartnerId)
				.addM_Product_ID(schedule.getM_Product_ID())
				.addWarehouseId(shipmentScheduleEffectiveBL.getWarehouseId(schedule))
				.addM_AttributeSetInstance_ID(schedule.getM_AttributeSetInstance_ID())
				.build();
	}

	@Override
	public void notifySegmentChangedForOrderLine(@NonNull final I_C_OrderLine orderLine)
	{
		final IStorageSegmentBuilder storageSegmentBuilder = storageBL.createStorageSegmentBuilder();

		// we can't restrict the segment to the sched's bpartner, because we don't know if the qty could in theory be reallocated to a *different* partner.
		// So we have to notify *all* partners' segments.
		final int bpartnerId = 0;
		final IStorageSegment storageSegment = storageSegmentBuilder
				.addC_BPartner_ID(bpartnerId)
				.addM_Product_ID(orderLine.getM_Product_ID())
				.addWarehouseIdIfNotNull(WarehouseId.ofRepoIdOrNull(orderLine.getM_Warehouse_ID()))
				.addM_AttributeSetInstance_ID(orderLine.getM_AttributeSetInstance_ID())
				.build();
		notifySegmentChanged(storageSegment);
	}

	@Override
	public void invalidateJustForOrderLine(@NonNull final I_C_OrderLine orderLine)
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID());
		final ShipmentScheduleId shipmentScheduleId = shipmentSchedulePA.getShipmentScheduleIdByOrderLineId(orderLineId);
		if (shipmentScheduleId == null)
		{
			return;
		}

		invalidateShipmentSchedule(shipmentScheduleId);
	}

	@Override
	public void invalidateForProduct(@NonNull final ProductId productId)
	{
		invalidSchedulesRepo.invalidateForProduct(productId);
	}

	@Override
	public void invalidateForHeaderAggregationKeys(@NonNull final Set<String> headerAggregationKeys)
	{
		invalidSchedulesRepo.invalidateForHeaderAggregationKeys(headerAggregationKeys);
	}

	@Override
	public void notifySegmentChanged(@NonNull final IStorageSegment storageSegment)
	{
		notifySegmentsChanged(ImmutableSet.of(storageSegment));
	}

	@Override
	public void notifySegmentsChanged(@NonNull final Collection<IStorageSegment> storageSegments)
	{
		if (storageSegments.isEmpty())
		{
			return;
		}

		final ShipmentScheduleSegmentChangedProcessor collector = ShipmentScheduleSegmentChangedProcessor.getOrCreateIfThreadInheritedElseNull(this);
		if (collector != null)
		{
			collector.addSegments(storageSegments);
		}
		else
		{
			final ITaskExecutorService taskExecutorService = Services.get(ITaskExecutorService.class);
			taskExecutorService.submit(
					() -> invalidateStorageSegments(storageSegments),
					this.getClass().getSimpleName());
		}
	}
}
