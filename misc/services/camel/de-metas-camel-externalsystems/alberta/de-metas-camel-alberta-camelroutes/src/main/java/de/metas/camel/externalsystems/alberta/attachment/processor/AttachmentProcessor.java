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
import io.swagger.client.api.AttachmentApi;
import io.swagger.client.model.Attachment;
import io.swagger.client.model.AttachmentMetadata;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.threeten.bp.OffsetDateTime;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;

import static de.metas.camel.externalsystems.alberta.common.ExternalIdentifierFormat.formatExternalId;

public class AttachmentProcessor implements Processor
{

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final GetAttachmentRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange, GetAttachmentRouteConstants.ROUTE_PROPERTY_GET_ATTACHMENT_CONTEXT, GetAttachmentRouteContext.class);

		final Attachment attachment = routeContext.getAttachment();

		if(attachment.getMetadata() == null)
		{
			throw new RuntimeException("No attachment metadata received for attachment id=" + attachment.getId());
		}

		final File file = getFile(routeContext, attachment.getId());
		final byte[] fileData = Files.readAllBytes(file.toPath());

		final String base64FileData = Base64.getEncoder().encodeToString(fileData);
		
		final JsonAttachment jsonAttachment = JsonAttachment.builder()
				.fileName(attachment.getFilename())
				.mimeType(attachment.getContentType())
				.data(base64FileData)
				.tags(computeTags(attachment))
				.build();

		final JsonAttachmentRequest jsonRequest = JsonAttachmentRequest.builder()
				.orgCode(routeContext.getOrgCode())
				.targets(computeTargets(attachment.getMetadata()))
				.attachment(jsonAttachment)
				.build();


		if (attachment.getMetadata().getCreatedAt() != null)
		{
			routeContext.setNextAttachmentImportStartDate(AlbertaUtil.asInstant(attachment.getMetadata().getCreatedAt()));
		}

		exchange.getIn().setBody(jsonRequest);
	}

	@NonNull
	final File getFile(
			@NonNull final GetAttachmentRouteContext context,
			@NonNull final String id) throws ApiException
	{
		final String apiKey = context.getApiKey();
		final AttachmentApi attachmentApi = context.getAttachmentApi();

		final File file = attachmentApi.getSingleAttachment(apiKey, id);

		if (file == null)
		{
			throw new RuntimeException("No data received for attachment id" + id);
		}

		return file;
	}

	@NonNull
	final List<JsonExternalReferenceTarget> computeTargets(@NonNull final AttachmentMetadata metadata)
	{
		final ImmutableList.Builder<JsonExternalReferenceTarget> targets = ImmutableList.builder();

		final String createdBy = metadata.getCreatedBy();
		if (!EmptyUtil.isEmpty(createdBy))
		{
			targets.add(JsonExternalReferenceTarget.ofTypeAndId(GetAttachmentRouteConstants.ESR_TYPE_USERID, formatExternalId(createdBy)));
		}

		final String patientId = metadata.getPatientId();
		if (!EmptyUtil.isEmpty(patientId))
		{
			targets.add(JsonExternalReferenceTarget.ofTypeAndId(GetAttachmentRouteConstants.ESR_TYPE_BPARTNER, formatExternalId(patientId)));
		}

		final ImmutableList<JsonExternalReferenceTarget> computedTargets = targets.build();
		if (EmptyUtil.isEmpty(computedTargets))
		{
			throw new RuntimeException("Attachment targets cannot be null!");
		}

		return computedTargets;
	}

	@NonNull
	final List<JsonTag> computeTags(@NonNull final Attachment attachment)
	{
		final ImmutableList.Builder<JsonTag> tags = ImmutableList.builder();

		final AttachmentMetadata metadata = attachment.getMetadata();

		final String id = attachment.getId();
		if (!EmptyUtil.isEmpty(id))
		{
			tags.add(JsonTag.of(GetAttachmentRouteConstants.ALBERTA_ATTACHMENT_ID, id));
		}

		final String uploadDate = attachment.getUploadDate();
		if (!EmptyUtil.isEmpty(uploadDate))
		{
			tags.add(JsonTag.of(GetAttachmentRouteConstants.ALBERTA_ATTACHMENT_UPLOAD_DATE, uploadDate));
		}

		final BigDecimal type = metadata.getType();
		if (type != null)
		{
			tags.add(JsonTag.of(GetAttachmentRouteConstants.ALBERTA_ATTACHMENT_TYPE, String.valueOf(type)));
		}

		final OffsetDateTime createdAt = metadata.getCreatedAt();
		if (createdAt != null)
		{
			tags.add(JsonTag.of(GetAttachmentRouteConstants.ALBERTA_ATTACHMENT_CREATEDAT, String.valueOf(createdAt)));
		}

		tags.add(JsonTag.of(GetAttachmentRouteConstants.ALBERTA_ATTACHMENT_ENDPOINT, GetAttachmentRouteConstants.ALBERTA_ATTACHMENT_ENDPOINT_VALUE));

		return tags.build();
	}
}
