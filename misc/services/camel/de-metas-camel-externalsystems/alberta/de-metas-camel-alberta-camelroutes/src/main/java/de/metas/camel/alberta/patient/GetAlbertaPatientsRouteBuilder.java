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

package de.metas.camel.alberta.patient;

import de.metas.camel.alberta.patient.processor.CreateBPRelationReqProcessor;
import de.metas.camel.alberta.patient.processor.CreateBPartnerReqProcessor;
import de.metas.camel.alberta.patient.processor.CreateESRQueryProcessor;
import de.metas.camel.alberta.patient.processor.RetrievePatientsProcessor;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.common.bpartner.v1.response.JsonResponseBPartnerCompositeUpsert;
import de.metas.common.externalreference.JsonExternalReferenceLookupResponse;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.StaticEndpointBuilders;
import org.springframework.stereotype.Component;

import static de.metas.camel.alberta.CamelRouteUtil.setupJacksonDataFormatFor;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;

@Component
public class GetAlbertaPatientsRouteBuilder extends RouteBuilder
{
	public static final String GET_PATIENTS_ROUTE_ID = "Alberta-getPatients";
	public static final String PROCESS_PATIENT_ROUTE_ID = "Alberta-processPatient";

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(StaticEndpointBuilders.direct(MF_ERROR_ROUTE_ID));

		//@formatter:off
		// this EP's name is matching the JsonExternalSystemRequest's ExternalSystem and Command
		from(StaticEndpointBuilders.direct(GET_PATIENTS_ROUTE_ID))
				.routeId(GET_PATIENTS_ROUTE_ID)
				.streamCaching()
				.process(new RetrievePatientsProcessor())
				.split(body())
					.to(StaticEndpointBuilders.direct(PROCESS_PATIENT_ROUTE_ID))
				.end();

		from(StaticEndpointBuilders.direct(PROCESS_PATIENT_ROUTE_ID))
				.routeId(PROCESS_PATIENT_ROUTE_ID)
				.process(new CreateESRQueryProcessor())

				.log(LoggingLevel.DEBUG, "Calling metasfresh-api to query ESR records: ${body}")
				.to("{{" + ExternalSystemCamelConstants.MF_LOOKUP_EXTERNALREFERENCE_CAMEL_URI + "}}")

				.unmarshal(setupJacksonDataFormatFor(getContext(), JsonExternalReferenceLookupResponse.class))
				.process(new CreateBPartnerReqProcessor())

				.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert BPartners: ${body}")
				.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_CAMEL_URI + "}}")

				.unmarshal(setupJacksonDataFormatFor(getContext(), JsonResponseBPartnerCompositeUpsert.class))
				.process(new CreateBPRelationReqProcessor())

				.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert BPRelations: ${body}")
				.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPRELATION_CAMEL_URI + "}}");
		//@formatter:on
	}
}
