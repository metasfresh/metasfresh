package de.metas.purchasecandidate.purchaseordercreation.vendorgateway;

import java.util.Collection;
import java.util.List;

import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableList;

import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.vendor.gateway.api.VendorGatewayService;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class RealVendorGatewayInvoker implements VendorGatewayInvoker
{
	private final int vendorBPartnerId;

	private final VendorGatewayService vendorGatewayService;

	private ITableRecordReference tableRecordReference;

	@Builder
	private RealVendorGatewayInvoker(
			final int vendorBPartnerId,
			@NonNull final VendorGatewayService vendorGatewayService)
	{
		this.vendorBPartnerId = vendorBPartnerId;
		this.vendorGatewayService = vendorGatewayService;
	}

	@Override
	public List<PurchaseCandidate> placeRemotePurchaseOrder(Collection<PurchaseCandidate> purchaseCandidates)
	{
		final ImmutableList<PurchaseOrderRequestItem> requestItems = purchaseCandidates.stream()
				.map(PurchaseCandidate::createPurchaseOrderRequestItem)
				.collect(ImmutableList.toImmutableList());

		final PurchaseOrderRequest purchaseOrderRequest = new PurchaseOrderRequest(vendorBPartnerId, requestItems);

		final PurchaseOrderResponse purchaseOrderResponse = vendorGatewayService.placePurchaseOrder(purchaseOrderRequest);

		tableRecordReference = TableRecordReference.of(
				purchaseOrderResponse.getCreatedTableName(),
				purchaseOrderResponse.getCreatedRecordId());

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void associateRemotePurchaseOrderWithId(int purchaseOrderId)
	{
		vendorGatewayService.associateRemotePurchaseOrderWithId(purchaseOrderId);
	}
}
