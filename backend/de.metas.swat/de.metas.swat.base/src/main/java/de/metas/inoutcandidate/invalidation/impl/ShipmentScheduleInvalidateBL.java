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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.inout.IInOutDAO;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ImmutableShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleSegments;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.inoutcandidate.picking_bom.PickingBOMsReversedIndex;
import de.metas.order.OrderLineId;
import de.metas.process.PInstanceId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.service.ITaskExecutorService;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ShipmentScheduleInvalidateBL implements IShipmentScheduleInvalidateBL
{
	/**
	 * Int sysconfig bounding the invalidation-segment accumulator during a long-running batch: when the accumulator
	 * ({@link ShipmentScheduleSegmentChangedProcessor}) reaches this size it flushes mid-batch (not only at
	 * AFTER_COMMIT). A value {@code <= 0} disables the mid-batch flush. Default {@value #DEFAULT_SegmentFlushThreshold}.
	 */
	static final String SYSCONFIG_SegmentFlushThreshold = "de.metas.inoutcandidate.ShipmentScheduleSegmentFlushThreshold";
	private static final int DEFAULT_SegmentFlushThreshold = 1000;

	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	private final IShipmentScheduleInvalidateRepository invalidSchedulesRepo = Services.get(IShipmentScheduleInvalidateRepository.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	protected final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
	protected final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final PickingBOMService pickingBOMService;

	/**
	 * The mid-batch flush threshold for the invalidation-segment accumulator. Owned here (this BL owns the
	 * invalidation concern and its collaborators); {@link ShipmentScheduleSegmentChangedProcessor} obtains it from
	 * its owning BL rather than reaching into the {@link ISysConfigBL} registry itself.
	 */
	int getSegmentFlushThreshold()
	{
		return sysConfigBL.getIntValue(SYSCONFIG_SegmentFlushThreshold, DEFAULT_SegmentFlushThreshold);
	}

	private boolean isShipmentScheduleUpdaterRunning()
	{
		// NOTE: cannot declare on top as field because it's a spring bean and it would make junit tests fail
		final IShipmentScheduleUpdater shipmentScheduleUpdater = Services.get(IShipmentScheduleUpdater.class);

		return shipmentScheduleUpdater.isRunning();
	}

	/**
	 * A non-stocked product (not Item+IsStocked) never competes for on-hand stock, so a change narrows to its own
	 * schedule. A null product (product-less charge/freight line) narrows the same way, since {@link IProductBL#isStocked}
	 * is null-safe and returns {@code false} for it.
	 */
	private boolean shouldNarrowToSelf(@Nullable final ProductId productId)
	{
		return !productBL.isStocked(productId);
	}

	/**
	 * Narrow-decision from a raw {@code M_Product_ID} that may be 0: a charge/freight line has no product
	 * ({@code M_Product_ID=0}; see {@code MOrderLine}/{@code MInOutLine} zeroing it when {@code C_Charge_ID} is set).
	 * One raw-id entry point keeps every product-bearing line type (inout line, order line, …) on the same
	 * {@link #shouldNarrowToSelf(ProductId)} decision.
	 */
	private boolean shouldNarrowToSelfByProductRepoId(final int productRepoId)
	{
		return shouldNarrowToSelf(ProductId.ofRepoIdOrNull(productRepoId));
	}

	@Override
	public boolean isFlaggedForRecompute(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return invalidSchedulesRepo.isFlaggedForRecompute(shipmentScheduleId);
	}

	@Override
	public void flagForRecompute(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		flagForRecompute(ImmutableSet.of(shipmentScheduleId));
	}

	@Override
	public void flagForRecompute(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		invalidSchedulesRepo.invalidateShipmentSchedules(shipmentScheduleIds);
	}

	@Override
	public void flagForRecomputeStorageSegment(@NonNull final IShipmentScheduleSegment segment)
	{
		final PInstanceId addToSelectionId = null;
		invalidSchedulesRepo.invalidateStorageSegments(ImmutableSet.of(segment), addToSelectionId);
	}

	@Override
	public void flagSegmentForRecompute(final Collection<IShipmentScheduleSegment> segments)
	{
		final PInstanceId addToSelectionId = null;
		invalidSchedulesRepo.invalidateStorageSegments(segments, addToSelectionId);
	}

	@Override
	public void flagSegmentsForRecompute(
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

		flagForRecompute(shipmentScheduleIds);
	}

	@Override
	public void flagForRecompute(final I_M_InOutLine shipmentLine)
	{
		final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = streamShipmentScheduleIdsForInOutLine(shipmentLine)
				.collect(ImmutableSet.toImmutableSet());

		flagForRecompute(shipmentScheduleIds);
	}

	private Stream<ShipmentScheduleId> streamShipmentScheduleIdsForInOutLine(@NonNull final I_M_InOutLine inoutLine)
	{
		return shipmentScheduleAllocDAO.retrieveAllForInOutLine(inoutLine, I_M_ShipmentSchedule_QtyPicked.class)
				.stream()
				.map(alloc -> ShipmentScheduleId.ofRepoIdOrNull(alloc.getM_ShipmentSchedule_ID()))
				.filter(Objects::nonNull); // shall not happen
	}

	@Override
	public void notifySegmentsChangedForShipment(final I_M_InOut shipment)
	{
		final List<IShipmentScheduleSegment> segments = new ArrayList<>();

		final int bpartnerId = shipment.getC_BPartner_ID();
		for (final I_M_InOutLine inoutLine : inOutDAO.retrieveLines(shipment))
		{
			if (shouldNarrowToSelfByProductRepoId(inoutLine.getM_Product_ID()))
			{
				flagForRecompute(inoutLine);
			}
			else
			{
				segments.add(createSegmentForInOutLine(bpartnerId, inoutLine));
			}
		}

		notifySegmentsChanged(segments);
	}

	@Override
	public void notifySegmentChangedForShipmentLine(final I_M_InOutLine shipmentLine)
	{
		if (shouldNarrowToSelfByProductRepoId(shipmentLine.getM_Product_ID()))
		{
			flagForRecompute(shipmentLine);
		}
		else
		{
			notifySegmentChanged(createSegmentForInOutLine(shipmentLine.getM_InOut().getC_BPartner_ID(), shipmentLine));
		}
	}

	/**
	 * Note that this method is overridden in the de.metas.handlingunits.base module!
	 * TODO: don't override this whole method, there are plenty of better ways
	 */
	protected IShipmentScheduleSegment createSegmentForInOutLine(final int bPartnerId, @NonNull final I_M_InOutLine inoutLine)
	{
		return ShipmentScheduleSegments.builder()
				.bpartnerId(0) // we can't restrict the segment to the inOut-partner, because we don't know if the qty could in theory be reallocated to a *different* partner.
				// So we have to notify *all* partners' segments.
				.productId(inoutLine.getM_Product_ID())
				.locatorId(inoutLine.getM_Locator_ID())
				.attributeSetInstanceId(inoutLine.getM_AttributeSetInstance_ID())
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

		// M_ShipmentSchedule.M_Product_ID is Mandatory and never 0 (schedules are only created for real products),
		// so — unlike the InOutLine/OrderLine siblings, which can be product-less charge lines — no charge-line guard is needed here.
		final ProductId productId = ProductId.ofRepoId(schedule.getM_Product_ID());
		if (shouldNarrowToSelf(productId))
		{
			flagForRecompute(ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID()));
		}
		else
		{
			notifySegmentChanged(createSegmentForShipmentSchedule(schedule));
		}
	}

	@Override
	public void notifySegmentChangedForShipmentScheduleInclSched(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());

		flagForRecompute(shipmentScheduleId); // 08746: make sure that at any rate, the schedule itself is invalidated, even if it has delivery rule "force"
		notifySegmentChangedForShipmentSchedule(shipmentSchedule);
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
				.bpartnerId(bpartnerId)
				.productId(schedule.getM_Product_ID())
				.warehouseId(shipmentScheduleEffectiveBL.getWarehouseId(schedule))
				.attributeSetInstanceId(schedule.getM_AttributeSetInstance_ID())
				.build();
	}

	@Override
	public void notifySegmentChangedForOrderLine(@NonNull final I_C_OrderLine orderLine)
	{
		if (shouldNarrowToSelfByProductRepoId(orderLine.getM_Product_ID()))
		{
			invalidateJustForOrderLine(orderLine);
		}
		else
		{
			// we can't restrict the segment to the order line's bpartner, because the qty could in theory be
			// reallocated to a *different* partner, so we notify *all* partners' segments (bpartnerId 0).
			notifySegmentChanged(ShipmentScheduleSegments.builder()
					.bpartnerId(0)
					.productId(orderLine.getM_Product_ID())
					.warehouseIdIfNotNull(WarehouseId.ofRepoIdOrNull(orderLine.getM_Warehouse_ID()))
					.attributeSetInstanceId(orderLine.getM_AttributeSetInstance_ID())
					.build());
		}
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

		flagForRecompute(shipmentScheduleId);
	}

	@Override
	public void flagForRecompute(@NonNull final ProductId productId)
	{
		invalidSchedulesRepo.invalidateForProduct(productId);
	}

	@Override
	public void flagHeaderAggregationKeysForRecompute(@NonNull final Set<String> headerAggregationKeys)
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
				.filter(segment -> !segment.isInvalid())
				.flatMap(this::explodeByPickingBOMs)
				.collect(ImmutableList.toImmutableList());
		if (segmentsEffective.isEmpty())
		{
			return;
		}

		final ShipmentScheduleSegmentChangedProcessor collector = ShipmentScheduleSegmentChangedProcessor.getOrCreateIfThreadInheritedElseNull(this);
		if (collector != null)
		{
			collector.addSegments(segmentsEffective); // they will be flagged for recompute after commit
		}
		else
		{
			final ITaskExecutorService taskExecutorService = Services.get(ITaskExecutorService.class);
			taskExecutorService.submit(
					() -> flagSegmentForRecompute(segmentsEffective),
					this.getClass().getSimpleName());
		}
	}

	@Override
	public void deleteRecomputeMarkers(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		invalidSchedulesRepo.deleteRecomputeMarkers(shipmentScheduleId);
	}

	@VisibleForTesting
	Stream<IShipmentScheduleSegment> explodeByPickingBOMs(final IShipmentScheduleSegment segment)
	{
		if (segment.isAnyProduct())
		{
			return Stream.of(segment);
		}

		final PickingBOMsReversedIndex pickingBOMsReversedIndex = pickingBOMService.getPickingBOMsReversedIndex();
		final Set<ProductId> componentIds = ProductId.ofRepoIds(segment.getProductIds());
		final ImmutableSet<ProductId> pickingBOMProductIds = pickingBOMsReversedIndex.getBOMProductIdsByComponentIds(componentIds);
		if (pickingBOMProductIds.isEmpty())
		{
			return Stream.of(segment);
		}

		final ImmutableShipmentScheduleSegment pickingBOMsSegment = ImmutableShipmentScheduleSegment.builder()
				.productIds(ProductId.toRepoIds(pickingBOMProductIds))
				.anyBPartner()
				.locatorIds(segment.getLocatorIds())
				.warehouseIds(segment.getWarehouseIds())
				.build();

		return Stream.of(segment, pickingBOMsSegment);
	}

}
