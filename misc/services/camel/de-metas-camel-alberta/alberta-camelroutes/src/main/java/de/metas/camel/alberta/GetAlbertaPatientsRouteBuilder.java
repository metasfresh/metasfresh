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

package de.metas.camel.alberta;

import de.metas.common.externalsystem.JsonExternalSystemRequest;
import io.swagger.client.ApiClient;
import io.swagger.client.api.PatientApi;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class GetAlbertaPatientsRouteBuilder extends RouteBuilder
{
	@Override
	public void configure()
	{
		from("direct:Alberta-GetPatients") // this EP's name is matching the JsonExternalSystemRequest's ExternalSystem and Command
				.process(exchange -> {
					
					final var request = exchange.getIn().getBody(JsonExternalSystemRequest.class);
					var apiKey = request.getParameters().get("APIKey");
					var tenant = request.getParameters().get("Tenant");
					var updatedAfter = request.getParameters().get("UpdatedAfter");
					var basePath = request.getParameters().get("BasePath");

					final var apiClient = new ApiClient().setBasePath(basePath);

					final var patients = new PatientApi(apiClient).getCreatedPatients(apiKey, tenant, "both", updatedAfter);

					exchange.getIn().setBody(patients);
				})
				// .split() // from here we have 1 message per patient
				//.transform(patient-to-metasfresh-bpartner)
				//.to("direct:metasfresh-put-bpartner") // no need to worry about metasfresh-URLs, API-Keys etc
		;
	}
}
