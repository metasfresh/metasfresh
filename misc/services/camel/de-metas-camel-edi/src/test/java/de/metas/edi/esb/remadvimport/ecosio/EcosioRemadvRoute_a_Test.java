/*
 * #%L
 * de-metas-camel-edi
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
package de.metas.edi.esb.remadvimport.ecosio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.common.rest_api.v1.remittanceadvice.JsonCreateRemittanceAdviceRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.CREATE_REMADV_MF_URL;
import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.ECOSIO_REMADV_XML_TO_JSON_ROUTE;
import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.REMADV_XML_TO_JSON_PROCESSOR;
import static org.assertj.core.api.Assertions.*;

class EcosioRemadvRoute_a_Test extends CamelTestSupport
{
	private static final String MOCK_FROM_ENDPOINT = "direct:mockInput";
	private static final String MOCK_XML_TO_JSON_ENDPOINT = "mock:xmlToJsonResult";

	private static final String CREATE_REMADV_VALID_XML_RESOURCE_PATH = "/de/metas/edi/esb/remadvimport/ecosio/a_10_EcosioRemadvTestFile.xml";
	private static final String CREATE_REMADV_REQUEST_JSON_RESOURCE_PATH = "/de/metas/edi/esb/remadvimport/ecosio/a_20_JsonCreateREMADVRequest.json";
	private static final String CREATE_REMADV_RESPONSE_JSON_RESOURCE_PATH = "/de/metas/edi/esb/remadvimport/ecosio/a_30_JsonCreateREMADVResponse.json";

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(EcosioRemadvRoute_a_Test.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new EcosioRemadvRoute();
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Test
	void validFile_test() throws Exception
	{
		final MockSuccessfullyCreatedRemadvProcessor createRemadvEndpoint = new MockSuccessfullyCreatedRemadvProcessor();

		prepareRouteForTesting(createRemadvEndpoint);

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		final InputStream expectedCreateREMADVRequest = this.getClass().getResourceAsStream(CREATE_REMADV_REQUEST_JSON_RESOURCE_PATH);

		final MockEndpoint mock = getMockEndpoint(MOCK_XML_TO_JSON_ENDPOINT);
		mock.expectedBodiesReceived(objectMapper.readValue(expectedCreateREMADVRequest, JsonCreateRemittanceAdviceRequest.class));

		final InputStream createREMADVFile = this.getClass().getResourceAsStream(CREATE_REMADV_VALID_XML_RESOURCE_PATH);

		template.sendBodyAndHeader(MOCK_FROM_ENDPOINT, createREMADVFile, Exchange.FILE_NAME_ONLY, "a_10_EcosioRemadvTestFile.xml");

		assertThat(createRemadvEndpoint.called).isEqualTo(1);
		assertMockEndpointsSatisfied();
	}


	private void prepareRouteForTesting(final MockSuccessfullyCreatedRemadvProcessor createdRemadvProcessor) throws Exception
	{
		AdviceWithRouteBuilder.adviceWith(context, ECOSIO_REMADV_XML_TO_JSON_ROUTE,
										  advice -> {
											  advice.replaceFromWith(MOCK_FROM_ENDPOINT);

											  advice.weaveById(REMADV_XML_TO_JSON_PROCESSOR)
													  .after()
													  .to(MOCK_XML_TO_JSON_ENDPOINT);

											  advice.interceptSendToEndpoint("http://" + CREATE_REMADV_MF_URL)
													  .skipSendToOriginalEndpoint()
													  .process(createdRemadvProcessor);
										  });
	}

	private static class MockSuccessfullyCreatedRemadvProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			exchange.getIn().setBody(this.getClass().getResourceAsStream(CREATE_REMADV_RESPONSE_JSON_RESOURCE_PATH));

			called++;
		}
	}
}
