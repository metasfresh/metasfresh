package de.metas.inoutcandidate.invalidation.impl;

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
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.slf4j.Logger;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.inout.IInOutDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleSegments;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.logging.LogManager;
import de.metas.order.OrderLineId;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;

public class ShipmentScheduleInvalidateBL implements IShipmentScheduleInvalidateBL
{
	private static final Logger logger = LogManager.getLogger(ShipmentScheduleInvalidateBL.class);
	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	private final IShipmentScheduleInvalidateRepository invalidSchedulesRepo = Services.get(IShipmentScheduleInvalidateRepository.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	protected final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
	protected final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private PickingBOMService pickingBOMService = SpringContextHolder.instance.getBean(PickingBOMService.class);

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
	public void invalidateStorageSegment(@NonNull final IShipmentScheduleSegment segment)
	{
		final PInstanceId addToSelectionId = null;
		invalidSchedulesRepo.invalidateStorageSegments(ImmutableSet.of(segment), addToSelectionId);
	}

	@Override
	public void invalidateStorageSegments(final Collection<IShipmentScheduleSegment> segments)
	{
		final PInstanceId addToSelectionId = null;
		invalidSchedulesRepo.invalidateStorageSegments(segments, addToSelectionId);
	}

	@Override
	public void invalidateStorageSegments(
			@Nullable final Collection<IShipmentScheduleSegment> segments,
			@Nullable final PInstanceId addToSelectionId)
	{
		invalidSchedulesRepo.invalidateStorageSegments(segments, addToSelectionId);
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
		final List<IShipmentScheduleSegment> segments = new ArrayList<>();

		final int bpartnerId = shipment.getC_BPartner_ID();
		for (final I_M_InOutLine inoutLine : inOutDAO.retrieveLines(shipment))
		{
			final IShipmentScheduleSegment segment = createSegmentForInOutLine(bpartnerId, inoutLine);
			segments.add(segment);
		}

		notifySegmentsChanged(segments);
	}

	@Override
	public void notifySegmentChangedForShipmentLine(final I_M_InOutLine shipmentLine)
	{
		final IShipmentScheduleSegment segment = createSegmentForInOutLine(shipmentLine.getM_InOut().getC_BPartner_ID(), shipmentLine);
		notifySegmentChanged(segment);
	}

	/**
	 * Note that this method is overridden in the de.metas.handlingunits.base module!
	 * TODO: don't override this whole method, there are plenty of better ways
	 */
	protected IShipmentScheduleSegment createSegmentForInOutLine(final int bPartnerId, @NonNull final I_M_InOutLine inoutLine)
	{
		return ShipmentScheduleSegments.builder()
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

		final IShipmentScheduleSegment segment = createSegmentForShipmentSchedule(schedule);
		notifySegmentChanged(segment);
	}

	/**
	 * Note that this method is overridden in the de.metas.handlingunits.base module!
	 * TODO: don't override this whole method, there are plenty of better ways
	 */
	protected IShipmentScheduleSegment createSegmentForShipmentSchedule(@NonNull final I_M_ShipmentSchedule schedule)
	{
		// we can't restrict the segment to the sched's bpartner, because we don't know if the qty could in theory be reallocated to a *different* partner.
		// So we have to notify *all* partners' segments.
		final int bpartnerId = 0;

		return ShipmentScheduleSegments.builder()
				.addC_BPartner_ID(bpartnerId)
				.addM_Product_ID(schedule.getM_Product_ID())
				.addWarehouseId(shipmentScheduleEffectiveBL.getWarehouseId(schedule))
				.addM_AttributeSetInstance_ID(schedule.getM_AttributeSetInstance_ID())
				.build();
	}

	@Override
	public void notifySegmentChangedForOrderLine(@NonNull final I_C_OrderLine orderLine)
	{

		// we can't restrict the segment to the sched's bpartner, because we don't know if the qty could in theory be reallocated to a *different* partner.
		// So we have to notify *all* partners' segments.
		final int bpartnerId = 0;
		final IShipmentScheduleSegment segment = ShipmentScheduleSegments.builder()
				.addC_BPartner_ID(bpartnerId)
				.addM_Product_ID(orderLine.getM_Product_ID())
				.addWarehouseIdIfNotNull(WarehouseId.ofRepoIdOrNull(orderLine.getM_Warehouse_ID()))
				.addM_AttributeSetInstance_ID(orderLine.getM_AttributeSetInstance_ID())
				.build();
		notifySegmentChanged(segment);
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
	public void notifySegmentChanged(@NonNull final IShipmentScheduleSegment segment)
	{
		notifySegmentsChanged(ImmutableSet.of(segment));
	}

	@Override
	public void notifySegmentsChanged(@NonNull final Collection<IShipmentScheduleSegment> segments)
	{
		if (segments.isEmpty())
		{
			return;
		}

		final ImmutableList<IShipmentScheduleSegment> segmentsEffective = segments.stream()
				.flatMap(this::explodeByPickingBOMs)
				.collect(ImmutableList.toImmutableList());
		if (segmentsEffective.isEmpty())
		{
			// shall not happen
			logger.warn("Nothing to notify because segments were exploded to empty list: {}", segments);
			return;
		}

		final ShipmentScheduleSegmentChangedProcessor collector = ShipmentScheduleSegmentChangedProcessor.getOrCreateIfThreadInheritedElseNull(this);
		if (collector != null)
		{
			collector.addSegments(segmentsEffective);
		}
		else
		{
			final ITaskExecutorService taskExecutorService = Services.get(ITaskExecutorService.class);
			taskExecutorService.submit(
					() -> invalidateStorageSegments(segmentsEffective),
					this.getClass().getSimpleName());
		}
	}

	private Stream<IShipmentScheduleSegment> explodeByPickingBOMs(final IShipmentScheduleSegment segment)
	{
		// TODO
		return Stream.of(segment);
//		final PickingBOMsReversedIndex pickingBOMsReversedIndex = pickingBOMService.getPickingBOMsReversedIndex();
//		
//		final Set<IShipmentScheduleSegment> result = new HashSet<>();
//		result.add(segment);
//		segment.
//		pickingBOMsReversedIndex.getBOMProductIdsByComponentId(segment.get)
	}

}
