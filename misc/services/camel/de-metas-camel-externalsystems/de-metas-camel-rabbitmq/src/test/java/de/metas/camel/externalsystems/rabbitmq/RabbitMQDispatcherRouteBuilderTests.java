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

package de.metas.camel.externalsystems.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.rabbitmq.api.DispatchMessageRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.rabbitmq.RabbitMQDispatcherRouteBuilder.RABBITMQ_DEADLETTER_ROUTE_ID;
import static de.metas.camel.externalsystems.rabbitmq.RabbitMQDispatcherRouteBuilder.RABBITMQ_DISPATCHER_ROUTE_ID;
import static de.metas.camel.externalsystems.rabbitmq.RabbitMQDispatcherRouteBuilder.RABBITMQ_ENDPOINT_ID;
import static de.metas.camel.externalsystems.rabbitmq.RabbitMQDispatcherRouteBuilder.RABBITMQ_MESSAGE_SENDER;
import static de.metas.camel.externalsystems.rabbitmq.RabbitMQDispatcherRouteBuilder.RABBITMQ_PUBLISH_ENDPOINT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RabbitMQDispatcherRouteBuilderTests extends CamelTestSupport
{
	private static final String MOCK_RABBIT_MQ_ENDPOINT = "mock:rabbitMQEndpoint";

	private static final String DISPATCH_MESSAGE_REQUEST = "0_10_DispatchMessageRequest.json";
	private static final String JSON_RABBIT_MQ_HTTP_RESPONSE = "1_20_JsonRabbitMQHttpResponse.json";
	private static final String JSON_RABBIT_MQ_NEGATIVE_HTTP_RESPONSE = "2_20_JsonRabbitMQNegativeHttpResponse.json";

	private final static ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

	@Test
	public void happyFlow() throws Exception
	{
		final MockRabbitMQEndpointProcessor mockRabbitMQEndpointProcessor = new MockRabbitMQEndpointProcessor();

		prepareRouteForTestingHappyFlow(mockRabbitMQEndpointProcessor);

		context.start();

		// validate the JsonRabbitMQHttpMessage and the URL
		final InputStream dispatchMessageRequestIS = this.getClass().getResourceAsStream(DISPATCH_MESSAGE_REQUEST);
		final DispatchMessageRequest dispatchMessageRequest = objectMapper.readValue(dispatchMessageRequestIS, DispatchMessageRequest.class);

		final MockEndpoint rabbitMqEndpoint = getMockEndpoint(MOCK_RABBIT_MQ_ENDPOINT);
		rabbitMqEndpoint.expectedHeaderReceived(Exchange.HTTP_URI, dispatchMessageRequest.getUrl() + RABBITMQ_PUBLISH_ENDPOINT);
		rabbitMqEndpoint.expectedBodiesReceived(objectMapper.writeValueAsString(dispatchMessageRequest.getMessage()));

		//when
		template.sendBody("direct:" + RABBITMQ_DISPATCHER_ROUTE_ID, dispatchMessageRequest);

		//then
		assertThat(mockRabbitMQEndpointProcessor.called).isEqualTo(1);
		assertMockEndpointsSatisfied();
	}

	@Test
	public void messageNotRoutedFlow() throws Exception
	{
		final MockRabbitMQNegativeResponseProcessor mockRabbitMQNegativeResponseProcessor = new MockRabbitMQNegativeResponseProcessor();
		final MockErrorRouteProcessor mockErrorRouteProcessor = new MockErrorRouteProcessor();

		prepareRouteForTestingNotRoutedFlow(mockRabbitMQNegativeResponseProcessor, mockErrorRouteProcessor);

		context.start();

		final InputStream dispatchMessageRequestIS = this.getClass().getResourceAsStream(DISPATCH_MESSAGE_REQUEST);
		final DispatchMessageRequest dispatchMessageRequest = objectMapper.readValue(dispatchMessageRequestIS, DispatchMessageRequest.class);

		final MockEndpoint rabbitMqEndpoint = getMockEndpoint(MOCK_RABBIT_MQ_ENDPOINT);
		final String jsonHttpMessageRequest =  objectMapper.writeValueAsString(dispatchMessageRequest.getMessage());
		rabbitMqEndpoint.expectedBodiesReceived(jsonHttpMessageRequest, jsonHttpMessageRequest, jsonHttpMessageRequest);
		rabbitMqEndpoint.expectedHeaderReceived(Exchange.HTTP_URI, dispatchMessageRequest.getUrl() + RABBITMQ_PUBLISH_ENDPOINT);

		//when
		template.sendBody("direct:" + RABBITMQ_DISPATCHER_ROUTE_ID, dispatchMessageRequest);

		//then
		assertThat(mockRabbitMQNegativeResponseProcessor.called).isEqualTo(3);
		assertThat(mockErrorRouteProcessor.called).isEqualTo(1);
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
		return new RabbitMQDispatcherRouteBuilder();
	}

	private void prepareRouteForTestingHappyFlow(
			final MockRabbitMQEndpointProcessor rabbitMQResponseProcessor) throws Exception
	{
		AdviceWith.adviceWith(context, RABBITMQ_MESSAGE_SENDER,
							  advice -> advice.weaveById(RABBITMQ_ENDPOINT_ID)
									  .replace()
									  .to(MOCK_RABBIT_MQ_ENDPOINT)
									  .process(rabbitMQResponseProcessor));
	}

	private void prepareRouteForTestingNotRoutedFlow(
			final MockRabbitMQNegativeResponseProcessor rabbitMQNegativeResponseProcessor,
			final MockErrorRouteProcessor mockErrorRouteProcessor) throws Exception
	{
		AdviceWith.adviceWith(context, RABBITMQ_MESSAGE_SENDER,
							  advice -> advice.weaveById(RABBITMQ_ENDPOINT_ID)
									  .replace()
									  .to(MOCK_RABBIT_MQ_ENDPOINT)
									  .process(rabbitMQNegativeResponseProcessor));

		AdviceWith.adviceWith(context, RABBITMQ_DEADLETTER_ROUTE_ID,
							  advice -> advice.interceptSendToEndpoint("direct:" + MF_ERROR_ROUTE_ID)
									  .skipSendToOriginalEndpoint()
									  .process(mockErrorRouteProcessor));
	}

	private static class MockRabbitMQEndpointProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;

			final InputStream jsonRabbitMQHttpResponseIS = RabbitMQDispatcherRouteBuilder.class.getResourceAsStream(JSON_RABBIT_MQ_HTTP_RESPONSE);
			exchange.getIn().setBody(jsonRabbitMQHttpResponseIS);
		}
	}

	private static class MockRabbitMQNegativeResponseProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;

			final InputStream jsonRabbitMQHttpResponse = RabbitMQDispatcherRouteBuilder.class.getResourceAsStream(JSON_RABBIT_MQ_NEGATIVE_HTTP_RESPONSE);
			exchange.getIn().setBody(jsonRabbitMQHttpResponse);
		}
	}

	private static class MockErrorRouteProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}
}