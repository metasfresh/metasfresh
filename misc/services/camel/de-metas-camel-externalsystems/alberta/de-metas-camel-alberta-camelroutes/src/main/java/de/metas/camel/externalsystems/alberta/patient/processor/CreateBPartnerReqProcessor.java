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

package de.metas.camel.externalsystems.alberta.patient.processor;

import de.metas.camel.externalsystems.alberta.common.AlbertaConnectionDetails;
import de.metas.camel.externalsystems.alberta.common.AlbertaUtil;
import de.metas.camel.externalsystems.alberta.patient.BPartnerUpsertRequestProducer;
import de.metas.camel.externalsystems.alberta.patient.GetPatientsRouteConstants;
import de.metas.camel.externalsystems.alberta.patient.GetPatientsRouteContext;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.common.externalreference.v1.JsonExternalReferenceLookupResponse;
import de.metas.common.util.EmptyUtil;
import io.swagger.client.ApiException;
import io.swagger.client.api.DoctorApi;
import io.swagger.client.api.HospitalApi;
import io.swagger.client.api.NursingHomeApi;
import io.swagger.client.api.NursingServiceApi;
import io.swagger.client.api.PayerApi;
import io.swagger.client.api.PharmacyApi;
import io.swagger.client.api.UserApi;
import io.swagger.client.model.Doctor;
import io.swagger.client.model.Hospital;
import io.swagger.client.model.NursingHome;
import io.swagger.client.model.NursingService;
import io.swagger.client.model.Patient;
import io.swagger.client.model.PatientHospital;
import io.swagger.client.model.PatientPayer;
import io.swagger.client.model.Payer;
import io.swagger.client.model.Pharmacy;
import io.swagger.client.model.Users;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.annotation.Nullable;

/**
 * Takes a {@link JsonExternalReferenceLookupResponse} and the current {@link Patient} and transformes them into a {@link BPUpsertCamelRequest}.
 */
public class CreateBPartnerReqProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final Patient patient = exchange.getIn().getBody(Patient.class);

		final String orgCode = ProcessorHelper.getPropertyOrThrowError(exchange, GetPatientsRouteConstants.ROUTE_PROPERTY_ORG_CODE, String.class);

		final GetPatientsRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange, GetPatientsRouteConstants.ROUTE_PROPERTY_GET_PATIENTS_CONTEXT, GetPatientsRouteContext.class);

		final AlbertaConnectionDetails connectionDetails = routeContext.getAlbertaConnectionDetails();

		final Doctor doctorOrNull = getDoctorOrNull(routeContext.getDoctorApi(), connectionDetails, patient.getPrimaryDoctorId());

		final NursingHome nursingHomeOrNull = getNursingHomeOrNull(routeContext.getNursingHomeApi(), connectionDetails, patient.getNursingHomeId());

		final NursingService nursingServiceOrNull = getNursingServiceOrNull(routeContext.getNursingServiceApi(), connectionDetails, patient.getNursingServiceId());

		final Hospital hospital = getHospital(routeContext.getHospitalApi(), connectionDetails, patient.getHospital());

		final Payer payerOrNull = getPayerOrNull(routeContext.getPayerApi(), connectionDetails, patient.getPayer());

		final Pharmacy pharmacyOrNull = getPharmacyOrNull(routeContext.getPharmacyApi(), connectionDetails, patient.getPharmacyId());

		final Users createdBy = getUserOrNull(routeContext.getUserApi(), connectionDetails, patient.getCreatedBy());
		final Users updatedBy = getUserOrNull(routeContext.getUserApi(), connectionDetails, patient.getUpdatedBy());

		final BPartnerUpsertRequestProducer bPartnerUpsertRequestProducer = BPartnerUpsertRequestProducer.builder()
				.patient(patient)
				.orgCode(orgCode)
				.doctor(doctorOrNull)
				.nursingHome(nursingHomeOrNull)
				.nursingService(nursingServiceOrNull)
				.hospital(hospital)
				.payer(payerOrNull)
				.pharmacy(pharmacyOrNull)
				.createdBy(createdBy)
				.updatedBy(updatedBy)
				.rootBPartnerIdForUsers(routeContext.getRootBPartnerIdForUsers())
				.build();

		final BPartnerUpsertRequestProducer.BPartnerRequestProducerResult result = bPartnerUpsertRequestProducer.run();

		final BPartnerRoleInfoProvider bPartnerRoleInfoProvider = BPartnerRoleInfoProvider.builder()
				.sourceBPartnerIdentifier(result.getPatientBPartnerIdentifier())
				.bpIdentifier2Role(result.getBPartnerIdentifier2RelationRole())
				.sourceBPartnerLocationIdentifier(result.getPatientMainAddressIdentifier())
				.build();

		exchange.getIn().setBody(result.getJsonRequestBPartnerUpsert());
		exchange.setProperty(GetPatientsRouteConstants.ROUTE_PROPERTY_BP_IDENTIFIER_TO_ROLE, bPartnerRoleInfoProvider);

		routeContext.setUpdatedAfterValue(AlbertaUtil.asInstant(patient.getCreatedAt()));
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

		final Doctor doctor = doctorApi.getDoctor(albertaConnectionDetails.getApiKey(), doctorId);

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

		final NursingHome nursingHome = nursingHomeApi.geNursingHome(albertaConnectionDetails.getApiKey(), nursingHomeId);

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

		final NursingService nursingService = nursingServiceApi.getNursingService(albertaConnectionDetails.getApiKey(), nursingServiceId);

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

		final Hospital hospital = hospitalApi.getHospital(albertaConnectionDetails.getApiKey(), patientHospital.getHospitalId());

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

		final Payer payer = payerApi.getPayer(albertaConnectionDetails.getApiKey(), patientPayer.getPayerId());

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

		final Pharmacy pharmacy = pharmacyApi.getPharmacy(albertaConnectionDetails.getApiKey(), pharmacyId);

		if (pharmacy == null)
		{
			throw new RuntimeException("No info returned for pharmacy: " + pharmacyId);
		}
		return pharmacy;
	}

	@Nullable
	private Users getUserOrNull(
			@NonNull final UserApi userApi,
			@NonNull final AlbertaConnectionDetails albertaConnectionDetails,
			@Nullable final String userId) throws ApiException
	{
		if (EmptyUtil.isBlank(userId))
		{
			return null;
		}

		final Users user = userApi.getUser(albertaConnectionDetails.getApiKey(), userId);

		if (user == null)
		{
			throw new RuntimeException("No info returned for user: " + userId);
		}
		return user;
	}
}
