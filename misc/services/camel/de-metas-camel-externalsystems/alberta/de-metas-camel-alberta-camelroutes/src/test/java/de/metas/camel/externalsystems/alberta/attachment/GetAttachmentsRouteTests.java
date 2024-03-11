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
import de.metas.camel.externalsystems.alberta.common.AlbertaApiProvider;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.common.externalsystem.JsonESRuntimeParameterUpsertRequest;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentRequest;
import io.swagger.client.ApiClient;
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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Properties;
import java.util.stream.Collectors;

import static de.metas.camel.externalsystems.alberta.attachment.GetAlbertaAttachmentRoute.ATTACHMENT_RUNTIME_PARAMS_PROCESSOR_ID;
import static de.metas.camel.externalsystems.alberta.attachment.GetAlbertaAttachmentRoute.DOCUMENT_RUNTIME_PARAMS_PROCESSOR_ID;
import static de.metas.camel.externalsystems.alberta.attachment.GetAlbertaAttachmentRoute.UPSERT_ATTACHMENT_RUNTIME_PARAMS_ROUTE_ID;
import static de.metas.camel.externalsystems.alberta.attachment.GetAlbertaAttachmentRoute.UPSERT_DOCUMENT_RUNTIME_PARAMS_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class GetAttachmentsRouteTests extends CamelTestSupport
{
	private static final String MOCK_DOCUMENT_UPSERT_CONTACT = "mock:upsertBPartnerRequest_Document";
	private static final String MOCK_DOCUMENT_TO_MF_ATTACHMENT_REQUEST = "mock:attachmentRequest_Document";
	private static final String MOCK_DOCUMENT_RUNTIME_PARAMETER = "mock:runtimeParam_Document";

	private static final String MOCK_ATTACHMENT_UPSERT_CONTACT = "mock:upsertBPartnerRequest_Attachment";
	private static final String MOCK_ATTACHMENT_TO_MF_ATTACHMENT_REQUEST = "mock:attachmentRequest_Attachment";
	private static final String MOCK_ATTACHMENT_RUNTIME_PARAMETER = "mock:runtimeParam_Attachment";

	private static final String EXTERNAL_SYSTEM_REQUEST = "5_ExternalSystemRequest.json";
	private static final String JSON_DOCUMENT_GET_RESPONSE = "10_Document_GET_Response.json";
	private static final String JSON_DOCUMENT_USER_GET_RESPONSE = "20_Document_Users_GET_Response.json";
	private static final String JSON_DOCUMENT_UPSERT_USER_REQUEST = "30_Document_UPSERT_User_Request.json";
	private static final String JSON_DOCUMENT_CREATE_ATTACHMENT_REQUEST = "40_Document_CREATE_Attachment_Request.json";
	private static final String JSON_DOCUMENT_RUNTIME_PARAM_REQUEST = "50_Document_UPSERT_RuntimeParam_Request.json";

	private static final String JSON_ATTACHMENT_GET_RESPONSE = "60_Attachment_GET_Response.json";
	private static final String JSON_ATTACHMENT_UPSERT_USER_REQUEST = "80_Attachment_UPSERT_User_Request.json";
	private static final String JSON_ATTACHMENT_CREATE_ATTACHMENT_REQUEST = "90_Attachment_CREATE_Attachment_Request.json";
	private static final String JSON_ATTACHMENT_RUNTIME_PARAM_REQUEST = "100_Attachment_UPSERT_RuntimeParam_Request.json";

	private static final String MOCK_JSON_ALBERTA_DATA_PATH = "src/test/resources/de/metas/camel/externalsystems/alberta/attachment/test.txt";

	private final AlbertaApiProvider albertaApiProviderMock = Mockito.mock(AlbertaApiProvider.class);

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
		return new GetAlbertaAttachmentRoute(albertaApiProviderMock);
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

		prepareRouteForTesting(mockDocumentMetasfreshResponse,
							   mockAttachmentMetasfreshResponse,
							   mockRuntimeParametersProcessor,
							   mockBPartnerUpsertResponse);

		final JSON json = new JSON();
		final AttachmentApi mockAttachmentApi = prepareAttachmentApi(json);

		Mockito.when(albertaApiProviderMock.getAttachmentApi((any(ApiClient.class))))
				.thenReturn(mockAttachmentApi);

		final DocumentApi mockDocumentApi = prepareDocumentApi(json);

		Mockito.when(albertaApiProviderMock.getDocumentApi((any(ApiClient.class))))
				.thenReturn(mockDocumentApi);

		final UserApi userApi = prepareUserApi(json);

		Mockito.when(albertaApiProviderMock.getUserApi((any(ApiClient.class))))
				.thenReturn(userApi);

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		//1. upsert document user
		final MockEndpoint upsertDocumentUserEndpoint = getMockEndpoint(MOCK_DOCUMENT_UPSERT_CONTACT);

		final InputStream expectedUserRequestIS = this.getClass().getResourceAsStream(JSON_DOCUMENT_UPSERT_USER_REQUEST);
		final BPUpsertCamelRequest expectedUserRequest = objectMapper.readValue(expectedUserRequestIS, BPUpsertCamelRequest.class);
		upsertDocumentUserEndpoint.expectedBodiesReceived(expectedUserRequest);

		//2. create attachment
		final MockEndpoint createAttachmentMockEndpointDocument = getMockEndpoint(MOCK_DOCUMENT_TO_MF_ATTACHMENT_REQUEST);

		final InputStream expectedAttachmentRequestIS = this.getClass().getResourceAsStream(JSON_DOCUMENT_CREATE_ATTACHMENT_REQUEST);
		final JsonAttachmentRequest expectedAttachmentRequest = objectMapper.readValue(expectedAttachmentRequestIS, JsonAttachmentRequest.class);
		createAttachmentMockEndpointDocument.expectedBodiesReceived(expectedAttachmentRequest);

		//3. upsert document runtime param
		final MockEndpoint upsertDocumentRuntimeParam = getMockEndpoint(MOCK_DOCUMENT_RUNTIME_PARAMETER);

		final InputStream expectedDocumentRuntimeParamReqIS = this.getClass().getResourceAsStream(JSON_DOCUMENT_RUNTIME_PARAM_REQUEST);
		final JsonESRuntimeParameterUpsertRequest expectedDocumentRuntimeParamReq = objectMapper.readValue(expectedDocumentRuntimeParamReqIS, JsonESRuntimeParameterUpsertRequest.class);
		upsertDocumentRuntimeParam.expectedBodiesReceived(expectedDocumentRuntimeParamReq);

		//4. upsert attachment user
		final MockEndpoint upsertAttachmentUserEndpoint = getMockEndpoint(MOCK_ATTACHMENT_UPSERT_CONTACT);

		final InputStream expectedAttachmentUserRequestIS = this.getClass().getResourceAsStream(JSON_ATTACHMENT_UPSERT_USER_REQUEST);
		final BPUpsertCamelRequest expectedAttachmentUserRequest = objectMapper.readValue(expectedAttachmentUserRequestIS, BPUpsertCamelRequest.class);
		upsertAttachmentUserEndpoint.expectedBodiesReceived(expectedAttachmentUserRequest);

		//5. upsert attachment
		final MockEndpoint createAttachmentMockEndpointAttachment = getMockEndpoint(MOCK_ATTACHMENT_TO_MF_ATTACHMENT_REQUEST);

		final InputStream expectedAttachmentRequestAttIS = this.getClass().getResourceAsStream(JSON_ATTACHMENT_CREATE_ATTACHMENT_REQUEST);
		final JsonAttachmentRequest expectedAttachmentRequestAtt = objectMapper.readValue(expectedAttachmentRequestAttIS, JsonAttachmentRequest.class);
		createAttachmentMockEndpointAttachment.expectedBodiesReceived(expectedAttachmentRequestAtt);

		//6. upsert attachment runtime param
		final MockEndpoint upsertAttachRuntimeParam = getMockEndpoint(MOCK_ATTACHMENT_RUNTIME_PARAMETER);

		final InputStream expectedAttachRuntimeParamReqIS = this.getClass().getResourceAsStream(JSON_ATTACHMENT_RUNTIME_PARAM_REQUEST);
		final JsonESRuntimeParameterUpsertRequest expectedAttachRuntimeParamReq = objectMapper.readValue(expectedAttachRuntimeParamReqIS, JsonESRuntimeParameterUpsertRequest.class);
		upsertAttachRuntimeParam.expectedBodiesReceived(expectedAttachRuntimeParamReq);

		// fire the route
		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper
				.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		template.sendBody("direct:" + GetAlbertaAttachmentRoute.GET_DOCUMENTS_ROUTE_ID, invokeExternalSystemRequest);

		assertMockEndpointsSatisfied();
	}

	private void prepareRouteForTesting(
			final GetAttachmentsRouteTests.MockDocumentMetasfreshResponse mockDocumentMetasfreshResponse,
			final GetAttachmentsRouteTests.MockAttachmentMetasfreshResponse mockAttachmentMetasfreshResponse,
			final MockRuntimeParametersProcessor mockRuntimeParametersProcessor,
			final GetAttachmentsRouteTests.MockBPartnerUpsertResponse mockBPartnerUpsertResponse) throws Exception
	{
		AdviceWith.adviceWith(context, UPSERT_DOCUMENT_RUNTIME_PARAMS_ROUTE_ID,
							  advice -> {
								  advice.weaveById(DOCUMENT_RUNTIME_PARAMS_PROCESSOR_ID)
										  .after()
										  .to(MOCK_DOCUMENT_RUNTIME_PARAMETER);

								  advice.interceptSendToEndpoint("direct:" + MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .process(mockRuntimeParametersProcessor);
							  });

		AdviceWith.adviceWith(context, GetAlbertaAttachmentRoute.PROCESS_DOCUMENT_ROUTE_ID,
							  advice -> {
								  advice.weaveById(GetAlbertaAttachmentRoute.DOCUMENT_TO_USER_PROCESSOR_ID)
										  .after()
										  .to(MOCK_DOCUMENT_UPSERT_CONTACT);

								  advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")
										  .skipSendToOriginalEndpoint()
										  .process(mockBPartnerUpsertResponse);

								  advice.weaveById(GetAlbertaAttachmentRoute.PREPARE_DOCUMENT_API_PROCESSOR_ID)
										  .after()
										  .to(MOCK_DOCUMENT_TO_MF_ATTACHMENT_REQUEST);

								  advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .process(mockDocumentMetasfreshResponse);
							  });

		AdviceWith.adviceWith(context, UPSERT_ATTACHMENT_RUNTIME_PARAMS_ROUTE_ID,
							  advice -> {
								  advice.weaveById(ATTACHMENT_RUNTIME_PARAMS_PROCESSOR_ID)
										  .after()
										  .to(MOCK_ATTACHMENT_RUNTIME_PARAMETER);

								  advice.interceptSendToEndpoint("direct:" + MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .process(mockRuntimeParametersProcessor);
							  });

		AdviceWith.adviceWith(context, GetAlbertaAttachmentRoute.PROCESS_ATTACHMENT_ROUTE_ID,
							  advice -> {
								  advice.weaveById(GetAlbertaAttachmentRoute.ATTACHMENT_TO_USER_PROCESSOR_ID)
										  .after()
										  .to(MOCK_ATTACHMENT_UPSERT_CONTACT);

								  advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")
										  .skipSendToOriginalEndpoint()
										  .process(mockBPartnerUpsertResponse);

								  advice.weaveById(GetAlbertaAttachmentRoute.PREPARE_ATTACHMENT_API_PROCESSOR_ID)
										  .after()
										  .to(MOCK_ATTACHMENT_TO_MF_ATTACHMENT_REQUEST);

								  advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .process(mockAttachmentMetasfreshResponse);
							  });
	}

	@NonNull
	private static AttachmentApi prepareAttachmentApi(@NonNull final JSON json) throws ApiException
	{
		final AttachmentApi attachmentApi = Mockito.mock(AttachmentApi.class);
		final String jsonString = loadAsString(JSON_ATTACHMENT_GET_RESPONSE);
		final ArrayOfAttachments attachments = json.deserialize(jsonString, ArrayOfAttachments.class);

		Mockito.when(attachmentApi.getAllAttachments(any(String.class), any(String.class), eq(null)))
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
		final String jsonString = loadAsString(JSON_DOCUMENT_GET_RESPONSE);
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
		final String jsonString = loadAsString(JSON_DOCUMENT_USER_GET_RESPONSE);
		final Users users = json.deserialize(jsonString, Users.class);

		Mockito.when(userApi.getUser(any(String.class), any(String.class)))
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
		}
	}

	private static class MockAttachmentMetasfreshResponse implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
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

	private static class MockBPartnerUpsertResponse implements Processor
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
}
