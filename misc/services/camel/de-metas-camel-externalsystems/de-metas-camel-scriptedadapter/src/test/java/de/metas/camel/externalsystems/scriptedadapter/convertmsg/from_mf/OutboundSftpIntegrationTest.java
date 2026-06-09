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
import de.metas.camel.externalsystems.scriptedadapter.sftp.EmbeddedSftpServer;
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
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID;
import static de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf.ScriptedAdapterConvertMsgFromMFRouteBuilder.PROPERTY_SCRIPTING_REPO_BASE_DIR;
import static de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf.ScriptedAdapterConvertMsgFromMFRouteBuilder.ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_JAVASCRIPT_IDENTIFIER;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_OUTBOUND_ENDPOINT_PARAMETERS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_TABLE_NAME;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * End-to-end integration test that sends a message through the full
 * {@link ScriptedAdapterConvertMsgFromMFRouteBuilder} route with {@code transportType=SFTP},
 * verifying that the file is actually delivered to an embedded SFTP server.
 */
class OutboundSftpIntegrationTest extends CamelTestSupport
{
	private static final String MOCK_ATTACHMENT_ENDPOINT = "mock:AttachmentEndpoint";

	private static final String SFTP_USER = "testuser";
	private static final String SFTP_PASS = "testpass";

	@TempDir
	Path sftpRootDir;

	@Override
	public void configureContext(@NonNull final CamelContextConfiguration camelContextConfiguration)
	{
		super.configureContext(camelContextConfiguration);
		testConfiguration().withUseAdviceWith(true);

		final Properties properties = new Properties();
		try
		{
			properties.load(OutboundSftpIntegrationTest.class.getClassLoader().getResourceAsStream("application.properties"));
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
				.thenReturn(OAuthAccessToken.of("dummy access token", SystemTime.asInstant().plus(24, ChronoUnit.HOURS)));

		return new ScriptedAdapterConvertMsgFromMFRouteBuilder(oauthTokenManager, new SftpDeliveryProcessor());
	}

	@Test
	void sftpTransport_deliversFileToEmbeddedSftpServer() throws Exception
	{
		try (final EmbeddedSftpServer sftpServer = new EmbeddedSftpServer(sftpRootDir, SFTP_USER, SFTP_PASS))
		{
			// Given: a JS script that returns a simple transformed string
			final String jsScript = """
					function transform(messageFromMetasfresh) {
						var input = JSON.parse(messageFromMetasfresh);
						var result = { status: "transformed", original: input.value };
						return JSON.stringify(result);
					}
					""";

			final String messageFromMetasfresh = "{\"value\":\"hello\"}";

			// Save the script to the repo so the route can load it
			final JavaScriptRepo javaScriptRepo = new JavaScriptRepo(
					context.resolvePropertyPlaceholders("{{" + PROPERTY_SCRIPTING_REPO_BASE_DIR + "}}"));
			javaScriptRepo.save("sftpTestScript", jsScript);

			// Build endpoint parameters JSON with SFTP transport type
			final String endpointParamsJson = String.format("""
					{
					  "value": "sftp-test-endpoint",
					  "transportType": "SFTP",
					  "sftpHost": "localhost",
					  "sftpPort": %d,
					  "sftpUsername": "%s",
					  "sftpAuthType": "PASSWORD",
					  "password": "%s",
					  "sftpRemotePath": "",
					  "sftpFilenamePattern": "test_delivery.json"
					}""", sftpServer.getPort(), SFTP_USER, SFTP_PASS);

			final Exchange exchange = new DefaultExchange(template.getCamelContext());
			exchange.getIn().setBody(
					JsonExternalSystemRequest.builder()
							.orgCode("testOrg")
							.externalSystemName(JsonExternalSystemName.of("ScriptedAdapter"))
							.command("ConvertMsgFromMF")
							.externalSystemConfigId(JsonMetasfreshId.of(1))
							.traceId("test-trace-sftp")
							.externalSystemChildConfigValue("testConfig")
							.parameter(PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT, messageFromMetasfresh)
							.parameter(PARAM_SCRIPTEDADAPTER_JAVASCRIPT_IDENTIFIER, "sftpTestScript")
							.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_ENDPOINT_PARAMETERS, endpointParamsJson)
							.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_TABLE_NAME, "C_Order")
							.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_ID, "999")
							.build());

			// Mock the attachment endpoint so it doesn't call metasfresh
			final MockEndpoint mockAttachmentEndpoint = getMockEndpoint(MOCK_ATTACHMENT_ENDPOINT);
			mockAttachmentEndpoint.expectedMessageCount(1);

			AdviceWith.adviceWith(context,
					ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID,
					advice -> advice.interceptSendToEndpoint("direct:" + MF_ATTACHMENT_ROUTE_ID)
							.skipSendToOriginalEndpoint()
							.to(mockAttachmentEndpoint));

			context.start();

			// When: send the message through the route
			template.send("direct:" + ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID, exchange);

			// Then: verify the attachment endpoint was called (SFTP log attachment)
			MockEndpoint.assertIsSatisfied(context);

			// Then: verify the file was actually delivered to the embedded SFTP server
			final List<Path> files;
			try (final Stream<Path> stream = Files.list(sftpRootDir))
			{
				files = stream.filter(Files::isRegularFile).toList();
			}

			assertThat(files).hasSize(1);

			final Path uploadedFile = files.get(0);
			assertThat(uploadedFile.getFileName().toString()).isEqualTo("test_delivery.json");

			final String fileContent = Files.readString(uploadedFile);
			assertThat(fileContent).isEqualTo("{\"status\":\"transformed\",\"original\":\"hello\"}");
		}
	}

	@Test
	void sftpTransport_deliversFileToSubdirectory() throws Exception
	{
		// Create a subdirectory on the SFTP server
		final Path subDir = sftpRootDir.resolve("outgoing");
		Files.createDirectories(subDir);

		try (final EmbeddedSftpServer sftpServer = new EmbeddedSftpServer(sftpRootDir, SFTP_USER, SFTP_PASS))
		{
			// Given: a JS script that passes through the input
			final String jsScript = """
					function transform(messageFromMetasfresh) {
						return messageFromMetasfresh;
					}
					""";

			final String messageFromMetasfresh = "<order><id>42</id></order>";

			final JavaScriptRepo javaScriptRepo = new JavaScriptRepo(
					context.resolvePropertyPlaceholders("{{" + PROPERTY_SCRIPTING_REPO_BASE_DIR + "}}"));
			javaScriptRepo.save("sftpSubdirScript", jsScript);

			final String endpointParamsJson = String.format("""
					{
					  "value": "sftp-subdir-endpoint",
					  "transportType": "SFTP",
					  "sftpHost": "localhost",
					  "sftpPort": %d,
					  "sftpUsername": "%s",
					  "sftpAuthType": "PASSWORD",
					  "password": "%s",
					  "sftpRemotePath": "outgoing",
					  "sftpFilenamePattern": "order_export.xml"
					}""", sftpServer.getPort(), SFTP_USER, SFTP_PASS);

			final Exchange exchange = new DefaultExchange(template.getCamelContext());
			exchange.getIn().setBody(
					JsonExternalSystemRequest.builder()
							.orgCode("testOrg")
							.externalSystemName(JsonExternalSystemName.of("ScriptedAdapter"))
							.command("ConvertMsgFromMF")
							.externalSystemConfigId(JsonMetasfreshId.of(1))
							.traceId("test-trace-sftp-subdir")
							.externalSystemChildConfigValue("testConfig")
							.parameter(PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT, messageFromMetasfresh)
							.parameter(PARAM_SCRIPTEDADAPTER_JAVASCRIPT_IDENTIFIER, "sftpSubdirScript")
							.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_ENDPOINT_PARAMETERS, endpointParamsJson)
							.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_TABLE_NAME, "C_Order")
							.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_ID, "42")
							.build());

			final MockEndpoint mockAttachmentEndpoint = getMockEndpoint(MOCK_ATTACHMENT_ENDPOINT);
			mockAttachmentEndpoint.expectedMessageCount(1);

			AdviceWith.adviceWith(context,
					ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID,
					advice -> advice.interceptSendToEndpoint("direct:" + MF_ATTACHMENT_ROUTE_ID)
							.skipSendToOriginalEndpoint()
							.to(mockAttachmentEndpoint));

			context.start();

			// When
			template.send("direct:" + ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID, exchange);

			// Then
			MockEndpoint.assertIsSatisfied(context);

			final Path expectedFile = subDir.resolve("order_export.xml");
			assertThat(expectedFile).exists();

			final String fileContent = Files.readString(expectedFile);
			assertThat(fileContent).isEqualTo("<order><id>42</id></order>");
		}
	}

	@Test
	void sftpTransport_filenamePatternWithTimestamp_deliversFile() throws Exception
	{
		try (final EmbeddedSftpServer sftpServer = new EmbeddedSftpServer(sftpRootDir, SFTP_USER, SFTP_PASS))
		{
			final String jsScript = """
					function transform(messageFromMetasfresh) {
						return "payload-content";
					}
					""";

			final String messageFromMetasfresh = "{\"data\":\"test\"}";

			final JavaScriptRepo javaScriptRepo = new JavaScriptRepo(
					context.resolvePropertyPlaceholders("{{" + PROPERTY_SCRIPTING_REPO_BASE_DIR + "}}"));
			javaScriptRepo.save("sftpTimestampScript", jsScript);

			final String endpointParamsJson = String.format("""
					{
					  "value": "sftp-timestamp-endpoint",
					  "transportType": "SFTP",
					  "sftpHost": "localhost",
					  "sftpPort": %d,
					  "sftpUsername": "%s",
					  "sftpAuthType": "PASSWORD",
					  "password": "%s",
					  "sftpRemotePath": "",
					  "sftpFilenamePattern": "export_{timestamp}.txt"
					}""", sftpServer.getPort(), SFTP_USER, SFTP_PASS);

			final Exchange exchange = new DefaultExchange(template.getCamelContext());
			exchange.getIn().setBody(
					JsonExternalSystemRequest.builder()
							.orgCode("testOrg")
							.externalSystemName(JsonExternalSystemName.of("ScriptedAdapter"))
							.command("ConvertMsgFromMF")
							.externalSystemConfigId(JsonMetasfreshId.of(1))
							.traceId("test-trace-sftp-timestamp")
							.externalSystemChildConfigValue("testConfig")
							.parameter(PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT, messageFromMetasfresh)
							.parameter(PARAM_SCRIPTEDADAPTER_JAVASCRIPT_IDENTIFIER, "sftpTimestampScript")
							.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_ENDPOINT_PARAMETERS, endpointParamsJson)
							.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_TABLE_NAME, "M_InOut")
							.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_ID, "777")
							.build());

			final MockEndpoint mockAttachmentEndpoint = getMockEndpoint(MOCK_ATTACHMENT_ENDPOINT);
			mockAttachmentEndpoint.expectedMessageCount(1);

			AdviceWith.adviceWith(context,
					ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID,
					advice -> advice.interceptSendToEndpoint("direct:" + MF_ATTACHMENT_ROUTE_ID)
							.skipSendToOriginalEndpoint()
							.to(mockAttachmentEndpoint));

			context.start();

			// When
			template.send("direct:" + ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID, exchange);

			// Then
			MockEndpoint.assertIsSatisfied(context);

			final List<Path> files;
			try (final Stream<Path> stream = Files.list(sftpRootDir))
			{
				files = stream.filter(Files::isRegularFile).toList();
			}

			assertThat(files).hasSize(1);

			final Path uploadedFile = files.get(0);
			assertThat(uploadedFile.getFileName().toString())
					.startsWith("export_")
					.endsWith(".txt");

			final String fileContent = Files.readString(uploadedFile);
			assertThat(fileContent).isEqualTo("payload-content");
		}
	}
}
