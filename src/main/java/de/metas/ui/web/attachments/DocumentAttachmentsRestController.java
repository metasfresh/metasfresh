package de.metas.ui.web.attachments;

import java.io.IOException;
import java.util.List;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.metas.ui.web.attachments.json.JSONAttachment;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.controller.WindowRestController;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@RestController
@RequestMapping(value = DocumentAttachmentsRestController.ENDPOINT)
public class DocumentAttachmentsRestController
{
	public static final String ENDPOINT = WindowRestController.ENDPOINT + "/{windowId}/{documentId}/attachments";

	@Autowired
	private UserSession userSession;
	@Autowired
	private DocumentDescriptorFactory documentDescriptorFactory;

	private DocumentAttachments getDocumentAttachments(final int adWindowId, final String documentId)
	{
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(DocumentType.Window, adWindowId, documentId);
		final TableRecordReference recordRef = documentDescriptorFactory.getTableRecordReference(documentPath);
		return DocumentAttachments.of(recordRef);
	}

	/**
	 * Attaches a file to given root document.
	 *
	 * @param adWindowId
	 * @param documentId
	 * @param file
	 * @throws IOException
	 */
	@PostMapping
	public void attachFile(
			@PathVariable("windowId") final int adWindowId //
			, @PathVariable("documentId") final String documentId //
			, @RequestParam("file") final MultipartFile file //
	) throws IOException
	{
		userSession.assertLoggedIn();

		getDocumentAttachments(adWindowId, documentId)
				.addEntry(file);
	}

	@GetMapping
	public List<JSONAttachment> getAttachments(
			@PathVariable("windowId") final int adWindowId //
			, @PathVariable("documentId") final String documentId //
	)
	{
		userSession.assertLoggedIn();

		return getDocumentAttachments(adWindowId, documentId)
				.toJson();
	}

	@GetMapping("/{id}")
	public ResponseEntity<byte[]> getAttachmentById(
			@PathVariable("windowId") final int adWindowId //
			, @PathVariable("documentId") final String documentId //
			, @PathVariable("id") final String entryIdStr)
	{
		userSession.assertLoggedIn();

		final DocumentId entryId = DocumentId.of(entryIdStr);
		final IDocumentAttachmentEntry entry = getDocumentAttachments(adWindowId, documentId)
				.getEntry(entryId);

		final String entryFilename = entry.getFilename();
		final byte[] entryData = entry.getData();
		if (entryData == null || entryData.length == 0)
		{
			throw new EntityNotFoundException("No attachment found (ID=" + entryId + ")");
		}

		final String entryContentType = entry.getContentType();

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(entryContentType));
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + entryFilename + "\"");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		final ResponseEntity<byte[]> response = new ResponseEntity<>(entryData, headers, HttpStatus.OK);
		return response;
	}

	@DeleteMapping("/{id}")
	public void deleteAttachmentById(
			@PathVariable("windowId") final int adWindowId //
			, @PathVariable("documentId") final String documentId //
			, @PathVariable("id") final String entryIdStr //
	)
	{
		userSession.assertLoggedIn();

		final DocumentId entryId = DocumentId.of(entryIdStr);
		getDocumentAttachments(adWindowId, documentId)
				.deleteEntry(entryId);
	}

}
