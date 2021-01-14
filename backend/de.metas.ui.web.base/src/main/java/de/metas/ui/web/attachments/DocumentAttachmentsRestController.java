package de.metas.ui.web.attachments;

import com.google.common.collect.ImmutableList;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.listener.TableAttachmentListenerService;
import de.metas.security.IUserRolePermissions;
import de.metas.ui.web.attachments.json.JSONAttachURLRequest;
import de.metas.ui.web.attachments.json.JSONAttachment;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.controller.WindowRestController;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.events.DocumentWebsocketPublisher;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

	private UserSession userSession;

	private DocumentDescriptorFactory documentDescriptorFactory;

	private DocumentWebsocketPublisher websocketPublisher;

	private AttachmentEntryService attachmentEntryService;

	private TableAttachmentListenerService tableAttachmentListenerService;

	public DocumentAttachmentsRestController(final UserSession userSession,
			final DocumentDescriptorFactory documentDescriptorFactory,
			final DocumentWebsocketPublisher websocketPublisher,
			final AttachmentEntryService attachmentEntryService,
			final TableAttachmentListenerService tableAttachmentListenerService)
	{
		this.userSession = userSession;
		this.documentDescriptorFactory = documentDescriptorFactory;
		this.websocketPublisher = websocketPublisher;
		this.attachmentEntryService = attachmentEntryService;
		this.tableAttachmentListenerService = tableAttachmentListenerService;
	}

	private DocumentAttachments getDocumentAttachments(final String windowIdStr, final String documentId)
	{
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(WindowId.fromJson(windowIdStr), documentId);
		return getDocumentAttachments(documentPath);
	}

	private DocumentAttachments getDocumentAttachments(final DocumentPath documentPath)
	{
		if (documentPath.isComposedKey())
		{
			throw new AdempiereException("Document does not support attachments")
					.setParameter("technicalReason", "documents with composed keys are not handled");
		}

		final TableRecordReference recordRef = documentDescriptorFactory.getTableRecordReference(documentPath);

		return DocumentAttachments.builder()
				.documentPath(documentPath)
				.recordRef(recordRef)
				.entityDescriptor(documentDescriptorFactory.getDocumentEntityDescriptor(documentPath))
				.websocketPublisher(websocketPublisher)
				.tableAttachmentListenerService(tableAttachmentListenerService)
				.attachmentEntryService(attachmentEntryService)
				.build();
	}

	/**
	 * Attaches a file to given root document.
	 */
	@PostMapping
	public void attachFile(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentId //
			, @RequestParam("file") final MultipartFile file //
	) throws IOException
	{
		userSession.assertLoggedIn();

		getDocumentAttachments(windowIdStr, documentId)
				.addEntry(file);
	}

	@PostMapping("/addUrl")
	public void attachURL(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("documentId") final String documentId,
			@RequestBody final JSONAttachURLRequest request)
	{
		userSession.assertLoggedIn();

		getDocumentAttachments(windowIdStr, documentId)
				.addURLEntry(request.getName(), request.getUri());
	}

	@GetMapping
	public List<JSONAttachment> getAttachments(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentId //
	)
	{
		userSession.assertLoggedIn();

		final DocumentPath documentPath = DocumentPath.rootDocumentPath(WindowId.fromJson(windowIdStr), documentId);
		if (documentPath.isComposedKey())
		{
			// document with composed keys does not support attachments
			return ImmutableList.of();
		}

		final boolean allowDelete = isAllowDeletingAttachments();

		final List<JSONAttachment> attachments = getDocumentAttachments(documentPath)
				.toJson();
		attachments.forEach(attachment -> attachment.setAllowDelete(allowDelete));

		return attachments;
	}

	private boolean isAllowDeletingAttachments()
	{
		return userSession
				.getUserRolePermissions()
				.hasPermission(IUserRolePermissions.PERMISSION_IsAttachmentDeletionAllowed);
	}

	@GetMapping("/{id}")
	public ResponseEntity<byte[]> getAttachmentById(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentId //
			, @PathVariable("id") final String entryIdStr)
	{
		userSession.assertLoggedIn();

		final DocumentId entryId = DocumentId.of(entryIdStr);
		final IDocumentAttachmentEntry entry = getDocumentAttachments(windowIdStr, documentId)
				.getEntry(entryId);

		final AttachmentEntry.Type type = entry.getType();
		if (type == AttachmentEntry.Type.Data)
		{
			return extractResponseEntryFromData(entry);
		}
		else if (type == AttachmentEntry.Type.URL)
		{
			return extractResponseEntryFromURL(entry);
		}
		else
		{
			throw new AdempiereException("Invalid attachment entry")
					.setParameter("reason", "invalid type")
					.setParameter("type", type)
					.setParameter("entry", entry);
		}
	}

	private static ResponseEntity<byte[]> extractResponseEntryFromData(@NonNull final IDocumentAttachmentEntry entry)
	{
		final String entryFilename = entry.getFilename();
		final byte[] entryData = entry.getData();
		if (entryData == null || entryData.length == 0)
		{
			throw new EntityNotFoundException("No attachment found")
					.setParameter("entry", entry)
					.setParameter("reason", "data is null or empty");
		}

		final String entryContentType = entry.getContentType();

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(entryContentType));
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + entryFilename + "\"");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		final ResponseEntity<byte[]> response = new ResponseEntity<>(entryData, headers, HttpStatus.OK);
		return response;
	}

	private static ResponseEntity<byte[]> extractResponseEntryFromURL(@NonNull final IDocumentAttachmentEntry entry)
	{
		final HttpHeaders headers = new HttpHeaders();
		headers.setLocation(entry.getUrl()); // forward to attachment entry's URL
		final ResponseEntity<byte[]> response = new ResponseEntity<>(new byte[] {}, headers, HttpStatus.FOUND);
		return response;
	}

	@DeleteMapping("/{id}")
	public void deleteAttachmentById(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentId //
			, @PathVariable("id") final String entryIdStr //
	)
	{
		userSession.assertLoggedIn();

		if (!isAllowDeletingAttachments())
		{
			throw new AdempiereException("Delete not allowed");
		}
		final DocumentId entryId = DocumentId.of(entryIdStr);
		getDocumentAttachments(windowIdStr, documentId)
				.deleteEntry(entryId);
	}

}
