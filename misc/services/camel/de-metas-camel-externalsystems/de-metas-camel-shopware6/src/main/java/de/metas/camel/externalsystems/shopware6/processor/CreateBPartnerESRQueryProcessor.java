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

import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrder;
import de.metas.common.externalreference.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupRequest;
import de.metas.common.externalsystem.JsonExternalSystemName;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ESR_TYPE_BPARTNER;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ESR_TYPE_BPARTNER_LOCATION;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_CURRENT_ORDER;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.SHOPWARE6_SYSTEM_NAME;

public class CreateBPartnerESRQueryProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final JsonOrder order = exchange.getIn().getBody(JsonOrder.class);

		final JsonExternalReferenceLookupRequest lookupRequest = buildESRLookupRequest(order);

		exchange.getIn().setBody(lookupRequest);

		exchange.setProperty(ROUTE_PROPERTY_CURRENT_ORDER, order);
	}

	@NonNull
	private JsonExternalReferenceLookupRequest buildESRLookupRequest(@NonNull final JsonOrder order)
	{
		final String customerId = order.getOrderCustomer().getCustomerId();

		final JsonExternalReferenceLookupItem customerLookupItem = JsonExternalReferenceLookupItem.builder()
				.id(customerId)
				.type(ESR_TYPE_BPARTNER)
				.build();

		final JsonExternalReferenceLookupItem billingAddressLookupItem = JsonExternalReferenceLookupItem.builder()
				.id(order.getBillingAddressId())
				.type(ESR_TYPE_BPARTNER_LOCATION)
				.build();

		return JsonExternalReferenceLookupRequest.builder()
				.systemName(JsonExternalSystemName.of(SHOPWARE6_SYSTEM_NAME))
				.item(customerLookupItem)
				.item(billingAddressLookupItem)
				.build();
	}
}
