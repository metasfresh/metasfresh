package de.metas.purchasecandidate.purchaseordercreation;

import java.util.IdentityHashMap;
import java.util.Optional;

import org.compiere.Adempiere;

import com.google.common.collect.ImmutableList;

import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.vendor.gateway.api.ProductAndQuantity;
import de.metas.vendor.gateway.api.VendorGatewayRegistry;
import de.metas.vendor.gateway.api.VendorGatewayService;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequest;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequestItem;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.purchasecandidate.base
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

/* package */ interface VendorRequestFromCandidatesFactory
{
	void addCandidate(PurchaseCandidate candidate);

	/**
	 * The {@code C_Order_ID} of the purchase order we just created.
	 *
	 * @param purchaseOrderId
	 * @return
	 */
	VendorGatewayStatus createAndComplete(int purchaseOrderId);

	enum VendorGatewayStatus
	{
		NO_GATEWAY_SERVICE,

		SERVICE_ORDER_CREATED,

		SERVICE_ORDER_NEEDS_ATENTION,
	}

	static VendorRequestFromCandidatesFactory createForVendorId(final int vendorBPartnerId)
	{
		final VendorGatewayRegistry vendorGatewayRegistry = Adempiere.getBean(VendorGatewayRegistry.class);
		final Optional<VendorGatewayService> vendorGatewayService = vendorGatewayRegistry
				.getSingleVendorGatewayService(vendorBPartnerId);

		if (vendorGatewayService.isPresent())
		{
			return RealVendorRequestFromCandidatesFactory.builder()
					.vendorBPartnerId(vendorBPartnerId)
					.vendorGatewayService(vendorGatewayService.get())
					.build();
		}
		return new NullFactory();
	}

	static class NullFactory implements VendorRequestFromCandidatesFactory
	{
		@Override
		public void addCandidate(PurchaseCandidate candidate)
		{
		}

		@Override
		public VendorGatewayStatus createAndComplete(int purchaseOrderId)
		{
			return VendorGatewayStatus.NO_GATEWAY_SERVICE;
		}
	}

	static class RealVendorRequestFromCandidatesFactory implements VendorRequestFromCandidatesFactory
	{
		private final IdentityHashMap<PurchaseCandidate, PurchaseOrderRequestItem> purchaseCandidate2OrderLine;

		private final int vendorBPartnerId;

		private final VendorGatewayService vendorGatewayService;

		@Builder
		private RealVendorRequestFromCandidatesFactory(
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
			final ProductAndQuantity productAndQty = candidate.createRequestItem();
			final PurchaseOrderRequestItem purchaseOrderRequestItem = new PurchaseOrderRequestItem(productAndQty, -1);

			purchaseCandidate2OrderLine.put(candidate, purchaseOrderRequestItem);
		}

		@Override
		public VendorGatewayStatus createAndComplete(final int purchaseOrderId)
		{
			final ImmutableList<PurchaseOrderRequestItem> values = ImmutableList.copyOf(purchaseCandidate2OrderLine.values());

			final PurchaseOrderRequest purchaseOrderRequest = new PurchaseOrderRequest(purchaseOrderId, vendorBPartnerId, values);

			vendorGatewayService.placePurchaseOrder(purchaseOrderRequest);
			return VendorGatewayStatus.SERVICE_ORDER_CREATED;
		}
	}
}
