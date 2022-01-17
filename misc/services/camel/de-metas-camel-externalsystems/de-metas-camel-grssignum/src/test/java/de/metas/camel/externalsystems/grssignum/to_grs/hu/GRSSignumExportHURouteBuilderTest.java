/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.to_grs.hu;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.v2.RetrieveHUCamelRequest;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonHUUpdate;
import de.metas.camel.externalsystems.grssignum.to_grs.client.GRSSignumDispatcherRouteBuilder;
import de.metas.camel.externalsystems.grssignum.to_grs.client.model.DispatchRequest;
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

import static de.metas.camel.externalsystems.grssignum.from_grs.hu.UpdateHURouteBuilder.UPDATE_HU_ROUTE_ID;
import static de.metas.camel.externalsystems.grssignum.to_grs.hu.GRSSignumExportHURouteBuilder.EXPORT_HU_ROUTE_ID;
import static de.metas.camel.externalsystems.grssignum.to_grs.hu.GRSSignumExportHURouteBuilder.PREPARE_JSON_HU_UPDATE_PROCESSOR_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GRSSignumExportHURouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_HU_RETRIEVE_ENDPOINT = "mock:HURetrieveEndpoint";
	private static final String MOCK_GRSSIGNUM_DISPATCHER_ENDPOINT = "mock:grsSignumDispatcherEndpoint";
	private static final String MOCK_UPDATE_HU_ROUTE_ID = "mock:updateHU";

	private static final String JSON_EXTERNAL_SYSTEM_REQUEST = "0_JsonExternalSystemRequest.json";
	private static final String JSON_RETRIEVE_HU_CAMEL_REQUEST = "10_RetrieveHUCamelRequest.json";
	private static final String JSON_GET_HU_RESPONSE = "20_JsonGetSingleHUResponse.json";
	private static final String JSON_DISPATCH_REQUEST = "30_DispatchRequest.json";
	private static final String JSON_HU_UPDATE_REQUEST = "40_JsonHUUpdateRequest.json";

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new GRSSignumExportHURouteBuilder();
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final Properties properties = new Properties();
		try
		{
			properties.load(GRSSignumExportHURouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
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

		final MockGetHUProcessor mockGetHUProcessor = new MockGetHUProcessor();
		final MockUpdateHUProcessor mockUpdateHUProcessor = new MockUpdateHUProcessor();

		prepareRouteForTesting(mockGetHUProcessor, mockUpdateHUProcessor);

		context.start();

		// validate the RetrieveHUCamelRequest
		final InputStream expectedRetrieveHUCamelRequestIS = this.getClass().getResourceAsStream(JSON_RETRIEVE_HU_CAMEL_REQUEST);
		final MockEndpoint getHUMockEndpoint = getMockEndpoint(MOCK_HU_RETRIEVE_ENDPOINT);
		getHUMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedRetrieveHUCamelRequestIS, RetrieveHUCamelRequest.class));

		// validate the DispatchRequest
		final InputStream expectedDispatchRequestIS = this.getClass().getResourceAsStream(JSON_DISPATCH_REQUEST);
		final MockEndpoint grsSignumDispatcherMockEndpoint = getMockEndpoint(MOCK_GRSSIGNUM_DISPATCHER_ENDPOINT);
		grsSignumDispatcherMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedDispatchRequestIS, DispatchRequest.class));

		// validate the JsonHUUpdate
		final InputStream expectedJsonHUUpdateIS = this.getClass().getResourceAsStream(JSON_HU_UPDATE_REQUEST);
		final MockEndpoint grsUpdateHUMockEndpoint = getMockEndpoint(MOCK_UPDATE_HU_ROUTE_ID);
		grsUpdateHUMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedJsonHUUpdateIS, JsonHUUpdate.class));

		//input request
		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		//when
		template.sendBody("direct:" + EXPORT_HU_ROUTE_ID, invokeExternalSystemRequest);

		//then
		assertThat(mockGetHUProcessor.called).isEqualTo(1);
		assertThat(mockUpdateHUProcessor.called).isEqualTo(1);
		assertMockEndpointsSatisfied();
	}

	private void prepareRouteForTesting(
			@NonNull final MockGetHUProcessor mockGetHUProcessor,
			@NonNull final MockUpdateHUProcessor mockUpdateHUProcessor) throws Exception
	{
		AdviceWith.adviceWith(context, EXPORT_HU_ROUTE_ID,
							  advice -> {
								  advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_RETRIEVE_HU_V2_CAMEL_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_HU_RETRIEVE_ENDPOINT)
										  .process(mockGetHUProcessor);

								  advice.interceptSendToEndpoint("direct:" + GRSSignumDispatcherRouteBuilder.GRS_DISPATCHER_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_GRSSIGNUM_DISPATCHER_ENDPOINT);

								  advice.weaveById(PREPARE_JSON_HU_UPDATE_PROCESSOR_ID)
										  .after()
										  .to(MOCK_UPDATE_HU_ROUTE_ID);

								  advice.interceptSendToEndpoint("direct:" + UPDATE_HU_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .process(mockUpdateHUProcessor);
							  });
	}

	private static class MockGetHUProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;

			final InputStream jsonHUToExport = GRSSignumExportHURouteBuilderTest.class.getResourceAsStream(JSON_GET_HU_RESPONSE);
			exchange.getIn().setBody(jsonHUToExport);
		}
	}

	private static class MockUpdateHUProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}
}
