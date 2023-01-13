/*
 * #%L
 * de-metas-camel-sap-file-import
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.camel.externalsystems.sap.bpartner;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.v2.BPRetrieveCamelRequest;
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
import java.util.Properties;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.ERROR_WRITE_TO_ADISSUE;
import static de.metas.camel.externalsystems.sap.bpartner.ProcessSkippedBPartnerRouteBuilder.PROCESS_SKIPPED_BPARTNER_ROUTE;
import static de.metas.camel.externalsystems.sap.bpartner.ProcessSkippedBPartnerRouteBuilder.PROCESS_SKIPPED_BPARTNER_ROUTE_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProcessSkippedBPartnerRouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_BPARTNER_RETRIEVE_ENDPOINT = "mock:bPartnerRetrieveEndpoint";

	private static final String JSON_PROCESS_SKIPPED_BPARTNER_REQUEST = "/de/metas/camel/externalsystems/sap/bpartner/processskippedbpartner/10_ProcessSkippedBPartnerRequest.json";
	private static final String JSON_RETRIEVE_BPARTNER_CAMEL_REQUEST = "/de/metas/camel/externalsystems/sap/bpartner/processskippedbpartner/20_BPRetrieveCamelRequest.json";
	private static final String JSON_RETRIEVE_ACTIVE_BPARTNER_RESPONSE = "/de/metas/camel/externalsystems/sap/bpartner/processskippedbpartner/30_JsonResponseCompositeActiveBPartner.json";
	private static final String JSON_RETRIEVE_DISABLED_BPARTNER_RESPONSE = "/de/metas/camel/externalsystems/sap/bpartner/processskippedbpartner/31_JsonResponseCompositeDisabledBPartner.json";

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new ProcessSkippedBPartnerRouteBuilder();
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(ProcessSkippedBPartnerRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	public void givenSkippedBPartner_whenRetrieveBPartnerFromMF_andBPartnerActive_thenWriteToAdIssueIsCalled() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final InputStream jsonResponseCompositeIS = ProcessSkippedBPartnerRouteBuilderTest.class.getResourceAsStream(JSON_RETRIEVE_ACTIVE_BPARTNER_RESPONSE);
		final MockRetrieveBPartnerProcessor mockRetrieveBPartnerProcessor = new MockRetrieveBPartnerProcessor(jsonResponseCompositeIS);
		final MockWriteToAdIssueProcessor mockWriteToAdIssueProcessor = new MockWriteToAdIssueProcessor();

		prepareRouteForTesting(mockRetrieveBPartnerProcessor, mockWriteToAdIssueProcessor);

		context.start();

		final InputStream expectedRetrieveBPartnerRequestIS = this.getClass().getResourceAsStream(JSON_RETRIEVE_BPARTNER_CAMEL_REQUEST);
		final MockEndpoint bPartnerMockEndpoint = getMockEndpoint(MOCK_BPARTNER_RETRIEVE_ENDPOINT);
		bPartnerMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedRetrieveBPartnerRequestIS, BPRetrieveCamelRequest.class));

		final InputStream processSkippedBPartnerRequestIS = this.getClass().getResourceAsStream(JSON_PROCESS_SKIPPED_BPARTNER_REQUEST);
		final ProcessSkippedBPartnerRequest processSkippedBPartnerRequest = objectMapper.readValue(processSkippedBPartnerRequestIS, ProcessSkippedBPartnerRequest.class);

		template.sendBody("direct:" + PROCESS_SKIPPED_BPARTNER_ROUTE, processSkippedBPartnerRequest);

		assertThat(mockRetrieveBPartnerProcessor.called).isEqualTo(1);
		assertThat(mockWriteToAdIssueProcessor.called).isEqualTo(1);
		assertMockEndpointsSatisfied();
	}

	@Test
	public void givenSkippedBPartner_whenRetrieveBPartnerFromMF_andBPartnerDisabled_thenWriteToAdIssueIsNotCalled() throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final InputStream jsonResponseCompositeIS = ProcessSkippedBPartnerRouteBuilderTest.class.getResourceAsStream(JSON_RETRIEVE_DISABLED_BPARTNER_RESPONSE);
		final MockRetrieveBPartnerProcessor mockRetrieveBPartnerProcessor = new MockRetrieveBPartnerProcessor(jsonResponseCompositeIS);

		final MockWriteToAdIssueProcessor mockWriteToAdIssueProcessor = new MockWriteToAdIssueProcessor();

		prepareRouteForTesting(mockRetrieveBPartnerProcessor, mockWriteToAdIssueProcessor);

		context.start();

		final InputStream expectedRetrieveBPartnerRequestIS = this.getClass().getResourceAsStream(JSON_RETRIEVE_BPARTNER_CAMEL_REQUEST);
		final MockEndpoint bPartnerMockEndpoint = getMockEndpoint(MOCK_BPARTNER_RETRIEVE_ENDPOINT);
		bPartnerMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedRetrieveBPartnerRequestIS, BPRetrieveCamelRequest.class));

		final InputStream processSkippedBPartnerRequestIS = this.getClass().getResourceAsStream(JSON_PROCESS_SKIPPED_BPARTNER_REQUEST);
		final ProcessSkippedBPartnerRequest processSkippedBPartnerRequest = objectMapper.readValue(processSkippedBPartnerRequestIS, ProcessSkippedBPartnerRequest.class);

		template.sendBody("direct:" + PROCESS_SKIPPED_BPARTNER_ROUTE, processSkippedBPartnerRequest);

		assertThat(mockRetrieveBPartnerProcessor.called).isEqualTo(1);
		assertThat(mockWriteToAdIssueProcessor.called).isEqualTo(0);
		assertMockEndpointsSatisfied();
	}

	private void prepareRouteForTesting(
			@NonNull final MockRetrieveBPartnerProcessor mockRetrieveBPartnerProcessor,
			@NonNull final MockWriteToAdIssueProcessor mockWriteToAdIssueProcessor) throws Exception
	{
		AdviceWith.adviceWith(context, PROCESS_SKIPPED_BPARTNER_ROUTE_ID,
							  advice -> {
								  advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_RETRIEVE_BPARTNER_V2_CAMEL_URI + "}}")
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_BPARTNER_RETRIEVE_ENDPOINT)
										  .process(mockRetrieveBPartnerProcessor);

								  advice.interceptSendToEndpoint("direct:" + ERROR_WRITE_TO_ADISSUE)
										  .skipSendToOriginalEndpoint()
										  .process(mockWriteToAdIssueProcessor);
							  });

	}

	private static class MockRetrieveBPartnerProcessor implements Processor
	{
		private int called = 0;

		private final InputStream jsonResponseCompositeIS;

		private MockRetrieveBPartnerProcessor(@NonNull final InputStream jsonResponseCompositeIS)
		{
			this.jsonResponseCompositeIS = jsonResponseCompositeIS;
		}

		@Override
		public void process(final Exchange exchange)
		{
			called++;

			exchange.getIn().setBody(jsonResponseCompositeIS);
		}
	}

	private static class MockWriteToAdIssueProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}
}
