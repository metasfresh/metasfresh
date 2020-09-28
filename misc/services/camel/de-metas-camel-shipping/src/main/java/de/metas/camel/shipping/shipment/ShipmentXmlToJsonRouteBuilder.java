package de.metas.camel.shipping.shipment;

import de.metas.camel.shipping.RouteBuilderCommonUtil;
import de.metas.camel.shipping.shipment.inventory.InventoryCorrectionXmlToJsonProcessor;
import de.metas.common.shipment.JsonCreateShipmentRequest;
import de.metas.common.shipment.JsonCreateShipmentResponse;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JacksonXMLDataFormat;

import static de.metas.camel.inventory.InventoryXmlToMetasfreshRouteBuilder.ROUTE_ID_FROM_JSON;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.AUTHORIZATION;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.AUTHORIZATION_TOKEN;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.CREATE_SHIPMENT_MF_URL;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.INVENTORY_CORRECTION_XML_TO_JSON_PROCESSOR;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.LOCAL_STORAGE_URL;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.SHIPMENT_XML_TO_JSON_PROCESSOR;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.SIRO_FTP_PATH;

public class ShipmentXmlToJsonRouteBuilder extends EndpointRouteBuilder
{

	static final String MF_SHIPMENT_FILEMAKER_XML_TO_JSON = "MF-FM-To-Json-Shipment";
	static final String MF_SHIPMENT_INVENTORY_CORRECTION = "MF-FM-To-Json-Inventory-Correction";
	static final String MF_GENERATE_SHIPMENTS = "MF-FM-To-Json-Generate-Shipments";

	@Override
	public void configure() throws Exception
	{
		errorHandler(defaultErrorHandler());

		RouteBuilderCommonUtil.setupProperties(getContext());

		final JacksonXMLDataFormat jacksonXMLDataFormat = RouteBuilderCommonUtil.setupFileMakerFormat(getContext());

		// @formatter:off
		from(SIRO_FTP_PATH)
				.routeId(MF_SHIPMENT_FILEMAKER_XML_TO_JSON)
				.to(LOCAL_STORAGE_URL)
				.streamCaching()
				.unmarshal(jacksonXMLDataFormat)
				.multicast()
					.stopOnException()
					.to(direct(MF_SHIPMENT_INVENTORY_CORRECTION), direct(MF_GENERATE_SHIPMENTS))
				.end();
		// @formatter:on

		buildInventoryCorrectionRoute();

		buildGenerateShipmentsRoute();
	}

	private void buildInventoryCorrectionRoute()
	{
		from(direct(MF_SHIPMENT_INVENTORY_CORRECTION))
				.routeId(MF_SHIPMENT_INVENTORY_CORRECTION)
				.process(new InventoryCorrectionXmlToJsonProcessor()).id(INVENTORY_CORRECTION_XML_TO_JSON_PROCESSOR)
				// @formatter:off
				.choice()
					.when(header(RouteBuilderCommonUtil.NUMBER_OF_ITEMS).isLessThanOrEqualTo(0))
						.log(LoggingLevel.INFO, "Nothing to do! no inventory corrections were found in file: ${header." + Exchange.FILE_NAME + "}")
					.otherwise()
						.log(LoggingLevel.INFO, "Passing ${header." + RouteBuilderCommonUtil.NUMBER_OF_ITEMS + "} inventory correction lines to route:[" + ROUTE_ID_FROM_JSON + "].")
						.to(direct(ROUTE_ID_FROM_JSON))
				.end();
				// @formatter:on
	}

	private void buildGenerateShipmentsRoute()
	{
		final JacksonDataFormat requestJacksonDataFormat = RouteBuilderCommonUtil.setupMetasfreshJSONFormat(getContext(), JsonCreateShipmentRequest.class);
		final JacksonDataFormat responseJacksonDataFormat = RouteBuilderCommonUtil.setupMetasfreshJSONFormat(getContext(), JsonCreateShipmentResponse.class);

		from(direct(MF_GENERATE_SHIPMENTS))
				.routeId(MF_GENERATE_SHIPMENTS)
				.process(new ShipmentXmlToJsonProcessor()).id(SHIPMENT_XML_TO_JSON_PROCESSOR)
				// @formatter:off
				.choice()
					.when(header(RouteBuilderCommonUtil.NUMBER_OF_ITEMS).isLessThanOrEqualTo(0))
						.log(LoggingLevel.INFO, "Nothing to do! no shipments were found in file: ${header." + Exchange.FILE_NAME + "}")
					.otherwise()
						.log(LoggingLevel.INFO, "Posting ${header." + RouteBuilderCommonUtil.NUMBER_OF_ITEMS + "} shipments to metasfresh.")
						.marshal(requestJacksonDataFormat)
						.removeHeaders("*") // we don't want so send all headers as HTTP-headers; might be too much and we'd get an error back
						.setHeader(AUTHORIZATION, simple(AUTHORIZATION_TOKEN))
						.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
						.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
						.to(http(CREATE_SHIPMENT_MF_URL))
						.unmarshal(responseJacksonDataFormat)
						.process(new ShipmentResponseProcessor())
				.end();
				// @formatter:on
	}
}
