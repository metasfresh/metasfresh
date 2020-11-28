package de.metas.vertical.pharma.vendor.gateway.msv3.purchaseOrder;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.xml.bind.JAXBElement;

import org.springframework.ws.client.core.WebServiceTemplate;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.organization.OrgId;
import de.metas.util.Check;
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
import de.metas.vertical.pharma.msv3.protocol.order.OrderClientJAXBConverters;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequest;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackage;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackageItem;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackageItemId;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponse;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackage;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackageItem;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackageItemPart;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackageItemPart.Type;
import de.metas.vertical.pharma.msv3.protocol.order.OrderType;
import de.metas.vertical.pharma.msv3.protocol.order.SupportIDType;
import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.ClientSoftwareId;
import de.metas.vertical.pharma.msv3.protocol.types.FaultInfo;
import de.metas.vertical.pharma.msv3.protocol.types.Id;
import de.metas.vertical.pharma.msv3.protocol.types.PZN;
import de.metas.vertical.pharma.msv3.protocol.types.Quantity;
import de.metas.vertical.pharma.vendor.gateway.msv3.MSV3Client;
import de.metas.vertical.pharma.vendor.gateway.msv3.MSV3ConnectionFactory;
import de.metas.vertical.pharma.vendor.gateway.msv3.common.Msv3ClientException;
import de.metas.vertical.pharma.vendor.gateway.msv3.config.MSV3ClientConfig;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Bestellung_Transaction;
import lombok.Builder;
import lombok.NonNull;

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

public class MSV3PurchaseOrderClientImpl implements MSV3PurchaseOrderClient
{
	private static final String URL_SUFFIX_PLACE_PURCHASE_ORDER = "/bestellen";

	private final MSV3Client client;
	private final OrderClientJAXBConverters jaxbConverters;
	private final SupportIdProvider supportIdProvider;

	private OrderCreateRequest request;
	private ImmutableMap<OrderCreateRequestPackageItemId, PurchaseOrderRequestItem> purchaseOrderRequestItems;
	private OrgId orgId;

	@Builder
	private MSV3PurchaseOrderClientImpl(
			@NonNull final MSV3ConnectionFactory connectionFactory,
			@NonNull final MSV3ClientConfig config,
			@NonNull final SupportIdProvider supportIdProvider,
			@NonNull final OrderClientJAXBConverters jaxbConverters)
	{
		client = MSV3Client.builder()
				.connectionFactory(connectionFactory)
				.config(config)
				.urlPrefix(URL_SUFFIX_PLACE_PURCHASE_ORDER)
				.faultInfoExtractor(jaxbConverters::extractFaultInfoOrNull)
				.build();
		this.supportIdProvider = supportIdProvider;
		this.jaxbConverters = jaxbConverters;
	}

	@Override
	public MSV3PurchaseOrderClient prepare(final PurchaseOrderRequest request)
	{
		Check.errorIf(this.request != null,
				"The local field request is already set. Please call resetForReuse() or placeOrder() instead; this={}", this);

		orgId = OrgId.ofRepoId(request.getOrgId());

		purchaseOrderRequestItems = Maps.uniqueIndex(
				request.getItems(),
				item -> toOrderCreateRequestPackageItemId(item.getId()));

		final SupportIDType supportId = supportIdProvider.getNextSupportId();
		this.request = createBestellungWithOneAuftrag(request, supportId);

		return this;
	}

	@Override
	public RemotePurchaseOrderCreated placeOrder()
	{
		try
		{
			assertPrepared();

			final MSV3PurchaseOrderTransaction purchaseTransaction = MSV3PurchaseOrderTransaction.builder()
					.orgId(orgId)
					.request(request)
					.build();

			// invoke the deeper layer
			performOrdering(purchaseTransaction, this::callWebService);

			return createRemotePurchaseOrderCreated(purchaseTransaction, purchaseOrderRequestItems);
		}
		finally
		{
			resetForReuse();
		}
	}

	@Override
	public MSV3PurchaseOrderClient resetForReuse()
	{
		request = null;
		purchaseOrderRequestItems = null;
		orgId = null;

		return this;
	}

	private void assertPrepared()
	{
		Check.errorIf(request == null,
				"Local field purchaseOrderRequestPayload is still null. Please run prepare() first; this={}", this);
	}

	private OrderResponse callWebService(final OrderCreateRequest request)
	{
		final JAXBElement<?> soapRequest = jaxbConverters.encodeRequestToServer(request, client.getClientSoftwareId());
		final Object soapReponse = client.sendAndReceive(soapRequest, jaxbConverters.getSoapResponseClassFromServer());
		final OrderResponse response = jaxbConverters.decodeResponseFromServer(soapReponse, request);
		return response;
	}

	@VisibleForTesting
	WebServiceTemplate getWebServiceTemplate()
	{
		return client.getWebServiceTemplate();
	}

	@VisibleForTesting
	ClientSoftwareId getClientSoftwareId()
	{
		return client.getClientSoftwareId();
	}

	@VisibleForTesting
	OrderCreateRequest getRequest()
	{
		return request;
	}

	private static OrderCreateRequest createBestellungWithOneAuftrag(@NonNull final PurchaseOrderRequest request, @NonNull final SupportIDType supportId)
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
								.map(MSV3PurchaseOrderClientImpl::createOrderCreateRequestPackageItem)
								.collect(ImmutableList.toImmutableList()))
						.build())
				.build();
	}

	private static void performOrdering(
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

	private static OrderCreateRequestPackageItemId toOrderCreateRequestPackageItemId(final PurchaseOrderRequestItemId purchaseOrderRequestItemId)
	{
		return OrderCreateRequestPackageItemId.of(purchaseOrderRequestItemId.getValueAsString());
	}

	private static RemotePurchaseOrderCreated createRemotePurchaseOrderCreated(
			@NonNull final MSV3PurchaseOrderTransaction purchaseTransaction,
			@NonNull final Map<OrderCreateRequestPackageItemId, PurchaseOrderRequestItem> purchaseOrderRequestItems)
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
					final Type type = responseItemPart.getType();
					if (type.isNoDeliveryPossible())
					{
						continue;
					}

					final MSV3OrderResponsePackageItemPartRepoId internalItemId = purchaseTransaction.getResponseItemPartRepoId(responseItemPart.getId());
					builder
							.confirmedDeliveryDateOrNull(responseItemPart.getDeliveryDate())
							.confirmedOrderQuantity(responseItemPart.getQty().getValueAsBigDecimal())
							.internalItemId(internalItemId);

					purchaseOrderResponseItems.add(builder.build());
				}
			}
		}

		return purchaseOrderResponseItems.build();
	}
}
