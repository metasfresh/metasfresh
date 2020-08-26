/*
 * #%L
 * de-metas-camel-shipping
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.camel.shipping.receipt;

import de.metas.camel.shipping.RouteBuilderCommonUtil;
import de.metas.common.receipt.JsonCreateReceiptsRequest;
import de.metas.common.receipt.JsonCreateReceiptsResponse;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JacksonXMLDataFormat;

import static de.metas.camel.shipping.receipt.SiroReceiptConstants.CREATE_RECEIPT_MF_URL;
import static de.metas.camel.shipping.receipt.SiroReceiptConstants.LOCAL_STORAGE_URL;
import static de.metas.camel.shipping.receipt.SiroReceiptConstants.RECEIPT_XML_TO_JSON_PROCESSOR;
import static de.metas.camel.shipping.receipt.SiroReceiptConstants.SIRO_RECEIPTS_FTP_PATH;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.AUTHORIZATION;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.AUTHORIZATION_TOKEN;

public class ReceiptXmlToJsonRouteBuilder extends EndpointRouteBuilder
{
	static final String MF_RECEIPT_FILEMAKER_XML_TO_JSON = "MF-FM-To-Json-Receipt";

	@Override public void configure() throws Exception
	{
		errorHandler(defaultErrorHandler());

		RouteBuilderCommonUtil.setupProperties(getContext());

		final JacksonDataFormat requestJacksonDataFormat = RouteBuilderCommonUtil.setupMetasfreshJSONFormat(getContext(), JsonCreateReceiptsRequest.class);
		final JacksonDataFormat responseJacksonDataFormat = RouteBuilderCommonUtil.setupMetasfreshJSONFormat(getContext(), JsonCreateReceiptsResponse.class);
		final JacksonXMLDataFormat jacksonXMLDataFormat = RouteBuilderCommonUtil.setupFileMakerFormat(getContext());

		from(SIRO_RECEIPTS_FTP_PATH)
				.routeId(MF_RECEIPT_FILEMAKER_XML_TO_JSON)
				.to(LOCAL_STORAGE_URL)
				.streamCaching()
				.unmarshal(jacksonXMLDataFormat)
				.process(new ReceiptXmlToJsonProcessor()).id(RECEIPT_XML_TO_JSON_PROCESSOR)
				.choice()
					.when(header(RouteBuilderCommonUtil.NUMBER_OF_ITEMS).isLessThanOrEqualTo(0))
						.log(LoggingLevel.INFO, "Nothing to do! no receipts were found in file:" + header(Exchange.FILE_NAME))
					.otherwise()
						.log(LoggingLevel.INFO, "Posting " + header(RouteBuilderCommonUtil.NUMBER_OF_ITEMS) + " receipts to metasfresh.")
						.marshal(requestJacksonDataFormat)
						.setHeader(AUTHORIZATION, simple(AUTHORIZATION_TOKEN))
						.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
						.to(http(CREATE_RECEIPT_MF_URL))
						.unmarshal(responseJacksonDataFormat)
						.process(new ReceiptResponseProcessor())
				.end()
		;
	}
}
