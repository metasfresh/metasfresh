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
import io.swagger.client.api.AttachmentApi;
import io.swagger.client.model.Attachment;
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

public class AttachmentProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final GetAttachmentRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange, GetAttachmentRouteConstants.ROUTE_PROPERTY_GET_ATTACHMENT_CONTEXT, GetAttachmentRouteContext.class);

		final Attachment attachment = exchange.getIn().getBody(Attachment.class);

		final File file = getDataOrNull(routeContext, attachment.getId());

		//todo florina here
		if (EmptyUtil.isEmpty(file))
		{
			throw new RuntimeException("No data received for attachment id" + attachment.getId());
		}

		final String data = new String(Files.readAllBytes(file.toPath()));  //todo florina

		final JsonAttachment jsonAttachment = JsonAttachment.builder()
				.fileName(file.getName())
				.mimeType(Files.probeContentType(Path.of(file.getName())))
				.data(data)
				.tags(computeTags(attachment))
				.build();

		final JsonAttachmentRequest jsonRequest = JsonAttachmentRequest.builder()
				.targets(computeTargets(attachment))
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
		final AttachmentApi attachmentApi = context.getAttachmentApi();

		return attachmentApi.getSingleAttachment(apiKey, id);
	}

	@NonNull
	final List<JsonExternalReferenceTarget> computeTargets(@NonNull final Attachment attachment)
	{
		final ImmutableList.Builder<JsonExternalReferenceTarget> targets = ImmutableList.builder();

		final String id = attachment.getId();
		targets.add(mapJsonExternalReferenceTarget(id));

		return targets.build();
	}

	@NonNull
	final List<JsonTag> computeTags(@NonNull final Attachment attachment)
	{
		final ImmutableList.Builder<JsonTag> tags = ImmutableList.builder();

		final String id = attachment.getId();
		if (!EmptyUtil.isEmpty(id))
		{
			tags.add(mapJsonTag(GetAttachmentRouteConstants.ALBERTA_ATTACHMENT_ID, id));
		}

		final String timestamp = attachment.getTimestamp();
		if (!EmptyUtil.isEmpty(timestamp))
		{
			tags.add(mapJsonTag(GetAttachmentRouteConstants.ALBERTA_ATTACHMENT_TIMESTAMP, timestamp));
		}

		final BigDecimal type = attachment.getType();
		if (!EmptyUtil.isEmpty(type))
		{
			tags.add(mapJsonTag(GetAttachmentRouteConstants.ALBERTA_ATTACHMENT_TYPE, String.valueOf(type)));
		}

		final OffsetDateTime createdAt = attachment.getCreatedAt();
		if (!EmptyUtil.isEmpty(createdAt))
		{
			tags.add(mapJsonTag(GetAttachmentRouteConstants.ALBERTA_ATTACHMENT_CREATEDAT, String.valueOf(createdAt)));
		}

		tags.add(mapJsonTag(GetAttachmentRouteConstants.ALBERTA_ATTACHMENT_ENDPOINT, GetAttachmentRouteConstants.ALBERTA_ATTACHMENT_ENDPOINT_VALUE));

		return tags.build();
	}

	@NonNull
	final JsonExternalReferenceTarget mapJsonExternalReferenceTarget(
			@NonNull final String identifier)
	{
		return JsonExternalReferenceTarget.builder()
				.externalReferenceIdentifier(identifier)
				.externalReferenceType(GetAttachmentRouteConstants.ESR_TYPE_USERID)
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
