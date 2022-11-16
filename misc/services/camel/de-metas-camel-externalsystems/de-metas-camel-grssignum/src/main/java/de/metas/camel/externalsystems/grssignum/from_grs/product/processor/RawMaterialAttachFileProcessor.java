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

package de.metas.camel.externalsystems.grssignum.from_grs.product.processor;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import de.metas.camel.externalsystems.grssignum.from_grs.product.PushRawMaterialsRouteContext;
import de.metas.camel.externalsystems.grssignum.to_grs.ExternalIdentifierFormat;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonProduct;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.attachment.JsonAttachment;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentRequest;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentSourceType;
import de.metas.common.rest_api.v2.attachment.JsonExternalReferenceTarget;
import de.metas.common.rest_api.v2.attachment.JsonTag;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.file.Path;
import java.nio.file.Paths;

import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.EXTERNAL_REF_TYPE_BPARTNER;
import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.EXTERNAL_REF_TYPE_PRODUCT;
import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.ROUTE_PROPERTY_PUSH_RAW_MATERIALS_CONTEXT;

public class RawMaterialAttachFileProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange)
	{
		final de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonAttachment jsonAttachment = exchange.getIn().getBody(de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonAttachment.class);

		final PushRawMaterialsRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_PUSH_RAW_MATERIALS_CONTEXT, PushRawMaterialsRouteContext.class);

		final JsonAttachmentRequest attachmentRequest = getAttachmentRequest(routeContext, jsonAttachment);

		exchange.getIn().setBody(attachmentRequest);
	}

	@NonNull
	private JsonAttachmentRequest getAttachmentRequest(
			@NonNull final PushRawMaterialsRouteContext routeContext,
			@NonNull final de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonAttachment jsonAttachment)
	{
		final JsonMetasfreshId bpartnerMetasfreshId = routeContext.getCurrentBPartnerID();

		if (bpartnerMetasfreshId == null)
		{
			throw new RuntimeCamelException("bpartnerMetasfreshId can not be null at this point!");
		}

		final JsonProduct jsonProduct = routeContext.getJsonProduct();

		final JsonExternalReferenceTarget targetBPartner = JsonExternalReferenceTarget
				.ofTypeAndId(EXTERNAL_REF_TYPE_BPARTNER, String.valueOf(bpartnerMetasfreshId.getValue()));

		final JsonExternalReferenceTarget targetProduct = JsonExternalReferenceTarget
				.ofTypeAndId(EXTERNAL_REF_TYPE_PRODUCT, ExternalIdentifierFormat.asExternalIdentifier(jsonProduct.getProductId()));

		return JsonAttachmentRequest.builder()
				.targets(ImmutableList.of(targetBPartner, targetProduct))
				.orgCode(getAuthOrgCode())
				.attachment(buildJsonAttachment(jsonAttachment))
				.build();
	}

	@NonNull
	private String getAuthOrgCode()
	{
		return ((TokenCredentials)SecurityContextHolder.getContext().getAuthentication().getCredentials()).getOrgCode();
	}

	@NonNull
	private JsonAttachment buildJsonAttachment(@NonNull final de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonAttachment attachment)
	{
		final Path attachmentPath = Paths.get(attachment.getFileName());

		return JsonAttachment.builder()
				.fileName(attachmentPath.getFileName().toString())
				.data(attachmentPath.toUri().toString())
				.type(JsonAttachmentSourceType.LocalFileURL)
				.tags(buildJsonTags(attachment))
				.build();
	}

	@NonNull
	private ImmutableList<JsonTag> buildJsonTags(@NonNull final de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonAttachment attachment)
	{
		final ImmutableList.Builder<JsonTag> jsonTagBuilder = ImmutableList.builder();

		jsonTagBuilder.add(JsonTag.of(AttachmentTags.ID.getName(), attachment.getId()));

		if (Check.isNotBlank(attachment.getValidUntil()))
		{
			jsonTagBuilder.add(JsonTag.of(AttachmentTags.VALID_TO.getName(), attachment.getValidUntil()));
		}

		if (Check.isNotBlank(attachment.getDocumentType()))
		{
			jsonTagBuilder.add(JsonTag.of(AttachmentTags.DOCUMENT_TYPE.getName(), attachment.getDocumentType()));
		}

		if (Check.isNotBlank(attachment.getDocumentGroup()))
		{
			jsonTagBuilder.add(JsonTag.of(AttachmentTags.DOCUMENT_GROUP.getName(), attachment.getDocumentGroup()));
		}

		return jsonTagBuilder.build();
	}
}
