/*
 * #%L
 * de-metas-camel-rabbitmq
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.rabbitmq.externalreference;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.v2.ExternalReferenceLookupCamelRequest;
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

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_LOOKUP_EXTERNALREFERENCE_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.rabbitmq.RabbitMQDispatcherRouteBuilder.RABBITMQ_DISPATCHER_ROUTE_ID;
import static de.metas.camel.externalsystems.rabbitmq.externalreference.RabbitMQExportExternalReferenceRouteBuilder.EXPORT_EXTERNAL_REFERENCE_ROUTE_ID;
import static org.assertj.core.api.Assertions.*;

public class RabbitMQExportExternalReferenceRouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_EXTERNAL_REFERENCE_RETRIEVE_ENDPOINT = "mock:externalReferenceRetrieveEndpoint";
	private static final String MOCK_RABBITMQ_DISPATCHER_ENDPOINT = "mock:rabbitMQDispatcherEndpoint";

	private static final String JSON_EXTERNAL_SYSTEM_REQUEST = "0_JsonExternalSystemRequest.json";
	private static final String JSON_RETRIEVE_EXTERNAL_REFERENCE_CAMEL_REQUEST = "10_ExternalReferenceLookupCamelRequest.json";
	private static final String JSON_RETRIEVE_EXTERNAL_REFERENCE_RESPONSE = "20_JsonExternalReferenceLookupResponse.json";
	private static final String JSON_DISPATCH_MESSAGE_REQUEST = "30_DispatchMessageRequest.json";

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new RabbitMQExportExternalReferenceRouteBuilder();
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final Properties properties = new Properties();
		try
		{
			properties.load(RabbitMQExportExternalReferenceRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	public void happyFlow() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final RabbitMQExportExternalReferenceRouteBuilderTest.MockRetrieveExternalReferenceProcessor mockRetrieveExternalReferenceProcessor = new RabbitMQExportExternalReferenceRouteBuilderTest.MockRetrieveExternalReferenceProcessor();

		prepareRouteForTesting(mockRetrieveExternalReferenceProcessor);

		context.start();

		// validate the ExternalReferenceLookupCamelRequest
		final InputStream externalReferenceLookupCamelRequestIS = this.getClass().getResourceAsStream(JSON_RETRIEVE_EXTERNAL_REFERENCE_CAMEL_REQUEST);
		final MockEndpoint externalReferenceMockEndpoint = getMockEndpoint(MOCK_EXTERNAL_REFERENCE_RETRIEVE_ENDPOINT);
		externalReferenceMockEndpoint.expectedBodiesReceived(objectMapper.readValue(externalReferenceLookupCamelRequestIS, ExternalReferenceLookupCamelRequest.class));

		// validate the DispatchMessageRequest
		final InputStream dispatchMessageRequestIS = this.getClass().getResourceAsStream(JSON_DISPATCH_MESSAGE_REQUEST);
		final MockEndpoint rabbitMQDispatcherMockEndpoint = getMockEndpoint(MOCK_RABBITMQ_DISPATCHER_ENDPOINT);
		rabbitMQDispatcherMockEndpoint.expectedBodiesReceived(objectMapper.readValue(dispatchMessageRequestIS, DispatchMessageRequest.class));

		//input request
		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		//when
		template.sendBody("direct:" + EXPORT_EXTERNAL_REFERENCE_ROUTE_ID, invokeExternalSystemRequest);

		//then
		assertThat(mockRetrieveExternalReferenceProcessor.called).isEqualTo(1);
		assertMockEndpointsSatisfied();
	}

	private void prepareRouteForTesting(
			final RabbitMQExportExternalReferenceRouteBuilderTest.MockRetrieveExternalReferenceProcessor retrieveExternalReferenceProcessor) throws Exception
	{
		AdviceWith.adviceWith(context, EXPORT_EXTERNAL_REFERENCE_ROUTE_ID,
							  advice -> {
								  advice.interceptSendToEndpoint("direct:" + MF_LOOKUP_EXTERNALREFERENCE_V2_CAMEL_URI)
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_EXTERNAL_REFERENCE_RETRIEVE_ENDPOINT)
										  .process(retrieveExternalReferenceProcessor);

								  advice.interceptSendToEndpoint("direct:" + RABBITMQ_DISPATCHER_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_RABBITMQ_DISPATCHER_ENDPOINT);
							  });
	}

	private static class MockRetrieveExternalReferenceProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;

			final InputStream jsonExternalReferenceLookupResponse = RabbitMQExportExternalReferenceRouteBuilderTest.class.getResourceAsStream(JSON_RETRIEVE_EXTERNAL_REFERENCE_RESPONSE);
			exchange.getIn().setBody(jsonExternalReferenceLookupResponse);
		}
	}
}
