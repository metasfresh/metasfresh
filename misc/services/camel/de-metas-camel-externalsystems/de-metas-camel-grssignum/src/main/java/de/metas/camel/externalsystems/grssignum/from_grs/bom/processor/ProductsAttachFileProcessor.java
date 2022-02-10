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

package de.metas.camel.externalsystems.grssignum.from_grs.bom.processor;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import de.metas.camel.externalsystems.grssignum.GRSSignumConstants;
import de.metas.camel.externalsystems.grssignum.from_grs.bom.PushBOMsRouteContext;
import de.metas.camel.externalsystems.grssignum.to_grs.ExternalIdentifierFormat;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonBOM;
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

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.nio.file.Paths;

import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.EXTERNAL_REF_TYPE_BPARTNER;
import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.EXTERNAL_REF_TYPE_PRODUCT;

public class ProductsAttachFileProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange)
	{
		final PushBOMsRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, GRSSignumConstants.ROUTE_PROPERTY_PUSH_BOMs_CONTEXT, PushBOMsRouteContext.class);

		if (Check.isBlank(context.getExportDirectoriesBasePath()))
		{
			exchange.getIn().setBody(null);
			return;
		}

		final JsonBOM jsonBOM = context.getJsonBOM();

		final JsonAttachmentRequest attachmentRequest = getAttachmentRequest(jsonBOM, context.getExportDirectoriesBasePath());

		exchange.getIn().setBody(attachmentRequest);
	}

	@Nullable
	private JsonAttachmentRequest getAttachmentRequest(
			@NonNull final JsonBOM jsonBOM,
			@NonNull final String basePathForExportDirectories)
	{
		final String filePath = jsonBOM.getAttachmentFilePath();

		if (Check.isBlank(filePath))
		{
			return null;
		}

		final String bpartnerMetasfreshId = jsonBOM.getBPartnerMetasfreshId();

		if (Check.isBlank(bpartnerMetasfreshId))
		{
			throw new RuntimeCamelException("Missing METASFRESHID! ARTNRID=" + jsonBOM.getProductId());
		}

		final Path attachmentPath = Paths.get(basePathForExportDirectories, filePath);

		final JsonAttachment attachment = JsonAttachment.builder()
				.type(JsonAttachmentSourceType.LocalFileURL)
				.fileName(attachmentPath.getFileName().toString())
				.data(attachmentPath.toUri().toString())
				.build();

		final JsonExternalReferenceTarget targetBPartner = JsonExternalReferenceTarget.ofTypeAndId(EXTERNAL_REF_TYPE_BPARTNER, bpartnerMetasfreshId);
		final JsonExternalReferenceTarget targetProduct = JsonExternalReferenceTarget.ofTypeAndId(EXTERNAL_REF_TYPE_PRODUCT, ExternalIdentifierFormat.asExternalIdentifier(jsonBOM.getProductId()));

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
