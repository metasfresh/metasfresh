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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.common.externalsystem.endpoint.JsonExternalSystemEndpoint;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Base64;

import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.ROUTE_MSG_FROM_MF_CONTEXT;

/**
 * Processor that rewrites the HTTP request body to {@code multipart/form-data} when
 * {@link JsonExternalSystemEndpoint#getIsFileUpload()} is {@code TRUE}.
 *
 * <p>Expected upstream script output shape (JSON):
 * <pre>
 * {
 *   "document":   { ... },          // arbitrary JSON object — becomes the "Document" part
 *   "fileBase64": "<base64>",       // the binary file, Base64-encoded
 *   "fileName":   "invoice.pdf",    // filename for the File[] part
 *   "contentType": "application/pdf" // MIME type for the File[] part
 * }
 * </pre>
 *
 * <p>When {@code isFileUpload} is absent/false the processor is a no-op.
 *
 * <p>Multipart part names follow the receiving system's upload contract
 * (e.g. a DMS such as DocuWare expects "Document" and "File[]").
 * Header {@value #HEADER_X_FILE_MODIFIED_DATE} is set to the current ISO-8601 timestamp.
 */
public class FileUploadProcessor
{
	/** Multipart part name for the document JSON. */
	public static final String PART_NAME_DOCUMENT = "Document";

	/** Multipart part name for the binary file. */
	public static final String PART_NAME_FILE = "File[]";

	/** Header carrying the file's modification date (ISO-8601). */
	public static final String HEADER_X_FILE_MODIFIED_DATE = "X-File-ModifiedDate";

	private static final String FIELD_DOCUMENT = "document";
	private static final String FIELD_FILE_BASE64 = "fileBase64";
	private static final String FIELD_FILE_NAME = "fileName";
	private static final String FIELD_CONTENT_TYPE = "contentType";

	private final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	/**
	 * If the endpoint has {@code isFileUpload=TRUE}, parses the script return value as a
	 * {@code {document, fileBase64, fileName, contentType}} payload and replaces the exchange body
	 * with a {@code multipart/form-data} byte array.  Also sets the {@code Content-Type} header
	 * (including boundary) and the {@code X-File-ModifiedDate} header.
	 *
	 * <p>When {@code isFileUpload} is absent/false this method is a no-op.
	 */
	public void applyFileUploadBodyIfRequested(@NonNull final Exchange exchange)
	{
		final MsgFromMfContext ctx = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_MSG_FROM_MF_CONTEXT, MsgFromMfContext.class);

		if (!isFileUploadRequested(ctx.getEndpointParameters()))
		{
			return;
		}

		final String scriptReturnValue = ctx.getScriptReturnValue();
		if (scriptReturnValue == null)
		{
			throw new RuntimeCamelException("isFileUpload=true but scriptReturnValue is null");
		}

		final JsonNode root;
		try
		{
			root = mapper.readTree(scriptReturnValue);
		}
		catch (final IOException e)
		{
			throw new RuntimeCamelException("isFileUpload=true but script return value is not valid JSON: " + scriptReturnValue, e);
		}

		// Validate required fields
		final String fileBase64 = requireTextField(root, FIELD_FILE_BASE64);
		final String fileName = requireTextField(root, FIELD_FILE_NAME);
		final String contentTypeValue = requireTextField(root, FIELD_CONTENT_TYPE);
		final JsonNode documentNode = root.get(FIELD_DOCUMENT);
		if (documentNode == null || documentNode.isNull() || documentNode.isMissingNode())
		{
			throw new RuntimeCamelException("isFileUpload=true but required field '" + FIELD_DOCUMENT + "' is missing from script return value");
		}
		final String documentJson = documentNode.toString();

		// Decode the binary file
		final byte[] fileBytes;
		try
		{
			fileBytes = Base64.getDecoder().decode(fileBase64);
		}
		catch (final IllegalArgumentException e)
		{
			throw new RuntimeCamelException("isFileUpload=true but field '" + FIELD_FILE_BASE64 + "' is not valid Base64", e);
		}

		// Build the multipart entity
		final ContentType fileMimeType = ContentType.create(contentTypeValue);

		final HttpEntity multipartEntity = MultipartEntityBuilder.create()
				.addTextBody(PART_NAME_DOCUMENT, documentJson, ContentType.APPLICATION_JSON)
				.addBinaryBody(PART_NAME_FILE, fileBytes, fileMimeType, fileName)
				.build();

		// Serialise entity to bytes so Camel's HTTP component can send it
		final byte[] multipartBytes;
		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream())
		{
			multipartEntity.writeTo(baos);
			multipartBytes = baos.toByteArray();
		}
		catch (final IOException e)
		{
			throw new RuntimeCamelException("Failed to serialise multipart entity", e);
		}

		// Override body + Content-Type header (includes boundary)
		exchange.getIn().setBody(multipartBytes);
		exchange.getIn().setHeader(Exchange.CONTENT_TYPE, multipartEntity.getContentType());

		// Set the file-modified-date header (ISO-8601, current instant)
		exchange.getIn().setHeader(HEADER_X_FILE_MODIFIED_DATE, Instant.now().toString());
	}

	private static boolean isFileUploadRequested(@NonNull final JsonExternalSystemEndpoint endpointParameters)
	{
		return Boolean.TRUE.equals(endpointParameters.getIsFileUpload());
	}

	@NonNull
	private static String requireTextField(@NonNull final JsonNode root, @NonNull final String fieldName)
	{
		final JsonNode node = root.get(fieldName);
		if (node == null || node.isNull() || node.isMissingNode())
		{
			throw new RuntimeCamelException("isFileUpload=true but required field '" + fieldName + "' is missing from script return value");
		}
		final String text = node.asText(null);
		if (text == null || text.isEmpty())
		{
			throw new RuntimeCamelException("isFileUpload=true but required field '" + fieldName + "' is blank in script return value");
		}
		return text;
	}
}
