/*
 * #%L
 * de-metas-camel-rabbitmq
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

package de.metas.camel.externalsystems.rabbitmq.bpartner;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.v2.BPRetrieveCamelRequest;
import de.metas.camel.externalsystems.rabbitmq.api.DispatchMessageRequest;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static de.metas.camel.externalsystems.rabbitmq.RabbitMQDispatcherRouteBuilder.RABBITMQ_DISPATCHER_ROUTE_ID;
import static de.metas.camel.externalsystems.rabbitmq.bpartner.RabbitMQExportBPartnerRouteBuilder.EXPORT_BPARTNER_ROUTE_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RabbitMQExportBPartnerRouteBuilderTests extends CamelTestSupport
{
	private static final String MOCK_BPARTNER_RETRIEVE_ENDPOINT = "mock:bPartnerRetrieveEndpoint";
	private static final String MOCK_RABBITMQ_DISPATCHER_ENDPOINT = "mock:rabbitMQDispatcherEndpoint";

	private static final String JSON_EXTERNAL_SYSTEM_REQUEST = "0_JsonExternalSystemRequest.json";
	private static final String JSON_RETRIEVE_BPARTNER_CAMEL_REQUEST = "10_BPRetrieveCamelRequest.json";
	private static final String JSON_RETRIEVE_BPARTNER_RESPONSE = "20_JsonResponseComposite.json";
	private static final String JSON_DISPATCH_MESSAGE_REQUEST = "30_DispatchMessageRequest.json";

	@Test
	public void happyFlow() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final MockRetrieveBPartnerProcessor mockRetrieveBPartnerProcessor = new MockRetrieveBPartnerProcessor();

		prepareRouteForTesting(mockRetrieveBPartnerProcessor);

		context.start();

		// validate the BPRetrieveCamelRequest
		final InputStream retrieveBPartnerRequestIS = this.getClass().getResourceAsStream(JSON_RETRIEVE_BPARTNER_CAMEL_REQUEST);
		final MockEndpoint bPartnerMockEndpoint = getMockEndpoint(MOCK_BPARTNER_RETRIEVE_ENDPOINT);
		bPartnerMockEndpoint.expectedBodiesReceived(objectMapper.readValue(retrieveBPartnerRequestIS, BPRetrieveCamelRequest.class));

		// validate the DispatchMessageRequest
		final InputStream dispatchMessageRequestIS = this.getClass().getResourceAsStream(JSON_DISPATCH_MESSAGE_REQUEST);
		final MockEndpoint rabbitMQDispatcherMockEndpoint = getMockEndpoint(MOCK_RABBITMQ_DISPATCHER_ENDPOINT);
		rabbitMQDispatcherMockEndpoint.expectedBodiesReceived(objectMapper.readValue(dispatchMessageRequestIS, DispatchMessageRequest.class));

		//input request
		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		//when
		template.sendBody("direct:" + EXPORT_BPARTNER_ROUTE_ID, invokeExternalSystemRequest);

		//then
		assertThat(mockRetrieveBPartnerProcessor.called).isEqualTo(1);
		assertMockEndpointsSatisfied();
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new RabbitMQExportBPartnerRouteBuilder();
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final Properties properties = new Properties();
		try
		{
			properties.load(RabbitMQExportBPartnerRouteBuilderTests.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private void prepareRouteForTesting(
			final MockRetrieveBPartnerProcessor retrieveBPartnerProcessor) throws Exception
	{
		AdviceWith.adviceWith(context, EXPORT_BPARTNER_ROUTE_ID,
							  advice -> {
								  advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_RETRIEVE_BPARTNER_V2_CAMEL_URI + "}}")
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_BPARTNER_RETRIEVE_ENDPOINT)
										  .process(retrieveBPartnerProcessor);

								  advice.interceptSendToEndpoint("direct:" + RABBITMQ_DISPATCHER_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_RABBITMQ_DISPATCHER_ENDPOINT);
							  });
	}

	private static class MockRetrieveBPartnerProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;

			final InputStream jsonResponseComposite = RabbitMQExportBPartnerRouteBuilderTests.class.getResourceAsStream(JSON_RETRIEVE_BPARTNER_RESPONSE);
			exchange.getIn().setBody(jsonResponseComposite);
		}
	}
}
