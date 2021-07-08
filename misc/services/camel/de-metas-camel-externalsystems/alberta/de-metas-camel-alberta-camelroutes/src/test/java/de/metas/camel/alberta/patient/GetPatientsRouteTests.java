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

package de.metas.camel.alberta.patient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import de.metas.camel.externalsystems.common.BPRelationsCamelRequest;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import de.metas.common.bprelation.request.JsonRequestBPRelationsUpsert;
import de.metas.common.externalreference.JsonExternalReferenceLookupRequest;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import io.swagger.client.ApiException;
import io.swagger.client.JSON;
import io.swagger.client.api.DoctorApi;
import io.swagger.client.api.HospitalApi;
import io.swagger.client.api.NursingHomeApi;
import io.swagger.client.api.NursingServiceApi;
import io.swagger.client.api.PatientApi;
import io.swagger.client.api.PayerApi;
import io.swagger.client.api.PharmacyApi;
import io.swagger.client.model.ArrayOfPatients;
import io.swagger.client.model.Doctor;
import io.swagger.client.model.Hospital;
import io.swagger.client.model.NursingService;
import io.swagger.client.model.Payer;
import io.swagger.client.model.Pharmacy;
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
import java.util.Properties;
import java.util.stream.Collectors;

import static de.metas.camel.alberta.patient.GetAlbertaPatientsRoute.CREATE_ESR_QUERY_REQ_PROCESSOR_ID;
import static de.metas.camel.alberta.patient.GetAlbertaPatientsRoute.CREATE_UPSERT_BPARTNER_RELATION_REQUEST_PROCESSOR_ID;
import static de.metas.camel.alberta.patient.GetAlbertaPatientsRoute.CREATE_UPSERT_BPARTNER_REQUEST_PROCESSOR_ID;
import static de.metas.camel.alberta.patient.GetAlbertaPatientsRoute.GET_PATIENTS_ROUTE_ID;
import static de.metas.camel.alberta.patient.GetAlbertaPatientsRoute.PREPARE_PATIENTS_API_PROCESSOR_ID;
import static de.metas.camel.alberta.patient.GetAlbertaPatientsRoute.PROCESS_PATIENT_ROUTE_ID;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.PatientStatus.UPDATED;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_DOCTOR_API;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_HOSPITAL_API;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_NURSINGHOME_API;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_NURSINGSERVICE_API;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_PATIENT_API;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_ALBERTA_PAYER_API;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_ALBERTA_PHARMACY_API;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_LOOKUP_EXTERNALREFERENCE_CAMEL_URI;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class GetPatientsRouteTests extends CamelTestSupport
{
	private static final String MOCK_ESR_QUERY_REQUEST = "mock:esrQueryRequest";
	private static final String MOCK_UPSERT_BPARTNER_REQUEST = "mock:upsertBPartnerRequest";
	private static final String MOCK_UPSERT_BPARTNER_RELATION_REQUEST = "mock:upsertBPartnerRelationRequest";

	private static final String JSON_MF_GET_PATIENTS_REQUEST = "/de/metas/camel/alberta/patient/10_ExternalSystemRequest.json";
	private static final String JSON_ALBERTA_GET_CREATED_PATIENTS_RESPONSE = "/de/metas/camel/alberta/patient/20_GetCreatedPatientsAlbertaResponse.json";
	private static final String JSON_ALBERTA_GET_UPDATED_PATIENTS_RESPONSE = "/de/metas/camel/alberta/patient/30_GetUpdatedPatientsAlbertaResponse.json";
	private static final String JSON_ALBERTA_GET_DOCTOR_RESPONSE = "/de/metas/camel/alberta/patient/31_GetDoctorAlberta_5ab23eb59d69c74b68d0eded_Response.json";
	private static final String JSON_ALBERTA_GET_NURINGSERVICE_RESPONSE = "/de/metas/camel/alberta/patient/32_GetNursingServiceAlberta_5ab2383d9d69c74b68cf19dc_Response.json";
	private static final String JSON_ALBERTA_GET_HOSPIPAL_RESPONSE = "/de/metas/camel/alberta/patient/33_GetHospitalAlberta_5ab233bc9d69c74b68cec23a_Response.json";
	private static final String JSON_ALBERTA_GET_PAYER_RESPONSE = "/de/metas/camel/alberta/patient/34_GetPayerAlberta_5ada01a2c3918e1bdcb5460e_Response.json";
	private static final String JSON_ALBERTA_GET_PHARMACY_RESPONSE = "/de/metas/camel/alberta/patient/35_GetPharmacyAlberta_5ab2390e9d69c74b68cf4f2d_Response.json";

	private static final String JSON_EXTERNAL_REFERENCE_LOOKUP_REQUEST = "/de/metas/camel/alberta/patient/40_GetExternalReferencesMetasfreshRequest.json";
	private static final String JSON_EXTERNAL_REFERENCE_LOOKUP_RESPONSE = "/de/metas/camel/alberta/patient/50_GetExternalReferencesMetasfreshResponse.json";
	private static final String JSON_UPSERT_BPARTNER_REQUEST = "/de/metas/camel/alberta/patient/60_UpsertBPartnerMetasfreshRequest.json";
	private static final String JSON_UPSERT_BPARTNER_RESPONSE = "/de/metas/camel/alberta/patient/70_UpsertBPartnerMetasfreshResponse.json";
	private static final String JSON_UPSERT_BPARTNER_RELATIONS_REQUEST = "/de/metas/camel/alberta/patient/80_UpsertBPartnerRelationsMetasfreshRequest.json";
	private static final String JSON_UPSERT_BPARTNER_RELATIONS_RESPONSE = "/de/metas/camel/alberta/patient/90_UpsertBPartnerRelationsMetasfreshResponse.json";

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
	 * Note that according to {@link #JSON_EXTERNAL_REFERENCE_LOOKUP_RESPONSE}, none of the BPartners exist in metasfresh, so they are all created.
	 */
	@Test
	void happyFlow() throws Exception
	{
		final MockExternalReferenceResponse successfullySentExternalReferenceRequest = new MockExternalReferenceResponse();
		final MockBPartnerUpsertResponse mockBPartnerUpsertResponse = new MockBPartnerUpsertResponse();
		final MockBPartnerRelationUpsertResponse mockBPartnerRelationUpsertResponse = new MockBPartnerRelationUpsertResponse();

		prepareRouteForTesting(successfullySentExternalReferenceRequest, mockBPartnerUpsertResponse, mockBPartnerRelationUpsertResponse);

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_MF_GET_PATIENTS_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		//validate the external system query request that is done towards metasfresh
		final MockEndpoint esrQueryValidationMockEndpoint = getMockEndpoint(MOCK_ESR_QUERY_REQUEST);
		final InputStream esrQueryRequestExpected = this.getClass().getResourceAsStream(JSON_EXTERNAL_REFERENCE_LOOKUP_REQUEST);
		esrQueryValidationMockEndpoint.expectedBodiesReceived(objectMapper.readValue(esrQueryRequestExpected, JsonExternalReferenceLookupRequest.class));

		//validate the upsert-bpartner-request that is done towards metasfresh
		final MockEndpoint bpartnerUpsertMockEndpoint = getMockEndpoint(MOCK_UPSERT_BPARTNER_REQUEST);
		final InputStream bparnerUpsertRequestExpected = this.getClass().getResourceAsStream(JSON_UPSERT_BPARTNER_REQUEST);
		final JsonRequestBPartnerUpsert jsonRequestBPartnerUpsert = objectMapper.readValue(bparnerUpsertRequestExpected, JsonRequestBPartnerUpsert.class);
		final BPUpsertCamelRequest bpUpsertCamelRequest = BPUpsertCamelRequest.builder()
				.jsonRequestBPartnerUpsert(jsonRequestBPartnerUpsert)
				.orgCode(invokeExternalSystemRequest.getOrgCode()).build();
		bpartnerUpsertMockEndpoint.expectedBodiesReceived(bpUpsertCamelRequest);

		//validate the upsert-bpartner-relation-request that is done towards metasfresh
		final MockEndpoint bpartnerRelationUpsertMockEndpoint = getMockEndpoint(MOCK_UPSERT_BPARTNER_RELATION_REQUEST);
		final InputStream bparnerRelationUpsertRequestExpected = this.getClass().getResourceAsStream(JSON_UPSERT_BPARTNER_RELATIONS_REQUEST);
		final JsonRequestBPRelationsUpsert jsonRequestBPRelationsUpsert = objectMapper.readValue(bparnerRelationUpsertRequestExpected, JsonRequestBPRelationsUpsert.class);
		final BPRelationsCamelRequest bpRelationsCamelRequest = BPRelationsCamelRequest.builder()
				.jsonRequestBPRelationsUpsert(jsonRequestBPRelationsUpsert)
				.bpartnerIdentifier("910") // this is the ID that camel got from 70_UpsertBPartnerMetasfreshResponse.json
				.build();
		bpartnerRelationUpsertMockEndpoint.expectedBodiesReceived(bpRelationsCamelRequest);

		//fire the route
		template.sendBody("direct:" + GET_PATIENTS_ROUTE_ID, invokeExternalSystemRequest);

		assertMockEndpointsSatisfied();
		assertThat(successfullySentExternalReferenceRequest.called).isEqualTo(1);
		assertThat(mockBPartnerUpsertResponse.called).isEqualTo(1);
		assertThat(mockBPartnerRelationUpsertResponse.called).isEqualTo(1);
	}

	private void prepareRouteForTesting(
			final MockExternalReferenceResponse successfullySentExternalReferenceRequest,
			final MockBPartnerUpsertResponse mockBPartnerUpsertResponse,
			final MockBPartnerRelationUpsertResponse mockBPartnerRelationUpsertResponse) throws Exception
	{
		// inject our mock processor that returns the patient-JSON from alberta
		AdviceWith.adviceWith(context, GET_PATIENTS_ROUTE_ID,
				advice -> advice.weaveById(PREPARE_PATIENTS_API_PROCESSOR_ID)
						.replace()
						.process(new MockPreparePatientsApiProcessor()));

		AdviceWith.adviceWith(context, PROCESS_PATIENT_ROUTE_ID,
				advice -> {
					// validate the the external reference query request and send a response 
					advice.weaveById(CREATE_ESR_QUERY_REQ_PROCESSOR_ID)
							.after()
							.to(MOCK_ESR_QUERY_REQUEST);
					advice.interceptSendToEndpoint("{{" + MF_LOOKUP_EXTERNALREFERENCE_CAMEL_URI + "}}")
							.skipSendToOriginalEndpoint()
							.process(successfullySentExternalReferenceRequest);

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
					advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPRELATION_CAMEL_URI + "}}")
							.skipSendToOriginalEndpoint()
							.process(mockBPartnerRelationUpsertResponse);
				});
	}

	private static class MockPreparePatientsApiProcessor implements Processor
	{
		@Override
		public void process(@NonNull final Exchange exchange) throws ApiException
		{
			final JSON json = new JSON();
			exchange.setProperty(ROUTE_PROPERTY_PATIENT_API, preparePatientApiClient(json));
			exchange.setProperty(ROUTE_PROPERTY_DOCTOR_API, prepareDoctorApiClient(json));
			exchange.setProperty(ROUTE_PROPERTY_NURSINGHOME_API, prepareNursingHomeApiClient(json));
			exchange.setProperty(ROUTE_PROPERTY_NURSINGSERVICE_API, prepareNursingServiceApiClient(json));
			exchange.setProperty(ROUTE_PROPERTY_HOSPITAL_API, prepareHospitalApiClient(json));
			exchange.setProperty(ROUTE_PROPERTY_ALBERTA_PAYER_API, preparePayerApiClient(json));
			exchange.setProperty(ROUTE_PROPERTY_ALBERTA_PHARMACY_API, preparePharmacyApiClient(json));
		}

		@NonNull
		private static PatientApi preparePatientApiClient(@NonNull final JSON json) throws ApiException
		{
			final PatientApi albertaPatientApi = Mockito.mock(PatientApi.class);

			//1. mock retrieval of created payments
			final String createdPatientsStr = loadAsString(JSON_ALBERTA_GET_CREATED_PATIENTS_RESPONSE);
			final ArrayOfPatients createdPatients = json.deserialize(createdPatientsStr, ArrayOfPatients.class);
			//Mockito.when(albertaPatientApi.getCreatedPatients(any(String.class), any(String.class), eq(CREATED.getValue()), any(String.class)))
			Mockito.when(albertaPatientApi.getCreatedPatients(any(String.class), any(String.class), any(String.class), any(String.class)))
					.thenReturn(createdPatients);

			//2. mock retrieval of updated payments
			final String updatedPatientsStr = loadAsString(JSON_ALBERTA_GET_UPDATED_PATIENTS_RESPONSE);
			final ArrayOfPatients updatedPatients = json.deserialize(updatedPatientsStr, ArrayOfPatients.class);
			Mockito.when(albertaPatientApi.getCreatedPatients(any(String.class), any(String.class), eq(UPDATED.getValue()), any(String.class)))
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

		Mockito.when(albertaDoctorApi.getDoctor(any(String.class), any(String.class), any(String.class)))
				.thenReturn(doctor);

		return albertaDoctorApi;
	}

	@NonNull
	private static NursingHomeApi prepareNursingHomeApiClient(@NonNull final JSON json) throws ApiException
	{
		final NursingHomeApi nursingHomeApi = Mockito.mock(NursingHomeApi.class);
		// final String jsonString = loadAsString(JSON_ALBERTA_GET_NURSINGHOME_RESPONSE);
		// final NursingHome nursingHome = json.deserialize(jsonString, NursingHome.class);

		Mockito.when(nursingHomeApi.geNursingHome(any(String.class), any(String.class), any(String.class)))
				.thenReturn(null);

		return nursingHomeApi;
	}

	@NonNull
	private static NursingServiceApi prepareNursingServiceApiClient(@NonNull final JSON json) throws ApiException
	{
		final NursingServiceApi nursingServiceApi = Mockito.mock(NursingServiceApi.class);
		final String jsonString = loadAsString(JSON_ALBERTA_GET_NURINGSERVICE_RESPONSE);
		final NursingService nursingService = json.deserialize(jsonString, NursingService.class);

		Mockito.when(nursingServiceApi.getNursingService(any(String.class), any(String.class), any(String.class)))
				.thenReturn(nursingService);

		return nursingServiceApi;
	}

	@NonNull
	private static HospitalApi prepareHospitalApiClient(@NonNull final JSON json) throws ApiException
	{
		final HospitalApi hospitalApi = Mockito.mock(HospitalApi.class);
		final String jsonString = loadAsString(JSON_ALBERTA_GET_HOSPIPAL_RESPONSE);
		final Hospital hospital = json.deserialize(jsonString, Hospital.class);

		Mockito.when(hospitalApi.getHospital(any(String.class), any(String.class), any(String.class)))
				.thenReturn(hospital);

		return hospitalApi;
	}

	@NonNull
	private static PayerApi preparePayerApiClient(@NonNull final JSON json) throws ApiException
	{
		final PayerApi payerApi = Mockito.mock(PayerApi.class);
		final String jsonString = loadAsString(JSON_ALBERTA_GET_PAYER_RESPONSE);
		final Payer payer = json.deserialize(jsonString, Payer.class);

		Mockito.when(payerApi.getPayer(any(String.class), any(String.class), any(String.class)))
				.thenReturn(payer);

		return payerApi;
	}

	@NonNull
	private static PharmacyApi preparePharmacyApiClient(@NonNull final JSON json) throws ApiException
	{
		final PharmacyApi pharmacyApi = Mockito.mock(PharmacyApi.class);
		final String jsonString = loadAsString(JSON_ALBERTA_GET_PHARMACY_RESPONSE);
		final Pharmacy pharmacy = json.deserialize(jsonString, Pharmacy.class);

		Mockito.when(pharmacyApi.getPharmacy(any(String.class), any(String.class), any(String.class)))
				.thenReturn(pharmacy);

		return pharmacyApi;
	}

	private static String loadAsString(@NonNull final String name)
	{
		final InputStream createdPatientsIS = GetPatientsRouteTests.class.getResourceAsStream(name);
		return new BufferedReader(
				new InputStreamReader(createdPatientsIS, StandardCharsets.UTF_8))
				.lines()
				.collect(Collectors.joining("\n"));
	}

	private static class MockExternalReferenceResponse implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
			final InputStream esrLookupResponse = GetPatientsRouteTests.class.getResourceAsStream(JSON_EXTERNAL_REFERENCE_LOOKUP_RESPONSE);
			exchange.getIn().setBody(esrLookupResponse);
		}
	}

	private static class MockBPartnerUpsertResponse implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
			final InputStream esrLookupResponse = GetPatientsRouteTests.class.getResourceAsStream(JSON_UPSERT_BPARTNER_RESPONSE);
			exchange.getIn().setBody(esrLookupResponse);
		}
	}

	private static class MockBPartnerRelationUpsertResponse implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
			final InputStream esrLookupResponse = GetPatientsRouteTests.class.getResourceAsStream(JSON_UPSERT_BPARTNER_RELATIONS_RESPONSE);
			exchange.getIn().setBody(esrLookupResponse);
		}
	}
}
