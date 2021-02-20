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

package de.metas.camel.alberta.processor;

import de.metas.camel.alberta.AlbertaConnectionDetails;
import de.metas.camel.alberta.BPartnerUpsertRequestProducer;
import de.metas.camel.externalsystems.common.BPUpsertCamelRequest;
import de.metas.common.externalreference.JsonExternalReferenceItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupResponse;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.util.EmptyUtil;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.DoctorApi;
import io.swagger.client.api.HospitalApi;
import io.swagger.client.api.NursingHomeApi;
import io.swagger.client.api.NursingServiceApi;
import io.swagger.client.model.Doctor;
import io.swagger.client.model.Hospital;
import io.swagger.client.model.NursingHome;
import io.swagger.client.model.NursingService;
import io.swagger.client.model.Patient;
import io.swagger.client.model.PatientHospital;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static de.metas.camel.alberta.GetPatientsRouteConstants.ROUTE_PROPERTY_ALBERTA_CONN_DETAILS;
import static de.metas.camel.alberta.GetPatientsRouteConstants.ROUTE_PROPERTY_BP_IDENTIFIER_TO_ROLE;
import static de.metas.camel.alberta.GetPatientsRouteConstants.ROUTE_PROPERTY_CURRENT_PATIENT;
import static de.metas.camel.alberta.GetPatientsRouteConstants.ROUTE_PROPERTY_ORG_CODE;

public class CreateBPartnerReqProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final JsonExternalReferenceLookupResponse esrLookupResponse = exchange.getIn().getBody(JsonExternalReferenceLookupResponse.class);
		final Map<String, JsonMetasfreshId> externalId2MetasfreshId = buildExternalId2MetasfreshIdMap(esrLookupResponse);

		final AlbertaConnectionDetails connectionDetails = exchange.getProperty(ROUTE_PROPERTY_ALBERTA_CONN_DETAILS, AlbertaConnectionDetails.class);
		if (connectionDetails == null)
		{
			throw new RuntimeException("Missing camel route property: " + ROUTE_PROPERTY_ALBERTA_CONN_DETAILS);
		}

		final Patient patient = exchange.getProperty(ROUTE_PROPERTY_CURRENT_PATIENT, Patient.class);
		if (patient == null)
		{
			throw new RuntimeException("Missing camel route property: " + ROUTE_PROPERTY_CURRENT_PATIENT);
		}

		final String orgCode = exchange.getProperty(ROUTE_PROPERTY_ORG_CODE, String.class);
		if (orgCode == null)
		{
			throw new RuntimeException("Missing camel route property: " + ROUTE_PROPERTY_ORG_CODE);
		}

		final BPartnerUpsertRequestProducer bPartnerUpsertRequestProducer = BPartnerUpsertRequestProducer.builder()
				.patient(patient)
				.externalId2MetasfreshId(externalId2MetasfreshId)
				.orgCode(orgCode)
				.doctor(getDoctorOrNull(connectionDetails, patient.getPrimaryDoctorId()))
				.nursingHome(getNursingHomeOrNull(connectionDetails, patient.getNursingHomeId()))
				.nursingService(getNursingServiceOrNull(connectionDetails, patient.getNursingServiceId()))
				.hospital(getHospital(connectionDetails, patient.getHospital()))
				.build();

		final BPartnerUpsertRequestProducer.BPartnerRequestProducerResult result = bPartnerUpsertRequestProducer.run();
		final BPartnerRoleInfoProvider bPartnerRoleInfoProvider = BPartnerRoleInfoProvider.builder()
				.sourceBPartnerIdentifier(result.getPatientBPartnerIdentifier())
				.bpIdentifier2Role(result.getBPartnerIdentifier2RelationRole())
				.sourceBPartnerLocationIdentifier(result.getPatientMainAddressIdentifier())
				.build();

		final BPUpsertCamelRequest bpUpsertCamelRequest = BPUpsertCamelRequest.builder()
				.jsonRequestBPartnerUpsert(result.getJsonRequestBPartnerUpsert())
				.orgCode(orgCode)
				.build();

		exchange.getIn().setBody(bpUpsertCamelRequest);
		exchange.setProperty(ROUTE_PROPERTY_BP_IDENTIFIER_TO_ROLE, bPartnerRoleInfoProvider);
	}

	private Map<String, JsonMetasfreshId> buildExternalId2MetasfreshIdMap(@NonNull final JsonExternalReferenceLookupResponse esrLookupResponse)
	{
		if (esrLookupResponse.getItems() == null || esrLookupResponse.getItems().isEmpty())
		{
			return new HashMap<>();
		}

		return esrLookupResponse.getItems()
				.stream()
				.filter(item -> item.getMetasfreshId() != null)
				.collect(Collectors.toMap(item -> item.getLookupItem().getId(),
						JsonExternalReferenceItem::getMetasfreshId));
	}

	@Nullable
	private Doctor getDoctorOrNull(@NonNull final AlbertaConnectionDetails albertaConnectionDetails, @Nullable final String doctorId) throws ApiException
	{
		if (EmptyUtil.isBlank(doctorId))
		{
			return null;
		}
		final var apiClient = new ApiClient().setBasePath(albertaConnectionDetails.getBasePath());
		final DoctorApi doctorApi = new DoctorApi(apiClient);
		
		final Doctor doctor = doctorApi.getDoctor(albertaConnectionDetails.getApiKey(), albertaConnectionDetails.getTenant(), doctorId);
	
		if (doctor == null)
		{
			throw new RuntimeException("No info returned for doctorId: " + doctorId);
		}

		return doctor;
	}

	@Nullable
	private NursingHome getNursingHomeOrNull(@NonNull final AlbertaConnectionDetails albertaConnectionDetails, @Nullable final String nursingHomeId) throws ApiException
	{
		if (EmptyUtil.isBlank(nursingHomeId))
		{
			return null;
		}
		final var apiClient = new ApiClient().setBasePath(albertaConnectionDetails.getBasePath());
		final NursingHomeApi nursingHomeApi = new NursingHomeApi(apiClient);

		final NursingHome nursingHome = nursingHomeApi.geNursingHome(albertaConnectionDetails.getApiKey(), albertaConnectionDetails.getTenant(), nursingHomeId);

		if (nursingHome == null)
		{
			throw new RuntimeException("No info returned for nursingHomeId: " + nursingHomeId);
		}

		return nursingHome;
	}

	@Nullable
	private NursingService getNursingServiceOrNull(@NonNull final AlbertaConnectionDetails albertaConnectionDetails, @Nullable final String nursingServiceId) throws ApiException
	{
		if (EmptyUtil.isBlank(nursingServiceId))
		{
			return null;
		}
		final var apiClient = new ApiClient().setBasePath(albertaConnectionDetails.getBasePath());
		final NursingServiceApi nursingServiceApi = new NursingServiceApi(apiClient);

		final NursingService nursingService = nursingServiceApi.getNursingService(albertaConnectionDetails.getApiKey(), albertaConnectionDetails.getTenant(), nursingServiceId);

		if (nursingService == null)
		{
			throw new RuntimeException("No info returned for nursingServiceId: " + nursingServiceId);
		}

		return nursingService;
	}

	@Nullable
	private Hospital getHospital(@NonNull final AlbertaConnectionDetails albertaConnectionDetails, @Nullable final PatientHospital patientHospital) throws ApiException
	{
		if (patientHospital == null || EmptyUtil.isBlank(patientHospital.getHospitalId()))
		{
			return null;
		}

		final var apiClient = new ApiClient().setBasePath(albertaConnectionDetails.getBasePath());
		final HospitalApi hospitalApi = new HospitalApi(apiClient);

		final Hospital hospital = hospitalApi.getHospital(albertaConnectionDetails.getApiKey(), albertaConnectionDetails.getTenant(), patientHospital.getHospitalId());

		if (hospital == null)
		{
			throw new RuntimeException("No info returned for hospitalId: " + patientHospital.getHospitalId());
		}

		return hospital;
	}
}
