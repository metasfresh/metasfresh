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
 * Unlike the SFTP/REST transports, this payload is BINARY: it is read as {@code byte[]} and never put
 * through {@code convertBodyTo(String.class)}, whose decode/re-encode round trip silently alters any
 * byte sequence invalid in the JVM's default charset.
 * <p>
 * The consumed file is removed from the input directory by the {@code file://} endpoint's
 * {@code delete=true} option; the archived copy lives separately, in the LOCAL, transport-agnostic
 * {@code processedDir}/{@code errorDir} (see {@link AbstractScriptedImportConversionArchivingRouteBuilder}).
 */
public class ScriptedImportConversionLocalFileDynamicRouteBuilder extends AbstractScriptedImportConversionArchivingRouteBuilder
{
	/** Resolved attachment/archive file name, written in {@link #captureRawPayloadAndBuildEnvelope(Exchange)}. */
	@VisibleForTesting
	static final String EXCHANGE_PROPERTY_RESOLVED_ARCHIVE_FILE_NAME = "ScriptedImportConversionLocalFile-resolvedArchiveFileName";

	@NonNull private final String routeKey;
	@NonNull private final String localRootLocation;
	@Nullable private final String importFileNamePattern;
	private final long frequencyMs;

	private final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	public ScriptedImportConversionLocalFileDynamicRouteBuilder(
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
			@NonNull final String errorDir,
			@NonNull final String mfAuthToken)
	{
		super(endpointName, scriptIdentifier, javaScriptRepo, javaScriptExecutorService, producerTemplate, processedDir, errorDir, mfAuthToken);
		this.routeKey = routeKey;
		this.localRootLocation = localRootLocation;
		this.importFileNamePattern = importFileNamePattern;
		this.frequencyMs = frequencyMs;
	}

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		// handled(true) keeps Camel's file-consumer "commit" path (which performs the delete=true) on the
		// failure path too, instead of leaving the file to be re-polled forever; the payload is archived
		// separately either way.
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

	/** Package-visible so the route test can assert the built URI directly. */
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
			// Reject rather than encode: UnsafeUriCharactersEncoder (camel-util:4.10.6) escapes neither
			// '&' nor '?'.
			throw new RuntimeCamelException("Parameter '" + ExternalSystemConstants.PARAM_LOCAL_FILE_POLLING_ENDPOINT_ROOT_LOCATION
					+ "' must not contain any of '?', '#', '&', '%': " + localRootLocation);
		}
		if (frequencyMs <= 0)
		{
			throw new RuntimeCamelException("Parameter '" + ExternalSystemConstants.PARAM_LOCAL_FILE_POLLING_ENDPOINT_FREQUENCY_MS + "' must be a positive value!");
		}

		// readLock=changed (minAge 2000ms): mtime-only heuristic against a progressively-writing scanner,
		// not a true completion signal -- a writer pausing longer than 2s still looks "old enough".
		// readLockMinLength=0: a 0-byte file would otherwise fail the length check and wedge the single
		// poller thread for readLockTimeout on every poll.
		// readLockMarkerFile=false: single-consumer route, so the marker would only litter the polled dir.
		// antExclude=**/*.tmp (case-insensitive, e.g. scan.PDF.TMP): a scanner that writes a temp file and
		// renames it would otherwise have it consumed mid-write -- readLock=changed does not cover that.
		return "file://" + localRootLocation + "?delay=" + frequencyMs + "&initialDelay=0&delete=true&noop=false"
				+ "&readLock=changed&readLockCheckInterval=1000&readLockMinAge=2000&readLockMinLength=0"
				+ "&readLockMarkerFile=false&antExclude=**/*.tmp&antFilterCaseSensitive=false";
	}

	private static boolean containsUriSignificantCharacter(@NonNull final String value)
	{
		return value.indexOf('?') >= 0 || value.indexOf('#') >= 0 || value.indexOf('&') >= 0 || value.indexOf('%') >= 0;
	}

	/**
	 * Reads the polled file's body as raw {@code byte[]} (see the class javadoc on why never as a
	 * {@code String}), keeps those exact bytes for the archiver, and sets the serialized
	 * {@link ScriptedImportConversionFileInput} envelope as the new message body.
	 */
	@VisibleForTesting
	void captureRawPayloadAndBuildEnvelope(@NonNull final Exchange exchange)
	{
		final String incomingFileName = Optional.ofNullable(exchange.getIn().getHeader(Exchange.FILE_NAME, String.class))
				.orElseThrow(() -> new RuntimeCamelException("Missing " + Exchange.FILE_NAME + " header for polled local file"));

		final byte[] rawPayload = exchange.getIn().getBody(byte[].class);
		if (rawPayload == null)
		{
			// A null body (e.g. the file vanished between poll and read) must fail loudly, not become a
			// silent delete with no archived copy.
			throw new RuntimeCamelException("No body could be read for polled local file " + incomingFileName);
		}

		exchange.setProperty(PROPERTY_SCRIPTED_IMPORT_ORIGINAL_PAYLOAD, rawPayload);

		final String fileBase64 = Base64.getEncoder().encodeToString(rawPayload);
		final String fileNameOverride = ImportFileNameResolver.resolve(importFileNamePattern, incomingFileName);
		// Stashed rather than re-resolved in archiveFileName(), so a {timestamp}-bearing pattern cannot
		// yield different names for the attachment and the archived copy.
		exchange.setProperty(EXCHANGE_PROPERTY_RESOLVED_ARCHIVE_FILE_NAME, fileNameOverride);

		final ScriptedImportConversionFileInput fileInput = ScriptedImportConversionFileInput.builder()
				.fileName(incomingFileName)
				.fileBase64(fileBase64)
				.fileNameOverride(fileNameOverride)
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
	 * The resolved {@code importFileNamePattern} name — the same one the attachment carries, never
	 * re-resolved — falling back to the polled file name, then to a synthesized one if the failure came
	 * before the pattern was resolved.
	 */
	@Override
	protected String archiveFileName(@NonNull final Exchange exchange)
	{
		return Optional.ofNullable(exchange.getProperty(EXCHANGE_PROPERTY_RESOLVED_ARCHIVE_FILE_NAME, String.class))
				.or(() -> Optional.ofNullable(exchange.getIn().getHeader(Exchange.FILE_NAME, String.class)))
				.orElseGet(() -> endpointName + "_" + System.currentTimeMillis());
	}
}
