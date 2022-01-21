/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.ui.web.attachments;

import de.metas.common.util.FileUtil;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

@UtilityClass
public class DocumentAttachmentRestControllerHelper
{
	@NonNull
	public static ResponseEntity<StreamingResponseBody> extractResponseEntryFromData(@NonNull final IDocumentAttachmentEntry entry)
	{
		if (entry.getData() == null)
		{
			throw new EntityNotFoundException("No attachment found")
					.setParameter("entry", entry)
					.setParameter("reason", "data is null or empty");
		}

		final String entryFilename = entry.getFilename();
		final String entryContentType = entry.getContentType();

		final StreamingResponseBody entryData = outputStream -> {
			outputStream.write(entry.getData());
		};

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(entryContentType));
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + entryFilename + "\"");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

		return new ResponseEntity<>(entryData, headers, HttpStatus.OK);
	}

	@NonNull
	public static ResponseEntity<StreamingResponseBody> extractResponseEntryFromURL(@NonNull final IDocumentAttachmentEntry entry)
	{
		final StreamingResponseBody responseBody = outputStream -> {
			outputStream.write(new byte[] {});
		};
		final HttpHeaders headers = new HttpHeaders();
		headers.setLocation(entry.getUrl()); // forward to attachment entry's URL
		final ResponseEntity<StreamingResponseBody> response = new ResponseEntity<>(responseBody, headers, HttpStatus.FOUND);
		return response;
	}

	@NonNull
	public static ResponseEntity<StreamingResponseBody> extractResponseEntryFromLocalFileURL(@NonNull final IDocumentAttachmentEntry attachmentEntry)
	{
		final String contentType = attachmentEntry.getContentType();
		final String filename = attachmentEntry.getFilename();
		URL url = null;
		StreamingResponseBody responseBody = null;
		try
		{
			url = attachmentEntry.getUrl().toURL();
			responseBody = streamFile(url);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
	}

	@NonNull
	private StreamingResponseBody streamFile(@NonNull final URL url) throws IOException
	{
		final Path filePath = FileUtil.getFilePath(url);

		final StreamingResponseBody responseBody = outputStream -> {
			Files.copy(filePath, outputStream);
		};

		return responseBody;
	}
}
