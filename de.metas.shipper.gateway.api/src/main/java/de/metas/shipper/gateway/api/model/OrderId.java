package de.metas.shipper.gateway.api.model;

import org.adempiere.util.Check;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

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
	public static final OrderId of(final String shipperGatewayId, final String orderIdAsString)
	{
		return new OrderId(shipperGatewayId, orderIdAsString);
	}

	@JsonProperty("gatewayId")
	private final String shipperGatewayId;
	@JsonProperty("orderId")
	private final String orderIdAsString;
	@JsonIgnore
	private Integer orderIdAsInt = null; // lazy

	@JsonCreator
	private OrderId(
			@JsonProperty("gatewayId") final String shipperGatewayId,
			@JsonProperty("orderId") final String orderIdAsString)
	{
		Check.assumeNotEmpty(shipperGatewayId, "shipperGatewayId is not empty");
		Check.assumeNotEmpty(orderIdAsString, "orderIdAsString is not empty");
		this.shipperGatewayId = shipperGatewayId;
		this.orderIdAsString = orderIdAsString;
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
