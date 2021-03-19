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

import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrder;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderAddress;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderDeliveries;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderDelivery;
import de.metas.common.externalreference.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupRequest;
import de.metas.common.externalsystem.JsonExternalSystemName;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ESR_TYPE_BPARTNER;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ESR_TYPE_BPARTNER_LOCATION;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_CURRENT_ORDER;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_ORDER_DELIVERIES;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_SHOPWARE_CLIENT;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.SHOPWARE6_SYSTEM_NAME;
import static de.metas.camel.externalsystems.shopware6.processor.ProcessorHelper.getPropertyOrThrowError;

public class CreateBPartnerESRQueryProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange)
	{
		final JsonOrder order = exchange.getIn().getBody(JsonOrder.class);

		final ShopwareClient shopwareClient = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_SHOPWARE_CLIENT, ShopwareClient.class);

		final JsonOrderDeliveries orderDeliveries = shopwareClient.getDeliveries(order.getId())
				.orElseThrow(() -> new RuntimeException("Missing shipping address for orderId:" + order.getId()));

		final List<JsonOrderAddress> shippingAddressList = orderDeliveries.getData().stream()
				.map(JsonOrderDelivery::getShippingOrderAddress)
				.collect(Collectors.toList());

		final JsonExternalReferenceLookupRequest lookupRequest = buildESRLookupRequest(order, shippingAddressList);

		exchange.getIn().setBody(lookupRequest);

		exchange.setProperty(ROUTE_PROPERTY_CURRENT_ORDER, order);
		exchange.setProperty(ROUTE_PROPERTY_ORDER_DELIVERIES, orderDeliveries);
	}

	@NonNull
	private JsonExternalReferenceLookupRequest buildESRLookupRequest(
			@NonNull final JsonOrder order,
			@NonNull final List<JsonOrderAddress> shippingAddressList)
	{
		final Function<String,JsonExternalReferenceLookupItem> externalIdToBPLocationLookupFunc = (externalId) -> JsonExternalReferenceLookupItem.builder()
				.id(externalId)
				.type(ESR_TYPE_BPARTNER_LOCATION)
				.build();

		final JsonExternalReferenceLookupItem customerLookupItem = JsonExternalReferenceLookupItem.builder()
				.id(order.getOrderCustomer().getCustomerId())
				.type(ESR_TYPE_BPARTNER)
				.build();

		final JsonExternalReferenceLookupRequest.JsonExternalReferenceLookupRequestBuilder externalRefLookupReqBuilder = JsonExternalReferenceLookupRequest.builder()
				.systemName(JsonExternalSystemName.of(SHOPWARE6_SYSTEM_NAME))
				.item(customerLookupItem);

		final JsonExternalReferenceLookupItem billingAddressLookupItem = externalIdToBPLocationLookupFunc.apply(order.getBillingAddressId());
		externalRefLookupReqBuilder.item(billingAddressLookupItem);

		shippingAddressList.stream()
				.map(JsonOrderAddress::getId)
				.map(externalIdToBPLocationLookupFunc)
				.forEach(externalRefLookupReqBuilder::item);

		return externalRefLookupReqBuilder.build();
	}
}
