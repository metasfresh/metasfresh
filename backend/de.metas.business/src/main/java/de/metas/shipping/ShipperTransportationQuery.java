/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipping;

import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.shipping.model.ShipperTransportationId;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
public class ShipperTransportationQuery
{
	@Nullable ShipperTransportationId id;
	@Nullable OrderId orderId;
	@Nullable List<OrderLineId> orderLineIds;
	@Builder.Default boolean onlyRecordsWithNullOrderLineId = false;

	public ShipperTransportationQuery(@Nullable final ShipperTransportationId id,
									  @Nullable final OrderId orderId,
									  @Nullable final List<OrderLineId> orderLineIds,
									  final boolean onlyRecordsWithNullOrderLineId)
	{
		if (id == null && orderId == null && orderLineIds == null)
		{
			throw new IllegalArgumentException("At least one query parameter must be non-null");
		}
		this.id = id;
		this.orderId = orderId;
		this.onlyRecordsWithNullOrderLineId = onlyRecordsWithNullOrderLineId;
		//if onlyRecordsWithNullOrderLineId is true, then orderLineIds is ignored
		this.orderLineIds = orderLineIds;

	}
}
