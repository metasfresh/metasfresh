/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.auth.JsonAuthenticateRequest;
import de.metas.camel.externalsystems.common.auth.JsonExpireTokenResponse;
import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import de.metas.camel.externalsystems.common.v2.ExternalStatusCreateCamelRequest;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptExecutorService;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptRepo;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateBulkRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.junit.jupiter.api.AfterAll;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_PUSH_OL_CANDIDATES_ROUTE_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_SCRIPT_IDENTIFIER;
import static org.assertj.core.api.Assertions.assertThat;

public class ScriptedImportConversionRestAPIRouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_REST_API_AUTHENTICATE_TOKEN_ROUTE_ID = "mock:Core-registerTokenRoute";
	private static final String MOCK_REST_API_EXPIRE_TOKEN_ROUTE_ID = "mock:Core-expireTokenRoute";
	private static final String MOCK_STORE_EXTERNAL_STATUS_ROUTE_ID = "mock:Core-storeExternalStatus";
	private static final String OLCAND_MOCK_ROUTE_URI = "mock:olCandRoute";

	private static final String EXTERNAL_SYSTEM_REQUEST = "1_ExternalSystemRequest.json";
	private static final String JSON_AUTHENTICATE_REQUEST = "10_JsonAuthenticateRequest.json";
	private static final String JSON_EXPIRE_TOKEN_RESPONSE = "20_JsonExpireTokenResponse.json";
	private static final String EXTERNAL_STATUS_ACTIVE_CAMEL_REQUEST = "30_ExternalStatusCreateCamelRequestActive.json";
	private static final String EXTERNAL_STATUS_INACTIVE_CAMEL_REQUEST = "30_ExternalStatusCreateCamelRequestInactive.json";

	private static final String OLCAND_ENDPOINT_NAME = "restOlCandEndpoint";
	private static final String OLCAND_SCRIPT_IDENTIFIER = "rest_olcand_test_transform";
	private static final String OLCAND_INPUT_JSON = "{\"orderId\": \"REST-99\"}";

	/**
	 * The requestBody the {@code rest_olcand_test_transform.js} script produces for {@link #OLCAND_INPUT_JSON}.
	 */
	private static final String OLCAND_REQUEST_BODY_JSON = "{\"requests\": [{\"orgCode\": \"001\", \"externalHeaderId\": \"REST-99\", "
			+ "\"externalLineId\": \"REST-99\", \"externalSystemCode\": \"Other\", \"dataSource\": \"int-Shopware\", "
			+ "\"bpartner\": {\"bpartnerIdentifier\": \"2156425\", \"bpartnerLocationIdentifier\": \"2205175\"}, "
			+ "\"dateRequired\": \"2022-12-12\", \"dateOrdered\": \"2022-12-12\", \"orderDocType\": \"SalesOrder\", "
			+ "\"paymentTerm\": \"val-1000002\", \"productIdentifier\": \"2005577\", \"qty\": 1, \"currencyCode\": \"EUR\", "
			+ "\"discount\": 0, \"poReference\": \"ref_12301\", \"deliveryViaRule\": \"S\", \"deliveryRule\": \"F\", "
			+ "\"bpartnerName\": \"testName\"}]}";

	private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	private static final String PROPERTY_SCRIPT_REPO_BASE_DIR = "metasfresh.scriptedadapter.repo.baseDir";

	/**
	 * A real script-repo directory. Created eagerly as a {@code static final} field — i.e. at class-load
	 * time, before the JUnit5-constructed test instance even runs its constructor.
	 * <p>
	 * {@link ScriptedImportConversionRestAPIRouteBuilder#configure()} calls {@code CamelRouteUtil.setupProperties(getContext())}
	 * as its very first action, which replaces the ENTIRE {@code PropertiesComponent} with a fresh one
	 * pointed only at {@code classpath:application.properties} — discarding whatever override properties
	 * {@code CamelTestSupport} (via {@link #useOverridePropertiesWithPropertiesComponent()}) had configured.
	 * Since that replacement happens synchronously inside {@code configure()} (i.e. while {@code context.addRoutes(...)}
	 * runs, before any test method body executes), there is no window left to inject an override property
	 * through the properties-component mechanism at all. A JVM system property survives this component
	 * swap instead — Camel's {@code PropertiesComponent} defaults {@code system-properties-mode} to
	 * {@code OVERRIDE}, so a system property still wins over the fresh component's classpath-file value.
	 * Set in a static initializer so it is in place before the class is even referenced, and cleared in
	 * {@link #clearScriptRepoBaseDirSystemProperty()} so it cannot leak into another test class sharing the
	 * same forked JVM (Surefire reuses forks across test classes by default).
	 */
	private static final Path SCRIPT_REPO_DIR = createScriptRepoDir();

	static
	{
		System.setProperty(PROPERTY_SCRIPT_REPO_BASE_DIR, SCRIPT_REPO_DIR.toAbsolutePath().toString());
	}

	@NonNull
	private static Path createScriptRepoDir()
	{
		try
		{
			return Files.createTempDirectory("scriptedadapter-rest-test-scripts");
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

	/** LOCAL processed-folder archive target (REST has no remote file — see AC5 runtime-fixes refinement). */
	@TempDir
	Path localProcessedDir;

	/** LOCAL error-folder archive target (REST has no remote file — see AC5 runtime-fixes refinement). */
	@TempDir
	Path localErrorDir;

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		// By default Camel inlines a rest()-DSL verb into its "direct:" .to(...) target when that target
		// has exactly one route pointing at it (camel.rest.inlineRoutes, default true) — the production
		// ScriptedImportConversionRestAPIRouteBuilder relies on this (there is no restConfiguration() call
		// setting it explicitly). Inlined, the "direct:ScriptedImportConversion_import-catchall" endpoint
		// has no standalone consumer, so a real driver has to enter via the actual REST/HTTP layer, which
		// this Camel-route unit test does not bind. Disabling inlining here (test-only, not a production
		// change) keeps the "direct:" route addressable so the test can drive it directly while still
		// exercising the real endpoint-derived-channel-name logic in restAPIProcessor + toD(...).
		context.getRestConfiguration().setInlineRoutes(false);

		// A real (context-bound) producer template so that a dispatch to the OLCand route id actually
		// reaches whatever destination route is registered for it in this test's context (see registerOlCandMockRoute()).
		final ProducerTemplate producerTemplate = context.createProducerTemplate();
		return new ScriptedImportConversionRestAPIRouteBuilder(producerTemplate);
	}

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
			properties.load(ScriptedImportConversionRestAPIRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@AfterEach
	void tearDown()
	{
		SecurityContextHolder.clearContext();
	}

	@Test
	void enableRestAPI() throws Exception
	{

		final MockAuthenticateTokenEP mockAuthenticateTokenEP = new MockAuthenticateTokenEP();
		final MockStoreExternalStatusEP mockStoreExternalStatusEP = new MockStoreExternalStatusEP();

		prepareEnableRouteForTesting(mockAuthenticateTokenEP, mockStoreExternalStatusEP);

		context.start();

		final MockEndpoint registerRouteMockEP = getMockEndpoint(MOCK_REST_API_AUTHENTICATE_TOKEN_ROUTE_ID);
		final InputStream jsonAuthenticateReqIS = this.getClass().getResourceAsStream(JSON_AUTHENTICATE_REQUEST);
		final JsonAuthenticateRequest jsonAuthenticateReq = objectMapper.readValue(jsonAuthenticateReqIS, JsonAuthenticateRequest.class);
		registerRouteMockEP.expectedBodiesReceived(jsonAuthenticateReq);

		final MockEndpoint storeStatusMockEP = getMockEndpoint(MOCK_STORE_EXTERNAL_STATUS_ROUTE_ID);
		final InputStream jsonExternalStatusReqIS = this.getClass().getResourceAsStream(EXTERNAL_STATUS_ACTIVE_CAMEL_REQUEST);
		final ExternalStatusCreateCamelRequest jsonExternalStatusReq = objectMapper.readValue(jsonExternalStatusReqIS, ExternalStatusCreateCamelRequest.class);
		storeStatusMockEP.expectedBodiesReceived(jsonExternalStatusReq);

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper
				.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final String endpointName = invokeExternalSystemRequest.getParameters().get(PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME);

		// when fire the route
		template.sendBody("direct:" + ScriptedImportConversionRestAPIRouteBuilder.ENABLE_RESOURCE_ROUTE_ID, invokeExternalSystemRequest);

		// then
		MockEndpoint.assertIsSatisfied(context);
		assertThat(mockAuthenticateTokenEP.called).isEqualTo(1);
		assertThat(mockStoreExternalStatusEP.called).isEqualTo(1);

		Assertions.assertThat(context.getRouteController().getRouteStatus(endpointName).isStarted()).isTrue();

	}

	@Test
	void disableRestAPI() throws Exception
	{

		final MockExpireTokenEP mockExpireTokenEP = new MockExpireTokenEP();
		final MockStoreExternalStatusEP mockStoreExternalStatusEP = new MockStoreExternalStatusEP();

		prepareDisableRouteForTesting(mockExpireTokenEP, mockStoreExternalStatusEP);

		context.start();

		final MockEndpoint expireRouteMockEP = getMockEndpoint(MOCK_REST_API_EXPIRE_TOKEN_ROUTE_ID);

		final InputStream jsonAuthenticateReqIS = this.getClass().getResourceAsStream(JSON_AUTHENTICATE_REQUEST);
		final JsonAuthenticateRequest jsonAuthenticateReq = objectMapper.readValue(jsonAuthenticateReqIS, JsonAuthenticateRequest.class);
		expireRouteMockEP.expectedBodiesReceived(jsonAuthenticateReq);

		final MockEndpoint storeStatusMockEP = getMockEndpoint(MOCK_STORE_EXTERNAL_STATUS_ROUTE_ID);
		final InputStream jsonExternalStatusReqIS = this.getClass().getResourceAsStream(EXTERNAL_STATUS_INACTIVE_CAMEL_REQUEST);
		final ExternalStatusCreateCamelRequest jsonExternalStatusReq = objectMapper.readValue(jsonExternalStatusReqIS, ExternalStatusCreateCamelRequest.class);
		storeStatusMockEP.expectedBodiesReceived(jsonExternalStatusReq);

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper
				.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final String endpointName = invokeExternalSystemRequest.getParameters().get(PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME);
		final String scriptIdentifier = invokeExternalSystemRequest.getParameters().get(PARAM_SCRIPTEDADAPTER_TO_MF_SCRIPT_IDENTIFIER);

		context.addRoutes(new ScriptedImportConversionDynamicRouteBuilder(endpointName, scriptIdentifier, new JavaScriptRepo("baseDir"), new JavaScriptExecutorService(), template,
				localProcessedDir.toAbsolutePath().toString(), localErrorDir.toAbsolutePath().toString()));
		context.getRouteController().startRoute(endpointName);

		//when fire the route
		template.sendBody("direct:" + ScriptedImportConversionRestAPIRouteBuilder.DISABLE_RESOURCE_ROUTE_ID, invokeExternalSystemRequest);

		// then
		MockEndpoint.assertIsSatisfied(context);
		assertThat(mockExpireTokenEP.called).isEqualTo(1);
		assertThat(mockStoreExternalStatusEP.called).isEqualTo(1);

		assertThat(context.getRouteController().getRouteStatus(endpointName).isSuspended()).isTrue();

	}

	@Test
	void postToRestApiDispatchesToOlCandRoute() throws Exception
	{
		final MockAuthenticateTokenEP mockAuthenticateTokenEP = new MockAuthenticateTokenEP();
		final MockStoreExternalStatusEP mockStoreExternalStatusEP = new MockStoreExternalStatusEP();

		prepareEnableRouteForTesting(mockAuthenticateTokenEP, mockStoreExternalStatusEP);
		registerDummyErrorRoute();
		registerOlCandMockRoute();

		context.start();

		writeOlCandTransformScript();

		final MockEndpoint olCandMockEndpoint = getMockEndpoint(OLCAND_MOCK_ROUTE_URI);
		olCandMockEndpoint.expectedMessageCount(1);

		// Enable the REST endpoint with a real (non-mocked) OLCand-producing transform
		final JsonExternalSystemRequest enableRequest = buildOlCandEnableRequest();
		template.sendBody("direct:" + ScriptedImportConversionRestAPIRouteBuilder.ENABLE_RESOURCE_ROUTE_ID, enableRequest);
		assertThat(context.getRouteController().getRouteStatus(OLCAND_ENDPOINT_NAME).isStarted()).isTrue();

		// Emulate an authenticated POST /interchange/import/{endpoint}: binding an actual HTTP/servlet
		// container is outside the scope of this Camel-route unit test, so this drives the same "direct:"
		// route that rest("/interchange/import").post("*") forwards a matching request to, with the same
		// HTTP_PATH header and security context restAPIProcessor expects from a real request.
		mockAuthenticatedRequest();

		final Exchange responseExchange = template.send("direct:" + ScriptedImportConversionRestAPIRouteBuilder.REST_API_ROUTE_ID,
				exchange -> {
					exchange.getIn().setHeader(Exchange.HTTP_PATH, "/interchange/import/" + OLCAND_ENDPOINT_NAME);
					exchange.getIn().setBody(OLCAND_INPUT_JSON);
				});

		// Then: the item produced by the real transform was actually dispatched to the OLCand route.
		// Generous timeout: this is the first real GraalJS script execution in this test class' JVM fork,
		// and cold-starting the polyglot JS engine can itself take many seconds.
		olCandMockEndpoint.assertIsSatisfied(60_000);
		final JsonOLCandCreateBulkRequest actualDispatchedBody = olCandMockEndpoint.getExchanges().get(0).getIn().getBody(JsonOLCandCreateBulkRequest.class);
		final JsonOLCandCreateBulkRequest expectedDispatchedBody = JsonObjectMapperHolder.sharedJsonObjectMapper()
				.readValue(OLCAND_REQUEST_BODY_JSON, JsonOLCandCreateBulkRequest.class);
		assertThat(actualDispatchedBody).isEqualTo(expectedDispatchedBody);

		// And: the REST path completed successfully — confirms the endpoint-derived channel name
		// (toD("direct:${exchangeProperty.endpointName}")) correctly routed to the dynamic OLCand-producing route
		final Integer httpResponseCode = responseExchange.getMessage().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
		assertThat(httpResponseCode).isEqualTo(200);

		// And: the raw POST payload was archived to the LOCAL processed folder (REST has no remote file
		// to consume — see AC5 runtime-fixes refinement)
		final List<Path> processedFiles;
		try (var files = Files.list(localProcessedDir))
		{
			processedFiles = files.toList();
		}
		assertThat(processedFiles).hasSize(1);
		assertThat(Files.readString(processedFiles.get(0), StandardCharsets.UTF_8)).isEqualTo(OLCAND_INPUT_JSON);

		try (var errorFiles = Files.list(localErrorDir))
		{
			assertThat(errorFiles.findAny()).isEmpty();
		}
	}

	@Test
	void postMalformedPayloadToRestApiArchivesToLocalErrorDir() throws Exception
	{
		final MockAuthenticateTokenEP mockAuthenticateTokenEP = new MockAuthenticateTokenEP();
		final MockStoreExternalStatusEP mockStoreExternalStatusEP = new MockStoreExternalStatusEP();

		prepareEnableRouteForTesting(mockAuthenticateTokenEP, mockStoreExternalStatusEP);
		registerDummyErrorRoute();
		registerOlCandMockRoute();

		context.start();

		writeOlCandTransformScript();

		final MockEndpoint olCandMockEndpoint = getMockEndpoint(OLCAND_MOCK_ROUTE_URI);
		olCandMockEndpoint.expectedMessageCount(0);

		// Enable the REST endpoint with the same real (non-mocked) OLCand-producing transform — its
		// JSON.parse will throw on the malformed input below.
		final JsonExternalSystemRequest enableRequest = buildOlCandEnableRequest();
		template.sendBody("direct:" + ScriptedImportConversionRestAPIRouteBuilder.ENABLE_RESOURCE_ROUTE_ID, enableRequest);
		assertThat(context.getRouteController().getRouteStatus(OLCAND_ENDPOINT_NAME).isStarted()).isTrue();

		mockAuthenticatedRequest();

		final String malformedInput = "{ \"orderId\": \"REST-99\", this is not valid json !!";

		final Exchange responseExchange = template.send("direct:" + ScriptedImportConversionRestAPIRouteBuilder.REST_API_ROUTE_ID,
				exchange -> {
					exchange.getIn().setHeader(Exchange.HTTP_PATH, "/interchange/import/" + OLCAND_ENDPOINT_NAME);
					exchange.getIn().setBody(malformedInput);
				});

		// Then: nothing should have been dispatched to the OLCand route
		olCandMockEndpoint.assertIsSatisfied(2_000);

		// And: the client sees an error response — the exception propagated back through the REST
		// catch-all's doCatch(Exception.class) (not a JsonProcessingException, so 500, not 400)
		final Integer httpResponseCode = responseExchange.getMessage().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
		assertThat(httpResponseCode).isEqualTo(500);

		// And: the malformed payload was archived to the LOCAL error folder — never silently lost
		final List<Path> errorFiles;
		try (var files = Files.list(localErrorDir))
		{
			errorFiles = files.toList();
		}
		assertThat(errorFiles).hasSize(1);
		assertThat(Files.readString(errorFiles.get(0), StandardCharsets.UTF_8)).isEqualTo(malformedInput);

		// And: it must not have ended up in the local processed folder
		try (var files = Files.list(localProcessedDir))
		{
			assertThat(files.findAny()).isEmpty();
		}
	}

	private void mockAuthenticatedRequest()
	{
		final TokenCredentials credentials = TokenCredentials.builder()
				.pInstance(JsonMetasfreshId.of(999))
				.orgCode("testOrg")
				.externalSystemValue("testChild")
				.build();

		final Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.getCredentials()).thenReturn(credentials);

		final SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

		SecurityContextHolder.setContext(securityContext);
	}

	private JsonExternalSystemRequest buildOlCandEnableRequest()
	{
		final Map<String, String> params = new HashMap<>();
		params.put(PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME, OLCAND_ENDPOINT_NAME);
		params.put(PARAM_SCRIPTEDADAPTER_TO_MF_SCRIPT_IDENTIFIER, OLCAND_SCRIPT_IDENTIFIER);
		params.put(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_TOKEN, "token");
		// LOCAL, transport-agnostic archive folders (REST has no remote file to consume)
		params.put(ExternalSystemConstants.PARAM_PROCESSED_DIR, localProcessedDir.toAbsolutePath().toString());
		params.put(ExternalSystemConstants.PARAM_ERROR_DIR, localErrorDir.toAbsolutePath().toString());

		return JsonExternalSystemRequest.builder()
				.externalSystemName(JsonExternalSystemName.of("ScriptedImportConversion"))
				.externalSystemConfigId(JsonMetasfreshId.of(100))
				.externalSystemChildConfigValue("testChild")
				.command("ScriptedImportConversion-enableRestAPI")
				.orgCode("testOrg")
				.adPInstanceId(JsonMetasfreshId.of(123))
				.traceId("integrationTest-trace")
				.parameters(params)
				.build();
	}

	/**
	 * Writes a real JS transform (parses the input, emits one OLCand create-bulk item) to {@link #SCRIPT_REPO_DIR},
	 * as opposed to a mocked/no-op script — mirrors what a real scripted-import endpoint config would load.
	 */
	private void writeOlCandTransformScript() throws IOException
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
				+ "            bpartner: { bpartnerIdentifier: \"2156425\", bpartnerLocationIdentifier: \"2205175\" },\n"
				+ "            dateRequired: \"2022-12-12\",\n"
				+ "            dateOrdered: \"2022-12-12\",\n"
				+ "            orderDocType: \"SalesOrder\",\n"
				+ "            paymentTerm: \"val-1000002\",\n"
				+ "            productIdentifier: \"2005577\",\n"
				+ "            qty: 1,\n"
				+ "            currencyCode: \"EUR\",\n"
				+ "            discount: 0,\n"
				+ "            poReference: \"ref_12301\",\n"
				+ "            deliveryViaRule: \"S\",\n"
				+ "            deliveryRule: \"F\",\n"
				+ "            bpartnerName: \"testName\"\n"
				+ "        }]\n"
				+ "    });\n"
				+ "    return JSON.stringify([{ camelServiceRouteID: \"" + MF_PUSH_OL_CANDIDATES_ROUTE_ID + "\", requestBody: requestBody }]);\n"
				+ "}\n";
		Files.writeString(SCRIPT_REPO_DIR.resolve(OLCAND_SCRIPT_IDENTIFIER + ".js"), script, StandardCharsets.UTF_8);
	}

	private void registerDummyErrorRoute() throws Exception
	{
		// Register a dummy error route so onException doesn't fail
		context.addRoutes(new RouteBuilder()
		{
			@Override
			public void configure()
			{
				from("direct:" + MF_ERROR_ROUTE_ID)
						.routeId(MF_ERROR_ROUTE_ID)
						.log("Error route invoked (test): ${body}");
			}
		});
	}

	/**
	 * Registers a dummy destination route for the OLCand route id, ending in a mock endpoint, so that a
	 * dispatch to {@value ExternalSystemCamelConstants#MF_PUSH_OL_CANDIDATES_ROUTE_ID} (the production route
	 * lives outside this module's Camel context) has somewhere to land and can be asserted on.
	 */
	private void registerOlCandMockRoute() throws Exception
	{
		context.addRoutes(new RouteBuilder()
		{
			@Override
			public void configure()
			{
				from("direct:" + MF_PUSH_OL_CANDIDATES_ROUTE_ID)
						.routeId(MF_PUSH_OL_CANDIDATES_ROUTE_ID)
						.to(OLCAND_MOCK_ROUTE_URI)
						.setBody(constant("{}"));
			}
		});
	}

	private void prepareEnableRouteForTesting(@NonNull final ScriptedImportConversionRestAPIRouteBuilderTest.MockAuthenticateTokenEP mockAuthenticateTokenEP,
											  @NonNull final ScriptedImportConversionRestAPIRouteBuilderTest.MockStoreExternalStatusEP mockStoreExternalStatusEP) throws Exception
	{
		AdviceWith.adviceWith(context, ScriptedImportConversionRestAPIRouteBuilder.ENABLE_RESOURCE_ROUTE_ID,
				advice -> {
					advice.weaveById(ScriptedImportConversionRestAPIRouteBuilder.ENABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID)
							.after()
							.to(MOCK_REST_API_AUTHENTICATE_TOKEN_ROUTE_ID);

					advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.REST_API_AUTHENTICATE_TOKEN)
							.skipSendToOriginalEndpoint()
							.process(mockAuthenticateTokenEP);

					advice.weaveById(ScriptedImportConversionRestAPIRouteBuilder.ENABLE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID)
							.after()
							.to(MOCK_STORE_EXTERNAL_STATUS_ROUTE_ID);

					advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
							.skipSendToOriginalEndpoint()
							.process(mockStoreExternalStatusEP);
				});
	}

	private void prepareDisableRouteForTesting(@NonNull final ScriptedImportConversionRestAPIRouteBuilderTest.MockExpireTokenEP mockExpireTokenEP,
											   @NonNull final ScriptedImportConversionRestAPIRouteBuilderTest.MockStoreExternalStatusEP mockStoreExternalStatusEP) throws Exception
	{
		AdviceWith.adviceWith(context, ScriptedImportConversionRestAPIRouteBuilder.DISABLE_RESOURCE_ROUTE_ID,
				advice -> {
					advice.weaveById(ScriptedImportConversionRestAPIRouteBuilder.DISABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID)
							.after()
							.to(MOCK_REST_API_EXPIRE_TOKEN_ROUTE_ID);

					advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.REST_API_EXPIRE_TOKEN)
							.skipSendToOriginalEndpoint()
							.process(mockExpireTokenEP);

					advice.weaveById(ScriptedImportConversionRestAPIRouteBuilder.DISABLE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID)
							.after()
							.to(MOCK_STORE_EXTERNAL_STATUS_ROUTE_ID);

					advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
							.skipSendToOriginalEndpoint()
							.process(mockStoreExternalStatusEP);
				});
	}

	private static class MockAuthenticateTokenEP implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	private static class MockExpireTokenEP implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange) throws IOException
		{
			final InputStream jsonExpireTokenRes = this.getClass().getResourceAsStream(JSON_EXPIRE_TOKEN_RESPONSE);
			final JsonExpireTokenResponse jsonExpireTokenResponse = objectMapper.readValue(jsonExpireTokenRes
					, JsonExpireTokenResponse.class);

			exchange.getIn().setBody(jsonExpireTokenResponse);

			called++;
		}
	}

	private static class MockStoreExternalStatusEP implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}
}
