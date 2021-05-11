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

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.customer.JsonCustomerGroup;
import de.metas.camel.externalsystems.shopware6.api.model.customer.JsonCustomerGroups;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrder;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderLine;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderLines;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonTax;
import de.metas.camel.externalsystems.shopware6.api.model.order.OrderCandidate;
import de.metas.camel.externalsystems.shopware6.api.model.order.PaymentMethodType;
import de.metas.camel.externalsystems.shopware6.common.ExternalIdentifierFormat;
import de.metas.camel.externalsystems.shopware6.currency.CurrencyInfoProvider;
import de.metas.camel.externalsystems.shopware6.order.ImportOrdersRouteContext;
import de.metas.camel.externalsystems.shopware6.order.OrderCompositeInfo;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsert;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsertItem;
import de.metas.common.bpartner.v2.response.JsonResponseUpsertItem;
import de.metas.common.externalsystem.JsonExternalSystemShopware6ConfigMapping;
import de.metas.common.ordercandidates.v2.request.JSONPaymentRule;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateBulkRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateRequest;
import de.metas.common.ordercandidates.v2.request.JsonOrderDocType;
import de.metas.common.ordercandidates.v2.request.JsonOrderLineGroup;
import de.metas.common.ordercandidates.v2.request.JsonRequestBPartnerLocationAndContact;
import de.metas.common.ordercandidates.v2.request.JsonSalesPartner;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static de.metas.camel.externalsystems.shopware6.ProcessorHelper.getPropertyOrThrowError;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.DATA_SOURCE_INT_SHOPWARE;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.DEFAULT_DELIVERY_RULE;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.DEFAULT_DELIVERY_VIA_RULE;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.DEFAULT_ORDER_LINE_DISCOUNT;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.FREIGHT_COST_EXTERNAL_LINE_ID_PREFIX;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.MULTIPLE_SHIPPING_ADDRESSES_WARN_MESSAGE;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.VALUE_PREFIX;

public class OLCandRequestProcessor implements Processor
{

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final ImportOrdersRouteContext importOrdersRouteContext = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT, ImportOrdersRouteContext.class);

		final JsonResponseBPartnerCompositeUpsert bPartnerUpsertResponseList = exchange.getIn().getBody(JsonResponseBPartnerCompositeUpsert.class);
		final JsonResponseBPartnerCompositeUpsertItem bPartnerUpsertResponse = Check.singleElement(bPartnerUpsertResponseList.getResponseItems());

		if (bPartnerUpsertResponse == null)
		{
			throw new RuntimeException("No JsonResponseUpsert present! OrderId=" + importOrdersRouteContext.getOrderNotNull().getJsonOrder().getId());
		}

		final JsonOLCandCreateBulkRequest olCandBulkRequest = buildOlCandRequest(importOrdersRouteContext, bPartnerUpsertResponse);

		exchange.getIn().setBody(olCandBulkRequest);
	}

	private JsonOLCandCreateBulkRequest buildOlCandRequest(
			@NonNull final ImportOrdersRouteContext context,
			@NonNull final JsonResponseBPartnerCompositeUpsertItem bPartnerUpsertResponse)
	{
		final JsonOLCandCreateBulkRequest.JsonOLCandCreateBulkRequestBuilder olCandCreateBulkRequestBuilder = JsonOLCandCreateBulkRequest.builder();

		final OrderCandidate orderCandidate = context.getOrderNotNull();

		final JsonOLCandCreateRequest.JsonOLCandCreateRequestBuilder olCandCreateRequestBuilder = JsonOLCandCreateRequest.builder();
		olCandCreateRequestBuilder
				.orgCode(context.getOrgCode())
				.currencyCode(getCurrencyCode(context.getCurrencyInfoProvider(), orderCandidate.getJsonOrder().getCurrencyId()))
				.externalHeaderId(orderCandidate.getJsonOrder().getId())
				.poReference(orderCandidate.getJsonOrder().getOrderNumber())
				.bpartner(getBPartnerInfo(context, bPartnerUpsertResponse))
				.billBPartner(getBillBPartnerInfo(context, bPartnerUpsertResponse))
				.dateOrdered(getDateOrdered(orderCandidate.getJsonOrder()))
				.dateRequired(context.getDateRequired())
				.dateCandidate(getDateCandidate(orderCandidate.getJsonOrder()))
				.dataSource(DATA_SOURCE_INT_SHOPWARE)
				.isManualPrice(true)
				.isImportedWithIssues(true)
				.discount(DEFAULT_ORDER_LINE_DISCOUNT)
				.deliveryViaRule(DEFAULT_DELIVERY_VIA_RULE)
				.deliveryRule(DEFAULT_DELIVERY_RULE)
				.importWarningMessage(context.isMultipleShippingAddresses() ? MULTIPLE_SHIPPING_ADDRESSES_WARN_MESSAGE : null);

		if (Check.isNotBlank(context.getShippingMethodId()))
		{
			olCandCreateRequestBuilder.shipper(ExternalIdentifierFormat.formatExternalId(context.getShippingMethodId()));
		}

		if (Check.isNotBlank(orderCandidate.getSalesRepId()))
		{
			olCandCreateRequestBuilder.salesPartner(JsonSalesPartner.builder()
															.salesPartnerCode(orderCandidate.getSalesRepId())
															.build());
		}

		processShopwareConfigs(context, olCandCreateRequestBuilder);

		final List<JsonOrderLine> orderLines = getJsonOrderLines(context, orderCandidate.getJsonOrder().getId());

		if (orderLines.isEmpty())
		{
			throw new RuntimeException("Missing order lines! OrderId=" + orderCandidate.getJsonOrder().getId());
		}

		orderLines.stream()
				.map(orderLine -> processOrderLine(olCandCreateRequestBuilder, orderLine))
				.forEach(olCandCreateBulkRequestBuilder::request);

		if (context.getShippingCostNotNull().getCalculatedTaxes() != null)
		{
			context.getShippingCostNotNull().getCalculatedTaxes()
					.stream()
					.map(tax -> processTax(context.getTaxProductIdProvider(), olCandCreateRequestBuilder, tax))
					.filter(Optional::isPresent)
					.map(Optional::get)
					.forEach(olCandCreateBulkRequestBuilder::request);
		}

		return olCandCreateBulkRequestBuilder.build();
	}

	@NonNull
	private JsonRequestBPartnerLocationAndContact getBPartnerInfo(
			@NonNull final ImportOrdersRouteContext context,
			@NonNull final JsonResponseBPartnerCompositeUpsertItem bPartnerUpsertResponse)
	{
		final OrderCandidate orderCandidate = context.getOrderNotNull();
		final String bPartnerExternalId = CoalesceUtil.coalesce(orderCandidate.getCustomBPartnerId(), orderCandidate.getJsonOrder().getOrderCustomer().getCustomerId());
		final String bPartnerExternalIdentifier = ExternalIdentifierFormat.formatExternalId(bPartnerExternalId);

		final JsonMetasfreshId bpartnerId = getMetasfreshIdForExternalIdentifier(ImmutableList.of(bPartnerUpsertResponse.getResponseBPartnerItem()), bPartnerExternalIdentifier);

		final String shippingBPLocationExternalIdentifier = ExternalIdentifierFormat.formatExternalId(context.getShippingBPLocationExternalIdNotNull());
		final JsonMetasfreshId shippingBPartnerLocationId = getMetasfreshIdForExternalIdentifier(bPartnerUpsertResponse.getResponseLocationItems(), shippingBPLocationExternalIdentifier);

		return JsonRequestBPartnerLocationAndContact.builder()
				.bPartnerIdentifier(String.valueOf(bpartnerId.getValue()))
				.bPartnerLocationIdentifier(String.valueOf(shippingBPartnerLocationId.getValue()))
				.build();
	}

	@NonNull
	private JsonRequestBPartnerLocationAndContact getBillBPartnerInfo(
			@NonNull final ImportOrdersRouteContext context,
			@NonNull final JsonResponseBPartnerCompositeUpsertItem bPartnerUpsertResponse)
	{
		final OrderCandidate orderCandidate = context.getOrderNotNull();

		final String bPartnerExternalId = CoalesceUtil.coalesce(orderCandidate.getCustomBPartnerId(), orderCandidate.getJsonOrder().getOrderCustomer().getCustomerId());
		final String bPartnerExternalIdentifier = ExternalIdentifierFormat.formatExternalId(bPartnerExternalId);

		final JsonMetasfreshId bpartnerId = getMetasfreshIdForExternalIdentifier(ImmutableList.of(bPartnerUpsertResponse.getResponseBPartnerItem()), bPartnerExternalIdentifier);

		final String billingBPLocationExternalIdentifier = ExternalIdentifierFormat.formatExternalId(context.getBillingBPLocationExternalIdNotNull());
		final JsonMetasfreshId billingBPartnerLocationId = getMetasfreshIdForExternalIdentifier(bPartnerUpsertResponse.getResponseLocationItems(), billingBPLocationExternalIdentifier);

		return JsonRequestBPartnerLocationAndContact.builder()
				.bPartnerIdentifier(String.valueOf(bpartnerId.getValue()))
				.bPartnerLocationIdentifier(String.valueOf(billingBPartnerLocationId.getValue()))
				.build();
	}

	@NonNull
	private List<JsonOrderLine> getJsonOrderLines(
			@NonNull final ImportOrdersRouteContext importOrdersRouteContext,
			@NonNull final String orderId)
	{
		final ShopwareClient shopwareClient = importOrdersRouteContext.getShopwareClient();

		return shopwareClient.getOrderLines(orderId)
				.map(JsonOrderLines::getOrderLinesWithProductId)
				.orElseThrow(() -> new RuntimeException("Missing order lines! OrderId=" + orderId));
	}

	private JsonOLCandCreateRequest processOrderLine(
			@NonNull final JsonOLCandCreateRequest.JsonOLCandCreateRequestBuilder olCandCreateRequestBuilder,
			@NonNull final JsonOrderLine orderLine)
	{
		return olCandCreateRequestBuilder
				.externalLineId(orderLine.getId())
				.productIdentifier(ExternalIdentifierFormat.formatExternalId(orderLine.getProductId()))
				.price(orderLine.getUnitPrice())
				.qty(orderLine.getQuantity())
				.description(orderLine.getDescription())
				.line(orderLine.getPosition())
				.orderLineGroup(getJsonOrderLineGroup(orderLine))
				.build();
	}

	@NonNull
	private LocalDate getDateCandidate(@NonNull final JsonOrder order)
	{
		if (order.getUpdatedAt() != null)
		{
			return order.getUpdatedAt().toLocalDate();
		}

		return order.getCreatedAt().toLocalDate();
	}

	@Nullable
	private JsonOrderLineGroup getJsonOrderLineGroup(@NonNull final JsonOrderLine orderLine)
	{
		final boolean isBundle = orderLine.getPayload() != null && Boolean.TRUE.equals(orderLine.getPayload().getIsBundle());

		if (!isBundle && Check.isBlank(orderLine.getParentId()))
		{
			return null;
		}

		return JsonOrderLineGroup.builder()
				.groupKey(isBundle ? orderLine.getId() : orderLine.getParentId())
				.isGroupMainItem(isBundle)
				.build();
	}

	@NonNull
	private JsonMetasfreshId getMetasfreshIdForExternalIdentifier(
			@NonNull final List<JsonResponseUpsertItem> bPartnerResponseUpsertItems,
			@NonNull final String externalIdentifier)
	{
		return bPartnerResponseUpsertItems
				.stream()
				.filter(responseItem -> responseItem.getIdentifier().equals(externalIdentifier) && responseItem.getMetasfreshId() != null)
				.findFirst()
				.map(JsonResponseUpsertItem::getMetasfreshId)
				.orElseThrow(() -> new RuntimeException("Something went wrong! No JsonResponseUpsertItem was found for the externalIdentifier:" + externalIdentifier));
	}

	@Nullable
	private String getCurrencyCode(@NonNull final CurrencyInfoProvider currencyInfoProvider, @Nullable final String currencyId)
	{
		if (Check.isBlank(currencyId))
		{
			return null;
		}

		return currencyInfoProvider.getIsoCodeByCurrencyIdNotNull(currencyId);
	}

	@Nullable
	private LocalDate getDateOrdered(@NonNull final JsonOrder order)
	{
		return order.getOrderDate() != null
				? order.getOrderDate().toLocalDate()
				: null;
	}

	private void processShopwareConfigs(
			@NonNull final ImportOrdersRouteContext routeContext,
			@NonNull final JsonOLCandCreateRequest.JsonOLCandCreateRequestBuilder olCandCreateRequestBuilder)
	{
		if (routeContext.getShopware6ConfigMappings() == null
				|| routeContext.getShopware6ConfigMappings().getJsonExternalSystemShopware6ConfigMappingList().isEmpty())
		{
			return;
		}

		final Optional<JsonCustomerGroups> groupsOptional = invokeShopwareClientGetCustomerGroups(routeContext);
		final Optional<JsonCustomerGroup> customerGroup = groupsOptional
				.filter(customerGroups -> customerGroups.getCustomerGroupList().size() == 1)
				.map(jsonCustomerGroups -> jsonCustomerGroups.getCustomerGroupList().get(0));

		if (customerGroup.isEmpty())
		{
			return;
		}

		final OrderCompositeInfo orderCompositeInfo = routeContext.getCompositeOrderNotNull();

		final PaymentMethodType candidatePaymentMethod = PaymentMethodType.ofValue(orderCompositeInfo.getJsonPaymentMethod().getShortName());
		final String customerGroupValue = customerGroup.get().getName();

		final Optional<JsonExternalSystemShopware6ConfigMapping> matchingConfig = routeContext.getShopware6ConfigMappings()
				.getJsonExternalSystemShopware6ConfigMappingList()
				.stream()
				.sorted(Comparator.comparingInt(JsonExternalSystemShopware6ConfigMapping::getSeqNo))
				.filter(config -> config.isGroupMatching(customerGroupValue) && config.isPaymentMethodMatching(candidatePaymentMethod.getValue()))
				.findFirst();

		matchingConfig.ifPresent(config -> olCandCreateRequestBuilder.orderDocType(JsonOrderDocType.ofCode(config.getDocTypeOrder()))
				.paymentRule(JSONPaymentRule.ofCode(config.getPaymentRule()))
				.paymentTerm(Check.isBlank(config.getPaymentTermValue())
						? null
						: VALUE_PREFIX + "-" + config.getPaymentTermValue()));
	}

	@NonNull
	private Optional<JsonCustomerGroups> invokeShopwareClientGetCustomerGroups(@NonNull final ImportOrdersRouteContext routeContext)
	{
		final OrderCandidate order = routeContext.getOrderNotNull();
		final Optional<JsonCustomerGroups> groupsOptional;
		try
		{
			// we need the "internal" shopware-ID to navigate to the customer
			groupsOptional = routeContext.getShopwareClient().getCustomerGroup(order.getShopwareCustomerId());
		}
		catch (final RuntimeException e)
		{
			throw new RuntimeCamelException("Exception getting CustomerGroup for order-id=" + order.getJsonOrder().getId() + " and customer-id=" + order.getShopwareCustomerId(), e);
		}
		return groupsOptional;
	}

	@NonNull
	private Optional<JsonOLCandCreateRequest> processTax(
			@Nullable final TaxProductIdProvider taxProductIdProvider,
			@NonNull final JsonOLCandCreateRequest.JsonOLCandCreateRequestBuilder olCandCreateRequestBuilder,
			@NonNull final JsonTax tax)
	{
		if (taxProductIdProvider == null)
		{
			return Optional.empty();
		}

		return Optional.of(
				olCandCreateRequestBuilder
						.externalLineId(FREIGHT_COST_EXTERNAL_LINE_ID_PREFIX + tax.getTaxRate())
						.line(null)
						.orderLineGroup(null)
						.description(null)
						.productIdentifier(String.valueOf(taxProductIdProvider.getProductIdByVatRate(tax.getTaxRate()).getValue()))
						.price(tax.getPrice())
						.qty(BigDecimal.ONE)
						.build()
		);
	}
}
