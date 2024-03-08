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

package de.metas.camel.externalsystems.grssignum.from_grs.hu;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.common.handlingunits.JsonSetClearanceStatusRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ClearHURouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_CLEAR_HU = "mock:clearHURoute";
	private static final String JSON_HU_CLEAR = "1_JsonHUClear.json";
	private static final String JSON_CLEARANCE_STATUS_REQUEST = "10_ClearanceStatusRequest.json";

	private final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final Properties properties = new Properties();
		try
		{
			properties.load(ClearHURouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
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
		return new ClearHURouteBuilder();
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Test
	void clearHU() throws Exception
	{
		//given
		final MockClearHUEP mockClearHUEP = new MockClearHUEP();
		prepareClearRouteForTesting(mockClearHUEP);

		context.start();

		final MockEndpoint clearHUMockEP = getMockEndpoint(MOCK_CLEAR_HU);
		final InputStream clearanceStatusReq = ClearHURouteBuilderTest.class.getResourceAsStream(JSON_CLEARANCE_STATUS_REQUEST);
		clearHUMockEP.expectedBodiesReceived(objectMapper.readValue(clearanceStatusReq, JsonSetClearanceStatusRequest.class));

		final String requestBodyAsString = loadAsString(JSON_HU_CLEAR);

		//when
		template.sendBody("direct:" + ClearHURouteBuilder.CLEAR_HU_ROUTE_ID, requestBodyAsString);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockClearHUEP.called).isEqualTo(1);
	}

	private void prepareClearRouteForTesting(@NonNull final ClearHURouteBuilderTest.MockClearHUEP mockClearHUEP) throws Exception
	{
		AdviceWith.adviceWith(context, ClearHURouteBuilder.CLEAR_HU_ROUTE_ID,
							  advice -> {
								  advice.weaveById(ClearHURouteBuilder.CLEAR_HU_PROCESSOR_ID)
										  .after()
										  .to(MOCK_CLEAR_HU);

								  advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_CLEAR_HU_V2_CAMEL_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .process(mockClearHUEP);
							  });
	}

	private static class MockClearHUEP implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	private static String loadAsString(@NonNull final String name)
	{
		final InputStream inputStream = ClearHURouteBuilderTest.class.getResourceAsStream(name);
		return new BufferedReader(
				new InputStreamReader(inputStream, StandardCharsets.UTF_8))
				.lines()
				.collect(Collectors.joining("\n"));
	}
}
