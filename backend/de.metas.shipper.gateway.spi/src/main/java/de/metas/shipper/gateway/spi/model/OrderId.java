package de.metas.shipper.gateway.spi.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipping.ShipperGatewayId;
import de.metas.util.Check;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.shipper.gateway.api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class OrderId
{
	@JsonProperty("gatewayId") @NonNull private final ShipperGatewayId shipperGatewayId;
	@JsonProperty("orderId") @NonNull private final String orderIdAsString;
	@JsonIgnore @Nullable private Integer orderIdAsInt = null; // lazy

	@JsonCreator
	private OrderId(
			@JsonProperty("gatewayId") @NonNull final ShipperGatewayId shipperGatewayId,
			@JsonProperty("orderId") @NonNull final String orderIdAsString)
	{
		Check.assumeNotEmpty(orderIdAsString, "orderIdAsString is not empty");
		this.shipperGatewayId = shipperGatewayId;
		this.orderIdAsString = orderIdAsString;
		this.orderIdAsInt = null;
	}

	private OrderId(
			@NonNull final ShipperGatewayId shipperGatewayId,
			@NonNull final DeliveryOrderId deliveryOrderId)
	{
		this.shipperGatewayId = shipperGatewayId;
		this.orderIdAsString = String.valueOf(deliveryOrderId.getRepoId());
		this.orderIdAsInt = deliveryOrderId.getRepoId();
	}

	public static OrderId of(@NonNull final ShipperGatewayId shipperGatewayId, @NonNull final String orderIdAsString)
	{
		return new OrderId(shipperGatewayId, orderIdAsString);
	}

	public static OrderId of(@NonNull final ShipperGatewayId shipperGatewayId, @NonNull final DeliveryOrderId deliveryOrderId)
	{
		return new OrderId(shipperGatewayId, deliveryOrderId);
	}

	public int getOrderIdAsInt()
	{
		if (orderIdAsInt == null)
		{
			try
			{
				orderIdAsInt = Integer.parseInt(orderIdAsString);
			}
			catch (final Exception ex)
			{
				throw new IllegalArgumentException("orderId shall be integer but it was: " + orderIdAsString, ex);
			}
		}
		return orderIdAsInt;
	}
}
