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
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import io.swagger.client.ApiClient;
import io.swagger.client.api.PatientApi;
import io.swagger.client.model.Patient;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static de.metas.camel.alberta.GetPatientsRouteConstants.HEADER_ORG_CODE;
import static de.metas.camel.alberta.GetPatientsRouteConstants.PatientStatus.CREATED;
import static de.metas.camel.alberta.GetPatientsRouteConstants.PatientStatus.UPDATED;
import static de.metas.camel.alberta.GetPatientsRouteConstants.ROUTE_PROPERTY_ALBERTA_CONN_DETAILS;
import static de.metas.camel.alberta.GetPatientsRouteConstants.ROUTE_PROPERTY_ORG_CODE;

public class RetrievePatientsProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final var request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final var apiKey = request.getParameters().get(ExternalSystemConstants.PARAM_API_KEY);
		final var tenant = request.getParameters().get(ExternalSystemConstants.PARAM_TENANT);
		final var basePath = request.getParameters().get(ExternalSystemConstants.PARAM_BASE_PATH);
		final var updatedAfter = request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER);

		final var apiClient = new ApiClient().setBasePath(basePath);

		final var patientApi = new PatientApi(apiClient);
		final var createdPatients = patientApi.getCreatedPatients(apiKey, tenant, CREATED.getValue(), updatedAfter);

		final List<Patient> patientsToImport = createdPatients == null || createdPatients.isEmpty()
				? new ArrayList<>()
				: createdPatients;

		final Set<String> createdPatientIds = createdPatients == null || createdPatients.isEmpty()
		? new HashSet<>()
		: createdPatients.stream().map(Patient::getId).filter(Objects::nonNull).map(UUID::toString).collect(Collectors.toSet());

		final List<Patient> updatedPatients = patientApi.getCreatedPatients(apiKey, tenant, UPDATED.getValue(), updatedAfter);

		if (updatedPatients != null && !updatedPatients.isEmpty())
		{
			updatedPatients.stream()
					.filter(patient -> patient.getId() != null && !createdPatientIds.contains(patient.getId().toString()))
					.forEach(patientsToImport::add);
		}

		final AlbertaConnectionDetails albertaConnectionDetails = AlbertaConnectionDetails.builder()
				.apiKey(apiKey)
				.basePath(basePath)
				.tenant(tenant)
				.build();

		exchange.getIn().setHeader(HEADER_ORG_CODE, request.getOrgCode());
		exchange.setProperty(ROUTE_PROPERTY_ORG_CODE, request.getOrgCode());
		exchange.setProperty(ROUTE_PROPERTY_ALBERTA_CONN_DETAILS, albertaConnectionDetails);
		exchange.getIn().setBody(patientsToImport);
	}
}
