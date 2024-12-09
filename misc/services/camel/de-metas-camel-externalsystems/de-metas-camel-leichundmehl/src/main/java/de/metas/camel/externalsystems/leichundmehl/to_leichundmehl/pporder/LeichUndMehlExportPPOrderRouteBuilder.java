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

import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.LogMessageRequest;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.v2.RetrieveProductCamelRequest;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder.processor.ReadPluFileProcessor;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.util.JSONUtil;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.leichundmehl.JsonPluFileAudit;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrder;
import de.metas.common.product.v2.response.JsonProduct;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.adprocess.JsonAdProcessRequest;
import de.metas.common.rest_api.v2.adprocess.JsonAdProcessRequestParam;
import de.metas.common.rest_api.v2.attachment.JsonAttachment;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentRequest;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentSourceType;
import de.metas.common.rest_api.v2.attachment.JsonTableRecordReference;
import de.metas.common.util.Check;
import de.metas.common.util.StringUtils;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_AD_Process_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_LOG_MESSAGE_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_RETRIEVE_MATERIAL_PRODUCT_INFO_V2_CAMEL_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_RETRIEVE_PP_ORDER_V2_CAMEL_ROUTE_ID;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.networking.tcp.SendToTCPRouteBuilder.SEND_TO_TCP_ROUTE_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_PP_ORDER_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class LeichUndMehlExportPPOrderRouteBuilder extends RouteBuilder
{
	public static final String EXPORT_PPORDER_ROUTE_ID = "LeichUndMehl-exportPPOrder";

	private final ProcessLogger processLogger;

	public LeichUndMehlExportPPOrderRouteBuilder(final ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}

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

				// get the PP_Order
				.process(this::prepareGetManufacturingOrderRequest)
				.to(direct(MF_RETRIEVE_PP_ORDER_V2_CAMEL_ROUTE_ID))
				.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonResponseManufacturingOrder.class))
				.process(this::processJsonResponseManufacturingOrder)

				// get the PP_Order's manufactured product
				.process(this::prepareGetProductRequest)
				.to(direct(MF_RETRIEVE_MATERIAL_PRODUCT_INFO_V2_CAMEL_ROUTE_ID))
				.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonProduct.class))
				.process(this::processJsonProduct)

				// if requested, then invoke a custom process, too
				.process(this::prepareCustomQueryProcessRequestIfAny)
				.choice()
					.when(header(ExternalSystemConstants.PARAM_CUSTOM_QUERY_AD_PROCESS_VALUE).isNotNull())
						.to(direct(MF_AD_Process_ROUTE_ID))
						.process(this::processCustomQueryProcessResponse)
					.otherwise()
						.log("No CustomQueryAdProcessValue => not invoking custom process")
					.endChoice()
				.end()
								
				.process(new ReadPluFileProcessor(processLogger))
				.to(direct(SEND_TO_TCP_ROUTE_ID))
				//.to(direct(SEND_TO_UDP_ROUTE_ID))

				.choice()
					.when(ExportPPOrderHelper.isPluFileExportAuditEnabled())
						.process(this::processLogMessageRequest)
						.to(direct(MF_LOG_MESSAGE_ROUTE_ID))

						.process(this::processJsonAttachmentRequest)
						.to(direct(ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID))
					.otherwise()
						.log("PLU-File export audit not enabled!")
					.endChoice()
				.end();
		//@formatter:on
	}

	private void buildAndAttachContext(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);
		final Map<String, String> parameters = request.getParameters();

		if (parameters.isEmpty())
		{
			throw new RuntimeException("Missing mandatory parameters from JsonExternalSystemRequest: " + request);
		}

		final String productBaseFolderName = parameters.get(ExternalSystemConstants.PARAM_PRODUCT_BASE_FOLDER_NAME);
		if (Check.isBlank(productBaseFolderName))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_PRODUCT_BASE_FOLDER_NAME);
		}

		final String pluFileExportAuditEnabled = parameters.get(ExternalSystemConstants.PARAM_PLU_FILE_EXPORT_AUDIT_ENABLED);
		final String customQueryAdProcessValue = parameters.get(ExternalSystemConstants.PARAM_CUSTOM_QUERY_AD_PROCESS_VALUE); // may be null

		final ExportPPOrderRouteContext context = ExportPPOrderRouteContext.builder()
				.jsonExternalSystemRequest(request)
				.ppOrderMetasfreshId(ExportPPOrderHelper.getPPOrderMetasfreshId(parameters))
				.connectionDetails(ExportPPOrderHelper.getTcpConnectionDetails(parameters))
				.customQueryAdProcessValue(customQueryAdProcessValue)
				.productMapping(ExportPPOrderHelper.getProductMapping(parameters))
				.productBaseFolderName(productBaseFolderName)
				.pluFileExportAuditEnabled(StringUtils.toBoolean(pluFileExportAuditEnabled))
				.pluFileConfigs(ExportPPOrderHelper.getPluFileConfigs(parameters))
				.build();

		exchange.setProperty(ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT, context);
	}

	private void prepareGetManufacturingOrderRequest(@NonNull final Exchange exchange)
	{
		final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange,
																						  ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT,
																						  ExportPPOrderRouteContext.class);
		exchange.getIn().setBody(context.getPpOrderMetasfreshId(), Integer.class);
	}

	private void processJsonResponseManufacturingOrder(@NonNull final Exchange exchange)
	{
		final JsonResponseManufacturingOrder jsonResponseManufacturingOrder = exchange.getIn().getBody(JsonResponseManufacturingOrder.class);

		final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange,
																						  ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT,
																						  ExportPPOrderRouteContext.class);
		context.setJsonResponseManufacturingOrder(jsonResponseManufacturingOrder);
	}

	private void prepareGetProductRequest(@NonNull final Exchange exchange)
	{
		final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange,
																						  ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT,
																						  ExportPPOrderRouteContext.class);

		final JsonResponseManufacturingOrder ppOrder = context.getManufacturingOrderNonNull();

		final String productIdentifier = String.valueOf(ppOrder.getProductId().getValue());

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

		context.setJsonProduct(jsonProduct);
	}

	private void prepareCustomQueryProcessRequestIfAny(@NonNull final Exchange exchange)
	{
		final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange,
																						  ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT,
																						  ExportPPOrderRouteContext.class);

		exchange.getIn().setHeader(ExternalSystemConstants.PARAM_CUSTOM_QUERY_AD_PROCESS_VALUE, context.getCustomQueryAdProcessValue());
		if (context.getCustomQueryAdProcessValue() == null)
		{
			return; // nothing to do
		}

		final JsonAdProcessRequest requestBody = JsonAdProcessRequest.builder()
				.processParameter(JsonAdProcessRequestParam.builder()
										  .name(PARAM_PP_ORDER_ID)
										  .value(context.getPpOrderMetasfreshId().toString())
										  .build())
				.build();
		exchange.getIn().setBody(requestBody);
	}

	private void processCustomQueryProcessResponse(@NonNull final Exchange exchange)
	{
		final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT, ExportPPOrderRouteContext.class);

		context.setCustomQueryProcessResponse(exchange.getIn().getBody(String.class));
	}

	private void processLogMessageRequest(@NonNull final Exchange exchange)
	{
		final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT, ExportPPOrderRouteContext.class);

		final JsonMetasfreshId adPInstanceId = context.getJsonExternalSystemRequest().getAdPInstanceId();

		Check.assumeNotNull(adPInstanceId, "adPInstanceId cannot be null at this stage!");

		final JsonPluFileAudit jsonPluFileAudit = context.getJsonPluFileAuditNonNull();
		final String jsonPluFileAuditMessage = JSONUtil.writeValueAsString(jsonPluFileAudit);

		final LogMessageRequest logMessageRequest = LogMessageRequest.builder()
				.logMessage(jsonPluFileAuditMessage)
				.pInstanceId(adPInstanceId)
				.build();

		exchange.getIn().setBody(logMessageRequest);
	}

	private void processJsonAttachmentRequest(@NonNull final Exchange exchange)
	{
		final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT, ExportPPOrderRouteContext.class);

		final JsonExternalSystemRequest request = context.getJsonExternalSystemRequest();

		final JsonMetasfreshId adPInstanceId = request.getAdPInstanceId();

		Check.assumeNotNull(adPInstanceId, "adPInstanceId cannot be null at this stage!");

		final String fileContent = context.getUpdatedPLUFileContent();

		final byte[] fileData = fileContent.getBytes();

		final String base64FileData = Base64.getEncoder().encodeToString(fileData);

		final JsonAttachment attachment = JsonAttachment.builder()
				.fileName(context.getFilename())
				.data(base64FileData)
				.type(JsonAttachmentSourceType.Data)
				.build();

		final JsonTableRecordReference jsonTableRecordReference = JsonTableRecordReference.builder()
				.tableName(LeichMehlConstants.AD_PINSTANCE_TABLE_NAME)
				.recordId(adPInstanceId)
				.build();

		final JsonAttachmentRequest jsonAttachmentRequest = JsonAttachmentRequest.builder()
				.attachment(attachment)
				.orgCode(request.getOrgCode())
				.reference(jsonTableRecordReference)
				.build();

		exchange.getIn().setBody(jsonAttachmentRequest);
	}
}
