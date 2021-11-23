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

package de.metas.camel.externalsystems.grssignum.from_grs.vendor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PushBPartnerRouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_UPSERT_BPARTNER = "mock:upsertBPartnerRoute";
	private static final String JSON_BPARTNER = "1_JsonBPartner.json";
	private static final String JSON_UPSERT_CAMEL_BPARTNER_REQ = "10_BPUpsertCamelRequest.json";

	private Authentication authentication;
	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final Properties properties = new Properties();
		try
		{
			properties.load(PushBPartnerRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
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
		return new PushBPartnerRouteBuilder();
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@BeforeEach
	public void beforeEach()
	{
		authentication = Mockito.mock(Authentication.class);
		final SecurityContext securityContext = Mockito.mock(SecurityContext.class);

		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

		SecurityContextHolder.setContext(securityContext);
	}

	@Test
	void pushBPartner() throws Exception
	{
		//given
		final TokenCredentials tokenCredentials = TokenCredentials.builder()
				.orgCode("orgCode")
				.pInstance(JsonMetasfreshId.of(1))
				.build();

		Mockito.when(authentication.getCredentials()).thenReturn(tokenCredentials);

		final PushBPartnerRouteBuilderTest.MockUpsertBPartnerEP mockUpsertBPartnerEP = new PushBPartnerRouteBuilderTest.MockUpsertBPartnerEP();
		preparePushRouteForTesting(mockUpsertBPartnerEP);

		context.start();

		final MockEndpoint pushBPartnersMockEP = getMockEndpoint(MOCK_UPSERT_BPARTNER);
		final InputStream upsertCamelBPartnerReq = PushBPartnerRouteBuilderTest.class.getResourceAsStream(JSON_UPSERT_CAMEL_BPARTNER_REQ);
		pushBPartnersMockEP.expectedBodiesReceived(objectMapper.readValue(upsertCamelBPartnerReq, BPUpsertCamelRequest.class));

		final String requestBodyAsString = loadAsString(JSON_BPARTNER);

		//when
		template.sendBody("direct:" + PushBPartnerRouteBuilder.PUSH_BPARTNERS_ROUTE_ID, requestBodyAsString);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockUpsertBPartnerEP.called).isEqualTo(1);
	}

	private void preparePushRouteForTesting(@NonNull final PushBPartnerRouteBuilderTest.MockUpsertBPartnerEP mockUpsertBPartnerEP) throws Exception
	{
		AdviceWith.adviceWith(context, PushBPartnerRouteBuilder.PUSH_BPARTNERS_ROUTE_ID,
							  advice -> {
								  advice.weaveById(PushBPartnerRouteBuilder.PUSH_BPARTNERS_PROCESSOR_ID)
										  .after()
										  .to(MOCK_UPSERT_BPARTNER);

								  advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")
										  .skipSendToOriginalEndpoint()
										  .process(mockUpsertBPartnerEP);
							  });
	}

	private static class MockUpsertBPartnerEP implements Processor
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
		final InputStream inputStream = PushBPartnerRouteBuilderTest.class.getResourceAsStream(name);
		return new BufferedReader(
				new InputStreamReader(inputStream, StandardCharsets.UTF_8))
				.lines()
				.collect(Collectors.joining("\n"));
	}
}
