/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.from_grs.attachment.processor;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import de.metas.camel.externalsystems.grssignum.GRSSignumConstants;
import de.metas.camel.externalsystems.grssignum.from_grs.attachment.BPartnerAttachmentsRouteContext;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonBPartnerAttachment;
import de.metas.common.rest_api.v2.attachment.JsonAttachment;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentRequest;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentSourceType;
import de.metas.common.rest_api.v2.attachment.JsonExternalReferenceTarget;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.file.Path;
import java.nio.file.Paths;

import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.EXTERNAL_REF_TYPE_BPARTNER;

public class BPartnerAttachmentsProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange)
	{
		final BPartnerAttachmentsRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, GRSSignumConstants.ROUTE_PROPERTY_ATTACH_FILE_CONTEXT, BPartnerAttachmentsRouteContext.class);

		final JsonBPartnerAttachment jsonBPartnerAttachment = context.getJsonBPartnerAttachment();

		final JsonAttachmentRequest attachmentRequest = getAttachmentRequest(jsonBPartnerAttachment, context.getExportDirectoriesBasePathNotNull());

		exchange.getIn().setBody(attachmentRequest, JsonAttachmentRequest.class);
	}

	@NonNull
	private JsonAttachmentRequest getAttachmentRequest(
			@NonNull final JsonBPartnerAttachment jsonBPartnerAttachment,
			@NonNull final String basePathForExportDirectories)
	{
		final Path attachmentPath = Paths.get(basePathForExportDirectories, jsonBPartnerAttachment.getAttachmentFilePath());

		final JsonAttachment attachment = JsonAttachment.builder()
				.type(JsonAttachmentSourceType.LocalFileURL)
				.fileName(attachmentPath.getFileName().toString())
				.data(attachmentPath.toUri().toString())
				.build();

		return JsonAttachmentRequest.builder()
				.targets(ImmutableList.of(JsonExternalReferenceTarget.ofTypeAndId(EXTERNAL_REF_TYPE_BPARTNER, jsonBPartnerAttachment.getMetasfreshId())))
				.orgCode(getAuthOrgCode())
				.attachment(attachment)
				.build();
	}

	@NonNull
	private String getAuthOrgCode()
	{
		return ((TokenCredentials)SecurityContextHolder.getContext().getAuthentication().getCredentials()).getOrgCode();
	}
}
