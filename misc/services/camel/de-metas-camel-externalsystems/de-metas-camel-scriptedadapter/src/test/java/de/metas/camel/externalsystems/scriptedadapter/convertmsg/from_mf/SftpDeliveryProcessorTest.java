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

import de.metas.camel.externalsystems.scriptedadapter.sftp.EmbeddedSftpServer;
import de.metas.common.externalsystem.endpoint.JsonExternalSystemEndpoint;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.ROUTE_MSG_FROM_MF_CONTEXT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SftpDeliveryProcessorTest
{
	private static final String SFTP_USER = "testuser";
	private static final String SFTP_PASS = "testpass";
	private static final String PRIVATE_KEY =
			"-----BEGIN OPENSSH PRIVATE KEY-----\nZm9vYmFyYmF6cXV4\n-----END OPENSSH PRIVATE KEY-----\n";

	@TempDir
	Path sftpRootDir;

	@Test
	void passwordAuth_deliversFileToSftpServer() throws Exception
	{
		try (final EmbeddedSftpServer sftpServer = new EmbeddedSftpServer(sftpRootDir, SFTP_USER, SFTP_PASS);
			 final CamelContext camelContext = new DefaultCamelContext())
		{
			camelContext.start();

			final JsonExternalSystemEndpoint endpoint = JsonExternalSystemEndpoint.builder()
					.value("test-endpoint")
					.transportType("SFTP")
					.sftpHost("localhost")
					.sftpPort(sftpServer.getPort())
					.sftpUsername(SFTP_USER)
					.sftpAuthType("PASSWORD")
					.password(SFTP_PASS)
					.sftpRemotePath("")
					.sftpFilenamePattern("test_output_{timestamp}.json")
					.build();

			final MsgFromMfContext context = MsgFromMfContext.builder()
					.orgCode("testOrg")
					.scriptingRequestBody("{\"input\": \"data\"}")
					.scriptIdentifier("testScript")
					.endpointParameters(endpoint)
					.outboundRecordTableName("C_Order")
					.outboundRecordId("123")
					.build();
			context.setScriptReturnValue("{\"transformed\": \"output\"}");

			final Exchange exchange = new DefaultExchange(camelContext);
			exchange.setProperty(ROUTE_MSG_FROM_MF_CONTEXT, context);

			// When
			final SftpDeliveryProcessor processor = new SftpDeliveryProcessor();
			processor.process(exchange);

			// Then: verify a file was written to the SFTP root dir
			final List<Path> files;
			try (final Stream<Path> stream = Files.list(sftpRootDir))
			{
				files = stream.filter(Files::isRegularFile).toList();
			}

			assertThat(files).hasSize(1);

			final Path uploadedFile = files.get(0);
			assertThat(uploadedFile.getFileName().toString()).startsWith("test_output_").endsWith(".json");

			final String fileContent = Files.readString(uploadedFile);
			assertThat(fileContent).isEqualTo("{\"transformed\": \"output\"}");
		}
	}

	@Test
	void passwordAuth_deliversFileToSubdirectory() throws Exception
	{
		// Create a subdirectory in the SFTP root
		final Path subDir = sftpRootDir.resolve("outgoing");
		Files.createDirectories(subDir);

		try (final EmbeddedSftpServer sftpServer = new EmbeddedSftpServer(sftpRootDir, SFTP_USER, SFTP_PASS);
			 final CamelContext camelContext = new DefaultCamelContext())
		{
			camelContext.start();

			final JsonExternalSystemEndpoint endpoint = JsonExternalSystemEndpoint.builder()
					.value("test-endpoint")
					.transportType("SFTP")
					.sftpHost("localhost")
					.sftpPort(sftpServer.getPort())
					.sftpUsername(SFTP_USER)
					.sftpAuthType("PASSWORD")
					.password(SFTP_PASS)
					.sftpRemotePath("outgoing")
					.sftpFilenamePattern("delivery.xml")
					.build();

			final MsgFromMfContext context = MsgFromMfContext.builder()
					.orgCode("testOrg")
					.scriptingRequestBody("<order/>")
					.scriptIdentifier("xmlScript")
					.endpointParameters(endpoint)
					.outboundRecordTableName("C_Order")
					.outboundRecordId("456")
					.build();
			context.setScriptReturnValue("<transformed-order/>");

			final Exchange exchange = new DefaultExchange(camelContext);
			exchange.setProperty(ROUTE_MSG_FROM_MF_CONTEXT, context);

			// When
			final SftpDeliveryProcessor processor = new SftpDeliveryProcessor();
			processor.process(exchange);

			// Then: verify the file was written to the subdirectory
			final Path expectedFile = subDir.resolve("delivery.xml");
			assertThat(expectedFile).exists();

			final String fileContent = Files.readString(expectedFile);
			assertThat(fileContent).isEqualTo("<transformed-order/>");
		}
	}

	@Test
	void sshKeyAuth_buildSftpUri_bindsKeyBytesInRegistry_andReferencesBean_notTempFile() throws Exception
	{
		try (final CamelContext camelContext = new DefaultCamelContext())
		{
			camelContext.start();

			final JsonExternalSystemEndpoint endpoint = JsonExternalSystemEndpoint.builder()
					.value("test-endpoint")
					.transportType("SFTP")
					.sftpHost("localhost")
					.sftpPort(22)
					.sftpUsername(SFTP_USER)
					.sftpAuthType("SSH_KEY")
					.sshPrivateKey(PRIVATE_KEY)
					.sftpRemotePath("outgoing")
					.sftpFilenamePattern("delivery.json")
					.build();

			final String beanId = "sftpDeliveryPrivateKey-test";
			final SftpDeliveryProcessor processor = new SftpDeliveryProcessor();

			final String uri = processor.buildSftpUri(endpoint, 22, "outgoing", SFTP_USER, "SSH_KEY", "delivery.json", camelContext, beanId);

			// the key is referenced in-memory via #bean:<id> — never written to a temp file on disk
			assertThat(uri).contains("&privateKey=#bean:" + beanId);
			assertThat(uri).doesNotContain("privateKeyFile");
			assertThat(uri).doesNotContain(".pem");

			// the key bytes are bound in the Camel registry under the given id
			final byte[] bound = camelContext.getRegistry().lookupByNameAndType(beanId, byte[].class);
			assertThat(bound).isEqualTo(PRIVATE_KEY.getBytes(StandardCharsets.UTF_8));
		}
	}

	@Test
	void passwordAuth_buildSftpUri_usesPasswordInUri_andBindsNoKeyBean() throws Exception
	{
		try (final CamelContext camelContext = new DefaultCamelContext())
		{
			camelContext.start();

			final JsonExternalSystemEndpoint endpoint = JsonExternalSystemEndpoint.builder()
					.value("test-endpoint")
					.transportType("SFTP")
					.sftpHost("localhost")
					.sftpPort(22)
					.sftpUsername(SFTP_USER)
					.sftpAuthType("PASSWORD")
					.password(SFTP_PASS)
					.sftpRemotePath("outgoing")
					.sftpFilenamePattern("delivery.json")
					.build();

			final String beanId = "sftpDeliveryPrivateKey-test";
			final SftpDeliveryProcessor processor = new SftpDeliveryProcessor();

			final String uri = processor.buildSftpUri(endpoint, 22, "outgoing", SFTP_USER, "PASSWORD", "delivery.json", camelContext, beanId);

			assertThat(uri).doesNotContain("privateKey");
			assertThat(camelContext.getRegistry().lookupByNameAndType(beanId, byte[].class)).isNull();
		}
	}

	@Test
	void sshKeyAuth_process_alwaysUnbindsKeyBean_evenWhenDeliveryFails() throws Exception
	{
		// The embedded server only accepts PASSWORD auth, so an SSH_KEY delivery to it fails at send time.
		// The point of the test is the finally-block guarantee: however process() ends, it must leave NO
		// private-key bytes behind in the registry (no key-material leak on the error path either).
		try (final EmbeddedSftpServer sftpServer = new EmbeddedSftpServer(sftpRootDir, SFTP_USER, SFTP_PASS);
			 final CamelContext camelContext = new DefaultCamelContext())
		{
			camelContext.start();

			final JsonExternalSystemEndpoint endpoint = JsonExternalSystemEndpoint.builder()
					.value("test-endpoint")
					.transportType("SFTP")
					.sftpHost("localhost")
					.sftpPort(sftpServer.getPort())
					.sftpUsername(SFTP_USER)
					.sftpAuthType("SSH_KEY")
					.sshPrivateKey(PRIVATE_KEY)
					.sftpRemotePath("")
					.sftpFilenamePattern("delivery.json")
					.build();

			final MsgFromMfContext context = MsgFromMfContext.builder()
					.orgCode("testOrg")
					.scriptingRequestBody("{}")
					.scriptIdentifier("testScript")
					.endpointParameters(endpoint)
					.outboundRecordTableName("C_Order")
					.outboundRecordId("789")
					.build();
			context.setScriptReturnValue("{\"transformed\": \"output\"}");

			final Exchange exchange = new DefaultExchange(camelContext);
			exchange.setProperty(ROUTE_MSG_FROM_MF_CONTEXT, context);

			final SftpDeliveryProcessor processor = new SftpDeliveryProcessor();

			assertThatThrownBy(() -> processor.process(exchange)).isInstanceOf(Exception.class);

			final String beanId = "sftpDeliveryPrivateKey-" + exchange.getExchangeId();
			assertThat(camelContext.getRegistry().lookupByNameAndType(beanId, byte[].class)).isNull();
		}
	}
}
