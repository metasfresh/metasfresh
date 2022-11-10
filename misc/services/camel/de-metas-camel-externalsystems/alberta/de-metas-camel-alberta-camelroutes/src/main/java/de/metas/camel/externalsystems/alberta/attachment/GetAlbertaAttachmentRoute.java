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
import de.metas.camel.externalsystems.alberta.attachment.processor.AttachmentRuntimeParametersProcessor;
import de.metas.camel.externalsystems.alberta.attachment.processor.DocumentProcessor;
import de.metas.camel.externalsystems.alberta.attachment.processor.DocumentRuntimeParametersProcessor;
import de.metas.camel.externalsystems.alberta.attachment.processor.GetAttachmentProcessor;
import de.metas.camel.externalsystems.alberta.attachment.processor.GetDocumentsProcessor;
import de.metas.camel.externalsystems.alberta.common.AlbertaApiProvider;
import de.metas.camel.externalsystems.alberta.common.DataMapper;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.EmptyUtil;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.UserApi;
import io.swagger.client.model.Attachment;
import io.swagger.client.model.Document;
import io.swagger.client.model.Users;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class GetAlbertaAttachmentRoute extends RouteBuilder
{
	public static final String GET_DOCUMENTS_ROUTE_ID = "Alberta-getDocuments";
	public static final String GET_ATTACHMENTS_ROUTE_ID = "Alberta-getAttachments";
	public static final String UPSERT_DOCUMENT_RUNTIME_PARAMS_ROUTE_ID = "Alberta-upsertDocumentRuntimeParams";
	public static final String UPSERT_ATTACHMENT_RUNTIME_PARAMS_ROUTE_ID = "Alberta-upsertAttachmentRuntimeParams";

	public static final String RETRIEVE_DOCUMENTS_PROCESSOR_ID = "Alberta-GetDocumentsFromAlbertaProcessorId";
	public static final String RETRIEVE_ATTACHMENTS_PROCESSOR_ID = "Alberta-GetAttachmentsFromAlbertaProcessorId";
	public static final String PREPARE_DOCUMENT_API_PROCESSOR_ID = "Alberta-CreateDocumentRequestProcessorId";
	public static final String PREPARE_ATTACHMENT_API_PROCESSOR_ID = "Alberta-CreateAttachmentRequestProcessorId";
	public static final String DOCUMENT_TO_USER_PROCESSOR_ID = "Alberta-DocumentToUserProcessor";
	public static final String ATTACHMENT_TO_USER_PROCESSOR_ID = "Alberta-AttachmentToUserProcessor";
	public static final String DOCUMENT_RUNTIME_PARAMS_PROCESSOR_ID = "Alberta-DocumentRuntimeParamsProcessorId";
	public static final String ATTACHMENT_RUNTIME_PARAMS_PROCESSOR_ID = "Alberta-AttachmentRuntimeParamsProcessorId";

	public static final String PROCESS_DOCUMENT_ROUTE_ID = "Alberta-ProcessDocument";
	public static final String PROCESS_ATTACHMENT_ROUTE_ID = "Alberta-ProcessAttachment";

	@NonNull
	private final AlbertaApiProvider albertaApiProvider;

	public GetAlbertaAttachmentRoute(@NonNull final AlbertaApiProvider albertaApiProvider)
	{
		this.albertaApiProvider = albertaApiProvider;
	}

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
						.log(LoggingLevel.INFO, "No documents received")
					.otherwise()
						.split(body())
							.stopOnException()
							.to(direct(PROCESS_DOCUMENT_ROUTE_ID))
						.end()
					.endChoice()
				.end()
				.to(direct(UPSERT_DOCUMENT_RUNTIME_PARAMS_ROUTE_ID))

				.to(direct(GET_ATTACHMENTS_ROUTE_ID));

		from(direct(PROCESS_DOCUMENT_ROUTE_ID))
				.routeId(PROCESS_DOCUMENT_ROUTE_ID)
				.log("Route invoked!")

				.process(this::documentToUserUpsert).id(DOCUMENT_TO_USER_PROCESSOR_ID)
				.choice()
					.when(body().isNull())
						.log(LoggingLevel.DEBUG, "No users received")
					.otherwise()
						.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")
					.endChoice()
				.end()

				.process(new DocumentProcessor()).id(PREPARE_DOCUMENT_API_PROCESSOR_ID)
				.log(LoggingLevel.DEBUG, "Calling metasfresh-api to save attachment: ${body}")
				.to(direct(ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID));

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
							.to(direct(PROCESS_ATTACHMENT_ROUTE_ID))
						.end()
				.endChoice()
				.to(direct(UPSERT_ATTACHMENT_RUNTIME_PARAMS_ROUTE_ID));

		from(direct(PROCESS_ATTACHMENT_ROUTE_ID))
				.routeId(PROCESS_ATTACHMENT_ROUTE_ID)
				.log("Route invoked!")

				.process(this::attachmentToUserUpsert).id(ATTACHMENT_TO_USER_PROCESSOR_ID)
				.choice()
					.when(body().isNull())
						.log(LoggingLevel.DEBUG, "No users received")
					.otherwise()
						.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")
				.end()

				.process(new AttachmentProcessor()).id(PREPARE_ATTACHMENT_API_PROCESSOR_ID)
				.log(LoggingLevel.DEBUG, "Calling metasfresh-api to save attachment: ${body}")
				.to(direct(ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID));

		from(direct(UPSERT_DOCUMENT_RUNTIME_PARAMS_ROUTE_ID))
				.routeId(UPSERT_DOCUMENT_RUNTIME_PARAMS_ROUTE_ID)
				.log("Route invoked")
				.process(new DocumentRuntimeParametersProcessor()).id(DOCUMENT_RUNTIME_PARAMS_PROCESSOR_ID)
				.to(direct(ExternalSystemCamelConstants.MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID));

		from(direct(UPSERT_ATTACHMENT_RUNTIME_PARAMS_ROUTE_ID))
				.routeId(UPSERT_ATTACHMENT_RUNTIME_PARAMS_ROUTE_ID)
				.log("Route invoked")
				.process(new AttachmentRuntimeParametersProcessor()).id(ATTACHMENT_RUNTIME_PARAMS_PROCESSOR_ID)
				.to(direct(ExternalSystemCamelConstants.MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID));
		//@formatter:on
	}

	private void prepareContext(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final String orgCode = request.getOrgCode();
		final String basePath = request.getParameters().get(ExternalSystemConstants.PARAM_BASE_PATH);
		final String apiKey = request.getParameters().get(ExternalSystemConstants.PARAM_API_KEY);
		final String tenant = request.getParameters().get(ExternalSystemConstants.PARAM_TENANT);
		final String rootBPartnerId = request.getParameters().get(ExternalSystemConstants.PARAM_ROOT_BPARTNER_ID_FOR_USERS);

		if (Check.isBlank(rootBPartnerId))
		{
			throw new RuntimeException("Mandatory param missing from request: RootBPartnerID");
		}

		final JsonMetasfreshId rootBPartnerIdForUsers = JsonMetasfreshId.of(Integer.parseInt(rootBPartnerId));

		final ApiClient apiClient = new ApiClient().setBasePath(basePath);

		final String createdAfterDocument = CoalesceUtil.coalesceNotNull(
				request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER_OVERRIDE),
				request.getParameters().get(ExternalSystemConstants.PARAM_UPDATE_AFTER_DOCUMENT),
				Instant.ofEpochMilli(0).toString());

		final String createdAfterAttachment = CoalesceUtil.coalesceNotNull(
				request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER_OVERRIDE),
				request.getParameters().get(ExternalSystemConstants.PARAM_UPDATE_AFTER_ATTACHMENT),
				Instant.ofEpochMilli(0).toString());

		final GetAttachmentRouteContext context = GetAttachmentRouteContext.builder()
				.orgCode(orgCode)
				.apiKey(apiKey)
				.tenant(tenant)
				.attachmentApi(albertaApiProvider.getAttachmentApi(apiClient))
				.documentApi(albertaApiProvider.getDocumentApi(apiClient))
				.userApi(albertaApiProvider.getUserApi(apiClient))
				.rootBPartnerIdForUsers(rootBPartnerIdForUsers)
				.createdAfterDocument(createdAfterDocument)
				.createdAfterAttachment(createdAfterAttachment)
				.nextDocumentImportStartDate(Instant.parse(createdAfterDocument))
				.nextAttachmentImportStartDate(Instant.parse(createdAfterAttachment))
				.request(request)
				.build();

		exchange.setProperty(GetAttachmentRouteConstants.ROUTE_PROPERTY_GET_ATTACHMENT_CONTEXT, context);
	}

	private void documentToUserUpsert(@NonNull final Exchange exchange) throws ApiException
	{
		final Document document = exchange.getIn().getBody(Document.class);

		final GetAttachmentRouteContext routeContext =
				ProcessorHelper.getPropertyOrThrowError(exchange, GetAttachmentRouteConstants.ROUTE_PROPERTY_GET_ATTACHMENT_CONTEXT, GetAttachmentRouteContext.class);

		routeContext.setDocument(document);

		final Users createdBy = getUserOrNull(routeContext.getUserApi(), routeContext.getApiKey(), document.getCreatedBy());
		final Users updatedBy = getUserOrNull(routeContext.getUserApi(), routeContext.getApiKey(), document.getUpdatedBy());

		final Optional<BPUpsertCamelRequest> contactUpsertRequest = DataMapper
				.usersToBPartnerUpsert(routeContext.getOrgCode(), routeContext.getRootBPartnerIdForUsers(), createdBy, updatedBy);

		if (contactUpsertRequest.isEmpty())
		{
			exchange.getIn().setBody(null);
			return;
		}

		exchange.getIn().setBody(contactUpsertRequest.get());
	}

	private void attachmentToUserUpsert(@NonNull final Exchange exchange) throws ApiException
	{
		final Attachment attachment = exchange.getIn().getBody(Attachment.class);

		final GetAttachmentRouteContext routeContext =
				ProcessorHelper.getPropertyOrThrowError(exchange, GetAttachmentRouteConstants.ROUTE_PROPERTY_GET_ATTACHMENT_CONTEXT, GetAttachmentRouteContext.class);

		routeContext.setAttachment(attachment);

		final Users createdBy = getUserOrNull(routeContext.getUserApi(), routeContext.getApiKey(), attachment.getMetadata().getCreatedBy());

		final Optional<BPUpsertCamelRequest> contactUpsertRequest = DataMapper
				.usersToBPartnerUpsert(routeContext.getOrgCode(), routeContext.getRootBPartnerIdForUsers(), createdBy);

		if (contactUpsertRequest.isEmpty())
		{
			exchange.getIn().setBody(null);
			return;
		}

		exchange.getIn().setBody(contactUpsertRequest.get());
	}

	@Nullable
	private Users getUserOrNull(
			@NonNull final UserApi userApi,
			@NonNull final String apiKey,
			@Nullable final String userId) throws ApiException
	{
		if (EmptyUtil.isBlank(userId))
		{
			return null;
		}

		final Users user = userApi.getUser(apiKey, userId);

		if (user == null)
		{
			throw new RuntimeException("No info returned for user: " + userId);
		}
		return user;
	}
}
