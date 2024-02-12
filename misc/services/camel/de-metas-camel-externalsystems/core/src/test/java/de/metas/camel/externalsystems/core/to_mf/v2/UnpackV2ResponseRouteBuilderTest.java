/*
 * #%L
 * de-metas-camel-externalsystems-core
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

package de.metas.camel.externalsystems.core.to_mf.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.converter.stream.InputStreamCache;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;
import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE_PROCESSOR_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UnpackV2ResponseRouteBuilderTest extends CamelTestSupport
{
	private final static String MOCK_UNPACK_V2_API_RESPONSE = "mock:UnpackV2ApiResponse";

	private final static String UnpackV2_JsonApiResponse = "10_UnpackV2_JsonApiResponse.json";
	private final static String UnpackedV2_Object = "10_UnpackedV2_Object.json";

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final Properties properties = new Properties();
		try
		{
			properties.load(BPartnerRouteBuilderV2Test.class.getClassLoader().getResourceAsStream("application.properties"));
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
		return new UnpackV2ResponseRouteBuilder();
	}

	@Test
	void givenInputJsonApiResponse_whenFireRoute_thenResponseIsUnpackedAccordingly() throws Exception
	{
		prepareRouteForTesting();

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		final MockEndpoint unpackV2ApiResponseEP = getMockEndpoint(MOCK_UNPACK_V2_API_RESPONSE);

		final InputStream responseItemsIS = this.getClass().getResourceAsStream(UnpackedV2_Object);
		final Object responseItems = objectMapper.readValue(responseItemsIS, Object.class);
		unpackV2ApiResponseEP.expectedBodiesReceived(responseItems);

		// fire the route
		final InputStream jsonApiResponseIS = this.getClass().getResourceAsStream(UnpackV2_JsonApiResponse);

		template.sendBody("direct:" + UNPACK_V2_API_RESPONSE, jsonApiResponseIS);

		assertMockEndpointsSatisfied();
	}

	@Test
	void givenEmptyInputStream_whenFireRoute_thenNoResponseToUnpack() throws Exception
	{
		prepareRouteForTesting();

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		final MockEndpoint unpackV2ApiResponseEP = getMockEndpoint(MOCK_UNPACK_V2_API_RESPONSE);
		unpackV2ApiResponseEP.expectedBodyReceived();

		// fire the route
		final InputStreamCache cachedInputStream = new InputStreamCache(new byte[0]);

		template.sendBody("direct:" + UNPACK_V2_API_RESPONSE, cachedInputStream);

		assertThat(unpackV2ApiResponseEP.getReceivedCounter()).isEqualTo(0);
	}

	private void prepareRouteForTesting() throws Exception
	{
		AdviceWith.adviceWith(context, UNPACK_V2_API_RESPONSE,
							  advice -> advice.weaveById(UNPACK_V2_API_RESPONSE_PROCESSOR_ID)
									  .after()
									  .to(MOCK_UNPACK_V2_API_RESPONSE)
		);
	}
}
