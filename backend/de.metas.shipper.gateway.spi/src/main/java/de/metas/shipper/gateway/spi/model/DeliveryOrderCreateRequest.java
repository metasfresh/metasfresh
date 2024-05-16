package de.metas.shipper.gateway.spi.model;

import de.metas.async.AsyncBatchId;
import de.metas.common.util.CoalesceUtil;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

/*
 * #%L
 * de.metas.shipper.gateway.api
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

@Value
public class DeliveryOrderCreateRequest
{
	Set<Integer> packageIds;
	ShipperTransportationId shipperTransportationId;

	String shipperGatewayId;

	LocalDate pickupDate;
	LocalTime timeFrom;
	LocalTime timeTo;
	AsyncBatchId asyncBatchId;

	@Builder
	public DeliveryOrderCreateRequest(
			@NonNull final LocalDate pickupDate,
			@NonNull @Singular final Set<Integer> packageIds,
			final ShipperTransportationId shipperTransportationId,
			@NonNull final String shipperGatewayId,
 			@Nullable final LocalTime timeFrom,
			@Nullable final LocalTime timeTo,
			@Nullable final AsyncBatchId asyncBatchId)
	{
		this.pickupDate = pickupDate;
		this.packageIds = Check.assumeNotEmpty(packageIds, "packageIds is not empty");
		this.shipperTransportationId = shipperTransportationId;
		this.shipperGatewayId = shipperGatewayId;
		this.timeFrom = CoalesceUtil.coalesceNotNull(timeFrom, LocalTime.MIN);
		this.timeTo = CoalesceUtil.coalesceNotNull(timeTo, LocalTime.MAX);
		this.asyncBatchId = asyncBatchId;
	}
}
