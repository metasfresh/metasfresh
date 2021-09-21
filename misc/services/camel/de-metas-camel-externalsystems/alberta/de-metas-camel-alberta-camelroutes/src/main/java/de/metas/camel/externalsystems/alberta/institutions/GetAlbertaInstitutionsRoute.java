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

import de.metas.camel.externalsystems.alberta.common.AlbertaConnectionDetails;
import de.metas.camel.externalsystems.alberta.institutions.processor.PrepareInstitutionToBPartnerProcessor;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.common.bpartner.v2.request.alberta.JsonBPartnerRole;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import io.swagger.client.ApiClient;
import io.swagger.client.api.DoctorApi;
import io.swagger.client.api.HospitalApi;
import io.swagger.client.api.NursingHomeApi;
import io.swagger.client.api.NursingServiceApi;
import io.swagger.client.api.PayerApi;
import io.swagger.client.api.PharmacyApi;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class GetAlbertaInstitutionsRoute extends RouteBuilder
{
	public static final String EXTERNAL_SYSTEM_REQUEST = "Alberta-syncBPartnerById";
	public static final String PREPARE_ALBERTA_INSTITUTIONS_CONTEXT_PROCESSOR_ID = "AlbertaBPartners-PrepareAlbertaInstitutionContextProcessor";
	public static final String PREPARE_ALBERTA_BPARTNER_PROCESSOR_ID = "AlbertaBPartners-PrepareInstitutionToBPartnerProcessor";

	@Override
	public void configure() throws Exception
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		//@formatter:off

		// this EP's name is matching the JsonExternalSystemRequest's ExternalSystem and Command
		from(direct(EXTERNAL_SYSTEM_REQUEST))
				.routeId(EXTERNAL_SYSTEM_REQUEST)
				.log("Route invoked!")
				.process(this::prepareContext).id(PREPARE_ALBERTA_INSTITUTIONS_CONTEXT_PROCESSOR_ID)

				.process(new PrepareInstitutionToBPartnerProcessor()).id(PREPARE_ALBERTA_BPARTNER_PROCESSOR_ID)

				.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert BPartner: ${body}")

				.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}");
		//@formatter:on
	}

	private void prepareContext(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		if (request == null)
		{
			throw new RuntimeCamelException("Missing exchange body! No JsonExternalSystemRequest found!");
		}

		final String orgCode = request.getOrgCode();

		final String basePath = request.getParameters().get(ExternalSystemConstants.PARAM_BASE_PATH);
		final String apiKey = request.getParameters().get(ExternalSystemConstants.PARAM_API_KEY);
		final String tenant = request.getParameters().get(ExternalSystemConstants.PARAM_TENANT);

		final String albertaResourceId = request.getParameters().get(ExternalSystemConstants.PARAM_ALBERTA_ID);
		final String role = request.getParameters().get(ExternalSystemConstants.PARAM_ALBERTA_ROLE);

		final AlbertaConnectionDetails albertaConnectionDetails = AlbertaConnectionDetails.builder()
				.apiKey(apiKey)
				.basePath(basePath)
				.tenant(tenant)
				.build();

		final ApiClient apiClient = new ApiClient().setBasePath(basePath);

		final GetInstitutionsRouteContext context = GetInstitutionsRouteContext.builder()
				.orgCode(orgCode)
				.albertaConnectionDetails(albertaConnectionDetails)
				.albertaResourceId(albertaResourceId)
				.role(JsonBPartnerRole.valueOf(role))
				.doctorApi(new DoctorApi(apiClient))
				.hospitalApi(new HospitalApi(apiClient))
				.nursingHomeApi(new NursingHomeApi(apiClient))
				.nursingServiceApi(new NursingServiceApi(apiClient))
				.payerApi(new PayerApi(apiClient))
				.pharmacyApi(new PharmacyApi(apiClient))
				.build();

		exchange.setProperty(GetInstitutionsRouteConstants.ROUTE_PROPERTY_GET_INSTITUTIONS_CONTEXT, context);

		if (request.getAdPInstanceId() != null)
		{
			exchange.getIn().setHeader(HEADER_PINSTANCE_ID, request.getAdPInstanceId().getValue());
		}
	}
}
