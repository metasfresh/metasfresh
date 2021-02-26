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
import de.metas.common.externalreference.JsonExternalReferenceLookupResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.logging.Level;
import java.util.logging.Logger;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_CURRENT_ORDER;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_SHOPWARE_CLIENT;

public class CreateBPartnerUpsertReqProcessor implements Processor
{
	private static final Logger logger = Logger.getLogger(CreateBPartnerUpsertReqProcessor.class.getName());

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		//we will start using it in the 2nd step of the implementation
		final JsonExternalReferenceLookupResponse esrResponse = exchange.getIn().getBody(JsonExternalReferenceLookupResponse.class);

		final JsonOrder order = exchange.getProperty(ROUTE_PROPERTY_CURRENT_ORDER, JsonOrder.class);
		if (order == null)
		{
			throw new RuntimeException("Missing route property: " + ROUTE_PROPERTY_CURRENT_ORDER + " !");
		}

		final ShopwareClient shopwareClient = exchange.getProperty(ROUTE_PROPERTY_SHOPWARE_CLIENT, ShopwareClient.class);
		if (shopwareClient == null)
		{
			throw new RuntimeException("Missing route property: " + ROUTE_PROPERTY_SHOPWARE_CLIENT + " !");
		}

		//1. retrieve billing address
		final JsonOrderAddress billingAddress = shopwareClient.getOrderAddressDetails(order.getBillingAddressId()).orElse(null);

		//2 retreive deliveries
		final JsonOrderDeliveries orderDeliveries = shopwareClient.getDeliveries(order.getId()).orElse(null);

		logger.log(Level.INFO, "Retrieved the following data form shopware: jsonOrder: " + order
				+ "\n billingAddress: " + billingAddress + "\n deliveries: " + orderDeliveries);
	}
}
