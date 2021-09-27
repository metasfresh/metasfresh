/*
 * #%L
 * de-metas-camel-alberta-camelroutes
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

package de.metas.camel.externalsystems.alberta.attachment.processor;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.alberta.ProcessorHelper;
import de.metas.camel.externalsystems.alberta.attachment.AttachmentUtil;
import de.metas.camel.externalsystems.alberta.attachment.GetAttachmentRouteConstants;
import de.metas.camel.externalsystems.alberta.attachment.GetAttachmentRouteContext;
import de.metas.camel.externalsystems.alberta.common.AlbertaUtil;
import de.metas.common.rest_api.v2.attachment.JsonAttachment;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentRequest;
import de.metas.common.rest_api.v2.attachment.JsonExternalReferenceTarget;
import de.metas.common.rest_api.v2.attachment.JsonTag;
import de.metas.common.util.EmptyUtil;
import io.swagger.client.ApiException;
import io.swagger.client.api.DocumentApi;
import io.swagger.client.model.Document;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.threeten.bp.OffsetDateTime;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.Instant;
import java.util.Base64;
import java.util.List;

import static de.metas.camel.externalsystems.alberta.common.ExternalIdentifierFormat.formatExternalId;

public class DocumentProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final GetAttachmentRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange, GetAttachmentRouteConstants.ROUTE_PROPERTY_GET_ATTACHMENT_CONTEXT, GetAttachmentRouteContext.class);
		final Document document = routeContext.getDocument();

		final File file = getFile(routeContext, document.getId());
		final byte[] fileData = Files.readAllBytes(file.toPath());

		final String base64FileData = Base64.getEncoder().encodeToString(fileData);

		final String effectiveFileName = AttachmentUtil.appendPDFSuffix(document.getName());

		final JsonAttachment jsonAttachment = JsonAttachment.builder()
				.fileName(effectiveFileName)
				.data(base64FileData)
				.mimeType(GetAttachmentRouteConstants.MIME_TYPE_PDF)
				.tags(computeTags(document))
				.build();

		final JsonAttachmentRequest jsonRequest = JsonAttachmentRequest.builder()
				.orgCode(routeContext.getOrgCode())
				.targets(computeTargets(document))
				.attachment(jsonAttachment)
				.build();

		if (document.getUpdatedAt() != null)
		{
			routeContext.setNextDocumentImportStartDate(AlbertaUtil.asInstant(document.getUpdatedAt()));
		}
		exchange.getIn().setBody(jsonRequest);
	}
	@NonNull
	final File getFile(
			@NonNull final GetAttachmentRouteContext context,
			@NonNull final String id) throws ApiException
	{
		final String apiKey = context.getApiKey();
		final DocumentApi documentApi = context.getDocumentApi();

		final File file = documentApi.getSingleDocument(apiKey, id);

		if (file == null)
		{
			throw new RuntimeException("No data received for document id" + id);
		}

		return file;
	}

	@NonNull
	final List<JsonExternalReferenceTarget> computeTargets(@NonNull final Document document)
	{
		final ImmutableList.Builder<JsonExternalReferenceTarget> targets = ImmutableList.builder();

		final String patientId = document.getPatientId();
		if (!EmptyUtil.isBlank(patientId))
		{
			targets.add(JsonExternalReferenceTarget.ofTypeAndId(GetAttachmentRouteConstants.ESR_TYPE_BPARTNER, formatExternalId(patientId)));
		}

		final String createdBy = document.getCreatedBy();
		if (!EmptyUtil.isBlank(createdBy))
		{
			targets.add(JsonExternalReferenceTarget.ofTypeAndId(GetAttachmentRouteConstants.ESR_TYPE_USERID, formatExternalId(createdBy)));
		}

		final String updatedBy = document.getUpdatedBy();
		if (!EmptyUtil.isBlank(updatedBy)
				&& (EmptyUtil.isBlank(createdBy) || !createdBy.equals(updatedBy)))
		{
			targets.add(JsonExternalReferenceTarget.ofTypeAndId(GetAttachmentRouteConstants.ESR_TYPE_USERID, formatExternalId(updatedBy)));
		}

		final ImmutableList<JsonExternalReferenceTarget> computedTargets = targets.build();

		if (EmptyUtil.isEmpty(computedTargets))
		{
			throw new RuntimeException("Document targets cannot be null! DocumentId: " + document.getId());
		}

		return computedTargets;
	}

	@NonNull
	final List<JsonTag> computeTags(@NonNull final Document document)
	{
		final ImmutableList.Builder<JsonTag> tags = ImmutableList.builder();

		tags.add(JsonTag.of(GetAttachmentRouteConstants.ALBERTA_DOCUMENT_ID, document.getId()));

		if (document.isArchived() != null)
		{
			tags.add(JsonTag.of(GetAttachmentRouteConstants.ALBERTA_DOCUMENT_ARCHIVED, String.valueOf(document.isArchived())));
		}

		final BigDecimal therapyId = document.getTherapyId();
		if (therapyId != null)
		{
			tags.add(JsonTag.of(GetAttachmentRouteConstants.ALBERTA_DOCUMENT_THERAPYID, String.valueOf(therapyId)));
		}

		final BigDecimal therapyTypeId = document.getTherapyTypeId();
		if (therapyTypeId != null)
		{
			tags.add(JsonTag.of(GetAttachmentRouteConstants.ALBERTA_DOCUMENT_THERAPYTYPEID, String.valueOf(therapyTypeId)));
		}

		final OffsetDateTime createdAt = document.getCreatedAt();
		if (createdAt != null)
		{
			final Instant createdAtInstant = AlbertaUtil.asInstant(createdAt);
			tags.add(JsonTag.of(GetAttachmentRouteConstants.ALBERTA_DOCUMENT_CREATEDAT, String.valueOf(createdAtInstant)));
		}

		final OffsetDateTime updatedAt = document.getUpdatedAt();
		if (updatedAt != null)
		{
			final Instant updatedAtInstant = AlbertaUtil.asInstant(updatedAt);
			tags.add(JsonTag.of(GetAttachmentRouteConstants.ALBERTA_DOCUMENT_UPDATEDAT, String.valueOf(updatedAtInstant)));
		}

		tags.add(JsonTag.of(GetAttachmentRouteConstants.ALBERTA_DOCUMENT_ENDPOINT, GetAttachmentRouteConstants.ALBERTA_DOCUMENT_ENDPOINT_VALUE));

		return tags.build();
	}
}