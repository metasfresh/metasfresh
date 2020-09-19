package de.metas.camel.shipping.shipment;

import de.metas.camel.shipping.RouteBuilderCommonUtil;
import de.metas.common.shipment.JsonCreateShipmentRequest;
import de.metas.common.shipment.JsonCreateShipmentResponse;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JacksonXMLDataFormat;

import static de.metas.camel.shipping.shipment.SiroShipmentConstants.AUTHORIZATION;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.AUTHORIZATION_TOKEN;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.CREATE_SHIPMENT_MF_URL;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.LOCAL_STORAGE_URL;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.SHIPMENT_XML_TO_JSON_PROCESSOR;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.SIRO_FTP_PATH;

public class ShipmentXmlToJsonRouteBuilder extends EndpointRouteBuilder
{

	static final String MF_SHIPMENT_FILEMAKER_XML_TO_JSON = "MF-FM-To-Json-Shipment";

	@Override
	public void configure() throws Exception
	{
		errorHandler(defaultErrorHandler());

		RouteBuilderCommonUtil.setupProperties(getContext());

		final JacksonDataFormat requestJacksonDataFormat = RouteBuilderCommonUtil.setupMetasfreshJSONFormat(getContext(), JsonCreateShipmentRequest.class);
		final JacksonDataFormat responseJacksonDataFormat = RouteBuilderCommonUtil.setupMetasfreshJSONFormat(getContext(), JsonCreateShipmentResponse.class);
		final JacksonXMLDataFormat jacksonXMLDataFormat = RouteBuilderCommonUtil.setupFileMakerFormat(getContext());

		from(SIRO_FTP_PATH)
				.routeId(MF_SHIPMENT_FILEMAKER_XML_TO_JSON)
				.to(LOCAL_STORAGE_URL)
				.streamCaching()
				.unmarshal(jacksonXMLDataFormat)
				.process(new ShipmentXmlToJsonProcessor()).id(SHIPMENT_XML_TO_JSON_PROCESSOR)

				// @formatter:off
				.choice()
					.when(header(RouteBuilderCommonUtil.NUMBER_OF_ITEMS).isLessThanOrEqualTo(0))
						.log(LoggingLevel.INFO, "Nothing to do! no shipments were found in file: ${header." + Exchange.FILE_NAME + "}")
					.otherwise()
						.log(LoggingLevel.INFO, "Posting ${header." + RouteBuilderCommonUtil.NUMBER_OF_ITEMS + "} shipments to metasfresh.")
						.marshal(requestJacksonDataFormat)
						.setHeader(AUTHORIZATION, simple(AUTHORIZATION_TOKEN))
						.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
						.to(http(CREATE_SHIPMENT_MF_URL))
						.unmarshal(responseJacksonDataFormat)
						.process(new ShipmentResponseProcessor())
				.end()		;
				// @formatter:off
	}
}
