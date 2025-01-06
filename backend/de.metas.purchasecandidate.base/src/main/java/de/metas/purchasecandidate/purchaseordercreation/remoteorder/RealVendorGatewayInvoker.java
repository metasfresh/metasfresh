package de.metas.purchasecandidate.purchaseordercreation.remoteorder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.bpartner.BPartnerId;
import de.metas.order.OrderAndLineId;
import de.metas.organization.OrgId;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseErrorItem;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseItem;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Loggables;
import de.metas.vendor.gateway.api.ProductAndQuantity;
import de.metas.vendor.gateway.api.VendorGatewayService;
import de.metas.vendor.gateway.api.order.LocalPurchaseOrderForRemoteOrderCreated;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequest;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequestItem;
import de.metas.vendor.gateway.api.order.RemotePurchaseOrderCreated;
import de.metas.vendor.gateway.api.order.RemotePurchaseOrderCreatedItem;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_UOM;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
	private final BPartnerId vendorId;
	private final VendorGatewayService vendorGatewayService;

	private Map<PurchaseOrderItem, RemotePurchaseOrderCreatedItem> map;

	@Builder
	private RealVendorGatewayInvoker(
			@NonNull final BPartnerId vendorId,
			@NonNull final VendorGatewayService vendorGatewayService)
	{
		this.vendorId = vendorId;
		this.vendorGatewayService = vendorGatewayService;
	}

	@Override
	public List<PurchaseItem> placeRemotePurchaseOrder(
			@NonNull final Collection<PurchaseCandidate> purchaseCandidates)
	{
		final ImmutableMap<PurchaseOrderRequestItem, PurchaseCandidate> requestItem2Candidate = //
				Maps.uniqueIndex(purchaseCandidates, RealVendorGatewayInvoker::createPurchaseOrderRequestItem);

		final PurchaseOrderRequest purchaseOrderRequest = PurchaseOrderRequest.builder()
				.orgId(getOrgIdFromPurchaseCandidates(purchaseCandidates).getRepoId())
				.vendorId(vendorId.getRepoId())
				.items(requestItem2Candidate.keySet())
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
		if (!purchaseOrderResponseItems.isEmpty())
		{
			final Map<Integer, I_C_UOM> uomsById = extractUOMsMap(purchaseCandidates);

			for (final RemotePurchaseOrderCreatedItem remotePurchaseOrderCreatedItem : purchaseOrderResponseItems)
			{
				final PurchaseOrderRequestItem correspondingRequestItem = remotePurchaseOrderCreatedItem.getCorrespondingRequestItem();

				final PurchaseCandidate correspondingRequestCandidate = requestItem2Candidate.get(correspondingRequestItem);

				final I_C_UOM uom = uomsById.get(remotePurchaseOrderCreatedItem.getUomId());

				ZonedDateTime confirmedDeliveryDate = remotePurchaseOrderCreatedItem.getConfirmedDeliveryDateOrNull();
				if (confirmedDeliveryDate == null)
				{
					Loggables
							.get()
							.addLog("The current remotePurchaseOrderCreatedItem has no confirmedDeliveryDate; "
											+ "falling back to the purchase candidate's purchaseDatePromised={}; remotePurchaseOrderCreatedItem={}",
									correspondingRequestCandidate.getPurchaseDatePromised(),
									remotePurchaseOrderCreatedItem);
					confirmedDeliveryDate = correspondingRequestCandidate.getPurchaseDatePromised();
				}

				final PurchaseOrderItem purchaseOrderItem = correspondingRequestCandidate.createOrderItem()
						.datePromised(confirmedDeliveryDate)
						.purchasedQty(Quantity.of(remotePurchaseOrderCreatedItem.getConfirmedOrderQuantity(), uom))
						.remotePurchaseOrderId(remotePurchaseOrderCreatedItem.getRemotePurchaseOrderId())
						.transactionReference(transactionReference)
						.dimension(correspondingRequestCandidate.getDimension())
						.buildAndAddToParent();
				result.add(purchaseOrderItem);

				mapBuilder.put(purchaseOrderItem, remotePurchaseOrderCreatedItem);
			}
		}

		map = mapBuilder.build();
		return result.build();
	}

	@NonNull
	private OrgId getOrgIdFromPurchaseCandidates(final Collection<PurchaseCandidate> purchaseCandidates)
	{
		final OrgId orgId = purchaseCandidates.stream()
				.map(PurchaseCandidate::getOrgId)
				.reduce((a, b) -> {
					throw new AdempiereException("Can only process purchase candidates from a single organization.");
				})
				.orElseThrow(() -> new AdempiereException("No purchase candidates available. Can't derive OrgId."));
		Check.errorIf(orgId.isAny(), "Cannot process purchase candidates with orgId = ANY");
		return orgId;
	}

	private static Map<Integer, I_C_UOM> extractUOMsMap(@NonNull final Collection<PurchaseCandidate> purchaseCandidates)
	{
		return purchaseCandidates.stream()
				.map(PurchaseCandidate::getQtyToPurchase)
				.map(Quantity::getUOM)
				.collect(GuavaCollectors.toImmutableMapByKeyKeepFirstDuplicate(I_C_UOM::getC_UOM_ID));
	}

	private static PurchaseOrderRequestItem createPurchaseOrderRequestItem(final PurchaseCandidate purchaseCandidate)
	{
		final Quantity qtyToPurchase = purchaseCandidate.getQtyToPurchase();
		final ProductAndQuantity productAndQuantity = ProductAndQuantity.of(purchaseCandidate.getVendorProductNo(), qtyToPurchase.toBigDecimal(), qtyToPurchase.getUOMId());
		return PurchaseOrderRequestItem.builder()
				.purchaseCandidateId(PurchaseCandidateId.getRepoIdOr(purchaseCandidate.getId(), -1))
				.productAndQuantity(productAndQuantity)
				.build();
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
