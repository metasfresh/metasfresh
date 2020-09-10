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

package de.metas.camel.shipping.shipmentcandidate;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory.HttpMethods;
import org.apache.camel.component.file.GenericFileOperationFailedException;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JacksonXMLDataFormat;

import de.metas.camel.shipping.FeedbackProzessor;
import de.metas.camel.shipping.RouteBuilderCommonUtil;
import de.metas.common.shipping.shipmentcandidate.JsonResponseShipmentCandidates;

public class ShipmentCandidateJsonToXmlRouteBuilder extends EndpointRouteBuilder
{
	public static final String MF_SHIPMENT_CANDIDATE_JSON_TO_FILEMAKER_XML = "MF-JSON-To-FM-XML-ShipmentCandidate";

	public static final String SHIPMENT_CANDIDATE_FEEDBACK_ROUTE = "receiptCandidate-feedback";
	public static final String SHIPMENT_CANDIDATE_FEEDBACK_TO_MF = "ShipmentCandidate-Feedback-TO-MF";

	public static final String SHIPMENT_CANDIDATE_UPLOAD_ROUTE = "FM-upload-shipment-candidate";

	private static final String SHIPMENT_CANDIDATE_UPLOAD_URI = "{{siro.ftp.upload.shipment-candidate.uri}}";

	@Override
	public void configure()
	{
		final var timerPeriod = RouteBuilderCommonUtil.resolveProperty(getContext(), "metasfresh.shipment-candidate.pollIntervall", "5s");

		errorHandler(defaultErrorHandler());
		onException(GenericFileOperationFailedException.class)
				.handled(true)
				.logHandled(true)
				.to(direct(SHIPMENT_CANDIDATE_FEEDBACK_ROUTE));

		RouteBuilderCommonUtil.setupProperties(getContext());

		final JacksonDataFormat jacksonDataFormat = RouteBuilderCommonUtil.setupMetasfreshJSONFormat(getContext(), JsonResponseShipmentCandidates.class);
		final JacksonXMLDataFormat jacksonXMLDataFormat = RouteBuilderCommonUtil.setupFileMakerFormat(getContext());

		//@formatter:off
		from(timer("pollShipmentCandidateAPI")
				.period(timerPeriod))
				.routeId(MF_SHIPMENT_CANDIDATE_JSON_TO_FILEMAKER_XML)
				.streamCaching()
				.setHeader("Authorization", simple("{{metasfresh.api.authtoken}}"))
				.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
				.to("http://{{metasfresh.api.baseurl}}/shipments/shipmentCandidates")
				.unmarshal(jacksonDataFormat)

				.process(new ShipmentCandidateJsonToXmlProcessor())

				.choice()
				.when(header(RouteBuilderCommonUtil.NUMBER_OF_ITEMS).isGreaterThan(0))
				.log(LoggingLevel.INFO, "Converting ${header." + RouteBuilderCommonUtil.NUMBER_OF_ITEMS + "} shipment candidates to file ${header." + Exchange.FILE_NAME + "}")
				.marshal(jacksonXMLDataFormat)
				.multicast() // store the file both locally and send it to the remote folder
					.stopOnException()
					.to(file("{{local.file.output_path}}"), direct(SHIPMENT_CANDIDATE_UPLOAD_ROUTE))
				.end()
				.to(direct(SHIPMENT_CANDIDATE_FEEDBACK_ROUTE))
				.end() // "NumberOfItems" - choice
		;
		//@formatter:on

		RouteBuilderCommonUtil.setupFileMakerUploadRoute(this, SHIPMENT_CANDIDATE_UPLOAD_ROUTE, SHIPMENT_CANDIDATE_UPLOAD_URI);

		from(direct(SHIPMENT_CANDIDATE_FEEDBACK_ROUTE))
				.routeId(SHIPMENT_CANDIDATE_FEEDBACK_TO_MF)
				.log(LoggingLevel.INFO, "Reporting shipmentCandidate-outcome to metasfresh")
				.process(new FeedbackProzessor())
				.marshal(jacksonDataFormat)
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
				.to(http("{{metasfresh.api.baseurl}}/shipments/shipmentCandidatesResult"));
	}
}
