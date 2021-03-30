/*
 * #%L
 * de-metas-camel-shopware6
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.shopware6;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.shopware6.processor.CreateBPartnerESRQueryProcessor;
import de.metas.camel.externalsystems.shopware6.processor.CreateBPartnerUpsertReqProcessor;
import de.metas.camel.externalsystems.shopware6.processor.GetOrdersProcessor;
import de.metas.camel.externalsystems.shopware6.processor.ProcessorHelper;
import de.metas.common.externalreference.JsonExternalReferenceLookupResponse;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.StaticEndpointBuilders;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;

@Component
public class GetOrdersRouteBuilder extends RouteBuilder
{
	public static final String GET_ORDERS_ROUTE_ID = "Shopware6-getOrders";
	public static final String PROCESS_ORDER_ROUTE_ID = "Shopware6-processOrder";

	public static final String GET_ORDERS_PROCESSOR_ID = "SW6Orders-GetOrdersProcessorId";
	public static final String CREATE_ESR_QUERY_REQ_PROCESSOR_ID = "SW6Orders-CreateBPartnerESRQueryProcessorId";
	public static final String CREATE_BPARTNER_UPSERT_REQ_PROCESSOR_ID = "SW6Orders-CreateBPartnerUpsertReqProcessorId";

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(StaticEndpointBuilders.direct(MF_ERROR_ROUTE_ID));

		//@formatter:off
		from(StaticEndpointBuilders.direct(GET_ORDERS_ROUTE_ID))
				.routeId(GET_ORDERS_ROUTE_ID)
				.log("Route invoked")
				.streamCaching()
				.process(new GetOrdersProcessor()).id(GET_ORDERS_PROCESSOR_ID)
				.split(body())
					.to(StaticEndpointBuilders.direct(PROCESS_ORDER_ROUTE_ID))
				.end()
				.process((exchange) -> ProcessorHelper.logProcessMessage(exchange, "Shopware6:GetOrders process ended!" + Instant.now(),
																		 exchange.getIn().getHeader(HEADER_PINSTANCE_ID, Integer.class)));

		from(StaticEndpointBuilders.direct(PROCESS_ORDER_ROUTE_ID))
				.routeId(PROCESS_ORDER_ROUTE_ID)
				.log("Route invoked")
				.process(new CreateBPartnerESRQueryProcessor()).id(CREATE_ESR_QUERY_REQ_PROCESSOR_ID)

				.log(LoggingLevel.DEBUG, "Calling metasfresh-api to query ESR records: ${body}")
				.to("{{" + ExternalSystemCamelConstants.MF_LOOKUP_EXTERNALREFERENCE_CAMEL_URI + "}}")

				.unmarshal(setupJacksonDataFormatFor(getContext(), JsonExternalReferenceLookupResponse.class))
				.log(LoggingLevel.DEBUG, "Metasfresh ESR API responded with: ${body}")
				.process(new CreateBPartnerUpsertReqProcessor()).id(CREATE_BPARTNER_UPSERT_REQ_PROCESSOR_ID)

				.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert BPartners: ${body}")
				.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_CAMEL_URI + "}}");
		//@formatter:on
	}

	@NonNull
	private JacksonDataFormat setupJacksonDataFormatFor(
			@NonNull final CamelContext context,
			@NonNull final Class<?> unmarshalType)
	{
		final ObjectMapper objectMapper = (new ObjectMapper())
				.findAndRegisterModules()
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.enable(MapperFeature.USE_ANNOTATIONS);

		final JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
		jacksonDataFormat.setCamelContext(context);
		jacksonDataFormat.setObjectMapper(objectMapper);
		jacksonDataFormat.setUnmarshalType(unmarshalType);
		return jacksonDataFormat;
	}
}
