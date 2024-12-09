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
<<<<<<< HEAD
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderCustomer;
import de.metas.camel.externalsystems.shopware6.api.model.order.OrderCandidate;
import de.metas.camel.externalsystems.shopware6.api.model.order.OrderDeliveryItem;
import de.metas.camel.externalsystems.shopware6.order.ImportOrdersRouteContext;
import de.metas.common.externalsystem.JsonExternalSystemShopware6ConfigMapping;
import de.metas.common.externalsystem.JsonExternalSystemShopware6ConfigMappings;
=======
import de.metas.camel.externalsystems.shopware6.api.model.order.AddressDetail;
import de.metas.camel.externalsystems.shopware6.api.model.order.Customer;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderDelivery;
import de.metas.camel.externalsystems.shopware6.api.model.order.OrderCandidate;
import de.metas.camel.externalsystems.shopware6.api.model.order.OrderDeliveryItem;
import de.metas.camel.externalsystems.shopware6.order.ImportOrdersRouteContext;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
<<<<<<< HEAD
import java.util.Comparator;
import java.util.List;

import static de.metas.camel.externalsystems.common.ProcessorHelper.getPropertyOrThrowError;
=======
import java.util.List;
import java.util.Objects;

import static de.metas.camel.externalsystems.common.ProcessorHelper.getPropertyOrThrowError;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.BPARTNER_LOCATION_METASFRESH_ID_JSON_PATH;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		final String bPartnerLocationMetasfreshIdJSONPath = "/customFields/metasfreshLocationId"; // TODO: replace with request parameter that comes from the external system config
		
		final List<OrderDeliveryItem> orderDeliveryItems = shopwareClient.getDeliveryAddresses(orderCandidate.getJsonOrder().getId(),
																							   bPartnerLocationShopwareIdJSONPath,
																							   bPartnerLocationMetasfreshIdJSONPath,
=======

		final List<OrderDeliveryItem> orderDeliveryItems = shopwareClient.getDeliveryAddresses(orderCandidate.getJsonOrder().getId(),
																							   bPartnerLocationShopwareIdJSONPath,
																							   BPARTNER_LOCATION_METASFRESH_ID_JSON_PATH,
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
																							   importOrdersRouteContext.getEmailJsonPath());

		if (CollectionUtils.isEmpty(orderDeliveryItems))
		{
			throw new RuntimeException("Missing shipping address! OrderId=" + orderCandidate.getJsonOrder().getId());
		}
		final OrderDeliveryItem lastOrderDeliveryItem = orderDeliveryItems.get(orderDeliveryItems.size() - 1);
<<<<<<< HEAD

		importOrdersRouteContext.setMultipleShippingAddresses(orderDeliveryItems.size() > 1);
		importOrdersRouteContext.setDateRequired(lastOrderDeliveryItem.getJsonOrderDelivery().getShippingDateLatest().toLocalDate());
		importOrdersRouteContext.setShippingCost(lastOrderDeliveryItem.getJsonOrderDelivery().getShippingCost());
		importOrdersRouteContext.setShippingMethodId(lastOrderDeliveryItem.getJsonOrderDelivery().getShippingMethodId());
		importOrdersRouteContext.setOrderShippingAddress(lastOrderDeliveryItem.getOrderAddressDetails().getJsonOrderAddress());

		final JsonCustomerGroup jsonCustomerGroup =  getCustomerGroup(shopwareClient, orderCandidate.getJsonOrder().getOrderCustomer());

		importOrdersRouteContext.setBPartnerCustomerGroup(jsonCustomerGroup);

		final BPartnerUpsertRequestProducer bPartnerUpsertRequestProducer = BPartnerUpsertRequestProducer.builder()
				.shopwareClient(shopwareClient)
				.billingAddressId(orderCandidate.getJsonOrder().getBillingAddressId())
				.orderCustomer(orderCandidate.getJsonOrder().getOrderCustomer())
				.salutationInfoProvider(importOrdersRouteContext.getSalutationInfoProvider())
				.shippingAddress(lastOrderDeliveryItem.getOrderAddressDetails())
				.orgCode(orgCode)
				.metasfreshId(importOrdersRouteContext.getMetasfreshId())
				.userId(importOrdersRouteContext.getUserId())
				.bPartnerLocationIdentifierCustomShopwarePath(bPartnerLocationShopwareIdJSONPath)
				.bPartnerLocationIdentifierCustomMetasfreshPath(bPartnerLocationMetasfreshIdJSONPath)
				.emailCustomPath(importOrdersRouteContext.getEmailJsonPath())
				.matchingShopware6Mapping(importOrdersRouteContext.getMatchingShopware6Mapping())
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
			@NonNull final JsonOrderCustomer customer)
=======
			@NonNull final String customerShopwareId)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		try
		{
			// we need the "internal" shopware-ID to navigate to the customer
<<<<<<< HEAD
			return shopwareClient.getCustomerGroup(customer.getCustomerId())
=======
			return shopwareClient.getCustomerGroup(customerShopwareId)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
					.map(customerGroups -> Check.singleElement(customerGroups.getCustomerGroupList()))
					.orElse(null);
		}
		catch (final RuntimeException e)
		{
<<<<<<< HEAD
			throw new RuntimeCamelException("Exception getting CustomerGroup for customerId=" + customer.getCustomerId(), e);
		}
	}

	@Nullable
	private JsonExternalSystemShopware6ConfigMapping getMatchingShopware6Mapping(
			@Nullable final JsonExternalSystemShopware6ConfigMappings shopware6ConfigMappings,
			@Nullable final JsonCustomerGroup customerGroup)
	{
		if (shopware6ConfigMappings == null
				|| Check.isEmpty(shopware6ConfigMappings.getJsonExternalSystemShopware6ConfigMappingList())
				|| customerGroup == null)
		{
			return null;
		}

		return shopware6ConfigMappings.getJsonExternalSystemShopware6ConfigMappingList()
				.stream()
				.filter(mapping -> mapping.isGroupMatching(customerGroup.getName()))
				.min(Comparator.comparingInt(JsonExternalSystemShopware6ConfigMapping::getSeqNo))
				.orElse(null);
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
