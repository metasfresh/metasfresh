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

package de.metas.camel.shipping.receipt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.common.receipt.JsonCreateReceiptsRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static de.metas.camel.shipping.RouteBuilderCommonUtil.NUMBER_OF_ITEMS;
import static de.metas.camel.shipping.receipt.SiroReceiptConstants.CREATE_RECEIPT_MF_URL;
import static de.metas.camel.shipping.receipt.SiroReceiptConstants.LOCAL_STORAGE_URL;
import static de.metas.camel.shipping.receipt.SiroReceiptConstants.RECEIPT_XML_TO_JSON_PROCESSOR;
import static org.assertj.core.api.Assertions.assertThat;

public class ReceiptXmlToJsonRouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_FROM_ENDPOINT = "direct:mockInput";
	private static final String MOCK_XML_TO_JSON_ENDPOINT = "mock:xmlToJsonResult";
	private static final String CREATE_RECEIPT_VALID_XML_RESOURCE_PATH = "/de/metas/camel/shipping/receipt/SiroCreateReceipts.xml";
	private static final String CREATE_RECEIPT_EMPTY_XML_RESOURCE_PATH = "/de/metas/camel/shipping/receipt/SiroCreateReceipts_Empty.xml";
	private static final String CREATE_RECEIPT_RESPONSE_JSON_RESOURCE_PATH = "/de/metas/camel/shipping/receipt/JsonCreateReceiptsResponse.json";
	private static final String CREATE_RECEIPT_REQUEST_JSON_RESOURCE_PATH = "/de/metas/camel/shipping/receipt/JsonCreateReceiptsRequest.json";

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new ReceiptXmlToJsonRouteBuilder();
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Test
	void validFile_test() throws Exception
	{
		final MockEmptyProcessor localStorageEndpoint = new MockEmptyProcessor();
		final MockSuccessfullyCreatedReceiptProcessor createReceiptEndpoint = new MockSuccessfullyCreatedReceiptProcessor();

		prepareRouteForTesting(localStorageEndpoint, createReceiptEndpoint);

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		final InputStream expectedCreateReceiptRequest = this.getClass().getResourceAsStream(CREATE_RECEIPT_REQUEST_JSON_RESOURCE_PATH);

		final MockEndpoint mock = getMockEndpoint(MOCK_XML_TO_JSON_ENDPOINT);
		mock.expectedBodiesReceived(objectMapper.readValue(expectedCreateReceiptRequest, JsonCreateReceiptsRequest.class));

		final InputStream createReceiptFile = this.getClass().getResourceAsStream(CREATE_RECEIPT_VALID_XML_RESOURCE_PATH);

		template.sendBody(MOCK_FROM_ENDPOINT,createReceiptFile);

		assertThat(localStorageEndpoint.called).isEqualTo(1);
		assertThat(createReceiptEndpoint.called).isEqualTo(1);

		assertMockEndpointsSatisfied();
	}

	@Test
	void emptyFile_test() throws Exception
	{
		final MockEmptyProcessor localStorageEndpoint = new MockEmptyProcessor();
		final MockSuccessfullyCreatedReceiptProcessor createReceiptEndpoint = new MockSuccessfullyCreatedReceiptProcessor();

		prepareRouteForTesting(localStorageEndpoint, createReceiptEndpoint);

		context.start();

		final MockEndpoint mock = getMockEndpoint(MOCK_XML_TO_JSON_ENDPOINT);
		mock.expectedHeaderReceived(NUMBER_OF_ITEMS, 0);

		final InputStream createReceiptFile = this.getClass().getResourceAsStream(CREATE_RECEIPT_EMPTY_XML_RESOURCE_PATH);

		template.sendBody(MOCK_FROM_ENDPOINT,createReceiptFile);

		assertThat(localStorageEndpoint.called).isEqualTo(1);
		assertThat(createReceiptEndpoint.called).isEqualTo(0);

		assertMockEndpointsSatisfied();
	}

	private void prepareRouteForTesting(final MockEmptyProcessor localStorageEndpoint,
			final MockSuccessfullyCreatedReceiptProcessor createReceiptEndpoint) throws Exception
	{
		AdviceWithRouteBuilder.adviceWith(context, ReceiptXmlToJsonRouteBuilder.MF_RECEIPT_FILEMAKER_XML_TO_JSON,
				advice -> {
					advice.replaceFromWith(MOCK_FROM_ENDPOINT);

					advice.interceptSendToEndpoint(LOCAL_STORAGE_URL)
							.skipSendToOriginalEndpoint()
							.process(localStorageEndpoint);

					advice.weaveById(RECEIPT_XML_TO_JSON_PROCESSOR)
							.after()
							.to(MOCK_XML_TO_JSON_ENDPOINT);

					advice.interceptSendToEndpoint("http://" + CREATE_RECEIPT_MF_URL)
							.skipSendToOriginalEndpoint()
							.process(createReceiptEndpoint);
				});
	}

	private static class MockEmptyProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	private static class MockSuccessfullyCreatedReceiptProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			exchange.getIn().setBody(this.getClass().getResourceAsStream(CREATE_RECEIPT_RESPONSE_JSON_RESOURCE_PATH));

			called++;
		}
	}
}
