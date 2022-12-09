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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.common.handlingunits.JsonHUAttributesRequest;
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

public class UpdateHURouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_UPDATE_HU_ATTRIBUTES = "mock:updateHUAttributesRoute";
	private static final String JSON_HU_UPDATE = "1_JsonHUUpdate.json";
	private static final String JSON_HU_ATTRIBUTES_REQUEST = "10_JsonHUAttributesRequest.json";

	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final Properties properties = new Properties();
		try
		{
			properties.load(UpdateHURouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
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
		return new UpdateHURouteBuilder();
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Test
	void updateHU() throws Exception
	{
		//given
		final UpdateHURouteBuilderTest.MockHUAttributesEP mockUpdateHUAttributesEP = new UpdateHURouteBuilderTest.MockHUAttributesEP();
		prepareUpdateRouteForTesting(mockUpdateHUAttributesEP);

		context.start();

		final MockEndpoint updateHUAttributesMockEP = getMockEndpoint(MOCK_UPDATE_HU_ATTRIBUTES);
		final InputStream jsonHuAttributeReq = UpdateHURouteBuilderTest.class.getResourceAsStream(JSON_HU_ATTRIBUTES_REQUEST);
		updateHUAttributesMockEP.expectedBodiesReceived(objectMapper.readValue(jsonHuAttributeReq, JsonHUAttributesRequest.class));

		final String requestBodyAsString = loadAsString(JSON_HU_UPDATE);

		//when
		template.sendBody("direct:" + UpdateHURouteBuilder.UPDATE_HU_ROUTE_ID, requestBodyAsString);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockUpdateHUAttributesEP.called).isEqualTo(1);
	}

	private void prepareUpdateRouteForTesting(@NonNull final UpdateHURouteBuilderTest.MockHUAttributesEP mockHUAttributesEP) throws Exception
	{
		AdviceWith.adviceWith(context, UpdateHURouteBuilder.UPDATE_HU_ROUTE_ID,
							  advice -> {
								  advice.weaveById(UpdateHURouteBuilder.UPDATE_HU_PROCESSOR_ID)
										  .after()
										  .to(MOCK_UPDATE_HU_ATTRIBUTES);

								  advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_UPDATE_HU_ATTRIBUTES_V2_CAMEL_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .process(mockHUAttributesEP);
							  });
	}

	private static class MockHUAttributesEP implements Processor
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
		final InputStream inputStream = UpdateHURouteBuilderTest.class.getResourceAsStream(name);
		return new BufferedReader(
				new InputStreamReader(inputStream, StandardCharsets.UTF_8))
				.lines()
				.collect(Collectors.joining("\n"));
	}
}
