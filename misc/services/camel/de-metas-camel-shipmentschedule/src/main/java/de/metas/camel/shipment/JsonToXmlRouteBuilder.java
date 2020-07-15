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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.metas.common.shipmentschedule.JsonResponseShipmentCandidates;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory.HttpMethods;
import org.apache.camel.component.jackson.JacksonDataFormat;

public class JsonToXmlRouteBuilder extends EndpointRouteBuilder
{
	@Override
	public void configure() throws Exception
	{
		final ObjectMapper objectMapper = new ObjectMapper()
				.findAndRegisterModules()
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.enable(MapperFeature.USE_ANNOTATIONS);
		final JacksonDataFormat jacksonDataFormat = new JacksonDataFormat(JsonResponseShipmentCandidates.class);
		jacksonDataFormat.setCamelContext(getContext());
		jacksonDataFormat.setObjectMapper(objectMapper);
		jacksonDataFormat.setUnmarshalType(JsonResponseShipmentCandidates.class);

		from(timer("test").period(5 * 1000))
				.streamCaching()
				.routeId("MF-ShipmentSchedule-JSON-To-Filemaker-XML")

				.setHeader("Authorization", simple("{{metasfresh.api.authtoken}}"))
				.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
				.to(http("{{metasfresh.api.baseurl}}/shipments/shipmentSchedules"))

				.unmarshal(jacksonDataFormat)
				.process(new JsonToXmlProcessor())
				.marshal(jacksonDataFormat) // here we will marshal to the xml-format

				// store the file both locally and send it to the remote folder
				.multicast()
				.stopOnException()
				.to(file("{{local.file.output_path}}")/*, sftp("{{remote.sftp.output_path}}")*/)
				.end()

				.log(LoggingLevel.INFO, "Reporting outcome to metasfresh")
				.process(new FeedBackJsonCreator())
				.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.POST))
				.to(http("{{metasfresh.api.baseurl}}/shipments/shipmentSchedules"));
	}
}
