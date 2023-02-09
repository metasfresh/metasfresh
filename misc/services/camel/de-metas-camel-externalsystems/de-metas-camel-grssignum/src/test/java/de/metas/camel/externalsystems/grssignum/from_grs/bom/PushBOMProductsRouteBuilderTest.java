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

package de.metas.camel.externalsystems.grssignum.from_grs.bom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import de.metas.camel.externalsystems.common.v2.BOMUpsertCamelRequest;
import de.metas.camel.externalsystems.common.v2.ProductUpsertCamelRequest;
import de.metas.camel.externalsystems.common.v2.RetrieveExternalSystemInfoCamelRequest;
import de.metas.camel.externalsystems.common.v2.VerifyBOMCamelRequest;
import de.metas.common.externalsystem.JsonExternalSystemInfo;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentRequest;
import de.metas.common.util.time.SystemTime;
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
import java.time.Instant;
import java.util.Properties;
import java.util.stream.Collectors;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_GET_EXTERNAL_SYSTEM_INFO;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_BOM_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_PRODUCT_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_VERIFY_BOM_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.grssignum.from_grs.bom.PushBOMProductsRouteBuilder.ATTACH_FILE_TO_BOM_PRODUCT_ROUTE_ID;
import static de.metas.camel.externalsystems.grssignum.from_grs.bom.PushBOMProductsRouteBuilder.PUSH_BOM_PRODUCTS_ROUTE_ID;
import static de.metas.camel.externalsystems.grssignum.from_grs.bom.PushBOMProductsRouteBuilder.PUSH_BOM_PRODUCTS_ROUTE_PROCESSOR_ID;
import static de.metas.camel.externalsystems.grssignum.from_grs.bom.PushBOMProductsRouteBuilder.PUSH_PRODUCT_ROUTE_PROCESSOR_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PushBOMProductsRouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_PUSH_BOM_PRODUCTS = "mock:pushBOMProductsRoute";
	private static final String MOCK_VERIFY_BOM_PRODUCTS = "mock:verifyBOMProductsRoute";
	private static final String MOCK_UPSERT_PRODUCTS = "mock:upsertProductsRoute";
	private static final String MOCK_RETRIEVE_EXTERNAL_SYSTEM_INFO = "mock:retrieveExternalSysInfo";
	private static final String MOCK_ATTACH_FILE = "mock:attachFile";

	private static final String JSON_BOM = "0_JsonBOM.json";
	private static final String JSON_UPSERT_CAMEL_PRODUCT_REQ = "10_ProductUpsertCamelRequest.json";
	private static final String JSON_BOM_UPSERT_CAMEL_REQ = "20_BOMUpsertCamelRequest.json";
	private static final String JSON_VERIFY_BOM_CAMEL_REQ = "25_VerifyBOMRequest.json";

	private static final String JSON_RETRIEVE_EXTERNAL_SYSTEM_INFO_CAMEL_REQ_BOMs = "30_RetrieveExternalSystemInfoCamelRequestBOMs.json";
	private static final String JSON_EXTERNAL_SYSTEM_INFO_RES_BOMs = "30_JsonExternalSystemInfo.json";
	private static final String JSON_ATTACHMENT_REQ_BOMs = "40_JsonAttachmentRequestBOMs.json";


	private Authentication authentication;
	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final Properties properties = new Properties();
		try
		{
			properties.load(PushBOMProductsRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
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
		return new PushBOMProductsRouteBuilder();
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
	void pushBOMProducts() throws Exception
	{
		//given
		SystemTime.setTimeSource(() -> Instant.parse("2021-11-01T20:00:00.00Z").toEpochMilli());

		final TokenCredentials tokenCredentials = TokenCredentials.builder()
				.orgCode("orgCode")
				.pInstance(JsonMetasfreshId.of(1))
				.externalSystemValue("childConfigValue")
				.build();

		Mockito.when(authentication.getCredentials()).thenReturn(tokenCredentials);

		final PushBOMProductsRouteBuilderTest.MockPushBOMsEP mockPushBOMsEP = new PushBOMProductsRouteBuilderTest.MockPushBOMsEP();
		final PushBOMProductsRouteBuilderTest.MockUpsertProductsEP mockUpsertProductsEP = new PushBOMProductsRouteBuilderTest.MockUpsertProductsEP();
		final PushBOMProductsRouteBuilderTest.MockRetrieveExternalSysInfoEP mockRetrieveExternalSysInfoEP = new PushBOMProductsRouteBuilderTest.MockRetrieveExternalSysInfoEP();
		final PushBOMProductsRouteBuilderTest.MockAttachFileEP mockAttachFileEP = new PushBOMProductsRouteBuilderTest.MockAttachFileEP();
		preparePushRouteForTesting(mockPushBOMsEP, mockUpsertProductsEP, mockRetrieveExternalSysInfoEP, mockAttachFileEP);

		context.start();

		final MockEndpoint pushProductsMockEP = getMockEndpoint(MOCK_UPSERT_PRODUCTS);
		final InputStream upsertCamelProductsReq = PushBOMProductsRouteBuilderTest.class.getResourceAsStream(JSON_UPSERT_CAMEL_PRODUCT_REQ);
		pushProductsMockEP.expectedBodiesReceived(objectMapper.readValue(upsertCamelProductsReq, ProductUpsertCamelRequest.class));

		final MockEndpoint retrieveExternalSysInfoMockEP = getMockEndpoint(MOCK_RETRIEVE_EXTERNAL_SYSTEM_INFO);
		final InputStream retrieveExternalSysInfoCamelReq = PushBOMProductsRouteBuilderTest.class.getResourceAsStream(JSON_RETRIEVE_EXTERNAL_SYSTEM_INFO_CAMEL_REQ_BOMs);
		retrieveExternalSysInfoMockEP.expectedBodiesReceived(objectMapper.readValue(retrieveExternalSysInfoCamelReq, RetrieveExternalSystemInfoCamelRequest.class));

		final MockEndpoint attachFileMockEP = getMockEndpoint(MOCK_ATTACH_FILE);
		final InputStream jsonAttachmentReq = PushBOMProductsRouteBuilderTest.class.getResourceAsStream(JSON_ATTACHMENT_REQ_BOMs);
		attachFileMockEP.expectedBodiesReceived(objectMapper.readValue(jsonAttachmentReq, JsonAttachmentRequest.class));

		final MockEndpoint pushBOMProductsMockEP = getMockEndpoint(MOCK_PUSH_BOM_PRODUCTS);
		final InputStream bomUpsertCamelReq = PushBOMProductsRouteBuilderTest.class.getResourceAsStream(JSON_BOM_UPSERT_CAMEL_REQ);
		pushBOMProductsMockEP.expectedBodiesReceived(objectMapper.readValue(bomUpsertCamelReq, BOMUpsertCamelRequest.class));

		final MockEndpoint verifyBOMEp = getMockEndpoint(MOCK_VERIFY_BOM_PRODUCTS);
		final InputStream verifyBOMCamelRequest = PushBOMProductsRouteBuilderTest.class.getResourceAsStream(JSON_VERIFY_BOM_CAMEL_REQ);
		verifyBOMEp.expectedBodiesReceived(objectMapper.readValue(verifyBOMCamelRequest, VerifyBOMCamelRequest.class));

		final String requestBodyAsString = loadAsString(JSON_BOM);

		//when
		template.sendBody("direct:" + PUSH_BOM_PRODUCTS_ROUTE_ID, requestBodyAsString);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockPushBOMsEP.called).isEqualTo(1);
		assertThat(mockUpsertProductsEP.called).isEqualTo(1);
		assertThat(mockRetrieveExternalSysInfoEP.called).isEqualTo(1);
		assertThat(mockAttachFileEP.called).isEqualTo(1);
	}

	private void preparePushRouteForTesting(
			@NonNull final PushBOMProductsRouteBuilderTest.MockPushBOMsEP mockPushBOMsEP,
			@NonNull final PushBOMProductsRouteBuilderTest.MockUpsertProductsEP mockUpsertProductsEP,
			@NonNull final PushBOMProductsRouteBuilderTest.MockRetrieveExternalSysInfoEP mockRetrieveExternalSysInfoEP,
			@NonNull final PushBOMProductsRouteBuilderTest.MockAttachFileEP mockAttachFileEP) throws Exception
	{
		AdviceWith.adviceWith(context, PUSH_BOM_PRODUCTS_ROUTE_ID,
							  advice -> {
								  advice.weaveById(PUSH_PRODUCT_ROUTE_PROCESSOR_ID)
										  .after()
										  .to(MOCK_UPSERT_PRODUCTS);

								  advice.interceptSendToEndpoint("direct:" + MF_UPSERT_PRODUCT_V2_CAMEL_URI)
										  .skipSendToOriginalEndpoint()
										  .process(mockUpsertProductsEP);

								  advice.weaveById(PUSH_BOM_PRODUCTS_ROUTE_PROCESSOR_ID)
										  .after()
										  .to(MOCK_PUSH_BOM_PRODUCTS);

								  advice.interceptSendToEndpoint("direct:" + MF_UPSERT_BOM_V2_CAMEL_URI)
										  .skipSendToOriginalEndpoint()
										  .process(mockPushBOMsEP);

								  advice.interceptSendToEndpoint("direct:" + MF_VERIFY_BOM_V2_CAMEL_URI)
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_VERIFY_BOM_PRODUCTS);
							  });

		AdviceWith.adviceWith(context, ATTACH_FILE_TO_BOM_PRODUCT_ROUTE_ID,
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

	private static class MockPushBOMsEP implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	private static class MockRetrieveExternalSysInfoEP implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			final InputStream jsonExternalSystemInfo = PushBOMProductsRouteBuilderTest.class.getResourceAsStream(JSON_EXTERNAL_SYSTEM_INFO_RES_BOMs);
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

	private static class MockUpsertProductsEP implements Processor
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
		final InputStream inputStream = PushBOMProductsRouteBuilderTest.class.getResourceAsStream(name);
		return new BufferedReader(
				new InputStreamReader(inputStream, StandardCharsets.UTF_8))
				.lines()
				.collect(Collectors.joining("\n"));
	}
}
