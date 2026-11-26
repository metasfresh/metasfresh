package de.metas.inoutcandidate.invalidation;

import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.QueryLimit;
import org.compiere.model.IQuery;

import java.util.Collection;
import java.util.Properties;
import java.util.Set;

/*
 * #%L
 * de.metas.swat.base
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

public interface IShipmentScheduleInvalidateRepository extends ISingletonService
{
	boolean isFlaggedForRecompute(ShipmentScheduleId shipmentScheduleId);

	boolean isAllValid(@NonNull Set<ShipmentScheduleId> shipmentScheduleIds);

	/**
	 * Invalidate all shipment schedules for give product.
	 *
	 * @deprecated please be more selective with the invalidation, using storage segments
	 */
	@Deprecated
	void invalidateForProduct(ProductId productId);

	/**
	 * Invalidates all shipment schedules which have one of the given <code>headerAggregationKeys</code>.
	 */
	void invalidateForHeaderAggregationKeys(Set<String> headerAggregationKeys);

	/**
	 * Invalidate given shipment schedules, even if they are already Processed.
	 */
	void invalidateShipmentSchedules(Set<ShipmentScheduleId> shipmentScheduleIds);

	/**
	 * Invalidates shipment schedules for the given storage segments.
	 * <p>
	 * <b>IMPORTANT:</b> won't invalidate any processed schedules.
	 *
	 * @param addToSelectionId if not null will add the invalidated records to given selection
	 */
	void invalidateStorageSegments(Collection<IShipmentScheduleSegment> storageSegments, PInstanceId addToSelectionId);

	void invalidateSchedulesForSelection(PInstanceId pinstanceId);

	/**
	 * Invalidate (i.e. schedule for recompute) all records from current tenant
	 */
	void invalidateAll(Properties ctx);

	/**
	 * @param pinstanceId the {@code AD_PInstance_ID} to tag the matched {@code M_ShipmentSchedule_Recompute} rows with
	 * @param maxToProcess if limited, bounds the tagging to <b>whole products</b> (stock-coherent unit): products are
	 *                     accumulated in ascending {@code M_Product_ID} order until their cumulative distinct
	 *                     shipment-schedule count would reach {@code maxToProcess}; a product's schedules are
	 *                     <b>never split</b> across the boundary (splitting would let {@code ShipmentScheduleUpdater}
	 *                     recompute the same product's schedules against two different on-hand-stock snapshots,
	 *                     double-allocating stock), and at least one whole product is always tagged even if it alone
	 *                     exceeds {@code maxToProcess}. All of each selected product's duplicate recompute markers
	 *                     are still tagged. {@link QueryLimit#NO_LIMIT} keeps the previous unbounded behavior.
	 */
	void markAllToRecomputeOutOfTrx(PInstanceId pinstanceId, QueryLimit maxToProcess);

	/**
	 * @return {@code true} if at least one {@code M_ShipmentSchedule_Recompute} row is still untagged
	 *         (i.e. {@code AD_PInstance_ID IS NULL}) <b>and taggable</b> (its {@code M_ShipmentSchedule} still
	 *         exists, which is what {@link #markAllToRecomputeOutOfTrx(PInstanceId, QueryLimit)} requires) --
	 *         i.e. there is more backlog a follow-up recompute pass could actually pick up. An orphaned marker
	 *         (no schedule row; there is no FK) can never be tagged and is deliberately not counted.
	 *         This is the correct signal for "should a follow-up bounded run be enqueued", as opposed to
	 *         comparing a pass's recomputed count against its {@code maxToProcess} (which, because the tag
	 *         unit is a whole product, is not reliable: a pass can recompute more or fewer schedules than
	 *         {@code maxToProcess}).
	 */
	boolean existsUntaggedRecomputeMarkers();

	/**
	 * Delete M_ShipmentSchedule_Recompute records for given tag
	 */
	void deleteRecomputeMarkersOutOfTrx(PInstanceId adPInstanceId);

	/**
	 * Deletes every M_ShipmentSchedule_Recompute record of the given schedule, including tagged ones -- once the
	 * schedule is gone, no marker of it can ever be tagged again, so leaving one behind only creates an orphan.
	 */
	void deleteRecomputeMarkers(ShipmentScheduleId shipmentScheduleId);

	/**
	 * Untag M_ShipmentSchedule_Recompute records which were tagged with given tag
	 */
	void releaseRecomputeMarkerOutOfTrx(PInstanceId adPInstanceId);

	IQueryFilter<I_M_ShipmentSchedule> createInvalidShipmentSchedulesQueryFilter(PInstanceId pinstanceId);

	void invalidateShipmentSchedulesFor(IQuery<I_M_ShipmentSchedule> query);
}
