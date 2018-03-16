package de.metas.vertical.pharma.msv3.protocol.order;

import com.google.common.collect.ImmutableList;

import de.metas.vertical.pharma.msv3.protocol.types.Id;
import lombok.Builder;
import lombok.NonNull;
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

@Value
public class OrderResponsePackage
{
	Id id;
	OrderType orderType;
	/** One of 4 predefined or one free identifier. May deviate from the request identifier and be replaced by one of the 4 predefined identifiers (see specifications). */
	String orderIdentification;
	SupportIDType supportId;
	String packingMaterialId;
	ImmutableList<OrderResponsePackageItem> items;

	@Builder
	private OrderResponsePackage(
			@NonNull final Id id,
			@NonNull final OrderType orderType,
			@NonNull final String orderIdentification,
			@NonNull final SupportIDType supportId,
			@NonNull final String packingMaterialId,
			@NonNull final ImmutableList<OrderResponsePackageItem> items)
	{
		this.id = id;
		this.orderType = orderType;
		this.orderIdentification = orderIdentification;
		this.supportId = supportId;
		this.packingMaterialId = packingMaterialId;
		this.items = items;
	}

}
