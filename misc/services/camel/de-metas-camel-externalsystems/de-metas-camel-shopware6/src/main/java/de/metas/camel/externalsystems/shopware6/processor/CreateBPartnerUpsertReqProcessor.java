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

import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderAddressAndCustomId;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderAndCustomId;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_ORG_CODE;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_PATH_CONSTANT_BPARTNER_LOCATION_ID;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_SHOPWARE_CLIENT;
import static de.metas.camel.externalsystems.shopware6.processor.ProcessorHelper.getPropertyOrThrowError;

public class CreateBPartnerUpsertReqProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange)
	{
		final JsonOrderAndCustomId orderAndCustomId = exchange.getIn().getBody(JsonOrderAndCustomId.class);

		final String orgCode = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_ORG_CODE, String.class);

		final String bPartnerLocationIdJSONPath = exchange.getProperty(ROUTE_PROPERTY_PATH_CONSTANT_BPARTNER_LOCATION_ID, String.class);

		final ShopwareClient shopwareClient = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_SHOPWARE_CLIENT, ShopwareClient.class);

		final List<JsonOrderAddressAndCustomId> shippingAddressList = shopwareClient.getDeliveryAddresses(orderAndCustomId.getJsonOrder().getId(), bPartnerLocationIdJSONPath);

		final BPartnerUpsertRequestProducer bPartnerUpsertRequestProducer = BPartnerUpsertRequestProducer.builder()
				.shopwareClient(shopwareClient)
				.billingAddressId(orderAndCustomId.getJsonOrder().getBillingAddressId())
				.orderCustomer(orderAndCustomId.getJsonOrder().getOrderCustomer())
				.shippingAddressList(shippingAddressList)
				.orgCode(orgCode)
				.bPartnerCustomIdentifier(orderAndCustomId.getCustomId())
				.bPartnerLocationIdentifierCustomPath(bPartnerLocationIdJSONPath)
				.build();

		final JsonRequestBPartnerUpsert upsertBPartner = bPartnerUpsertRequestProducer.run();

		final BPUpsertCamelRequest bpUpsertCamelRequest = BPUpsertCamelRequest.builder()
				.jsonRequestBPartnerUpsert(upsertBPartner)
				.orgCode(orgCode)
				.build();

		exchange.getIn().setBody(bpUpsertCamelRequest);
	}
}
