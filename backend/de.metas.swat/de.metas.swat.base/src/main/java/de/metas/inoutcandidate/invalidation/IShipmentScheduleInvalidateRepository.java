/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.inoutcandidate.invalidation;

import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.util.ISingletonService;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.IQuery;

import java.util.Collection;
import java.util.Properties;
import java.util.Set;

public interface IShipmentScheduleInvalidateRepository extends ISingletonService
{
	boolean isFlaggedForRecompute(ShipmentScheduleId shipmentScheduleId);

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

	void markAllToRecomputeOutOfTrx(PInstanceId pinstanceId);

	/**
	 * Delete M_ShipmentSchedule_Recompute records for given tag
	 */
	void deleteRecomputeMarkersOutOfTrx(PInstanceId adPInstanceId);

	/**
	 * Untag M_ShipmentSchedule_Recompute records which were tagged with given tag
	 */
	void releaseRecomputeMarkerOutOfTrx(PInstanceId adPInstanceId);

	IQueryFilter<I_M_ShipmentSchedule> createInvalidShipmentSchedulesQueryFilter(PInstanceId pinstanceId);

	void invalidateShipmentSchedulesFor(IQuery<I_M_ShipmentSchedule> query);
}
