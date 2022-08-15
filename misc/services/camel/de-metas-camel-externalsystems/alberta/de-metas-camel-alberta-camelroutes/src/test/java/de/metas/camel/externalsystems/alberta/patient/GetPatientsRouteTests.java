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

package de.metas.camel.externalsystems.alberta.patient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.alberta.common.AlbertaConnectionDetails;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import de.metas.common.externalsystem.JsonESRuntimeParameterUpsertRequest;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.client.ApiException;
import io.swagger.client.JSON;
import io.swagger.client.api.DoctorApi;
import io.swagger.client.api.HospitalApi;
import io.swagger.client.api.NursingHomeApi;
import io.swagger.client.api.NursingServiceApi;
import io.swagger.client.api.PatientApi;
import io.swagger.client.api.PayerApi;
import io.swagger.client.api.PharmacyApi;
import io.swagger.client.api.UserApi;
import io.swagger.client.model.ArrayOfPatients;
import io.swagger.client.model.Doctor;
import io.swagger.client.model.Hospital;
import io.swagger.client.model.NursingService;
import io.swagger.client.model.Payer;
import io.swagger.client.model.Pharmacy;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Properties;
import java.util.stream.Collectors;

import static de.metas.camel.externalsystems.alberta.attachment.GetAlbertaAttachmentRoute.GET_DOCUMENTS_ROUTE_ID;
import static de.metas.camel.externalsystems.alberta.patient.GetAlbertaPatientsRoute.CREATE_UPSERT_BPARTNER_RELATION_REQUEST_PROCESSOR_ID;
import static de.metas.camel.externalsystems.alberta.patient.GetAlbertaPatientsRoute.CREATE_UPSERT_BPARTNER_REQUEST_PROCESSOR_ID;
import static de.metas.camel.externalsystems.alberta.patient.GetAlbertaPatientsRoute.GET_PATIENTS_ROUTE_ID;
import static de.metas.camel.externalsystems.alberta.patient.GetAlbertaPatientsRoute.PREPARE_PATIENTS_API_PROCESSOR_ID;
import static de.metas.camel.externalsystems.alberta.patient.GetAlbertaPatientsRoute.PROCESS_PATIENT_ROUTE_ID;
import static de.metas.camel.externalsystems.alberta.patient.GetAlbertaPatientsRoute.RUNTIME_PARAMS_PROCESSOR_ID;
import static de.metas.camel.externalsystems.alberta.patient.GetAlbertaPatientsRoute.UPSERT_RUNTIME_PARAMS_ROUTE_ID;
import static de.metas.camel.externalsystems.alberta.patient.GetPatientsRouteConstants.PatientStatus.UPDATED;
import static de.metas.camel.externalsystems.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_GET_PATIENTS_CONTEXT;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class GetPatientsRouteTests extends CamelTestSupport
{
	private static final String MOCK_UPSERT_BPARTNER_REQUEST = "mock:upsertBPartnerRequest";
	private static final String MOCK_UPSERT_BPARTNER_RELATION_REQUEST = "mock:upsertBPartnerRelationRequest";

	private static final String JSON_MF_GET_PATIENTS_REQUEST = "/de/metas/camel/externalsystems/alberta/patient/10_ExternalSystemRequest.json";
	private static final String JSON_ALBERTA_GET_CREATED_PATIENTS_RESPONSE = "/de/metas/camel/externalsystems/alberta/patient/20_GetCreatedPatientsAlbertaResponse.json";
	private static final String JSON_ALBERTA_GET_UPDATED_PATIENTS_RESPONSE = "/de/metas/camel/externalsystems/alberta/patient/30_GetUpdatedPatientsAlbertaResponse.json";
	private static final String JSON_ALBERTA_GET_DOCTOR_RESPONSE = "/de/metas/camel/externalsystems/alberta/patient/31_GetDoctorAlberta_5ab23eb59d69c74b68d0eded_Response.json";
	private static final String JSON_ALBERTA_GET_NURINGSERVICE_RESPONSE = "/de/metas/camel/externalsystems/alberta/patient/32_GetNursingServiceAlberta_5ab2383d9d69c74b68cf19dc_Response.json";
	private static final String JSON_ALBERTA_GET_HOSPIPAL_RESPONSE = "/de/metas/camel/externalsystems/alberta/patient/33_GetHospitalAlberta_5ab233bc9d69c74b68cec23a_Response.json";
	private static final String JSON_ALBERTA_GET_PAYER_RESPONSE = "/de/metas/camel/externalsystems/alberta/patient/34_GetPayerAlberta_5ada01a2c3918e1bdcb5460e_Response.json";
	private static final String JSON_ALBERTA_GET_PHARMACY_RESPONSE = "/de/metas/camel/externalsystems/alberta/patient/35_GetPharmacyAlberta_5ab2390e9d69c74b68cf4f2d_Response.json";
	private static final String JSON_ALBERTA_GET_USER = "/de/metas/camel/externalsystems/alberta/patient/35_GetUserAlberta_Response.json";

	public static final String JSON_UPSERT_BPARTNER_REQUEST = "/de/metas/camel/externalsystems/alberta/patient/60_UpsertBPartnerMetasfreshRequest.json";
	private static final String JSON_UPSERT_BPARTNER_RESPONSE = "/de/metas/camel/externalsystems/alberta/patient/70_UpsertBPartnerMetasfreshResponse.json";
	private static final String JSON_UPSERT_BPARTNER_RELATIONS_REQUEST = "/de/metas/camel/externalsystems/alberta/patient/80_UpsertBPartnerRelationsMetasfreshRequest.json";

	private static final String JSON_UPSERT_RUNTIME_PARAM_REQUEST = "100_UpsertRuntimeParamRequest.json";
	private static final String MOCK_UPSERT_RUNTIME_PRAMS = "mock:patient-upsertRuntimeParam";

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final Properties properties = new Properties();
		try
		{
			properties.load(GetPatientsRouteTests.class.getClassLoader().getResourceAsStream("application.properties"));
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
		return new GetAlbertaPatientsRoute();
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	/**
	 * GETs one patient from the mocked Alberta-API and PUT BPartners and BPartner-Relations to the mocked metasfresh-API.
	 */
	@Test
	void happyFlow() throws Exception
	{
		final MockBPartnerUpsertResponse mockBPartnerUpsertResponse = new MockBPartnerUpsertResponse();
		final MockBPartnerRelationUpsertResponse mockBPartnerRelationUpsertResponse = new MockBPartnerRelationUpsertResponse();
		final MockGetDocumentsProcessor mockGetDocumentsProcessor = new MockGetDocumentsProcessor();

		prepareRouteForTesting(mockBPartnerUpsertResponse, mockBPartnerRelationUpsertResponse, mockGetDocumentsProcessor);

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_MF_GET_PATIENTS_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		//validate the upsert-bpartner-request that is done towards metasfresh
		final MockEndpoint bpartnerUpsertMockEndpoint = getMockEndpoint(MOCK_UPSERT_BPARTNER_REQUEST);
		final InputStream bparnerUpsertRequestExpected = this.getClass().getResourceAsStream(JSON_UPSERT_BPARTNER_REQUEST);
		final JsonRequestBPartnerUpsert jsonRequestBPartnerUpsert = objectMapper.readValue(bparnerUpsertRequestExpected, JsonRequestBPartnerUpsert.class);
		bpartnerUpsertMockEndpoint.expectedBodiesReceived(jsonRequestBPartnerUpsert);

		final MockEndpoint mockUpsertRuntimeParamEP = getMockEndpoint(MOCK_UPSERT_RUNTIME_PRAMS);
		final InputStream expectedRuntimeParamIS = this.getClass().getResourceAsStream(JSON_UPSERT_RUNTIME_PARAM_REQUEST);
		final JsonESRuntimeParameterUpsertRequest expectedRuntimeParamRequest = objectMapper.readValue(expectedRuntimeParamIS, JsonESRuntimeParameterUpsertRequest.class);

		mockUpsertRuntimeParamEP.expectedBodiesReceived(expectedRuntimeParamRequest);

		//validate the upsert-bpartner-relation-request that is done towards metasfresh
		// final MockEndpoint bpartnerRelationUpsertMockEndpoint = getMockEndpoint(MOCK_UPSERT_BPARTNER_RELATION_REQUEST);
		// final InputStream bparnerRelationUpsertRequestExpected = this.getClass().getResourceAsStream(JSON_UPSERT_BPARTNER_RELATIONS_REQUEST);
		// final JsonRequestBPRelationsUpsert jsonRequestBPRelationsUpsert = objectMapper.readValue(bparnerRelationUpsertRequestExpected, JsonRequestBPRelationsUpsert.class);
		// final BPRelationsCamelRequest bpRelationsCamelRequest = BPRelationsCamelRequest.builder()
		// 		.jsonRequestBPRelationsUpsert(jsonRequestBPRelationsUpsert)
		// 		.bpartnerIdentifier("910") // this is the ID that camel got from 70_UpsertBPartnerMetasfreshResponse.json
		// 		.build();
		// bpartnerRelationUpsertMockEndpoint.expectedBodiesReceived(bpRelationsCamelRequest); //FIXME

		//fire the route
		template.sendBody("direct:" + GET_PATIENTS_ROUTE_ID, invokeExternalSystemRequest);

		assertMockEndpointsSatisfied();
		assertThat(mockBPartnerUpsertResponse.called).isEqualTo(7);
		assertThat(mockBPartnerRelationUpsertResponse.called).isEqualTo(1);
		assertThat(mockGetDocumentsProcessor.called).isEqualTo(1);
	}

	private void prepareRouteForTesting(
			final MockBPartnerUpsertResponse mockBPartnerUpsertResponse,
			final MockBPartnerRelationUpsertResponse mockBPartnerRelationUpsertResponse,
			final MockGetDocumentsProcessor mockGetDocumentsProcessor) throws Exception
	{
		// inject our mock processor that returns the patient-JSON from alberta
		AdviceWith.adviceWith(context, GET_PATIENTS_ROUTE_ID,
				advice -> {
					advice.weaveById(PREPARE_PATIENTS_API_PROCESSOR_ID)
							.replace()
							.process(new MockPreparePatientsApiProcessor());

					advice.interceptSendToEndpoint("direct:" + GET_DOCUMENTS_ROUTE_ID)
							.skipSendToOriginalEndpoint()
							.process(mockGetDocumentsProcessor);
		});

		AdviceWith.adviceWith(context, PROCESS_PATIENT_ROUTE_ID,
				advice -> {
					// validate the the bpartner upsert request and send a response 
					advice.weaveById(CREATE_UPSERT_BPARTNER_REQUEST_PROCESSOR_ID)
							.after()
							.to(MOCK_UPSERT_BPARTNER_REQUEST);

					advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")
							.skipSendToOriginalEndpoint()
							.process(mockBPartnerUpsertResponse);

					// validate the the bpartner relation upsert request and send a response 
					advice.weaveById(CREATE_UPSERT_BPARTNER_RELATION_REQUEST_PROCESSOR_ID)
							.after()
							.to(MOCK_UPSERT_BPARTNER_RELATION_REQUEST);
					advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPRELATION_V2_CAMEL_URI + "}}")
							.skipSendToOriginalEndpoint()
							.process(mockBPartnerRelationUpsertResponse);
				});

		AdviceWith.adviceWith(context, UPSERT_RUNTIME_PARAMS_ROUTE_ID,
							  advice -> {
								  advice.weaveById(RUNTIME_PARAMS_PROCESSOR_ID)
										  .after()
										  .to(MOCK_UPSERT_RUNTIME_PRAMS);

								  advice.interceptSendToEndpoint("direct:" + MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .process((exchange -> System.out.println("Do nothing")));
							  });
	}

	private static class MockPreparePatientsApiProcessor implements Processor
	{
		@Override
		public void process(@NonNull final Exchange exchange) throws ApiException
		{
			final JSON json = new JSON();

			final AlbertaConnectionDetails albertaConnectionDetails = AlbertaConnectionDetails.builder()
					.apiKey("apiKey")
					.basePath("basePath")
					.tenant("tenant")
					.build();

			final JsonExternalSystemRequest externalSystemRequest = JsonExternalSystemRequest.builder()
					.traceId("traceId")
					.externalSystemName(JsonExternalSystemName.of("externalSystem"))
					.externalSystemChildConfigValue("ChildValue")
					.externalSystemConfigId(JsonMetasfreshId.of(1))
					.orgCode("orgCode")
					.command("command")
					.build();

			final GetPatientsRouteContext context = GetPatientsRouteContext.builder()
					.doctorApi(prepareDoctorApiClient(json))
					.hospitalApi(prepareHospitalApiClient(json))
					.nursingHomeApi(prepareNursingHomeApiClient(json))
					.nursingServiceApi(prepareNursingServiceApiClient(json))
					.patientApi(preparePatientApiClient(json))
					.payerApi(preparePayerApiClient(json))
					.pharmacyApi(preparePharmacyApiClient(json))
					.userApi(prepareUserApi(json))
					.albertaConnectionDetails(albertaConnectionDetails)
					.rootBPartnerIdForUsers(JsonMetasfreshId.of(200))
					.request(externalSystemRequest)
					.updatedAfterValue(Instant.ofEpochSecond(0))
					.build();

			exchange.setProperty(ROUTE_PROPERTY_GET_PATIENTS_CONTEXT, context);
		}

		@NonNull
		private static PatientApi preparePatientApiClient(@NonNull final JSON json) throws ApiException
		{
			final PatientApi albertaPatientApi = Mockito.mock(PatientApi.class);

			//1. mock retrieval of created payments
			final String createdPatientsStr = loadAsString(JSON_ALBERTA_GET_CREATED_PATIENTS_RESPONSE);
			final ArrayOfPatients createdPatients = json.deserialize(createdPatientsStr, ArrayOfPatients.class);
			//Mockito.when(albertaPatientApi.getCreatedPatients(any(String.class), any(String.class), eq(CREATED.getValue()), any(String.class)))
			Mockito.when(albertaPatientApi.getCreatedPatients(any(String.class), any(String.class), any(String.class)))
					.thenReturn(createdPatients);

			//2. mock retrieval of updated payments
			final String updatedPatientsStr = loadAsString(JSON_ALBERTA_GET_UPDATED_PATIENTS_RESPONSE);
			final ArrayOfPatients updatedPatients = json.deserialize(updatedPatientsStr, ArrayOfPatients.class);
			Mockito.when(albertaPatientApi.getCreatedPatients(any(String.class), eq(UPDATED.getValue()), any(String.class)))
					.thenReturn(updatedPatients);

			return albertaPatientApi;
		}
	}

	@NonNull
	private static DoctorApi prepareDoctorApiClient(@NonNull final JSON json) throws ApiException
	{
		final DoctorApi albertaDoctorApi = Mockito.mock(DoctorApi.class);
		final String jsonString = loadAsString(JSON_ALBERTA_GET_DOCTOR_RESPONSE);
		final Doctor doctor = json.deserialize(jsonString, Doctor.class);

		Mockito.when(albertaDoctorApi.getDoctor(any(String.class), any(String.class)))
				.thenReturn(doctor);

		return albertaDoctorApi;
	}

	@NonNull
	private static NursingHomeApi prepareNursingHomeApiClient(@NonNull final JSON json) throws ApiException
	{
		final NursingHomeApi nursingHomeApi = Mockito.mock(NursingHomeApi.class);
		// final String jsonString = loadAsString(JSON_ALBERTA_GET_NURSINGHOME_RESPONSE);
		// final NursingHome nursingHome = json.deserialize(jsonString, NursingHome.class);

		Mockito.when(nursingHomeApi.geNursingHome(any(String.class), any(String.class)))
				.thenReturn(null);

		return nursingHomeApi;
	}

	@NonNull
	private static NursingServiceApi prepareNursingServiceApiClient(@NonNull final JSON json) throws ApiException
	{
		final NursingServiceApi nursingServiceApi = Mockito.mock(NursingServiceApi.class);
		final String jsonString = loadAsString(JSON_ALBERTA_GET_NURINGSERVICE_RESPONSE);
		final NursingService nursingService = json.deserialize(jsonString, NursingService.class);

		Mockito.when(nursingServiceApi.getNursingService(any(String.class), any(String.class)))
				.thenReturn(nursingService);

		return nursingServiceApi;
	}

	@NonNull
	private static HospitalApi prepareHospitalApiClient(@NonNull final JSON json) throws ApiException
	{
		final HospitalApi hospitalApi = Mockito.mock(HospitalApi.class);
		final String jsonString = loadAsString(JSON_ALBERTA_GET_HOSPIPAL_RESPONSE);
		final Hospital hospital = json.deserialize(jsonString, Hospital.class);

		Mockito.when(hospitalApi.getHospital(any(String.class), any(String.class)))
				.thenReturn(hospital);

		return hospitalApi;
	}

	@NonNull
	private static PayerApi preparePayerApiClient(@NonNull final JSON json) throws ApiException
	{
		final PayerApi payerApi = Mockito.mock(PayerApi.class);
		final String jsonString = loadAsString(JSON_ALBERTA_GET_PAYER_RESPONSE);
		final Payer payer = json.deserialize(jsonString, Payer.class);

		Mockito.when(payerApi.getPayer(any(String.class), any(String.class)))
				.thenReturn(payer);

		return payerApi;
	}

	@NonNull
	private static PharmacyApi preparePharmacyApiClient(@NonNull final JSON json) throws ApiException
	{
		final PharmacyApi pharmacyApi = Mockito.mock(PharmacyApi.class);
		final String jsonString = loadAsString(JSON_ALBERTA_GET_PHARMACY_RESPONSE);
		final Pharmacy pharmacy = json.deserialize(jsonString, Pharmacy.class);

		Mockito.when(pharmacyApi.getPharmacy(any(String.class), any(String.class)))
				.thenReturn(pharmacy);

		return pharmacyApi;
	}
	@NonNull
	private static UserApi prepareUserApi(@NonNull final JSON json) throws ApiException
	{
		final UserApi userApi = Mockito.mock(UserApi.class);
		final String jsonString = loadAsString(JSON_ALBERTA_GET_USER);
		final Users users = json.deserialize(jsonString, Users.class);

		Mockito.when(userApi.getUser(any(String.class), any(String.class)))
				.thenReturn(users);

		return userApi;
	}


	private static String loadAsString(@NonNull final String name)
	{
		final InputStream createdPatientsIS = GetPatientsRouteTests.class.getResourceAsStream(name);
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
			final InputStream bpartnerResponse = GetPatientsRouteTests.class.getResourceAsStream(JSON_UPSERT_BPARTNER_RESPONSE);
			exchange.getIn().setBody(bpartnerResponse);
		}
	}

	private static class MockBPartnerRelationUpsertResponse implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	private static class MockGetDocumentsProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}
}
