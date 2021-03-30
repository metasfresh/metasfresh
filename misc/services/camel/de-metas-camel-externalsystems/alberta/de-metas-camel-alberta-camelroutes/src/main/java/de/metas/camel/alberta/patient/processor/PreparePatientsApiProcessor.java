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
import io.swagger.client.api.PatientApi;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_ALBERTA_PATIENT_API;

/**
 * Prepares the Alberta patient PAI and adds it to the processor.
 * This is in a dedicated processor to allow injecting a mocked API.
 */
public class PreparePatientsApiProcessor implements Processor
{
	@Override
	public void process(@NonNull final Exchange exchange) throws Exception
	{
		final var request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final var basePath = request.getParameters().get(ExternalSystemConstants.PARAM_BASE_PATH);
		final var apiClient = new ApiClient().setBasePath(basePath);

		final var patientApi = new PatientApi(apiClient);
		
		exchange.setProperty(ROUTE_PROPERTY_ALBERTA_PATIENT_API, patientApi);
	}
}
