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
import de.metas.camel.externalsystems.shopware6.api.model.customer.JsonCustomerGroup;
import de.metas.camel.externalsystems.shopware6.api.model.order.AddressDetail;
import de.metas.camel.externalsystems.shopware6.api.model.order.Customer;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderDelivery;
import de.metas.camel.externalsystems.shopware6.api.model.order.OrderCandidate;
import de.metas.camel.externalsystems.shopware6.api.model.order.OrderDeliveryItem;
import de.metas.camel.externalsystems.shopware6.order.ImportOrdersRouteContext;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

import static de.metas.camel.externalsystems.common.ProcessorHelper.getPropertyOrThrowError;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.BPARTNER_LOCATION_METASFRESH_ID_JSON_PATH;
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
		final String bPartnerLocationShopwareIdJSONPath = importOrdersRouteContext.getBpLocationCustomJsonPath();

		final List<OrderDeliveryItem> orderDeliveryItems = shopwareClient.getDeliveryAddresses(orderCandidate.getJsonOrder().getId(),
																							   bPartnerLocationShopwareIdJSONPath,
																							   BPARTNER_LOCATION_METASFRESH_ID_JSON_PATH,
																							   importOrdersRouteContext.getEmailJsonPath());

		if (CollectionUtils.isEmpty(orderDeliveryItems))
		{
			throw new RuntimeException("Missing shipping address! OrderId=" + orderCandidate.getJsonOrder().getId());
		}
		final OrderDeliveryItem lastOrderDeliveryItem = orderDeliveryItems.get(orderDeliveryItems.size() - 1);
		final JsonOrderDelivery jsonOrderDelivery = lastOrderDeliveryItem.getJsonOrderDelivery();
		final AddressDetail shippingOrderAddressDetails = lastOrderDeliveryItem.getOrderAddressDetails();

		importOrdersRouteContext.setMultipleShippingAddresses(orderDeliveryItems.size() > 1);
		importOrdersRouteContext.setDateRequired(jsonOrderDelivery.getShippingDateLatest().toLocalDate());
		importOrdersRouteContext.setShippingCost(jsonOrderDelivery.getShippingCost());
		importOrdersRouteContext.setShippingMethodId(jsonOrderDelivery.getShippingMethodId());
		importOrdersRouteContext.setOrderShippingAddress(shippingOrderAddressDetails.getJsonAddress());

		final Customer customerCandidate = orderCandidate.getCustomer();

		final JsonCustomerGroup jsonCustomerGroup = getCustomerGroup(shopwareClient, customerCandidate.getShopwareId());

		importOrdersRouteContext.setBPartnerCustomerGroup(jsonCustomerGroup);

		final AddressDetail billingAddressDetail = retrieveOrderBillingAddress(importOrdersRouteContext,
																			   shippingOrderAddressDetails,
																			   orderCandidate.getJsonOrder().getBillingAddressId());

		final BPartnerUpsertRequestProducer bPartnerUpsertRequestProducer = BPartnerUpsertRequestProducer.builder()
				.shopwareClient(shopwareClient)
				.billingAddress(billingAddressDetail)
				.customerCandidate(customerCandidate)
				.salutationInfoProvider(importOrdersRouteContext.getSalutationInfoProvider())
				.shippingAddress(shippingOrderAddressDetails)
				.orgCode(orgCode)
				.metasfreshId(importOrdersRouteContext.getBPExternalIdentifier())
				.userId(importOrdersRouteContext.getUserId())
				.matchingShopware6Mapping(importOrdersRouteContext.getMatchingShopware6Mapping())
				.priceListBasicInfo(importOrdersRouteContext.getPriceListBasicInfo())
				.jsonCustomerGroup(jsonCustomerGroup)
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

	@Nullable
	private JsonCustomerGroup getCustomerGroup(
			@NonNull final ShopwareClient shopwareClient,
			@NonNull final String customerShopwareId)
	{
		try
		{
			// we need the "internal" shopware-ID to navigate to the customer
			return shopwareClient.getCustomerGroup(customerShopwareId)
					.map(customerGroups -> Check.singleElement(customerGroups.getCustomerGroupList()))
					.orElse(null);
		}
		catch (final RuntimeException e)
		{
			throw new RuntimeCamelException("Exception getting CustomerGroup for customerId=" + customerShopwareId, e);
		}
	}

	@NonNull
	private AddressDetail retrieveOrderBillingAddress(
			@NonNull final ImportOrdersRouteContext context,
			@NonNull final AddressDetail orderShippingAddress,
			@NonNull final String orderBillingAddressId
	)
	{
		if (Objects.equals(orderShippingAddress.getJsonAddress().getId(), orderBillingAddressId))
		{
			return orderShippingAddress;
		}

		return context.getShopwareClient()
				.getOrderAddressDetails(orderBillingAddressId,
										context.getBpLocationCustomJsonPath(),
										BPARTNER_LOCATION_METASFRESH_ID_JSON_PATH,
										context.getEmailJsonPath())
				.orElseThrow(() -> new RuntimeException("Missing address details for addressId: " + orderBillingAddressId));
	}
}
