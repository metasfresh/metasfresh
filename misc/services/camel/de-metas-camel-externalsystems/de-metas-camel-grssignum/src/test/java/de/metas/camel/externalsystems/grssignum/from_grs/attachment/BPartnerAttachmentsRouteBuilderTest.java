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

package de.metas.camel.externalsystems.grssignum.from_grs.attachment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import de.metas.camel.externalsystems.common.v2.RetrieveExternalSystemInfoCamelRequest;
import de.metas.common.externalsystem.JsonExternalSystemInfo;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentRequest;
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

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_GET_EXTERNAL_SYSTEM_INFO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BPartnerAttachmentsRouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_RETRIEVE_EXTERNAL_SYSTEM_INFO = "mock:retrieveExternalSysInfo";
	private static final String MOCK_ATTACH_FILE = "mock:attachFile";

	private static final String JSON_BPARTNER_ATTACHMENT = "0_JsonBPartnerAttachment.json";
	private static final String JSON_RETRIEVE_EXTERNAL_SYSTEM_INFO_CAMEL_REQ = "10_RetrieveExternalSystemInfoCamelRequest.json";
	private static final String JSON_EXTERNAL_SYSTEM_INFO_RES = "10_JsonExternalSystemInfo.json";
	private static final String JSON_ATTACHMENT_REQ = "20_JsonAttachmentRequest.json";

	private Authentication authentication;
	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final Properties properties = new Properties();
		try
		{
			properties.load(BPartnerAttachmentsRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
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
		return new BPartnerAttachmentsRouteBuilder();
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
	void attachFileToBPartner() throws Exception
	{
		//given
		final TokenCredentials tokenCredentials = TokenCredentials.builder()
				.orgCode("orgCode")
				.pInstance(JsonMetasfreshId.of(1))
				.externalSystemValue("childConfigValue")
				.build();

		Mockito.when(authentication.getCredentials()).thenReturn(tokenCredentials);

		final MockRetrieveExternalSysInfoEP mockRetrieveExternalSysInfoEP = new MockRetrieveExternalSysInfoEP();
		final MockAttachFileEP mockAttachFileEP = new MockAttachFileEP();
		preparePushRouteForTesting(mockRetrieveExternalSysInfoEP, mockAttachFileEP);

		context.start();

		final MockEndpoint retrieveExternalSysInfoMockEP = getMockEndpoint(MOCK_RETRIEVE_EXTERNAL_SYSTEM_INFO);
		final InputStream retrieveExternalSysInfoCamelReq = BPartnerAttachmentsRouteBuilderTest.class.getResourceAsStream(JSON_RETRIEVE_EXTERNAL_SYSTEM_INFO_CAMEL_REQ);
		retrieveExternalSysInfoMockEP.expectedBodiesReceived(objectMapper.readValue(retrieveExternalSysInfoCamelReq, RetrieveExternalSystemInfoCamelRequest.class));

		final MockEndpoint attachFileMockEP = getMockEndpoint(MOCK_ATTACH_FILE);
		final InputStream jsonAttachmentReq = BPartnerAttachmentsRouteBuilderTest.class.getResourceAsStream(JSON_ATTACHMENT_REQ);
		attachFileMockEP.expectedBodiesReceived(objectMapper.readValue(jsonAttachmentReq, JsonAttachmentRequest.class));

		final String requestBodyAsString = loadAsString(JSON_BPARTNER_ATTACHMENT);

		//when
		template.sendBody("direct:" + BPartnerAttachmentsRouteBuilder.ATTACH_FILE_TO_BPARTNER_ROUTE_ID, requestBodyAsString);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockRetrieveExternalSysInfoEP.called).isEqualTo(1);
		assertThat(mockAttachFileEP.called).isEqualTo(1);
	}

	private void preparePushRouteForTesting(
			@NonNull final BPartnerAttachmentsRouteBuilderTest.MockRetrieveExternalSysInfoEP mockRetrieveExternalSysInfoEP,
			@NonNull final BPartnerAttachmentsRouteBuilderTest.MockAttachFileEP mockAttachFileEP) throws Exception
	{
		AdviceWith.adviceWith(context, BPartnerAttachmentsRouteBuilder.ATTACH_FILE_TO_BPARTNER_ROUTE_ID,
							  advice -> {
								  advice.interceptSendToEndpoint("{{" + MF_GET_EXTERNAL_SYSTEM_INFO + "}}")
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_RETRIEVE_EXTERNAL_SYSTEM_INFO)
										  .process(mockRetrieveExternalSysInfoEP);

								  advice.interceptSendToEndpoint("direct:" + MF_ATTACHMENT_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_ATTACH_FILE)
										  .process(mockAttachFileEP);
							  });
	}

	private static class MockRetrieveExternalSysInfoEP implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			final InputStream jsonExternalSystemInfo = BPartnerAttachmentsRouteBuilderTest.class.getResourceAsStream(JSON_EXTERNAL_SYSTEM_INFO_RES);
			exchange.getIn().setBody(jsonExternalSystemInfo, JsonExternalSystemInfo.class);
			called++;
		}
	}

	private static class MockAttachFileEP implements Processor
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
		final InputStream inputStream = BPartnerAttachmentsRouteBuilderTest.class.getResourceAsStream(name);
		return new BufferedReader(
				new InputStreamReader(inputStream, StandardCharsets.UTF_8))
				.lines()
				.collect(Collectors.joining("\n"));
	}
}
