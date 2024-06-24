/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.rest_api.v2.attachment;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.AttachmentEntryType;
import de.metas.attachments.AttachmentTags;
import de.metas.common.rest_api.v2.attachment.JsonAttachment;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentRequest;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentResponse;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentSourceType;
import de.metas.common.rest_api.v2.attachment.JsonExternalReferenceTarget;
import de.metas.common.rest_api.v2.attachment.JsonTableRecordReference;
import de.metas.common.rest_api.v2.attachment.JsonTag;
import de.metas.common.util.FileUtil;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.IExternalReferenceType;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.rest_api.v2.util.JsonConverters;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.MimeType;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;

@Service
public class AttachmentRestService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final AttachmentEntryService attachmentEntryService;
	private final ExternalReferenceRestControllerService externalReferenceService;
	private final ExternalReferenceTypes externalReferenceTypes;

	public AttachmentRestService(
			@NonNull final AttachmentEntryService attachmentEntryService,
			@NonNull final ExternalReferenceRestControllerService externalReferenceService,
			@NonNull final ExternalReferenceTypes externalReferenceTypes)
	{
		this.attachmentEntryService = attachmentEntryService;
		this.externalReferenceService = externalReferenceService;
		this.externalReferenceTypes = externalReferenceTypes;
	}

	@NonNull
	public JsonAttachmentResponse createAttachment(@NonNull final JsonAttachmentRequest jsonAttachmentRequest)
	{
		return trxManager.callInNewTrx(() -> createAttachmentWithTrx(jsonAttachmentRequest));
	}

	@NonNull
	private JsonAttachmentResponse createAttachmentWithTrx(@NonNull final JsonAttachmentRequest jsonAttachmentRequest) throws IOException
	{
		final JsonAttachment attachment = jsonAttachmentRequest.getAttachment();

		final AttachmentTags attachmentTags = extractAttachmentTags(attachment.getTags());

		final JsonAttachmentSourceType jsonType = attachment.getType();

		final AttachmentEntryType type = JsonConverters.toAttachmentType(jsonType);
		byte[] data = null;
		URI url = null;
		switch (type)
		{
			case Data:
				data = Base64.getDecoder().decode(attachment.getData().getBytes());
				break;
			case URL:
				url = URI.create(attachment.getData());
				break;
			case LocalFileURL:
				url = URI.create(attachment.getData());
				validateLocalFileURL(url.toURL());
				break;
			default:
				throw new AdempiereException("Unknown AttachmentEntryType = " + type);
		}

		final String contentType = attachment.getMimeType() != null
				? attachment.getMimeType()
				: MimeType.getMimeType(attachment.getFileName());

		final AttachmentEntryCreateRequest request = AttachmentEntryCreateRequest.builder()
				.type(type)
				.filename(attachment.getFileName())
				.contentType(contentType)
				.tags(attachmentTags)
				.data(data)
				.url(url)
				.build();

		final List<TableRecordReference> references = extractTableRecordReferences(jsonAttachmentRequest);

		final AttachmentEntry entry = attachmentEntryService.createNewAttachment(references, request);

		return JsonAttachmentResponse.builder()
				.target(jsonAttachmentRequest.getTargets())
				.attachmentId(String.valueOf(entry.getId().getRepoId()))
				.filename(entry.getFilename())
				.mimeType(entry.getMimeType())
				.build();
	}

	@NonNull
	private TableRecordReference extractTableRecordReference(
			@NonNull final String orgCode,
			@NonNull final JsonExternalReferenceTarget target)
	{
		final IExternalReferenceType targetType = extractTargetType(target.getExternalReferenceType());
		final MetasfreshId metasfreshId = extractTargetIdentifier(orgCode, target.getExternalReferenceIdentifier(), targetType);

		return TableRecordReference.of(targetType.getTableName(), metasfreshId.getValue());
	}

	private MetasfreshId extractTargetIdentifier(
			@NonNull final String orgCode,
			@NonNull final String externalTargetIdentifier,
			@NonNull final IExternalReferenceType externalReferenceType)
	{

		final ExternalIdentifier targetIdentifier = ExternalIdentifier.of(externalTargetIdentifier);

		switch (targetIdentifier.getType())
		{
			case METASFRESH_ID:
				return targetIdentifier.asMetasfreshId();
			case EXTERNAL_REFERENCE:
				return externalReferenceService
						.getJsonMetasfreshIdFromExternalReference(orgCode, targetIdentifier, externalReferenceType)
						.map(MetasfreshId::of)
						.orElseThrow(() -> MissingResourceException.builder()
								.resourceIdentifier(targetIdentifier.getRawValue())
								.resourceName(externalReferenceType.getTableName())
								.build());
			default:
				throw new InvalidIdentifierException(targetIdentifier.getRawValue());
		}
	}

	@NonNull
	private IExternalReferenceType extractTargetType(@NonNull final String externalReferenceType)
	{
		return externalReferenceTypes.ofCode(externalReferenceType)
				.orElseThrow(() -> new AdempiereException("Unknown Type=")
						.appendParametersToMessage()
						.setParameter("externalReferenceType", externalReferenceType));
	}

	@NonNull
	private AttachmentTags extractAttachmentTags(@Nullable final List<JsonTag> tags)
	{
		if (tags == null || Check.isEmpty(tags))
		{
			return AttachmentTags.EMPTY;
		}

		final ImmutableMap<String, String> tagName2Value = tags.stream()
				.collect(ImmutableMap.toImmutableMap(JsonTag::getName, JsonTag::getValue));

		return AttachmentTags.ofMap(tagName2Value);
	}

	@NonNull
	private List<TableRecordReference> extractTableRecordReferences(@NonNull final JsonAttachmentRequest request)
	{
		final ImmutableList.Builder<TableRecordReference> tableRecordReferenceBuilder = ImmutableList.builder();

		request.getTargets()
				.stream()
				.map(target -> extractTableRecordReference(request.getOrgCode(), target))
				.forEach(tableRecordReferenceBuilder::add);

		request.getReferences()
				.stream()
				.map(AttachmentRestService::extractTableRecordReference)
				.forEach(tableRecordReferenceBuilder::add);

		return tableRecordReferenceBuilder.build();
	}

	@NonNull
	private static TableRecordReference extractTableRecordReference(@NonNull final JsonTableRecordReference reference)
	{
		return TableRecordReference.of(reference.getTableName(), reference.getRecordId().getValue());
	}

	private static void validateLocalFileURL(@NonNull final URL url)
	{
		if (!url.getProtocol().equals("file"))
		{
			throw new AdempiereException("Protocol " + url.getProtocol() + " not supported!");
		}

		final Path filePath = FileUtil.getFilePath(url);

		if (!filePath.toFile().isFile())
		{
			throw new AdempiereException("Provided local file with URL: " + url + " is not accessible!")
					.appendParametersToMessage()
					.setParameter("ParsedPath", filePath.toString());
		}
	}
}
