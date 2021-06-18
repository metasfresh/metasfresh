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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentRequest;
import io.swagger.client.ApiException;
import io.swagger.client.JSON;
import io.swagger.client.api.AttachmentApi;
import io.swagger.client.api.DocumentApi;
import io.swagger.client.api.UserApi;
import io.swagger.client.model.ArrayOfAttachments;
import io.swagger.client.model.ArrayOfDocuments;
import io.swagger.client.model.Users;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;

public class GetAttachmentsRouteTests extends CamelTestSupport
{
	private static final String MOCK_ATTACHMENT_REQUEST_DOC = "mock:attachmentRequest_Document";
	private static final String MOCK_ATTACHMENT_REQUEST_ATTACHMENT = "mock:attachmentRequest_Attachment";

	private static final String MOCK_UPSERT_BPARTNER_REQUEST_DOC = "mock:upsertBPartnerRequest_Document";
	private static final String MOCK_UPSERT_BPARTNER_REQUEST_ATTACHMENT = "mock:upsertBPartnerRequest_Attachment";

	private static final String EXTERNAL_SYSTEM_REQUEST = "/de/metas/camel/externalsystems/alberta/attachment/5_ExternalSystemRequest.json";

	private static final String JSON_ALBERTA_GET_DOCUMENT = "/de/metas/camel/externalsystems/alberta/attachment/10_GetDocumentAlberta_Response.json";
	private static final String JSON_ALBERTA_GET_ATTACHMENT = "/de/metas/camel/externalsystems/alberta/attachment/20_GetAttachmentAlberta_Response.json";
	private static final String JSON_ALBERTA_GET_USER = "/de/metas/camel/externalsystems/alberta/patient/35_GetUserAlberta_Response.json";
	private static final String MOCK_JSON_ALBERTA_DATA_PATH = "src/test/resources/de/metas/camel/externalsystems/alberta/attachment/test.txt";

	private static final String JSON_ATTACHMENT_METASFRESH_REQUEST = "/de/metas/camel/externalsystems/alberta/attachment/40_JsonAttachmentMetasfreshRequest.json";
	private static final String JSON_ATTACHMENT_METASFRESH_RESPONSE = "/de/metas/camel/externalsystems/alberta/attachment/50_JsonAttachmentMetasfreshResponse.json";
	private static final String JSON_DOCUMENT_METASFRESH_REQUEST = "/de/metas/camel/externalsystems/alberta/attachment/41_JsonDocumentMetasfreshRequest.json";
	private static final String JSON_DOCUMENT_METASFRESH_RESPONSE = "/de/metas/camel/externalsystems/alberta/attachment/51_JsonsDocumentMetasfreshResponse.json";

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final Properties properties = new Properties();
		try
		{
			properties.load(GetAttachmentsRouteTests.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new GetAlbertaAttachmentRoute();
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Test
	void happyFlow() throws Exception
	{
		final MockDocumentMetasfreshResponse mockDocumentMetasfreshResponse = new MockDocumentMetasfreshResponse();
		final MockAttachmentMetasfreshResponse mockAttachmentMetasfreshResponse = new MockAttachmentMetasfreshResponse();
		final MockRuntimeParametersProcessor mockRuntimeParametersProcessor = new MockRuntimeParametersProcessor();
		final MockBPartnerUpsertResponse mockBPartnerUpsertResponse = new MockBPartnerUpsertResponse();
		prepareRouteForTesting(mockDocumentMetasfreshResponse, mockAttachmentMetasfreshResponse, mockRuntimeParametersProcessor, mockBPartnerUpsertResponse);

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		final MockEndpoint createAttachmentMockEndpointDocument = getMockEndpoint(MOCK_ATTACHMENT_REQUEST_DOC);
		final InputStream jsonAttachmentRequestExpected_Document = this.getClass().getResourceAsStream(JSON_DOCUMENT_METASFRESH_REQUEST);
		final JsonAttachmentRequest jsonRequestDocument = objectMapper.readValue(jsonAttachmentRequestExpected_Document, JsonAttachmentRequest.class);
		createAttachmentMockEndpointDocument.expectedBodiesReceived(jsonRequestDocument);

		final MockEndpoint createAttachmentMockEndpointAttachment = getMockEndpoint(MOCK_ATTACHMENT_REQUEST_ATTACHMENT);
		final InputStream jsonAttachmentRequestExpected_Attachment = this.getClass().getResourceAsStream(JSON_ATTACHMENT_METASFRESH_REQUEST);
		final JsonAttachmentRequest jsonRequestAttachment = objectMapper.readValue(jsonAttachmentRequestExpected_Attachment, JsonAttachmentRequest.class);
		createAttachmentMockEndpointAttachment.expectedBodiesReceived(jsonRequestAttachment);

		// fire the route
		template.sendBody("direct:" + GetAlbertaAttachmentRoute.GET_DOCUMENTS_ROUTE_ID, invokeExternalSystemRequest);

		assertMockEndpointsSatisfied();
	}

	private void prepareRouteForTesting(
			final GetAttachmentsRouteTests.MockDocumentMetasfreshResponse mockDocumentMetasfreshResponse,
			final GetAttachmentsRouteTests.MockAttachmentMetasfreshResponse mockAttachmentMetasfreshResponse,
			final MockRuntimeParametersProcessor mockRuntimeParametersProcessor,
			final GetAttachmentsRouteTests.MockBPartnerUpsertResponse mockBPartnerUpsertResponse) throws Exception
	{
		AdviceWith.adviceWith(context, GetAlbertaAttachmentRoute.GET_DOCUMENTS_ROUTE_ID,
							  advice -> advice.weaveById(GetAlbertaAttachmentRoute.RETRIEVE_DOCUMENTS_PROCESSOR_ID)
									  .replace()
									  .process(new MockPrepareContext()));

		AdviceWith.adviceWith(context, GetAlbertaAttachmentRoute.DOCUMENT_PROCESSOR_ID,
							  advice -> {
								  advice.weaveById(GetAlbertaAttachmentRoute.PREPARE_DOCUMENT_API_PROCESSOR_ID)
										  .after()
										  .to(MOCK_ATTACHMENT_REQUEST_DOC);

								  advice.weaveById(GetAlbertaAttachmentRoute.DOCUMENT_TO_USER_PROCESSOR_ID)
										  .after()
										  .to(MOCK_UPSERT_BPARTNER_REQUEST_DOC);

								  advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")
										  .skipSendToOriginalEndpoint()
										  .process(mockBPartnerUpsertResponse);

								  advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .process(mockDocumentMetasfreshResponse);

								 /* advice.interceptSendToEndpoint("direct:" + UPSERT_RUNTIME_PARAMS_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .process(mockRuntimeParametersProcessor);*/
							  });

		AdviceWith.adviceWith(context, GetAlbertaAttachmentRoute.GET_ATTACHMENTS_ROUTE_ID,
							  advice -> advice.weaveById(GetAlbertaAttachmentRoute.RETRIEVE_ATTACHMENTS_PROCESSOR_ID)
									  .replace()
									  .process(new MockPrepareAttachmentProcessor()));

		AdviceWith.adviceWith(context, GetAlbertaAttachmentRoute.ATTACHMENT_PROCESSOR_ID,
							  advice -> {
								  advice.weaveById(GetAlbertaAttachmentRoute.ATTACHMENT_TO_USER_PROCESSOR_ID)
										  .after()
										  .to(MOCK_UPSERT_BPARTNER_REQUEST_ATTACHMENT);

								  advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")
										  .skipSendToOriginalEndpoint()
										  .process(mockBPartnerUpsertResponse);

								  advice.weaveById(GetAlbertaAttachmentRoute.PREPARE_ATTACHMENT_API_PROCESSOR_ID)
										  .after()
										  .to(MOCK_ATTACHMENT_REQUEST_ATTACHMENT);

								  advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .process(mockAttachmentMetasfreshResponse);
							  });
	}

	private static class MockPrepareAttachmentProcessor implements Processor
	{
		@Override
		public void process(@NonNull final Exchange exchange)
		{
			final JSON json = new JSON();

			final String jsonString = loadAsString(JSON_ALBERTA_GET_ATTACHMENT);
			final ArrayOfAttachments attachments = json.deserialize(jsonString, ArrayOfAttachments.class);

			exchange.getIn().setBody(attachments.get(0));
		}
	}

	private static class MockPrepareContext implements Processor
	{
		@Override
		public void process(@NonNull final Exchange exchange) throws ApiException, IOException
		{
			final JSON json = new JSON();
			final ObjectMapper objectMapper = new ObjectMapper();

			final InputStream request = this.getClass().getResourceAsStream(EXTERNAL_SYSTEM_REQUEST);
			final JsonExternalSystemRequest externalSystemRequest = objectMapper.readValue(request, JsonExternalSystemRequest.class);

			final GetAttachmentRouteContext context = GetAttachmentRouteContext.builder()
					.orgCode("orgCode")
					.apiKey("apiKey")
					.tenant("tenant")
					.rootBPartnerIdForUsers(JsonMetasfreshId.of(1))
					.attachmentApi(prepareAttachmentApi(json))
					.documentApi(prepareDocumentApi(json))
					.userApi(prepareUserApi(json))
					.createdAfter("2021-01-01T02:17:20Z")
					.nextAttachmentImportStartDate(String.valueOf(Instant.parse("2021-01-01T02:17:20Z")))
					.request(externalSystemRequest)
					.build();

			final String jsonString = loadAsString(JSON_ALBERTA_GET_DOCUMENT);
			final ArrayOfDocuments documents = json.deserialize(jsonString, ArrayOfDocuments.class);

			exchange.setProperty(GetAttachmentRouteConstants.ROUTE_PROPERTY_GET_ATTACHMENT_CONTEXT, context);
			exchange.getIn().setBody(documents.get(0));
		}
	}

	@NonNull
	private static AttachmentApi prepareAttachmentApi(@NonNull final JSON json) throws ApiException
	{
		final AttachmentApi attachmentApi = Mockito.mock(AttachmentApi.class);
		final String jsonString = loadAsString(JSON_ALBERTA_GET_ATTACHMENT);
		final ArrayOfAttachments attachments = json.deserialize(jsonString, ArrayOfAttachments.class);

		Mockito.when(attachmentApi.getAllAttachments(any(String.class), any(String.class), any(BigDecimal.class)))
				.thenReturn(attachments);

		final File mockedFile = Mockito.mock(File.class);
		Mockito.when(mockedFile.toPath()).thenReturn(Path.of(MOCK_JSON_ALBERTA_DATA_PATH));
		Mockito.when(mockedFile.getName()).thenReturn("test.txt");

		Mockito.when(attachmentApi.getSingleAttachment(any(String.class), any(String.class)))
				.thenReturn(mockedFile);

		return attachmentApi;
	}

	@NonNull
	private static DocumentApi prepareDocumentApi(@NonNull final JSON json) throws ApiException
	{
		final DocumentApi documentApi = Mockito.mock(DocumentApi.class);
		final String jsonString = loadAsString(JSON_ALBERTA_GET_DOCUMENT);
		final ArrayOfDocuments documents = json.deserialize(jsonString, ArrayOfDocuments.class);

		Mockito.when(documentApi.getAllDocuments(any(String.class), any(String.class)))
				.thenReturn(documents);

		final File mockedFile = Mockito.mock(File.class);
		Mockito.when(mockedFile.exists()).thenReturn(true);
		Mockito.when(mockedFile.toPath()).thenReturn(Path.of(MOCK_JSON_ALBERTA_DATA_PATH));

		Mockito.when(documentApi.getSingleDocument(any(String.class), any(String.class)))
				.thenReturn(mockedFile);

		return documentApi;
	}

	@NonNull
	private static UserApi prepareUserApi(@NonNull final JSON json) throws ApiException
	{
		final UserApi userApi = Mockito.mock(UserApi.class);
		final String jsonString = loadAsString(JSON_ALBERTA_GET_USER);
		final Users users = json.deserialize(jsonString, Users.class);

		Mockito.when(userApi.getUser(any(String.class), any(String.class), any(String.class)))
				.thenReturn(users);

		return userApi;
	}

	private static class MockDocumentMetasfreshResponse implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
			final InputStream documentResponse = GetAttachmentsRouteTests.class.getResourceAsStream(JSON_DOCUMENT_METASFRESH_RESPONSE);
			exchange.getIn().setBody(documentResponse);
		}
	}

	private static class MockAttachmentMetasfreshResponse implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
			final InputStream attachmentResponse = GetAttachmentsRouteTests.class.getResourceAsStream(JSON_ATTACHMENT_METASFRESH_RESPONSE);
			exchange.getIn().setBody(attachmentResponse);
		}
	}

	private static class MockRuntimeParametersProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	private static String loadAsString(@NonNull final String name)
	{
		final InputStream createdPatientsIS = GetAttachmentsRouteTests.class.getResourceAsStream(name);
		return new BufferedReader(
				new InputStreamReader(createdPatientsIS, StandardCharsets.UTF_8))
				.lines()
				.collect(Collectors.joining("\n"));
	}

	private static class MockBPartnerUpsertResponse implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}
}
