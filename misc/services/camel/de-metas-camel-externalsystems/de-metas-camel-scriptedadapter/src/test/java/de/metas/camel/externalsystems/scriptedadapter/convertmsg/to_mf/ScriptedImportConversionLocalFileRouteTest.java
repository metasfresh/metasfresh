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
import de.metas.common.externalsystem.ExternalSystemConstants;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.DefaultExchange;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

/**
 * Route-wiring coverage for the LOCAL_FILE polling/archiving path: a real {@code file://} consumer
 * against {@code @TempDir} directories (no mock stand-in — unlike the SFTP transport, a real local
 * filesystem consumer is trivial to exercise), proving the binary payload survives the route
 * byte-for-byte (never decoded to/re-encoded from a {@code String}, which would corrupt it), the
 * envelope carries the original file name, and the source file ends up archived to the processed
 * directory and gone from the input directory.
 */
public class ScriptedImportConversionLocalFileRouteTest extends CamelTestSupport
{
	private static final String MOCK_ENDPOINT_NAME = "mock:endpointName";
	private static final String MOCK_SCRIPT_IDENTIFIER = "mock:scriptIdentifier";
	private static final String MOCK_SCRIPT = "mock:script.js";
	private static final long FREQUENCY_MS = 100L;

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
				null, // importFileNamePattern: unset -> attachment name falls back to the incoming file name
				FREQUENCY_MS,
				MOCK_SCRIPT_IDENTIFIER,
				javaScriptRepo,
				javaScriptExecutorService,
				producerTemplate,
				localProcessedDir.toAbsolutePath().toString(),
				localErrorDir.toAbsolutePath().toString());
	}

	@Test
	void binaryFileConsumed_envelopeAndArchiveAreByteIdentical_fileLeavesInputDir() throws Exception
	{
		Mockito.when(javaScriptRepo.get(MOCK_SCRIPT_IDENTIFIER)).thenReturn(MOCK_SCRIPT);
		Mockito.when(javaScriptExecutorService.executeScript(eq(MOCK_SCRIPT_IDENTIFIER), eq(MOCK_SCRIPT), any()))
				.thenReturn("[]");

		// Deliberately NOT valid UTF-8 (0x00 and 0xFF are not valid UTF-8 byte sequences on their own):
		// a route that does getBody(String.class) + re-encode would mangle these bytes -- e.g. replace
		// them with the UTF-8 replacement character U+FFFD -- so this content, unlike an ASCII payload,
		// actually catches that defect instead of silently passing despite it.
		final byte[] pdfBytes = { (byte) 0x25, (byte) 0x50, (byte) 0x44, (byte) 0x46, 0x00, (byte) 0xFF, (byte) 0x00, (byte) 0xFF };
		final String originalFileName = "packzettel.pdf";

		context.start();

		final NotifyBuilder notify = new NotifyBuilder(context).whenDone(1).create();
		Files.write(localInputDir.resolve(originalFileName), pdfBytes);

		assertThat(notify.matches(10, TimeUnit.SECONDS)).isTrue();

		// the script received the envelope, by name -- fileName + fileBase64
		final ArgumentCaptor<String> requestCaptor = ArgumentCaptor.forClass(String.class);
		Mockito.verify(javaScriptExecutorService)
				.executeScript(eq(MOCK_SCRIPT_IDENTIFIER), eq(MOCK_SCRIPT), requestCaptor.capture());

		final ScriptedImportConversionFileInput envelope = JsonObjectMapperHolder.sharedJsonObjectMapper()
				.readValue(requestCaptor.getValue(), ScriptedImportConversionFileInput.class);

		assertThat(envelope.getFileName()).isEqualTo(originalFileName);
		assertThat(Base64.getDecoder().decode(envelope.getFileBase64())).isEqualTo(pdfBytes);

		// archived to the processed dir, byte-identical to the source
		final List<Path> archivedFiles;
		try (var files = Files.list(localProcessedDir))
		{
			archivedFiles = files.toList();
		}
		assertThat(archivedFiles).hasSize(1);
		assertThat(Files.readAllBytes(archivedFiles.get(0))).isEqualTo(pdfBytes);

		// gone from the input directory
		try (var remaining = Files.list(localInputDir))
		{
			assertThat(remaining.findAny()).isEmpty();
		}

		// nothing filed under error
		try (var errorFiles = Files.list(localErrorDir))
		{
			assertThat(errorFiles.findAny()).isEmpty();
		}
	}

	/**
	 * Tests of {@link ScriptedImportConversionLocalFileRouteBuilder#buildFileUri()} -- a pure
	 * configuration method, not a behavioural/timing one.
	 */
	@Nested
	class BuildFileUri
	{
		/**
		 * Asserts the {@code file://} consumer URI carries every fixed poller option -- {@code
		 * readLock=changed} with the chosen {@code readLockCheckInterval}/{@code readLockMinAge}, {@code
		 * readLockMinLength=0} (a 0-byte file must not wedge the poller), {@code
		 * readLockMarkerFile=false} (no marker file in the polled directory), and {@code
		 * antExclude=**{@literal /}*.tmp} with {@code antFilterCaseSensitive=false} (a scanner's
		 * temp-file-then-rename must not be consumed mid-write, including one written with an uppercase
		 * suffix) -- so a future edit that silently drops or weakens any of them fails this test
		 * immediately. A faithful slow-writer integration test (actually writing a file progressively and
		 * asserting it is NOT picked up mid-write) would be disproportionate for this guard and
		 * timing-fragile in CI -- see {@link ScriptedImportConversionLocalFileRouteBuilder#buildFileUri()}
		 * for why each option matters here specifically and how the values were chosen.
		 */
		@Test
		void configuresReadLockAgainstPartiallyWrittenFiles()
		{
			final ScriptedImportConversionLocalFileRouteBuilder routeBuilder = new ScriptedImportConversionLocalFileRouteBuilder(
					"routeKey",
					MOCK_ENDPOINT_NAME,
					localInputDir.toAbsolutePath().toString(),
					null,
					FREQUENCY_MS,
					MOCK_SCRIPT_IDENTIFIER,
					javaScriptRepo,
					javaScriptExecutorService,
					producerTemplate,
					localProcessedDir.toAbsolutePath().toString(),
					localErrorDir.toAbsolutePath().toString());

			final String fileUri = routeBuilder.buildFileUri();

			assertThat(fileUri).isEqualTo(
					"file://" + localInputDir.toAbsolutePath()
							+ "?delay=" + FREQUENCY_MS + "&initialDelay=0&delete=true&noop=false"
							+ "&readLock=changed&readLockCheckInterval=1000&readLockMinAge=2000&readLockMinLength=0"
							+ "&readLockMarkerFile=false&antExclude=**/*.tmp&antFilterCaseSensitive=false");
		}

		/**
		 * {@code localRootLocation} and {@code frequency} are free-text/operator-configured -- a blank
		 * root location must fail loudly and name the offending parameter, rather than produce an opaque
		 * JDK/Camel error far from the empty field that caused it.
		 */
		@Test
		void rejectsBlankRootLocation()
		{
			assertThatThrownBy(() -> newLocalFileRouteBuilder(" ", FREQUENCY_MS).buildFileUri())
					.isInstanceOf(RuntimeCamelException.class)
					.hasMessageContaining(ExternalSystemConstants.PARAM_LOCAL_FILE_POLLING_ENDPOINT_ROOT_LOCATION);
		}

		/**
		 * A URI-significant character in {@code localRootLocation} must fail loudly and name the
		 * offending parameter, rather than produce a {@code ResolveEndpointFailedException} from an
		 * unescaped {@code &} deep in the Camel URI-resolution machinery.
		 */
		@Test
		void rejectsUnsafeRootLocation()
		{
			assertThatThrownBy(() -> newLocalFileRouteBuilder("/data/in&out", FREQUENCY_MS).buildFileUri())
					.isInstanceOf(RuntimeCamelException.class)
					.hasMessageContaining(ExternalSystemConstants.PARAM_LOCAL_FILE_POLLING_ENDPOINT_ROOT_LOCATION);
		}

		/**
		 * A non-positive {@code frequency} must fail loudly and name the offending parameter, rather than
		 * produce an opaque error from {@code scheduleWithFixedDelay} rejecting a zero period.
		 */
		@Test
		void rejectsNonPositiveFrequency()
		{
			assertThatThrownBy(() -> newLocalFileRouteBuilder(localInputDir.toAbsolutePath().toString(), 0L).buildFileUri())
					.isInstanceOf(RuntimeCamelException.class)
					.hasMessageContaining(ExternalSystemConstants.PARAM_LOCAL_FILE_POLLING_ENDPOINT_FREQUENCY_MS);
		}

		private ScriptedImportConversionLocalFileRouteBuilder newLocalFileRouteBuilder(final String rootLocation, final long frequencyMs)
		{
			return new ScriptedImportConversionLocalFileRouteBuilder(
					"routeKey",
					MOCK_ENDPOINT_NAME,
					rootLocation,
					null,
					frequencyMs,
					MOCK_SCRIPT_IDENTIFIER,
					javaScriptRepo,
					javaScriptExecutorService,
					producerTemplate,
					localProcessedDir.toAbsolutePath().toString(),
					localErrorDir.toAbsolutePath().toString());
		}
	}

	/**
	 * A conversion failure must still terminate the input file's lifecycle correctly: the input file is
	 * consumed (deleted) exactly once, its bytes end up in the error archive, and nothing is written to
	 * the processed archive. A route that stops deleting the input file on failure (leaving it to be
	 * re-polled forever) or stops archiving the failure copy (losing the payload with no record of it)
	 * fails this test.
	 */
	@Test
	void failingConversion_landsInErrorArchive_inputFileRemoved_nothingInProcessedDir() throws Exception
	{
		// A real consumer for the route's onException(...).to(direct(MF_ERROR_ROUTE_ID)) send -- same
		// reason as ScriptedImportConversionErrorRoutingTest#registerDummyErrorRoute: a send that
		// originates from inside an onException(...) clause is not intercepted by
		// interceptSendToEndpoint, so a real consumer is needed or the send fails with
		// DirectConsumerNotAvailableException.
		context.addRoutes(new RouteBuilder()
		{
			@Override
			public void configure()
			{
				from("direct:" + MF_ERROR_ROUTE_ID)
						.routeId(MF_ERROR_ROUTE_ID)
						.log("Error route invoked (test)");
			}
		});

		Mockito.when(javaScriptRepo.get(MOCK_SCRIPT_IDENTIFIER)).thenReturn(MOCK_SCRIPT);
		Mockito.when(javaScriptExecutorService.executeScript(eq(MOCK_SCRIPT_IDENTIFIER), eq(MOCK_SCRIPT), any()))
				.thenThrow(new RuntimeCamelException("script failed"));

		final byte[] pdfBytes = { (byte) 0x25, (byte) 0x50, (byte) 0x44, (byte) 0x46, 0x01, 0x02 };
		final String originalFileName = "scan002.pdf";

		context.start();

		final NotifyBuilder notify = new NotifyBuilder(context).whenDone(1).create();
		Files.write(localInputDir.resolve(originalFileName), pdfBytes);

		assertThat(notify.matches(10, TimeUnit.SECONDS)).isTrue();

		// archived to the error dir, byte-identical to the source
		final List<Path> errorFiles;
		try (var files = Files.list(localErrorDir))
		{
			errorFiles = files.toList();
		}
		assertThat(errorFiles).hasSize(1);
		assertThat(Files.readAllBytes(errorFiles.get(0))).isEqualTo(pdfBytes);

		// never filed as a (false) success under processed
		try (var processedFiles = Files.list(localProcessedDir))
		{
			assertThat(processedFiles.findAny()).isEmpty();
		}

		// gone from the input directory -- not left to be re-polled forever
		try (var remaining = Files.list(localInputDir))
		{
			assertThat(remaining.findAny()).isEmpty();
		}
	}

	/**
	 * A {@code null} body (e.g. the file vanished between poll and read) must fail loudly rather than
	 * silently letting the consumer commit the delete with no copy anywhere and no error raised.
	 * <p>
	 * Drives {@link ScriptedImportConversionLocalFileRouteBuilder#captureRawPayloadAndBuildEnvelope(Exchange)}
	 * directly rather than through the route: a real {@code file://} consumer only ever reads bytes from
	 * an existing file, so it cannot produce a {@code null} {@code byte[]} body -- there is no route-level
	 * path that reaches this case.
	 */
	@Test
	void nullBody_throwsInsteadOfSilentlyDroppingFile()
	{
		final ScriptedImportConversionLocalFileRouteBuilder routeBuilder = new ScriptedImportConversionLocalFileRouteBuilder(
				"routeKey",
				MOCK_ENDPOINT_NAME,
				localInputDir.toAbsolutePath().toString(),
				null,
				FREQUENCY_MS,
				MOCK_SCRIPT_IDENTIFIER,
				javaScriptRepo,
				javaScriptExecutorService,
				producerTemplate,
				localProcessedDir.toAbsolutePath().toString(),
				localErrorDir.toAbsolutePath().toString());

		final Exchange exchange = new DefaultExchange(context);
		exchange.getIn().setBody(null);
		exchange.getIn().setHeader(Exchange.FILE_NAME, "scan003.pdf");

		assertThatThrownBy(() -> routeBuilder.captureRawPayloadAndBuildEnvelope(exchange))
				.isInstanceOf(RuntimeCamelException.class)
				.hasMessageContaining("scan003.pdf");
	}
}
