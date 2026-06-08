/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.handlingunits.shipmentschedule.api;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Collection;

@Value
@Builder
public class GenerateShipmentsForSchedulesRequest
{
	@NonNull M_ShipmentSchedule_QuantityTypeToUse quantityTypeToUse;
	@Nullable ShipmentScheduleAndJobScheduleIdSet scheduleIds;
	@Nullable ImmutableSet<HuId> onlyLUIds;

	/**
	 * If {@code false} and HUs are picked on-the-fly, then those HUs are created as CUs that are taken from bigger LUs, TUs or CUs (the default).
	 * If {@code true}, then the on-the-fly picked HUs are in addition created as TUs, using the respective shipment schedules' packing instructions.
	 */
	@Builder.Default boolean onTheFlyPickToPackingInstructions = false;

	@NonNull Boolean isCompleteShipment;
	boolean isCloseShipmentSchedules;

	@Nullable Boolean isShipDateToday;

	/**
	 * The shipments are generally created via async-workpackage and this flag decides if the caller wants to wait for it.
	 * By default, it is set to {@code true} for backwards compatibility.
	 *
	 * @see ShipmentService#generateShipmentsForScheduleIds(GenerateShipmentsForSchedulesRequest)
	 */
	@Builder.Default boolean waitForShipments = true;

	/**
	 * When {@code true}, the {@code groupSchedulesByAsyncBatch} step inside
	 * {@link ShipmentService#generateShipmentsForScheduleIds} runs in the caller's inherited transaction
	 * instead of a new transaction.
	 *
	 * <p>Use this <em>only</em> when the caller already holds an open transaction that locks
	 * {@code M_ShipmentSchedule} rows (e.g. the mass-printing flow inside
	 * {@link de.metas.handlingunits.picking.job.service.commands.PickingJobCompleteCommand}),
	 * where a nested {@code callInNewTrx} would dead-lock waiting for the same row locks.
	 *
	 * <p>The default is {@code false}: all other callers (normal shipment generation, EDI, REST)
	 * require the new-transaction isolation so that newly written async-batch records are committed
	 * and visible to downstream consumers (e.g. DESADV-pack creation) before they query them.
	 */
	@Builder.Default boolean groupSchedulesInInheritedTrx = false;

	@SuppressWarnings("unused")
	public static class GenerateShipmentsForSchedulesRequestBuilder
	{
		public GenerateShipmentsForSchedulesRequestBuilder shipmentScheduleIds(@Nullable final Collection<ShipmentScheduleId> shipmentScheduleIds)
		{
			return scheduleIds(shipmentScheduleIds != null ? ShipmentScheduleAndJobScheduleIdSet.ofShipmentScheduleIds(shipmentScheduleIds) : null);
		}
	}
}
