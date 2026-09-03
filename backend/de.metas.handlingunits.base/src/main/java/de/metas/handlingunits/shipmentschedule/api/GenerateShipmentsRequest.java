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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.common.util.EmptyUtil;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.shipmentschedule.spi.impl.ShipmentScheduleExternalInfo;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Map.Entry;

@Value
@Builder
public class GenerateShipmentsRequest
{
	@NonNull ShipmentScheduleAndJobScheduleIdSet scheduleIds;
	@Nullable ImmutableSet<HuId> onlyLUIds;

	@NonNull ImmutableMap<ShipmentScheduleId, ShipmentScheduleExternalInfo> scheduleToExternalInfo;

	@NonNull QtyToDeliverMap scheduleToQuantityToDeliverOverride;

	@NonNull M_ShipmentSchedule_QuantityTypeToUse quantityTypeToUse;

	/**
	 * If {@code false} and HUs are picked on-the-fly, then those HUs are created as CUs that are taken from bigger LUs, TUs or CUs (the default).
	 * If {@code true}, then the on-the-fly picked HUs are in addition created as TUs, using the respective shipment schedules' packing instructions.
	 */
	@Builder.Default boolean onTheFlyPickToPackingInstructions = false;

	@NonNull AsyncBatchId asyncBatchId;

	@NonNull Boolean isCompleteShipment;
	boolean isCloseShipmentSchedules;

	@Nullable Boolean isShipDateToday;

	/**
	 * The shipments are created via async-workpackage and this flag decides if the caller wants to wait for them.
	 * By default, it is set to true for backwards compatibility.
	 *
	 * @see ShipmentService#generateShipments(GenerateShipmentsRequest)
	 */
	@Builder.Default boolean waitForShipments = true;

	/**
	 * {@code M_Delivery_Planning_ID} to stamp onto every shipment header created for this request, or
	 * {@code 0} for none (the default, i.e. every caller that is not the delivery-planning
	 * generate-shipment process).
	 * <p>
	 * It has to travel with the request rather than being set on the finished shipment, because the
	 * shipment is <b>completed</b> inside the generation workpackage: an {@code M_Delivery_Planning_ID}
	 * written afterwards is invisible to the document's {@code TIMING_AFTER_COMPLETE} interceptor, which is
	 * what derives the planning's delivered state, its actual quantities, its {@code Processed} flag and the
	 * shipment back-link from the completion.
	 * <p>
	 * A plain repo id, because {@code DeliveryPlanningId} lives in {@code de.metas.deliveryplanning.base},
	 * which depends on this module.
	 */
	int deliveryPlanningId;

	public ImmutableMap<ShipmentScheduleId, String> extractShipmentDocumentNos()
	{
		final ImmutableMap.Builder<ShipmentScheduleId, String> result = ImmutableMap.builder();

		for (final Entry<ShipmentScheduleId, ShipmentScheduleExternalInfo> entry : scheduleToExternalInfo.entrySet())
		{
			final String documentNo = entry.getValue().getDocumentNo();
			if (EmptyUtil.isBlank(documentNo))
			{
				continue;
			}
			result.put(entry.getKey(), documentNo);
		}
		return result.build();
	}
}
