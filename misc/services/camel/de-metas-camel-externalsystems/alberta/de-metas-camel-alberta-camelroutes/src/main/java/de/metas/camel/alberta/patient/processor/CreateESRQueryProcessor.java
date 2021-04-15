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

import de.metas.common.externalreference.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.JsonExternalReferenceLookupRequest;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.util.EmptyUtil;
import io.swagger.client.model.CareGiver;
import io.swagger.client.model.Patient;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ALBERTA_SYSTEM_NAME;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ESR_TYPE_BPARTNER;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_CURRENT_PATIENT;

/**
 * Note: ESR stands for External System Reference
 */
public class CreateESRQueryProcessor implements Processor
{
	@Override
	public void process(@NonNull final Exchange exchange) throws Exception
	{
		final Patient patient = exchange.getIn().getBody(Patient.class);

		final JsonExternalReferenceLookupRequest esrLookupRequest = buildESRLookupRequest(patient);

		exchange.getIn().setBody(esrLookupRequest);

		exchange.setProperty(ROUTE_PROPERTY_CURRENT_PATIENT, patient);
	}

	@NonNull
	private JsonExternalReferenceLookupRequest buildESRLookupRequest(@NonNull final Patient patient)
	{
		final String patientId = Optional.ofNullable(patient.getId())
				.map(UUID::toString)
				.orElseThrow(() -> new RuntimeException("Missing patient._id!"));

		final JsonExternalReferenceLookupRequest.JsonExternalReferenceLookupRequestBuilder jsonExternalReferenceLookupRequest =
				JsonExternalReferenceLookupRequest.builder()
				.systemName(JsonExternalSystemName.of(ALBERTA_SYSTEM_NAME))
				.item(getBPLookupItem(patientId));

		if (EmptyUtil.isNotBlank(patient.getPrimaryDoctorId()))
		{
			jsonExternalReferenceLookupRequest.item(getBPLookupItem(patient.getPrimaryDoctorId()));
		}

		if (EmptyUtil.isNotBlank(patient.getNursingHomeId()))
		{
			jsonExternalReferenceLookupRequest.item(getBPLookupItem(patient.getNursingHomeId()));
		}

		if (EmptyUtil.isNotBlank(patient.getNursingServiceId()))
		{
			jsonExternalReferenceLookupRequest.item(getBPLookupItem(patient.getNursingServiceId()));
		}

		if (EmptyUtil.isNotBlank(patient.getPharmacyId()))
		{
			jsonExternalReferenceLookupRequest.item(getBPLookupItem(patient.getPharmacyId()));
		}

		if (patient.getHospital() != null && patient.getHospital().getHospitalId() != null)
		{
			jsonExternalReferenceLookupRequest.item(getBPLookupItem(patient.getHospital().getHospitalId()));
		}

		if (patient.getCareGivers() != null && !patient.getCareGivers().isEmpty())
		{
			patient.getCareGivers().stream()
					.map(CareGiver::getId)
					.filter(Objects::nonNull)
					.map(UUID::toString)
					.forEach(careGiveId -> jsonExternalReferenceLookupRequest.item(getBPLookupItem(careGiveId)));
		}

		if (patient.getPayer() != null && patient.getPayer().getPayerId() != null)
		{
			jsonExternalReferenceLookupRequest.item(getBPLookupItem(patient.getPayer().getPayerId()));
		}

		return jsonExternalReferenceLookupRequest.build();
	}

	@NonNull
	private JsonExternalReferenceLookupItem getBPLookupItem(@NonNull final String externalId)
	{
		return JsonExternalReferenceLookupItem.builder()
				.type(ESR_TYPE_BPARTNER)
				.id(externalId)
				.build();
	}

}
