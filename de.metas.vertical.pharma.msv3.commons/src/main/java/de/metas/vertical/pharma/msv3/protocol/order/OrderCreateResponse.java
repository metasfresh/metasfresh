package de.metas.vertical.pharma.msv3.protocol.order;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.Id;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.msv3.commons
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class OrderCreateResponse
{
	public static OrderCreateResponse ok(final OrderResponse order)
	{
		return _builder().order(order).build();
	}

	public static OrderCreateResponse error(Id orderId, BPartnerId bpartnerId, String errorMsg)
	{
		return _builder()
				.error(OrderCreateError.builder().orderId(orderId).bpartnerId(bpartnerId).errorMsg(errorMsg).build())
				.build();
	}

	@JsonProperty("error")
	OrderCreateError error;

	@JsonProperty("order")
	OrderResponse order;

	@Builder(builderMethodName = "_builder")
	@JsonCreator
	private OrderCreateResponse(
			@JsonProperty("error") final OrderCreateError error,
			@JsonProperty("order") final OrderResponse order)
	{
		if (error == null && order == null)
		{
			throw new IllegalArgumentException("order or error shall be set");
		}
		if (error != null && order != null)
		{
			throw new IllegalArgumentException("either order or error shall be set");
		}

		this.error = error;
		this.order = order;
	}

	public boolean isError()
	{
		return error != null;
	}

	public OrderCreateError getError()
	{
		if (error == null)
		{
			throw new RuntimeException("Not an error response: " + this);
		}
		return error;
	}

	public OrderResponse getOrder()
	{
		if (order == null)
		{
			throw new RuntimeException("Not an order response: " + this);
		}
		return order;
	}

}
