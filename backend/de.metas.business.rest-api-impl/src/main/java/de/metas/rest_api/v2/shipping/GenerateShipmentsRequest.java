/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.rest_api.v2.shipping;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.common.util.EmptyUtil;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.spi.impl.ShipmentScheduleExternalInfo;
import de.metas.inout.ShipmentScheduleId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Map.Entry;

@Value
@Builder
class GenerateShipmentsRequest
{
	@NonNull
	ImmutableSet<ShipmentScheduleId> scheduleIds;

	@NonNull
	ImmutableMap<ShipmentScheduleId, ShipmentScheduleExternalInfo> scheduleToExternalInfo;

	@NonNull
	ImmutableMap<ShipmentScheduleId, BigDecimal> scheduleToQuantityToDeliverOverride;
	
	@NonNull
	M_ShipmentSchedule_QuantityTypeToUse quantityTypeToUse;

	@NonNull
	AsyncBatchId asyncBatchId;

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
