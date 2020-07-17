/*
 * #%L
 * de-metas-camel-shipmentschedule
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

package de.metas.camel.shipment;

import de.metas.common.filemaker.ConfiguredXmlMapper;
import de.metas.common.filemaker.FMPXMLRESULT;
import de.metas.common.shipmentschedule.ConfiguredJsonMapper;
import de.metas.common.shipmentschedule.JsonResponseShipmentCandidates;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory.HttpMethods;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JacksonXMLDataFormat;

public class JsonToXmlRouteBuilder extends EndpointRouteBuilder
{
	public static final String NUMBER_OF_ITEMS = "NumberOfItems";
	public static final String FEEDBACK_POJO = "JsonRequestShipmentCandidateResults";

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler()
				.maximumRedeliveries(5)
				.redeliveryDelay(10000));
		onException(Exception.class)
		 	.handled(true)
		 	.to(direct("feedback"));

		final JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
		jacksonDataFormat.setCamelContext(getContext());
		jacksonDataFormat.setObjectMapper(ConfiguredJsonMapper.get());
		jacksonDataFormat.setUnmarshalType(JsonResponseShipmentCandidates.class);

		getContext().getRegistry().bind("FMPXMLRESULT-XmlMapper", ConfiguredXmlMapper.get());
		final var jacksonXMLDataFormat = new JacksonXMLDataFormat();
		jacksonXMLDataFormat.setUnmarshalType(FMPXMLRESULT.class);
		jacksonXMLDataFormat.setXmlMapper("FMPXMLRESULT-XmlMapper");


		from(timer("pollShipmentCandidateAPI")
				.period(5 * 1000))
				.routeId("MF-ShipmentCandidate-JSON-To-Filemaker-XML")
				.streamCaching()

				.setHeader("Authorization", simple("{{metasfresh.api.authtoken}}"))
				.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
				.to(http("{{metasfresh.api.baseurl}}/shipments/shipmentCandidates"))
				.unmarshal(jacksonDataFormat)

				.process(new JsonToXmlProcessor())

				.choice()
				.when(header(NUMBER_OF_ITEMS).isGreaterThan(0))
					.marshal(jacksonXMLDataFormat)
					.multicast() // store the file both locally and send it to the remote folder
						.stopOnException()
						.to("file://{{local.file.output_path}}", "{{upload.endpoint.uri}}")
					.end()
					.to(direct("feedback"))
				.end() // "NumberOfItems" - choice
		;

		from(direct("feedback"))
				.routeId("Candidate-Feedback-TO-MF")
				.log(LoggingLevel.INFO, "Reporting outcome to metasfresh")
				.process(new FeedBackJsonCreator())
				.marshal(jacksonDataFormat)
				.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.POST))
				.to(http("{{metasfresh.api.baseurl}}/shipments/shipmentCandidates"))
		;

	}
}
