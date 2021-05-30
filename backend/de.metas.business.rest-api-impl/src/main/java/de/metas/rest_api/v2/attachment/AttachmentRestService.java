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
import de.metas.attachments.AttachmentTags;
import de.metas.common.rest_api.v2.attachment.JsonAttachment;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentRequest;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentResponse;
import de.metas.common.rest_api.v2.attachment.JsonExternalReferenceTarget;
import de.metas.common.rest_api.v2.attachment.JsonTag;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.IExternalReferenceType;
import de.metas.externalreference.rest.ExternalReferenceRestControllerService;
import de.metas.organization.OrgId;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.util.Services;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.apache.commons.codec.binary.Base64;
import org.compiere.SpringContextHolder;
import org.compiere.util.MimeType;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

@Service
public class AttachmentRestService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
	private final ExternalReferenceRestControllerService externalReferenceService;
	private final ExternalReferenceTypes externalReferenceTypes;

	public AttachmentRestService(final ExternalReferenceRestControllerService externalReferenceService, final ExternalReferenceTypes externalReferenceTypes)
	{
		this.externalReferenceService = externalReferenceService;
		this.externalReferenceTypes = externalReferenceTypes;
	}

	@NonNull
	public List<JsonAttachmentResponse> createAttachment(@NonNull final JsonAttachmentRequest jsonAttachmentRequest)
	{
		return trxManager.callInNewTrx(() -> createAttachmentWithTrx(jsonAttachmentRequest));
	}

	@NonNull
	private List<JsonAttachmentResponse> createAttachmentWithTrx(@NonNull final JsonAttachmentRequest jsonAttachmentRequest)
	{
		final JsonAttachment attachment = jsonAttachmentRequest.getAttachment();

		final AttachmentTags attachmentTags = extractAttachmentTags(attachment.getTags());

		final byte[] data = Base64.decodeBase64(attachment.getData().getBytes());

		final AttachmentEntryCreateRequest request = AttachmentEntryCreateRequest.builder()
				.type(AttachmentEntry.Type.Data)
				.filename(attachment.getFileName())
				.contentType(MimeType.getExtensionByType(attachment.getMimeType()))
				.tags(attachmentTags)
				.data(data)
				.build();

		final List<TableRecordReference> references = jsonAttachmentRequest.getTargets()
				.stream()
				.map(this::extractTableRecordReference)
				.collect(ImmutableList.toImmutableList());

		final AttachmentEntry entry = attachmentEntryService.createNewAttachment(references, request);

		return jsonAttachmentRequest.getTargets()
				.stream()
				.map(target -> this.createJsonAttachmentResponse(target, entry))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private JsonAttachmentResponse createJsonAttachmentResponse(
			@NonNull final JsonExternalReferenceTarget target,
			@NonNull final AttachmentEntry attachmentEntry)
	{
		return JsonAttachmentResponse.builder()
				.target(target)
				.attachmentId(String.valueOf(attachmentEntry.getId().getRepoId()))
				.filename(attachmentEntry.getFilename())
				.mimeType(attachmentEntry.getMimeType())
				.build();
	}

	@NonNull
	private TableRecordReference extractTableRecordReference(@NonNull final JsonExternalReferenceTarget target)
	{
		final IExternalReferenceType targetType = extractTargetType(target.getExternalReferenceType());
		final MetasfreshId metasfreshId = extractTargetIdentifier(target.getExternalReferenceIdentifier(), targetType);

		return TableRecordReference.of(targetType.getTableName(), metasfreshId.getValue());
	}

	private MetasfreshId extractTargetIdentifier(
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
						.getJsonMetasfreshIdFromExternalReference((OrgId)null, targetIdentifier, externalReferenceType)
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
		final ImmutableMap<String, String> mappedTags = tags != null ?
				tags.stream().collect(ImmutableMap.toImmutableMap(JsonTag::getName, JsonTag::getValue)) : ImmutableMap.of();

		return AttachmentTags.ofMap(mappedTags);
	}
}
