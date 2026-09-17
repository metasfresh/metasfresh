/*
 * #%L
 * de-metas-camel-scriptedadapter
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf;

import de.metas.camel.externalsystems.scriptedadapter.JavaScriptRepo;
import de.metas.camel.externalsystems.scriptedadapter.oauth.OAuthAccessToken;
import de.metas.camel.externalsystems.scriptedadapter.oauth.OAuthTokenManager;
import de.metas.camel.externalsystems.scriptedadapter.oauth2.OAuth2TokenManager;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.time.SystemTime;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.support.DefaultExchange;
import org.apache.camel.test.junit5.CamelContextConfiguration;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID;
import static de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf.ScriptedAdapterConvertMsgFromMFRouteBuilder.PROPERTY_SCRIPTING_REPO_BASE_DIR;
import static de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf.ScriptedAdapterConvertMsgFromMFRouteBuilder.ScriptedExportConversion_ConvertMsgFromMF_OUTBOUND_HTTP_EP_ID;
import static de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf.ScriptedAdapterConvertMsgFromMFRouteBuilder.ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_JAVASCRIPT_IDENTIFIER;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_OUTBOUND_ENDPOINT_PARAMETERS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_TABLE_NAME;
import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that when a {@link de.metas.common.externalsystem.endpoint.JsonExternalSystemEndpoint}
 * carries {@code authType=OAuth2} the outbound HTTP request is decorated with
 * {@code Authorization: Bearer <token>} where the token is obtained from the
 * {@link OAuth2TokenManager}.
 */
class OAuth2RouteTest extends CamelTestSupport
{
	private static final String MOCK_ATTACHMENT_ENDPOINT = "mock:AttachmentEndpoint";

	private OAuth2TokenManager oauth2TokenManager;

	@Override
	public void configureContext(@NonNull final CamelContextConfiguration camelContextConfiguration)
	{
		super.configureContext(camelContextConfiguration);
		testConfiguration().withUseAdviceWith(true);

		final Properties properties = new Properties();
		try
		{
			properties.load(OAuth2RouteTest.class.getClassLoader().getResourceAsStream("application.properties"));
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
		camelContextConfiguration.withUseOverridePropertiesWithPropertiesComponent(properties);
	}

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		final OAuthTokenManager oauthTokenManager = Mockito.mock(OAuthTokenManager.class);
		Mockito.when(oauthTokenManager.getAccessToken(Mockito.any()))
				.thenReturn(OAuthAccessToken.of("dummy-legacy-oauth-token", SystemTime.asInstant().plus(24, ChronoUnit.HOURS)));

		oauth2TokenManager = Mockito.mock(OAuth2TokenManager.class);
		Mockito.when(oauth2TokenManager.getAccessToken(
						Mockito.anyString(),
						Mockito.nullable(String.class),
						Mockito.anyString(),
						Mockito.anyString(),
						Mockito.anyString()))
				.thenReturn("test-oauth2-bearer-token");

		return new ScriptedAdapterConvertMsgFromMFRouteBuilder(oauthTokenManager, oauth2TokenManager, new SftpDeliveryProcessor());
	}

	/**
	 * Happy-path: endpoint with {@code authType=OAuth2} → dispatched HTTP request carries
	 * {@code Authorization: Bearer test-oauth2-bearer-token}.
	 */
	@Test
	void oauth2AuthType_setsAuthorizationBearerHeader() throws Exception
	{
		// Given
		final String jsScript = """
				function transform(messageFromMetasfresh) {
					return JSON.stringify({exported: true});
				}
				""";

		final Exchange exchange = prepareOAuth2Exchange(jsScript, "{}", "https://auth.example.com/token", "docuware.platform", "my-client-id");

		final String[] authHeaderCaptor = { null };
		final MockEndpoint mockHttpEndpoint = getMockEndpoint("mock:httpEndPoint");
		mockHttpEndpoint.expectedMessageCount(1);

		AdviceWith.adviceWith(context,
				ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID,
				advice -> {
					advice.weaveById(ScriptedExportConversion_ConvertMsgFromMF_OUTBOUND_HTTP_EP_ID)
							.replace()
							.process(ex -> {
								// Capture the Authorization header that was set just before the HTTP call
								authHeaderCaptor[0] = ex.getIn().getHeader(AUTHORIZATION, String.class);
								// Simulate a 200 response so the route completes normally
								ex.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
								ex.getIn().setBody("OK");
							})
							.to(mockHttpEndpoint);

					advice.interceptSendToEndpoint("direct:" + MF_ATTACHMENT_ROUTE_ID)
							.skipSendToOriginalEndpoint()
							.to(MOCK_ATTACHMENT_ENDPOINT);
				});

		context.start();

		// When
		template.send("direct:" + ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID, exchange);

		// Then: the mock HTTP endpoint was called exactly once
		MockEndpoint.assertIsSatisfied(context);

		// The Authorization header must be "Bearer <token-from-OAuth2TokenManager>"
		assertThat(authHeaderCaptor[0])
				.as("Authorization header")
				.isEqualTo("Bearer test-oauth2-bearer-token");

		// OAuth2TokenManager.getAccessToken() must have been called with the endpoint's token URL,
		// scope, clientId, user, and password
		Mockito.verify(oauth2TokenManager).getAccessToken(
				"https://auth.example.com/token",
				"docuware.platform",
				"my-client-id",
				"testuser",
				"testpass");
	}

	/**
	 * 401-retry: when the external endpoint returns 401, the token is invalidated and the request
	 * is retried once with a fresh token.
	 */
	@Test
	void oauth2AuthType_on401_invalidatesTokenAndRetries() throws Exception
	{
		// Arrange: first call returns "stale-token", second call (after invalidation) returns "fresh-token"
		Mockito.reset(oauth2TokenManager);
		Mockito.when(oauth2TokenManager.getAccessToken(
						Mockito.anyString(),
						Mockito.nullable(String.class),
						Mockito.anyString(),
						Mockito.anyString(),
						Mockito.anyString()))
				.thenReturn("stale-token", "fresh-token");

		final String jsScript = """
				function transform(messageFromMetasfresh) {
					return JSON.stringify({exported: true});
				}
				""";

		final Exchange exchange = prepareOAuth2Exchange(jsScript, "{}", "https://auth.example.com/token", null, "my-client-id");

		final String[] authHeaderCaptor = { null };

		// HTTP mock: first call returns 401 (first .id endpoint), the retry endpoint returns 200
		AdviceWith.adviceWith(context,
				ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID,
				advice -> {
					advice.weaveById(ScriptedExportConversion_ConvertMsgFromMF_OUTBOUND_HTTP_EP_ID)
							.replace()
							.process(ex -> {
								// First attempt: respond with 401 to trigger retry logic
								ex.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 401);
								ex.getIn().setBody("Unauthorized");
							});

					// The retry endpoint: capture the Authorization header and respond 200
					advice.weaveById(ScriptedExportConversion_ConvertMsgFromMF_OUTBOUND_HTTP_EP_ID + "_OAUTH2_RETRY")
							.replace()
							.process(ex -> {
								authHeaderCaptor[0] = ex.getIn().getHeader(AUTHORIZATION, String.class);
								ex.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
								ex.getIn().setBody("OK-retry");
							});

					advice.interceptSendToEndpoint("direct:" + MF_ATTACHMENT_ROUTE_ID)
							.skipSendToOriginalEndpoint()
							.to(MOCK_ATTACHMENT_ENDPOINT);
				});

		context.start();

		// When
		template.send("direct:" + ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID, exchange);

		// Then: the OAuth2TokenManager must have had its cache invalidated and been called twice
		Mockito.verify(oauth2TokenManager, Mockito.times(2)).getAccessToken(
				Mockito.anyString(),
				Mockito.nullable(String.class),
				Mockito.anyString(),
				Mockito.anyString(),
				Mockito.anyString());
		Mockito.verify(oauth2TokenManager).invalidateToken(
				"https://auth.example.com/token",
				"my-client-id",
				"testuser");

		// The retry request carries the fresh token
		assertThat(authHeaderCaptor[0])
				.as("Authorization header on retry")
				.isEqualTo("Bearer fresh-token");
	}

	// ---- helpers ----

	@NonNull
	private Exchange prepareOAuth2Exchange(
			@NonNull final String jsScript,
			@NonNull final String messageFromMetasfresh,
			@NonNull final String oauthTokenUrl,
			final String oauthScope,
			@NonNull final String clientId)
	{
		final JavaScriptRepo javaScriptRepo = new JavaScriptRepo(context.resolvePropertyPlaceholders("{{" + PROPERTY_SCRIPTING_REPO_BASE_DIR + "}}"));
		javaScriptRepo.save("oauth2TestScript", jsScript);

		final String scopeLine = oauthScope != null ? ",\n  \"oauthScope\" : \"" + oauthScope + "\"" : "";

		final Exchange exchange = new DefaultExchange(template.getCamelContext());
		exchange.getIn().setBody(
				JsonExternalSystemRequest.builder()
						.orgCode("orgCode")
						.externalSystemName(JsonExternalSystemName.of("ScriptedAdapter"))
						.command("ConvertMsgFromMF")
						.externalSystemConfigId(JsonMetasfreshId.of(1))
						.traceId("test-trace-oauth2")
						.externalSystemChildConfigValue("testConfig")
						.parameter(PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT, messageFromMetasfresh)
						.parameter(PARAM_SCRIPTEDADAPTER_JAVASCRIPT_IDENTIFIER, "oauth2TestScript")
						.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_ENDPOINT_PARAMETERS, """
								{
								  "value" : "oauth2-endpoint",
								  "endpointUrl" : "http://localhost:8080/test",
								  "method" : "POST",
								  "authType" : "OAuth2",
								  "oauthTokenUrl" : "%s"%s,
								  "clientId" : "%s",
								  "user" : "testuser",
								  "password" : "testpass"
								}""".formatted(oauthTokenUrl, scopeLine, clientId))
						.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_TABLE_NAME, "C_Order")
						.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_ID, "42")
						.build());

		return exchange;
	}
}
