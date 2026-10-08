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

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.ROUTE_MSG_FROM_MF_CONTEXT;
import static org.assertj.core.api.Assertions.assertThat;

class SftpDeliveryProcessorTest
{
	private static final String SFTP_USER = "testuser";
	private static final String SFTP_PASS = "testpass";

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
}
