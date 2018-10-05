package de.metas.vertical.healthcare.forum_datenaustausch_ch.rest;

import java.io.IOException;
import java.util.List;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentEntryService;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandQuery;
import de.metas.ordercandidate.api.OLCandRepository;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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
@RequestMapping(AddPdfAttachmentController.ENDPOINT)
@Profile(Profiles.PROFILE_App)
@Api(value = "forum-datenaustausch.ch XML endpoint")
public class AddPdfAttachmentController
{
	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/sales/order";

	private final AttachmentEntryService attachmentEntryService;

	private OLCandRepository olCandRepository;

	public AddPdfAttachmentController(
			@NonNull final OLCandRepository olCandRepository,
			@NonNull final AttachmentEntryService attachmentEntryService)
	{
		this.attachmentEntryService = attachmentEntryService;
		this.olCandRepository = olCandRepository;
	}

	// // TODO: only return attachments that were added using this endpoint
	// @GetMapping("/attachedPdfFiles/{externalOrderId}")
	// @ApiOperation(value = "Retrieve meta-infos about all attachments that where uploaded with a given externalOrderId")
	// public List<JsonAttachment> getAttachments(@PathVariable("externalOrderId") final String externalOrderId)
	// {
	// final ImmutableList<TableRecordReference> olCandReferences = extractOLCandRecordReferences(externalOrderId);
	//
	// return olCandReferences
	// .stream()
	// .flatMap(referencedRecord -> attachmentEntryService.getByReferencedRecord(referencedRecord).stream())
	// .map(attachmentEntry -> toJsonAttachment(externalOrderId, attachmentEntry))
	// .collect(ImmutableList.toImmutableList());
	// }

	@PostMapping("/attachedPdfFiles/{salesOrderId}")
	@ApiOperation(value = "Attach a PDF document to an externalOrderId")
	// TODO only allow PDF
	public JsonAttachment attachPdfFile(
			@PathVariable("externalOrderId") final String externalOrderId,
			@RequestParam("file") @NonNull final MultipartFile file)
			throws IOException
	{
		final ImmutableList<TableRecordReference> olCandReferences = extractOLCandRecordReferences(externalOrderId);
		if (olCandReferences.isEmpty())
		{
			throw new OLCandsNotFoundExeception();
		}

		final String name = file.getOriginalFilename();
		final byte[] data = file.getBytes();

		final AttachmentEntryCreateRequest request = AttachmentEntryCreateRequest.fromByteArray(name, data);
		final AttachmentEntry entry = attachmentEntryService.createNewAttachment(olCandReferences, request);

		return toJsonAttachment(externalOrderId, entry);
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No order line candidates were found for the given externalOrderId")
	public static class OLCandsNotFoundExeception extends RuntimeException
	{
		private static final long serialVersionUID = 8216181888558013882L;
	}

	private ImmutableList<TableRecordReference> extractOLCandRecordReferences(@NonNull final String externalReference)
	{
		final OLCandQuery query = OLCandQuery.builder()
				.inputDataSourceName(XmlToOLCandsService.INPUT_SOURCE_INTERAL_NAME)
				.externalReference(externalReference)
				.build();
		final List<OLCand> olCands = olCandRepository.getByQuery(query);

		final ImmutableList<TableRecordReference> olCandReferences = olCands
				.stream()
				.map(olCand -> TableRecordReference.of(I_C_OLCand.Table_Name, olCand.getId()))
				.collect(ImmutableList.toImmutableList());
		return olCandReferences;
	}

	private JsonAttachment toJsonAttachment(
			@NonNull final String externalOrderId,
			@NonNull final AttachmentEntry entry)
	{
		final String attachmentId = Integer.toString(entry.getId().getRepoId());

		return JsonAttachment.builder()
				.externalOrderId(externalOrderId)
				.attachmentId(attachmentId)
				.type(entry.getType())
				.filename(entry.getFilename())
				.contentType(entry.getContentType())
				.url(entry.getUrl() != null ? entry.getUrl().toString() : null)
				.build();
	}
}
