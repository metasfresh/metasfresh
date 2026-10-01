/*
 * #%L
 * de-metas-camel-externalsystems-core
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.camel.externalsystems.core.authorization;

import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import de.metas.camel.externalsystems.core.CoreConstants;
import de.metas.camel.externalsystems.core.CustomRouteController;
import de.metas.camel.externalsystems.core.authorization.provider.MetasfreshAuthProvider;
import de.metas.camel.externalsystems.core.to_mf.v2.OrderLineCandRouteBuilder;
import de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder;
import de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf.ScriptedImportConversionRestAPIRouteBuilder;
import de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf.ScriptedImportConversionSftpRouteBuilder;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_PUSH_OL_CANDIDATES_ROUTE_ID;
import static de.metas.camel.externalsystems.core.authorization.CustomMessageToMFRouteBuilder.CUSTOM_TO_MF_ROUTE_ID;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * End-to-end reproduction of the scripted-import authorization: the real SFTP and REST scripted-import routes
 * (enabled through their real enable routes, the SFTP consumer replaced by a {@code direct:} endpoint), the real
 * {@link OrderLineCandRouteBuilder} and the real {@link MetasfreshAuthorizationTokenNotifier}, with only the metasfresh
 * HTTP API mocked.
 * <p>
 * The global service token ({@value #SERVICE_TOKEN}) must NOT be what the import sends: every call the script
 * dispatches has to carry the configured Importeur's token ({@value #IMPORTEUR_TOKEN}), so the records are
 * authored by the Importeur in its org. A rejected Importeur token (HTTP 401) fails only that import.
 */
public class ScriptedImportImporteurTokenEndToEndTest extends CamelTestSupport
{
	private static final String SERVICE_TOKEN = "service-token";
	private static final String IMPORTEUR_TOKEN = "importeur-token";

	private static final String MF_API_BASE_URL = "http://localhost:8282/api";
	private static final String MOCK_MF_OLCAND_BULK = "mock:mfOlCandBulk";
	private static final String MOCK_REAUTH_REQUEST = "mock:reAuthRequest";
	private static final String MOCK_ERROR_ROUTE = "mock:errorRoute";

	private static final String SFTP_ROUTE_KEY = "ScriptedImportConversion-e2e-sftp";
	private static final String SFTP_ENDPOINT_NAME = "e2eSftpEndpoint";
	private static final String SFTP_TEST_INPUT = "direct:e2e-sftp-input";
	private static final String SFTP_FILE_NAME = "e2e_order.json";

	private static final String REST_ENDPOINT_NAME = "e2eRestEndpoint";

	private static final String SCRIPT_IDENTIFIER = "e2e_olcand_transform";
	private static final String INPUT_JSON = "{\"orderId\": \"E2E-1\"}";

	private static final String PROPERTY_SCRIPT_REPO_BASE_DIR = "metasfresh.scriptedadapter.repo.baseDir";

	/**
	 * The scripted-import route builders replace the whole PropertiesComponent in {@code configure()}
	 * ({@code CamelRouteUtil.setupProperties}), so the script repo dir can only be injected as a JVM system property
	 * (see {@code ScriptedImportConversionRestAPIRouteBuilderTest} for the full story).
	 */
	private static final Path SCRIPT_REPO_DIR = createScriptRepoDir();

	static
	{
		System.setProperty(PROPERTY_SCRIPT_REPO_BASE_DIR, SCRIPT_REPO_DIR.toAbsolutePath().toString());
	}

	@TempDir
	Path localProcessedDir;

	@TempDir
	Path localErrorDir;

	private MetasfreshAuthProvider metasfreshAuthProvider;
	private CustomRouteController customRouteController;

	@NonNull
	private static Path createScriptRepoDir()
	{
		try
		{
			return Files.createTempDirectory("e2e-importeur-token-scripts");
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@AfterAll
	static void clearScriptRepoBaseDirSystemProperty()
	{
		System.clearProperty(PROPERTY_SCRIPT_REPO_BASE_DIR);
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected CamelContext createCamelContext() throws Exception
	{
		final CamelContext camelContext = super.createCamelContext();
		metasfreshAuthProvider = new MetasfreshAuthProvider();
		metasfreshAuthProvider.setAuthToken(SERVICE_TOKEN);
		customRouteController = new CustomRouteController(camelContext);
		return camelContext;
	}

	@Override
	protected RoutesBuilder[] createRouteBuilders()
	{
		// keep the REST "direct:" catch-all addressable (see ScriptedImportConversionRestAPIRouteBuilderTest)
		context.getRestConfiguration().setInlineRoutes(false);

		return new RoutesBuilder[] {
				new ScriptedImportConversionSftpRouteBuilder(context.createProducerTemplate()),
				new ScriptedImportConversionRestAPIRouteBuilder(context.createProducerTemplate()),
				new OrderLineCandRouteBuilder(),
				new UnpackV2ResponseRouteBuilder(),
				new RouteBuilder()
				{
					@Override
					public void configure()
					{
						// stands in for the RabbitMQ re-auth request route: must never be triggered by an Importeur 401
						from("direct:" + CUSTOM_TO_MF_ROUTE_ID).routeId(CUSTOM_TO_MF_ROUTE_ID).to(MOCK_REAUTH_REQUEST);
						// stands in for the AD_Issue error route
						from("direct:" + MF_ERROR_ROUTE_ID).routeId(MF_ERROR_ROUTE_ID).to(MOCK_ERROR_ROUTE);
					}
				}
		};
	}

	@BeforeEach
	void writeScript() throws IOException
	{
		final String script = "function transform(messageFromMetasfresh) {\n"
				+ "    var order = JSON.parse(messageFromMetasfresh);\n"
				+ "    var requestBody = JSON.stringify({\n"
				+ "        requests: [{\n"
				+ "            orgCode: \"001\",\n"
				+ "            externalHeaderId: String(order.orderId),\n"
				+ "            externalLineId: String(order.orderId),\n"
				+ "            externalSystemCode: \"Other\",\n"
				+ "            dataSource: \"int-Shopware\",\n"
				+ "            bpartner: { bpartnerIdentifier: \"1\", bpartnerLocationIdentifier: \"1\" },\n"
				+ "            dateRequired: \"2026-10-01\",\n"
				+ "            dateOrdered: \"2026-10-01\",\n"
				+ "            orderDocType: \"SalesOrder\",\n"
				+ "            paymentTerm: \"val-1000002\",\n"
				+ "            productIdentifier: \"1\",\n"
				+ "            qty: 1,\n"
				+ "            currencyCode: \"EUR\",\n"
				+ "            discount: 0,\n"
				+ "            poReference: \"ref_e2e\",\n"
				+ "            deliveryViaRule: \"S\",\n"
				+ "            deliveryRule: \"F\",\n"
				+ "            bpartnerName: \"e2eName\"\n"
				+ "        }]\n"
				+ "    });\n"
				+ "    return JSON.stringify([{ camelServiceRouteID: \"" + MF_PUSH_OL_CANDIDATES_ROUTE_ID + "\", requestBody: requestBody }]);\n"
				+ "}\n";
		Files.writeString(SCRIPT_REPO_DIR.resolve(SCRIPT_IDENTIFIER + ".js"), script, StandardCharsets.UTF_8);
	}

	@AfterEach
	void clearSecurityContext()
	{
		SecurityContextHolder.clearContext();
	}

	@Test
	void sftpImport_sendsImporteurToken_notServiceToken() throws Exception
	{
		setUpContext(mfRespondsOk());

		final MockEndpoint mfBulk = getMockEndpoint(MOCK_MF_OLCAND_BULK);
		mfBulk.expectedMessageCount(1);

		enableSftpAndFeedFile();

		mfBulk.assertIsSatisfied(60_000);
		assertThat(mfBulk.getExchanges().get(0).getIn().getHeader(CoreConstants.AUTHORIZATION)).isEqualTo(IMPORTEUR_TOKEN);
		assertThat(listFiles(localProcessedDir)).containsExactly(SFTP_FILE_NAME);
	}

	@Test
	void restImport_sendsImporteurToken_notServiceToken() throws Exception
	{
		setUpContext(mfRespondsOk());

		final MockEndpoint mfBulk = getMockEndpoint(MOCK_MF_OLCAND_BULK);
		mfBulk.expectedMessageCount(1);

		final Exchange response = enableRestAndPost();

		mfBulk.assertIsSatisfied(60_000);
		assertThat(mfBulk.getExchanges().get(0).getIn().getHeader(CoreConstants.AUTHORIZATION)).isEqualTo(IMPORTEUR_TOKEN);
		assertThat(response.getMessage().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class)).isEqualTo(200);
	}

	@Test
	void rejectedImporteurToken_failsOnlyThatImport() throws Exception
	{
		setUpContext(mfRejectsWith401());

		final MockEndpoint mfBulk = getMockEndpoint(MOCK_MF_OLCAND_BULK);
		mfBulk.expectedMessageCount(2);
		final MockEndpoint reAuth = getMockEndpoint(MOCK_REAUTH_REQUEST);
		reAuth.expectedMessageCount(0);

		// SFTP: the file lands in the error dir
		enableSftpAndFeedFile();
		final Path errorFile = localErrorDir.resolve(SFTP_FILE_NAME);
		assertThat(waitFor(() -> Files.exists(errorFile))).as("SFTP payload archived to the LOCAL error dir").isTrue();
		assertThat(listFiles(localProcessedDir)).isEmpty();

		// REST: the client gets an error response with the clear message
		final Exchange response = enableRestAndPost();
		assertThat(response.getMessage().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class)).isEqualTo(500);
		assertThat(response.getMessage().getBody(String.class)).contains("Importeur");

		mfBulk.assertIsSatisfied(60_000);
		mfBulk.getExchanges().forEach(e -> assertThat(e.getIn().getHeader(CoreConstants.AUTHORIZATION)).isEqualTo(IMPORTEUR_TOKEN));

		// no route stopped, no global re-authentication, the service token untouched
		assertThat(context.getRouteController().getRouteStatus(SFTP_ROUTE_KEY).isStarted()).isTrue();
		assertThat(context.getRouteController().getRouteStatus(REST_ENDPOINT_NAME).isStarted()).isTrue();
		assertThat(context.getRouteController().getRouteStatus(MF_PUSH_OL_CANDIDATES_ROUTE_ID).isStarted()).isTrue();
		reAuth.assertIsSatisfied();
		assertThat(metasfreshAuthProvider.getAuthToken()).isEqualTo(SERVICE_TOKEN);
	}

	private void setUpContext(@NonNull final Processor mfApiBehaviour) throws Exception
	{
		final Processor noop = exchange -> {};

		AdviceWith.adviceWith(context, ScriptedImportConversionSftpRouteBuilder.ENABLE_SFTP_POLLING_ROUTE_ID,
				a -> a.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
						.skipSendToOriginalEndpoint()
						.process(noop));
		AdviceWith.adviceWith(context, ScriptedImportConversionRestAPIRouteBuilder.ENABLE_RESOURCE_ROUTE_ID,
				a -> {
					a.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.REST_API_AUTHENTICATE_TOKEN)
							.skipSendToOriginalEndpoint()
							.process(noop);
					a.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
							.skipSendToOriginalEndpoint()
							.process(noop);
				});
		// the metasfresh HTTP API is the only mock: record what it receives, then answer
		AdviceWith.adviceWith(context, MF_PUSH_OL_CANDIDATES_ROUTE_ID,
				a -> a.interceptSendToEndpoint("http*")
						.skipSendToOriginalEndpoint()
						.to(MOCK_MF_OLCAND_BULK)
						.process(mfApiBehaviour));

		context.getManagementStrategy().addEventNotifier(new MetasfreshAuthorizationTokenNotifier(
				metasfreshAuthProvider, MF_API_BASE_URL, customRouteController, context.createProducerTemplate()));

		context.start();
	}

	@NonNull
	private static Processor mfRespondsOk()
	{
		return exchange -> exchange.getIn().setBody("{\"result\":[]}");
	}

	@NonNull
	private static Processor mfRejectsWith401()
	{
		return exchange -> {
			throw new HttpOperationFailedException(MF_API_BASE_URL + "/v2/orders/sales/candidates/bulk", 401, "Unauthorized", null, null, "{\"error\":\"unauthorized\"}");
		};
	}

	private void enableSftpAndFeedFile() throws Exception
	{
		final Map<String, String> params = baseParams(SFTP_ENDPOINT_NAME);
		params.put(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ROUTE_KEY, SFTP_ROUTE_KEY);
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_HOST, "localhost");
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_PORT, "1");
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_USERNAME, "user");
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_PASSWORD, "pass");
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_AUTH_TYPE, "PASSWORD");
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_INTERVAL_MS, "600000");

		template.sendBody("direct:" + ScriptedImportConversionSftpRouteBuilder.ENABLE_SFTP_POLLING_ROUTE_ID, request("ScriptedImportConversion-enableSftpPolling", params));

		// the real dynamic SFTP route, with only its sftp:// consumer swapped for a direct: endpoint
		AdviceWith.adviceWith(context, SFTP_ROUTE_KEY, a -> a.replaceFromWith(SFTP_TEST_INPUT));

		template.sendBodyAndHeader(SFTP_TEST_INPUT, INPUT_JSON, Exchange.FILE_NAME, SFTP_FILE_NAME);
	}

	@NonNull
	private Exchange enableRestAndPost()
	{
		template.sendBody("direct:" + ScriptedImportConversionRestAPIRouteBuilder.ENABLE_RESOURCE_ROUTE_ID,
				request("ScriptedImportConversion-enableRestAPI", baseParams(REST_ENDPOINT_NAME)));

		mockAuthenticatedRequest();

		return template.send("direct:" + ScriptedImportConversionRestAPIRouteBuilder.REST_API_ROUTE_ID,
				exchange -> {
					exchange.getIn().setHeader(Exchange.HTTP_PATH, "/interchange/import/" + REST_ENDPOINT_NAME);
					exchange.getIn().setBody(INPUT_JSON);
				});
	}

	@NonNull
	private Map<String, String> baseParams(@NonNull final String endpointName)
	{
		final Map<String, String> params = new HashMap<>();
		params.put(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME, endpointName);
		params.put(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_SCRIPT_IDENTIFIER, SCRIPT_IDENTIFIER);
		params.put(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_TOKEN, IMPORTEUR_TOKEN);
		params.put(ExternalSystemConstants.PARAM_PROCESSED_DIR, localProcessedDir.toAbsolutePath().toString());
		params.put(ExternalSystemConstants.PARAM_ERROR_DIR, localErrorDir.toAbsolutePath().toString());
		return params;
	}

	@NonNull
	private static JsonExternalSystemRequest request(@NonNull final String command, @NonNull final Map<String, String> params)
	{
		return JsonExternalSystemRequest.builder()
				.externalSystemName(JsonExternalSystemName.of("ScriptedImportConversion"))
				.externalSystemConfigId(JsonMetasfreshId.of(100))
				.externalSystemChildConfigValue("e2eChild")
				.command(command)
				.orgCode("e2eOrg")
				.adPInstanceId(JsonMetasfreshId.of(999))
				.traceId("e2e-trace")
				.parameters(params)
				.build();
	}

	private static void mockAuthenticatedRequest()
	{
		final TokenCredentials credentials = TokenCredentials.builder()
				.pInstance(JsonMetasfreshId.of(999))
				.orgCode("e2eOrg")
				.externalSystemValue("e2eChild")
				.build();

		final Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.getCredentials()).thenReturn(credentials);
		final SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
	}

	@NonNull
	private static List<String> listFiles(@NonNull final Path dir) throws IOException
	{
		try (var files = Files.list(dir))
		{
			return files.map(p -> p.getFileName().toString()).toList();
		}
	}

	@SuppressWarnings("BusyWait")
	private static boolean waitFor(@NonNull final java.util.function.BooleanSupplier condition) throws InterruptedException
	{
		final long deadline = System.currentTimeMillis() + 10_000;
		while (System.currentTimeMillis() < deadline)
		{
			if (condition.getAsBoolean())
			{
				return true;
			}
			Thread.sleep(100);
		}
		return condition.getAsBoolean();
	}
}
