package de.metas.vertical.pharma.vendor.gateway.msv3.purchaseOrder;

import javax.xml.bind.JAXBElement;

import org.adempiere.service.OrgId;
import org.adempiere.util.Check;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.vendor.gateway.api.order.PurchaseOrderRequest;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequestItem;
import de.metas.vendor.gateway.api.order.RemotePurchaseOrderCreated;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequest;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackageItemId;
import de.metas.vertical.pharma.msv3.protocol.order.OrderJAXBConverters;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponse;
import de.metas.vertical.pharma.msv3.protocol.order.SupportIDType;
import de.metas.vertical.pharma.msv3.protocol.types.ClientSoftwareId;
import de.metas.vertical.pharma.vendor.gateway.msv3.MSV3Client;
import de.metas.vertical.pharma.vendor.gateway.msv3.MSV3ConnectionFactory;
import de.metas.vertical.pharma.vendor.gateway.msv3.config.MSV3ClientConfig;
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
	private final OrderJAXBConverters jaxbConverters;
	private final SupportIdProvider supportIdProvider;

	private OrderCreateRequest request;
	private ImmutableMap<OrderCreateRequestPackageItemId, PurchaseOrderRequestItem> purchaseOrderRequestItems;
	private OrgId orgId;

	@Builder
	private MSV3PurchaseOrderClientImpl(
			@NonNull final MSV3ConnectionFactory connectionFactory,
			@NonNull final MSV3ClientConfig config,
			@NonNull final SupportIdProvider supportIdProvider,
			@NonNull final OrderJAXBConverters jaxbConverters)
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
				item -> MSV3PurchaseOrderClientUtils.toOrderCreateRequestPackageItemId(item.getId()));

		final SupportIDType supportId = supportIdProvider.getNextSupportId();
		this.request = MSV3PurchaseOrderClientUtils.createBestellungWithOneAuftrag(request, supportId);

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
			MSV3PurchaseOrderClientUtils.performOrdering(purchaseTransaction, this::callWebService);

			return MSV3PurchaseOrderClientUtils.createRemotePurchaseOrderCreated(purchaseTransaction, purchaseOrderRequestItems);
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
		final JAXBElement<?> soapRequest = jaxbConverters.encodeRequest(request, client.getClientSoftwareId());
		final Object soapReponse = client.sendAndReceive(soapRequest, jaxbConverters.getResponseClass());
		final OrderResponse response = jaxbConverters.decodeResponse(soapReponse, request);
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
}
