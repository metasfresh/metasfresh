/*
 * #%L
 * de-metas-camel-leichundmehl
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.JsonPPOrder;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.tcp.DispatchMessageRequest;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.tcp.TCPConnection;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_RETRIEVE_PP_ORDER_V2_CAMEL_ROUTE_ID;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder.LeichUndMehlExportPPOrderRouteBuilder.EXPORT_PPORDER_ROUTE_ID;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.tcp.SendToTCPRouteBuilder.SEND_TO_TCP_ROUTE_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LeichUndMehlExportPPOrderRouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_RETRIEVE_PP_ORDER_ENDPOINT = "mock:RetrievePPOrderEndpoint";
	private static final String MOCK_TCP_ENDPOINT = "mock:TCPEndpoint";

	private static final String JSON_EXTERNAL_SYSTEM_REQUEST = "0_JsonExternalSystemRequest.json";
	private static final String JSON_MANUFACTURING_ORDER_RESPONSE = "10_JsonResponseManufacturingOrder.json";
	private static final String JSON_DISPATCH_MESSAGE_REQUEST = "50_DispatchMessageRequest_Payload.json";

	private static final Integer ppOrderMetasfreshId = 11111;

	private static final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new LeichUndMehlExportPPOrderRouteBuilderUnused();
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final Properties properties = new Properties();
		try
		{
			properties.load(LeichUndMehlExportPPOrderRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
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
		final MockRetrievePPOrderProcessor mockRetrievePPOrderProcessor = new MockRetrievePPOrderProcessor();
		final MockTCPProcessor mockTCPProcessor = new MockTCPProcessor();

		prepareRouteForTesting(mockRetrievePPOrderProcessor, mockTCPProcessor);

		context.start();

		final MockEndpoint retrievePPOrderMockEndpoint = getMockEndpoint(MOCK_RETRIEVE_PP_ORDER_ENDPOINT);
		retrievePPOrderMockEndpoint.expectedBodiesReceived(ppOrderMetasfreshId);

		//input request
		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		//when
		template.sendBody("direct:" + EXPORT_PPORDER_ROUTE_ID, invokeExternalSystemRequest);

		//then
		assertThat(mockRetrievePPOrderProcessor.called).isEqualTo(1);
		assertThat(mockTCPProcessor.called).isEqualTo(1);
		assertMockEndpointsSatisfied();
	}

	private void prepareRouteForTesting(
			@NonNull final MockRetrievePPOrderProcessor mockRetrievePPOrderProcessor,
			@NonNull final LeichUndMehlExportPPOrderRouteBuilderTest.MockTCPProcessor mockTCPProcessor) throws Exception
	{
		AdviceWith.adviceWith(context, EXPORT_PPORDER_ROUTE_ID,
							  advice -> {
								  advice.interceptSendToEndpoint("direct:" + MF_RETRIEVE_PP_ORDER_V2_CAMEL_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_RETRIEVE_PP_ORDER_ENDPOINT)
										  .process(mockRetrievePPOrderProcessor);

								  advice.interceptSendToEndpoint("direct:" + SEND_TO_TCP_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_TCP_ENDPOINT)
										  .process(mockTCPProcessor);
							  });
	}

	private static class MockRetrievePPOrderProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
			final InputStream expectedJsonResponseManufacturingOrderIS = this.getClass().getResourceAsStream(JSON_MANUFACTURING_ORDER_RESPONSE);
			exchange.getIn().setBody(expectedJsonResponseManufacturingOrderIS);
		}
	}

	private static class MockTCPProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange) throws IOException
		{
			final DispatchMessageRequest request = exchange.getIn().getBody(DispatchMessageRequest.class);
			final JsonPPOrder jsonPPOrder = (JsonPPOrder)request.getTcpPayload();

			final InputStream expectedDispatchMessageRequestIS = this.getClass().getResourceAsStream(JSON_DISPATCH_MESSAGE_REQUEST);
			final JsonPPOrder expectedJson = objectMapper.readValue(expectedDispatchMessageRequestIS, JsonPPOrder.class);

			assertThat(jsonPPOrder).isEqualTo(expectedJson);

			final InputStream externalSystemRequestIS = this.getClass().getResourceAsStream(JSON_EXTERNAL_SYSTEM_REQUEST);
			final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper.readValue(externalSystemRequestIS, JsonExternalSystemRequest.class);
			final Map<String, String> params = invokeExternalSystemRequest.getParameters();

			final TCPConnection tcpCredentials = request.getTcpConnection(); //todo fp

			called++;
		}
	}
}
