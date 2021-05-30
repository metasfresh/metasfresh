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
import de.metas.camel.externalsystems.alberta.attachment.GetAttachmentRouteConstants;
import de.metas.camel.externalsystems.alberta.attachment.GetAttachmentRouteContext;
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

import javax.annotation.Nullable;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DocumentProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final GetAttachmentRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange, GetAttachmentRouteConstants.ROUTE_PROPERTY_GET_ATTACHMENT_CONTEXT, GetAttachmentRouteContext.class);
		final Document document = exchange.getIn().getBody(Document.class);

		final File file = getDataOrNull(routeContext, document.getId());

		if (EmptyUtil.isEmpty(file))
		{
			throw new RuntimeException("No data received for document id" + document.getId());
		}

		final String data = new String(Files.readAllBytes(file.toPath()));  //todo florina

		final JsonAttachment jsonAttachment = JsonAttachment.builder()
				.fileName(document.getName())
				.mimeType(Files.probeContentType(Path.of(document.getName())))
				.data(data)
				.tags(computeTags(document))
				.build();

		final JsonAttachmentRequest jsonRequest = JsonAttachmentRequest.builder()
				.targets(computeTargets(document))
				.attachment(jsonAttachment)
				.build();

		exchange.getIn().setBody(jsonRequest);
	}

	@Nullable
	final File getDataOrNull(
			@NonNull final GetAttachmentRouteContext context,
			@NonNull final String id) throws ApiException
	{
		final String apiKey = context.getApiKey();
		final DocumentApi documentApi = context.getDocumentApi();

		return documentApi.getSingleDocument(apiKey, id);
	}

	@NonNull
	final List<JsonExternalReferenceTarget> computeTargets(@NonNull final Document document)
	{
		final ImmutableList.Builder<JsonExternalReferenceTarget> targets = ImmutableList.builder();

		final String patientId = document.getPatientId();
		if (!EmptyUtil.isBlank(patientId))
		{
			targets.add(mapJsonExternalReferenceTarget(patientId, GetAttachmentRouteConstants.ESR_TYPE_BPARTNER));
		}

		final String createdBy = document.getCreatedBy();
		if (!EmptyUtil.isBlank(createdBy))
		{
			targets.add(mapJsonExternalReferenceTarget(createdBy, GetAttachmentRouteConstants.ESR_TYPE_USERID));
		}

		final String updatedBy = document.getUpdatedBy();
		if (!EmptyUtil.isBlank(updatedBy))
		{
			targets.add(mapJsonExternalReferenceTarget(updatedBy, GetAttachmentRouteConstants.ESR_TYPE_USERID));
		}

		return targets.build();
	}

	@NonNull
	final List<JsonTag> computeTags(@NonNull final Document document)
	{
		final ImmutableList.Builder<JsonTag> tags = ImmutableList.builder();

		final String id = document.getId();
		if (!EmptyUtil.isEmpty(id))
		{
			tags.add(mapJsonTag(GetAttachmentRouteConstants.ALBERTA_DOCUMENT_ID, id));
		}

		final Boolean archived = document.isArchived();
		if (!EmptyUtil.isEmpty(archived))
		{
			tags.add(mapJsonTag(GetAttachmentRouteConstants.ALBERTA_DOCUMENT_ARCHIVED, String.valueOf(archived)));
		}

		final BigDecimal therapyId = document.getTherapyId();
		if (!EmptyUtil.isEmpty(therapyId))
		{
			tags.add(mapJsonTag(GetAttachmentRouteConstants.ALBERTA_DOCUMENT_THERAPYID, String.valueOf(therapyId)));
		}

		final BigDecimal therapyTypeId = document.getTherapyTypeId();
		if (!EmptyUtil.isEmpty(therapyTypeId))
		{
			tags.add(mapJsonTag(GetAttachmentRouteConstants.ALBERTA_DOCUMENT_THERAPYTYPEID, String.valueOf(therapyTypeId)));
		}

		final OffsetDateTime createdAt = document.getCreatedAt();
		if (!EmptyUtil.isEmpty(createdAt))
		{
			tags.add(mapJsonTag(GetAttachmentRouteConstants.ALBERTA_DOCUMENT_CREATEDAT, String.valueOf(createdAt)));
		}

		final OffsetDateTime updatedAt = document.getUpdatedAt();
		if (!EmptyUtil.isEmpty(updatedAt))
		{
			tags.add(mapJsonTag(GetAttachmentRouteConstants.ALBERTA_DOCUMENT_UPDATEDAT, String.valueOf(updatedAt)));
		}

		tags.add(mapJsonTag(GetAttachmentRouteConstants.ALBERTA_DOCUMENT_ENDPOINT, GetAttachmentRouteConstants.ALBERTA_DOCUMENT_ENDPOINT_VALUE));

		return tags.build();
	}

	@NonNull
	final JsonExternalReferenceTarget mapJsonExternalReferenceTarget(
			@NonNull final String identifier,
			@NonNull final String type)
	{
		return JsonExternalReferenceTarget.builder()
				.externalReferenceIdentifier(identifier)
				.externalReferenceType(type)
				.build();
	}

	@NonNull
	final JsonTag mapJsonTag(@NonNull final String name, @NonNull final String value)
	{
		return JsonTag.builder()
				.name(name)
				.value(value)
				.build();
	}
}