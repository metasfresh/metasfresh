package de.metas.vertical.pharma.vendor.gateway.msv3.purchaseOrder;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;

import de.metas.vendor.gateway.api.ProductAndQuantity;
import de.metas.vendor.gateway.api.order.MSV3OrderResponsePackageItemPartRepoId;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequest;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequestItem;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequestItemId;
import de.metas.vendor.gateway.api.order.RemotePurchaseOrderCreated;
import de.metas.vendor.gateway.api.order.RemotePurchaseOrderCreated.RemotePurchaseOrderCreatedBuilder;
import de.metas.vendor.gateway.api.order.RemotePurchaseOrderCreatedItem;
import de.metas.vendor.gateway.api.order.RemotePurchaseOrderCreatedItem.RemotePurchaseOrderCreatedItemBuilder;
import de.metas.vertical.pharma.msv3.protocol.order.DeliverySpecifications;
import de.metas.vertical.pharma.msv3.protocol.order.MSV3PurchaseCandidateId;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequest;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackage;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackageItem;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackageItemId;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponse;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackage;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackageItem;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackageItemPart;
import de.metas.vertical.pharma.msv3.protocol.order.OrderType;
import de.metas.vertical.pharma.msv3.protocol.order.SupportIDType;
import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.FaultInfo;
import de.metas.vertical.pharma.msv3.protocol.types.Id;
import de.metas.vertical.pharma.msv3.protocol.types.PZN;
import de.metas.vertical.pharma.msv3.protocol.types.Quantity;
import de.metas.vertical.pharma.vendor.gateway.msv3.common.Msv3ClientException;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Bestellung_Transaction;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.msv3
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

@UtilityClass
class MSV3PurchaseOrderClientUtils
{
	public static OrderCreateRequest createBestellungWithOneAuftrag(@NonNull final PurchaseOrderRequest request, @NonNull final SupportIDType supportId)
	{
		return OrderCreateRequest.builder()
				.orderId(Id.random())
				.supportId(supportId)
				.bpartnerId(BPartnerId.of(request.getVendorId()))
				.orderPackage(OrderCreateRequestPackage.builder()
						.id(Id.random())
						.orderType(OrderType.NORMAL)
						.orderIdentification(supportId.getValueAsString())
						.supportId(supportId)
						.items(request.getItems().stream()
								.map(MSV3PurchaseOrderClientUtils::createOrderCreateRequestPackageItem)
								.collect(ImmutableList.toImmutableList()))
						.build())
				.build();
	}

	public static void performOrdering(
			@NonNull final MSV3PurchaseOrderTransaction purchaseTransaction,
			final Function<OrderCreateRequest, OrderResponse> webService)
	{
		try
		{
			// invoke webservice
			final OrderResponse response = webService.apply(purchaseTransaction.getRequest());

			purchaseTransaction.setResponse(response);

			final FaultInfo faultInfo = response.getSingleOrderFaultInfo();
			if (faultInfo != null)
			{
				purchaseTransaction.setFaultInfo(faultInfo);
			}
		}
		catch (final Msv3ClientException msv3ClientException)
		{
			purchaseTransaction.setFaultInfo(msv3ClientException.getMsv3FaultInfo());
		}
		catch (final Exception e)
		{
			purchaseTransaction.setOtherException(e);
		}
	}

	private static OrderCreateRequestPackageItem createOrderCreateRequestPackageItem(@NonNull final PurchaseOrderRequestItem purchaseOrderRequestItem)
	{
		final ProductAndQuantity productAndQuantity = purchaseOrderRequestItem.getProductAndQuantity();
		return OrderCreateRequestPackageItem.builder()
				.id(toOrderCreateRequestPackageItemId(purchaseOrderRequestItem.getId()))
				.deliverySpecifications(DeliverySpecifications.NORMAL)
				.pzn(PZN.of(productAndQuantity.getProductIdentifier()))
				.qty(Quantity.of(productAndQuantity.getQuantity()))
				.purchaseCandidateId(MSV3PurchaseCandidateId.ofRepoIdOrNull(purchaseOrderRequestItem.getPurchaseCandidateId()))
				.build();
	}

	public static OrderCreateRequestPackageItemId toOrderCreateRequestPackageItemId(final PurchaseOrderRequestItemId purchaseOrderRequestItemId)
	{
		return OrderCreateRequestPackageItemId.of(purchaseOrderRequestItemId.getValueAsString());
	}

	public static RemotePurchaseOrderCreated createRemotePurchaseOrderCreated(
			final MSV3PurchaseOrderTransaction purchaseTransaction,
			final Map<OrderCreateRequestPackageItemId, PurchaseOrderRequestItem> purchaseOrderRequestItems)
	{
		final I_MSV3_Bestellung_Transaction purchaseTransactionRecord = purchaseTransaction.store();

		final RemotePurchaseOrderCreatedBuilder responseBuilder = RemotePurchaseOrderCreated.builder()
				.transactionRecordId(purchaseTransactionRecord.getMSV3_Bestellung_Transaction_ID())
				.transactionTableName(I_MSV3_Bestellung_Transaction.Table_Name)
				.exception(purchaseTransaction.getExceptionOrNull());

		final List<RemotePurchaseOrderCreatedItem> purchaseOrderResponseItems = createResponseItems(purchaseTransaction, purchaseOrderRequestItems);

		responseBuilder.purchaseOrderResponseItems(purchaseOrderResponseItems);

		return responseBuilder.build();
	}

	private static List<RemotePurchaseOrderCreatedItem> createResponseItems(
			@NonNull final MSV3PurchaseOrderTransaction purchaseTransaction,
			@NonNull final Map<OrderCreateRequestPackageItemId, PurchaseOrderRequestItem> purchaseOrderRequestItems)
	{
		if (purchaseTransaction.getResponse() == null)
		{
			return ImmutableList.of();
		}

		final OrderCreateRequest request = purchaseTransaction.getRequest();

		final ImmutableList.Builder<RemotePurchaseOrderCreatedItem> purchaseOrderResponseItems = ImmutableList.builder();

		final List<OrderResponsePackage> responseOrders = purchaseTransaction
				.getResponse()
				.getOrderPackages();
		for (int orderIdx = 0; orderIdx < responseOrders.size(); orderIdx++)
		{
			final OrderCreateRequestPackage requestOrder = request.getOrderPackages().get(orderIdx);
			final OrderResponsePackage responseOrder = responseOrders.get(orderIdx);

			final List<OrderCreateRequestPackageItem> requestItems = requestOrder.getItems();
			final List<OrderResponsePackageItem> responseItems = responseOrder.getItems();

			for (int itemIdx = 0; itemIdx < responseItems.size(); itemIdx++)
			{
				final OrderCreateRequestPackageItem requestItem = requestItems.get(itemIdx);
				final OrderResponsePackageItem responseItem = responseItems.get(itemIdx);

				final RemotePurchaseOrderCreatedItemBuilder builder = RemotePurchaseOrderCreatedItem.builder()
						.remotePurchaseOrderId(responseOrder.getId().getValueAsString())
						.correspondingRequestItem(purchaseOrderRequestItems.get(requestItem.getId()));

				for (final OrderResponsePackageItemPart responseItemPart : responseItem.getParts())
				{
					if (responseItemPart.getDeliveryDate() == null)
					{
						continue;
					}

					final MSV3OrderResponsePackageItemPartRepoId internalItemId = purchaseTransaction.getResponseItemPartRepoId(responseItemPart.getId());

					builder
							.confirmedDeliveryDate(responseItemPart.getDeliveryDate())
							.confirmedOrderQuantity(responseItemPart.getQty().getValueAsBigDecimal())
							.internalItemId(internalItemId);

					purchaseOrderResponseItems.add(builder.build());
				}
			}
		}

		return purchaseOrderResponseItems.build();
	}

}
