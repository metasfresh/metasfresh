package de.metas.vertical.pharma.msv3.protocol.order;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

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
public class OrderCreateRequestPackage
{
	@JsonProperty("id")
	Id id;

	@JsonProperty("orderType")
	OrderType orderType;

	@JsonProperty("orderIdentification")
	/** One of 4 predefined or one free identifier. May deviate from the request identifier and be replaced by one of the 4 predefined identifiers (see specifications). */
	String orderIdentification;

	@JsonProperty("supportId")
	SupportIDType supportId;

	@JsonProperty("packingMaterialId")
	String packingMaterialId;

	@JsonProperty("items")
	List<OrderCreateRequestPackageItem> items;

	@Builder
	private OrderCreateRequestPackage(
			@JsonProperty("id") @NonNull final Id id,
			@JsonProperty("orderType") @NonNull final OrderType orderType,
			@JsonProperty("orderIdentification") @NonNull final String orderIdentification,
			@JsonProperty("supportId") @NonNull final SupportIDType supportId,
			@JsonProperty("packingMaterialId") @NonNull final String packingMaterialId,
			@JsonProperty("items") @Singular @NonNull final List<OrderCreateRequestPackageItem> items)
	{
		if (items.isEmpty())
		{
			throw new IllegalArgumentException("Order package shall have at least one item");
		}

		this.id = id;
		this.orderType = orderType;
		this.orderIdentification = orderIdentification;
		this.supportId = supportId;
		this.packingMaterialId = packingMaterialId;
		this.items = ImmutableList.copyOf(items);
	}
}
