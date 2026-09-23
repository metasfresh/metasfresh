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

import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptExecutorService;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptRepo;
import de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf.model.ScriptedImportConversionFileInput;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.DefaultExchange;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

/**
 * Coverage for a non-null {@code importFileNamePattern} end to end: a real {@code file://} consumer, a
 * real {@code {timestamp}}-bearing pattern, asserting the resolved name is used for BOTH the envelope's
 * {@code attachmentFileName} AND the file actually written into {@code processedDir}.
 * <p>
 * The archived copy must carry the same resolved name as the attachment -- otherwise a scanner that
 * reuses a file name (e.g. re-scanning into {@code scan001.pdf} every time) would silently overwrite an
 * earlier archived original, which is exactly what the {@code {timestamp}} placeholder is configured to
 * prevent.
 */
public class ScriptedImportConversionLocalFileImportFileNamePatternTest extends CamelTestSupport
{
	private static final String MOCK_ENDPOINT_NAME = "mock:endpointName";
	private static final String MOCK_SCRIPT_IDENTIFIER = "mock:scriptIdentifier";
	private static final String MOCK_SCRIPT = "mock:script.js";
	private static final long FREQUENCY_MS = 100L;
	private static final String IMPORT_FILE_NAME_PATTERN = "Packzettel_{filename}_{timestamp}";

	private final JavaScriptRepo javaScriptRepo = Mockito.mock(JavaScriptRepo.class);
	private final JavaScriptExecutorService javaScriptExecutorService = Mockito.mock(JavaScriptExecutorService.class);
	private final ProducerTemplate producerTemplate = Mockito.spy(ProducerTemplate.class);

	@TempDir
	Path localInputDir;

	@TempDir
	Path localProcessedDir;

	@TempDir
	Path localErrorDir;

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new ScriptedImportConversionLocalFileRouteBuilder(
				"routeKey",
				MOCK_ENDPOINT_NAME,
				localInputDir.toAbsolutePath().toString(),
				IMPORT_FILE_NAME_PATTERN,
				FREQUENCY_MS,
				MOCK_SCRIPT_IDENTIFIER,
				javaScriptRepo,
				javaScriptExecutorService,
				producerTemplate,
				localProcessedDir.toAbsolutePath().toString(),
				localErrorDir.toAbsolutePath().toString());
	}

	@Test
	void importFileNamePattern_resolvedNameAppliesToBothAttachmentAndArchivedCopy() throws Exception
	{
		Mockito.when(javaScriptRepo.get(MOCK_SCRIPT_IDENTIFIER)).thenReturn(MOCK_SCRIPT);
		Mockito.when(javaScriptExecutorService.executeScript(eq(MOCK_SCRIPT_IDENTIFIER), eq(MOCK_SCRIPT), any()))
				.thenReturn("[]");

		final byte[] pdfBytes = { (byte) 0x25, (byte) 0x50, (byte) 0x44, (byte) 0x46, 0x01, 0x02 };
		final String originalFileName = "scan001.pdf";

		context.start();

		final NotifyBuilder notify = new NotifyBuilder(context).whenDone(1).create();
		Files.write(localInputDir.resolve(originalFileName), pdfBytes);

		assertThat(notify.matches(10, TimeUnit.SECONDS)).isTrue();

		final ArgumentCaptor<String> requestCaptor = ArgumentCaptor.forClass(String.class);
		Mockito.verify(javaScriptExecutorService)
				.executeScript(eq(MOCK_SCRIPT_IDENTIFIER), eq(MOCK_SCRIPT), requestCaptor.capture());

		final ScriptedImportConversionFileInput envelope = JsonObjectMapperHolder.sharedJsonObjectMapper()
				.readValue(requestCaptor.getValue(), ScriptedImportConversionFileInput.class);

		// the pattern was actually applied -- not silently bypassed: if resolution were skipped,
		// attachmentFileName would equal the raw incoming name instead of matching the pattern.
		assertThat(envelope.getAttachmentFileName()).matches("Packzettel_scan001_\\d{8}_\\d{6}\\.pdf");

		final List<Path> archivedFiles;
		try (var files = Files.list(localProcessedDir))
		{
			archivedFiles = files.toList();
		}
		assertThat(archivedFiles).hasSize(1);
		assertThat(Files.readAllBytes(archivedFiles.get(0))).isEqualTo(pdfBytes);

		// the archived copy's name must be the SAME resolved name as the attachment -- computed once,
		// not re-resolved independently for the archive (which could yield a different {timestamp}) and
		// not left as the raw scanner file name.
		assertThat(archivedFiles.get(0).getFileName().toString()).isEqualTo(envelope.getAttachmentFileName());
	}

	/**
	 * Direct, deterministic companion to the assertion above: {@code archiveFileName(exchange)} must
	 * return exactly what is held in the resolved-name exchange property -- a single write (in
	 * {@code captureRawPayloadAndBuildEnvelope}), single read (here) structure. Asserting against a
	 * sentinel value that no {@code {timestamp}}-resolution could ever coincidentally produce -- rather
	 * than against another resolved name -- fails deterministically if a second, independent resolve is
	 * ever reintroduced on the read side: two real {@code {timestamp}} resolutions landing in the same
	 * second would still pass by coincidence, but neither could ever equal this sentinel.
	 */
	@Test
	void archiveFileName_returnsExactlyWhatWasStashedByCapture() throws Exception
	{
		final ScriptedImportConversionLocalFileRouteBuilder routeBuilder = new ScriptedImportConversionLocalFileRouteBuilder(
				"routeKey",
				MOCK_ENDPOINT_NAME,
				localInputDir.toAbsolutePath().toString(),
				IMPORT_FILE_NAME_PATTERN,
				FREQUENCY_MS,
				MOCK_SCRIPT_IDENTIFIER,
				javaScriptRepo,
				javaScriptExecutorService,
				producerTemplate,
				localProcessedDir.toAbsolutePath().toString(),
				localErrorDir.toAbsolutePath().toString());

		// Reference the route builder's own key constant rather than duplicating its literal, so a rename
		// is a compile error here instead of a test that silently stops asserting anything.
		final Exchange exchange = new DefaultExchange(context);
		exchange.setProperty(ScriptedImportConversionLocalFileRouteBuilder.EXCHANGE_PROPERTY_RESOLVED_ARCHIVE_FILE_NAME,
				"sentinel-resolved-name.pdf");

		assertThat(routeBuilder.archiveFileName(exchange)).isEqualTo("sentinel-resolved-name.pdf");
	}
}
