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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.v2.BPRetrieveCamelRequest;
import de.metas.camel.externalsystems.common.v2.RetrieveProductCamelRequest;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.JsonConverter;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.JsonBPartner;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.JsonPPOrder;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.JsonPrice;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.JsonProductInfo;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.ftp.DispatchMessageRequest;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrder;
import de.metas.common.pricing.v2.productprice.JsonRequestProductPriceSearch;
import de.metas.common.pricing.v2.productprice.JsonResponsePriceList;
import de.metas.common.product.v2.response.JsonProduct;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_ORG_CODE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_RETRIEVE_MATERIAL_PRODUCT_INFO_V2_CAMEL_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_RETRIEVE_PP_ORDER_V2_CAMEL_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_SEARCH_PRODUCT_PRICES_V2_CAMEL_ROUTE_ID;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.ftp.SendToFTPRouteBuilder.SEND_TO_FTP_ROUTE_ID;
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
					.when(ExportPPOrderHelper.ppOrderHasBPartnerAssigned())
						.process(this::attachBPRetrieveCamelRequest)

						.to("{{" + ExternalSystemCamelConstants.MF_RETRIEVE_BPARTNER_V2_CAMEL_URI + "}}")
						.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonResponseComposite.class))
						.process(this::processJsonResponseComposite)

						.process(this::attachJsonRequestProductPriceSearch)
						.to(direct(MF_SEARCH_PRODUCT_PRICES_V2_CAMEL_ROUTE_ID))
						.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonResponsePriceList.class))
						.process(this::processJsonResponsePriceListResponse)

					.otherwise()
						.log("No BPartnerId present on PPOrder!")
					.endChoice()
				.end()

				.process(this::buildDispatchMessageRequest)
				.to(direct(SEND_TO_FTP_ROUTE_ID));

		//@formatter:on
	}

	private void buildAndAttachContext(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);
		final Map<String, String> parameters = request.getParameters();

		final ExportPPOrderRouteContext context = ExportPPOrderRouteContext.builder()
				.jsonExternalSystemRequest(request)
				.ftpCredentials(ExportPPOrderHelper.getFTPCredentials(parameters))
				.build();

		exchange.setProperty(ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT, context);

		if (Check.isBlank(parameters.get(ExternalSystemConstants.PARAM_PP_ORDER_ID)))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_PP_ORDER_ID);
		}

		final Integer ppOrderMetasfreshId = Integer.parseInt(parameters.get(ExternalSystemConstants.PARAM_PP_ORDER_ID));

		exchange.getIn().setBody(ppOrderMetasfreshId, Integer.class);
	}

	private void processJsonResponseManufacturingOrder(@NonNull final Exchange exchange)
	{
		final JsonResponseManufacturingOrder jsonResponseManufacturingOrder = exchange.getIn().getBody(JsonResponseManufacturingOrder.class);

		final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange,
																						  ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT,
																						  ExportPPOrderRouteContext.class);
		context.setJsonResponseManufacturingOrder(jsonResponseManufacturingOrder);

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

		final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT, ExportPPOrderRouteContext.class);

		final JsonProductInfo.JsonProductInfoBuilder jsonProductInfoBuilder = JsonConverter.initProductInfo(context.getManufacturingOrderNonNull(), jsonProduct);

		context.setJsonProductBuilder(jsonProductInfoBuilder);
	}

	private void attachBPRetrieveCamelRequest(@NonNull final Exchange exchange)
	{
		final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT, ExportPPOrderRouteContext.class);

		final String bPartnerIdentifier = String.valueOf(context.getBPartnerIdNonNull().getValue());

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
				.glnList(ExportPPOrderHelper.getBPartnerGLNList(jsonResponseComposite))
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

		final JsonResponseManufacturingOrder responseManufacturingOrder = context.getManufacturingOrderNonNull();

		final String productIdentifier = String.valueOf(responseManufacturingOrder.getProductId().getValue());
		final String bpartnerIdentifier = String.valueOf(context.getBPartnerIdNonNull().getValue());

		final JsonRequestProductPriceSearch requestProductPriceSearch = JsonRequestProductPriceSearch.builder()
				.productIdentifier(productIdentifier)
				.bpartnerIdentifier(bpartnerIdentifier)
				.targetDate(responseManufacturingOrder.getDatePromised().toLocalDate())
				.build();

		exchange.getIn().setHeader(HEADER_ORG_CODE, context.getJsonExternalSystemRequest().getOrgCode());
		exchange.getIn().setBody(requestProductPriceSearch, JsonRequestProductPriceSearch.class);
	}

	private void processJsonResponsePriceListResponse(@NonNull final Exchange exchange)
	{
		final JsonResponsePriceList response = exchange.getIn().getBody(JsonResponsePriceList.class);

		final ImmutableList<JsonPrice> productPrices = response.getPrices()
				.stream()
				.map(JsonConverter::mapToJsonPrice)
				.collect(ImmutableList.toImmutableList());

		final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange,
																						  ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT,
																						  ExportPPOrderRouteContext.class);

		context.getProductInfoBuilderNonNull().prices(productPrices);
	}

	private void buildDispatchMessageRequest(@NonNull final Exchange exchange) throws JsonProcessingException
	{
		final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT, ExportPPOrderRouteContext.class);

		final JsonProductInfo jsonProductInfo = context.getProductInfoBuilderNonNull().build();

		final JsonPPOrder jsonPPOrder = JsonConverter.mapToJsonPPOrder(context.getManufacturingOrderNonNull(), jsonProductInfo, context.getJsonBPartner());

		final DispatchMessageRequest request = DispatchMessageRequest.builder()
				.ftpPayload(JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(jsonPPOrder))
				.ftpCredentials(context.getFtpCredentials())
				.build();

		exchange.getIn().setBody(request, DispatchMessageRequest.class);
	}
}
