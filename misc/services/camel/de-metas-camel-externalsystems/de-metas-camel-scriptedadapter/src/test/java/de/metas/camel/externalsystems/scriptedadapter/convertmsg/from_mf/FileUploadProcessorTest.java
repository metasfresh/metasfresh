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
import java.nio.charset.StandardCharsets;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf.FileUploadProcessor.HEADER_X_FILE_MODIFIED_DATE;
import static de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf.FileUploadProcessor.PART_NAME_DOCUMENT;
import static de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf.FileUploadProcessor.PART_NAME_FILE;
import static de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf.ScriptedAdapterConvertMsgFromMFRouteBuilder.PROPERTY_SCRIPTING_REPO_BASE_DIR;
import static de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf.ScriptedAdapterConvertMsgFromMFRouteBuilder.ScriptedExportConversion_ConvertMsgFromMF_OUTBOUND_HTTP_EP_ID;
import static de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf.ScriptedAdapterConvertMsgFromMFRouteBuilder.ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_JAVASCRIPT_IDENTIFIER;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_OUTBOUND_ENDPOINT_PARAMETERS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_TABLE_NAME;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the multipart/form-data file-upload dispatch path.
 *
 * <p>Verifies three scenarios:
 * <ol>
 *   <li>{@code isFileUpload=true} → outbound request is {@code multipart/form-data} with a
 *       {@code Document} (application/json) part and a {@code File[]} (binary) part, plus
 *       the {@value FileUploadProcessor#HEADER_X_FILE_MODIFIED_DATE} header.</li>
 *   <li>{@code isFileUpload} absent → body/dispatch unchanged (plain JSON pass-through).</li>
 *   <li>Malformed input (fileBase64 missing while isFileUpload=true) → clear exception naming
 *       the missing field.</li>
 * </ol>
 */
class FileUploadProcessorTest extends CamelTestSupport
{
	private static final String MOCK_ATTACHMENT_ENDPOINT = "mock:AttachmentEndpoint";

	// ----- test data -----
	private static final byte[] PDF_BYTES = new byte[] { 0x25, 0x50, 0x44, 0x46, 0x2D }; // "%PDF-"
	private static final String PDF_BASE64 = Base64.getEncoder().encodeToString(PDF_BYTES);
	private static final String FILE_NAME = "invoice.pdf";
	private static final String FILE_CONTENT_TYPE = "application/pdf";

	// The document JSON object produced by the JS script
	private static final String DOCUMENT_JSON = "{\"docNo\":\"INV-001\",\"amount\":123.45}";

	/** Script return value for the isFileUpload=true case. */
	private static final String FILE_UPLOAD_SCRIPT_RETURN = String.format(
			"{\"document\":%s,\"fileBase64\":\"%s\",\"fileName\":\"%s\",\"contentType\":\"%s\"}",
			DOCUMENT_JSON, PDF_BASE64, FILE_NAME, FILE_CONTENT_TYPE);

	// -----------------------------------------------------------------------
	// CamelTestSupport boilerplate
	// -----------------------------------------------------------------------

	@Override
	public void configureContext(@NonNull final CamelContextConfiguration camelContextConfiguration)
	{
		super.configureContext(camelContextConfiguration);
		testConfiguration().withUseAdviceWith(true);

		final Properties properties = new Properties();
		try
		{
			properties.load(FileUploadProcessorTest.class.getClassLoader().getResourceAsStream("application.properties"));
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
				.thenReturn(OAuthAccessToken.of("dummy-token", SystemTime.asInstant().plus(24, ChronoUnit.HOURS)));

		final OAuth2TokenManager oauth2TokenManager = Mockito.mock(OAuth2TokenManager.class);
		Mockito.when(oauth2TokenManager.getAccessToken(
						Mockito.anyString(), Mockito.nullable(String.class),
						Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn("dummy-oauth2-token");

		return new ScriptedAdapterConvertMsgFromMFRouteBuilder(oauthTokenManager, oauth2TokenManager, new SftpDeliveryProcessor());
	}

	// -----------------------------------------------------------------------
	// Test 1 — isFileUpload=true → multipart body with Document + File[] parts
	// -----------------------------------------------------------------------

	@Test
	void fileUpload_true_dispatchesMultipartFormData() throws Exception
	{
		// JS script returns a payload containing document, base64 file, filename and contentType
		final String jsScript = String.format("""
				function transform(messageFromMetasfresh) {
				    return '%s';
				}
				""", FILE_UPLOAD_SCRIPT_RETURN.replace("'", "\\'"));

		final Exchange exchange = prepareFileUploadExchange(jsScript, "{}", /*isFileUpload*/ true);

		// Capture what is sent to the HTTP endpoint
		final byte[][] capturedBody = { null };
		final String[] capturedContentType = { null };
		final String[] capturedModifiedDate = { null };

		final MockEndpoint mockHttpEndpoint = getMockEndpoint("mock:httpEndPoint");
		mockHttpEndpoint.expectedMessageCount(1);

		AdviceWith.adviceWith(context,
				ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID,
				advice -> {
					advice.weaveById(ScriptedExportConversion_ConvertMsgFromMF_OUTBOUND_HTTP_EP_ID)
							.replace()
							.process(ex -> {
								capturedBody[0] = ex.getIn().getBody(byte[].class);
								capturedContentType[0] = ex.getIn().getHeader(Exchange.CONTENT_TYPE, String.class);
								capturedModifiedDate[0] = ex.getIn().getHeader(HEADER_X_FILE_MODIFIED_DATE, String.class);
								ex.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
								ex.getIn().setBody("OK");
							})
							.to(mockHttpEndpoint);

					advice.interceptSendToEndpoint("direct:" + MF_ATTACHMENT_ROUTE_ID)
							.skipSendToOriginalEndpoint()
							.to(MOCK_ATTACHMENT_ENDPOINT);
				});

		context.start();

		template.send("direct:" + ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID, exchange);

		MockEndpoint.assertIsSatisfied(context);

		// 1. Content-Type must be multipart/form-data with a boundary parameter
		assertThat(capturedContentType[0])
				.as("Content-Type header must be multipart/form-data")
				.isNotNull()
				.startsWith("multipart/form-data")
				.contains("boundary=");

		// 2. X-File-ModifiedDate header must be present (ISO-8601 instant, e.g. "2025-...")
		assertThat(capturedModifiedDate[0])
				.as("X-File-ModifiedDate header must be set")
				.isNotNull()
				.isNotBlank();

		// 3. Parse the raw multipart body and verify the two parts
		assertThat(capturedBody[0])
				.as("Multipart body bytes must be non-null")
				.isNotNull();

		final String bodyString = new String(capturedBody[0], StandardCharsets.UTF_8);

		// Part name "Document" must be present with the document JSON content
		assertThat(bodyString)
				.as("Document part name must appear in the multipart body")
				.contains("name=\"" + PART_NAME_DOCUMENT + "\"");
		assertThat(bodyString)
				.as("Document JSON content must appear in the multipart body")
				.contains("\"docNo\":\"INV-001\"");
		assertThat(bodyString)
				.as("Document part Content-Type must be application/json")
				.contains("Content-Type: application/json");

		// Part name "File[]" must be present with binary content and correct filename
		assertThat(bodyString)
				.as("File[] part name must appear in the multipart body")
				.contains("name=\"" + PART_NAME_FILE + "\"");
		assertThat(bodyString)
				.as("File[] part filename must match the input fileName")
				.contains("filename=\"" + FILE_NAME + "\"");
		assertThat(bodyString)
				.as("File[] part Content-Type must match the input contentType")
				.contains("Content-Type: " + FILE_CONTENT_TYPE);

		// Verify the actual binary content of the File[] part
		// The PDF_BYTES are the raw bytes; they must appear verbatim in the body
		assertBodyContainsBytes(capturedBody[0], PDF_BYTES);
	}

	// -----------------------------------------------------------------------
	// Test 2 — isFileUpload absent → plain JSON pass-through
	// -----------------------------------------------------------------------

	@Test
	void fileUpload_absent_plainJsonPassThrough() throws Exception
	{
		final String plainJsonOutput = "{\"key\":\"value\",\"num\":42}";
		final String jsScript = String.format("""
				function transform(messageFromMetasfresh) {
				    return '%s';
				}
				""", plainJsonOutput);

		// No isFileUpload flag (null) — endpoint built without it
		final Exchange exchange = prepareFileUploadExchange(jsScript, "{}", /*isFileUpload*/ null);

		final String[] capturedBody = { null };
		final String[] capturedContentType = { null };
		final boolean[] modifiedDatePresent = { false };

		final MockEndpoint mockHttpEndpoint = getMockEndpoint("mock:httpEndPoint");
		mockHttpEndpoint.expectedMessageCount(1);

		AdviceWith.adviceWith(context,
				ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID,
				advice -> {
					advice.weaveById(ScriptedExportConversion_ConvertMsgFromMF_OUTBOUND_HTTP_EP_ID)
							.replace()
							.process(ex -> {
								capturedBody[0] = ex.getIn().getBody(String.class);
								capturedContentType[0] = ex.getIn().getHeader(Exchange.CONTENT_TYPE, String.class);
								modifiedDatePresent[0] = ex.getIn().getHeader(HEADER_X_FILE_MODIFIED_DATE) != null;
								ex.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
								ex.getIn().setBody("OK");
							})
							.to(mockHttpEndpoint);

					advice.interceptSendToEndpoint("direct:" + MF_ATTACHMENT_ROUTE_ID)
							.skipSendToOriginalEndpoint()
							.to(MOCK_ATTACHMENT_ENDPOINT);
				});

		context.start();

		template.send("direct:" + ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID, exchange);

		MockEndpoint.assertIsSatisfied(context);

		// Content-Type must NOT be multipart (auth handler sets it to application/json)
		assertThat(capturedContentType[0])
				.as("Plain-JSON path must not set multipart/form-data Content-Type")
				.doesNotContain("multipart");

		// X-File-ModifiedDate must NOT be set
		assertThat(modifiedDatePresent[0])
				.as("X-File-ModifiedDate must not be present for plain-JSON dispatch")
				.isFalse();

		// Body must equal the plain JS output
		assertThat(capturedBody[0])
				.as("Body must be the plain JS output, unchanged")
				.isEqualTo(plainJsonOutput);
	}

	// -----------------------------------------------------------------------
	// Test 3 — isFileUpload=true but fileBase64 field is missing → clear exception
	// -----------------------------------------------------------------------

	@Test
	void fileUpload_true_missingFileBase64_throwsClearException() throws Exception
	{
		// Script returns a payload that has document, fileName and contentType but NO fileBase64
		final String malformedReturn = String.format(
				"{\"document\":%s,\"fileName\":\"%s\",\"contentType\":\"%s\"}",
				DOCUMENT_JSON, FILE_NAME, FILE_CONTENT_TYPE);

		final String jsScript = String.format("""
				function transform(messageFromMetasfresh) {
				    return '%s';
				}
				""", malformedReturn.replace("'", "\\'"));

		final Exchange exchange = prepareFileUploadExchange(jsScript, "{}", /*isFileUpload*/ true);

		final MockEndpoint mockErrorRoute = getMockEndpoint("mock:errorRoute");
		mockErrorRoute.expectedMessageCount(1);

		context.addRoutes(new RouteBuilder()
		{
			@Override
			public void configure()
			{
				from("direct:" + MF_ERROR_ROUTE_ID)
						.routeId("mock-error-handler-for-file-upload-test")
						.to(mockErrorRoute);
			}
		});

		AdviceWith.adviceWith(context,
				ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID,
				advice -> advice.interceptSendToEndpoint("direct:" + MF_ATTACHMENT_ROUTE_ID)
						.skipSendToOriginalEndpoint()
						.to(MOCK_ATTACHMENT_ENDPOINT));

		context.start();

		template.send("direct:" + ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID, exchange);

		MockEndpoint.assertIsSatisfied(context);

		// The exception recorded on the exchange must mention the missing field
		final Exception caught = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
		assertThat(caught)
				.as("An exception must have been raised")
				.isNotNull();

		// Walk the cause chain to find the RuntimeCamelException naming the missing field
		assertThat(collectMessages(caught))
				.anySatisfy(msg -> assertThat(msg).contains("fileBase64"));
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	/**
	 * Builds a Camel exchange whose endpoint parameters include the optional
	 * {@code isFileUpload} flag.  Uses Token auth so the test does not need an
	 * OAuth token manager.
	 *
	 * @param isFileUpload {@code true} to set the flag, {@code null} to omit it
	 */
	@NonNull
	private Exchange prepareFileUploadExchange(
			@NonNull final String jsScript,
			@NonNull final String messageFromMetasfresh,
			final Boolean isFileUpload)
	{
		final JavaScriptRepo javaScriptRepo = new JavaScriptRepo(
				context.resolvePropertyPlaceholders("{{" + PROPERTY_SCRIPTING_REPO_BASE_DIR + "}}"));
		javaScriptRepo.save("fileUploadTestScript", jsScript);

		final String isFileUploadLine = isFileUpload == null
				? ""
				: ",\n  \"isFileUpload\" : " + isFileUpload;

		final Exchange exchange = new DefaultExchange(template.getCamelContext());
		exchange.getIn().setBody(
				JsonExternalSystemRequest.builder()
						.orgCode("orgCode")
						.externalSystemName(JsonExternalSystemName.of("DocuWare"))
						.command("ExportDocument")
						.externalSystemConfigId(JsonMetasfreshId.of(1))
						.traceId("traceId-fileupload")
						.externalSystemChildConfigValue("testConfig")
						.parameter(PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT, messageFromMetasfresh)
						.parameter(PARAM_SCRIPTEDADAPTER_JAVASCRIPT_IDENTIFIER, "fileUploadTestScript")
						.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_ENDPOINT_PARAMETERS, """
								{
								  "value" : "docuware-endpoint",
								  "endpointUrl" : "http://localhost:8080/docuware/upload",
								  "method" : "POST",
								  "authType" : "Token",
								  "token" : "Bearer DOCUWARE_TOKEN"%s
								}""".formatted(isFileUploadLine))
						.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_TABLE_NAME, "M_InOut")
						.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_ID, "456")
						.build());

		return exchange;
	}

	/**
	 * Asserts that {@code haystack} contains {@code needle} as a contiguous byte sequence.
	 */
	private static void assertBodyContainsBytes(final byte[] haystack, final byte[] needle)
	{
		outer:
		for (int i = 0; i <= haystack.length - needle.length; i++)
		{
			for (int j = 0; j < needle.length; j++)
			{
				if (haystack[i + j] != needle[j])
				{
					continue outer;
				}
			}
			return; // found
		}
		throw new AssertionError(
				"Expected body to contain bytes " + Arrays.toString(needle)
						+ " but they were not found in the " + haystack.length + "-byte body");
	}

	/** Walks the cause chain collecting all messages. */
	@NonNull
	private static List<String> collectMessages(@NonNull final Throwable root)
	{
		final List<String> messages = new ArrayList<>();
		Throwable current = root;
		while (current != null)
		{
			if (current.getMessage() != null)
			{
				messages.add(current.getMessage());
			}
			for (final Throwable suppressed : current.getSuppressed())
			{
				if (suppressed != null && suppressed.getMessage() != null)
				{
					messages.add(suppressed.getMessage());
				}
			}
			current = current.getCause();
		}
		return messages;
	}
}
