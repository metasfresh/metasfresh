package de.metas.purchasecandidate.purchaseordercreation.remoteorder;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.service.OrgId;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.order.OrderAndLineId;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseErrorItem;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseItem;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem;
import de.metas.vendor.gateway.api.VendorGatewayService;
import de.metas.vendor.gateway.api.order.LocalPurchaseOrderForRemoteOrderCreated;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequest;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequestItem;
import de.metas.vendor.gateway.api.order.RemotePurchaseOrderCreated;
import de.metas.vendor.gateway.api.order.RemotePurchaseOrderCreatedItem;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

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

@ToString
public class RealVendorGatewayInvoker implements VendorGatewayInvoker
{
	private final OrgId orgId;
	private final BPartnerId vendorId;
	private final VendorGatewayService vendorGatewayService;

	private Map<PurchaseOrderItem, RemotePurchaseOrderCreatedItem> map;

	@Builder
	private RealVendorGatewayInvoker(
			@NonNull final OrgId orgId,
			@NonNull final BPartnerId vendorId,
			@NonNull final VendorGatewayService vendorGatewayService)
	{
		this.orgId = orgId;
		this.vendorId = vendorId;
		this.vendorGatewayService = vendorGatewayService;
	}

	@Override
	public List<PurchaseItem> placeRemotePurchaseOrder(
			@NonNull final Collection<PurchaseCandidate> purchaseCandidates)
	{
		final ImmutableMap<PurchaseOrderRequestItem, PurchaseCandidate> requestItem2Candidate = //
				Maps.uniqueIndex(purchaseCandidates, PurchaseCandidate::createPurchaseOrderRequestItem);

		final PurchaseOrderRequest purchaseOrderRequest = PurchaseOrderRequest.builder()
				.orgId(orgId.getRepoId())
				.vendorId(vendorId.getRepoId())
				.purchaseOrderRequestItems(requestItem2Candidate.keySet())
				.build();

		final RemotePurchaseOrderCreated purchaseOrderResponse = vendorGatewayService.placePurchaseOrder(purchaseOrderRequest);

		final ITableRecordReference transactionReference = TableRecordReference.of(
				purchaseOrderResponse.getTransactionTableName(),
				purchaseOrderResponse.getTransactionRecordId());

		final ImmutableList.Builder<PurchaseItem> result = ImmutableList.builder();
		if (purchaseOrderResponse.getException() != null)
		{
			for (final PurchaseCandidate purchaseCandidate : purchaseCandidates)
			{
				final PurchaseErrorItem purchaseErrorItem = purchaseCandidate.createErrorItem()
						.transactionReference(transactionReference)
						.throwable(purchaseOrderResponse.getException())
						.buildAndAdd();

				result.add(purchaseErrorItem);
			}
		}

		final ImmutableMap.Builder<PurchaseOrderItem, RemotePurchaseOrderCreatedItem> mapBuilder = ImmutableMap.builder();

		final List<RemotePurchaseOrderCreatedItem> purchaseOrderResponseItems = purchaseOrderResponse.getPurchaseOrderResponseItems();
		for (final RemotePurchaseOrderCreatedItem remotePurchaseOrderCreatedItem : purchaseOrderResponseItems)
		{
			final PurchaseOrderRequestItem correspondingRequestItem = remotePurchaseOrderCreatedItem.getCorrespondingRequestItem();

			final PurchaseCandidate correspondingRequestCandidate = requestItem2Candidate.get(correspondingRequestItem);

			final PurchaseOrderItem purchaseOrderItem = correspondingRequestCandidate.createOrderItem()
					.datePromised(TimeUtil.asLocalDateTime(remotePurchaseOrderCreatedItem.getConfirmedDeliveryDate()))
					.purchasedQty(remotePurchaseOrderCreatedItem.getConfirmedOrderQuantity())
					.remotePurchaseOrderId(remotePurchaseOrderCreatedItem.getRemotePurchaseOrderId())
					.transactionReference(transactionReference)
					.buildAndAddToParent();
			result.add(purchaseOrderItem);

			mapBuilder.put(purchaseOrderItem, remotePurchaseOrderCreatedItem);
		}

		map = mapBuilder.build();
		return result.build();
	}

	@Override
	public void updateRemoteLineReferences(@NonNull final Collection<PurchaseOrderItem> purchaseOrderItems)
	{
		purchaseOrderItems.forEach(this::updateRemoteLineReference);
	}

	private void updateRemoteLineReference(@NonNull final PurchaseOrderItem purchaseOrderItem)
	{
		final RemotePurchaseOrderCreatedItem remotePurchaseOrderCreatedItem = map.get(purchaseOrderItem);

		final OrderAndLineId purchaseOrderAndLineId = purchaseOrderItem.getPurchaseOrderAndLineId();

		final LocalPurchaseOrderForRemoteOrderCreated localPurchaseOrderForRemoteOrderCreated = //
				LocalPurchaseOrderForRemoteOrderCreated.builder()
						.purchaseOrderId(OrderAndLineId.getOrderRepoIdOr(purchaseOrderAndLineId, -1))
						.purchaseOrderLineId(OrderAndLineId.getOrderLineRepoIdOr(purchaseOrderAndLineId, -1))
						.remotePurchaseOrderCreatedItem(remotePurchaseOrderCreatedItem)
						.build();

		vendorGatewayService.associateLocalWithRemotePurchaseOrderId(localPurchaseOrderForRemoteOrderCreated);
	}
}
