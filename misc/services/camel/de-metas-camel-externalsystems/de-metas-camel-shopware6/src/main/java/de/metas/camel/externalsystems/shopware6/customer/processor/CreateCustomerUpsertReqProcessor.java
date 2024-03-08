/*
 * #%L
 * de-metas-camel-shopware6
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.shopware6.customer.processor;

import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.customer.JsonCustomerGroup;
import de.metas.camel.externalsystems.shopware6.api.model.order.AddressDetail;
import de.metas.camel.externalsystems.shopware6.api.model.order.Customer;
import de.metas.camel.externalsystems.shopware6.customer.ImportCustomersRouteContext;
import de.metas.camel.externalsystems.shopware6.order.processor.BPartnerRequestProducerResult;
import de.metas.camel.externalsystems.shopware6.order.processor.BPartnerUpsertRequestProducer;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

import static de.metas.camel.externalsystems.common.ProcessorHelper.getPropertyOrThrowError;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_IMPORT_CUSTOMERS_CONTEXT;

public class CreateCustomerUpsertReqProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final Customer customerCandidate = exchange.getIn().getBody(Customer.class);

		final ImportCustomersRouteContext routeContext = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_CUSTOMERS_CONTEXT, ImportCustomersRouteContext.class);
		routeContext.setCustomer(customerCandidate);

		final String shippingAddressId = Optional.ofNullable(customerCandidate.getDefaultShippingAddressId())
				.orElseThrow(() -> new RuntimeException("Missing default shipping address Id for customerId: " + customerCandidate.getShopwareId()));

		final String billingAddressId = Optional.ofNullable(customerCandidate.getDefaultBillingAddressId())
				.orElseThrow(() -> new RuntimeException("Missing default billing address Id for customerId: " + customerCandidate.getShopwareId()));

		final ShopwareClient shopwareClient = routeContext.getShopwareClient();

		final AddressDetail shippingAddressDetail = retrieveAddressDetails(shopwareClient, shippingAddressId);

		final AddressDetail billingAddressDetail = Objects.equals(shippingAddressId, billingAddressId)
				? shippingAddressDetail
				: retrieveAddressDetails(shopwareClient, billingAddressId);

		final JsonCustomerGroup jsonCustomerGroup = getJsonCustomerGroup(shopwareClient, customerCandidate.getGroupId());

		final BPartnerUpsertRequestProducer bPartnerUpsertRequestProducer = BPartnerUpsertRequestProducer.builder()
				.orgCode(routeContext.getOrgCode())
				.shopwareClient(shopwareClient)
				.shippingAddress(shippingAddressDetail)
				.billingAddress(billingAddressDetail)
				.customerCandidate(customerCandidate)
				.metasfreshId(customerCandidate.getExternalIdentifier(routeContext.getMetasfreshIdJsonPath(), routeContext.getShopwareIdJsonPath()))
				.userId(customerCandidate.getShopwareId(routeContext.getShopwareIdJsonPath()))
				.priceListBasicInfo(routeContext.getPriceListBasicInfo())
				.matchingShopware6Mapping(routeContext.getMatchingShopware6Mapping(jsonCustomerGroup))
				.jsonCustomerGroup(jsonCustomerGroup)
				.isDefaultAddress(true)
				.build();

		final BPartnerRequestProducerResult bPartnerRequestProducerResult = bPartnerUpsertRequestProducer.run();

		final BPUpsertCamelRequest bpUpsertCamelRequest = BPUpsertCamelRequest.builder()
				.jsonRequestBPartnerUpsert(bPartnerRequestProducerResult.getJsonRequestBPartnerUpsert())
				.orgCode(routeContext.getOrgCode())
				.build();

		exchange.getIn().setBody(bpUpsertCamelRequest);
	}

	@Nullable
	private JsonCustomerGroup getJsonCustomerGroup(@NonNull final ShopwareClient shopwareClient, @Nullable final String customerGroupId)
	{
		if (Check.isBlank(customerGroupId))
		{
			return null;
		}

		return shopwareClient.getGroupInformation(customerGroupId)
				.orElse(null);
	}

	@NonNull
	private AddressDetail retrieveAddressDetails(@NonNull final ShopwareClient shopwareClient, @NonNull final String addressDetailId)
	{
		return shopwareClient.getCustomerAddressDetail(addressDetailId)
				.orElseThrow(() -> new RuntimeException("Missing address details for addressId: " + addressDetailId));
	}
}
