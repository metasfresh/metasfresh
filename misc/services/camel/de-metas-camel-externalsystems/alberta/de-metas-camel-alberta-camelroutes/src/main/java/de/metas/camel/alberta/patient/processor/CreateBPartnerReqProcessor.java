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

package de.metas.camel.alberta.patient.processor;

import com.google.common.collect.ImmutableMap;
import de.metas.camel.alberta.patient.AlbertaConnectionDetails;
import de.metas.camel.alberta.patient.BPartnerUpsertRequestProducer;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.common.externalreference.JsonExternalReferenceItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupResponse;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.EmptyUtil;
import io.swagger.client.ApiException;
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
import io.swagger.client.model.Patient;
import io.swagger.client.model.PatientHospital;
import io.swagger.client.model.PatientPayer;
import io.swagger.client.model.Payer;
import io.swagger.client.model.Pharmacy;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.annotation.Nullable;
import java.util.List;

import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_ALBERTA_CONN_DETAILS;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_DOCTOR_API;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_HOSPITAL_API;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_NURSINGHOME_API;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_NURSINGSERVICE_API;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_ALBERTA_PAYER_API;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_ALBERTA_PHARMACY_API;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_BP_IDENTIFIER_TO_ROLE;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_CURRENT_PATIENT;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_ORG_CODE;

/**
 * Takes a {@link JsonExternalReferenceLookupResponse} and the current {@link Patient} and transformes them into a {@link BPUpsertCamelRequest}.
 */
public class CreateBPartnerReqProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final JsonExternalReferenceLookupResponse esrLookupResponse = exchange.getIn().getBody(JsonExternalReferenceLookupResponse.class);
		final ImmutableMap<String, JsonMetasfreshId> externalId2MetasfreshId = buildExternalId2MetasfreshIdMap(esrLookupResponse);

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

		final DoctorApi doctorApi = exchange.getProperty(ROUTE_PROPERTY_DOCTOR_API, DoctorApi.class);
		final Doctor doctorOrNull = getDoctorOrNull(doctorApi, connectionDetails, patient.getPrimaryDoctorId());

		final NursingHomeApi nursingHomeApi = exchange.getProperty(ROUTE_PROPERTY_NURSINGHOME_API, NursingHomeApi.class);
		final NursingHome nursingHomeOrNull = getNursingHomeOrNull(nursingHomeApi, connectionDetails, patient.getNursingHomeId());

		final NursingServiceApi nursingServiceApi = exchange.getProperty(ROUTE_PROPERTY_NURSINGSERVICE_API, NursingServiceApi.class);
		final NursingService nursingServiceOrNull = getNursingServiceOrNull(nursingServiceApi, connectionDetails, patient.getNursingServiceId());

		final HospitalApi hospitalApi = exchange.getProperty(ROUTE_PROPERTY_HOSPITAL_API, HospitalApi.class);
		final Hospital hospital = getHospital(hospitalApi, connectionDetails, patient.getHospital());

		final PayerApi payerApi = exchange.getProperty(ROUTE_PROPERTY_ALBERTA_PAYER_API, PayerApi.class);
		final Payer payerOrNull = getPayerOrNull(payerApi, connectionDetails, patient.getPayer());

		final PharmacyApi pharmacyApi = exchange.getProperty(ROUTE_PROPERTY_ALBERTA_PHARMACY_API, PharmacyApi.class);
		final Pharmacy pharmacyOrNull = getPharmacyOrNull(pharmacyApi, connectionDetails, patient.getPharmacyId());

		final BPartnerUpsertRequestProducer bPartnerUpsertRequestProducer = BPartnerUpsertRequestProducer.builder()
				.patient(patient)
				.externalId2MetasfreshId(externalId2MetasfreshId)
				.orgCode(orgCode)
				.doctor(doctorOrNull)
				.nursingHome(nursingHomeOrNull)
				.nursingService(nursingServiceOrNull)
				.hospital(hospital)
				.payer(payerOrNull)
				.pharmacy(pharmacyOrNull)
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

	private ImmutableMap<String, JsonMetasfreshId> buildExternalId2MetasfreshIdMap(@NonNull final JsonExternalReferenceLookupResponse esrLookupResponse)
	{
		final List<JsonExternalReferenceItem> items = esrLookupResponse.getItems();
		if (items == null || items.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableMap.Builder<String, JsonMetasfreshId> result = ImmutableMap.builder();
		for (final JsonExternalReferenceItem item : items)
		{
			if (item.getMetasfreshId() == null)
			{
				continue;
			}
			result.put(item.getLookupItem().getId(), item.getMetasfreshId());
		}
		return result.build();
	}

	@Nullable
	private Doctor getDoctorOrNull(
			@NonNull final DoctorApi doctorApi,
			@NonNull final AlbertaConnectionDetails albertaConnectionDetails,
			@Nullable final String doctorId) throws ApiException
	{
		if (EmptyUtil.isBlank(doctorId))
		{
			return null;
		}

		final Doctor doctor = doctorApi.getDoctor(albertaConnectionDetails.getApiKey(), albertaConnectionDetails.getTenant(), doctorId);

		if (doctor == null)
		{
			throw new RuntimeException("No info returned for doctorId: " + doctorId);
		}
		return doctor;
	}

	@Nullable
	private NursingHome getNursingHomeOrNull(
			@NonNull final NursingHomeApi nursingHomeApi,
			@NonNull final AlbertaConnectionDetails albertaConnectionDetails,
			@Nullable final String nursingHomeId) throws ApiException
	{
		if (EmptyUtil.isBlank(nursingHomeId))
		{
			return null;
		}

		final NursingHome nursingHome = nursingHomeApi.geNursingHome(albertaConnectionDetails.getApiKey(), albertaConnectionDetails.getTenant(), nursingHomeId);

		if (nursingHome == null)
		{
			throw new RuntimeException("No info returned for nursingHomeId: " + nursingHomeId);
		}
		return nursingHome;
	}

	@Nullable
	private NursingService getNursingServiceOrNull(
			@NonNull final NursingServiceApi nursingServiceApi,
			@NonNull final AlbertaConnectionDetails albertaConnectionDetails,
			@Nullable final String nursingServiceId) throws ApiException
	{
		if (EmptyUtil.isBlank(nursingServiceId))
		{
			return null;
		}

		final NursingService nursingService = nursingServiceApi.getNursingService(albertaConnectionDetails.getApiKey(), albertaConnectionDetails.getTenant(), nursingServiceId);

		if (nursingService == null)
		{
			throw new RuntimeException("No info returned for nursingServiceId: " + nursingServiceId);
		}
		return nursingService;
	}

	@Nullable
	private Hospital getHospital(
			@NonNull final HospitalApi hospitalApi,
			@NonNull final AlbertaConnectionDetails albertaConnectionDetails,
			@Nullable final PatientHospital patientHospital) throws ApiException
	{
		if (patientHospital == null || EmptyUtil.isBlank(patientHospital.getHospitalId()))
		{
			return null;
		}

		final Hospital hospital = hospitalApi.getHospital(albertaConnectionDetails.getApiKey(), albertaConnectionDetails.getTenant(), patientHospital.getHospitalId());

		if (hospital == null)
		{
			throw new RuntimeException("No info returned for hospitalId: " + patientHospital.getHospitalId());
		}
		return hospital;
	}

	@Nullable
	private Payer getPayerOrNull(
			@NonNull final PayerApi payerApi,
			@NonNull final AlbertaConnectionDetails albertaConnectionDetails,
			@Nullable final PatientPayer patientPayer) throws ApiException
	{
		if (patientPayer == null || EmptyUtil.isBlank(patientPayer.getPayerId()))
		{
			return null;
		}

		final Payer payer = payerApi.getPayer(albertaConnectionDetails.getApiKey(), albertaConnectionDetails.getTenant(), patientPayer.getPayerId());

		if (payer == null)
		{
			throw new RuntimeException("No info returned for payer: " + patientPayer.getPayerId());
		}
		return payer;
	}

	@Nullable
	private Pharmacy getPharmacyOrNull(
			@NonNull final PharmacyApi pharmacyApi,
			@NonNull final AlbertaConnectionDetails albertaConnectionDetails,
			@Nullable final String pharmacyId) throws ApiException
	{
		if (EmptyUtil.isBlank(pharmacyId))
		{
			return null;
		}

		final Pharmacy pharmacy = pharmacyApi.getPharmacy(albertaConnectionDetails.getApiKey(), albertaConnectionDetails.getTenant(), pharmacyId);

		if (pharmacy == null)
		{
			throw new RuntimeException("No info returned for pharmacy: " + pharmacyId);
		}
		return pharmacy;
	}
}
