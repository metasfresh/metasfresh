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

package de.metas.camel.externalsystems.shopware6.order.processor;

import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.order.OrderCandidate;
import de.metas.camel.externalsystems.shopware6.api.model.order.OrderDeliveryItem;
import de.metas.camel.externalsystems.shopware6.order.ImportOrdersRouteContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static de.metas.camel.externalsystems.shopware6.ProcessorHelper.getPropertyOrThrowError;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT;

public class CreateBPartnerUpsertReqProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange)
	{
		final ImportOrdersRouteContext importOrdersRouteContext = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT, ImportOrdersRouteContext.class);

		final OrderCandidate orderCandidate = exchange.getIn().getBody(OrderCandidate.class);

		final String orgCode = importOrdersRouteContext.getOrgCode();
		final ShopwareClient shopwareClient = importOrdersRouteContext.getShopwareClient();
		final String bPartnerLocationIdJSONPath = importOrdersRouteContext.getBpLocationCustomJsonPath();

		final List<OrderDeliveryItem> orderDeliveryItems = shopwareClient.getDeliveryAddresses(orderCandidate.getJsonOrder().getId(), bPartnerLocationIdJSONPath);

		if (CollectionUtils.isEmpty(orderDeliveryItems))
		{
			throw new RuntimeException("Missing shipping address! OrderId=" + orderCandidate.getJsonOrder().getId());
		}
		final OrderDeliveryItem lastOrderDeliveryItem = orderDeliveryItems.get(orderDeliveryItems.size() - 1);

		importOrdersRouteContext.setMultipleShippingAddresses(orderDeliveryItems.size() > 1);
		importOrdersRouteContext.setDateRequired(lastOrderDeliveryItem.getJsonOrderDelivery().getShippingDateLatest().toLocalDate());

		final BPartnerUpsertRequestProducer bPartnerUpsertRequestProducer = BPartnerUpsertRequestProducer.builder()
				.shopwareClient(shopwareClient)
				.billingAddressId(orderCandidate.getJsonOrder().getBillingAddressId())
				.orderCustomer(orderCandidate.getJsonOrder().getOrderCustomer())
				.shippingAddress(lastOrderDeliveryItem.getJsonOrderAddressAndCustomId())
				.orgCode(orgCode)
				.externalBPartnerId(orderCandidate.getEffectiveCustomerId())
				.bPartnerLocationIdentifierCustomPath(bPartnerLocationIdJSONPath)
				.build();

		final BPartnerRequestProducerResult bPartnerRequestProducerResult = bPartnerUpsertRequestProducer.run();
		importOrdersRouteContext.setBillingBPLocationExternalId(bPartnerRequestProducerResult.getBillingBPartnerLocationExternalId());
		importOrdersRouteContext.setShippingBPLocationExternalId(bPartnerRequestProducerResult.getShippingBPartnerLocationExternalId());

		final BPUpsertCamelRequest bpUpsertCamelRequest = BPUpsertCamelRequest.builder()
				.jsonRequestBPartnerUpsert(bPartnerRequestProducerResult.getJsonRequestBPartnerUpsert())
				.orgCode(orgCode)
				.build();

		exchange.getIn().setBody(bpUpsertCamelRequest);
	}
}
