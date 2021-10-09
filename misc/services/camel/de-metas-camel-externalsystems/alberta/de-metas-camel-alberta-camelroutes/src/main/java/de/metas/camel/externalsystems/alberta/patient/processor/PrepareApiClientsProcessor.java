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
import de.metas.camel.externalsystems.alberta.patient.GetPatientsRouteConstants;
import de.metas.camel.externalsystems.alberta.patient.GetPatientsRouteContext;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import io.swagger.client.ApiClient;
import io.swagger.client.api.DoctorApi;
import io.swagger.client.api.HospitalApi;
import io.swagger.client.api.NursingHomeApi;
import io.swagger.client.api.NursingServiceApi;
import io.swagger.client.api.PatientApi;
import io.swagger.client.api.PayerApi;
import io.swagger.client.api.PharmacyApi;
import io.swagger.client.api.UserApi;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.time.Instant;

/**
 * Prepares the Alberta client API and adds them to the processor.
 * This is in a dedicated processor to allow injecting a mocked API.
 */
public class PrepareApiClientsProcessor implements Processor
{
	@Override
	public void process(@NonNull final Exchange exchange) throws Exception
	{
		final var request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final var basePath = request.getParameters().get(ExternalSystemConstants.PARAM_BASE_PATH);
		final var apiKey = request.getParameters().get(ExternalSystemConstants.PARAM_API_KEY);
		final var tenant = request.getParameters().get(ExternalSystemConstants.PARAM_TENANT);
		final String rootBPartnerId = request.getParameters().get(ExternalSystemConstants.PARAM_ROOT_BPARTNER_ID_FOR_USERS);
		final JsonMetasfreshId rootBPartnerMFId = Check.isNotBlank(rootBPartnerId) ? JsonMetasfreshId.of(Integer.parseInt(rootBPartnerId)) : null;

		final var apiClient = new ApiClient().setBasePath(basePath);

		final AlbertaConnectionDetails albertaConnectionDetails = AlbertaConnectionDetails.builder()
				.apiKey(apiKey)
				.basePath(basePath)
				.tenant(tenant)
				.build();

		final String updatedAfter = CoalesceUtil.coalesce(
				request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER_OVERRIDE),
				request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER),
				Instant.ofEpochMilli(0).toString());

		final GetPatientsRouteContext context = GetPatientsRouteContext.builder()
				.doctorApi(new DoctorApi(apiClient))
				.hospitalApi(new HospitalApi(apiClient))
				.nursingHomeApi(new NursingHomeApi(apiClient))
				.nursingServiceApi(new NursingServiceApi(apiClient))
				.patientApi(new PatientApi(apiClient))
				.payerApi(new PayerApi(apiClient))
				.pharmacyApi(new PharmacyApi(apiClient))
				.userApi(new UserApi(apiClient))
				.albertaConnectionDetails(albertaConnectionDetails)
				.rootBPartnerIdForUsers(rootBPartnerMFId)
				.request(request)
				.updatedAfterValue(Instant.parse(updatedAfter))
				.build();

		exchange.setProperty(GetPatientsRouteConstants.ROUTE_PROPERTY_GET_PATIENTS_CONTEXT, context);
	}
}
