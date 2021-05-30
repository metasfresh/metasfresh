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

import de.metas.camel.externalsystems.alberta.ProcessorHelper;
import de.metas.camel.externalsystems.alberta.attachment.GetAttachmentRouteConstants;
import de.metas.camel.externalsystems.alberta.attachment.GetAttachmentRouteContext;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.util.EmptyUtil;
import io.swagger.client.ApiException;
import io.swagger.client.api.DocumentApi;
import io.swagger.client.model.Document;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.annotation.Nullable;
import java.util.List;

public class GetDocumentsProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);
		final String createdAfter = request.getParameters().get(ExternalSystemConstants.PARAM_CREATED_AFTER);

		final GetAttachmentRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange, GetAttachmentRouteConstants.ROUTE_PROPERTY_GET_ATTACHMENT_CONTEXT, GetAttachmentRouteContext.class);

		if (EmptyUtil.isBlank(createdAfter))
		{
			throw new RuntimeException("Missing route property: " + createdAfter + " !");
		}

		final List<Document> documents = getDocumentsOrNull(routeContext, createdAfter);

		GetAttachmentRouteContext.builder().documents(documents).build();

		exchange.getIn().setBody(documents);
	}

	@Nullable
	final List<Document> getDocumentsOrNull(
			@NonNull final GetAttachmentRouteContext context,
			@NonNull final String createdAfter) throws ApiException
	{
		final String apiKey = context.getApiKey();
		final DocumentApi documentApi = context.getDocumentApi();

		final var documents = documentApi.getAllDocuments(apiKey, createdAfter);

		return documents == null || documents.isEmpty()
				? null
				: documents;
	}
}
