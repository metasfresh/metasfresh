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

import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import io.swagger.client.ApiClient;
import io.swagger.client.api.DoctorApi;
import io.swagger.client.api.HospitalApi;
import io.swagger.client.api.NursingHomeApi;
import io.swagger.client.api.NursingServiceApi;
import io.swagger.client.api.PatientApi;
import io.swagger.client.api.PayerApi;
import io.swagger.client.api.PharmacyApi;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_DOCTOR_API;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_HOSPITAL_API;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_NURSINGHOME_API;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_NURSINGSERVICE_API;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_PATIENT_API;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_ALBERTA_PAYER_API;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_ALBERTA_PHARMACY_API;

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
		final var apiClient = new ApiClient().setBasePath(basePath);

		exchange.setProperty(ROUTE_PROPERTY_PATIENT_API, new PatientApi(apiClient));
		exchange.setProperty(ROUTE_PROPERTY_DOCTOR_API, new DoctorApi(apiClient));
		exchange.setProperty(ROUTE_PROPERTY_NURSINGHOME_API, new NursingHomeApi(apiClient));
		exchange.setProperty(ROUTE_PROPERTY_NURSINGSERVICE_API, new NursingServiceApi(apiClient));
		exchange.setProperty(ROUTE_PROPERTY_HOSPITAL_API, new HospitalApi(apiClient));
		exchange.setProperty(ROUTE_PROPERTY_ALBERTA_PAYER_API, new PayerApi(apiClient));
		exchange.setProperty(ROUTE_PROPERTY_ALBERTA_PHARMACY_API, new PharmacyApi(apiClient));
	}
}
