package de.metas.vertical.pharma.msv3.protocol.order;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
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
public class OrderCreateResponse
{
	public static OrderCreateResponse ok(final OrderResponse order)
	{
		return _builder().error(false).order(order).build();
	}

	public static OrderCreateResponse error(final String errorMsg)
	{
		return _builder().error(true).errorMsg(errorMsg).build();
	}

	@JsonProperty("error")
	boolean error;
	@JsonProperty("errorMsg")
	String errorMsg;

	@JsonProperty("order")
	OrderResponse order;

	@Builder(builderMethodName = "_builder")
	@JsonCreator
	private OrderCreateResponse(
			@JsonProperty("error") final boolean error,
			@JsonProperty("errorMsg") final String errorMsg,
			@JsonProperty("order") final OrderResponse order)
	{
		this.error = error;
		this.errorMsg = errorMsg;
		this.order = order;
	}

	public OrderResponse getOrder()
	{
		if (order == null)
		{
			throw new RuntimeException("No order response for " + this);
		}
		return order;
	}

}
