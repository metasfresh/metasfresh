/*
 * #%L
 * de-metas-camel-shopware6
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.shopware6.processor;

import de.metas.camel.externalsystems.common.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrder;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderAddress;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderDeliveries;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderDelivery;
import de.metas.common.bpartner.request.JsonRequestBPartnerUpsert;
import de.metas.common.externalreference.JsonExternalReferenceLookupResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_CURRENT_ORDER;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_ORDER_DELIVERIES;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_ORG_CODE;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_SHOPWARE_CLIENT;
import static de.metas.camel.externalsystems.shopware6.processor.ProcessorHelper.getPropertyOrThrowError;

public class CreateBPartnerUpsertReqProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange)
	{
		final JsonExternalReferenceLookupResponse esrResponse = exchange.getIn().getBody(JsonExternalReferenceLookupResponse.class);

		final String orgCode = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_ORG_CODE, String.class);

		final JsonOrder order = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_CURRENT_ORDER, JsonOrder.class);

		final ShopwareClient shopwareClient = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_SHOPWARE_CLIENT, ShopwareClient.class);

		final JsonOrderDeliveries orderDeliveries = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_ORDER_DELIVERIES, JsonOrderDeliveries.class);

		final List<JsonOrderAddress> shippingAddressList = orderDeliveries.getData().stream()
				.map(JsonOrderDelivery::getShippingOrderAddress)
				.collect(Collectors.toList());

		final BPartnerUpsertRequestProducer bPartnerUpsertRequestProducer = BPartnerUpsertRequestProducer.builder()
				.shopwareClient(shopwareClient)
				.externalReferenceLookupResponse(esrResponse)
				.billingAddressId(order.getBillingAddressId())
				.billingAddressVersion(order.getBillingAddressVersionId())
				.orderCustomer(order.getOrderCustomer())
				.shippingAddressList(shippingAddressList)
				.orgCode(orgCode)
				.build();

		final JsonRequestBPartnerUpsert upsertBPartner = bPartnerUpsertRequestProducer.run();

		final BPUpsertCamelRequest bpUpsertCamelRequest = BPUpsertCamelRequest.builder()
				.jsonRequestBPartnerUpsert(upsertBPartner)
				.orgCode(orgCode)
				.build();

		exchange.getIn().setBody(bpUpsertCamelRequest);
	}
}
