package de.metas.vertical.pharma.msv3.protocol.order;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.FaultInfo;
import de.metas.vertical.pharma.msv3.protocol.types.Id;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
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
public class OrderResponse
{
	@JsonProperty("bpartnerId")
	BPartnerId bpartnerId;

	@JsonProperty("orderId")
	Id orderId;

	@JsonProperty("supportId")
	SupportIDType supportId;

	@JsonProperty("nightOperation")
	boolean nightOperation;

	@JsonProperty("orderPackages")
	List<OrderResponsePackage> orderPackages;

	@Builder
	private OrderResponse(
			@JsonProperty("bpartnerId") @NonNull final BPartnerId bpartnerId,
			@JsonProperty("orderId") @NonNull final Id orderId,
			@JsonProperty("supportId") @NonNull final SupportIDType supportId,
			@JsonProperty("nightOperation") @NonNull final Boolean nightOperation,
			@JsonProperty("orderPackages") @NonNull @Singular final List<OrderResponsePackage> orderPackages)
	{
		this.bpartnerId = bpartnerId;
		this.orderId = orderId;
		this.supportId = supportId;
		this.nightOperation = nightOperation;
		this.orderPackages = ImmutableList.copyOf(orderPackages);
	}

	public FaultInfo getSingleOrderFaultInfo()
	{
		return getSingleOrderPackage().getFaultInfo();
	}

	private OrderResponsePackage getSingleOrderPackage()
	{
		final List<OrderResponsePackage> responseOrders = getOrderPackages();
		if (responseOrders.size() != 1)
		{
			throw new IllegalStateException("Only one order was expected but we have: " + responseOrders);
		}
		return responseOrders.getFirst();
	}
}
