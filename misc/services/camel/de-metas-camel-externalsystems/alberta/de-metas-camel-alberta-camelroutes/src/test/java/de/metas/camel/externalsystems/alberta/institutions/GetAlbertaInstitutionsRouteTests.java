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

package de.metas.camel.externalsystems.alberta.institutions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.alberta.common.AlbertaConnectionDetails;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.common.bpartner.v2.request.alberta.JsonBPartnerRole;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import io.swagger.client.ApiException;
import io.swagger.client.JSON;
import io.swagger.client.api.DoctorApi;
import io.swagger.client.api.HospitalApi;
import io.swagger.client.api.NursingHomeApi;
import io.swagger.client.api.NursingServiceApi;
import io.swagger.client.api.PayerApi;
import io.swagger.client.api.PharmacyApi;
import io.swagger.client.model.Doctor;
import io.swagger.client.model.Hospital;
import io.swagger.client.model.NursingHome;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

public class GetAlbertaInstitutionsRouteTests extends CamelTestSupport
{
	private static final String MOCK_UPSERT_BPARTNER_REQUEST = "mock:upsertBPartnerRequest";

	private static final String JSON_MF_GET_DOCTOR_REQUEST = "10_1_Doctor_ExternalSystemRequest.json";
	private static final String JSON_MF_GET_HOSPITAL_REQUEST = "20_1_Hospital_ExternalSystemRequest.json";
	private static final String JSON_MF_GET_NURSINGHOME_REQUEST = "30_1_NursingHome_ExternalSystemRequest.json";
	private static final String JSON_MF_GET_NURSINGSERVICE_REQUEST = "40_1_NursingService_ExternalSystemRequest.json";
	private static final String JSON_MF_GET_PAYER_REQUEST = "50_1_Payer_ExternalSystemRequest.json";
	private static final String JSON_MF_GET_PHARMACY_REQUEST = "60_1_Pharmacy_ExternalSystemRequest.json";

	private static final String JSON_ALBERTA_GET_DOCTOR_RESPONSE = "10_2_GetDoctorAlberta_Response.json";
	private static final String JSON_ALBERTA_GET_HOSPITAL_RESPONSE = "20_2_GetHospitalAlberta_Response.json";
	private static final String JSON_ALBERTA_GET_NURINGHOME_RESPONSE = "30_2_GetNursingHomeAlberta_Response.json";
	private static final String JSON_ALBERTA_GET_NURINGSERVICE_RESPONSE = "40_2_GetNursingServiceAlberta_Response.json";
	private static final String JSON_ALBERTA_GET_PAYER_RESPONSE = "50_2_GetPayerAlberta_Response.json";
	private static final String JSON_ALBERTA_GET_PHARMACY_RESPONSE = "60_2_GetPharmacyAlberta_Response.json";

	private static final String JSON_UPSERT_BPARTNER_REQUEST_PD = "10_3_UpsertBPartnerMetasfreshRequest_PD.json";
	private static final String JSON_UPSERT_BPARTNER_REQUEST_HO = "20_3_UpsertBPartnerMetasfreshRequest_HO.json";
	private static final String JSON_UPSERT_BPARTNER_REQUEST_NH = "30_3_UpsertBPartnerMetasfreshRequest_NH.json";
	private static final String JSON_UPSERT_BPARTNER_REQUEST_NS = "40_3_UpsertBPartnerMetasfreshRequest_NS.json";
	private static final String JSON_UPSERT_BPARTNER_REQUEST_PA = "50_3_UpsertBPartnerMetasfreshRequest_PA.json";
	private static final String JSON_UPSERT_BPARTNER_REQUEST_PH = "60_3_UpsertBPartnerMetasfreshRequest_PH.json";

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final Properties properties = new Properties();
		try
		{
			properties.load(GetAlbertaInstitutionsRouteTests.class.getClassLoader().getResourceAsStream("application.properties"));
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
		return new GetAlbertaInstitutionsRoute();
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	/**
	 * GETs Doctor institution from the mocked Alberta-API and PUT BPartners to the mocked metasfresh-API.
	 */
	@Test
	void happyFlow_Doctor() throws Exception
	{
		final MockBPartnerUpsertResponse mockBPartnerUpsertResponse = new MockBPartnerUpsertResponse();

		prepareRouteForTesting(mockBPartnerUpsertResponse);

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_MF_GET_DOCTOR_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		//validate the upsert-bpartner-request that is done towards metasfresh
		final MockEndpoint bpartnerUpsertMockEndpoint = getMockEndpoint(MOCK_UPSERT_BPARTNER_REQUEST);

		final InputStream expectedRequestAsIS = this.getClass().getResourceAsStream(JSON_UPSERT_BPARTNER_REQUEST_PD);
		final BPUpsertCamelRequest expectedRequest = objectMapper.readValue(expectedRequestAsIS, BPUpsertCamelRequest.class);

		bpartnerUpsertMockEndpoint.expectedBodiesReceived(expectedRequest);

		//fire the route
		template.sendBody("direct:" + GetAlbertaInstitutionsRoute.EXTERNAL_SYSTEM_REQUEST, invokeExternalSystemRequest);

		assertMockEndpointsSatisfied();
		assertThat(mockBPartnerUpsertResponse.called).isEqualTo(1);
	}

	/**
	 * GETs Hospital institution from the mocked Alberta-API and PUT BPartners to the mocked metasfresh-API.
	 */
	@Test
	void happyFlow_Hospital() throws Exception
	{
		final MockBPartnerUpsertResponse mockBPartnerUpsertResponse = new MockBPartnerUpsertResponse();

		prepareRouteForTesting(mockBPartnerUpsertResponse);

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_MF_GET_HOSPITAL_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		//validate the upsert-bpartner-request that is done towards metasfresh
		final MockEndpoint bpartnerUpsertMockEndpoint = getMockEndpoint(MOCK_UPSERT_BPARTNER_REQUEST);

		final InputStream expectedRequestAsIS = this.getClass().getResourceAsStream(JSON_UPSERT_BPARTNER_REQUEST_HO);
		final BPUpsertCamelRequest expectedRequest = objectMapper.readValue(expectedRequestAsIS, BPUpsertCamelRequest.class);

		bpartnerUpsertMockEndpoint.expectedBodiesReceived(expectedRequest);

		//fire the route
		template.sendBody("direct:" + GetAlbertaInstitutionsRoute.EXTERNAL_SYSTEM_REQUEST, invokeExternalSystemRequest);

		assertMockEndpointsSatisfied();
		assertThat(mockBPartnerUpsertResponse.called).isEqualTo(1);
	}

	/**
	 * GETs NursingService institution from the mocked Alberta-API and PUT BPartners to the mocked metasfresh-API.
	 */
	@Test
	void happyFlow_NursingService() throws Exception
	{
		final MockBPartnerUpsertResponse mockBPartnerUpsertResponse = new MockBPartnerUpsertResponse();

		prepareRouteForTesting(mockBPartnerUpsertResponse);

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_MF_GET_NURSINGSERVICE_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		//validate the upsert-bpartner-request that is done towards metasfresh
		final MockEndpoint bpartnerUpsertMockEndpoint = getMockEndpoint(MOCK_UPSERT_BPARTNER_REQUEST);

		final InputStream expectedRequestAsIS = this.getClass().getResourceAsStream(JSON_UPSERT_BPARTNER_REQUEST_NS);
		final BPUpsertCamelRequest expectedRequest = objectMapper.readValue(expectedRequestAsIS, BPUpsertCamelRequest.class);

		bpartnerUpsertMockEndpoint.expectedBodiesReceived(expectedRequest);

		//fire the route
		template.sendBody("direct:" + GetAlbertaInstitutionsRoute.EXTERNAL_SYSTEM_REQUEST, invokeExternalSystemRequest);

		assertMockEndpointsSatisfied();
		assertThat(mockBPartnerUpsertResponse.called).isEqualTo(1);
	}

	/**
	 * GETs Payer institution from the mocked Alberta-API and PUT BPartners to the mocked metasfresh-API.
	 */
	@Test
	void happyFlow_Payer() throws Exception
	{
		final MockBPartnerUpsertResponse mockBPartnerUpsertResponse = new MockBPartnerUpsertResponse();

		prepareRouteForTesting(mockBPartnerUpsertResponse);

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_MF_GET_PAYER_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		//validate the upsert-bpartner-request that is done towards metasfresh
		final MockEndpoint bpartnerUpsertMockEndpoint = getMockEndpoint(MOCK_UPSERT_BPARTNER_REQUEST);

		final InputStream expectedRequestAsIS = this.getClass().getResourceAsStream(JSON_UPSERT_BPARTNER_REQUEST_PA);
		final BPUpsertCamelRequest expectedRequest = objectMapper.readValue(expectedRequestAsIS, BPUpsertCamelRequest.class);

		bpartnerUpsertMockEndpoint.expectedBodiesReceived(expectedRequest);

		//fire the route
		template.sendBody("direct:" + GetAlbertaInstitutionsRoute.EXTERNAL_SYSTEM_REQUEST, invokeExternalSystemRequest);

		assertMockEndpointsSatisfied();
		assertThat(mockBPartnerUpsertResponse.called).isEqualTo(1);
	}

	/**
	 * GETs Pharmacy institution from the mocked Alberta-API and PUT BPartners to the mocked metasfresh-API.
	 */
	@Test
	void happyFlow_Pharmacy() throws Exception
	{
		final MockBPartnerUpsertResponse mockBPartnerUpsertResponse = new MockBPartnerUpsertResponse();

		prepareRouteForTesting(mockBPartnerUpsertResponse);

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_MF_GET_PHARMACY_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		//validate the upsert-bpartner-request that is done towards metasfresh
		final MockEndpoint bpartnerUpsertMockEndpoint = getMockEndpoint(MOCK_UPSERT_BPARTNER_REQUEST);

		final InputStream expectedRequestAsIS = this.getClass().getResourceAsStream(JSON_UPSERT_BPARTNER_REQUEST_PH);
		final BPUpsertCamelRequest expectedRequest = objectMapper.readValue(expectedRequestAsIS, BPUpsertCamelRequest.class);

		bpartnerUpsertMockEndpoint.expectedBodiesReceived(expectedRequest);

		//fire the route
		template.sendBody("direct:" + GetAlbertaInstitutionsRoute.EXTERNAL_SYSTEM_REQUEST, invokeExternalSystemRequest);

		assertMockEndpointsSatisfied();
		assertThat(mockBPartnerUpsertResponse.called).isEqualTo(1);
	}

	/**
	 * GETs NursingHome institution from the mocked Alberta-API and PUT BPartners to the mocked metasfresh-API.
	 */
	@Test
	void happyFlow_NursingHome() throws Exception
	{
		final MockBPartnerUpsertResponse mockBPartnerUpsertResponse = new MockBPartnerUpsertResponse();

		prepareRouteForTesting(mockBPartnerUpsertResponse);

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_MF_GET_NURSINGHOME_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		//validate the upsert-bpartner-request that is done towards metasfresh
		final MockEndpoint bpartnerUpsertMockEndpoint = getMockEndpoint(MOCK_UPSERT_BPARTNER_REQUEST);

		final InputStream expectedRequestAsIS = this.getClass().getResourceAsStream(JSON_UPSERT_BPARTNER_REQUEST_NH);
		final BPUpsertCamelRequest expectedRequest = objectMapper.readValue(expectedRequestAsIS, BPUpsertCamelRequest.class);

		bpartnerUpsertMockEndpoint.expectedBodiesReceived(expectedRequest);

		//fire the route
		template.sendBody("direct:" + GetAlbertaInstitutionsRoute.EXTERNAL_SYSTEM_REQUEST, invokeExternalSystemRequest);

		assertMockEndpointsSatisfied();
		assertThat(mockBPartnerUpsertResponse.called).isEqualTo(1);
	}

	private void prepareRouteForTesting(final MockBPartnerUpsertResponse mockBPartnerUpsertResponse) throws Exception
	{
		AdviceWith.adviceWith(context, GetAlbertaInstitutionsRoute.EXTERNAL_SYSTEM_REQUEST,
							  advice -> {
								  advice.weaveById(GetAlbertaInstitutionsRoute.PREPARE_ALBERTA_INSTITUTIONS_CONTEXT_PROCESSOR_ID)
										  .replace()
										  .process(new MockPrepareContext());

								  advice.weaveById(GetAlbertaInstitutionsRoute.PREPARE_ALBERTA_BPARTNER_PROCESSOR_ID)
										  .after()
										  .to(MOCK_UPSERT_BPARTNER_REQUEST);

								  advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")
										  .skipSendToOriginalEndpoint()
										  .process(mockBPartnerUpsertResponse);
							  });
	}

	private static class MockPrepareContext implements Processor
	{
		@Override
		public void process(@NonNull final Exchange exchange) throws ApiException
		{
			final JSON json = new JSON();

			final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

			final String externalReference = request.getParameters().get(ExternalSystemConstants.PARAM_ALBERTA_ID);
			final String role = request.getParameters().get(ExternalSystemConstants.PARAM_ALBERTA_ROLE);

			final AlbertaConnectionDetails albertaConnectionDetails = AlbertaConnectionDetails.builder()
					.apiKey("apiKey")
					.basePath("basePath")
					.tenant("tenant")
					.build();

			final GetInstitutionsRouteContext context = GetInstitutionsRouteContext.builder()
					.orgCode("orgCode")
					.albertaResourceId(externalReference)
					.role(JsonBPartnerRole.valueOf(role))
					.albertaConnectionDetails(albertaConnectionDetails)
					.doctorApi(prepareDoctorApiClient(json))
					.hospitalApi(prepareHospitalApiClient(json))
					.nursingHomeApi(prepareNursingHomeApiClient(json))
					.nursingServiceApi(prepareNursingServiceApiClient(json))
					.payerApi(preparePayerApiClient(json))
					.pharmacyApi(preparePharmacyApiClient(json))
					.albertaConnectionDetails(albertaConnectionDetails)
					.build();

			exchange.setProperty(GetInstitutionsRouteConstants.ROUTE_PROPERTY_GET_INSTITUTIONS_CONTEXT, context);
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
		final String jsonString = loadAsString(JSON_ALBERTA_GET_NURINGHOME_RESPONSE);
		final NursingHome nursingHome = json.deserialize(jsonString, NursingHome.class);

		Mockito.when(nursingHomeApi.geNursingHome(any(String.class), any(String.class)))
				.thenReturn(nursingHome);

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
		final String jsonString = loadAsString(JSON_ALBERTA_GET_HOSPITAL_RESPONSE);
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
		final InputStream createdPatientsIS = GetAlbertaInstitutionsRouteTests.class.getResourceAsStream(name);
		return new BufferedReader(
				new InputStreamReader(createdPatientsIS, StandardCharsets.UTF_8))
				.lines()
				.collect(Collectors.joining("\n"));
	}
}
