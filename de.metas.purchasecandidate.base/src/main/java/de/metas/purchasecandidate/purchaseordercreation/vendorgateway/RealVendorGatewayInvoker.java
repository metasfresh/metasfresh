package de.metas.purchasecandidate.purchaseordercreation.vendorgateway;

import java.util.IdentityHashMap;
import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.vendor.gateway.api.VendorGatewayService;
import de.metas.vendor.gateway.api.availability.AvailabilityRequestItem;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequest;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequestItem;
import de.metas.vendor.gateway.api.order.PurchaseOrderResponse;
import lombok.Builder;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


public class RealVendorGatewayInvoker implements VendorGatewayInvoker
{
	private final IdentityHashMap<PurchaseCandidate, PurchaseOrderRequestItem> purchaseCandidate2OrderLine;

	private final int vendorBPartnerId;

	private final VendorGatewayService vendorGatewayService;

	@Builder
	private RealVendorGatewayInvoker(
			final int vendorBPartnerId,
			@NonNull final VendorGatewayService vendorGatewayService)
	{
		this.vendorBPartnerId = vendorBPartnerId;
		this.vendorGatewayService = vendorGatewayService;
		this.purchaseCandidate2OrderLine = new IdentityHashMap<>();
	}

	@Override
	public void addCandidate(final PurchaseCandidate candidate)
	{
		final AvailabilityRequestItem availabilityRequestItem = candidate.createRequestItem();
		final PurchaseOrderRequestItem purchaseOrderRequestItem = new PurchaseOrderRequestItem(
				availabilityRequestItem.getProductAndQuantity());

		purchaseCandidate2OrderLine.put(candidate, purchaseOrderRequestItem);
	}

	@Override
	public List<PurchaseCandidate> createAndComplete(final int purchaseOrderId)
	{
		final ImmutableList<PurchaseOrderRequestItem> values = ImmutableList.copyOf(purchaseCandidate2OrderLine.values());

		final PurchaseOrderRequest purchaseOrderRequest = new PurchaseOrderRequest(purchaseOrderId, vendorBPartnerId, values);

		final PurchaseOrderResponse purchaseOrderResponse = vendorGatewayService.placePurchaseOrder(purchaseOrderRequest);
		return VendorGatewayStatus.SERVICE_ORDER_CREATED;
	}
}