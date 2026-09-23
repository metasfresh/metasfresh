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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf;

import de.metas.camel.externalsystems.scriptedadapter.JavaScriptExecutorService;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptRepo;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Route-wiring regression coverage for the SFTP polling archiving path: the route builder is
 * driven through a {@code direct:} endpoint standing in for the real {@code sftp://} consumer, so
 * the Camel wiring (convertBodyTo + explicit UTF-8 property capture + archive) is exercised the
 * same way it would be for a polled SFTP file, without needing a real SFTP server.
 */
public class ScriptedImportConversionSftpDynamicRouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_SFTP_URI = "direct:mockSftpIn";
	private static final String MOCK_ENDPOINT_NAME = "mock:endpointName";
	private static final String MOCK_SCRIPT_IDENTIFIER = "mock:scriptIdentifier";
	private static final String MOCK_SCRIPT = "mock:script.js";

	private final JavaScriptRepo javaScriptRepo = Mockito.mock(JavaScriptRepo.class);
	private final JavaScriptExecutorService javaScriptExecutorService = Mockito.mock(JavaScriptExecutorService.class);
	private final ProducerTemplate producerTemplate = Mockito.spy(ProducerTemplate.class);

	@TempDir
	Path localProcessedDir;

	@TempDir
	Path localErrorDir;

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new ScriptedImportConversionSftpDynamicRouteBuilder(
				"routeKey",
				MOCK_ENDPOINT_NAME,
				MOCK_SFTP_URI,
				MOCK_SCRIPT_IDENTIFIER,
				javaScriptRepo,
				javaScriptExecutorService,
				producerTemplate,
				localProcessedDir.toAbsolutePath().toString(),
				localErrorDir.toAbsolutePath().toString());
	}

	@Test
	void successfulProcessing_archivesNonAsciiPayloadByteIdenticalAsUtf8() throws Exception
	{
		// regression guard: this route now carries the archived payload as explicit UTF-8 bytes
		// (rather than an implicitly-converted String); a non-ASCII payload proves the archived
		// file is byte-identical to the old Files.writeString(..., UTF_8) behaviour, not merely
		// String-equal after a round-trip decode.
		context.start();

		Mockito.when(javaScriptRepo.get(MOCK_SCRIPT_IDENTIFIER)).thenReturn(MOCK_SCRIPT);

		final String inputPayload = "{\"customer\":\"Müller & Söhne\",\"note\":\"日本語 café €\"}";

		Mockito.when(javaScriptExecutorService.executeScript(MOCK_SCRIPT_IDENTIFIER, MOCK_SCRIPT, inputPayload))
				.thenReturn("[]");

		template.sendBody(MOCK_SFTP_URI, inputPayload);

		final List<Path> archivedFiles;
		try (var files = Files.list(localProcessedDir))
		{
			archivedFiles = files.toList();
		}
		assertThat(archivedFiles).hasSize(1);
		assertThat(Files.readAllBytes(archivedFiles.get(0)))
				.isEqualTo(inputPayload.getBytes(StandardCharsets.UTF_8));

		try (var errorFiles = Files.list(localErrorDir))
		{
			assertThat(errorFiles.findAny()).isEmpty();
		}
	}
}
