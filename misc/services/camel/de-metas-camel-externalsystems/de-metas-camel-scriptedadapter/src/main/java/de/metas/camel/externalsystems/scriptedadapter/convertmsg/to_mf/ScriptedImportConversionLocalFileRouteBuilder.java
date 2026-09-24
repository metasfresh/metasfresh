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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;
import de.metas.camel.externalsystems.common.CamelRoutesGroup;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptExecutorService;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptRepo;
import de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf.model.ScriptedImportConversionFileInput;
import de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf.processor.ScriptedImportConversionProcessor;
import de.metas.camel.externalsystems.scriptedadapter.filename.ImportFileNameResolver;
import de.metas.common.externalsystem.ExternalSystemConstants;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RuntimeCamelException;

import javax.annotation.Nullable;
import java.util.Base64;
import java.util.Optional;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.PROPERTY_SCRIPTED_IMPORT_ORIGINAL_PAYLOAD;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

/**
 * Inbound transport for a {@code LOCAL_FILE} scripted-import endpoint: polls a local directory for
 * binary files (e.g. scanned PDFs, "Packzettel") via Camel's {@code file://} component and hands each
 * one to the inbound script as a {@link ScriptedImportConversionFileInput} envelope.
 * <p>
 * Unlike the SFTP/REST transports (which carry text payloads and reuse
 * {@code captureOriginalPayloadAsUtf8Bytes}), this route's payload is BINARY. The file body is
 * therefore read as {@code byte[]} and never decoded to / re-encoded from a {@code String} — a
 * {@code convertBodyTo(String.class)} step here would corrupt the archived file: any byte sequence
 * that is not valid in the JVM's default charset gets silently altered by the decode/re-encode round
 * trip. See {@link #captureRawPayloadAndBuildEnvelope(Exchange)}.
 * <p>
 * The consumed file is removed from the input directory by the {@code file://} endpoint's
 * {@code delete=true} option (never moved/renamed there) — the archived copy lives separately, in the
 * LOCAL, transport-agnostic {@code processedDir}/{@code errorDir} (see
 * {@link AbstractScriptedImportConversionArchivingRouteBuilder}), exactly like the SFTP transport's
 * remote {@code delete=true}. See {@link #buildFileUri()} for the read-lock / poller configuration.
 */
public class ScriptedImportConversionLocalFileRouteBuilder extends AbstractScriptedImportConversionArchivingRouteBuilder
{
	/**
	 * Exchange property stashing the resolved attachment/archive file name — written once in
	 * {@link #captureRawPayloadAndBuildEnvelope(Exchange)}, read back by {@link #archiveFileName(Exchange)}.
	 */
	@VisibleForTesting
	static final String EXCHANGE_PROPERTY_RESOLVED_ARCHIVE_FILE_NAME = "ScriptedImportConversionLocalFile-resolvedArchiveFileName";

	@NonNull private final String routeKey;
	@NonNull private final String localRootLocation;
	@Nullable private final String importFileNamePattern;
	private final long frequencyMs;

	private final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	public ScriptedImportConversionLocalFileRouteBuilder(
			@NonNull final String routeKey,
			@NonNull final String endpointName,
			@NonNull final String localRootLocation,
			@Nullable final String importFileNamePattern,
			final long frequencyMs,
			@NonNull final String scriptIdentifier,
			@NonNull final JavaScriptRepo javaScriptRepo,
			@NonNull final JavaScriptExecutorService javaScriptExecutorService,
			@NonNull final ProducerTemplate producerTemplate,
			@NonNull final String processedDir,
			@NonNull final String errorDir)
	{
		super(endpointName, scriptIdentifier, javaScriptRepo, javaScriptExecutorService, producerTemplate, processedDir, errorDir);
		this.routeKey = routeKey;
		this.localRootLocation = localRootLocation;
		this.importFileNamePattern = importFileNamePattern;
		this.frequencyMs = frequencyMs;
	}

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		// handled(true): the input file is consumed (deleted, via the file:// endpoint's delete=true
		// option) regardless of whether the transform succeeds or fails — mirrors
		// ScriptedImportConversionSftpDynamicRouteBuilder: keeps Camel's file-consumer "commit" path
		// (which performs the delete) on the failure path too, instead of leaving the file in place to
		// be re-polled forever. The payload itself is archived separately either way (see
		// archiveLocallyOnError / archiveLocallyOnSuccess).
		onException(Exception.class)
				.handled(true)
				.process(this::archiveLocallyOnError)
				.to(direct(MF_ERROR_ROUTE_ID));

		//@formatter:off
		from(buildFileUri())
				.routeId(routeKey)
				.group(CamelRoutesGroup.START_ON_DEMAND.getCode())
				.log("Local file received: ${header.CamelFileName}")
				.process(this::captureRawPayloadAndBuildEnvelope)
				.process(this::initFailedItemCount)
				.process(new ScriptedImportConversionProcessor(javaScriptExecutorService, scriptIdentifier, javaScriptRepo))
				.choice()
					.when(body().isNull())
						.log(LoggingLevel.INFO, "Nothing to process for ${header.CamelFileName}")
					.otherwise()
						.split(body(), new ResponseAggregationStrategy())
							.stopOnException()
							.process(this::handleItemInList)
						.end()
					.endChoice()
				.end()
				.process(this::archiveLocallyByItemOutcome);
		//@formatter:on
	}

	/**
	 * Package-visible (not {@code private}) so the route test can assert the built URI directly — a
	 * configuration check, not a behavioural one.
	 */
	@VisibleForTesting
	@NonNull
	String buildFileUri()
	{
		if (localRootLocation.isBlank())
		{
			throw new RuntimeCamelException("Parameter '" + ExternalSystemConstants.PARAM_LOCAL_FILE_POLLING_ENDPOINT_ROOT_LOCATION + "' is required!");
		}
		if (containsUriSignificantCharacter(localRootLocation))
		{
			// Reject rather than encode: UnsafeUriCharactersEncoder (camel-util:4.10.6) does not escape
			// '&' or '?', so encoding would leave those two paths to the same failure open.
			throw new RuntimeCamelException("Parameter '" + ExternalSystemConstants.PARAM_LOCAL_FILE_POLLING_ENDPOINT_ROOT_LOCATION
					+ "' must not contain any of '?', '#', '&', '%': " + localRootLocation);
		}
		if (frequencyMs <= 0)
		{
			throw new RuntimeCamelException("Parameter '" + ExternalSystemConstants.PARAM_LOCAL_FILE_POLLING_ENDPOINT_FREQUENCY_MS + "' must be a positive value!");
		}

		// readLock=changed (readLockMinAge=2000ms): guards a progressively-writing scanner over a
		// possibly slow/flaky network drop -- an mtime-only heuristic, not a true completion signal; a
		// writer that pauses mid-transfer longer than 2s without closing the file looks "old enough"
		// regardless.
		// readLockMinLength=0: a 0-byte file would otherwise never satisfy the strategy's length check,
		// wedging the single poller thread for readLockTimeout on every poll, forever.
		// readLockMarkerFile=false: single-consumer route keyed on a unique route id, so the marker file
		// buys nothing and would otherwise write into the polled directory.
		// antExclude=**/*.tmp with antFilterCaseSensitive=false: a scanner writing a temp file then
		// renaming would otherwise have the temp file consumed mid-write -- readLock=changed only guards
		// the progressive-write case, not this one; case-insensitive because a scanner may write an
		// uppercase suffix (e.g. scan.PDF.TMP), which a case-sensitive exclude would miss.
		return "file://" + localRootLocation + "?delay=" + frequencyMs + "&initialDelay=0&delete=true&noop=false"
				+ "&readLock=changed&readLockCheckInterval=1000&readLockMinAge=2000&readLockMinLength=0"
				+ "&readLockMarkerFile=false&antExclude=**/*.tmp&antFilterCaseSensitive=false";
	}

	private static boolean containsUriSignificantCharacter(@NonNull final String value)
	{
		return value.indexOf('?') >= 0 || value.indexOf('#') >= 0 || value.indexOf('&') >= 0 || value.indexOf('%') >= 0;
	}

	/**
	 * Reads the polled file's body as raw {@code byte[]} — never {@code convertBodyTo(String.class)},
	 * which would decode/re-encode the bytes and silently corrupt a binary payload (e.g. a PDF).
	 * Captures those exact bytes as
	 * {@link de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants#PROPERTY_SCRIPTED_IMPORT_ORIGINAL_PAYLOAD}
	 * (so the archiver round-trips the source byte-for-byte), then builds and explicitly serializes the
	 * {@link ScriptedImportConversionFileInput} envelope the inbound script expects, setting it as the
	 * new message body for the inherited {@link ScriptedImportConversionProcessor} step.
	 */
	@VisibleForTesting
	void captureRawPayloadAndBuildEnvelope(@NonNull final Exchange exchange)
	{
		final String incomingFileName = Optional.ofNullable(exchange.getIn().getHeader(Exchange.FILE_NAME, String.class))
				.orElseThrow(() -> new RuntimeCamelException("Missing " + Exchange.FILE_NAME + " header for polled local file"));

		final byte[] rawPayload = exchange.getIn().getBody(byte[].class);
		if (rawPayload == null)
		{
			// getBody(byte[].class) returns null rather than throwing when no converter applies (e.g. the
			// file vanished between poll and read) -- silently continuing would let the consumer commit
			// the delete with no copy anywhere and no error raised: silent payload loss. Throw so the
			// failure is visible via the route's onException handling instead. This throw happens before
			// PROPERTY_SCRIPTED_IMPORT_ORIGINAL_PAYLOAD is set below, so archiveLocally (see the base
			// class) still has no bytes to write and the input file is deleted with no archive copy on
			// this path -- acceptable because there is nothing to archive; the thrown exception is what
			// makes the loss visible instead of silent.
			throw new RuntimeCamelException("No body could be read for polled local file " + incomingFileName);
		}

		exchange.setProperty(PROPERTY_SCRIPTED_IMPORT_ORIGINAL_PAYLOAD, rawPayload);

		final String fileBase64 = Base64.getEncoder().encodeToString(rawPayload);
		final String attachmentFileName = ImportFileNameResolver.resolve(importFileNamePattern, incomingFileName);
		// Resolved once and stashed: archiveFileName() reads this back instead of re-resolving, so a
		// {timestamp}-bearing importFileNamePattern cannot produce two different names for the attachment
		// vs. the archived copy (which would defeat the pattern's purpose of avoiding an overwrite).
		exchange.setProperty(EXCHANGE_PROPERTY_RESOLVED_ARCHIVE_FILE_NAME, attachmentFileName);

		final ScriptedImportConversionFileInput fileInput = ScriptedImportConversionFileInput.builder()
				.fileName(incomingFileName)
				.fileBase64(fileBase64)
				.attachmentFileName(attachmentFileName)
				.build();

		try
		{
			exchange.getIn().setBody(mapper.writeValueAsString(fileInput));
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeCamelException("Failed to serialize " + ScriptedImportConversionFileInput.class.getSimpleName(), e);
		}
	}

	/**
	 * The one behavioural difference from the SFTP sibling (see the base class javadoc): the resolved
	 * {@code importFileNamePattern} name computed in {@link #captureRawPayloadAndBuildEnvelope(Exchange)}
	 * — same name as the attachment, never re-resolved — falling back to the raw polled file name, then to
	 * a synthesized name, for a failure early enough that the pattern was never resolved.
	 */
	@Override
	protected String archiveFileName(@NonNull final Exchange exchange)
	{
		return Optional.ofNullable(exchange.getProperty(EXCHANGE_PROPERTY_RESOLVED_ARCHIVE_FILE_NAME, String.class))
				.or(() -> Optional.ofNullable(exchange.getIn().getHeader(Exchange.FILE_NAME, String.class)))
				.orElseGet(() -> endpointName + "_" + System.currentTimeMillis());
	}
}
