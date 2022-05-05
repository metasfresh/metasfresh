/*
 * #%L
 * de-metas-camel-leichundmehl
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.RouteBuilderHelper;
import de.metas.camel.externalsystems.common.v2.BPRetrieveCamelRequest;
import de.metas.camel.externalsystems.common.v2.RetrieveProductCamelRequest;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.JsonBPartner;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.JsonBPartnerProduct;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.JsonPPOrder;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.JsonPrice;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.JsonProductInfo;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrder;
import de.metas.common.pricing.v2.productprice.JsonRequestProductPriceSearch;
import de.metas.common.pricing.v2.productprice.JsonResponsePrice;
import de.metas.common.pricing.v2.productprice.JsonResponsePriceList;
import de.metas.common.product.v2.response.JsonProduct;
import de.metas.common.product.v2.response.JsonProductBPartner;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_RETRIEVE_MATERIAL_PRODUCT_INFO_V2_CAMEL_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_RETRIEVE_PP_ORDER_V2_CAMEL_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_SEARCH_PRODUCT_PRICES_V2_CAMEL_ROUTE_ID;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.HEADER_FTP_DIRECTORY;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.HEADER_FTP_HOST;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.HEADER_FTP_PASSWORD;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.HEADER_FTP_PORT;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.HEADER_FTP_USERNAME;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FTP_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FTP_HOST;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FTP_PASSWORD;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FTP_PORT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FTP_USERNAME;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class LeichUndMehlExportPPOrderRouteBuilder extends RouteBuilder
{
	public static final String EXPORT_PPORDER_ROUTE_ID = "LeichUndMehl-exportPPOrder";

	@Override
	public void configure() throws Exception
	{
		//@formatter:off
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(EXPORT_PPORDER_ROUTE_ID))
				.routeId(EXPORT_PPORDER_ROUTE_ID)
				.log("Route invoked!")
				.streamCaching()
				.process(this::buildAndAttachContext)

				.to(direct(MF_RETRIEVE_PP_ORDER_V2_CAMEL_ROUTE_ID))
				.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonResponseManufacturingOrder.class))
				.process(this::processJsonResponseManufacturingOrder)

				.to(direct(MF_RETRIEVE_MATERIAL_PRODUCT_INFO_V2_CAMEL_ROUTE_ID))
				.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonProduct.class))
				.process(this::processJsonProduct)

				.choice()
					.when(ppOrderHasBPartnerAssigned())
						.process(this::attachBPRetrieveCamelRequest)
						.to("{{" + ExternalSystemCamelConstants.MF_RETRIEVE_BPARTNER_V2_CAMEL_URI + "}}")
							.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonResponseComposite.class))
							.process(this::processJsonResponseComposite)

						.process(this::attachJsonRequestProductPriceSearch)
						.to(direct(MF_SEARCH_PRODUCT_PRICES_V2_CAMEL_ROUTE_ID))
							.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonResponsePriceList.class))
							.process(this::processJsonResponsePriceListResponse)

				.process(this::prepareAndSendToFTP)
				.marshal(RouteBuilderHelper.setupJacksonDataFormatFor(getContext(), JsonPPOrder.class))
				.removeHeaders("CamelHttp*")
				.toD("ftp://${header.FTPHost}:${header.FTPPort}/${header.FTPDirectory}?username=${header.FTPUsername}&password=${header.FTPPassword}");

		//@formatter:on
	}

	private void buildAndAttachContext(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);
		final Map<String, String> parameters = request.getParameters();

		validateMandatoryParams(parameters);

		final String ppOrderId = parameters.get(ExternalSystemConstants.PARAM_PP_ORDER_ID);

		final ExportPPOrderRouteContext context = ExportPPOrderRouteContext.builder()
				.jsonExternalSystemRequest(request)
				.build();

		exchange.setProperty(ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT, context);

		final Integer ppOrderMetasfreshId = Integer.parseInt(ppOrderId);
		exchange.getIn().setBody(ppOrderMetasfreshId, Integer.class);
	}

	private void processJsonResponseManufacturingOrder(@NonNull final Exchange exchange)
	{
		final JsonResponseManufacturingOrder jsonResponseManufacturingOrder = exchange.getIn().getBody(JsonResponseManufacturingOrder.class);

		final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange,
																						  ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT,
																						  ExportPPOrderRouteContext.class);
		context.setJsonResponseManufacturingOrder(jsonResponseManufacturingOrder);
		context.setBpartnerId(jsonResponseManufacturingOrder.getBpartnerId());

		final String productIdentifier = String.valueOf(jsonResponseManufacturingOrder.getProductId().getValue());
		final RetrieveProductCamelRequest request = RetrieveProductCamelRequest.builder()
				.orgCode(context.getJsonExternalSystemRequest().getOrgCode())
				.productIdentifier(productIdentifier)
				.build();

		exchange.getIn().setBody(request, RetrieveProductCamelRequest.class);
	}

	private void processJsonProduct(@NonNull final Exchange exchange)
	{
		final JsonProduct jsonProduct = exchange.getIn().getBody(JsonProduct.class);
		final JsonProductInfo.JsonProductInfoBuilder jsonProductInfoBuilder = buildJsonProductInfo(jsonProduct);

		final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange,
																						  ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT,
																						  ExportPPOrderRouteContext.class);
		if (context.getJsonResponseManufacturingOrder() == null)
		{
			throw new RuntimeCamelException("JsonResponseManufacturingOrder cannot be null!");
		}

		final JsonMetasfreshId bpartnerId = context.getJsonResponseManufacturingOrder().getBpartnerId();

		if (bpartnerId != null && bpartnerId.getValue() != 0)
		{
			final JsonProductBPartner productBPartner = jsonProduct.getBpartners()
					.stream()
					.filter(bpartner -> bpartner.getBpartnerId().equals(bpartnerId))
					.findFirst()
					.orElseThrow(() -> new RuntimeCamelException("Cannot find jsonProductBPartner that matches PPOrder.bpartnerId " + bpartnerId));

			final JsonBPartnerProduct bPartnerProduct = mapToJsonProductBPartner(productBPartner);
			jsonProductInfoBuilder.bPartnerProduct(bPartnerProduct);
		}

		context.setJsonProduct(jsonProductInfoBuilder.build());
	}

	@NonNull
	private Predicate ppOrderHasBPartnerAssigned()
	{
		return exchange -> {
			final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT, ExportPPOrderRouteContext.class);

			final JsonMetasfreshId bpartnerId = context.getBpartnerId();
			return (bpartnerId != null && bpartnerId.getValue() != 0);
		};
	}

	private void attachBPRetrieveCamelRequest(@NonNull final Exchange exchange)
	{
		final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT, ExportPPOrderRouteContext.class);

		if (context.getBpartnerId() == null)
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_BPARTNER_ID);
		}

		final String bPartnerIdentifier = String.valueOf(context.getBpartnerId().getValue());

		final JsonMetasfreshId externalSystemConfigId = context.getJsonExternalSystemRequest().getExternalSystemConfigId();
		final JsonMetasfreshId adPInstanceId = context.getJsonExternalSystemRequest().getAdPInstanceId();

		final BPRetrieveCamelRequest retrieveCamelRequest = BPRetrieveCamelRequest.builder()
				.bPartnerIdentifier(bPartnerIdentifier)
				.externalSystemConfigId(externalSystemConfigId)
				.adPInstanceId(adPInstanceId)
				.build();

		exchange.getIn().setBody(retrieveCamelRequest);
	}

	private void processJsonResponseComposite(@NonNull final Exchange exchange)
	{
		final JsonResponseComposite jsonResponseComposite = exchange.getIn().getBody(JsonResponseComposite.class);

		final JsonBPartner jsonBPartner = JsonBPartner.builder()
				.bpartnerId(jsonResponseComposite.getBpartner().getMetasfreshId().getValue())
				.name(jsonResponseComposite.getBpartner().getName())
				.build();

		final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange,
																						  ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT,
																						  ExportPPOrderRouteContext.class);
		context.setJsonBPartner(jsonBPartner);
	}

	private void attachJsonRequestProductPriceSearch(@NonNull final Exchange exchange)
	{
		final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange,
																						  ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT,
																						  ExportPPOrderRouteContext.class);

		final JsonResponseManufacturingOrder responseManufacturingOrder = context.getJsonResponseManufacturingOrder();

		if (responseManufacturingOrder == null)
		{
			throw new RuntimeCamelException("JsonResponseManufacturingOrder cannot be null at this point!");
		}

		if (responseManufacturingOrder.getBpartnerId() == null)
		{
			throw new RuntimeCamelException("JsonResponseManufacturingOrder.bpartnerId cannot be null at this point!");
		}

		final String productIdentifier = String.valueOf(responseManufacturingOrder.getProductId().getValue());
		final String bpartnerIdentifier = String.valueOf(responseManufacturingOrder.getBpartnerId().getValue());

		final JsonRequestProductPriceSearch requestProductPriceSearch = JsonRequestProductPriceSearch.builder()
				.productIdentifier(productIdentifier)
				.bpartnerIdentifier(bpartnerIdentifier)
				.targetDate(responseManufacturingOrder.getDatePromised().toLocalDate())
				.build();

		exchange.getIn().setBody(requestProductPriceSearch, JsonRequestProductPriceSearch.class);
	}

	private void processJsonResponsePriceListResponse(@NonNull final Exchange exchange)
	{
		final JsonResponsePriceList response = exchange.getIn().getBody(JsonResponsePriceList.class);

		exchange.getIn().setBody(response.getPrices(), List.class);
	}

	private void prepareAndSendToFTP(@NonNull final Exchange exchange)
	{
		final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT, ExportPPOrderRouteContext.class);

		ImmutableList<JsonPrice> prices = null;
		if (exchange.getIn().getBody() != null)
		{
			final List<JsonResponsePrice> priceLists = exchange.getIn().getBody(List.class);

			prices = priceLists.stream()
					.map(this::mapToJsonPrice)
					.collect(ImmutableList.toImmutableList());
		}

		if (context.getJsonProduct() == null || context.getJsonResponseManufacturingOrder() == null)
		{
			throw new RuntimeCamelException("JsonProductInfo and JsonResponseManufacturingOrder cannot be null!");
		}

		final JsonProductInfo jsonProductInfo = context.getJsonProduct().withPrices(prices);

		final JsonPPOrder jsonPPOrder = mapToJsonPPOrder(context.getJsonResponseManufacturingOrder(), jsonProductInfo, context.getJsonBPartner());
		final Map<String, String> params = context.getJsonExternalSystemRequest().getParameters();

		exchange.getIn().setBody(jsonPPOrder, JsonPPOrder.class);
		exchange.getIn().setHeader(HEADER_FTP_HOST, params.get(PARAM_FTP_HOST));
		exchange.getIn().setHeader(HEADER_FTP_PORT, params.get(PARAM_FTP_PORT));
		exchange.getIn().setHeader(HEADER_FTP_USERNAME, params.get(PARAM_FTP_USERNAME));
		exchange.getIn().setHeader(HEADER_FTP_PASSWORD, params.get(PARAM_FTP_PASSWORD));
		exchange.getIn().setHeader(HEADER_FTP_DIRECTORY, params.get(PARAM_FTP_DIRECTORY));
	}

	@NonNull
	private JsonPPOrder mapToJsonPPOrder(
			@NonNull final JsonResponseManufacturingOrder ppOrder,
			@NonNull final JsonProductInfo jsonProductInfo,
			@Nullable final JsonBPartner jsonBPartner)
	{
		final JsonPPOrder.JsonPPOrderBuilder builder = JsonPPOrder.builder()
				.orderId(String.valueOf(ppOrder.getOrderId().getValue()))
				.dateOrdered(ppOrder.getDateOrdered())
				.components(ppOrder.getComponents())
				.dateStartSchedule(ppOrder.getDateStartSchedule())
				.description(ppOrder.getDescription())
				.documentNo(ppOrder.getDocumentNo())
				.productInfo(jsonProductInfo)
				.orgCode(ppOrder.getOrgCode())
				.finishGoodProduct(ppOrder.getFinishGoodProduct())
				.qtyToProduce(ppOrder.getQtyToProduce());

		if (ppOrder.getBpartnerId() != null && jsonBPartner != null)
		{
			builder.bPartner(jsonBPartner.withEan(jsonProductInfo.getBPartnerProduct().getEan()));
		}

		return builder.build();
	}

	@NonNull
	private JsonProductInfo.JsonProductInfoBuilder buildJsonProductInfo(@NonNull final JsonProduct jsonProduct)
	{
		final JsonProductInfo.JsonProductInfoBuilder builder = JsonProductInfo.builder()
				.id(jsonProduct.getId().getValue())
				.externalId(jsonProduct.getExternalId())
				.manufacturerName(jsonProduct.getManufacturerName())
				.productCategoryId(jsonProduct.getProductCategoryId().getValue())
				.productNo(jsonProduct.getProductNo())
				.uom(jsonProduct.getUom())
				.name(jsonProduct.getName())
				.manufacturerNumber(jsonProduct.getManufacturerNumber())
				.ean(jsonProduct.getEan())
				.description(jsonProduct.getDescription());

		if (jsonProduct.getManufacturerId() != null)
		{
			builder.manufacturerId(jsonProduct.getManufacturerId().getValue());
		}

		return builder;
	}

	@NonNull
	private JsonBPartnerProduct mapToJsonProductBPartner(@NonNull final JsonProductBPartner json)
	{
		return JsonBPartnerProduct.builder()
				.bpartnerId(String.valueOf(json.getBpartnerId().getValue()))
				.currentVendor(json.isCurrentVendor())
				.ean(json.getEan())
				.productNo(json.getProductNo())
				.customer(json.isCustomer())
				.excludedFromPurchase(json.isExcludedFromPurchase())
				.excludedFromSale(json.isExcludedFromSale())
				.productCategory(json.getProductCategory())
				.exclusionFromPurchaseReason(json.getExclusionFromPurchaseReason())
				.productName(json.getProductName())
				.productDescription(json.getProductDescription())
				.exclusionFromSaleReason(json.getExclusionFromSaleReason())
				.leadTimeInDays(json.getLeadTimeInDays())
				.productId(String.valueOf(json.getProductId().getValue()))
				.vendor(json.isVendor())
				.build();
	}

	@NonNull
	private JsonPrice mapToJsonPrice(@NonNull final JsonResponsePrice json)
	{
		return JsonPrice.builder()
				.price(json.getPrice())
				.currencyCode(json.getCurrencyCode())
				.isSOTrx(json.getIsSOTrx())
				.productCode(json.getProductCode())
				.productId(json.getProductId().getValue())
				.taxCategoryId(json.getTaxCategoryId().getValue())
				.build();
	}

	private void validateMandatoryParams(@NonNull final Map<String, String> params)
	{
		if (Check.isBlank(params.get(ExternalSystemConstants.PARAM_FTP_HOST)))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_FTP_HOST);
		}

		if (Check.isBlank(params.get(ExternalSystemConstants.PARAM_PP_ORDER_ID)))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_PP_ORDER_ID);
		}

		if (Check.isBlank(params.get(PARAM_FTP_PORT)))
		{
			throw new RuntimeException("Missing mandatory param: " + PARAM_FTP_PORT);
		}

		if (Check.isBlank(params.get(ExternalSystemConstants.PARAM_FTP_USERNAME)))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_FTP_USERNAME);
		}

		if (Check.isBlank(params.get(ExternalSystemConstants.PARAM_FTP_PASSWORD)))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_FTP_PASSWORD);
		}

		if (Check.isBlank(params.get(ExternalSystemConstants.PARAM_FTP_DIRECTORY)))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_FTP_DIRECTORY);
		}
	}
}
