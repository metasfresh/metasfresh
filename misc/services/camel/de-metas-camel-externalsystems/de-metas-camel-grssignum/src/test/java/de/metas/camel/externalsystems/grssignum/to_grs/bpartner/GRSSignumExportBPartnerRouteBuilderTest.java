/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.to_grs.bpartner;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.v2.BPRetrieveCamelRequest;
import de.metas.camel.externalsystems.grssignum.to_grs.client.model.DispatchRequest;
import de.metas.camel.externalsystems.grssignum.to_grs.client.GRSSignumDispatcherRouteBuilder;
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
import java.util.Properties;

import static de.metas.camel.externalsystems.grssignum.to_grs.bpartner.GRSSignumExportBPartnerRouteBuilder.EXPORT_BPARTNER_ROUTE_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GRSSignumExportBPartnerRouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_BPARTNER_RETRIEVE_ENDPOINT = "mock:bPartnerRetrieveEndpoint";
	private static final String MOCK_GRSSIGNUM_DISPATCHER_ENDPOINT = "mock:grsSignumDispatcherEndpoint";

	private static final String JSON_EXTERNAL_SYSTEM_REQUEST = "0_JsonExternalSystemRequest.json";
	private static final String JSON_RETRIEVE_BPARTNER_CAMEL_REQUEST = "10_BPRetrieveCamelRequest.json";
	private static final String JSON_RETRIEVE_BPARTNER_RESPONSE = "20_JsonResponseComposite.json";
	private static final String JSON_DISPATCH_REQUEST = "30_DispatchRequest.json";

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new GRSSignumExportBPartnerRouteBuilder();
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final Properties properties = new Properties();
		try
		{
			properties.load(GRSSignumExportBPartnerRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
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

		final MockRetrieveBPartnerProcessor mockRetrieveBPartnerProcessor = new MockRetrieveBPartnerProcessor();

		prepareRouteForTesting(mockRetrieveBPartnerProcessor);

		context.start();

		// validate the BPRetrieveCamelRequest
		final InputStream expectedRetrieveBPartnerRequestIS = this.getClass().getResourceAsStream(JSON_RETRIEVE_BPARTNER_CAMEL_REQUEST);
		final MockEndpoint bPartnerMockEndpoint = getMockEndpoint(MOCK_BPARTNER_RETRIEVE_ENDPOINT);
		bPartnerMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedRetrieveBPartnerRequestIS, BPRetrieveCamelRequest.class));

		// validate the DispatchRequest
		final InputStream expectedDispatchRequestIS = this.getClass().getResourceAsStream(JSON_DISPATCH_REQUEST);
		final MockEndpoint grsSignumDispatcherMockEndpoint = getMockEndpoint(MOCK_GRSSIGNUM_DISPATCHER_ENDPOINT);
		grsSignumDispatcherMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedDispatchRequestIS, DispatchRequest.class));

		//input request
		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		//when
		template.sendBody("direct:" + EXPORT_BPARTNER_ROUTE_ID, invokeExternalSystemRequest);

		//then
		assertThat(mockRetrieveBPartnerProcessor.called).isEqualTo(1);
		assertMockEndpointsSatisfied();
	}

	private void prepareRouteForTesting(
			@NonNull final MockRetrieveBPartnerProcessor retrieveBPartnerProcessor) throws Exception
	{
		AdviceWith.adviceWith(context, EXPORT_BPARTNER_ROUTE_ID,
							  advice -> {
								  advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_RETRIEVE_BPARTNER_V2_CAMEL_URI + "}}")
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_BPARTNER_RETRIEVE_ENDPOINT)
										  .process(retrieveBPartnerProcessor);

								  advice.interceptSendToEndpoint("direct:" + GRSSignumDispatcherRouteBuilder.GRS_DISPATCHER_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_GRSSIGNUM_DISPATCHER_ENDPOINT);
							  });
	}

	private static class MockRetrieveBPartnerProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;

			final InputStream jsonResponseComposite = GRSSignumExportBPartnerRouteBuilderTest.class.getResourceAsStream(JSON_RETRIEVE_BPARTNER_RESPONSE);
			exchange.getIn().setBody(jsonResponseComposite);
		}
	}
}
