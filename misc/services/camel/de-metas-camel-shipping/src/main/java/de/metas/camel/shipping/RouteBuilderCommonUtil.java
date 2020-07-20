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

package de.metas.camel.shipping;

import de.metas.common.filemaker.ConfiguredXmlMapper;
import de.metas.common.filemaker.FMPXMLRESULT;
import de.metas.common.shipping.ConfiguredJsonMapper;
import de.metas.common.shipping.shipmentcandidate.JsonResponseShipmentCandidates;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.model.dataformat.JacksonXMLDataFormat;

@UtilityClass
public class RouteBuilderCommonUtil
{
	public static final String NUMBER_OF_ITEMS = "NumberOfItems";

	public static final String FEEDBACK_ROUTE = "feedback";

	@NonNull
	public JacksonDataFormat setupMetasfreshJSONFormat(
			@NonNull final CamelContext context,
			@NonNull final Class<?> unmarshalType)
	{
		final JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
		jacksonDataFormat.setCamelContext(context);
		jacksonDataFormat.setObjectMapper(ConfiguredJsonMapper.get());
		jacksonDataFormat.setUnmarshalType(unmarshalType);
		return jacksonDataFormat;
	}

	@NonNull
	public JacksonXMLDataFormat setupFileMakerFormat(@NonNull final CamelContext context)
	{
		context.getRegistry().bind("FMPXMLRESULT-XmlMapper", ConfiguredXmlMapper.get());
		final var jacksonXMLDataFormat = new JacksonXMLDataFormat();
		jacksonXMLDataFormat.setUnmarshalType(FMPXMLRESULT.class);
		jacksonXMLDataFormat.setXmlMapper("FMPXMLRESULT-XmlMapper");
		return jacksonXMLDataFormat;
	}

	public void setupProperties(@NonNull final CamelContext context)
	{
		final PropertiesComponent pc = new PropertiesComponent();
		pc.setLocation("classpath:shipping.properties");
		context.setPropertiesComponent(pc);
	}

	public void setupFeedbackRoute(
			@NonNull final EndpointRouteBuilder routeBuilder,
			@NonNull final JacksonDataFormat jacksonDataFormat,
			@NonNull final String apiPath)
	{
		routeBuilder
				.from(routeBuilder.direct(FEEDBACK_ROUTE))
				.routeId("Candidate-Feedback-TO-MF")
				.log(LoggingLevel.INFO, "Reporting outcome to metasfresh")
				.process(new FeedbackProzessor())
				.marshal(jacksonDataFormat)
				.setHeader(Exchange.HTTP_METHOD, routeBuilder.constant(HttpEndpointBuilderFactory.HttpMethods.POST))
				.to(routeBuilder.http("{{metasfresh.api.baseurl}}" + apiPath));
	}
}
