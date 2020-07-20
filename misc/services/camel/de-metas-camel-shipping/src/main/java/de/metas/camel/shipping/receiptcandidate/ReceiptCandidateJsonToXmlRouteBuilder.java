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

package de.metas.camel.shipping.receiptcandidate;

import de.metas.camel.shipping.RouteBuilderCommonUtil;
import de.metas.camel.shipping.shipmentcandidate.ShipmentCandidateJsonToXmlProcessor;
import de.metas.common.shipping.receiptcandidate.JsonResponseReceiptCandidates;
import de.metas.common.shipping.shipmentcandidate.JsonResponseShipmentCandidates;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory.HttpMethods;
import org.apache.camel.component.file.GenericFileOperationFailedException;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JacksonXMLDataFormat;

public class ReceiptCandidateJsonToXmlRouteBuilder extends EndpointRouteBuilder
{
	public static final String MF_RECEIPT_CANDIDATE_JSON_TO_FILEMAKER_XML = "MF-ReceiptCandidate-JSON-To-Filemaker-XML";

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler()
				.maximumRedeliveries(5)
				.redeliveryDelay(10000));
		onException(GenericFileOperationFailedException.class)
				.handled(true)
				.to(direct(RouteBuilderCommonUtil.FEEDBACK_ROUTE));

		RouteBuilderCommonUtil.setupProperties(getContext());

		final JacksonDataFormat jacksonDataFormat = RouteBuilderCommonUtil.setupMetasfreshJSONFormat(getContext(), JsonResponseReceiptCandidates.class);
		final JacksonXMLDataFormat jacksonXMLDataFormat = RouteBuilderCommonUtil.setupFileMakerFormat(getContext());

		from(timer("pollReceiptCandidateAPI")
				.period(5 * 1000))
				.routeId(MF_RECEIPT_CANDIDATE_JSON_TO_FILEMAKER_XML)
				.streamCaching()
				.log(LoggingLevel.INFO, "Invoking REST-EP")
				.setHeader("Authorization", simple("{{metasfresh.api.authtoken}}"))
				.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
				.to(http("{{metasfresh.api.baseurl}}/receipts/receiptCandidates"))
				.log(LoggingLevel.TRACE, "Unmarshalling JSON")
				.unmarshal(jacksonDataFormat)

				.log(LoggingLevel.TRACE, "Processing JSON")
				.process(new ReceiptCandidateJsonToXmlProcessor())

				.choice()
				.when(header(RouteBuilderCommonUtil.NUMBER_OF_ITEMS).isGreaterThan(0))
				.marshal(jacksonXMLDataFormat)
				.multicast() // store the file both locally and send it to the remote folder
				.stopOnException()
				.to("file://{{local.file.output_path}}", "{{upload.endpoint.uri}}")
				.end()
				.to(direct(RouteBuilderCommonUtil.FEEDBACK_ROUTE))
				.end() // "NumberOfItems" - choice
		;

		RouteBuilderCommonUtil.setupFeedbackRoute(this, jacksonDataFormat, "/receipts/receiptCandidates");
	}
}
