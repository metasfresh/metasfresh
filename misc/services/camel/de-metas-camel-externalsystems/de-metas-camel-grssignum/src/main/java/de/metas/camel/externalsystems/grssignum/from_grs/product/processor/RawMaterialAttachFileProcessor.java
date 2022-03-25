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
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonBPartnerProduct;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonProduct;
import de.metas.common.rest_api.v2.attachment.JsonAttachment;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentRequest;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentSourceType;
import de.metas.common.rest_api.v2.attachment.JsonExternalReferenceTarget;
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
		final JsonBPartnerProduct jsonBPartnerProduct = exchange.getIn().getBody(JsonBPartnerProduct.class);

		final PushRawMaterialsRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_PUSH_RAW_MATERIALS_CONTEXT, PushRawMaterialsRouteContext.class);

		final JsonAttachmentRequest attachmentRequest = getAttachmentRequest(routeContext, jsonBPartnerProduct);

		exchange.getIn().setBody(attachmentRequest);
	}

	private JsonAttachmentRequest getAttachmentRequest(
			@NonNull final PushRawMaterialsRouteContext routeContext,
			@NonNull final JsonBPartnerProduct jsonBPartnerProduct)
	{
		final String filePath = jsonBPartnerProduct.getAttachmentFilePath();

		if (Check.isBlank(filePath))
		{
			throw new RuntimeCamelException("JsonBPartnerProduct.ANHANGDATEI can not be missing at this point!");
		}

		final String bpartnerMetasfreshId = jsonBPartnerProduct.getBPartnerMetasfreshId();

		if (Check.isBlank(bpartnerMetasfreshId))
		{
			throw new RuntimeCamelException("JsonBPartnerProduct.METASFRESHID can not be null at this point!");
		}

		final Path attachmentPath = Paths.get(routeContext.getBasePathForExportDirectoriesNotNull(), filePath);

		final JsonAttachment attachment = JsonAttachment.builder()
				.type(JsonAttachmentSourceType.LocalFileURL)
				.fileName(attachmentPath.getFileName().toString())
				.data(attachmentPath.toUri().toString())
				.build();

		final JsonProduct jsonProduct = routeContext.getJsonProduct();

		final JsonExternalReferenceTarget targetBPartner = JsonExternalReferenceTarget.ofTypeAndId(EXTERNAL_REF_TYPE_BPARTNER, bpartnerMetasfreshId);
		final JsonExternalReferenceTarget targetProduct = JsonExternalReferenceTarget
				.ofTypeAndId(EXTERNAL_REF_TYPE_PRODUCT, ExternalIdentifierFormat.asExternalIdentifier(jsonProduct.getProductId()));

		return JsonAttachmentRequest.builder()
				.targets(ImmutableList.of(targetBPartner, targetProduct))
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
