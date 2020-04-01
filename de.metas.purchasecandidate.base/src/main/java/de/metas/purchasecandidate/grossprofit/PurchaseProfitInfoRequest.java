package de.metas.purchasecandidate.grossprofit;

import java.util.Set;

import java.util.Objects;
import com.google.common.collect.ImmutableSet;

import de.metas.order.OrderAndLineId;
import de.metas.purchasecandidate.VendorProductInfo;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.purchasecandidate.base
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
public class PurchaseProfitInfoRequest
{
	Set<OrderAndLineId> salesOrderAndLineIds;

	Quantity qtyToPurchase;

	VendorProductInfo vendorProductInfo;

	@Builder
	private PurchaseProfitInfoRequest(
			@Singular final Set<OrderAndLineId> salesOrderAndLineIds,
			@NonNull final Quantity qtyToPurchase,
			@NonNull final VendorProductInfo vendorProductInfo)
	{
		this.salesOrderAndLineIds = salesOrderAndLineIds.stream().filter(Objects::nonNull).collect(ImmutableSet.toImmutableSet());
		this.qtyToPurchase = qtyToPurchase;
		this.vendorProductInfo = vendorProductInfo;
	}
}
