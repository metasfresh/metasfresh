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

package de.metas.camel.externalsystems.alberta.attachment;

import de.metas.camel.externalsystems.alberta.attachment.processor.AttachmentProcessor;
import de.metas.camel.externalsystems.alberta.attachment.processor.DocumentProcessor;
import de.metas.camel.externalsystems.alberta.attachment.processor.GetAttachmentProcessor;
import de.metas.camel.externalsystems.alberta.attachment.processor.GetDocumentsProcessor;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.util.CoalesceUtil;
import io.swagger.client.ApiClient;
import io.swagger.client.api.AttachmentApi;
import io.swagger.client.api.DocumentApi;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class GetAlbertaAttachmentRoute extends RouteBuilder
{
	public static final String GET_DOCUMENTS_ROUTE_ID = "Alberta-getDocuments";
	public static final String GET_ATTACHMENTS_ROUTE_ID = "Alberta-getAttachments";

	public static final String RETRIEVE_DOCUMENTS_PROCESSOR_ID = "Alberta-GetDocumentsFromAlbertaProcessorId";
	public static final String RETRIEVE_ATTACHMENTS_PROCESSOR_ID = "Alberta-GetAttachmentsFromAlbertaProcessorId";
	public static final String PREPARE_DOCUMENT_API_PROCESSOR_ID = "Alberta-CreateDocumentRequestProcessorId";
	public static final String PREPARE_ATTACHMENT_API_PROCESSOR_ID = "Alberta-CreateAttachmentRequestProcessorId";

	public static final String DOCUMENT_PROCESSOR_ID = "Alberta-DocumentProcessor";
	public static final String ATTACHMENT_PROCESSOR_ID = "Alberta-AttachmentProcessor";

	@Override
	public void configure() throws Exception
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		//@formatter:off
		from(direct(GET_DOCUMENTS_ROUTE_ID))
				.routeId(GET_DOCUMENTS_ROUTE_ID)
				.streamCaching()
				.log("Route invoked!")
				.process(this::prepareContext)
				.process(new GetDocumentsProcessor()).id(RETRIEVE_DOCUMENTS_PROCESSOR_ID)
				.choice()
					.when(body().isNull())
						.log(LoggingLevel.DEBUG, "No documents received")
					.otherwise()
						.split(body())
							.stopOnException()
							.to(direct(DOCUMENT_PROCESSOR_ID))
						.end()
				.endChoice()
				.to(direct(GET_ATTACHMENTS_ROUTE_ID));

		from(direct(DOCUMENT_PROCESSOR_ID))
				.routeId(DOCUMENT_PROCESSOR_ID)
				.log("Route invoked!")
				.process(new DocumentProcessor()).id(PREPARE_DOCUMENT_API_PROCESSOR_ID)
				.split(body())
					.stopOnException()
					.log(LoggingLevel.DEBUG, "Calling metasfresh-api to save attachment: ${body}")
					.to(direct(ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID))
				.end();

		from(direct(GET_ATTACHMENTS_ROUTE_ID))
				.routeId(GET_ATTACHMENTS_ROUTE_ID)
				.log("Route invoked!")
				.process(new GetAttachmentProcessor()).id(RETRIEVE_ATTACHMENTS_PROCESSOR_ID)
				.choice()
					.when(body().isNull())
						.log(LoggingLevel.DEBUG, "No attachments received")
					.otherwise()
						.split(body())
							.stopOnException()
							.to(direct(ATTACHMENT_PROCESSOR_ID))
						.end()
				.endChoice();

		from(direct(ATTACHMENT_PROCESSOR_ID))
				.routeId(ATTACHMENT_PROCESSOR_ID)
				.log("Route invoked!")
				.process(new AttachmentProcessor()).id(PREPARE_ATTACHMENT_API_PROCESSOR_ID)
				.split(body())
					.stopOnException()
					.log(LoggingLevel.DEBUG, "Calling metasfresh-api to save attachment: ${body}")
					.to(direct(ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID))
				.end();
		//@formatter:on
	}

	private void prepareContext(@NonNull final Exchange exchange)
	{
		final var request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final var basePath = request.getParameters().get(ExternalSystemConstants.PARAM_BASE_PATH);
		final var apiKey = request.getParameters().get(ExternalSystemConstants.PARAM_API_KEY);

		final var apiClient = new ApiClient().setBasePath(basePath);

		final String createdAfter = CoalesceUtil.coalesceNotNull(
				request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER),
				Instant.ofEpochMilli(0).toString());

		final GetAttachmentRouteContext context = GetAttachmentRouteContext.builder()
				.apiKey(apiKey)
				.attachmentApi(new AttachmentApi(apiClient))
				.documentApi(new DocumentApi(apiClient))
				.createdAfter(createdAfter)
				.build();

		exchange.setProperty(GetAttachmentRouteConstants.ROUTE_PROPERTY_GET_ATTACHMENT_CONTEXT, context);
	}
}
