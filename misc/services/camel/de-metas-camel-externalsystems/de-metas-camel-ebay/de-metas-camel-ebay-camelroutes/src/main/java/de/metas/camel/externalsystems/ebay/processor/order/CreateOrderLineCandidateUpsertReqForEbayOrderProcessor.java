/*
 * #%L
 * de-metas-camel-ebay-camelroutes
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

package de.metas.camel.externalsystems.ebay.processor.order;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.ebay.EbayImportOrdersRouteContext;
import de.metas.camel.externalsystems.ebay.EbayUtils;
import de.metas.camel.externalsystems.ebay.api.model.LineItem;
import de.metas.camel.externalsystems.ebay.api.model.Order;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsert;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsertItem;
import de.metas.common.bpartner.v2.response.JsonResponseUpsertItem;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateBulkRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateRequest;
import de.metas.common.ordercandidates.v2.request.JsonRequestBPartnerLocationAndContact;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static de.metas.camel.externalsystems.ebay.EbayConstants.DATA_SOURCE_INT_EBAY;
import static de.metas.camel.externalsystems.ebay.EbayConstants.DEFAULT_DELIVERY_RULE;
import static de.metas.camel.externalsystems.ebay.EbayConstants.DEFAULT_DELIVERY_VIA_RULE;
import static de.metas.camel.externalsystems.ebay.EbayConstants.DEFAULT_ORDER_LINE_DISCOUNT;
import static de.metas.camel.externalsystems.ebay.EbayConstants.ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT;
import static de.metas.camel.externalsystems.ebay.ProcessorHelper.getPropertyOrThrowError;

public class CreateOrderLineCandidateUpsertReqForEbayOrderProcessor implements Processor
{

	protected Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final EbayImportOrdersRouteContext importOrdersRouteContext = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT, EbayImportOrdersRouteContext.class);
		log.debug("Create OrderLineCandidates for ebay order {}", importOrdersRouteContext.getOrder().getOrderId());

		final JsonResponseBPartnerCompositeUpsert bPartnerUpsertResponseList = exchange.getIn().getBody(JsonResponseBPartnerCompositeUpsert.class);
		final JsonResponseBPartnerCompositeUpsertItem bPartnerUpsertResponse = Check.singleElement(bPartnerUpsertResponseList.getResponseItems());

		if (bPartnerUpsertResponse == null)
		{
			throw new RuntimeException("No JsonResponseUpsert present! OrderId=" + importOrdersRouteContext.getOrderNotNull().getOrderId());
		}

		final JsonOLCandCreateBulkRequest olCandBulkRequest = buildOlCandRequest(importOrdersRouteContext, bPartnerUpsertResponse);

		exchange.getIn().setBody(olCandBulkRequest);
	}

	private JsonOLCandCreateBulkRequest buildOlCandRequest(
			@NonNull final EbayImportOrdersRouteContext context,
			@NonNull final JsonResponseBPartnerCompositeUpsertItem bPartnerUpsertResponse)
	{
		final JsonOLCandCreateBulkRequest.JsonOLCandCreateBulkRequestBuilder olCandCreateBulkRequestBuilder = JsonOLCandCreateBulkRequest.builder();

		final Order ebayOrder = context.getOrderNotNull();

		final JsonOLCandCreateRequest.JsonOLCandCreateRequestBuilder olCandCreateRequestBuilder = JsonOLCandCreateRequest.builder();
		olCandCreateRequestBuilder
				.orgCode(context.getOrgCode())
				.currencyCode(getCurrencyCode(ebayOrder))
				.externalHeaderId(ebayOrder.getOrderId())
				.poReference(ebayOrder.getOrderId())
				.bpartner(getBPartnerInfo(context, bPartnerUpsertResponse))
				.billBPartner(getBillBPartnerInfo(context, bPartnerUpsertResponse))
				.dateOrdered(getDateOrdered(ebayOrder))
				.dateRequired(getDateOrdered(ebayOrder).plusDays(7)) // TODO - no mapping.
				.dataSource(DATA_SOURCE_INT_EBAY)
				.isManualPrice(true)
				.isImportedWithIssues(true)
				.discount(DEFAULT_ORDER_LINE_DISCOUNT)
				.importWarningMessage("PRE ALPHA TEST IMPORT"); // FIXME ;)

		final List<LineItem> orderLines = ebayOrder.getLineItems();

		if (orderLines.isEmpty())
		{
			throw new RuntimeException("Missing order lines! OrderId=" + ebayOrder.getOrderId());
		}

		orderLines.stream()
				.map(orderLine -> processOrderLine(olCandCreateRequestBuilder, ebayOrder, orderLine))
				.forEach(olCandCreateBulkRequestBuilder::request);

		return olCandCreateBulkRequestBuilder.build();
	}

	@NonNull
	private JsonRequestBPartnerLocationAndContact getBPartnerInfo(
			@NonNull final EbayImportOrdersRouteContext context,
			@NonNull final JsonResponseBPartnerCompositeUpsertItem bPartnerUpsertResponse)
	{
		final Order orderAndCustomId = context.getOrderNotNull();
		final String bPartnerExternalIdentifier = EbayUtils.bPartnerIdentifier(orderAndCustomId);

		final JsonMetasfreshId bpartnerId = getMetasfreshIdForExternalIdentifier(ImmutableList.of(bPartnerUpsertResponse.getResponseBPartnerItem()), bPartnerExternalIdentifier);

		final String shippingBPLocationExternalIdentifier = context.getShippingBPLocationExternalIdNotNull();
		final JsonMetasfreshId shippingBPartnerLocationId = getMetasfreshIdForExternalIdentifier(bPartnerUpsertResponse.getResponseLocationItems(), shippingBPLocationExternalIdentifier);

		return JsonRequestBPartnerLocationAndContact.builder()
				.bPartnerIdentifier(String.valueOf(bpartnerId.getValue()))
				.bPartnerLocationIdentifier(String.valueOf(shippingBPartnerLocationId.getValue()))
				.build();
	}

	@NonNull
	private JsonRequestBPartnerLocationAndContact getBillBPartnerInfo(
			@NonNull final EbayImportOrdersRouteContext context,
			@NonNull final JsonResponseBPartnerCompositeUpsertItem bPartnerUpsertResponse)
	{
		final Order orderAndCustomId = context.getOrderNotNull();

		final String bPartnerExternalIdentifier = EbayUtils.bPartnerIdentifier(orderAndCustomId);

		final JsonMetasfreshId bpartnerId = getMetasfreshIdForExternalIdentifier(ImmutableList.of(bPartnerUpsertResponse.getResponseBPartnerItem()), bPartnerExternalIdentifier);

		//use billing bparnter for business otherwise bpartner used for shipping.
		final String billingBPLocationExternalIdentifier;
		if (orderAndCustomId.getBuyer().getTaxIdentifier() != null) {
			billingBPLocationExternalIdentifier = context.getBillingBPLocationExternalIdNotNull();
		} else {
			billingBPLocationExternalIdentifier = context.getShippingBPLocationExternalIdNotNull();
		}

		final JsonMetasfreshId billingBPartnerLocationId = getMetasfreshIdForExternalIdentifier(bPartnerUpsertResponse.getResponseLocationItems(), billingBPLocationExternalIdentifier);

		return JsonRequestBPartnerLocationAndContact.builder()
				.bPartnerIdentifier(String.valueOf(bpartnerId.getValue()))
				.bPartnerLocationIdentifier(String.valueOf(billingBPartnerLocationId.getValue()))
				.build();
	}

	private JsonOLCandCreateRequest processOrderLine(
			@NonNull final JsonOLCandCreateRequest.JsonOLCandCreateRequestBuilder olCandCreateRequestBuilder,
			@NonNull final Order order,
			@NonNull final LineItem orderLine)
	{
		return olCandCreateRequestBuilder
				.externalLineId(orderLine.getLineItemId())
				.productIdentifier("val-" + orderLine.getSku())  //TODO review - val makes it use the value identifier.
				//TODO: better currency handling.
				.price(new BigDecimal(orderLine.getTotal().getValue()))
				.currencyCode(orderLine.getTotal().getCurrency())
				.qty(BigDecimal.valueOf(orderLine.getQuantity()))
				.description(orderLine.getTitle())
				.dateCandidate(getDateCandidate(order))
				.deliveryViaRule(DEFAULT_DELIVERY_VIA_RULE)
				.deliveryRule(DEFAULT_DELIVERY_RULE)
				.build();
	}

	@Nullable
	private LocalDate getDateCandidate(@NonNull final Order orderLine)
	{
		if (orderLine.getLastModifiedDate() != null)
		{
			return EbayUtils.toLocalDate(orderLine.getLastModifiedDate());
		}
		else if (orderLine.getCreationDate() != null)
		{
			return EbayUtils.toLocalDate(orderLine.getCreationDate());
		}

		return null;
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
	private String getCurrencyCode(@NonNull final Order order)
	{
		return order.getPaymentSummary().getTotalDueSeller().getCurrency();
	}

	@Nullable
	private LocalDate getDateOrdered(@NonNull final Order order)
	{
		return order.getCreationDate() != null
				? EbayUtils.toLocalDate(order.getCreationDate())
				: null;
	}

}
